package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTDeviceTypeVO implements Serializable{

	@ApiModelProperty(name = "id", notes = "id", dataType = "int", required = true, example = "")
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
	
	
}
