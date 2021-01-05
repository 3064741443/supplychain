package cn.com.glsx.supplychain.model.bs;

import cn.com.glsx.supplychain.model.PageInfo;
import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

/**
 * @ClassName MerchantOrderExcelVo
 * @Author admin
 * @Param
 * @Date 2019/3/13 14:27
 * @Version
 **/

@SuppressWarnings("serial")
public class MerchantOrderImport extends PageInfo implements Serializable {
    /**
     * 序号
     */
    private String number;
    /**
     * 项目
     */
    private String project;
    /**
     * 客户名称
     */
    private String merchantName;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 规格
     */
    private String specification;
    /**
     * 方案
     */
    private String programme;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 下单日期
     */
    private String orderTime;
    /**
     * 下单数
     */
    private Integer orderQuantity;
    /**
     * 出货数量
     */
    private Integer shipmentQuantity;
    /**
     * 欠数
     */
    private  Integer oweQuantity;
    /**
     * 发货日期
     */
    private String shipmentTime;
    /**
     * 物流单
     */
    private String dispatchOrderNumber;
    /**
     * 物流公司
     */
    private String company;
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
     * 产品备注
     */
    private String productRemarks;

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

    @ExcelResources(title = "项目",order = 1)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
    @ExcelResources(title = "客户名称",order = 2)
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    @ExcelResources(title = "订单号",order = 3)
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    @ExcelResources(title = "产品类型",order = 4)
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
    @ExcelResources(title = "产品规格",order = 5)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    @ExcelResources(title = "方案",order = 6)
    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
    @ExcelResources(title = "产品编码",order = 7)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    @ExcelResources(title = "产品名称",order = 8)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    @ExcelResources(title = "下单日期",order = 9)
    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    @ExcelResources(title = "下单数量",order = 10)
    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    @ExcelResources(title = "出货数量",order = 11)
    public Integer getShipmentQuantity() {
        return shipmentQuantity;
    }

    public void setShipmentQuantity(Integer shipmentQuantity) {
        this.shipmentQuantity = shipmentQuantity;
    }
    @ExcelResources(title = "欠数",order = 12)
    public Integer getOweQuantity() {
        return oweQuantity;
    }

    public void setOweQuantity(Integer oweQuantity) {
        this.oweQuantity = oweQuantity;
    }
    @ExcelResources(title = "发货日期",order = 13)
    public String getShipmentTime() {
        return shipmentTime;
    }

    public void setShipmentTime(String shipmentTime) {
        this.shipmentTime = shipmentTime;
    }
    @ExcelResources(title = "物流单号",order = 14)
    public String getDispatchOrderNumber() {
        return dispatchOrderNumber;
    }

    public void setDispatchOrderNumber(String dispatchOrderNumber) {
        this.dispatchOrderNumber = dispatchOrderNumber;
    }
    @ExcelResources(title = "物流公司",order = 15)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    @ExcelResources(title = "联系人",order = 16)
    public String getAddressee() {
        return Addressee;
    }

    public void setAddressee(String addressee) {
        Addressee = addressee;
    }
    @ExcelResources(title = "联系方式",order = 17)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @ExcelResources(title = "地址",order = 18)
    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    @ExcelResources(title = "备注",order = 19)
    public String getProductRemarks() {
        return productRemarks;
    }

    public void setProductRemarks(String productRemarks) {
        this.productRemarks = productRemarks;
    }
    @ExcelResources(title = "失败原因",order = 20)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "MerchantOrderExcelVo{" +
                "project='" + project + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", productType='" + productType + '\'' +
                ", specification='" + specification + '\'' +
                ", programme='" + programme + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", orderTime=" + orderTime +
                ", orderQuantity=" + orderQuantity +
                ", shipmentQuantity=" + shipmentQuantity +
                ", oweQuantity=" + oweQuantity +
                ", shipmentTime=" + shipmentTime +
                ", dispatchOrderNumber='" + dispatchOrderNumber + '\'' +
                ", Addressee='" + Addressee + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", productRemarks='" + productRemarks + '\'' +
                '}';
    }
}
