package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Transient;
import java.util.Date;
@SuppressWarnings("serial")
public class JstShopOrderDetail {
    private Integer id;

    /**
     * 门店订单号
     */
    private String shopOrderCode;
    /**
     * 设备SN号
     */
    private String sn;
    /**
     * 属性配置编号 同物料编码
     */
    private String attribCode;

    /**
     * 物流
     */
    private Integer logisticsId;

    /**
     * 物流单号
     */
    @Transient
    private String logisticsOrderNumber;

    /**
     * 物流公司
     */
    @Transient
    private String logisticsCompany;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;


    // 页面大小
    @Transient
 	private Integer pageSize;
 	// 页号
    @Transient
 	private Integer pageStart;
    //已发货数
    @Transient
    private Integer sendCount;

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

    public String getLogisticsOrderNumber() {
        return logisticsOrderNumber;
    }

    public void setLogisticsOrderNumber(String logisticsOrderNumber) {
        this.logisticsOrderNumber = logisticsOrderNumber;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopOrderCode() {
        return shopOrderCode;
    }

    public void setShopOrderCode(String shopOrderCode) {
        this.shopOrderCode = shopOrderCode == null ? null : shopOrderCode.trim();
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

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}

    @Override
    public String toString() {
        return "JstShopOrderDetail{" +
                "id=" + id +
                ", shopOrderCode='" + shopOrderCode + '\'' +
                ", sn='" + sn + '\'' +
                ", attribCode='" + attribCode + '\'' +
                ", logisticsId=" + logisticsId +
                ", logisticsOrderNumber='" + logisticsOrderNumber + '\'' +
                ", logisticsCompany='" + logisticsCompany + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                ", pageSize=" + pageSize +
                ", pageStart=" + pageStart +
                ", sendCount=" + sendCount +
                ", vtSn='" + vtSn + '\'' +
                '}';
    }
}