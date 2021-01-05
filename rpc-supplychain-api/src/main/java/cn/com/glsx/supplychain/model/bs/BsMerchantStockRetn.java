package cn.com.glsx.supplychain.model.bs;

import java.io.Serializable;
import java.util.Date;

/**
* 商户库存退货明细表
*/
@SuppressWarnings("serial")
public class BsMerchantStockRetn implements Serializable{
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
	* 商户退货单号
	*/
    private String merchantAfterSaleNo;

    /**
	* 退货数量
	*/
    private Integer retnNum;

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

    public String getMerchantAfterSaleNo() {
        return merchantAfterSaleNo;
    }

    public void setMerchantAfterSaleNo(String merchantAfterSaleNo) {
        this.merchantAfterSaleNo = merchantAfterSaleNo == null ? null : merchantAfterSaleNo.trim();
    }

    public Integer getRetnNum() {
        return retnNum;
    }

    public void setRetnNum(Integer retnNum) {
        this.retnNum = retnNum;
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
		return "BsMerchantStockRetn [id=" + id + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", merchantAfterSaleNo=" + merchantAfterSaleNo
				+ ", retnNum=" + retnNum + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
    
}