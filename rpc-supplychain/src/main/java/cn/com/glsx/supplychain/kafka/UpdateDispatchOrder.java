package cn.com.glsx.supplychain.kafka;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class UpdateDispatchOrder implements Serializable{

	private List<String> dispatchOrderCodes;

	public List<String> getDispatchOrderCodes() {
		return dispatchOrderCodes;
	}

	public void setDispatchOrderCodses(List<String> dispatchOrderCodes) {
		this.dispatchOrderCodes = dispatchOrderCodes;
	}

	@Override
	public String toString() {
		return "UpdateDispatchOrder [dispatchOrderCodes=" + dispatchOrderCodes
				+ "]";
	}

	
	
	
}
