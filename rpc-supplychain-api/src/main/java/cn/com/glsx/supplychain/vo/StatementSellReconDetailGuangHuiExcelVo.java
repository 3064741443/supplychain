package cn.com.glsx.supplychain.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @program: supplychain
 * @description：StatementSellReconDetailExcelVo
 * @create: 2020-07-15 15:16
 * @author: luoqiang
 * @version: 1.0
 */
@SuppressWarnings("serial")
public class StatementSellReconDetailGuangHuiExcelVo implements Serializable {
    /**
     * 商户订单号
     */
    @ExcelProperty(value = "商户订单号", index = 0)
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
     * 物料名称
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

    @Override
    public String toString() {
        return "StatementSellReconDetailGuangHuiExcelVo{" +
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
                '}';
    }
}
