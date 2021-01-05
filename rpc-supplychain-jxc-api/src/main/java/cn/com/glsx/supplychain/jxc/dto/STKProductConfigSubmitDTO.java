package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class STKProductConfigSubmitDTO extends JXCMTBaseDTO implements Serializable{

	@ApiModelProperty(name = "operateCode", notes = "操作编码", dataType = "string", required = false, example = "")
	private String operateCode;
	@ApiModelProperty(name = "listProductConfig", notes = "配置实体", dataType = "object", required = false, example = "")
	private List<STKProductConfigDTO> listProductConfig;
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	public List<STKProductConfigDTO> getListProductConfig() {
		return listProductConfig;
	}
	public void setListProductConfig(List<STKProductConfigDTO> listProductConfig) {
		this.listProductConfig = listProductConfig;
	}
	@Override
	public String toString() {
		return "STKProductConfigSubmitDTO [operateCode=" + operateCode
				+ ", listProductConfig=" + listProductConfig + "]";
	}
	
}
