package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "bs_maintain_product_detail")
public class MaintainProductDetail implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 售后订单
     */
    private String afterSaleOrderNumber;
    /**
     * ICCID
     */
    private String iccid;
    /**
     * IMEI
     */
    private String imei;
    /**
     * 设备SN
     */
    private String sn;
    /**
     * sn发货标识
     */
    private String dispatchFlag;
    /**
     * 最后激活时间
     */
    private String packageUserTime;
    /**
     * 工厂ID
     */
    private Integer warehouseId;
    /**
     *维修内容(0:更换屏幕,1:更换主板,2:更换TP,3:其他)
     */
    private String mainTainDetails;
    /**
     * 状态(1:待维修，2:已维修,3:待退货,4:已退货)
     */
    private Byte status;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     *  工厂名称
     */
    @Transient
    private String warehouseName;

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

    /**
     * 售后类型(1：退货，2：返修)
     */
    @Transient
    private Byte type;

    /**
     * 维修管理SN切换记录对象
     */
    @Transient
    private MaintainSnChange maintainSnChange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAfterSaleOrderNumber() {
        return afterSaleOrderNumber;
    }

    public void setAfterSaleOrderNumber(String afterSaleOrderNumber) {
        this.afterSaleOrderNumber = afterSaleOrderNumber;
    }

    public String getDispatchFlag() {
        return dispatchFlag;
    }

    public void setDispatchFlag(String dispatchFlag) {
        this.dispatchFlag = dispatchFlag;
    }

    public MaintainSnChange getMaintainSnChange() {
        return maintainSnChange;
    }

    public void setMaintainSnChange(MaintainSnChange maintainSnChange) {
        this.maintainSnChange = maintainSnChange;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPackageUserTime() {
        return packageUserTime;
    }

    public void setPackageUserTime(String packageUserTime) {
        this.packageUserTime = packageUserTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getMainTainDetails() {
        return mainTainDetails;
    }

    public void setMainTainDetails(String mainTainDetails) {
        this.mainTainDetails = mainTainDetails;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MaintainProductDetail{" +
                "id=" + id +
                ", afterSaleOrderNumber='" + afterSaleOrderNumber + '\'' +
                ", iccid='" + iccid + '\'' +
                ", imei='" + imei + '\'' +
                ", sn='" + sn + '\'' +
                ", dispatchFlag='" + dispatchFlag + '\'' +
                ", packageUserTime='" + packageUserTime + '\'' +
                ", warehouseId=" + warehouseId +
                ", mainTainDetails='" + mainTainDetails + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", type=" + type +
                ", maintainSnChange=" + maintainSnChange +
                '}';
    }
}