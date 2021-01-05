package cn.com.glsx.supplychain.jxc.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class JXCMTDispatchOrderNumCount implements Serializable{
	private String dispatchOrderCode;
	private Integer sendNums;
	private Date sendTime;
	public String getDispatchOrderCode() {
		return dispatchOrderCode;
	}
	public void setDispatchOrderCode(String dispatchOrderCode) {
		this.dispatchOrderCode = dispatchOrderCode;
	}
	public Integer getSendNums() {
		return sendNums;
	}
	public void setSendNums(Integer sendNums) {
		this.sendNums = sendNums;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "JXCMTDispatchOrderNumCount [dispatchOrderCode="
				+ dispatchOrderCode + ", sendNums=" + sendNums + ", sendTime="
				+ sendTime + "]";
	}
	
	
	
}
