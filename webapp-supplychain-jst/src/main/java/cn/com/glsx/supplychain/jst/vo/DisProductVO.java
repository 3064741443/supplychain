package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisProductVO implements Serializable{

	//搜索内容
	private String context;
	
	//业务类型  JB:驾宝无忧,JR:金融风控,CZ:车机,HS:后视镜,OT:其它
	private String serviceType;
	
	//待查产品所属的商户列表
	private List<String> listMerchantCode;
	
	//页面大小
	private Integer pageSize;
	
	//页号
	private Integer pageNo;
	
	//产品列表
	private List<ProductVO> listProductVo;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}



	public String getServiceType() {
		return serviceType;
	}



	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public List<ProductVO> getListProductVo() {
		return listProductVo;
	}



	public void setListProductVo(List<ProductVO> listProductVo) {
		this.listProductVo = listProductVo;
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

	public List<String> getListMerchantCode() {
		return listMerchantCode;
	}

	public void setListMerchantCode(List<String> listMerchantCode) {
		this.listMerchantCode = listMerchantCode;
	}

	@Override
	public String toString() {
		return "DisProductVO [context=" + context + ", serviceType="
				+ serviceType + ", listMerchantCode=" + listMerchantCode
				+ ", pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", listProductVo=" + listProductVo + "]";
	}

	
	
	
	
	
}
