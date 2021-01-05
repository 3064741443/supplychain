package cn.com.glsx.supplychain.service.bs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.mapper.bs.BsMerchantOrderVehicleMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantOrderVehicle;

@Service
public class BsMerchantOrderVehicleService {

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantOrderVehicleService.class);
	@Autowired
	private BsMerchantOrderVehicleMapper vehicleMapper;
	
	
	public String getBsMerchantOrderCodeByDispatchOrderCode(String dispatchOrderCode){
		String result = "";
		BsMerchantOrderVehicle condition = new BsMerchantOrderVehicle();
		condition.setDispatchOrderCode(dispatchOrderCode);
		condition.setDeletedFlag("N");
		BsMerchantOrderVehicle vehicle = vehicleMapper.selectOne(condition);
		if(null != vehicle){
			result =  vehicle.getMerchantOrder();
		}
		return result;
	}
}
