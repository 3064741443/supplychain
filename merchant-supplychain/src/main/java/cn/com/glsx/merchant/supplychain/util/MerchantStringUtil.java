package cn.com.glsx.merchant.supplychain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MerchantStringUtil {
	
	public static long kfc = 0;
	/**
	 * 生成订单编号
	 * 
	 * @return
	 */
	public static synchronized String getReceiveOrderNo() {
		String format = "yyyyMMddHHmmss";
		String str = new SimpleDateFormat(format).format(new Date());
		String date = "";
		long orderNum = kfc;	
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0l;
		}
		kfc++;
		if(kfc >= 10000)
		{
			kfc = 0;
		}
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return "DJH" + orderNo + "";
	}
}
