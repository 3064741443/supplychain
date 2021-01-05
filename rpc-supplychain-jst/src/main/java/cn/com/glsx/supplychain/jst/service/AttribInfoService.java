package cn.com.glsx.supplychain.jst.service;

import java.util.List;
import java.util.Map;

import cn.com.glsx.supplychain.jst.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.glsx.supplychain.jst.dto.gh.GhProductConfigDTO;
import cn.com.glsx.supplychain.jst.manager.AttribInfoRedisManager;
import cn.com.glsx.supplychain.jst.mapper.AttribInfoMapper;
import cn.com.glsx.supplychain.jst.model.AttribInfo;

@Service
public class AttribInfoService {

	private static final Logger logger = LoggerFactory.getLogger(AttribInfoService.class);
	@Autowired
	private AttribInfoRedisManager attribInfoRedis;
	@Autowired
	private AttribInfoMapper attribInfoMapper;
	
	public List<AttribInfo> listAttribInfo(AttribInfo record){
		Example example = new Example(AttribInfo.class);
		Criteria criteria = example.createCriteria();
		if(null != record.getType()){
			criteria.andEqualTo("type",record.getType());
		}
		if(!StringUtils.isEmpty(record.getName())){
			criteria.andLike("name", "%" + record.getName() + "%");
		}
		if(!StringUtils.isEmpty(record.getComment())){
			criteria.andLike("comment", "%" + record.getComment() + "%");
		}
		if(null != record.getId()){
			criteria.andEqualTo("id", record.getId());
		}
		return attribInfoMapper.selectByExample(example);
	}
	
	public AttribInfo getAttribInfoWithLocalCache(Integer attribInfoId,Map<Integer,AttribInfo> mapLocalCache){
		AttribInfo result = mapLocalCache.get(attribInfoId);
		if(null != result){
			return result;
		}
		result = getAttribInfo(attribInfoId);
		if(null != result){
			mapLocalCache.put(attribInfoId, result);
		}
		return result;
	}
	
	public AttribInfo getAttribInfo(Integer attribInfoId){
		AttribInfo result = attribInfoRedis.getAttribInfoById(attribInfoId);
		if(null != result){
			return result;
		}
		AttribInfo condition = new AttribInfo();
		condition.setId(attribInfoId);
		condition.setDeletedFlag("N");
		result = attribInfoMapper.selectOne(condition);
		if(null != result){
			attribInfoRedis.setAttribInfoById(result);
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

	public String getMerchantChannelNameById(Integer id,Map<Integer,String> mapLocalCache){
		String result = null;
		if(null != mapLocalCache){
			result = mapLocalCache.get(id);
			if(null != result){
				return result;
			}
		}
		AttribInfo attribInfo = this.getAttribInfo(id);
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
