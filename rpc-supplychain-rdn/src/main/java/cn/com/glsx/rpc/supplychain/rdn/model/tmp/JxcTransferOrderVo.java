package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantOrderVehicleDTO;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JxcTransferOrderVo implements Serializable {
    private String tranOrderCode;
    private String orderSource;
    private String transferType;
//    @ApiModelProperty(name = "serviceProviderName", notes = "服务商名称", dataType = "string", required = true, example = "")
//    private String serviceProviderName;
    private String  inServiceProviderName;
    private String  outServiceProviderName;
    private String materialCode;
    private String materialName;
    private JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO;
    private String orderStatus;
    private Integer orderTotal;
    private Integer sendTotal;
    private Integer oweTotal;
    private JXCMTBsAddressDTO bsAddressDTO;
    private String createdDate;
    private Long receiveId;
    private String code;
    private String logisticsNumber;
    private String company;
    private String deliveryType;
    private Integer shipmentsQuantity;
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

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
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

    public JXCMTBsMerchantOrderVehicleDTO getBsMerchantOrderVehicleDTO() {
        return bsMerchantOrderVehicleDTO;
    }

    public void setBsMerchantOrderVehicleDTO(JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO) {
        this.bsMerchantOrderVehicleDTO = bsMerchantOrderVehicleDTO;
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
        return "JXCMdbTransferOrderExportDTO{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", transferType='" + transferType + '\'' +
                ", inServiceProviderName='" + inServiceProviderName + '\'' +
                ", outServiceProviderName='" + outServiceProviderName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", bsMerchantOrderVehicleDTO=" + bsMerchantOrderVehicleDTO +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTotal=" + orderTotal +
                ", sendTotal=" + sendTotal +
                ", oweTotal=" + oweTotal +
                ", bsAddressDTO=" + bsAddressDTO +
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
