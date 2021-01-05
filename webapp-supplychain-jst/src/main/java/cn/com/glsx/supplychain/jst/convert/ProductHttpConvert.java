package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.ProductDTO;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.vo.ProductVO;

public class ProductHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(ProductHttpConvert.class);
	
	public static List<ProductDTO> convertListVo(List<ProductVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<ProductDTO> dtoList = new ArrayList<ProductDTO>();
		for(ProductVO vo:record)
		{
			dtoList.add(convertVo(vo));
		}
		return dtoList;
	}
	
	public static List<ProductVO> convertListDto(List<ProductDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<ProductVO> voList = new ArrayList<ProductVO>();
		for(ProductDTO dto:record)
		{
			voList.add(convertDto(dto));
		}
		return voList;
	}
	
	public static ProductDTO convertVo(ProductVO record)
	{
		ProductDTO dto = new ProductDTO();
		dto.setChannel(convertChannelVo(record.getChannel()));
		dto.setMerchantCode(record.getMerchantCode());
		dto.setMerchantName(record.getMerchantName());
		dto.setPackageOne(record.getPackageOne());
		dto.setPrice(record.getPrice());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setServiceTime(record.getServiceTime());
		dto.setServiceType(convertServiceTypeVO(record.getServiceType()));
		dto.setListMaterialDto(MaterialHttpConvert.convertListVo(record.getListMaterialVo()));
		return dto;
	}
	
	public static ProductVO convertDto(ProductDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		ProductVO vo = new ProductVO();
		vo.setChannel(convertChannelDto(record.getChannel()));
		vo.setMerchantCode(record.getMerchantCode());
		vo.setMerchantName(record.getMerchantName());
		vo.setPackageOne(record.getPackageOne());
		vo.setPrice(JstUtils.getDecimalDouble(record.getPrice()));
		vo.setProductCode(record.getProductCode());
		vo.setProductName(record.getProductName());
		vo.setServiceTime(record.getServiceTime());
		vo.setServiceType(convertServiceTypeDTO(record.getServiceType()));
		vo.setListMaterialVo(MaterialHttpConvert.convertListDto(record.getListMaterialDto()));
		return vo;
	}
	
	private static Byte convertServiceTypeVO(String serviceType)
	{
		Byte dto = 0;
		if(StringUtils.isEmpty(serviceType))
		{
			return null;
		}
		if(serviceType.equals("JB"))
		{
			dto = 1;
		}
		else if(serviceType.equals("JR"))
		{
			dto = 2;
		}
		else if(serviceType.equals("CZ"))
		{
			dto = 3;
		}
		else if(serviceType.equals("HS"))
		{
			dto = 4;
		}
		else if(serviceType.equals("OT"))
		{
			dto = 5;
		}
		return dto;
	}
	
	private static String convertServiceTypeDTO(Byte serviceType)
	{
		String vo = null;
		if(StringUtils.isEmpty(serviceType))
		{
			return null;
		}
		if(serviceType.intValue() == 1)
		{
			vo = "JB";
		}
		else if(serviceType.intValue() == 2)
		{
			vo = "JR";
		}
		else if(serviceType.intValue() == 3)
		{
			vo = "CZ";
		}
		else if(serviceType.intValue() == 4)
		{
			vo = "HS";
		}
		else if(serviceType.intValue() == 5)
		{
			vo = "OT";
		}
		
		return vo;
	}
	
	
	private static Byte convertChannelVo(String record)
	{
		Byte dto = 8;
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		if(record.equals("AL"))
		{
			dto = 0;
		}
		else if(record.equals("GH"))
		{
			dto = 1;
		}
		else if(record.equals("OT"))
		{
			dto = 2;
		}
		else if(record.equals("TM"))
		{
			dto = 3;
		}
		else if(record.equals("YK"))
		{
			dto = 5;
		}
		return dto;
	}
	
	private static String convertChannelDto(Byte record)
	{
		String dto = "";
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		if(record.equals(0))
		{
			dto = "AL";
		}
		else if(record.equals(1))
		{
			dto = "GH";
		}
		else if(record.equals(2))
		{
			dto = "OT";
		}
		else if(record.equals(3))
		{
			dto = "TM";
		}
		else if(record.equals(5))
		{
			dto = "YK";
		}
		return dto;
	}
}
