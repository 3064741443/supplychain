package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Transient;

import java.util.Date;
@SuppressWarnings("serial")
public class JstShopOrder {
    private Integer id;

    /**
     * 门店订单编号
     */
    private String shopOrderCode;
    /**
     * 门店编码
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 代理商商户code
     */
    private String agentMerchantCode;
    /**
     * 代理商商户名称
     */
    private String agentMerchantName;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 业务类型ID(1:驾宝无忧,2:金融风控,3:车机,4:后视镜,5:其它)
     */
    private Byte serviceType;
    /**
     * 物料编号 这里是硬件物料 原型中的小类编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料类型(0:硬件,1:网联软件,2:风险评估,3:风控服务,4:安装服务,5:智慧门店服务,6:AI车联网服务,7:配件)
     */
    private Byte materialType;
    
    /**
     * 订购总数
     */
    private Integer total;
    /**
     * 产品单价
     */
    private Double price;
    /**
     * 产品物料价格拆分下订单时的价格时间
     */
    private Date productSplitPriceTime;
    /**
     * 有效期
     */
    private String serviceTime;
    /**
     * 套餐
     */
    private String packageOne;
    /**
     * UF: 未完成 OV：完成 CL：已取消
     */
    private String status;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String scanType;
    
    /**
     * N:无订单发货  Y:有订单发货
     */
    @Transient
    private String noOrder;

    /**
     * 已发货数
     */
    @Transient
    private Integer sendCount;

    /**
     * 待发货数
     */
    @Transient
    private Integer waitCount;

    /**
     * 订购开始时间
     */
    @Transient
    private Date createdDateStart;

    /**
     * 订购结束时间
     */
    @Transient
    private Date createdDateEnd;

    // 地址信息
    @Transient
    private BsAddress bsAddress;
    // 物流信息
    @Transient
    private BsLogistics bsLogistics;
    // 起始页
    @Transient
    private Integer pageStart;
    // 页面大小
    @Transient
    private Integer pageSize;
    // 下单时间
    @Transient
    private String orderTime;

    /**
     * 地址
     */
    @Transient
    private String address;
    @Transient
    private Integer provinceId;
    @Transient
    private String provinceName;
    @Transient
    private Integer cityId;
    @Transient
    private String cityName;
    @Transient
    private Integer areaId;
    @Transient
    private String areaName;

    /**
     * 联系人
     */
    @Transient
    private String name;

    /**
     * 联系电话
     */
    @Transient
    private String mobile;

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(Integer waitCount) {
        this.waitCount = waitCount;
    }

    public BsAddress getBsAddress() {
        return bsAddress;
    }

    public void setBsAddress(BsAddress bsAddress) {
        this.bsAddress = bsAddress;
    }

    public BsLogistics getBsLogistics() {
        return bsLogistics;
    }

    public void setBsLogistics(BsLogistics bsLogistics) {
        this.bsLogistics = bsLogistics;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Date getCreatedDateStart() {
        return createdDateStart;
    }

    public void setCreatedDateStart(Date createdDateStart) {
        this.createdDateStart = createdDateStart;
    }

    public Date getCreatedDateEnd() {
        return createdDateEnd;
    }

    public void setCreatedDateEnd(Date createdDateEnd) {
        this.createdDateEnd = createdDateEnd;
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
        this.shopOrderCode = shopOrderCode == null ? null : shopOrderCode.trim();
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getAgentMerchantCode() {
        return agentMerchantCode;
    }

    public void setAgentMerchantCode(String agentMerchantCode) {
        this.agentMerchantCode = agentMerchantCode == null ? null : agentMerchantCode.trim();
    }

    public String getAgentMerchantName() {
        return agentMerchantName;
    }

    public void setAgentMerchantName(String agentMerchantName) {
        this.agentMerchantName = agentMerchantName == null ? null : agentMerchantName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getProductSplitPriceTime() {
        return productSplitPriceTime;
    }

    public void setProductSplitPriceTime(Date productSplitPriceTime) {
        this.productSplitPriceTime = productSplitPriceTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getPackageOne() {
        return packageOne;
    }

    public void setPackageOne(String packageOne) {
        this.packageOne = packageOne;
    }
    
    

    public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	@Override
    public String toString() {
        return "JstShopOrder{" +
                "id=" + id +
                ", shopOrderCode='" + shopOrderCode + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", shopName='" + shopName + '\'' +
                ", agentMerchantCode='" + agentMerchantCode + '\'' +
                ", agentMerchantName='" + agentMerchantName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", serviceType=" + serviceType +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", total=" + total +
                ", price=" + price +
                ", productSplitPriceTime=" + productSplitPriceTime +
                ", serviceTime='" + serviceTime + '\'' +
                ", packageOne='" + packageOne + '\'' +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", sendCount=" + sendCount +
                ", waitCount=" + waitCount +
                ", createdDateStart=" + createdDateStart +
                ", createdDateEnd=" + createdDateEnd +
                ", bsAddress=" + bsAddress +
                ", bsLogistics=" + bsLogistics +
                ", pageStart=" + pageStart +
                ", pageSize=" + pageSize +
                ", orderTime='" + orderTime + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

	public Byte getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Byte materialType) {
		this.materialType = materialType;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
}