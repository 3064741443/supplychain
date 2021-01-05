package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class DisShopVO implements Serializable{

	/**
     * 门店编号
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 门店地址
     */
    private String addr;
    /**
     *联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 门店状态 WC:待审核 PS:审核通过 FI:审核失败
     */
    private String status;
    /**
     * 门店本身的商户code
     */
    private String shopMerchantCode;
    /**
     * 门店本事的商户名称
     */
    private String shopMerchantName;

    /**
     * 代理商商户COde
     */
    private String agentMerchantCode;

    /**
     * 代理商商户NAME
     */
    private String agentMerchantName;
    
    
    private List<JstShopVO> listShopVo;
    
    //页面大小
  	private Integer pageSize;
  			
  	//页号
  	private Integer pageNo;

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShopMerchantCode() {
		return shopMerchantCode;
	}

	public void setShopMerchantCode(String shopMerchantCode) {
		this.shopMerchantCode = shopMerchantCode;
	}

	public String getShopMerchantName() {
		return shopMerchantName;
	}

	public void setShopMerchantName(String shopMerchantName) {
		this.shopMerchantName = shopMerchantName;
	}

	public String getAgentMerchantCode() {
		return agentMerchantCode;
	}

	public void setAgentMerchantCode(String agentMerchantCode) {
		this.agentMerchantCode = agentMerchantCode;
	}

	public String getAgentMerchantName() {
		return agentMerchantName;
	}

	public void setAgentMerchantName(String agentMerchantName) {
		this.agentMerchantName = agentMerchantName;
	}

	public List<JstShopVO> getListShopVo() {
		return listShopVo;
	}

	public void setListShopVo(List<JstShopVO> listShopVo) {
		this.listShopVo = listShopVo;
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
		return "DisShopVO [shopCode=" + shopCode + ", shopName=" + shopName
				+ ", addr=" + addr + ", contact=" + contact + ", phone="
				+ phone + ", status=" + status + ", shopMerchantCode="
				+ shopMerchantCode + ", shopMerchantName=" + shopMerchantName
				+ ", agentMerchantCode=" + agentMerchantCode
				+ ", agentMerchantName=" + agentMerchantName + ", listShopVo="
				+ listShopVo + ", pageSize=" + pageSize + ", pageNo=" + pageNo
				+ "]";
	}
  	
  	
}
