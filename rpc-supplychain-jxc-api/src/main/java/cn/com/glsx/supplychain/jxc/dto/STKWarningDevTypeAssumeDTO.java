package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class STKWarningDevTypeAssumeDTO implements Serializable{

	@ApiModelProperty(name = "waringTotal", notes = "预警总数", dataType = "int", required = false, example = "")
	private Integer waringTotal;
	@ApiModelProperty(name = "listDetailDto", notes = "按照设备类型分类统计", dataType = "object", required = false, example = "")
	private List<STKWarningDevTypeDetailDTO> listDetailDto;
	public Integer getWaringTotal() {
		return waringTotal;
	}
	public void setWaringTotal(Integer waringTotal) {
		this.waringTotal = waringTotal;
	}
	public List<STKWarningDevTypeDetailDTO> getListDetailDto() {
		return listDetailDto;
	}
	public void setListDetailDto(List<STKWarningDevTypeDetailDTO> listDetailDto) {
		this.listDetailDto = listDetailDto;
	}
	@Override
	public String toString() {
		return "STKWarningDevTypeAssumeDTO [waringTotal=" + waringTotal
				+ ", listDetailDto=" + listDetailDto + "]";
	}
	
}
