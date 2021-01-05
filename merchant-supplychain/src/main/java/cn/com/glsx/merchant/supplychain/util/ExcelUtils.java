package cn.com.glsx.merchant.supplychain.util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * excel工具类
 * 
 */
public class ExcelUtils {
	
	/**
	 * @Title: downloadExcelTemplate
	 * @Description: 下载Excle模版文件
	 * @author: 
	 * @param os
	 * @param path
	 * @throws Exception
	 */
	public static void downloadExcelTemplate(OutputStream os, String path) throws Exception {
		InputStream is = 
			Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		
		byte[] bs = new byte[1024];
		int len = -1;
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		os.flush();
		if (os != null) {
			os.close();
		}
		if (is != null) {
			is.close();
		}
	}
	
}
