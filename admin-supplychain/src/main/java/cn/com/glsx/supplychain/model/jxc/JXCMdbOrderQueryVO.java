package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMdbOrderQueryVO implements Serializable{
	@ApiModelProperty(name = "merchantName", notes = "服务商名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
	private String materialName;
	@ApiModelProperty(name = "orderStatus", notes = "调拨订单状态", dataType = "string", required = false, example = "")
    private String orderStatus;
	@ApiModelProperty(name = "transferType", notes = "调拨类型", dataType = "string", required = false, example = "")
	private String transferType;
	@ApiModelProperty(name = "orderSource", notes = "发起方", dataType = "string", required = false, example = "")
	private String orderSource;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	@Override
	public String toString() {
		return "JXCMdbOrderQueryVO{" +
				"merchantName='" + merchantName + '\'' +
				", materialName='" + materialName + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", transferType='" + transferType + '\'' +
				", orderSource='" + orderSource + '\'' +
				'}';
	}
}
