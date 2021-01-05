package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTBsAddressDelDTO extends JXCMTBaseDTO implements Serializable{

	private List<Long> listIds;

	
	public List<Long> getListIds() {
		return listIds;
	}


	public void setListIds(List<Long> listIds) {
		this.listIds = listIds;
	}


	@Override
	public String toString() {
		return "JXCMTBsAddressDelDTO [listIds=" + listIds + "]";
	}
	
	
}
