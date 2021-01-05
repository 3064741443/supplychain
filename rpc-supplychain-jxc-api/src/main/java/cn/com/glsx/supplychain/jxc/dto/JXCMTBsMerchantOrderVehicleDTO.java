package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderVehicleDTO implements Serializable{
	
	@ApiModelProperty(name = "id", notes = "id", dataType = "int", required = false, example = "")
	private Integer id;
	@ApiModelProperty(name = "merchantCode", notes = "下单商户号", dataType = "string", required = false, example = "")
	private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "下单商户名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "merchantOrder", notes = "子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = false, example = "")
    private String dispatchOrderCode;
	@ApiModelProperty(name = "bsParentBrandId", notes = "父品牌id", dataType = "int", required = false, example = "")
    private Integer bsParentBrandId;
	@ApiModelProperty(name = "bsParentBrandName", notes = "父品牌名称", dataType = "string", required = false, example = "")
    private String bsParentBrandName;
	@ApiModelProperty(name = "bsSubBrandId", notes = "子品牌id", dataType = "int", required = false, example = "")
    private Integer bsSubBrandId;
	@ApiModelProperty(name = "bsSubBrandName", notes = "子品牌名称", dataType = "string", required = false, example = "")
    private String bsSubBrandName;
	@ApiModelProperty(name = "bsAudiId", notes = "车系id", dataType = "int", required = false, example = "")
    private Integer bsAudiId;
	@ApiModelProperty(name = "bsAudiName", notes = "车系名称", dataType = "string", required = false, example = "")
    private String bsAudiName;
	@ApiModelProperty(name = "bsMotorcycle", notes = "车型名称", dataType = "string", required = false, example = "")
    private String bsMotorcycle;
	@ApiModelProperty(name = "bsRemark", notes = "备注 车价/颜色等", dataType = "string", required = false, example = "")
    private String bsRemark;
	@ApiModelProperty(name = "bsTotal", notes = "采购总数", dataType = "int", required = false, example = "")
    private Integer bsTotal;
	@ApiModelProperty(name = "bsCheckQuantity", notes = "审核总数", dataType = "int", required = false, example = "")
    private Integer bsCheckQuantity;
	@ApiModelProperty(name = "bsSendQuantity", notes = "已发总数", dataType = "int", required = false, example = "")
    private Integer bsSendQuantity;
	@ApiModelProperty(name = "bsOweQuantity", notes = "欠数", dataType = "int", required = false, example = "")
    private Integer bsOweQuantity;
	@ApiModelProperty(name = "dispatchOrderStatus", notes = "状态 OV:已完成  UF:未完成 CL:已取消", dataType = "int", required = false, example = "")
    private String dispatchOrderStatus;
	@ApiModelProperty(name = "fastenConfigDesc", notes = "固定配置", dataType = "string", required = false, example = "")
	private String fastenConfigDesc;
	@ApiModelProperty(name = "optionConfigDesc", notes = "选择配置", dataType = "string", required = false, example = "")
	private String optionConfigDesc;
	@ApiModelProperty(name = "fastenConfigList", notes = "固定配置列表", dataType = "list", required = false, example = "")
	List<JXCMTAttribInfoDTO> fastenConfigList;
	@ApiModelProperty(name = "optionConfigList", notes = "选择配置列表", dataType = "list", required = false, example = "")
	List<JXCMTAttribInfoDTO> optionConfigList;

	public List<JXCMTAttribInfoDTO> getFastenConfigList() {
		return fastenConfigList;
	}

	public void setFastenConfigList(List<JXCMTAttribInfoDTO> fastenConfigList) {
		this.fastenConfigList = fastenConfigList;
	}

	public List<JXCMTAttribInfoDTO> getOptionConfigList() {
		return optionConfigList;
	}

	public void setOptionConfigList(List<JXCMTAttribInfoDTO> optionConfigList) {
		this.optionConfigList = optionConfigList;
	}

	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}

	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}

	public String getOptionConfigDesc() {
		return optionConfigDesc;
	}

	public void setOptionConfigDesc(String optionConfigDesc) {
		this.optionConfigDesc = optionConfigDesc;
	}
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public Integer getBsParentBrandId() {
		return bsParentBrandId;
	}
	public void setBsParentBrandId(Integer bsParentBrandId) {
		this.bsParentBrandId = bsParentBrandId;
	}
	public String getBsParentBrandName() {
		return bsParentBrandName;
	}
	public void setBsParentBrandName(String bsParentBrandName) {
		this.bsParentBrandName = bsParentBrandName;
	}
	public Integer getBsSubBrandId() {
		return bsSubBrandId;
	}
	public void setBsSubBrandId(Integer bsSubBrandId) {
		this.bsSubBrandId = bsSubBrandId;
	}
	public String getBsSubBrandName() {
		return bsSubBrandName;
	}
	public void setBsSubBrandName(String bsSubBrandName) {
		this.bsSubBrandName = bsSubBrandName;
	}
	public Integer getBsAudiId() {
		return bsAudiId;
	}
	public void setBsAudiId(Integer bsAudiId) {
		this.bsAudiId = bsAudiId;
	}
	public String getBsAudiName() {
		return bsAudiName;
	}
	public void setBsAudiName(String bsAudiName) {
		this.bsAudiName = bsAudiName;
	}
	public String getBsMotorcycle() {
		return bsMotorcycle;
	}
	public void setBsMotorcycle(String bsMotorcycle) {
		this.bsMotorcycle = bsMotorcycle;
	}
	public String getBsRemark() {
		return bsRemark;
	}
	public void setBsRemark(String bsRemark) {
		this.bsRemark = bsRemark;
	}
	public Integer getBsTotal() {
		return bsTotal;
	}
	public void setBsTotal(Integer bsTotal) {
		this.bsTotal = bsTotal;
	}
	public Integer getBsCheckQuantity() {
		return bsCheckQuantity;
	}
	public void setBsCheckQuantity(Integer bsCheckQuantity) {
		this.bsCheckQuantity = bsCheckQuantity;
	}
	public Integer getBsSendQuantity() {
		return bsSendQuantity;
	}
	public void setBsSendQuantity(Integer bsSendQuantity) {
		this.bsSendQuantity = bsSendQuantity;
	}
	public Integer getBsOweQuantity() {
		return bsOweQuantity;
	}
	public void setBsOweQuantity(Integer bsOweQuantity) {
		this.bsOweQuantity = bsOweQuantity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getDispatchOrderStatus() {
		return dispatchOrderStatus;
	}
	public void setDispatchOrderStatus(String dispatchOrderStatus) {
		this.dispatchOrderStatus = dispatchOrderStatus;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderVehicleDTO{" +
				"id=" + id +
				", merchantCode='" + merchantCode + '\'' +
				", merchantName='" + merchantName + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", dispatchOrderCode='" + dispatchOrderCode + '\'' +
				", bsParentBrandId=" + bsParentBrandId +
				", bsParentBrandName='" + bsParentBrandName + '\'' +
				", bsSubBrandId=" + bsSubBrandId +
				", bsSubBrandName='" + bsSubBrandName + '\'' +
				", bsAudiId=" + bsAudiId +
				", bsAudiName='" + bsAudiName + '\'' +
				", bsMotorcycle='" + bsMotorcycle + '\'' +
				", bsRemark='" + bsRemark + '\'' +
				", bsTotal=" + bsTotal +
				", bsCheckQuantity=" + bsCheckQuantity +
				", bsSendQuantity=" + bsSendQuantity +
				", bsOweQuantity=" + bsOweQuantity +
				", dispatchOrderStatus='" + dispatchOrderStatus + '\'' +
				", fastenConfigDesc='" + fastenConfigDesc + '\'' +
				", optionConfigDesc='" + optionConfigDesc + '\'' +
				", fastenConfigList=" + fastenConfigList +
				", optionConfigList=" + optionConfigList +
				'}';
	}
}
