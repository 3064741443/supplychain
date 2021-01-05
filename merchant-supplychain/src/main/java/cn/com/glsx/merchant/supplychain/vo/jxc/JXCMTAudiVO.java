package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTAudiVO implements Serializable{

	@ApiModelProperty(name = "id", notes = "id", dataType = "int", required = true, example = "1011")
	private Integer id;
	@ApiModelProperty(name = "audiName", notes = "子品牌名称", dataType = "int", required = true, example = "1011")
	private String audiName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAudiName() {
		return audiName;
	}
	public void setAudiName(String audiName) {
		this.audiName = audiName;
	}
	@Override
	public String toString() {
		return "JXCMTAudiVO [id=" + id + ", audiName=" + audiName + "]";
	}
	
	
	
}
