package cn.com.glsx.supplychain.exception;

/**
 * @Title: ServiceException.java
 * @Description:
 * @author ${userName}  
 * @date 2017年12月23日 上午11:39:23
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {
	
	private ErrorCode code;
	
	private String errId;
	
	public enum ErrorCode{
		
		SERVER_ERROR("服务器异常"),
		SERVICE_ERROR("业务异常"),
		VALIDATE_FAILED("验签失败"),
		NULL("数据为空"), 
		EXIST("数据已存在"),
		INVALID("数据无效");
		
		private final String value;
		
		ErrorCode(String value){
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
	}
	
	public ServiceException() {
	}
	
	public ServiceException(String message) {
        super(message);
    }
	
	public ServiceException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
	
	public ServiceException(String errId, String message) {
        super(message);
        this.errId = errId;
    }
	
	public ServiceException(ErrorCode code, String errId, String message) {
        super(message);
        this.code = code;
        this.errId = errId;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

	public ErrorCode getCode() {
		return code;
	}

	public void setCode(ErrorCode code) {
		this.code = code;
	}

	public String getErrId() {
		return errId;
	}

	public void setErrId(String errId) {
		this.errId = errId;
	}
}
