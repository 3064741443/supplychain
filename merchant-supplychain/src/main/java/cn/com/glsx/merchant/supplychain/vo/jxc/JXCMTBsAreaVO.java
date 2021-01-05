package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsAreaVO implements Serializable{

	@ApiModelProperty(name = "areaId", notes = "区域id", dataType = "int", required = true, example = "3")
    private Integer areaId;
	@ApiModelProperty(name = "areaName", notes = "区域名称", dataType = "string", required = true, example = "江海区")
    private String areaName;
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@Override
	public String toString() {
		return "JXCMTBsAreaVO [areaId=" + areaId + ", areaName=" + areaName
				+ "]";
	}
	
	
}
