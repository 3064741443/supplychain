//package cn.com.glsx.supplychain.jxc.vo;
//
//
//import com.alibaba.excel.annotation.ExcelProperty;
//
//import java.io.Serializable;
//
///**
// * @author luoqiang
// * @version 1.0
// * @program: supplychain
// * @description: 商务审核订单导出实体类
// * @date 2020/8/3 14:35
// */
//
//@SuppressWarnings("serial")
//public class BsMerchantOrderExcelVo implements Serializable {
//    /**
//     * 订单编号
//     */
//    @ExcelProperty(value = "订单编号")
//    private String orderNumber;
//
//    /**
//     * 产品名称
//     */
//    @ExcelProperty(value = "产品名称")
//    private String productName;
//
//    /**
//     *  物料名称
//     */
//    @ExcelProperty(value = "物料名称")
//    private String materialName;
//
//    /**
//     * 下单数量（总）
//     */
//    @ExcelProperty(value = "下单数量（总）")
//    private Integer orderQuantity;
//
//    /**
//     * 审核数量（总）
//     */
//    @ExcelProperty(value = "审核数量（总）")
//    private Integer checkQuantity;
//
//    /**
//     * 已发数量（总）
//     */
//    @ExcelProperty(value = "已发数量（总）")
//    private Integer alreadyShipmentQuantity;
//
//    /**
//     * 欠发数量（总）
//     */
//    @ExcelProperty(value = "欠发数量（总）")
//    private Integer oweQuantity;
//
//    /**
//     * 签收数量（总）
//     */
//    @ExcelProperty(value = "签收数量（总）")
//    private Integer signQuantity;
//
//    /**
//     * 订单状态
//     */
//    @ExcelProperty(value = "订单状态")
//    private String status;
//
//    /**
//     * 订单备注
//     */
//    @ExcelProperty(value = "订单备注")
//    private String orderRemark;
//
//    /**
//     * 收货人
//     */
//    @ExcelProperty(value = "收货人")
//    private String Addressee;
//
//    /**
//     * 收货人联系电话
//     */
//    @ExcelProperty(value = "收货人联系电话")
//    private String mobile;
//
//    /**
//     * 收货地址
//     */
//    @ExcelProperty(value = "收货地址")
//    private String addressDetail;
//
//    /**
//     * 下单时间
//     */
//    @ExcelProperty(value = "下单时间")
//    private String createdDate;
//
//    public String getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setOrderNumber(String orderNumber) {
//        this.orderNumber = orderNumber;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getMaterialName() {
//        return materialName;
//    }
//
//    public void setMaterialName(String materialName) {
//        this.materialName = materialName;
//    }
//
//    public Integer getOrderQuantity() {
//        return orderQuantity;
//    }
//
//    public void setOrderQuantity(Integer orderQuantity) {
//        this.orderQuantity = orderQuantity;
//    }
//
//    public Integer getCheckQuantity() {
//        return checkQuantity;
//    }
//
//    public void setCheckQuantity(Integer checkQuantity) {
//        this.checkQuantity = checkQuantity;
//    }
//
//    public Integer getAlreadyShipmentQuantity() {
//        return alreadyShipmentQuantity;
//    }
//
//    public void setAlreadyShipmentQuantity(Integer alreadyShipmentQuantity) {
//        this.alreadyShipmentQuantity = alreadyShipmentQuantity;
//    }
//
//    public Integer getOweQuantity() {
//        return oweQuantity;
//    }
//
//    public void setOweQuantity(Integer oweQuantity) {
//        this.oweQuantity = oweQuantity;
//    }
//
//    public Integer getSignQuantity() {
//        return signQuantity;
//    }
//
//    public void setSignQuantity(Integer signQuantity) {
//        this.signQuantity = signQuantity;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getOrderRemark() {
//        return orderRemark;
//    }
//
//    public void setOrderRemark(String orderRemark) {
//        this.orderRemark = orderRemark;
//    }
//
//    public String getAddressee() {
//        return Addressee;
//    }
//
//    public void setAddressee(String addressee) {
//        Addressee = addressee;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getAddressDetail() {
//        return addressDetail;
//    }
//
//    public void setAddressDetail(String addressDetail) {
//        this.addressDetail = addressDetail;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    @Override
//    public String toString() {
//        return "BsMerchantOrderExcelVo{" +
//                "orderNumber='" + orderNumber + '\'' +
//                ", productName='" + productName + '\'' +
//                ", materialName='" + materialName + '\'' +
//                ", orderQuantity=" + orderQuantity +
//                ", checkQuantity=" + checkQuantity +
//                ", alreadyShipmentQuantity=" + alreadyShipmentQuantity +
//                ", oweQuantity=" + oweQuantity +
//                ", signQuantity=" + signQuantity +
//                ", status='" + status + '\'' +
//                ", orderRemark='" + orderRemark + '\'' +
//                ", Addressee='" + Addressee + '\'' +
//                ", mobile='" + mobile + '\'' +
//                ", addressDetail='" + addressDetail + '\'' +
//                ", createdDate='" + createdDate + '\'' +
//                '}';
//    }
//}
