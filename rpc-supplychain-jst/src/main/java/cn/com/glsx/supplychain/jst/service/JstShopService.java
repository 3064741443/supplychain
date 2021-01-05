package cn.com.glsx.supplychain.jst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.convert.JstShopAgentRelationRpcConvert;
import cn.com.glsx.supplychain.jst.convert.JstShopRpcConvert;
import cn.com.glsx.supplychain.jst.dto.DisShopDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopAgentRelationDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.JstShopStatusEnum;
import cn.com.glsx.supplychain.jst.mapper.JstShopAgentRelationMapper;
import cn.com.glsx.supplychain.jst.mapper.JstShopMapper;
import cn.com.glsx.supplychain.jst.model.JstShop;
import cn.com.glsx.supplychain.jst.model.JstShopAgentRelation;

@Service
public class JstShopService {

	private static final Logger logger = LoggerFactory.getLogger(JstShopService.class);
	
	@Autowired
	private JstShopMapper jstShopMapper;
	@Autowired
	private JstShopAgentRelationMapper agentRelationMapper;
	
	public JstShop getJspShopByShopcode(String shopCode) throws RpcServiceException{
		JstShop result;
		logger.info("JstShopService::getJspShopByShopcode start shopCode:{}",shopCode);
		try
		{
			JstShop condition = new JstShop();
			condition.setShopCode(shopCode);
			result = jstShopMapper.selectOne(condition);
		}
		catch(Exception e)
		{
			logger.error("JstShopService::getJspShopByShopcode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstShopService::getJspShopByShopcode end result:{}",result);
		return result;
	}
	
	public JstShop getJspShopByShopMerchantCode(String shopMerchantCode) throws RpcServiceException{
		
		JstShop result;
		logger.info("JstShopService::getJspShopByShopMerchantCode start shopMerchantCode:{}",shopMerchantCode);
		try
		{
			JstShop condition = new JstShop();
			condition.setShopMerchantCode(shopMerchantCode);
			result = jstShopMapper.selectOne(condition);
		}
		catch(Exception e)
		{
			logger.error("JstShopService::getJspShopByShopMerchantCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstShopService::getJspShopByShopMerchantCode end result:{}",result);
		return result;
	} 
	
	public List<JstShopAgentRelationDTO> listShopAgentMerchant(JstShopDTO recordDTO) throws RpcServiceException
	{
		List<JstShopAgentRelationDTO> result = null;
		logger.info("JstShopService::listShopAgentMerchant start recordDTO:{}",recordDTO);
		try
		{
			JstShop jstShopCondition = new JstShop();
			jstShopCondition.setShopCode(recordDTO.getShopCode());
			jstShopCondition.setShopMerchantCode(recordDTO.getShopMerchantCode());
			JstShop jstShop = jstShopMapper.selectOne(jstShopCondition);
			if(!StringUtils.isEmpty(jstShop))
			{
				Example example = new Example(JstShopAgentRelation.class);
				example.createCriteria().andEqualTo("shopCode",jstShop.getShopCode());
				example.createCriteria().andEqualTo("status",JstShopStatusEnum.PS.getCode());
				List<JstShopAgentRelation> listRelation = agentRelationMapper.selectByExample(example);
				result = JstShopAgentRelationRpcConvert.convertBeanList(listRelation);
			}
		}
		catch(Exception e)
		{
			logger.error("JstShopService::listShopAgentMerchant 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstShopService::listShopAgentMerchant end result:{}",result);
		return result;
	}
	
	public JstShopAgentRelationDTO getShopAgentMerchant(JstShopAgentRelationDTO recordDTO) throws RpcServiceException
	{
		JstShopAgentRelationDTO result = null;
		logger.info("JstShopService::getShopAgentMerchant start recordDTO:{}",recordDTO);
		try
		{
			JstShopAgentRelation condition = new JstShopAgentRelation();
			condition.setShopCode(recordDTO.getShopCode());
			condition.setAgentMerchantCode(recordDTO.getAgentMerchantCode());
			JstShopAgentRelation jstShopAgentRelation = agentRelationMapper.selectOne(condition);
			result = JstShopAgentRelationRpcConvert.convertBean(jstShopAgentRelation);
		}
		catch(Exception e)
		{
			logger.error("JstShopService::getShopAgentMerchant 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstShopService::getShopAgentMerchant end result:{}",result);
		return result;
	}
	
	public JstShopAgentRelation getShopAgentMerchantBean(String shopCode, String shopAgentMerchantCode)
	{
		JstShopAgentRelation condition = new JstShopAgentRelation();
		condition.setShopCode(shopCode);
		condition.setAgentMerchantCode(shopAgentMerchantCode);
		return agentRelationMapper.selectOne(condition);
	}
	
	public DisShopDTO pageJstShopByAgentMerchant(DisShopDTO record) throws RpcServiceException{
		JstShop jstShopCondition = new JstShop();
		jstShopCondition.setAgentMerchantCode(record.getAgentMerchantCode());
		jstShopCondition.setShopCode(record.getShopCode());
		jstShopCondition.setShopName(record.getShopName());
		jstShopCondition.setShopMerchantCode(record.getShopMerchantCode());
		jstShopCondition.setShopMerchantName(record.getShopMerchantName());
		jstShopCondition.setAddr(record.getAddr());
		jstShopCondition.setContact(record.getContact());
		jstShopCondition.setPhone(record.getPhone());
		jstShopCondition.setPageStart((record.getPageNo()-1)*record.getPageSize());
		jstShopCondition.setPageSize(record.getPageSize());
		if(!StringUtils.isEmpty(record.getShopName()) && 
				!StringUtils.isEmpty(record.getAddr()) && 
				!StringUtils.isEmpty(record.getContact()) &&
				!StringUtils.isEmpty(record.getPhone()) &&
				record.getShopName().equals(record.getAddr()) &&
				record.getAddr().equals(record.getContact()) &&
				record.getContact().equals(record.getPhone())){
			jstShopCondition.setConcatKey(record.getShopName());
		}
		try{
			List<JstShop> listJstShop = jstShopMapper.pageJstShopByAgentMerchant(jstShopCondition);
			record.setListShopDto(JstShopRpcConvert.convertListBean(listJstShop));
		}catch(Exception e)
		{
			logger.error("JstShopService::listJstShopByAgentMerchant 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		return record;
	}
	
	public List<JstShopDTO> listJstShopByAgentMerchant(String agentMerchantCode) throws RpcServiceException
	{
		List<JstShopDTO> result = null;
		logger.info("JstShopService::listJstShopByAgentMerchant start agentMerchantCode:{}",agentMerchantCode);
		try
		{
			List<JstShop> listBean = jstShopMapper.listJstShopByAgentMerchantCode(agentMerchantCode);
			result = JstShopRpcConvert.convertListBean(listBean);
		}
		catch(Exception e)
		{
			logger.error("JstShopService::listJstShopByAgentMerchant 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("JstShopService::listJstShopByAgentMerchant end result:{}",result);
		return result;
	}
}
