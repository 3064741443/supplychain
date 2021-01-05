package glsx.com.cn.task.model;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "bs_dealer_user_info")
public class DealerUserInfo implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户类型(0:管理者)
     */
    private Byte type;
    /**
     * 商户渠道(0:广汇,1:非广汇)
     */
    private Byte channel;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户CODE
     */
    private String merchantCode;
    /**
     * 密码
     */
    private String password;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "DealerUserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", password='" + password + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}