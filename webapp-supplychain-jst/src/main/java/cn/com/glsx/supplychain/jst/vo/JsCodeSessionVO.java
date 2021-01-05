package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect(JsonMethod.FIELD)
public class JsCodeSessionVO implements Serializable{

	private static final long serialVersionUID = 5543145431827278104L;

	@JsonProperty("openid")
	private String openId;
	
	@JsonProperty("session_key")
	private String sessionKey;
	
	@JsonProperty("unionid")
	private String unionId;
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Override
	public String toString() {
		return "JsCodeSessionDTO [openId=" + openId + ", sessionKey=" + sessionKey + ", unionId=" + unionId + "]";
	}
}
