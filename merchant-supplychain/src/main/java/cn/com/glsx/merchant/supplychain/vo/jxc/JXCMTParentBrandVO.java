package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTParentBrandVO implements Serializable{

	@ApiModelProperty(name = "id", notes = "id", dataType = "int", required = true, example = "1011")
	private Integer id;
	@ApiModelProperty(name = "parentBrandName", notes = "父品牌名称", dataType = "int", required = true, example = "1011")
	private String parentBrandName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParentBrandName() {
		return parentBrandName;
	}
	public void setParentBrandName(String parentBrandName) {
		this.parentBrandName = parentBrandName;
	}
	@Override
	public String toString() {
		return "JXCMTParentBrandVO [id=" + id + ", parentBrandName="
				+ parentBrandName + "]";
	}
	
	
}
