package cn.com.glsx.supplychain.service.am;

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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.StatementCollectionStatusEnum;
import cn.com.glsx.supplychain.mapper.am.StatementSellCombileMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellGlwyMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellJbwyMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellRenewMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellRzfbMapper;
import cn.com.glsx.supplychain.mapper.am.StatementSellSplitMapper;
import cn.com.glsx.supplychain.model.am.StatementSellCombile;
import cn.com.glsx.supplychain.model.am.StatementSellGlwy;
import cn.com.glsx.supplychain.model.am.StatementSellGlwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellJbwy;
import cn.com.glsx.supplychain.model.am.StatementSellJbwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellRenew;
import cn.com.glsx.supplychain.model.am.StatementSellRenewImport;
import cn.com.glsx.supplychain.model.am.StatementSellRzfb;
import cn.com.glsx.supplychain.model.am.StatementSellRzfbImport;
import cn.com.glsx.supplychain.model.am.StatementSellSplit;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

/**
 * @ClassName StatementSellSpecialService
 * @Author 
 * @Param
 * @Date 2020/5/7 10:02
 * @Version 1.0
 **/
@Service
@Transactional
public class StatementSellSpecialService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StatementSellRzfbMapper sellRzfbMapper;
	@Autowired
	private StatementSellRenewMapper sellRenewMapper;
	@Autowired
	private StatementSellJbwyMapper sellJbwyMapper;
	@Autowired
	private StatementSellGlwyMapper sellGlwyMapper;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	@Autowired
	private StatementSellSplitMapper sellSplitMapper;
	@Autowired
	private StatementSellCombileMapper sellCombileMapper;
	
	
	public CheckImportDataVo CheckStatementSellGlwyImportData(List<StatementSellGlwyImport> importList){
		CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
		List<StatementSellGlwyImport> sucessList = new ArrayList<>();
		List<StatementSellGlwyImport> failedList = new ArrayList<>();
		//List<String> contractPaymentNos = new ArrayList<>();
		//Map<String,Integer> mapContractPaymentNos = new HashMap<>();
		/*for(StatementSellGlwyImport item:importList){
			if(StringUtils.isEmpty(item.getContractPaymentNo())){
				continue;
			}
			contractPaymentNos.add(item.getContractPaymentNo());
		}
		List<StatementSellGlwy> listSellGlwys = getStatementSellGlwyByContractPaymentNosDB(contractPaymentNos);
		if(null != listSellGlwys && !listSellGlwys.isEmpty()){
			for(StatementSellGlwy glwy:listSellGlwys){
				mapContractPaymentNos.put(glwy.getContractPaymentNo(), 1);
			}
		}*/
		Integer flag = null;
		for(StatementSellGlwyImport item:importList){
			if(StringUtils.isEmpty(item.getContractPaymentNo())){
				item.setResult("合同流水号不能为空");
				failedList.add(item);
				continue;
			}
			if(item.getGlwyInsureMoney() == null){
				item.setResult("广联无忧保费总额不能为空");
				failedList.add(item);
				continue;
			}
			if(item.getGlwySettleMoney() == null){
				item.setResult("广联无忧结算总额不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getFinancingMaturity())){
				item.setResult("融资期限不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getApplyDate())){
				item.setResult("申请日期不能为空");
				failedList.add(item);
				continue;
			}
			/*flag = mapContractPaymentNos.get(item.getContractPaymentNo());
			if(null != flag){
				item.setResult("合同流水号库中已经存在或者excel表格中重复");
				failedList.add(item);
				continue;
			}*/
			if(!SupplychainUtils.isTimeYMDHMSFormat(item.getApplyDate()) && !SupplychainUtils.isTimeYMDPFormat(item.getApplyDate())){
				item.setResult("申请日期格式不正确");
				failedList.add(item);
				continue;
			}
			sucessList.add(item);
		//	mapContractPaymentNos.put(item.getContractPaymentNo(), 1);
		}
		checkImportDataVo.setStatementSellGlwySuccessList(sucessList);
		checkImportDataVo.setStatementSellGlwyFailedList(failedList);
		return checkImportDataVo;
	}
	
	public Integer ImportStatementSellGlwyImportData(String operatorName, List<StatementSellGlwyImport> importList){
		List<StatementSellGlwy> listSellGlwys = new ArrayList<StatementSellGlwy>();
		StatementSellGlwy glwy = null;
		//Map<String,Integer> mapPaymentNoCount = new HashMap<>();
		//Integer count = null;
		StatementSellGlwyImport glwyImport = null;
		Map<String,StatementSellGlwyImport> mapGlwyImport = new HashMap<>();
		for(StatementSellGlwyImport item:importList){
			glwyImport = mapGlwyImport.get(item.getContractPaymentNo());
			if(null == glwyImport){
				mapGlwyImport.put(item.getContractPaymentNo(), item);
				continue;
			}
			glwyImport.setGlwySettleMoney(glwyImport.getGlwySettleMoney() + item.getGlwySettleMoney());
			glwyImport.setRentProfitMoney(glwyImport.getRentProfitMoney() + item.getRentProfitMoney());
		}
		
		for(StatementSellGlwyImport item:mapGlwyImport.values()){
			glwy = new StatementSellGlwy();
			glwy.setSettleCustomerCode(item.getSettleCustomerCode());
			glwy.setSettleCustomerName(item.getSettleCustomerName());
			glwy.setSaleGroupCode(item.getSaleGroupCode());
			glwy.setSaleGroupName(item.getSaleGroupName());
			glwy.setBelongCompany(item.getBelongCompany());
			glwy.setArea(item.getArea());
			glwy.setShopCode(item.getShopCode());
			glwy.setShopName(item.getShopName());
			glwy.setApplyNo(item.getApplyNo());
			glwy.setContractPaymentNo(item.getContractPaymentNo());
			glwy.setCustomerCode(item.getCustomerCode());
			glwy.setCustomerName(item.getCustomerName());
			glwy.setRentAttrible(item.getRentAttrible());
			glwy.setFinancialDes(item.getFinancialDes());
			glwy.setVinNo(item.getVinNo());
			glwy.setEngineNo(item.getEngineNo());
			glwy.setContractDate(SupplychainUtils.convertTimeYMDPFormat2Format(item.getContractDate()));
			glwy.setInsureYear(item.getInsureYear());
			glwy.setGlwyInsureMoney(item.getGlwyInsureMoney());
			glwy.setGlwySettleMoney(item.getGlwySettleMoney());
			glwy.setRentProfitMoney(item.getRentProfitMoney());
			glwy.setInsureAssureMoney(item.getInsureAssureMoney());
			glwy.setContractStatu(item.getContractStatu());
			glwy.setFinancingMaturity(item.getFinancingMaturity());
			glwy.setShopAttrib(item.getShopAttrib());
			glwy.setSettleStatu(item.getSettleStatu());
			glwy.setBillNo(item.getBillNo());
			glwy.setApplyDate(SupplychainUtils.convertTimeYMDPFormat2Format(item.getApplyDate()));
			glwy.setWorkOrder("RER" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
			glwy.setCreatedBy(operatorName);
			glwy.setCreatedDate(SupplychainUtils.getNowDate());
			glwy.setUpdatedBy(operatorName);
			glwy.setUpdatedDate(SupplychainUtils.getNowDate());
			glwy.setDeletedFlag("N");
			glwy.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
			/*count = mapPaymentNoCount.get(item.getContractPaymentNo());
			if(null == count){
				count = 0;
			}
			glwy.setFinancingMaturitySplit("12期");
			glwy.setContractDateSplit(SupplychainUtils.getYearPlusDate(glwy.getContractDate(), count));
			count++;
			mapPaymentNoCount.put(item.getContractPaymentNo(), count);*/
			listSellGlwys.add(glwy);
		}	
		return sellGlwyMapper.insertList(listSellGlwys);
	}
	
	public Page<StatementSellGlwy> pageStatementSellGlwy(int pageNum, int pageSize,StatementSellGlwy record){
		PageHelper.startPage(pageNum, pageSize);
		return sellGlwyMapper.pageStatementSellGlwy(record);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer delStatementSellGlwy(StatementSellGlwy record) throws RpcServiceException{
		
		List<StatementSellGlwy> listSellGlwy = getStatementSellGlwyByCustomerCodeDB(record.getSettleCustomerCode(),SupplychainUtils.getNowDateStringYM(record.getContractDate()));
		List<String> listWorkOrders = listSellGlwy.stream().map(StatementSellGlwy::getWorkOrder).collect(Collectors.toList());
		try{
			delStatementSellGlwyByWorkOrdersDB(listWorkOrders);
			delStatementSellSplitByWorkOrdersDB(listWorkOrders);
			delStatementSellCombileByWorkOrdersDB(listWorkOrders);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		return 0;
	}
	
	public CheckImportDataVo CheckStatementSellJbwyImportData(List<StatementSellJbwyImport> importList){
		CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
		List<StatementSellJbwyImport> sucessList = new ArrayList<>();
		List<StatementSellJbwyImport> failedList = new ArrayList<>();
		/*List<String> insureNos = new ArrayList<>();
		Map<String,Integer> mapInsureNos = new HashMap<>();
		for(StatementSellJbwyImport item:importList){
			if(StringUtils.isEmpty(item.getInsureNo())){
				continue;
			}
			insureNos.add(item.getInsureNo());
		}
		List<StatementSellJbwy> listSellJbwy = getStatementSellJbwyByInsureNosDB(insureNos);
		if(null != listSellJbwy && !listSellJbwy.isEmpty()){
			for(StatementSellJbwy item:listSellJbwy){
				mapInsureNos.put(item.getInsureNo(), 1);
			}
		}
		Integer flag = null;*/
		for(StatementSellJbwyImport item:importList){
			if(StringUtils.isEmpty(item.getInsureNo())){
				item.setResult("保单号不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getMoney()){
				item.setResult("价格不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getInsureDueTime())){
				item.setResult("保单期限不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getInsureReportPractice())){
				item.setResult("提报成功时间不能为空");
				failedList.add(item);
				continue;
			}
			/*flag = mapInsureNos.get(item.getInsureNo());
			if(null != flag){
				item.setResult("保单号库中已经存在或者excel表格中重复");
				failedList.add(item);
				continue;
			}*/
			if(!SupplychainUtils.isTimeYMDHMSFormat(item.getInsureReportPractice())){
				item.setResult("提报成功时间格式不正确");
				failedList.add(item);
				continue;
			}
			sucessList.add(item);
		//	mapInsureNos.put(item.getInsureNo(), 1);
		}
		checkImportDataVo.setStatementSellJbwySuccessList(sucessList);
		checkImportDataVo.setStatementSellJbwyFailedList(failedList);
		return checkImportDataVo;
	}
	
	public Integer ImportStatementSellJbwyImportData(String operatorName,List<StatementSellJbwyImport> importList){
		List<StatementSellJbwy> listSellJbwys = new ArrayList<StatementSellJbwy>();
		StatementSellJbwy jbwy = null;
		for(StatementSellJbwyImport item:importList){
			jbwy = new StatementSellJbwy();
			jbwy.setSettleCustomerCode(item.getSettleCustomerCode());
			jbwy.setSettleCustomerName(item.getSettleCustomerName());
			jbwy.setSaleGroupCode(item.getSaleGroupCode());
			jbwy.setSaleGroupName(item.getSaleGroupName());
			jbwy.setNo(item.getNo());
			jbwy.setInsureNo(item.getInsureNo());
			jbwy.setVechoPrice(item.getVechoPrice());
			jbwy.setVinNo(item.getVinNo());
			jbwy.setVechoUserName(item.getVechoUserName());
			jbwy.setDeviceSn(item.getDeviceSn());
			jbwy.setEngineNo(item.getEngineNo());
			jbwy.setDeviceType(item.getDeviceType());
			jbwy.setVersion(item.getVersion());
			jbwy.setInsureDueTime(item.getInsureDueTime());
			jbwy.setMoney(item.getMoney());
			jbwy.setInsureReportPractice(item.getInsureReportPractice());
			jbwy.setInsureStartDate(SupplychainUtils.getDateFromString(item.getInsureStartDate()));
			jbwy.setInsureEndDate(SupplychainUtils.getDateFromString(item.getInsureEndDate()));
			jbwy.setPrinceAgent(item.getPrinceAgent());
			jbwy.setCityAgent(item.getCityAgent());
			jbwy.setHandInMerchant(item.getHandInMerchant());
			jbwy.setShopName(item.getShopName());
			jbwy.setPreMerchant(item.getPreMerchant());
			jbwy.setArea(item.getArea());
			jbwy.setCertifiNo(item.getCertifiNo());
			jbwy.setMobile(item.getMobile());
			jbwy.setVechoBrand(item.getVechoBrand());
			jbwy.setVechoType(item.getVechoType());
			jbwy.setVechoSet(item.getVechoSet());
			jbwy.setVechoColor(item.getVechoColor());
			jbwy.setFirstMan(item.getFirstMan());
		//	jbwy.setInsureMaturity(item.getInsureMaturity());
			jbwy.setSellServerMane(item.getSellServerMane());
			jbwy.setJbwyServerMoney(item.getJbwyServerMoney());
			jbwy.setMileage(item.getMileage());
			jbwy.setInsureCompany(item.getInsureCompany());
			jbwy.setOperator(item.getOperator());
			jbwy.setWorkOrder("RER" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
			jbwy.setCreatedBy(operatorName);
			jbwy.setCreatedDate(SupplychainUtils.getNowDate());
			jbwy.setUpdatedBy(operatorName);
			jbwy.setUpdatedDate(SupplychainUtils.getNowDate());
			jbwy.setDeletedFlag("N");
			jbwy.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
			listSellJbwys.add(jbwy);
		}
		return sellJbwyMapper.insertList(listSellJbwys);
	}
	
	public Page<StatementSellJbwy> pageStatementSellJbwy(int pageNum, int pageSize,StatementSellJbwy record){
		PageHelper.startPage(pageNum, pageSize);
		return sellJbwyMapper.pageStatementSellJbwy(record);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer delStatementSellJbwy(StatementSellJbwy record) throws RpcServiceException{
		List<StatementSellJbwy> listSellJbwy = getStatementSellJbwyByCustomerCodeDB(record.getSettleCustomerCode(),SupplychainUtils.getNowDateStringYM(record.getInsureStartDate()));
		List<String> listWorkOrders = listSellJbwy.stream().map(StatementSellJbwy::getWorkOrder).collect(Collectors.toList());
		try{
			delStatementSellJbwyByWorkOrdersDB(listWorkOrders);
			delStatementSellSplitByWorkOrdersDB(listWorkOrders);
			delStatementSellCombileByWorkOrdersDB(listWorkOrders);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		return 0;
	}
	
	public CheckImportDataVo CheckStatementSellRenewImportData(List<StatementSellRenewImport> importList){
		CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
		List<StatementSellRenewImport> successList = new ArrayList<>();
		List<StatementSellRenewImport> failedList = new ArrayList<>();
		/*List<String> wexinOrderCodes = new ArrayList<>();
		List<String> merchantOrderCodes = new ArrayList<>();
		Map<String,Integer> mapWexinOrderCode = new HashMap<>();
		Map<String,Integer> mapMerchantOrderCode = new HashMap<>();
		for(StatementSellRenewImport item:importList){
			if(!StringUtils.isEmpty(item.getWeixinOrderNo())){
				wexinOrderCodes.add(item.getWeixinOrderNo());
			}
			if(!StringUtils.isEmpty(item.getMerchantOrderNo())){
				merchantOrderCodes.add(item.getMerchantOrderNo());
			}
		}
		List<StatementSellRenew> listSellRenews1 = getStatementSellRenewByWexinOrderNosDB(wexinOrderCodes);
		List<StatementSellRenew> listSellRenews2 = getStatementSellRenewByMerchantOrderNosDB(merchantOrderCodes);
		if(null != listSellRenews1){
			for(StatementSellRenew item:listSellRenews1){
				mapWexinOrderCode.put(item.getWeixinOrderNo(), 1);
			}
		}
		if(null != listSellRenews2){
			for(StatementSellRenew item:listSellRenews2){
				mapMerchantOrderCode.put(item.getMerchantOrderNo(), 1);
			}
		}
		Integer flag = null;*/
		for(StatementSellRenewImport item:importList){
			if(StringUtils.isEmpty(item.getWeixinOrderNo())){
				item.setResult("微信订单号不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getMerchantOrderNo())){
				item.setResult("商户订单号不能为空");
				failedList.add(item);
				continue;
			}		
			if(StringUtils.isEmpty(item.getTradeTime())){
				item.setResult("交易时间不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getShsettleOrderMoney()){
				item.setResult("应结订单金额不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getOrderMoney()){
				item.setResult("订单金额不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getReturnMoney()){
				item.setResult("退款金额不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getChargesMoney()){
				item.setResult("退款金额不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getFeeRate()){
				item.setResult("费率不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getMerchantName())){
				item.setResult("商品不能为空");
				failedList.add(item);
				continue;
			}
			/*flag = mapWexinOrderCode.get(item.getWeixinOrderNo());
			if(null != flag){
				item.setResult("微信订单号库中已经存在或者excel表格中重复");
				failedList.add(item);
				continue;
			}
			flag = mapMerchantOrderCode.get(item.getMerchantOrderNo());
			if(null != flag){
				item.setResult("商户订单号库中已经存在或者excel表格中重复");
				failedList.add(item);
				continue;
			}*/
			if(!SupplychainUtils.isTimeYMDHMSFormat(item.getTradeTime())){
				item.setResult("交易日期格式不正确");
				failedList.add(item);
				continue;
			}
			successList.add(item);
		//	mapWexinOrderCode.put(item.getWeixinOrderNo(), 1);
		//	mapMerchantOrderCode.put(item.getMerchantOrderNo(), 1);
		}
		checkImportDataVo.setStatementSellRenewSuccessList(successList);
		checkImportDataVo.setStatementSellRenewFailedList(failedList);
		return checkImportDataVo;
	}
	
	public Integer ImportStatementSellRenewImportData(String operatorName,List<StatementSellRenewImport> importList){
		List<StatementSellRenew> listSellRenews = new ArrayList<StatementSellRenew>();
		StatementSellRenew renew = null;
		for(StatementSellRenewImport item:importList){
			renew = new StatementSellRenew();
			renew.setTradeTime(SupplychainUtils.getDateFromString(item.getTradeTime()));			
			renew.setPubaccountId(item.getPubaccountId());
			renew.setMerchantCode(item.getMerchantCode());
			renew.setSpecialMerchantCode(item.getSpecialMerchantCode());
			renew.setDeviceSn(item.getDeviceSn());
			renew.setWeixinOrderNo(item.getWeixinOrderNo());
			renew.setMerchantOrderNo(item.getMerchantOrderNo());
			renew.setUserFlag(item.getUserFlag());
			renew.setTradeType(item.getTradeType());
			renew.setTradeStatu(item.getTradeStatu());
			renew.setPayBank(item.getPayBank());
			renew.setCurrencyType(item.getCurrencyType());
			renew.setShsettleOrderMoney(item.getShsettleOrderMoney());
			renew.setVouchersMoney(item.getVouchersMoney());
			renew.setWeixinReturnNo(item.getWeixinReturnNo());
			renew.setMerchantReturnNo(item.getMerchantReturnNo());
			renew.setReturnMoney(item.getReturnMoney());
			renew.setErchangeReturnMoney(item.getErchangeReturnMoney());
			renew.setReturnType(item.getReturnType());
			renew.setReturnStatu(item.getReturnStatu());
			renew.setMerchantName(item.getMerchantName());
			renew.setChargesMoney(item.getChargesMoney());
			renew.setFeeRate(item.getFeeRate());
			renew.setOrderMoney(item.getOrderMoney());
			renew.setApplyReturnMoney(item.getApplyReturnMoney());
			renew.setFeeRateRemark(item.getFeeRateRemark());
			renew.setWorkOrder("REW" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
			renew.setCreatedBy(operatorName);
			renew.setCreatedDate(SupplychainUtils.getNowDate());
			renew.setUpdatedBy(operatorName);
			renew.setUpdatedDate(SupplychainUtils.getNowDate());
			renew.setDeletedFlag("N");
			renew.setSettleCustomerCode(item.getSettleCustomerCode());
			renew.setSettleCustomerName(item.getSettleCustomerName());
			renew.setSaleGroupCode(item.getSaleGroupCode());
			renew.setSaleGroupName(item.getSaleGroupName());
			renew.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
			listSellRenews.add(renew);
		}
		return sellRenewMapper.insertList(listSellRenews);
	}
	
	public Page<StatementSellRenew> pageStatementSellRenew(int pageNum, int pageSize,StatementSellRenew record){
		PageHelper.startPage(pageNum, pageSize);
		return sellRenewMapper.pageStatementSellRenew(record);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer delStatementSellRenew(StatementSellRenew record){
		List<StatementSellRenew> listRenews = getStatementSellRenewByCustomerCodeDB(record.getSettleCustomerCode(),SupplychainUtils.getNowDateStringYM(record.getTradeTime()),record.getPubaccountId());
		List<String> listWorkOrders = listRenews.stream().map(StatementSellRenew::getWorkOrder).collect(Collectors.toList());
		try{
			delStatementSellRenewByWorkOrdersDB(listWorkOrders);
			delStatementSellSplitByWorkOrdersDB(listWorkOrders);
			delStatementSellCombileByWorkOrdersDB(listWorkOrders);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		return 0;
	}
	
	public CheckImportDataVo CheckStatementSellRzfbImportData(List<StatementSellRzfbImport> importList){
		CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
		List<StatementSellRzfbImport> successList = new ArrayList<>();
		List<StatementSellRzfbImport> failedList = new ArrayList<>();
		for(StatementSellRzfbImport item:importList){
			if(StringUtils.isEmpty(item.getRecordedData())){
				item.setResult("入账时间不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getServiceFee()){
				item.setResult("服务费不能为空");
				failedList.add(item);
				continue;
			}
			if(null == item.getIncome()){
				item.setResult("收入不能为空");
				failedList.add(item);
				continue;
			}
			if(StringUtils.isEmpty(item.getProductName())){
				item.setResult("商品名称不能为空");
				failedList.add(item);
				continue;
			}
			if(!SupplychainUtils.isTimeYMDHMSFormat(item.getRecordedData())){
				item.setResult("入账时间格式不正确");
				failedList.add(item);
				continue;
			}
			successList.add(item);
		}
		checkImportDataVo.setStatementSellRzfbSuccessList(successList);
		checkImportDataVo.setStatementSellRzfbFailedList(failedList);
		return checkImportDataVo;
	}
	
	public Integer ImportStatementSellRzfbImportData(String operatorName,List<StatementSellRzfbImport> importList){
		List<StatementSellRzfb> listSellRzfbs = new ArrayList<StatementSellRzfb>();
		StatementSellRzfb rzfb = null;
		for(StatementSellRzfbImport item:importList){
			rzfb = new StatementSellRzfb();
			rzfb.setSettleCustomerCode(item.getSettleCustomerCode());
			rzfb.setSettleCustomerName(item.getSettleCustomerName());
			rzfb.setSaleGroupCode(item.getSaleGroupCode());
			rzfb.setSaleGroupName(item.getSaleGroupName());
			rzfb.setWorkOrder("REZ" + SupplychainUtils.getDispatchOrderNumber(snowflakeWorker));
			rzfb.setAlipaySerialNumber(item.getAlipaySerialNumber());
			rzfb.setAlipayTransactionNumber(item.getAlipayTransactionNumber());
			rzfb.setMerchantOrderCode(item.getMerchantOrderCode());
			rzfb.setAccountType(item.getAccountType());
			rzfb.setIncome(item.getIncome());
			rzfb.setExpenditure(item.getExpenditure());
			rzfb.setAccountBalance(item.getAccountBalance());
			rzfb.setServiceFee(item.getServiceFee());
			rzfb.setPaymentChannel(item.getPaymentChannel());
			rzfb.setSignedProducts(item.getSignedProducts());
			rzfb.setCounterAccount(item.getCounterAccount());
			rzfb.setCounterName(item.getCounterName());
			rzfb.setBankOrderNumber(item.getBankOrderNumber());
			rzfb.setProductName(item.getProductName());
			rzfb.setRecordedData(SupplychainUtils.getDateFromString(item.getRecordedData()));
			rzfb.setCreatedBy(operatorName);
			rzfb.setCreatedDate(SupplychainUtils.getNowDate());
			rzfb.setUpdatedBy(operatorName);
			rzfb.setUpdatedDate(SupplychainUtils.getNowDate());
			rzfb.setDeletedFlag("N");
			rzfb.setStatus(StatementCollectionStatusEnum.STATEMENT_COLLECTION_NOT_SPLIT.getCode());
			listSellRzfbs.add(rzfb);
		}
		return sellRzfbMapper.insertList(listSellRzfbs);
	}
	
	public Page<StatementSellRzfb> pageStatementSellRzfb(int pageNum, int pageSize,StatementSellRzfb record){
		PageHelper.startPage(pageNum, pageSize);
		return sellRzfbMapper.pageStatementSellRzfb(record);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer delStatementSellRzfb(StatementSellRzfb record){
		List<StatementSellRzfb> listSellRzfb = this.getStatementSellRzfbByCustomerCodeDB(record.getSettleCustomerCode(), SupplychainUtils.getNowDateStringYM(record.getRecordedData()));
		List<String> listWorkOrders = listSellRzfb.stream().map(StatementSellRzfb::getWorkOrder).collect(Collectors.toList());
		try{
			delStatementSellRzfbByWorkOrdersDB(listWorkOrders);
			delStatementSellSplitByWorkOrdersDB(listWorkOrders);
			delStatementSellCombileByWorkOrdersDB(listWorkOrders);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
		return 0;
	}
	
	private List<StatementSellJbwy> getStatementSellJbwyByInsureNosDB(List<String> insureNos){
		if(null == insureNos || insureNos.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSellJbwy.class);
		example.createCriteria().andIn("insureNo", insureNos);
		return sellJbwyMapper.selectByExample(example);	
	}
	
	private List<StatementSellGlwy> getStatementSellGlwyByContractPaymentNosDB(List<String> contractPaymentNos){
		if(null == contractPaymentNos || contractPaymentNos.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSellGlwy.class);
		example.createCriteria().andIn("contractPaymentNo", contractPaymentNos);
		return sellGlwyMapper.selectByExample(example);
	}
	
	private List<StatementSellRenew> getStatementSellRenewByWexinOrderNosDB(List<String> wexinOrderNos){
		if(null == wexinOrderNos || wexinOrderNos.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSellRenew.class);
		example.createCriteria().andIn("weixinOrderNo", wexinOrderNos);
		return sellRenewMapper.selectByExample(example);
	}
	
	private List<StatementSellRenew> getStatementSellRenewByMerchantOrderNosDB(List<String> merchantNos){
		if(null == merchantNos || merchantNos.isEmpty()){
			return null;
		}
		Example example = new Example(StatementSellRenew.class);
		example.createCriteria().andIn("merchantOrderNo", merchantNos);
		return sellRenewMapper.selectByExample(example);
	}
	
	private List<StatementSellRzfb> getStatementSellRzfbByCustomerCodeDB(String settleCustomerCode,String recordDate){
		Example example = new Example(StatementSellRzfb.class);
		example.createCriteria().andEqualTo("settleCustomerCode", settleCustomerCode)
								.andLike("createdDate", recordDate+"%");
		return sellRzfbMapper.selectByExample(example);
	}
	
	private List<StatementSellGlwy> getStatementSellGlwyByCustomerCodeDB(String settleCustomerCode, String contractDate){
		Example example = new Example(StatementSellGlwy.class);
		example.createCriteria().andEqualTo("settleCustomerCode", settleCustomerCode)
								.andLike("createdDate", contractDate+"%");
		return sellGlwyMapper.selectByExample(example);						
	}
	
	private List<StatementSellRenew> getStatementSellRenewByCustomerCodeDB(String settleCustomerCode,String tradeDate,String weixinPublicAccountId){
		Example example = new Example(StatementSellRenew.class);
		if(StringUtils.isEmpty(weixinPublicAccountId)){
			example.createCriteria().andEqualTo("settleCustomerCode", settleCustomerCode)
			.andLike("createdDate", tradeDate+"%");
		}else{
			example.createCriteria().andEqualTo("settleCustomerCode", settleCustomerCode)
			.andLike("createdDate", tradeDate+"%")
			.andEqualTo("pubaccountId",weixinPublicAccountId);
		}
		return sellRenewMapper.selectByExample(example);	
	}
	
	private List<StatementSellJbwy> getStatementSellJbwyByCustomerCodeDB(String settleCustomerCode,String insureStartDate){
		Example example = new Example(StatementSellJbwy.class);
		example.createCriteria().andEqualTo("settleCustomerCode", settleCustomerCode)
								.andLike("createdDate", insureStartDate+"%");
		return sellJbwyMapper.selectByExample(example);
	}
	
	private Integer delStatementSellRzfbByWorkOrdersDB(List<String> workOrders){
		if(null == workOrders || workOrders.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellRzfb.class);
		example.createCriteria().andIn("workOrder", workOrders);
		return sellRzfbMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellGlwyByWorkOrdersDB(List<String> workOrders){
		if(null == workOrders || workOrders.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellGlwy.class);
		example.createCriteria().andIn("workOrder", workOrders);
		return sellGlwyMapper.deleteByExample(example);
	}
	
	public Integer delStatementSellRenewByWorkOrdersDB(List<String> workOrders){
		if(null == workOrders || workOrders.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellRenew.class);
		example.createCriteria().andIn("workOrder", workOrders);
		return sellRenewMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellJbwyByWorkOrdersDB(List<String> workOrders){
		if(null == workOrders || workOrders.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellJbwy.class);
		example.createCriteria().andIn("workOrder", workOrders);
		return sellJbwyMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellSplitByWorkOrdersDB(List<String> workOrders){
		if(null == workOrders || workOrders.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellSplit.class);
		example.createCriteria().andIn("workOrder", workOrders);
		return sellSplitMapper.deleteByExample(example);
	}
	
	private Integer delStatementSellCombileByWorkOrdersDB(List<String> workOrders){
		if(null == workOrders || workOrders.isEmpty()){
			return 0;
		}
		Example example = new Example(StatementSellCombile.class);
		example.createCriteria().andIn("workOrder", workOrders);
		return sellCombileMapper.deleteByExample(example);
	}
	
	
}
