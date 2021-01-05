package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.STKMerchantStockSnMapper;
import cn.com.glsx.rpc.supplychain.rdn.mapper.STKMerchantStockSnStatMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.kafka.ExportMerchantStockSn;
import cn.com.glsx.rpc.supplychain.rdn.model.kafka.ExportMerchantStockSnStat;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantStockSn;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantStockSnStat;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnDTO;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnStatDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class STKMerchantStockSnService {
	private static final Logger logger = LoggerFactory.getLogger(STKMerchantStockSnService.class);
	@Autowired
	private STKMerchantStockSnStatMapper stockSnStatMapper;
	@Autowired
	private STKMerchantStockSnMapper merchantStockSnMapper;

	public List<STKMerchantStockSnStatDTO> exportMerchantStockSnStatByToMerchantCode(STKMerchantStockSnStat stkMerchantStockSnStat){
		logger.info("STKMerchantStockSnService::exportMerchantStockSnStatByToMerchantCode stkMerchantStockSnStat::{}",stkMerchantStockSnStat);
		stkMerchantStockSnStat.setActiveOrNot("N");
		return stockSnStatMapper.exportMerchantStockSnStatByToMerchantCode(stkMerchantStockSnStat);
	}

	private ExportMerchantStockSnStat generatorMerchantStockSnStat(STKMerchantStockSnStatDTO merchantStockSnStatDTO)
	{
		ExportMerchantStockSnStat exportMerchantStockSnStat = new ExportMerchantStockSnStat();
		exportMerchantStockSnStat.setUnActiveDayFlag(merchantStockSnStatDTO.getUnActiveDayFlag());
		return exportMerchantStockSnStat;
	}

	public List<STKMerchantStockSnDTO> exportMerchantStockSn(STKMerchantStockSn merchantStockSn){
		logger.info("STKMerchantStockSnService::exportMerchantStockSn merchantStockSn::{}",merchantStockSn);
		return merchantStockSnMapper.exportMerchantStockSn(merchantStockSn);
	}

	private ExportMerchantStockSn generatorMerchantStockSn(STKMerchantStockSnDTO merchantStockSnDTO)
	{
		ExportMerchantStockSn exportMerchantStockSn = new ExportMerchantStockSn();
		exportMerchantStockSn.setDeviceType(merchantStockSnDTO.getDeviceType());
		exportMerchantStockSn.setUnActiveDayFlag(merchantStockSnDTO.getActiveOrNot());
		return exportMerchantStockSn;
	}
}
