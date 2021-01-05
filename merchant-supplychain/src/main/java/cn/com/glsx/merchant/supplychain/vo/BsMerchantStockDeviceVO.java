package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 代理商门店设备库存明细
 */
@SuppressWarnings("serial")
public class BsMerchantStockDeviceVO implements Serializable {
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

    /**
     * 在库库存
     */
    private Integer inSum;
    /**
     * 调出库存
     */
    private Integer odSum;
    /**
     * 发货库存
     */
    private Integer osSum;

    /**
     * 设备总数
     */
    private Integer sum;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 入库开始时间
     */
    private String inTimeStart;

    /**
     * 入库结束时间
     */
    private String inTimeEnd;

    /**
     * 出库开始时间
     */
    private String outTimeStart;

    /**
     * 出库结束时间
     */
    private String outTimeEnd;

    /**
     * 入库物流公司
     */
    private String inLogisticsCompany;
    /**
     * 入库物流单号
     */
    private String inLogisticsOrderNumber;
    /**
     * 出库物流公司
     */
    private String outLogisticsCompany;

    /**
     * 出库物流单号
     */
    private String outLogisticsOrderNumber;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getInSum() {
        return inSum;
    }

    public void setInSum(Integer inSum) {
        this.inSum = inSum;
    }

    public Integer getOdSum() {
        return odSum;
    }

    public void setOdSum(Integer odSum) {
        this.odSum = odSum;
    }

    public Integer getOsSum() {
        return osSum;
    }

    public void setOsSum(Integer osSum) {
        this.osSum = osSum;
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

    public String getInTimeStart() {
        return inTimeStart;
    }

    public void setInTimeStart(String inTimeStart) {
        this.inTimeStart = inTimeStart;
    }

    public String getInTimeEnd() {
        return inTimeEnd;
    }

    public void setInTimeEnd(String inTimeEnd) {
        this.inTimeEnd = inTimeEnd;
    }

    public String getOutTimeStart() {
        return outTimeStart;
    }

    public void setOutTimeStart(String outTimeStart) {
        this.outTimeStart = outTimeStart;
    }

    public String getOutTimeEnd() {
        return outTimeEnd;
    }

    public void setOutTimeEnd(String outTimeEnd) {
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
        return "BsMerchantStockDeviceVO{" +
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
                ", inSum=" + inSum +
                ", odSum=" + odSum +
                ", osSum=" + osSum +
                ", sum=" + sum +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", inTimeStart='" + inTimeStart + '\'' +
                ", inTimeEnd='" + inTimeEnd + '\'' +
                ", outTimeStart='" + outTimeStart + '\'' +
                ", outTimeEnd='" + outTimeEnd + '\'' +
                ", inLogisticsCompany='" + inLogisticsCompany + '\'' +
                ", inLogisticsOrderNumber='" + inLogisticsOrderNumber + '\'' +
                ", outLogisticsCompany='" + outLogisticsCompany + '\'' +
                ", outLogisticsOrderNumber='" + outLogisticsOrderNumber + '\'' +
                '}';
    }
}