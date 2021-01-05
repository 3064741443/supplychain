package cn.com.glsx.supplychain.jxc.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.dto.JXCMTPackageDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;

import com.glsx.cloudframework.exception.ServiceException;
import com.glsx.platform.goods.common.entity.ServicePackage;
import com.glsx.platform.goods.common.service.ServicePackageService;

@Service
public class JXCServicePackageService {
	private static final Logger logger = LoggerFactory.getLogger(JXCServicePackageService.class);
	@Autowired
	private ServicePackageService servicePackageService;
	
	public List<JXCMTPackageDTO> listPackageByDeviceCode(Integer deviceCode) throws RpcServiceException{
		List<JXCMTPackageDTO> listResult = new ArrayList<>();
		try{
			List<ServicePackage> packageList = servicePackageService.findActiveUpShelvePackageByDeviceId(deviceCode);
			if(null == packageList || packageList.isEmpty()){
				return listResult;
			}
			JXCMTPackageDTO dto = null;
			for(ServicePackage sp:packageList){
				dto = new JXCMTPackageDTO();
				dto.setPackageCode(String.valueOf(sp.getId()));
				dto.setPackageName(sp.getName());
				dto.setDeviceCode(deviceCode);
				listResult.add(dto);
			}
		}catch (ServiceException e){
			logger.error("根据硬件设备类型获取相应套餐:调用外部服务失败 错误信息:" + e.getMessage());
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_EXTERNAL_INTERFACE);
		}
		return listResult;
	}
}
