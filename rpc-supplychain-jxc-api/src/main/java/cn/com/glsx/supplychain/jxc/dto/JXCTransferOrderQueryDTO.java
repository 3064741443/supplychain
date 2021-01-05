package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JXCTransferOrderQueryDTO extends JXCMTBaseDTO implements Serializable {
    @ApiModelProperty(name = "merchantCode", notes = "登录服务商编码", dataType = "string", required = false, example = "")
    private String merchantCode;
    @ApiModelProperty(name = "serviceProviderName", notes = "服务商名称", dataType = "string", required = false, example = "")
    private String serviceProviderName;
    @ApiModelProperty(name = "inServiceProviderName", notes = "调入服务商名称", dataType = "string", required = false, example = "")
    private String inServiceProviderName;
    @ApiModelProperty(name = "transferType", notes = "调拨类型", dataType = "string", required = false, example = "")
    private String transferType;
    @ApiModelProperty(name = "orderSource", notes = "发起方 GXS:广联商务 SMJ:服务商pc经销存 SMX:服务商JXC小程序", dataType = "string", required = false, example = "")
    private String orderSource;
    @ApiModelProperty(name = "materialName", notes = "物料编码/名称", dataType = "string", required = false, example = "")
    private String materialName;
    @ApiModelProperty(name = "orderStatus", notes = "调拨状态", dataType = "string", required = false, example = "")
    private String orderStatus;
    @ApiModelProperty(name = "startTime", notes = "开始时间", dataType = "string", required = false, example = "")
    private String startTime;
    @ApiModelProperty(name = "endTime", notes = "结束时间", dataType = "string", required = false, example = "")
    private String endTime;
    @ApiModelProperty(name = "tranOrderCode", notes = "调拨订单号", dataType = "string", required = false, example = "")
    private String tranOrderCode;;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
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
        return "JXCTransferOrderQueryDTO{" +
                "merchantCode='" + merchantCode + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", inServiceProviderName='" + inServiceProviderName + '\'' +
                ", transferType='" + transferType + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", materialName='" + materialName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tranOrderCode='" + tranOrderCode + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
