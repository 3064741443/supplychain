package cn.com.glsx.supplychain.model.jxc;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title: DeviceInfo
 * @Description: 设备管理导出实体类
 * @author luoqiang
 * @date 2020/9/9 15:21
 * @version V1.0
 *
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class JXCMTDeviceInfoImportCheckVo implements Serializable {

    private Integer id;

    private String iccid;

    private String imei;
    
    private String sn;

    private String attribCode;
    
    private String batch;

    private String status;

    private String orderCode;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private String deletedFlag;
    
    private Integer wareHouseId;
    
    private Integer wareHouseIdUp;
    
    private String remark;
    
    private String vcode;

    private String imsi;

    private String simCardNo;

    /**
     * 生产工厂
     */
	private String wareHouseName;

	/**
	 * 当前所在工厂/仓库
	 */
	private String wareHouseUpName;

	private  String resultDesc;

	@ExcelResources(title = "IMSI",order = 2)
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@ExcelResources(title = "网卡号",order = 1)
	public String getSimCardNo() {
		return simCardNo;
	}


	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	@ExcelResources(title = "失败原因",order=10)
	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ExcelResources(title = "ICCID",order = 3)
	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}


	@ExcelResources(title = "IMEI",order = 4)
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@ExcelResources(title = "SN",order = 5)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@ExcelResources(title = "物料编码",order = 0)
	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	@ExcelResources(title = "批次号",order = 7)
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public Integer getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public Integer getWareHouseIdUp() {
		return wareHouseIdUp;
	}

	public void setWareHouseIdUp(Integer wareHouseIdUp) {
		this.wareHouseIdUp = wareHouseIdUp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ExcelResources(title = "验证码",order = 6)
	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	@ExcelResources(title = "生产工厂",order = 8)
	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	@ExcelResources(title = "当前所在工厂/仓库",order = 9)
	public String getWareHouseUpName() {
		return wareHouseUpName;
	}

	public void setWareHouseUpName(String wareHouseUpName) {
		this.wareHouseUpName = wareHouseUpName;
	}

	@Override
	public String toString() {
		return "JXCMTDeviceInfoImport{" +
				"id=" + id +
				", iccid='" + iccid + '\'' +
				", imei='" + imei + '\'' +
				", sn='" + sn + '\'' +
				", attribCode='" + attribCode + '\'' +
				", batch='" + batch + '\'' +
				", status='" + status + '\'' +
				", orderCode='" + orderCode + '\'' +
				", createdDate=" + createdDate +
				", createdBy='" + createdBy + '\'' +
				", updatedDate=" + updatedDate +
				", updatedBy='" + updatedBy + '\'' +
				", deletedFlag='" + deletedFlag + '\'' +
				", wareHouseId=" + wareHouseId +
				", wareHouseIdUp=" + wareHouseIdUp +
				", remark='" + remark + '\'' +
				", vcode='" + vcode + '\'' +
				", imsi='" + imsi + '\'' +
				", simCardNo='" + simCardNo + '\'' +
				", wareHouseName='" + wareHouseName + '\'' +
				", wareHouseUpName='" + wareHouseUpName + '\'' +
				", resultDesc='" + resultDesc + '\'' +
				'}';
	}
}