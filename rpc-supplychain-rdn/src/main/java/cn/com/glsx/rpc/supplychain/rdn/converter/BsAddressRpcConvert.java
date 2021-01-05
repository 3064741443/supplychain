package cn.com.glsx.rpc.supplychain.rdn.converter;

import org.springframework.beans.BeanUtils;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.Address;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsAddressVo;

import java.util.ArrayList;
import java.util.List;

public class BsAddressRpcConvert {
	
	public static List<BsAddressVo> convertListBean(List<Address> listBean){
		if(null == listBean || listBean.isEmpty()){
			return null;
		}
		List<BsAddressVo> listDto = new ArrayList<>();
		for(Address bean:listBean){
			listDto.add(convertBean(bean));
		}
		return listDto;
	}
	
	public static List<Address> convertListDto(List<BsAddressVo> listDto){
		if(null == listDto || listDto.isEmpty()){
			return null;
		}
		List<Address> listBean = new ArrayList<>();
		for(BsAddressVo dto:listDto){
			listBean.add(converDto(dto));
		}
		return listBean;
	}
	
	public static Address converDto(BsAddressVo dto){
		if(null == dto){
			return null;
		}
		Address bean = new Address();
		BeanUtils.copyProperties(dto, bean);
		return bean;
	}
	
	public static BsAddressVo convertBean(Address bean){
		if(null == bean){
			return null;
		}
		BsAddressVo dto = new BsAddressVo();
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
