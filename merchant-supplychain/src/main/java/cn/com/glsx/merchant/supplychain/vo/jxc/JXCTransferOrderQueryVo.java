package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */

@SuppressWarnings("serial")
public class JXCTransferOrderQueryVo implements Serializable {
    @ApiModelProperty(name = "inServiceProviderName", notes = "调入服务商名称", dataType = "string", required = false, example = "")
    private String inServiceProviderName;
    @ApiModelProperty(name = "transferType", notes = "调拨类型 IN:调入 OUT:调出", dataType = "string", required = false, example = "")
    private String transferType;
    @ApiModelProperty(name = "orderSource", notes = "发起方 GXS:广联商务 SMJ:服务商pc经销存 SMX:服务商JXC小程序", dataType = "string", required = false, example = "")
    private String orderSource;
    @ApiModelProperty(name = "materialName", notes = "物料名称/编码", dataType = "string", required = false, example = "")
    private String materialName;
    @ApiModelProperty(name = "orderStatus", notes = "调拨状态:WC:待审核,WS:待发货,PS:部分完成,FA:已完成,RB:审核驳回 ", dataType = "string", required = false, example = "")
    private String orderStatus;
    @ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;

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

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "JXCTransferOrderQueryVo{" +
				"inServiceProviderName='" + inServiceProviderName + '\'' +
				", transferType='" + transferType + '\'' +
				", orderSource='" + orderSource + '\'' +
				", materialName='" + materialName + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				'}';
	}
}
