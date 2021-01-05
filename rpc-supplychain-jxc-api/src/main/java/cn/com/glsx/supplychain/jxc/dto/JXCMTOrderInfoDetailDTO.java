package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTOrderInfoDetailDTO implements Serializable{

	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = false, example = "")
    private String dispatchOrderCode;
	@ApiModelProperty(name = "iccid", notes = "iccid", dataType = "string", required = false, example = "")
    private String iccid;
	@ApiModelProperty(name = "imei", notes = "imei", dataType = "string", required = false, example = "")
    private String imei;
	@ApiModelProperty(name = "attribCode", notes = "设备类型配置编码", dataType = "string", required = false, example = "")
    private String attribCode;
	@ApiModelProperty(name = "batch", notes = "批次号", dataType = "string", required = false, example = "")
    private String batch;
	@ApiModelProperty(name = "sn", notes = "sn", dataType = "string", required = false, example = "")
    private String sn;
	@ApiModelProperty(name = "updatedDate", notes = "修改时间（出库时间）", dataType = "string", required = false, example = "")
    private String updatedDate;
	@ApiModelProperty(name = "logisticsNum", notes = "物流单号", dataType = "string", required = false, example = "")
    private String logisticsNum;
	@ApiModelProperty(name = "logisticsCompany", notes = "物流公司", dataType = "string", required = false, example = "")
    private String logisticsCompany;
	@ApiModelProperty(name = "sendQulities", notes = "出库数量", dataType = "string", required = false, example = "")
	private Integer sendQulities;
	@ApiModelProperty(name = "pageNo", notes = "当前页", dataType = "int", required = false, example = "")
	private Integer pageNo;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = false, example = "")
	private Integer pageSize;
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getAttribCode() {
		return attribCode;
	}
	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getLogisticsNum() {
		return logisticsNum;
	}
	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}
	public String getLogisticsCompany() {
		return logisticsCompany;
	}
	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Integer getSendQulities() {
		return sendQulities;
	}
	public void setSendQulities(Integer sendQulities) {
		this.sendQulities = sendQulities;
	}
	@Override
	public String toString() {
		return "JXCMTOrderInfoDetailDTO [dispatchOrderCode="
				+ dispatchOrderCode + ", iccid=" + iccid + ", imei=" + imei
				+ ", attribCode=" + attribCode + ", batch=" + batch + ", sn="
				+ sn + ", updatedDate=" + updatedDate + ", logisticsNum="
				+ logisticsNum + ", logisticsCompany=" + logisticsCompany
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
	
	
}
