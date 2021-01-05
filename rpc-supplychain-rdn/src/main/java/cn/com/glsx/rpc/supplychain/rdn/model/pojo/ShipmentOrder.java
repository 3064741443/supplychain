package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

/**
 * @author luoqiang
 * @version 1.0   发货订单对象
 * @program: supplychain
 * @description:
 * @date 2020/9/27 17:15
 */
public class ShipmentOrder {
	
	/**
     * 订单号
     */
	private Integer dataNo;
    /**
     * 订单号
     */
    private  String orderNumber;

    /**
     * 产品编码
     */
    private  String productCode;

    /**
     * 产品名称
     */
    private  String productName;
    /**
     * 发货数量
     */
    private  Integer sendCount;

    /**
     * 发货日期
     */
    private  String  sendDate;
    /**
     * 物流公司
     */
    private  String  logisticsCompany;
    /**
     * 备注
     */
    private String remark;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
	public Integer getDataNo() {
		return dataNo;
	}

	public void setDataNo(Integer dataNo) {
		this.dataNo = dataNo;
	}

	@Override
	public String toString() {
		return "ShipmentOrder [dataNo=" + dataNo + ", orderNumber="
				+ orderNumber + ", productCode=" + productCode
				+ ", productName=" + productName + ", sendCount=" + sendCount
				+ ", sendDate=" + sendDate + ", logisticsCompany="
				+ logisticsCompany + ", remark=" + remark + "]";
	}

	

	

	
}
