package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.kafka.ExportMerchantWarningWaresale;
import cn.com.glsx.supplychain.jxc.kafka.ExportWarningDeviceSn;
import cn.com.glsx.supplychain.jxc.kafka.SendKafkaService;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantWarningDevicesnMapper;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantWarningSlowCodeAssumeMapper;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantWarningSlowMaterialAssumeMapper;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantWarningWaresaleMapper;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningDevicesn;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningSlowMaterialAssume;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningWaresale;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
	private STKMerchantWarningSlowCodeAssumeMapper warningSlowCodeAssumeMapper;
	@Autowired
	private STKMerchantWarningSlowMaterialAssumeMapper warningSlowMaterialAssumeMapper;
	@Autowired
	private STKMerchantWarningDevicesnMapper warningDevicesnMapper;
	@Autowired
	private JXCMTAttribInfoService jxcmtAttribInfoService;

	@Autowired
	private SendKafkaService sendKafkaService;
	
	public Page<STKWarningDevicesnDTO> pageMerchantWarningDeviceSn(RpcPagination<STKWarningDevicesnDTO> pagination){
		STKMerchantWarningDevicesn condition = new STKMerchantWarningDevicesn();
		condition.setWarningCode(pagination.getCondition().getWarningCode());
		condition.setToMerchantCode(pagination.getCondition().getToMerchantCode());
		condition.setToMerchantName(pagination.getCondition().getToMerchantName());
		condition.setCurMerchantCode(pagination.getCondition().getCurMerchantCode());
		condition.setCurMerchantName(pagination.getCondition().getCurMerchantName());
		condition.setDeviceType(pagination.getCondition().getDeviceType());
		condition.setMaterialCode(pagination.getCondition().getMaterialCode());
		condition.setMaterialName(pagination.getCondition().getMaterialName());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<STKWarningDevicesnDTO> pageResult = warningDevicesnMapper.pageWarningDevicesn(condition);
		if(null == pageResult || pageResult.isEmpty()){
			return null;
		}
		Date nowDate = JxcUtils.getNowDate();
		for(STKWarningDevicesnDTO snDto:pageResult){
			snDto.setUnActiveDays((int)((nowDate.getTime()-JxcUtils.getDateYmdFromString(snDto.getSendTime()).getTime())/Constants.MillSecondsInOneDay));
			snDto.setStayCurMerchantDays((int)((nowDate.getTime()-JxcUtils.getDateYmdFromString(snDto.getSendTime()).getTime())/Constants.MillSecondsInOneDay));
		}
		return pageResult;
	}

	public List<STKWarningDevicesnDTO> exportMerchantWarningDeviceSn(STKWarningDevicesnDTO stkWarningDevicesnDTO){
		logger.info("JXCTransferOrderService::exportWarningWaresale record::{}",stkWarningDevicesnDTO);
		//发送kafka异步执行
		ExportWarningDeviceSn exportWarningDeviceSn = generatorExportWarningDeviceSn(stkWarningDevicesnDTO);
		sendKafkaService.notifyMerchantWarningDeviceSn(stkWarningDevicesnDTO.getConsumer(),Constants.TASK_CFG_ID_STK_WARNING_DEVICESN, exportWarningDeviceSn);
		STKMerchantWarningDevicesn condition = new STKMerchantWarningDevicesn();
		condition.setWarningCode(stkWarningDevicesnDTO.getWarningCode());
		condition.setToMerchantCode(stkWarningDevicesnDTO.getToMerchantCode());
		condition.setToMerchantName(stkWarningDevicesnDTO.getToMerchantName());
		condition.setCurMerchantCode(stkWarningDevicesnDTO.getCurMerchantCode());
		condition.setCurMerchantName(stkWarningDevicesnDTO.getCurMerchantName());
		condition.setDeviceType(stkWarningDevicesnDTO.getDeviceType());
		condition.setMaterialCode(stkWarningDevicesnDTO.getMaterialCode());
		condition.setMaterialName(stkWarningDevicesnDTO.getMaterialName());
		List<STKWarningDevicesnDTO> stkWarningDevicesnDTOS = warningDevicesnMapper.exportWarningDevicesn(condition);
		if(null == stkWarningDevicesnDTOS || stkWarningDevicesnDTOS.isEmpty()){
			return null;
		}
		Date nowDate = JxcUtils.getNowDate();
		for(STKWarningDevicesnDTO snDto:stkWarningDevicesnDTOS){
			snDto.setUnActiveDays((int)((nowDate.getTime()-JxcUtils.getDateYmdFromString(snDto.getSendTime()).getTime())/Constants.MillSecondsInOneDay));
			snDto.setStayCurMerchantDays((int)((nowDate.getTime()-JxcUtils.getDateYmdFromString(snDto.getSendTime()).getTime())/Constants.MillSecondsInOneDay));
		}
		return stkWarningDevicesnDTOS;
	}


	private ExportWarningDeviceSn generatorExportWarningDeviceSn(STKWarningDevicesnDTO stkWarningDevicesnDTO)
	{
		ExportWarningDeviceSn exportWarningDeviceSn = new ExportWarningDeviceSn();
		exportWarningDeviceSn.setDeviceType(stkWarningDevicesnDTO.getDeviceType());
		exportWarningDeviceSn.setMaterialName(stkWarningDevicesnDTO.getMaterialName());
		exportWarningDeviceSn.setToMerchantName(stkWarningDevicesnDTO.getToMerchantName());
		exportWarningDeviceSn.setWarningCode(stkWarningDevicesnDTO.getWarningCode());
		return exportWarningDeviceSn;
	}
	
	public Page<STKWarningMaterialAssumeDTO> pageMerchantWarningMaterialAssume(RpcPagination<STKWarningMaterialAssumeDTO> pagination){
		STKMerchantWarningSlowMaterialAssume condition = new STKMerchantWarningSlowMaterialAssume();
		condition.setDeviceType(pagination.getCondition().getDeviceType());
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(pagination.getCondition().getChannelId()));
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		condition.setMerchantName(pagination.getCondition().getMerchantName());
		condition.setMaterialCode(pagination.getCondition().getMaterialCode());
		condition.setMaterialName(pagination.getCondition().getMaterialName());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<STKWarningMaterialAssumeDTO> pageResult = warningSlowMaterialAssumeMapper.pageMerchantWarningMaterialAssume(condition);
		if(null == pageResult || pageResult.isEmpty()){
			return null;
		}
		List<Integer> listChannelIds = pageResult.getResult().stream().map(STKWarningMaterialAssumeDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKWarningMaterialAssumeDTO assumeDto:pageResult.getResult()){
			if(assumeDto.getChannelId() != null){
				assumeDto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(assumeDto.getChannelId().byteValue())));
			}	
		}
		return pageResult;
	}
	
	public STKWarningDevTypeAssumeDTO getMerchantWarningDeviceTypeAssume(){
		STKWarningDevTypeAssumeDTO dtoResult = new STKWarningDevTypeAssumeDTO();
		Integer waringTotal = warningSlowCodeAssumeMapper.sumWarningDeviceTotal();
		if(null == waringTotal){
			waringTotal = 0;
		}
		List<STKWarningDevTypeDetailDTO> listDetailDto = warningSlowCodeAssumeMapper.groupWarningDeviceTotalByDeviceType();
		dtoResult.setWaringTotal(waringTotal);
		dtoResult.setListDetailDto(listDetailDto);
		return dtoResult;
	}
	
	public Page<STKWarningWaresaleDTO> pageWarningWaresale(RpcPagination<STKWarningWaresaleDTO> pagination){
		STKMerchantWarningWaresale condition = new STKMerchantWarningWaresale();
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(pagination.getCondition().getChannelId()));
		condition.setMerchantCode(pagination.getCondition().getMerchantCode());
		condition.setMerchantName(pagination.getCondition().getMerchantName());
		condition.setDeviceType(pagination.getCondition().getDeviceType());
		PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
		Page<STKWarningWaresaleDTO> pageResult = warningWaresaleMapper.pageWarningWaresale(condition);
		if(null == pageResult || pageResult.isEmpty()){
			return null;
		}
		if(pageResult.getResult() == null || pageResult.getResult().isEmpty()){
			return null;
		}
		List<Integer> listChannelIds = pageResult.getResult().stream().map(STKWarningWaresaleDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKWarningWaresaleDTO saleDto:pageResult.getResult()){
			if(saleDto.getChannelId() != null){
				saleDto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(saleDto.getChannelId().byteValue())));
			}	
		}
		return pageResult;
	}

	public List<STKWarningWaresaleDTO> exportWarningWaresale(STKWarningWaresaleDTO stkWarningWaresaleDTO){
		logger.info("JXCTransferOrderService::exportWarningWaresale record::{}",stkWarningWaresaleDTO);
		//发送kafka异步执行
		ExportMerchantWarningWaresale exportWarningWaresale = generatorExportWarningWaresale(stkWarningWaresaleDTO);
		sendKafkaService.notifyMerchantWarningWaresale(stkWarningWaresaleDTO.getConsumer(),Constants.TASK_CFG_ID_STK_WARNING_WARESALE, exportWarningWaresale);
		STKMerchantWarningWaresale condition = new STKMerchantWarningWaresale();
		condition.setMerchantChannelId(jxcmtAttribInfoService.getDbMerchantChannelFromAttribInfo(stkWarningWaresaleDTO.getChannelId()));
		condition.setMerchantCode(stkWarningWaresaleDTO.getMerchantCode());
		condition.setMerchantName(stkWarningWaresaleDTO.getMerchantName());
		condition.setDeviceType(stkWarningWaresaleDTO.getDeviceType());
		List<STKWarningWaresaleDTO> stkWarningWaresaleDTOS = warningWaresaleMapper.exportWarningWaresale(condition);
		if(null == stkWarningWaresaleDTOS || stkWarningWaresaleDTOS.isEmpty()){
			return null;
		}
		List<Integer> listChannelIds = stkWarningWaresaleDTOS.stream().map(STKWarningWaresaleDTO::getChannelId).collect(Collectors.toList());
		Map<Integer,String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
		for(STKWarningWaresaleDTO saleDto:stkWarningWaresaleDTOS){
			if(saleDto.getChannelId() != null){
				saleDto.setChannelName(mapMerchantChannels.get(jxcmtAttribInfoService.getMerchantChannelFromDbMerchantChannel(saleDto.getChannelId().byteValue())));
			}
		}
		return stkWarningWaresaleDTOS;
	}

	private ExportMerchantWarningWaresale generatorExportWarningWaresale(STKWarningWaresaleDTO warningWaresaleDTO)
	{
		ExportMerchantWarningWaresale exportMerchantWarningWaresale = new ExportMerchantWarningWaresale();
	    exportMerchantWarningWaresale.setChannelId(warningWaresaleDTO.getChannelId());
	    exportMerchantWarningWaresale.setDeviceType(warningWaresaleDTO.getDeviceType());
	    exportMerchantWarningWaresale.setMerchantName(warningWaresaleDTO.getMerchantName());
		return exportMerchantWarningWaresale;
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
