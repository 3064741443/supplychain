package cn.com.glsx.supplychain.jst.vo.gh;

import java.io.Serializable;
import java.util.List;

import cn.com.glsx.supplychain.jst.dto.gh.AudiMotorcycleDTO;

@SuppressWarnings("serial")
public class DisAudiMotorcycleVO implements Serializable{
	
	private String merchantCode;
	
	private List<AudiMotorcycleDTO>  listAudiMotorcycle;
	// 页面大小
	private Integer pageSize;
	// 页号
	private Integer pageNo;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public List<AudiMotorcycleDTO> getListAudiMotorcycle() {
		return listAudiMotorcycle;
	}
	public void setListAudiMotorcycle(List<AudiMotorcycleDTO> listAudiMotorcycle) {
		this.listAudiMotorcycle = listAudiMotorcycle;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	@Override
	public String toString() {
		return "DisAudiMotorcycleVO [merchantCode=" + merchantCode
				+ ", listAudiMotorcycle=" + listAudiMotorcycle + ", pageSize="
				+ pageSize + ", pageNo=" + pageNo + "]";
	}
	
	
}
