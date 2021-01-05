package cn.com.glsx.supplychain.jxc.converter;

import cn.com.glsx.supplychain.jxc.dto.JXCLogisticsDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsLogisticsDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsLogistics;

import java.util.ArrayList;
import java.util.List;

public class JXCMTBsLogisticsRpcConvert {

	public static List<JXCMTBsLogisticsDTO> convertListBean(List<JXCMTBsLogistics> listBean){
		if(null == listBean || listBean.isEmpty()){
			return null;
		}
		List<JXCMTBsLogisticsDTO> listDto = new ArrayList<>();
		for(JXCMTBsLogistics bean:listBean){
			listDto.add(convertBean(bean));
		}
		return listDto;
	}
	
	public static JXCMTBsLogisticsDTO convertBean(JXCMTBsLogistics bean){
		if(null == bean){
			return null;
		}
		JXCMTBsLogisticsDTO dto = new JXCMTBsLogisticsDTO();
		dto.setAccept(bean.getAccept());
		dto.setCompany(bean.getCompany());
		dto.setOrderNumber(bean.getOrderNumber());
		dto.setShipmentsQuantity(bean.getShipmentsQuantity());
		dto.setSendTime(bean.getSendTime());
		return dto;
	}


	public static List<JXCLogisticsDTO> convertBsLogisticsToList(List<JXCMTBsLogistics> listBean){
		if(null == listBean || listBean.isEmpty()){
			return null;
		}
		List<JXCLogisticsDTO> listDto = new ArrayList<>();
		for(JXCMTBsLogistics bean:listBean){
			listDto.add(convertToBeanDTO(bean));
		}
		return listDto;
	}

	public static JXCLogisticsDTO convertToBeanDTO(JXCMTBsLogistics bean){
		if(null == bean){
			return null;
		}
		JXCLogisticsDTO dto = new JXCLogisticsDTO();
		dto.setCode(bean.getCode());
		dto.setCompany(bean.getCompany());
		dto.setOrderNumber(bean.getOrderNumber());
		dto.setShipmentsQuantity(bean.getShipmentsQuantity());
		dto.setSendTime(bean.getSendTime());
		return dto;
	}
}
