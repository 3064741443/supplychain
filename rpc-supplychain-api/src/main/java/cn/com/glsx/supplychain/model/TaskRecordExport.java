package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class TaskRecordExport implements Serializable{
    private Integer id;

    private Integer taskCfgId;

    private String checkCondition;

    private String url;

    private String statu;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    private String operatorFlag;
    
    @Transient
    private String taskCfgName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskCfgId() {
        return taskCfgId;
    }

    public void setTaskCfgId(Integer taskCfgId) {
        this.taskCfgId = taskCfgId;
    }

    public String getCondition() {
        return checkCondition;
    }

    public void setCondition(String checkCondition) {
        this.checkCondition = checkCondition == null ? null : checkCondition.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu == null ? null : statu.trim();
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

	public String getOperatorFlag() {
		return operatorFlag;
	}

	public void setOperatorFlag(String operatorFlag) {
		this.operatorFlag = operatorFlag;
	}
	
	

	public String getCheckCondition() {
		return checkCondition;
	}

	public void setCheckCondition(String checkCondition) {
		this.checkCondition = checkCondition;
	}

	public String getTaskCfgName() {
		return taskCfgName;
	}

	public void setTaskCfgName(String taskCfgName) {
		this.taskCfgName = taskCfgName;
	}

	@Override
	public String toString() {
		return "TaskRecordExport [id=" + id + ", taskCfgId=" + taskCfgId
				+ ", condition=" + checkCondition + ", url=" + url + ", statu="
				+ statu + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", updatedBy=" + updatedBy + ", updatedDate="
				+ updatedDate + ", deletedFlag=" + deletedFlag
				+ ", operatorFlag=" + operatorFlag + "]";
	}
    
    
}