package cn.com.glsx.supplychain.remote;

import java.util.HashMap;
import java.util.List;

import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.service.AttribManagerSevice;
import cn.com.glsx.supplychain.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.service.DeviceCodeService;
import cn.com.glsx.supplychain.service.SupplyChainAdminRemoteService;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;

@Component("supplyChainAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000 )
public class SupplyChainAdminRemoteImpl implements SupplyChainAdminRemote{

	@Autowired
	private SupplyChainAdminRemoteService adminService;
	
	@Autowired
	private SupplyChainExternalService externalService;
	
	@Autowired
	private DeviceCodeService deviceCodeService;

	@Autowired
	private AttribManagerSevice attribManagerSevice;

	@Autowired
	private DeviceInfoService deviceInfoService;
	
	@Override
	public RpcResponse<RpcPagination<WareHouseInfo>> listWarehouseInfo(
			RpcPagination<WareHouseInfo> pagination, WareHouseInfo wareHouseInfo) {
		try{
			Page<WareHouseInfo> page = adminService.listWareHouseInfo(pagination, wareHouseInfo);
			//复制查询对象
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		}catch(RpcServiceException e){
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateWareHouseInfo(
			WareHouseInfo wareHouseInfo) {
		try {
			Integer result = adminService.addAndUpdateWareHouseInfo(wareHouseInfo);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<WareHouseInfo> getWareHouseInfoById(Integer id) {
		try {
			WareHouseInfo wareHouseInfo = adminService.getWareHouseInfoById(id);
			return RpcResponse.success(wareHouseInfo); 
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<UserInfo>> listUserInfo(
			RpcPagination<UserInfo> pagination, UserInfo userInfo) {
		try {
			//查询设备信息
			Page<UserInfo> page = adminService.listUserInfo(pagination, userInfo);
			//复制查询对象
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateUserInfo(UserInfo userInfo) {
		try {
			Integer result = adminService.addAndUpdateUserInfo(userInfo);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<UserInfo> getUserInfoById(Integer id) {
		try {
			UserInfo userInfo = adminService.getUserInfoById(id);
			return RpcResponse.success(userInfo); 
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<FirmwareInfo>> listFirmwareInfo(
			RpcPagination<FirmwareInfo> pagination, FirmwareInfo firmwareInfo) {
		try {
			//查询设备信息
			Page<FirmwareInfo> page = adminService.listFirmwareInfo(pagination, firmwareInfo);
			//复制查询对象
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateFirmwareInfo(
			FirmwareInfo firmwareInfo) {
		try {
			Integer result = adminService.addAndUpdateFirmwareInfo(firmwareInfo);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<FirmwareInfo> getFirmwareInfoById(Integer id) {
		try {
			FirmwareInfo firmwareInfo = adminService.getFirmwareInfoById(id);	
			return RpcResponse.success(firmwareInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<DeviceInfo>> listDeviceInfo(RpcPagination<DeviceInfo> pagination, DeviceInfo deviceInfo) {
		try {
			//查询设备信息
			Page<DeviceInfo> page = adminService.listDeviceInfo(pagination, deviceInfo);
			//复制查询对象
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}
	
	@Override
	public RpcResponse<RpcPagination<DeviceInfo>> ListDeviceInfoByOrderCode(
			RpcPagination<DeviceInfo> pagination, String orderCode) {
		// TODO Auto-generated method stub
		try{
			Page<DeviceInfo> page = adminService.listDeviceInfoByOrderCode(pagination, orderCode);
			
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		}catch(RpcServiceException e){	
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}
	
	@Override
	public RpcResponse<RpcPagination<OrderInfoDetail>> pageOrderInfoDetail(
			RpcPagination<OrderInfoDetail> pagination,OrderInfoDetail record){
		
		try
		{
			Page<OrderInfoDetail> page = adminService.pageOrderInfoDetails(pagination, record);
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		}catch(RpcServiceException e){	
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<DeviceInfo>> ExportDeviceInfo(DeviceInfo deviceInfo) {
		try{

			List<DeviceInfo> list = adminService.ExportDeviceInfo(deviceInfo);

			return RpcResponse.success(list);

		}catch(RpcServiceException e){

			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateDeviceInfo(DeviceInfo deviceInfo) {
		try {
			Integer result = adminService.addAndUpdateDeviceInfo(deviceInfo);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<DeviceInfo> getDeviceInfoById(Integer id) {
		try {
			DeviceInfo deviceInfo = adminService.getDeviceInfoById(id);	
			return RpcResponse.success(deviceInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<DeviceInfo> getDeviceInfoByIccid(String iccid) {
		try {
			DeviceInfo deviceInfo = deviceInfoService.getDeviceInfoByIccid(iccid);
			return RpcResponse.success(deviceInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<DeviceInfo> getDeviceInfoBySn(String sn) {
		try {
			DeviceInfo deviceInfo = deviceInfoService.getDeviceInfoBySn(sn);
			return RpcResponse.success(deviceInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<DeviceInfo> getDeviceInfoByImei(String imei) {
		try {
			DeviceInfo deviceInfo = deviceInfoService.getDeviceInfoByImei(imei);
			return RpcResponse.success(deviceInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<OrderInfo>> listOrderInfo(
			RpcPagination<OrderInfo> pagination, OrderInfo orderInfo) {
		try {
			//查询设备信息
			Page<OrderInfo> page = adminService.listOrderInfo(pagination, orderInfo);
			//复制查询对象
			pagination = RpcPagination.copyPage(page);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateOrderInfo(OrderInfo orderInfo) {
		try {
			Integer result = adminService.addAndUpdateOrderInfo(orderInfo);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<OrderInfo> getOrderInfoById(Integer id) {
		try {
			OrderInfo orderInfo = adminService.getOrderInfoById(id);
			return RpcResponse.success(orderInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> listDeviceCode(DeviceCode record) {
		
		if(StringUtils.isEmpty(record))
		{
			return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
		}	
		try
		{
			List<DeviceCode> oList = deviceCodeService.listDeviceCode(record);
			return RpcResponse.success(oList);
		}
		catch(RpcServiceException e)
		{
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> listPackageInfoByDeviceCode(Integer code) {
		try {
			List list = externalService.listPackageInfoByDeviceCode(code);
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<SupplyChainMerchantInfo> findMerchantInfoByMerchantId(Integer merchantId) {
		try {
			SupplyChainMerchantInfo supplyChainMerchantInfo = externalService.findMerchantInfoByMerchantId(merchantId);
			return RpcResponse.success(supplyChainMerchantInfo);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> listMerchantInfo(Integer targetPage, Integer pageSize) {
		try {
			List list = externalService.listMerchantInfo(targetPage, pageSize);
			return RpcResponse.success(list);
			
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public RpcResponse<List> listMerchantInfo(HashMap<String, Object> condition,Integer targetPage, Integer pageSize) {
		try {
			
			List list = externalService.listMerchantInfo(condition,targetPage, pageSize);
			return RpcResponse.success(list);
			
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<AttribInfo>> listAttribInfo(AttribInfo attribInfo) {
		try {
			List<AttribInfo> list = adminService.listAttribInfo(attribInfo);
			return RpcResponse.success(list);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}
	
	@Override
	public RpcResponse<Integer> syncDeviceInfo(String orderCode) {
		try {
			//查询设备信息
			int result = adminService.syncDeviceInfo(orderCode);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> addAndUpdateAttribMana(AttribMana attribMana) {
		try {
			//插入和更新
			int result = adminService.addAndUpdateAttribMana(attribMana);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<RpcPagination<AttribMana>> listAttribMana(
			RpcPagination<AttribMana> pagination, AttribMana attribMana) {
		try {
			Page<AttribMana> list = adminService.listAttribMana(pagination, attribMana);
			//复制查询对象
			pagination = RpcPagination.copyPage(list);
			return RpcResponse.success(pagination);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<OrderInfoDetail>> ExportOrderInfoDetail(OrderInfoDetail orderInfoDetail) {
		try{
			
			List<OrderInfoDetail> list = adminService.ExportOrderInfoDetail(orderInfoDetail);
			
			return RpcResponse.success(list);
			
		}catch(RpcServiceException e){
			
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}
	
	@Override
	public RpcResponse<Integer> saveDeviceCategory(DeviceCode deviceCode){
		try {
            externalService.saveDeviceCategory(deviceCode);
			return RpcResponse.success(1);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<OrderInfoDetail> getOrderInfoDetailByImei(String imei) {
		try {
			RpcAssert.assertNotNull(imei, DefaultErrorEnum.PARAMETER_NULL, "imei must not be null");
			return RpcResponse.success(adminService.getOrderInfoDetailByImei(imei));
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<AttribMana> getAttribManaInfo(String manaCode) {
		try {
			AttribMana result =  adminService.getAttribManaInfo(manaCode);
			return RpcResponse.success(result);
		} catch (RpcServiceException e) {
			return RpcResponse.error(e.getError(),e.getMessage());
		}
	}

	@Override
	public RpcResponse<List<AttribMana>> listAttribManaCodes(AttribMana record)
	{
		RpcResponse<List<AttribMana>> result;
		try
		{
			RpcAssert.assertNotNull(record, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(attribManagerSevice.listAttribManaCodes(record));
		}
		catch (RpcServiceException e)
		{
			result = RpcResponse.error(e);
		}
		return result;
	}

    @Override
    public RpcResponse<AttribMana> getAttribManaByManaCode(String manaCode) {
        RpcResponse<AttribMana> result;
        try
        {
            RpcAssert.assertNotNull(manaCode, ErrorCodeEnum.ERRCODE_INVALID_PARAM,"manaCode must not be null");
            return RpcResponse.success(attribManagerSevice.getAttribManaByManaCode(manaCode));
        }
        catch (RpcServiceException e)
        {
            result = RpcResponse.error(e);
        }
        return result;
    }

}
