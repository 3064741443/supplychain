package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "am_product_split_source_flag")
public class ProductSplitSourceFlag implements Serializable{
	@Id
    private Integer id;

    private String productCode;

    private String hardwareContainSource;

    private Integer sourceFlag;

    private Double sourceProportion;

    private Double notSourceProportion;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getHardwareContainSource() {
        return hardwareContainSource;
    }

    public void setHardwareContainSource(String hardwareContainSource) {
        this.hardwareContainSource = hardwareContainSource == null ? null : hardwareContainSource.trim();
    }

    public Integer getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(Integer sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public Double getSourceProportion() {
        return sourceProportion;
    }

    public void setSourceProportion(Double sourceProportion) {
        this.sourceProportion = sourceProportion;
    }

    public Double getNotSourceProportion() {
        return notSourceProportion;
    }

    public void setNotSourceProportion(Double notSourceProportion) {
        this.notSourceProportion = notSourceProportion;
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
}