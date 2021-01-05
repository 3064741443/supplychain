package cn.com.glsx.supplychain.model.bs;

import java.io.Serializable;
import java.util.Date;

/**
* 商户库存结算明细表
*/
@SuppressWarnings("serial")
public class BsMerchantStockSett implements Serializable{
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
	* 结算标识
	*/
    private String setterNo;

    /**
	* 1:经销  2:金融风控代销 3:广汇集采代销
	*/
    private Integer setterType;

    /**
	* 结算数量
	*/
    private Integer settNum;

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

    public String getSetterNo() {
        return setterNo;
    }

    public void setSetterNo(String setterNo) {
        this.setterNo = setterNo == null ? null : setterNo.trim();
    }

    public Integer getSetterType() {
        return setterType;
    }

    public void setSetterType(Integer setterType) {
        this.setterType = setterType;
    }

    public Integer getSettNum() {
        return settNum;
    }

    public void setSettNum(Integer settNum) {
        this.settNum = settNum;
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
		return "BsMerchantStockSett [id=" + id + ", merchantCode="
				+ merchantCode + ", merchantName=" + merchantName
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", setterNo=" + setterNo + ", setterType="
				+ setterType + ", settNum=" + settNum + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
    
}