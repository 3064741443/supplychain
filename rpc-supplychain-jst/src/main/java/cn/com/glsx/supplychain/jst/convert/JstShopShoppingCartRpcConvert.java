package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.model.JstShopShoppingCart;

public class JstShopShoppingCartRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(JstShopShoppingCartRpcConvert.class);
	
	public static List<JstShopShoppingCart> convertListDto(List<JstShopShoppingCartDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<JstShopShoppingCart> listModel = new ArrayList<JstShopShoppingCart>();
		for(JstShopShoppingCartDTO dto:record)
		{
			listModel.add(convertDto(dto));
		}
		return listModel;
	}
	
	public static List<JstShopShoppingCartDTO> convertListBean(List<JstShopShoppingCart> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<JstShopShoppingCartDTO> listDto = new ArrayList<JstShopShoppingCartDTO>();
		for(JstShopShoppingCart model:record)
		{
			listDto.add(convertBean(model));
		}
		return listDto;
	}
	
	public static JstShopShoppingCart convertDto(JstShopShoppingCartDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShopShoppingCart model = new JstShopShoppingCart();
		model.setAgentMerchantCode(record.getAgentMerchantCode());
		model.setAgentMerchantName(record.getAgentMerchantName());
		model.setCreatedBy(record.getCreatedBy());
		model.setCreatedDate(record.getCreatedDate());
		model.setUpdatedBy(record.getUpdatedBy());
		model.setUpdatedDate(record.getUpdatedDate());
		model.setDeletedFlag(record.getDeletedFlag());
		model.setId(record.getId());
		model.setMaterialCode(record.getMaterialCode());
		model.setMaterialName(record.getMaterialName());
		model.setPackageOne(record.getPackageOne());
		model.setPrice(record.getPrice());
		model.setProductCode(record.getProductCode());
		model.setProductName(record.getProductName());
		model.setServiceTime(record.getServiceTime());
		model.setShopCode(record.getShopCode());
		model.setShopName(record.getShopName());
		model.setTotal(record.getTotal());
		return model;
	}
	
	public static JstShopShoppingCartDTO convertBean(JstShopShoppingCart record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShopShoppingCartDTO dto = new JstShopShoppingCartDTO();
		dto.setAgentMerchantCode(record.getAgentMerchantCode());
		dto.setAgentMerchantName(record.getAgentMerchantName());
		dto.setCreatedBy(record.getCreatedBy());
		dto.setCreatedDate(record.getCreatedDate());
		dto.setUpdatedBy(record.getUpdatedBy());
		dto.setUpdatedDate(record.getUpdatedDate());
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setMaterialCode(record.getMaterialCode());
		dto.setMaterialName(record.getMaterialName());
		dto.setPackageOne(record.getPackageOne());
		dto.setPrice(record.getPrice());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setServiceTime(record.getServiceTime());
		dto.setShopCode(record.getShopCode());
		dto.setShopName(record.getShopName());
		dto.setTotal(record.getTotal());
		return dto;
	}
	
	
}
