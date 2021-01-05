package cn.com.glsx.supplychain.jxc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.druid.util.StringUtils;

import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDeductionDTO;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDeductionDetailDTO;
import cn.com.glsx.supplychain.jxc.enums.MerchantStockDeductTypeEnum;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantStockLossMapper;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantStockReturnMapper;
import cn.com.glsx.supplychain.jxc.model.JXCAmKcWarehouseRelation;
import cn.com.glsx.supplychain.jxc.model.JXCMTMaterialInfo;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStockLoss;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStockReturn;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;

@Service
public class STKMerchantStockDeductService {

	private static final Logger logger = LoggerFactory.getLogger(STKMerchantStockDeductService.class);
	@Autowired
	private STKMerchantStockLossMapper stockLossMapper;
	@Autowired
	private STKMerchantStockReturnMapper stockReturnMapper;
	@Autowired
	private JXCMTMaterialInfoService materialInfoService;
	@Autowired
	private JXCAMKcRelationService kcRelationService;
	
	public Integer importMerchantStockDeduction(STKMerchantStockDeductionDTO condition){
		List<String> listMaterialCodes = condition.getListDetailDto().stream().map(STKMerchantStockDeductionDetailDTO::getMaterialCode).collect(Collectors.toList());
		List<String> listWarehouseCodes = condition.getListDetailDto().stream().map(STKMerchantStockDeductionDetailDTO::getWarehouseCode).collect(Collectors.toList());
		Map<String,JXCMTMaterialInfo> mapMaterialInfo =  materialInfoService.mapMaterialByListMaterialCode(listMaterialCodes);
		Map<String,JXCAmKcWarehouseRelation> mapWarehouseRelation = kcRelationService.mapKcWarehouseRelationByWarehouseCodes(listWarehouseCodes);
		if(condition.getTradeType().equals(MerchantStockDeductTypeEnum.TRADE_TYPE_LOSS.getValue())){
			List<STKMerchantStockLoss> listStockLoss = new ArrayList<>();
			STKMerchantStockLoss stockLoss = null;
			JXCMTMaterialInfo materialInfo = null;
			JXCAmKcWarehouseRelation houseRelation = null;
			for(STKMerchantStockDeductionDetailDTO dto:condition.getListDetailDto()){
				stockLoss = new STKMerchantStockLoss();
				stockLoss.setTradeDate(dto.getTradeDate());
				stockLoss.setTradeTotal(dto.getTradeTotal());
				materialInfo = mapMaterialInfo.get(dto.getMaterialCode());
				stockLoss.setDeviceType(materialInfo.getDeviceTypeId());
				stockLoss.setMaterialCode(dto.getMaterialCode());
				stockLoss.setMaterialName(dto.getMaterialName());
				houseRelation = mapWarehouseRelation.get(dto.getWarehouseCode());
				stockLoss.setMerchantCode(houseRelation.getMerchantCode());
				stockLoss.setRemark(dto.getRemark());
				stockLoss.setWarehouseCode(dto.getWarehouseCode());
				stockLoss.setCreatedBy(condition.getConsumer());
				stockLoss.setUpdatedBy(condition.getConsumer());
				stockLoss.setCreatedDate(JxcUtils.getNowDate());
				stockLoss.setUpdatedDate(JxcUtils.getNowDate());
				stockLoss.setDeletedFlag("N");
				listStockLoss.add(stockLoss);
				return this.AddMerchantStockDeductLoss(listStockLoss);
			}
		}else{
			List<STKMerchantStockReturn> listStockReturn = new ArrayList<>();
			STKMerchantStockReturn stockReturn = null;
			JXCMTMaterialInfo materialInfo = null;
			JXCAmKcWarehouseRelation houseRelation = null;
			for(STKMerchantStockDeductionDetailDTO dto:condition.getListDetailDto()){
				stockReturn = new STKMerchantStockReturn();
				stockReturn.setTradeDate(dto.getTradeDate());
				stockReturn.setTradeTotal(dto.getTradeTotal());
				materialInfo = mapMaterialInfo.get(dto.getMaterialCode());
				stockReturn.setDeviceType(materialInfo.getDeviceTypeId());
				stockReturn.setMaterialCode(dto.getMaterialCode());
				stockReturn.setMaterialName(dto.getMaterialName());
				houseRelation = mapWarehouseRelation.get(dto.getWarehouseCode());
				stockReturn.setMerchantCode(houseRelation.getMerchantCode());
				stockReturn.setRemark(dto.getRemark());
				stockReturn.setWarehouseCode(dto.getWarehouseCode());
				stockReturn.setCreatedBy(condition.getConsumer());
				stockReturn.setUpdatedBy(condition.getConsumer());
				stockReturn.setCreatedDate(JxcUtils.getNowDate());
				stockReturn.setUpdatedDate(JxcUtils.getNowDate());
				stockReturn.setDeletedFlag("N");
				listStockReturn.add(stockReturn);
				return this.AddMerchantStockDeductReturn(listStockReturn);
			}
		}
		return 0;
	}
	
	public STKMerchantStockDeductionDTO importMerchantStockDeductionCheck(STKMerchantStockDeductionDTO condition){
		List<String> listMaterialCodes = condition.getListDetailDto().stream().map(STKMerchantStockDeductionDetailDTO::getMaterialCode).collect(Collectors.toList());
		List<String> listWarehouseCodes = condition.getListDetailDto().stream().map(STKMerchantStockDeductionDetailDTO::getWarehouseCode).collect(Collectors.toList());
		
		Map<String,JXCMTMaterialInfo> mapMaterialInfo =  materialInfoService.mapMaterialByListMaterialCode(listMaterialCodes);
		Map<String,JXCAmKcWarehouseRelation> mapWarehouseRelation = kcRelationService.mapKcWarehouseRelationByWarehouseCodes(listWarehouseCodes);
		Map<String,STKMerchantStockDeductionDetailDTO> mapRepeateDetail = mapMerchantStockDeductionDetailRepeate(condition);
		List<STKMerchantStockDeductionDetailDTO> listSuccessDto = new ArrayList<>();
		List<STKMerchantStockDeductionDetailDTO> listFailedDto = new ArrayList<>();
		
		JXCMTMaterialInfo materialInfo = null;
		JXCAmKcWarehouseRelation warehouseRelation = null;
		STKMerchantStockDeductionDetailDTO repeatedDetailDto = null;
		for(STKMerchantStockDeductionDetailDTO dto:condition.getListDetailDto()){
			if(StringUtils.isEmpty(dto.getTradeDate()) || StringUtils.isEmpty(dto.getMaterialCode()) ||
					StringUtils.isEmpty(dto.getMaterialName()) || StringUtils.isEmpty(dto.getWarehouseCode()) ||
					dto.getTradeTotal() == null){
				dto.setResult("数据有必填字段为空，请检查");
				listFailedDto.add(dto);
				continue;
			}
			if(!JxcUtils.isValidTimeYMDFormat(dto.getTradeDate())){
				dto.setResult("交易日期格式不正确，参考格式:2020-11-09");
				listFailedDto.add(dto);
				continue;
			}
			materialInfo = mapMaterialInfo.get(dto.getMaterialCode());
			if(null == materialInfo){
				dto.setResult("物料库里面查不到该物料信息,请检查");
				listFailedDto.add(dto);
				continue;
			}
			warehouseRelation = mapWarehouseRelation.get(dto.getWarehouseCode());
			if(null == warehouseRelation){
				dto.setResult("仓库关系表中查不到该仓库信息,请检查");
				listFailedDto.add(dto);
				continue;
			}
			
			String strKey = "";
			strKey = "tp:" + condition.getTradeType();
			strKey += "td:";
			strKey += dto.getTradeDate();
			strKey += "mc:";
			strKey += dto.getMaterialCode();
			strKey += "mn:";
			strKey += dto.getMaterialName();
			strKey += "wc:";
			strKey += dto.getWarehouseCode();
			strKey += "tt:";
			strKey += dto.getTradeTotal();
			strKey += "rm:";
			strKey += dto.getRemark();
			repeatedDetailDto = mapRepeateDetail.get(strKey);
			if(null != repeatedDetailDto){
				dto.setResult("数据重复,请检查" );
				listFailedDto.add(dto);
				continue;
			}
			
			listSuccessDto.add(dto);
		}
		condition.setListSuccessDto(listSuccessDto);
		condition.setListFailedDto(listFailedDto);
		return condition;
	}
	
	private Map<String,STKMerchantStockDeductionDetailDTO> mapMerchantStockDeductionDetailRepeate(STKMerchantStockDeductionDTO condition){
		List<STKMerchantStockLoss> listMerchantStockLoss = null;
		List<STKMerchantStockReturn> listMerchantStockReturn = null;
		Map<String,STKMerchantStockDeductionDetailDTO> mapResult = new HashMap<>();
		List<String> listTradeDates = condition.getListDetailDto().stream().map(STKMerchantStockDeductionDetailDTO::getTradeDate).collect(Collectors.toList());
		if(condition.getTradeType().equals(MerchantStockDeductTypeEnum.TRADE_TYPE_LOSS.getValue())){
			listMerchantStockLoss = listStkMerchantStockLossByTradeDate(listTradeDates);
		}else{
			listMerchantStockReturn = listStkMerchantStockRetrunByTradeDate(listTradeDates);
		}
		STKMerchantStockDeductionDetailDTO detailDto = null;
		if(null != listMerchantStockLoss){
			for(STKMerchantStockLoss loss:listMerchantStockLoss){
				String strKey = "";
				strKey = "tp:" + MerchantStockDeductTypeEnum.TRADE_TYPE_LOSS.getValue();
				strKey += "td:";
				strKey += loss.getTradeDate();
				strKey += "mc:";
				strKey += loss.getMaterialCode();
				strKey += "mn:";
				strKey += loss.getMaterialName();
				strKey += "wc:";
				strKey += loss.getWarehouseCode();
				strKey += "tt:";
				strKey += loss.getTradeTotal();
				strKey += "rm:";
				strKey += loss.getRemark();
				detailDto = new STKMerchantStockDeductionDetailDTO();
				detailDto.setTradeDate(loss.getTradeDate());
				detailDto.setTradeTotal(loss.getTradeTotal());
				detailDto.setMaterialCode(loss.getMaterialCode());
				detailDto.setMaterialName(loss.getMaterialName());
				detailDto.setRemark(loss.getRemark());
				detailDto.setWarehouseCode(loss.getWarehouseCode());
				mapResult.put(strKey, detailDto);
			}
		}
		
		if(null != listMerchantStockReturn){
			for(STKMerchantStockReturn stockrt:listMerchantStockReturn){
				String strKey = "";
				strKey = "tp:" + MerchantStockDeductTypeEnum.TRADE_TYPE_LOSS.getValue();
				strKey += "td:";
				strKey += stockrt.getTradeDate();		
				strKey += "mc:";
				strKey += stockrt.getMaterialCode();
				strKey += "mn:";
				strKey += stockrt.getMaterialName();
				strKey += "wc:";
				strKey += stockrt.getWarehouseCode();
				strKey += "tt:";
				strKey += stockrt.getTradeTotal();
				strKey += "rm:";
				strKey += stockrt.getRemark();
				detailDto = new STKMerchantStockDeductionDetailDTO();
				detailDto.setTradeDate(stockrt.getTradeDate());
				detailDto.setTradeTotal(stockrt.getTradeTotal());
				detailDto.setMaterialCode(stockrt.getMaterialCode());
				detailDto.setMaterialName(stockrt.getMaterialName());
				detailDto.setRemark(stockrt.getRemark());
				detailDto.setWarehouseCode(stockrt.getWarehouseCode());
				mapResult.put(strKey, detailDto);
			}
		}
		
		Map<String,STKMerchantStockDeductionDetailDTO> mapRepeat = new HashMap<>();
		STKMerchantStockDeductionDetailDTO dtoRepeate = null;
		for(STKMerchantStockDeductionDetailDTO dto:condition.getListDetailDto()){
			String strKey = "";
			strKey = "tp:" + condition.getTradeType();
			strKey += "td:";
			strKey += dto.getTradeDate();
			strKey += "mc:";
			strKey += dto.getMaterialCode();
			strKey += "mn:";
			strKey += dto.getMaterialName();
			strKey += "wc:";
			strKey += dto.getWarehouseCode();
			strKey += "tt:";
			strKey += dto.getTradeTotal();
			strKey += "rm:";
			strKey += dto.getRemark();
			dtoRepeate = mapRepeat.get(strKey);
			if(null == dtoRepeate){
				mapRepeat.put(strKey, dto);
				continue;
			}
			mapResult.put(strKey, dto);
		}
		return mapResult;
	}
	
	private List<STKMerchantStockLoss> listStkMerchantStockLossByTradeDate(List<String> listTradeDates){
		if(null == listTradeDates || listTradeDates.isEmpty()){
			return null;
		}
		Example example = new Example(STKMerchantStockLoss.class);
		example.createCriteria().andIn("tradeDate", listTradeDates)
								.andEqualTo("deletedFlag", "N");
		return stockLossMapper.selectByExample(example);
	}
	
	private List<STKMerchantStockReturn> listStkMerchantStockRetrunByTradeDate(List<String> listTradeDates){
		if(null == listTradeDates || listTradeDates.isEmpty()){
			return null;
		}
		Example example = new Example(STKMerchantStockReturn.class);
		example.createCriteria().andIn("tradeDate", listTradeDates)
								.andEqualTo("deletedFlag", "N");
		return stockReturnMapper.selectByExample(example);
	}
	
	private Integer AddMerchantStockDeductLoss(List<STKMerchantStockLoss> listStockLoss){
		if(null == listStockLoss || listStockLoss.isEmpty()){
			return 0;
		}
		return stockLossMapper.insertList(listStockLoss);
	}
	

	
	private Integer AddMerchantStockDeductReturn(List<STKMerchantStockReturn> listStockReturn){
		if(null == listStockReturn || listStockReturn.isEmpty()){
			return 0;
		}
		return stockReturnMapper.insertList(listStockReturn);
	}
}
