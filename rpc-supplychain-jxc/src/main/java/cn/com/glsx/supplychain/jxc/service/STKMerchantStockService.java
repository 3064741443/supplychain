package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDTO;
import cn.com.glsx.supplychain.jxc.kafka.ExportSTKMerchantStock;
import cn.com.glsx.supplychain.jxc.kafka.SendKafkaService;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantStockMapper;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStock;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
	private JXCMTAttribInfoService jxcmtAttribInfoService;
	@Autowired
	private SendKafkaService sendKafkaService;
	
	public Page<STKMerchantStockDTO> pageMerchantStock(RpcPagination<STKMerchantStockDTO> pagination){
		
		STKMerchantStock condition = new STKMerchantStock();
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		condition.setMerchantName(pagination.getCondition().getMerchantName());
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(pagination.getCondition().getChannelId()));
		condition.setDeviceType(pagination.getCondition().getDeviceType());
		condition.setStockMonth(pagination.getCondition().getStockMonth());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<STKMerchantStockDTO>  pageResult = merchantStockMapper.pageMerchantStock(condition);
		if(null == pageResult || pageResult.isEmpty()){
			return null;
		}
		List<STKMerchantStockDTO> listResult = pageResult.getResult();
		List<Integer> listChannelIds = listResult.stream().map(STKMerchantStockDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKMerchantStockDTO stockDto:pageResult){
			if(stockDto.getChannelId() != null){
				stockDto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(stockDto.getChannelId().byteValue())));
			}
			if(stockDto.getMonthSales()==0){
				stockDto.setStockSaleRatio(0.0);
			}else {
				stockDto.setStockSaleRatio(stockDto.getEndingInventory().doubleValue() / stockDto.getMonthSales().doubleValue());
			}
		}
		return pageResult;
	}


	public List<STKMerchantStockDTO> exportMerchantStock(STKMerchantStockDTO stkMerchantStockDTO){
		logger.info("STKMerchantStockService::exportMerchantStock record::{}",stkMerchantStockDTO);
		//发送kafka异步执行
		ExportSTKMerchantStock exportSTKMerchantStock = generatorExportMerchantStock(stkMerchantStockDTO);
		sendKafkaService.notifyStkMerchantStock(stkMerchantStockDTO.getConsumer(),Constants.TASK_CFG_ID_STK_MERCHANT_STOCK, exportSTKMerchantStock);
		STKMerchantStock condition = new STKMerchantStock();
		condition.setMerchantCode(stkMerchantStockDTO.getMerchantCode());
		condition.setMerchantName(stkMerchantStockDTO.getMerchantName());
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(stkMerchantStockDTO.getChannelId()));
		condition.setDeviceType(stkMerchantStockDTO.getDeviceType());
		List<STKMerchantStockDTO>  stkMerchantStockDTOS = merchantStockMapper.exportMerchantStock(condition);
		if(null == stkMerchantStockDTOS || stkMerchantStockDTOS.isEmpty()){
			return null;
		}
		List<Integer> listChannelIds = stkMerchantStockDTOS.stream().map(STKMerchantStockDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKMerchantStockDTO stockDto:stkMerchantStockDTOS){
			if(stockDto.getChannelId() != null){
				stockDto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(stockDto.getChannelId().byteValue())));
			}
			if(stockDto.getMonthSales()==0){
				stockDto.setStockSaleRatio(0.0);
			}else {
				stockDto.setStockSaleRatio(stockDto.getEndingInventory().doubleValue() / stockDto.getMonthSales().doubleValue());
			}
		}
		return stkMerchantStockDTOS;
	}

	private ExportSTKMerchantStock generatorExportMerchantStock(STKMerchantStockDTO stkMerchantStockDTO)
	{
		ExportSTKMerchantStock exportSTKMerchantStock = new ExportSTKMerchantStock();
		exportSTKMerchantStock.setChannelId(stkMerchantStockDTO.getChannelId());
		exportSTKMerchantStock.setDeviceType(stkMerchantStockDTO.getDeviceType());
		exportSTKMerchantStock.setMerchantSearchKey(stkMerchantStockDTO.getMerchantCode());
		exportSTKMerchantStock.setStockMonth(stkMerchantStockDTO.getStockMonth());
		return exportSTKMerchantStock;
	}
	
	private Map<Integer,String> listBsMerchantChannelName(List<Integer> listChannelIds){
		List<Integer> listIds = new ArrayList<>();
		for(Integer item:listChannelIds){
			if(null == item){
				continue;
			}
			listIds.add(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(item.byteValue()));
		}
		return jxcmtAttribInfoService.listAttribNameByIds(listIds);
	}
	
}
