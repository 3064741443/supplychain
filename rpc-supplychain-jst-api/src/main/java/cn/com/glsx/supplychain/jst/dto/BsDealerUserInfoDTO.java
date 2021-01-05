package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BsDealerUserInfoDTO extends BaseDTO implements Serializable {

	private Long id;

	private String name;

	private Byte type;

	private Byte channel;

	private String merchantName;

	private String merchantCode;

	private String password;

	private Byte saleMode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getChannel() {
		return channel;
	}

	public void setChannel(Byte channel) {
		this.channel = channel;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getSaleMode() {
		return saleMode;
	}

	public void setSaleMode(Byte saleMode) {
		this.saleMode = saleMode;
	}

}
