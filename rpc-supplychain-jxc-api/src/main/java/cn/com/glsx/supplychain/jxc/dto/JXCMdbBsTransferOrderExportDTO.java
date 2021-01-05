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
public class JXCMdbBsTransferOrderExportDTO implements Serializable{
    @ApiModelProperty(name = "tranOrderCode", notes = "调拨订单号", dataType = "string", required = true, example = "")
    private String tranOrderCode;
    @ApiModelProperty(name = "orderNumber", notes = "关联商户订单号", dataType = "string", required = true, example = "")
    private String orderNumber;
    @ApiModelProperty(name = "orderSource", notes = "发起方", dataType = "string", required = true, example = "")
    private String orderSource;
    @ApiModelProperty(name = "initiateAccount", notes = "发起账号", dataType = "string", required = true, example = "")
    private String initiateAccount;
    @ApiModelProperty(name = "inServiceProviderName", notes = "调入服务商名称", dataType = "string", required = true, example = "")
    private String  inServiceProviderName;
    @ApiModelProperty(name = "outServiceProviderName", notes = "调出服务商名称", dataType = "string", required = true, example = "")
    private String  outServiceProviderName;
    @ApiModelProperty(name = "materialCode", notes = "调拨物料编号", dataType = "string", required = true, example = "")
    private String materialCode;
    @ApiModelProperty(name = "materialName", notes = "调拨物料名称", dataType = "string", required = true, example = "")
    private String materialName;
    @ApiModelProperty(name = "orderStatus", notes = "调拨状态(WC:待审核,WS:待发货/待完成,PS:部分完成,FA:已完成,RB:审核驳回)", dataType = "string", required = true, example = "")
    private String orderStatus;
    @ApiModelProperty(name = "orderTotal", notes = "调拨数量", dataType = "string", required = true, example = "")
    private Integer orderTotal;
    @ApiModelProperty(name = "sendTotal", notes = "已发数量", dataType = "string", required = true, example = "")
    private Integer sendTotal;
    @ApiModelProperty(name = "oweTotal", notes = "未完成数量", dataType = "string", required = true, example = "")
    private Integer oweTotal;
    @ApiModelProperty(name = "bsAddressDTO", notes = "下单地址信息", dataType = "object", required = true, example = "")
    private JXCMTBsAddressDTO bsAddressDTO;
    @ApiModelProperty(name = "bsMerchantOrderVehicleDTO", notes = "车辆信息", dataType = "object", required = true, example = "")
    private JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO;
    @ApiModelProperty(name = "adress", notes = "发货地址信息", dataType = "string", required = true, example = "")
    private String adress;
    @ApiModelProperty(name = "createdDate", notes = "订单生成时间", dataType = "String", required = true, example = "")
    private String createdDate;
    @ApiModelProperty(name = "receiveId", notes = "收货地址ID", dataType = "Long", required = true, example = "")
    private Long receiveId;
    @ApiModelProperty(name = "code", notes = "物流编号", dataType = "string", required = true, example = "")
    private String code;
    @ApiModelProperty(name = "logisticsNumber", notes = "物流单号", dataType = "string", required = true, example = "")
    private String logisticsNumber;
    @ApiModelProperty(name = "company", notes = "物流公司", dataType = "string", required = true, example = "")
    private String company;
    @ApiModelProperty(name = "deliveryType", notes = "L:物流配送  O:线下配送", dataType = "string", required = false, example = "")
    private String deliveryType;
    @ApiModelProperty(name = "shipmentsQuantity", notes = "配送数量", dataType = "int", required = true, example = "")
    private Integer shipmentsQuantity;
    @ApiModelProperty(name = "sendTime", notes = "调拨时间", dataType = "string", required = false, example = "")
    private String sendTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getInitiateAccount() {
        return initiateAccount;
    }

    public void setInitiateAccount(String initiateAccount) {
        this.initiateAccount = initiateAccount;
    }

    public String getInServiceProviderName() {
        return inServiceProviderName;
    }

    public void setInServiceProviderName(String inServiceProviderName) {
        this.inServiceProviderName = inServiceProviderName;
    }

    public String getOutServiceProviderName() {
        return outServiceProviderName;
    }

    public void setOutServiceProviderName(String outServiceProviderName) {
        this.outServiceProviderName = outServiceProviderName;
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

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(Integer sendTotal) {
        this.sendTotal = sendTotal;
    }

    public Integer getOweTotal() {
        return oweTotal;
    }

    public void setOweTotal(Integer oweTotal) {
        this.oweTotal = oweTotal;
    }

    public JXCMTBsAddressDTO getBsAddressDTO() {
        return bsAddressDTO;
    }

    public void setBsAddressDTO(JXCMTBsAddressDTO bsAddressDTO) {
        this.bsAddressDTO = bsAddressDTO;
    }

    public JXCMTBsMerchantOrderVehicleDTO getBsMerchantOrderVehicleDTO() {
        return bsMerchantOrderVehicleDTO;
    }

    public void setBsMerchantOrderVehicleDTO(JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO) {
        this.bsMerchantOrderVehicleDTO = bsMerchantOrderVehicleDTO;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getShipmentsQuantity() {
        return shipmentsQuantity;
    }

    public void setShipmentsQuantity(Integer shipmentsQuantity) {
        this.shipmentsQuantity = shipmentsQuantity;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "JXCMdbBsTransferOrderExportDTO{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", initiateAccount='" + initiateAccount + '\'' +
                ", inServiceProviderName='" + inServiceProviderName + '\'' +
                ", outServiceProviderName='" + outServiceProviderName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTotal=" + orderTotal +
                ", sendTotal=" + sendTotal +
                ", oweTotal=" + oweTotal +
                ", bsAddressDTO=" + bsAddressDTO +
                ", bsMerchantOrderVehicleDTO=" + bsMerchantOrderVehicleDTO +
                ", adress='" + adress + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", receiveId=" + receiveId +
                ", code='" + code + '\'' +
                ", logisticsNumber='" + logisticsNumber + '\'' +
                ", company='" + company + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
