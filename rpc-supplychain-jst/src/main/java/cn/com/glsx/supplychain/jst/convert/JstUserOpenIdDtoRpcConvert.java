package cn.com.glsx.supplychain.jst.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstUserOpenIdDTO;
import cn.com.glsx.supplychain.jst.model.JstUserOpenId;

public class JstUserOpenIdDtoRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(JstUserOpenIdDtoRpcConvert.class);
	
	public static JstUserOpenId convertDto(JstUserOpenIdDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstUserOpenId model = new JstUserOpenId();
		model.setConsumer(record.getConsumer());
		model.setCreatedBy(record.getCreatedBy());
		model.setUpdatedBy(record.getUpdatedBy());
		model.setCreatedDate(record.getCreatedDate());
		model.setUpdatedDate(record.getUpdatedDate());
		model.setDeletedFlag(record.getDeletedFlag());
		model.setId(record.getId());
		model.setLoginType(record.getLoginType());
		model.setMerchantCode(record.getMerchantCode());
		model.setMerchantName(record.getMerchantName());
		model.setOpenid(record.getOpenid());
		model.setPassword(record.getPassword());
		model.setPhone(record.getPhone());
		model.setRole(record.getRole());
		model.setShopCode(record.getShopCode());
		model.setShopName(record.getShopName());
		model.setVersion(record.getVersion());
		return model;
	}
	
	public static JstUserOpenIdDTO convertBeen(JstUserOpenId record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstUserOpenIdDTO dto = new JstUserOpenIdDTO();
		dto.setConsumer(record.getConsumer());
		dto.setCreatedBy(record.getCreatedBy());
		dto.setUpdatedBy(record.getUpdatedBy());
		dto.setCreatedDate(record.getCreatedDate());
		dto.setUpdatedDate(record.getUpdatedDate());
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setLoginType(record.getLoginType());
		dto.setMerchantCode(record.getMerchantCode());
		dto.setMerchantName(record.getMerchantName());
		dto.setOpenid(record.getOpenid());
		dto.setPassword(record.getPassword());
		dto.setPhone(record.getPhone());
		dto.setRole(record.getRole());
		dto.setShopCode(record.getShopCode());
		dto.setShopName(record.getShopName());
		dto.setVersion(record.getVersion());
		return dto;
	}
}
