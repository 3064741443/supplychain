package cn.com.glsx.rpc.supplychain.rdn.converter;

import java.util.ArrayList;
import java.util.List;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.Logistics;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsLogisticsVo;

public class BsLogisticsRpcConvert {

	public static List<BsLogisticsVo> convertListBean(List<Logistics> listBean){
		if(null == listBean || listBean.isEmpty()){
			return null;
		}
		List<BsLogisticsVo> listDto = new ArrayList<>();
		for(Logistics bean:listBean){
			listDto.add(convertBean(bean));
		}
		return listDto;
	}
	
	public static BsLogisticsVo convertBean(Logistics bean){
		if(null == bean){
			return null;
		}
		BsLogisticsVo dto = new BsLogisticsVo();
		dto.setAccept(bean.getAccept());
		dto.setCompany(bean.getCompany());
		dto.setOrderNumber(bean.getOrderNumber());
		dto.setShipmentsQuantity(bean.getShipmentsQuantity());
		dto.setSendTime(bean.getSendTime());
		return dto;
	}
	
}
