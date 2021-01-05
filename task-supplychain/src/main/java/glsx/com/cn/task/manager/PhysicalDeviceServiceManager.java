package glsx.com.cn.task.manager;

import glsx.com.cn.task.util.ErrorCodeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glsx.cloudframework.core.datastructure.page.Pagination;
import com.glsx.cloudframework.exception.ServiceException;
import com.glsx.biz.common.user.entity.DeviceCategory;
import com.glsx.biz.common.user.service.DeviceCategoryService;
import com.glsx.biz.user.common.entity.PhysicalDevice;
import com.glsx.biz.user.common.vo.ResponseResult;
import com.glsx.biz.user.service.PhysicalDeviceService;

/**
 * 
 * @Title: PhysicalDeviceServiceManager
 * @Description: 老运营平台设备管理服务接口
 * @author ql 
 * @date 2018年4月24日 上午11:32:02
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@Component
public class PhysicalDeviceServiceManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PhysicalDeviceService physicalDeviceService;
	
	
	@Autowired
	private DeviceCategoryService deviceCategoryService;  //老运营平台接口后面改用供应链提供的接口
	
	@SuppressWarnings("rawtypes")
	public Map batchAddPhysicalDevice(Integer deviceId,List<PhysicalDevice> physicalDeviceList){
		
		Map map = null;
		
		try{	
			 map = physicalDeviceService.batchAddPhysicalDevice(deviceId, physicalDeviceList);
		}catch(ServiceException e){
			
			logger.error("PhysicalDeviceServiceManager::batchAddPhysicalDevice调用外部接口失败：" + e.getMessage(),e);
		}
		
		return map;
	}

	/*
	public ErrorCodeEnum addPhysicalDevice(PhysicalDevice physicalDevice) throws ServiceException{
		
		ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.E_OK;
		
		try{
			
			physicalDeviceService.batchAddPhysicalDevice(physicalDevice.getDeviceId(), arg1);
			
		}catch(ServiceException e){
			
			errorCodeEnum.setCode(e.getCode());
			errorCodeEnum.setDescrible(e.getMessage());
			
			logger.error("PhysicalDeviceServiceManager::addPhysicalDevice调用外部接口失败：" + e.getMessage(),e);
		}
		
		return errorCodeEnum;
	}*/
	
	
	public ErrorCodeEnum addPhysicalDevice(PhysicalDevice physicalDevice) throws ServiceException{
		
		ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.E_OK;
		errorCodeEnum.setCode("0");
		errorCodeEnum.setDescrible("");
		
		try{
			List<PhysicalDevice> physicalDeviceList = new ArrayList<PhysicalDevice>();
			physicalDeviceList.add(physicalDevice);
			logger.info("入参参数：" + physicalDevice.toString());
			Map map = physicalDeviceService.batchAddPhysicalDevice(physicalDevice.getDeviceId(), physicalDeviceList);
			
			logger.info("同步数据老平台返回结果：" + map.toString());
			
			List resultList =  (List) map.get("fail");
			if(null != resultList && resultList.size() > 0)
			{
				ResponseResult response = (ResponseResult) resultList.get(0);
				if(null != response)
				{
					String errorCode = response.getErrorCode();
					String errorMsg = response.getErrorMsg();
					if(!"0".equals(errorCode))
					{
						errorCodeEnum.setCode(errorCode);
						errorCodeEnum.setDescrible(errorMsg);
					}
				}
			}			
			
		}catch(ServiceException e){
			logger.error("异常错误：" + e.getMessage(),e);
			errorCodeEnum.setCode(e.getCode());
			errorCodeEnum.setDescrible(e.getMessage());
			
			logger.error("PhysicalDeviceServiceManager::addPhysicalDevice调用外部接口失败：" + e.getMessage(),e);
		}
		
		return errorCodeEnum;
	}
	
	/**
	 * 
	 * @Title: getDeviceCategories 
	 * @Description: 获取设备类型
	 * @param @return 
	 * @return List<DeviceCategory>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<DeviceCategory> getDeviceCategories(){
		List<DeviceCategory> list = null;
		try {
			Pagination page = deviceCategoryService.getDeviceCategories(1, 200);
			list = (List<DeviceCategory>) page.getList();
		} catch (ServiceException e) {
			logger.error("请求查询老设备平台设备类型接口失败：" + e.getMessage());
		}
		return list;
	}
	
}
