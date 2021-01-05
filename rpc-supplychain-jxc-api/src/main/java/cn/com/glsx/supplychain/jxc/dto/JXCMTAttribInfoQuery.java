package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTAttribInfoQuery implements Serializable{

	@ApiModelProperty(name = "typeId", notes = "设备大类id", dataType = "int", required = false, example = "")
	private Integer typeId;
	@ApiModelProperty(name = "name", notes = "设备型号", dataType = "string", required = false, example = "")
	private  String name;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
