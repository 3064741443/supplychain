package cn.com.glsx.supplychain.jxc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.springframework.util.StringUtils;

public class JxcUtils {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat formatter_ymd = new SimpleDateFormat("yyyy-MM-dd");
	
	public static boolean isValidTimeYMDFormat(String str){	
		boolean isResult = true;
		try {
			formatter_ymd.setLenient(false);
			formatter_ymd.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isResult = false;
		}	
		return isResult;
	}
	
	public static Date getNowDate()
	{
		return new Date();
	}
	
	public static Date getDateYmdFromString(String strDate){
		if(StringUtils.isEmpty(strDate)){
			return null;
		}
		Date dt = null;
		try{
			return formatter_ymd.parse(strDate);
		}catch (ParseException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStringYmdFromDate(Date dt){
		return formatter_ymd.format(dt);
	}
	
	public static Date getDateFromString(String strDate){
		if(StringUtils.isEmpty(strDate)){
			return null;
		}
		Date dt = null;
		try{
			return formatter.parse(strDate);
		}catch (ParseException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNowDateString()
	{
		return formatter.format(new Date());
	}

	public static String getStringFromDate(Date dt)
	{
		return formatter.format(dt);
	}
	
	//后面改成雪花算法生成
	public static synchronized String generatorOrderCode(SnowflakeWorker snowflakeWorker) {
		String format = "yyyyMMddHHmmss";
		String str = new SimpleDateFormat(format).format(new Date());
		String flakseed =  String.valueOf(snowflakeWorker.nextId());
		String snowflake = flakseed.substring(flakseed.length()-4,flakseed.length());
		Integer numseed = RandomUtils.nextInt(9999);
		String num = String.format("%04d", numseed);
		return str + snowflake + num;
	}
	
	//生成签收单号
	public static synchronized String generatorBillNumber(SnowflakeWorker snowflakeWorker){
		String pre = "B";
		String format = "yyyyMMdd";
		String str = new SimpleDateFormat(format).format(new Date());
		String flakseed =  String.valueOf(snowflakeWorker.nextId());
		String snowflake = flakseed.substring(flakseed.length()-2,flakseed.length());
		Integer numseed = RandomUtils.nextInt(9999);
		String num = String.format("%04d", numseed);
		return pre + str + snowflake + num;
	}
	
}
