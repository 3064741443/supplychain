package cn.com.glsx.supplychain.jst.dto.gh;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.glsx.supplychain.jst.dto.BaseDTO;

@SuppressWarnings("serial")
public class AudiMotorcycleDTO extends BaseDTO implements Serializable{

	private String merchantCode;
	private Integer parentBrandId;
	private String parentBrandName;
	private Integer subBrandId;  
    private String subBrandName;
	private Integer audiId;
	private String audiName;
	private List<String> listMotocyleDto; //车型
	private Map<String,Integer> mapMotocycleDto;
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public Integer getParentBrandId() {
		return parentBrandId;
	}
	public void setParentBrandId(Integer parentBrandId) {
		this.parentBrandId = parentBrandId;
	}
	public String getParentBrandName() {
		return parentBrandName;
	}
	public void setParentBrandName(String parentBrandName) {
		this.parentBrandName = parentBrandName;
	}
	public Integer getSubBrandId() {
		return subBrandId;
	}
	public void setSubBrandId(Integer subBrandId) {
		this.subBrandId = subBrandId;
	}
	public String getSubBrandName() {
		return subBrandName;
	}
	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
	}
	public Integer getAudiId() {
		return audiId;
	}
	public void setAudiId(Integer audiId) {
		this.audiId = audiId;
	}
	public String getAudiName() {
		return audiName;
	}
	public void setAudiName(String audiName) {
		this.audiName = audiName;
	}
	
	public List<String> getListMotocyleDto() {
		return listMotocyleDto;
	}
	public void setListMotocyleDto(List<String> listMotocyleDto) {
		this.listMotocyleDto = listMotocyleDto;
	}
	@Override
	public String toString() {
		return "AudiMotorcycleDTO [merchantCode=" + merchantCode
				+ ", parentBrandId=" + parentBrandId + ", parentBrandName="
				+ parentBrandName + ", subBrandId=" + subBrandId
				+ ", subBrandName=" + subBrandName + ", audiId=" + audiId
				+ ", audiName=" + audiName + ", listMotocyleDto="
				+ listMotocyleDto + "]";
	}
	public Map<String,Integer> getMapMotocycleDto() {
		return mapMotocycleDto;
	}
	public void setMapMotocycleDto(Map<String,Integer> mapMotocycleDto) {
		this.mapMotocycleDto = mapMotocycleDto;
	}
	
		
}
