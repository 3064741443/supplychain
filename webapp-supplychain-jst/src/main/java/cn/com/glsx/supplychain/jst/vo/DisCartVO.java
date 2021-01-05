package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisCartVO implements Serializable{

	//页面大小
	private Integer pageSize;
		
	//页号
	private Integer pageNo;
	
	private List<CartVO> listCartVo;

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

	public List<CartVO> getListCartVo() {
		return listCartVo;
	}

	public void setListCartVo(List<CartVO> listCartVo) {
		this.listCartVo = listCartVo;
	}

	@Override
	public String toString() {
		return "DisCartVo [pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", listCartVo=" + listCartVo + "]";
	}
		
}
