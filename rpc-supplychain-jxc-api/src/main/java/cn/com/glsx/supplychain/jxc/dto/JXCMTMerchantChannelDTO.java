package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTMerchantChannelDTO implements Serializable{
	@ApiModelProperty(name = "id", notes = "商户渠道配置id", dataType = "int", required = true, example = "1")
	private Integer id;
	@ApiModelProperty(name = "name", notes = "商户渠道配置名称", dataType = "int", required = true, example = "1")
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
		return "JXCMTMerchantChannelDTO [id=" + id + ", name=" + name + "]";
	}
	
}
