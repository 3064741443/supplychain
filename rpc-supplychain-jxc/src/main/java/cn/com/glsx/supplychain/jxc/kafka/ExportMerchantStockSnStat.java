package cn.com.glsx.supplychain.jxc.kafka;

import java.io.Serializable;

/**
 * TH:大于3个月未激活 SI:大于6个月未激活 NI:大于9个月未激活
 */
@SuppressWarnings("serial")
public class ExportMerchantStockSnStat implements Serializable{
    /**
     * TH:大于3个月未激活 SI:大于6个月未激活 NI:大于9个月未激活
     */
	private String unActiveDayFlag;

	public String getUnActiveDayFlag() {
		return unActiveDayFlag;
	}

	public void setUnActiveDayFlag(String unActiveDayFlag) {
		this.unActiveDayFlag = unActiveDayFlag;
	}

	@Override
	public String toString() {
		return "ExportMerchantStockSnStat{" +
				"unActiveDayFlag='" + unActiveDayFlag + '\'' +
				'}';
	}
}
