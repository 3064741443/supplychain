package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.common.Constants;
import cn.com.glsx.rpc.supplychain.rdn.mapper.STKMerchantWarningDevicesnMapper;
import cn.com.glsx.rpc.supplychain.rdn.mapper.STKMerchantWarningWaresaleMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantWarningDevicesn;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantWarningWaresale;
import cn.com.glsx.rpc.supplychain.rdn.util.RdnUtils;
import cn.com.glsx.supplychain.jxc.dto.STKWarningDevicesnDTO;
import cn.com.glsx.supplychain.jxc.dto.STKWarningWaresaleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class STKWarningService {
	private static final Logger logger = LoggerFactory.getLogger(STKWarningService.class);
	@Autowired
	private STKMerchantWarningWaresaleMapper warningWaresaleMapper;
	@Autowired
	private STKMerchantWarningDevicesnMapper warningDevicesnMapper;
	@Autowired
	private AttribInfoService attribInfoService;

	public List<STKWarningDevicesnDTO> exportMerchantWarningDeviceSn(STKMerchantWarningDevicesn stkMerchantWarningDevicesn){
		logger.info("STKWarningService::exportMerchantWarningDeviceSn stkMerchantWarningDevicesn::{}",stkMerchantWarningDevicesn);
		List<STKWarningDevicesnDTO> stkWarningDevicesnDTOS = warningDevicesnMapper.exportWarningDevicesn(stkMerchantWarningDevicesn);
		if(null == stkWarningDevicesnDTOS || stkWarningDevicesnDTOS.isEmpty()){
			return null;
		}
		Date nowDate = RdnUtils.getNowDate();
		for(STKWarningDevicesnDTO snDto:stkWarningDevicesnDTOS){
			snDto.setUnActiveDays((int)((nowDate.getTime()-RdnUtils.getDateFromStrYMD(snDto.getSendTime()).getTime())/Constants.MillSecondsInOneDay));
			snDto.setStayCurMerchantDays((int)((nowDate.getTime()-RdnUtils.getDateFromStrYMD(snDto.getSendTime()).getTime())/Constants.MillSecondsInOneDay));
		}
		return stkWarningDevicesnDTOS;
	}


	public List<STKWarningWaresaleDTO> exportWarningWaresale(STKMerchantWarningWaresale stkMerchantWarningWaresale){
		logger.info("JXCTransferOrderService::exportWarningWaresale record::{}",stkMerchantWarningWaresale);
		List<STKWarningWaresaleDTO> stkWarningWaresaleDTOS = warningWaresaleMapper.exportWarningWaresale(stkMerchantWarningWaresale);
		if(null == stkWarningWaresaleDTOS || stkWarningWaresaleDTOS.isEmpty()){
			return null;
		}
		List<Integer> listChannelIds = stkWarningWaresaleDTOS.stream().map(STKWarningWaresaleDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKWarningWaresaleDTO saleDto:stkWarningWaresaleDTOS){
			if(saleDto.getChannelId() != null){
				saleDto.setChannelName(mapMerchantChannels.get(attribInfoService.getMerchantChannelFromDbMerchantChannel(saleDto.getChannelId().byteValue())));
			}
		}
		return stkWarningWaresaleDTOS;
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
