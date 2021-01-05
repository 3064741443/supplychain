package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.model.BsAddress;

public class BsAddressRpcConvert {
	
	private static final Logger logger = LoggerFactory.getLogger(BsAddressRpcConvert.class);

	public static List<BsAddressDTO> convertList(List<BsAddress> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<BsAddressDTO> listDto = new ArrayList<BsAddressDTO>();
		for(BsAddress bsAddress:record)
		{
			listDto.add(convertBean(bsAddress));
		}
		return listDto;
	}
	
	public static BsAddressDTO convertBean(BsAddress record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsAddressDTO dto = new BsAddressDTO();
		dto.setId(record.getId());
		dto.setName(record.getName());
		dto.setMobile(record.getMobile());
		dto.setProvinceId(record.getProvinceId());
		dto.setProvinceName(record.getProvinceName());
		dto.setCityId(record.getCityId());
		dto.setCityName(record.getCityName());
		dto.setAreaId(record.getAreaId());
		dto.setAreaName(record.getAreaName());
		dto.setAddress(record.getAddress());
		dto.setMerchantCode(record.getMerchantCode());
		dto.setDeletedFlag(record.getDeletedFlag());
		return dto;	
	}
	
	public static BsAddress convertDTO(BsAddressDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsAddress model = new BsAddress();
		model.setId(record.getId());
		model.setName(record.getName());
		model.setMobile(record.getMobile());
		model.setProvinceId(record.getProvinceId());
		model.setProvinceName(record.getProvinceName());
		model.setCityId(record.getCityId());
		model.setCityName(record.getCityName());
		model.setAreaId(record.getAreaId());
		model.setAreaName(record.getAreaName());
		model.setAddress(record.getAddress());
		model.setMerchantCode(record.getMerchantCode());
		model.setDeletedFlag(record.getDeletedFlag());
		model.setCreatedBy(record.getMerchantCode());
		model.setUpdatedBy(record.getMerchantCode());
		return model;
	}
}
