package glsx.com.cn.task.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;

public class TaskUtils {
private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat formatter_order = new  SimpleDateFormat("yyyy-MM-dd");

	public static void copyObject(Object target, Object origist)
	{
		try {
			
			BeanUtils.copyProperties(target, origist);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断对象是否为空，且对象的所有属性都为空
	 * ps: boolean类型会有默认值false 判断结果不会为null 会影响判断结果
	 *     序列化的默认值也会影响判断结果
	 * @param object
	 * @return
	 */
	public static  boolean objCheckIsNull(Object object){
		Class clazz = (Class)object.getClass(); // 得到类对象
		Field fields[] = clazz.getDeclaredFields(); // 得到所有属性
		boolean flag = true; //定义返回结果，默认为true
		for(Field field : fields){
			field.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = field.get(object); //得到属性值
				Type fieldType =field.getGenericType();//得到属性类型
				String fieldName = field.getName(); // 得到属性名
				System.out.println("属性类型："+fieldType+",属性名："+fieldName+",属性值："+fieldValue);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if(fieldValue != null){  //只要有一个属性值不为null 就返回false 表示对象不为null
				flag = false;
				break;
			}
		}
		return flag;
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

	public static String getNowDateString()
	{
		return formatter.format(new Date());
	}

	public static String getStringFromDate(Date dt)
	{
		return formatter.format(dt);
	}
	
	public static String getNowDateStringYMD(Date dt)
	{
		return formatter_order.format(dt);
	}
	
	public static Date getNowDateYMD(Date dt){
		try {
			return formatter_order.parse(formatter_order.format(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getFetureDate(Integer past)
	{
		Calendar calendar = Calendar.getInstance();  
	    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
	    Date today = calendar.getTime();  
	    return today;  
	}
	
	//后面改成雪花算法生成
	public static synchronized String getOrderNumber(SnowflakeWorker snowflakeWorker) {
		
	//	return formatter_order.format(new Date()) + String.valueOf(snowflakeWorker.nextId());
		
		String format = "yyyyMMddHHmmss";
		String str = new SimpleDateFormat(format).format(new Date());
		String flakseed =  String.valueOf(snowflakeWorker.nextId());
		String snowflake = flakseed.substring(flakseed.length()-4,flakseed.length());
		Integer numseed = RandomUtils.nextInt(9999);
		String num = String.format("%04d", numseed);
		return str + snowflake + num;
	}
	
	public static Date convertTimeStemp2Date(Integer timeStep){
		
		Long timestamp = Long.parseLong(timeStep.toString())*1000;
		return new Date(timestamp);
	}
	
	public static Integer convertDate2TimeStemp(Date dateTime){
		return (int)(dateTime.getTime()/1000);
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
	
	public static Date getDateFromStringYMD(String sdt){
		Date dt = null;	
		try {
			return formatter_order.parse(sdt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;	
	}
	
}
