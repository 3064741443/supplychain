package cn.com.glsx.supplychain.remote.am;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ReasonInvalidDeviceNum;
import cn.com.glsx.supplychain.enums.StatementCollectionStatusEnum;
import cn.com.glsx.supplychain.model.am.KcCustomerRelation;
import cn.com.glsx.supplychain.model.am.KcWarehouseRelation;
import cn.com.glsx.supplychain.model.am.MaterialInfo;
import cn.com.glsx.supplychain.model.am.StatementCollection;
import cn.com.glsx.supplychain.model.am.StatementCollectionExport;
import cn.com.glsx.supplychain.model.am.StatementCollectionImport;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.service.am.KcWarehouseRelationService;
import cn.com.glsx.supplychain.service.am.MaterialInfoService;
import cn.com.glsx.supplychain.service.am.StatementCollectionService;
import cn.com.glsx.supplychain.service.am.StatementCollectionSplitService;
import cn.com.glsx.supplychain.service.bs.DealerUserInfoService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.StatementCollectSplitExcelVo;

import com.alibaba.dubbo.config.annotation.Service;
import com.glsx.biz.merchant.service.MerchantService;
import com.glsx.cloudframework.core.util.BeanUtils;

import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leiming
 * @version V1.0
 * @Title: StatementCollectionAdminRemote.java
 * @Description: 对账单-广联采集接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("StatementCollectionAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class StatementCollectionAdminRemoteImpl implements StatementCollectionAdminRemote{
    private static final Logger LOGGER = LoggerFactory.getLogger(StatementCollectionAdminRemoteImpl.class);

    @Autowired
    private StatementCollectionService statementCollectionService;

    @Autowired
    private StatementCollectionSplitService statementCollectionSplitService;
    
    @Autowired
    private KcWarehouseRelationService warehouseRelationService;
    
    @Autowired
    private MaterialInfoService materialInfoService;

    @Autowired
    private MerchantService merchantService;  //老运营平台接口后面改用商户新平台接口
    
    @Autowired
    private SnowflakeWorker snowflakeWorker;

    @Override
    public RpcResponse<RpcPagination<StatementCollection>> listStatementCollection(RpcPagination<StatementCollection> pagination) {
        RpcResponse<RpcPagination<StatementCollection>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(statementCollectionService.listStatementCollection(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<StatementCollection> getStatementCollectionByid(Long id) {
        try {
            RpcAssert.assertNotNull(id, DefaultErrorEnum.PARAMETER_NULL, "id must not be null");
            return RpcResponse.success(statementCollectionService.getStatementCollectionByid(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> add(StatementCollection statementCollection) {
        try {
            RpcAssert.assertNotNull(statementCollection, DefaultErrorEnum.PARAMETER_NULL, "statementCollection must not be null");
            return RpcResponse.success(statementCollectionService.add(statementCollection));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateById(StatementCollection statementCollection) {
        try {
            RpcAssert.assertNotNull(statementCollection, DefaultErrorEnum.PARAMETER_NULL, "statementCollection must not be null");
            return RpcResponse.success(statementCollectionService.updateById(statementCollection));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> deleteStatementCollectionByDate(StatementCollection statementCollection) {
        try {
            RpcAssert.assertNotNull(statementCollection, DefaultErrorEnum.PARAMETER_NULL, "statementCollection must not be null");
            return RpcResponse.success(statementCollectionService.deleteStatementCollectionByDate(statementCollection));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }
    
    private boolean isNotNullParam(StatementCollectionImport item){
    	if(StringUtils.isEmpty(item.getTime()))
    		return false;
    	if(StringUtils.isEmpty(item.getArea()))
    		return false;
    	if(StringUtils.isEmpty(item.getShopName()))
    		return false;
    	if(StringUtils.isEmpty(item.getMerchant()))
    		return false;
    	if(StringUtils.isEmpty(item.getSettleCompany()))
    		return false;   	
    	if(StringUtils.isEmpty(item.getGoodsCode()))
    		return false;
    	if(StringUtils.isEmpty(item.getGoodsName()))
    		return false;
    	if(StringUtils.isEmpty(item.getSalesQuantity()))
    		return false;
    	if(StringUtils.isEmpty(item.getPrice()))
    		return false;
    	if(StringUtils.isEmpty(item.getRebate()))
    		return false;
    	try{
    		Double.valueOf(item.getPrice());
    	}catch(NumberFormatException e){
    		return false;
    	}
    	try{
    		Double.valueOf(item.getRebate());
    	}catch(NumberFormatException e){
    		return false;
    	}
    	return true;
    }
    
    private boolean checkCollectionTime(String strParam){
    	if(StringUtils.isEmpty(strParam)){
    		return false;
    	}
    	return SupplychainUtils.isTimeYMFormat(strParam);
    }
    
    private KcWarehouseRelation getWarehouseRelation(String strParam,Map<String, KcWarehouseRelation> mapKcWarehouseRelation){
    	KcWarehouseRelation wareHouseRelation = mapKcWarehouseRelation.get(strParam);
    	if(null != wareHouseRelation){
    		return wareHouseRelation;
    	}
    	wareHouseRelation = warehouseRelationService.getWarehouseRelationByCode(strParam);
    	if(null != wareHouseRelation){
    		mapKcWarehouseRelation.put(strParam, wareHouseRelation);		
    	}
    	return wareHouseRelation;
    }
    
    private KcCustomerRelation getCustomerRelation(String strParam,Map<String, KcCustomerRelation> mapKcCustomerRelation){
    	KcCustomerRelation customerRelation = mapKcCustomerRelation.get(strParam);
    	if(null != customerRelation){
    		return customerRelation;
    	}
    	customerRelation = warehouseRelationService.getCustomerRelationByCode(strParam);
    	if(null != customerRelation){
    		mapKcCustomerRelation.put(strParam, customerRelation);
    	}
    	return customerRelation;
    }
    
    private MaterialInfo getMaterialInfo(String strParam,Map<String, MaterialInfo> mapMaterialInfo){
    	MaterialInfo materialInfo = mapMaterialInfo.get(strParam);
    	if(null != materialInfo){
    		return materialInfo;
    	}
    	materialInfo = materialInfoService.getMaterialInfoByCode(strParam);
    	if(null != materialInfo){
    		mapMaterialInfo.put(strParam, materialInfo);
    	}
    	return materialInfo;
    }
    
    @Override
    public RpcResponse<CheckImportDataVo> checkImportStatementCollection(List<StatementCollectionImport> statementCollectionImportList){
    	CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
    	if(null == statementCollectionImportList || statementCollectionImportList.isEmpty()){
    		return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
    	}
    	if(statementCollectionImportList.size() > 20000){
    		return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
    	}
    	//验证成功数据返回
    	List<StatementCollectionImport> successList = new ArrayList<>();
    	//验证失败数据返回
    	List<StatementCollectionImport> failList = new ArrayList<>();
    	Map<String, KcWarehouseRelation> mapKcWarehouseRelation = new HashMap<>();
    	Map<String, KcCustomerRelation> mapKcCustomerRelation = new HashMap<>();
    	Map<String, MaterialInfo> mapMaterialInfo = new HashMap<>();
    	
    	try{
    		LOGGER.info("StatementCollectionAdminRemoteImpl :广汇集采importTime 检验开始" + new Date());
        	for(StatementCollectionImport item:statementCollectionImportList){
        		LOGGER.info("StatementCollectionAdminRemoteImpl::checkImportStatementCollection handle data item:" + item.toString());
        		if(!isNotNullParam(item)){
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT.getValue());
        			failList.add(item);
        			continue;
        		}        		
        		if(!checkCollectionTime(item.getTime())){
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_STATEMENT_FINANCE_TIME_ERROR.getValue());
        			failList.add(item);
        			continue;
        		}
        		if(null == getWarehouseRelation(item.getMerchant(),mapKcWarehouseRelation)){
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_MERCHANT_NAME.getValue());
        			failList.add(item);
        			continue;
        		}
        		if(null == getCustomerRelation(item.getSettleCompany(),mapKcCustomerRelation)){
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_CUSTOMER_CODE.getValue());
        			failList.add(item);
        			continue;
        		}
        		if(null == getMaterialInfo(item.getGoodsCode(),mapMaterialInfo)){
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_GOODS_CODE.getValue());
        			failList.add(item);
        			continue;
        		} 
        		item.setGoodsType("");
        		successList.add(item);
        	}
        	LOGGER.info("StatementCollectionAdminRemoteImpl :广汇集采importTime 检验结束" + new Date());
        	checkImportDataVo.setStatementCollectionSuccessList(successList);
        	checkImportDataVo.setStatementCollectionFailedList(failList);	
    	}catch (Exception e){
    		LOGGER.error(e.getMessage(), e);
    		 return RpcResponse.error(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
    	}
    	mapKcWarehouseRelation = null;
    	mapKcCustomerRelation = null;
    	mapMaterialInfo = null;
    	return RpcResponse.success(checkImportDataVo);
    }
    
    @Override
    public RpcResponse<Integer> importStatementCollectionImport(String operatorName,List<StatementCollectionImport> statementCollectionImportList){
    	Integer result = 0;
    	if(null == statementCollectionImportList || statementCollectionImportList.isEmpty()){
    		return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
    	}
    	if(statementCollectionImportList.size() > 5000){
    		return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
    	}
    	Map<String, KcWarehouseRelation> mapKcWarehouseRelation = new HashMap<>();
    	Map<String, KcCustomerRelation> mapKcCustomerRelation = new HashMap<>();
    	Map<String, MaterialInfo> mapMaterialInfo = new HashMap<>();
    	try{
    		List<StatementCollection>statementCollectionList = new ArrayList<>();
    		StatementCollection statementCollection = null;
    		KcWarehouseRelation warehouseRelation = null;
    		KcCustomerRelation customerRelation = null;
    		MaterialInfo materialInfo = null;
    		for(StatementCollectionImport item:statementCollectionImportList){
    			LOGGER.info("StatementCollectionAdminRemoteImpl::importStatementCollectionImport handle data item:{}", item);
    			if(!isNotNullParam(item)){ 
    				LOGGER.info("StatementCollectionAdminRemoteImpl::importStatementCollectionImport null param");
        			continue;
        		}
    			if(!checkCollectionTime(item.getTime())){
    				LOGGER.info("StatementCollectionAdminRemoteImpl::importStatementCollectionImport invalid time format");
        			continue;
        		}
    			warehouseRelation = this.getWarehouseRelation(item.getMerchant(), mapKcWarehouseRelation);
    			if(null == warehouseRelation){
    				LOGGER.info("StatementCollectionAdminRemoteImpl::importStatementCollectionImport invalid warehouse relation");
        			continue;
    			}
    			customerRelation = this.getCustomerRelation(item.getSettleCompany(), mapKcCustomerRelation);
    			if(null == customerRelation){
    				LOGGER.info("StatementCollectionAdminRemoteImpl::importStatementCollectionImport invalid customer relation");
        			continue;
    			}
    			materialInfo = this.getMaterialInfo(item.getGoodsCode(), mapMaterialInfo);
    			if(null == materialInfo){
    				LOGGER.info("StatementCollectionAdminRemoteImpl::importStatementCollectionImport invalid material info");
        			continue;
    			}
    			
    			statementCollection = new StatementCollection();
    			statementCollection.setId(null);
    			statementCollection.setWorkOrder("DCO" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
    			statementCollection.setServiceType(item.getServiceType());
    			statementCollection.setTime(SupplychainUtils.getDateFromStringYM(item.getTime()));
                statementCollection.setArea(item.getArea());
                statementCollection.setMerchant(warehouseRelation.getWarehouseName());
                statementCollection.setShopName(item.getShopName());
                statementCollection.setMaterialCode(materialInfo.getMaterialCode());
                statementCollection.setMaterialName(materialInfo.getMaterialName());
                statementCollection.setDeviceType(item.getGoodsType());
                statementCollection.setPriceNum(Double.valueOf(item.getPrice()));
                statementCollection.setPrice(Double.valueOf(item.getPrice())/Double.valueOf(item.getSalesQuantity()));
                statementCollection.setRebate(Double.valueOf(item.getRebate()));
                statementCollection.setRebateNum(Double.valueOf(item.getRebate())*Double.valueOf(item.getPrice()));
                statementCollection.setAfterRebateNum(statementCollection.getPriceNum()-statementCollection.getRebateNum());
                statementCollection.setAfterRebatePrice(statementCollection.getAfterRebateNum()/Double.valueOf(item.getSalesQuantity()));               
                statementCollection.setSalesQuantity(SupplychainUtils.beInteger(item.getSalesQuantity())?Integer.valueOf(item.getSalesQuantity()):(int)Math.round(Double.valueOf(item.getSalesQuantity())));
                statementCollection.setSalesQuantity(Integer.valueOf(item.getSalesQuantity()));          
                statementCollection.setCustomerCode(customerRelation.getCustomerCode());
                statementCollection.setCustomerName(customerRelation.getCustomerName());
                statementCollection.setSaleGroupCode(item.getSaleCode());
                statementCollection.setSaleGroupName(item.getSaleName());
                statementCollection.setWarehouseCode(warehouseRelation.getWarehouseCode());
                statementCollection.setWarehouseName(warehouseRelation.getWarehouseName());
                
                statementCollection.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
                statementCollection.setCreatedBy(operatorName);
                statementCollection.setCreatedDate(new Date());
                statementCollection.setUpdatedBy(operatorName);
                statementCollection.setUpdatedDate(new Date());
                statementCollection.setDeletedFlag("N");
                
                statementCollectionList.add(statementCollection);
    		}	
    		statementCollectionService.batchAddStatementCollection(statementCollectionList);
    	}
    	catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
        return RpcResponse.success(result);
    }

    @Override
    public RpcResponse<RpcPagination<StatementCollectionSplit>> listStatementCollectionSplit(RpcPagination<StatementCollectionSplit> pagination) {
        RpcResponse<RpcPagination<StatementCollectionSplit>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(statementCollectionSplitService.listStatementCollectionSplit(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(StatementCollectionSplit statementCollectionSplit) {
        try {
            RpcAssert.assertNotNull(statementCollectionSplit, DefaultErrorEnum.PARAMETER_NULL, "statementCollectionSplit must not be null");
            return RpcResponse.success(statementCollectionSplitService.add(statementCollectionSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateById(StatementCollectionSplit statementCollectionSplit) {
        try {
            RpcAssert.assertNotNull(statementCollectionSplit, DefaultErrorEnum.PARAMETER_NULL, "statementCollectionSplit must not be null");
            return RpcResponse.success(statementCollectionSplitService.updateById(statementCollectionSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<StatementCollectSplitExcelVo>> exportStatementCollectionSplitExit(StatementCollectionSplit statementCollectionSplit) {
        RpcResponse<List<StatementCollectSplitExcelVo>> result;
        try {
            RpcAssert.assertNotNull(statementCollectionSplit, DefaultErrorEnum.PARAMETER_NULL, "statementCollectionSplit must not be null");
            result =  RpcResponse.success(statementCollectionSplitService.exportStatementCollectionSplitExit(statementCollectionSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
        return result;
    }

}
