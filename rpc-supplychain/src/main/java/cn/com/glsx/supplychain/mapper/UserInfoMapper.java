package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.UserInfo;

import com.github.pagehelper.Page;

/**
 * @Title: DemoMapper.java
 * @Description:
 * @author leiyj
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */

@Mapper
public interface UserInfoMapper extends OreMapper<UserInfo> {
	
	Page<UserInfo> listUserInfo(UserInfo userInfo);
	
	int count(UserInfo record);
	
	int deleteUserInfo(Integer id);

    int insertUserInfo(UserInfo record);

    int update(UserInfo record);
    
    UserInfo getUserInfoById(Integer id);
    
    UserInfo getUserInfoByUserName(@Param(value="userName")String userName);
    
    UserInfo getUserInfoByByUpdate(UserInfo record);
    
    UserInfo logins(UserInfo userInfo);
}
