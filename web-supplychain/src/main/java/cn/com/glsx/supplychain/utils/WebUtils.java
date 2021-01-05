package cn.com.glsx.supplychain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.model.SupplyRequest;

public class WebUtils {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Map<String, String> parseMap(Map<?, ?> map)
	{
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		for(Iterator<?> iter = map.keySet().iterator();iter.hasNext();)
		{
			String key = (String)iter.next();
			String[] values = (String[])map.get(key);
			String value = "";
			for(int i = 0; i < values.length; i++)
			{
				if(i == values.length - 1)
				{
					value += values[i];
				}
				else
				{
					value += values[i] + ",";
				}
			}
			paramsMap.put(key, value);
		}
		return paramsMap;
	}
	
	public static void setRequest(SupplyRequest request)
	{
		request.setConsumer("web-supplychain");
		request.setVersion("v1.0");
		request.setTime(getCurrentDate());
	}
	
	public static String getCurrentDate()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	/**
	*判断是否字母数字组合
	*
	* @return
	*/
	public static boolean isNumberAndLetter(String str)
	{
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}
	
	public static boolean isCanParseInt(String str)
	{
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
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
}
