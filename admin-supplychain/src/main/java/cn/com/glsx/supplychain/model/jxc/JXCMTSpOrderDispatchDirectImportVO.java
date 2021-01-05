package cn.com.glsx.supplychain.model.jxc;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class JXCMTSpOrderDispatchDirectImportVO implements Serializable{

	public List<JXCMTSpOrderDispatchDirectVO> listDirectVos;

	public List<JXCMTSpOrderDispatchDirectVO> getListDirectVos() {
		return listDirectVos;
	}

	public void setListDirectVos(List<JXCMTSpOrderDispatchDirectVO> listDirectVos) {
		this.listDirectVos = listDirectVos;
	}

	@Override
	public String toString() {
		return "JXCMTSpOrderDispatchDirectImportVO [listDirectVos="
				+ listDirectVos + "]";
	}
	
	
}
