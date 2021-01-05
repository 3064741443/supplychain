package cn.com.glsx.supplychain.service;


import java.util.Date;

import net.sf.json.JSONSerializer;

import org.oreframework.util.encrypt.Md5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.exception.ServiceException;
import cn.com.glsx.supplychain.manager.UserInfoRedisManager;
import cn.com.glsx.supplychain.mapper.UserInfoMapper;
import cn.com.glsx.supplychain.model.UserInfo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Title: DemoService.java
 * @Description:用户信息服务类
 * @author leiyj
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class UserInfoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserInfoRedisManager userInfoRedis;


	/** 
	 * @Title: listUserInfo 
	 * @Description: 查询用户信息列表
	 * @param @param requestEntity
	 * @param @return
	 * @param @throws ServiceException 
	 * @return ResponseEntity<UserInfo>
	 * @throws
	 */
	public Page<UserInfo> listUserInfo(RpcPagination<UserInfo> pagination,UserInfo userInfo) throws RpcServiceException{
		
		
		//判断条件是否为空
		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");		
		logger.info("查询订单列表传入参数："  + JSONSerializer.toJSON(pagination).toString());
		
		try {
			//设置分页数据
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			//返回结果集
			return userInfoMapper.listUserInfo(userInfo);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/** 
	 * @Title: count 
	 * @Description: 统计用户总数
	 * @param @param record
	 * @param @return 
	 * @return int
	 * @throws
	 */
	public int count(UserInfo record) throws RpcServiceException {		
		try {
			return userInfoMapper.count(record);
		} catch (RpcServiceException e) {
			logger.info("统计用户数据异常：" + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: delete 
	 * @Description: 删除用户信息
	 * @param @param id
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int delete(Integer id) throws RpcServiceException {
		logger.info("删除用户数据传入参数ID：" + id);

		try 
		{
			UserInfo userInfo = userInfoRedis.getUserInfoById(id);
			if(!StringUtils.isEmpty(userInfo))
			{
				userInfoRedis.delUserInfoById(userInfo.getId());
				userInfoRedis.delUserInfoByName(userInfo.getUserName());
			}
			return userInfoMapper.deleteUserInfo(id);
		} 
		catch (RpcServiceException e)
		{
			logger.info("删除用户数据异常：" + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: insert 
	 * @Description: 插入用户信息
	 * @param @param record
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int insert(UserInfo record) throws RpcServiceException {
		logger.info("UserInfoService::insert record:{}",record);
		//判断是否为修改/新增
		if(StringUtils.isEmpty(record.getId()))
		{		
			try
			{
				UserInfo userInfo = this.getUserInfoByUserName(record.getUserName());
				if(!StringUtils.isEmpty(userInfo))
				{
					logger.info("新增的用户:" + record.getUserName() + ",已经存在");
					throw new ServiceException(ServiceException.ErrorCode.EXIST,"用户已经存在");
				}
				
				record.setCreatedDate(new Date());
				record.setUpdatedDate(new Date());
				record.setPassword(Md5Encrypt.md5(record.getPassword()));
				return userInfoMapper.insertUserInfo(record);
			}
			catch (Exception e)
			{
				logger.error("新增用户信息异常," + e.getMessage(),e);
				throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
			}
		}
		else
		{
			logger.info("更新用户入口");
			//查询除当前用户信息外,是否存在相同用户名称
			try
			{
				record.setPassword(Md5Encrypt.md5(record.getPassword()));
				return update(record);
			}
			catch (Exception e)
			{
				logger.error("修改用户信息异常," + e.getMessage(),e);
				throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @Title: update 
	 * @Description: 更新用户信息
	 * @param @param record
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int update(UserInfo record) throws RpcServiceException  {
		try 
		{
			record.setUpdatedDate(new Date());
			userInfoMapper.update(record);
			UserInfo userInfo = userInfoMapper.getUserInfoById(record.getId());
			if(!StringUtils.isEmpty(userInfo))
			{
				userInfoRedis.setUserInfoById(userInfo);
				userInfoRedis.setUserInfoByName(userInfo);
			}
			return 1;
		} 
		catch (Exception e) 
		{
			logger.error("更新用户信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getUserInfoById 
	 * @Description: 通过ID查询用户信息
	 * @param @param id
	 * @param @return
	 * @param @throws ServiceException 
	 * @return UserInfo
	 * @throws
	 */
	public UserInfo getUserInfoById(Integer id) throws RpcServiceException {
		try 
		{
			logger.info("UserInfoService::getUserInfoById start id:" + id);
			UserInfo userInfo = userInfoRedis.getUserInfoById(id);
			if(!StringUtils.isEmpty(userInfo))
			{
				logger.info("UserInfoService::getUserInfoById end userInfo:{}", userInfo);
				return userInfo;
			}
			userInfo = userInfoMapper.getUserInfoById(id);
			if(!StringUtils.isEmpty(userInfo))
			{
				userInfoRedis.setUserInfoById(userInfo);
			}
			logger.info("UserInfoService::getUserInfoById end userInfo:{}", userInfo);
			return userInfo; 
		}
		catch (Exception e) 
		{
			logger.error("通过ID查询用户信息异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	
	/** 
	 * @Title: getUserInfoByUserName 
	 * @Description:根据用户名称查询用户信息
	 * @param @param record
	 * @param @return 
	 * @return OrderInfo
	 * @throws
	 */
	public UserInfo getUserInfoByUserName(String userName) throws RpcServiceException {
		try 
		{
			logger.info("UserInfoService::getUserInfoByUserName start userName:" + userName);
			UserInfo userInfo = userInfoRedis.getUserInfoByName(userName);
			if(!StringUtils.isEmpty(userInfo))
			{
				logger.info("UserInfoService::getUserInfoByUserName end userInfo:{}", userInfo);
				return userInfo;
			}
			userInfo = userInfoMapper.getUserInfoByUserName(userName);
			if(!StringUtils.isEmpty(userInfo))
			{
				userInfoRedis.setUserInfoByName(userInfo);
			}
			logger.info("UserInfoService::getUserInfoByUserName end userInfo:{}", userInfo);
			return userInfo;
		} 
		catch (Exception e)
		{
			logger.error("根据用户名称查询用户信息异常," + e.getMessage(),e);
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED,e.getMessage());
		}
	}
	
}
