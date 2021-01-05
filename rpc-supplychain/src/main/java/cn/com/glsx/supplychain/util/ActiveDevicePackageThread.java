package cn.com.glsx.supplychain.util;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import cn.com.glsx.supplychain.remote.DeviceManagerService;
import cn.com.glsx.supplychain.service.DeviceManagerServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ActiveDevicePackageThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SupplyDeviceFileRequest request;

    @Autowired
    private DeviceManagerServiceService deviceManagerServiceService;

    public ActiveDevicePackageThread(SupplyDeviceFileRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            deviceManagerServiceService.activeDevicePackage(request);
        } catch (RpcServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
