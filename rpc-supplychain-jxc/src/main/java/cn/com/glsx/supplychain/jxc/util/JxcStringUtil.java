package cn.com.glsx.supplychain.jxc.util;


import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Title: StringUtil
 * @Description: 处字符处理工具类
 * @author Leiyj  
 * @date 2018年1月11日 上午10:16:10
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
public class JxcStringUtil extends StringUtils {
	/**
	 * 生成订单编号
	 * 
	 * @return
	 */
	public static synchronized String getOrderNo() {
		String format = "yyyyMMddHHmmss";
		int i = (int)(Math.random()*900)+100;
		String str = new SimpleDateFormat(format).format(new Date());
		String date = "";
		long orderNum = 000l;
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0l;
		}
		orderNum++;
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return orderNo + i + "";
	}

	/**
	 * 生成配置编码编号
	 * 
	 * @return
	 */
	public static synchronized String getDeviceAttribCode() {
		String format = "yyyyMMdd";
		int i = (int)(Math.random()*900)+100;
		String str = new SimpleDateFormat(format).format(new Date());
		String date = "";
		long CodeNum = 000l;
		if (date == null || !date.equals(str)) {
			date = str;
			CodeNum = 0l;
		}
		CodeNum++;
		long CodeNo = Long.parseLong((date))*10 ;
		CodeNo += CodeNum;
		return "GLSX"+CodeNo + i + "";
	}

	/**
	 * 判断imsi格式
	 *
	 * @return
	 */
	public static boolean isRightImsiFormat(String imsi)
	{
		if(imsi.length() != 15)
		{
			return false;
		}

		String strPrex = imsi.substring(0,3);

        return "460".equals(strPrex);
    }
	
	/**
	 * 判断模块手机号格式
	 *
	 * @return
	 */
	public static boolean isRightPhoneFormat(String phone)
	{
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		 if (phone.length() != 11)
		 {
			 return false;
		 }
		 else
		 {
			 Pattern p = Pattern.compile(regex);
			 Matcher m = p.matcher(phone);
			 return m.matches();
		 }
	}
	
	/**
	 * 判断sn格式
	 *
	 * @return
	 */
    public static boolean isRightFormatSn(String sn)
    {
    	String regex = "^[a-z0-9A-Z]+$";
    	if(StringUtils.isEmpty(sn))
    	{
    		return false;
    	}
    	if(sn.length() > 32)
    	{
    		return false;
    	}
    	return sn.matches(regex);
    }
    
    /**
	 * 判断imsi格式
	 *
	 * @return
	 */
    public static boolean isRightFormatImsi(String imsi)
    {
    	String regex = "^[a-z0-9A-Z]+$";
    	if(StringUtils.isEmpty(imsi))
    	{
    		return false;
    	}
    	if(imsi.length() != 15)
		{
			return false;
		}

		String strPrex = imsi.substring(0,3);

		if(!"460".equals(strPrex))
		{
			return false;
		}

		return imsi.matches(regex);	
    }
    
    /**
	 * 判断iccid格式
	 *
	 * @return
	 */
    public static boolean isRightFormatIccid(String iccid)
    {
    	String regex = "^[a-z0-9A-Z]+$";
    	if(StringUtils.isEmpty(iccid))
    	{
    		return false;
    	}
    	if(iccid.length() < 18 || iccid.length()>20)
    	{
    		return false;
    	}
    	return iccid.matches(regex);
    }
	
	public static boolean isRightModulePhoneFormat(String modulePhone)
	{
        return modulePhone.length() >= 5 && modulePhone.length() <= 25;
    }
	
	public static boolean isCheckImsiDeviceType(Integer deviceType)
	{
		Integer typeDvd = 2;				//dvd
		Integer typeCloudMirror = 12134;	//智能云镜
		if(StringUtils.isEmpty(deviceType))
		{
			return false;
		}
		if(deviceType.equals(typeDvd))
		{
			return true;
		}
        return deviceType.equals(typeCloudMirror);
    }

	/**
	 * 生成直接发货发货单
	 *
	 * @return
	 */
	public static synchronized String getDispatchOrderNumber() {
		String format = "yyyyMMddHHmmss";
		int i  = (int)(Math.random()*900)+100;
		String str = new SimpleDateFormat(format).format(new Date());
		String date = "";
		long orderNum = 000l;
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0l;
		}
        orderNum++;
        long orderNo = Long.parseLong((date)) * 10000;
        orderNo += orderNum;
        String j = String.valueOf(i);
        String no = String.valueOf(orderNo);
        String value = no.substring(0, no.length() - 3);
        return j + value + "";
	}

}
