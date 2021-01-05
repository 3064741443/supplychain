package cn.com.glsx.supplychain.jst.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.convert.JstUserOpenIdDtoRpcConvert;
import cn.com.glsx.supplychain.jst.dto.JstUserOpenIdDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.JstUserOpenIdMapper;
import cn.com.glsx.supplychain.jst.model.JstUserOpenId;

@Service
public class JstUserOpenIdService {
	
	private static final Logger logger = LoggerFactory.getLogger(JstUserOpenIdService.class);
	
	@Autowired
	private JstUserOpenIdMapper userOpenIdMapper;
	
	public JstUserOpenIdDTO getJstUserOpenId(JstUserOpenIdDTO record) throws RpcServiceException
	{
		JstUserOpenIdDTO result = null;
		logger.info("JstUserOpenIdService::getJstUserOpenId start record:{}",record);
		try
		{
			JstUserOpenId condition = new JstUserOpenId();
			condition.setOpenid(record.getOpenid());
			condition.setDeletedFlag(record.getDeletedFlag());
			JstUserOpenId model = userOpenIdMapper.selectOne(condition);
			result = JstUserOpenIdDtoRpcConvert.convertBeen(model);
		}
		catch(Exception e)
		{
			logger.error("JstUserOpenIdService::getJstUserOpenId 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstUserOpenIdService::getJstUserOpenId end result:{}",result);
		return result;
	}
	
	
	public JstUserOpenIdDTO saveJstUserOpenId(JstUserOpenIdDTO record) throws RpcServiceException
	{
		JstUserOpenIdDTO result = null;
		logger.info("JstUserOpenIdService::saveJstUserOpenId start record:{}",record);
		try
		{
			JstUserOpenId param = JstUserOpenIdDtoRpcConvert.convertDto(record);
			JstUserOpenId condition = new JstUserOpenId();
			condition.setOpenid(param.getOpenid());
			JstUserOpenId model = userOpenIdMapper.selectOne(condition);
			if(!StringUtils.isEmpty(model))
			{
				param.setId(model.getId());
				userOpenIdMapper.updateByPrimaryKeySelective(param);
			}
			else
			{
				userOpenIdMapper.insertSelective(param);
			}
			result = JstUserOpenIdDtoRpcConvert.convertBeen(userOpenIdMapper.selectOne(condition));
		}
		catch(Exception e)
		{
			logger.error("JstUserOpenIdService::saveJstUserOpenId 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstUserOpenIdService::saveJstUserOpenId end result:{}",result);
		return result;
	}
	
}
