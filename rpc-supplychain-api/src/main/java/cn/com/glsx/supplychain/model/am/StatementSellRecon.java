package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.glsx.supplychain.model.SupplyRequest;

@Table(name = "am_statement_sell_recon")
public class StatementSellRecon implements Serializable{
	@Id
    private Integer id;

    private String reconCode;

    private Byte channel;
    
    private String saleGroupCode;

    private String saleGroupName;
    
    private String saleGroupNameCus;

    private String saleGroupAddr;

    private String saleGroupPhone;

    private String saleGroupContact;

    private String customerCode;

    private String customerName;

    private String customerAddr;

    private String customerPhone;

    private String customerContact;
    
    private String warehouseCode;

    private String warehouseName;

    private String merchantCode;

    private String merchantName;

    private Date reconTimeStart;

    private Date reconTimeEnd;

    private Byte status;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    @Transient
    private String channelName;
    @Transient
    private Double totalPrice;
    
  
	@Transient
    private List<StatementSellReconDetail> listReconDetail;
    
    @Transient
    private List<StatementSellReconSplit> listReconSplit;
    
    @Transient
    private List<StatementSellReconInstall> listReconInstall;
    
    
    
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<StatementSellReconDetail> getListReconDetail() {
		return listReconDetail;
	}

	public void setListReconDetail(List<StatementSellReconDetail> listReconDetail) {
		this.listReconDetail = listReconDetail;
	}

	public List<StatementSellReconSplit> getListReconSplit() {
		return listReconSplit;
	}

	public void setListReconSplit(List<StatementSellReconSplit> listReconSplit) {
		this.listReconSplit = listReconSplit;
	}
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReconCode() {
        return reconCode;
    }

    public void setReconCode(String reconCode) {
        this.reconCode = reconCode == null ? null : reconCode.trim();
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public String getSaleGroupCode() {
        return saleGroupCode;
    }

    public void setSaleGroupCode(String saleGroupCode) {
        this.saleGroupCode = saleGroupCode == null ? null : saleGroupCode.trim();
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName == null ? null : saleGroupName.trim();
    }

    public String getSaleGroupAddr() {
        return saleGroupAddr;
    }

    public void setSaleGroupAddr(String saleGroupAddr) {
        this.saleGroupAddr = saleGroupAddr == null ? null : saleGroupAddr.trim();
    }

    public String getSaleGroupPhone() {
        return saleGroupPhone;
    }

    public void setSaleGroupPhone(String saleGroupPhone) {
        this.saleGroupPhone = saleGroupPhone == null ? null : saleGroupPhone.trim();
    }

    public String getSaleGroupContact() {
        return saleGroupContact;
    }

    public void setSaleGroupContact(String saleGroupContact) {
        this.saleGroupContact = saleGroupContact == null ? null : saleGroupContact.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr == null ? null : customerAddr.trim();
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone == null ? null : customerPhone.trim();
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact == null ? null : customerContact.trim();
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

    public Date getReconTimeStart() {
        return reconTimeStart;
    }

    public void setReconTimeStart(Date reconTimeStart) {
        this.reconTimeStart = reconTimeStart;
    }

    public Date getReconTimeEnd() {
        return reconTimeEnd;
    }

    public void setReconTimeEnd(Date reconTimeEnd) {
        this.reconTimeEnd = reconTimeEnd;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
    
    
	public List<StatementSellReconInstall> getListReconInstall() {
		return listReconInstall;
	}

	public void setListReconInstall(List<StatementSellReconInstall> listReconInstall) {
		this.listReconInstall = listReconInstall;
	}

	@Override
	public String toString() {
		return "StatementSellRecon [id=" + id + ", reconCode=" + reconCode
				+ ", channel=" + channel + ", saleGroupCode=" + saleGroupCode
				+ ", saleGroupName=" + saleGroupName + ", saleGroupAddr="
				+ saleGroupAddr + ", saleGroupPhone=" + saleGroupPhone
				+ ", saleGroupContact=" + saleGroupContact + ", customerCode="
				+ customerCode + ", customerName=" + customerName
				+ ", customerAddr=" + customerAddr + ", customerPhone="
				+ customerPhone + ", customerContact=" + customerContact
				+ ", merchantCode=" + merchantCode + ", merchantName="
				+ merchantName + ", reconTimeStart=" + reconTimeStart
				+ ", reconTimeEnd=" + reconTimeEnd + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", deletedFlag=" + deletedFlag + ", listReconDetail="
				+ listReconDetail + ", listReconSplit=" + listReconSplit
				+ ", listReconInstall=" + listReconInstall + "]";
	}

	public String getSaleGroupNameCus() {
		return saleGroupNameCus;
	}

	public void setSaleGroupNameCus(String saleGroupNameCus) {
		this.saleGroupNameCus = saleGroupNameCus;
	}

	

	
    
    
}