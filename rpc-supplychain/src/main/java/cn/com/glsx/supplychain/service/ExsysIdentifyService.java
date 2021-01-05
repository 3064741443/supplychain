package cn.com.glsx.supplychain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.ExsysIdentifyRedisManager;
import cn.com.glsx.supplychain.mapper.ExsysIdentifyMapper;
import cn.com.glsx.supplychain.model.ExsysIdentify;

/**
* 
* @Title: ExsysIdentifyRedisManager 
* @Description: 数据分发外部系统定义服务
* @throws
*/
@Service
public class ExsysIdentifyService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExsysIdentifyRedisManager exsysIdentifyRedis;
	@Autowired
	private ExsysIdentifyMapper	exsysIdentifyMapper;
	
	/**
	* 
	* @Title: getExsysIdentifyBySystemFlag 
	* @Description: 
	* @param @systemFlag
	* @return String
	* @throws
	*/
	public ExsysIdentify getExsysIdentifyBySystemFlag(String systemFlag, String strType) throws RpcServiceException
	{
		ExsysIdentify exsysIdentify = null;
		logger.info("ExsysIdentifyService::getExsysIdentifyBySystemFlag start systemFlag:" + systemFlag + " strType:" + strType);
		try
		{
			exsysIdentify = exsysIdentifyRedis.getExsysIdentifyBySystemFlag(systemFlag, strType);
			if(StringUtils.isEmpty(exsysIdentify))
			{
				ExsysIdentify condition = new ExsysIdentify();
				condition.setSystemFlag(systemFlag);
				condition.setType(strType);
				exsysIdentify = exsysIdentifyMapper.selectOne(condition);
				if(!StringUtils.isEmpty(exsysIdentify))
				{
					exsysIdentifyRedis.setExsysIdentifyBySystemFlag(exsysIdentify);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("ExsysIdentifyService::getExsysIdentifyBySystemFlag 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ExsysIdentifyService::getExsysIdentifyBySystemFlag end exsysIdentify:{}",exsysIdentify);
		return exsysIdentify;
	}
}
