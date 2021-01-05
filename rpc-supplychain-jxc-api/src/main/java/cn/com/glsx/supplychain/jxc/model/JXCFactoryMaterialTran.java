package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "factory_material_tran")
public class JXCFactoryMaterialTran implements Serializable{
	@Id
    private Integer id;

    private String factMaterialCode;

    private String factMaterialName;

    private String materialCode;

    private String materialName;

    private Integer warehouseId;
    
    private Integer bsParentBrandId;

    private String bsParentBrandName;

    private Integer bsSubBrandId;

    private String bsSubBrandName;

    private Integer bsAudiId;

    private String bsAudiName;

    private String bsMotorcycle;

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

    public String getFactMaterialCode() {
        return factMaterialCode;
    }

    public void setFactMaterialCode(String factMaterialCode) {
        this.factMaterialCode = factMaterialCode == null ? null : factMaterialCode.trim();
    }

    public String getFactMaterialName() {
        return factMaterialName;
    }

    public void setFactMaterialName(String factMaterialName) {
        this.factMaterialName = factMaterialName == null ? null : factMaterialName.trim();
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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
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

	public Integer getBsParentBrandId() {
		return bsParentBrandId;
	}

	public void setBsParentBrandId(Integer bsParentBrandId) {
		this.bsParentBrandId = bsParentBrandId;
	}

	public String getBsParentBrandName() {
		return bsParentBrandName;
	}

	public void setBsParentBrandName(String bsParentBrandName) {
		this.bsParentBrandName = bsParentBrandName;
	}

	public Integer getBsSubBrandId() {
		return bsSubBrandId;
	}

	public void setBsSubBrandId(Integer bsSubBrandId) {
		this.bsSubBrandId = bsSubBrandId;
	}

	public String getBsSubBrandName() {
		return bsSubBrandName;
	}

	public void setBsSubBrandName(String bsSubBrandName) {
		this.bsSubBrandName = bsSubBrandName;
	}

	public Integer getBsAudiId() {
		return bsAudiId;
	}

	public void setBsAudiId(Integer bsAudiId) {
		this.bsAudiId = bsAudiId;
	}

	public String getBsAudiName() {
		return bsAudiName;
	}

	public void setBsAudiName(String bsAudiName) {
		this.bsAudiName = bsAudiName;
	}

	public String getBsMotorcycle() {
		return bsMotorcycle;
	}

	public void setBsMotorcycle(String bsMotorcycle) {
		this.bsMotorcycle = bsMotorcycle;
	}
    
    
}