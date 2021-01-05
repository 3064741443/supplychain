package cn.com.glsx.supplychain.jst.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CheckImportDataDTO extends BaseDTO implements Serializable{

	private List<MyJstShopImportDTO> myJstShopImportDTOList;

	private List<MyJstShopExportDTO> myJstShopExportDTOList;

	private List<JstShopImportDTO> jstShopImportDTOList;

	private List<JstShopExportDTO> jstShopExportDTOList;

	private List<JstShopOrderDetailImportDTO> jstShopOrderDetailImportDTOList;

	private List<JstShopOrderDetailExportDTO> jstShopOrderDetailExportDTOList;

	private  List<OrderDetailDTO> orderDetailDTOList;

	private List<NoOrderDetailImportDTO> noOrderDetailImportDTOList;

	private List<NoOrderDetailExportDTO> noOrderDetailExportDTOList;

	public List<NoOrderDetailExportDTO> getNoOrderDetailExportDTOList() {
		return noOrderDetailExportDTOList;
	}

	public void setNoOrderDetailExportDTOList(List<NoOrderDetailExportDTO> noOrderDetailExportDTOList) {
		this.noOrderDetailExportDTOList = noOrderDetailExportDTOList;
	}

	public List<NoOrderDetailImportDTO> getNoOrderDetailImportDTOList() {
		return noOrderDetailImportDTOList;
	}

	public void setNoOrderDetailImportDTOList(List<NoOrderDetailImportDTO> noOrderDetailImportDTOList) {
		this.noOrderDetailImportDTOList = noOrderDetailImportDTOList;
	}

	public List<MyJstShopImportDTO> getMyJstShopImportDTOList() {
		return myJstShopImportDTOList;
	}

	public void setMyJstShopImportDTOList(List<MyJstShopImportDTO> myJstShopImportDTOList) {
		this.myJstShopImportDTOList = myJstShopImportDTOList;
	}

	public List<MyJstShopExportDTO> getMyJstShopExportDTOList() {
		return myJstShopExportDTOList;
	}

	public void setMyJstShopExportDTOList(List<MyJstShopExportDTO> myJstShopExportDTOList) {
		this.myJstShopExportDTOList = myJstShopExportDTOList;
	}

	public List<JstShopImportDTO> getJstShopImportDTOList() {
		return jstShopImportDTOList;
	}

	public void setJstShopImportDTOList(List<JstShopImportDTO> jstShopImportDTOList) {
		this.jstShopImportDTOList = jstShopImportDTOList;
	}

	public List<JstShopExportDTO> getJstShopExportDTOList() {
		return jstShopExportDTOList;
	}

	public void setJstShopExportDTOList(List<JstShopExportDTO> jstShopExportDTOList) {
		this.jstShopExportDTOList = jstShopExportDTOList;
	}

	public List<JstShopOrderDetailImportDTO> getJstShopOrderDetailImportDTOList() {
		return jstShopOrderDetailImportDTOList;
	}

	public void setJstShopOrderDetailImportDTOList(List<JstShopOrderDetailImportDTO> jstShopOrderDetailImportDTOList) {
		this.jstShopOrderDetailImportDTOList = jstShopOrderDetailImportDTOList;
	}

	public List<JstShopOrderDetailExportDTO> getJstShopOrderDetailExportDTOList() {
		return jstShopOrderDetailExportDTOList;
	}

	public void setJstShopOrderDetailExportDTOList(List<JstShopOrderDetailExportDTO> jstShopOrderDetailExportDTOList) {
		this.jstShopOrderDetailExportDTOList = jstShopOrderDetailExportDTOList;
	}

	public List<OrderDetailDTO> getOrderDetailDTOList() {
		return orderDetailDTOList;
	}

	public void setOrderDetailDTOList(List<OrderDetailDTO> orderDetailDTOList) {
		this.orderDetailDTOList = orderDetailDTOList;
	}
}
