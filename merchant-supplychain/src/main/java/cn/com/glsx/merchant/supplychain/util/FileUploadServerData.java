package cn.com.glsx.merchant.supplychain.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class FileUploadServerData implements Serializable {
	
	public static final int UPLOAD_SUCCSSS = 0; // "上传文件成功！",
	public static final int UPLOAD_FAILURE = 1; // "上传文件失败！"),
	//private static final int UPLOAD_TYPE_ERROR = 2; // "上传文件类型错误！"),
	//private static final int UPLOAD_OVERSIZE = 3; // "上传文件过大！"),
	//private static final int UPLOAD_ZEROSIZE = 4; // "上传文件为空！"),
	//private static final int UPLOAD_NOTFOUND = 5; // "上传文件路径错误！")
	
	private Integer status = 0;
	private String message = "";
	private String url = "";

	private String systemSign;
	private String filePath;
	private String param1;
	private String param2;
	private String param3;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSystemSign() {
		return systemSign;
	}

	public void setSystemSign(String systemSign) {
		this.systemSign = systemSign;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("status:" + this.status + ",");
		sb.append("message:'" + this.message + "',");
		sb.append("url:'" + this.url + "',");
		sb.append("systemSign:'" + this.systemSign + "',");
		sb.append("filePath:'" + this.filePath + "',");
		sb.append("param1:'" + this.param1 + "',");
		sb.append("param2:'" + this.param2 + "',");
		sb.append("param3:'" + this.param3 + "'");
		sb.append("}");
		return sb.toString();
	}
	
	public FileUploadServerData() {
		
	}
	
	public FileUploadServerData(HttpServletRequest request) {
		this.systemSign = request.getParameter("systemSign");
		this.filePath = request.getParameter("filePath");
		this.param1 = request.getParameter("param1");
		this.param2 = request.getParameter("param2");
		this.param3 = request.getParameter("param3");
	}

}
