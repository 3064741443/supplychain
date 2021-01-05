package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

@SuppressWarnings("serial")
@Table(name = "DeviceInfoGpsPreimport")
public class DeviceInfoGpsPreimport implements Serializable{
    private Integer id;

    private String orderCode;

    private String simCardNo;

    private String iccid;

    private String imsi;

    private String imei;

    private String sn;

    private String model;

    private String batch;

    private String vcode;

    private String logisticsNo;

    private String logisticsCpy;

    private String factoryName;

    private String result;

    private String resultDesc;

    private String seedTag;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private String dispatch;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    @ExcelResources(title = "发货单号",order=0)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    @ExcelResources(title = "SIM卡手机号码",order=1)
    public String getSimCardNo() {
        return simCardNo;
    }

    public void setSimCardNo(String simCardNo) {
        this.simCardNo = simCardNo == null ? null : simCardNo.trim();
    }

    @ExcelResources(title = "ICCID",order=2)
    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    @ExcelResources(title = "IMSI",order=3)
    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    @ExcelResources(title = "IMEI",order=4)
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    @ExcelResources(title = "SN",order=5)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    @ExcelResources(title = "设备型号",order=6)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    @ExcelResources(title = "批次号",order=8)
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    @ExcelResources(title = "验证码",order=7)
    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode == null ? null : vcode.trim();
    }

    @ExcelResources(title = "物流单号",order=10)
    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    @ExcelResources(title = "物流公司",order=9)
    public String getLogisticsCpy() {
        return logisticsCpy;
    }

    public void setLogisticsCpy(String logisticsCpy) {
        this.logisticsCpy = logisticsCpy == null ? null : logisticsCpy.trim();
    }

    @ExcelResources(title = "工厂",order=11)
    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
    }

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    @ExcelResources(title = "失败原因",order=12)
    public String getResultDesc() {
        return resultDesc;
    }


    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc == null ? null : resultDesc.trim();
    }

    public String getSeedTag() {
        return seedTag;
    }

    public void setSeedTag(String seedTag) {
        this.seedTag = seedTag == null ? null : seedTag.trim();
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
}