package cn.com.glsx.supplychain.service.bs;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.mapper.bs.BsMerchantOrderSignMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantOrderSign;

@Service
public class MerchantOrderSignService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantOrderService.class);
	
	@Autowired
	private BsMerchantOrderSignMapper merchantOrderSignsMapper;
	
	public int updateMerchantOrderSignBySignCode(BsMerchantOrderSign orderSign)
	{
		return merchantOrderSignsMapper.updateMerchantOrderSignBySignCode(orderSign);
	}
	
	public int batchAddMerchantOrderSign(List<BsMerchantOrderSign> listOrderSign)
	{
		if(listOrderSign == null)
		{
			return 0;
		}
		if(listOrderSign.size() == 0)
		{
			return 0;
		}
		Date nowTime = new Date();
		BsMerchantOrderSign condition = new BsMerchantOrderSign();
		for(BsMerchantOrderSign orderSign:listOrderSign)
		{
			if(StringUtils.isEmpty(orderSign.getMerchantOrderNumber()))
			{
				continue;
			}
			condition.setMerchantOrderNumber(orderSign.getMerchantOrderNumber());
			BsMerchantOrderSign dbOrderSign = merchantOrderSignsMapper.selectOne(condition);
			if(dbOrderSign == null)
			{
				orderSign.setCreatedDate(nowTime);
				orderSign.setUpdatedDate(nowTime);
				merchantOrderSignsMapper.insertSelective(orderSign);
			}
			else
			{
				orderSign.setUpdatedDate(nowTime);
				orderSign.setId(dbOrderSign.getId());
				merchantOrderSignsMapper.updateByPrimaryKeySelective(orderSign);
			}
		}
		return 0;
	}
	
	public List<BsMerchantOrderSign> listMerchantOrderSignByMerchantOrder(List<String> listMerchantOrders)
	{
		return merchantOrderSignsMapper.listMerchantOrderSignByMerchantOrder(listMerchantOrders);
	}
}
