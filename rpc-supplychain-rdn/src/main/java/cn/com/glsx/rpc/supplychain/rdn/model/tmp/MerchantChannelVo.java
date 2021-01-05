package cn.com.glsx.rpc.supplychain.rdn.model.tmp;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MerchantChannelVo implements Serializable{
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "JXCMTMerchantChannelDTO [id=" + id + ", name=" + name + "]";
	}
	
}
