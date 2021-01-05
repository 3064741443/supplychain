package cn.com.glsx.rpc.supplychain.rdn.util;

import org.apache.commons.lang.math.RandomUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RdnUtils {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat formatter_order = new  SimpleDateFormat("yyyy-MM-dd");
	
	//后面改成雪花算法生成
	public static synchronized String getSnowflakeWorkerId(SnowflakeWorker snowflakeWorker) {
		
		return String.valueOf(snowflakeWorker.nextId());
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
		if(null == dt){
			return "";
		}
		return formatter.format(dt);
	}
	
	public static String getNowDateStringYMD(Date dt)
	{
		return formatter_order.format(dt);
	}
	
	public static Date getFetureDate(Integer past)
	{
		Calendar calendar = Calendar.getInstance();  
	    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
	    Date today = calendar.getTime();  
	    return today;  
	}
	
	public static boolean beYmdDateStr(String str){
		if(StringUtils.isEmpty(str)){
			return false;
		}
		try{
			formatter_order.setLenient(true);
			formatter_order.parse(str);
			return true;
		}catch (ParseException e) {
			e.printStackTrace();
			return false;
		}	
		
	}
	
	public static Date getDateFromStrYMDHMS(String str){
		if(StringUtils.isEmpty(str)){
			return null;
		}
		try{
			return formatter.parse(str);
		}catch(ParseException e){
			return null;
		}
	}

	public static Date getDateFromStrYMD(String str){
		if(StringUtils.isEmpty(str)){
			return null;
		}
		try{
			return formatter_order.parse(str);
		}catch(ParseException e){
			return null;
		}
	}
	
	public static boolean parseInt(String value){
		try{
			Integer.parseInt(value);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean parseDouble(String value){
		try{
			Double.parseDouble(value);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	public static synchronized String generatorTimeRandCode(SnowflakeWorker snowflakeWorker) {
		String format = "yyyyMMddHHmmss";
		String str = new SimpleDateFormat(format).format(new Date());
		String flakseed =  String.valueOf(snowflakeWorker.nextId());
		String snowflake = flakseed.substring(flakseed.length()-4,flakseed.length());
		Integer numseed = RandomUtils.nextInt(9999);
		String num = String.format("%04d", numseed);
		return str + snowflake + num;
	}
}
