package cn.com.glsx.supplychain.jxc.converter;

import cn.com.glsx.supplychain.jxc.dto.JXCMTMerchantOrderDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTMerchantOrder;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;

public class JXCMTMerchantOrderRpcConvert {

	public static JXCMTMerchantOrder convertDto(JXCMTMerchantOrderDTO dto){
		if(null == dto){
			return null;
		}
		JXCMTMerchantOrder result = new JXCMTMerchantOrder();
		result.setId(dto.getId());
		result.setMoHopeTime(JxcUtils.getDateFromString(dto.getMoHopeTime()));
		result.setMoMerchantCode(dto.getMoMerchantCode());
		result.setMoMerchantName(dto.getMoMerchantName());
		result.setMoOrderCode(dto.getMoOrderCode());
		result.setMoPrice(dto.getMoPrice());
		result.setMoProductCode(dto.getMoProductCode());
		result.setMoProductName(dto.getMoProductName());
		result.setMoProductPackage(dto.getMoProductPackage());
		result.setMoProductServiceTime(dto.getMoProductServiceTime());
		result.setMoRemark(dto.getMoRemark());
		result.setMoTotal(dto.getMoTotal());
		return result;
	}
	
	public static JXCMTMerchantOrderDTO convertBean(JXCMTMerchantOrder bean){
		if(null == bean){
			return null;
		}
		JXCMTMerchantOrderDTO result = new JXCMTMerchantOrderDTO();
		result.setId(bean.getId());
		result.setMoHopeTime(JxcUtils.getStringFromDate(bean.getMoHopeTime()));
		result.setMoMerchantCode(bean.getMoMerchantCode());
		result.setMoMerchantName(bean.getMoMerchantName());
		result.setMoOrderCode(bean.getMoOrderCode());
		result.setMoPrice(bean.getMoPrice());
		result.setMoProductCode(bean.getMoProductCode());
		result.setMoProductName(bean.getMoProductName());
		result.setMoProductPackage(bean.getMoProductPackage());
		result.setMoProductServiceTime(bean.getMoProductServiceTime());
		result.setMoRemark(bean.getMoRemark());
		result.setMoTotal(bean.getMoTotal());
		return result;
	}
}
