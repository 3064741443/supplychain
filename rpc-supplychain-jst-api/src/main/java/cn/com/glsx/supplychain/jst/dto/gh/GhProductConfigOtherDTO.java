package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;

@SuppressWarnings("serial")
public class GhProductConfigOtherDTO extends BaseDTO implements Serializable{

	private Integer id;
	private String productConfigCode;
    private Integer attribInfoId;
    private String attribInfoName;
    private Integer attribTypeId;
    private String attribTypeName;
    private String option;
    
	public String getProductConfigCode() {
		return productConfigCode;
	}
	public void setProductConfigCode(String productConfigCode) {
		this.productConfigCode = productConfigCode;
	}
	public Integer getAttribInfoId() {
		return attribInfoId;
	}
	public void setAttribInfoId(Integer attribInfoId) {
		this.attribInfoId = attribInfoId;
	}
	public String getAttribInfoName() {
		return attribInfoName;
	}
	public void setAttribInfoName(String attribInfoName) {
		this.attribInfoName = attribInfoName;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
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
		return "GhProductConfigOtherDTO [productConfigCode="
				+ productConfigCode + ", attribInfoId=" + attribInfoId
				+ ", attribInfoName=" + attribInfoName + ", option=" + option
				+ "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
