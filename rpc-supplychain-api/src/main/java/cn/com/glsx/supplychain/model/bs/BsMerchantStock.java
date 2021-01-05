package cn.com.glsx.supplychain.model.bs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

/**
* 商户库存表
*/
@SuppressWarnings("serial")
public class BsMerchantStock implements Serializable{
	/**
    * ID
    */
    private Integer id;

    /**
    * 商户编号
    */
    private String merchantCode;

    /**
    * 商户名称
    */
    private String merchantName;

    /**
     * 物料编号
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料类型id
     */
    private Integer materialTypeId;

    /**
     * 物料类型名称
     */
    private String materialTypeName;

    /**
     * 物料关联的设备类型id
     */
    private Integer materialDeviceTypeId;

    /**
     * 物料关联的设备类型名称
     */
    private String materialDeviceTypeName;

    /**
     * 出货数量
     */
    private Integer statSellNum;

    /**
     * 退货数量
     */
    private Integer statRetnNum;

    /**
     * 结算数量
     */
    private Integer statSettNum;

    /**
     * 调入数量
     */
    private Integer statCainNum;

    /**
     * 调出数量
     */
    private Integer statCaouNum;

    /**
     * 库存数量
     */
    private Integer statStckNum;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改人
     */
    private String updatedBy;

    /**
     * 修改时间
     */
    private Date updatedDate;

    /**
     * 删除标记
     */	
    private String deletedFlag;
    
    /**
     * 搜索条件最小库存
     */	
    @Transient
    private Integer maxStockNum;
    
    /**
     * 搜索条件最大库存
     */	
    @Transient
    private Integer minStockNum;

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

	public Integer getMaxStockNum() {
		return maxStockNum;
	}

	public void setMaxStockNum(Integer maxStockNum) {
		this.maxStockNum = maxStockNum;
	}

	public Integer getMinStockNum() {
		return minStockNum;
	}

	public void setMinStockNum(Integer minStockNum) {
		this.minStockNum = minStockNum;
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
				+ updatedDate + ", deletedFlag=" + deletedFlag
				+ ", maxStockNum=" + maxStockNum + ", minStockNum="
				+ minStockNum + "]";
	}
    
    
}