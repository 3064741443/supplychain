package cn.com.glsx.supplychain.service;

import java.util.List;
import java.util.Map;

import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.manager.AttribInfoRedisManager;
import cn.com.glsx.supplychain.mapper.AttribInfoMapper;
import cn.com.glsx.supplychain.model.AttribInfo;

/**
 * @Title: DemoService.java
 * @Description: 属性信息服务类
 * @author leiyj
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class AttribInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttribInfoMapper attribInfoMapper;
	
	@Autowired
	private AttribInfoRedisManager attribInfoRedis;

	/** 
	 * @Title: listAttribInfo 
	 * @Description:查询配置信息
	 * @param @param type
	 * @param @return 
	 * @return List<AttribInfo>
	 * @throws
	 */
	public List<AttribInfo> listAttribInfo(Integer type){
		return attribInfoMapper.getAttribInfoByList(type);
	}
	
	public Map<Integer, String> mapAttriInfo(Integer type){
		return attribInfoMapper.mapAttrInfo(type);
	}
	
	/**
	* 
	* @Title: getAttribInfoNameById 
	* @Description: 通过ID查询对应name值
	* @param @param id
	* @param @return 
	* @return String
	* @throws
	*/
	public String getAttribInfoNameById(Integer id)
	{
		logger.info("AttribInfoService::getAttribInfoNameById start id:" + id);
		AttribInfo attribInfo = attribInfoRedis.getAttribInfoById(id);
		if(StringUtils.isEmpty(attribInfo))
		{
			attribInfo = attribInfoMapper.getAttribInfoById(id);
			if(!StringUtils.isEmpty(attribInfo))
			{
				attribInfoRedis.setAttribInfoById(attribInfo);
			}
		}
		logger.info("AttribInfoService::getAttribInfoNameById end attribInfo:{}",attribInfo);
		return StringUtils.isEmpty(attribInfo)?null:attribInfo.getName();
	}
	
	public Page<AttribInfo> pageAttribInfo(RpcPagination<AttribInfo> pagination) throws RpcServiceException
	{
		try
		{
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			return attribInfoMapper.pageAttribInfo(pagination.getCondition());
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(e.getMessage());
		}
	}
	
	
}
