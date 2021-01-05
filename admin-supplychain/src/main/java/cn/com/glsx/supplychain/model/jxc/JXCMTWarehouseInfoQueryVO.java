package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTWarehouseInfoQueryVO implements Serializable{
	
	@ApiModelProperty(name = "name", notes = "仓库名称", dataType = "string", required = false, example = "")
    private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "JXCMTWarehouseInfoQueryVO [name=" + name + "]";
	}
	
}
