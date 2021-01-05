package cn.com.glsx.rpc.supplychain.rdn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.EcMerchantOrderMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.EcMerchantOrder;


@Service
public class EcMerchantOrderService {

	@Autowired
	private EcMerchantOrderMapper ecMerchantOrderMapper;
	
	public List<EcMerchantOrder> exportEcMerchantOrderExit(EcMerchantOrder record)
	{
		return ecMerchantOrderMapper.exportEcMerchantOrderExit(record);
	}
}
