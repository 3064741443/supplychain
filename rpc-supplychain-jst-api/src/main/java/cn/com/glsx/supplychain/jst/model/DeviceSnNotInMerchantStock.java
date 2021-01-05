package cn.com.glsx.supplychain.jst.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DeviceSnNotInMerchantStock implements Serializable{

	private String sn;
	private String sendMerchantNo;
	private String attribCode;
	private String iccid;
	private Date updatedDate;
	private Integer logisticsId;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSendMerchantNo() {
		return sendMerchantNo;
	}
	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}
	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Integer getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}
	
	
}
