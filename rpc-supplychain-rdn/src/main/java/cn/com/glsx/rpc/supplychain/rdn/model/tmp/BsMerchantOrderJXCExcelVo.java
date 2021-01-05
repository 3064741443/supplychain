package cn.com.glsx.rpc.supplychain.rdn.model.tmp;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description: 采购订单导出实体类
 * @date 2020/8/3 14:35
 */

public class BsMerchantOrderJXCExcelVo implements Serializable {
    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    private String moOrderCode;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称")
    private String productName;

    /**
     *  物料名称
     */
    @ExcelProperty(value = "物料名称")
    private String materialName;

    /**
     * 下单数量（总）
     */
    @ExcelProperty(value = "下单数量（总")
    private Integer totalOrder;

    /**
     * 审核数量（总）
     */
    @ExcelProperty(value = "审核数量（总）")
    private Integer totalCheck;

    /**
     * 已发数量（总）
     */
    @ExcelProperty(value = "已发数量（总）")
    private Integer totalSends;

    /**
     * 欠发数量（总）
     */
    @ExcelProperty(value = "欠发数量（总）")
    private Integer totalOwes;

    /**
     * 签收数量（总）
     */
    @ExcelProperty(value = "签收数量（总）")
    private Integer acceptQuantity;

    /**
     * 订单状态
     */
    @ExcelProperty(value = "订单状态")
    private String status;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String remarks;

    /**
     * 收货人
     */
    @ExcelProperty(value = "收货人")
    private String name;

    /**
     * 收货人联系电话
     */
    @ExcelProperty(value = "收货人联系电话")
    private String mobile;

    /**
     * 收货地址
     */
    @ExcelProperty(value = "收货地址")
    private String address;

    /**
     * 下单时间
     */
    @ExcelProperty(value = "下单时间")
    private String  orderTime;

    public String getMoOrderCode() {
        return moOrderCode;
    }

    public void setMoOrderCode(String moOrderCode) {
        this.moOrderCode = moOrderCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getTotalCheck() {
        return totalCheck;
    }

    public void setTotalCheck(Integer totalCheck) {
        this.totalCheck = totalCheck;
    }

    public Integer getTotalSends() {
        return totalSends;
    }

    public void setTotalSends(Integer totalSends) {
        this.totalSends = totalSends;
    }

    public Integer getTotalOwes() {
        return totalOwes;
    }

    public void setTotalOwes(Integer totalOwes) {
        this.totalOwes = totalOwes;
    }

    public Integer getAcceptQuantity() {
        return acceptQuantity;
    }

    public void setAcceptQuantity(Integer acceptQuantity) {
        this.acceptQuantity = acceptQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

    
}
