package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderVehicleDTO implements Serializable{

	@ApiModelProperty(name = "moOrderCode", notes = "总订单编号", dataType = "string", required = false, example = "JXC202007241346310752")
	private String moOrderCode;
	@ApiModelProperty(name = "moParentBrandId", notes = "父品牌id", dataType = "int", required = true, example = "101")
    private Integer moParentBrandId;
	@ApiModelProperty(name = "moParentBrandName", notes = "父品牌名称", dataType = "string", required = true, example = "丰田")
    private String moParentBrandName;
	@ApiModelProperty(name = "moSubBrandId", notes = "子品牌id", dataType = "int", required = true, example = "222")
    private Integer moSubBrandId;
	@ApiModelProperty(name = "moSubBrandName", notes = "子品牌名称", dataType = "string", required = true, example = "凯美瑞")
    private String moSubBrandName;
	@ApiModelProperty(name = "moAudiId", notes = "车系id", dataType = "int", required = true, example = "333")
    private Integer moAudiId;
	@ApiModelProperty(name = "moAudiName", notes = "车系名称", dataType = "string", required = true, example = "君悦")
    private String moAudiName;
	@ApiModelProperty(name = "moMotorcycle", notes = "车型名称", dataType = "string", required = true, example = "2020款 君悦")
    private String moMotorcycle;
	@ApiModelProperty(name = "moRemark", notes = "车价/颜色", dataType = "string", required = true, example = "50万/红色")
    private String moRemark;
	@ApiModelProperty(name = "moTotal", notes = "订购数量", dataType = "int", required = true, example = "30")
    private Integer moTotal;
	public String getMoOrderCode() {
		return moOrderCode;
	}
	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}
	public Integer getMoParentBrandId() {
		return moParentBrandId;
	}
	public void setMoParentBrandId(Integer moParentBrandId) {
		this.moParentBrandId = moParentBrandId;
	}
	public String getMoParentBrandName() {
		return moParentBrandName;
	}
	public void setMoParentBrandName(String moParentBrandName) {
		this.moParentBrandName = moParentBrandName;
	}
	public Integer getMoSubBrandId() {
		return moSubBrandId;
	}
	public void setMoSubBrandId(Integer moSubBrandId) {
		this.moSubBrandId = moSubBrandId;
	}
	public String getMoSubBrandName() {
		return moSubBrandName;
	}
	public void setMoSubBrandName(String moSubBrandName) {
		this.moSubBrandName = moSubBrandName;
	}
	public Integer getMoAudiId() {
		return moAudiId;
	}
	public void setMoAudiId(Integer moAudiId) {
		this.moAudiId = moAudiId;
	}
	public String getMoAudiName() {
		return moAudiName;
	}
	public void setMoAudiName(String moAudiName) {
		this.moAudiName = moAudiName;
	}
	public String getMoMotorcycle() {
		return moMotorcycle;
	}
	public void setMoMotorcycle(String moMotorcycle) {
		this.moMotorcycle = moMotorcycle;
	}
	public String getMoRemark() {
		return moRemark;
	}
	public void setMoRemark(String moRemark) {
		this.moRemark = moRemark;
	}
	public Integer getMoTotal() {
		return moTotal;
	}
	public void setMoTotal(Integer moTotal) {
		this.moTotal = moTotal;
	}
	@Override
	public String toString() {
		return "JXCMTMerchantOrderVehicleDTO [moOrderCode=" + moOrderCode
				+ ", moParentBrandId=" + moParentBrandId
				+ ", moParentBrandName=" + moParentBrandName
				+ ", moSubBrandId=" + moSubBrandId + ", moSubBrandName="
				+ moSubBrandName + ", moAudiId=" + moAudiId + ", moAudiName="
				+ moAudiName + ", moMotorcycle=" + moMotorcycle + ", moRemark="
				+ moRemark + ", moTotal=" + moTotal + "]";
	}
	
}
