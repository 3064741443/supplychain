package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

/**
 * 
 * @Title: FirmwareInfo
 * @Description: 固件版本实体
 * @author Leiyj  
 * @date 2018年1月15日 下午4:29:36
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class FirmwareInfo extends BaseInfo implements Serializable {
    private Integer id;

    private Integer model;

    private Integer configure;
    
    private Integer type;

    private String vendorCode;

    private String svnAddress;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String deletedFlag;

    private String updateContent;
    
	private String mcuVersion;
	
	private String fastenerVersion;
    
	private String softVersion;
    
    private String boardVersion;

    @Transient
    private AttribMana attribMana;
    

	public String getMcuVersion() {
		return mcuVersion;
	}

	public void setMcuVersion(String mcuVersion) {
		this.mcuVersion = mcuVersion;
	}

	public String getFastenerVersion() {
		return fastenerVersion;
	}

	public void setFastenerVersion(String fastenerVersion) {
		this.fastenerVersion = fastenerVersion;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getBoardVersion() {
		return boardVersion;
	}

	public void setBoardVersion(String boardVersion) {
		this.boardVersion = boardVersion;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getConfigure() {
        return configure;
    }

    public void setConfigure(Integer configure) {
        this.configure = configure;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode == null ? "" : vendorCode.trim();
    }

    public String getSvnAddress() {
        return svnAddress;
    }

    public void setSvnAddress(String svnAddress) {
        this.svnAddress = svnAddress == null ? "" : svnAddress.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate == null ? new Date() : createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? "" : createdBy.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate == null ? new Date() : updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? "" : updatedBy.trim();
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag == null ? "" : deletedFlag.trim();
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent == null ? "" : updateContent.trim();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public AttribMana getAttribMana() {
		return attribMana;
	}

	public void setAttribMana(AttribMana attribMana) {
		this.attribMana = attribMana;
	}

	@Override
	public String toString() {
		return "FirmwareInfo ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (model != null ? "model=" + model + ", " : "")
				+ (configure != null ? "configure=" + configure + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (vendorCode != null ? "vendorCode=" + vendorCode + ", " : "")
				+ (svnAddress != null ? "svnAddress=" + svnAddress + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", "
						: "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (updatedDate != null ? "updatedDate=" + updatedDate + ", "
						: "")
				+ (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "")
				+ (deletedFlag != null ? "deletedFlag=" + deletedFlag + ", "
						: "")
				+ (updateContent != null ? "updateContent=" + updateContent
						+ ", " : "")
				+ (mcuVersion != null ? "mcuVersion=" + mcuVersion + ", " : "")
				+ (fastenerVersion != null ? "fastenerVersion="
						+ fastenerVersion + ", " : "")
				+ (softVersion != null ? "softVersion=" + softVersion + ", "
						: "")
				+ (boardVersion != null ? "boardVersion=" + boardVersion + ", "
						: "")
				+ (attribMana != null ? "attribMana=" + attribMana : "") + "]";
	}

	
	
}