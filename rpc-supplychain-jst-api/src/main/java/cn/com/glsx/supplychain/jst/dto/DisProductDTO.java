package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisProductDTO extends BaseDTO implements Serializable{

	//搜索内容
	private String context;
		
	// 业务类型 1:驾宝无忧,2:金融风控,3:车机,4:后视镜,5:其它
	private Byte serviceType;
	
	//商户列表
	private List<BsDealerUserInfoDTO> listDealerUserInfoDTO;
	
	//页面大小
	private Integer pageSize;
			
	//页号
	private Integer pageNo;
			
	//产品列表
	private List<ProductDTO> listProductDTO;
	

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

	public List<ProductDTO> getListProductDTO() {
		return listProductDTO;
	}

	public void setListProductDTO(List<ProductDTO> listProductDTO) {
		this.listProductDTO = listProductDTO;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Byte getServiceType() {
		return serviceType;
	}

	public void setServiceType(Byte serviceType) {
		this.serviceType = serviceType;
	}

	public List<BsDealerUserInfoDTO> getListDealerUserInfoDTO() {
		return listDealerUserInfoDTO;
	}

	public void setListDealerUserInfoDTO(
			List<BsDealerUserInfoDTO> listDealerUserInfoDTO) {
		this.listDealerUserInfoDTO = listDealerUserInfoDTO;
	}

	@Override
	public String toString() {
		return "DisProductDTO [context=" + context + ", serviceType="
				+ serviceType + ", listDealerUserInfoDTO="
				+ listDealerUserInfoDTO + ", pageSize=" + pageSize
				+ ", pageNo=" + pageNo + ", listProductDTO=" + listProductDTO
				+ "]";
	}

	
		
}
