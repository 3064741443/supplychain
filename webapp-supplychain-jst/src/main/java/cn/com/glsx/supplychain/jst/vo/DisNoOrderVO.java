package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DisNoOrderVO implements Serializable{

	public Integer totalCount;
	
	public List<DisNoOrderDetailVO> listNoOrderDetail;
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<DisNoOrderDetailVO> getListNoOrderDetail() {
		return listNoOrderDetail;
	}

	public void setListNoOrderDetail(List<DisNoOrderDetailVO> listNoOrderDetail) {
		this.listNoOrderDetail = listNoOrderDetail;
	}

	

	
	
	
}
