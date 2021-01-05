package glsx.com.cn.task.model;
import java.io.Serializable;
import java.util.Date;

public class DeviceImeiStock implements Serializable{
    private Integer id;

    private String imei;

    private String externalFlag;

    private Integer devType;

    private String merchantNo;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getExternalFlag() {
        return externalFlag;
    }

    public void setExternalFlag(String externalFlag) {
        this.externalFlag = externalFlag == null ? null : externalFlag.trim();
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

    @Override
    public String toString() {
        return "DeviceImeiStock{" +
                "id=" + id +
                ", imei='" + imei + '\'' +
                ", externalFlag='" + externalFlag + '\'' +
                ", devType=" + devType +
                ", merchantNo='" + merchantNo + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                '}';
    }
}