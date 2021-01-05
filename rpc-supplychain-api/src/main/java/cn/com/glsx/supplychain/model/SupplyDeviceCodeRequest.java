package cn.com.glsx.supplychain.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.StringUtils;

@SuppressWarnings("serial")
public class SupplyDeviceCodeRequest extends SupplyRequest implements Serializable{

	//设备编码
	private Integer deviceCode;

	//设备编码ID
    private Integer id;

    //设备名称
    private String deviceName;

    //品牌定制商id
    private Integer merchantId;

    //设备大类ID
    private Integer typeId;
    
    //厂商码
    private String manufacturerCode;  
    
    //设备编码ID集合
    private List<Integer> listIds;
    
	public List<Integer> getListIds() {
		return listIds;
	}

	public void setListIds(List<Integer> listIds) {
		this.listIds = listIds;
	}

	public Integer getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(Integer deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	@Override
	public String toString() {
		return "SupplyDeviceCodeRequest [deviceCode=" + deviceCode + ", id="
				+ id + ", deviceName=" + deviceName + ", merchantId="
				+ merchantId + ", typeId=" + typeId + ", manufacturerCode="
				+ manufacturerCode + ", listIds=" + (StringUtils.isEmpty(listIds)?0:listIds.size()) + "]";
	}

	
    
	
    
}
