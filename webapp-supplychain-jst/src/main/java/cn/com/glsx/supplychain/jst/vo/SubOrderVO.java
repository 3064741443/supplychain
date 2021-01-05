package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubOrderVO implements Serializable{

	//地址
	private AddressVO addressVo;
	
	//下单物品
	private List<CartVO> listCartVo;

	public AddressVO getAddressVo() {
		return addressVo;
	}

	public void setAddressVo(AddressVO addressVo) {
		this.addressVo = addressVo;
	}

	public List<CartVO> getListCartVo() {
		return listCartVo;
	}

	public void setListCartVo(List<CartVO> listCartVo) {
		this.listCartVo = listCartVo;
	}

	@Override
	public String toString() {
		return "SubOrderVo [addressVo=" + addressVo + ", listCartVo="
				+ listCartVo + "]";
	}
	
	
	
	
}
