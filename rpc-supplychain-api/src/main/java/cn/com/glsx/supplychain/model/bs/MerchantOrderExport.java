package cn.com.glsx.supplychain.model.bs;

import cn.com.glsx.supplychain.model.PageInfo;
import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName MerchantOrderExcelVo
 * @Author admin
 * @Param
 * @Date 2019/3/13 14:27
 * @Version
 **/

@SuppressWarnings("serial")
public class MerchantOrderExport extends PageInfo implements Serializable {
    /**
     * 序号
     */
    private String number;
    /**
     * 商户渠道
     */
    private Byte channel;
    /**
     * 下单商户
     */
    private String merchantName;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 下单产品
     */
    private String productName;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 单价
     */
    private Double price;
    /**
     * 下单数
     */
    private Integer orderQuantity;
    /**
     * 审核数量
     */
    private Integer checkQuantity;
    /**
     * 发货单
     */
    private String dispatchOrderNumber;
    /**
     * 已发数量
     */
    private Integer alreadyShipmentQuantity;
    /**
     * 发货时间
     */
    private String shipmentTime;
    /**
     * 发货数量
     */
    private Integer shipmentQuantity;
    /**
     * 签收数量
     */
    private Integer signQuantity;
    /**
     * 欠数
     */
    private Integer oweQuantity;
    /**
     * 订单总额
     */
    private Double totalAmount;
    /**
     * 下单日期
     */
    private String orderTime;
    /**
     * 产品备注
     */
    private String productRemarks;
    /**
     * 审核人
     */
    private String checkBy;
    /**
     * 审核时间
     */
    private Date checkTime;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 收件人
     */
    private String Addressee;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 地址
     */
    private String addressDetail;
    /**
     * 失败原因
     */
    private String reason;

    @ExcelResources(title = "序号",order = 0)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @ExcelResources(title = "商户渠道",order = 1)
    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }
    @ExcelResources(title = "下单商户",order = 2)
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    @ExcelResources(title = "订单ID",order = 3)
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    @ExcelResources(title = "下单产品",order = 4)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    @ExcelResources(title = "产品编号",order = 5)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    @ExcelResources(title = "产品分类",order = 6)
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
    @ExcelResources(title = "单价",order = 7)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @ExcelResources(title = "订购数量",order = 7)
    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    @ExcelResources(title = "审核数量",order = 8)
    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }
    @ExcelResources(title = "发货单号",order = 9)
    public String getDispatchOrderNumber() {
        return dispatchOrderNumber;
    }

    public void setDispatchOrderNumber(String dispatchOrderNumber) {
        this.dispatchOrderNumber = dispatchOrderNumber;
    }
    @ExcelResources(title = "已发数量",order = 10)
    public Integer getAlreadyShipmentQuantity() {
        return alreadyShipmentQuantity;
    }

    public void setAlreadyShipmentQuantity(Integer alreadyShipmentQuantity) {
        this.alreadyShipmentQuantity = alreadyShipmentQuantity;
    }
    @ExcelResources(title = "发货时间",order = 11)
    public String getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(String shipmentTime) {
        this.shipmentTime = shipmentTime;
    }
    @ExcelResources(title = "发货数量",order = 12)
    public Integer getShipmentQuantity() {
        return shipmentQuantity;
    }

    public void setShipmentQuantity(Integer shipmentQuantity) {
        this.shipmentQuantity = shipmentQuantity;
    }
    @ExcelResources(title = "签收数量",order = 12)
    public Integer getSignQuantity() {
        return signQuantity;
    }

    public void setSignQuantity(Integer signQuantity) {
        this.signQuantity = signQuantity;
    }
    @ExcelResources(title = "欠数",order = 13)
    public Integer getOweQuantity() {
        return oweQuantity;
    }

    public void setOweQuantity(Integer oweQuantity) {
        this.oweQuantity = oweQuantity;
    }
    @ExcelResources(title = "订单总额",order = 14)
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    @ExcelResources(title = "下单时间",order = 15)
    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    @ExcelResources(title = "备注",order = 16)
    public String getProductRemarks() {
        return productRemarks;
    }

    public void setProductRemarks(String productRemarks) {
        this.productRemarks = productRemarks;
    }
    @ExcelResources(title = "审核人",order = 17)
    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }
    @ExcelResources(title = "审核时间",order = 18)
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    @ExcelResources(title = "状态",order = 19)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
    @ExcelResources(title = "联系人",order = 20)
    public String getAddressee() {
        return Addressee;
    }

    public void setAddressee(String addressee) {
        Addressee = addressee;
    }
    @ExcelResources(title = "联系电话",order = 21)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @ExcelResources(title = "地址详情",order = 22)
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }


    @ExcelResources(title = "失败原因",order = 23)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "MerchantOrderExport{" +
                "number='" + number + '\'' +
                ", channel=" + channel +
                ", merchantName='" + merchantName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productType='" + productType + '\'' +
                ", price=" + price +
                ", orderQuantity=" + orderQuantity +
                ", checkQuantity=" + checkQuantity +
                ", dispatchOrderNumber='" + dispatchOrderNumber + '\'' +
                ", alreadyShipmentQuantity=" + alreadyShipmentQuantity +
                ", shipmentTime='" + shipmentTime + '\'' +
                ", shipmentQuantity=" + shipmentQuantity +
                ", signQuantity=" + signQuantity +
                ", oweQuantity=" + oweQuantity +
                ", totalAmount=" + totalAmount +
                ", orderTime='" + orderTime + '\'' +
                ", productRemarks='" + productRemarks + '\'' +
                ", checkBy='" + checkBy + '\'' +
                ", checkTime=" + checkTime +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }
}
