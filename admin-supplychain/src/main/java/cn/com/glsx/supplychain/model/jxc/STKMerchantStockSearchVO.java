package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class STKMerchantStockSearchVO implements Serializable{

	@ApiModelProperty(name = "channelId", notes = "商户渠道id", dataType = "string", required = false, example = "")
	private Integer channelId;
	@ApiModelProperty(name = "merchantSearchKey", notes = "服务商登陆名", dataType = "string", required = false, example = "")
	private String merchantSearchKey;
	@ApiModelProperty(name = "deviceType", notes = "设备类型id", dataType = "int", required = false, example = "")
    private Integer deviceType;
	@ApiModelProperty(name = "stockMonth", notes = "月份 格式2020-08", dataType = "string", required = false, example = "")
	private String stockMonth;	
	@ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
	private Integer pageNum;
	@ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
	private Integer pageSize;
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getMerchantSearchKey() {
		return merchantSearchKey;
	}
	public void setMerchantSearchKey(String merchantSearchKey) {
		this.merchantSearchKey = merchantSearchKey;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getStockMonth() {
		return stockMonth;
	}
	public void setStockMonth(String stockMonth) {
		this.stockMonth = stockMonth;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "STKMerchantStockSearchVO [channelId=" + channelId
				+ ", merchantSearchKey=" + merchantSearchKey + ", deviceType="
				+ deviceType + ", stockMonth=" + stockMonth + ", pageNum="
				+ pageNum + ", pageSize=" + pageSize + "]";
	}
	
	
}
