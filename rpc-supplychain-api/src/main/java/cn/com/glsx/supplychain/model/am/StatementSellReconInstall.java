package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StatementSellReconInstall implements Serializable{

	private String installCode;
	
	private String materialCode;
	
	private String materialName;
	
	private Integer sendCount;

    private Double serviceUintPrice;

    private Double serviceTotalPrice;
    
    private String deletedFlag;

	public String getInstallCode() {
		return installCode;
	}

	public void setInstallCode(String installCode) {
		this.installCode = installCode;
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

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Double getServiceUintPrice() {
		return serviceUintPrice;
	}

	public void setServiceUintPrice(Double serviceUintPrice) {
		this.serviceUintPrice = serviceUintPrice;
	}

	public Double getServiceTotalPrice() {
		return serviceTotalPrice;
	}

	public void setServiceTotalPrice(Double serviceTotalPrice) {
		this.serviceTotalPrice = serviceTotalPrice;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	@Override
	public String toString() {
		return "StatementSellReconInstall [installCode=" + installCode
				+ ", materialCode=" + materialCode + ", materialName="
				+ materialName + ", sendCount=" + sendCount
				+ ", serviceUintPrice=" + serviceUintPrice
				+ ", serviceTotalPrice=" + serviceTotalPrice + ", deletedFlag="
				+ deletedFlag + "]";
	}
    
	
    
}
