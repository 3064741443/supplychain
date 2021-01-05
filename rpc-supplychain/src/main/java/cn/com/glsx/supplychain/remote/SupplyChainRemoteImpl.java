package cn.com.glsx.supplychain.remote;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.ExsysDeviceStatu;
import cn.com.glsx.supplychain.model.ExsysDispatchLog;
import cn.com.glsx.supplychain.model.ExsysIdentify;
import cn.com.glsx.supplychain.model.ExsysOrderInfo;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.RequestLog;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.Logistics;
import cn.com.glsx.supplychain.service.AttribManagerSevice;
import cn.com.glsx.supplychain.service.DeviceTypeService;
import cn.com.glsx.supplychain.service.ExsysDispatchLogService;
import cn.com.glsx.supplychain.service.ExsysIdentifyService;
import cn.com.glsx.supplychain.service.SupplyChainRemoteService;
import cn.com.glsx.supplychain.service.WareHouseInfoService;
import cn.com.glsx.supplychain.util.RequestVerifyService;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

import com.alibaba.dubbo.config.annotation.Service;

@Component("supplyChainRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000 )
public class SupplyChainRemoteImpl implements SupplyChainRemote{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SupplyChainRemoteService supplyChainRemoteService;
	
	@Autowired
	private WareHouseInfoService wareHouseInfoService;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@Autowired
	private AttribManagerSevice attribManagerService;
	
	@Autowired
    private RequestVerifyService requestVerifyService;
	
	@Autowired
	private ExsysIdentifyService exsysIdentifyService;
	
	@Autowired
	private ExsysDispatchLogService exsysDispatchLogService;
	
	@Override
	public RpcResponse<UserInfo> login(UserInfo userInfo) {
		
		RpcResponse<UserInfo> result;
		logger.info("SupplyChainRemoteImpl::login start userInfo:{}",userInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(userInfo.getPassword(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getPassword() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.login(userInfo));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::login end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<List<DeviceType>> listDeviceType(UserInfo userInfo,DeviceType deviceType)
	{
		RpcResponse<List<DeviceType>> result;
		logger.info("SupplyChainRemoteImpl::getDeviceType start userInfo:{}",userInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(deviceTypeService.listDeviceType(deviceType));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::login end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<List<AttribMana>> listAttribManaCodes(UserInfo userInfo,AttribMana record) 
	{
		RpcResponse<List<AttribMana>> result;
		logger.info("SupplyChainRemoteImpl::listAttribMana start userInfo:{},record:{}",userInfo,record);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			return RpcResponse.success(attribManagerService.listAttribManaCodes(record));
		}
		catch (RpcServiceException e) 
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::listAttribMana end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<AttribMana> getAttribManaByManaCode(UserInfo userInfo,AttribMana record) 
	{
		RpcResponse<AttribMana> result;
		logger.info("SupplyChainRemoteImpl::getAttribManaByManaCode start userInfo:{},record:{}",userInfo,record);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAttribCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAttribCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(attribManagerService.getAttribManaByManaCode(record.getAttribCode()));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getAttribManaByManaCode end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<DeviceInfo> scannerDeviceInfo(UserInfo userInfo,DeviceInfo record) {
		
		RpcResponse<DeviceInfo> result;
		logger.info("SupplyChainRemoteImpl::scannerDeviceInfo start userInfo:{},record:{}",userInfo,record);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAttribCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAttribCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.scannerDeviceInfo(userInfo, record));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::scannerDeviceInfo end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> cancelDeviceInfo(UserInfo userInfo, DeviceInfo record)
	{
		 RpcResponse<Integer> result;
		 logger.info("SupplyChainRemoteImpl::cancelDeviceInfo start userInfo:{},record:{}",userInfo,record);
		 try
		 {
			 RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			 RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			 RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			 RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			 result = RpcResponse.success(supplyChainRemoteService.cancelDeviceInfo(userInfo, record));
		 }
		 catch(RpcServiceException e)
		 {
			 logger.error(e.getMessage(),e);
			 result = RpcResponse.error(e);
		 }
		 logger.info("SupplyChainRemoteImpl::cancelDeviceInfo end result:{}",result);
		 return result;
	}
	
	@Override
	public RpcResponse<RpcPagination<DeviceInfo>> pageStatAttrib(UserInfo userInfo,RpcPagination<DeviceInfo> pagination)
	{
		RpcResponse<RpcPagination<DeviceInfo>> result;
		logger.info("SupplyChainRemoteImpl::pageStatAttrib start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(pagination, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			
			RpcPagination<DeviceInfo> rpcPagination = RpcPagination.copyPage(supplyChainRemoteService.pageStatAttrib(userInfo, pagination));
			rpcPagination.setCondition(pagination.getCondition());
			rpcPagination.setPageNum(pagination.getPageNum());
			rpcPagination.setPages(pagination.getPages());
			rpcPagination.setPageSize(pagination.getPageSize());
			rpcPagination.setTotal(pagination.getTotal());
			result = RpcResponse.success(rpcPagination);
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::pageStatAttrib end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<RpcPagination<DeviceInfo>> pageStatAttribDeviceInfos(UserInfo userInfo,RpcPagination<DeviceInfo> pagination)
	{
		RpcResponse<RpcPagination<DeviceInfo>> result;
		logger.info("SupplyChainRemoteImpl::pageStatAttribDeviceInfos start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(pagination, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			
			RpcPagination<DeviceInfo> rpcPagination = RpcPagination.copyPage(supplyChainRemoteService.pageStatAttribDeviceInfos(userInfo, pagination));
			rpcPagination.setCondition(pagination.getCondition());
			rpcPagination.setPageNum(pagination.getPageNum());
			rpcPagination.setPages(pagination.getPages());
			rpcPagination.setPageSize(pagination.getPageSize());
			rpcPagination.setTotal(pagination.getTotal());
			result = RpcResponse.success(rpcPagination);
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::pageStatAttribDeviceInfos end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<List<DeviceInfo>> listExportAttribDeviceInfos(UserInfo userInfo,DeviceInfo record)
	{
		RpcResponse<List<DeviceInfo>> result;
		logger.info("SupplyChainRemoteImpl::listExportAttribDeviceInfos start userInfo:{},record:{}",userInfo,record);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAttribCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAttribCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.listExportAttribDeviceInfos(userInfo, record));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::pageStatAttribDeviceInfos end");
		return result;
	}
	
	@Override
	public RpcResponse<CheckImportDataVo> excelDeviceInfoInCheck(UserInfo userInfo,List<DeviceInfo> deviceInfoList)
	{
		RpcResponse<CheckImportDataVo> result;
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoInCheck start userInfo:{},deviceInfoList.size:{}",userInfo,deviceInfoList.size());
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(deviceInfoList, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.excelDeviceInfoInCheck(userInfo, deviceInfoList));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoInCheck end");
		return result;
	}
	
	@Override
	public RpcResponse<CheckImportDataVo> excelDeviceInfoOutCheck(UserInfo userInfo,OrderInfo orderInfo,List<DeviceInfo> deviceInfoList)
	{
		RpcResponse<CheckImportDataVo> result;
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoOutCheck start userInfo:{},orderInfo:{},deviceInfoList.size:{}",userInfo,orderInfo,deviceInfoList.size());
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(deviceInfoList, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(orderInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"orderInfo must not be null");
			RpcAssert.assertNotNull(orderInfo.getOrderCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"orderInfo.getOrderCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.excelDeviceInfoOutCheck(userInfo,orderInfo,deviceInfoList));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoOutCheck end");
		return result;
	}
	
	@Override
	public RpcResponse<CheckImportDataVo> excelDeviceInfoImport(UserInfo userInfo,List<DeviceInfo> deviceInfoList)
	{
		RpcResponse<CheckImportDataVo> result;
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoImport start userInfo:{},deviceInfoList.size:{}",userInfo,deviceInfoList.size());
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(deviceInfoList, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.excelDeviceInfoImport(userInfo, deviceInfoList));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoImport end");
		return result;
	}
	
	@Override
	public RpcResponse<CheckImportDataVo> excelDeviceInfoOutImport(UserInfo userInfo,OrderInfo orderInfo,List<DeviceInfo> deviceInfoList)
	{
		RpcResponse<CheckImportDataVo> result;
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoOutImport start userInfo:{},orderInfo:{},deviceInfoList.size:{}",userInfo,orderInfo,deviceInfoList.size());
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(deviceInfoList, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(orderInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"orderInfo must not be null");
			RpcAssert.assertNotNull(orderInfo.getOrderCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"orderInfo.getOrderCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.excelDeviceInfoOutImport(userInfo,orderInfo,deviceInfoList));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::excelDeviceInfoOutImport end");
		return result;
	}
	
	@Override
	public RpcResponse<List<OrderInfo>> listOrderInfo(UserInfo userInfo,OrderInfo record)
	{
		RpcResponse<List<OrderInfo>> result;
		logger.info("SupplyChainRemoteImpl::listOrderInfo start userInfo:{},record:{}",userInfo,record);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.listOrderInfo(userInfo, record));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::listOrderInfo end");
		return result;
	}
	
	@Override
	public RpcResponse<RpcPagination<OrderInfo>> pageOrderInfo(UserInfo userInfo,RpcPagination<OrderInfo> pagination)
	{
		RpcResponse<RpcPagination<OrderInfo>> result;
		logger.info("SupplyChainRemoteImpl::pageOrderInfo start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(pagination, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			
			result = RpcResponse.success(RpcPagination.copyPage(supplyChainRemoteService.pageOrderInfo(userInfo, pagination)));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::pageOrderInfo end");
		return result;
	}
	
	@Override
	public RpcResponse<RpcPagination<OrderInfoDetail>> pageOrderInfoDetail(UserInfo userInfo,RpcPagination<OrderInfoDetail> pagination)
	{
		RpcResponse<RpcPagination<OrderInfoDetail>> result;
		logger.info("SupplyChainRemoteImpl::pageOrderInfoDetail start userInfo:{},pagination:{}",userInfo,pagination);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(pagination, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(RpcPagination.copyPage(supplyChainRemoteService.pageOrderInfoDetail(userInfo, pagination)));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::pageOrderInfoDetail end");
		return result;
	}
	
	@Override
	public RpcResponse<OrderInfo> getOrderInfoByOrderCode(UserInfo userInfo,OrderInfo record)
	{
		RpcResponse<OrderInfo> result;
		logger.info("SupplyChainRemoteImpl::getOrderInfoByOrderCode start userInfo:{},record:{}",userInfo,record);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOrderCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOrderCode() must not be null");
			result = RpcResponse.success(supplyChainRemoteService.getOrderInfoByOrderCode(userInfo, record));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getOrderInfoByOrderCode end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> deliveryDeviceInfo(UserInfo userInfo, OrderInfo orderInfo, DeviceInfo deviceInfo,Logistics logistics,Address address)
	{
		RpcResponse<Integer> result;
		logger.info("SupplyChainRemoteImpl::deliveryDeviceInfo start userInfo:{},orderInfo:{},deviceInfo:{},logistics:{},address:{}",userInfo,orderInfo,deviceInfo,logistics,address);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(orderInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"orderInfo must not be null");
			RpcAssert.assertNotNull(orderInfo.getOrderCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"orderInfo.getOrderCode() must not be null");
			RpcAssert.assertNotNull(deviceInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"deviceInfo must not be null");
			RpcAssert.assertNotNull(logistics, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"logistics must not be null");
			RpcAssert.assertNotNull(address, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"address must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.deliveryDeviceInfo(userInfo, orderInfo, deviceInfo, logistics, address));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::deliveryDeviceInfo end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> canceDeliveryDeviceInfo(UserInfo userInfo,DeviceInfo deviceInfo)
	{
		RpcResponse<Integer> result;
		logger.info("SupplyChainRemoteImpl::canceDeliveryDeviceInfo start userInfo:{},deviceInfo:{}",userInfo,deviceInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(deviceInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"deviceInfo must not be null");
			RpcAssert.assertNotNull(deviceInfo.getSn(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"deviceInfo.getSn must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.canceDeliveryDeviceInfo(userInfo,deviceInfo));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::canceDeliveryDeviceInfo end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<List<WareHouseInfo>> getWareHouseInfoList(
			UserInfo userInfo, WareHouseInfo wareHouseInfo) 
	{
		RpcResponse<List<WareHouseInfo>> result;
		logger.info("SupplyChainRemoteImpl::getWareHouseInfoList userInfo:{},wareHouseInfo:{}",userInfo,wareHouseInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(wareHouseInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"wareHouseInfo must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(wareHouseInfoService.listWareHouseInfo(wareHouseInfo));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getWareHouseInfoList end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<WareHouseInfo> getWareHouseInfo(UserInfo userInfo,WareHouseInfo wareHouseInfo) 
	{
		RpcResponse<WareHouseInfo> result;
		logger.info("SupplyChainRemoteImpl::getWareHouseInfo userInfo:{},wareHouseInfo:{}",userInfo,wareHouseInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(userInfo.getUserName(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo.getUserName() must not be null");
			RpcAssert.assertNotNull(wareHouseInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"wareHouseInfo must not be null");
			RpcAssert.assertNotNull(wareHouseInfo.getId(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"wareHouseInfo.getId() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(wareHouseInfoService.getWarehouseById(wareHouseInfo.getId()));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getWareHouseInfo end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> dispatchDeviceInfo(UserInfo userInfo,
			WareHouseInfo toHouseInfo, DeviceInfo deviceInfo,
			AttribMana attribMana) {
		
		RpcResponse<Integer> result;
		logger.info("SupplyChainRemoteImpl::dispatchDeviceInfo userInfo:{},toHouseInfo:{},deviceInfo:{},attribMana:{}",userInfo,toHouseInfo,deviceInfo,attribMana);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(toHouseInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"toHouseInfo must not be null");
			RpcAssert.assertNotNull(toHouseInfo.getId(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"toHouseInfo.getId must not be null");
			RpcAssert.assertNotNull(deviceInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"deviceInfo must not be null");
			RpcAssert.assertNotNull(attribMana, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"attribMana must not be null");
			RpcAssert.assertNotNull(attribMana.getAttribCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"attribMana.getAttribCode() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.dispatchDeviceInfo(userInfo, toHouseInfo, deviceInfo, attribMana));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::dispatchDeviceInfo end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<AttribMana> getAttribManaByDeviceSn(UserInfo userInfo,
			DeviceInfo deviceInfo) {
		
		RpcResponse<AttribMana> result;
		logger.info("SupplyChainRemoteImpl::getAttribManaByDeviceSn userInfo:{},deviceInfo:{}",userInfo,deviceInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(deviceInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"deviceInfo must not be null");
			RpcAssert.assertNotNull(deviceInfo.getSn(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"deviceInfo.getSn() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.getAttribManaByDeviceSn(deviceInfo));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getAttribManaByDeviceSn end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<ExsysIdentify> getExsysIdentifyBySystemFlag(
			UserInfo userInfo, ExsysIdentify exsysIdentify) {
		
		RpcResponse<ExsysIdentify> result;
		logger.info("SupplyChainRemoteImpl::getExsysIdentifyBySystemFlag userInfo:{},exsysIdentify:{}",userInfo,exsysIdentify);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(exsysIdentify, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysIdentify must not be null");
			RpcAssert.assertNotNull(exsysIdentify.getSystemFlag(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysIdentify.getSystemFlag() must not be null");
			RpcAssert.assertNotNull(exsysIdentify.getType(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysIdentify.getType() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(exsysIdentifyService.getExsysIdentifyBySystemFlag(exsysIdentify.getSystemFlag(), exsysIdentify.getType()));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getExsysIdentifyBySystemFlag end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> saveExsysDispatchLog(UserInfo userInfo,
			ExsysDispatchLog exsysDispatchLog) {
		
		RpcResponse<Integer> result = null;
		logger.info("SupplyChainRemoteImpl::saveExsysDispatchLog userInfo:{},exsysDispatchLog:{}",userInfo,exsysDispatchLog);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getSystemFlag(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getSystemFlag() must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getModuleFlag(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getModuleFlag() must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getSn(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getSn must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(exsysDispatchLogService.saveExsysDispatchLog(exsysDispatchLog));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::saveExsysDispatchLog end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> delExsysDispatchLog(UserInfo userInfo,
			ExsysDispatchLog exsysDispatchLog) {
		
		RpcResponse<Integer> result = null;
		logger.info("SupplyChainRemoteImpl::delExsysDispatchLog userInfo:{},exsysDispatchLog:{}",userInfo,exsysDispatchLog);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getSystemFlag(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getSystemFlag() must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getSn(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getSn must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getModuleFlag(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getModuleFlag() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(exsysDispatchLogService.delExsysDispatchLog(exsysDispatchLog));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::delExsysDispatchLog end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<ExsysDispatchLog> getExsysDispatchLog(UserInfo userInfo,
			ExsysDispatchLog exsysDispatchLog) {
		
		RpcResponse<ExsysDispatchLog> result = null;
		logger.info("SupplyChainRemoteImpl::getExsysDispatchLog userInfo:{},exsysDispatchLog:{}",userInfo,exsysDispatchLog);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog must not be null");
			RpcAssert.assertNotNull(exsysDispatchLog.getSn(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDispatchLog.getSn must not be null");
			result = RpcResponse.success(exsysDispatchLogService.getExsysDispatchLog(exsysDispatchLog));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::getExsysDispatchLog end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> handleExsysDeviceStatu(UserInfo userInfo,
			ExsysDeviceStatu exsysDeviceStatu) {
		
		RpcResponse<Integer> result = null;
		logger.info("SupplyChainRemoteImpl::handleExsysDeviceStatu userInfo:{},exsysDeviceStatu:{}",userInfo,exsysDeviceStatu);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(exsysDeviceStatu, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDeviceStatu must not be null");
			RpcAssert.assertNotNull(exsysDeviceStatu.getSn(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysDeviceStatu.getSn() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.handleExsysDeviceStatu(userInfo,exsysDeviceStatu));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::handleExsysDeviceStatu end result:{}",result);
		return result;
	}
	
	@Override
	public RpcResponse<Integer> handleExsysOrderInfo(UserInfo userInfo,
			ExsysOrderInfo exsysOrderInfo) {
		
		RpcResponse<Integer> result = null;
		logger.info("SupplyChainRemoteImpl::handleExsysOrderInfo userInfo:{},exsysOrderInfo:{}",userInfo,exsysOrderInfo);
		try
		{
			RpcAssert.assertNotNull(userInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"userInfo must not be null");
			RpcAssert.assertNotNull(exsysOrderInfo, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysOrderInfo must not be null");
			RpcAssert.assertNotNull(exsysOrderInfo.getOrderno(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysOrderInfo.getOrderno() must not be null");
			RpcAssert.assertNotNull(exsysOrderInfo.getOrderstatu(), ErrorCodeEnum.ERRCODE_INVALID_PARAM,"exsysOrderInfo.getOrderstatu() must not be null");
			RpcAssert.assertIsTrue(requestVerifyService.verifyRequest(userInfo), ErrorCodeEnum.ERRCODE_INVALID_SIGN,"invalid sign");
			result = RpcResponse.success(supplyChainRemoteService.handleExsysOrderInfo(userInfo, exsysOrderInfo));
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::handleExsysOrderInfo end result:{}",result);
		return result;
	}
	
	
	@Override
	public RpcResponse<List<FirmwareInfo>> getFirmwareInfoList(
			FirmwareInfo firmwareInfo) {
		// TODO Auto-generated method stub
		try{
			List<FirmwareInfo> list = supplyChainRemoteService.getFirmwareInfoList(firmwareInfo);
			return RpcResponse.success(list);
		}catch(RpcServiceException e){
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> insert(RequestLog record) {
		try {
			Integer result = supplyChainRemoteService.insert(record);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<RequestLog> getRequestLog(RequestLog record) {
		try {
			return RpcResponse.success(supplyChainRemoteService.getRequestLog(record));
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	
}
