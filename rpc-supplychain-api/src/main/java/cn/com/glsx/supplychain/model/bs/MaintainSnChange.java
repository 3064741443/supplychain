package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "bs_maintain_sn_change")
public class MaintainSnChange implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 维修管理详情ID
     */
    private Long mainTainProductDetailId;
    /**
     * 切换的ICCID
     */
    private String iccid;
    /**
     * 切换的IMEI
     */
    private String imei;
    /**
     * 切换的设备SN
     */
    private String sn;
    /**
     * 维修费用类型
     */
    private String repairCostType;
    /**
     * 维修金额
     */
    private String price;
    /**
     * SN切换标识(1:是,2:否)
     */
    private Byte snChangeFlag;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    /**
     * 维修管理详情List
     */
    @Transient
    private List<MaintainProductDetail> maintainProductDetailList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getMainTainProductDetailId() {
        return mainTainProductDetailId;
    }

    public void setMainTainProductDetailId(Long mainTainProductDetailId) {
        this.mainTainProductDetailId = mainTainProductDetailId;
    }

    public String getRepairCostType() {
        return repairCostType;
    }

    public void setRepairCostType(String repairCostType) {
        this.repairCostType = repairCostType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Byte getSnChangeFlag() {
        return snChangeFlag;
    }

    public void setSnChangeFlag(Byte snChangeFlag) {
        this.snChangeFlag = snChangeFlag;
    }

    public List<MaintainProductDetail> getMaintainProductDetailList() {
        return maintainProductDetailList;
    }

    public void setMaintainProductDetailList(List<MaintainProductDetail> maintainProductDetailList) {
        this.maintainProductDetailList = maintainProductDetailList;
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
}