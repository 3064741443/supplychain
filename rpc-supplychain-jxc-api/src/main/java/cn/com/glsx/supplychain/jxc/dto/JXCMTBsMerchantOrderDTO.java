package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderDTO extends JXCMTBaseDTO implements Serializable{
	@ApiModelProperty(name = "id", notes = "商户子订单数据库id", dataType = "int", required = false, example = "")
	private Integer id;
	@ApiModelProperty(name = "merchantCode", notes = "下单商户号", dataType = "string", required = false, example = "")
	private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "下单商户名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "moOrderCode", notes = "总订单编号", dataType = "string", required = false, example = "")
	private String moOrderCode;
	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = false, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "orderTime", notes = "下单时间", dataType = "string", required = false, example = "")
	private Date orderTime;
	@ApiModelProperty(name = "checkTime", notes = "审核时间", dataType = "string", required = false, example = "")
	private Date checkTime;
	@ApiModelProperty(name = "checkBy", notes = "审核人", dataType = "string", required = false, example = "")
	private String checkBy;
	@ApiModelProperty(name = "productCode", notes = "产品编号", dataType = "string", required = false, example = "")
	private String productCode;
	@ApiModelProperty(name = "productName", notes = "产品名称", dataType = "string", required = false, example = "")
    private String productName;
	@ApiModelProperty(name = "productTypeId", notes = "产品类型id", dataType = "int", required = false, example = "")
	private Integer productTypeId;
	@ApiModelProperty(name = "productTypeName", notes = "产品类型名称", dataType = "string", required = false, example = "")
	private String productTypeName;
	@ApiModelProperty(name = "materialCode", notes = "审核物料编号", dataType = "string", required = false, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "审核物料名称", dataType = "string", required = false, example = "")
    private String materialName;
	@ApiModelProperty(name = "mdeviceTypeId", notes = "审核物料设备大类id", dataType = "int", required = false, example = "")
    private Integer mdeviceTypeId;
	@ApiModelProperty(name = "mdeviceTypeName", notes = "审核物料设备大类名称", dataType = "string", required = false, example = "")
    private String mdeviceTypeName;
	@ApiModelProperty(name = "orderMaterialCode", notes = "采购物料编号", dataType = "string", required = false, example = "")
    private String orderMaterialCode;
	@ApiModelProperty(name = "orderMaterialName", notes = "采购物料名称", dataType = "string", required = false, example = "")
    private String orderMaterialName;
	@ApiModelProperty(name = "odeviceTypeId", notes = "采购物料设备大类id", dataType = "int", required = false, example = "")
    private Integer odeviceTypeId;
	@ApiModelProperty(name = "odeviceTypeName", notes = "采购物料设备大类名称", dataType = "string", required = false, example = "")
    private String odeviceTypeName;
	@ApiModelProperty(name = "propQuantity", notes = "单套数量", dataType = "int", required = false, example = "")
    private Integer propQuantity;
	@ApiModelProperty(name = "totalOrder", notes = "订购数量", dataType = "int", required = false, example = "")
    private Integer totalOrder;
	@ApiModelProperty(name = "totalCheck", notes = "审核数量", dataType = "int", required = false, example = "")
    private Integer totalCheck;
	@ApiModelProperty(name = "totalSends", notes = "已发货数量", dataType = "int", required = false, example = "")
    private Integer totalSends;
	@ApiModelProperty(name = "totalOwes", notes = "总欠数", dataType = "int", required = false, example = "")
    private Integer totalOwes;
	@ApiModelProperty(name = "acceptQuantity", notes = "签收数量", dataType = "int", required = false, example = "")
    private Integer acceptQuantity;
	@ApiModelProperty(name = "status", notes = "订单状态", dataType = "string", required = false, example = "")
    private String status;
	@ApiModelProperty(name = "remarks", notes = "订单备注", dataType = "string", required = false, example = "")
    private String remarks;
	@ApiModelProperty(name = "checkRemark", notes = "审核备注", dataType = "string", required = false, example = "")
    private String checkRemark;
	@ApiModelProperty(name = "rebackReason", notes = "驳回原因", dataType = "string", required = false, example = "")
	private String rebackReason; 
	@ApiModelProperty(name = "finishReason", notes = "提前结束原因", dataType = "string", required = false, example = "")
    private String finishReason;
	@ApiModelProperty(name = "recallReason", notes = "撤回原因", dataType = "string", required = false, example = "")
    private String recallReason;
	@ApiModelProperty(name = "signStatus", notes = "签收状态", dataType = "string", required = false, example = "")
    private String signStatus;
	@ApiModelProperty(name = "orderTimeStart", notes = "下单时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeStart;
	@ApiModelProperty(name = "orderTimeEnd", notes = "下单时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String orderTimeEnd;
	@ApiModelProperty(name = "motorcyleFlag", notes = "是否拥有车型车系标识 Y:有 N:无", dataType = "object", required = false, example = "")
	private String motorcyleFlag;
	@ApiModelProperty(name = "updateFlag", notes = "是否可修改标识 Y:可以 N:不可以", dataType = "object", required = false, example = "")
	private String updateFlag;
	@ApiModelProperty(name = "bsAddressDto", notes = "收获地址信息", dataType = "object", required = false, example = "")
    private JXCMTBsAddressDTO bsAddressDto;

	@ApiModelProperty(name = "spaPurchaseCode", notes = "采购订单编码", dataType = "object", required = false, example = "")
	private String  spaPurchaseCode;
	@ApiModelProperty(name = "checkTimeStart", notes = "审核时间开始（搜索条件）", dataType = "string", required = false, example = "")
	private String checkTimeStart;
	@ApiModelProperty(name = "checkTimeEnd", notes = "审核时间结束（搜索条件）", dataType = "string", required = false, example = "")
	private String checkTimeEnd;

	public String getCheckTimeStart() {
		return checkTimeStart;
	}

	public void setCheckTimeStart(String checkTimeStart) {
		this.checkTimeStart = checkTimeStart;
	}

	public String getCheckTimeEnd() {
		return checkTimeEnd;
	}

	public void setCheckTimeEnd(String checkTimeEnd) {
		this.checkTimeEnd = checkTimeEnd;
	}

	public String getSpaPurchaseCode() {
		return spaPurchaseCode;
	}

	public void setSpaPurchaseCode(String spaPurchaseCode) {
		this.spaPurchaseCode = spaPurchaseCode;
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
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public Integer getPropQuantity() {
		return propQuantity;
	}
	public void setPropQuantity(Integer propQuantity) {
		this.propQuantity = propQuantity;
	}
	public Integer getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(Integer totalOrder) {
		this.totalOrder = totalOrder;
	}
	public Integer getTotalCheck() {
		return totalCheck;
	}
	public void setTotalCheck(Integer totalCheck) {
		this.totalCheck = totalCheck;
	}
	public Integer getTotalSends() {
		return totalSends;
	}
	public void setTotalSends(Integer totalSends) {
		this.totalSends = totalSends;
	}
	public Integer getTotalOwes() {
		return totalOwes;
	}
	public void setTotalOwes(Integer totalOwes) {
		this.totalOwes = totalOwes;
	}
	public Integer getAcceptQuantity() {
		return acceptQuantity;
	}
	public void setAcceptQuantity(Integer acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getRebackReason() {
		return rebackReason;
	}
	public void setRebackReason(String rebackReason) {
		this.rebackReason = rebackReason;
	}
	public String getFinishReason() {
		return finishReason;
	}
	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getOrderTimeStart() {
		return orderTimeStart;
	}
	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}
	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}
	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}
	public JXCMTBsAddressDTO getBsAddressDto() {
		return bsAddressDto;
	}
	public void setBsAddressDto(JXCMTBsAddressDTO bsAddressDto) {
		this.bsAddressDto = bsAddressDto;
	}
	public String getRecallReason() {
		return recallReason;
	}
	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
	}
	public String getMotorcyleFlag() {
		return motorcyleFlag;
	}
	public void setMotorcyleFlag(String motorcyleFlag) {
		this.motorcyleFlag = motorcyleFlag;
	}
	public Integer getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public Integer getMdeviceTypeId() {
		return mdeviceTypeId;
	}
	public void setMdeviceTypeId(Integer mdeviceTypeId) {
		this.mdeviceTypeId = mdeviceTypeId;
	}
	public String getMdeviceTypeName() {
		return mdeviceTypeName;
	}
	public void setMdeviceTypeName(String mdeviceTypeName) {
		this.mdeviceTypeName = mdeviceTypeName;
	}
	public Integer getOdeviceTypeId() {
		return odeviceTypeId;
	}
	public void setOdeviceTypeId(Integer odeviceTypeId) {
		this.odeviceTypeId = odeviceTypeId;
	}
	public String getOdeviceTypeName() {
		return odeviceTypeName;
	}
	public void setOdeviceTypeName(String odeviceTypeName) {
		this.odeviceTypeName = odeviceTypeName;
	}
	
	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderDTO{" +
				"id=" + id +
				", merchantCode='" + merchantCode + '\'' +
				", merchantName='" + merchantName + '\'' +
				", channelId=" + channelId +
				", channelName='" + channelName + '\'' +
				", moOrderCode='" + moOrderCode + '\'' +
				", merchantOrder='" + merchantOrder + '\'' +
				", orderTime=" + orderTime +
				", checkTime=" + checkTime +
				", checkBy='" + checkBy + '\'' +
				", productCode='" + productCode + '\'' +
				", productName='" + productName + '\'' +
				", productTypeId=" + productTypeId +
				", productTypeName='" + productTypeName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", mdeviceTypeId=" + mdeviceTypeId +
				", mdeviceTypeName='" + mdeviceTypeName + '\'' +
				", orderMaterialCode='" + orderMaterialCode + '\'' +
				", orderMaterialName='" + orderMaterialName + '\'' +
				", odeviceTypeId=" + odeviceTypeId +
				", odeviceTypeName='" + odeviceTypeName + '\'' +
				", propQuantity=" + propQuantity +
				", totalOrder=" + totalOrder +
				", totalCheck=" + totalCheck +
				", totalSends=" + totalSends +
				", totalOwes=" + totalOwes +
				", acceptQuantity=" + acceptQuantity +
				", status='" + status + '\'' +
				", remarks='" + remarks + '\'' +
				", checkRemark='" + checkRemark + '\'' +
				", rebackReason='" + rebackReason + '\'' +
				", finishReason='" + finishReason + '\'' +
				", recallReason='" + recallReason + '\'' +
				", signStatus='" + signStatus + '\'' +
				", orderTimeStart='" + orderTimeStart + '\'' +
				", orderTimeEnd='" + orderTimeEnd + '\'' +
				", motorcyleFlag='" + motorcyleFlag + '\'' +
				", updateFlag='" + updateFlag + '\'' +
				", bsAddressDto=" + bsAddressDto +
				", spaPurchaseCode='" + spaPurchaseCode + '\'' +
				", checkTimeStart='" + checkTimeStart + '\'' +
				", checkTimeEnd='" + checkTimeEnd + '\'' +
				'}';
	}


}
