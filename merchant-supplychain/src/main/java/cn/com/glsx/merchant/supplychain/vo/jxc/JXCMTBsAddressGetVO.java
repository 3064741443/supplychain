package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

public class JXCMTBsAddressGetVO {

	@ApiModelProperty(name = "id", notes = "地址id", dataType = "int", required = false, example = "3")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "JXCMTBsAddressGetVO [id=" + id + "]";
	}
	
	
}
