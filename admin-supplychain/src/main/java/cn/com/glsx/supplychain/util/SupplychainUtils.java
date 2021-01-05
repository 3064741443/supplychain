package cn.com.glsx.supplychain.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class SupplychainUtils {

	private static SimpleDateFormat format_YM = new SimpleDateFormat("yyyy-MM"); 
	
	private static SimpleDateFormat format_YMD = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat format_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static boolean isValidTimeFormat(String str){
		
		boolean isResult = true;
		
		try {
			format_YMD.setLenient(false);
			format_YMD.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isResult = false;
		}	
		return isResult;
	}
	
	public static boolean isValidDate(String str){
		boolean isResult = true;
			
		try {
			format_YM.setLenient(false);
			format_YM.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isResult = false;
		}	
		return isResult;
	}
	
	public static String getStrYMDHMSFromDate(Date dt){
		if(null == dt){
			return "";
		}
		return format_YMDHMS.format(dt);
	}
	
	public static Date getDateFromStrYMDHMS(String str){
		if(StringUtils.isEmpty(str)){
			return null;
		}
		try{
			return format_YMDHMS.parse(str);
		}catch(ParseException e){
			return null;
		}
	}
	
	public static Date getDateFromStr(String str){
		Date date = null;
		try{
			format_YM.setLenient(false);
			return format_YM.parse(str);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return date;
	}
	
	public static Date getDateFromStrYMD(String str){
		Date date = null;
		try{
			format_YMD.setLenient(false);
			return format_YMD.parse(str);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return date;
	}
	
	
	public static double getSpecifiedDigitsDouble(Integer num,Double param){
		if(null == param){
			param = 0.00;
		}
		BigDecimal b = new BigDecimal(param);
		return b.setScale(num,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static String formartImportString(String strImport){
		if(StringUtils.isEmpty(strImport)){
			return "";
		}
		if(strImport.equals("null")){
			return "";
		}
		if(strImport.equals("NULL")){
			return "";
		}
		if(strImport.equals("Null")){
			return "";
		}
		return strImport;
	}
	
	public static boolean isInteger(String str){
		if(StringUtils.isEmpty(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
		return pattern.matcher(str).matches(); 
	}
	

	public static boolean parseDouble(String value){
		try{
			Double.parseDouble(value);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static boolean parseInt(String value) {
		try{
			Integer.parseInt(value);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}
