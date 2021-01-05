package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.DisProductDTO;
import cn.com.glsx.supplychain.jst.vo.DisProductVO;

public class DisProductHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(DisProductHttpConvert.class);
	
	public static DisProductDTO convertVo(DisProductVO record,List<BsDealerUserInfoDTO> listDealerUserInfoDTO)
	{
		DisProductDTO dto = new DisProductDTO();
		if(!StringUtils.isEmpty(record.getContext()))
		{
			dto.setContext(record.getContext());
		}
		if(!StringUtils.isEmpty(record.getServiceType()))
		{
			dto.setServiceType(convertServiceTypeVO(record.getServiceType()));
		}
		dto.setPageNo(record.getPageNo());
		dto.setPageSize(record.getPageSize());
		dto.setListDealerUserInfoDTO(listDealerUserInfoDTO);
	//	dto.setListProductDTO(ProductHttpConvert.convertListVo(record.getListProductVo()));
		return dto;
	}
	
	public static DisProductVO convertDto(DisProductDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		DisProductVO vo = new DisProductVO();
		vo.setContext(record.getContext());
		vo.setPageNo(record.getPageNo());
		vo.setPageSize(record.getPageSize());
		vo.setServiceType(convertServiceTypeDto(record.getServiceType()));
		vo.setListProductVo(ProductHttpConvert.convertListDto(record.getListProductDTO()));
		List<String> listMerchantCode = new ArrayList<String>();
		if(!StringUtils.isEmpty(record.getListDealerUserInfoDTO()))
		{
			for(BsDealerUserInfoDTO dto:record.getListDealerUserInfoDTO())
			{
				listMerchantCode.add(dto.getMerchantCode());
			}
		}
		return vo;
	}
	
	public static Byte convertServiceTypeVO(String serviceType)
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
	
	public static String convertServiceTypeDto(Byte serviceType)
	{
		String vo = null;
		if(StringUtils.isEmpty(serviceType))
		{
			return vo;
		}
		if(serviceType.equals(1))
		{
			vo = "JB";
		}
		else if(serviceType.equals(2))
		{
			vo = "JR";
		}
		else if(serviceType.equals(3))
		{
			vo = "CZ";
		}
		else if(serviceType.equals(4))
		{
			vo = "HS";
		}
		else if(serviceType.equals(5))
		{
			vo = "OT";
		}
		return vo;
	}
}
