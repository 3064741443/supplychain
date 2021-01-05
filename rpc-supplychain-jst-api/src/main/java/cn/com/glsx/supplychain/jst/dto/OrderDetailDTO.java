package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class OrderDetailDTO extends BaseDTO implements Serializable{

	private Integer id;

    private String shopOrderCode;

    private String sn;

    private String attribCode;

    private Integer logisticsId;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String productCode;

    private String productName;

    private String materialCode;

    private String materialName;

	/**
	 * 是否是系统虚拟的sn Y:是 N:否
	 */
	private String vtSn;

	public String getVtSn() {
		return vtSn;
	}

	public void setVtSn(String vtSn) {
		this.vtSn = vtSn;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopOrderCode() {
		return shopOrderCode;
	}

	public void setShopOrderCode(String shopOrderCode) {
		this.shopOrderCode = shopOrderCode;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "OrderDetailDTO{" +
				"id=" + id +
				", shopOrderCode='" + shopOrderCode + '\'' +
				", sn='" + sn + '\'' +
				", attribCode='" + attribCode + '\'' +
				", logisticsId=" + logisticsId +
				", createdBy='" + createdBy + '\'' +
				", createdDate=" + createdDate +
				", updatedBy='" + updatedBy + '\'' +
				", updatedDate=" + updatedDate +
				", deletedFlag='" + deletedFlag + '\'' +
				", productCode='" + productCode + '\'' +
				", productName='" + productName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", materialName='" + materialName + '\'' +
				", vtSn='" + vtSn + '\'' +
				'}';
	}


}
