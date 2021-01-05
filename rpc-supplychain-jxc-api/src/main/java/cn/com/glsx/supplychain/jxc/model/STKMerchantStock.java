package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Table(name = "stk_merchant_stock")
public class STKMerchantStock implements Serializable{
	@Id
	private Integer id;

    private String merchantCode;

    private Integer deviceType;

    private String materialCode;

    private String stockMonth;

    private Integer openingInventory;

    private Integer endingInventory;

    private Integer monthSales;

    private Integer monthReceives;

    private Integer monthCallin;

    private Integer monthCallout;

    private Integer monthRets;

    private Integer monthLosses;

    private Integer monthActives;
    
    private Integer monthDemolish;

    private Integer openingUnatInv;

    private Integer endingUnatInv;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    @Transient
    private Byte merchantChannelId;
    @Transient
    private String merchantName;

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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getStockMonth() {
        return stockMonth;
    }

    public void setStockMonth(String stockMonth) {
        this.stockMonth = stockMonth == null ? null : stockMonth.trim();
    }

    public Integer getOpeningInventory() {
        return openingInventory;
    }

    public void setOpeningInventory(Integer openingInventory) {
        this.openingInventory = openingInventory;
    }

    public Integer getEndingInventory() {
        return endingInventory;
    }

    public void setEndingInventory(Integer endingInventory) {
        this.endingInventory = endingInventory;
    }

    public Integer getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    public Integer getMonthReceives() {
        return monthReceives;
    }

    public void setMonthReceives(Integer monthReceives) {
        this.monthReceives = monthReceives;
    }

    public Integer getMonthCallin() {
        return monthCallin;
    }

    public void setMonthCallin(Integer monthCallin) {
        this.monthCallin = monthCallin;
    }

    public Integer getMonthCallout() {
        return monthCallout;
    }

    public void setMonthCallout(Integer monthCallout) {
        this.monthCallout = monthCallout;
    }

    public Integer getMonthRets() {
        return monthRets;
    }

    public void setMonthRets(Integer monthRets) {
        this.monthRets = monthRets;
    }

    public Integer getMonthLosses() {
        return monthLosses;
    }

    public void setMonthLosses(Integer monthLosses) {
        this.monthLosses = monthLosses;
    }

    public Integer getMonthActives() {
        return monthActives;
    }

    public void setMonthActives(Integer monthActives) {
        this.monthActives = monthActives;
    }

    public Integer getOpeningUnatInv() {
        return openingUnatInv;
    }

    public void setOpeningUnatInv(Integer openingUnatInv) {
        this.openingUnatInv = openingUnatInv;
    }

    public Integer getEndingUnatInv() {
        return endingUnatInv;
    }

    public void setEndingUnatInv(Integer endingUnatInv) {
        this.endingUnatInv = endingUnatInv;
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

	public Byte getMerchantChannelId() {
		return merchantChannelId;
	}

	public void setMerchantChannelId(Byte merchantChannelId) {
		this.merchantChannelId = merchantChannelId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getMonthDemolish() {
		return monthDemolish;
	}

	public void setMonthDemolish(Integer monthDemolish) {
		this.monthDemolish = monthDemolish;
	} 
    
    
}