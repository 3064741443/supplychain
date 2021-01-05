package cn.com.glsx.supplychain.jst.utils;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;
import org.apache.commons.lang.math.RandomUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JstUtils {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat formatterMonth = new SimpleDateFormat("yyyy-MM");
	
	public static void setBaseDTO(BaseDTO baseDTO)
	{
		baseDTO.setConsumer("webapp-supplychain-jst");
		baseDTO.setVersion("v1.0.0");
		baseDTO.setTime(formatter.format(new Date()));
	}
	
	public static Date getNowDate()
	{
		return new Date();
	}
	
	public static String getNowDateString()
	{
		return formatter.format(new Date());
	}
	
	public static String getStringFromDate(Date dt)
	{
		return formatter.format(dt);
	}
	
	public static double getDecimalDouble(Double d)
	{
		double dParam = 0.00;
		if(StringUtils.isEmpty(d))
		{
			dParam = 0.00;
		}
		else
		{
			dParam = d.doubleValue();
		}
		double dRet = (double)Math.round(dParam*100)/100;
		return dRet;
	}
	
	// 2020-3转2020-03
	public static String formatOrderTime(String dt) 
	{
		String result = null;
		if(StringUtils.isEmpty(dt))
		{
			return null;
		}
		try {
			Date ddt = formatterMonth.parse(dt);
			result = formatterMonth.format(ddt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	//后面改成雪花算法生成
	public static synchronized String getDispatchOrderNumber(SnowflakeWorker snowflakeWorker) {

		//	return formatter_order.format(new Date()) + String.valueOf(snowflakeWorker.nextId());

		String format = "yyyyMMddHHmmss";
		String str = new SimpleDateFormat(format).format(new Date());
		String flakseed =  String.valueOf(snowflakeWorker.nextId());
		String snowflake = flakseed.substring(flakseed.length()-4,flakseed.length());
		Integer numseed = RandomUtils.nextInt(9999);
		String num = String.format("%04d", numseed);
		return str + snowflake + num;
	}
}
