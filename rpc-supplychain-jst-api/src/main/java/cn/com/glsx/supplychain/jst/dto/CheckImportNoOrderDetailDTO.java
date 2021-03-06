package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CheckImportNoOrderDetailDTO extends BaseDTO implements Serializable{

	/**
	 * 发货数量
	 */
	private Integer shipmentsQuantity;

	private String merchantCode;
	
	private String shopOrderCode;

	private  Integer totalCount;
	
	private Integer successCount;
	
	private Integer failCount;
	
	private BsLogisticsDTO logisticsDto;

	private List<NoOrderDetailImportDTO> noOrderDetailImportDTOList;
	
	private List<NoOrderDetailImportDTO> listSuccess;
	
	private List<NoOrderDetailExportDTO> listFailed;
	
	// 是否导入成功 1：成功，0：失败
	private int isImported;
		
	// 失败的原因
	private String cause;
		
	// 失败文件的url
	private String url;
		
	//导入返回的字符串
	private String msg;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getShipmentsQuantity() {
		return shipmentsQuantity;
	}

	public void setShipmentsQuantity(Integer shipmentsQuantity) {
		this.shipmentsQuantity = shipmentsQuantity;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getShopOrderCode() {
		return shopOrderCode;
	}

	public void setShopOrderCode(String shopOrderCode) {
		this.shopOrderCode = shopOrderCode;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public BsLogisticsDTO getLogisticsDto() {
		return logisticsDto;
	}

	public void setLogisticsDto(BsLogisticsDTO logisticsDto) {
		this.logisticsDto = logisticsDto;
	}

	public List<NoOrderDetailImportDTO> getNoOrderDetailImportDTOList() {
		return noOrderDetailImportDTOList;
	}

	public void setNoOrderDetailImportDTOList(List<NoOrderDetailImportDTO> noOrderDetailImportDTOList) {
		this.noOrderDetailImportDTOList = noOrderDetailImportDTOList;
	}

	public List<NoOrderDetailImportDTO> getListSuccess() {
		return listSuccess;
	}

	public void setListSuccess(List<NoOrderDetailImportDTO> listSuccess) {
		this.listSuccess = listSuccess;
	}

	public List<NoOrderDetailExportDTO> getListFailed() {
		return listFailed;
	}

	public void setListFailed(List<NoOrderDetailExportDTO> listFailed) {
		this.listFailed = listFailed;
	}

	public int getIsImported() {
		return isImported;
	}

	public void setIsImported(int isImported) {
		this.isImported = isImported;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "CheckImportNoOrderDetailDTO{" +
				"shipmentsQuantity=" + shipmentsQuantity +
				", merchantCode='" + merchantCode + '\'' +
				", shopOrderCode='" + shopOrderCode + '\'' +
				", successCount=" + successCount +
				", failCount=" + failCount +
				", logisticsDto=" + logisticsDto +
				", noOrderDetailImportDTOList=" + noOrderDetailImportDTOList +
				", listSuccess=" + listSuccess +
				", listFailed=" + listFailed +
				", isImported=" + isImported +
				", cause='" + cause + '\'' +
				", url='" + url + '\'' +
				", msg='" + msg + '\'' +
				'}';
	}
}
