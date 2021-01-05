package cn.com.glsx.supplychain.model.bs;

import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName 销售管理与销售对账汇总关系表
 * @Author admin
 * @Param
 * @Date 2019/5/15 11:18
 * @Version
 **/
@Table(name = "bs_sales_relation")
public class SalesRelation implements Serializable {
    /**
     * ID
     */
	@Id
    private Long id;

    /**
     * 销售管理ID
     */
    private Long salesId;

    /**
     * 销售汇总对账ID
     */
     private Long summarizingId;

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

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public Long getSummarizingId() {
        return summarizingId;
    }

    public void setSummarizingId(Long summarizingId) {
        this.summarizingId = summarizingId;
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
        return "SalesRelation{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", summarizingId=" + summarizingId +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}
