package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CheckImportTransferSnDTO implements Serializable{
	private  Integer totalCount;
	
	private Integer successCount;
	
	private Integer failCount;
	
	// 是否导入成功 1：成功，0：失败
	private int isImported;
		
	// 失败的原因
	private String cause;
		
	// 失败文件的url
	private String url;
		
	//导入返回的字符串
	private String msg;

	List<TransferSnImportDTO> transferSnImportDTOList;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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

	public List<TransferSnImportDTO> getTransferSnImportDTOList() {
		return transferSnImportDTOList;
	}

	public void setTransferSnImportDTOList(List<TransferSnImportDTO> transferSnImportDTOList) {
		this.transferSnImportDTOList = transferSnImportDTOList;
	}

	@Override
	public String toString() {
		return "CheckImportTransferSnDTO{" +
				"totalCount=" + totalCount +
				", successCount=" + successCount +
				", failCount=" + failCount +
				", isImported=" + isImported +
				", cause='" + cause + '\'' +
				", url='" + url + '\'' +
				", msg='" + msg + '\'' +
				", transferSnImportDTOList=" + transferSnImportDTOList +
				'}';
	}
}
