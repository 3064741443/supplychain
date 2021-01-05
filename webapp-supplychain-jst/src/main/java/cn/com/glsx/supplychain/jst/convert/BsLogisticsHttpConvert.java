package cn.com.glsx.supplychain.jst.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.vo.LogisticsVO;

public class BsLogisticsHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(BsLogisticsHttpConvert.class);
	
	public static LogisticsVO convertDto(BsLogisticsDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		LogisticsVO vo = new LogisticsVO();
		vo.setCompany(record.getCompany());
		vo.setOrderNumber(record.getOrderNumber());
		vo.setShipType(record.getShipType());
		return vo;
	}
	
	public static BsLogisticsDTO convertVo(LogisticsVO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsLogisticsDTO dto = new BsLogisticsDTO();
		dto.setCompany(record.getCompany());
		dto.setOrderNumber(record.getOrderNumber());
		return dto;
	}
}
