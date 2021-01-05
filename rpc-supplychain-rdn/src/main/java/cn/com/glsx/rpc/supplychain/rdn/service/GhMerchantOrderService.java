package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.GhMerchantOrderConfigMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.GhMerchantOrderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GhMerchantOrderService {
	private static final Logger logger = LoggerFactory.getLogger(GhMerchantOrderService.class);
	@Autowired
	private GhMerchantOrderConfigMapper jxcmtGhMerchantOrderConfigMapper;

	public Map<String,List<GhMerchantOrderConfig>> mapListGhMerchantOrderConfigByMerchantOrders(List<String> listMerchantOrders){
		Map<String,List<GhMerchantOrderConfig>> mapResult = new HashMap<>();
		if(null == listMerchantOrders || listMerchantOrders.isEmpty()){
			return mapResult;
		}
		GhMerchantOrderConfig condition = new GhMerchantOrderConfig();
		condition.setDeletedFlag("N");
		condition.setListMerchantOrder(listMerchantOrders);
		List<GhMerchantOrderConfig> listMerchantOrderConfigs = jxcmtGhMerchantOrderConfigMapper.listGhMerchantOrderConfig(condition);
		if(null == listMerchantOrderConfigs || listMerchantOrderConfigs.isEmpty()){
			return mapResult;
		}
		List<GhMerchantOrderConfig> listResult = null;
		for(GhMerchantOrderConfig oConfig:listMerchantOrderConfigs){
			listResult = mapResult.get(oConfig.getMerchantOrder());
			if(null == listResult){
				listResult = new ArrayList<>();
				mapResult.put(oConfig.getMerchantOrder(), listResult);
			}
			listResult.add(oConfig);
			mapResult.put(oConfig.getMerchantOrder(),listResult);
		}

		return mapResult;
	}
}
