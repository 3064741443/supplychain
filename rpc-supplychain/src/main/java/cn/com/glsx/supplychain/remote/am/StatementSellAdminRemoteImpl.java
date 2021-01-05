package cn.com.glsx.supplychain.remote.am;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.am.StatementSellCombile;
import cn.com.glsx.supplychain.model.am.StatementSellGlwy;
import cn.com.glsx.supplychain.model.am.StatementSellGlwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellJbwy;
import cn.com.glsx.supplychain.model.am.StatementSellJbwyImport;
import cn.com.glsx.supplychain.model.am.StatementSellRecon;
import cn.com.glsx.supplychain.model.am.StatementSellRenew;
import cn.com.glsx.supplychain.model.am.StatementSellRenewImport;
import cn.com.glsx.supplychain.model.am.StatementSellRzfb;
import cn.com.glsx.supplychain.model.am.StatementSellRzfbImport;
import cn.com.glsx.supplychain.model.am.StatementSellSplit;
import cn.com.glsx.supplychain.service.am.StatementSellService;
import cn.com.glsx.supplychain.service.am.StatementSellSpecialService;
import cn.com.glsx.supplychain.util.RequestVerifyService;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.ProductSplitDetailVo;
import cn.com.glsx.supplychain.vo.ProductSplitVo;
import cn.com.glsx.supplychain.vo.StatementSellSplitExcelVo;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author leiming
 * @version V1.0
 * @Title: StatementSellAdminRemoteImpl.java
 * @Description: 对账单-经销对账接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("StatementSellAdminRemoteImpl")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class StatementSellAdminRemoteImpl implements StatementSellAdminRemote{

	private static final Logger LOGGER = LoggerFactory.getLogger(StatementSellAdminRemoteImpl.class);
	
	@Autowired
	private StatementSellService statementSellService;
	@Autowired
	private StatementSellSpecialService statementSellSpecialService;
	
	//生成对账单草稿
	@Override
	public RpcResponse<StatementSellRecon> generaterStatementSellRecon(
			StatementSellRecon record) {
		LOGGER.info("StatementSellAdminRemoteImpl::generaterStatementSellRecon start record:{}",record);		
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getCreatedBy(), DefaultErrorEnum.PARAMETER_NULL, "record.getCreatedBy() must not be null");
			RpcAssert.assertNotNull(record.getChannel(), DefaultErrorEnum.PARAMETER_NULL, "record.getChannel() must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getReconTimeStart(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconTimeStart() must not be null");
			RpcAssert.assertNotNull(record.getReconTimeEnd(),DefaultErrorEnum.PARAMETER_NULL, "record.getReconTimeEnd() must not be null");								
			return RpcResponse.success(statementSellService.generaterStatementSellRecon(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<StatementSellRecon>> pageStatementSellRecon(
			RpcPagination<StatementSellRecon> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellRecon start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellService.pageStatementSellRecon(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<StatementSellRecon> getStatementSellRecon(
			StatementSellRecon record) {
		LOGGER.info("StatementSellAdminRemoteImpl::getStatementSellRecon start record:{}",record);		
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getReconCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconCode() must not be null");
			return RpcResponse.success(statementSellService.getStatementSellRecon(record));
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<StatementSellRecon> saveStatementSellRecon(
			StatementSellRecon record) {
		LOGGER.info("StatementSellAdminRemoteImpl::saveStatementSellRecon start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getUpdatedBy(), DefaultErrorEnum.PARAMETER_NULL, "record.getUpdatedBy() must not be null");
			RpcAssert.assertNotNull(record.getReconCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconCode() must not be null");
			RpcAssert.assertNotNull(record.getReconTimeStart(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconTimeStart() must not be null");
			RpcAssert.assertNotNull(record.getReconTimeEnd(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconTimeEnd() must not be null");
			RpcAssert.assertNotNull(record.getListReconDetail(), DefaultErrorEnum.PARAMETER_NULL, "record.getListReconDetail() must not be null");
			RpcAssert.assertNotNull(record.getListReconInstall(), DefaultErrorEnum.PARAMETER_NULL, "record.getListReconInstall() must not be null");
			RpcAssert.assertNotNull(record.getListReconSplit(), DefaultErrorEnum.PARAMETER_NULL, "record.getListReconSplit() must not be null");
			RpcAssert.assertIsTrue(!record.getListReconDetail().isEmpty(), DefaultErrorEnum.DATA_INVALID, "record.getListReconDetail().isEmpty() is not true");
			RpcAssert.assertIsTrue(!record.getListReconSplit().isEmpty(), DefaultErrorEnum.DATA_INVALID, "record.getListReconSplit().isEmpty() is not true");
			return RpcResponse.success(statementSellService.saveStatementSellRecon(record));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> delStatementSellRecon(
			StatementSellRecon record) {
		LOGGER.info("StatementSellAdminRemoteImpl::delStatementSellRecon start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getReconCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconCode() must not be null");
			return RpcResponse.success(statementSellService.delStatementSellRecon(record.getReconCode()));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> splitStatementSellRecon(
			StatementSellRecon record) {
		LOGGER.info("StatementSellAdminRemoteImpl::splitStatementSellRecon start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getCreatedBy(), DefaultErrorEnum.PARAMETER_NULL, "record.getCreatedBy() must not be null");
			RpcAssert.assertNotNull(record.getReconCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getReconCode() must not be null");
			return RpcResponse.success(statementSellService.splitStatementSellRecon(record.getCreatedBy(), record.getReconCode()));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<StatementSellSplit>> pageStatementSellSplit(
			RpcPagination<StatementSellSplit> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellSplit start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellService.pageStatementSellSplit(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<StatementSellSplitExcelVo>> listStatementSellSplit(
			StatementSellSplit record) {
		LOGGER.info("StatementSellAdminRemoteImpl::listStatementSellSplit start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			return RpcResponse.success(statementSellService.listStatementSellSplit(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<RpcPagination<StatementSellCombile>> pageStatementSellCombile(
			RpcPagination<StatementSellCombile> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellCombile start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellService.pageStatementSellCombile(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<StatementSellSplitExcelVo>> listStatementSellCombile(
			StatementSellCombile record) {
		LOGGER.info("StatementSellAdminRemoteImpl::listStatementSellCombile start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			return RpcResponse.success(statementSellService.listStatementSellCombile(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<ProductSplitVo>> listStatementSellProductSplit(
			StatementSellRecon record) {
		LOGGER.info("StatementSellAdminRemoteImpl::listStatementSellProductSplit start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getChannel(), DefaultErrorEnum.PARAMETER_NULL, "record.getChannel() must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getMerchantCode() must not be null");
			return RpcResponse.success(statementSellService.listStatementSellProductSplit(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<ProductSplitDetailVo>> listStatementSellProductSplitDetail(
			ProductSplitVo record) {
		LOGGER.info("StatementSellAdminRemoteImpl::listStatementSellProductSplit start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getProductCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getProductCode() must not be null");
			return RpcResponse.success(statementSellService.listStatementSellProductSplitDetail(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<CheckImportDataVo> CheckStatementSellGlwyImportData(
			List<StatementSellGlwyImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::CheckStatementSellGlwyImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "importList must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.CheckStatementSellGlwyImportData(importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> ImportStatementSellGlwyImportData(
			String operatorName, List<StatementSellGlwyImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::ImportStatementSellGlwyImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "importList must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.ImportStatementSellGlwyImportData(operatorName, importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<StatementSellGlwy>> pageStatementSellGlwy(
			RpcPagination<StatementSellGlwy> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellGlwy start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellSpecialService.pageStatementSellGlwy(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> delStatementSellGlwy(StatementSellGlwy record) {
		LOGGER.info("StatementSellAdminRemoteImpl::delStatementSellGlwy start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getSettleCustomerCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getSettleCustomerCode() must not be null");
			RpcAssert.assertNotNull(record.getContractDate(), DefaultErrorEnum.PARAMETER_NULL, "record.getContractDate() must not be null");
			return RpcResponse.success(statementSellSpecialService.delStatementSellGlwy(record));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<CheckImportDataVo> CheckStatementSellJbwyImportData(
			List<StatementSellJbwyImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::CheckStatementSellJbwyImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "importList must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.CheckStatementSellJbwyImportData(importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> ImportStatementSellJbwyImportData(
			String operatorName, List<StatementSellJbwyImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::ImportStatementSellJbwyImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "importList must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.ImportStatementSellJbwyImportData(operatorName, importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<StatementSellJbwy>> pageStatementSellJbwy(
			RpcPagination<StatementSellJbwy> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellJbwy start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellSpecialService.pageStatementSellJbwy(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> delStatementSellJbwy(StatementSellJbwy record) {
		LOGGER.info("StatementSellAdminRemoteImpl::delStatementSellJbwy start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getSettleCustomerCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getSettleCustomerCode() must not be null");
			RpcAssert.assertNotNull(record.getInsureStartDate(), DefaultErrorEnum.PARAMETER_NULL, "record.getInsureStartDate() must not be null");
			return RpcResponse.success(statementSellSpecialService.delStatementSellJbwy(record));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<CheckImportDataVo> CheckStatementSellRenewImportData(
			List<StatementSellRenewImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::CheckStatementSellRenewImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.CheckStatementSellRenewImportData(importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> ImportStatementSellRenewImportData(
			String operatorName, List<StatementSellRenewImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::ImportStatementSellRenewImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "importList must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.ImportStatementSellRenewImportData(operatorName, importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<StatementSellRenew>> pageStatementSellRenew(
			RpcPagination<StatementSellRenew> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellRenew start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellSpecialService.pageStatementSellRenew(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> delStatementSellRenew(StatementSellRenew record) {
		LOGGER.info("StatementSellAdminRemoteImpl::delStatementSellRenew start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getSettleCustomerCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getSettleCustomerCode() must not be null");
			RpcAssert.assertNotNull(record.getTradeTime(), DefaultErrorEnum.PARAMETER_NULL, "record.getInsureStartDate() must not be null");
			return RpcResponse.success(statementSellSpecialService.delStatementSellRenew(record));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<CheckImportDataVo> CheckStatementSellRzfbImportData(
			List<StatementSellRzfbImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::CheckStatementSellRzfbImportData start importList.size()",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.CheckStatementSellRzfbImportData(importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> ImportStatementSellRzfbImportData(
			String operatorName, List<StatementSellRzfbImport> importList) {
		LOGGER.info("StatementSellAdminRemoteImpl::ImportStatementSellRzfbImportData start importList.size()",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(statementSellSpecialService.ImportStatementSellRzfbImportData(operatorName, importList));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<StatementSellRzfb>> pageStatementSellRzfb(
			RpcPagination<StatementSellRzfb> pagination) {
		LOGGER.info("StatementSellAdminRemoteImpl::pageStatementSellRzfb start pagination:{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(statementSellSpecialService.pageStatementSellRzfb(pagination.getPageNum(),pagination.getPageSize(),pagination.getCondition())));
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> delStatementSellRzfb(StatementSellRzfb record) {
		LOGGER.info("StatementSellAdminRemoteImpl::delStatementSellRzfb start record:{}",record);
		try{
			RpcAssert.assertNotNull(record, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertNotNull(record.getSettleCustomerCode(), DefaultErrorEnum.PARAMETER_NULL, "record.getSettleCustomerCode() must not be null");
			RpcAssert.assertNotNull(record.getRecordedData(), DefaultErrorEnum.PARAMETER_NULL, "record.getInsureStartDate() must not be null");
			return RpcResponse.success(statementSellSpecialService.delStatementSellRzfb(record));
		}catch(RpcServiceException e){
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}	
	}

}
