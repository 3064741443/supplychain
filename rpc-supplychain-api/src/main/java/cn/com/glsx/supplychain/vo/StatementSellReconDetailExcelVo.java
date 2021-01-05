package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

/**
 * @program: supplychain
 * @description：StatementSellReconDetailExcelVo
 * @create: 2020-07-15 15:16
 * @author: luoqiang
 * @version: 1.0
 */
@SuppressWarnings("serial")
public class StatementSellReconDetailExcelVo implements Serializable {

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户地址
     */
    private String customerAddr;

    /**
     * 客户联系人
     */
    private String customerContact;

    /**
     * 客户联系电话
     */
    private String customerPhone;


    /**
     * 销售组名称
     */
    private String saleGroupName;

    /**
     * 销售组地址
     */
    private String saleGroupAddr;

    /**
     * 销售组联系人
     */
    private String saleGroupContact;

    /**
     * 销售组联系电话
     */
    private String saleGroupPhone;

    /**
     * 商户订单号
     */
    private String merchantOrderCode;

    /**
     * 产品名称
     */
    private String  productName;

    /**
     * 物料编号
     */
    private String materialCodes;

    /**
     * 物料编码
     */
    private String materialNames;

    /***
     * 单位
     */
    private  String unitType;

    /**
     * 销售数量
     */
    private Integer sendCount;

    /**
     * 含税单价
     */
    private Double uintTotalPrice;

    /**
     * 价税合计
     */
    private Double totalPrice;

    /**
     * 发货时间
     */
    private String sendGoodsTime;


    /**
     * 发货物流
     */
    private String logisticsInfo;

    /**
     * 服务套餐
     */
    private String packageOne;

    /**
     * 服务期限
     */
    private String serviceTime;

    /**
     * 硬件单价含税
     */
    private Double hardwareUintPrice;

    /**
     * 服务费单价含税
     */
    private Double serviceUintPrice;

    /**
     * 硬件小计
     */
    private Double hardwareTotalPrice;

    /**
     * 服务费小计
     */
    private Double serviceTotalPrice;

    public String getMerchantOrderCode() {
        return merchantOrderCode;
    }

    public void setMerchantOrderCode(String merchantOrderCode) {
        this.merchantOrderCode = merchantOrderCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMaterialCodes() {
        return materialCodes;
    }

    public void setMaterialCodes(String materialCodes) {
        this.materialCodes = materialCodes;
    }

    public String getMaterialNames() {
        return materialNames;
    }

    public void setMaterialNames(String materialNames) {
        this.materialNames = materialNames;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Double getUintTotalPrice() {
        return uintTotalPrice;
    }

    public void setUintTotalPrice(Double uintTotalPrice) {
        this.uintTotalPrice = uintTotalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSendGoodsTime() {
        return sendGoodsTime;
    }

    public void setSendGoodsTime(String sendGoodsTime) {
        this.sendGoodsTime = sendGoodsTime;
    }

    public String getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(String logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public String getPackageOne() {
        return packageOne;
    }

    public void setPackageOne(String packageOne) {
        this.packageOne = packageOne;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Double getHardwareUintPrice() {
        return hardwareUintPrice;
    }

    public void setHardwareUintPrice(Double hardwareUintPrice) {
        this.hardwareUintPrice = hardwareUintPrice;
    }

    public Double getServiceUintPrice() {
        return serviceUintPrice;
    }

    public void setServiceUintPrice(Double serviceUintPrice) {
        this.serviceUintPrice = serviceUintPrice;
    }

    public Double getHardwareTotalPrice() {
        return hardwareTotalPrice;
    }

    public void setHardwareTotalPrice(Double hardwareTotalPrice) {
        this.hardwareTotalPrice = hardwareTotalPrice;
    }

    public Double getServiceTotalPrice() {
        return serviceTotalPrice;
    }

    public void setServiceTotalPrice(Double serviceTotalPrice) {
        this.serviceTotalPrice = serviceTotalPrice;
    }

    @Override
    public String toString() {
        return "StatementSellReconDetailExcelVo{" +
                "merchantOrderCode='" + merchantOrderCode + '\'' +
                ", productName='" + productName + '\'' +
                ", materialCodes='" + materialCodes + '\'' +
                ", materialNames='" + materialNames + '\'' +
                ", unitType='" + unitType + '\'' +
                ", sendCount=" + sendCount +
                ", uintTotalPrice=" + uintTotalPrice +
                ", totalPrice=" + totalPrice +
                ", sendGoodsTime='" + sendGoodsTime + '\'' +
                ", logisticsInfo='" + logisticsInfo + '\'' +
                ", packageOne='" + packageOne + '\'' +
                ", serviceTime='" + serviceTime + '\'' +
                ", hardwareUintPrice=" + hardwareUintPrice +
                ", serviceUintPrice=" + serviceUintPrice +
                ", hardwareTotalPrice=" + hardwareTotalPrice +
                ", serviceTotalPrice=" + serviceTotalPrice +
                '}';
    }
}
