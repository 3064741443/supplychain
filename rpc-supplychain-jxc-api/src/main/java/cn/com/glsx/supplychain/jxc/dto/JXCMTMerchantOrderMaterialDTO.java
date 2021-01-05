package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderMaterialDTO implements Serializable{
	
	@ApiModelProperty(name = "id", notes = "数据库id", dataType = "Integer", required = true, example = "")
	private Integer id;
	@ApiModelProperty(name = "moOrderCode", notes = "商户总订单号", dataType = "string", required = true, example = "")
	private String moOrderCode;
	@ApiModelProperty(name = "moProductCode", notes = "产品编码", dataType = "string", required = true, example = "")
	private String moProductCode;
	@ApiModelProperty(name = "moMaterialCode", notes = "物料编码", dataType = "string", required = true, example = "")
	private String moMaterialCode;
	@ApiModelProperty(name = "moMaterialName", notes = "物料名称", dataType = "string", required = true, example = "")
	private String moMaterialName;
	@ApiModelProperty(name = "moPropQuantity", notes = "单套数量", dataType = "string", required = true, example = "")
	private Integer moPropQuantity;
	@ApiModelProperty(name = "moTotal", notes = "订购数量", dataType = "string", required = true, example = "")
	private Integer moTotal;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMoOrderCode() {
		return moOrderCode;
	}
	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}
	public String getMoProductCode() {
		return moProductCode;
	}
	public void setMoProductCode(String moProductCode) {
		this.moProductCode = moProductCode;
	}
	public String getMoMaterialCode() {
		return moMaterialCode;
	}
	public void setMoMaterialCode(String moMaterialCode) {
		this.moMaterialCode = moMaterialCode;
	}
	public String getMoMaterialName() {
		return moMaterialName;
	}
	public void setMoMaterialName(String moMaterialName) {
		this.moMaterialName = moMaterialName;
	}
	public Integer getMoPropQuantity() {
		return moPropQuantity;
	}
	public void setMoPropQuantity(Integer moPropQuantity) {
		this.moPropQuantity = moPropQuantity;
	}
	public Integer getMoTotal() {
		return moTotal;
	}
	public void setMoTotal(Integer moTotal) {
		this.moTotal = moTotal;
	}
	@Override
	public String toString() {
		return "JXCMTMerchantOrderMaterialDTO [id=" + id + ", moOrderCode="
				+ moOrderCode + ", moProductCode=" + moProductCode
				+ ", moMaterialCode=" + moMaterialCode + ", moMaterialName="
				+ moMaterialName + ", moPropQuantity=" + moPropQuantity
				+ ", moTotal=" + moTotal + "]";
	}
	
}
