package cn.com.glsx.supplychain.model;

public class DeviceUpdateRecordPresnapshot {
    private Integer id;

    private String sn;

    private Integer preFlagId;

    private String flagType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
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
        this.flagType = flagType == null ? null : flagType.trim();
    }

	@Override
	public String toString() {
		return "DeviceUpdateRecordPresnapshot ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (sn != null ? "sn=" + sn + ", " : "")
				+ (preFlagId != null ? "preFlagId=" + preFlagId + ", " : "")
				+ (flagType != null ? "flagType=" + flagType : "") + "]";
	}
    
    
}