package cn.com.glsx.supplychain.jxc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceCodeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeDTO;
import cn.com.glsx.supplychain.jxc.manager.JXCDeviceTypeRedisManager;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceCodeMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceTypeDispatchSurpportMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTDeviceTypeMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceCode;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceType;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceTypeDispatchSurpport;

@Service
public class JXCMTDeviceTypeService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTDeviceTypeService.class);
	@Autowired
	private JXCMTDeviceTypeDispatchSurpportMapper jxcmtDeviceTypeDispatchMapper;
	@Autowired
	private JXCMTDeviceTypeMapper jxcmtDeviceTypeMapper;
	@Autowired
	private JXCMTDeviceCodeMapper jxcmtDeviceCodeMapper;
	@Autowired
	private JXCDeviceTypeRedisManager jxcmtDeviceTypeRedis;
	
	public JXCMTDeviceTypeDispatchSurpport getDeviceTypeDispatchSurpport(Integer deviceTypeId){
		if(null == deviceTypeId){
			return null;
		}
		JXCMTDeviceTypeDispatchSurpport condition = new JXCMTDeviceTypeDispatchSurpport();
		condition.setDeviceTypeId(deviceTypeId);
		return jxcmtDeviceTypeDispatchMapper.selectOne(condition);
	}
	
	public List<JXCMTDeviceTypeDTO> listDeviceTypeSupportDispatch(JXCMTDeviceTypeDTO record){
		List<JXCMTDeviceTypeDTO> listDeviceTypeDto = new ArrayList<>();
		List<JXCMTDeviceTypeDispatchSurpport> listDeviceType = jxcmtDeviceTypeDispatchMapper.selectAll();
		if(null == listDeviceType || listDeviceType.isEmpty()){
			return listDeviceTypeDto;
		}
		JXCMTDeviceTypeDTO deviceTypeDto = null;
		for(JXCMTDeviceTypeDispatchSurpport deviceType:listDeviceType){
			deviceTypeDto = new JXCMTDeviceTypeDTO();
			deviceTypeDto.setId(deviceType.getDeviceTypeId());
			deviceTypeDto.setName(deviceType.getDeviceTypeName());
			listDeviceTypeDto.add(deviceTypeDto);
		}
		return listDeviceTypeDto;
	}
	
	public List<JXCMTDeviceTypeDTO> listDeviceTypeDto(JXCMTDeviceTypeDTO record){
		List<JXCMTDeviceTypeDTO> listDeviceTypeDto = new ArrayList<>();
		List<JXCMTDeviceType> listDeviceType = jxcmtDeviceTypeMapper.selectAll();
		if(null == listDeviceType || listDeviceType.isEmpty()){
			return listDeviceTypeDto;
		}
		JXCMTDeviceTypeDTO deviceTypeDto = null;
		for(JXCMTDeviceType deviceType:listDeviceType){
			deviceTypeDto = new JXCMTDeviceTypeDTO();
			deviceTypeDto.setId(deviceType.getId());
			deviceTypeDto.setName(deviceType.getName());
			listDeviceTypeDto.add(deviceTypeDto);
		}
		return listDeviceTypeDto;
	}
	
	public List<JXCMTDeviceCodeDTO> listDeviceCodeDto(JXCMTDeviceCodeDTO record){
		List<JXCMTDeviceCodeDTO> listDeviceCodeDto = new ArrayList<>();
		List<JXCMTDeviceCode> listDeviceCode = null;
		Example example = new Example(JXCMTDeviceCode.class);
		Criteria criteria = example.createCriteria();
		if(null != record.getTypeId()){
			criteria.andEqualTo("typeId", record.getTypeId());
		}
		if(null != record.getDeviceCode()){
			criteria.andLike("deviceCode", "%"+record.getDeviceCode()+"%");
		}
		if(null != record.getDeviceName()){
			criteria.andLike("deviceName", "%"+record.getDeviceName()+"%");
		}
		listDeviceCode = jxcmtDeviceCodeMapper.selectByExample(example);
		if(null == listDeviceCode || listDeviceCode.isEmpty()){
			return listDeviceCodeDto;
		}
		JXCMTDeviceCodeDTO dto = null;
		for(JXCMTDeviceCode deviceCode:listDeviceCode){
			dto = new JXCMTDeviceCodeDTO();
			dto.setDeviceCode(deviceCode.getDeviceCode());
			dto.setDeviceName(deviceCode.getDeviceName());
			dto.setTypeId(deviceCode.getTypeId());
			listDeviceCodeDto.add(dto);
		}
		return listDeviceCodeDto;
	}
	
	public String getDeviceTypeNameById(Integer id,Map<Integer,String> mapCache){
		String result = null;
		if(null != mapCache){
			result = mapCache.get(id);
		}
		if(!StringUtils.isEmpty(result)){
			return result;
		}
		result = jxcmtDeviceTypeRedis.getDeviceTypeById(id);
		if(!StringUtils.isEmpty(result)){
			if(null != mapCache){
				mapCache.put(id, result);
			}
			return result;
		}
		JXCMTDeviceType condition = new JXCMTDeviceType();
		condition.setId(id);
		JXCMTDeviceType deviceType = jxcmtDeviceTypeMapper.selectOne(condition);
		if(null != deviceType){
			jxcmtDeviceTypeRedis.setDeviceTypeById(deviceType);
			if(null != mapCache){
				mapCache.put(deviceType.getId(), deviceType.getName());
			}
			result = deviceType.getName();
		}
		return result;
	}
	
	public JXCMTDeviceType getDeviceTypeById(Integer deviceTypeId){
		JXCMTDeviceType condition = new JXCMTDeviceType();
		condition.setId(deviceTypeId);
		return jxcmtDeviceTypeMapper.selectOne(condition);
	}
	
	public boolean supportDispatchDeviceByDeviceCode(Integer deviceCode){
		if(null == deviceCode){
			return false;
		}
		JXCMTDeviceCode condition = new JXCMTDeviceCode();
		condition.setDeviceCode(deviceCode);
		condition.setDeletedFlag("N");
		JXCMTDeviceCode jxcmtDeviceCode = jxcmtDeviceCodeMapper.selectOne(condition);
		if(null == jxcmtDeviceCode){
			return false;
		}
		JXCMTDeviceTypeDispatchSurpport jxcmtDispatchSurpport = this.getDeviceTypeDispatchSurpport(jxcmtDeviceCode.getTypeId());
		if(null == jxcmtDispatchSurpport){
			return false;
		}
		return true;
	}
	
}




