package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;
@Table(name = "bs_logistics")
public class Logistics{
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 物流编号
     */
    private String code;
    /**
     * 物流单号
     */
    private String orderNumber;
    /**
     * 物流公司
     */
    private String company;
    /**
     * 物流类型（1：商户订单物流信息，2：售后订单物流信息，3：售后订单维修物流信息，4：扫码工具物流信息）
     */
    private Byte type;
    /**
     * 业务CODE
     */
    private String serviceCode;
    /**
     * 发货地址详情ID
     */
    private Long sendId;
    /**
     * 收货地址详情ID
     */
    private Long receiveId;

    /**
     * 是否签收
     */
    private String accept;

    /**
     * 发货数量
     */
    private Integer shipmentsQuantity;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String sendTime;

    /**
     * 发货地址详情
     */
    @Transient
    private Address sendAddress;

    /**
     * 收货地址详情
     */
    @Transient
    private Address receiveAddress;

    /**
     * 物流签收数量
     */
    @Transient
    private Integer logisticsAcceptQuantity;
    
    @Transient
    private String name;
    @Transient
    private String mobile;
    @Transient
    private String address;
    
    

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getLogisticsAcceptQuantity() {
        return logisticsAcceptQuantity;
    }

    public void setLogisticsAcceptQuantity(Integer logisticsAcceptQuantity) {
        this.logisticsAcceptQuantity = logisticsAcceptQuantity;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public Address getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(Address sendAddress) {
        this.sendAddress = sendAddress;
    }

    public Address getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(Address receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCompany() {
        return company;
    }


    public void setCompany(String company) {
        this.company = company;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getShipmentsQuantity() {
        return shipmentsQuantity;
    }

    public void setShipmentsQuantity(Integer shipmentsQuantity) {
        this.shipmentsQuantity = shipmentsQuantity;
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

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


}