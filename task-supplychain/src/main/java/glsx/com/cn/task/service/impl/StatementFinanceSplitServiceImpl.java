package glsx.com.cn.task.service.impl;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.FinanceSettleTypeEnum;
import cn.com.glsx.supplychain.enums.ProductSplitMaterialTypeEnum;
import cn.com.glsx.supplychain.enums.ProductSplitServiceTypeEnum;
import cn.com.glsx.supplychain.enums.StatementFinanceStatusEnum;
import cn.com.glsx.supplychain.enums.SureOrNotEnum;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.am.*;

import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;

import glsx.com.cn.task.common.Constants;
import glsx.com.cn.task.mapper.AttribInfoMapper;
import glsx.com.cn.task.mapper.DealerUserInfoMapper;
import glsx.com.cn.task.mapper.am.*;
import glsx.com.cn.task.model.AmSyncLastidRecord;
import glsx.com.cn.task.model.DealerUserInfo;
import glsx.com.cn.task.service.StatementFinanceSplitService;
import glsx.com.cn.task.util.TaskUtils;

import org.apache.commons.lang3.StringUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatementFinanceSplitServiceImpl implements
		StatementFinanceSplitService {

	@Autowired
	private StatementFinanceMapper statementFinanceMapper;
	@Autowired
	private StatementFinanceSplitMapper statementFinanceSplitMapper;
	@Autowired
	private StatementFinanceCombileMapper statementFinanceCombileMapper;
	@Autowired
	private ProductSplitMapper productSplitMapper;
	@Autowired
	private ProductSplitDetailMapper productSplitDetailMapper;
	@Autowired
	private ProductSplitHistoryPriceMapper productSplitHistoryPriceMapper;
	@Autowired
	private AmSyncLastidRecordMapper amSyncLastidRecordMapper;
	@Autowired
	private DealerUserInfoMapper dealerUserInfoMapper;
	@Autowired
	private IMaterialDubboService iMaterialDubboService;
	@Autowired
	private KcCustomerRelationMapper kcCustomerRelationMapper;
	@Autowired
	private KcWarehouseRelationMapper kcWarehouseRelationMapper;	
	@Autowired
	private MaterialInfoMapper materialInfoMapper;
	@Autowired
	private AttribInfoMapper attribInfoMapper;
	@Autowired
	private ProductSplitSourceFlagMapper sourceFlagMapper;
	@Autowired
	private SnowflakeWorker snowflakeWorker;
	@Autowired
	private StatementSnMapper statementSnMapper;
	

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 金融风控
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void statementFinanceSplit() throws RpcServiceException {
		try {
			AmSyncLastidRecord syncLastidRecord = amSyncLastidRecordMapper
					.getSyncLastidRecord();
			StatementFinance financeCondition = new StatementFinance();
			financeCondition
					.setId(syncLastidRecord.getLastStatementFinanceId());
			logger.info("statementFinanceSplit：syncLastidRecord.getLastStatementFinanceId:"
					+ syncLastidRecord.getLastStatementFinanceId());
			List<StatementFinance> listFinances = statementFinanceMapper
					.listStatementFinance(financeCondition);
			if (listFinances == null || listFinances.size() == 0) {
				return;
			}
			logger.info("statementFinanceSplit：listFinances.size=" + listFinances.size());
			Map<String, DealerUserInfo> mapCustomCode2DealerUser = new HashMap<>();
			Map<String, List<ProductSplit>> mapCache2ListProductSplit = new HashMap<>();
			Map<Integer,AttribInfo> mapAttribInfo	= new HashMap<>();
			Map<String,ProductSplitSourceFlag> mapSourceFlag = new HashMap<>();
			List<StatementFinanceSplit> listFinanceSplits = new ArrayList<>();
			List<StatementFinanceCombile> listFinanceCombile = new ArrayList<>();
			List<StatementSn> listStatementSn = new ArrayList<>();
				
			DealerUserInfo dealerUserInfo = null;	
			String merchantDevices = "";
			ProductSplit productSplitBase = null;
			ProductSplit productSplitExpr = null;
			Byte jrfkServiceType = ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_TWO
					.getCode();
			Byte jbwyServiceType = ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_ONE
					.getCode();
			
			// 产品编码 对应产品拆分规则列表
			Map<String, List<ProductSplitHistoryPrice>> mapProductCode2ListProductSplitPrice = new HashMap<>();
			Map<String,MaterialInfo> mapMaterialCode2MaterialInfo = new HashMap<>();
			mapCustomCode2DealerUser = getCustomCode2DealerUserMap(
					listFinances, mapCustomCode2DealerUser);

			for (StatementFinance finance : listFinances) {
				
				syncLastidRecord.setLastStatementFinanceId(finance.getId());
				dealerUserInfo = getDealerUserInfoByCustomCode(
						finance.getCustomerCode(), mapCustomCode2DealerUser);
				if (null == dealerUserInfo) {
					finance.setReasons("未找到客户编码或者商户用户未创建");
					finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
							.getCode());
					continue;
				}
				
				//新装结算
				if(isFinanceSettleTypeN(finance.getWorkType())){					
					productSplitBase = getProductSplitByJRFK(jrfkServiceType,finance,dealerUserInfo,mapCache2ListProductSplit,mapAttribInfo,mapSourceFlag);
					productSplitExpr = getProductSplitByJBWY(jbwyServiceType, finance, dealerUserInfo, mapCache2ListProductSplit);
					if(null == productSplitBase){
						continue;
					}
					if(finance.getIsSure().equals(SureOrNotEnum.SURE_YES.getCode())){
						if(null == productSplitExpr){
							continue;
						}
					}		
					doSplitStatementFinance(finance,productSplitBase,productSplitExpr,mapProductCode2ListProductSplitPrice,mapMaterialCode2MaterialInfo,listFinanceSplits);
				//续费
				}else if(isFinanceSettleTypeC(finance.getWorkType())){
					productSplitBase = getProductSplitByJRFK(jrfkServiceType,finance,dealerUserInfo,mapCache2ListProductSplit,mapAttribInfo,mapSourceFlag);
					if(null == productSplitBase){
						continue;
					}
					doSplitStatementFinanceBySettleTypeC(finance,productSplitBase,mapProductCode2ListProductSplitPrice,mapMaterialCode2MaterialInfo,listFinanceSplits);	
				//特殊场景
				}else if(isFinanceSettleTypeBDISH(finance.getWorkType())){
					doSplitStatementFinanceBySettleTypeBDISH(finance,mapMaterialCode2MaterialInfo,listFinanceSplits);
				}	
				
				doFiltStatementSn(finance,productSplitBase,mapMaterialCode2MaterialInfo,listStatementSn);
				
			}			
			doCombieStatementFinance(listFinanceSplits,listFinanceCombile);
			
			setDBStatementSn(listStatementSn);
			setDBStatementFinance(listFinances);
			setDBStatementFinanceSplit(listFinanceSplits);
			setDBStatementFinanceCombile(listFinanceCombile);

			amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RpcServiceException(e.getMessage());
		}
	}
	
	private void doFiltStatementSn(StatementFinance finance,ProductSplit productSplitBase,
			Map<String,MaterialInfo> mapMaterialCode2MaterialInfo,
			List<StatementSn> listStatementSn){
		if(null == productSplitBase){
			return;
		}
		if(!StringUtils.isEmpty(finance.getDeviceNumberOne())){
			MaterialInfo materialInfo = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeOne(), mapMaterialCode2MaterialInfo);
			if(null == materialInfo){
				return;
			}
			listStatementSn.add(generatorStatementSn(finance.getDeviceNumberOne(),finance,productSplitBase,materialInfo));
		}
		if(!StringUtils.isEmpty(finance.getDeviceNumberTwo())){
			MaterialInfo materialInfo = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeTwo(), mapMaterialCode2MaterialInfo);
			if(null == materialInfo){
				return;
			}
			listStatementSn.add(generatorStatementSn(finance.getDeviceNumberTwo(),finance,productSplitBase,materialInfo));
		}
	}
	
	private StatementSn generatorStatementSn(String sn,StatementFinance finance,ProductSplit productSplitBase,MaterialInfo materialInfo){
		StatementSn statementSn = new StatementSn();
		statementSn.setSn(sn);
		statementSn.setProductCode(StringUtils.isEmpty(productSplitBase.getProductCode())?"":productSplitBase.getProductCode());
		statementSn.setProductName(StringUtils.isEmpty(productSplitBase.getProductName())?"":productSplitBase.getProductName());
		statementSn.setMaterialCode(StringUtils.isEmpty(materialInfo.getMaterialCode())?"":materialInfo.getMaterialCode());
		statementSn.setMaterialName(StringUtils.isEmpty(materialInfo.getMaterialName())?"":materialInfo.getMaterialName());
		statementSn.setVtSn("N");
		statementSn.setStatementType("FINA");
		statementSn.setWorkOrder(finance.getWorkOrder());
		statementSn.setCreatedBy(finance.getCreatedBy());
		statementSn.setCreatedDate(finance.getCreatedDate());
		statementSn.setUpdatedBy(finance.getUpdatedBy());
		statementSn.setUpdatedDate(finance.getUpdatedDate());
		statementSn.setDeletedFlag("N");
		return statementSn;
	}
	
	private boolean isFinanceSettleTypeN(String settleType){
		return settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_N.getCode());
	}
	
	private boolean isFinanceSettleTypeC(String settleType){
		return settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_C.getCode());
	}
	
	private boolean isFinanceSettleTypeBDISH(String settleType){
		if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getCode()))
			return true;
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getCode()))
			return true;
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getCode()))
			return true;
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getCode()))
			return true;
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getCode()))
			return true;
		return false;
	}
	
	private void setDBStatementSn(List<StatementSn> listStatementSn){
		if(null == listStatementSn || listStatementSn.size() == 0){
			return;
		}
		statementSnMapper.batchInsertReplace(listStatementSn);
	}
	
	private void setDBStatementFinanceCombile(List<StatementFinanceCombile> listFinanceCombile){
		if(null == listFinanceCombile || listFinanceCombile.size() == 0){
			return;
		}
		statementFinanceCombileMapper.insertList(listFinanceCombile);
	}
	
	private void setDBStatementFinanceSplit(List<StatementFinanceSplit> listFinanceSplits){
		if(null == listFinanceSplits || listFinanceSplits.size() == 0){
			return;
		}
		statementFinanceSplitMapper.insertList(listFinanceSplits);
	}
	
	private void setDBStatementFinance(List<StatementFinance> listFinances){
		if(null == listFinances || listFinances.size() == 0){
			return;
		}
		statementFinanceMapper.batchInsertOnDuplicate(listFinances);
	}
	
	//合并 
	private void doCombieStatementFinance(List<StatementFinanceSplit> listFinanceSplits,List<StatementFinanceCombile> listFinanceCombile){
		
		Map<String,List<StatementFinanceSplit>> mapFinance = new HashMap<>();
		for(StatementFinanceSplit spilt:listFinanceSplits){
			String strKey = "s" + spilt.getSalesCode() + 
					"c" + spilt.getCustomerCode() + 
					"w" + spilt.getWarehouseCode() +
					"m" + spilt.getMaterialCode() +
					"p"+ spilt.getPrice() + 
					"wt" + spilt.getWorkType();
			List<StatementFinanceSplit> listSplit = mapFinance.get(strKey);
			if(null == listSplit){
				listSplit = new ArrayList<>();
				mapFinance.put(strKey, listSplit);
			}
			listSplit.add(spilt);
		}
		
		for(Map.Entry<String,List<StatementFinanceSplit>> entry:mapFinance.entrySet()){
			String combileCode = "jrfk" + TaskUtils.getOrderNumber(snowflakeWorker);
			List<StatementFinanceSplit> listSplit = entry.getValue();
			Integer count = 0;
			for(StatementFinanceSplit split:listSplit){
				count += split.getQuantity();
				split.setCombileCode(combileCode);
			}
			StatementFinanceSplit financeSplit = listSplit.get(0);
			listFinanceCombile.add(generateFinanceCombile(financeSplit,count,combileCode));
		}
		mapFinance = null;
	}
	
	private StatementFinanceCombile generateFinanceCombile(StatementFinanceSplit financeSplit,Integer count,String combileCode){
		StatementFinanceCombile financeCombile = new StatementFinanceCombile();
		financeCombile.setWorkType(financeSplit.getWorkType());
		financeCombile.setWorkOrder(financeSplit.getWorkOrder());
		financeCombile.setCombileCode(combileCode);
		financeCombile.setPrice(financeSplit.getPrice());
		financeCombile.setTime(financeSplit.getTime());
		financeCombile.setTakeGoodsDate(financeSplit.getTakeGoodsDate());
		financeCombile.setBillNumber(financeSplit.getBillNumber());
		financeCombile.setCustomerCode(financeSplit.getCustomerCode());
		financeCombile.setCustomerName(financeSplit.getCustomerName());
		financeCombile.setSaleGroupCode(financeSplit.getSaleGroupCode());
		financeCombile.setSaleGroupName(financeSplit.getSaleGroupName());
		financeCombile.setWarehouseCode(financeSplit.getWarehouseCode());
		financeCombile.setWarehouseName(financeSplit.getWarehouseName());
		financeCombile.setSalesQuantity(count);
		financeCombile.setQuantity(count);
		financeCombile.setMaterialCode(financeSplit.getMaterialCode());
		financeCombile.setMaterialName(financeSplit.getMaterialName());
		financeCombile.setTaxRate(financeSplit.getTaxRate());
		financeCombile.setCreatedBy(financeSplit.getCreatedBy());
		financeCombile.setUpdatedBy(financeSplit.getUpdatedBy());
		financeCombile.setCreatedDate(financeSplit.getCreatedDate());
		financeCombile.setUpdatedDate(financeSplit.getUpdatedDate());
		financeCombile.setDeletedFlag(financeSplit.getDeletedFlag());
		financeCombile.setBillTypeCode(financeSplit.getBillTypeCode());
		financeCombile.setBillTypeName(financeSplit.getBillTypeName());
		financeCombile.setSalesCode(financeSplit.getSalesCode());
		financeCombile.setSalesName(financeSplit.getSalesName());
		financeCombile.setStatementCurrencyCode(financeSplit.getStatementCurrencyCode());
		financeCombile.setStatementCurrencyName(financeSplit.getStatementCurrencyName());
		financeCombile.setSalesUnitCode(financeSplit.getSalesUnitCode());
		financeCombile.setSalesUnitName(financeSplit.getSalesUnitName());
		financeCombile.setStatementOrganizeCode(financeSplit.getStatementOrganizeCode());
		financeCombile.setStatementOrganizeName(financeSplit.getStatementOrganizeName());
		financeCombile.setReserveType(financeSplit.getReserveType());
		financeCombile.setGift(financeSplit.getGift());	
		financeCombile.setFinanceCustomerCode(financeSplit.getFinanceCustomerCode());
		return financeCombile;
	}
	
	private String getProductTypeByMetrialInfo(MaterialInfo materialInfo){
		Integer materialTypeId = materialInfo.getMaterialTypeId();
		if(null == materialTypeId){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_UNKNOW.getCode());
		}
		if(materialTypeId == 1){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_THREE.getCode());
		}
		if(materialTypeId == 46){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode());
		}
		if(materialTypeId == 47){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode());
		}
		if(materialTypeId == 44){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode());
		}
		if(materialTypeId == 49){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FIVE.getCode());
		}
		if(materialTypeId == 50){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_THREE.getCode());
		}
		if(materialTypeId == 51){
			return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SIX.getCode());
		}
		return String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_UNKNOW.getCode());
	}
	
	//拆分 特殊场景
	private void doSplitStatementFinanceBySettleTypeBDISH(StatementFinance finance,
			Map<String,MaterialInfo> mapMaterialCode2MaterialInfo,
			List<StatementFinanceSplit> listFinanceSplits){
		if(StringUtils.isEmpty(finance.getMaterialCode())){
			finance.setReasons("特殊场景未填物料编码 工单号:" + finance.getWorkOrder());
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return;
		}
		MaterialInfo materialInfo = this.getMaterialInfoByMaterialCode(finance.getMaterialCode(), mapMaterialCode2MaterialInfo);
		if(null == materialInfo){
			finance.setReasons("特殊场景未找到物料信息 工单号:" + finance.getWorkOrder());
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return;
		}
		ProductSplitHistoryPrice splitPrice = new ProductSplitHistoryPrice();
		splitPrice.setMaterialCode(materialInfo.getMaterialCode());
		splitPrice.setMaterialName(materialInfo.getMaterialName());
		splitPrice.setPrice(finance.getSum());	
		splitPrice.setProductType(getProductTypeByMetrialInfo(materialInfo));
		splitPrice.setTaxRate(finance.getWorkType().equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getCode())?13.0:6.0);		
		listFinanceSplits.add(generatorFinanceSplit(finance,splitPrice));
		finance.setReasons("");
		finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_SUCCESS.getCode());
	}
	
	//拆分 续费
	private void doSplitStatementFinanceBySettleTypeC(StatementFinance finance,
			ProductSplit productSplitBase,Map<String, 
			List<ProductSplitHistoryPrice>> mapProductCode2ListProductSplitPrice,
			Map<String,MaterialInfo> mapMaterialCode2MaterialInfo,
			List<StatementFinanceSplit> listFinanceSplits){
		
		List<ProductSplitHistoryPrice> listProductSplitPriceBase = null;
		listProductSplitPriceBase = getProductSplitPriceByProductCode(productSplitBase.getProductCode(),finance.getContractDate(),mapProductCode2ListProductSplitPrice);
		if(null == listProductSplitPriceBase || listProductSplitPriceBase.size() == 0)
		{
			finance.setReasons("经融风控产品未找到拆分规则 产品编码:" + productSplitBase.getProductCode());
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return;
		}
		//找到金融风控运营服务拆分规则
		ProductSplitHistoryPrice newPrice = null;
		for(ProductSplitHistoryPrice price:listProductSplitPriceBase){
			if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_THREE.getCode()))){
				newPrice = new ProductSplitHistoryPrice();
				TaskUtils.copyObject(newPrice, price);
				newPrice.setPrice(finance.getSum());
				break;
			}
		}
		if(null == newPrice){
			finance.setReasons("续费未找到拆分规则中的金融风控服务物料 产品编码:" + productSplitBase.getProductCode());
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return;
		}
		listFinanceSplits.add(generatorFinanceSplit(finance,newPrice));
		finance.setReasons("");
		finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_SUCCESS.getCode());
	}
	
	// 拆分
	private void doSplitStatementFinance(StatementFinance finance,
			ProductSplit productSplitBase,
			ProductSplit productSplitExpr,
			Map<String, List<ProductSplitHistoryPrice>> mapProductCode2ListProductSplitPrice,
			Map<String,MaterialInfo> mapMaterialCode2MaterialInfo,
			List<StatementFinanceSplit> listFinanceSplits) {
		
		List<ProductSplitHistoryPrice> listProductSplitPriceBase = null;
		List<ProductSplitHistoryPrice> listProductSplitPriceExpr = null;
		
		listProductSplitPriceBase = getProductSplitPriceByProductCode(productSplitBase.getProductCode(),finance.getContractDate(),mapProductCode2ListProductSplitPrice);
		if(null == listProductSplitPriceBase || listProductSplitPriceBase.size() == 0)
		{
			finance.setReasons("经融风控产品未找到拆分规则 产品编码:" + productSplitBase.getProductCode());
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return;
		}
		if(null != productSplitExpr){
			listProductSplitPriceExpr = getProductSplitPriceByProductCode(productSplitExpr.getProductCode(),finance.getContractDate(),mapProductCode2ListProductSplitPrice);
			if(null == listProductSplitPriceExpr || listProductSplitPriceBase.size() == 0)
			{
				finance.setReasons("驾宝无忧加融产品未找到拆分规则 产品编码:" + productSplitBase.getProductCode());
				finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
						.getCode());
				return;
			}
		}
		Double sassPrice = 0.0;
		Double hardTaxRate = 0.0;
		ProductSplitHistoryPrice sassProductSplitPrice = getSassProductSplitPrice(listProductSplitPriceExpr);
		if(null != sassProductSplitPrice){
			sassPrice = sassProductSplitPrice.getPrice();
		}
		Double hardPrice = getHardPrice(listProductSplitPriceBase,productSplitBase,finance,sassPrice);
		//先拆服务价格
		List<ProductSplitHistoryPrice> listSplit = new ArrayList<>();
		for(ProductSplitHistoryPrice price:listProductSplitPriceBase){
			//不需要算安装费
			if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
				if(finance.getSettleInstall().equals("N")){
					continue;
				}
			}
			ProductSplitHistoryPrice newPrice = new ProductSplitHistoryPrice();
			TaskUtils.copyObject(newPrice, price);
			if(!price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()))){
				if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_THREE.getCode()))){
					//如果是金融服务 查看是否需要分期
					if(finance.getSettleByStages().equals("Y")){
						if(StringUtils.isEmpty(productSplitBase.getServiceTime())){
							newPrice.setPrice(price.getPrice());
						}else{
							if(Integer.valueOf(productSplitBase.getServiceTime()) <= 12){
								newPrice.setPrice(price.getPrice());
							}else{
								newPrice.setPrice(price.getPrice()*(12.0/Double.valueOf(productSplitBase.getServiceTime())));
							}
						}
					}
				}
				listSplit.add(newPrice);
			}else{
				hardTaxRate = price.getTaxRate();
			}
				
		}
		//如果是投保 项目 加入sass服务拆分
		if(finance.getIsSure().equals(SureOrNotEnum.SURE_YES.getCode())){
			if(null != sassProductSplitPrice){
				listSplit.add(sassProductSplitPrice);
			}
		}
			
		//拆分硬件
		if(!StringUtils.isEmpty(finance.getDeviceNumberOne()) && StringUtils.isEmpty(finance.getDeviceNumberTwo())){
			MaterialInfo materialInfo = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeOne(), mapMaterialCode2MaterialInfo);
			ProductSplitHistoryPrice splitPrice = new ProductSplitHistoryPrice();
			splitPrice.setMaterialCode(materialInfo.getMaterialCode());
			splitPrice.setMaterialName(materialInfo.getMaterialName());
			splitPrice.setPrice(hardPrice);
			splitPrice.setTaxRate(hardTaxRate);
			splitPrice.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()));
			listSplit.add(splitPrice);
		}else if(StringUtils.isEmpty(finance.getDeviceNumberOne()) && !StringUtils.isEmpty(finance.getDeviceNumberTwo())){
			MaterialInfo materialInfo = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeTwo(), mapMaterialCode2MaterialInfo);
			ProductSplitHistoryPrice splitPrice = new ProductSplitHistoryPrice();
			splitPrice.setMaterialCode(materialInfo.getMaterialCode());
			splitPrice.setMaterialName(materialInfo.getMaterialName());
			splitPrice.setPrice(hardPrice);
			splitPrice.setTaxRate(hardTaxRate);
			splitPrice.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()));
			listSplit.add(splitPrice);
		}else{
			if(productSplitBase.getHardwareContainSource().equals("N"))
			{
				MaterialInfo materialInfoOne = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeOne(), mapMaterialCode2MaterialInfo);
				ProductSplitHistoryPrice splitPriceOne = new ProductSplitHistoryPrice();
				splitPriceOne.setMaterialCode(materialInfoOne.getMaterialCode());
				splitPriceOne.setMaterialName(materialInfoOne.getMaterialName());
				splitPriceOne.setPrice(hardPrice*0.5);
				splitPriceOne.setTaxRate(hardTaxRate);
				splitPriceOne.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()));
				listSplit.add(splitPriceOne);
				
				MaterialInfo materialInfoTwo = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeTwo(), mapMaterialCode2MaterialInfo);
				ProductSplitHistoryPrice splitPriceTwo = new ProductSplitHistoryPrice();
				splitPriceTwo.setMaterialCode(materialInfoTwo.getMaterialCode());
				splitPriceTwo.setMaterialName(materialInfoTwo.getMaterialName());
				splitPriceTwo.setPrice(hardPrice*0.5);
				splitPriceTwo.setTaxRate(hardTaxRate);
				splitPriceTwo.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()));
				listSplit.add(splitPriceTwo);
			}else{
				MaterialInfo materialInfoOne = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeOne(), mapMaterialCode2MaterialInfo);
				MaterialInfo materialInfoTwo = this.getMaterialInfoByMaterialCode(finance.getMaterialCodeTwo(), mapMaterialCode2MaterialInfo);
				ProductSplitHistoryPrice splitPriceOne = new ProductSplitHistoryPrice();
				splitPriceOne.setMaterialCode(materialInfoOne.getMaterialCode());
				splitPriceOne.setMaterialName(materialInfoOne.getMaterialName());
				splitPriceOne.setTaxRate(hardTaxRate);
				ProductSplitHistoryPrice splitPriceTwo = new ProductSplitHistoryPrice();
				splitPriceTwo.setMaterialCode(materialInfoTwo.getMaterialCode());
				splitPriceTwo.setMaterialName(materialInfoTwo.getMaterialName());
				splitPriceTwo.setTaxRate(hardTaxRate);
				if(finance.getSourceOne().equals("Y"))
				{
					splitPriceOne.setPrice(hardPrice*productSplitBase.getSourceProportion()*0.01);
					splitPriceTwo.setPrice(hardPrice*productSplitBase.getNotSourceProportion()*0.01);
				}else{
					splitPriceOne.setPrice(hardPrice*productSplitBase.getNotSourceProportion()*0.01);
					splitPriceTwo.setPrice(hardPrice*productSplitBase.getSourceProportion()*0.01);
				}
				splitPriceOne.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()));
				splitPriceTwo.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()));
				splitPriceOne.setTaxRate(hardTaxRate);
				splitPriceTwo.setTaxRate(hardTaxRate);	
				listSplit.add(splitPriceOne);
				listSplit.add(splitPriceTwo);
			}
		}
		
		for(ProductSplitHistoryPrice price:listSplit)
		{
			listFinanceSplits.add(generatorFinanceSplit(finance,price));
		}
		
		finance.setReasons("");
		finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_SUCCESS.getCode());	
		listSplit = null;
	}
	
	private StatementFinanceSplit generatorFinanceSplit(StatementFinance finance,ProductSplitHistoryPrice price){
		StatementFinanceSplit financeSplit = new StatementFinanceSplit();
		financeSplit.setPrice(price.getPrice());
		financeSplit.setTime(finance.getTime());
		financeSplit.setWorkOrder(finance.getWorkOrder());
		financeSplit.setWorkType(finance.getWorkType());
		financeSplit.setTakeGoodsDate(TaskUtils.getNowDateYMD(new Date()));
		financeSplit.setFinanceId(finance.getId());
		financeSplit.setBillNumber("XX" + TaskUtils.getOrderNumber(snowflakeWorker));
		if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()))){
			financeSplit.setCustomerCode(finance.getHardwareCustomerCode());
			financeSplit.setCustomerName(finance.getHardwareCustomerName());
		}else{
			financeSplit.setCustomerCode(finance.getServiceCustomerCode());
			financeSplit.setCustomerName(finance.getServiceCustomerName());
		}	
		financeSplit.setFinanceCustomerCode(finance.getCustomerCode());
		financeSplit.setSaleGroupCode(finance.getSaleGroupCode());
		financeSplit.setSaleGroupName(finance.getSaleGroupName());
		financeSplit.setWarehouseCode(finance.getWarehouseCode());
		financeSplit.setWarehouseName(finance.getWarehouseName());
		financeSplit.setSalesQuantity(1);
		financeSplit.setQuantity(1);
		financeSplit.setMaterialCode(price.getMaterialCode());
		financeSplit.setMaterialName(price.getMaterialName());
		financeSplit.setTaxRate(price.getTaxRate());
		financeSplit.setCreatedBy(finance.getCreatedBy());
		financeSplit.setUpdatedBy(finance.getUpdatedBy());
		financeSplit.setCreatedDate(TaskUtils.getNowDate());
		financeSplit.setUpdatedDate(TaskUtils.getNowDate());
		financeSplit.setDeletedFlag("N");
		financeSplit.setBillTypeCode(Constants.bill_type_code);
		financeSplit.setBillTypeName(Constants.bill_type_name);
		financeSplit.setSalesCode(Constants.statement_organize_code);
		financeSplit.setSalesName(Constants.statement_organize_name);
		financeSplit.setStatementCurrencyCode(Constants.statement_currency_code);
		financeSplit.setStatementCurrencyName(Constants.statement_currency_name);
		financeSplit.setSalesUnitCode(Constants.sales_unit_code);
		financeSplit.setSalesUnitName(Constants.sales_unit_name);
		financeSplit.setStatementOrganizeCode(Constants.statement_organize_code);
		financeSplit.setStatementOrganizeName(Constants.statement_organize_name);
		financeSplit.setReserveType(Constants.reserve_type);
		financeSplit.setGift("False");	
		return financeSplit;
	}
	
	
	
	
	//获取硬件价格
	private Double getHardPrice(List<ProductSplitHistoryPrice> listProductSplitPriceBase,ProductSplit productSplitBase,StatementFinance finance,Double sassPrice){
		Double hardPrice = finance.getSum() - sassPrice;
		if(null == listProductSplitPriceBase){
			return hardPrice;
		}
		for(ProductSplitHistoryPrice price:listProductSplitPriceBase){
			//是否需要算安装费
			if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
				if(finance.getSettleInstall().equals("N")){
					continue;
				}
			}			
			if(!price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()))){
				//如果是金融服务 查看是否需要分期
				if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_THREE.getCode())))
				{
					if(finance.getSettleByStages().equals("Y")){
						if(StringUtils.isEmpty(productSplitBase.getServiceTime())){
							hardPrice -= price.getPrice();
						}else{
							if(Integer.valueOf(productSplitBase.getServiceTime()) <= 12){
								hardPrice -= price.getPrice();
							}else{
								hardPrice -= (price.getPrice()*(12.0/Double.valueOf(productSplitBase.getServiceTime())));
							}
						}
					}
					else{
						hardPrice -= price.getPrice();
					}		
				}else{
					hardPrice -= price.getPrice();
				}	
			}	
		}
		return hardPrice;	
	}
	
	//获取 sass服务物料 以及价格信息 
	private ProductSplitHistoryPrice getSassProductSplitPrice(List<ProductSplitHistoryPrice> listProductSplitPriceExpr){
		ProductSplitHistoryPrice result = null;
		if(null == listProductSplitPriceExpr){
			return result;
		}
		for(ProductSplitHistoryPrice price:listProductSplitPriceExpr){
			if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FIVE.getCode()))){
				result = new ProductSplitHistoryPrice();
				BeanUtils.copyProperties(price, result);
				break;
			}
		}
		return result;
	}
	
	
	//按照产品编码获取当前产品拆分规则
	private List<ProductSplitHistoryPrice> getProductSplitPriceByProductCode(
			String productCode,Date contactDate,
			Map<String, List<ProductSplitHistoryPrice>> mapProductCode2ListProductSplitPrice) {
		
		String strKey = "";
		if(null != contactDate){
			strKey = TaskUtils.getNowDateStringYMD(contactDate);
		}else{
			strKey = "1990-01-01";
		}
		strKey += productCode; 
		
		List<ProductSplitHistoryPrice> listPrice = mapProductCode2ListProductSplitPrice.get(strKey);
		if(null != listPrice)
		{
			return listPrice;
		}
		
		if(null == contactDate){
			Example example = new Example(ProductSplitHistoryPrice.class);
			example.createCriteria().andEqualTo("productCode", productCode)
									.andEqualTo("deletedFlag","N")
									.andEqualTo("type","0");
			listPrice = productSplitHistoryPriceMapper.selectByExample(example);
			if(null != listPrice && !listPrice.isEmpty())
			{
				mapProductCode2ListProductSplitPrice.put(strKey, listPrice);
				return listPrice;
			}
		}else{			
			listPrice = new ArrayList<>();			
			Example example = new Example(ProductSplitHistoryPrice.class);
			example.createCriteria().andEqualTo("productCode", productCode)
									.andEqualTo("deletedFlag","N");
			List<ProductSplitHistoryPrice>  listPriceReturn = productSplitHistoryPriceMapper.selectByExample(example);
			Collections.sort(listPriceReturn, new Comparator<ProductSplitHistoryPrice>(){
				@Override
				public int compare(ProductSplitHistoryPrice o1,
						ProductSplitHistoryPrice o2) {
					return o2.getTime().compareTo(o1.getTime());
				}	
			});
			if(null != listPriceReturn && !listPriceReturn.isEmpty()){
				Date timeFlag = null;
				for(ProductSplitHistoryPrice price:listPriceReturn){
					if(price.getTime().after(contactDate)){
						continue;
					}
					if(null == timeFlag){
						timeFlag = price.getTime();
					}
					if(price.getTime().compareTo(timeFlag) != 0){
						break;
					}
					listPrice.add(price);
				}
				if(null != listPrice && !listPrice.isEmpty())
				{
					mapProductCode2ListProductSplitPrice.put(strKey, listPrice);
					return listPrice;
				}
			}
		}	
		return null;
	}
	
	//按照商户查找金融风控产品
	private ProductSplit getProductSplitByJRFK(Byte serviceType,StatementFinance
			 finance,DealerUserInfo dealerUserInfo,
			 Map<String, List<ProductSplit>> mapCache2ListProductSplit,
			 Map<Integer,AttribInfo> mapAttribInfo,
			 Map<String,ProductSplitSourceFlag> mapSourceFlag){
		
		ProductSplit productSplit = null;
		List<ProductSplit> listProductSplit = listProductSplitByMerchantCode(dealerUserInfo.getMerchantCode(),serviceType,mapCache2ListProductSplit);
		if(null == listProductSplit){
			listProductSplit = listProductSplitByChannel(dealerUserInfo.getChannel(),serviceType,mapCache2ListProductSplit);
			if(null == listProductSplit){
				finance.setReasons("该客户未找到可拆分的产品");
				finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
						.getCode());
				return productSplit;
			}
		}
		//商户号+渠道+设备数对应产品
		listProductSplit = listProductSplitByDevices(serviceType,finance,dealerUserInfo,listProductSplit,mapCache2ListProductSplit);
		if (null == listProductSplit){
			finance.setReasons("未匹配到相应设备数的产品");
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return productSplit;
		}
		// 商户号+渠道+设备数 + 服务期限 对应产品
		listProductSplit = listProductSplitByServices(serviceType,finance,dealerUserInfo,listProductSplit,mapCache2ListProductSplit);
		if(null == listProductSplit){
			finance.setReasons("未匹配到相应业务服务期限的产品");
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return productSplit;
		}
		//商户号+渠道+设备数+服务器+有源/无源
		listProductSplit = listProductSplitBySource(serviceType,finance,dealerUserInfo,listProductSplit,mapCache2ListProductSplit);
		if (null == listProductSplit) {
			finance.setReasons("未匹配到相应设备组合模式的产品");
			finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
					.getCode());
			return productSplit;
		}
		//是否匹配 一个
		if (listProductSplit.size() > 1) {
			
			productSplit = this.findProductSplitBySourceFlag(listProductSplit, finance, mapAttribInfo, mapSourceFlag);
			if(null == productSplit){
				finance.setReasons("匹配到多个相同设备数、相同期限的产品");
				finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
						.getCode());
			}
			return productSplit;
		}
		
		return 	listProductSplit.get(0);	
	}
	
	private ProductSplitSourceFlag getProductSplitSourceFlagByProductCode(String productCode,
			Map<String,ProductSplitSourceFlag> mapSourceFlag){
		 ProductSplitSourceFlag sourceFlag =mapSourceFlag.get(productCode);
		 if(null != sourceFlag){
			 return sourceFlag;
		 }
		 ProductSplitSourceFlag condition = new ProductSplitSourceFlag();
		 condition.setProductCode(productCode);
		 sourceFlag = sourceFlagMapper.selectOne(condition);
		 if(null != sourceFlag){
			 mapSourceFlag.put(productCode, sourceFlag);
		 }
		 return sourceFlag;
	}
	
	private AttribInfo getAttribInfoBySourceFlag(Integer surceFlag,Map<Integer,AttribInfo> mapAttribInfo){
		AttribInfo attribInfo = mapAttribInfo.get(surceFlag);
		if(null != attribInfo){
			return attribInfo;
		}
		AttribInfo condition = new AttribInfo();
		condition.setId(surceFlag);
		attribInfo = attribInfoMapper.selectOne(condition);
		if(null != attribInfo){
			mapAttribInfo.put(surceFlag, attribInfo);
		}
		return attribInfo;
	}
	
	private ProductSplit findProductSplitBySourceFlag(List<ProductSplit> listProductSplit,
			StatementFinance finance,
			Map<Integer,AttribInfo> mapAttribInfo,
			Map<String,ProductSplitSourceFlag> mapSourceFlag){	
		ProductSplit productSplit = null;
		if(null == listProductSplit){
			return productSplit;
		}
		String sourceStr = "";
		String sourceOne = (finance.getSourceOne().equals("Y"))?"有源":"无源";
		String sourceTwo = (StringUtils.isEmpty(finance.getSourceTwo())?"":((finance.getSourceTwo().equals("Y"))?"有源":"无源"));

		ProductSplitSourceFlag sourceFlag = null;
		AttribInfo attribInfo = null;
		for(ProductSplit item:listProductSplit){
			sourceFlag = getProductSplitSourceFlagByProductCode(item.getProductCode(),mapSourceFlag);
			if(null == sourceFlag){
				continue;
			}
			attribInfo = getAttribInfoBySourceFlag(sourceFlag.getSourceFlag(),mapAttribInfo);
			if(null == attribInfo){
				continue;
			}
			if(!StringUtils.isEmpty(sourceOne) && StringUtils.isEmpty(sourceTwo)){
				sourceStr = sourceOne;
				if(sourceStr.equals(attribInfo.getName())){
					productSplit = item;
					break;
				}
			}else if(StringUtils.isEmpty(sourceOne) && !StringUtils.isEmpty(sourceTwo)){
				sourceStr = sourceTwo;
				if(sourceStr.equals(attribInfo.getName())){
					productSplit = item;
					break;
				}
			}else if(!StringUtils.isEmpty(sourceOne) && !StringUtils.isEmpty(sourceTwo)){
				sourceStr = sourceOne + "+" + sourceTwo;
				if(sourceStr.equals(attribInfo.getName())){
					productSplit = item;
					break;
				}
				sourceStr = sourceTwo + "+" + sourceOne;
				if(sourceStr.equals(attribInfo.getName())){
					productSplit = item;
					break;
				}
			}
		}
		return productSplit;
	}

	 // 按照商户查找价保无忧风控产品
	 private ProductSplit getProductSplitByJBWY(Byte serviceType,StatementFinance
	 finance,DealerUserInfo dealerUserInfo,
	 Map<String, List<ProductSplit>> mapCache2ListProductSplit)
	 {
		 ProductSplit productSplit = null;
		 if(!finance.getIsSure().equals(SureOrNotEnum.SURE_YES.getCode())){
			 return productSplit;
		 }
		  
		 List<ProductSplit> listProductSplit = listProductSplitByMerchantCode(dealerUserInfo.getMerchantCode(),serviceType,mapCache2ListProductSplit);
		 if(null == listProductSplit)
		 {
			 listProductSplit = listProductSplitByChannel(dealerUserInfo.getChannel(),serviceType,mapCache2ListProductSplit);
			 if(null == listProductSplit)
			 {
				 finance.setReasons("该工单已投保，但未该客户未配置驾宝业务产品");
				 finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
							.getCode());
				 return productSplit;
			 }
		 }
		 
		 listProductSplit = this.listProductSplitByServices(serviceType, finance, dealerUserInfo, listProductSplit, mapCache2ListProductSplit);
		 if(null == listProductSplit)
		 {
			 finance.setReasons("未找到对应投保期限的产品");
			 finance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
						.getCode());
			 return productSplit;
		 }		 
		 productSplit = listProductSplit.get(0);
		 return productSplit;
	 }

	// 商户号+渠道+设备数+服务器+有源/无源
	private List<ProductSplit> listProductSplitBySource(Byte serviceType,
			StatementFinance finance, DealerUserInfo dealerUserInfo,
			List<ProductSplit> listProductSplit,
			Map<String, List<ProductSplit>> mapSource2ListProductSplit) {
		String sourceFlag = "N";
		if (!StringUtils.isEmpty(finance.getSourceOne())
				&& !StringUtils.isEmpty(finance.getSourceTwo())
				&& !finance.getSourceOne().equals(finance.getSourceTwo())) {
			sourceFlag = "Y";
		}
		String source = "m" + dealerUserInfo.getMerchantCode() + "s"
				+ serviceType + " c" + dealerUserInfo.getChannel() + " d"
				+ finance.getDeviceQuantity() + " s" + finance.getServiceTime()
				+ " s" + sourceFlag;
		List<ProductSplit> listResult = mapSource2ListProductSplit.get(source);
		if (null != listResult) {
			return listResult;
		}
		listResult = new ArrayList<ProductSplit>();
		for (ProductSplit productSplit : listProductSplit) {
			if (sourceFlag.equals("N")) {
				if (StringUtils
						.isEmpty(productSplit.getHardwareContainSource())) {
					listResult.add(productSplit);
					continue;
				}
				if (productSplit.getHardwareContainSource().equals("N")) {
					listResult.add(productSplit);
					continue;
				}
			} else {
				if (sourceFlag.equals(productSplit.getHardwareContainSource())) {
					listResult.add(productSplit);
					continue;
				}
			}
		}
		if(listResult.isEmpty()){
			return null;
		}
		mapSource2ListProductSplit.put(source, listResult);
		return listResult;
	}

	// 商户号+渠道+设备数 + 服务期限 对应产品
	private List<ProductSplit> listProductSplitByServices(Byte serviceType,
			StatementFinance finance, DealerUserInfo dealerUserInfo,
			List<ProductSplit> listProductSplit,
			Map<String, List<ProductSplit>> mapServices2ListProductSplit) {
		String services ="";
		String serviceTime = "";
		if(serviceType.equals(ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_TWO.getCode())){
			services = "m" + dealerUserInfo.getMerchantCode() + "s"
					+ serviceType + " c"
					+ String.valueOf(dealerUserInfo.getChannel()) + " d"
					+ finance.getDeviceQuantity() + " s" + finance.getServiceTime();
			serviceTime = finance.getServiceTime();
		}else{
			services = "m" + dealerUserInfo.getMerchantCode() + "s"
					+ serviceType + " c"
					+ String.valueOf(dealerUserInfo.getChannel()) + " d"
					+ finance.getDeviceQuantity() + " su" + finance.getSureTime();
			serviceTime = finance.getSureTime();
		}
		
		List<ProductSplit> listResult = mapServices2ListProductSplit
				.get(services);
		if (null != listResult) {
			return listResult;
		}
		listResult = new ArrayList<ProductSplit>();
		
		for (ProductSplit productSplit : listProductSplit) {
			
			if (StringUtils.isEmpty(serviceTime)) {
				if (StringUtils.isEmpty(productSplit.getServiceTime())) {
					listResult.add(productSplit);
				}
			} else {
				if (!StringUtils.isEmpty(productSplit.getServiceTime())) {
					if (serviceTime.equals(productSplit.getServiceTime())) {
						listResult.add(productSplit);
					}
				}
			}
		}
		if(listResult.isEmpty()){
			return null;
		}
		mapServices2ListProductSplit.put(services, listResult);
		return listResult;
	}

	// 商户号+渠道+设备数对应产品
	private List<ProductSplit> listProductSplitByDevices(Byte serviceType,
			StatementFinance finance, DealerUserInfo dealerUserInfo,
			List<ProductSplit> listProductSplit,
			Map<String, List<ProductSplit>> mapDevices2ListProductSplit) {
		String merchantDevices = "m" + dealerUserInfo.getMerchantCode() + " s"
				+ serviceType + " c"
				+ String.valueOf(dealerUserInfo.getChannel()) + "d"
				+ finance.getDeviceQuantity();
		List<ProductSplit> listResult = mapDevices2ListProductSplit
				.get(merchantDevices);
		if (null != listResult) {
			return listResult;
		}
		listResult = new ArrayList<ProductSplit>();
		for (ProductSplit productSplit : listProductSplit) {
			if (null == finance.getDeviceQuantity()) {
				if (null == productSplit.getDeviceQuantity()) {
					listResult.add(productSplit);
				}
			} else {
				if (null != productSplit.getDeviceQuantity()) {
					if (productSplit.getDeviceQuantity().equals(
							finance.getDeviceQuantity())) {
						listResult.add(productSplit);
					}
				}
			}
		}
		if(listResult.isEmpty()){
			return null;
		}
		mapDevices2ListProductSplit.put(merchantDevices, listResult);
		return listResult;
	}

	// 渠道对应产品
	private List<ProductSplit> listProductSplitByChannel(Byte channel,
			Byte serviceType,
			Map<String, List<ProductSplit>> mapChannel2ListProductSplit) {
		String strKey = "c" + channel + "s" + serviceType;
		List<ProductSplit> listProductSplit = mapChannel2ListProductSplit
				.get(strKey);
		if (null != listProductSplit) {
			return listProductSplit;
		}
		listProductSplit = listProductSplitByChannelFromDB(serviceType, channel);
		if (null != listProductSplit) {
			mapChannel2ListProductSplit.put(strKey, listProductSplit);
		}
		return listProductSplit;
	}

	private List<ProductSplit> listProductSplitByChannelFromDB(
			Byte serviceType, Byte channel) {
		Example example = new Example(ProductSplit.class);
		if(serviceType.equals(ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_ONE.getCode()))
		{
			example.createCriteria().andEqualTo("channel", channel)
			.andEqualTo("serviceType", serviceType)
			.andEqualTo("deletedFlag", "N")
			.andEqualTo("plusJrfk", "N");
		}else{
			example.createCriteria().andEqualTo("channel", channel)
			.andEqualTo("serviceType", serviceType)
			.andEqualTo("deletedFlag", "N");
		}
		List<ProductSplit> result = productSplitMapper.selectByExample(example);
		if(null == result || result.isEmpty()){
			return null;
		}
		return result;
	}

	// 按照商户查找产品列表
	private List<ProductSplit> listProductSplitByMerchantCode(
			String merchantCode, Byte serviceType,
			Map<String, List<ProductSplit>> mapMerchantCode2ListProductSplit) {
		String strKey = "m" + merchantCode + "s" + serviceType;
		List<ProductSplit> listProductSplit = mapMerchantCode2ListProductSplit
				.get(strKey);
		if (null != listProductSplit) {
			return listProductSplit;
		}
		listProductSplit = listProductSplitByMerchantCodeFromDB(merchantCode,
				serviceType);
		if(null == listProductSplit || listProductSplit.isEmpty()){
			return null;
		}
		mapMerchantCode2ListProductSplit.put(strKey, listProductSplit);		
		return listProductSplit;
	}

	private List<ProductSplit> listProductSplitByMerchantCodeFromDB(
			String merchantCode, Byte serviceType) {
		
		Example example = new Example(ProductSplit.class);
		if(serviceType.equals(ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_ONE.getCode()))
		{
			example.createCriteria().andEqualTo("merchantCode", merchantCode)
			.andEqualTo("serviceType", serviceType)
			.andEqualTo("deletedFlag", "N")
			.andEqualTo("plusJrfk","Y");
		}
		else
		{
			example.createCriteria().andEqualTo("merchantCode", merchantCode)
			.andEqualTo("serviceType", serviceType)
			.andEqualTo("deletedFlag", "N");
		}
		
		List<ProductSplit> result = productSplitMapper.selectByExample(example);
		if(null == result || result.isEmpty()){
			return null;
		}
		return result;
	}

	private DealerUserInfo getDealerUserInfoByCustomCode(String customCode,
			Map<String, DealerUserInfo> mapCustomCode2DealerUser) {
		return mapCustomCode2DealerUser.get(customCode);
	}

	private Map<String, DealerUserInfo> getCustomCode2DealerUserMap(
			List<StatementFinance> listFinances,
			Map<String, DealerUserInfo> mapCustomCode2DealerUser) {
		List<String> listCustomCodes = new ArrayList<>();
		Map<String, String> mapCustomCode2MerchantCode = new HashMap<String, String>();
		for (StatementFinance stFinance : listFinances) {
			listCustomCodes.add(stFinance.getCustomerCode());
		}
		mapCustomCode2MerchantCode = getCustomCode2MerchantCodeMap(
				listCustomCodes, mapCustomCode2MerchantCode);
		List<String> listMerchantCodes = new ArrayList(
				mapCustomCode2MerchantCode.values());
		List<DealerUserInfo> listDealerUserInfo = getDealerUserInfoList(listMerchantCodes);
		if (null == listDealerUserInfo) {
			return mapCustomCode2DealerUser;
		}
		for (String customCode : listCustomCodes) {
			for (DealerUserInfo userInfo : listDealerUserInfo) {
				if (mapCustomCode2MerchantCode.get(customCode).equals(
						userInfo.getMerchantCode())) {
					mapCustomCode2DealerUser.put(customCode, userInfo);
					break;
				}
			}
		}
		listCustomCodes = null;
		mapCustomCode2MerchantCode = null;
		return mapCustomCode2DealerUser;
	}
	
	private MaterialInfo getMaterialInfoByMaterialCode(String materialCode,Map<String,MaterialInfo> mapMaterialCode2MaterialInfo){
		
		MaterialInfo materialInfo = mapMaterialCode2MaterialInfo.get(materialCode);
		if(null != materialInfo){
			return materialInfo;
		}
		MaterialInfo condition = new MaterialInfo();
		condition.setMaterialCode(materialCode);
		materialInfo = materialInfoMapper.selectOne(condition);
		if(null != materialInfo){
			mapMaterialCode2MaterialInfo.put(materialCode, materialInfo);
		}
		return materialInfo;
	}
	
	private List<DealerUserInfo> getDealerUserInfoList(
			List<String> listMerchantCode) {
		if (null == listMerchantCode || listMerchantCode.size() == 0) {
			return null;
		}
		Example example = new Example(DealerUserInfo.class);
		example.createCriteria().andIn("merchantCode", listMerchantCode)
				.andEqualTo("deletedFlag", "N");
		List<DealerUserInfo> listDealerUserInfo = dealerUserInfoMapper
				.selectByExample(example);
		if(null == listDealerUserInfo || listDealerUserInfo.isEmpty()){
			return null;
		}
		return listDealerUserInfo;
	}

	private Map<String, String> getCustomCode2MerchantCodeMap(
			List<String> listCustomCodes,
			Map<String, String> mapCustomCode2MerchantCode) {
		if (listCustomCodes == null || listCustomCodes.size() == 0) {
			return mapCustomCode2MerchantCode;
		}
		Example example = new Example(KcCustomerRelation.class);
		example.createCriteria().andIn("customerCode", listCustomCodes)
				.andEqualTo("deletedFlag", "N");
		List<KcCustomerRelation> listCustomRelations = kcCustomerRelationMapper
				.selectByExample(example);
		if (null == listCustomRelations || listCustomRelations.size() == 0) {
			return mapCustomCode2MerchantCode;
		}
		for (KcCustomerRelation relation : listCustomRelations) {
			mapCustomCode2MerchantCode.put(relation.getCustomerCode(),
					relation.getMerchantCode());
		}
		return mapCustomCode2MerchantCode;
	}
	
	

	/**
	 * 金融风控
	 */
	/*
	 * @Override public void statementFinanceSplit() { ProductSplit
	 * productSplit; AmSyncLastidRecord syncLastidRecord =
	 * amSyncLastidRecordMapper.getSyncLastidRecord(); StatementFinance
	 * statementFinance = new StatementFinance();
	 * statementFinance.setId(syncLastidRecord.getLastStatementFinanceId());
	 * logger.info("金融风控的ID：" + syncLastidRecord.getLastStatementFinanceId());
	 * List<StatementFinance> statementFinances =
	 * statementFinanceMapper.listStatementFinance(statementFinance); for
	 * (StatementFinance statementFinanceData : statementFinances) {
	 * productSplit = new ProductSplit(); KcCustomerRelation kcCustomerRelation
	 * = new KcCustomerRelation();
	 * kcCustomerRelation.setCustomerCode(statementFinanceData
	 * .getCustomerCode()); List<KcCustomerRelation> kcCustomerRelationList =
	 * kcCustomerRelationMapper.getKcCustomerRelationList(kcCustomerRelation);
	 * if (kcCustomerRelationList != null && kcCustomerRelationList.size() > 0)
	 * { if (kcCustomerRelationList.size() > 1) {
	 * statementFinanceData.setStatus(
	 * StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("客户对应的商户大于1");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; } } else {
	 * statementFinanceData.setStatus(StatementFinanceStatusEnum
	 * .STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("客户对应的商户不存在");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; }
	 * productSplit.setMerchantCode(kcCustomerRelationList.get(0).getMerchantCode
	 * ()); productSplit.setServiceType(ProductSplitServiceTypeEnum.
	 * PRODUCT_SPLIT_SERVICE_TYPE_TWO.getCode());
	 * productSplit.setDeletedFlag("N"); //根据用户查询产品 List<ProductSplit>
	 * productSplitList = productSplitMapper.select(productSplit); if
	 * (productSplitList == null || productSplitList.size() < 1) {
	 * DealerUserInfo dealerUserInfo = new DealerUserInfo();
	 * dealerUserInfo.setMerchantCode
	 * (kcCustomerRelationList.get(0).getMerchantCode()); //查询用户渠道
	 * dealerUserInfo = dealerUserInfoMapper.selectOne(dealerUserInfo); if
	 * (dealerUserInfo == null) {
	 * statementFinanceData.setStatus(StatementFinanceStatusEnum
	 * .STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("商户不存在");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; }
	 * 
	 * productSplit = new ProductSplit();
	 * productSplit.setChannel(dealerUserInfo.getChannel());
	 * productSplit.setServiceType
	 * (ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_TWO.getCode());
	 * productSplit.setDeletedFlag("N"); //根据用户渠道查询产品 productSplitList =
	 * productSplitMapper.select(productSplit); if (productSplitList == null ||
	 * productSplitList.size() < 1) { //根据仓库查询商户再查询产品 KcWarehouseRelation
	 * kcWarehouseRelation = new KcWarehouseRelation();
	 * kcWarehouseRelation.setWarehouseCode
	 * (statementFinanceData.getWarehouseCode()); List<KcWarehouseRelation>
	 * kcWarehouseRelations =
	 * kcWarehouseRelationMapper.select(kcWarehouseRelation); if
	 * (kcWarehouseRelations != null && kcWarehouseRelations.size() > 0) { if
	 * (kcCustomerRelationList.size() > 1) {
	 * statementFinanceData.setStatus(StatementFinanceStatusEnum
	 * .STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("仓库对应的商户大于1");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; } } else {
	 * statementFinanceData.setStatus(StatementFinanceStatusEnum
	 * .STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("仓库对应的商户不存在");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; } productSplit = new ProductSplit();
	 * productSplit.setMerchantCode
	 * (kcWarehouseRelations.get(0).getMerchantCode());
	 * productSplit.setServiceType
	 * (ProductSplitServiceTypeEnum.PRODUCT_SPLIT_SERVICE_TYPE_TWO.getCode());
	 * productSplit.setDeletedFlag("N"); productSplitList =
	 * productSplitMapper.select(productSplit); if (productSplitList == null ||
	 * productSplitList.size() < 1) {
	 * statementFinanceData.setStatus(StatementFinanceStatusEnum
	 * .STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("没有当前用户的产品");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; } } }
	 * 
	 * 
	 * String reasons = ""; //根据服务期限去查找 List<ProductSplit> productSplits = new
	 * ArrayList<>(); for (ProductSplit product : productSplitList) { if
	 * (product.getServiceTime().equals(statementFinanceData.getServiceTime()))
	 * { productSplits.add(product); } reasons = product.getProductCode() + ",";
	 * } if (productSplits == null || productSplits.size() < 1) {
	 * statementFinanceData
	 * .setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
	 * .getCode()); statementFinanceData.setReasons("服务期限不匹配[" + reasons + "]");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; }
	 * 
	 * 
	 * reasons = ""; //根据价格加5减5匹配产品 Double priceSum = 0.0; String
	 * priceSumString; ProductSplitHistoryPrice productSplitHistoryPrice = new
	 * ProductSplitHistoryPrice(); List<ProductSplit> productSplitCorrect = new
	 * ArrayList<>(); for (ProductSplit productData : productSplits) { reasons =
	 * productData.getProductCode() + ",";
	 * productSplitHistoryPrice.setProductCode(productData.getProductCode());
	 * productSplitHistoryPrice.setType(Byte.valueOf("0"));
	 * List<ProductSplitHistoryPrice> productSplitHistoryPriceList =
	 * productSplitHistoryPriceMapper
	 * .selecrtProductSplitHistoryPriceByServiceTypeTwoProductTypeZero
	 * (productSplitHistoryPrice); priceSum =
	 * productSplitHistoryPriceList.get(0).getPrice();
	 * productSplitHistoryPriceList = productSplitHistoryPriceMapper.
	 * selecrtProductSplitHistoryPriceByServiceTypeTwo
	 * (productSplitHistoryPrice); if (productData.getDeviceQuantity() != null
	 * && statementFinanceData.getDeviceQuantity() != null &&
	 * productData.getDeviceQuantity
	 * ().equals(statementFinanceData.getDeviceQuantity())) { for
	 * (ProductSplitHistoryPrice price : productSplitHistoryPriceList) {
	 * priceSum = priceSum + price.getPrice(); } priceSumString = priceSum + "";
	 * if (String.valueOf(statementFinanceData.getSum()).equals(priceSumString))
	 * { productSplitCorrect.add(productData); break; } else { if (priceSum <=
	 * statementFinanceData.getSum() + 5 || priceSum >=
	 * statementFinanceData.getSum() - 5) {
	 * productSplitCorrect.add(productData); } } } } if (productSplitCorrect ==
	 * null || productSplitCorrect.size() < 1) {
	 * statementFinanceData.setStatus(StatementFinanceStatusEnum
	 * .STATEMENT_FINANCE_SPLIT_FAIL.getCode());
	 * statementFinanceData.setReasons("价格或设备数量不匹配[" + reasons + "]");
	 * statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; }
	 * 
	 * //根据是否包含有源无源匹配 reasons = ""; List<ProductSplit> productSplitCorrectData =
	 * new ArrayList<>(); if (!statementFinanceData.getSourceOne().equals("") &&
	 * !statementFinanceData.getSourceTwo().equals("") &&
	 * statementFinanceData.getSourceOne() != null &&
	 * statementFinanceData.getSourceTwo() != null &&
	 * !statementFinanceData.getSourceOne
	 * ().equals(statementFinanceData.getSourceTwo())) { for (ProductSplit
	 * productData : productSplitCorrect) { reasons =
	 * productData.getProductCode() + ","; if
	 * (productData.getHardwareContainSource().equals("Y")) {
	 * productSplitCorrectData.add(productData); } } } else { for (ProductSplit
	 * productData : productSplitCorrect) { reasons =
	 * productData.getProductCode() + ","; //如果非单源设备 直接报错 if
	 * (!productData.getHardwareContainSource().equals("N")) {
	 * productSplitCorrectData.add(productData); } } } if
	 * (productSplitCorrectData == null || productSplitCorrectData.size() < 1) {
	 * statementFinanceData
	 * .setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_FAIL
	 * .getCode()); statementFinanceData.setReasons("产品是否包含有源无源不匹配[" + reasons +
	 * "]"); statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord);
	 * continue; }
	 * 
	 * 
	 * //进行拆分 if (productSplitCorrect.size() > 0 && productSplitCorrect != null)
	 * { productSplitHistoryPrice.setProductCode(productSplitCorrect.get(0).
	 * getProductCode()); productSplitHistoryPrice.setType(Byte.valueOf("0"));
	 * Double statementCollectionDataPrice = 0.0; //查询服务和软件物料
	 * List<ProductSplitHistoryPrice> productSplitHistoryPriceList =
	 * productSplitHistoryPriceMapper
	 * .selecrtProductSplitHistoryPriceByServiceTypeTwo
	 * (productSplitHistoryPrice); for (ProductSplitHistoryPrice data :
	 * productSplitHistoryPriceList) { statementCollectionDataPrice =
	 * statementCollectionDataPrice + data.getPrice(); } //
	 * 硬件价格=对账单中的销售价格-硬件之外所有物料价格之和 statementCollectionDataPrice =
	 * statementFinanceData.getSum() - statementCollectionDataPrice; //只查询硬件
	 * List<ProductSplitHistoryPrice> serviceTypeTwoProductList =
	 * productSplitHistoryPriceMapper
	 * .selecrtProductSplitHistoryPriceByServiceTypeTwoProductTypeZero
	 * (productSplitHistoryPrice); Material material = new Material();
	 * material.setMaterialCode1(statementFinanceData.getMaterialCodeOne());
	 * List<Material> materials = iMaterialDubboService.list(material); //拆分硬件
	 * if (statementFinanceData.getDeviceNumberTwo() == null ||
	 * statementFinanceData.getDeviceNumberTwo().equals("")) {
	 * materials.get(0).setPrice(statementCollectionDataPrice);
	 * materials.get(0).
	 * setMaterialCode(statementFinanceData.getMaterialCodeOne());
	 * this.add(statementFinanceData, null, materials.get(0),
	 * serviceTypeTwoProductList.get(0).getTaxRate()); } else { //俩个设备是否都是有源或者无源
	 * if (!statementFinanceData.getSourceOne().equals(statementFinanceData.
	 * getSourceTwo())) { //主设备 if
	 * (statementFinanceData.getSourceOne().equals("Y")) {
	 * materials.get(0).setPrice(statementCollectionDataPrice *
	 * productSplitCorrect.get(0).getSourceProportion() / 100); } else {
	 * materials.get(0).setPrice(statementCollectionDataPrice *
	 * productSplitCorrect.get(0).getNotSourceProportion() / 100); }
	 * this.add(statementFinanceData, null, materials.get(0),
	 * serviceTypeTwoProductList.get(0).getTaxRate()); //从设备
	 * material.setMaterialCode1(statementFinanceData.getMaterialCodeTwo());
	 * materials = iMaterialDubboService.list(material); if
	 * (statementFinanceData.getSourceTwo().equals("Y")) {
	 * materials.get(0).setPrice(statementCollectionDataPrice *
	 * productSplitCorrect.get(0).getSourceProportion() / 100); } else {
	 * materials.get(0).setPrice(statementCollectionDataPrice *
	 * productSplitCorrect.get(0).getNotSourceProportion() / 100); }
	 * this.add(statementFinanceData, null, materials.get(0),
	 * serviceTypeTwoProductList.get(0).getTaxRate()); } else { //插入主设备
	 * materials.get(0).setPrice(statementCollectionDataPrice / 2);
	 * this.add(statementFinanceData, null, materials.get(0),
	 * serviceTypeTwoProductList.get(0).getTaxRate());
	 * 
	 * //插入从设备
	 * material.setMaterialCode1(statementFinanceData.getMaterialCodeTwo());
	 * materials = iMaterialDubboService.list(material);
	 * materials.get(0).setPrice(statementCollectionDataPrice / 2);
	 * this.add(statementFinanceData, null, materials.get(0),
	 * serviceTypeTwoProductList.get(0).getTaxRate()); } } //拆分软件服务 for
	 * (ProductSplitHistoryPrice price : productSplitHistoryPriceList) {
	 * this.add(statementFinanceData, price, null, 0.0); } }
	 * statementFinanceData
	 * .setProductCode(productSplitCorrect.get(0).getProductCode());
	 * statementFinanceData
	 * .setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_SPLIT_SUCCESS
	 * .getCode()); statementFinanceMapper.updateById(statementFinanceData);
	 * syncLastidRecord.setLastStatementFinanceId(statementFinanceData.getId());
	 * amSyncLastidRecordMapper.updateSyncLastidRecord(syncLastidRecord); } }
	 */ 
	/*private void add(StatementFinance statementFinance,
			ProductSplitHistoryPrice productSplitHistoryPrice,
			Material material, Double taxRate) {
		StatementFinanceSplit statementFinanceSplit = new StatementFinanceSplit();
		statementFinanceSplit.setWarehouseCode(statementFinance
				.getWarehouseCode());
		statementFinanceSplit
				.setMaterialCode(productSplitHistoryPrice != null ? productSplitHistoryPrice
						.getMaterialCode() : material.getMaterialCode());
		statementFinanceSplit
				.setPrice(productSplitHistoryPrice != null ? productSplitHistoryPrice
						.getPrice() : material.getPrice());
		String year = String.format("%tY", statementFinance.getTime());
		String mon = String.format("%tm", statementFinance.getTime());
		String time = year + "-" + mon + "-15";
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(time);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		statementFinanceSplit.setTime(date);
		statementFinanceSplit.setTakeGoodsDate(date);
		statementFinanceSplit.setCustomerName(statementFinance
				.getCustomerName());
		statementFinanceSplit.setDeletedFlag("N");
		statementFinanceSplit.setFinanceId(statementFinance.getId());
		statementFinanceSplit.setBillNumber("XX" + StringUtil.getOrderNo());
		statementFinanceSplit.setCustomerCode(statementFinance
				.getCustomerCode());
		statementFinanceSplit.setCustomerName(statementFinance
				.getCustomerName());
		statementFinanceSplit.setSaleGroupCode(statementFinance
				.getSaleGroupCode());
		statementFinanceSplit.setSaleGroupName(statementFinance
				.getSaleGroupName());
		statementFinanceSplit.setWarehouseCode(statementFinance
				.getWarehouseCode());
		statementFinanceSplit.setWarehouseName(statementFinance
				.getWarehouseName());
		statementFinanceSplit.setSalesQuantity(1);
		statementFinanceSplit.setQuantity(1);
		statementFinanceSplit
				.setPrice(productSplitHistoryPrice != null ? productSplitHistoryPrice
						.getPrice() : material.getPrice());
		statementFinanceSplit
				.setMaterialCode(productSplitHistoryPrice != null ? productSplitHistoryPrice
						.getMaterialCode() : material.getMaterialCode());
		statementFinanceSplit
				.setMaterialName(productSplitHistoryPrice != null ? productSplitHistoryPrice
						.getMaterialName() : material.getMaterialName());
		statementFinanceSplit
				.setTaxRate(productSplitHistoryPrice != null ? productSplitHistoryPrice
						.getTaxRate() : taxRate);
		statementFinanceSplit.setCreatedBy("admin");
		statementFinanceSplit.setCreatedDate(new Date());
		statementFinanceSplit.setUpdatedBy("admin");
		statementFinanceSplit.setUpdatedDate(new Date());
		statementFinanceSplit.setDeletedFlag("N");
		statementFinanceSplitMapper.add(statementFinanceSplit);
	}*/
	 

}
