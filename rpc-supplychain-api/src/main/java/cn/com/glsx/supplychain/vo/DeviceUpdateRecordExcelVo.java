package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

import cn.com.glsx.supplychain.model.PageInfo;

@SuppressWarnings("serial")
public class DeviceUpdateRecordExcelVo extends PageInfo implements Serializable{
    private String sn;

    private Integer flagId;

    private Integer preFlagId;

    private String flagType;

    private String createdTime;
        
    private String flagName;
        
    private String preFlagName;
    
	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public String getPreFlagName() {
		return preFlagName;
	}

	public void setPreFlagName(String preFlagName) {
		this.preFlagName = preFlagName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getFlagId() {
		return flagId;
	}

	public void setFlagId(Integer flagId) {
		this.flagId = flagId;
	}

	public Integer getPreFlagId() {
		return preFlagId;
	}

	public void setPreFlagId(Integer preFlagId) {
		this.preFlagId = preFlagId;
	}

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	@Override
	public String toString() {
		return "DeviceUpdateRecordExcelVo [sn=" + sn + ", flagId=" + flagId
				+ ", preFlagId=" + preFlagId + ", flagType=" + flagType
				+ ", createdTime=" + createdTime + ", flagName=" + flagName
				+ ", preFlagName=" + preFlagName + ", getCreatedTime()="
				+ getCreatedTime() + ", getFlagName()=" + getFlagName()
				+ ", getPreFlagName()=" + getPreFlagName() + ", getSn()="
				+ getSn() + ", getFlagId()=" + getFlagId()
				+ ", getPreFlagId()=" + getPreFlagId() + ", getFlagType()="
				+ getFlagType() + "]";
	}
	
}