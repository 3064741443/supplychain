package cn.com.glsx.supplychain.model.am;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName StatementCollectionExport 对账单-广汇采集(导入)
 * @Author admin
 * @Param
 * @Date 2019/9/12 11:28
 * @Version
 **/
@SuppressWarnings("serial")
public class StatementCollectionExport implements Serializable {
    /**
     * 时间
     */
    private String time;
    /**
     * 区域
     */
    private String area;
    /**
     * 服务商/经销商
     */
    private String merchant;
    /**
     * 店名
     */
    private String shopName;
    /**
     *物料编码
     */
    private String materialCode;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 分类
     */
    private String deviceType;
    /**
     * 总部含税单价
     */
    private Double price;
    /**
     * 总部总金额
     */
    private Double priceNum;
    /**
     * 返利点
     */
    private Double rebate;
    /**
     * 返利总额
     */
    private Double rebateNum;
    /**
     * 返利后总额
     */
    private Double afterRebateNum;
    /**
     * 返利后单价
     */
    private Double afterRebatePrice;
    /**
     * 销售数量
     */
    private Integer salesQuantity;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 销售编码
     */
    private String saleCode;
    /**
     * 销售名称
     */
    private String saleName;
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 发货仓库名称
     */
    private String warehouseName;

    //失败描述
    private String failDesc;

    @ExcelResources(title = "月份",order = 0)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    @ExcelResources(title = "区域",order = 1)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    @ExcelResources(title = "服务商/经销商",order = 2)
    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
    @ExcelResources(title = "店名",order = 3)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    @ExcelResources(title = "*(订单明细)物料编码#编码",order = 4)
    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
    @ExcelResources(title = "(订单明细)物料编码#名称",order = 5)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    @ExcelResources(title = "分类",order = 6)
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    @ExcelResources(title = "(订单明细)销售数量",order = 7)
    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }
    @ExcelResources(title = "总部单价（含税）",order = 8)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @ExcelResources(title = "总部总金额",order = 9)
    public Double getPriceNum() {
        return priceNum;
    }

    public void setPriceNum(Double priceNum) {
        this.priceNum = priceNum;
    }
    @ExcelResources(title = "返利点",order = 10)
    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }
    @ExcelResources(title = "返利总额",order = 11)
    public Double getRebateNum() {
        return rebateNum;
    }

    public void setRebateNum(Double rebateNum) {
        this.rebateNum = rebateNum;
    }
    @ExcelResources(title = "返利后总额",order = 12)
    public Double getAfterRebateNum() {
        return afterRebateNum;
    }

    public void setAfterRebateNum(Double afterRebateNum) {
        this.afterRebateNum = afterRebateNum;
    }
    @ExcelResources(title = "返利后单价",order = 13)
    public Double getAfterRebatePrice() {
        return afterRebatePrice;
    }

    public void setAfterRebatePrice(Double afterRebatePrice) {
        this.afterRebatePrice = afterRebatePrice;
    }

    @ExcelResources(title = " *(基本信息)客户#编码",order = 14)
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    @ExcelResources(title = "(基本信息)客户#名称",order = 15)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    @ExcelResources(title = "*(基本信息)销售组#编码",order = 16)
    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
    @ExcelResources(title = "*(基本信息)销售组#名称",order = 17)
    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }
    @ExcelResources(title = "(订单明细)发货仓库#编码",order = 18)
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    @ExcelResources(title = "(订单明细)发货仓库#名称",order = 19)
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    @ExcelResources(title = "失败原因",order = 20)
    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    @Override
    public String toString() {
        return "StatementCollection{" +
                ", time=" + time +
                ", area='" + area + '\'' +
                ", merchant='" + merchant + '\'' +
                ", shopName='" + shopName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", price=" + price +
                ", priceNum=" + priceNum +
                ", rebate=" + rebate +
                ", rebateNum=" + rebateNum +
                ", afterRebateNum=" + afterRebateNum +
                ", afterRebatePrice=" + afterRebatePrice +
                ", salesQuantity=" + salesQuantity +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", saleCode='" + saleCode + '\'' +
                ", saleName='" + saleName + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
