package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTAttribManaPageVO implements Serializable{

	@ApiModelProperty(name = "deviceTypeId", notes = "设备大类id", dataType = "int", required = false, example = "")
	private Integer deviceTypeId;
	@ApiModelProperty(name = "attribName", notes = "硬件配置编码等同物料编码", dataType = "string", required = false, example = "")
	private String attribName;
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getAttribName() {
		return attribName;
	}
	public void setAttribName(String attribName) {
		this.attribName = attribName;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "JXCMTAttribManaPageVO [deviceTypeId=" + deviceTypeId
				+ ", attribCode="  + ", attribName=" + attribName
				+ ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
    
    
}
