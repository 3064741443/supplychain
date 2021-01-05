package cn.com.glsx.supplychain.jxc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderExportDTO implements Serializable{

	//子订单信息
	private String moOrderCode;
	private String merchantOrder;
	private String merchantName;
	private Integer channelId;
	private String channelName;
	private String productName;
	private String orderMaterialCode;
	private String orderMaterialName;
	private String materialCode;	
    private String materialName;
    private String mdeviceTypeName;
    private Double price;
    private String checkRemark;
    private String subjectName;
    private String insure;
	private Integer propQuantity;
    //发货信息
    private String dispatchOrderCode;
    private Integer bsTotal;
    private Integer bsCheckQuantity;
    private Integer bsSendQuantity;
    private Integer bsOweQuantity;
    private String bsParentBrandName;
    private String bsSubBrandName;
    private String bsAudiName;
    private String bsMotorcycle;
    private String bsRemark;
    private String warehouseName;
    private String address;
    private String contacts;
    private String mobile;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date orderTime;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date checkTime;
	private Integer status;
	private String checkBy;
	private String fastenConfigDesc;
	private String setOptionConfigDesc;

	public Integer getPropQuantity() {
		return propQuantity;
	}

	public void setPropQuantity(Integer propQuantity) {
		this.propQuantity = propQuantity;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}

	public String getFastenConfigDesc() {
		return fastenConfigDesc;
	}

	public void setFastenConfigDesc(String fastenConfigDesc) {
		this.fastenConfigDesc = fastenConfigDesc;
	}

	public String getSetOptionConfigDesc() {
		return setOptionConfigDesc;
	}

	public void setSetOptionConfigDesc(String setOptionConfigDesc) {
		this.setOptionConfigDesc = setOptionConfigDesc;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	//物流信息
    private List<JXCMTBsLogisticsDTO> listLogistics;
	public String getMoOrderCode() {
		return moOrderCode;
	}
	public void setMoOrderCode(String moOrderCode) {
		this.moOrderCode = moOrderCode;
	}
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderMaterialCode() {
		return orderMaterialCode;
	}
	public void setOrderMaterialCode(String orderMaterialCode) {
		this.orderMaterialCode = orderMaterialCode;
	}
	public String getOrderMaterialName() {
		return orderMaterialName;
	}
	public void setOrderMaterialName(String orderMaterialName) {
		this.orderMaterialName = orderMaterialName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMdeviceTypeName() {
		return mdeviceTypeName;
	}
	public void setMdeviceTypeName(String mdeviceTypeName) {
		this.mdeviceTypeName = mdeviceTypeName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getInsure() {
		return insure;
	}
	public void setInsure(String insure) {
		this.insure = insure;
	}
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
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
	public String getBsParentBrandName() {
		return bsParentBrandName;
	}
	public void setBsParentBrandName(String bsParentBrandName) {
		this.bsParentBrandName = bsParentBrandName;
	}
	public String getBsSubBrandName() {
		return bsSubBrandName;
	}
	public void setBsSubBrandName(String bsSubBrandName) {
		this.bsSubBrandName = bsSubBrandName;
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
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<JXCMTBsLogisticsDTO> getListLogistics() {
		return listLogistics;
	}
	public void setListLogistics(List<JXCMTBsLogisticsDTO> listLogistics) {
		this.listLogistics = listLogistics;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderExportDTO{" +
				"moOrderCode='" + moOrderCode + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", merchantName='" + merchantName + '\'' +
				", channelId=" + channelId +
				", channelName='" + channelName + '\'' +
				", productName='" + productName + '\'' +
				", orderMaterialCode='" + orderMaterialCode + '\'' +
				", orderMaterialName='" + orderMaterialName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", mdeviceTypeName='" + mdeviceTypeName + '\'' +
				", price=" + price +
				", checkRemark='" + checkRemark + '\'' +
				", subjectName='" + subjectName + '\'' +
				", insure='" + insure + '\'' +
				", dispatchOrderCode='" + dispatchOrderCode + '\'' +
				", bsTotal=" + bsTotal +
				", bsCheckQuantity=" + bsCheckQuantity +
				", bsSendQuantity=" + bsSendQuantity +
				", bsOweQuantity=" + bsOweQuantity +
				", bsParentBrandName='" + bsParentBrandName + '\'' +
				", bsSubBrandName='" + bsSubBrandName + '\'' +
				", bsAudiName='" + bsAudiName + '\'' +
				", bsMotorcycle='" + bsMotorcycle + '\'' +
				", bsRemark='" + bsRemark + '\'' +
				", warehouseName='" + warehouseName + '\'' +
				", address='" + address + '\'' +
				", contacts='" + contacts + '\'' +
				", mobile='" + mobile + '\'' +
				", orderTime=" + orderTime +
				", checkTime=" + checkTime +
				", status=" + status +
				", checkBy='" + checkBy + '\'' +
				", fastenConfigDesc='" + fastenConfigDesc + '\'' +
				", setOptionConfigDesc='" + setOptionConfigDesc + '\'' +
				", listLogistics=" + listLogistics +
				'}';
	}

	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

}
