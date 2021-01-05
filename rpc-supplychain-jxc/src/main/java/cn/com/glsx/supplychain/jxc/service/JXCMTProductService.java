package cn.com.glsx.supplychain.jxc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.glsx.supplychain.jxc.enums.JXCMaterialTypeEnum;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTProductDetailMapper;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTProductMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTProduct;
import cn.com.glsx.supplychain.jxc.model.JXCMTProductDetail;

@Service
public class JXCMTProductService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTProductService.class);
	@Autowired
	private JXCMTProductMapper jxcmtProductMapper;
	@Autowired
	private JXCMTProductDetailMapper jxcmtProductDetailMapper;
	
	public Integer insertListJxcmtProduct(List<JXCMTProduct> listJxcmtProdct){
		if(null == listJxcmtProdct || listJxcmtProdct.isEmpty()){
			return 0;
		}
		return jxcmtProductMapper.insertList(listJxcmtProdct);
	}
	
	public Integer insertListJxcmtProductDetail(List<JXCMTProductDetail> listJxcmtProductDetail){
		if(null == listJxcmtProductDetail || listJxcmtProductDetail.isEmpty()){
			return 0;
		}
		return jxcmtProductDetailMapper.insertList(listJxcmtProductDetail);
	}
	
	public JXCMTProduct getBsProductByProductCode(String productCode){
		JXCMTProduct condition = new JXCMTProduct();
		condition.setCode(productCode);
		return jxcmtProductMapper.selectOne(condition);
	}
	
	public JXCMTProductDetail getBsProductDetailByProductCode(String productCode){
		Example example = new Example(JXCMTProductDetail.class);
		example.createCriteria().andEqualTo("productCode", productCode)
								.andEqualTo("deletedFlag", "N");
		List<JXCMTProductDetail> listProductDetail = jxcmtProductDetailMapper.selectByExample(example);
		if(null == listProductDetail || listProductDetail.isEmpty()){
			return null;
		}
		for(JXCMTProductDetail detail:listProductDetail){
			if(detail.getType().equals(JXCMaterialTypeEnum.MATERIAL_TYPE_HARD.getCode())){
				return detail;
			}
			if(detail.getType().equals(JXCMaterialTypeEnum.MATERIAL_TYPE_PART.getCode())){
				return detail;
			}
		}
		return null;
	}
	
	public Map<String,JXCMTProductDetail> getBsProductDetailByProductCode(List<String> listPorductCodes){
		Map<String,JXCMTProductDetail> mapResult = new HashMap<>();
		Example example = new Example(JXCMTProductDetail.class);
		example.createCriteria().andIn("productCode", listPorductCodes)
								.andEqualTo("type",JXCMaterialTypeEnum.MATERIAL_TYPE_HARD);					
		Criteria criteria_7 = example.createCriteria();
		criteria_7.andIn("productCode", listPorductCodes);
		criteria_7.andEqualTo("type",JXCMaterialTypeEnum.MATERIAL_TYPE_PART);
		example.or(criteria_7);
		List<JXCMTProductDetail> listProductDetail = jxcmtProductDetailMapper.selectByExample(example);
		if(null == listProductDetail){
			return mapResult;
		}
		for(JXCMTProductDetail detail:listProductDetail){
			mapResult.put(detail.getProductCode(), detail);
		}
		return mapResult;
	}
	
	public Integer updateProductDetail(JXCMTProductDetail productDetail){
		return jxcmtProductDetailMapper.updateByPrimaryKey(productDetail);
	}
}
