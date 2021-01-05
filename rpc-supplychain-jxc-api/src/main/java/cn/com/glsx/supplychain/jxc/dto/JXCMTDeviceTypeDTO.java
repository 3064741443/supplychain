package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTDeviceTypeDTO implements Serializable{

	@ApiModelProperty(name = "id", notes = "id", dataType = "int", required = false, example = "")
	private Integer id;
	@ApiModelProperty(name = "name", notes = "id", dataType = "string", required = false, example = "")
    private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "JXCMTDeviceTypeDTO [id=" + id + ", name=" + name + "]";
	}
	

}
