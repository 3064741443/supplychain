package cn.com.glsx.supplychain.jst.dto.gh;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;

public class AttribInfoDTO extends BaseDTO implements Serializable{

	@ApiModelProperty(name = "id", notes = "配置id", dataType = "Integer", required = true, example = "1")
	private Integer id;
	private Integer type;
	private String name;
	private String comment;
	private Integer value;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
	public String toString() {
		return "AttribInfoDTO [id=" + id + ", type=" + type + ", name=" + name
				+ ", comment=" + comment + "]";
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
