package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTOrderInfoDownloadVO implements Serializable{

	@ApiModelProperty(name = "dispatchOrderCode", notes = "发货单编号", dataType = "string", required = true, example = "")
    private String dispatchOrderCode;

	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}

	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	
	
}
