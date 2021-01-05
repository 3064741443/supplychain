package cn.com.glsx.merchant.supplychain.vo.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class JXCMTBsAddressDelVO {

	@ApiModelProperty(name = "listIds", notes = "地址id集合", dataType = "list", required = false, example = "")
	public List<Long> listIds;

	public List<Long> getListIds() {
		return listIds;
	}

	public void setListIds(List<Long> listIds) {
		this.listIds = listIds;
	}
	
	
}
