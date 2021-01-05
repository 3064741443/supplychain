package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

@Table(name = "bs_merchant_order_detail")
public class MerchantOrderDetail{
    /**
     * ID
     */
	@Id
    private Long id;
    /**
     * 商户订单号
     */
    private String merchantOrderNumber;
    /**
     * 产品CODE
     */
    private String productCode;
    /**
     * 订购数量
     */
    private Integer orderQuantity;
    /**
     * 审核数量
     */
    private Integer checkQuantity;
    /**
     * 签收数量
     */
    private Integer acceptQuantity;
    /**
     * 发货单
     */
    private String dispatchOrderNumber;
    /**
     * 产品备注
     */
    private String productRemarks;
    /**
     * 审核人
     */
    private String checkBy;
    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 项目ID
     */
    private Integer subjectId;

    /**
     * 是否投保(Y:投保，N:不投保)
     */
    private String insure;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 发货数量
     */
    @Transient
    private Integer shipmentsQuantity;

    /**
     * 产品当前金额
     */
    @Transient
    private Double productAmount;

    /**
     * 产品名称
     */
    @Transient
    private String productName;

    /**
     * 产品规格
     */
    @Transient
    private String productSpecification;

    /**
     * 设备类型
     */
    @Transient
    private String deviceType;

    /**
     * 物流信息
     */
    @Transient
    private Logistics logistics;

    /**
     * 物料编号
     */
    @Transient
    private String materialCode;
    /**
     * 物料名称
     */
    @Transient
    private String materialName;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Double productAmount) {
        this.productAmount = productAmount;
    }

    public Integer getShipmentsQuantity() {
        return shipmentsQuantity;
    }

    public void setShipmentsQuantity(Integer shipmentsQuantity) {
        this.shipmentsQuantity = shipmentsQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantOrderNumber() {
        return merchantOrderNumber;
    }

    public void setMerchantOrderNumber(String merchantOrderNumber) {
        this.merchantOrderNumber = merchantOrderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getAcceptQuantity() {
        return acceptQuantity;
    }

    public void setAcceptQuantity(Integer acceptQuantity) {
        this.acceptQuantity = acceptQuantity;
    }

    public String getDispatchOrderNumber() {
        return dispatchOrderNumber;
    }

    public void setDispatchOrderNumber(String dispatchOrderNumber) {
        this.dispatchOrderNumber = dispatchOrderNumber;
    }

    public String getProductRemarks() {
        return productRemarks;
    }

    public void setProductRemarks(String productRemarks) {
        this.productRemarks = productRemarks;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    @Override
    public String toString() {
        return "MerchantOrderDetail{" +
                "id=" + id +
                ", merchantOrderNumber='" + merchantOrderNumber + '\'' +
                ", productCode='" + productCode + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", checkQuantity=" + checkQuantity +
                ", acceptQuantity=" + acceptQuantity +
                ", dispatchOrderNumber='" + dispatchOrderNumber + '\'' +
                ", productRemarks='" + productRemarks + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", productAmount=" + productAmount +
                ", productName='" + productName + '\'' +
                ", productSpecification='" + productSpecification + '\'' +
                ", logistics=" + logistics +
                '}';
    }
}