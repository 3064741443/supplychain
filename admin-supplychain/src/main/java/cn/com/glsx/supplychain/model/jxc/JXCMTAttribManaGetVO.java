package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTAttribManaGetVO implements Serializable{

	@ApiModelProperty(name = "attribCode", notes = "硬件配置编码等同物料编码", dataType = "string", required = true, example = "")
	private String attribCode;

	public String getAttribCode() {
		return attribCode;
	}

	public void setAttribCode(String attribCode) {
		this.attribCode = attribCode;
	}

	@Override
	public String toString() {
		return "JXCMTAttribManaGetVO [attribCode=" + attribCode + "]";
	}
	
	
}
