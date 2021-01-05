package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.STKMerchantStockMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantStock;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDTO;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class STKMerchantStockService {
	private static final Logger logger = LoggerFactory.getLogger(STKMerchantStockService.class);
	@Autowired
	private STKMerchantStockMapper merchantStockMapper;
	@Autowired
	private AttribInfoService attribInfoService;

	public List<STKMerchantStockDTO> exportMerchantStock(STKMerchantStock merchantStock ){
		logger.info("STKMerchantStockService::exportMerchantStock merchantStock::{}", JSONObject.toJSON(merchantStock));
		List<STKMerchantStockDTO>  stkMerchantStockDTOS = merchantStockMapper.exportMerchantStock(merchantStock);
		if(null == stkMerchantStockDTOS || stkMerchantStockDTOS.isEmpty()){
			return null;
		}
		List<Integer> listChannelIds = stkMerchantStockDTOS.stream().map(STKMerchantStockDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKMerchantStockDTO stockDto:stkMerchantStockDTOS){
			if(stockDto.getChannelId() != null){
				stockDto.setChannelName(mapMerchantChannels.get(attribInfoService.getMerchantChannelFromDbMerchantChannel(stockDto.getChannelId().byteValue())));
			}
			if(stockDto.getMonthSales()==0){
				stockDto.setStockSaleRatio(0.0);
			}else {
				stockDto.setStockSaleRatio(stockDto.getEndingInventory().doubleValue() / stockDto.getMonthSales().doubleValue());
			}
		}
		return stkMerchantStockDTOS;
	}

	
	private Map<Integer,String> listBsMerchantChannelName(List<Integer> listChannelIds){
		List<Integer> listIds = new ArrayList<>();
		for(Integer item:listChannelIds){
			if(null == item){
				continue;
			}
			listIds.add(attribInfoService.getMerchantChannelFromDbMerchantChannel(item.byteValue()));
		}
		return attribInfoService.listAttribNameByIds(listIds);
	}
	
}
