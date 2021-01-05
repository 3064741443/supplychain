package cn.com.glsx.supplychain.jst.dto.gh;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import java.io.Serializable;

@SuppressWarnings("serial")
@ContentRowHeight(96)
@HeadRowHeight(16)
@ColumnWidth(16)
public class GhMerchantOrderExportDTO implements Serializable{
	//订单编号
	@ExcelProperty(value = "订单ID", index = 1)
    private String ghMerchantOrderCode;

    //下单商户
	@ExcelProperty(value = "下单商户", index = 2)
    private String merchantName;

    //spa产品编码
	@ExcelProperty(value = "SAP产品编码", index =3)
    private String spaProductCode;

    //SAP订单编号
    @ExcelProperty(value = "SAP订单编号", index =4)
	private String spaPurchaseCode;

    //spa产品名称
	@ExcelProperty(value = "产品名称", index = 5)
    private String spaProductName;

    //产品类型
	@ExcelProperty(value = "产品类型", index = 6)
    private String categoryName;

    //车辆属性
	@ExcelProperty(value = "车辆属性", index = 7)
    private String vechoAttrib;

    //固定配置
	@ExcelProperty(value = "固定配置", index = 8)
    private String fasternConfig;

    //选项配置
	@ExcelProperty(value = "选择配置", index = 9)
    private String optionConfig;

    //订单备注
	@ExcelProperty(value = "订单备注	", index = 10)
    private String remark;

	//状态
	@ExcelProperty(value = "状态", index = 11)
	private String conversionStatus;

    //订单状态
	@ExcelProperty(value = "订单状态", index = 12)
    private String status;

    //订购总数
	@ExcelProperty(value = "订购总数", index = 13)
    private Integer total;

    //已发数量
	@ExcelProperty(value = "已发数量", index = 14)
    private Integer alreadySend;

    //欠发数量
	@ExcelProperty(value = "欠发数量", index = 15)
    private Integer owerSend;

	//催单次数
	@ExcelProperty(value = "催单次数", index = 16)
	private Integer reminderTotal;

    //下单时间
	@ExcelProperty(value = "下单时间", index = 17)
    private String orderTime;

	//下单时间
	@ExcelProperty(value = "收货人", index = 18)
	private String name;

	//下单时间
	@ExcelProperty(value = "电话", index = 19)
	private String mobile;

	//下单时间
	@ExcelProperty(value = "地址", index = 20)
	private String address;

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

	public String getGhMerchantOrderCode() {
		return ghMerchantOrderCode;
	}
	public void setGhMerchantOrderCode(String ghMerchantOrderCode) {
		this.ghMerchantOrderCode = ghMerchantOrderCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getSpaProductCode() {
		return spaProductCode;
	}
	public void setSpaProductCode(String spaProductCode) {
		this.spaProductCode = spaProductCode;
	}
	public String getSpaProductName() {
		return spaProductName;
	}
	public void setSpaProductName(String spaProductName) {
		this.spaProductName = spaProductName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getVechoAttrib() {
		return vechoAttrib;
	}
	public void setVechoAttrib(String vechoAttrib) {
		this.vechoAttrib = vechoAttrib;
	}
	public String getFasternConfig() {
		return fasternConfig;
	}
	public void setFasternConfig(String fasternConfig) {
		this.fasternConfig = fasternConfig;
	}
	public String getOptionConfig() {
		return optionConfig;
	}
	public void setOptionConfig(String optionConfig) {
		this.optionConfig = optionConfig;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getAlreadySend() {
		return alreadySend;
	}
	public void setAlreadySend(Integer alreadySend) {
		this.alreadySend = alreadySend;
	}
	public Integer getOwerSend() {
		return owerSend;
	}
	public void setOwerSend(Integer owerSend) {
		this.owerSend = owerSend;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

    public String getSpaPurchaseCode() {
        return spaPurchaseCode;
    }

    public void setSpaPurchaseCode(String spaPurchaseCode) {
        this.spaPurchaseCode = spaPurchaseCode;
    }

	public String getConversionStatus() {
		return conversionStatus;
	}

	public void setConversionStatus(String conversionStatus) {
		this.conversionStatus = conversionStatus;
	}

	public Integer getReminderTotal() {
		return reminderTotal;
	}

	public void setReminderTotal(Integer reminderTotal) {
		this.reminderTotal = reminderTotal;
	}

	@Override
	public String toString() {
		return "GhMerchantOrderExportDTO{" +
				"ghMerchantOrderCode='" + ghMerchantOrderCode + '\'' +
				", merchantName='" + merchantName + '\'' +
				", spaProductCode='" + spaProductCode + '\'' +
				", spaPurchaseCode='" + spaPurchaseCode + '\'' +
				", spaProductName='" + spaProductName + '\'' +
				", categoryName='" + categoryName + '\'' +
				", vechoAttrib='" + vechoAttrib + '\'' +
				", fasternConfig='" + fasternConfig + '\'' +
				", optionConfig='" + optionConfig + '\'' +
				", remark='" + remark + '\'' +
				", conversionStatus='" + conversionStatus + '\'' +
				", status='" + status + '\'' +
				", total=" + total +
				", alreadySend=" + alreadySend +
				", owerSend=" + owerSend +
				", reminderTotal=" + reminderTotal +
				", orderTime='" + orderTime + '\'' +
				", name='" + name + '\'' +
				", mobile='" + mobile + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
