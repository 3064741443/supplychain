package cn.com.glsx.supplychain.jst.dto.gh;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class GhMerchantOrderDTO extends BaseDTO implements Serializable{

	private Integer id;
    private String ghMerchantOrderCode;
    private Date orderTime;
    private String merchantCode;
    private String productConfigCode;
    private Integer parentBrandId;
    private String parentBrandName;
    private Integer subBrandId;
    private String subBrandName;
    private Integer audiId;
    private String audiName;
    private String motorcycle;
    private Integer categoryId;
    private String categoryName;
    private String spaPurchaseCode;
    private String spaProductCode;
    private String spaProductName;
    private String glsxProductCode;
    private String glsxProductName;
    private Integer total;
    private String remark;
    private String status;
    private List<GhMerchantOrderConfigDTO> listMerchantOrderConfig;
    private List<BsLogisticsDTO> listLogistics;
    private BsAddressDTO bsAddress;
    private String merchantName;
    private String startDate;
    private String endDate;
	/**
	 * 界面展示的转换状态
	 */
	/**
	 * 采购订单状态
	 */
	@ApiModelProperty(value = "采购订单状态")
	private String conversionStatus;

	private Integer reminderTotal;


	public String getConversionStatus() {
		return conversionStatus;
	}

	public void setConversionStatus(String conversionStatus) {
		this.conversionStatus = conversionStatus;
	}

	public BsAddressDTO getBsAddress() {
		return bsAddress;
	}

	public void setBsAddress(BsAddressDTO bsAddress) {
		this.bsAddress = bsAddress;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGhMerchantOrderCode() {
		return ghMerchantOrderCode;
	}
	public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
		this.ghMerchantOrderCode = ghMerchantOrderCode;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getProductConfigCode() {
		return productConfigCode;
	}
	public void setProductConfigCode(String productConfigCode) {
		this.productConfigCode = productConfigCode;
	}
	public Integer getParentBrandId() {
		return parentBrandId;
	}
	public void setParentBrandId(Integer parentBrandId) {
		this.parentBrandId = parentBrandId;
	}
	public Integer getSubBrandId() {
		return subBrandId;
	}
	public void setSubBrandId(Integer subBrandId) {
		this.subBrandId = subBrandId;
	}
	public Integer getAudiId() {
		return audiId;
	}
	public void setAudiId(Integer audiId) {
		this.audiId = audiId;
	}
	public String getMotorcycle() {
		return motorcycle;
	}
	public void setMotorcycle(String motorcycle) {
		this.motorcycle = motorcycle;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getSpaPurchaseCode() {
		return spaPurchaseCode;
	}
	public void setSpaPurchaseCode(String spaPurchaseCode) {
		this.spaPurchaseCode = spaPurchaseCode;
	}
	public String getSpaProductCode() {
		return spaProductCode;
	}
	public void setSpaProductCode(String spaProductCode) {
		this.spaProductCode = spaProductCode;
	}
	public String getSpaProductName() {
		return spaProductName;
	}
	public void setSpaProductName(String spaProductName) {
		this.spaProductName = spaProductName;
	}
	public String getGlsxProductCode() {
		return glsxProductCode;
	}
	public void setGlsxProductCode(String glsxProductCode) {
		this.glsxProductCode = glsxProductCode;
	}
	public String getGlsxProductName() {
		return glsxProductName;
	}
	public void setGlsxProductName(String glsxProductName) {
		this.glsxProductName = glsxProductName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParentBrandName() {
		return parentBrandName;
	}
	public void setParentBrandName(String parentBrandName) {
		this.parentBrandName = parentBrandName;
	}
	public String getSubBrandName() {
		return subBrandName;
	}
	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
	}
	public String getAudiName() {
		return audiName;
	}
	public void setAudiName(String audiName) {
		this.audiName = audiName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<BsLogisticsDTO> getListLogistics() {
		return listLogistics;
	}
	public void setListLogistics(List<BsLogisticsDTO> listLogistics) {
		this.listLogistics = listLogistics;
	}
	public List<GhMerchantOrderConfigDTO> getListMerchantOrderConfig() {
		return listMerchantOrderConfig;
	}
	public void setListMerchantOrderConfig(
			List<GhMerchantOrderConfigDTO> listMerchantOrderConfig) {
		this.listMerchantOrderConfig = listMerchantOrderConfig;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getReminderTotal() {
		return reminderTotal;
	}

	public void setReminderTotal(Integer reminderTotal) {
		this.reminderTotal = reminderTotal;
	}

	@Override
	public String toString() {
		return "GhMerchantOrderDTO{" +
				"id=" + id +
				", ghMerchantOrderCode='" + ghMerchantOrderCode + '\'' +
				", orderTime=" + orderTime +
				", merchantCode='" + merchantCode + '\'' +
				", productConfigCode='" + productConfigCode + '\'' +
				", parentBrandId=" + parentBrandId +
				", parentBrandName='" + parentBrandName + '\'' +
				", subBrandId=" + subBrandId +
				", subBrandName='" + subBrandName + '\'' +
				", audiId=" + audiId +
				", audiName='" + audiName + '\'' +
				", motorcycle='" + motorcycle + '\'' +
				", categoryId=" + categoryId +
				", categoryName='" + categoryName + '\'' +
				", spaPurchaseCode='" + spaPurchaseCode + '\'' +
				", spaProductCode='" + spaProductCode + '\'' +
				", spaProductName='" + spaProductName + '\'' +
				", glsxProductCode='" + glsxProductCode + '\'' +
				", glsxProductName='" + glsxProductName + '\'' +
				", total=" + total +
				", remark='" + remark + '\'' +
				", status='" + status + '\'' +
				", listMerchantOrderConfig=" + listMerchantOrderConfig +
				", listLogistics=" + listLogistics +
				", bsAddress=" + bsAddress +
				", merchantName='" + merchantName + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", conversionStatus='" + conversionStatus + '\'' +
				", reminderTotal=" + reminderTotal +
				'}';
	}
}
