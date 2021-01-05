package cn.com.glsx.supplychain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.manager.AttribManaRedisManager;
import cn.com.glsx.supplychain.mapper.AttribManaMapper;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.AttribMana;

@Service
@Transactional
public class AttribManagerSevice {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AttribManaMapper attribManaMapper;
	
	@Autowired
	private AttribManaRedisManager attribManaRedis;
	
	@Autowired
	private AttribInfoService attribInfoService;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	public String guessAttribManaCodeByMnum(String mnum,Integer attribInfoType)
	{
		AttribInfo attribInfo = null;
		List<AttribInfo> listAttribInfo = attribInfoService.listAttribInfo(attribInfoType);
		if(StringUtils.isEmpty(listAttribInfo))
		{
			return "";
		}
		for(AttribInfo item:listAttribInfo)
		{
			if(item.getName().equals(mnum))
			{
				attribInfo = item;
				break;
			}
		}
		if(StringUtils.isEmpty(attribInfo))
		{
			return "";
		}
		AttribMana attribMana = attribManaMapper.guessAttribManaByDevmnumId(attribInfo.getId());
		if(StringUtils.isEmpty(attribMana))
		{
			return "";
		}
		return attribMana.getAttribCode();
	}
	
	public List<AttribMana> listAttribManaCodes(AttribMana record) throws RpcServiceException 
	{
		List<AttribMana> result;
		logger.info("AttribManagerSevice::listAttribManaCodes start record:{}",record);
		try
		{
			result = attribManaMapper.getAttribManaCodeList(record);
		}
		catch(Exception e)
		{
			logger.error("AttribManagerSevice::listAttribManaCodes 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("AttribManagerSevice::listAttribManaCodes end result:{}",result);
		return result;
	}
	
	public AttribMana getAttribManaByManaCode(String manaCode) throws RpcServiceException
	{
		AttribMana result;
		logger.info("AttribManagerSevice::getAttribManaByManaCode start manaCode:"+manaCode);
		try
		{
			result = attribManaRedis.getAttribManaByCode(manaCode);
			if(StringUtils.isEmpty(result))
			{
				result = attribManaMapper.getAttribManaByCode(manaCode);
				if(!StringUtils.isEmpty(result))
				{
					attribManaRedis.setAttribManaByCode(result);
				}
			}
			if(!StringUtils.isEmpty(result))
			{
			    if(result.getModel() != null && !result.getModel().equals(0)){
                    result.setModelName(attribInfoService.getAttribInfoNameById(result.getModel()));
                }
                if(result.getType() != null && !result.getType().equals(0)){
                    result.setTypeName(attribInfoService.getAttribInfoNameById(result.getType()));
                }
                if(result.getConfigure() != null && !result.getConfigure().equals(0)){
                    result.setConfigureName(attribInfoService.getAttribInfoNameById(result.getConfigure()));
                }
                if(result.getMsize() != null && !result.getMsize().equals(0)){
                    result.setMsizeName(attribInfoService.getAttribInfoNameById(result.getMsize()));
                }
                if(result.getDevTypeId() != null && !result.getDevTypeId().equals(0))
                {
                	result.setDevTypeName(deviceTypeService.getDeviceTypeNameById(result.getDevTypeId()));
                }
                if(result.getDevMnumId() != null && !result.getDevMnumId().equals(0))
                {
                	result.setDevMnumName(attribInfoService.getAttribInfoNameById(result.getDevMnumId()));
                }
                if(result.getOrNetId() != null && !result.getOrNetId().equals(0))
                {
                	result.setOrNetName(attribInfoService.getAttribInfoNameById(result.getOrNetId()));
                }
				if(result.getCardSelfId() != null && !result.getCardSelfId().equals(0))
				{
					result.setCardSelfName(attribInfoService.getAttribInfoNameById(result.getCardSelfId()));
				}
				if(result.getSourceId() != null && !result.getSourceId().equals(0))
				{
					result.setSourceName(attribInfoService.getAttribInfoNameById(result.getSourceId()));
				}
				if(result.getScreenId() != null && !result.getScreenId().equals(0))
				{
					result.setScreenName(attribInfoService.getAttribInfoNameById(result.getScreenId()));
				}
				if(result.getOrOpenId() != null && !result.getOrOpenId().equals(0))
				{
					result.setOrOpenName(attribInfoService.getAttribInfoNameById(result.getOrOpenId()));
				}
			}
		}
		catch(Exception e)
		{
			logger.error("AttribManagerSevice::getAttribManaByManaCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("AttribManagerSevice::getAttribManaByManaCode end result:{}",result);
		return result;
	}
}
