package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description: 采购订单导出实体类
 * @date 2020/8/3 14:35
 */

@SuppressWarnings("serial")
public class JXCMTBsMerchantOrderExcelVo implements Serializable {
    /**
     * 订单编号
     */
    private String moOrderCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     *  物料名称
     */
    private String materialName;

    /**
     * 下单数量（总）
     */
    private Integer totalOrder;

    /**
     * 审核数量（总）
     */
    private Integer totalCheck;

    /**
     * 已发数量（总）
     */
    private Integer totalSends;

    /**
     * 欠发数量（总）
     */
    private Integer totalOwes;

    /**
     * 签收数量（总）
     */
    private Integer acceptQuantity;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * 收货人
     */
    private String name;

    /**
     * 收货人联系电话
     */
    private String mobile;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 下单时间
     */
    private String  orderTime;

    @ExcelResources(title = "订单编号",order = 0)
    public String getMoOrderCode() {
        return moOrderCode;
    }

    public void setMoOrderCode(String moOrderCode) {
        this.moOrderCode = moOrderCode;
    }

    @ExcelResources(title = "产品名称",order = 1)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ExcelResources(title = "物料名称",order = 2)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @ExcelResources(title = "下单数量（总）",order = 3)
    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    @ExcelResources(title = "审核数量（总）",order = 4)
    public Integer getTotalCheck() {
        return totalCheck;
    }

    public void setTotalCheck(Integer totalCheck) {
        this.totalCheck = totalCheck;
    }

    @ExcelResources(title = "已发数量（总）",order = 5)
    public Integer getTotalSends() {
        return totalSends;
    }

    public void setTotalSends(Integer totalSends) {
        this.totalSends = totalSends;
    }

    @ExcelResources(title = "欠发数量（总）",order = 6)
    public Integer getTotalOwes() {
        return totalOwes;
    }

    public void setTotalOwes(Integer totalOwes) {
        this.totalOwes = totalOwes;
    }

    @ExcelResources(title = "签收数量（总）",order = 7)
    public Integer getAcceptQuantity() {
        return acceptQuantity;
    }

    public void setAcceptQuantity(Integer acceptQuantity) {
        this.acceptQuantity = acceptQuantity;
    }

    @ExcelResources(title = "订单状态",order = 8)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ExcelResources(title = "订单备注",order = 9)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ExcelResources(title = "收货人",order = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelResources(title = "收货人联系电话",order = 11)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ExcelResources(title = "收货地址",order = 12)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ExcelResources(title = "下单时间",order = 13)
	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "JXCMTBsMerchantOrderExcelVo [moOrderCode=" + moOrderCode
				+ ", productName=" + productName + ", materialName="
				+ materialName + ", totalOrder=" + totalOrder + ", totalCheck="
				+ totalCheck + ", totalSends=" + totalSends + ", totalOwes="
				+ totalOwes + ", acceptQuantity=" + acceptQuantity
				+ ", status=" + status + ", remarks=" + remarks + ", name="
				+ name + ", mobile=" + mobile + ", address=" + address
				+ ", orderTime=" + orderTime + "]";
	}
 
	
}
