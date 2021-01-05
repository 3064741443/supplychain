package glsx.com.cn.task.model;

import java.util.Date;

public class BsMerchantStock {
    private Integer id;

    private String merchantCode;

    private String merchantName;

    private String materialCode;

    private String materialName;

    private Integer materialTypeId;

    private String materialTypeName;

    private Integer materialDeviceTypeId;

    private String materialDeviceTypeName;

    private Integer statSellNum;

    private Integer statRetnNum;

    private Integer statSettNum;

    private Integer statCainNum;

    private Integer statCaouNum;

    private Integer statStckNum;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

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
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getMaterialTypeName() {
        return materialTypeName;
    }

    public void setMaterialTypeName(String materialTypeName) {
        this.materialTypeName = materialTypeName == null ? null : materialTypeName.trim();
    }

    public Integer getMaterialDeviceTypeId() {
        return materialDeviceTypeId;
    }

    public void setMaterialDeviceTypeId(Integer materialDeviceTypeId) {
        this.materialDeviceTypeId = materialDeviceTypeId;
    }

    public String getMaterialDeviceTypeName() {
        return materialDeviceTypeName;
    }

    public void setMaterialDeviceTypeName(String materialDeviceTypeName) {
        this.materialDeviceTypeName = materialDeviceTypeName == null ? null : materialDeviceTypeName.trim();
    }

    public Integer getStatSellNum() {
        return statSellNum;
    }

    public void setStatSellNum(Integer statSellNum) {
        this.statSellNum = statSellNum;
    }

    public Integer getStatRetnNum() {
        return statRetnNum;
    }

    public void setStatRetnNum(Integer statRetnNum) {
        this.statRetnNum = statRetnNum;
    }

    public Integer getStatSettNum() {
        return statSettNum;
    }

    public void setStatSettNum(Integer statSettNum) {
        this.statSettNum = statSettNum;
    }

    public Integer getStatCainNum() {
        return statCainNum;
    }

    public void setStatCainNum(Integer statCainNum) {
        this.statCainNum = statCainNum;
    }

    public Integer getStatCaouNum() {
        return statCaouNum;
    }

    public void setStatCaouNum(Integer statCaouNum) {
        this.statCaouNum = statCaouNum;
    }

    public Integer getStatStckNum() {
        return statStckNum;
    }

    public void setStatStckNum(Integer statStckNum) {
        this.statStckNum = statStckNum;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
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
        this.deletedFlag = deletedFlag == null ? null : deletedFlag.trim();
    }

	@Override
	public String toString() {
		return "BsMerchantStock [id=" + id + ", merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", materialTypeId=" + materialTypeId + ", materialTypeName="
				+ materialTypeName + ", materialDeviceTypeId="
				+ materialDeviceTypeId + ", materialDeviceTypeName="
				+ materialDeviceTypeName + ", statSellNum=" + statSellNum
				+ ", statRetnNum=" + statRetnNum + ", statSettNum="
				+ statSettNum + ", statCainNum=" + statCainNum
				+ ", statCaouNum=" + statCaouNum + ", statStckNum="
				+ statStckNum + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", deletedFlag=" + deletedFlag + "]";
	}
    
    
    
}