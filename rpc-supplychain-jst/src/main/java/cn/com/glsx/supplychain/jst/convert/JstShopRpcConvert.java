package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.model.JstShop;

public class JstShopRpcConvert {
	
	private static final Logger logger = LoggerFactory.getLogger(JstShopRpcConvert.class);
	
	public static List<JstShopDTO> convertListBean(List<JstShop> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<JstShopDTO> listDto = new ArrayList<JstShopDTO>();
		for(JstShop bean:record)
		{
			listDto.add(convertBean(bean));
		}
		return listDto;
	}
	
	public static JstShop convertDTO(JstShopDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShop model = new JstShop();
		model.setId(record.getId());
		model.setAddr(record.getAddr());
		model.setCheckFailResult(record.getCheckFailResult());
		model.setContact(record.getContact());
		model.setPhone(record.getPhone());
		model.setRemark(record.getRemark());
		model.setShopCode(record.getShopCode());
		model.setShopMerchantCode(record.getShopMerchantCode());
		model.setShopMerchantName(record.getShopMerchantName());
		model.setShopName(record.getShopName());
		model.setStatus(record.getStatus());
		return model;
	}
	
	public static JstShopDTO convertBean(JstShop record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShopDTO dto = new JstShopDTO();
		dto.setId(record.getId());
		dto.setAddr(record.getAddr());
		dto.setCheckFailResult(record.getCheckFailResult());
		dto.setContact(record.getContact());
		dto.setPhone(record.getPhone());
		dto.setRemark(record.getRemark());
		dto.setShopCode(record.getShopCode());
		dto.setShopMerchantCode(record.getShopMerchantCode());
		dto.setShopMerchantName(record.getShopMerchantName());
		dto.setShopName(record.getShopName());
		dto.setStatus(record.getStatus());
		return dto;
	}
}
