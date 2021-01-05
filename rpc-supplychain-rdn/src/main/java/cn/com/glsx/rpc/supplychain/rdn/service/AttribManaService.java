package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.AttribManaMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.DeviceType;
import cn.com.glsx.supplychain.model.AttribMana;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class AttribManaService {

	private static final Logger logger = LoggerFactory.getLogger(AttribManaService.class);
	@Autowired
	private AttribManaMapper attribManaMapper;
	@Autowired
	private DeviceTypeService jxcmtDeviceTypeService;

	
	public DeviceType getDeviceTypeByAttribCode(String attribCode, Map<String,DeviceType> mapCache){
		DeviceType result = null;
		if(StringUtils.isEmpty(attribCode)){
			return result;
		}
		if(mapCache != null){
			result = mapCache.get(attribCode);
		}
		if(null != result){
			return result;
		}
		AttribMana attribMana = getAttribManaByAttribCode(attribCode);
		if(null == attribMana){
			return result;
		}
		if(attribMana.getDevTypeId() == null){
			return result;
		}
		DeviceType deviceType = jxcmtDeviceTypeService.getDeviceTypeById(attribMana.getDevTypeId());
		if(null == deviceType){
			return result;
		}
		if(mapCache != null){
			mapCache.put(attribCode, deviceType);
		}
		return deviceType;
	}
	
	public AttribMana getAttribManaByAttribCode(String attribCode){
		if(StringUtils.isEmpty(attribCode)){
			return null;
		}
		AttribMana condition = new AttribMana();
		condition.setAttribCode(attribCode);
		condition.setDeletedFlag("N");
		return attribManaMapper.selectOne(condition);
	}

}
