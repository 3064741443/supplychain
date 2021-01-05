package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTAttribInfoDTO implements Serializable{

	@ApiModelProperty(name = "id", notes = "配置属性ID", dataType = "integer", required = false, example = "")
	private Integer id;	
	@ApiModelProperty(name = "type", notes = "配置大类型", dataType = "integer", required = false, example = "")
	private Integer type;
	@ApiModelProperty(name = "name", notes = "设备型号", dataType = "string", required = false, example = "")
	private String name;
	@ApiModelProperty(name = "value", notes = "计数值", dataType = "integer", required = false, example = "")
    private Integer value;
	@ApiModelProperty(name = "comment", notes = "备注", dataType = "string", required = false, example = "")
    private String comment;
	@ApiModelProperty(name = "createdBy", notes = "创建人", dataType = "string", required = false, example = "")
    private String createdBy;
	@ApiModelProperty(name = "createdDate", notes = "创建时间", dataType = "date", required = false, example = "")
    private Date createdDate;
	@ApiModelProperty(name = "updatedBy", notes = "修改人", dataType = "string", required = false, example = "")
    private String updatedBy;
	@ApiModelProperty(name = "updatedDate", notes = "修改时间", dataType = "date", required = false, example = "")
    private Date updatedDate;
	@ApiModelProperty(name = "deletedFlag", notes = "删除标记", dataType = "string", required = false, example = "")
    private String deletedFlag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
		this.updatedBy = updatedBy;
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
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "JXCMTAttribInfoDTO [id=" + id + ", type=" + type + ", name="
				+ name + ", value=" + value + ", comment=" + comment
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", deletedFlag=" + deletedFlag + "]";
	}

	
	
}
