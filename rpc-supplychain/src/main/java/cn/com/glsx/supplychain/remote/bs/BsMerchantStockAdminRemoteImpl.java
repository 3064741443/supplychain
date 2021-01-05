package cn.com.glsx.supplychain.remote.bs;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.SupplyRequest;
import cn.com.glsx.supplychain.model.bs.BsMerchantStock;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockCainou;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockRetn;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSell;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSett;
import cn.com.glsx.supplychain.service.DeviceTypeService;
import cn.com.glsx.supplychain.service.bs.BsMerchantStockCainouService;
import cn.com.glsx.supplychain.service.bs.BsMerchantStockRetnService;
import cn.com.glsx.supplychain.service.bs.BsMerchantStockSellService;
import cn.com.glsx.supplychain.service.bs.BsMerchantStockService;
import cn.com.glsx.supplychain.service.bs.BsMerchantStockSettService;
import cn.com.glsx.supplychain.util.RequestVerifyService;

import com.alibaba.dubbo.config.annotation.Service;


/**
 * @author liuquan
 * @version V1.0
 * @Title: BsMerchantStockAdminRemote.java
 * @Description: 商户库存调拨相关接口(针对运营后台以及进销存管理后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("BsMerchantStockAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class BsMerchantStockAdminRemoteImpl implements BsMerchantStockAdminRemote{

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockAdminRemoteImpl.class);
	@Autowired
	private BsMerchantStockCainouService merchantCainouService;
	@Autowired
	private BsMerchantStockService merchantStockService;
	@Autowired
	private BsMerchantStockSellService merchantStockSellService;
	@Autowired
	private BsMerchantStockRetnService merchantStockRetnSerivce;
	@Autowired
	private BsMerchantStockSettService merchantStockSettSerivce;
	@Autowired
    private RequestVerifyService requestVerifyService;
	@Autowired
	private DeviceTypeService deviceTypeService;
	@Autowired
	private SupplyChainExternalService externalService;
	
	@Override
	public RpcResponse<RpcPagination<BsMerchantStock>> pageMerchantStocks(SupplyRequest request,
			RpcPagination<BsMerchantStock> pagination) {
		
		RpcResponse<RpcPagination<BsMerchantStock>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStocks start request:{},pagination:{}",request,pagination);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(pagination,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(RpcPagination.copyPage(merchantStockService.pageMerchantStocks(pagination)));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}	
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStocks end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsMerchantStock> statMerchantStocks(SupplyRequest request,
			BsMerchantStock record) {
		
		RpcResponse<BsMerchantStock> result;
		logger.info("BsMerchantStockAdminRemoteImpl::statMerchantStocks start request:{}, record:{}",request,record);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(record,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(merchantStockService.getStatMerchantStocks(record));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::statMerchantStocks end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<RpcPagination<BsMerchantStockSell>> pageMerchantStockSell(SupplyRequest request,
			RpcPagination<BsMerchantStockSell> pagination) {
		
		RpcResponse<RpcPagination<BsMerchantStockSell>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockSell start request:{}, pagination:{}",request,pagination);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(pagination,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMaterialCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMaterialCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(RpcPagination.copyPage(merchantStockSellService.pageMerchantStockSell(pagination)));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockSell end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<RpcPagination<BsMerchantStockRetn>> pageMerchantStockRetn(
			SupplyRequest request, RpcPagination<BsMerchantStockRetn> pagination) {
		
		RpcResponse<RpcPagination<BsMerchantStockRetn>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockRetn start request:{}, pagination:{}",request,pagination);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(pagination,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMaterialCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMaterialCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(RpcPagination.copyPage(merchantStockRetnSerivce.pageMerchantStockRetn(pagination)));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockRetn end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<RpcPagination<BsMerchantStockSett>> pageMerchantStockSett(
			SupplyRequest request, RpcPagination<BsMerchantStockSett> pagination) {
		
		RpcResponse<RpcPagination<BsMerchantStockSett>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockSett start request:{}, pagination:{}",request,pagination);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(pagination,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMaterialCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMaterialCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(RpcPagination.copyPage(merchantStockSettSerivce.pageMerchantStockSett(pagination)));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockSett end result:{}",result);
		return result;
	
	}


	@Override
	public RpcResponse<List<BsMerchantStock>> listMerchantStocks(SupplyRequest request,
			BsMerchantStock record) {
		
		RpcResponse<List<BsMerchantStock>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::listMerchantStocks start request:{},record:{}",request,record);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(record,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(merchantStockService.listMerchantStocks(record));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::listMerchantStocks end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<RpcPagination<BsMerchantStockCainou>> pageMerchantStockCainou(SupplyRequest request,
			RpcPagination<BsMerchantStockCainou> pagination) {
		
		RpcResponse<RpcPagination<BsMerchantStockCainou>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockCainou start request:{},pagination:{}",request,pagination);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(pagination,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(RpcPagination.copyPage(merchantCainouService.pageMerchantStockCainou(pagination)));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::pageMerchantStockCainou end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsMerchantStockCainou> addMerchantStockCainou(SupplyRequest request,
			BsMerchantStockCainou record) {
		
		RpcResponse<BsMerchantStockCainou> result;
		logger.info("BsMerchantStockAdminRemoteImpl::addMerchantStockCainou start request:{},record:{}",request,record);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(record,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getToMerchantCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getToMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getFromMerchantCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getFromMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getToMerchantName(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getToMerchantName() must not be null");
			RpcAssert.assertNotNull(record.getFromMerchantName(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getFromMerchantName() must not be null");
			RpcAssert.assertNotNull(record.getMaterialCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getMaterialName(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
			RpcAssert.assertNotNull(record.getDeliNum(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeliNum() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(merchantCainouService.addMerchantStockCainou(record));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::addMerchantStockCainou end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsMerchantStockCainou> updateMerchantStockCainou(SupplyRequest request,
			BsMerchantStockCainou record) {
		
		RpcResponse<BsMerchantStockCainou> result;
		logger.info("BsMerchantStockAdminRemoteImpl::updateMerchantStockCainou start request:{},record:{}",request,record);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertNotNull(record.getId(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getFromMerchantCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getFromMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getFromMerchantName(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getFromMerchantName() must not be null");
			RpcAssert.assertNotNull(record.getToMerchantCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getToMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getToMerchantName(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getToMerchantName() must not be null");
			RpcAssert.assertNotNull(record.getMaterialCode(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
			RpcAssert.assertNotNull(record.getMaterialName(),ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(merchantCainouService.updateMerchantStock(record));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::updateMerchantStockCainou end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<List<DeviceType>> listMerchantStockDeviceType(
			SupplyRequest request,DeviceType record) {
		
		RpcResponse<List<DeviceType>> result;
		logger.info("BsMerchantStockAdminRemoteImpl::listMerchantDeviceType start request:{}",request);
		try
		{
			RpcAssert.assertNotNull(request,ErrorCodeEnum.ERRCODE_INVALID_PARAM,"request must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(request),ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			if(null == record)
			{
				record = new DeviceType();
			}
			result = RpcResponse.success(deviceTypeService.listDeviceType(record));
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		logger.info("BsMerchantStockAdminRemoteImpl::listMerchantDeviceType end result:{}",result);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> listMerchantStockMerchantInfo(
			HashMap<String, Object> condition, Integer targetPage,
			Integer pageSize) {
	
		try
		{
			List list = externalService.listMerchantInfo(condition, targetPage, pageSize);
			return RpcResponse.success(list);
		}
		catch (RpcServiceException e)
		{
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	
	

}
