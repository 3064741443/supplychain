package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTWarehouseInfoDTO implements Serializable{
	@ApiModelProperty(name = "id", notes = "地址id", dataType = "int", required = false, example = "3")
	private Integer id;
	@ApiModelProperty(name = "name", notes = "仓库名称", dataType = "string", required = false, example = "")
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
		return "JXCMTWarehouseInfoDTO [id=" + id + ", name=" + name + "]";
	}
	
}
