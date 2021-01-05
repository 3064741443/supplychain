package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JXCTransferOrderQueryVo implements Serializable {
    @ApiModelProperty(name = "serviceProviderName", notes = "服务商名称", dataType = "string", required = false, example = "")
    private String serviceProviderName;
    @ApiModelProperty(name = "transferType", notes = "调拨类型", dataType = "string", required = false, example = "")
    private String transferType;
    @ApiModelProperty(name = "orderSource", notes = "发起方", dataType = "string", required = false, example = "")
    private String orderSource;
    @ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
    private String  materialCode;
    @ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;
    @ApiModelProperty(name = "orderStatus", notes = "调拨状态", dataType = "string", required = false, example = "")
    private String orderStatus;
    @ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;

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
                "serviceProviderName='" + serviceProviderName + '\'' +
                ", transferType='" + transferType + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
