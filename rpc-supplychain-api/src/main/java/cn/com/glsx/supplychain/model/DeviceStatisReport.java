package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DeviceStatisReport implements Serializable {
    private Integer id;

    private String day;

    private Integer deviceTotal;

    private Integer deviceIn;

    private Integer deviceAl;

    private Integer deviceUa;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getDeviceTotal() {
        return deviceTotal;
    }

    public void setDeviceTotal(Integer deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public Integer getDeviceIn() {
        return deviceIn;
    }

    public void setDeviceIn(Integer deviceIn) {
        this.deviceIn = deviceIn;
    }

    public Integer getDeviceAl() {
        return deviceAl;
    }

    public void setDeviceAl(Integer deviceAl) {
        this.deviceAl = deviceAl;
    }

    public Integer getDeviceUa() {
        return deviceUa;
    }

    public void setDeviceUa(Integer deviceUa) {
        this.deviceUa = deviceUa;
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
        return "DeviceStatisReport{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", deviceTotal=" + deviceTotal +
                ", deviceIn=" + deviceIn +
                ", deviceAl=" + deviceAl +
                ", deviceUa=" + deviceUa +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}