package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockDTO extends JXCMTBaseDTO implements Serializable{
	
	@ApiModelProperty(name = "merchantCode", notes = "服务商编码", dataType = "string", required = false, example = "")
    private String merchantCode;
	@ApiModelProperty(name = "merchantName", notes = "服务商名称", dataType = "string", required = false, example = "")
	private String merchantName;
	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "channelName", notes = "商户渠道名称", dataType = "string", required = false, example = "")
	private String channelName;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "deviceTypeName", notes = "设备类型名称", dataType = "string", required = false, example = "")
	private String deviceTypeName;
	@ApiModelProperty(name = "stockMonth", notes = "月份 格式2020-08", dataType = "string", required = false, example = "")
	private String stockMonth;	
	@ApiModelProperty(name = "openingInventory", notes = "期初结存", dataType = "int", required = false, example = "")
    private Integer openingInventory;
	@ApiModelProperty(name = "endingInventory", notes = "期末结存", dataType = "int", required = false, example = "")
    private Integer endingInventory;
	@ApiModelProperty(name = "monthSales", notes = "当期销售数", dataType = "int", required = false, example = "")
    private Integer monthSales;
	@ApiModelProperty(name = "monthReceives", notes = "当期收获数", dataType = "int", required = false, example = "")
    private Integer monthReceives;
	@ApiModelProperty(name = "monthCallin", notes = "当期调入数", dataType = "int", required = false, example = "")
    private Integer monthCallin;
	@ApiModelProperty(name = "monthCallout", notes = "当期调出数", dataType = "int", required = false, example = "")
    private Integer monthCallout;
	@ApiModelProperty(name = "monthRets", notes = "当期退货数", dataType = "int", required = false, example = "")
    private Integer monthRets;
	@ApiModelProperty(name = "monthLosses", notes = "当期盘亏数", dataType = "int", required = false, example = "")
    private Integer monthLosses;
	@ApiModelProperty(name = "monthActives", notes = "当期激活数", dataType = "int", required = false, example = "")
    private Integer monthActives;
	@ApiModelProperty(name = "monthDemolish", notes = "当期删除数", dataType = "int", required = false, example = "")
    private Integer monthDemolish;
	@ApiModelProperty(name = "openingUnatInv", notes = "期初未激活库存", dataType = "int", required = false, example = "")
    private Integer openingUnatInv;
	@ApiModelProperty(name = "endingUnatInv", notes = "期末未激活库存", dataType = "int", required = false, example = "")
    private Integer endingUnatInv;
	@ApiModelProperty(name = "stockSaleRatio", notes = "结存库销比", dataType = "int", required = false, example = "")
	private Double stockSaleRatio;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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
	
	public Integer getMonthDemolish() {
		return monthDemolish;
	}
	public void setMonthDemolish(Integer monthDemolish) {
		this.monthDemolish = monthDemolish;
	}
	@Override
	public String toString() {
		return "STKMerchantStockDTO [merchantCode=" + merchantCode
				+ ", merchantName=" + merchantName + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + ", stockMonth="
				+ stockMonth + ", openingInventory=" + openingInventory
				+ ", endingInventory=" + endingInventory + ", monthSales="
				+ monthSales + ", monthReceives=" + monthReceives
				+ ", monthCallin=" + monthCallin + ", monthCallout="
				+ monthCallout + ", monthRets=" + monthRets + ", monthLosses="
				+ monthLosses + ", monthActives=" + monthActives
				+ ", monthDemolish=" + monthDemolish + ", openingUnatInv="
				+ openingUnatInv + ", endingUnatInv=" + endingUnatInv
				+ ", stockSaleRatio=" + stockSaleRatio + "]";
	}
	
}
