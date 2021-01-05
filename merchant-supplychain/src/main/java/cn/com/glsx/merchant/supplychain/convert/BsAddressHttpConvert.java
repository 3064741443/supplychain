package cn.com.glsx.merchant.supplychain.convert;

import cn.com.glsx.merchant.supplychain.vo.AddressVO;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BsAddressHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(BsAddressHttpConvert.class);
	
	public static List<AddressVO> convertList(List<BsAddressDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<AddressVO> listVo = new ArrayList<AddressVO>();
		for(BsAddressDTO bsAddressDTO:record)
		{
			listVo.add(convertDTO(bsAddressDTO));
		}
		return listVo;
	}
	
	public static BsAddressDTO convertVO(AddressVO record)
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
		return dto;
	}
	
	public static AddressVO convertDTO(BsAddressDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		AddressVO vo = new AddressVO();
		vo.setId(record.getId());
		vo.setName(record.getName());
		vo.setMobile(record.getMobile());
		vo.setProvinceId(record.getProvinceId());
		vo.setProvinceName(record.getProvinceName());
		vo.setCityId(record.getCityId());
		vo.setCityName(record.getCityName());
		vo.setAreaId(record.getAreaId());
		vo.setAreaName(record.getAreaName());
		vo.setAddress(record.getAddress());
		vo.setMerchantCode(record.getMerchantCode());
		return vo;
	}
}
