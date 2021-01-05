package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMaterialInfoDTO implements Serializable{
	
	@ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = true, example = "")
	private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = true, example = "")
    private String materialName;
	@ApiModelProperty(name = "materialTotal", notes = "订购总数", dataType = "int", required = true, example = "")
    private Integer materialTotal;

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getMaterialTotal() {
		return materialTotal;
	}

	public void setMaterialTotal(Integer materialTotal) {
		this.materialTotal = materialTotal;
	}

	@Override
	public String toString() {
		return "JXCMaterialInfoDTO{" +
				"materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", materialTotal=" + materialTotal +
				'}';
	}
}
