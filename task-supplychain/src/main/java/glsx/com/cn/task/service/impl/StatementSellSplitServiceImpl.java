package glsx.com.cn.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.SettlementSellWorkType;
import cn.com.glsx.supplychain.enums.StatementCollectionStatusEnum;
import glsx.com.cn.task.common.Constants;
import glsx.com.cn.task.mapper.am.StatementSellCombileMapper;
import glsx.com.cn.task.mapper.am.StatementSellExruleMapper;
import glsx.com.cn.task.mapper.am.StatementSellGlwyMapper;
import glsx.com.cn.task.mapper.am.StatementSellJbwyMapper;
import glsx.com.cn.task.mapper.am.StatementSellRenewMapper;
import glsx.com.cn.task.mapper.am.StatementSellRzfbMapper;
import glsx.com.cn.task.mapper.am.StatementSellSplitMapper;
import glsx.com.cn.task.model.StatementSellCombile;
import glsx.com.cn.task.model.StatementSellExrule;
import glsx.com.cn.task.model.StatementSellGlwy;
import glsx.com.cn.task.model.StatementSellJbwy;
import glsx.com.cn.task.model.StatementSellRenew;
import glsx.com.cn.task.model.StatementSellRzfb;
import glsx.com.cn.task.model.StatementSellSplit;
import glsx.com.cn.task.service.StatementSellSplitService;
import glsx.com.cn.task.util.TaskUtils;

@Service
public class StatementSellSplitServiceImpl implements StatementSellSplitService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatementSellJbwyMapper sellJbwyMapper;
	@Autowired
	private StatementSellGlwyMapper sellGlwyMapper;
	@Autowired
	private StatementSellRenewMapper sellRenewMapper;
	@Autowired
	private StatementSellRzfbMapper sellRzfbMapper;
	@Autowired
	private StatementSellSplitMapper sellSplitMapper;
	@Autowired
	private StatementSellCombileMapper sellCombileMapper;
	@Autowired
	private StatementSellExruleMapper sellExruleMapper;
	@Autowired
	private SnowflakeWorker snowflakeWorker;
	
	//续费 微信
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void doStatementSellRenewSplit() throws RpcServiceException{
		
		try{
			List<StatementSellRenew> listSellRenews = getStatementSellRenewUnSplitFromDB();
			if(listSellRenews == null || listSellRenews.isEmpty()){
				return;
			}
			logger.info("StatementSellSplitServiceImpl::doStatementSellRenewSplit listSellRenews.size()=" + listSellRenews.size());
			StatementSellExrule sellExrule = null;
			List<StatementSellSplit> listSellSplit = new ArrayList<>();
			Map<String,StatementSellExrule> mapSellExrule = new HashMap<>();
			for(StatementSellRenew renew:listSellRenews){
				sellExrule = getStatementSellExruleByPackageOne(renew.getMerchantName(),mapSellExrule);
				if(null == sellExrule){
					renew.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
					renew.setReasons("根据商品套餐名称未找到对应的物料");
					continue;
				}
				renew.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
				listSellSplit.add(generatorSellSplitBySellRenew(renew,sellExrule));
			}
			List<StatementSellCombile> listSellCombile = combileSellSplit(listSellSplit);
			batchUpdateStatementSellRenewStatus(listSellRenews);
			batchInsertStatementSellSplit(listSellSplit);
			batchInsertStatementSellCombile(listSellCombile);
			logger.info("StatementSellSplitServiceImpl::doStatementSellRenewSplit handle ok!");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
	}

	//续费 支付宝
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void doStatementSellRzfbSplit() throws RpcServiceException{
		
		try{
			List<StatementSellRzfb> listSellRzfbs = getStatementSellRzfbUnSplitFromDB();
			if(listSellRzfbs == null || listSellRzfbs.isEmpty()){
				return;
			}
			logger.info("StatementSellSplitServiceImpl::doStatementSellRzfbSplit listSellRzfbs.size()=" + listSellRzfbs.size());
			StatementSellExrule sellExrule = null;
			List<StatementSellSplit> listSellSplit = new ArrayList<>();
			Map<String,StatementSellExrule> mapSellExrule = new HashMap<>();
			for(StatementSellRzfb rzfb:listSellRzfbs){
				if(!rzfb.getAccountType().equals("在线支付") && !rzfb.getAccountType().equals("退款（交易退款）")){
					rzfb.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
					rzfb.setReasons("非在线支付或非退款不参与拆分");
					continue;
				}
				sellExrule = getStatementSellExruleByPackageOne(rzfb.getProductName(),mapSellExrule);
				if(null == sellExrule){
					rzfb.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
					rzfb.setReasons("根据商品套餐名称未找到对应的物料");
					continue;
				}
				rzfb.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
				listSellSplit.add(generatorSellSplitBySellRzfb(rzfb,sellExrule));
			}
			List<StatementSellCombile> listSellCombile = combileSellSplit(listSellSplit);
			batchUpdateStatementSellRzfbStatus(listSellRzfbs);
			batchInsertStatementSellSplit(listSellSplit);
			batchInsertStatementSellCombile(listSellCombile);
			logger.info("StatementSellSplitServiceImpl::doStatementSellRzfbSplit handle ok!");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
	}

	//续费 驾保无忧
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void doStatementSellGlwySplit() throws RpcServiceException{
		
		try{
			List<StatementSellGlwy> listSellGlwys = getStatementSellGlwyUnSplitFromDB();
			if(listSellGlwys == null || listSellGlwys.isEmpty()){
				return;
			}
			logger.info("StatementSellSplitServiceImpl::doStatementSellGlwySplit listSellGlwys.size()=" + listSellGlwys.size());
			StatementSellExrule sellExrule = null;
			List<StatementSellSplit> listSellSplit = new ArrayList<>();
			Map<String,StatementSellExrule> mapSellExrule = new HashMap<>();
			for(StatementSellGlwy glwy:listSellGlwys){
				sellExrule = getStatementSellExruleByTerm("G",glwy.getFinancingMaturity(),mapSellExrule);
				if(null == sellExrule){
					glwy.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
					glwy.setReasons("根据商品套餐名称未找到对应的物料");
					continue;
				}
				glwy.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
				listSellSplit.add(generatorSellSplitBySellGlwy(glwy,sellExrule));
			}
			List<StatementSellCombile> listSellCombile = combileSellSplit(listSellSplit);
			batchUpdateStatementSellGlwyStatus(listSellGlwys);
			batchInsertStatementSellSplit(listSellSplit);
			batchInsertStatementSellCombile(listSellCombile);
			logger.info("StatementSellSplitServiceImpl::doStatementSellRzfbSplit handle ok!");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
	}

	//续费 驾保无忧
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RpcServiceException.class })
	@Override
	public void doStatementSellJbwySplit() {
		
		try{
			List<StatementSellJbwy> listSellJbwys = getStatementSellJbwyUnSplitFromDB();
			if(listSellJbwys == null || listSellJbwys.isEmpty()){
				return;
			}
			logger.info("StatementSellSplitServiceImpl::doStatementSellJbwySplit listSellJbwys.size()=" + listSellJbwys.size());
			StatementSellExrule sellExrule = null;
			List<StatementSellSplit> listSellSplit = new ArrayList<>();
			Map<String,StatementSellExrule> mapSellExrule = new HashMap<>();
			for(StatementSellJbwy jbwy:listSellJbwys){
				sellExrule = getStatementSellExruleByTerm("J",jbwy.getInsureDueTime(),mapSellExrule);
				if(null == sellExrule){
					jbwy.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode());
					jbwy.setReasons("根据商品套餐名称未找到对应的物料");
					continue;
				}
				jbwy.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
				listSellSplit.add(generatorSellSplitBySellJbwy(jbwy,sellExrule));
			}
			List<StatementSellCombile> listSellCombile = combileSellSplit(listSellSplit);
			batchUpdateStatementSellJbwyStatus(listSellJbwys);
			batchInsertStatementSellSplit(listSellSplit);
			batchInsertStatementSellCombile(listSellCombile);
			logger.info("StatementSellSplitServiceImpl::doStatementSellJbwySplit handle ok!");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}	
	}
	
	private Integer batchUpdateStatementSellJbwyStatus(List<StatementSellJbwy> listSellJbwys){
		List<Integer> splitSussIds = new ArrayList<>();
		List<Integer> splitFailIds = new ArrayList<>();
		for(StatementSellJbwy item:listSellJbwys){
			if(item.getStatus().equals(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode())){
				splitFailIds.add(item.getId());
			}else{
				splitSussIds.add(item.getId());
			}
		}
		if(!splitSussIds.isEmpty()){
			sellJbwyMapper.setSuccessStatusByIds(splitSussIds);
		}
		if(!splitFailIds.isEmpty()){
			sellJbwyMapper.setFailedStatusByIds(splitFailIds);
		}
		return 0;
	}
	
	private Integer batchUpdateStatementSellGlwyStatus(List<StatementSellGlwy> listSellGlwys){
		List<Integer> splitSussIds = new ArrayList<>();
		List<Integer> splitFailIds = new ArrayList<>();
		for(StatementSellGlwy item:listSellGlwys){
			if(item.getStatus().equals(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode())){
				splitFailIds.add(item.getId());
			}else{
				splitSussIds.add(item.getId());
			}
		}
		if(!splitSussIds.isEmpty()){
			sellGlwyMapper.setSuccessStatusByIds(splitSussIds);
		}
		if(!splitFailIds.isEmpty()){
			sellGlwyMapper.setFailedStatusByIds(splitFailIds);
		}
		return 0;
	}
	
	private Integer batchUpdateStatementSellRzfbStatus(List<StatementSellRzfb> listSellRzfbs){
		//List<Integer> splitSussIds = new ArrayList<>();
		//List<Integer> splitFailIds = new ArrayList<>();
		for(StatementSellRzfb item:listSellRzfbs){
			sellRzfbMapper.updateByPrimaryKeySelective(item);
			/*if(item.getStatus().equals(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode())){
				splitFailIds.add(item.getId());
			}else{
				splitSussIds.add(item.getId());
			}*/
		}
		/*if(!splitSussIds.isEmpty()){
			sellRzfbMapper.setSuccessStatusByIds(splitSussIds);
		}
		if(!splitFailIds.isEmpty()){
			sellRzfbMapper.setFailedStatusByIds(splitFailIds);
		}*/
		
		
		return 0;
	}
	
	private Integer batchUpdateStatementSellRenewStatus(List<StatementSellRenew> listSellRenews){
		
		List<Integer> splitSussIds = new ArrayList<>();
		List<Integer> splitFailIds = new ArrayList<>();
		for(StatementSellRenew item:listSellRenews){
			if(item.getStatus().equals(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_FAIL.getCode())){
				splitFailIds.add(item.getId());
			}else{
				splitSussIds.add(item.getId());
			}
		}
		if(!splitSussIds.isEmpty()){
			sellRenewMapper.setSuccessStatusByIds(splitSussIds);
		}
		if(!splitFailIds.isEmpty()){
			sellRenewMapper.setFailedStatusByIds(splitFailIds);
		}
		return 0;
	}
	
	private Integer batchInsertStatementSellSplit(List<StatementSellSplit> listSellSplit){
		if(null == listSellSplit || listSellSplit.isEmpty()){
			return 0;
		}
		return sellSplitMapper.insertList(listSellSplit);
	}
	
	private Integer batchInsertStatementSellCombile(List<StatementSellCombile> listSellCombile){
		if(null == listSellCombile || listSellCombile.isEmpty()){
			return 0;
		}
		return sellCombileMapper.insertList(listSellCombile);
	}
	
	private List<StatementSellCombile> combileSellSplit(List<StatementSellSplit> listSellSplit){
		Map<String,StatementSellCombile> mapCombile = new HashMap<>();
		StatementSellCombile sellCombile = null;
		for(StatementSellSplit split:listSellSplit){
			String strKey = TaskUtils.getNowDateStringYMD(split.getTime()) + split.getMaterialCode() + split.getPrice() + split.getTaxRate() + split.getCustomerCode();
			sellCombile = mapCombile.get(strKey);
			if(null == sellCombile){
				sellCombile = generatorStatementSellCombile(split);
				mapCombile.put(strKey, sellCombile);
				split.setCombileCode(sellCombile.getCombileCode());
				continue;
			}
			sellCombile.setQuantity(sellCombile.getQuantity() + 1);
			sellCombile.setSalesQuantity(sellCombile.getSalesQuantity() + 1);
			split.setCombileCode(sellCombile.getCombileCode());
		}
		return mapCombile.values().stream().collect(Collectors.toList());
	}
	
	private StatementSellCombile generatorStatementSellCombile(StatementSellSplit sellSplit){
		StatementSellCombile result = new StatementSellCombile();
		String combileCode = "sell" + TaskUtils.getOrderNumber(snowflakeWorker);
		result.setWorkOrder(sellSplit.getWorkOrder());
		result.setWorkType(sellSplit.getWorkType());
		result.setBillTypeCode(sellSplit.getBillTypeCode());
		result.setBillTypeName(sellSplit.getBillTypeName());
		result.setBillNumber(sellSplit.getBillNumber());
		result.setTime(sellSplit.getTime());
		result.setSalesCode(sellSplit.getSalesCode());
		result.setSalesName(sellSplit.getSalesName());
		result.setCustomerCode(sellSplit.getCustomerCode());
		result.setCustomerName(sellSplit.getCustomerName());
		result.setSaleGroupCode(sellSplit.getSaleGroupCode());
		result.setSaleGroupName(sellSplit.getSaleGroupName());
		result.setStatementCurrencyCode(sellSplit.getStatementCurrencyCode());
		result.setStatementCurrencyName(sellSplit.getStatementCurrencyName());
		result.setMaterialCode(sellSplit.getMaterialCode());
		result.setMaterialName(sellSplit.getMaterialName());
		result.setSalesUnitCode(sellSplit.getSalesUnitCode());
		result.setSalesUnitName(sellSplit.getSalesUnitName());
		result.setSalesQuantity(1);
		result.setQuantity(1);
		result.setPrice(sellSplit.getPrice());
		result.setGift(sellSplit.getGift());
		result.setTaxRate(sellSplit.getTaxRate());
		result.setTakeGoodsDate(sellSplit.getTakeGoodsDate());
		result.setStatementOrganizeCode(sellSplit.getStatementOrganizeCode());
		result.setStatementOrganizeName(sellSplit.getStatementOrganizeName());
		result.setReserveType(sellSplit.getReserveType());
		result.setWarehouseCode(sellSplit.getWarehouseCode());
		result.setWarehouseName(sellSplit.getWarehouseName());
		result.setCreatedBy(sellSplit.getCreatedBy());
		result.setUpdatedBy(sellSplit.getUpdatedBy());
		result.setCreatedDate(sellSplit.getCreatedDate());
		result.setUpdatedDate(sellSplit.getUpdatedDate());
		result.setDeletedFlag(sellSplit.getDeletedFlag());
		result.setCombileCode(combileCode);
		return result;
	}
	
	private StatementSellSplit generatorSellSplitBySellJbwy(StatementSellJbwy jbwy,StatementSellExrule sellExrule){
		StatementSellSplit sellSplit = new StatementSellSplit();
		sellSplit.setWorkOrder(jbwy.getWorkOrder());
		sellSplit.setWorkType(SettlementSellWorkType.WORK_TYPE_J.getCode());
		sellSplit.setBillTypeCode(Constants.bill_type_code);
		sellSplit.setBillTypeName(Constants.bill_type_name);
		sellSplit.setBillNumber("XX" + TaskUtils.getOrderNumber(snowflakeWorker));
		sellSplit.setTime(TaskUtils.getDateFromString(jbwy.getInsureReportPractice()));
		sellSplit.setSalesCode(Constants.statement_organize_code);
		sellSplit.setSalesName(Constants.statement_organize_name);
		sellSplit.setCustomerCode(jbwy.getSettleCustomerCode());
		sellSplit.setCustomerName(jbwy.getSettleCustomerName());
		sellSplit.setSaleGroupCode(jbwy.getSaleGroupCode());
		sellSplit.setSaleGroupName(jbwy.getSaleGroupName());
		sellSplit.setStatementCurrencyCode(Constants.statement_currency_code);
		sellSplit.setStatementCurrencyName(Constants.statement_currency_name);
		sellSplit.setMaterialCode(sellExrule.getMaterialCode());
		sellSplit.setMaterialName(sellExrule.getMaterialName());
		sellSplit.setSalesUnitCode(Constants.sales_unit_code);
		sellSplit.setSalesUnitName(Constants.sales_unit_name);
		sellSplit.setQuantity(1);
		sellSplit.setSalesQuantity(1);
		sellSplit.setPrice(jbwy.getMoney());
		sellSplit.setGift("False");
		sellSplit.setTaxRate(6.0);
		sellSplit.setTakeGoodsDate(TaskUtils.getNowDate());
		sellSplit.setStatementOrganizeCode(Constants.statement_organize_code);
		sellSplit.setStatementOrganizeName(Constants.statement_organize_name);
		sellSplit.setReserveType(Constants.reserve_type);
		sellSplit.setWarehouseCode("");
		sellSplit.setWarehouseName("");
		sellSplit.setCreatedBy(jbwy.getUpdatedBy());
		sellSplit.setUpdatedBy(jbwy.getUpdatedBy());
		sellSplit.setCreatedDate(TaskUtils.getNowDate());
		sellSplit.setUpdatedDate(TaskUtils.getNowDate());
		sellSplit.setDeletedFlag("N");
		return sellSplit;
	}
	
	private StatementSellSplit generatorSellSplitBySellGlwy(StatementSellGlwy glwy,StatementSellExrule sellExrule){
		StatementSellSplit sellSplit = new StatementSellSplit();
		sellSplit.setWorkOrder(glwy.getWorkOrder());
		sellSplit.setWorkType(SettlementSellWorkType.WORK_TYPE_G.getCode());
		sellSplit.setBillTypeCode(Constants.bill_type_code);
		sellSplit.setBillTypeName(Constants.bill_type_name);
		sellSplit.setBillNumber("XX" + TaskUtils.getOrderNumber(snowflakeWorker));
		sellSplit.setTime(glwy.getApplyDate());
		sellSplit.setSalesCode(Constants.statement_organize_code);
		sellSplit.setSalesName(Constants.statement_organize_name);
		sellSplit.setCustomerCode(glwy.getSettleCustomerCode());
		sellSplit.setCustomerName(glwy.getSettleCustomerName());
		sellSplit.setSaleGroupCode(glwy.getSaleGroupCode());
		sellSplit.setSaleGroupName(glwy.getSaleGroupName());
		sellSplit.setStatementCurrencyCode(Constants.statement_currency_code);
		sellSplit.setStatementCurrencyName(Constants.statement_currency_name);
		sellSplit.setMaterialCode(sellExrule.getMaterialCode());
		sellSplit.setMaterialName(sellExrule.getMaterialName());
		sellSplit.setSalesUnitCode(Constants.sales_unit_code);
		sellSplit.setSalesUnitName(Constants.sales_unit_name);
		sellSplit.setQuantity(1);
		sellSplit.setSalesQuantity(1);
		sellSplit.setPrice(glwy.getGlwySettleMoney());
		sellSplit.setGift("False");
		sellSplit.setTaxRate(6.0);
		sellSplit.setTakeGoodsDate(TaskUtils.getNowDate());
		sellSplit.setStatementOrganizeCode(Constants.statement_organize_code);
		sellSplit.setStatementOrganizeName(Constants.statement_organize_name);
		sellSplit.setReserveType(Constants.reserve_type);
		sellSplit.setWarehouseCode("");
		sellSplit.setWarehouseName("");
		sellSplit.setCreatedBy(glwy.getUpdatedBy());
		sellSplit.setUpdatedBy(glwy.getUpdatedBy());
		sellSplit.setCreatedDate(TaskUtils.getNowDate());
		sellSplit.setUpdatedDate(TaskUtils.getNowDate());
		sellSplit.setDeletedFlag("N");
		return sellSplit;
	}
	
	private StatementSellSplit generatorSellSplitBySellRzfb(StatementSellRzfb rzfb,StatementSellExrule sellExrule){
		StatementSellSplit sellSplit = new StatementSellSplit();
		sellSplit.setWorkOrder(rzfb.getWorkOrder());
		sellSplit.setWorkType(SettlementSellWorkType.WORK_TYPE_Z.getCode());
		sellSplit.setBillTypeCode(Constants.bill_type_code);
		sellSplit.setBillTypeName(Constants.bill_type_name);
		sellSplit.setBillNumber("XX" + TaskUtils.getOrderNumber(snowflakeWorker));
		sellSplit.setTime(rzfb.getRecordedData());
		sellSplit.setSalesCode(Constants.statement_organize_code);
		sellSplit.setSalesName(Constants.statement_organize_name);
		sellSplit.setCustomerCode(rzfb.getSettleCustomerCode());
		sellSplit.setCustomerName(rzfb.getSettleCustomerName());
		sellSplit.setSaleGroupCode(rzfb.getSaleGroupCode());
		sellSplit.setSaleGroupName(rzfb.getSaleGroupName());
		sellSplit.setStatementCurrencyCode(Constants.statement_currency_code);
		sellSplit.setStatementCurrencyName(Constants.statement_currency_name);
		sellSplit.setMaterialCode(sellExrule.getMaterialCode());
		sellSplit.setMaterialName(sellExrule.getMaterialName());
		sellSplit.setSalesUnitCode(Constants.sales_unit_code);
		sellSplit.setSalesUnitName(Constants.sales_unit_name);
		sellSplit.setSalesQuantity(1);
		sellSplit.setQuantity(1);
		sellSplit.setPrice(!StringUtils.isEmpty(rzfb.getAccountType())?(rzfb.getAccountType().equals("退款（交易退款）")?(0-rzfb.getExpenditure()):rzfb.getIncome()):rzfb.getIncome());
		sellSplit.setGift("False");
		sellSplit.setTaxRate(6.0);
		sellSplit.setTakeGoodsDate(TaskUtils.getNowDate());
		sellSplit.setStatementOrganizeCode(Constants.statement_organize_code);
		sellSplit.setStatementOrganizeName(Constants.statement_organize_name);
		sellSplit.setReserveType(Constants.reserve_type);
		sellSplit.setWarehouseCode("");
		sellSplit.setWarehouseName("");
		sellSplit.setCreatedBy(rzfb.getUpdatedBy());
		sellSplit.setUpdatedBy(rzfb.getUpdatedBy());
		sellSplit.setCreatedDate(TaskUtils.getNowDate());
		sellSplit.setUpdatedDate(TaskUtils.getNowDate());
		sellSplit.setDeletedFlag("N");
		return sellSplit;
	}
	
	private StatementSellSplit generatorSellSplitBySellRenew(StatementSellRenew renew,StatementSellExrule sellExrule){
		StatementSellSplit sellSplit = new StatementSellSplit();
		sellSplit.setWorkOrder(renew.getWorkOrder());
		sellSplit.setWorkType(SettlementSellWorkType.WORK_TYPE_W.getCode());
		sellSplit.setBillTypeCode(Constants.bill_type_code);
		sellSplit.setBillTypeName(Constants.bill_type_name);
		sellSplit.setBillNumber("XX" + TaskUtils.getOrderNumber(snowflakeWorker));
		sellSplit.setTime(renew.getTradeTime());
		sellSplit.setSalesCode(Constants.statement_organize_code);
		sellSplit.setSalesName(Constants.statement_organize_name);
		sellSplit.setCustomerCode(renew.getSettleCustomerCode());
		sellSplit.setCustomerName(renew.getSettleCustomerName());
		sellSplit.setSaleGroupCode(renew.getSaleGroupCode());
		sellSplit.setSaleGroupName(renew.getSaleGroupName());
		sellSplit.setStatementCurrencyCode(Constants.statement_currency_code);
		sellSplit.setStatementCurrencyName(Constants.statement_currency_name);
		sellSplit.setMaterialCode(sellExrule.getMaterialCode());
		sellSplit.setMaterialName(sellExrule.getMaterialName());
		sellSplit.setSalesUnitCode(Constants.sales_unit_code);
		sellSplit.setSalesUnitName(Constants.sales_unit_name);
		sellSplit.setSalesQuantity(1);
		sellSplit.setQuantity(1);
		sellSplit.setPrice(!StringUtils.isEmpty(renew.getReturnStatu())?(renew.getReturnStatu().equals("SUCCESS")?(0-renew.getReturnMoney()):renew.getOrderMoney()):renew.getOrderMoney());
		sellSplit.setGift("False");
		sellSplit.setTaxRate(6.0);
		sellSplit.setTakeGoodsDate(TaskUtils.getNowDate());
		sellSplit.setStatementOrganizeCode(Constants.statement_organize_code);
		sellSplit.setStatementOrganizeName(Constants.statement_organize_name);
		sellSplit.setReserveType(Constants.reserve_type);
		sellSplit.setWarehouseCode("");
		sellSplit.setWarehouseName("");
		sellSplit.setCreatedBy(renew.getUpdatedBy());
		sellSplit.setUpdatedBy(renew.getUpdatedBy());
		sellSplit.setCreatedDate(TaskUtils.getNowDate());
		sellSplit.setUpdatedDate(TaskUtils.getNowDate());
		sellSplit.setDeletedFlag("N");
		return sellSplit;
	}
	
	private List<StatementSellJbwy> getStatementSellJbwyUnSplitFromDB(){
		Example example = new Example(StatementSellJbwy.class);
		example.createCriteria().andEqualTo("status", StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
		return sellJbwyMapper.selectByExample(example);
	}
	
	private List<StatementSellGlwy> getStatementSellGlwyUnSplitFromDB(){
		Example example = new Example(StatementSellGlwy.class);
		example.createCriteria().andEqualTo("status", StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
		return sellGlwyMapper.selectByExample(example);
	}
	
	private List<StatementSellRzfb> getStatementSellRzfbUnSplitFromDB(){
		Example example = new Example(StatementSellRzfb.class);
		example.createCriteria().andEqualTo("status", StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
		return sellRzfbMapper.selectByExample(example);
	}
	
	private List<StatementSellRenew> getStatementSellRenewUnSplitFromDB(){
		Example example = new Example(StatementSellRenew.class);
		example.createCriteria().andEqualTo("status", StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
		return sellRenewMapper.selectByExample(example);
	}
	
	private StatementSellExrule getStatementSellExruleByTerm(String workType,String Term,Map<String,StatementSellExrule> mapSellExrule){
		StatementSellExrule result = mapSellExrule.get(Term);
		if(null != result){
			return result;
		}
		StatementSellExrule condition = new StatementSellExrule();
		Example example = new Example(StatementSellExrule.class);
		example.createCriteria().andEqualTo("workType", workType)
								.andEqualTo("term", Term);
		List<StatementSellExrule> listExrules = sellExruleMapper.selectByExample(example);
		if(null == listExrules || listExrules.isEmpty()){
			return result;
		}
		result = listExrules.get(0);
		mapSellExrule.put(result.getPackageOne(), result);
		return result;
	}
	
	private StatementSellExrule getStatementSellExruleByPackageOne(String packageOne,Map<String,StatementSellExrule> mapSellExrule){
		StatementSellExrule result = mapSellExrule.get(packageOne);
		if(null != result){
			return result;
		}
		StatementSellExrule condition = new StatementSellExrule();
		Example example = new Example(StatementSellExrule.class);
		example.createCriteria().andEqualTo("workType", "W")
								.andEqualTo("packageOne", packageOne);
		List<StatementSellExrule> listExrules = sellExruleMapper.selectByExample(example);
		if(null == listExrules || listExrules.isEmpty()){
			return result;
		}
		result = listExrules.get(0);
		mapSellExrule.put(result.getPackageOne(), result);
		return result;
	}

}
