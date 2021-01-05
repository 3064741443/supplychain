package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.service.JXCMTBsAddressService;
import cn.com.glsx.supplychain.jxc.service.JXCMTOrderDispatchService;
import cn.com.glsx.supplychain.jxc.service.JXCMTOrderService;
import cn.com.glsx.supplychain.jxc.service.JXCMTOrderSignSupplyService;

import com.alibaba.dubbo.config.annotation.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ql
 * @version V1.0
 * @Title: JxcOrderRemote.java
 * @Description: 进销存重构商户总订单，子订单,发货单接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Component("JxcOrderRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class JxcOrderRemoteImpl implements JxcOrderRemote{
	private static final Logger logger = LoggerFactory.getLogger(JxcOrderRemoteImpl.class);
	@Autowired
	private JXCMTOrderService jxcmtOrderService;
	@Autowired
	private JXCMTOrderDispatchService jxcmtOrderDispatchService;
	@Autowired
	private JXCMTBsAddressService jxcmtBsAddressService;
	@Autowired
	private JXCMTOrderSignSupplyService jxcmtOrderSignSuppyService;
	@Override
	public RpcResponse<Integer> submitJxcMerchantOrder(
			JXCMTMerchantOrderSubmitDTO record) {
		logger.info("JxcOrderRemoteImpl::submitJxcMerchantOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getAddressDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto() must not be null");
			RpcAssert.assertNotNull(record.getHopeTime(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getHopeTime() must not be null");
			RpcAssert.assertNotNull(record.getListProductSplitDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListProductSplitDto() must not be null");
			RpcAssert.assertIsTrue(!record.getListProductSplitDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListProductSplitDto() must not empty");
			return RpcResponse.success(jxcmtOrderService.submitJxcMerchantOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<Integer> updateJxcMerchantOrder(
			JXCMTMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::updateJxcMerchantOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMoOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(jxcmtOrderService.updateJxcMerchantOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<Integer> cancelJxcMerchantOrder(
			JXCMTMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::cancelJxcMerchantOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMoOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(jxcmtOrderService.cancelJxcMerchantOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<JXCMTMerchantOrderDTO> getJxcMerchantOrder(
			JXCMTMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::getJxcMerchantOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMoOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMoOrderCode() must not be null");
			return RpcResponse.success(jxcmtOrderService.getJxcMerchantOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<RpcPagination<JXCMTBsMerchantOrderDTO>> pageBsMerchantOrderJXC(
			RpcPagination<JXCMTBsMerchantOrderDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageBsMerchantOrderJXC pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMerchantCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMerchantCode() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderService.pageBsMerchantOrderJXC(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderDTO>> exportPurchaseOrder(JXCMTBsMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::exportPurchaseOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode must not be null");
			return RpcResponse.success(jxcmtOrderService.exportPurchaseOrder(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<RpcPagination<JXCMTBsMerchantOrderDTO>> pageBsMerchantOrderBSS(
			RpcPagination<JXCMTBsMerchantOrderDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageBsMerchantOrderBSS pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderService.pageBsMerchantOrderBSS(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	
	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderExportDTO>> exportBsMerchantOrderBSS(
			JXCMTBsMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::exportBsMerchantOrderBSS record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(jxcmtOrderService.exportBsMerchantOrderBSS(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	
	@Override
	public RpcResponse<RpcPagination<JXCMTSpMerchantOrderDTO>> pageBsMerchantOrderBSP(
			RpcPagination<JXCMTSpMerchantOrderDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageBsMerchantOrderBSP pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderService.pageBsMerchantOrderBSP(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	
	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderExportBspDTO>> exportBsMerchantOrderExportBSP(
			JXCMTSpMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::exportBsMerchantOrderExportBSP record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(jxcmtOrderService.exportBsMerchantOrderExportBSP(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	
	@Override
	public RpcResponse<JXCMTBsMerchantOrderDetailDTO> getBsOrder(
			JXCMTBsMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::getBsOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			return RpcResponse.success(jxcmtOrderService.getBsOrder(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<Integer> rebackBsOrder(JXCMTBsMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::rebackBsOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			return RpcResponse.success(jxcmtOrderService.rebackBsOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> rebackSpOrder(
			JXCMTBsMerchantOrderRebackDTO record) {
		logger.info("JxcOrderRemoteImpl::rebackSpOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			return RpcResponse.success(jxcmtOrderService.rebackSpOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> recallSpOrder(
			JXCMTBsMerchantOrderRecallDTO record) {
		logger.info("JxcOrderRemoteImpl::recallSpOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			return RpcResponse.success(jxcmtOrderService.recallSpOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> finishSpOrder(
			JXCMTBsMerchantOrderFinishDTO record) {
		logger.info("JxcOrderRemoteImpl::finishSpOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			return RpcResponse.success(jxcmtOrderService.finishSpOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<Integer> checkBsOrder(JXCMTBsOrderCheckDTO record) {
		logger.info("JxcOrderRemoteImpl::checkBsOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getCheckMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getCheckMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
			RpcAssert.assertNotNull(record.getTotalCheck(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTotalCheck() must not be null");
			RpcAssert.assertNotNull(record.getTotalCheck(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTotalCheck() must not be null");
			return RpcResponse.success(jxcmtOrderService.checkBsOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<Integer> updateBsOrder(JXCMTBsOrderCheckDTO record) {
		logger.info("JxcOrderRemoteImpl::updateBsOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getCheckMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getCheckMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
			RpcAssert.assertNotNull(record.getTotalCheck(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTotalCheck() must not be null");
			RpcAssert.assertNotNull(record.getTotalCheck(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTotalCheck() must not be null");
			return RpcResponse.success(jxcmtOrderService.updateBsOrder(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> listBsMerchantOrderDetail(
			JXCMTBsMerchantOrderDTO record) {
		logger.info("JxcOrderRemoteImpl::listBsMerchantOrderDetail record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			return RpcResponse.success(jxcmtOrderService.listBsMerchantOrderDetail(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> listVehicleInformation() {
		logger.info("JxcOrderRemoteImpl::listVehicleInformation::{}");
		try{
			return RpcResponse.success(jxcmtOrderService.listVehicleInformation());
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderSignDTO>> generatorMerchantOrderSign(
			JXCMTBsMerchantOrderGenSignDTO record) {
		logger.info("JxcOrderRemoteImpl::generatorMerchantOrderSign record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getDocumentNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDocumentNo() must not be null");
			RpcAssert.assertNotNull(record.getListMerchantOrders(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrders() must not be null");
			RpcAssert.assertIsTrue(!record.getListMerchantOrders().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrders() must not empty");
			return RpcResponse.success(jxcmtOrderService.generatorMerchantOrderSign(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> listSpOrderVehicle(
			JXCMTSpOrderVehicleQueryDTO record) {
		logger.info("JxcOrderRemoteImpl::listSpOrderVehicle record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrder() must not be null");
			RpcAssert.assertIsTrue(!record.getListMerchantOrder().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrder() must not be empty");
			return RpcResponse.success(jxcmtOrderService.listSpOrderVehicle(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<Integer> dispatchSpOrderScanYSingle(
			JXCMTSpOrderDispatchScanYDTO record) {
		logger.info("JxcOrderRemoteImpl::dispatchSpOrderScanYSingle record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getSendMerchantNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendMerchantNo() must not be null");
			RpcAssert.assertNotNull(record.getSendMerchantName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendMerchantName() must not be null");
			RpcAssert.assertNotNull(record.getDeviceId(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeviceId() must not be null");
			RpcAssert.assertNotNull(record.getDeviceName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeviceName() must not be null");
	//		RpcAssert.assertNotNull(record.getPackageOne(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPackageOne() must not be null");
			RpcAssert.assertNotNull(record.getMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
			RpcAssert.assertNotNull(record.getAddress(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddress() must not be null");
			RpcAssert.assertNotNull(record.getContacts(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getContacts() must not be null");
			RpcAssert.assertNotNull(record.getMobile(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMobile() must not be null");	
			RpcAssert.assertNotNull(record.getListMerchantOrderWarehouseDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be null");	
			RpcAssert.assertIsTrue(!record.getListMerchantOrderWarehouseDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be empty");
			return RpcResponse.success(jxcmtOrderDispatchService.dispatchSpOrderScanYSingle(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> dispatchSpOrderScanYBatch(
			JXCMTSpOrderDispatchScanYDTO record) {
		logger.info("JxcOrderRemoteImpl::dispatchSpOrderScanYBatch record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");			
			RpcAssert.assertNotNull(record.getDeviceId(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeviceId() must not be null");
			RpcAssert.assertNotNull(record.getDeviceName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeviceName() must not be null");
		//	RpcAssert.assertNotNull(record.getPackageOne(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPackageOne() must not be null");
			RpcAssert.assertNotNull(record.getMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");		
			RpcAssert.assertNotNull(record.getListMerchantOrderWarehouseDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be null");	
			RpcAssert.assertIsTrue(!record.getListMerchantOrderWarehouseDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be empty");
			return RpcResponse.success(jxcmtOrderDispatchService.dispatchSpOrderScanYBatch(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> dispatchSpOrderScanNSingle(
			JXCMTSpOrderDispatchScanNDTO record) {
		logger.info("JxcOrderRemoteImpl::dispatchSpOrderScanYSingle record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getSendMerchantNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendMerchantNo() must not be null");
			RpcAssert.assertNotNull(record.getSendMerchantName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendMerchantName() must not be null");			
			RpcAssert.assertNotNull(record.getMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
			RpcAssert.assertNotNull(record.getAddress(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddress() must not be null");
			RpcAssert.assertNotNull(record.getContacts(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getContacts() must not be null");
			RpcAssert.assertNotNull(record.getMobile(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMobile() must not be null");	
			RpcAssert.assertNotNull(record.getListMerchantOrderWarehouseDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be null");	
			RpcAssert.assertIsTrue(!record.getListMerchantOrderWarehouseDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be empty");
			return RpcResponse.success(jxcmtOrderDispatchService.dispatchSpOrderScanNSingle(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> dispatchSpOrderScanNBatch(
			JXCMTSpOrderDispatchScanNDTO record) {
		logger.info("JxcOrderRemoteImpl::dispatchSpOrderScanYBatch record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");			
			RpcAssert.assertNotNull(record.getMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");		
			RpcAssert.assertNotNull(record.getListMerchantOrderWarehouseDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be null");	
			RpcAssert.assertIsTrue(!record.getListMerchantOrderWarehouseDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantOrderWarehouseDto() must not be empty");
			return RpcResponse.success(jxcmtOrderDispatchService.dispatchSpOrderScanNBatch(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> dispatchSpOrderDirect(
			JXCMTSpOrderDispatchDirectDTO record) {
		logger.info("JxcOrderRemoteImpl::dispatchSpOrderDirect record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantOrder(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantOrder() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsCompany(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsCompany() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsNum(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNum() must not be null");
			RpcAssert.assertNotNull(record.getSendNums(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendNums() must not be null");
			RpcAssert.assertNotNull(record.getSendTime(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendTime() must not be null");
			RpcAssert.assertNotNull(record.getWarehouseId(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getWarehouseId() must not be null");
			return RpcResponse.success(jxcmtOrderDispatchService.dispatchSpOrderDirect(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<RpcPagination<JXCMTOrderInfoDTO>> pageDispatchOrder(
			RpcPagination<JXCMTOrderInfoDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageDispatchOrder pagination::{},record::{}",pagination,pagination.getCondition());
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderDispatchService.pageDispatchOrder(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<JXCMTOrderInfoDTO>> exportDispatchOrder(JXCMTOrderInfoDTO record) {
		logger.info("JxcOrderRemoteImpl::exportDispatchOrder record::{},record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			return RpcResponse.success(jxcmtOrderDispatchService.exportDispatchOrder(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<JXCMTOrderInfoSignDTO>> pageSignOrders(
			RpcPagination<JXCMTOrderInfoSignDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageSignOrders pagination::{},record::{}",pagination,pagination.getCondition());
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderDispatchService.pageSignOrders(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<RpcPagination<JXCMTBsBillDTO>> pageSignBillNumber(
			RpcPagination<JXCMTBsBillDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageSignBillNumber pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getWarehouseId(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getWarehouseId() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderSignSuppyService.pageSignBillNumber(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<Integer> genBillNumber(JXCMTGenBillNumberDTO record) {
		logger.info("JxcOrderRemoteImpl::genBillNumber record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getUserName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getUserName() must not be null");
			RpcAssert.assertNotNull(record.getListOrderInfoSigns(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListOrderInfoSigns() must not be null");
			RpcAssert.assertIsTrue(!record.getListOrderInfoSigns().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListOrderInfoSigns() must not be empty");
			return RpcResponse.success(jxcmtOrderDispatchService.genBillNumber(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<Integer> genBillNumberRecord(
			JXCMTBsMerchantOrderSignSuplyDTO record) {
		logger.info("JxcOrderRemoteImpl::genBillNumberRecord record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getBillSignNumber(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getBillSignNumber must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode must not be null");
			RpcAssert.assertNotNull(record.getLogisticsNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNo must not be null");
			return RpcResponse.success(jxcmtOrderSignSuppyService.genBillNumberRecord(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<RpcPagination<JXCMTOrderInfoDetailDTO>> pageDispatchOrderDetail(
			JXCMTOrderInfoDetailDTO record) {
		logger.info("JxcOrderRemoteImpl::pageDispatchOrderDetail record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode must not be null");
			RpcAssert.assertNotNull(record.getPageNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageNo must not be null");
			RpcAssert.assertNotNull(record.getPageSize(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageSize must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderDispatchService.pageDispatchOrderDetail(record)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<RpcPagination<JXCMTOrderInfoMerchantDTO>> pageDispatchOrderMerchant(
			RpcPagination<JXCMTOrderInfoMerchantDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageDispatchOrderMerchant pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(jxcmtOrderDispatchService.pageDispatchOrderMerchant(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<JXCMTGhMerchantOrderConfigDTO> getGhMerchantOrderConfig(
			JXCMTGhMerchantOrderConfigDTO record) {
		logger.info("JxcOrderRemoteImpl::getGhMerchantOrderConfig record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode() must not be null");
			return RpcResponse.success(jxcmtOrderDispatchService.getGhMerchantOrderConfig(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

}
