package cn.com.glsx.supplychain.jst.service;

import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.dto.gh.AttribInfoDTO;
import cn.com.glsx.supplychain.jst.dto.gh.GhShoppingCartConfigDTO;
import cn.com.glsx.supplychain.jst.dto.gh.GhShoppingCartDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.GhShoppingCartConfigMapper;
import cn.com.glsx.supplychain.jst.mapper.GhShoppingCartMapper;
import cn.com.glsx.supplychain.jst.model.AttribInfo;
import cn.com.glsx.supplychain.jst.model.GhProductConfig;
import cn.com.glsx.supplychain.jst.model.GhProductConfigOther;
import cn.com.glsx.supplychain.jst.model.GhShoppingCart;
import cn.com.glsx.supplychain.jst.model.GhShoppingCartConfig;
import cn.com.glsx.supplychain.jst.util.JstUtils;

@Service
public class GhShoppingCartService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GhShoppingCartService.class);
	@Autowired
	private GhShoppingCartMapper ghShoppingCartMapper;
	@Autowired
	private GhShoppingCartConfigMapper ghShoppingCartConfigMapper;
	@Autowired
	private GhProductConfigService ghProductConfigService;
	@Autowired
    private SnowflakeWorker snowflakeWorker;
	@Autowired
	private AttribInfoService attribInfoService;
	
	public Integer insertShoppingCartListReplace(List<GhShoppingCart> listRecord){
		return ghShoppingCartMapper.insertGhShoppingCartReplace(listRecord);
	}
	
	public Integer insertShoppingCartList(List<GhShoppingCart> listRecord){
		return ghShoppingCartMapper.insertList(listRecord);
	}
	
	public Integer insertShoppingCart(GhShoppingCart record){
		return ghShoppingCartMapper.insertSelective(record);
	}
	
	public Integer updateShoppingCartById(GhShoppingCart record){
		return ghShoppingCartMapper.updateByPrimaryKey(record);
	}
	
	public Integer insertShoppingCartConfig(List<GhShoppingCartConfig> listConfig){
		if(null == listConfig || listConfig.isEmpty()){
			return 0;
		}
		return ghShoppingCartConfigMapper.insertCartConfigList(listConfig);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer addGhProductConfigCart(GhShoppingCartDTO record) throws RpcServiceException{
		
		Integer result = 0;
		String cartCode = Constants.GH_SHOP_CART_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
		Map<Integer,GhProductConfigOther> mapConfigOther = null;
		GhProductConfigOther configOther = null;
		GhShoppingCartConfig shoppingCartConfig = null;
		List<GhShoppingCartConfig> listShoppingCartConfig = null;
		List<GhShoppingCart> listShoppingCart = this.getGhShoppingCartByProductConfigCode(record.getMerchantCode(), record.getProductConfigCode());
		if(null != listShoppingCart){
			for(GhShoppingCart shoppingCart:listShoppingCart){
				String dtoKey = "p" + (StringUtils.isEmpty(record.getRemark())?"":record.getRemark());
				if(record.getListCartConfig() != null && !record.getListCartConfig().isEmpty()){
					List<GhShoppingCartConfigDTO> listConfigDtos = record.getListCartConfig().stream().sorted(Comparator.comparing(GhShoppingCartConfigDTO::getAttribInfoId).reversed()).collect(Collectors.toList());	
					for(GhShoppingCartConfigDTO dtoConfig:listConfigDtos){
						dtoKey += "s";
						dtoKey += dtoConfig.getAttribInfoId();
					}
				}
				String beanKey = "p" + (StringUtils.isEmpty(shoppingCart.getRemark())?"":shoppingCart.getRemark());
				List<GhShoppingCartConfig> listReadCartConfig = listShoppingCartConfigByCartCodes(shoppingCart.getCartCode());
				if(null == listReadCartConfig || !listReadCartConfig.isEmpty()){
					List<GhShoppingCartConfig> listConfigBeans = listReadCartConfig.stream().sorted(Comparator.comparing(GhShoppingCartConfig::getAttribInfoId).reversed()).collect(Collectors.toList());	
					for(GhShoppingCartConfig config:listConfigBeans){
						beanKey += "s";
						beanKey += config.getAttribInfoId();
					}
				}
				if(beanKey.equals(dtoKey)){
					shoppingCart.setTotal(shoppingCart.getTotal() + record.getTotal());
					if(shoppingCart.getTotal() > Constants.GH_CART_COUNT){
						shoppingCart.setTotal(Constants.GH_CART_COUNT);
					}
					return updateShoppingCartById(shoppingCart);
				}
			}
		}
		
		GhProductConfig productConfig = ghProductConfigService.getProductConfigByConfigCode(record.getProductConfigCode());
		if(null == productConfig){
			LOGGER.info("GhShoppingCartService::addGhProductConfigCart valid product config code!");
	        throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_CONFIG_CODE);
		}
		
		if(null != record.getListCartConfig() && !record.getListCartConfig().isEmpty()){
			List<GhProductConfigOther> listConfigOthers = ghProductConfigService.listGhproductConfigOtherByConfigCode(record.getProductConfigCode());
			if(null != listConfigOthers){
				mapConfigOther = listConfigOthers.stream().collect(Collectors.toMap(GhProductConfigOther::getAttribInfoId, a -> a,(k1,k2)->k1));
			}
			if(null == mapConfigOther){
				mapConfigOther = new HashMap<>();
			}
			for(GhShoppingCartConfigDTO configDto:record.getListCartConfig()){
				configOther = mapConfigOther.get(configDto.getAttribInfoId());
				if(null == configOther){
					LOGGER.info("GhShoppingCartService::addGhProductConfigCart valid product config other attribId!");
			        throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_CONFIG_OTHERS);
				}
				shoppingCartConfig = new GhShoppingCartConfig();
				shoppingCartConfig.setAttribInfoId(configOther.getAttribInfoId());
				shoppingCartConfig.setCartCode(cartCode);
				shoppingCartConfig.setOption(configOther.getOption());
				shoppingCartConfig.setCreatedBy(record.getMerchantCode());
				shoppingCartConfig.setCreatedDate(JstUtils.getNowDate());
				shoppingCartConfig.setUpdatedBy(record.getMerchantCode());
				shoppingCartConfig.setUpdatedDate(JstUtils.getNowDate());
				shoppingCartConfig.setDeletedFlag("N");
				if(null == listShoppingCartConfig){
					listShoppingCartConfig = new ArrayList<>();
				}
				listShoppingCartConfig.add(shoppingCartConfig);
			}	
		}
		
		GhShoppingCart shoppingCart = new GhShoppingCart();
		shoppingCart.setCartCode(cartCode);
		shoppingCart.setMerchantCode(record.getMerchantCode());
		shoppingCart.setProductConfigCode(productConfig.getProductConfigCode());
		shoppingCart.setParentBrandId(productConfig.getParentBrandId());
		shoppingCart.setSubBrandId(productConfig.getSubBrandId());
		shoppingCart.setAudiId(productConfig.getAudiId());
		shoppingCart.setMotorcycle(productConfig.getMotorcycle());
		shoppingCart.setCategoryId(productConfig.getCategoryId());
		shoppingCart.setSpaProductCode(productConfig.getSpaProductCode());
		shoppingCart.setSpaProductName(productConfig.getSpaProductName());
		shoppingCart.setGlsxProductCode(productConfig.getGlsxProductCode());
		shoppingCart.setGlsxProductName(productConfig.getGlsxProductName());
		shoppingCart.setTotal(record.getTotal());
		shoppingCart.setRemark(record.getRemark());
		shoppingCart.setCreatedBy(record.getMerchantCode());
		shoppingCart.setCreatedDate(JstUtils.getNowDate());
		shoppingCart.setUpdatedBy(record.getMerchantCode());
		shoppingCart.setUpdatedDate(JstUtils.getNowDate());
		shoppingCart.setDeletedFlag("N");
		
		try{
			insertShoppingCart(shoppingCart);
			insertShoppingCartConfig(listShoppingCartConfig);	
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
		}
		
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer batchAddGhProductConfigCart(List<GhShoppingCartDTO> listRecord) throws RpcServiceException{
		Integer result = 0;
		List<GhShoppingCartConfig> listShoppingCartConfig = null;
		List<GhShoppingCart> listShoppingCart = null;
		GhShoppingCart shoppingCart = null;
		GhShoppingCartConfig shoppingCartConfig = null;	
		GhProductConfig shoppingConfig = null;
		GhProductConfigOther shoppingConfigOther = null;
		Map<Integer,GhProductConfigOther> mapSubConfigOther = null;
		Map<String,Map<Integer,GhProductConfigOther>> mapConfigOther = new HashMap<>();
		Map<String,GhProductConfig> mapConfig = null;
		Map<String,GhShoppingCart> mapShoppingCart = null;
		List<String> listProductConfigCodes = listRecord.stream().map(GhShoppingCartDTO::getProductConfigCode).collect(Collectors.toList());
		List<GhShoppingCart> listProductCarts =  listGhShoppingCartByListProductConfigCode(listRecord.get(0).getMerchantCode(),listProductConfigCodes);
		List<GhProductConfig> listProductConfig = ghProductConfigService.listProductConfigByListConfigCodes(listProductConfigCodes);
		List<GhProductConfigOther> listProductConfigOther = ghProductConfigService.listGhproductConfigOther(null, listProductConfigCodes);
		if(null != listProductConfigOther){
			Map<Integer,GhProductConfigOther> mapSubObject = null;
			for(GhProductConfigOther other:listProductConfigOther){
				mapSubObject = mapConfigOther.get(other.getProductConfigCode());
				if(null == mapSubObject){
					mapSubObject = new HashMap<>();
					mapConfigOther.put(other.getProductConfigCode(), mapSubObject);
				}
				mapSubObject.put(other.getAttribInfoId(), other);	
			}
		}
		if(null != listProductConfig){
			mapConfig = listProductConfig.stream().collect(Collectors.toMap(GhProductConfig::getProductConfigCode, a -> a,(k1,k2)->k1));
		}
		if(null == mapConfig){
			mapConfig = new HashMap<>();
		}
		if(null != listProductCarts && !listProductCarts.isEmpty()){
			List<String> listReadShoppingCartCodes = listProductCarts.stream().map(GhShoppingCart::getCartCode).collect(Collectors.toList());
			List<GhShoppingCartConfig> listReadCartConfigs = this.listShoppingCartConfig(listReadShoppingCartCodes);
			Map<String,List<GhShoppingCartConfig>> mapReadCartConfigs = null;
			if(null != listReadCartConfigs){
				mapReadCartConfigs = listReadCartConfigs.stream().collect(Collectors.groupingBy(GhShoppingCartConfig::getCartCode));
			}
			if(null == mapReadCartConfigs){
				mapReadCartConfigs = new HashMap<>();
			}
			//配置 备注 产品编码组成key值
			mapShoppingCart = new HashMap<>();
			String strMapKey = "";
			for(GhShoppingCart cart:listProductCarts){
				strMapKey = "c" + cart.getProductConfigCode() + "p" + (StringUtils.isEmpty(cart.getRemark())?"":cart.getRemark());
				List<GhShoppingCartConfig> listCartConfgis = mapReadCartConfigs.get(cart.getCartCode());
				if(null != listCartConfgis && !listCartConfgis.isEmpty()){
					listCartConfgis = listCartConfgis.stream().sorted(Comparator.comparing(GhShoppingCartConfig::getAttribInfoId).reversed()).collect(Collectors.toList());	
					for(GhShoppingCartConfig config:listCartConfgis){
						strMapKey += "g";
						strMapKey += config.getAttribInfoId();
					}
				}
				mapShoppingCart.put(strMapKey, cart);
			}		
			//mapShoppingCart = listProductCarts.stream().collect(Collectors.toMap(GhShoppingCart::getProductConfigCode, a -> a,(k1,k2)->k1));
		}
		if(null == mapShoppingCart){
			mapShoppingCart = new HashMap<>();
		}
		
		for(GhShoppingCartDTO dto:listRecord){
			shoppingConfig = mapConfig.get(dto.getProductConfigCode());
			if(null == shoppingConfig){
				LOGGER.info("GhShoppingCartService::batchAddGhProductConfigCart valid product config code!");
		        throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_CONFIG_CODE);
			}
			String dtoKey = "c" + dto.getProductConfigCode() + "p" + (StringUtils.isEmpty(dto.getRemark())?"":dto.getRemark());
			if(dto.getListCartConfig() != null && !dto.getListCartConfig().isEmpty()){
				List<GhShoppingCartConfigDTO> listConfigDtos = dto.getListCartConfig().stream().sorted(Comparator.comparing(GhShoppingCartConfigDTO::getId).reversed()).collect(Collectors.toList()); 
				for(GhShoppingCartConfigDTO configDto:listConfigDtos){
					dtoKey += "g";
					dtoKey += configDto.getId();
				}
			}
			shoppingCart = mapShoppingCart.get(dtoKey);
			if(null == shoppingCart){
				shoppingCart = new GhShoppingCart();
				String cartCode = Constants.GH_SHOP_CART_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
				shoppingCart = new GhShoppingCart();
				shoppingCart.setCartCode(cartCode);
				shoppingCart.setMerchantCode(dto.getMerchantCode());
				shoppingCart.setProductConfigCode(shoppingConfig.getProductConfigCode());
				shoppingCart.setParentBrandId(shoppingConfig.getParentBrandId());
				shoppingCart.setSubBrandId(shoppingConfig.getSubBrandId());
				shoppingCart.setAudiId(shoppingConfig.getAudiId());
				shoppingCart.setMotorcycle(shoppingConfig.getMotorcycle());
				shoppingCart.setCategoryId(shoppingConfig.getCategoryId());
				shoppingCart.setSpaProductCode(shoppingConfig.getSpaProductCode());
				shoppingCart.setSpaProductName(shoppingConfig.getSpaProductName());
				shoppingCart.setGlsxProductCode(shoppingConfig.getGlsxProductCode());
				shoppingCart.setGlsxProductName(shoppingConfig.getGlsxProductName());
				shoppingCart.setTotal(dto.getTotal());
				shoppingCart.setRemark(dto.getRemark());
				shoppingCart.setCreatedBy(dto.getMerchantCode());
				shoppingCart.setCreatedDate(JstUtils.getNowDate());
				shoppingCart.setUpdatedBy(dto.getMerchantCode());
				shoppingCart.setUpdatedDate(JstUtils.getNowDate());
				shoppingCart.setDeletedFlag("N");
				
				if(null != dto.getListCartConfig() && !dto.getListCartConfig().isEmpty()){
					mapSubConfigOther = mapConfigOther.get(dto.getProductConfigCode());
					if(null == mapSubConfigOther){
						mapSubConfigOther = new HashMap<>();
					}
					for(GhShoppingCartConfigDTO configDto:dto.getListCartConfig()){
						shoppingConfigOther = mapSubConfigOther.get(configDto.getId());
						if(null == shoppingConfigOther){
							LOGGER.info("GhShoppingCartService::batchAddGhProductConfigCart valid product config other attribId!");
					        throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_CONFIG_OTHERS);
						}					
						shoppingCartConfig = new GhShoppingCartConfig();
						shoppingCartConfig.setAttribInfoId(shoppingConfigOther.getAttribInfoId());
						shoppingCartConfig.setCartCode(cartCode);
						shoppingCartConfig.setOption(shoppingConfigOther.getOption());
						shoppingCartConfig.setCreatedBy(dto.getMerchantCode());
						shoppingCartConfig.setCreatedDate(JstUtils.getNowDate());
						shoppingCartConfig.setUpdatedBy(dto.getMerchantCode());
						shoppingCartConfig.setUpdatedDate(JstUtils.getNowDate());
						shoppingCartConfig.setDeletedFlag("N");
						if(null == listShoppingCartConfig){
							listShoppingCartConfig = new ArrayList<>();
						}
						listShoppingCartConfig.add(shoppingCartConfig);
					}	
				}
			}else{
				shoppingCart.setTotal(shoppingCart.getTotal() + dto.getTotal());
				if(shoppingCart.getTotal() > Constants.GH_CART_COUNT){
					shoppingCart.setTotal(Constants.GH_CART_COUNT);
				}
			}
			
			if(null == listShoppingCart){
				listShoppingCart = new ArrayList<>();
			}
			listShoppingCart.add(shoppingCart);
		}
		
		try{
			insertShoppingCartListReplace(listShoppingCart);
			insertShoppingCartConfig(listShoppingCartConfig);	
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
	public Integer delGhProductConfigCart(List<GhShoppingCartDTO> listRecord) throws RpcServiceException{
		Integer result = 0;
		List<String> listCartCodes = new ArrayList<>();
		for(GhShoppingCartDTO dto:listRecord){
			listCartCodes.add(dto.getCartCode());
		}
		try{	
			delGhShoppingCartConfigByCartCodes(listCartCodes);
			delGhShoppingCartByCartCodes(listCartCodes);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
		}
		return result;
	}
	
	public Integer updateGhProductConfigCart(GhShoppingCartDTO record){
		GhShoppingCart shoppingCart = getShoppingCartByCartCode(record.getCartCode());
		if(null != record.getTotal()){
			shoppingCart.setTotal(record.getTotal());
		}
		if(null != record.getRemark()){
			shoppingCart.setRemark(record.getRemark());
		}
		shoppingCart.setUpdatedBy(record.getMerchantCode());
		shoppingCart.setUpdatedDate(JstUtils.getNowDate());
		return ghShoppingCartMapper.updateByPrimaryKey(shoppingCart);
	}
	
	public Page<GhShoppingCartDTO> pageGhShoppingCart(Integer pageNo, Integer pageSize, GhShoppingCartDTO dtoRecord){
		List<String> listCartCode = new ArrayList<>();
		Map<Integer,AttribInfo> mapAttribInfo = new HashMap<>();
		Map<String,List<GhShoppingCartConfig>> mapShoppingCarts = null;
		Page<GhShoppingCartDTO> pageResult = new Page<GhShoppingCartDTO>();
		GhShoppingCartDTO shoppingCartDto = null;
		List<GhShoppingCartConfigDTO> listShoppingCartConfigDtos = null;
		GhShoppingCartConfigDTO shoppingCartConfigDto = null;
		List<GhShoppingCartConfig> listCartConfig = null;
		PageHelper.startPage(pageNo,pageSize);
		GhShoppingCart condition = new GhShoppingCart();
		condition.setMerchantCode(dtoRecord.getMerchantCode());
		Page<GhShoppingCart> dbResult = ghShoppingCartMapper.pageGhShoppingCart(condition);
		if(null != dbResult && null != dbResult.getResult()){
			for(GhShoppingCart dbCart:dbResult.getResult()){
				listCartCode.add(dbCart.getCartCode());
			}
		}
		List<GhShoppingCartConfig> listShoppingCartConfigs = listShoppingCartConfig(listCartCode);
		if(null != listShoppingCartConfigs){
			mapShoppingCarts = listShoppingCartConfigs.stream().collect(Collectors.groupingBy(GhShoppingCartConfig::getCartCode));
		}
		if(null == mapShoppingCarts){
			mapShoppingCarts = new HashMap<>();
		}
		if(null != dbResult && null != dbResult.getResult()){
			for(GhShoppingCart dbCart:dbResult.getResult()){
				shoppingCartDto = new GhShoppingCartDTO();
				pageResult.add(shoppingCartDto);
				shoppingCartDto.setId(dbCart.getId());
				shoppingCartDto.setCartCode(dbCart.getCartCode());
				shoppingCartDto.setMerchantCode(dbCart.getMerchantCode());
				shoppingCartDto.setProductConfigCode(dbCart.getProductConfigCode());
				shoppingCartDto.setParentBrandId(dbCart.getParentBrandId());
				shoppingCartDto.setParentBrandName(getAttribInfoName(dbCart.getParentBrandId(),mapAttribInfo));
				shoppingCartDto.setSubBrandId(dbCart.getSubBrandId());
				shoppingCartDto.setSubBrandName(getAttribInfoName(dbCart.getSubBrandId(),mapAttribInfo));
				shoppingCartDto.setAudiId(dbCart.getAudiId());
				shoppingCartDto.setAudiName(getAttribInfoName(dbCart.getAudiId(),mapAttribInfo));
				shoppingCartDto.setMotorcycle(dbCart.getMotorcycle());
				shoppingCartDto.setCategoryId(dbCart.getCategoryId());
				shoppingCartDto.setCategoryName(getAttribInfoName(dbCart.getCategoryId(),mapAttribInfo));
				shoppingCartDto.setSpaProductCode(dbCart.getSpaProductCode());
				shoppingCartDto.setSpaProductName(dbCart.getSpaProductName());
				shoppingCartDto.setGlsxProductCode(dbCart.getGlsxProductCode());
				shoppingCartDto.setGlsxProductName(dbCart.getGlsxProductName());
				shoppingCartDto.setTotal(dbCart.getTotal());
				shoppingCartDto.setRemark(dbCart.getRemark());
				listCartConfig = mapShoppingCarts.get(dbCart.getCartCode());
				if(null == listCartConfig){
					continue;
				}
				listShoppingCartConfigDtos = new ArrayList<>();
				for(GhShoppingCartConfig config:listCartConfig){
					shoppingCartConfigDto = new GhShoppingCartConfigDTO();
					shoppingCartConfigDto.setAttribInfoId(config.getAttribInfoId());
					shoppingCartConfigDto.setAttribInfoName(getAttribInfoName(config.getAttribInfoId(),mapAttribInfo));
					shoppingCartConfigDto.setAttribTypeId(getAttribTypeId(config.getAttribInfoId(), mapAttribInfo));
					shoppingCartConfigDto.setAttribTypeName(getAttribTypeName(config.getAttribInfoId(),mapAttribInfo));
					shoppingCartConfigDto.setOption(config.getOption());
					listShoppingCartConfigDtos.add(shoppingCartConfigDto);
				}
				shoppingCartDto.setListCartConfig(listShoppingCartConfigDtos);
			}
			pageResult.setPageNum(dbResult.getPageNum());
			pageResult.setPages(dbResult.getPages());
			pageResult.setPageSize(dbResult.getPageSize());
			pageResult.setTotal(dbResult.getTotal());
		}
		return pageResult;
	}
	
	private List<GhShoppingCart> getGhShoppingCartByProductConfigCode(String merchantCode,String productConfigCode){
		GhShoppingCart result = null;
		Example example = new Example(GhShoppingCart.class);
		example.createCriteria().andEqualTo("productConfigCode",productConfigCode)
								.andEqualTo("merchantCode",merchantCode);
		return ghShoppingCartMapper.selectByExample(example);
	}
	
	
	
	private List<GhShoppingCart> listGhShoppingCartByListProductConfigCode(String merchantCode,List<String> listProductConfigCodes){
		if(null == listProductConfigCodes || listProductConfigCodes.isEmpty()){
			return null;
		}
		Example example = new Example(GhShoppingCart.class);
		example.createCriteria().andIn("productConfigCode",listProductConfigCodes)
								.andEqualTo("merchantCode",merchantCode);
		return ghShoppingCartMapper.selectByExample(example);
	}
	
	public List<GhShoppingCart> listShoppingCart(List<String> listCartCodes){
		if(null == listCartCodes || listCartCodes.isEmpty()){
			return null;
		}
		Example example = new Example(GhShoppingCart.class);
		example.createCriteria().andIn("cartCode",listCartCodes);
		return ghShoppingCartMapper.selectByExample(example);
	}
	
	
	public List<GhShoppingCartConfig> listShoppingCartConfig(List<String> listCartCodes){
		if(null == listCartCodes || listCartCodes.isEmpty()){
			return null;
		}
		GhShoppingCartConfig condition = new GhShoppingCartConfig();
		condition.setListCartCodes(listCartCodes);
		return ghShoppingCartConfigMapper.selectCartConfig(condition);
	/*	Example example = new Example(GhShoppingCartConfig.class);
		example.createCriteria().andIn("cartCode", listCartCodes);
		return ghShoppingCartConfigMapper.selectByExample(example);*/
	}
	 
	public List<GhShoppingCartConfig> listShoppingCartConfigByCartCodes(String cartCodes){
		GhShoppingCartConfig condition = new GhShoppingCartConfig();
		condition.setCartCode(cartCodes);
		return ghShoppingCartConfigMapper.selectCartConfig(condition);
		/*Example example = new Example(GhShoppingCartConfig.class);
		example.createCriteria().andEqualTo("cartCode", cartCodes);
		return ghShoppingCartConfigMapper.selectByExample(example);*/
	}
	
	public Integer delGhShoppingCartByCartCodes(List<String> listCartCodes){
		if(null == listCartCodes){
			return 0;
		}
		if(listCartCodes.isEmpty()){
			return 0;
		}
		Example example = new Example(GhShoppingCart.class);
		example.createCriteria().andIn("cartCode", listCartCodes);
		return ghShoppingCartMapper.deleteByExample(example);
	}
	
	public Integer delGhShoppingCartConfigByCartCodes(List<String> listCartCodes){
		if(null == listCartCodes){
			return 0;
		}
		if(listCartCodes.isEmpty()){
			return 0;
		}
		GhShoppingCartConfig condition = new GhShoppingCartConfig();
		condition.setListCartCodes(listCartCodes);
		return ghShoppingCartConfigMapper.deletCartConfig(condition);
		/*Example example = new Example(GhShoppingCartConfig.class);
		example.createCriteria().andIn("cartCode", listCartCodes);
		return ghShoppingCartConfigMapper.deleteByExample(example);*/
	}
	
	private GhShoppingCart getShoppingCartByCartCode(String cartCode){
		GhShoppingCart condition = new GhShoppingCart();
		condition.setCartCode(cartCode);
		return ghShoppingCartMapper.selectOne(condition);
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
