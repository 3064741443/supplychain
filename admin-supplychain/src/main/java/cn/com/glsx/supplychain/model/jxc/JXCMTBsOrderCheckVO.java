package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderVehicleDTO;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsOrderCheckVO implements Serializable{

	@ApiModelProperty(name = "merchantOrder", notes = "商户子订单编号", dataType = "string", required = true, example = "")
	private String merchantOrder;
	@ApiModelProperty(name = "checkMaterialCode", notes = "审核物料编码", dataType = "string", required = true, example = "")
	private String checkMaterialCode;
	@ApiModelProperty(name = "checkMaterialName", notes = "审核物料名称", dataType = "string", required = true, example = "")
	private String checkMaterialName;
	@ApiModelProperty(name = "totalCheck", notes = "审核数量", dataType = "int", required = true, example = "")
	private Integer totalCheck;
	@ApiModelProperty(name = "subjectId", notes = "项目id", dataType = "int", required = false, example = "")
	private Integer subjectId;
	@ApiModelProperty(name = "modelDevice", notes = "是否样机 Y:是 N：否", dataType = "string", required = false, example = "")
	private String modelDevice;
	@ApiModelProperty(name = "insure", notes = "是否投保 Y:是 N：否", dataType = "string", required = false, example = "")
	private String insure;
	@ApiModelProperty(name = "checkRemark", notes = "审核备注", dataType = "string", required = false, example = "")
	private String checkRemark;
	@ApiModelProperty(name = "urlDispatchBills", notes = "发货单上传服务器返回的url", dataType = "string", required = false, example = "")
	private String urlDispatchBills;
	@ApiModelProperty(name = "listOrderVehicleDto", notes = "子订单车辆数量审核", dataType = "list", required = false, example = "")
	private List<JXCMTBsMerchantOrderVehicleVO> listOrderVehicleDto;
	public String getMerchantOrder() {
		return merchantOrder;
	}
	public void setMerchantOrder(String merchantOrder) {
		this.merchantOrder = merchantOrder;
	}
	public String getCheckMaterialCode() {
		return checkMaterialCode;
	}
	public void setCheckMaterialCode(String checkMaterialCode) {
		this.checkMaterialCode = checkMaterialCode;
	}
	public String getCheckMaterialName() {
		return checkMaterialName;
	}
	public void setCheckMaterialName(String checkMaterialName) {
		this.checkMaterialName = checkMaterialName;
	}
	public Integer getTotalCheck() {
		return totalCheck;
	}
	public void setTotalCheck(Integer totalCheck) {
		this.totalCheck = totalCheck;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getModelDevice() {
		return modelDevice;
	}
	public void setModelDevice(String modelDevice) {
		this.modelDevice = modelDevice;
	}
	public String getInsure() {
		return insure;
	}
	public void setInsure(String insure) {
		this.insure = insure;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public String getUrlDispatchBills() {
		return urlDispatchBills;
	}
	public void setUrlDispatchBills(String urlDispatchBills) {
		this.urlDispatchBills = urlDispatchBills;
	}
	public List<JXCMTBsMerchantOrderVehicleVO> getListOrderVehicleDto() {
		return listOrderVehicleDto;
	}
	public void setListOrderVehicleDto(
			List<JXCMTBsMerchantOrderVehicleVO> listOrderVehicleDto) {
		this.listOrderVehicleDto = listOrderVehicleDto;
	}
	@Override
	public String toString() {
		return "JXCMTBsOrderCheckVO [merchantOrder=" + merchantOrder
				+ ", checkMaterialCode=" + checkMaterialCode
				+ ", checkMaterialName=" + checkMaterialName + ", totalCheck="
				+ totalCheck + ", subjectId=" + subjectId + ", modelDevice="
				+ modelDevice + ", insure=" + insure + ", checkRemark="
				+ checkRemark + ", urlDispatchBills=" + urlDispatchBills
				+ ", listOrderVehicleDto=" + listOrderVehicleDto + "]";
	}
	
}
