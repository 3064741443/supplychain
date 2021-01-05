package cn.com.glsx.supplychain.jxc.converter;

import java.util.ArrayList;
import java.util.List;

import cn.com.glsx.supplychain.jxc.dto.JXCMTMerchantOrderMaterialDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTMerchantOrderMaterial;

public class JXCMTMerchantOrderMaterialRpcConvert {
	
	public static List<JXCMTMerchantOrderMaterialDTO> convertListBean(List<JXCMTMerchantOrderMaterial> listBean){
		List<JXCMTMerchantOrderMaterialDTO> listResult = new ArrayList<>();
		if(null == listBean || listBean.isEmpty()){
			return listResult;
		}
		for(JXCMTMerchantOrderMaterial material:listBean){
			listResult.add(convertBean(material));
		}
		return listResult;
	}
	
	public static List<JXCMTMerchantOrderMaterial> convertListDto(List<JXCMTMerchantOrderMaterialDTO> listDto){
		List<JXCMTMerchantOrderMaterial> listResult = new ArrayList<>();
		if(null == listDto || listDto.isEmpty()){
			return listResult;
		}
		for(JXCMTMerchantOrderMaterialDTO material:listDto){
			listResult.add(convertDto(material));
		}
		return listResult;
	}
	
	
	public static JXCMTMerchantOrderMaterialDTO convertBean(JXCMTMerchantOrderMaterial bean){
		if(null == bean){
			return null;
		}
		JXCMTMerchantOrderMaterialDTO dto = new JXCMTMerchantOrderMaterialDTO();
		dto.setId(bean.getId());
		dto.setMoMaterialCode(bean.getMoMaterialCode());
		dto.setMoMaterialName(bean.getMoMaterialName());
		dto.setMoOrderCode(bean.getMoOrderCode());
		dto.setMoProductCode(bean.getMoProductCode());
		dto.setMoPropQuantity(bean.getMoPropQuantity());
		dto.setMoTotal(bean.getMoTotal());
		return dto;
	}
	
	public static JXCMTMerchantOrderMaterial convertDto(JXCMTMerchantOrderMaterialDTO dto){
		if(null == dto){
			return null;
		}
		JXCMTMerchantOrderMaterial bean = new JXCMTMerchantOrderMaterial();
		bean.setId(dto.getId());
		bean.setMoMaterialCode(dto.getMoMaterialCode());
		bean.setMoMaterialName(dto.getMoMaterialName());
		bean.setMoOrderCode(dto.getMoOrderCode());
		bean.setMoProductCode(dto.getMoProductCode());
		bean.setMoPropQuantity(dto.getMoPropQuantity());
		bean.setMoTotal(dto.getMoTotal());
		return bean;
	}
}
