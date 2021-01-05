package cn.com.glsx.rpc.supplychain.rdn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.rpc.supplychain.rdn.common.Constants;
import cn.com.glsx.rpc.supplychain.rdn.manager.AttribInfoRedisManager;
import cn.com.glsx.rpc.supplychain.rdn.mapper.AttribInfoMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.AttribInfo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.AttribInfoVo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.MerchantChannelVo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.ProductTypeVo;

@Service
public class AttribInfoService {

	private static final Logger logger = LoggerFactory.getLogger(AttribInfoService.class);
	
	@Autowired
	private AttribInfoMapper JxcmtAttribInfoMapper;
	@Autowired
	private AttribInfoRedisManager attribInfoRedis; 


	public List<AttribInfoVo> listAttribInfoByAttribInfoType(AttribInfoVo record){
		List<AttribInfoVo> listResult = new ArrayList<>();
		Example example = new Example(AttribInfo.class);
		example.createCriteria().andEqualTo("type",record.getType())
								.andEqualTo("deletedFlag","N");
		List<AttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResult;
		}
		AttribInfoVo dtoObject = null;
		for(AttribInfo attribInfo:listAttribInfo){
			dtoObject = new AttribInfoVo();
			dtoObject.setId(attribInfo.getId());
			dtoObject.setName(attribInfo.getName());
			listResult.add(dtoObject);
		}
		return listResult;
	}
	

	public List<ProductTypeVo> listProductType(ProductTypeVo record){
		List<ProductTypeVo> listResultDto = new ArrayList<>();
		Example example = new Example(AttribInfo.class);
		example.createCriteria().andEqualTo("type", Constants.PRODUCT_CONFIG_TYPE_PRODUCT_TYPE)
								.andEqualTo("deletedFlag","N");
		List<AttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResultDto;
		}
		ProductTypeVo dtoObject = null;
		for(AttribInfo attribInfo:listAttribInfo){
			dtoObject = new ProductTypeVo();
			dtoObject.setId(attribInfo.getId());
			dtoObject.setName(attribInfo.getName());
			listResultDto.add(dtoObject);
		}
		return listResultDto;
	}
	
	public List<MerchantChannelVo> listMerchantChannel(MerchantChannelVo record){
		List<MerchantChannelVo> listResultDto = new ArrayList<>();
		Example example = new Example(AttribInfo.class);
		example.createCriteria().andEqualTo("type", Constants.PRODUCT_CONFIG_TYPE_MERCHANT_CHANNEL)
								.andEqualTo("deletedFlag","N");
		List<AttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo){
			return listResultDto;
		}
		MerchantChannelVo dtoObject = null;
		for(AttribInfo attribInfo:listAttribInfo){
			dtoObject = new MerchantChannelVo();
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
		Example example = new Example(AttribInfo.class);
		example.createCriteria().andIn("id", listIds);
		List<AttribInfo> listAttribInfo = JxcmtAttribInfoMapper.selectByExample(example);
		if(null == listAttribInfo || listAttribInfo.isEmpty()){
			return mapResult;
		}
		for(AttribInfo attribInfo:listAttribInfo){
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
	
	public AttribInfo getAttribInfoById(Integer id){
		AttribInfo attribInfo = attribInfoRedis.getAttribInfoById(id);
		if(null != attribInfo){
			return attribInfo;
		}
		AttribInfo condition = new AttribInfo();
		condition.setId(id);
		condition.setDeletedFlag("N");
		attribInfo = JxcmtAttribInfoMapper.selectOne(condition);
		if(null != attribInfo){
			attribInfoRedis.setAttribInfoById(attribInfo);
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
		AttribInfo attribInfo = this.getAttribInfoById(id);
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
		AttribInfo attribInfo = this.getAttribInfoById(id);
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
		AttribInfo attribInfo = this.getAttribInfoById(id);
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
