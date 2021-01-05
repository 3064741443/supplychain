package cn.com.glsx.supplychain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import cn.com.glsx.supplychain.service.DeviceManagerServiceService;

public class UpdateDeviceFileRecordThread extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DeviceFile deviceFile;
    
    private DeviceFileSnapshot deviceSnapshot;
    
    private String strOperator;

    @Autowired
    private DeviceManagerServiceService deviceManagerServiceService;

    public UpdateDeviceFileRecordThread(DeviceFile deviceFile,DeviceFileSnapshot deviceSnapshot,String strOperator) {
        
    	this.deviceFile = deviceFile;
    	this.deviceSnapshot = deviceSnapshot;
    	this.strOperator = strOperator;
    }

    @Override
    public void run() {
        try {
        	
            deviceManagerServiceService.addDeviceUpdateRecordOnActivePackage(deviceFile, deviceSnapshot, strOperator);
        } catch (RpcServiceException e) {
            logger.error(e.getMessage());
        }
    }

}
