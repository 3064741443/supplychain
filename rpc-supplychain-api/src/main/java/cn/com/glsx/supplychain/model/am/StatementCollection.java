package cn.com.glsx.supplychain.model.am;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName StatementCollection 对账单-广汇采集
 * @Author admin
 * @Param
 * @Date 2019/9/12 11:28
 * @Version
 **/
@Table(name = "am_statement_collection")
public class StatementCollection implements Serializable {
    /**
     * id
     */
    private Long id;
    
    private String workOrder;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 业务类型(1:驾宝无忧,5:车机与后视镜)
     */
    private Byte serviceType;
    /**
     * 时间
     */
    private Date time;
    /**
     * 区域
     */
    private String area;
    /**
     * 服务商/经销商
     */
    private String merchant;
    /**
     * 店名
     */
    private String shopName;
    /**
     *物料编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 分类
     */
    private String deviceType;
    /**
     * 总部含税单价
     */
    private Double price;
    /**
     * 总部总金额
     */
    private Double priceNum;
    /**
     * 返利点
     */
    private Double rebate;
    /**
     * 返利总额
     */
    private Double rebateNum;
    /**
     * 返利后总额
     */
    private Double afterRebateNum;
    /**
     * 返利后单价
     */
    private Double afterRebatePrice;
    /**
     * 销售数量
     */
    private Integer salesQuantity;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 销售组编码
     */
    private String saleGroupCode;
    /**
     * 销售组名称
     */
    private String saleGroupName;
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 发货仓库名称
     */
    private String warehouseName;
    /**
     *  状态(1:未拆分,2:拆分成功,3:拆分失败)
     */
    private Byte status;
    /**
     * 失败原因
     */
    private String reasons;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;


    /**
     * 开始时间
     */
    @Transient
    private Date startDate;

    /**
     * 结束时间
     */
    @Transient
    private Date endDate;

    //广汇集采详情List
    @Transient
    private List<StatementCollectionSplit> statementCollectionSplitList;
    
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceNum() {
        return priceNum;
    }

    public void setPriceNum(Double priceNum) {
        this.priceNum = priceNum;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getRebateNum() {
        return rebateNum;
    }

    public void setRebateNum(Double rebateNum) {
        this.rebateNum = rebateNum;
    }

    public Double getAfterRebateNum() {
        return afterRebateNum;
    }

    public void setAfterRebateNum(Double afterRebateNum) {
        this.afterRebateNum = afterRebateNum;
    }

    public Double getAfterRebatePrice() {
        return afterRebatePrice;
    }

    public void setAfterRebatePrice(Double afterRebatePrice) {
        this.afterRebatePrice = afterRebatePrice;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleGroupCode() {
        return saleGroupCode;
    }

    public void setSaleGroupCode(String saleGroupCode) {
        this.saleGroupCode = saleGroupCode;
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName;
    }

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<StatementCollectionSplit> getStatementCollectionSplitList() {
        return statementCollectionSplitList;
    }

    public void setStatementCollectionSplitList(List<StatementCollectionSplit> statementCollectionSplitList) {
        this.statementCollectionSplitList = statementCollectionSplitList;
    }
    

	@Override
    public String toString() {
        return "StatementCollection{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", serviceType=" + serviceType +
                ", time=" + time +
                ", area='" + area + '\'' +
                ", merchant='" + merchant + '\'' +
                ", shopName='" + shopName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", price=" + price +
                ", priceNum=" + priceNum +
                ", rebate=" + rebate +
                ", rebateNum=" + rebateNum +
                ", afterRebateNum=" + afterRebateNum +
                ", afterRebatePrice=" + afterRebatePrice +
                ", salesQuantity=" + salesQuantity +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", saleGroupCode='" + saleGroupCode + '\'' +
                ", saleGroupName='" + saleGroupName + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", status=" + status +
                ", reasons='" + reasons + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", statementCollectionSplitList=" + statementCollectionSplitList +
                '}';
    }
}
