package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.DeviceTypeMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DeviceTypeService {

	@Autowired
	private DeviceTypeMapper deviceTypeMapper;
	
	public String getDeviceTypeNameByTypeId(Integer typeId)
	{
		String result = "";
		DeviceType deviceType = deviceTypeMapper.selectByPrimaryKey(typeId);
		if(!StringUtils.isEmpty(deviceType))
		{
			result = deviceType.getName();
		}
		return result;
	}

	public DeviceType getDeviceTypeById(Integer deviceTypeId){
		DeviceType condition = new DeviceType();
		condition.setId(deviceTypeId);
		return deviceTypeMapper.selectOne(condition);
	}
}
