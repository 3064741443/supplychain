package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTProductTypeDTO implements Serializable{
	@ApiModelProperty(name = "id", notes = "产品分类配置id", dataType = "int", required = true, example = "1")
	private Integer id;
	@ApiModelProperty(name = "name", notes = "产品分类配置名称", dataType = "int", required = true, example = "爱车保镖")
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
		return "JXCMTProductTypeDTO [id=" + id + ", name=" + name + "]";
	}
}
