package cn.com.glsx.supplychain.jxc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.com.glsx.supplychain.jxc.manager.JXCUserInfoRedisManager;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTUseInfoMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTUseInfo;

@Service
public class JXCMTUserInfoService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTUserInfoService.class);
	@Autowired
	private JXCUserInfoRedisManager userInfoRedis;
	@Autowired
	private JXCMTUseInfoMapper userInfoMapper;
	
	public JXCMTUseInfo getUserInfoByName(String userName){
		if(StringUtils.isEmpty(userName)){
			return null;
		}
		JXCMTUseInfo result = userInfoRedis.getUserInfoByName(userName);
		if(null != result){
			return result;
		}
		JXCMTUseInfo condition = new JXCMTUseInfo();
		condition.setUserName(userName);
		condition.setDeletedFlag("N");
		result = userInfoMapper.selectOne(condition);
		if(null != result){
			userInfoRedis.setUserInfoByName(result);
		}
		return result;
	}
}
