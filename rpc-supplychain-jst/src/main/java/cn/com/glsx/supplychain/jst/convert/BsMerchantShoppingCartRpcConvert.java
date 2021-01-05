package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.model.BsMerchantShoppingCart;

public class BsMerchantShoppingCartRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantShoppingCartRpcConvert.class);
	
	public static List<BsMerchantShoppingCart> convertListDto(List<BsMerchantShoppingCartDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<BsMerchantShoppingCart> listModel = new ArrayList<BsMerchantShoppingCart>();
		for(BsMerchantShoppingCartDTO dto:record)
		{
			listModel.add(convertDto(dto));
		}
		return listModel;
	}
	
	public static List<BsMerchantShoppingCartDTO> convertListBean(List<BsMerchantShoppingCart> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<BsMerchantShoppingCartDTO> listDto = new ArrayList<BsMerchantShoppingCartDTO>();
		for(BsMerchantShoppingCart model:record)
		{
			listDto.add(convertBean(model));
		}
		return listDto;
	}
	
	public static BsMerchantShoppingCart convertDto(BsMerchantShoppingCartDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsMerchantShoppingCart model = new BsMerchantShoppingCart();
		model.setCreatedBy(record.getCreatedBy());
		model.setUpdatedBy(record.getUpdatedBy());
		model.setCreatedDate(record.getCreatedDate());
		model.setUpdatedDate(record.getUpdatedDate());
		model.setDeletedFlag(record.getDeletedFlag());
		model.setId(record.getId());
		model.setMaterialCode(record.getMaterialCode());
		model.setMaterialName(record.getMaterialName());
		model.setMerchantCode(record.getMerchantCode());
		model.setMerchantName(record.getMerchantName());
		model.setPackageOne(record.getPackageOne());
		model.setPrice(record.getPrice());
		model.setProductCode(record.getProductCode());
		model.setProductName(record.getProductName());
		model.setServiceTime(record.getServiceTime());
		model.setTotal(record.getTotal());
		return model;
	}
	
	public static BsMerchantShoppingCartDTO convertBean(BsMerchantShoppingCart record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsMerchantShoppingCartDTO dto = new BsMerchantShoppingCartDTO();
		dto.setCreatedBy(record.getCreatedBy());
		dto.setUpdatedBy(record.getUpdatedBy());
		dto.setCreatedDate(record.getCreatedDate());
		dto.setUpdatedDate(record.getUpdatedDate());
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setMaterialCode(record.getMaterialCode());
		dto.setMaterialName(record.getMaterialName());
		dto.setMerchantCode(record.getMerchantCode());
		dto.setMerchantName(record.getMerchantName());
		dto.setPackageOne(record.getPackageOne());
		dto.setPrice(record.getPrice());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setServiceTime(record.getServiceTime());
		dto.setTotal(record.getTotal());
		return dto;
	}
}
