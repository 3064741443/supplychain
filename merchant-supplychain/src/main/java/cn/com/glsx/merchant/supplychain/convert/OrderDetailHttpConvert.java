package cn.com.glsx.merchant.supplychain.convert;

import cn.com.glsx.merchant.supplychain.vo.OrderDetailVO;
import cn.com.glsx.supplychain.jst.dto.OrderDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(OrderDetailHttpConvert.class);
	
	public static List<OrderDetailVO> convertListDto(List<OrderDetailDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<OrderDetailVO> listVo = new ArrayList<OrderDetailVO>();
		for(OrderDetailDTO dto:record)
		{
			listVo.add(convertDto(dto));
		}
		return listVo;
	}
	
	public static List<OrderDetailDTO> convertListVo(List<OrderDetailVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<OrderDetailDTO> listDto = new ArrayList<OrderDetailDTO>();
		for(OrderDetailVO vo:record)
		{
			listDto.add(convertVo(vo));
		}
		return listDto;
	}
	
	public static OrderDetailVO convertDto(OrderDetailDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		OrderDetailVO vo = new OrderDetailVO();
		vo.setSn(record.getSn());
		vo.setAttribCode(record.getAttribCode());
		return vo;
	}
	
	public static OrderDetailDTO convertVo(OrderDetailVO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setSn(record.getSn());
		dto.setAttribCode(record.getAttribCode());
		return dto;
	}
}
