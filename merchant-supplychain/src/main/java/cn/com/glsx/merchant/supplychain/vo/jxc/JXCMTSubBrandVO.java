package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTSubBrandVO implements Serializable{
	@ApiModelProperty(name = "id", notes = "id", dataType = "int", required = true, example = "1011")
	private Integer id;
	@ApiModelProperty(name = "subBrandName", notes = "子品牌名称", dataType = "int", required = true, example = "1011")
	private String subBrandName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubBrandName() {
		return subBrandName;
	}
	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
	}
	@Override
	public String toString() {
		return "JXCMTSubBrandVO [id=" + id + ", subBrandName=" + subBrandName
				+ "]";
	}
	
}
