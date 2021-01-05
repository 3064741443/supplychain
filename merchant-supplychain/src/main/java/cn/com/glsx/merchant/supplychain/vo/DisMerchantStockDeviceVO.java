package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisMerchantStockDeviceVO implements Serializable{

	private Integer pageNum;
	
	private Integer pageSize;
	
	private String inTimeStart;
	
	private String inTimeEnd;
	
	private String outTimeStart;
	
	private String outTimeEnd;
	
	private String sn;
	
	private String merchantName;
	
	private String statu;
	
	private Integer total;
	
	private Integer pages;

	/**
	 * 出库商户
	 */
	private String toMerchantCode;
	/**
	 * 出库商户名称
	 */
	private String toMerchantName;

	private List<BsMerchantStockDeviceVO> listStockDevice;

	public String getToMerchantCode() {
		return toMerchantCode;
	}

	public void setToMerchantCode(String toMerchantCode) {
		this.toMerchantCode = toMerchantCode;
	}

	public String getToMerchantName() {
		return toMerchantName;
	}

	public void setToMerchantName(String toMerchantName) {
		this.toMerchantName = toMerchantName;
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

	public String getInTimeStart() {
		return inTimeStart;
	}

	public void setInTimeStart(String inTimeStart) {
		this.inTimeStart = inTimeStart;
	}

	public String getInTimeEnd() {
		return inTimeEnd;
	}

	public void setInTimeEnd(String inTimeEnd) {
		this.inTimeEnd = inTimeEnd;
	}

	public String getOutTimeStart() {
		return outTimeStart;
	}

	public void setOutTimeStart(String outTimeStart) {
		this.outTimeStart = outTimeStart;
	}

	public String getOutTimeEnd() {
		return outTimeEnd;
	}

	public void setOutTimeEnd(String outTimeEnd) {
		this.outTimeEnd = outTimeEnd;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public List<BsMerchantStockDeviceVO> getListStockDevice() {
		return listStockDevice;
	}

	public void setListStockDevice(List<BsMerchantStockDeviceVO> listStockDevice) {
		this.listStockDevice = listStockDevice;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	@Override
	public String toString() {
		return "DisMerchantStockDeviceVO{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", inTimeStart='" + inTimeStart + '\'' +
				", inTimeEnd='" + inTimeEnd + '\'' +
				", outTimeStart='" + outTimeStart + '\'' +
				", outTimeEnd='" + outTimeEnd + '\'' +
				", sn='" + sn + '\'' +
				", merchantName='" + merchantName + '\'' +
				", statu='" + statu + '\'' +
				", total=" + total +
				", pages=" + pages +
				", toMerchantCode='" + toMerchantCode + '\'' +
				", toMerchantName='" + toMerchantName + '\'' +
				", listStockDevice=" + listStockDevice +
				'}';
	}
}
