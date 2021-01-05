package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.supplychain.jxc.mapper.JXCMTGhMerchantOrderConfigMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTGhMerchantOrderMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTGhMerchantOrderReflectMcodeMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTGhMerchantOrderConfig;
import cn.com.glsx.supplychain.jxc.model.JXCMTGhMerchantOrderReflectMcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JXCMTGhMerchantOrderService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTGhMerchantOrderService.class);
	@Autowired
	private JXCMTGhMerchantOrderMapper jxcmtGhMerchantOrderMapper;
	@Autowired
	private JXCMTGhMerchantOrderConfigMapper jxcmtGhMerchantOrderConfigMapper;
	@Autowired
	private JXCMTGhMerchantOrderReflectMcodeMapper jxcmtGhMerchantOrderReflectMcodeMapper;
	
	public String getGhMerchantOrderByMerchantOrder(String merchantOrder){
		String result = "";
		JXCMTGhMerchantOrderReflectMcode condition = new JXCMTGhMerchantOrderReflectMcode();
		condition.setDeletedFlag("N");
		condition.setMerchantOrder(merchantOrder);
		JXCMTGhMerchantOrderReflectMcode reflectMcode = jxcmtGhMerchantOrderReflectMcodeMapper.selectOne(condition);
		if(null == reflectMcode){
			return result;
		}
		result = reflectMcode.getGhMerchantOrderCode();
		return result;
	}
	
	public List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfigByGhMerchantOrder(String ghMerchantOrder){
		JXCMTGhMerchantOrderConfig condition = new JXCMTGhMerchantOrderConfig();
		condition.setGhMerchantOrderCode(ghMerchantOrder);
		condition.setDeletedFlag("N");
		return jxcmtGhMerchantOrderConfigMapper.selectGhMerchantOrderConfig(condition);
	}
	
	public Map<String,List<JXCMTGhMerchantOrderConfig>> mapListGhMerchantOrderConfigByMerchantOrders(List<String> listMerchantOrders){
		Map<String,List<JXCMTGhMerchantOrderConfig>> mapResult = new HashMap<>();
		if(null == listMerchantOrders || listMerchantOrders.isEmpty()){
			return mapResult;
		}
		JXCMTGhMerchantOrderConfig condition = new JXCMTGhMerchantOrderConfig();
		condition.setDeletedFlag("N");
		condition.setListMerchantOrder(listMerchantOrders);
		List<JXCMTGhMerchantOrderConfig> listMerchantOrderConfigs = jxcmtGhMerchantOrderConfigMapper.listGhMerchantOrderConfig(condition);
		if(null == listMerchantOrderConfigs || listMerchantOrderConfigs.isEmpty()){
			return mapResult;
		}
		List<JXCMTGhMerchantOrderConfig> listResult = null;
		for(JXCMTGhMerchantOrderConfig oConfig:listMerchantOrderConfigs){
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
