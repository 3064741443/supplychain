package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.OrderInfoMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.kafka.ExportJXCMTOrderInfo;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.AttribInfo;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.DeviceType;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.GhMerchantOrderConfig;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.OrderInfo;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderDispatchService {
	private static final Logger logger = LoggerFactory.getLogger(OrderDispatchService.class);
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private AttribManaService attribManaService;
	@Autowired
	private AttribInfoService attribInfoService;
	@Autowired
	private GhMerchantOrderService ghMerchantOrderService;

	public List<JXCMTOrderInfoDTO> exportDispatchOrder(OrderInfo orderInfo){
		logger.info("OrderDispatchService::exportDispatchOrder orderInfo::{}",orderInfo);
		List<JXCMTOrderInfoDTO> orderInfoDTOList = orderInfoMapper.exportOrderInfo(orderInfo);
		if(null == orderInfoDTOList  || orderInfoDTOList.isEmpty()){
			return orderInfoDTOList;
		}
		List<Integer> listChannelIds = orderInfoDTOList.stream().map(JXCMTOrderInfoDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		logger.info("mapMerchantChannels:{}",mapMerchantChannels);
		List<String> listMerchantOrder = orderInfoDTOList.stream().map(JXCMTOrderInfoDTO::getMerchantOrder).collect(Collectors.toList());
		Map<String,List<GhMerchantOrderConfig>> mapGhMerchantOrderConfigs = ghMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(listMerchantOrder);
		Map<String,DeviceType> mapCache = new HashMap<>();
		DeviceType jxcmtDeviceType = null;
		List<GhMerchantOrderConfig> listGhMerchantOrderConfig = null;
		AttribInfo attribInfo = null;
		for(JXCMTOrderInfoDTO dto:orderInfoDTOList){
			if(null != dto.getChannelId()){
				dto.setChannelName(mapMerchantChannels.get(attribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
			}
			if(dto.getDeviceTypeId() == null){
				jxcmtDeviceType = attribManaService.getDeviceTypeByAttribCode(dto.getAttribCode(), mapCache);
				if(null != jxcmtDeviceType){
					dto.setDeviceTypeId(jxcmtDeviceType.getId());
					dto.setDeviceTypeName(jxcmtDeviceType.getName());
				}
			}
			if(StringUtils.isEmpty(dto.getNdScan())){
				dto.setNdScan("Y");
			}
			listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrder());
			if(null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()){
				continue;
			}
			String optionConfigDesc = "";
			String fastenConfigDesc = "";
			for(GhMerchantOrderConfig oConfig:listGhMerchantOrderConfig){
				oConfig.setOption(oConfig.getStrOption());
				attribInfo = attribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
				if(null == attribInfo){
					continue;
				}
				if(oConfig.getOption().equals("O")){
					optionConfigDesc += attribInfo.getComment();
					optionConfigDesc += ":";
					optionConfigDesc += attribInfo.getName();
					optionConfigDesc += "/";
				}else if(oConfig.getOption().equals("F")){
					fastenConfigDesc += attribInfo.getComment();
					fastenConfigDesc += ":";
					fastenConfigDesc += attribInfo.getName();
					fastenConfigDesc += "/";
				}
			}
			dto.setFastenConfigDesc(fastenConfigDesc);
			dto.setOptionConfigDesc(optionConfigDesc);
		}
		return orderInfoDTOList;
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

	private ExportJXCMTOrderInfo generatorExportJXCMTOrderInfo(JXCMTOrderInfoDTO orderInfoDTO){
		ExportJXCMTOrderInfo exportJXCMTOrderInfo=new ExportJXCMTOrderInfo();
		exportJXCMTOrderInfo.setDeviceTypeId(orderInfoDTO.getDeviceTypeId());
		exportJXCMTOrderInfo.setDispatchOrderCode(orderInfoDTO.getDispatchOrderCode());
		exportJXCMTOrderInfo.setStatus(orderInfoDTO.getStatus());
		exportJXCMTOrderInfo.setMerchantOrder(orderInfoDTO.getMerchantOrder());
		exportJXCMTOrderInfo.setSendMerchantNo(orderInfoDTO.getSendMerchantNo());
		exportJXCMTOrderInfo.setWarehouseId(orderInfoDTO.getWarehouseId());
		return exportJXCMTOrderInfo;
	}

}
