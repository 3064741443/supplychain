package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class AttribMana extends BaseInfo implements Serializable {
	private Integer id;
	
	private String attribCode;
	
	private Integer type;
	
	private Integer model;
	
	private Integer configure;
	
	private Integer msize;
	
	private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;

	private String deletedFlag;
	
	private String mcuVersion;
	
	private String fastenerVersion;
    
	private String softVersion;
    
    private String boardVersion;
    
    private Integer	devTypeId;
    
    private Integer devMnumId;
    
    private Integer orNetId;
    
    private Integer cardSelfId;
    
    private Integer sourceId;
    
    private Integer screenId;
    
    private Integer orOpenId;
    
    private String verifyIccid;
    
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

	public Integer getMsize() {
		return msize;
	}

	public void setMsize(Integer msize) {
		this.msize = msize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public Integer getConfigure() {
		return configure;
	}

	public void setConfigure(Integer configure) {
		this.configure = configure;
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

	@Override
	public String toString() {
		return "AttribMana [id=" + id + ", attribCode=" + attribCode
				+ ", type=" + type + ", model=" + model + ", configure="
				+ configure + ", msize=" + msize + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", updatedDate="
				+ updatedDate + ", updatedBy=" + updatedBy + ", deletedFlag="
				+ deletedFlag + ", mcuVersion=" + mcuVersion
				+ ", fastenerVersion=" + fastenerVersion + ", softVersion="
				+ softVersion + ", boardVersion=" + boardVersion
				+ ", devTypeId=" + devTypeId + ", devMnumId=" + devMnumId
				+ ", orNetId=" + orNetId + ", cardSelfId=" + cardSelfId
				+ ", sourceId=" + sourceId + ", screenId=" + screenId
				+ ", orOpenId=" + orOpenId + "]";
	}

	public String getVerifyIccid() {
		return verifyIccid;
	}

	public void setVerifyIccid(String verifyIccid) {
		this.verifyIccid = verifyIccid;
	}
	
	

	
}
