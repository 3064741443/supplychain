package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JxcTransferOrderExportVo implements Serializable {
    @ExcelProperty(value = "调拨订单ID",index = 1)
    private String tranOrderCode;

    @ExcelProperty(value = "发起方",index = 2)
    private String orderSource;

    @ExcelProperty(value = "调拨类型",index = 3)
    private String transferType;

    @ExcelProperty(value = "服务商名称",index = 4)
    private String serviceProviderName;

    @ExcelProperty(value = "物料编码",index = 5)
    private String materialCode;

    @ExcelProperty(value = "物料名称",index = 6)
    private String materialName;

    @ExcelProperty(value = "车辆信息",index =7)
    private String motorcycleDesc;

    @ExcelProperty(value = "选择配置",index = 8)
    private String optionConfig;

    @ExcelProperty(value = "固定配置",index = 9)
    private String fasternConfig;

    @ExcelProperty(value = "调拨状态",index = 10)
    private String orderStatus;

    @ExcelProperty(value = "调拨数量",index = 11)
    private Integer orderTotal;

    @ExcelProperty(value = "已发数量",index = 12)
    private Integer sendTotal;

    @ExcelProperty(value = "欠发数量",index = 13)
    private Integer oweTotal;

    @ExcelProperty(value = "联系人",index =14)
    private String name;

    @ExcelProperty(value = "联系电话",index =15)
    private String mobile;

    @ExcelProperty(value = "联系地址",index =16)
    private String address;

    @ExcelProperty(value = "订单生成时间",index = 17)
    private String createdDate;

    @ExcelProperty(value = "物流单号",index = 18)
    private String logisticsNumber;

    @ExcelProperty(value = "物流公司",index = 19)
    private String company;

    @ExcelProperty(value = "配送方式",index = 20)
    private String deliveryType;

    @ExcelProperty(value = "配送数量",index = 21)
    private Integer shipmentsQuantity;

    @ExcelProperty(value = "发货时间",index = 22)
    private String sendTime;

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

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
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

    public String getMotorcycleDesc() {
        return motorcycleDesc;
    }

    public void setMotorcycleDesc(String motorcycleDesc) {
        this.motorcycleDesc = motorcycleDesc;
    }

    public String getOptionConfig() {
        return optionConfig;
    }

    public void setOptionConfig(String optionConfig) {
        this.optionConfig = optionConfig;
    }

    public String getFasternConfig() {
        return fasternConfig;
    }

    public void setFasternConfig(String fasternConfig) {
        this.fasternConfig = fasternConfig;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
        return "JXCMdbTransferOrderExportVo{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", transferType='" + transferType + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", motorcycleDesc='" + motorcycleDesc + '\'' +
                ", optionConfig='" + optionConfig + '\'' +
                ", fasternConfig='" + fasternConfig + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTotal=" + orderTotal +
                ", sendTotal=" + sendTotal +
                ", oweTotal=" + oweTotal +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", logisticsNumber='" + logisticsNumber + '\'' +
                ", company='" + company + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
