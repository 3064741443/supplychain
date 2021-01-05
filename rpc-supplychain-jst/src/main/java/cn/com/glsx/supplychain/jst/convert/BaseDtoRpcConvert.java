package cn.com.glsx.supplychain.jst.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;
import cn.com.glsx.supplychain.jst.model.RequestVerify;

public class BaseDtoRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(BaseDtoRpcConvert.class);
	
	public static BaseDTO convertBean(RequestVerify record)
	{
		BaseDTO dto = new BaseDTO();
		dto.setConsumer(record.getConsumer());
		dto.setVersion(record.getVersion());
		return dto;
	}
	
	public static RequestVerify convertDTO(BaseDTO record)
	{
		RequestVerify model = new RequestVerify();
		model.setConsumer(record.getConsumer());
		model.setVersion(record.getVersion());
		return model;
	}
}
