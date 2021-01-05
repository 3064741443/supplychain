package cn.com.glsx.supplychain.service.am;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.model.response.MerchantFacadeResponse;
import cn.com.glsx.supplychain.common.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ProductSplitMaterialTypeEnum;
import cn.com.glsx.supplychain.enums.SettlementSellWorkType;
import cn.com.glsx.supplychain.enums.StatementCollectionStatusEnum;
import cn.com.glsx.supplychain.enums.StatementFinanceStatusEnum;
import cn.com.glsx.supplychain.enums.StatementTypeEnum;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.mapper.am.StatementSellCombileMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellReconDetailMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellReconMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellReconSplitMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellSplitMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSnMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSnTempMapper;
import cn.com.glsx.supplychain.model.am.KcCustomerRelation;
import cn.com.glsx.supplychain.model.am.KcWarehouseRelation;
import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import cn.com.glsx.supplychain.model.am.StatementSell;
import cn.com.glsx.supplychain.model.am.StatementSellCombile;
import cn.com.glsx.supplychain.model.am.StatementSellParam;
import cn.com.glsx.supplychain.model.am.StatementSellRecon;
import cn.com.glsx.supplychain.model.am.StatementSellReconDetail;
import cn.com.glsx.supplychain.model.am.StatementSellReconInstall;
import cn.com.glsx.supplychain.model.am.StatementSellReconSplit;
import cn.com.glsx.supplychain.model.am.StatementSellSplit;
import cn.com.glsx.supplychain.model.am.StatementSn;
import cn.com.glsx.supplychain.model.am.StatementSnTemp;
import cn.com.glsx.supplychain.service.AttribInfoService;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.ProductSplitDetailVo;
import cn.com.glsx.supplychain.vo.ProductSplitVo;
import cn.com.glsx.supplychain.vo.StatementSellSplitExcelVo;


/**
 * @ClassName StatementSellService
 * @Author 
 * @Param
 * @Date 2020/5/7 10:02
 * @Version 1.0
 **/
@Service
@Transactional
public class StatementSellService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StatementSellMapper statementSellMapper;
	@Autowired
	private StatementSellReconMapper statementSellReconMapper;
	@Autowired
	private StatementSellReconDetailMapper statementSellReconDetailMapper;
	@Autowired
	private StatementSellReconSplitMapper statementSellReconSplitMapper;
	@Autowired
	private StatementSellSplitMapper statementSellSplitMapper;
	@Autowired
	private StatementSellCombileMapper statementSellCombileMapper; 
	@Autowired
	private StatementSnMapper statementSnMapper;
	@Autowired
	private StatementSnTempMapper statementSnTempMapper;
	@Autowired
	private SupplyChainExternalService externalService;
	@Autowired
	private ProductSplitService productSplitService;
	@Autowired
	private ProductSplitDetailService productDetailService;
	@Autowired
	private ProductSplitHistoryPriceService productPriceService;
	@Autowired
	private AttribInfoService attribInfoService;
	@Autowired
	private KcWarehouseRelationService kcRelationService;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	
	/**
    * 获取产品详情列表用于对账
    *
    * @param 
    * @return
    */
	public List<ProductSplitDetailVo> listStatementSellProductSplitDetail(ProductSplitVo record){
		List<ProductSplitDetailVo> resultList = null;
		List<ProductSplitDetail> listProductDetail = productDetailService.listProductSplitDetailByProductCode(record.getProductCode());
		if(null == listProductDetail || listProductDetail.isEmpty()){
			return resultList;
		}
		List<ProductSplitHistoryPrice> listProductPrice = productPriceService.listProductPrice(record.getProductCode(), null);
		Double price = 0.0;
		Double hardwarePrice = 0.0;
		Double peijianPrice = 0.0;
		Double servicePrice = 0.0; //服务费扣除安装服务费
		Double installPrice = 0.0;
		if(null != listProductPrice){
			for(ProductSplitHistoryPrice itemPrice:listProductPrice){
				if(itemPrice.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()))){
					hardwarePrice = itemPrice.getPrice();
				}else if(itemPrice.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode()))){
					peijianPrice = itemPrice.getPrice();
				}else if(itemPrice.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
					installPrice = itemPrice.getPrice();
				}else{
					servicePrice += itemPrice.getPrice();
				}
				price += itemPrice.getPrice();
			}
		}
		resultList = new ArrayList<>();
		for(ProductSplitDetail detail:listProductDetail){
			ProductSplitDetailVo vo = new ProductSplitDetailVo();
			vo.setProductCode(detail.getProductCode());
			vo.setMaterialCode(detail.getMaterialCode());
			vo.setMaterialName(detail.getMaterialName());
			vo.setPrice(price);
			if(detail.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()))){
				vo.setHardwarePrice(hardwarePrice);
				vo.setServicePrice(servicePrice);
			}else if(detail.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode()))){
				vo.setHardwarePrice(hardwarePrice);
				vo.setServicePrice(0.0);
			}else{
				for(ProductSplitHistoryPrice itemPrice:listProductPrice){
					if(itemPrice.getMaterialCode().equals(detail.getMaterialCode())){
						vo.setHardwarePrice(0.0);
						vo.setServicePrice(itemPrice.getPrice());
						break;
					}
				}
			}
			resultList.add(vo);
		}
		return resultList;
	}
	
	/**
    * 获取产品列表用于对账
    *
    * @param 
    * @return
    */
	public List<ProductSplitVo> listStatementSellProductSplit(StatementSellRecon record){
		List<ProductSplitVo> resultList = null;
		List<ProductSplit> listProductSplit = productSplitService.listProductSplitByMerchantCodeAndChannel(record.getMerchantCode(), record.getChannel());
		if(null == listProductSplit || listProductSplit.isEmpty()){
			return resultList;
		}
		resultList = new ArrayList<>();
		for(ProductSplit split:listProductSplit){
			
			ProductSplitVo vo = new ProductSplitVo();
			vo.setProductCode(split.getProductCode());
			vo.setProductName(split.getProductName());
			vo.setPackageOne(split.getPackageOne());
			vo.setServiceTime(split.getServiceTime());
			vo.setUnitType("PSC");
			resultList.add(vo);
		}
		return resultList;
	}
	
	
	/**
    * 拆分对账单列表
    *
    * @param 
    * @return
    */
	public Page<StatementSellSplit> pageStatementSellSplit(int pageNum, int pageSize,StatementSellSplit record){
		
		PageHelper.startPage(pageNum, pageSize);
		return statementSellSplitMapper.pageStatementSellSplit(record);
	}
	
	/**
    * 拆分对账单导出
    *
    * @param 
    * @return
    */
	public List<StatementSellSplitExcelVo> listStatementSellSplit(StatementSellSplit record){
		List<StatementSellSplitExcelVo> listVo = new ArrayList<StatementSellSplitExcelVo>();
		List<StatementSellSplit> listBean = statementSellSplitMapper.exportStatementSellSplit(record);
		if(listBean == null || listBean.isEmpty()){
			return listVo;
		}
		for(StatementSellSplit bean:listBean){
			StatementSellSplitExcelVo vo = new StatementSellSplitExcelVo();
			vo.setBillTypeName(bean.getBillTypeName());
			vo.setBillNumber(bean.getBillNumber());
			vo.setNumber(SupplychainUtils.getStatementNumber(Constants.NUMBER_HEAD_SELL_SPLIT, bean.getId().intValue()));
			vo.setTime(SupplychainUtils.getNowDateStringYMD(bean.getTime()));
			vo.setSalesCode(bean.getSalesCode());
			vo.setSalesName(bean.getSalesName());
			vo.setCustomerCode(bean.getCustomerCode());
			vo.setCustomerName(bean.getCustomerName());
			vo.setSaleGroupCode(bean.getSaleGroupCode());
			vo.setSaleGroupName(bean.getSaleGroupName());
			vo.setStatementCurrencyCode(bean.getStatementCurrencyCode());
			vo.setStatementCurrencyName(bean.getStatementCurrencyName());
			vo.setMaterialCode(bean.getMaterialCode());
			vo.setMaterialName(bean.getMaterialName());
			vo.setSalesUnitCode(bean.getSalesUnitCode());
			vo.setSalesUnitName(bean.getSalesUnitName());
			vo.setSalesQuantity(bean.getSalesQuantity());
			vo.setPrice(bean.getPrice());
			vo.setGift(bean.getGift());
			vo.setTaxRate(bean.getTaxRate());
			vo.setTakeGoodsDate(SupplychainUtils.getNowDateStringYMD(bean.getTakeGoodsDate()));
			vo.setStatementOrganizeCode(bean.getStatementOrganizeCode());
			vo.setStatementOrganizeName(bean.getStatementOrganizeName());
			vo.setReserveType(bean.getReserveType());
            vo.setWarehouseCode(bean.getWarehouseCode());
            vo.setWarehouseName(bean.getWarehouseName());
            vo.setQuantity(bean.getQuantity());
            vo.setSaleOrderFinance(vo.getNumber());
            vo.setSaleOrderEntry(vo.getNumber());
            listVo.add(vo);
		}
		return listVo;
	}
		
	/**
    * 拆分对账单汇总列表
    *
    * @param 
    * @return
    */
	public Page<StatementSellCombile> pageStatementSellCombile(int pageNum, int pageSize,StatementSellCombile record){
		
		PageHelper.startPage(pageNum,pageSize);
		return statementSellCombileMapper.pageStatementSellCombile(record);
	}
	
	/**
    * 拆分对账单导出
    *
    * @param 
    * @return
    */
	public List<StatementSellSplitExcelVo> listStatementSellCombile(StatementSellCombile record){
		
		List<StatementSellSplitExcelVo> listVo = new ArrayList<StatementSellSplitExcelVo>();
		List<StatementSellCombile> listBean = statementSellCombileMapper.listStatementSellCombile(record);
		if(listBean == null || listBean.isEmpty()){
			return listVo;
		}
		for(StatementSellCombile bean:listBean){
			StatementSellSplitExcelVo vo = new StatementSellSplitExcelVo();
			vo.setBillTypeCode(StringUtils.isEmpty(bean.getBillTypeCode())?"":bean.getBillTypeCode());
			vo.setBillTypeName(StringUtils.isEmpty(bean.getBillTypeName())?"":bean.getBillTypeName());
			vo.setBillNumber(StringUtils.isEmpty(bean.getBillTypeName())?"":bean.getBillNumber());
			vo.setNumber(SupplychainUtils.getStatementNumber(Constants.NUMBER_HEAD_SELL_SPLIT, bean.getId().intValue()));
			vo.setTime(SupplychainUtils.getNowDateStringYMD(bean.getTime()));
			vo.setSalesCode(StringUtils.isEmpty(bean.getSalesCode())?"":bean.getSalesCode());
			vo.setSalesName(StringUtils.isEmpty(bean.getSalesName())?"":bean.getSalesName());
			vo.setCustomerCode(StringUtils.isEmpty(bean.getCustomerCode())?"":bean.getCustomerCode());
			vo.setCustomerName(StringUtils.isEmpty(bean.getCustomerName())?"":bean.getCustomerName());
			vo.setSaleGroupCode(StringUtils.isEmpty(bean.getSaleGroupCode())?"":bean.getSaleGroupCode());
			vo.setSaleGroupName(StringUtils.isEmpty(bean.getSaleGroupName())?"":bean.getSaleGroupName());
			vo.setStatementCurrencyCode(StringUtils.isEmpty(bean.getStatementCurrencyCode())?"":bean.getStatementCurrencyCode());
			vo.setStatementCurrencyName(StringUtils.isEmpty(bean.getStatementCurrencyName())?"":bean.getStatementCurrencyName());
			vo.setMaterialCode(StringUtils.isEmpty(bean.getMaterialCode())?"":bean.getMaterialCode());
			vo.setMaterialName(StringUtils.isEmpty(bean.getMaterialName())?"":bean.getMaterialName());
			vo.setSalesUnitCode(StringUtils.isEmpty(bean.getSalesUnitCode())?"":bean.getSalesUnitCode());
			vo.setSalesUnitName(StringUtils.isEmpty(bean.getSalesUnitName())?"":bean.getSalesUnitName());
			vo.setSalesQuantity(bean.getSalesQuantity());
			vo.setPrice(bean.getPrice());
			vo.setGift(bean.getGift());
			vo.setTaxRate(bean.getTaxRate());
			vo.setTakeGoodsDate(SupplychainUtils.getNowDateStringYMD(bean.getTakeGoodsDate()));
			vo.setStatementOrganizeCode(StringUtils.isEmpty(bean.getStatementOrganizeCode())?"":bean.getStatementOrganizeCode());
			vo.setStatementOrganizeName(StringUtils.isEmpty(bean.getStatementOrganizeName())?"":bean.getStatementOrganizeName());
			vo.setReserveType(StringUtils.isEmpty(bean.getReserveType())?"":bean.getReserveType());
            vo.setWarehouseCode(StringUtils.isEmpty(bean.getWarehouseCode())?"":bean.getWarehouseCode());
            vo.setWarehouseName(StringUtils.isEmpty(bean.getWarehouseName())?"":bean.getWarehouseName());
            vo.setQuantity(bean.getQuantity());
            vo.setCellOne("");
            vo.setCellTwo("");
            vo.setCellThree("");
            vo.setSaleOrderFinance(vo.getNumber());
            vo.setSaleOrderEntry(vo.getNumber());
            listVo.add(vo);
		}
		return listVo;
	}
	
	/**
    * 拆分对账单
    *
    * @param 
    * @return
    */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer splitStatementSellRecon(String user,String reconCode) throws RpcServiceException{
		StatementSellRecon recon = this.getStatementSellReconFromDB(reconCode);
		if(null == recon){
			return 0;
		}
		List<StatementSellReconSplit> listReconSplit = this.getStatementSellReconSplitFromDB(reconCode);
		if(null == listReconSplit || listReconSplit.isEmpty()){
			return 0;
		}
		List<StatementSellSplit> listSplit = new ArrayList<>();
		Map<String,StatementSellCombile> mapCombile = new HashMap<>();
		for(StatementSellReconSplit reconSplit:listReconSplit){
			if(reconSplit.getDeletedFlag().equals("Y")){
				continue;
			}
			for(int i=0;i<reconSplit.getSendCount();i++){
				listSplit.add(this.generatorStatementSellSplit(user, recon, reconSplit));
			}
		}
		StatementSellCombile sellCombile = null;
		for(StatementSellSplit split:listSplit){
			sellCombile = mapCombile.get(split.getMaterialCode());
			if(null == sellCombile){
				sellCombile = this.generatorStatementSellCombile(user,split);
				mapCombile.put(split.getMaterialCode(), sellCombile);
				split.setCombileCode(sellCombile.getCombileCode());
				continue;
			}
			sellCombile.setQuantity(sellCombile.getQuantity()+1);
			sellCombile.setSalesQuantity(sellCombile.getSalesQuantity() + 1);
			split.setCombileCode(sellCombile.getCombileCode());
		}
		List<StatementSellCombile> listCombile = mapCombile.values().stream().collect(Collectors.toList());
		
		List<StatementSellReconDetail> listSellReconDetail = getStatementSellReconDetailFromDB(reconCode);
		filtStatementSellSnFromDB(listSellReconDetail);
	//	List<String> listWorkOrders = new ArrayList<>();
		try{
			this.saveStatementSellSplitDB(listSplit);
			this.saveStatementSellCombileDB(listCombile);
			if(null != listSellReconDetail){
				for(StatementSellReconDetail reconDetail:listSellReconDetail){			
				//	setWorkOrderToStatementSellDB(reconDetail.getWorkOrder(),reconDetail.getListSn());
					this.saveStatementSnDB(reconDetail.getListStatementSn());
				//	listWorkOrders.add(reconDetail.getWorkOrder());
				}
			}
	//		this.delStatementSnTempByListSnsDB(listWorkOrders);
			recon.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_SPLIT_SUCCESS.getCode());
			statementSellReconMapper.updateByPrimaryKeySelective(recon);
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		return 0;
	}
	
	/**
    * 删除对账单
    *
    * @param 
    * @return
    */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer delStatementSellRecon(String reconCode) throws RpcServiceException{
		try{
			List<String> listWorkOrder = this.getStatementWordOrdersByReconCode(reconCode);
			List<String> listSns = this.getStatementSnTempsByListWorkOrders(listWorkOrder);
			List<String> listCombileCodes = this.getStatementCombileCodesByWorderOrders(listWorkOrder);
			this.delStatementSellCombileDB(listCombileCodes);
			this.delStatementSellSplitDB(listWorkOrder);
			this.setNullWorkOrderToStatementSellDB(listSns);
			this.delStatementSnTempDB(listWorkOrder);
			this.delStatementSnDB(listWorkOrder);
			this.delStatementSellReconSplitDB(reconCode);
			this.delStatementSellReconDetailDB(reconCode);
			this.delStatementSellReconDB(reconCode);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		return 0;
	}
	
	/**
    * 修改保存对账单
    *
    * @param 
    * @return
    */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public StatementSellRecon saveStatementSellRecon(StatementSellRecon record) throws RpcServiceException{
		List<StatementSellReconDetail> listSellReconDetail = record.getListReconDetail();
		List<StatementSellReconSplit> listSellReconSplit = record.getListReconSplit();
		List<StatementSellReconInstall> listReconInstall = record.getListReconInstall();
		filtStatementSellReconDetailListSnFromDB(listSellReconDetail);
		//计算需要删除的sn
		List<String> listDeletedSn = calculateDeletedSn(record.getUpdatedBy(),listSellReconDetail);
		
		try{
			calculateReconSplit(record,listSellReconDetail,listReconInstall,listSellReconSplit);
			setNullWorkOrderToStatementSellDB(listDeletedSn);
			/*for(StatementSellReconDetail reconDetail:listSellReconDetail){								
				if(null == reconDetail.getListSn() || reconDetail.getListSn().isEmpty()){
					continue;
				}
				List<StatementSell> listStatementSell = listStatementSellByListSns(reconDetail.getListSn());
				List<StatementSn> listStatementSn = new ArrayList<>();
				StatementSn statementSn = null;
				Integer bFlag = null;
				for(StatementSell sell:listStatementSell){
					statementSn = this.generatorStatementSn(record.getUpdatedBy(), reconDetail.getWorkOrder(), sell);
					bFlag = mapDeletedSn.get(statementSn.getSn());
					if(null != bFlag){
						statementSn.setDeletedFlag("Y");
					}
					listStatementSn.add(statementSn);
				}
				
				this.saveStatementSnDB(listStatementSn);
			}*/			
			this.delStatementSnTempByListSnsDB(listDeletedSn);
			this.saveStatementSellReconDetailDB(listSellReconDetail);
			this.saveStatementSellReconSplitDB(listSellReconSplit);
			this.saveStatementSellReconDB(record);
			return this.getStatementSellRecon(record);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		
	}
	
	/**
    * 对账单详情
    *
    * @param 
    * @return
    */
	public StatementSellRecon getStatementSellRecon(StatementSellRecon record){		
		StatementSellRecon sellRecon = getStatementSellReconFromDB(record.getReconCode());
		if(null == sellRecon){
			return sellRecon;
		}
		List<StatementSellReconDetail> listSellReconDetail = getStatementSellReconDetailFromDB(record.getReconCode());
		List<StatementSellReconSplit> listSellReconSplit = getStatementSellReconSplitFromDB(record.getReconCode());
		List<StatementSellReconInstall> listReconInstall = new ArrayList<>();
		filtStatementSellReconInstall(listSellReconSplit, listReconInstall);
		sellRecon.setTotalPrice(0.0);
		for(StatementSellReconSplit reconSplit:listSellReconSplit){
			sellRecon.setTotalPrice(sellRecon.getTotalPrice()+reconSplit.getServiceTotalPrice());
		}
		sellRecon.setListReconDetail(listSellReconDetail);
		sellRecon.setListReconSplit(listSellReconSplit);
		sellRecon.setListReconInstall(listReconInstall);
		return sellRecon;
	}
	
	/**
    * 对账单列表
    *
    * @param 
    * @return
    */
	public Page<StatementSellRecon> pageStatementSellRecon(int pageNum, int pageSize,StatementSellRecon record){
		Page<StatementSellRecon> pageResult = null;
		PageHelper.startPage(pageNum, pageSize);
		pageResult = statementSellReconMapper.pageStatementSellRecon(record);
		if(null == pageResult || pageResult.isEmpty()){
			return pageResult;
		}
		Integer configureId = 0;
		List<String> listReconCode = new ArrayList<>();
		for(StatementSellRecon sellRecon:pageResult.getResult()){
			listReconCode.add(sellRecon.getReconCode());
		}
		List<StatementSellReconSplit> listReconSplit = statementSellReconSplitMapper.getDistinctReconTotalPrice(listReconCode);
		Map<String,Double> mapReconPrice = listReconSplit.stream().collect(Collectors.toMap(StatementSellReconSplit::getReconCode,StatementSellReconSplit::getServiceTotalPrice));
		for(StatementSellRecon sellRecon:pageResult.getResult()){
			configureId = Integer.valueOf(sellRecon.getChannel()) + Constants.MERCHANT_CHANNEL_CONVERT_BASE;
			sellRecon.setChannelName(attribInfoService.getAttribInfoNameById(configureId));
			sellRecon.setTotalPrice(mapReconPrice.get(sellRecon.getReconCode()));
		}
		return pageResult;
	}
	/**
    * 生成对账单草稿
    *
    * @param 
    * @return
    */
	public StatementSellRecon generaterStatementSellRecon(
			StatementSellRecon record) throws RpcServiceException{
		StatementSellRecon result = null;

		boolean bIsFongKongChannel = this.beFongKongChannel(record.getChannel());
		
		List<StatementSell> listSell = listStatementSell(
				record.getReconTimeStart(), record.getReconTimeEnd(),
				record.getMerchantCode());
		if (null == listSell || listSell.isEmpty()) {
			logger.info("StatementSellService::generaterStatementSellRecon listSell is empty");
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_NULL_STATEMENT_SELL_DATA);
		}
		
		//获取商户地址
		MerchantFacadeResponse merchantFacade = externalService
				.getMerchantFacadeRemote(record.getMerchantCode());
		if (null == merchantFacade) {
			logger.info("StatementSellService::generaterStatementSellRecon merchantFacade is null");	
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE_IN_MERCHANTPLAT);
		}
		
		//验证生成对账时间是否合理
		if(!this.beRightReconTime(record.getReconTimeStart(),record.getMerchantCode()) ||
				!this.beRightReconTime(record.getReconTimeEnd(),record.getMerchantCode())){
			logger.info("StatementSellService::generaterStatementSellRecon recon time is not probable!");	
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_RECON_TIME_ISNOT_PROBABLE);
		}
		
		//获取商户对应的仓库信息
		KcWarehouseRelation kcWarehouseRelation = new KcWarehouseRelation();
		kcWarehouseRelation.setWarehouseCode("");
		kcWarehouseRelation.setWarehouseName("");

		//获取商户对应的客户信息
		KcCustomerRelation kcCustomerRelation = new KcCustomerRelation();
		kcCustomerRelation.setCustomerCode(record.getCustomerCode());
		kcCustomerRelation.setCustomerName(record.getCustomerName());
		
		String reconCode = "REC"
				+ SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
		String productCode = null;
		String sellKey = null;
		String productType = null;
		String merchantOrderCode = "";
		String sendGoodsTime = "";
		List<StatementSell> listItemSell = null;
		ProductSplit productSplit = null;
		List<ProductSplitHistoryPrice> listPrice = null;
		Map<String, ProductSplit> mapProduct = new HashMap<>();
		Map<String, List<ProductSplitHistoryPrice>> mapProductPrice = new HashMap<>();
		Map<String, List<StatementSell>> mapSell = new HashMap<>();
		List<StatementSellReconDetail> listReconDetail = new ArrayList<>();
		List<StatementSellReconSplit> listReconSplit = new ArrayList<>();
		List<StatementSellReconInstall> listReconInstall = new ArrayList<>();
		setMapStatementSell(bIsFongKongChannel,listSell, mapSell);
		for (Map.Entry<String, List<StatementSell>> entry : mapSell.entrySet()) {
			sellKey = entry.getKey();
			String[] arrayKey = sellKey.split(":");
			productCode = arrayKey[0];
			productType = arrayKey[1];
			if(!bIsFongKongChannel){
				merchantOrderCode = arrayKey[2];
				sendGoodsTime = arrayKey[3];
			}else{
				sendGoodsTime = arrayKey[2];
			}	
			listItemSell = entry.getValue();
			productSplit = getProductSplit(productCode, mapProduct);
			
			listPrice = getProductPrice(productCode, null, mapProductPrice);
			filtStatementSellReconDetail(bIsFongKongChannel,record.getCreatedBy(), reconCode,merchantOrderCode,sendGoodsTime,
					productType, productSplit, listPrice, listItemSell,
					listReconDetail, listReconSplit);
		}
		filtStatementSellReconInstall(listReconSplit,listReconInstall);
		result = generatorStatementSellRecon(record, reconCode,kcWarehouseRelation,kcCustomerRelation, 
				 merchantFacade, listReconDetail, listReconSplit,listReconInstall);
		
		for(StatementSellReconDetail reconDetail:result.getListReconDetail()){	
			this.saveStatementSnTempDB(reconDetail.getListStatementSnTemp());
			this.setWorkOrderToStatementSellDB(reconDetail.getWorkOrder(), reconDetail.getListSn());
			reconDetail.setListSn(null);
			reconDetail.setListStatementSn(null);
			reconDetail.setListStatementSnTemp(null);
		}
		this.saveStatementSellReconDetailDB(result.getListReconDetail());
		this.saveStatementSellReconSplitDB(result.getListReconSplit());
		this.saveStatementSellReconDB(result);
		
		return result;
	}
	
	private StatementSellCombile generatorStatementSellCombile(String user,StatementSellSplit sellSplit){
		StatementSellCombile result = new StatementSellCombile();
		String combileCode = "sell" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
		result.setWorkOrder(sellSplit.getWorkOrder());
		result.setWorkType(SettlementSellWorkType.WORK_TYPE_S.getCode());
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
		result.setQuantity(1);
		result.setSalesQuantity(1);
		result.setPrice(sellSplit.getPrice());
		result.setGift(sellSplit.getGift());
		result.setTaxRate(sellSplit.getTaxRate());
		result.setTakeGoodsDate(sellSplit.getTakeGoodsDate());
		result.setStatementOrganizeCode(sellSplit.getStatementOrganizeCode());
		result.setStatementOrganizeName(sellSplit.getStatementOrganizeName());
		result.setReserveType(sellSplit.getReserveType());
		result.setWarehouseCode(sellSplit.getWarehouseCode());
		result.setWarehouseName(sellSplit.getWarehouseName());
		result.setCreatedBy(user);
		result.setUpdatedBy(user);
		result.setCreatedDate(sellSplit.getCreatedDate());
		result.setUpdatedDate(sellSplit.getUpdatedDate());
		result.setDeletedFlag(sellSplit.getDeletedFlag());
		result.setCombileCode(combileCode);
		return result;
	}
	
	private StatementSellSplit generatorStatementSellSplit(String user,StatementSellRecon recon,StatementSellReconSplit reconSplit){
		
		StatementSellSplit result = new StatementSellSplit();
		result.setWorkOrder(reconSplit.getWorkOrder());
		result.setWorkType(SettlementSellWorkType.WORK_TYPE_S.getCode());
		result.setBillTypeCode(Constants.bill_type_code);
		result.setBillTypeName(Constants.bill_type_name);
		result.setBillNumber("XX" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
		result.setTime(SupplychainUtils.getNowDate());
		result.setSalesCode(Constants.statement_organize_code);
		result.setSalesName(Constants.statement_organize_name);
		result.setCustomerCode(recon.getCustomerCode());
		result.setCustomerName(recon.getCustomerName());
		result.setSaleGroupCode(recon.getSaleGroupCode());
		result.setSaleGroupName(recon.getSaleGroupName());
		result.setStatementCurrencyCode(Constants.statement_currency_code);
		result.setStatementCurrencyName(Constants.statement_currency_name);
		result.setMaterialCode(reconSplit.getMaterialCode());
		result.setMaterialName(reconSplit.getMaterialName());
		result.setSalesUnitCode(Constants.sales_unit_code);
		result.setSalesUnitName(Constants.sales_unit_name);
		result.setQuantity(1);
		result.setSalesQuantity(1);
		result.setPrice(reconSplit.getServiceUintPrice());
		result.setGift("False");
		result.setTaxRate(reconSplit.getTaxRate());
		result.setTakeGoodsDate(SupplychainUtils.getNowDate());
		result.setStatementOrganizeCode(Constants.statement_organize_code);
		result.setStatementOrganizeName(Constants.statement_organize_name);
		result.setReserveType(Constants.reserve_type);
		result.setWarehouseCode(recon.getWarehouseCode());
		result.setWarehouseName(recon.getWarehouseName());
		result.setCreatedBy(user);
		result.setUpdatedBy(user);
		result.setCreatedDate(SupplychainUtils.getNowDate());
		result.setUpdatedDate(SupplychainUtils.getNowDate());
		result.setDeletedFlag("N");
		return result;
	}
	
	private Integer delStatementSnTempByListSnsDB(List<String> listSns){
		if(null == listSns || listSns.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSnTemp.class);
		example.createCriteria().andIn("sn", listSns);
		return statementSnTempMapper.deleteByExample(example);
	}
	
	private Integer delStatementSnTempDB(List<String> listWorkOrder){
		if(null == listWorkOrder || listWorkOrder.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSnTemp.class);
		example.createCriteria().andIn("workOrder", listWorkOrder);
		return statementSnTempMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellReconDetailDB(String reconCode){
		Example example = new Example(StatementSellReconDetail.class);
		example.createCriteria().andEqualTo("reconCode", reconCode);
		return statementSellReconDetailMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellReconSplitDB(String reconCode){
		Example example = new Example(StatementSellReconSplit.class);
		example.createCriteria().andEqualTo("reconCode", reconCode);
		return statementSellReconSplitMapper.deleteByExample(example);
	}
	
	private Integer delStatementSnDB(List<String> listWordOrder){
		if(null == listWordOrder || listWordOrder.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSn.class);
		example.createCriteria().andIn("workOrder", listWordOrder)
								.andEqualTo("statementType", StatementTypeEnum.STATEMENT_TYPE_SELL.getCode());
		return statementSnMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellReconDB(String reconCode){
		Example example = new Example(StatementSellRecon.class);
		example.createCriteria().andEqualTo("reconCode", reconCode);
		return statementSellReconMapper.deleteByExample(example);
	}
	
	
	private Integer delStatementSellSplitDB(List<String> listWorkOrder){
		if(null == listWorkOrder || listWorkOrder.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellSplit.class);
		example.createCriteria().andIn("workOrder", listWorkOrder);
		return statementSellSplitMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellCombileDB(List<String> listCombileCode){
		if(null == listCombileCode || listCombileCode.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellCombile.class);
		example.createCriteria().andIn("combileCode", listCombileCode);
		return statementSellCombileMapper.deleteByExample(example);
	}
	
	private List<String> getStatementWordOrdersByReconCode(String reconCode){
		Example example = new Example(StatementSellReconDetail.class);
		example.createCriteria().andEqualTo("reconCode", reconCode);
		List<StatementSellReconDetail> listDetail = statementSellReconDetailMapper.selectByExample(example);
		if(null == listDetail || listDetail.isEmpty()){
			return null;
		}
		return listDetail.stream().map(StatementSellReconDetail::getWorkOrder).collect(Collectors.toList());
	}
	
	private List<String> getStatementSnTempsByListWorkOrders(List<String> listWorkOrder){
		if(null == listWorkOrder || listWorkOrder.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSnTemp.class);
		example.createCriteria().andIn("workOrder", listWorkOrder);
		List<StatementSnTemp> listStatementSn = statementSnTempMapper.selectByExample(example);
		return listStatementSn.stream().map(StatementSnTemp::getSn).collect(Collectors.toList());
	}
	
	private List<String> getStatementSnsByListWorkOrders(List<String> listWorkOrder){
		if(null == listWorkOrder || listWorkOrder.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSn.class);
		example.createCriteria().andIn("workOrder", listWorkOrder);
		List<StatementSn> listStatementSn = statementSnMapper.selectByExample(example);
		return listStatementSn.stream().map(StatementSn::getSn).collect(Collectors.toList());
	}
	
	private List<String> getStatementCombileCodesByWorderOrders(List<String> listWorkOrder){
		if(null == listWorkOrder || listWorkOrder.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSellSplit.class);
		example.createCriteria().andIn("workOrder", listWorkOrder);
		List<StatementSellSplit> listSplit = statementSellSplitMapper.selectByExample(example);
		if(null == listSplit || listSplit.isEmpty()){
			return null;
		}
		return listSplit.stream().map(StatementSellSplit::getCombileCode).collect(Collectors.toList()); 
	}
	
	
	private Integer setNullWorkOrderToStatementSellDB(List<String> listSn){
		if(null == listSn || listSn.isEmpty()){
			return 0;
		}
		return statementSellMapper.setNullWorkOrder(listSn);
	}
	
	private Integer setWorkOrderToStatementSellDB(String workOrder,List<String> listSn){
		if(null == listSn || listSn.isEmpty()){
			return 0;
		}
		StatementSellParam condition = new StatementSellParam();
		condition.setWorkOrder(workOrder);
		condition.setListSn(listSn);
		return statementSellMapper.setParamWorkOrder(condition);
	}
	
	private Integer saveStatementSnDB(List<StatementSn> listStatementSn){
		if(null == listStatementSn || listStatementSn.isEmpty()){
			return 0;
		}
		return statementSnMapper.batchInsertReplace(listStatementSn);
	}
	
	private Integer saveStatementSnTempDB(List<StatementSnTemp> listStatementSnTemp){
		if(null == listStatementSnTemp || listStatementSnTemp.isEmpty()){
			return 0;
		}
		return statementSnTempMapper.insertList(listStatementSnTemp);
	}
	
	
	private Integer saveStatementSellReconDetailDB(List<StatementSellReconDetail> listSellReconDetail){
		
		if(null == listSellReconDetail || listSellReconDetail.isEmpty()){
			return 0;
		}
		return statementSellReconDetailMapper.batchInsertReplace(listSellReconDetail);
	}
	
	private Integer saveStatementSellReconSplitDB(List<StatementSellReconSplit> listSellReconSplit){
		if(null == listSellReconSplit || listSellReconSplit.isEmpty()){
			return 0;
		}
		return statementSellReconSplitMapper.batchInsertReplace(listSellReconSplit);
	}
	
	private Integer saveStatementSellSplitDB(List<StatementSellSplit> listSellSplit){
		if(null == listSellSplit || listSellSplit.isEmpty()){
			return 0;
		}
		return statementSellSplitMapper.insertList(listSellSplit);
	}
	
	private Integer saveStatementSellCombileDB(List<StatementSellCombile> listSellCombile){
		if(null == listSellCombile || listSellCombile.isEmpty()){
			return 0;
		}
		return statementSellCombileMapper.insertList(listSellCombile);
	}
	
	private Integer saveStatementSellReconDB(StatementSellRecon sellRecon){
		StatementSellRecon condition = new StatementSellRecon();
		condition.setReconCode(sellRecon.getReconCode());
		condition.setDeletedFlag("N");
		StatementSellRecon db = statementSellReconMapper.selectOne(condition);
		if(null != db){
			sellRecon.setId(db.getId());
			statementSellReconMapper.updateByPrimaryKeySelective(sellRecon);
		}else{
			statementSellReconMapper.insert(sellRecon);
		}	
		return 0; 
	}
	
	private KcWarehouseRelation getWarehouseRelationByMerchantCode(String merchantCode){
		return kcRelationService.getWarehouseRelationByMerchantCode(merchantCode);
	}
	
	private KcCustomerRelation getCustomerRelationByMerchantCode(String merchantCode){
		return kcRelationService.getCustomerRelationByMerchantCode(merchantCode);
	}
	
	
	private void calculateReconSplit(StatementSellRecon record,
			List<StatementSellReconDetail> listSellReconDetail,
			List<StatementSellReconInstall> listReconInstall,
			List<StatementSellReconSplit> listSellReconSplit){
		Map<String, List<StatementSellReconSplit>> mapReconSplit = listSellReconSplit.stream().collect(Collectors.groupingBy(StatementSellReconSplit::getWorkOrder));
		Map<String,List<ProductSplitHistoryPrice>> mapProductPrice = new HashMap<>();
		Map<String,ProductSplit> mapProduct = new HashMap<>();
		//对账明细的修改
		for(StatementSellReconDetail reconDetail:listSellReconDetail){
			//新增
			if(StringUtils.isEmpty(reconDetail.getWorkOrder())){
				if(StringUtils.isEmpty(reconDetail.getProductCode()) ||
						StringUtils.isEmpty(reconDetail.getMaterialCodes())){
					continue;
				}
				if(reconDetail.getDeletedFlag().equals("Y")){
					continue;
				}
				
				ProductSplit productSplit = getProductSplit(reconDetail.getProductCode(), mapProduct);
				if(null == productSplit){
					continue;
				}
				String workOrder = "SEL" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
				reconDetail.setWorkOrder(workOrder);
				reconDetail.setReconCode(record.getReconCode());
				reconDetail.setCreatedBy(record.getUpdatedBy());
				reconDetail.setCreatedDate(SupplychainUtils.getNowDate());
				reconDetail.setUpdatedBy(record.getUpdatedBy());
				reconDetail.setUpdatedDate((null == reconDetail.getUpdatedDate())?SupplychainUtils.getNowDate():reconDetail.getUpdatedDate());
				List<ProductSplitHistoryPrice> listProductPrice = this.getProductPrice(reconDetail.getProductCode(), null, mapProductPrice);
				Double servicePrice = 0.0;
				for(ProductSplitHistoryPrice price:listProductPrice){
					if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode())) ||
							price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode())) ||
							price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
						continue;
					}
					servicePrice += price.getPrice();
				}
				boolean findHard = false;
				for(ProductSplitHistoryPrice price:listProductPrice){
					//安装服务除外
					if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
						continue;
					}
					if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode())) || 
							price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode()))){
						if(!SupplychainUtils.isZeroDoubleNumber(reconDetail.getHardwareUintPrice())){
							if(!findHard){
								price.setPrice(reconDetail.getHardwareUintPrice());
								listSellReconSplit.add(this.generatorStatementReconSplit(record.getUpdatedBy(), workOrder, record.getReconCode(), reconDetail.getSendCount(), productSplit, price));
								findHard = true;
							}
						}
					}else{
						if(!SupplychainUtils.isZeroDoubleNumber(servicePrice)){
							price.setPrice(reconDetail.getServiceUintPrice()*price.getPrice()/servicePrice);
							listSellReconSplit.add(this.generatorStatementReconSplit(record.getUpdatedBy(), workOrder, record.getReconCode(), reconDetail.getSendCount(), productSplit, price));
						}
						
					}
				}	
				continue;
			}
			
			//删除
			if(reconDetail.getDeletedFlag().equals("Y")){
				List<StatementSellReconSplit> listSubSplit = mapReconSplit.get(reconDetail.getWorkOrder());
				if(null == listSubSplit){
					continue;
				}
				for(StatementSellReconSplit split:listSubSplit){
					split.setUpdatedBy(record.getUpdatedBy());
					split.setDeletedFlag("Y");
				}
				continue;
			}
			
			//修改
			List<StatementSellReconSplit> listSubSplit = mapReconSplit.get(reconDetail.getWorkOrder());
			if(null == listSubSplit){
				continue;
			}
			double serviceTotalPrice = 0.0;
			for(StatementSellReconSplit split:listSubSplit){
				if(split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode())) ||
						split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode())) ||
						split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
					continue;
				}
				serviceTotalPrice += split.getServiceUintPrice();
			}
			
			for(StatementSellReconSplit split:listSubSplit){	
				if(split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
					continue;
				}else if(split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode())) ||
						split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode()))){
					//硬件或者配件
					split.setSendCount(reconDetail.getSendCount());
					split.setServiceUintPrice(reconDetail.getHardwareUintPrice());
					split.setServiceTotalPrice(split.getSendCount()*split.getServiceUintPrice());
					split.setUpdatedBy(record.getUpdatedBy());
					split.setUpdatedDate(SupplychainUtils.getNowDate());
				}else{
					//服务
					split.setSendCount(reconDetail.getSendCount());	
					if(!SupplychainUtils.isZeroDoubleNumber(serviceTotalPrice)){
						split.setServiceUintPrice(reconDetail.getServiceUintPrice()*split.getServiceUintPrice()/serviceTotalPrice);
					}
					split.setServiceTotalPrice(split.getSendCount()*split.getServiceUintPrice());
					split.setUpdatedBy(record.getUpdatedBy());
					split.setUpdatedDate(SupplychainUtils.getNowDate());
				}
			}
		}
		
		//安装服务修改
		for(StatementSellReconInstall reconInstall:listReconInstall){
			//添加
			if(StringUtils.isEmpty(reconInstall.getInstallCode())){
				reconInstall.setInstallCode("INS" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
				StatementSellReconSplit reconSplit = new StatementSellReconSplit();
				reconSplit.setSplitCode("SPT" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
				reconSplit.setWorkOrder(reconInstall.getInstallCode());
				reconSplit.setReconCode(record.getReconCode());
				reconSplit.setProductCode("");
				reconSplit.setProductName("");
				reconSplit.setProductType(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()));
				reconSplit.setMaterialCode(reconInstall.getMaterialCode());
				reconSplit.setMaterialName(reconInstall.getMaterialName());
				reconSplit.setSendCount(reconInstall.getSendCount());
				reconSplit.setServiceUintPrice(reconInstall.getServiceUintPrice());
				reconSplit.setServiceTotalPrice(reconInstall.getServiceTotalPrice());
				reconSplit.setTaxRate(6.0);
				reconSplit.setCreatedBy(record.getUpdatedBy());
				reconSplit.setUpdatedBy(record.getUpdatedBy());
				reconSplit.setCreatedDate(SupplychainUtils.getNowDate());
				reconSplit.setUpdatedDate(SupplychainUtils.getNowDate());
				reconSplit.setDeletedFlag("N");
				listSellReconSplit.add(reconSplit);
				continue;
			}
			//删除
			if(reconInstall.getDeletedFlag().equals("N")){
				for(StatementSellReconSplit reconSplit:listSellReconSplit){
					if(reconSplit.getMaterialCode().equals(reconInstall.getMaterialCode())){
						reconSplit.setDeletedFlag("N");
						reconSplit.setUpdatedBy(record.getUpdatedBy());
						reconSplit.setUpdatedDate(SupplychainUtils.getNowDate());
					}
				}
				continue;
			}
			
			//修改		
			for(StatementSellReconSplit reconSplit:listSellReconSplit){
				if(reconSplit.getDeletedFlag().equals("Y")){
					continue;
				}
				if(reconSplit.getMaterialCode().equals(reconInstall.getMaterialCode())){
					reconSplit.setSendCount(reconInstall.getSendCount());
					reconSplit.setServiceUintPrice(reconInstall.getServiceUintPrice());
					reconSplit.setServiceTotalPrice((double)reconInstall.getSendCount()*reconInstall.getServiceUintPrice());
					reconSplit.setUpdatedBy(record.getUpdatedBy());
					reconSplit.setUpdatedDate(SupplychainUtils.getNowDate());
					break;
				}
			}
				
		}	
	}
	
	//保存修改时 计算出需要删除的sn
	private List<String> calculateDeletedSn(String user,List<StatementSellReconDetail> listSellReconDetail){
		List<String> listDeletedSn = new ArrayList<>();
		for(StatementSellReconDetail reconDetail:listSellReconDetail){
			if(reconDetail.getListSn() == null || reconDetail.getListSn().isEmpty()){
				continue;
			}
			if(reconDetail.getDeletedFlag().equals("Y")){
				Iterator<String> iterator = reconDetail.getListSn().iterator();
				while(iterator.hasNext()){
					String statementSn = iterator.next();
					listDeletedSn.add(statementSn);
				}
			}else{
				if(reconDetail.getListSn().size() > reconDetail.getSendCount()){
					Integer delCount = reconDetail.getListSn().size() - reconDetail.getSendCount();
					Iterator<String> iterator = reconDetail.getListSn().iterator();
					Integer count = 0;
					while(iterator.hasNext()){
						if(count == delCount){
							break;
						}
						String statementSn = iterator.next();
						listDeletedSn.add(statementSn);						
						count++;
					}
				}
			}	
		}
		return listDeletedSn;
	}
	
	private void filtStatementSellSnFromDB(List<StatementSellReconDetail> listReconDetail){
		if(null == listReconDetail || listReconDetail.isEmpty()){
			return;
		}
		List<String> listWorkOrders = new ArrayList<>();
		for(StatementSellReconDetail reconDetail:listReconDetail){
			listWorkOrders.add(reconDetail.getWorkOrder());
		}
		Example example = new Example(StatementSnTemp.class);
		example.createCriteria().andIn("workOrder", listWorkOrders);
		List<StatementSnTemp> listStatementSnTemp = statementSnTempMapper.selectByExample(example);
		if(null == listStatementSnTemp || listStatementSnTemp.isEmpty()){
			return;
		}
		Map<String,List<StatementSnTemp>> mapStementSnTemp = listStatementSnTemp.stream().collect(Collectors.groupingBy(StatementSnTemp::getWorkOrder));
		for(StatementSellReconDetail reconDetail:listReconDetail){
			List<StatementSnTemp> listStementTempSn = mapStementSnTemp.get(reconDetail.getWorkOrder());
			if(null != listStementTempSn && !listStementTempSn.isEmpty()){
				List<String> listSn = listStementTempSn.stream().map(StatementSnTemp::getSn).collect(Collectors.toList());
				List<StatementSell> listStatementSell = listStatementSellByListSns(listSn);
				List<StatementSn> listStatementSn = new ArrayList<>();
				StatementSn statementSn = null;
				Integer bFlag = null;
				for(StatementSell sell:listStatementSell){
					statementSn = generatorStatementSn(reconDetail.getUpdatedBy(),reconDetail.getWorkOrder(),sell);
					listStatementSn.add(statementSn);
				}
				reconDetail.setListStatementSn(listStatementSn);				
			}
		}
	}
	
	private void filtStatementSellReconDetailListSnFromDB(List<StatementSellReconDetail> listReconDetail){
		if(null == listReconDetail || listReconDetail.isEmpty()){
			return;
		}
		List<String> listWorkOrders = new ArrayList<>();
		for(StatementSellReconDetail reconDetail:listReconDetail){
			listWorkOrders.add(reconDetail.getWorkOrder());
		}
		Example example = new Example(StatementSnTemp.class);
		example.createCriteria().andIn("workOrder", listWorkOrders);
		List<StatementSnTemp> listStatementSn = statementSnTempMapper.selectByExample(example);
		if(null == listStatementSn || listStatementSn.isEmpty()){
			return;
		}
		Map<String,List<StatementSnTemp>> mapStementSn = listStatementSn.stream().collect(Collectors.groupingBy(StatementSnTemp::getWorkOrder));
		for(StatementSellReconDetail reconDetail:listReconDetail){
			List<StatementSnTemp> listStementSn = mapStementSn.get(reconDetail.getWorkOrder());
			if(null != listStementSn && !listStementSn.isEmpty()){
				List<String> listSn = listStementSn.stream().map(StatementSnTemp::getSn).collect(Collectors.toList());
				reconDetail.setListSn(listSn);
			}	
		//	reconDetail.setListStatementSn();	
		}
	}
	
	private List<StatementSellReconDetail> getStatementSellReconDetailFromDB(String reconCode){
		Example example = new Example(StatementSellReconDetail.class);
		example.createCriteria().andEqualTo("reconCode", reconCode)
								.andEqualTo("deletedFlag","N");
		return statementSellReconDetailMapper.selectByExample(example);
	}
	
	private List<StatementSellReconSplit> getStatementSellReconSplitFromDB(String reconCode){
		Example example = new Example(StatementSellReconSplit.class);
		example.createCriteria().andEqualTo("reconCode", reconCode)
								.andEqualTo("deletedFlag","N");
		return statementSellReconSplitMapper.selectByExample(example);
	}
	
	private StatementSellRecon getStatementSellReconFromDB(String reconCode){
		StatementSellRecon condition = new StatementSellRecon();
		condition.setReconCode(reconCode);
		condition.setDeletedFlag("N");
		return statementSellReconMapper.selectOne(condition);
	}
	
	
	
	private StatementSellRecon generatorStatementSellRecon(StatementSellRecon record,String reconCode,KcWarehouseRelation kcWarehouseRelation,KcCustomerRelation kcCustomerRelation,
			MerchantFacadeResponse merchantFacade,
			List<StatementSellReconDetail> listReconDetail,
			List<StatementSellReconSplit> listReconSplit,
			List<StatementSellReconInstall> listReconInstall){
		StatementSellRecon sellRecon = new StatementSellRecon();
		sellRecon.setReconCode(reconCode);
		sellRecon.setChannel(record.getChannel());
		sellRecon.setSaleGroupCode(record.getSaleGroupCode());
		sellRecon.setSaleGroupName(record.getSaleGroupName());
		sellRecon.setSaleGroupNameCus(Constants.statement_organize_name);		
		sellRecon.setSaleGroupAddr(Constants.statement_organize_addr);
		sellRecon.setSaleGroupContact(Constants.statement_organize_contact);
		sellRecon.setSaleGroupPhone(Constants.statement_organize_phone);
		sellRecon.setCustomerCode(kcCustomerRelation.getCustomerCode());
		sellRecon.setCustomerName(kcCustomerRelation.getCustomerName());
		sellRecon.setWarehouseCode(kcWarehouseRelation.getWarehouseCode());
		sellRecon.setWarehouseName(kcWarehouseRelation.getWarehouseName());
		sellRecon.setCustomerAddr(merchantFacade.getAddress());
		sellRecon.setCustomerContact(merchantFacade.getContactor());
		sellRecon.setCustomerPhone(merchantFacade.getContactPhone());
		sellRecon.setMerchantCode(record.getMerchantCode());
		sellRecon.setMerchantName(record.getMerchantName());
		sellRecon.setReconTimeStart(record.getReconTimeStart());
		sellRecon.setReconTimeEnd(record.getReconTimeEnd());
		sellRecon.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_NOT_SPLIT.getCode());
		sellRecon.setCreatedBy(record.getCreatedBy());
		sellRecon.setUpdatedBy(record.getCreatedBy());
		sellRecon.setCreatedDate(SupplychainUtils.getNowDate());
		sellRecon.setUpdatedDate(SupplychainUtils.getNowDate());
		sellRecon.setDeletedFlag("N");
		sellRecon.setListReconDetail(listReconDetail);
		sellRecon.setListReconSplit(listReconSplit);
		sellRecon.setListReconInstall(listReconInstall);
		sellRecon.setTotalPrice(0.00);
		for(StatementSellReconSplit reconSplit:listReconSplit){
			sellRecon.setTotalPrice(sellRecon.getTotalPrice() + reconSplit.getServiceTotalPrice());
		}
		sellRecon.setTotalPrice(SupplychainUtils.getDecimalDouble(sellRecon.getTotalPrice()));
		return sellRecon;
	}
	
	private void filtStatementSellReconInstall(List<StatementSellReconSplit> listSplit,List<StatementSellReconInstall> listReconInstall){
		if(null == listSplit){
			return;
		}
		if(listSplit.isEmpty()){
			return;
		}
		StatementSellReconInstall install = null;
		Map<String,StatementSellReconInstall> mapMaterialCode = new HashMap<>();
		for(StatementSellReconSplit split:listSplit){
			if(!split.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
				continue;
			}
			install = mapMaterialCode.get(split.getMaterialCode());
			if(null == install){
				install = new StatementSellReconInstall();
				install.setDeletedFlag(split.getDeletedFlag());
				install.setInstallCode("INS" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
				install.setMaterialCode(split.getMaterialCode());
				install.setMaterialName(split.getMaterialName());
				install.setSendCount(split.getSendCount());
				install.setServiceUintPrice(SupplychainUtils.getDecimalDouble(split.getServiceUintPrice()));
				install.setServiceTotalPrice(SupplychainUtils.getDecimalDouble((double)install.getSendCount()*install.getServiceUintPrice()));
				mapMaterialCode.put(split.getMaterialCode(), install);
				
				split.setInstallCode(install.getInstallCode());
				continue;
			}
			split.setInstallCode(install.getInstallCode());
			install.setSendCount(split.getSendCount() + install.getSendCount());
			install.setServiceTotalPrice(SupplychainUtils.getDecimalDouble((double)install.getSendCount()*install.getServiceUintPrice()));
		}
		for(Map.Entry<String,StatementSellReconInstall> entry:mapMaterialCode.entrySet()){
			listReconInstall.add(entry.getValue());
		}
	}
	
	private void filtStatementSellReconDetail(boolean bIsFongKongChannel,String user,String reconCode,String merchantOrderCode,String sendGoodsTime,String productType,ProductSplit productSplit,List<ProductSplitHistoryPrice> listPrice,List<StatementSell> listItemSell,
			List<StatementSellReconDetail> listDetail,
			List<StatementSellReconSplit> listSplit){	
		StatementSellReconDetail reconDetail = new StatementSellReconDetail();
		//List<StatementSn> listSn = new ArrayList<>();
		//List<String> listSn = new ArrayList<>();
		List<String> listSnString = new ArrayList<>();
		List<StatementSnTemp> listSn = new ArrayList<>();
		boolean bFirstItem = false;
		String materialCodes = "";
		String materialNames = "";
		String logisticsInfo = "";
		String workOrder = "SEL" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker);
		Map<String,Integer> mapMaterial = new HashMap<>();
		Map<String,Integer> mapLogistics = new HashMap<>();
		Map<String,Integer> mapLogisticsCount = new HashMap<>();
		for(StatementSell sell:listItemSell){
			Integer iCount = mapLogisticsCount.get(sell.getLogisticsNo());
			if(null == iCount){
				iCount = 1;
				mapLogisticsCount.put(sell.getLogisticsNo(), iCount);
				continue;
			}
			iCount++;
			mapLogisticsCount.put(sell.getLogisticsNo(), iCount);
		}
			
		for(StatementSell sell:listItemSell){
			Integer bRepeat = mapLogistics.get(sell.getLogisticsNo());
			if(null != bRepeat){
				continue;
			}
			mapLogistics.put(sell.getLogisticsNo(), 1);
			if(bFirstItem){
				logisticsInfo += "<br />";
			}
			logisticsInfo += sell.getLogisticsNo();
			logisticsInfo += ":";
			logisticsInfo += sell.getLogisticsCmp();
			logisticsInfo += ":";
			logisticsInfo += mapLogisticsCount.get(sell.getLogisticsNo());
			bFirstItem = true;	
		}
		bFirstItem = false;
		for(StatementSell sell:listItemSell){
		//	listSn.add(generatorStatementSn(user,workOrder,sell));
			listSnString.add(sell.getSn());
			listSn.add(generatorStatementSnTemp(workOrder,sell.getSn()));
			Integer bRepeat = mapMaterial.get(sell.getMaterialCode());
			if(null != bRepeat){
				continue;
			}
			mapMaterial.put(sell.getMaterialCode(), 1);
			if(bFirstItem){
				materialCodes += "/";
				materialNames += "/";
			}
			materialCodes += sell.getMaterialCode();	
			materialNames += sell.getMaterialName();
			bFirstItem = true;			
		}

		reconDetail.setWorkOrder(workOrder);
		reconDetail.setMerchantOrderCode(merchantOrderCode);
		reconDetail.setProductCode(productSplit.getProductCode());
		reconDetail.setProductName(productSplit.getProductName());
		reconDetail.setMaterialCodes(materialCodes);
		reconDetail.setMaterialNames(materialNames);
		reconDetail.setLogisticsInfo(logisticsInfo);
		reconDetail.setPackageOne(productSplit.getPackageOne());
		reconDetail.setServiceTime(productSplit.getServiceTime());
		reconDetail.setUnitType("PSC");
		reconDetail.setSendCount(listItemSell.size());
		reconDetail.setReconCode(reconCode);		
		reconDetail.setCreatedBy(user);
		reconDetail.setCreatedDate(SupplychainUtils.getNowDate());
		reconDetail.setUpdatedBy(user);
		reconDetail.setUpdatedDate(SupplychainUtils.getNowDate());
		reconDetail.setDeletedFlag("N");
		double hardwarePrice = 0.0;
		double servicePrice = 0.0;
		double installPrice = 0.0;
		
		if(productType.equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode()))){
			for(ProductSplitHistoryPrice price:listPrice){
				if(productType.equals(price.getProductType())){
					int cmpresult = materialCodes.indexOf(price.getMaterialCode());	
					if(cmpresult != -1 || materialCodes.equals(price.getMaterialCode())){
						hardwarePrice = price.getPrice();
						listSplit.add(generatorStatementReconSplit(user,workOrder,reconCode,reconDetail.getSendCount(),productSplit,price));
						break;
					}
				}
			}
		}else{
			for(ProductSplitHistoryPrice price:listPrice){	
				if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_ZERO.getCode()))
						|| price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN.getCode()))){
					if(productType.equals(price.getProductType())){
						int cmpresult = materialCodes.indexOf(price.getMaterialCode());					
						if(cmpresult != -1 || materialCodes.equals(price.getMaterialCode())){						
							hardwarePrice = price.getPrice();
							listSplit.add(generatorStatementReconSplit(user,workOrder,reconCode,reconDetail.getSendCount(),productSplit,price));
						}	
					}
				}else if(price.getProductType().equals(String.valueOf(ProductSplitMaterialTypeEnum.PRODUCT_SPLIT_MATERIAL_TYPE_FOUR.getCode()))){
					installPrice = price.getPrice();
					listSplit.add(generatorStatementReconSplit(user,workOrder,reconCode,reconDetail.getSendCount(),productSplit,price));
				}else{
					if(SupplychainUtils.isZeroDoubleNumber(price.getPrice())){
						continue;
					}
					listSplit.add(generatorStatementReconSplit(user,workOrder,reconCode,reconDetail.getSendCount(),productSplit,price));
					servicePrice += price.getPrice();
				}
			}
		}
		reconDetail.setHardwareUintPrice(SupplychainUtils.getDecimalDouble(hardwarePrice));
		reconDetail.setServiceUintPrice(SupplychainUtils.getDecimalDouble(servicePrice));
		reconDetail.setHardwareTotalPrice(SupplychainUtils.getDecimalDouble(reconDetail.getHardwareUintPrice()*(double)reconDetail.getSendCount()));
		reconDetail.setServiceTotalPrice(SupplychainUtils.getDecimalDouble(reconDetail.getServiceUintPrice()*(double)reconDetail.getSendCount()));		
		if(bIsFongKongChannel){
			reconDetail.setUintTotalPrice(SupplychainUtils.getDecimalDouble(reconDetail.getHardwareUintPrice() + reconDetail.getServiceUintPrice()));
		}else{
			reconDetail.setUintTotalPrice(SupplychainUtils.getDecimalDouble(reconDetail.getHardwareUintPrice() + reconDetail.getServiceUintPrice()) + SupplychainUtils.getDecimalDouble(installPrice));
		}
		if(bIsFongKongChannel){
			reconDetail.setTotalPrice(SupplychainUtils.getDecimalDouble(reconDetail.getHardwareTotalPrice() + reconDetail.getServiceTotalPrice()));
		}else{
			reconDetail.setTotalPrice(SupplychainUtils.getDecimalDouble(reconDetail.getUintTotalPrice()*(double)reconDetail.getSendCount()));
		}
		
	//	reconDetail.setListStatementSn(listSn);
		reconDetail.setListSn(listSnString);
		reconDetail.setListStatementSnTemp(listSn);
		reconDetail.setSendGoodsTime(sendGoodsTime);
		listDetail.add(reconDetail);
	}
	
	private StatementSellReconSplit generatorStatementReconSplit(String user,String workOrder,String reconCode,
			Integer sendCount,ProductSplit productSplit,ProductSplitHistoryPrice productPrice){
		StatementSellReconSplit  reconSplit = new StatementSellReconSplit();
		reconSplit.setSplitCode("SPT" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
		reconSplit.setWorkOrder(workOrder);
		reconSplit.setReconCode(reconCode);
		reconSplit.setProductCode(productSplit.getProductCode());
		reconSplit.setProductName(productSplit.getProductName());
		reconSplit.setProductType(productPrice.getProductType());
		reconSplit.setMaterialCode(productPrice.getMaterialCode());
		reconSplit.setMaterialName(productPrice.getMaterialName());
		reconSplit.setSendCount(sendCount);
		reconSplit.setServiceUintPrice(SupplychainUtils.getDecimalDouble(productPrice.getPrice()));
		reconSplit.setServiceTotalPrice(SupplychainUtils.getDecimalDouble(productPrice.getPrice()*(double)sendCount));
		reconSplit.setTaxRate(SupplychainUtils.getDecimalDouble(productPrice.getTaxRate()));
		reconSplit.setCreatedBy(user);
		reconSplit.setUpdatedBy(user);
		reconSplit.setCreatedDate(SupplychainUtils.getNowDate());
		reconSplit.setUpdatedDate(SupplychainUtils.getNowDate());
		reconSplit.setDeletedFlag("N");
		return reconSplit;
	}
	
	private StatementSnTemp generatorStatementSnTemp(String workOrder,String sn){
		StatementSnTemp result = new StatementSnTemp();
		result.setSn(sn);
		result.setWorkOrder(workOrder);
		result.setCreatedBy("admin");
		result.setUpdatedBy("admin");
		result.setCreatedDate(SupplychainUtils.getNowDate());
		result.setUpdatedDate(SupplychainUtils.getNowDate());
		return result;	
	}
	
	private StatementSn generatorStatementSn(String user,String workOrder,StatementSell sell){
		StatementSn sn = new StatementSn();
		sn.setSn(sell.getSn());
		sn.setProductCode(sell.getProductCode());
		sn.setProductName(sell.getProductName());
		sn.setMaterialCode(sell.getMaterialCode());
		sn.setMaterialName(sell.getMaterialName());
		sn.setVtSn(sell.getVtSn());
		sn.setStatementType("SELL");
		sn.setWorkOrder(workOrder);
		sn.setCreatedBy(user);
		sn.setCreatedDate(SupplychainUtils.getNowDate());
		sn.setUpdatedBy(user);
		sn.setUpdatedDate(SupplychainUtils.getNowDate());
		sn.setDeletedFlag("N");
		return sn;
	}
	
	private ProductSplitHistoryPrice getMaterialProductPrice(String productCode,String materialCode,Date orderDate,Map<String,List<ProductSplitHistoryPrice>> mapProductPrice){
		List<ProductSplitHistoryPrice> listSplitPrice = this.getProductPrice(productCode, orderDate, mapProductPrice);
		if(null == listSplitPrice || listSplitPrice.isEmpty()){
			return null;
		}
		for(ProductSplitHistoryPrice price:listSplitPrice){
			if(price.getMaterialCode().equals(materialCode)){
				return price;
			}
		}
		return null;
	}
	
	private List<ProductSplitHistoryPrice> getProductPrice(String productCode,Date orderDate,Map<String,List<ProductSplitHistoryPrice>> mapProductPrice){
		
		String strKey = "";
		if(null != orderDate){
			strKey = SupplychainUtils.getNowDateStringYMD(orderDate);
		}else{
			strKey = "1990-01-01";
		}
		strKey += productCode; 
		List<ProductSplitHistoryPrice> listPrice = mapProductPrice.get(strKey);
		if(null != listPrice){
			return listPrice;
		}
		listPrice = productPriceService.listProductPrice(productCode, orderDate);
		if(null == listPrice || listPrice.isEmpty()){
			return listPrice;
		}
		mapProductPrice.put(productCode, listPrice);
		return listPrice;
	}
	
	private ProductSplit getProductSplit(String productCode,Map<String,ProductSplit> mapProduct){
		ProductSplit productSplit = mapProduct.get(productCode);
		if(null != productSplit){
			return productSplit;
		}
		productSplit =  productSplitService.getProductSplit(productCode);
		if(null != productSplit){
			mapProduct.put(productCode, productSplit);
		}
		return productSplit;
	}
	
	
	private void setMapStatementSell(boolean bIsFongKongChannel,List<StatementSell> listSell,Map<String,List<StatementSell>> mapSell){
		List<StatementSell> listItemSell = null;
		String strKey = "";
		if(bIsFongKongChannel){
			for(StatementSell sell:listSell){
				strKey = sell.getProductCode()+":"+sell.getProductType() + ":" +  SupplychainUtils.getNowDateStringYMD(sell.getSendTime());
				listItemSell = mapSell.get(strKey);
				if(null == listItemSell){
					listItemSell = new ArrayList<>();
					mapSell.put(strKey, listItemSell);
				}
				listItemSell.add(sell);
			}
		}else{
			for(StatementSell sell:listSell){
				strKey = sell.getProductCode()+":"+sell.getProductType() + ":" + sell.getMerchantOrderCode() + ":" + SupplychainUtils.getNowDateStringYMD(sell.getSendTime());
				listItemSell = mapSell.get(strKey);
				if(null == listItemSell){
					listItemSell = new ArrayList<>();
					mapSell.put(strKey, listItemSell);
				}
				listItemSell.add(sell);
			}
		}
		
	}
	
	private List<StatementSell> listStatementSellByListSns(List<String> listSn){
		if(null == listSn || listSn.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSell.class);
		example.createCriteria().andIn("sn", listSn);
		return statementSellMapper.selectByExample(example);
	}
	
	private List<StatementSell> listStatementSell(Date timeStart,Date timeEnd,String merchantCode){
		Example example = new Example(StatementSell.class);
		Date dt = SupplychainUtils.getDateFromStringYMD("2020-01-01");
		example.createCriteria().andBetween("sendTime", dt, timeEnd)
								.andIsNull("workOrder")
								.andEqualTo("merchantCode", merchantCode);
		return statementSellMapper.selectByExample(example);
	}
	
	private boolean beFongKongChannel(Byte channel){
		String param = String.valueOf(channel);
		if(param.equals("2") || param.equals("4")){
			return true;
		}
		return false;
	}

	private boolean beRightReconTime(Date date,String merchantCode){
		return statementSellReconMapper.countStatementSellRecon(SupplychainUtils.getStringFromDate(date),merchantCode) > 0 ? false:true;
	}
		
}
