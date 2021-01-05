package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.convert.ProductRpcConvert;
import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.DisProductDTO;
import cn.com.glsx.supplychain.jst.dto.MaterialDTO;
import cn.com.glsx.supplychain.jst.dto.ProductDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.MaterialCategoryEnum;
import cn.com.glsx.supplychain.jst.enums.MaterialTypeEnum;
import cn.com.glsx.supplychain.jst.mapper.AmMaterialInfoMapper;
import cn.com.glsx.supplychain.jst.mapper.AmProductSplitDetailMapper;
import cn.com.glsx.supplychain.jst.mapper.AmProductSplitHistoryPriceMapper;
import cn.com.glsx.supplychain.jst.mapper.AmProductSplitMapper;
import cn.com.glsx.supplychain.jst.model.*;
import cn.com.glsx.supplychain.jst.util.JstUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service
public class ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private AmProductSplitMapper amProductMapper;
	@Autowired
	private AmProductSplitDetailMapper amProductDetailMapper;
	@Autowired
	private AmProductSplitHistoryPriceMapper amProductPriceMapper;
	@Autowired
	private AmMaterialInfoMapper amMaterialInfoMapper;
	
	public Map<String,AmMaterialInfo> batchGetAmMaterialInfoByMaterialCode(List<String> listMaterialCode,Map<String,AmMaterialInfo> mapMaterialInfo) throws RpcServiceException
	{
		logger.info("ProductService::batchGetAmMaterialInfoByMaterialCode start listMaterialCode:{}",listMaterialCode);
		if(StringUtils.isEmpty(listMaterialCode) || listMaterialCode.size() == 0 || StringUtils.isEmpty(mapMaterialInfo))
		{
			return mapMaterialInfo;
		}
		try
		{
			List<AmMaterialInfo> listMaterial = amMaterialInfoMapper.listMaterialInfoByMaterialCode(listMaterialCode);
			if(!StringUtils.isEmpty(listMaterial))
			{
				for(AmMaterialInfo amMaterial:listMaterial)
				{
					mapMaterialInfo.put(amMaterial.getMaterialCode(), amMaterial);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("ProductService::batchGetAmMaterialInfoByMaterialCode 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::batchGetAmMaterialInfoByMaterialCode end mapMaterialInfo:{}",mapMaterialInfo);
		return mapMaterialInfo;
	}

	public AmMaterialInfo getMaterialInfoByMaterialCode(String materialCode){
		AmMaterialInfo condition = new AmMaterialInfo();
		condition.setMaterialCode(materialCode);
		condition=amMaterialInfoMapper.selectOne(condition);
		return amMaterialInfoMapper.selectOne(condition);
	}

	public Map<String,List<AmProductSplitDetail>> listAmProductSplitDetail(List<String> listProductCode, Map<String,List<AmProductSplitDetail>>  mapProductDetail) throws RpcServiceException
	{
		logger.info("ProductService::listAmProductSplitDetail start listProductCode:{}",listProductCode);
		if(StringUtils.isEmpty(listProductCode) || listProductCode.size() == 0 || StringUtils.isEmpty(mapProductDetail))
		{
			return mapProductDetail;
		}
		try
		{
			List<AmProductSplitDetail> listBean = amProductDetailMapper.listProductSplitDetailByProductCode(listProductCode);
			for(AmProductSplitDetail detail:listBean)
			{
				List<AmProductSplitDetail> listDetail = mapProductDetail.get(detail.getProductCode());
				if(StringUtils.isEmpty(listDetail))
				{
					listDetail = new ArrayList<AmProductSplitDetail>();
					mapProductDetail.put(detail.getProductCode(), listDetail);
				}
				listDetail.add(detail);
			}
		}
		catch(Exception e)
		{
			logger.error("ProductService::getProductBaseInfo 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::getProductBaseInfo end mapProductDetail:{}",mapProductDetail);
		return mapProductDetail;
	}
	
	public Map<String,List<AmProductSplitHistoryPrice>> listAmProductSplitPrice(List<String> listProductCode,Map<String,List<AmProductSplitHistoryPrice>> mapProductPrice) throws RpcServiceException
	{
		logger.info("ProductService::listAmProductSplitPrice start listProductCode:{}",listProductCode);
		if(StringUtils.isEmpty(listProductCode) || listProductCode.size() == 0 || StringUtils.isEmpty(mapProductPrice))
		{
			return mapProductPrice;
		}
		try
		{
			List<AmProductSplitHistoryPrice> listBean = amProductPriceMapper.listProductSplitPriceByProductCode(listProductCode);
			for(AmProductSplitHistoryPrice price:listBean)
			{
				List<AmProductSplitHistoryPrice> listPrice = mapProductPrice.get(price.getProductCode());
				if(StringUtils.isEmpty(listPrice))
				{
					listPrice = new ArrayList<AmProductSplitHistoryPrice>();
					mapProductPrice.put(price.getProductCode(), listPrice);
				}
				listPrice.add(price);
			}
		}
		catch(Exception e)
		{
			logger.error("ProductService::listAmProductSplitPrice 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::listAmProductSplitPrice end mapProductPrice:{}",mapProductPrice);
		return mapProductPrice;
	}

	public List<AmProductSplitHistoryPrice> getAmProductSplitPrice(String productCode) throws RpcServiceException
	{
		logger.info("ProductService::getAmProductSplitPrice start listProductCode:{}",productCode);
		if(StringUtils.isEmpty(productCode))
		{
			return new ArrayList<>();
		}
		try
		{
			return amProductPriceMapper.getCurrentProductPrice(productCode);
		}
		catch(Exception e)
		{
			logger.error("ProductService::getAmProductSplitPrice 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
	}
	
	public ProductDTO getProductBaseInfo(String productCode) throws RpcServiceException
	{
		ProductDTO result = null;
		logger.info("ProductService::getProductBaseInfo start productCode:{}",productCode);
		try
		{
			AmProductSplit amProductSplit = new AmProductSplit();
			amProductSplit.setProductCode(productCode);
			amProductSplit.setDeletedFlag("N");
			AmProductSplit model = amProductMapper.selectOne(amProductSplit);
			model = getProductPrice(model);
			result =  ProductRpcConvert.convertBean(model);
		}
		catch(Exception e)
		{
			logger.error("ProductService::getProductBaseInfo 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::getProductBaseInfo end record:{}",result);
		return result;
	}
	
	public ProductDTO listProductMaterial(ProductDTO record) throws RpcServiceException
	{
		logger.info("ProductService::listProductMaterial start record:{}",record);
		
		try
		{
			List<Byte> listProductType = new ArrayList<Byte>();
			listProductType.add(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode());
			listProductType.add(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode());
			Example example = new Example(AmProductSplitDetail.class);
			example.createCriteria().andIn("productType", listProductType)
									.andEqualTo("productCode",record.getProductCode())
									.andEqualTo("deletedFlag","N");		
			List<AmProductSplitDetail> listProductDetail = amProductDetailMapper.selectByExample(example);
			
			//所有大类物料
			List<String> listBigMaterialCode = new ArrayList<String>();
			List<MaterialDTO> listMaterialDto = new ArrayList<MaterialDTO>();
			for(AmProductSplitDetail detail:listProductDetail)
			{
				if(detail.getType().equals(MaterialCategoryEnum.MATERIAL_BIG.getCode()))
				{
					listBigMaterialCode.add(detail.getMaterialCode());
					continue;
				}
				MaterialDTO dto = new MaterialDTO();
				dto.setMaterialCode(detail.getMaterialCode());
				dto.setMaterialName(detail.getMaterialName());
				listMaterialDto.add(dto);
			}
			
			//获取大类物料下所有小类物料
			if(listBigMaterialCode.size() > 0)
			{
				List<AmMaterialInfo> listAmMaterialInfo = amMaterialInfoMapper.listMaterialInfoByFirstCode(listBigMaterialCode);
				if(!StringUtils.isEmpty(listAmMaterialInfo))
				{
					for(AmMaterialInfo materialInfo:listAmMaterialInfo)
					{
						MaterialDTO dto = new MaterialDTO();
						dto.setMaterialCode(materialInfo.getMaterialCode());
						dto.setMaterialName(materialInfo.getMaterialName());
						listMaterialDto.add(dto);
					}
				}
			}
			
			record.setListMaterialDto(listMaterialDto);
		}
		catch(Exception e)
		{
			logger.error("ProductService::listProductMaterial 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::listProductMaterial end record:{}",record);
		return record;
	}
	
	public DisProductDTO listDisplayProductBase(DisProductDTO record) throws RpcServiceException
	{
		logger.info("ProductService::listDisplayProductBase start record:{}",record);
		List<String> listMerchantCode = getListMerchantCodeFromDealerUserInfo(record.getListDealerUserInfoDTO());
		List<Byte> listChannel = getListChannelCodeFromDealerUserInfo(record.getListDealerUserInfoDTO());
		AmProductSplit amProductSplit = new AmProductSplit();
		amProductSplit.setProductName(record.getContext());
		amProductSplit.setServiceType(record.getServiceType());
		amProductSplit.setListMerchantCode(listMerchantCode);
		amProductSplit.setListChannel(listChannel);
		amProductSplit.setPageStart((record.getPageNo()-1)*record.getPageSize());
		amProductSplit.setPageSize(record.getPageSize());
		amProductSplit.setDeletedFlag("N");
		amProductSplit.setPlusJrfk("N");
		try
		{
			List<AmProductSplit> listProduct = amProductMapper.selectDisProduct(amProductSplit);
			
			//去重
			List<AmProductSplit> listProductCodeFilt = new ArrayList<AmProductSplit>();
			Map<String,Integer> mapProduct = new HashMap<String,Integer>();
			for(AmProductSplit split:listProduct)
			{
				Integer bFind = mapProduct.get(split.getProductCode());
				if(!StringUtils.isEmpty(bFind))
				{
					continue;
				}
				mapProduct.put(split.getProductCode(), 1);
				listProductCodeFilt.add(split);
			}
			
			record.setListProductDTO(ProductRpcConvert.convertListBeanSpread(listProductCodeFilt, record.getListDealerUserInfoDTO()));
		}
		catch(Exception e)
		{
			logger.error("ProductService::listDisplayProductBase 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::listDisplayProductBase end record:{}",record);
		return record;
	}
	
	
	public DisProductDTO listDisplayProduct(DisProductDTO record) throws RpcServiceException
	{
		logger.info("ProductService::listDisplayProduct start record:{}",record);
		List<String> listMerchantCode = getListMerchantCodeFromDealerUserInfo(record.getListDealerUserInfoDTO());
		List<Byte> listChannel = getListChannelCodeFromDealerUserInfo(record.getListDealerUserInfoDTO());
		AmProductSplit amProductSplit = new AmProductSplit();
		amProductSplit.setProductName(record.getContext());
		amProductSplit.setServiceType(record.getServiceType());
		amProductSplit.setListMerchantCode(listMerchantCode);
		amProductSplit.setListChannel(listChannel);
		amProductSplit.setPageStart((record.getPageNo()-1)*record.getPageSize());
		amProductSplit.setPageSize(record.getPageSize());
		amProductSplit.setDeletedFlag("N");
		amProductSplit.setPlusJrfk("N");
		try
		{
			List<AmProductSplit> listProduct = amProductMapper.selectDisProduct(amProductSplit);
			listProduct = batchGetProductPrice(listProduct);
			record.setListProductDTO(ProductRpcConvert.convertListBeanSpread(listProduct, record.getListDealerUserInfoDTO()));
		}
		catch(Exception e)
		{
			logger.error("ProductService::listDisplayProduct 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("ProductService::listDisplayProduct end record:{}",record);
		return record;
	}
	
	private List<AmProductSplitDetail> spreadProductMatetrial(List<AmProductSplitDetail> listProductDetail)
	{
		//硬件物料
		List<AmProductSplitDetail> listHeadSplitDetail = new ArrayList<AmProductSplitDetail>();
		//大类物料
		List<String> listMetrialCode = new ArrayList<String>();
		Map<String,AmProductSplitDetail> mapMetrialProduct = new HashMap<String,AmProductSplitDetail>();
		if(StringUtils.isEmpty(listProductDetail))
		{
			return null;
		}
		for(AmProductSplitDetail productDetail:listProductDetail)
		{
			if(productDetail.getType().equals(MaterialCategoryEnum.MATERIAL_SMALL.getCode()))
			{
				listHeadSplitDetail.add(productDetail);
			}
			else
			{
				listMetrialCode.add(productDetail.getMaterialCode());
				mapMetrialProduct.put(productDetail.getMaterialCode(), productDetail);
			}
		}
		
		List<AmMaterialInfo> listMaterialInfo = listHardMaterialInfoByBelong(listMetrialCode);
		for(AmMaterialInfo materialInfo:listMaterialInfo)
		{
			AmProductSplitDetail findDetail = mapMetrialProduct.get(materialInfo.getFirstLevelName());
			if(!StringUtils.isEmpty(findDetail))
			{
				AmProductSplitDetail addDetail = new AmProductSplitDetail();
				JstUtils.copyObject(addDetail, findDetail);
				addDetail.setMaterialCode(materialInfo.getMaterialCode());
				addDetail.setMaterialName(materialInfo.getMaterialName());
				listHeadSplitDetail.add(addDetail);
			}
		}
		return listHeadSplitDetail;
		
	}
	
	//获取归属物料下的小物料
	private List<AmMaterialInfo> listHardMaterialInfoByBelong(List<String> listMaterialCode)
	{
		if(StringUtils.isEmpty(listMaterialCode) ||
				listMaterialCode.size() == 0)
		{
			return null;
		}
		List<Integer> listMaterialTypeId = new ArrayList<Integer>();
		listMaterialTypeId.add(47);
		listMaterialTypeId.add(46);
		Example example = new Example(AmMaterialInfo.class);
		example.createCriteria().andIn("materialTypeId", listMaterialTypeId)
								.andIn("firstLevelName", listMaterialCode);
		return amMaterialInfoMapper.selectByExample(example);
	}
	
	public boolean isMaterialBelongProduct(String productCode,String materialCode,Map<String,Byte> mapMaterialType)
	{	
		AmProductSplitDetail condition = new AmProductSplitDetail();
		condition.setProductCode(productCode);
		condition.setMaterialCode(materialCode);
		condition.setProductType(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()));
		AmProductSplitDetail detail = amProductDetailMapper.selectOne(condition);
		if(!StringUtils.isEmpty(detail))
		{
			if(!StringUtils.isEmpty(mapMaterialType))
			{
				mapMaterialType.put(materialCode, Byte.valueOf(detail.getProductType()));
			}
			return true;
		}
		condition.setProductType(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode()));
		detail = amProductDetailMapper.selectOne(condition);
		if(!StringUtils.isEmpty(detail))
		{
			if(!StringUtils.isEmpty(mapMaterialType))
			{
				mapMaterialType.put(materialCode, Byte.valueOf(detail.getProductType()));
			}
			return true;
		}
		AmMaterialInfo materialCondition = new AmMaterialInfo();
		materialCondition.setMaterialCode(materialCode);
		AmMaterialInfo material = amMaterialInfoMapper.selectOne(materialCondition);
		if(StringUtils.isEmpty(material))
		{
			return false;
		}
		if(StringUtils.isEmpty(material.getFirstLevelName()))
		{
			return false;
		}
		condition.setMaterialCode(material.getFirstLevelName());
		condition.setProductType(null);
		detail = amProductDetailMapper.selectOne(condition);
		if(!StringUtils.isEmpty(detail))
		{
			if(!StringUtils.isEmpty(mapMaterialType))
			{
				mapMaterialType.put(materialCode, Byte.valueOf(detail.getProductType()));
			}
			return true;
		}
		return false;
	}
	
	public Double getProductPriceBean(String productCode)
	{
		AmProductSplitHistoryPrice productPrice = amProductPriceMapper.selectCurrentProductPrice(productCode);
		return productPrice.getPrice();
	}
	
	private AmProductSplit getProductPrice(AmProductSplit product)
	{
		if(StringUtils.isEmpty(product))
		{
			return product;
		}
		Example example = new Example(AmProductSplitHistoryPrice.class);
		example.createCriteria().andEqualTo("productCode",product.getProductCode())
								.andEqualTo("type", (byte)0);
		List<AmProductSplitHistoryPrice> listPrice = amProductPriceMapper.selectByExample(example);
		Map<String,Integer> mapRepeated = new HashMap<String,Integer>();
		Double doublePrice = 0.0;
		Date dt = null;
		if(!StringUtils.isEmpty(listPrice))
		{
			for(AmProductSplitHistoryPrice price:listPrice)
			{
				dt = price.getTime();
				Integer inter = mapRepeated.get(price.getProductType());
				if(!StringUtils.isEmpty(inter))
				{
					continue;
				}
				mapRepeated.put(price.getProductType(), 1);
				doublePrice += price.getPrice();
			}
		}
		product.setPrice(doublePrice);
		product.setTime(dt);
		
		/*AmProductSplitHistoryPrice productPrice = amProductPriceMapper.selectCurrentProductPrice(product.getProductCode());
		if(!StringUtils.isEmpty(productPrice))
		{
			product.setPrice(productPrice.getPrice());
			product.setTime(productPrice.getTime());
		}*/
		return product;
	}
	
	private List<AmProductSplit> batchGetProductPrice(List<AmProductSplit> listProduct)
	{
		if(StringUtils.isEmpty(listProduct) || listProduct.size() == 0)
		{
			return null;
		}
		
		for(AmProductSplit product:listProduct)
		{
			Example example = new Example(AmProductSplitHistoryPrice.class);
			example.createCriteria().andEqualTo("productCode",product.getProductCode())
									.andEqualTo("type", (byte)0);
			List<AmProductSplitHistoryPrice> listPrice = amProductPriceMapper.selectByExample(example);
			Map<String,Integer> mapRepeated = new HashMap<String,Integer>();
			Double doublePrice = 0.0;
			Date dt = null;
			if(!StringUtils.isEmpty(listPrice))
			{
				for(AmProductSplitHistoryPrice price:listPrice)
				{
					dt = price.getTime();
					Integer inter = mapRepeated.get(price.getProductType());
					if(!StringUtils.isEmpty(inter))
					{
						continue;
					}
					mapRepeated.put(price.getProductType(), 1);
					doublePrice += price.getPrice();
				}
			}
			product.setPrice(doublePrice);
			product.setTime(dt);
		}
		return listProduct;
	}
	
	
	
	private List<String> getListMerchantCodeFromDealerUserInfo(List<BsDealerUserInfoDTO> listBsDealerUserInfo)
	{
		List<String> listMerchantCode = new ArrayList<String>();
		for(BsDealerUserInfoDTO bsDealerUserInfoDTO:listBsDealerUserInfo)
		{
			boolean bFind = false;
			for(String merchantCode:listMerchantCode)
			{
				if(merchantCode.equals(bsDealerUserInfoDTO.getMerchantCode()))
				{
					bFind = true;
					break;
				}
			}
			if(!bFind)
			{
				listMerchantCode.add(bsDealerUserInfoDTO.getMerchantCode());
			}
		}
		listMerchantCode.add("98765432");//补位方便查询
		return listMerchantCode;
	}
	
	private List<Byte> getListChannelCodeFromDealerUserInfo(List<BsDealerUserInfoDTO> listBsDealerUserInfo)
	{
		List<Byte> listChannel = new ArrayList<Byte>();
		for(BsDealerUserInfoDTO bsDealerUserInfoDTO:listBsDealerUserInfo)
		{
			boolean bFind = false;
			for(Byte channel:listChannel)
			{
				if(channel.equals(bsDealerUserInfoDTO.getChannel()))
				{
					bFind = true;
					break;
				}
			}
			if(!bFind)
			{
				listChannel.add(bsDealerUserInfoDTO.getChannel());
			}
		}
		listChannel.add(Byte.valueOf("1"));//补位方便查询
		return listChannel;
	}
	public List<AmProductSplitDetail> listProductSplitDetailsByProductCodes(List<String> listProductCodes){
		if(null == listProductCodes || listProductCodes.isEmpty()){
			return null;
		}
		Example example = new Example(AmProductSplitDetail.class);
		example.createCriteria().andIn("productCode", listProductCodes)
				.andEqualTo("deletedFlag", "N");
		return amProductDetailMapper.selectByExample(example);
	}

	public List<AmProductSplit> listProductSplitByProductCodes(List<String> listProductCodes){
		if(null == listProductCodes || listProductCodes.isEmpty()){
			return null;
		}
		Example example = new Example(AmProductSplit.class);
		example.createCriteria().andIn("productCode", listProductCodes)
				.andEqualTo("deletedFlag","N");
		return amProductMapper.selectByExample(example);
	}
}
