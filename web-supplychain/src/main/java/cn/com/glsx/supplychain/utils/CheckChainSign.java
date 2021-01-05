package cn.com.glsx.supplychain.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.oreframework.util.encrypt.Md5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.glsx.supplychain.config.UtilsProperty;


public class CheckChainSign {
	
	public static Map<String, String[]> sortMapByKey(Map<String, String[]> map){
		
		if (map == null || map.isEmpty()) {  
	           return null;  
	    }
		
		Map<String, String[]> sortMap = new TreeMap<String, String[]>(new MapKeyComparator());
		sortMap.putAll(map);
		
		return sortMap;
	}
	
	public static boolean vertifySignature(HttpServletRequest request,String pubKey) {
		String sb = "";
		
		String strSign = request.getParameter("sign");
		
		Map<String, String[]> mapUrl = request.getParameterMap();
		
		mapUrl = sortMapByKey(mapUrl);
		
		sb += pubKey;
		
		for(Map.Entry<String, String[]> entry:mapUrl.entrySet()){
			
			if(entry.getKey().equals("sign")){
				
				continue;
			}
			
			String strValue[] = entry.getValue();
			
			sb += entry.getKey();
			sb += strValue[0];
		}
		
		sb += pubKey;

		String strMd5 = Md5Encrypt.md5(sb);

		return strSign.equals(strMd5);
	}
	
}
