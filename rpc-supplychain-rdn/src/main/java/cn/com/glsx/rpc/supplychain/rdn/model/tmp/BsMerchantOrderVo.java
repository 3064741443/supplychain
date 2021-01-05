package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BsMerchantOrderVo implements Serializable{
	
	private Integer id;
	private String merchantCode;
	private String merchantName;
	private Integer channelId;
	private String channelName;
	private String moOrderCode;
	private String merchantOrder;
	private Date orderTime;
	private Date checkTime;
	private String checkBy;
	private String productCode;
    private String productName;
	private Integer productTypeId;
	private String productTypeName;
    private String materialCode;
    private String materialName;
    private String orderMaterialCode;
    private String orderMaterialName;
    private Integer propQuantity;
    private Integer totalOrder;
    private Integer totalCheck;
    private Integer totalSends;
    private Integer totalOwes;
    private Integer acceptQuantity;
    private String status;
    private String remarks;
    private String checkRemark;
	private String rebackReason; 
    private String finishReason;
    private String recallReason;
    private String signStatus;
	private String orderTimeStart;
	private String orderTimeEnd;
	private String motorcyleFlag;
    private BsAddressVo bsAddressVo;
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
	public String getRecallReason() {
		return recallReason;
	}
	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
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
	public String getMotorcyleFlag() {
		return motorcyleFlag;
	}
	public void setMotorcyleFlag(String motorcyleFlag) {
		this.motorcyleFlag = motorcyleFlag;
	}
	public BsAddressVo getBsAddressVo() {
		return bsAddressVo;
	}
	public void setBsAddressVo(BsAddressVo bsAddressVo) {
		this.bsAddressVo = bsAddressVo;
	}
	@Override
	public String toString() {
		return "BsMerchantOrderVo [id=" + id + ", merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", moOrderCode="
				+ moOrderCode + ", merchantOrder=" + merchantOrder
				+ ", orderTime=" + orderTime + ", checkTime=" + checkTime
				+ ", checkBy=" + checkBy + ", productCode=" + productCode
				+ ", productName=" + productName + ", productTypeId="
				+ productTypeId + ", productTypeName=" + productTypeName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", orderMaterialCode=" + orderMaterialCode
				+ ", orderMaterialName=" + orderMaterialName
				+ ", propQuantity=" + propQuantity + ", totalOrder="
				+ totalOrder + ", totalCheck=" + totalCheck + ", totalSends="
				+ totalSends + ", totalOwes=" + totalOwes + ", acceptQuantity="
				+ acceptQuantity + ", status=" + status + ", remarks="
				+ remarks + ", checkRemark=" + checkRemark + ", rebackReason="
				+ rebackReason + ", finishReason=" + finishReason
				+ ", recallReason=" + recallReason + ", signStatus="
				+ signStatus + ", orderTimeStart=" + orderTimeStart
				+ ", orderTimeEnd=" + orderTimeEnd + ", motorcyleFlag="
				+ motorcyleFlag + ", bsAddressVo=" + bsAddressVo + "]";
	}

}
