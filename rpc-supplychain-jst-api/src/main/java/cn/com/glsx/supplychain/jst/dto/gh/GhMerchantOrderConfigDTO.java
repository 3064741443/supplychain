package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;

public class GhMerchantOrderConfigDTO implements Serializable{

	private Integer id;
    private String ghMerchantOrderCode;
    private Integer attribInfoId;
    private String attribInfoName;
    private Integer attribTypeId;
    private String attribTypeName;
    private String option;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGhMerchantOrderCode() {
		return ghMerchantOrderCode;
	}
	public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
		this.ghMerchantOrderCode = ghMerchantOrderCode;
	}
	public Integer getAttribInfoId() {
		return attribInfoId;
	}
	public void setAttribInfoId(Integer attribInfoId) {
		this.attribInfoId = attribInfoId;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getAttribInfoName() {
		return attribInfoName;
	}
	public void setAttribInfoName(String attribInfoName) {
		this.attribInfoName = attribInfoName;
	}
	public Integer getAttribTypeId() {
		return attribTypeId;
	}
	public void setAttribTypeId(Integer attribTypeId) {
		this.attribTypeId = attribTypeId;
	}
	public String getAttribTypeName() {
		return attribTypeName;
	}
	public void setAttribTypeName(String attribTypeName) {
		this.attribTypeName = attribTypeName;
	}
	@Override
	public String toString() {
		return "GhMerchantOrderConfigDTO [id=" + id + ", ghMerchantOrderCode="
				+ ghMerchantOrderCode + ", attribInfoId=" + attribInfoId
				+ ", attribInfoName=" + attribInfoName + ", attribTypeId="
				+ attribTypeId + ", attribTypeName=" + attribTypeName
				+ ", option=" + option + "]";
	}
    
    
}
