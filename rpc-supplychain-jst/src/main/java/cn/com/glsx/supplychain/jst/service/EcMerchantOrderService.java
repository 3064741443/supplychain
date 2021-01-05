package cn.com.glsx.supplychain.jst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.mapper.EcMerchantOrderMapper;
import cn.com.glsx.supplychain.jst.model.EcMerchantOrder;

@Service
public class EcMerchantOrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EcMerchantOrderService.class);
	
	@Autowired
	private EcMerchantOrderMapper ecMerchantOrderMapper;
	
	public Integer batchAddEcMerchantOrderNoCatch(List<EcMerchantOrder> listEcMerchantOrder)
	{
		return ecMerchantOrderMapper.insertList(listEcMerchantOrder);
	}
	
	public Integer updateEcMerchantOrderByIdNoCatch(EcMerchantOrder ecMerchantOrder)
	{
		if(StringUtils.isEmpty(ecMerchantOrder.getId()))
		{
			return 0;
		}
		return ecMerchantOrderMapper.updateByPrimaryKeySelective(ecMerchantOrder);
	}
	
	public EcMerchantOrder getEcMerchantOrderByMerchantCode(String merchantOrderCode)
	{
		EcMerchantOrder condition = new EcMerchantOrder();
		condition.setOrderNumber(merchantOrderCode);
		return ecMerchantOrderMapper.selectOne(condition);
	}
}
