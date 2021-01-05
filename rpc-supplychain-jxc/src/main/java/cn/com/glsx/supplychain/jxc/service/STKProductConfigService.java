package cn.com.glsx.supplychain.jxc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigDetailDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigQueryDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigSubmitDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantStockPeriodStatusEnum;
import cn.com.glsx.supplychain.jxc.enums.STKProductConfigMaterialTypeEnum;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantProductConfigDetailMapper;
import cn.com.glsx.supplychain.jxc.mapper.STKMerchantProductConfigMapper;
import cn.com.glsx.supplychain.jxc.model.STKMerchantProductConfig;
import cn.com.glsx.supplychain.jxc.model.STKMerchantProductConfigDetail;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;

@Service
public class STKProductConfigService {
	private static final Logger logger = LoggerFactory.getLogger(STKProductConfigService.class);
	@Autowired
	private STKMerchantProductConfigMapper productConfigMapper;
	@Autowired
	private STKMerchantProductConfigDetailMapper productConfigDetailMapper;
	@Autowired
	private JXCMTDeviceTypeService deviceTypeService;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer updateMerchantProductConfig(STKProductConfigSubmitDTO record)throws RpcServiceException{
		List<STKProductConfigDTO> listProductConfigDto = record.getListProductConfig();
		List<STKProductConfigDetailDTO> listProductConfigDetailDto = null;
		Integer salesMaterial = 0;
		Integer sendsMaterial = 0;
		Integer countMaterial = 0;
		String strKey = "";
		Map<String,Integer> mapMaterialRepeate = new HashMap<>();
		Map<String,String> mapKeyConfigCode = new HashMap<>();
		for(STKProductConfigDTO configDto:listProductConfigDto){
			if(configDto.getListConfigDetail() == null || configDto.getListConfigDetail().isEmpty()){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			salesMaterial = 0;
			sendsMaterial = 0;
			listProductConfigDetailDto = configDto.getListConfigDetail();
			strKey = "mc:" + configDto.getMerchantCode();
			for(STKProductConfigDetailDTO detailDto:listProductConfigDetailDto){
				if(StringUtils.isEmpty(detailDto.getMaterialCode()) ||
						StringUtils.isEmpty(detailDto.getMaterialName()) ||
						detailDto.getTotal() == null ||
						detailDto.getDeviceType() == null ||
						detailDto.getDeviceTypeName() == null ||
						detailDto.getTypeFlag() == null){
					throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				if(detailDto.getTypeFlag().equals(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SALES.getCode())){
					salesMaterial++;
					countMaterial = mapMaterialRepeate.get(detailDto.getMaterialCode());
					strKey += "ma:";
					strKey += detailDto.getMaterialCode();
					countMaterial = mapMaterialRepeate.get(strKey);
					if(countMaterial == null){
						countMaterial = 1;
					}else{
						countMaterial++;
					}
					mapMaterialRepeate.put(strKey, countMaterial);
					mapKeyConfigCode.put(strKey, detailDto.getConfigCode());
				}
				if(detailDto.getTypeFlag().equals(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SEND.getCode())){
					sendsMaterial++;
				}
			}
			if(salesMaterial == 0 || sendsMaterial == 0){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
		}
		for(Integer value:mapMaterialRepeate.values()){
			if(value > 1){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_PRODUCT_CONFIG_MATERIAL_REPEATED);
			}
		}
		List<String> listMerchantCodes = listProductConfigDto.stream().map(STKProductConfigDTO::getMerchantCode).collect(Collectors.toList());
		List<STKProductConfigDTO> listDBProductConfig = listProductConfigDtoByMerchantCodes(listMerchantCodes);
		for(STKProductConfigDTO dbDto:listDBProductConfig){
			strKey = "mc:" + dbDto.getMerchantCode();
			listProductConfigDetailDto = dbDto.getListConfigDetail();
			for(STKProductConfigDetailDTO detail:listProductConfigDetailDto){
				strKey += "ma:";
				strKey += detail.getMaterialCode();
				countMaterial = mapMaterialRepeate.get(strKey);
				if(countMaterial != null && !detail.getConfigCode().equals(mapKeyConfigCode.get(strKey)) && !dbDto.getOperateCode().equals(record.getOperateCode())){
					throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_PRODUCT_CONFIG_MATERIAL_REPEATED);
				}
			}
		}
		List<STKMerchantProductConfig> listMerchantProductConfig = new ArrayList<>();
		List<STKMerchantProductConfigDetail> listMerchantProductConfigDetail = new ArrayList<>();
		String operatorCode = record.getOperateCode();
		for(STKProductConfigDTO configDto:listProductConfigDto){
			String configCode = Constants.STKM_PRODUCTCONFIG_CONFIG_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
			listMerchantProductConfig.add(this.generateProductConfig(operatorCode, configCode, record.getConsumer(), configDto));
			listProductConfigDetailDto = configDto.getListConfigDetail();
			for(STKProductConfigDetailDTO detailDto:listProductConfigDetailDto){
				listMerchantProductConfigDetail.add(this.generateProductConfigDetail(configCode, record.getConsumer(), detailDto));
			}
		}
		try{
			this.deleteProductConfigByOperatorCode(record.getOperateCode(), record.getConsumer());
			productConfigMapper.insertList(listMerchantProductConfig);
			productConfigDetailMapper.insertList(listMerchantProductConfigDetail);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
		return 0;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer addMerchantProductConfig(STKProductConfigSubmitDTO record)throws RpcServiceException{
		List<STKProductConfigDTO> listProductConfigDto = record.getListProductConfig();
		List<STKProductConfigDetailDTO> listProductConfigDetailDto = null;
		Integer salesMaterial = 0;
		Integer sendsMaterial = 0;
		Integer countMaterial = 0;
		String strKey = "";
		Map<String,Integer> mapMaterialRepeate = new HashMap<>();
		for(STKProductConfigDTO configDto:listProductConfigDto){
			if(configDto.getListConfigDetail() == null || configDto.getListConfigDetail().isEmpty()){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
			salesMaterial = 0;
			sendsMaterial = 0;
			listProductConfigDetailDto = configDto.getListConfigDetail();
			strKey = "mc:" + configDto.getMerchantCode();
			for(STKProductConfigDetailDTO detailDto:listProductConfigDetailDto){
				if(StringUtils.isEmpty(detailDto.getMaterialCode()) ||
						StringUtils.isEmpty(detailDto.getMaterialName()) ||
						detailDto.getTotal() == null ||
						detailDto.getDeviceType() == null ||
						detailDto.getDeviceTypeName() == null ||
						detailDto.getTypeFlag() == null){
					throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
				}
				if(detailDto.getTypeFlag().equals(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SALES.getCode())){
					salesMaterial++;
					countMaterial = mapMaterialRepeate.get(detailDto.getMaterialCode());
					strKey += "ma:";
					strKey += detailDto.getMaterialCode();
					countMaterial = mapMaterialRepeate.get(strKey);
					if(countMaterial == null){
						countMaterial = 1;
					}else{
						countMaterial++;
					}
					mapMaterialRepeate.put(strKey, countMaterial);
				}
				if(detailDto.getTypeFlag().equals(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SEND.getCode())){
					sendsMaterial++;
				}
			}
			if(salesMaterial == 0 || sendsMaterial == 0){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM);
			}
		}
		for(Integer value:mapMaterialRepeate.values()){
			if(value > 1){
				throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_PRODUCT_CONFIG_MATERIAL_REPEATED);
			}
		}
		List<String> listMerchantCodes = listProductConfigDto.stream().map(STKProductConfigDTO::getMerchantCode).collect(Collectors.toList());
		List<STKProductConfigDTO> listDBProductConfig = listProductConfigDtoByMerchantCodes(listMerchantCodes);
		for(STKProductConfigDTO dbDto:listDBProductConfig){
			strKey = "mc:" + dbDto.getMerchantCode();
			listProductConfigDetailDto = dbDto.getListConfigDetail();
			for(STKProductConfigDetailDTO detail:listProductConfigDetailDto){
				strKey += "ma:";
				strKey += detail.getMaterialCode();
				countMaterial = mapMaterialRepeate.get(strKey);
				if(countMaterial != null){
					throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_PRODUCT_CONFIG_MATERIAL_REPEATED);
				}
			}
		}
		List<STKMerchantProductConfig> listMerchantProductConfig = new ArrayList<>();
		List<STKMerchantProductConfigDetail> listMerchantProductConfigDetail = new ArrayList<>();
		String operatorCode = Constants.STKM_PRODUCTCONFIG_OPERATOR_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
		for(STKProductConfigDTO configDto:listProductConfigDto){
			String configCode = Constants.STKM_PRODUCTCONFIG_CONFIG_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
			listMerchantProductConfig.add(this.generateProductConfig(operatorCode, configCode, record.getConsumer(), configDto));
			listProductConfigDetailDto = configDto.getListConfigDetail();
			for(STKProductConfigDetailDTO detailDto:listProductConfigDetailDto){
				listMerchantProductConfigDetail.add(this.generateProductConfigDetail(configCode, record.getConsumer(), detailDto));
			}
		}
		try{
			productConfigMapper.insertList(listMerchantProductConfig);
			productConfigDetailMapper.insertList(listMerchantProductConfigDetail);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
		return 0;
	}
	
	
	public List<STKProductConfigDTO> getMerchantProductConfigByOperatorCode(STKProductConfigDTO record){
		List<STKProductConfigDTO> listResult = new ArrayList<>();
		List<STKMerchantProductConfig> listProductConfig = listProductConfigByOperatorCode(record.getOperateCode());
		if(null == listProductConfig || listProductConfig.isEmpty()){
			return listResult;
		}
		List<String> listConfigCodes = listProductConfig.stream().map(STKMerchantProductConfig::getConfigCode).collect(Collectors.toList());
		Map<String,List<STKProductConfigDetailDTO>> mapProductConfigDetail = mapProductConfigDetailDto(listConfigCodes);
		STKProductConfigDTO configDto = null;
		for(STKMerchantProductConfig config:listProductConfig){
			configDto = new STKProductConfigDTO();
			configDto.setConfigCode(config.getConfigCode());
			configDto.setMerchantCode(config.getMerchantCode());
			configDto.setMerchantName(config.getMerchantName());
			configDto.setOperateCode(config.getOperateCode());
			configDto.setPeriodEnd(JxcUtils.getStringYmdFromDate(config.getPeriodEnd()));
			configDto.setPeriodStart(JxcUtils.getStringYmdFromDate(config.getPeriodStart()));
			configDto.setPeriodStatus(config.getPeriodStatus());
			configDto.setPeriodStatusName(getPeriodStatusName(config.getPeriodStatus()));
			configDto.setWarehouseCode(config.getWarehouseCode());
			configDto.setWarehouseName(config.getWarehouseName());
			configDto.setListConfigDetail(mapProductConfigDetail.get(config.getConfigCode()));
			listResult.add(configDto);
		}
		return listResult;
	}
	
	public Page<STKProductConfigDTO> pageMerchantProductConfig(STKProductConfigQueryDTO record){
		STKMerchantProductConfig condition = new STKMerchantProductConfig();
		condition.setMerchantCode(record.getMerchantCode());
		condition.setPeriodStatus(record.getPeriodStatus());
		condition.setMaterialCode(record.getMaterialCode());
		condition.setMaterialName(record.getMaterialName());
		condition.setDeletedFlag("N");
		PageHelper.startPage(record.getPageNum(), record.getPageSize());
		Page<STKProductConfigDTO> pageResult = productConfigMapper.pageMerchantProductConfig(condition);
		if(null == pageResult || pageResult.isEmpty()){
			return null;
		}
		List<STKProductConfigDTO> listResult = pageResult.getResult();
		if(null == listResult || listResult.isEmpty()){
			return null;
		}
		List<String> listConfigCodes = listResult.stream().map(STKProductConfigDTO::getConfigCode).collect(Collectors.toList());
		Map<String,List<STKProductConfigDetailDTO>> mapProductConfigDetail = mapProductConfigDetailDto(listConfigCodes);
		for(STKProductConfigDTO dto:listResult){
			dto.setPeriodStatusName(getPeriodStatusName(dto.getPeriodStatus()));
			dto.setListConfigDetail(mapProductConfigDetail.get(dto.getConfigCode()));
		}
		return pageResult;
	}
	
	private Integer deleteProductConfigByOperatorCode(String operatorCode,String consumer){
		List<STKMerchantProductConfig> listProductConfig = this.listProductConfigByOperatorCode(operatorCode);
		if(null == listProductConfig || listProductConfig.isEmpty()){
			return 0;
		}
		List<String> listConfigCodes = listProductConfig.stream().map(STKMerchantProductConfig::getConfigCode).collect(Collectors.toList());
		deleteProductConfigByConfigCodes(listConfigCodes,consumer);
		deleteProductConfigDetailByConfigCodes(listConfigCodes,consumer);
		return 0;
	}
	
	private Integer deleteProductConfigByConfigCodes(List<String> listConfigCodes,String consumer){
		if(null == listConfigCodes || listConfigCodes.isEmpty()){
			return 0;
		}
		STKMerchantProductConfig productConfig = new STKMerchantProductConfig();
		productConfig.setDeletedFlag("Y");
		productConfig.setUpdatedBy(consumer);
		productConfig.setUpdatedDate(JxcUtils.getNowDate());
		Example example = new Example(STKMerchantProductConfig.class);
		example.createCriteria().andIn("configCode", listConfigCodes);
		return productConfigMapper.updateByExampleSelective(productConfig, example);
	}
	
	private Integer deleteProductConfigDetailByConfigCodes(List<String> listConfigCodes,String consumer){
		if(null == listConfigCodes || listConfigCodes.isEmpty()){
			return 0;
		}
		STKMerchantProductConfigDetail configDetail = new STKMerchantProductConfigDetail();
		configDetail.setDeletedFlag("Y");
		configDetail.setUpdatedBy(consumer);
		configDetail.setUpdatedDate(JxcUtils.getNowDate());
		Example example = new Example(STKMerchantProductConfigDetail.class);
		example.createCriteria().andIn("configCode", listConfigCodes);
		return productConfigDetailMapper.updateByExampleSelective(configDetail, example);
	}
	
	private STKMerchantProductConfig generateProductConfig(String operatorCode,String configCode,String consumer,STKProductConfigDTO dtoObject){
		Date dateNow = JxcUtils.getNowDate();
		STKMerchantProductConfig config = new STKMerchantProductConfig();
		config.setOperateCode(operatorCode);
		config.setConfigCode(configCode);
		config.setMerchantCode(dtoObject.getMerchantCode());
		config.setMerchantName(dtoObject.getMerchantName());
		config.setWarehouseCode(dtoObject.getWarehouseCode());
		config.setWarehouseName(dtoObject.getWarehouseName());
		config.setPeriodStart(JxcUtils.getDateYmdFromString(dtoObject.getPeriodStart()));
		config.setPeriodEnd(JxcUtils.getDateYmdFromString(dtoObject.getPeriodEnd()));
		String periodStatus = MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_PRE.getCode();
		if(dateNow.before(config.getPeriodStart())){
			periodStatus = MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_PRE.getCode(); 
		}else if(dateNow.after(config.getPeriodStart()) && dateNow.before(config.getPeriodEnd())){
			periodStatus = MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_CUR.getCode(); 
		}else{
			periodStatus = MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_NEX.getCode();
		}
		config.setPeriodStatus(periodStatus);
		config.setCreatedBy(consumer);
		config.setUpdatedBy(consumer);
		config.setCreatedDate(dateNow);
		config.setUpdatedDate(dateNow);
		config.setDeletedFlag("N");
		return config;
	}
	
	private STKMerchantProductConfigDetail generateProductConfigDetail(String configCode,String consumer,STKProductConfigDetailDTO dtoObject){
		STKMerchantProductConfigDetail detail = new STKMerchantProductConfigDetail();
		detail.setConfigCode(configCode);
		detail.setDeviceType(dtoObject.getDeviceType());
		detail.setMaterialCode(dtoObject.getMaterialCode());
		detail.setMaterialName(dtoObject.getMaterialName());
		detail.setTotal(dtoObject.getTotal());
		detail.setTypeFlag(dtoObject.getTypeFlag());
		detail.setCreatedBy(consumer);
		detail.setUpdatedBy(consumer);
		detail.setCreatedDate(JxcUtils.getNowDate());
		detail.setUpdatedDate(JxcUtils.getNowDate());
		detail.setDeletedFlag("N");
		return detail;
	}
	
	private List<STKProductConfigDTO> listProductConfigDtoByMerchantCodes(List<String> listMerchantCodes){
		List<STKProductConfigDTO> listResult = new ArrayList<>();
		List<STKMerchantProductConfig> listProductConfig = listProductConfigByMerchantCodes(listMerchantCodes);
		if(null == listProductConfig || listProductConfig.isEmpty()){
			return listResult;
		}
		List<String> listConfigCodes = listProductConfig.stream().map(STKMerchantProductConfig::getConfigCode).collect(Collectors.toList());
		Map<String,List<STKProductConfigDetailDTO>> mapConfigDetailDto = mapProductConfigDetailDto(listConfigCodes);
		STKProductConfigDTO configDto = null;
		for(STKMerchantProductConfig config:listProductConfig){
			configDto = new STKProductConfigDTO();
			configDto.setConfigCode(config.getConfigCode());
			configDto.setMerchantCode(config.getMerchantCode());
			configDto.setMerchantName(config.getMerchantName());
			configDto.setOperateCode(config.getOperateCode());
			configDto.setPeriodEnd(JxcUtils.getStringYmdFromDate(config.getPeriodEnd()));
			configDto.setPeriodStart(JxcUtils.getStringYmdFromDate(config.getPeriodStart()));
			configDto.setPeriodStatus(config.getPeriodStatus());
			configDto.setPeriodStatusName(getPeriodStatusName(config.getPeriodStatus()));
			configDto.setWarehouseCode(config.getWarehouseCode());
			configDto.setWarehouseName(config.getWarehouseName());
			configDto.setListConfigDetail(mapConfigDetailDto.get(config.getConfigCode()));
			listResult.add(configDto);
		}
		return listResult;
	}
	
	private List<STKMerchantProductConfig> listProductConfigByMerchantCodes(List<String> listMerchantCodes){
		Example example = new Example(STKMerchantProductConfig.class);
		example.createCriteria().andIn("merchantCode", listMerchantCodes)
								.andEqualTo("deletedFlag", "N");
		return productConfigMapper.selectByExample(example);
	}
	
	private List<STKMerchantProductConfig> listProductConfigByOperatorCode(String operatorCode){
		Example example = new Example(STKMerchantProductConfig.class);
		example.createCriteria().andEqualTo("operateCode", operatorCode)
								.andEqualTo("deletedFlag", "N");
		return productConfigMapper.selectByExample(example);
	}
	
	private Map<String,List<STKProductConfigDetailDTO>> mapProductConfigDetailDto(List<String> listConfigCodes){
		Map<String,List<STKProductConfigDetailDTO>> mapResult = new HashMap<>();
		if(null == listConfigCodes || listConfigCodes.isEmpty()){
			return mapResult;
		}
		Example example = new Example(STKMerchantProductConfigDetail.class);
		example.createCriteria().andIn("configCode", listConfigCodes)
								.andEqualTo("deletedFlag", "N");
		List<STKMerchantProductConfigDetail> listDetail = productConfigDetailMapper.selectByExample(example);
		if(null == listDetail || listDetail.isEmpty()){
			return mapResult;
		}
		List<STKProductConfigDetailDTO> listDetailDto = null;
		STKProductConfigDetailDTO detailDto = null;
		for(STKMerchantProductConfigDetail detail:listDetail){
			listDetailDto = mapResult.get(detail.getConfigCode());
			if(null == listDetailDto){
				listDetailDto = new ArrayList<>();
				mapResult.put(detail.getConfigCode(), listDetailDto);
			}
			detailDto = new STKProductConfigDetailDTO();
			detailDto.setConfigCode(detail.getConfigCode());
			detailDto.setDeviceType(detail.getDeviceType());
			detailDto.setDeviceTypeName(deviceTypeService.getDeviceTypeNameById(detail.getDeviceType(), null));
			detailDto.setMaterialCode(detail.getMaterialCode());
			detailDto.setMaterialName(detail.getMaterialName());
			detailDto.setTotal(detail.getTotal());
			detailDto.setTypeFlag(detail.getTypeFlag());
			listDetailDto.add(detailDto);
		}
		return mapResult;
	}
	
	private String getPeriodStatusName(String periodStatus){
		if(StringUtils.isEmpty(periodStatus)){
			return "";
		}
		if(periodStatus.equals(MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_PRE.getCode())){
			return MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_PRE.getName();
		}
		if(periodStatus.equals(MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_CUR.getCode())){
			return MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_CUR.getName();
		}
		if(periodStatus.equals(MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_NEX.getCode())){
			return MerchantStockPeriodStatusEnum.STK_PRODUCT_CONFIG_PERIOD_NEX.name();
		}
		return "";
	}
	
}
