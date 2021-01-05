package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderVehicleVO implements Serializable{

	@ApiModelProperty(name = "id", notes = "数据id", dataType = "Integer", required = false, example = "")
	private Integer id;
	@ApiModelProperty(name = "bsCheckQuantity", notes = "审核总数", dataType = "int", required = false, example = "")
    private Integer bsCheckQuantity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBsCheckQuantity() {
		return bsCheckQuantity;
	}
	public void setBsCheckQuantity(Integer bsCheckQuantity) {
		this.bsCheckQuantity = bsCheckQuantity;
	}
	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderVehicleVO [id=" + id + ", bsCheckQuantity="
				+ bsCheckQuantity + "]";
	}
	
	
}
