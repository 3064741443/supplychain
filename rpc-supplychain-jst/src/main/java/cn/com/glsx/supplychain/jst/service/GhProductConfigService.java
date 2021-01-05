package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.dto.gh.AttribInfoDTO;
import cn.com.glsx.supplychain.jst.dto.gh.AudiMotorcycleDTO;
import cn.com.glsx.supplychain.jst.dto.gh.GhProductConfigDTO;
import cn.com.glsx.supplychain.jst.dto.gh.GhProductConfigOtherDTO;
import cn.com.glsx.supplychain.jst.enums.ShProductConfigOptionEnum;
import cn.com.glsx.supplychain.jst.mapper.GhProductConfigMapper;
import cn.com.glsx.supplychain.jst.mapper.GhProductConfigOtherMapper;
import cn.com.glsx.supplychain.jst.model.AttribInfo;
import cn.com.glsx.supplychain.jst.model.GhMerchantBrand;
import cn.com.glsx.supplychain.jst.model.GhProductConfig;
import cn.com.glsx.supplychain.jst.model.GhProductConfigOther;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GhProductConfigService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GhProductConfigService.class);
	@Autowired
	private GhProductConfigMapper productConfigMapper;
	@Autowired
	private GhProductConfigOtherMapper productConfigOtherMapper;
	@Autowired
	private GhMerchantBrandService merchantBrandService;
	@Autowired
	private AttribInfoService attribInfoService;
	
	public List<AttribInfo> listParentBrands(){
		List<AttribInfo> listResult = new ArrayList<>();
		List<GhProductConfig> listProductConfigs = productConfigMapper.listDistinctParentBrands();
		if(null == listProductConfigs || listProductConfigs.isEmpty()){
			return listResult;
		}
		for(GhProductConfig config:listProductConfigs){
			listResult.add(attribInfoService.getAttribInfo(config.getParentBrandId()));
		}
		return listResult;
	}
	
	public List<AttribInfo> listSubBrands(GhProductConfigDTO record){
		List<AttribInfo> listResult = new ArrayList<>();
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(record.getParentBrandId());
		List<GhProductConfig> listProductConfigs = productConfigMapper.listDistinctSubBrands(condition);
		if(null == listProductConfigs || listProductConfigs.isEmpty()){
			return listResult;
		}
		for(GhProductConfig config:listProductConfigs){
			listResult.add(attribInfoService.getAttribInfo(config.getSubBrandId()));
		}
		return listResult;
	}
	
	public List<AttribInfo> listAudis(GhProductConfigDTO record){
		List<AttribInfo> listResult = new ArrayList<>();
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(record.getParentBrandId());
		condition.setSubBrandId(record.getSubBrandId());
		List<GhProductConfig> listProductConfigs = productConfigMapper.listDistinctAudis(condition);
		if(null == listProductConfigs || listProductConfigs.isEmpty()){
			return listResult;
		}
		for(GhProductConfig config:listProductConfigs){
			listResult.add(attribInfoService.getAttribInfo(config.getAudiId()));
		}
		return listResult;
	}
	
	public List<AttribInfo> listMotorcles(GhProductConfigDTO record){
		List<AttribInfo> listResult = new ArrayList<>();
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(record.getParentBrandId());
		condition.setSubBrandId(record.getSubBrandId());
		condition.setAudiId(record.getAudiId());
		List<GhProductConfig> listProductConfigs = productConfigMapper.listDistinctMotorcyles(condition);
		if(null == listProductConfigs || listProductConfigs.isEmpty()){
			return listResult;
		}
		AttribInfo attribInfo = null;
		for(GhProductConfig config:listProductConfigs){
			attribInfo = new AttribInfo();
			attribInfo.setName(config.getMotorcycle());
			listResult.add(attribInfo);
		}
		return listResult;
	}
	
	public Page<AudiMotorcycleDTO> pageAudiMotorcycle(Integer pageNo, Integer pageSize, AudiMotorcycleDTO dtoRecord){
		boolean merchantSupport = false;
		Page<AudiMotorcycleDTO> pageResult = new Page<AudiMotorcycleDTO>();
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		List<GhMerchantBrand> listMerchantBrand = merchantBrandService.listMerchantBrand(dtoRecord.getMerchantCode());
		//如果商户下车品牌为空  默认获取全部品牌 否则获取商户支持的车系车型
		List<Integer> listParentBrandIds = new ArrayList<>();
		List<Integer> listSubBrandIds = new ArrayList<>();
		if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
			for(GhMerchantBrand item:listMerchantBrand){
				if(item.getParentBrandId() != 0){
					listParentBrandIds.add(item.getParentBrandId());
				}
				if(item.getSubBrandId() != 0){
					listSubBrandIds.add(item.getSubBrandId());
				}	
			}
		}
		PageHelper.startPage(pageNo,pageSize);
		Page<GhProductConfig> dbResult= null;
		if(!listSubBrandIds.isEmpty()){
			merchantSupport = true;
			dbResult = productConfigMapper.pageProductConfigBySubBrands(listSubBrandIds);
		}else if(!listParentBrandIds.isEmpty()){
			merchantSupport = true;
			dbResult = productConfigMapper.pageProductConfigByParentBrands(listParentBrandIds);
		}else{
			merchantSupport = false;
			dbResult = productConfigMapper.pageProductConfig();
		}
		String uniqueKey = "";
		AudiMotorcycleDTO audiMotorcycleDto = null;
		List<String> listMotocyleDto = null;
		Map<String,Integer> mapMotocycleDto = null;
		Map<String,AudiMotorcycleDTO> mapAudiMotorcycleDto = new HashMap<>();
		if(null != dbResult && null != dbResult.getResult() && !dbResult.getResult().isEmpty()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				uniqueKey = "p"+productConfig.getParentBrandId() + "s" + productConfig.getSubBrandId() + "a" +  productConfig.getAudiId();
				audiMotorcycleDto = mapAudiMotorcycleDto.get(uniqueKey);
				if(null == audiMotorcycleDto){
					audiMotorcycleDto = new AudiMotorcycleDTO();
					audiMotorcycleDto.setParentBrandId(productConfig.getParentBrandId());
					audiMotorcycleDto.setSubBrandId(productConfig.getSubBrandId());
					audiMotorcycleDto.setAudiId(productConfig.getAudiId());
					if(merchantSupport){
						audiMotorcycleDto.setMerchantCode(dtoRecord.getMerchantCode());
					}
					
					audiMotorcycleDto.setParentBrandName(getAttribInfoName(productConfig.getParentBrandId(),mapAttribInfo));
					audiMotorcycleDto.setSubBrandName(getAttribInfoName(productConfig.getSubBrandId(),mapAttribInfo));
					audiMotorcycleDto.setAudiName(getAttribInfoName(productConfig.getAudiId(),mapAttribInfo));
					listMotocyleDto = new ArrayList<>();
					mapMotocycleDto = new HashMap<>();
					audiMotorcycleDto.setListMotocyleDto(listMotocyleDto);
					audiMotorcycleDto.setMapMotocycleDto(mapMotocycleDto);
					mapAudiMotorcycleDto.put(uniqueKey, audiMotorcycleDto);
					pageResult.add(audiMotorcycleDto);
				}
				if(null == audiMotorcycleDto.getMapMotocycleDto().get(productConfig.getMotorcycle())){
					audiMotorcycleDto.getListMotocyleDto().add(productConfig.getMotorcycle());
					audiMotorcycleDto.getMapMotocycleDto().put(productConfig.getMotorcycle(), 1);
				}	
			}
			pageResult.setPageNum(dbResult.getPageNum());
			pageResult.setPages(dbResult.getPages());
			pageResult.setPageSize(dbResult.getPageSize());
			pageResult.setTotal(dbResult.getTotal());
		}
		return pageResult;
	}
	
	public Page<GhProductConfigDTO> pageGhProductConfig(Integer pageNo, Integer pageSize,GhProductConfigDTO dtoRecord){
		LOGGER.info("GhProductConfigService::pageGhProductConfig dtoRecord::{}",dtoRecord);
		List<String> productConfigCodes = new ArrayList<>();
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		Map<String,List<GhProductConfigOther>> mapConfigOthers = null;
		List<GhProductConfigOther> listOthers = null;
		GhProductConfigDTO productConfigDto = null;
		Page<GhProductConfigDTO> pageResult = new Page<GhProductConfigDTO>();
		PageHelper.startPage(pageNo,pageSize);
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(dtoRecord.getParentBrandId());
		condition.setSubBrandId(dtoRecord.getSubBrandId());
		condition.setCategoryId(dtoRecord.getCategoryId());
		condition.setAudiId(dtoRecord.getAudiId());	
		condition.setMotorcycle(dtoRecord.getMotorcycle());
		condition.setSpaProductCode(dtoRecord.getSpaProductCode());
		Page<GhProductConfig> dbResult = null;
		//如果未指定查询商户支持的
		/*Integer merchantBrandCount = merchantBrandService.countMerchantBrand(dtoRecord.getMerchantCode());
		LOGGER.info("GhProductConfigService::pageGhProductConfig merchantBrandCount::{}",merchantBrandCount);
		if(!StringUtils.isEmpty(dtoRecord.getMerchantCode()) && (null == condition.getParentBrandId()) && merchantBrandCount > 0){
			condition.setMerchantCode(dtoRecord.getMerchantCode());
			dbResult = productConfigMapper.pageGhProductConfigWithDistinctProductCodeWithMerchantCode(condition);
		}else{
			dbResult = productConfigMapper.pageGhProductConfigWithDistinctProductCode(condition);
		}*/
		
		
		List<GhMerchantBrand> listMerchantBrand = merchantBrandService.listMerchantBrand(dtoRecord.getMerchantCode());
		//如果商户下车品牌为空  默认获取全部品牌 否则获取商户支持的车系车型
		List<Integer> listParentBrandIds = new ArrayList<>();
		List<Integer> listSubBrandIds = new ArrayList<>();
		listParentBrandIds.add(Constants.PRODUCT_CONFIG_PARENT_BRAND_TONGYONG);
		listSubBrandIds.add(Constants.PRODUCT_CONFIG_SUB_BRAND_TONGYONG);
		if(null != condition.getParentBrandId()){
			listParentBrandIds.add(condition.getParentBrandId());
			condition.setListParentBrandIds(listParentBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getParentBrandId() != 0){
						listParentBrandIds.add(item.getParentBrandId());
					}
				}
				condition.setListParentBrandIds(listParentBrandIds);
			}
		}
		if(null != condition.getSubBrandId()){
			listSubBrandIds.add(condition.getSubBrandId());
			condition.setListSubBrandIds(listSubBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getSubBrandId() != 0){
						listSubBrandIds.add(item.getSubBrandId());
					}
				}
				condition.setListSubBrandIds(listSubBrandIds);
			}
		}

		dbResult = productConfigMapper.pageGhProductConfigWithDistinctProductCode(condition);
		
		if(null != dbResult && null != dbResult.getResult()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				productConfigCodes.add(productConfig.getProductConfigCode());
			}
		}
		List<GhProductConfigOther> listProductConfigOthers = listGhproductConfigOther(ShProductConfigOptionEnum.OPTION_ELECTI.getValue(),productConfigCodes);
		if(null != listProductConfigOthers){
			mapConfigOthers = listProductConfigOthers.stream().collect(Collectors.groupingBy(GhProductConfigOther::getProductConfigCode));
		}
		//补充 方便后面操作不需要再判断
		if(null == mapConfigOthers){
			mapConfigOthers = new HashMap<>();
		}
		
		if(null != dbResult && null != dbResult.getResult()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				productConfigDto = new GhProductConfigDTO();
				productConfigDto.setAudiId(productConfig.getAudiId());
				productConfigDto.setAudiName(getAttribInfoName(productConfig.getAudiId(),mapAttribInfo));
				productConfigDto.setCategoryId(productConfig.getCategoryId());
				productConfigDto.setCategoryName(getAttribInfoName(productConfig.getCategoryId(),mapAttribInfo));
				productConfigDto.setGlsxProductCode(productConfig.getGlsxProductCode());
				productConfigDto.setGlsxProductName(productConfig.getGlsxProductName());
				productConfigDto.setMerchantCode(dtoRecord.getMerchantCode());
				productConfigDto.setModelYearId(productConfig.getModelYearId());
				productConfigDto.setModelYearName(getAttribInfoName(productConfig.getModelYearId(),mapAttribInfo));
				productConfigDto.setMotorcycle(productConfig.getMotorcycle());
				productConfigDto.setParentBrandId(productConfig.getParentBrandId());
				productConfigDto.setParentBrandName(getAttribInfoName(productConfig.getParentBrandId(),mapAttribInfo));
				productConfigDto.setProductConfigCode(productConfig.getProductConfigCode());
				productConfigDto.setSpaProductCode(productConfig.getSpaProductCode());
				productConfigDto.setSpaProductName(productConfig.getSpaProductName());
				productConfigDto.setSubBrandId(productConfig.getSubBrandId());
				productConfigDto.setSubBrandName(getAttribInfoName(productConfig.getSubBrandId(),mapAttribInfo));
				if(!StringUtils.isEmpty(productConfigDto.getMotorcycle()) && !productConfigDto.getMotorcycle().equals("通用")){
					productConfigDto.setDisplayConfig("Y");
				}else{
					listOthers = mapConfigOthers.get(productConfig.getProductConfigCode());
					if(null != listOthers && !listOthers.isEmpty()){
						productConfigDto.setDisplayConfig("C");
					}else{
						productConfigDto.setDisplayConfig("N");
					}
				}
				
				/*if(productConfigDto.getCategoryId().equals(Constants.PRODUCT_CONFIG_CATEGORY_CZDH)){
					productConfigDto.setDisplayConfig("Y");
				}else{
					listOthers = mapConfigOthers.get(productConfig.getProductConfigCode());
					if(null != listOthers && !listOthers.isEmpty()){
						productConfigDto.setDisplayConfig("Y");
					}else{
						productConfigDto.setDisplayConfig("N");
					}
				}*/
				pageResult.add(productConfigDto);
			}
			pageResult.setPageNum(dbResult.getPageNum());
			pageResult.setPages(dbResult.getPages());
			pageResult.setPageSize(dbResult.getPageSize());
			pageResult.setTotal(dbResult.getTotal());
		}
		LOGGER.info("GhProductConfigService::pageGhProductConfig end pageResult::{}",pageResult);
		return pageResult;
	}
	
	public List<AttribInfoDTO> listGhProductConfigModeYears(GhProductConfigDTO record){
		AttribInfoDTO attribInfoDto = null;
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		List<AttribInfoDTO> listResultDto = new ArrayList<>();
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(record.getParentBrandId());
		condition.setSubBrandId(record.getSubBrandId());
		condition.setCategoryId(record.getCategoryId());
		condition.setAudiId(record.getAudiId());
		condition.setMotorcycle(record.getMotorcycle());
		condition.setSpaProductCode(record.getSpaProductCode());
		condition.setGlsxProductCode(record.getGlsxProductCode());
		List<GhProductConfig> listProductConfigs = null;
		
		//如果未指定查询商户支持的
		/*Integer merchantBrandCount = merchantBrandService.countMerchantBrand(record.getMerchantCode());
		if(!StringUtils.isEmpty(record.getMerchantCode()) && (null == condition.getParentBrandId()) && merchantBrandCount > 0){
			condition.setMerchantCode(record.getMerchantCode());
			listProductConfigs = productConfigMapper.listGhProductConfigModeYearsWithMerchantCode(condition);
		}else{
			listProductConfigs = productConfigMapper.listGhProductConfigModeYears(condition);
		}*/
		List<GhMerchantBrand> listMerchantBrand = merchantBrandService.listMerchantBrand(record.getMerchantCode());
		//如果商户下车品牌为空  默认获取全部品牌 否则获取商户支持的车系车型
		List<Integer> listParentBrandIds = new ArrayList<>();
		List<Integer> listSubBrandIds = new ArrayList<>();
		listParentBrandIds.add(Constants.PRODUCT_CONFIG_PARENT_BRAND_TONGYONG);
		listSubBrandIds.add(Constants.PRODUCT_CONFIG_SUB_BRAND_TONGYONG);
		if(null != condition.getParentBrandId()){
			listParentBrandIds.add(condition.getParentBrandId());
			condition.setListParentBrandIds(listParentBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getParentBrandId() != 0){
						listParentBrandIds.add(item.getParentBrandId());
					}
				}
				condition.setListParentBrandIds(listParentBrandIds);
			}
		}
		if(null != condition.getSubBrandId()){
			listSubBrandIds.add(condition.getSubBrandId());
			condition.setListSubBrandIds(listSubBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getSubBrandId() != 0){
						listSubBrandIds.add(item.getSubBrandId());
					}
				}
				condition.setListSubBrandIds(listSubBrandIds);
			}
		}
		
		listProductConfigs = productConfigMapper.listGhProductConfigModeYears(condition);
			
		if(null != listProductConfigs && !listProductConfigs.isEmpty()){
			for(GhProductConfig productConfig:listProductConfigs){
				attribInfoDto = new  AttribInfoDTO();
				attribInfoDto.setId(productConfig.getModelYearId());
				attribInfoDto.setName(this.getAttribInfoName(productConfig.getModelYearId(), mapAttribInfo));
				attribInfoDto.setValue(this.getAttribInfoValue(productConfig.getModelYearId(), mapAttribInfo));
				listResultDto.add(attribInfoDto);
			}
		}
		if(!listResultDto.isEmpty()){
			return listResultDto.stream().sorted(Comparator.comparing(AttribInfoDTO::getValue).reversed()).collect(Collectors.toList());	
		}else{
			return listResultDto;
		}
	}
	
	public List<AttribInfoDTO> listGhProductConfigAudis(GhProductConfigDTO record){
		AttribInfoDTO attribInfoDto = null;
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		List<AttribInfoDTO> listResultDto = new ArrayList<>();
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(record.getParentBrandId());
		condition.setSubBrandId(record.getSubBrandId());
		condition.setCategoryId(record.getCategoryId());
		condition.setAudiId(record.getAudiId());
		condition.setMotorcycle(record.getMotorcycle());
		condition.setSpaProductCode(record.getSpaProductCode());
		condition.setGlsxProductCode(record.getGlsxProductCode());
		
		List<GhProductConfig> listProductConfigs = null;
		//如果未指定查询商户支持的
		/*Integer merchantBrandCount = merchantBrandService.countMerchantBrand(record.getMerchantCode());
		if(!StringUtils.isEmpty(record.getMerchantCode()) && (null == condition.getParentBrandId()) && merchantBrandCount>0){
			condition.setMerchantCode(record.getMerchantCode());
			listProductConfigs = productConfigMapper.listGhProductConfigAudisWithMerchantCode(condition);
		}else{
			listProductConfigs = productConfigMapper.listGhProductConfigAudis(condition);
		}*/
		List<GhMerchantBrand> listMerchantBrand = merchantBrandService.listMerchantBrand(record.getMerchantCode());
		//如果商户下车品牌为空  默认获取全部品牌 否则获取商户支持的车系车型
		List<Integer> listParentBrandIds = new ArrayList<>();
		List<Integer> listSubBrandIds = new ArrayList<>();
		listParentBrandIds.add(Constants.PRODUCT_CONFIG_PARENT_BRAND_TONGYONG);
		listSubBrandIds.add(Constants.PRODUCT_CONFIG_SUB_BRAND_TONGYONG);
		if(null != condition.getParentBrandId()){
			listParentBrandIds.add(condition.getParentBrandId());
			condition.setListParentBrandIds(listParentBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getParentBrandId() != 0){
						listParentBrandIds.add(item.getParentBrandId());
					}
				}
				condition.setListParentBrandIds(listParentBrandIds);
			}
		}
		if(null != condition.getSubBrandId()){
			listSubBrandIds.add(condition.getSubBrandId());
			condition.setListSubBrandIds(listSubBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getSubBrandId() != 0){
						listSubBrandIds.add(item.getSubBrandId());
					}
				}
				condition.setListSubBrandIds(listSubBrandIds);
			}
		}
		
		listProductConfigs = productConfigMapper.listGhProductConfigAudis(condition);
		
		if(null != listProductConfigs && !listProductConfigs.isEmpty()){
			for(GhProductConfig productConfig:listProductConfigs){
				attribInfoDto = new  AttribInfoDTO();
				attribInfoDto.setId(productConfig.getAudiId());
				attribInfoDto.setName(this.getAttribInfoName(productConfig.getAudiId(), mapAttribInfo));
				listResultDto.add(attribInfoDto);
			}
		}
		return listResultDto;
	}
	
	public Page<GhProductConfigDTO> pageGhProductConfigMotorcycle(Integer pageNo, Integer pageSize,GhProductConfigDTO dtoRecord){
		List<String> productConfigCodes = new ArrayList<>();
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		Map<String,List<GhProductConfigOther>> mapConfigOthers = null;
		List<GhProductConfigOther> listOthers = null;
		List<GhProductConfigOtherDTO> listOtherDtos = null;
		GhProductConfigOtherDTO productConfigOtherDto = null;
		GhProductConfigDTO productConfigDto = null;
		Page<GhProductConfigDTO> pageResult = new Page<GhProductConfigDTO>();
		PageHelper.startPage(pageNo,pageSize);
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(dtoRecord.getParentBrandId());
		condition.setSubBrandId(dtoRecord.getSubBrandId());
		condition.setCategoryId(dtoRecord.getCategoryId());
		condition.setAudiId(dtoRecord.getAudiId());	
		condition.setMotorcycle(dtoRecord.getMotorcycle());
		condition.setSpaProductCode(dtoRecord.getSpaProductCode());
		condition.setModelYearId(dtoRecord.getModelYearId());
		condition.setMotorcycle(dtoRecord.getMotorcycle());
		condition.setGlsxProductCode(dtoRecord.getGlsxProductCode());
		Page<GhProductConfig> dbResult = null;
		//如果未指定查询商户支持的
		/*Integer merchantBrandCount = merchantBrandService.countMerchantBrand(dtoRecord.getMerchantCode());
		if(!StringUtils.isEmpty(dtoRecord.getMerchantCode()) && (null == condition.getParentBrandId()) && merchantBrandCount>0){
			condition.setMerchantCode(dtoRecord.getMerchantCode());
			dbResult = productConfigMapper.pageGhProductConfigMotorcycleWithMerchantCode(condition);
		}else{
			dbResult = productConfigMapper.pageGhProductConfigMotorcycle(condition);
		}*/
		
		List<GhMerchantBrand> listMerchantBrand = merchantBrandService.listMerchantBrand(dtoRecord.getMerchantCode());
		//如果商户下车品牌为空  默认获取全部品牌 否则获取商户支持的车系车型
		List<Integer> listParentBrandIds = new ArrayList<>();
		List<Integer> listSubBrandIds = new ArrayList<>();
		listParentBrandIds.add(Constants.PRODUCT_CONFIG_PARENT_BRAND_TONGYONG);
		listSubBrandIds.add(Constants.PRODUCT_CONFIG_SUB_BRAND_TONGYONG);
		if(null != condition.getParentBrandId()){
			listParentBrandIds.add(condition.getParentBrandId());
			condition.setListParentBrandIds(listParentBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getParentBrandId() != 0){
						listParentBrandIds.add(item.getParentBrandId());
					}
				}
				condition.setListParentBrandIds(listParentBrandIds);
			}
		}
		if(null != condition.getSubBrandId()){
			listSubBrandIds.add(condition.getSubBrandId());
			condition.setListSubBrandIds(listSubBrandIds);
		}else{
			if(null != listMerchantBrand && !listMerchantBrand.isEmpty()){
				for(GhMerchantBrand item:listMerchantBrand){
					if(item.getSubBrandId() != 0){
						listSubBrandIds.add(item.getSubBrandId());
					}
				}
				condition.setListSubBrandIds(listSubBrandIds);
			}
		}
		
		dbResult = productConfigMapper.pageGhProductConfigMotorcycle(condition);
		 
		if(null != dbResult && null != dbResult.getResult()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				productConfigCodes.add(productConfig.getProductConfigCode());
			}
		}
		List<GhProductConfigOther> listProductConfigOthers = listGhproductConfigOther(null,productConfigCodes);
		if(null != listProductConfigOthers){
			mapConfigOthers = listProductConfigOthers.stream().collect(Collectors.groupingBy(GhProductConfigOther::getProductConfigCode));
		}
		//补充 方便后面操作不需要再判断
		if(null == mapConfigOthers){
			mapConfigOthers = new HashMap<>();
		}
		if(null != dbResult && null != dbResult.getResult()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				productConfigDto = new GhProductConfigDTO();
				pageResult.add(productConfigDto);
				productConfigDto.setAudiId(productConfig.getAudiId());
				productConfigDto.setAudiName(getAttribInfoName(productConfig.getAudiId(),mapAttribInfo));
				productConfigDto.setCategoryId(productConfig.getCategoryId());
				productConfigDto.setCategoryName(getAttribInfoName(productConfig.getCategoryId(),mapAttribInfo));
				productConfigDto.setGlsxProductCode(productConfig.getGlsxProductCode());
				productConfigDto.setGlsxProductName(productConfig.getGlsxProductName());
				productConfigDto.setMerchantCode(dtoRecord.getMerchantCode());
				productConfigDto.setModelYearId(productConfig.getModelYearId());
				productConfigDto.setModelYearName(getAttribInfoName(productConfig.getModelYearId(),mapAttribInfo));
				productConfigDto.setModelYearValue(getAttribInfoValue(productConfig.getModelYearId(),mapAttribInfo));
				productConfigDto.setMotorcycle(productConfig.getMotorcycle());
				productConfigDto.setParentBrandId(productConfig.getParentBrandId());
				productConfigDto.setParentBrandName(getAttribInfoName(productConfig.getParentBrandId(),mapAttribInfo));
				productConfigDto.setProductConfigCode(productConfig.getProductConfigCode());
				productConfigDto.setSpaProductCode(productConfig.getSpaProductCode());
				productConfigDto.setSpaProductName(productConfig.getSpaProductName());
				productConfigDto.setSubBrandId(productConfig.getSubBrandId());
				productConfigDto.setSubBrandName(getAttribInfoName(productConfig.getSubBrandId(),mapAttribInfo));
				listOthers = mapConfigOthers.get(productConfig.getProductConfigCode());
				if(null == listOthers){
					continue;
				}
				listOtherDtos = new ArrayList<>();
				for(GhProductConfigOther other:listOthers){
					productConfigOtherDto = new GhProductConfigOtherDTO();
					productConfigOtherDto.setId(other.getId());
					productConfigOtherDto.setAttribInfoId(other.getAttribInfoId());
					productConfigOtherDto.setAttribInfoName(getAttribInfoName(other.getAttribInfoId(),mapAttribInfo));
					productConfigOtherDto.setAttribTypeId(getAttribTypeId(other.getAttribInfoId(),mapAttribInfo));
					productConfigOtherDto.setAttribTypeName(getAttribTypeName(other.getAttribInfoId(),mapAttribInfo));
					productConfigOtherDto.setOption(other.getOption());
					productConfigOtherDto.setProductConfigCode(other.getProductConfigCode());
					listOtherDtos.add(productConfigOtherDto);
				}
				productConfigDto.setListConfigOther(listOtherDtos);	
			}
		/*	if(pageResult.getResult() != null && pageResult.getResult().isEmpty()){
				pageResult.getResult().stream().sorted(Comparator.comparing(GhProductConfigDTO::getModelYearValue).reversed()).collect(Collectors.toList());
			}*/
			pageResult.setPageNum(dbResult.getPageNum());
			pageResult.setPages(dbResult.getPages());
			pageResult.setPageSize(dbResult.getPageSize());
			pageResult.setTotal(dbResult.getTotal());
		}
		return pageResult;
	}

	public List<GhProductConfigDTO> exportGhProductConfigMotorcycle(GhProductConfigDTO dtoRecord){
		List<String> productConfigCodes = new ArrayList<>();
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		Map<String,List<GhProductConfigOther>> mapConfigOthers = null;
		List<GhProductConfigOther> listOthers = null;
		List<GhProductConfigOtherDTO> listOtherDtos = null;
		GhProductConfigOtherDTO productConfigOtherDto = null;
		GhProductConfigDTO productConfigDto = null;
		List<GhProductConfigDTO> listResultDto = new ArrayList<>();
		GhProductConfig condition = new GhProductConfig();
		condition.setParentBrandId(dtoRecord.getParentBrandId());
		condition.setSubBrandId(dtoRecord.getSubBrandId());
		condition.setCategoryId(dtoRecord.getCategoryId());
		condition.setAudiId(dtoRecord.getAudiId());
		condition.setMotorcycle(dtoRecord.getMotorcycle());
		condition.setSpaProductCode(dtoRecord.getSpaProductCode());
		condition.setModelYearId(dtoRecord.getModelYearId());
		condition.setMotorcycle(dtoRecord.getMotorcycle());
		condition.setGlsxProductCode(dtoRecord.getGlsxProductCode());
		Page<GhProductConfig> dbResult = null;
		//如果未指定查询商户支持的
		if(!StringUtils.isEmpty(dtoRecord.getMerchantCode()) && (null == condition.getParentBrandId())){
			condition.setMerchantCode(dtoRecord.getMerchantCode());
			dbResult = productConfigMapper.pageGhProductConfigMotorcycleWithMerchantCode(condition);
		}else{
			dbResult = productConfigMapper.pageGhProductConfigMotorcycle(condition);
		}


		if(null != dbResult && null != dbResult.getResult()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				productConfigCodes.add(productConfig.getProductConfigCode());
			}
		}
		List<GhProductConfigOther> listProductConfigOthers = listGhproductConfigOther(null,productConfigCodes);
		if(null != listProductConfigOthers){
			mapConfigOthers = listProductConfigOthers.stream().collect(Collectors.groupingBy(GhProductConfigOther::getProductConfigCode));
		}
		//补充 方便后面操作不需要再判断
		if(null == mapConfigOthers){
			mapConfigOthers = new HashMap<>();
		}
		if(null != dbResult && null != dbResult.getResult()){
			for(GhProductConfig productConfig:dbResult.getResult()){
				productConfigDto = new GhProductConfigDTO();
				listResultDto.add(productConfigDto);
				productConfigDto.setAudiId(productConfig.getAudiId());
				productConfigDto.setAudiName(getAttribInfoName(productConfig.getAudiId(),mapAttribInfo));
				productConfigDto.setCategoryId(productConfig.getCategoryId());
				productConfigDto.setCategoryName(getAttribInfoName(productConfig.getCategoryId(),mapAttribInfo));
				productConfigDto.setGlsxProductCode(productConfig.getGlsxProductCode());
				productConfigDto.setGlsxProductName(productConfig.getGlsxProductName());
				productConfigDto.setMerchantCode(dtoRecord.getMerchantCode());
				productConfigDto.setModelYearId(productConfig.getModelYearId());
				productConfigDto.setModelYearName(getAttribInfoName(productConfig.getModelYearId(),mapAttribInfo));
				productConfigDto.setModelYearValue(getAttribInfoValue(productConfig.getModelYearId(),mapAttribInfo));
				productConfigDto.setMotorcycle(productConfig.getMotorcycle());
				productConfigDto.setParentBrandId(productConfig.getParentBrandId());
				productConfigDto.setParentBrandName(getAttribInfoName(productConfig.getParentBrandId(),mapAttribInfo));
				productConfigDto.setProductConfigCode(productConfig.getProductConfigCode());
				productConfigDto.setSpaProductCode(productConfig.getSpaProductCode());
				productConfigDto.setSpaProductName(productConfig.getSpaProductName());
				productConfigDto.setSubBrandId(productConfig.getSubBrandId());
				productConfigDto.setSubBrandName(getAttribInfoName(productConfig.getSubBrandId(),mapAttribInfo));
				listOthers = mapConfigOthers.get(productConfig.getProductConfigCode());
				if(null == listOthers){
					continue;
				}
				listOtherDtos = new ArrayList<>();
				for(GhProductConfigOther other:listOthers){
					productConfigOtherDto = new GhProductConfigOtherDTO();
					productConfigOtherDto.setAttribInfoId(other.getAttribInfoId());
					productConfigOtherDto.setAttribInfoName(getAttribInfoName(other.getAttribInfoId(),mapAttribInfo));
					productConfigOtherDto.setAttribTypeId(getAttribTypeId(other.getAttribInfoId(),mapAttribInfo));
					productConfigOtherDto.setAttribTypeName(getAttribTypeName(other.getAttribInfoId(),mapAttribInfo));
					productConfigOtherDto.setOption(other.getOption());
					productConfigOtherDto.setProductConfigCode(other.getProductConfigCode());
					listOtherDtos.add(productConfigOtherDto);
				}
				productConfigDto.setListConfigOther(listOtherDtos);
			}
		}
		return listResultDto;
	}
	
	public List<GhProductConfigOtherDTO> listGhProductConfigOther(GhProductConfigDTO record){
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		List<String> listConfigCodes = new ArrayList<>();
		listConfigCodes.add(record.getProductConfigCode());
		List<GhProductConfigOther> listConfigOthers = listGhproductConfigOther(null,listConfigCodes);
		List<GhProductConfigOtherDTO> listOtherDtos = null;
		GhProductConfigOtherDTO productConfigOtherDto = null;
		if(null == listConfigOthers || listConfigOthers.isEmpty()){
			return null;
		}
		listOtherDtos = new ArrayList<>();
		for(GhProductConfigOther other:listConfigOthers){
			productConfigOtherDto = new GhProductConfigOtherDTO();
			productConfigOtherDto.setId(other.getId());
			productConfigOtherDto.setAttribInfoId(other.getAttribInfoId());
			productConfigOtherDto.setAttribInfoName(getAttribInfoName(other.getAttribInfoId(),mapAttribInfo));
			productConfigOtherDto.setAttribTypeId(getAttribTypeId(other.getAttribInfoId(),mapAttribInfo));
			productConfigOtherDto.setAttribTypeName(getAttribTypeName(other.getAttribInfoId(),mapAttribInfo));
			productConfigOtherDto.setOption(other.getOption());
			productConfigOtherDto.setProductConfigCode(other.getProductConfigCode());
			listOtherDtos.add(productConfigOtherDto);
		}
		return listOtherDtos;
	}
	
	public List<GhProductConfig> listProductConfigByListConfigCodes(List<String> listProductConfigCodes){
		if(null == listProductConfigCodes || listProductConfigCodes.isEmpty()){
			return null;
		}
		Example example = new Example(GhProductConfig.class);
		example.createCriteria().andEqualTo("deletedFlag","N")
								.andIn("productConfigCode", listProductConfigCodes);
		return productConfigMapper.selectByExample(example);			
	}
	
	public GhProductConfig getProductConfigByConfigCode(String productConfigCode){
		GhProductConfig condition = new GhProductConfig();
		condition.setProductConfigCode(productConfigCode);
		condition.setDeletedFlag("N");
		return productConfigMapper.selectOne(condition);
	}
	
	public List<GhProductConfigOther> listGhproductConfigOtherByConfigCode(String productConfigCode){
		List<String> listProductConfigCodes = new ArrayList<>();
		listProductConfigCodes.add(productConfigCode);
		return listGhproductConfigOther(null,listProductConfigCodes);
	}
	
	//获取产品固定或者选项配置
	public List<GhProductConfigOther> listGhproductConfigOther(String option,List<String> listProductConfigCodes){
		if(null == listProductConfigCodes || listProductConfigCodes.isEmpty()){
			return null;
		}
		/*Example example = new Example(GhProductConfigOther.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deletedFlag","N");
		criteria.andIn("productConfigCode", listProductConfigCodes);
		if(!StringUtils.isEmpty(option)){
			criteria.andEqualTo("option", option);
		}
		return productConfigOtherMapper.selectByExample(example);*/
		LOGGER.info("GhProductConfigService::listGhproductConfigOther option=" + option);
		GhProductConfigOther condition = new GhProductConfigOther();
		condition.setDeletedFlag("N");
		condition.setOption(option);
		condition.setListProductConfigCodes(listProductConfigCodes);
		return productConfigOtherMapper.selectProductConfigOthers(condition);
	}
	

	private String getAttribInfoName(Integer attribInfoId,Map<Integer,AttribInfo> mapAttribInfo){
		String result = "";
		if(null == attribInfoId){
			return result;
		}
		AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
		if(null != attribInfo){
			result = attribInfo.getName();
		}
		return result;
	}
	
	private Integer getAttribInfoValue(Integer attribInfoId,Map<Integer,AttribInfo> mapAttribInfo){
		Integer result = null;
		if(null == attribInfoId){
			return result;
		}
		AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
		if(null != attribInfo){
			result = attribInfo.getValue();
		}
		return result;
	}
	
	private Integer getAttribTypeId(Integer attribInfoId,Map<Integer,AttribInfo> mapAttribInfo){
		Integer result = null;
		if(null == attribInfoId){
			return null;
		}
		AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
		if(null != attribInfo){
			result = attribInfo.getType();
		}
		return result;
	}
	
	private String getAttribTypeName(Integer attribInfoId,Map<Integer,AttribInfo> mapAttribInfo){
		String result = "";
		if(null == attribInfoId){
			return result;
		}
		AttribInfo attribInfo = attribInfoService.getAttribInfoWithLocalCache(attribInfoId, mapAttribInfo);
		if(null != attribInfo){
			result = attribInfo.getComment();
		}
		return result;
	}

	
	
}
