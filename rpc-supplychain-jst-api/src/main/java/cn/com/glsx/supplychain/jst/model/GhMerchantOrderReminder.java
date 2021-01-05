package cn.com.glsx.supplychain.jst.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: GhMerchantOrderReminder
 * @Description: 催单记录
 * @author tianming
 * @date 2020年8月27日 上午11:28:36
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class GhMerchantOrderReminder implements Serializable {

	@Id
	private Integer id;

	private String spaPurchaseCode;

	private String ghMerchantOrderCode;

	private Integer reminderTotal;

	private Date createdDate;

	private String createdBy;

	private Date updatedDate;

	private String updatedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpaPurchaseCode() {
		return spaPurchaseCode;
	}

	public void setSpaPurchaseCode(String spaPurchaseCode) {
		this.spaPurchaseCode = spaPurchaseCode;
	}

	public String getGhMerchantOrderCode() {
		return ghMerchantOrderCode;
	}

	public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
		this.ghMerchantOrderCode = ghMerchantOrderCode;
	}

	public Integer getReminderTotal() {
		return reminderTotal;
	}

	public void setReminderTotal(Integer reminderTotal) {
		this.reminderTotal = reminderTotal;
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

	@Override
	public String toString() {
		return "GhMerchantOrderReminder{" +
				"id=" + id +
				", spaPurchaseCode='" + spaPurchaseCode + '\'' +
				", ghMerchantOrderCode='" + ghMerchantOrderCode + '\'' +
				", reminderTotal=" + reminderTotal +
				", createdDate=" + createdDate +
				", createdBy='" + createdBy + '\'' +
				", updatedDate=" + updatedDate +
				", updatedBy='" + updatedBy + '\'' +
				'}';
	}
}