package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTMerchantOrderDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "id", notes = "订单主表数据库id", dataType = "string", required = true, example = "")
	private Integer id;
	@ApiModelProperty(name = "moOrderCode", notes = "订单编号", dataType = "string", required = true, example = "202007241346310752")
	private String moOrderCode;
	@ApiModelProperty(name = "moMerchantCode", notes = "商户编码", dataType = "string", required = true, example = "")
	private String moMerchantCode;
	@ApiModelProperty(name = "moMerchantName", notes = "商户名称", dataType = "string", required = true, example = "")
	private String moMerchantName;
	@ApiModelProperty(name = "moProductCode", notes = "产品编码", dataType = "string", required = true, example = "")
	private String moProductCode;
	@ApiModelProperty(name = "moProductName", notes = "产品名称", dataType = "string", required = true, example = "")
	private String moProductName;
	@ApiModelProperty(name = "moProductName", notes = "产品套餐", dataType = "string", required = true, example = "")
	private String moProductPackage;
	@ApiModelProperty(name = "moProductServiceTime", notes = "产品服务期限", dataType = "string", required = true, example = "")
	private String moProductServiceTime;
	@ApiModelProperty(name = "moTotal", notes = "产品订购数量", dataType = "Integer", required = true, example = "")
	private Integer moTotal;
	@ApiModelProperty(name = "moPrice", notes = "产品单价", dataType = "Double", required = true, example = "")
	private Double moPrice;
	@ApiModelProperty(name = "moRemark", notes = "订单备注", dataType = "String", required = true, example = "")
	private String moRemark;
	@ApiModelProperty(name = "moHopeTime", notes = "期望到货时间", dataType = "String", required = true, example = "")
	private String moHopeTime;
	@ApiModelProperty(name = "supportUpdate", notes = "是否可以修改 Y:可以 N:不可以", dataType = "String", required = true, example = "")
	private String supportUpdate;
	@ApiModelProperty(name = "bsAddress", notes = "地址", dataType = "object", required = true, example = "")
	private JXCMTBsAddressDTO bsAddress;
	@ApiModelProperty(name = "bsAddress", notes = "订单物料信息", dataType = "object", required = true, example = "")
	private List<JXCMTMerchantOrderMaterialDTO> listMerchantOrderMaterials;
	@ApiModelProperty(name = "bsAddress", notes = "订单车辆信息", dataType = "object", required = true, example = "")
	private List<JXCMTMerchantOrderVehicleDTO> listMerchantOrderVehicles;
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
	public String getMoMerchantCode() {
		return moMerchantCode;
	}
	public void setMoMerchantCode(String moMerchantCode) {
		this.moMerchantCode = moMerchantCode;
	}
	public String getMoMerchantName() {
		return moMerchantName;
	}
	public void setMoMerchantName(String moMerchantName) {
		this.moMerchantName = moMerchantName;
	}
	public String getMoProductCode() {
		return moProductCode;
	}
	public void setMoProductCode(String moProductCode) {
		this.moProductCode = moProductCode;
	}
	public String getMoProductName() {
		return moProductName;
	}
	public void setMoProductName(String moProductName) {
		this.moProductName = moProductName;
	}
	public String getMoProductPackage() {
		return moProductPackage;
	}
	public void setMoProductPackage(String moProductPackage) {
		this.moProductPackage = moProductPackage;
	}
	public String getMoProductServiceTime() {
		return moProductServiceTime;
	}
	public void setMoProductServiceTime(String moProductServiceTime) {
		this.moProductServiceTime = moProductServiceTime;
	}
	public Integer getMoTotal() {
		return moTotal;
	}
	public void setMoTotal(Integer moTotal) {
		this.moTotal = moTotal;
	}
	public Double getMoPrice() {
		return moPrice;
	}
	public void setMoPrice(Double moPrice) {
		this.moPrice = moPrice;
	}
	public String getMoRemark() {
		return moRemark;
	}
	public void setMoRemark(String moRemark) {
		this.moRemark = moRemark;
	}
	public String getMoHopeTime() {
		return moHopeTime;
	}
	public void setMoHopeTime(String moHopeTime) {
		this.moHopeTime = moHopeTime;
	}
	public JXCMTBsAddressDTO getBsAddress() {
		return bsAddress;
	}
	public void setBsAddress(JXCMTBsAddressDTO bsAddress) {
		this.bsAddress = bsAddress;
	}
	public List<JXCMTMerchantOrderMaterialDTO> getListMerchantOrderMaterials() {
		return listMerchantOrderMaterials;
	}
	public void setListMerchantOrderMaterials(
			List<JXCMTMerchantOrderMaterialDTO> listMerchantOrderMaterials) {
		this.listMerchantOrderMaterials = listMerchantOrderMaterials;
	}
	public List<JXCMTMerchantOrderVehicleDTO> getListMerchantOrderVehicles() {
		return listMerchantOrderVehicles;
	}
	public void setListMerchantOrderVehicles(
			List<JXCMTMerchantOrderVehicleDTO> listMerchantOrderVehicles) {
		this.listMerchantOrderVehicles = listMerchantOrderVehicles;
	}
	public String getSupportUpdate() {
		return supportUpdate;
	}
	public void setSupportUpdate(String supportUpdate) {
		this.supportUpdate = supportUpdate;
	}
	@Override
	public String toString() {
		return "JXCMTMerchantOrderDTO [id=" + id + ", moOrderCode="
				+ moOrderCode + ", moMerchantCode=" + moMerchantCode
				+ ", moMerchantName=" + moMerchantName + ", moProductCode="
				+ moProductCode + ", moProductName=" + moProductName
				+ ", moProductPackage=" + moProductPackage
				+ ", moProductServiceTime=" + moProductServiceTime
				+ ", moTotal=" + moTotal + ", moPrice=" + moPrice
				+ ", moRemark=" + moRemark + ", moHopeTime=" + moHopeTime
				+ ", bsAddress=" + bsAddress + ", listMerchantOrderMaterials="
				+ listMerchantOrderMaterials + ", listMerchantOrderVehicles="
				+ listMerchantOrderVehicles + "]";
	}
	
}
