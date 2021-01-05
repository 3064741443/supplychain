package cn.com.glsx.merchant.supplychain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBaseDTO;

public class JxcUtils {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void setBaseDTO(BaseDTO baseDTO)
	{
		baseDTO.setConsumer("merchant-supplychain");
		baseDTO.setVersion("v1.0.0");
		baseDTO.setTime(formatter.format(new Date()));
	}
	
	public static void setJXCBaseDTO(JXCMTBaseDTO baseDTO){
		
		baseDTO.setConsumer("merchant-supplychain");
		baseDTO.setVersion("v1.0.0");
		baseDTO.setTime(formatter.format(new Date()));
	}
	
	public static Date getNowDate()
	{
		return new Date();
	}
	
	public static Date getDateFromString(String str)
	{
		Date dt = null;
		try {
			dt=formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	
	public static String getNowDateString()
	{
		return formatter.format(new Date());
	}
	
	public static String getStringFromDate(Date dt)
	{
		return formatter.format(dt);
	}
	
	
}
