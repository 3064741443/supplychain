package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Table(name = "stk_merchant_stock_sn_stat")
public class STKMerchantStockSnStat implements Serializable{
	@Id
    private Integer id;

    private Integer deviceType;

    private String toMerchantCode;

    private String toMerchantName;

    private Integer total;

    private Integer unActiveDays;

    private String activeOrNot;

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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getToMerchantCode() {
        return toMerchantCode;
    }

    public void setToMerchantCode(String toMerchantCode) {
        this.toMerchantCode = toMerchantCode == null ? null : toMerchantCode.trim();
    }

    public String getToMerchantName() {
        return toMerchantName;
    }

    public void setToMerchantName(String toMerchantName) {
        this.toMerchantName = toMerchantName == null ? null : toMerchantName.trim();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getUnActiveDays() {
        return unActiveDays;
    }

    public void setUnActiveDays(Integer unActiveDays) {
        this.unActiveDays = unActiveDays;
    }

    public String getActiveOrNot() {
        return activeOrNot;
    }

    public void setActiveOrNot(String activeOrNot) {
        this.activeOrNot = activeOrNot == null ? null : activeOrNot.trim();
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
}