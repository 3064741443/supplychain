package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;

@SuppressWarnings("serial")
public class GhShoppingCartConfigDTO extends BaseDTO implements Serializable{

	private Integer id;
    private String cartCode;
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

	public String getCartCode() {
		return cartCode;
	}

	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
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
		return "GhShoppingCartConfigDTO [id=" + id + ", cartCode=" + cartCode
				+ ", attribInfoId=" + attribInfoId + ", attribInfoName="
				+ attribInfoName + ", attribTypeId=" + attribTypeId
				+ ", attribTypeName=" + attribTypeName + ", option=" + option
				+ "]";
	} 
	
}
