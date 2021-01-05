package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "am_statement_finance_exrule")
public class StatementFinanceExrule implements Serializable{
    private Integer id;

    private Integer warehouseId;

    private String snHeard;

    private String mnumName;

    private String source;

    private String materialCode;

    private String materialName;

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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSnHeard() {
        return snHeard;
    }

    public void setSnHeard(String snHeard) {
        this.snHeard = snHeard == null ? null : snHeard.trim();
    }

    public String getMnumName() {
        return mnumName;
    }

    public void setMnumName(String mnumName) {
        this.mnumName = mnumName == null ? null : mnumName.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
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
		return "statementFinanceExrule [id=" + id + ", warehouseId="
				+ warehouseId + ", snHeard=" + snHeard + ", mnumName="
				+ mnumName + ", source=" + source + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", deletedFlag=" + deletedFlag + "]";
	}
    
    
}