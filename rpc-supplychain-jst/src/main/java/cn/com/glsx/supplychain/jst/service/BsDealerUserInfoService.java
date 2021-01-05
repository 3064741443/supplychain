package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.convert.BsDealerUserInfoRpcConvert;
import cn.com.glsx.supplychain.jst.dto.DisDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.BsDealerUserInfoMapper;
import cn.com.glsx.supplychain.jst.model.BsDealerUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BsDealerUserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(BsDealerUserInfoService.class);
	
	@Autowired
	BsDealerUserInfoMapper bsDealerUserInfoMapper;
	
	public BsDealerUserInfo 	getBsDealerUserInfoByMerchantCode(String merchantCode) throws RpcServiceException
	{
		BsDealerUserInfo result;
		logger.info("BsDealerUserInfoService::getBsDealerUserInfoByMerchantCode start merchantCode:{}",merchantCode);
		try
		{
			BsDealerUserInfo condition = new BsDealerUserInfo();
			condition.setMerchantCode(merchantCode);
			result = bsDealerUserInfoMapper.selectOne(condition);	
		}
		catch(Exception e)
		{
			logger.error("BsDealerUserInfoService::getBsDealerUserInfoByMerchantCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsDealerUserInfoService::getBsDealerUserInfoByMerchantCode end result:{}",result);
		return result;
	}
	
	public DisDealerUserInfoDTO listBsDealerUserInfo(DisDealerUserInfoDTO record) throws RpcServiceException
	{
		logger.info("BsDealerUserInfoService::listBsDealerUserInfo start record:{}",record);
		try
		{
			List<BsDealerUserInfo> listBsDealerUserInfo = bsDealerUserInfoMapper.listByMerchantCode(record.getListMerchantCode());
			record.setListDealerUserInfoDTO(BsDealerUserInfoRpcConvert.convertBeanList(listBsDealerUserInfo));
		}
		catch(Exception e)
		{
			logger.error("BsDealerUserInfoService::listBsDealerUserInfo 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsDealerUserInfoService::listBsDealerUserInfo end record:{}",record);
		return record;
	}
}
