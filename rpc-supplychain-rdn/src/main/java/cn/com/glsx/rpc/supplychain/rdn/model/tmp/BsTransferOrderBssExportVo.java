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
public class BsTransferOrderBssExportVo implements Serializable {
    @ExcelProperty(value = "调拨订单ID",index = 1)
    private String tranOrderCode;
    @ExcelProperty(value = "关联商户订单ID",index =2)
    private String orderNumber;
    @ExcelProperty(value = "调出服务商",index =3)
    private String  outServiceProviderName;
    @ExcelProperty(value = "调入服务商",index =4)
    private String  inServiceProviderName;
    @ExcelProperty(value = "发起方",index =5)
    private String orderSource;
    @ExcelProperty(value = "发起账号",index =6)
    private String initiateAccount;
    @ExcelProperty(value = "物料编号",index =7)
    private String materialCode;
    @ExcelProperty(value = "物料名称",index =8)
    private String materialName;
    @ExcelProperty(value = "状态",index =9)
    private String orderStatus;
    @ExcelProperty(value = "联系人",index =10)
    private String name;
    @ExcelProperty(value = "联系电话",index =11)
    private String mobile;
    @ExcelProperty(value = "联系地址",index =12)
    private String address;
    @ExcelProperty(value = "车辆信息",index =13)
    private String motorcycleDesc;
    @ExcelProperty(value = "选择配置",index = 14)
    private String optionConfig;
    @ExcelProperty(value = "固定配置",index = 15)
    private String fasternConfig;
    @ExcelProperty(value = "调拨数量",index = 16)
    private Integer orderTotal;
    @ExcelProperty(value = "已完成数量",index = 17)
    private Integer sendTotal;
    @ExcelProperty(value = "未完成数量",index = 18)
    private Integer oweTotal;
    @ExcelProperty(value = "订单生成时间",index = 19)
    private String createdDate;
    @ExcelProperty(value = "调拨时间",index = 20)
    private String sendTime;
    @ExcelProperty(value = "配送方式",index = 21)
    private String deliveryType;
    @ExcelProperty(value = "物流公司",index = 22)
    private String company;
    @ExcelProperty(value = "物流单号",index = 23)
    private String logisticsNumber;

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

    public String getOutServiceProviderName() {
        return outServiceProviderName;
    }

    public void setOutServiceProviderName(String outServiceProviderName) {
        this.outServiceProviderName = outServiceProviderName;
    }

    public String getInServiceProviderName() {
        return inServiceProviderName;
    }

    public void setInServiceProviderName(String inServiceProviderName) {
        this.inServiceProviderName = inServiceProviderName;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    @Override
    public String toString() {
        return "JXCMdbBsTransferOrderExportVo{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", outServiceProviderName='" + outServiceProviderName + '\'' +
                ", inServiceProviderName='" + inServiceProviderName + '\'' +
                ", orderSource='" + orderSource + '\'' +
                ", initiateAccount='" + initiateAccount + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", motorcycleDesc='" + motorcycleDesc + '\'' +
                ", optionConfig='" + optionConfig + '\'' +
                ", fasternConfig='" + fasternConfig + '\'' +
                ", orderTotal=" + orderTotal +
                ", sendTotal=" + sendTotal +
                ", oweTotal=" + oweTotal +
                ", createdDate='" + createdDate + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", deliveryType='" + deliveryType + '\'' +
                ", company='" + company + '\'' +
                ", logisticsNumber='" + logisticsNumber + '\'' +
                '}';
    }
}
