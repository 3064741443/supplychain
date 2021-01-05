package cn.com.glsx.supplychain.model;

import cn.com.glsx.supplychain.enums.UpdateRecordEnum;

public class DeviceUpdateRecordSv {

	private String sn;

    private Integer id;

    private Integer flagId;

    private String flagName;
    
    private Integer preFlagId;
    
    private String preFlagName;

    private UpdateRecordEnum flagType;
    
    
    
	public Integer getPreFlagId() {
		return preFlagId;
	}

	public void setPreFlagId(Integer preFlagId) {
		this.preFlagId = preFlagId;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFlagId() {
		return flagId;
	}

	public void setFlagId(Integer flagId) {
		this.flagId = flagId;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public UpdateRecordEnum getFlagType() {
		return flagType;
	}

	public void setFlagType(UpdateRecordEnum flagType) {
		this.flagType = flagType;
	}

	@Override
	public String toString() {
		return "DeviceUpdateRecordSv [sn=" + sn + ", id=" + id + ", flagId="
				+ flagId + ", flagName=" + flagName + ", preFlagId="
				+ preFlagId + ", preFlagName=" + preFlagName + ", flagType="
				+ flagType + "]";
	}

    
}
