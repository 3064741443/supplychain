package glsx.com.cn.task.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.glsx.cloudframework.exception.ServiceException;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;
import com.glsx.oms.flowservice.api.request.DeviceImeiRequest;
import com.glsx.oms.flowservice.api.response.DeviceImeiResponse;
import com.glsx.oms.flowservice.api.service.DeviceService;


@Component
public class FlowCardServiceManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OpsMgrManager opsMgrManager;

	//@Autowired
	//private FlowCardService flowCardService; 
	@Autowired
	private DeviceService deviceService;
	
	public FlowResponse<Flowcard> getFlowCardByIccid(FlowCardRequest request) throws Exception{
		
		FlowResponse<Flowcard> response = null;
		
		try{
			
			response = opsMgrManager.getCardIccid(request);
			//response = flowCardService.getFlowCard(request);
			
		}catch(Exception e){
			
			logger.error("FlowCardServiceManager::getFlowCardByIccid调用外部接口失败：" + e.getMessage(),e);
			throw e;
		}
		
		return response;
	}
	
	public com.glsx.oms.flowservice.api.core.FlowResponse<DeviceImeiResponse> saveDeviceImei(DeviceImeiRequest request){
		
		com.glsx.oms.flowservice.api.core.FlowResponse<DeviceImeiResponse> respose = deviceService.saveDeviceImei(request);

		return respose;
	}
	
}
