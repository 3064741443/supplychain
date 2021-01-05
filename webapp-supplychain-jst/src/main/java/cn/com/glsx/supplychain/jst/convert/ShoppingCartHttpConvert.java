package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.vo.CartVO;

public class ShoppingCartHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartHttpConvert.class);
	
	public static List<CartVO> convertListShopCartDto(List<JstShopShoppingCartDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<CartVO> listVo = new ArrayList<CartVO>();
		for(JstShopShoppingCartDTO dto:record)
		{
			listVo.add(convertShopCartDto(dto));
		}
		return listVo;
	}
	
	public static List<JstShopShoppingCartDTO> convertListShopCartVo(List<CartVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<JstShopShoppingCartDTO> listDto = new ArrayList<JstShopShoppingCartDTO>();
		for(CartVO vo:record)
		{
			listDto.add(convertShopCartVo(vo));
		}
		return listDto;
	}
	
	public static List<CartVO> convertListMerchantCartDto(List<BsMerchantShoppingCartDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<CartVO> listVo = new ArrayList<CartVO>();
		for(BsMerchantShoppingCartDTO dto:record)
		{
			listVo.add(convertMerchantCartDto(dto));
		}
		return listVo;
	}
	
	public static List<BsMerchantShoppingCartDTO> convertListMerchantCartVo(List<CartVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<BsMerchantShoppingCartDTO> listDto = new ArrayList<BsMerchantShoppingCartDTO>();
		for(CartVO vo:record)
		{
			listDto.add(convertMerchantCartVo(vo));
		}
		return listDto;
	}
	
	public static CartVO convertShopCartDto(JstShopShoppingCartDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CartVO vo = new CartVO();
		vo.setDeletedFlag(record.getDeletedFlag());
		vo.setId(record.getId());
		vo.setMaterialCode(record.getMaterialCode());
		vo.setMaterialName(record.getMaterialName());
		vo.setMerchantCode(record.getAgentMerchantCode());
		vo.setMerchantName(record.getAgentMerchantName());
		vo.setOrderCount(record.getTotal());
		vo.setPackageOne(record.getPackageOne());
		vo.setPrice(record.getPrice());
		vo.setProductCode(record.getProductCode());
		vo.setProductName(record.getProductName());
		vo.setServiceTime(record.getServiceTime());
		return vo;
	}
	
	public static JstShopShoppingCartDTO convertShopCartVo(CartVO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShopShoppingCartDTO dto = new JstShopShoppingCartDTO();
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setMaterialCode(record.getMaterialCode());
		dto.setMaterialName(record.getMaterialName());
		dto.setAgentMerchantCode(record.getMerchantCode());
		dto.setAgentMerchantName(record.getMerchantName());
		dto.setTotal(record.getOrderCount());
		dto.setPackageOne(record.getPackageOne());
		dto.setPrice(record.getPrice());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setServiceTime(record.getServiceTime());
		return dto;
	}
	
	public static CartVO convertMerchantCartDto(BsMerchantShoppingCartDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CartVO vo = new CartVO();
		vo.setDeletedFlag(record.getDeletedFlag());
		vo.setId(record.getId());
		vo.setMaterialCode(record.getMaterialCode());
		vo.setMaterialName(record.getMaterialName());
		vo.setMerchantCode(null);
		vo.setMerchantName(null);
		vo.setOrderCount(record.getTotal());
		vo.setPackageOne(record.getPackageOne());
		vo.setPrice(record.getPrice());
		vo.setProductCode(record.getProductCode());
		vo.setProductName(record.getProductName());
		vo.setServiceTime(record.getServiceTime());
		return vo;
	}
	
	public static BsMerchantShoppingCartDTO convertMerchantCartVo(CartVO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsMerchantShoppingCartDTO dto = new BsMerchantShoppingCartDTO();
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setMaterialCode(record.getMaterialCode());
		dto.setMaterialName(record.getMaterialName());
		dto.setMerchantCode(null);
		dto.setMerchantName(null);
		dto.setTotal(record.getOrderCount());
		dto.setPackageOne(record.getPackageOne());
		dto.setPrice(record.getPrice());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setServiceTime(record.getServiceTime());
		return dto;
	}
}
