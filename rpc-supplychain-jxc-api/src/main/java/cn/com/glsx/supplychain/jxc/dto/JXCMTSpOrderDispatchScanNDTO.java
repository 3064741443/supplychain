package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTSpOrderDispatchScanNDTO extends JXCMTBaseDTO implements Serializable{
	
	@ApiModelProperty(name = "sendMerchantNo", notes = "发往商户号(批量不填)", dataType = "string", required = false, example = "")
	private String sendMerchantNo;
	@ApiModelProperty(name = "sendMerchantName", notes = "发往商户名称(批量不填)", dataType = "string", required = false, example = "")
	private String sendMerchantName;	
	@ApiModelProperty(name = "materialCode", notes = "硬件配置编码(物料编码)", dataType = "string", required = true, example = "")
    private String materialCode;
	@ApiModelProperty(name = "materialName", notes = "硬件配置编码(物料名称)", dataType = "string", required = true, example = "")
    private String materialName;
	@ApiModelProperty(name = "address", notes = "地址(批量不填)", dataType = "string", required = false, example = "")
    private String address;
	@ApiModelProperty(name = "contacts", notes = "联系人(批量不填)", dataType = "string", required = false, example = "")
    private String contacts;
	@ApiModelProperty(name = "mobile", notes = "联系电话(批量不填)", dataType = "string", required = false, example = "")
    private String mobile;
	@ApiModelProperty(name = "remark", notes = "备注", dataType = "string", required = false, example = "")
    private String remark;
	@ApiModelProperty(name = "batchOption", notes = "是否批量操作(Y:批量操作 N:非批量操作)", dataType = "string", required = false, example = "")
    private String batchOption;
	@ApiModelProperty(name = "listMerchantOrderWarehouseDto", notes = "商户子订单对应仓库id", dataType = "string", required = true, example = "")
	List<JXCMTMerchantOrderWarehouseDTO> listMerchantOrderWarehouseDto;
	public String getSendMerchantNo() {
		return sendMerchantNo;
	}
	public void setSendMerchantNo(String sendMerchantNo) {
		this.sendMerchantNo = sendMerchantNo;
	}
	public String getSendMerchantName() {
		return sendMerchantName;
	}
	public void setSendMerchantName(String sendMerchantName) {
		this.sendMerchantName = sendMerchantName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBatchOption() {
		return batchOption;
	}
	public void setBatchOption(String batchOption) {
		this.batchOption = batchOption;
	}
	public List<JXCMTMerchantOrderWarehouseDTO> getListMerchantOrderWarehouseDto() {
		return listMerchantOrderWarehouseDto;
	}
	public void setListMerchantOrderWarehouseDto(
			List<JXCMTMerchantOrderWarehouseDTO> listMerchantOrderWarehouseDto) {
		this.listMerchantOrderWarehouseDto = listMerchantOrderWarehouseDto;
	}
	@Override
	public String toString() {
		return "JXCMTSpOrderDispatchScanNDTO [sendMerchantNo=" + sendMerchantNo
				+ ", sendMerchantName=" + sendMerchantName + ", materialCode="
				+ materialCode + ", materialName=" + materialName
				+ ", address=" + address + ", contacts=" + contacts
				+ ", mobile=" + mobile + ", remark=" + remark
				+ ", batchOption=" + batchOption
				+ ", listMerchantOrderWarehouseDto="
				+ listMerchantOrderWarehouseDto + "]";
	}
	
}
