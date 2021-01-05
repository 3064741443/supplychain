package cn.com.glsx.supplychain.jxc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.JXCMTAttribInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeAttribInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTMerchantChannelDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductTypeDTO;
import cn.com.glsx.supplychain.jxc.manager.JXCAttribInfoRedisManager;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTAttribInfoMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTAttribInfo;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceType;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;

@Service
public class JXCMTAttribInfoService {

	private static final Logger logger = LoggerFactory.getLogger(JXCMTAttribInfoService.class);
	
	@Autowired
	private JXCMTAttribInfoMapper JxcmtAttribInfoMapper;
	@Autowired
	private JXCAttribInfoRedisManager JxcmtAttribInfoRedis; 
	@Autowired
	private JXCMTDeviceTypeService JxcmtDeviceTypeService;
	
	public List<JXCMTAttribInfoDTO> listAttribInfoByDeviceType(JXCMTDeviceTypeDTO record){
		List<JXCMTAttribInfoDTO> listResult = new ArrayList<>();
		Integer attribInfoTypeId = this.getAttribTypeFromDeviceType(record.getId());
		if(attribInfoTypeId == 0){
			return listResult;
		}
		Example example = new Example(JXCMTAttribInfo.class);
		example.createCriteria().andEqualTo("type",attribInfoTypeId)
								.andEqualTo("deletedFlag","N");
		List<JXCMTAttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResult;
		}
		JXCMTAttribInfoDTO dtoObject = null;
		for(JXCMTAttribInfo attribInfo:listAttribInfo){
			dtoObject = new JXCMTAttribInfoDTO();
			dtoObject.setId(attribInfo.getId());
			dtoObject.setName(attribInfo.getName());
			listResult.add(dtoObject);
		}
		return listResult;
	}
	
	public List<JXCMTAttribInfoDTO> listAttribInfoByAttribInfoType(JXCMTAttribInfoDTO record){
		List<JXCMTAttribInfoDTO> listResult = new ArrayList<>();
		Example example = new Example(JXCMTAttribInfo.class);
		example.createCriteria().andEqualTo("type",record.getType())
								.andEqualTo("deletedFlag","N");
		List<JXCMTAttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResult;
		}
		JXCMTAttribInfoDTO dtoObject = null;
		for(JXCMTAttribInfo attribInfo:listAttribInfo){
			dtoObject = new JXCMTAttribInfoDTO();
			dtoObject.setId(attribInfo.getId());
			dtoObject.setName(attribInfo.getName());
			listResult.add(dtoObject);
		}
		return listResult;
	}
	
	public Integer saveAttribInfoByDeviceType(JXCMTDeviceTypeAttribInfoDTO record){
		Integer result = 0;
		Integer attribInfoTypeId = this.getAttribTypeFromDeviceType(record.getDeviceTypeId());
		if(attribInfoTypeId == 0){
			return result;
		}
		Integer maxValue = JxcmtAttribInfoMapper.getAttribInfoMaxValue(attribInfoTypeId);
		if(maxValue == null){
			maxValue = 0;
		}
		maxValue++;
		JXCMTAttribInfo attribInfo = new JXCMTAttribInfo();
		attribInfo.setName(record.getAttribName());
		attribInfo.setType(attribInfoTypeId);
		JXCMTAttribInfo dbResult = JxcmtAttribInfoMapper.selectOne(attribInfo);
		if(dbResult != null){
			return result;
		}
		attribInfo.setComment("");
		attribInfo.setValue(maxValue);
		attribInfo.setCreatedBy(record.getConsumer());
		attribInfo.setUpdatedBy(record.getConsumer());
		attribInfo.setCreatedDate(JxcUtils.getNowDate());
		attribInfo.setUpdatedDate(JxcUtils.getNowDate());
		attribInfo.setDeletedFlag("N");
		return JxcmtAttribInfoMapper.insert(attribInfo);
	}
	
	public JXCMTAttribInfo getAttribInfoByDeviceType(JXCMTDeviceTypeAttribInfoDTO record){
		JXCMTAttribInfo result = null;
		Integer attribInfoTypeId = this.getAttribTypeFromDeviceType(record.getDeviceTypeId());
		if(attribInfoTypeId == 0){
			return result;
		}
		JXCMTAttribInfo attribInfo = new JXCMTAttribInfo();
		attribInfo.setName(record.getAttribName());
		attribInfo.setType(attribInfoTypeId);
		return JxcmtAttribInfoMapper.selectOne(attribInfo);
	}
	
	public List<JXCMTProductTypeDTO> listProductType(JXCMTProductTypeDTO record){
		List<JXCMTProductTypeDTO> listResultDto = new ArrayList<>();
		Example example = new Example(JXCMTAttribInfo.class);
		example.createCriteria().andEqualTo("type", Constants.PRODUCT_CONFIG_TYPE_PRODUCT_TYPE)
								.andEqualTo("deletedFlag","N");
		List<JXCMTAttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResultDto;
		}
		JXCMTProductTypeDTO dtoObject = null;
		for(JXCMTAttribInfo attribInfo:listAttribInfo){
			dtoObject = new JXCMTProductTypeDTO();
			dtoObject.setId(attribInfo.getId());
			dtoObject.setName(attribInfo.getName());
			listResultDto.add(dtoObject);
		}
		return listResultDto;
	}
	
	public List<JXCMTMerchantChannelDTO> listMerchantChannel(JXCMTMerchantChannelDTO record){
		List<JXCMTMerchantChannelDTO> listResultDto = new ArrayList<>();
		Example example = new Example(JXCMTAttribInfo.class);
		example.createCriteria().andEqualTo("type", Constants.PRODUCT_CONFIG_TYPE_MERCHANT_CHANNEL)
								.andEqualTo("deletedFlag","N");
		List<JXCMTAttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResultDto;
		}
		JXCMTMerchantChannelDTO dtoObject = null;
		for(JXCMTAttribInfo attribInfo:listAttribInfo){
			dtoObject = new JXCMTMerchantChannelDTO();
			dtoObject.setId(attribInfo.getId());
			dtoObject.setName(attribInfo.getName());
			listResultDto.add(dtoObject);
		}
		return listResultDto;
	}
	
	public Map<Integer,String> listAttribNameByIds(List<Integer> listIds){
		Map<Integer,String> mapResult = new HashMap<>();
		if(null == listIds || listIds.isEmpty()){
			return mapResult;
		}
		Example example = new Example(JXCMTAttribInfo.class);
		example.createCriteria().andIn("id", listIds);
		List<JXCMTAttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo || listAttribInfo.isEmpty()){
			return mapResult;
		}
		for(JXCMTAttribInfo attribInfo:listAttribInfo){
			mapResult.put(attribInfo.getId(), attribInfo.getName());
		}
		return mapResult;
	}
	
	public Byte getDbMerchantChannelFromAttribInfo(Integer merchantChannel){
		Byte result = null;
		if(null == merchantChannel){
			return result;
		}
		if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_GHDX)){
			result = 1;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_JRFKDX)){
			result = 2;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_TMHQD)){
			result = 3;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_JRQD)){
			result = 4;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_YKT)){
			result = 5;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_TSQD)){
			result = 6;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_AJZL)){
			result = 7;
		}else if(merchantChannel.equals(Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_GHZY)){
			result = 8;
		}else{
			result = (byte) (merchantChannel - Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_BASE);
		}
		return result;
	}
	
	public Byte getDbProductTypeFromAttribInfo(Integer productTypeConfigId){
		Byte result = null;
		if(null == productTypeConfigId){
			return result;
		}
		if(productTypeConfigId.equals(Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_CZ)){
			result = 3;
		}else if(productTypeConfigId.equals(Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_HSJ)){
			result = 4;
		}else if(productTypeConfigId.equals(Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_ACBB)){
			result = 1;
		}else if(productTypeConfigId.equals(Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_JRFK)){
			result = 2;
		}else if(productTypeConfigId.equals(Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_ORTHER)){
			result = 5;
		}else{
			result = (byte) (productTypeConfigId - Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_BASE);
		}
		return result;
	}
	
	public Integer getProductTypeFromDbProduct(byte productType){
		Integer result = null;
		if(productType == (byte)3){
			result = Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_CZ;
		}else if(productType == (byte)4){
			result = Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_HSJ;
		}else if(productType == (byte)1){
			result = Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_ACBB;
		}else if(productType == (byte)2){
			result = Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_JRFK;
		}else if(productType == (byte)5){
			result = Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_ORTHER;
		}else{
			result = (int)productType + Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_BASE;
		}
		return result;
	}
	
	public Integer getMerchantChannelFromDbMerchantChannel(byte merchantChannel){
		Integer result = null;
		if(merchantChannel == (byte)1){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_GHDX;
		}else if(merchantChannel == (byte)2){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_JRFKDX;
		}else if(merchantChannel == (byte)3){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_TMHQD;
		}else if(merchantChannel == (byte)4){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_JRQD;
		}else if(merchantChannel == (byte)5){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_YKT;
		}else if(merchantChannel == (byte)6){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_TSQD;
		}else if(merchantChannel == (byte)7){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_AJZL;
		}else if(merchantChannel == (byte)8){
			result = Constants.PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_GHZY;
		}else{
			result = (int)merchantChannel + Constants.PRODUCT_CONFIG_ID_PRODUCT_TYPE_BASE;
		}
		return result;
	}
	
	public Integer getAttribTypeFromDeviceType(Integer deviceTypeId){
		Integer result = 0;
		JXCMTDeviceType deviceType = JxcmtDeviceTypeService.getDeviceTypeById(deviceTypeId);
		if(null != deviceType){
			result = deviceType.getAttribInfoType();
		}
		return result;
	}
	
	public JXCMTAttribInfo getAttribInfoById(Integer id){
		JXCMTAttribInfo attribInfo = JxcmtAttribInfoRedis.getJXCMTAttribInfoById(id);
		if(null != attribInfo){
			return attribInfo;
		}
		JXCMTAttribInfo condition = new JXCMTAttribInfo();
		condition.setId(id);
		condition.setDeletedFlag("N");
		attribInfo = JxcmtAttribInfoMapper.selectOne(condition);
		if(null != attribInfo){
			//有问题
			JxcmtAttribInfoRedis.setJXCMTAttribInfoById(attribInfo);
		}
		return attribInfo;
	}
	
	public String getProductTypeNameById(Integer id,Map<Integer,String> mapLocalCache){
		String result = null;
		if(null != mapLocalCache){
			result = mapLocalCache.get(id);
			if(null != result){
				return result;
			}
		}
		JXCMTAttribInfo attribInfo = this.getAttribInfoById(id);
		if(null == attribInfo){
			return result;
		}
		result = attribInfo.getName();
		if(null != mapLocalCache){
			mapLocalCache.put(id, result);
		}
		return result;
	}
	
	public String getMerchantChannelNameById(Integer id,Map<Integer,String> mapLocalCache){
		String result = null;
		if(null != mapLocalCache){
			result = mapLocalCache.get(id);
			if(null != result){
				return result;
			}
		}
		JXCMTAttribInfo attribInfo = this.getAttribInfoById(id);
		if(null == attribInfo){
			return result;
		}
		result = attribInfo.getName();
		if(null != mapLocalCache){
			mapLocalCache.put(id, result);
		}
		return result;
	}
	
	public String getAttribInfoNameById(Integer id,Map<Integer,String> mapLocalCache){
		String result = null;
		if(null != mapLocalCache){
			result = mapLocalCache.get(id);
			if(null != result){
				return result;
			}
		}
		JXCMTAttribInfo attribInfo = this.getAttribInfoById(id);
		if(null == attribInfo){
			return result;
		}
		result = attribInfo.getName();
		if(null != mapLocalCache){
			mapLocalCache.put(id, result);
		}
		return result;
	}
	
}
