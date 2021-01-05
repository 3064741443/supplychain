package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockExportVo implements Serializable{
	@ExcelProperty(value = "服务商名称",index = 1)
	private String merchantName;

	@ExcelProperty(value = "商户渠道名称",index = 2)
	private String channelName;

	@ExcelProperty(value = "设备类型名称",index = 3)
	private String deviceTypeName;

	@ExcelProperty(value = "月份",index = 4)
	private String stockMonth;

	@ExcelProperty(value = "期初结存",index = 5)
    private Integer openingInventory;

	@ExcelProperty(value = "期末结存",index = 6)
	private Integer endingInventory;

	@ExcelProperty(value = "当期销售数",index = 7)
	private Integer monthSales;

	@ExcelProperty(value = "当期收获数",index = 8)
	private Integer monthReceives;

	@ExcelProperty(value = "当期调入数",index = 9)
	private Integer monthCallin;

	@ExcelProperty(value = "当期调出数",index = 10)
	private Integer monthCallout;

	@ExcelProperty(value = "当期退货数",index = 11)
	private Integer monthRets;

	@ExcelProperty(value = "当期盘亏数",index = 12)
    private Integer monthLosses;

	@ExcelProperty(value = "当期激活数",index = 13)
	private Integer monthActives;

	@ExcelProperty(value = "期初激活库存",index = 14)
	private Integer openingUnatInv;

	@ExcelProperty(value = "期末激活库存",index = 15)
    private Integer endingUnatInv;

	@ExcelProperty(value = "结存库销比",index = 16)
	private Double stockSaleRatio;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getStockMonth() {
		return stockMonth;
	}

	public void setStockMonth(String stockMonth) {
		this.stockMonth = stockMonth;
	}

	public Integer getOpeningInventory() {
		return openingInventory;
	}

	public void setOpeningInventory(Integer openingInventory) {
		this.openingInventory = openingInventory;
	}

	public Integer getEndingInventory() {
		return endingInventory;
	}

	public void setEndingInventory(Integer endingInventory) {
		this.endingInventory = endingInventory;
	}

	public Integer getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(Integer monthSales) {
		this.monthSales = monthSales;
	}

	public Integer getMonthReceives() {
		return monthReceives;
	}

	public void setMonthReceives(Integer monthReceives) {
		this.monthReceives = monthReceives;
	}

	public Integer getMonthCallin() {
		return monthCallin;
	}

	public void setMonthCallin(Integer monthCallin) {
		this.monthCallin = monthCallin;
	}

	public Integer getMonthCallout() {
		return monthCallout;
	}

	public void setMonthCallout(Integer monthCallout) {
		this.monthCallout = monthCallout;
	}

	public Integer getMonthRets() {
		return monthRets;
	}

	public void setMonthRets(Integer monthRets) {
		this.monthRets = monthRets;
	}

	public Integer getMonthLosses() {
		return monthLosses;
	}

	public void setMonthLosses(Integer monthLosses) {
		this.monthLosses = monthLosses;
	}

	public Integer getMonthActives() {
		return monthActives;
	}

	public void setMonthActives(Integer monthActives) {
		this.monthActives = monthActives;
	}

	public Integer getOpeningUnatInv() {
		return openingUnatInv;
	}

	public void setOpeningUnatInv(Integer openingUnatInv) {
		this.openingUnatInv = openingUnatInv;
	}

	public Integer getEndingUnatInv() {
		return endingUnatInv;
	}

	public void setEndingUnatInv(Integer endingUnatInv) {
		this.endingUnatInv = endingUnatInv;
	}

	public Double getStockSaleRatio() {
		return stockSaleRatio;
	}

	public void setStockSaleRatio(Double stockSaleRatio) {
		this.stockSaleRatio = stockSaleRatio;
	}

	@Override
	public String toString() {
		return "STKMerchantStockVo{" +
				"merchantName='" + merchantName + '\'' +
				", channelName='" + channelName + '\'' +
				", deviceTypeName='" + deviceTypeName + '\'' +
				", stockMonth='" + stockMonth + '\'' +
				", openingInventory=" + openingInventory +
				", endingInventory=" + endingInventory +
				", monthSales=" + monthSales +
				", monthReceives=" + monthReceives +
				", monthCallin=" + monthCallin +
				", monthCallout=" + monthCallout +
				", monthRets=" + monthRets +
				", monthLosses=" + monthLosses +
				", monthActives=" + monthActives +
				", openingUnatInv=" + openingUnatInv +
				", endingUnatInv=" + endingUnatInv +
				", stockSaleRatio=" + stockSaleRatio +
				'}';
	}
}
