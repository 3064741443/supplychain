package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name = "attrib_mana")
public class JXCMTAttribMana implements Serializable{
    private String attribCode;
    @Id
    private Integer id;

    private Integer type;

    private Integer model;

    private Integer configure;

    private Integer msize;

    private String boardVersion;

    private String mcuVersion;

    private String fastenerVersion;

    private String softVersion;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    private Integer devTypeId;

    private Integer devMnumId;

    private Integer orNetId;

    private Integer cardSelfId;

    private Integer sourceId;

    private Integer screenId;

    private Integer orOpenId;

    private String verifyIccid;
    
    private String materialName;

    public String getAttribCode() {
        return attribCode;
    }

    public void setAttribCode(String attribCode) {
        this.attribCode = attribCode == null ? null : attribCode.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getMsize() {
        return msize;
    }

    public void setMsize(Integer msize) {
        this.msize = msize;
    }

    public String getBoardVersion() {
        return boardVersion;
    }

    public void setBoardVersion(String boardVersion) {
        this.boardVersion = boardVersion == null ? null : boardVersion.trim();
    }

    public String getMcuVersion() {
        return mcuVersion;
    }

    public void setMcuVersion(String mcuVersion) {
        this.mcuVersion = mcuVersion == null ? null : mcuVersion.trim();
    }

    public String getFastenerVersion() {
        return fastenerVersion;
    }

    public void setFastenerVersion(String fastenerVersion) {
        this.fastenerVersion = fastenerVersion == null ? null : fastenerVersion.trim();
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion == null ? null : softVersion.trim();
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

    public Integer getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(Integer devTypeId) {
        this.devTypeId = devTypeId;
    }

    public Integer getDevMnumId() {
        return devMnumId;
    }

    public void setDevMnumId(Integer devMnumId) {
        this.devMnumId = devMnumId;
    }

    public Integer getOrNetId() {
        return orNetId;
    }

    public void setOrNetId(Integer orNetId) {
        this.orNetId = orNetId;
    }

    public Integer getCardSelfId() {
        return cardSelfId;
    }

    public void setCardSelfId(Integer cardSelfId) {
        this.cardSelfId = cardSelfId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public Integer getOrOpenId() {
        return orOpenId;
    }

    public void setOrOpenId(Integer orOpenId) {
        this.orOpenId = orOpenId;
    }

    public String getVerifyIccid() {
        return verifyIccid;
    }

    public void setVerifyIccid(String verifyIccid) {
        this.verifyIccid = verifyIccid == null ? null : verifyIccid.trim();
    }

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
    
    
}