package cn.com.glsx.supplychain.util;

import org.apache.commons.lang.math.RandomUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SupplychainUtils {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat formatter_ymd = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat formatter_ym = new SimpleDateFormat("yyyy-MM");
	
	private static SimpleDateFormat format_YMD_t = new SimpleDateFormat("yyyyMMdd");
	
	private static SimpleDateFormat format_YMD_p = new SimpleDateFormat("yyyy.MM.dd");
	
	private static SimpleDateFormat format_Y = new SimpleDateFormat("yyyy");
	
	
	public static boolean isTimeYMDPFormat(String str){
		try{
			format_YMD_p.setLenient(false);
			format_YMD_p.parse(str);
			return true;
		}catch (ParseException e) {
			e.printStackTrace();
			return false;
		}	
	}

	public static boolean isTimeYMFormat(String str){
		
		try {
			formatter_ym.setLenient(false);
			formatter_ym.parse(str);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public static boolean isTimeYMDHMSFormat(String str){
		try{
			formatter.setLenient(false);
			formatter.parse(str);
			return true;
		}catch(ParseException e){
			e.printStackTrace();
			return false;
		}	
	}
	
	public static Date getInitialDate() {
		String dateString = "2000-01-01 00:00:00";
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	public static synchronized String getDispatchOrderNumber(
			SnowflakeWorker snowflakeWorker) {

		// return formatter_order.format(new Date()) +
		// String.valueOf(snowflakeWorker.nextId());
		String format = "yyyyMMddHHmmss";
		String str = new SimpleDateFormat(format).format(new Date());
		String flakseed = String.valueOf(snowflakeWorker.nextId());
		String snowflake = flakseed.substring(flakseed.length() - 4,
				flakseed.length());
		Integer numseed = RandomUtils.nextInt(9999);
		String num = String.format("%04d", numseed);
		return str + snowflake + num;
	}
	
	public static boolean isZeroDoubleNumber(Double d){
        return Math.abs(d) < 0.00001;
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

	public static Date getNowDate()
	{
		return new Date();
	}
	
	public static String getNowDateStringYM(Date dt){
		return formatter_ym.format(dt);
	}
	
	public static String getNowDateStringYMD(Date dt)
	{
		return formatter_ymd.format(dt);
	}

	public static String getNowDateString()
	{
		return formatter.format(new Date());
	}

	public static String getStringFromDate(Date dt)
	{
		return formatter.format(dt);
	}
	
	
	public static Date getFetureDate(Integer past)
	{
		Calendar calendar = Calendar.getInstance();  
	    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
	    Date today = calendar.getTime();  
	    return today;  
	}
	
	public static Date getDateFromString(String sdt){
		Date dt = null;	
		try {
			return formatter.parse(sdt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;	
	}
	
	public static Date getYearPlusDate(Date dt,Integer step){
		Date result = null;
		String sdt = formatter.format(dt);
		String sdty = format_Y.format(dt);
		Integer idty = Integer.valueOf(sdty);
		idty += step;
		String tail = sdt.substring(4);
		String strre = idty + tail;
		try {
			result = formatter.parse(strre);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date convertTimeYMDPFormat2Format(String str){
		Date dt = null;
		try{
			dt = format_YMD_p.parse(str);
		
		}catch (ParseException e) {
			e.printStackTrace();
		}	
		return dt;
	}
	
	public static Date getDateFromStringYM(String sdt){
		Date dt = null;	
		try{
			return formatter_ym.parse(sdt);
		}catch (ParseException e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public static Date getDateFromStringYMD(String sdt){
		Date dt = null;	
		try{
			return formatter_ymd.parse(sdt);
		}catch (ParseException e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public static String getStatementNumber(String head, int dataId){
		
		return format_YMD_t.format(new Date()) + head + String.format("%0" + 10 + "d", dataId);
	}
	
	public static boolean beInteger(String param){
		try{
			int num = Integer.valueOf(param);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
