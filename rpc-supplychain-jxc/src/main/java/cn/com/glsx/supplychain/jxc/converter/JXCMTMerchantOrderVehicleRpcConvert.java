package cn.com.glsx.supplychain.jxc.converter;

import java.util.ArrayList;
import java.util.List;

import cn.com.glsx.supplychain.jxc.dto.JXCMTMerchantOrderVehicleDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTMerchantOrderVehicle;

public class JXCMTMerchantOrderVehicleRpcConvert {

	public static List<JXCMTMerchantOrderVehicleDTO> convertListBean(List<JXCMTMerchantOrderVehicle> listBean){
		List<JXCMTMerchantOrderVehicleDTO> listResult = new ArrayList<>();
		if(null == listBean || listBean.isEmpty()){
			return listResult;
		}
		for(JXCMTMerchantOrderVehicle vehicle:listBean){
			listResult.add(convertBean(vehicle));
		}
		return listResult;
	}
	
	public static List<JXCMTMerchantOrderVehicle> convertListDto(List<JXCMTMerchantOrderVehicleDTO> listDto){
		List<JXCMTMerchantOrderVehicle> listResult = new ArrayList<>();
		if(null == listDto || listDto.isEmpty()){
			return listResult;
		}
		for(JXCMTMerchantOrderVehicleDTO vehicle:listDto){
			listResult.add(convertDto(vehicle));
		}
		return listResult;
	}
	
	public static JXCMTMerchantOrderVehicleDTO convertBean(JXCMTMerchantOrderVehicle bean){
		if(null == bean){
			return null;
		}
		JXCMTMerchantOrderVehicleDTO dto = new JXCMTMerchantOrderVehicleDTO();
		dto.setMoAudiId(bean.getMoAudiId());
		dto.setMoAudiName(bean.getMoAudiName());
		dto.setMoMotorcycle(bean.getMoMotorcycle());
		dto.setMoOrderCode(bean.getMoOrderCode());
		dto.setMoParentBrandId(bean.getMoParentBrandId());
		dto.setMoParentBrandName(bean.getMoParentBrandName());
		dto.setMoRemark(bean.getMoRemark());
		dto.setMoSubBrandId(bean.getMoSubBrandId());
		dto.setMoSubBrandName(bean.getMoSubBrandName());
		dto.setMoTotal(bean.getMoTotal());
		return dto;
	}
	
	public static JXCMTMerchantOrderVehicle convertDto(JXCMTMerchantOrderVehicleDTO dto){
		if(null == dto){
			return null;
		}
		JXCMTMerchantOrderVehicle bean = new JXCMTMerchantOrderVehicle();
		bean.setMoAudiId(dto.getMoAudiId());
		bean.setMoAudiName(dto.getMoAudiName());
		bean.setMoMotorcycle(dto.getMoMotorcycle());
		bean.setMoOrderCode(dto.getMoOrderCode());
		bean.setMoParentBrandId(dto.getMoParentBrandId());
		bean.setMoParentBrandName(dto.getMoParentBrandName());
		bean.setMoRemark(dto.getMoRemark());
		bean.setMoSubBrandId(dto.getMoSubBrandId());
		bean.setMoSubBrandName(dto.getMoSubBrandName());
		bean.setMoTotal(dto.getMoTotal());
		return bean;
	}
}
