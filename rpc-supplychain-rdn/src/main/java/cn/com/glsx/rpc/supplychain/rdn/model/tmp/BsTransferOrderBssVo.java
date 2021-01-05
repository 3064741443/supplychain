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
public class BsTransferOrderBssVo implements Serializable {
    private String tranOrderCode;
    private String orderNumber;
    private String orderSource;
    private String initiateAccount;
    private String  inServiceProviderName;
    private String  outServiceProviderName;
    private String materialCode;
    private String materialName;
    private String orderStatus;
    private Integer orderTotal;
    private Integer sendTotal;
    private Integer oweTotal;
    private JXCMTBsAddressDTO bsAddressDTO;
    private JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO;
    private String adress;
    private String createdDate;
    private Long receiveId;
    private String code;
    private String logisticsNumber;
    private String company;
    private String deliveryType;
    private Integer shipmentsQuantity;
    private String sendTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "BsTransferOrderBssVo{" +
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
