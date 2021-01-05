package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDetailDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitListQueryDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCMaterialTypeEnum;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTProductSplitDetailMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTProductSplitMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTProductSplitPriceMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTProductSplit;
import cn.com.glsx.supplychain.jxc.model.JXCMTProductSplitDetail;
import cn.com.glsx.supplychain.jxc.model.JXCMTProductSplitPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JXCMTProductSplitService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTProductSplitService.class);
	@Autowired
	private JXCMTProductSplitMapper jxcmtProductSplitMapper;
	@Autowired
	private JXCMTProductSplitDetailMapper jxcmtProductSplitDetailMapper;
	@Autowired
	private JXCMTProductSplitPriceMapper jxcmtProductSplitPriceMapper;
	@Autowired
	private JXCMTBsDealerUserInfoService jxcmtBsDealerUserInfoService;
	@Autowired
	private JXCMTAttribInfoService jxcmAttribInfoService;
	
	public JXCMTProductSplit getProductSplitById(Integer id){
		JXCMTProductSplit condition = new JXCMTProductSplit();
		condition.setId(id);
		return jxcmtProductSplitMapper.selectOne(condition);
	}
	
	public JXCMTProductSplit getProductSplitByProductCode(String productCode){
		JXCMTProductSplit condition = new JXCMTProductSplit();
		condition.setProductCode(productCode);
		condition.setDeletedFlag("N");
		return jxcmtProductSplitMapper.selectOne(condition);
	}
	
	public JXCMTProductSplitDTO convertProductSplitDto(JXCMTProductSplit record){
		record = getProductPrice(record);
		return this.getProductSplitDtoFromProductSplit(record, null, null);
	}
	
	public List<JXCMTProductSplitDTO> listJxcProduct(JXCMTProductSplitListQueryDTO record){
		List<JXCMTProductSplitDTO> listResult = new ArrayList<>();
		JXCMTProductSplit condition = new JXCMTProductSplit();
		condition.setMerchantCode(record.getMerchantCode());
		condition.setChannel(this.getChannelByMerchantCode(record.getMerchantCode()));
		condition.setServiceType(jxcmAttribInfoService.getDbProductTypeFromAttribInfo(record.getProductTypeId()));
		condition.setProductName(record.getProductName());
		condition.setProductCode(record.getProductCode());
		condition.setMaterialCode(record.getMaterialCode());
		condition.setMaterialName(record.getMaterialName());
		condition.setDeletedFlag("N");
		List<JXCMTProductSplit> listProduct = jxcmtProductSplitMapper.listJxcProductSplit(condition);
		if(null == listProduct || listProduct.isEmpty()){
			return listResult;
		}
		List<String> listProductCodes = listProduct.stream().map(JXCMTProductSplit::getProductCode).collect(Collectors.toList());
		List<JXCMTProductSplitDetail> listProductDetail = this.listProductSplitDetailsByProductCodes(listProductCodes);
		if(StringUtils.isEmpty(listProductDetail)){
			return listResult;
		}
		listProduct = this.batchGetProductPrice(listProduct, listProductCodes);
		return generateProductSplitDto(listProduct,listProductDetail,listResult);
	}
	
	private List<JXCMTProductSplitDTO> generateProductSplitDto(List<JXCMTProductSplit> listProduct,
			List<JXCMTProductSplitDetail> listProductDetail,
			List<JXCMTProductSplitDTO> listResult){
		Map<String,Integer> mapRepeat = new HashMap<>();
		Integer flag = null;
		String strKey = "";
		Integer count = 0;
		List<JXCMTProductSplitDetail> listProductDetails = null;
		JXCMTProductSplitDTO productSplitDto = null;
		JXCMTProductSplitDetailDTO productSplitDetailDto = null;
		List<JXCMTProductSplitDetailDTO> listProductSplitDetailDto = null;
		Map<Integer,String> mapLocalProductType = new HashMap<>();
		Map<Integer,String> mapLocalMerchantChannel = new HashMap<>();
		Map<String,List<JXCMTProductSplitDetail>> mapProductDetail = listProductDetail.stream().collect(Collectors.groupingBy(JXCMTProductSplitDetail::getProductCode));
		for(JXCMTProductSplit product:listProduct){
			strKey = "p:" + product.getProductCode();
			listProductDetails = mapProductDetail.get(product.getProductCode());
			if(null == listProductDetails || listProductDetails.isEmpty()){
				continue;
			}
			count = 0;
			for(JXCMTProductSplitDetail detail:listProductDetails){
				if(detail.getProductType().equals(String.valueOf(JXCMaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()))){
					strKey += "m1:";
					strKey += detail.getMaterialCode();
				}else if(detail.getProductType().equals(String.valueOf(JXCMaterialTypeEnum.MATERIAL_TYPE_PART.getCode()))){
					strKey += "m2:";					
					strKey += detail.getMaterialCode();
				}
			}
			flag = mapRepeat.get(strKey);
			if(null != flag){
				continue;
			}
			mapRepeat.put(strKey, 1);
			productSplitDto = getProductSplitDtoFromProductSplit(product,mapLocalProductType,mapLocalMerchantChannel);
			listProductSplitDetailDto = new ArrayList<>();
			for(JXCMTProductSplitDetail detail:listProductDetails){
				if(detail.getProductType().equals(String.valueOf(JXCMaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()))
						|| detail.getProductType().equals(String.valueOf(JXCMaterialTypeEnum.MATERIAL_TYPE_PART.getCode()))){
					listProductSplitDetailDto.add(getProductSplitDetailDtoFromProductSplitDetail(detail));
				}
			}
			productSplitDto.setListProductSplitDetailDto(listProductSplitDetailDto);
			listResult.add(productSplitDto);
		}
		return listResult;
	}
	
	private JXCMTProductSplitDetailDTO getProductSplitDetailDtoFromProductSplitDetail(JXCMTProductSplitDetail productDetail){
		JXCMTProductSplitDetailDTO dto = new JXCMTProductSplitDetailDTO();
		dto.setMaterialCode(productDetail.getMaterialCode());
		dto.setMaterialName(productDetail.getMaterialName());
		dto.setProductCode(productDetail.getProductCode());
		dto.setPropQuantity(productDetail.getPropQuantity());
		return dto;
	}
	
	private JXCMTProductSplitDTO getProductSplitDtoFromProductSplit(JXCMTProductSplit product,
			Map<Integer,String> mapLocalProductType,
			Map<Integer,String> mapLocalMerchantChannel){
		JXCMTProductSplitDTO dto = new JXCMTProductSplitDTO();
		dto.setProductCode(product.getProductCode());
		dto.setProductName(product.getProductName());
		dto.setProductTypeId(jxcmAttribInfoService.getProductTypeFromDbProduct(product.getServiceType()));
		dto.setProductTypeName(jxcmAttribInfoService.getProductTypeNameById(dto.getProductTypeId(), mapLocalProductType));
		dto.setMerchantCode(product.getMerchantCode());
		dto.setMerchantName(product.getMerchantName());
		if(product.getChannel()!=null) {
			dto.setChannelId(jxcmAttribInfoService.getMerchantChannelFromDbMerchantChannel(product.getChannel()));
		}
		if(dto.getChannelId()!=null) {
			dto.setChannelName(jxcmAttribInfoService.getMerchantChannelNameById(dto.getChannelId(), mapLocalMerchantChannel));
		}
		dto.setAlias(product.getAlias());
		dto.setDeviceQuantity(product.getDeviceQuantity());
		dto.setServiceTime(product.getServiceTime());
		dto.setPackageOne(product.getPackageOne());
		dto.setUnitPrice(product.getProductPrice());
		if(product.getSaleMode()!=null) {
			dto.setSaleMode(this.getDtoSaleModeFromDb(product.getSaleMode()));
		}
		return dto;
	}
	
	public JXCMTProductSplit getProductPrice(JXCMTProductSplit product){
		if(null == product){
			return product;
		}
		Example example = new Example(JXCMTProductSplitPrice.class);
		example.createCriteria().andEqualTo("productCode", product.getProductCode())
								.andEqualTo("type",(byte)0)
								.andEqualTo("deletedFlag","N");
		List<JXCMTProductSplitPrice> listProductPrice = jxcmtProductSplitPriceMapper.selectByExample(example);
		if(null == listProductPrice || listProductPrice.isEmpty()){
			return product;
		}
		Double productPrice = 0.0;
		Integer repeatFlag = null;
		Map<String,Integer> mapProductType = new HashMap<>();
		for(JXCMTProductSplitPrice price:listProductPrice){
			repeatFlag = mapProductType.get(price.getProductCode());
			if(null != repeatFlag){
				continue;
			}
			mapProductType.put(price.getProductType(), 1);
			productPrice += price.getPrice();
		}
		product.setProductPrice(productPrice);
		return product;
	}
	
	public List<JXCMTProductSplit> batchGetProductPrice(List<JXCMTProductSplit> listProduct,List<String> listProductCodes){
		if(null == listProduct || listProduct.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTProductSplitPrice.class);
		example.createCriteria().andIn("productCode", listProductCodes)
								.andEqualTo("type",(byte)0)
								.andEqualTo("deletedFlag","N");
		List<JXCMTProductSplitPrice> listProductPrice = jxcmtProductSplitPriceMapper.selectByExample(example);
		if(null == listProductPrice || listProductPrice.isEmpty()){
			return null;
		}
		Map<String,List<JXCMTProductSplitPrice>> mapProductPrice = listProductPrice.stream().collect(Collectors.groupingBy(JXCMTProductSplitPrice::getProductCode));
		Double productPrice = 0.0;
		Integer repeatFlag = null;
		Map<String,Integer> mapProductType = new HashMap<>();
		List<JXCMTProductSplitPrice> listPrice = null;
		for(JXCMTProductSplit product:listProduct){
			listPrice = mapProductPrice.get(product.getProductCode());
			if(null == listPrice){
				continue;
			}
			productPrice = 0.0;
			mapProductType.clear();
			for(JXCMTProductSplitPrice price:listPrice){
				repeatFlag = mapProductType.get(price.getProductType());
				if(null != repeatFlag){
					continue;
				}
				mapProductType.put(price.getProductType(), 1);
				if(!StringUtils.isEmpty(price.getPrice())){
					productPrice += price.getPrice();
				}
			}
			product.setProductPrice(productPrice);
		}
		return listProduct;
	}
	
	
	public List<JXCMTProductSplitDetail> listProductSplitDetailsByProductCodes(List<String> listProductCodes){
		if(null == listProductCodes || listProductCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTProductSplitDetail.class);
		example.createCriteria().andIn("productCode", listProductCodes)
								.andEqualTo("deletedFlag", "N");				
		return jxcmtProductSplitDetailMapper.selectByExample(example);
	}
	
	public List<JXCMTProductSplit> listProductSplitByProductCodes(List<String> listProductCodes){
		if(null == listProductCodes || listProductCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTProductSplit.class);
		example.createCriteria().andIn("productCode", listProductCodes)
								.andEqualTo("deletedFlag","N");
		return jxcmtProductSplitMapper.selectByExample(example);
	}
	
	public List<JXCMTProductSplitPrice> listProductSplitPriceByProductCodes(List<String> listProductCodes){
		if(null == listProductCodes || listProductCodes.isEmpty()){
			return null;
		}
		Example example = new Example(JXCMTProductSplitPrice.class);
		example.createCriteria().andIn("productCode", listProductCodes)
								.andEqualTo("type", 0)
								.andEqualTo("deletedFlag","N");
		return jxcmtProductSplitPriceMapper.selectByExample(example);
	}
	
	private Byte getChannelByMerchantCode(String merchantCode){
		return jxcmtBsDealerUserInfoService.getChannelByMerchantCode(merchantCode);
	}
	
	private String getDtoSaleModeFromDb(Byte saleMode){
		if(saleMode.byteValue() == 1){
			return "S";
		}else if(saleMode.byteValue() == 2){
			return "D";
		}
		return null;
	}
	
	
	
	
}
