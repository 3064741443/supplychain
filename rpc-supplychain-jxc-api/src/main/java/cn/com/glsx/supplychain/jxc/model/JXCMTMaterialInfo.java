package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "am_material_info")
public class JXCMTMaterialInfo implements Serializable{
	
    private Integer materialId;

    private String materialCode;

    private String materialName;

    private Integer term;

    private Double price;

    private Double taxRate;

    private Integer productId;

    private String productName;

    private String productTypeName;

    private Integer materialTypeId;

    private String materialTypeName;

    private Integer deviceTypeId;

    private String deviceType;

    private Integer firstLevelId;

    private String firstLevelName;

    private Integer secondLevelId;

    private String secondLevelName;

    private Integer lastOperatorId;

    private String lastOperatorName;

    private Date createTime;

    private Date updateTime;

    private String ndScan;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
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

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName == null ? null : productTypeName.trim();
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

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public Integer getFirstLevelId() {
        return firstLevelId;
    }

    public void setFirstLevelId(Integer firstLevelId) {
        this.firstLevelId = firstLevelId;
    }

    public String getFirstLevelName() {
        return firstLevelName;
    }

    public void setFirstLevelName(String firstLevelName) {
        this.firstLevelName = firstLevelName == null ? null : firstLevelName.trim();
    }

    public Integer getSecondLevelId() {
        return secondLevelId;
    }

    public void setSecondLevelId(Integer secondLevelId) {
        this.secondLevelId = secondLevelId;
    }

    public String getSecondLevelName() {
        return secondLevelName;
    }

    public void setSecondLevelName(String secondLevelName) {
        this.secondLevelName = secondLevelName == null ? null : secondLevelName.trim();
    }

    public Integer getLastOperatorId() {
        return lastOperatorId;
    }

    public void setLastOperatorId(Integer lastOperatorId) {
        this.lastOperatorId = lastOperatorId;
    }

    public String getLastOperatorName() {
        return lastOperatorName;
    }

    public void setLastOperatorName(String lastOperatorName) {
        this.lastOperatorName = lastOperatorName == null ? null : lastOperatorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNdScan() {
        return ndScan;
    }

    public void setNdScan(String ndScan) {
        this.ndScan = ndScan == null ? null : ndScan.trim();
    }
}