package cn.com.glsx.supplychain.model.stk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductConfigGetVO implements Serializable{

	@ApiModelProperty(name = "operateCode", notes = "操作码", dataType = "string", required = true, example = "")
    private String operateCode;

	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	@Override
	public String toString() {
		return "ProductConfigGetVO [operateCode=" + operateCode + "]";
	}
	
	
}
