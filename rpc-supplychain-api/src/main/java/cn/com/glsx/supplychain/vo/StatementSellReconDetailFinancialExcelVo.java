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
public class StatementSellReconDetailFinancialExcelVo implements Serializable {
    /**
     * 产品名称
     */
    private String  productName;

    /**
     * 服务套餐
     */
    private String packageOne;

    /**
     * 服务期限
     */
    private String serviceTime;


    /**
     * 单位
     */
    private  String unitType;

    /**
     * 发货数量
     */
    private Integer sendCount;

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

    /**
     * 单价合计
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


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    @Override
    public String toString() {
        return "StatementSellReconDetailFinancialExcelVo{" +
                "productName='" + productName + '\'' +
                ", packageOne='" + packageOne + '\'' +
                ", serviceTime='" + serviceTime + '\'' +
                ", unitType='" + unitType + '\'' +
                ", sendCount=" + sendCount +
                ", hardwareUintPrice=" + hardwareUintPrice +
                ", serviceUintPrice=" + serviceUintPrice +
                ", hardwareTotalPrice=" + hardwareTotalPrice +
                ", serviceTotalPrice=" + serviceTotalPrice +
                ", uintTotalPrice=" + uintTotalPrice +
                ", totalPrice=" + totalPrice +
                ", sendGoodsTime='" + sendGoodsTime + '\'' +
                ", logisticsInfo='" + logisticsInfo + '\'' +
                '}';
    }
}
