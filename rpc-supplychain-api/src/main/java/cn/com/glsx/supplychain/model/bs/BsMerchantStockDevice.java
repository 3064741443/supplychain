package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Transient;
import java.util.Date;

public class BsMerchantStockDevice {
    private Integer id;

    private String sn;

    /**
     * 属性配置编号 同物料编码
     */
    private String attribCode;

    private String iccid;
    /**
     * 商户编号
     */
    private String merchantCode;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 入库时间
     */
    private Date inTime;
    /**
     * 入库物流
     */
    private Integer inLogisticsId;
    /**
     * 出库库时间
     */
    private Date outTime;
    /**
     * 出库物流
     */
    private Integer outLogisticsId;
    /**
     * 出库商户
     */
    private String toMerchantCode;
    /**
     * 出库商户名称
     */
    private String toMerchantName;
    /**
     * 状态 IN:在库 OD:调出 OS:发货,RT:退货
     */
    private String statu;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 入库开始时间
     */
    @Transient
    private Date inTimeStart;

    /**
     * 入库结束时间
     */
    @Transient
    private Date inTimeEnd;

    /**
     * 出库开始时间
     */
    @Transient
    private Date outTimeStart;

    /**
     * 出库结束时间
     */
    @Transient
    private Date outTimeEnd;

    /**
     * 入库物流公司
     */
    @Transient
    private String inLogisticsCompany;
    /**
     * 入库物流单号
     */
    @Transient
    private String inLogisticsOrderNumber;
    /**
     * 出库物流公司
     */
    @Transient
    private String outLogisticsCompany;

    /**
     * 出库物流单号
     */
    @Transient
    private String outLogisticsOrderNumber;

    /**
     * 是否是系统虚拟的sn Y:是 N:否
     */
    private String vtSn;

    public String getVtSn() {
        return vtSn;
    }

    public void setVtSn(String vtSn) {
        this.vtSn = vtSn;
    }

    public String getInLogisticsCompany() {
        return inLogisticsCompany;
    }

    public void setInLogisticsCompany(String inLogisticsCompany) {
        this.inLogisticsCompany = inLogisticsCompany;
    }

    public String getInLogisticsOrderNumber() {
        return inLogisticsOrderNumber;
    }

    public void setInLogisticsOrderNumber(String inLogisticsOrderNumber) {
        this.inLogisticsOrderNumber = inLogisticsOrderNumber;
    }

    public String getOutLogisticsCompany() {
        return outLogisticsCompany;
    }

    public void setOutLogisticsCompany(String outLogisticsCompany) {
        this.outLogisticsCompany = outLogisticsCompany;
    }

    public String getOutLogisticsOrderNumber() {
        return outLogisticsOrderNumber;
    }

    public void setOutLogisticsOrderNumber(String outLogisticsOrderNumber) {
        this.outLogisticsOrderNumber = outLogisticsOrderNumber;
    }

    public Date getInTimeStart() {
        return inTimeStart;
    }

    public void setInTimeStart(Date inTimeStart) {
        this.inTimeStart = inTimeStart;
    }

    public Date getInTimeEnd() {
        return inTimeEnd;
    }

    public void setInTimeEnd(Date inTimeEnd) {
        this.inTimeEnd = inTimeEnd;
    }

    public Date getOutTimeStart() {
        return outTimeStart;
    }

    public void setOutTimeStart(Date outTimeStart) {
        this.outTimeStart = outTimeStart;
    }

    public Date getOutTimeEnd() {
        return outTimeEnd;
    }

    public void setOutTimeEnd(Date outTimeEnd) {
        this.outTimeEnd = outTimeEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getAttribCode() {
        return attribCode;
    }

    public void setAttribCode(String attribCode) {
        this.attribCode = attribCode == null ? null : attribCode.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
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

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Integer getInLogisticsId() {
        return inLogisticsId;
    }

    public void setInLogisticsId(Integer inLogisticsId) {
        this.inLogisticsId = inLogisticsId;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Integer getOutLogisticsId() {
        return outLogisticsId;
    }

    public void setOutLogisticsId(Integer outLogisticsId) {
        this.outLogisticsId = outLogisticsId;
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

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu == null ? null : statu.trim();
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
        return "BsMerchantStockDevice{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", attribCode='" + attribCode + '\'' +
                ", iccid='" + iccid + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", inTime=" + inTime +
                ", inLogisticsId=" + inLogisticsId +
                ", outTime=" + outTime +
                ", outLogisticsId=" + outLogisticsId +
                ", toMerchantCode='" + toMerchantCode + '\'' +
                ", toMerchantName='" + toMerchantName + '\'' +
                ", statu='" + statu + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", inTimeStart=" + inTimeStart +
                ", inTimeEnd=" + inTimeEnd +
                ", outTimeStart=" + outTimeStart +
                ", outTimeEnd=" + outTimeEnd +
                ", inLogisticsCompany='" + inLogisticsCompany + '\'' +
                ", inLogisticsOrderNumber='" + inLogisticsOrderNumber + '\'' +
                ", outLogisticsCompany='" + outLogisticsCompany + '\'' +
                ", outLogisticsOrderNumber='" + outLogisticsOrderNumber + '\'' +
                ", vtSn='" + vtSn + '\'' +
                '}';
    }
}