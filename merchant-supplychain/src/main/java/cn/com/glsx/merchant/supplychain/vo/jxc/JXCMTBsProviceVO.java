package cn.com.glsx.merchant.supplychain.vo.jxc;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class JXCMTBsProviceVO implements Serializable{

	@ApiModelProperty(name = "provinceId", notes = "省份id", dataType = "int", required = true, example = "32")
    private Integer provinceId;
	@ApiModelProperty(name = "provinceName", notes = "省份名称", dataType = "string", required = true, example = "安徽省")
    private String provinceName;
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@Override
	public String toString() {
		return "JXCMTBsProviceVO [provinceId=" + provinceId + ", provinceName="
				+ provinceName + "]";
	}
	
	
}
