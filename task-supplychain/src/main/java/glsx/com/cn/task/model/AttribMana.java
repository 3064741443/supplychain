package glsx.com.cn.task.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AttribMana implements Serializable {
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

}
