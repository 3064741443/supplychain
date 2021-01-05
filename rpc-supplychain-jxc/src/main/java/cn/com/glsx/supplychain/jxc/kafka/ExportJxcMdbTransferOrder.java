package cn.com.glsx.supplychain.jxc.kafka;

//进销存调拨订单导出条件搜索
public class ExportJxcMdbTransferOrder {
	private String inServiceProviderName;
	private String transferType;
	private String orderSource;
	private String materialName;
	private String orderStatus;

	public String getInServiceProviderName() {
		return inServiceProviderName;
	}

	public void setInServiceProviderName(String inServiceProviderName) {
		this.inServiceProviderName = inServiceProviderName;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "ExportJxcMdbTransferOrder{" +
				"inServiceProviderName='" + inServiceProviderName + '\'' +
				", transferType='" + transferType + '\'' +
				", orderSource='" + orderSource + '\'' +
				", materialName='" + materialName + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				'}';
	}
}
