package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

import cn.com.glsx.supplychain.model.PageInfo;

@SuppressWarnings("serial")
public class DeviceResetRecordExcelVo extends PageInfo implements Serializable{

    private String sn;

    private String createdBy;

    private String remark;
    
    private String createdTime;

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DeviceResetRecordExcelVo [sn=" + sn + ", createdBy="
				+ createdBy + ", remark=" + remark + ", createdTime="
				+ createdTime + ", getCreatedTime()=" + getCreatedTime()
				+ ", getSn()=" + getSn() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getRemark()=" + getRemark() + "]";
	}

}