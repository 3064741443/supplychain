package cn.com.glsx.supplychain.model.am;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName KcWarehouseRelation
 * @Author admin
 * @Param
 * @Date 2019/10/21 13:44
 * @Version
 **/
@Table(name = "am_kc_warehouse_relation")
public class KcWarehouseRelation implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 仓库编号
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 商户编号
     */
    private String merchantCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
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
        return "KcWarehouseRelation{" +
                "id=" + id +
                ", wareHouseCode='" + warehouseCode + '\'' +
                ", wareHouseName='" + warehouseName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}
