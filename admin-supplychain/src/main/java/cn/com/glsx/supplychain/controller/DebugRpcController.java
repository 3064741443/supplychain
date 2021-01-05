package cn.com.glsx.supplychain.controller;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.SystemPlatEnum;
import cn.com.glsx.supplychain.enums.UserFlagTypeEnum;
import cn.com.glsx.supplychain.enums.VehicleFlagTypeEnum;
import cn.com.glsx.supplychain.model.DeviceFileSv;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import cn.com.glsx.supplychain.remote.DeviceManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("debugRpc")
public class DebugRpcController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private DeviceManagerService deviceManagerService;
    
    @RequestMapping("getSystemPlatByDeviceSn")
    @ResponseBody
    public RpcResponse<SystemPlatEnum> getSystemPlatByDeviceSn(String sn)
    {
    	logger.info("getSystemPlatByDeviceSn开始时间:" + System.currentTimeMillis());
    	SupplyDeviceFileRequest supplyDeviceFileRequest = new SupplyDeviceFileRequest();
    	supplyDeviceFileRequest.setSn(sn);
        supplyDeviceFileRequest.setConsumer("admin");
        supplyDeviceFileRequest.setVersion("v2.0");
        RpcResponse<SystemPlatEnum> response = deviceManagerService.getSystemPlatByDeviceSn(supplyDeviceFileRequest);
        logger.info("getSystemPlatByDeviceSn结束时间:" + System.currentTimeMillis());
        return response;
    }
    
    @RequestMapping("getSystemPlatByImsi")
    @ResponseBody
    public RpcResponse<SystemPlatEnum> getSystemPlatByImsi(String imsi)
    {
    	logger.info("getSystemPlatByImsi开始时间:" + System.currentTimeMillis());
    	SupplyDeviceFileRequest supplyDeviceFileRequest = new SupplyDeviceFileRequest();
    	supplyDeviceFileRequest.setImsi(imsi);
        supplyDeviceFileRequest.setConsumer("admin");
        supplyDeviceFileRequest.setVersion("v2.0");
        supplyDeviceFileRequest.setCompanyId(1);
        RpcResponse<SystemPlatEnum> response = deviceManagerService.getSystemPlatByImsi(supplyDeviceFileRequest);
        logger.info("getSystemPlatByImsi结束时间:" + System.currentTimeMillis());
        return response;
    }
    
    

    @RequestMapping("getDeviceByDeviceSn")
    @ResponseBody
    public RpcResponse<DeviceFileSv> getDeviceByDeviceSn(String sn) {
        logger.info("getDeviceByDeviceSn开始时间:" + System.currentTimeMillis());
        SupplyDeviceFileRequest supplyDeviceFileRequest = new SupplyDeviceFileRequest();
        supplyDeviceFileRequest.setSn(sn);
        supplyDeviceFileRequest.setConsumer("admin");
        supplyDeviceFileRequest.setVersion("v2.0");
        RpcResponse<DeviceFileSv> response = deviceManagerService.getDeviceByDeviceSn(supplyDeviceFileRequest);
        logger.info("getDeviceByDeviceSn结束时间:" + System.currentTimeMillis());
        return response;
    }

    @RequestMapping("getDeviceByImsi")
    @ResponseBody
    public RpcResponse<DeviceFileSv> getDeviceByImsi(String imsi) {
        logger.info("getDeviceByImsi开始时间:" + System.currentTimeMillis());
        SupplyDeviceFileRequest supplyDeviceFileRequest = new SupplyDeviceFileRequest();
        supplyDeviceFileRequest.setImsi(imsi);
        supplyDeviceFileRequest.setConsumer("admin");
        supplyDeviceFileRequest.setVersion("v2.0");
        supplyDeviceFileRequest.setCompanyId(1);
        RpcResponse<DeviceFileSv> response = deviceManagerService.getDeviceByImsi(supplyDeviceFileRequest);
        logger.info("getDeviceByImsi结束时间:" + System.currentTimeMillis());
        return response;
    }

    @RequestMapping("activeDevicePackage")
    @ResponseBody
    public RpcResponse<Integer> activeDevicePackage(String sn, String vehicleFlag, String userFlag, String iccid,
                                                    Integer companyId, String manufactureCode, String softVersion,
                                                        String imsi, String flagType,String userFlagType,
                                                    String pkgStatus,Integer deviceCode,Integer packageId,
                                                    Integer androidPackageId) {
        logger.info("activeDevicePackage开始时间:" + System.currentTimeMillis());
        SupplyDeviceFileRequest supplyDeviceFileRequest = new SupplyDeviceFileRequest();
        supplyDeviceFileRequest.setSn(sn);
        supplyDeviceFileRequest.setVehicleFlag(vehicleFlag);
        supplyDeviceFileRequest.setUserFlag(userFlag);
        supplyDeviceFileRequest.setIccid(iccid);
        supplyDeviceFileRequest.setCompanyId(companyId);
        supplyDeviceFileRequest.setManufactureCode(manufactureCode);
        supplyDeviceFileRequest.setSoftVersion(softVersion);
        supplyDeviceFileRequest.setImsi(imsi);
        supplyDeviceFileRequest.setFlagType(VehicleFlagTypeEnum.fromString(flagType));
        supplyDeviceFileRequest.setUserFlagType(UserFlagTypeEnum.fromString(userFlagType));
        supplyDeviceFileRequest.setPkgStatus(PackageStatuEnum.fromString(pkgStatus));
        supplyDeviceFileRequest.setDeviceCode(deviceCode);
        supplyDeviceFileRequest.setPackageId(packageId);
        supplyDeviceFileRequest.setAndroidPackageId(androidPackageId);
        supplyDeviceFileRequest.setConsumer("admin");
        supplyDeviceFileRequest.setVersion("v2.0");
        RpcResponse<Integer> response = deviceManagerService.activeDevicePackage(supplyDeviceFileRequest);
        logger.info("activeDevicePackage结束时间:" + System.currentTimeMillis());
        return response;
    }
}
