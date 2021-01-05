package cn.com.glsx.supplychain.jxc.converter;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsAddress;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class JXCMTBsAddressRpcConvert {
	
	public static List<JXCMTBsAddressDTO> convertListBean(List<JXCMTBsAddress> listBean){
		if(null == listBean || listBean.isEmpty()){
			return null;
		}
		List<JXCMTBsAddressDTO> listDto = new ArrayList<>();
		for(JXCMTBsAddress bean:listBean){
			listDto.add(convertBean(bean));
		}
		return listDto;
	}
	
	public static List<JXCMTBsAddress> convertListDto(List<JXCMTBsAddressDTO> listDto){
		if(null == listDto || listDto.isEmpty()){
			return null;
		}
		List<JXCMTBsAddress> listBean = new ArrayList<>();
		for(JXCMTBsAddressDTO dto:listDto){
			listBean.add(converDto(dto));
		}
		return listBean;
	}
	
	public static JXCMTBsAddress converDto(JXCMTBsAddressDTO dto){
		if(null == dto){
			return null;
		}
		JXCMTBsAddress bean = new JXCMTBsAddress();
		if(null != dto.getId()){
			bean.setId(dto.getId().longValue());
		}
		bean.setAddress(dto.getAddress());
		bean.setAreaId(dto.getAreaId());
		bean.setAreaName(dto.getAreaName());
		bean.setCityId(dto.getCityId());
		bean.setCityName(dto.getCityName());
		bean.setMerchantCode(dto.getMerchantCode());
		bean.setMobile(dto.getMobile());
		bean.setMerchantCode(dto.getMerchantCode());
		bean.setMobile(dto.getMobile());
		bean.setName(dto.getName());
		bean.setProvinceId(dto.getProvinceId());
		bean.setProvinceName(dto.getProvinceName());
	//	BeanUtils.copyProperties(dto, bean);
		return bean;
	}
	
	public static JXCMTBsAddressDTO convertBean(JXCMTBsAddress bean){
		if(null == bean){
			return null;
		}
		JXCMTBsAddressDTO dto = new JXCMTBsAddressDTO();
		dto.setId(bean.getId().intValue());
		dto.setName(bean.getName());
		dto.setMobile(bean.getMobile());
		dto.setAddress(bean.getAddress());
		dto.setAreaId(bean.getAreaId());
		dto.setAreaName(bean.getAreaName());
		dto.setCityId(bean.getCityId());
		dto.setCityName(bean.getCityName());
		dto.setProvinceId(bean.getProvinceId());
		dto.setProvinceName(bean.getProvinceName());
		return dto;
	}
}
