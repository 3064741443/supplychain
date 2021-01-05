package cn.com.glsx.supplychain.remote.am;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.config.DataFormatProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.FinanceSettleTypeEnum;
import cn.com.glsx.supplychain.enums.ReasonInvalidDeviceNum;
import cn.com.glsx.supplychain.enums.StatementFinanceStatusEnum;
import cn.com.glsx.supplychain.model.am.KcCustomerRelation;
import cn.com.glsx.supplychain.model.am.KcWarehouseRelation;
import cn.com.glsx.supplychain.model.am.StatementFinance;
import cn.com.glsx.supplychain.model.am.StatementFinanceCombile;
import cn.com.glsx.supplychain.model.am.StatementFinanceExport;
import cn.com.glsx.supplychain.model.am.StatementFinanceExrule;
import cn.com.glsx.supplychain.model.am.StatementFinanceImport;
import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;
import cn.com.glsx.supplychain.service.am.KcWarehouseRelationService;
import cn.com.glsx.supplychain.service.am.StatementFinanceCombileService;
import cn.com.glsx.supplychain.service.am.StatementFinanceExruleService;
import cn.com.glsx.supplychain.service.am.StatementFinanceService;
import cn.com.glsx.supplychain.service.am.StatementFinanceSplitService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.StatementFinanceSplitExcelVo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.glsx.cloudframework.core.util.BeanUtils;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leiming
 * @version V1.0
 * @Title: StatementFinanceAdminRemote.java
 * @Description: 对账单-金融风控接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("StatementFinanceAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class StatementFinanceAdminRemoteImpl implements StatementFinanceAdminRemote{
    private static final Logger LOGGER = LoggerFactory.getLogger(StatementFinanceAdminRemoteImpl.class);

    @Autowired
    private DataFormatProperty dataFormatProperty;
    @Autowired
    private StatementFinanceService statementFinanceService;
    @Autowired
    private StatementFinanceSplitService statementFinanceSplitService;
    @Autowired
    private StatementFinanceCombileService statementFinanceCombileService;
    @Autowired
    private StatementFinanceExruleService statementFinanceExruleService;
    @Autowired
    private IMaterialDubboService iMaterialDubboService;
    @Autowired
    private KcWarehouseRelationService warehouseRelationService;
    
    @Override
	public RpcResponse<RpcPagination<KcCustomerRelation>> listKcCustomerRelation(
			RpcPagination<KcCustomerRelation> pagination) {
    	RpcResponse<RpcPagination<KcCustomerRelation>> result;
    	try{
    		 RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
             RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
             RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
    		result = RpcResponse.success(RpcPagination.copyPage(warehouseRelationService.pageCustomerRelations(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
    	}catch (Exception e){
    		LOGGER.error(e.getMessage(), e);
    		result = RpcResponse.error(e);
    	}
		return result;
	}
    
    @Override
	public RpcResponse<KcWarehouseRelation> getWarehouseRelationByCode(
			KcWarehouseRelation record) {
    	RpcResponse<KcWarehouseRelation> result;
    	try{
    		RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
    		result = RpcResponse.success(warehouseRelationService.getWarehouseRelationByCode(record.getWarehouseCode()));
    	}catch (Exception e){
    		LOGGER.error(e.getMessage(), e);
    		result = RpcResponse.error(e);
    	}
		return result;
	}
    
    @Override
	public RpcResponse<KcCustomerRelation> getCustomerRelationByCode(
			KcCustomerRelation record) {
    	RpcResponse<KcCustomerRelation> result;
    	try{
    		RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
    		result = RpcResponse.success(warehouseRelationService.getCustomerRelationByCode(record.getCustomerCode()));
    	}catch (Exception e){
    		LOGGER.error(e.getMessage(), e);
    		result = RpcResponse.error(e);
    	}
		return result;
	}
    
    @Override
	public RpcResponse<Map<String, StatementFinanceExrule>> getStatementFinanceExruleMap(
			StatementFinanceExrule record) {
    	RpcResponse<Map<String, StatementFinanceExrule>> result;
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			result = RpcResponse.success(statementFinanceExruleService.getStatementFinanceExruleMap(record));
		}catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
	}

    @Override
    public RpcResponse<RpcPagination<StatementFinance>> listStatementFinance(RpcPagination<StatementFinance> pagination) {
        RpcResponse<RpcPagination<StatementFinance>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            Page<StatementFinance> pageFinance = statementFinanceService.listStatementFinance(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition());
            for(StatementFinance finance:pageFinance){
            	finance.setSourceOne(this.convertSourceDB2VO(finance.getSourceOne()));
            	finance.setSourceTwo(this.convertSourceDB2VO(finance.getSourceTwo()));
            	finance.setIsSure(this.convertYN2YesNo(finance.getIsSure()));
            	finance.setSettleByStages(this.convertYN2YesNo(finance.getSettleByStages()));
            	finance.setSettleInstall(this.convertYN2YesNo(finance.getSettleInstall()));
            }
            result = RpcResponse.success(RpcPagination.copyPage(pageFinance));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<StatementFinance> getStatementFinanceByid(Long id) {
        try {
            RpcAssert.assertNotNull(id, DefaultErrorEnum.PARAMETER_NULL, "id must not be null");
            return RpcResponse.success(statementFinanceService.getStatementFinanceByid(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> add(StatementFinance statementFinance) {
        try {
            RpcAssert.assertNotNull(statementFinance, DefaultErrorEnum.PARAMETER_NULL, "statementFinance must not be null");
            return RpcResponse.success(statementFinanceService.add(statementFinance));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateById(StatementFinance statementFinance) {
        try {
            RpcAssert.assertNotNull(statementFinance, DefaultErrorEnum.PARAMETER_NULL, "statementFinance must not be null");
            return RpcResponse.success(statementFinanceService.updateById(statementFinance));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> deleteStatementFinanceByDate(StatementFinance statementFinance) {
        try {
            RpcAssert.assertNotNull(statementFinance, DefaultErrorEnum.PARAMETER_NULL, "statementFinance must not be null");
            RpcAssert.assertNotNull(statementFinance.getTime(), DefaultErrorEnum.PARAMETER_NULL, "statementFinance.getTime must not be null");
            RpcAssert.assertNotNull(statementFinance.getCustomerCode(), DefaultErrorEnum.PARAMETER_NULL, "statementFinance.getCustomerCode must not be null");
            return RpcResponse.success(statementFinanceService.deleteStatementFinanceByDate(statementFinance));
        } catch (RpcServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }
    
    private boolean isWorkTypeBDISH(String workType){
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getName()))
    		return true;
    	return false;
    }
    
    @Override
    public RpcResponse<CheckImportDataVo> checkImportStatementFinance(List<StatementFinanceImport> statementFinanceImportList){
    	CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
    	
    	if (StringUtil.isEmpty(statementFinanceImportList) || statementFinanceImportList.size() == 0) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        if (statementFinanceImportList.size() > 20000) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
        }
        List<StatementFinanceImport> sucessList = new ArrayList<>();
        List<StatementFinanceImport> failedList = new ArrayList<>();
        try{
        	for (StatementFinanceImport item : statementFinanceImportList){
        		LOGGER.info("StatementFinanceAdminRemoteImpl::checkImportStatementFinance handle data item=" + item.toString());
        		 //验证月份格式是否正确
        		if(!SupplychainUtils.isTimeYMFormat(item.getTime())){
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_STATEMENT_FINANCE_TIME_INVALID.getValue());
        			failedList.add(item);
        			continue;
        		}
        		//验证所有字段是否为空
        		if(isEmptyStatementFinanceImport(item)) {               
        			item.setFailDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT.getValue());
        			failedList.add(item);                 
                    continue;
                }
        		//校验导入字段长度是否合法
                if (StatementFinanceImportCheckLength(item)) {
                	item.setFailDesc(ReasonInvalidDeviceNum.INVALID_DEVICE_COLUMN_LENGTH.getValue());
        			failedList.add(item);                 
                    continue;     
                }
                //检验从设备数据是否为空(从设备数据有一个不为空，其它均不能为空)
                if(!this.isWorkTypeBDISH(item.getWorkType())){
                	if (twoDeviceCheck(item.getDeviceNumberTwo(), item.getDeviceTypeTwo(), item.getSourceTwo(), item.getMaterialCodeTwo())) {
                		item.setFailDesc(ReasonInvalidDeviceNum.INVALID_STATEMENT_FINANCE_TWO_DEVICE_REEOR.getValue());
            			failedList.add(item);                 
                        continue;         
                    }
                }
                sucessList.add(item);
        	}
        	LOGGER.info("StatementFinanceAdminRemoteImpl::checkImportStatementFinance: check ok !");
        	checkImportDataVo.setStatementFinanceSuccessList(sucessList);
            checkImportDataVo.setStatementFinanceFailedList(failedList);
            return RpcResponse.success(checkImportDataVo);
        }
        catch (Exception e) {
        	LOGGER.info(e.getMessage(),e);
            return RpcResponse.error(e);
        }
    }

    @Override
	public RpcResponse<RpcPagination<StatementFinanceCombile>> listStatementFinanceCombile(
			RpcPagination<StatementFinanceCombile> pagination) {
    	RpcResponse<RpcPagination<StatementFinanceCombile>> result;
    	try{
    		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
    		RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(statementFinanceCombileService.listStatementFinanceCombile(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
    	}catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
	}

    @Override
    public RpcResponse<RpcPagination<StatementFinanceSplit>> listStatementFinanceSplit(RpcPagination<StatementFinanceSplit> pagination) {
        RpcResponse<RpcPagination<StatementFinanceSplit>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(statementFinanceSplitService.listStatementFinanceSplit(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(StatementFinanceSplit statementFinanceSplit) {
        try {
            RpcAssert.assertNotNull(statementFinanceSplit, DefaultErrorEnum.PARAMETER_NULL, "statementFinanceSplit must not be null");
            return RpcResponse.success(statementFinanceSplitService.add(statementFinanceSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateById(StatementFinanceSplit statementFinanceSplit) {
        try {
            RpcAssert.assertNotNull(statementFinanceSplit, DefaultErrorEnum.PARAMETER_NULL, "statementFinanceSplit must not be null");
            return RpcResponse.success(statementFinanceSplitService.updateById(statementFinanceSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<StatementFinanceSplitExcelVo>> exportStatementFinanceSplitExit(StatementFinanceSplit statementFinanceSplit) {
        RpcResponse<List<StatementFinanceSplitExcelVo>> result;
        try {
            RpcAssert.assertNotNull(statementFinanceSplit, DefaultErrorEnum.PARAMETER_NULL, "statementFinanceSplit must not be null");
            result =  RpcResponse.success(statementFinanceSplitService.exportStatementFinanceSplitExit(statementFinanceSplit));
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
        return result;
    }
    
    @Override
	public RpcResponse<List<StatementFinanceSplitExcelVo>> exportStatementFinanceCombileExit(
			StatementFinanceCombile statementFinanceCombile) {
    	RpcResponse<List<StatementFinanceSplitExcelVo>> result;
    	try{
    		RpcAssert.assertNotNull(statementFinanceCombile, DefaultErrorEnum.PARAMETER_NULL, "statementFinanceCombile must not be null");
    		result = RpcResponse.success(statementFinanceCombileService.exportStatementFinanceCombileExit(statementFinanceCombile));
    	}catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
        return result;
	}

    @Override
    public RpcResponse<Integer> importStatementFinanceImport(String operatorName, List<StatementFinanceImport> statementFinanceImportList) {
        Integer result = 0;
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM");
        if (StringUtils.isEmpty(operatorName) || StringUtils.isEmpty(statementFinanceImportList)) {
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
        }
        List<StatementFinance>statementFinanceList = new ArrayList<>();
        for(StatementFinanceImport list : statementFinanceImportList){
            Date date = null;
            String newTime = list.getTime();
            try {
                date = sp.parse(newTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }            
            StatementFinance statementFinance = new StatementFinance();
            statementFinance.setId(null);
            statementFinance.setTime(date);
            statementFinance.setWorkOrder(list.getWorkOrder());
            statementFinance.setDeviceNumberOne(list.getDeviceNumberOne());
            statementFinance.setDeviceNumberTwo(list.getDeviceNumberTwo());
            statementFinance.setMaterialCodeOne(list.getMaterialCodeOne());
            statementFinance.setMaterialCodeTwo(list.getMaterialCodeTwo());           
            statementFinance.setSourceOne(this.convertSourceVO2DB(list.getSourceOne()));
            statementFinance.setSourceTwo(this.convertSourceVO2DB(list.getSourceTwo()));
            statementFinance.setDeviceTypeOne(list.getDeviceTypeOne());
            statementFinance.setDeviceTypeTwo(list.getDeviceTypeTwo());
            statementFinance.setDeviceQuantity(list.getDeviceQuantity());
            statementFinance.setGpsType(list.getGpsType());
            statementFinance.setServiceTime(list.getServiceTime());
            statementFinance.setSum(list.getSum());
            statementFinance.setSaleGroupCode(list.getSaleCode());
            statementFinance.setSaleGroupName(list.getSaleName());
            statementFinance.setCustomerCode(list.getCustomerCode());
            statementFinance.setCustomerName(list.getCustomerName());
            statementFinance.setWarehouseCode(list.getWarehouseCode());
            statementFinance.setWarehouseName(list.getWarehouseName());
            statementFinance.setStatus(StatementFinanceStatusEnum.STATEMENT_FINANCE_NOT_SPLIT.getCode());
            statementFinance.setCreatedBy(operatorName);
            statementFinance.setCreatedDate(new Date());
            statementFinance.setUpdatedBy(operatorName);
            statementFinance.setUpdatedDate(new Date());
            statementFinance.setDeletedFlag("N");
            statementFinance.setHardwareCustomerCode(list.getHardwareCustomerCode());
            statementFinance.setHardwareCustomerName(list.getHardwareCustomerName());
            statementFinance.setServiceCustomerCode(list.getServiceCustomerCode());
            statementFinance.setServiceCustomerName(list.getServiceCustomerName());          
            statementFinance.setSettleByStages(this.convertYesNo2YN(list.getSettleByStages()));
            statementFinance.setIsSure(this.convertYesNo2YN(list.getIsSure()));          
            statementFinance.setSureTime(list.getSureTime());
            statementFinance.setWorkType(this.convertSettleTypeVO2DB(list.getWorkType()));
            if(!StringUtils.isEmpty(list.getContractDate())){
            	statementFinance.setContractDate(SupplychainUtils.getDateFromStringYMD(list.getContractDate()));
            }
            statementFinance.setMaterialCode(list.getMaterialCode());
            statementFinance.setSettleInstall(this.convertYesNo2YN(list.getSettleInstall()));
            statementFinanceList.add(statementFinance);
        }
        statementFinanceService.batchAddStatementFinance(statementFinanceList);
        return RpcResponse.success(result);
    }
    
    private String convertSettleTypeVO2DB(String settleTypeName){
    	String result = "";
    	if(StringUtils.isEmpty(settleTypeName)){
    		return result;
    	}
    	if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getCode();
		else if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getCode();
		else if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getCode();
		else if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getCode();
		else if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getCode();
		else if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_N.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_N.getCode();
		else if(settleTypeName.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_C.getName()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_C.getCode();
    	return result;
    }
    
    private String convertSettleTypeDB2VO(String settleType){
    	String result = "";
    	if(StringUtils.isEmpty(settleType)){
    		return result;
    	}
    	if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getName();
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getName();
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getName();
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getName();
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getName();
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_N.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_N.getName();
		else if(settleType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_C.getCode()))
			return FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_C.getName();
    	return result;
    }
       
    private String convertSourceDB2VO(String source){
    	String result = "";
    	if(StringUtils.isEmpty(source)){
    		return result;
    	}
    	if(source.equals("Y")){
    		return "有源";
    	}
    	if(source.equals("N"))
    	{
    		return "无源";
    	}
    	return result;
    }
    
    private String convertSourceVO2DB(String source){
    	String result = "";
    	if(StringUtils.isEmpty(source)){
    		return result;
    	}
    	if(source.equals("有源")){
    		return "Y";
    	}
    	if(source.equals("无源")){
    		return "N";
    	}
    	return result;
    }
    
    private String convertYesNo2YN(String source){
    	String result = "N";
    	if(StringUtils.isEmpty(source)){
    		return result;
    	}
    	if(source.equals("是")){
    		return "Y";
    	}
    	if(source.equals("否")){
    		return "N";
    	}
    	return result;
    }
    
    private String convertYN2YesNo(String source){
    	String result = "否";
    	if(StringUtils.isEmpty(source)){
    		return result;
    	}
    	if(source.equals("Y")){
    		return "是";
    	}
    	if(source.equals("N")){
    		return "否";
    	}
    	return result;
    }

    /**
     * 校验所有的导入字段是否为空
     */
    private Boolean isEmptyStatementFinanceImport(StatementFinanceImport statementFinanceImport) {

    	if (StringUtils.isEmpty(statementFinanceImport))
            return true;
    	if(StringUtils.isEmpty(statementFinanceImport.getWorkType()))
    		return true;
    	if (StringUtils.isEmpty(statementFinanceImport.getTime()))
            return true;
    	if (StringUtils.isEmpty(statementFinanceImport.getWorkOrder()))
            return true; 
    	if (StringUtils.isEmpty(statementFinanceImport.getWarehouseCode()))
            return true;
        if (StringUtils.isEmpty(statementFinanceImport.getWarehouseName()))
            return true;
        if (StringUtils.isEmpty(statementFinanceImport.getCustomerCode()))
        	return true;
        if (StringUtils.isEmpty(statementFinanceImport.getCustomerName()))
        	return true;
        if (StringUtils.isEmpty(statementFinanceImport.getHardwareCustomerCode()))
        	return true;
        if (StringUtils.isEmpty(statementFinanceImport.getHardwareCustomerName()))
        	return true;
        if (StringUtils.isEmpty(statementFinanceImport.getServiceCustomerCode()))
        	return true;
        if (StringUtils.isEmpty(statementFinanceImport.getServiceCustomerName()))
        	return true;
    	if(!this.isWorkTypeBDISH(statementFinanceImport.getWorkType())){
            if (StringUtils.isEmpty(statementFinanceImport.getDeviceNumberOne()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getSourceOne()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getDeviceTypeOne()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getMaterialCodeOne()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getDeviceQuantity()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getGpsType()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getServiceTime()))
                return true;
            if (StringUtils.isEmpty(statementFinanceImport.getSum()))
                return true;                
    	}else{
    		if(StringUtils.isEmpty(statementFinanceImport.getMaterialCode()))
    			return true;
    	}
        return false;
    }

    /**
     * 校验导入字段长度是否合法
     */
    private Boolean StatementFinanceImportCheckLength(StatementFinanceImport statementFinanceImport) {
    
    	if (statementFinanceImport.getWorkOrder().length() > 32)
            return true;
    	if(!this.isWorkTypeBDISH(statementFinanceImport.getWorkType())){
    		if (statementFinanceImport.getDeviceNumberOne().length() > dataFormatProperty.getMaxImeiLen())
                return true;
            if (statementFinanceImport.getDeviceNumberTwo().length() > dataFormatProperty.getMaxImeiLen())
                return true;
    	}
        return false;
    }

    /**
     * 校验从设备数据是否为空(从设备数据有一个不为空，其它均不能为空)
     */
    private Boolean twoDeviceCheck(String deviceNumberTwo,String deviceTypeTwo,String sourceTwo,String materialCodeTwo){
        if(!StringUtils.isEmpty(deviceNumberTwo) || !StringUtils.isEmpty(deviceTypeTwo)
                || !StringUtils.isEmpty(sourceTwo) || !StringUtils.isEmpty(materialCodeTwo)){
            if(StringUtils.isEmpty(deviceNumberTwo))
                return true;
            if(StringUtils.isEmpty(deviceTypeTwo))
                return true;
            if(StringUtils.isEmpty(sourceTwo))
                return true;
            if(StringUtils.isEmpty(materialCodeTwo))
                return true;
        }
        return false;
    }

    /**
     * 校验导入月份格式是否正确
     */
    private Boolean validDate(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = (Date)formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

}
