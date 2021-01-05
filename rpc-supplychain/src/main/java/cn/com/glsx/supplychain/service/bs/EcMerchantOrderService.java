package cn.com.glsx.supplychain.service.bs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.supplychain.mapper.bs.EcMerchantOrderMapper;
import cn.com.glsx.supplychain.model.am.EcMerchantOrder;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;

@Service
public class EcMerchantOrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EcMerchantOrderService.class);
	
	@Autowired
	private EcMerchantOrderMapper ecMerchantOrderMapper;
	
	public void batchAddEcMerchantOrder(List<EcMerchantOrder> listEcMerchantOrder)
	{
		ecMerchantOrderMapper.insertList(listEcMerchantOrder);
	}
	
	public void updateEcMerchantOrderByOrderNumber(EcMerchantOrder record)
	{
		EcMerchantOrder queryOrder = getEcMerchantOrderByOrderNumber(record.getOrderNumber());
		record.setId(queryOrder.getId());
		ecMerchantOrderMapper.updateByPrimaryKeySelective(record);							
	}
	
	public EcMerchantOrder getEcMerchantOrderByOrderNumber(String merchantOrderNumber)
	{
		EcMerchantOrder condition = new EcMerchantOrder();
		condition.setOrderNumber(merchantOrderNumber);
		return ecMerchantOrderMapper.selectOne(condition);
	}
	
	public Integer updateEcMerchantOrderById(EcMerchantOrder record)
	{
		if(StringUtils.isEmpty(record.getId()))
		{
			return 0;
		}
		return ecMerchantOrderMapper.updateByPrimaryKeySelective(record);
	}
	
}
