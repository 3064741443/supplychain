package cn.com.glsx.supplychain.jst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.glsx.supplychain.jst.mapper.GhMerchantBrandMapper;
import cn.com.glsx.supplychain.jst.model.GhMerchantBrand;

@Service
public class GhMerchantBrandService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GhMerchantBrandService.class);
	@Autowired
	private GhMerchantBrandMapper merchantBrandMapper;
	
	public List<GhMerchantBrand> listMerchantBrand(String merchantCode){
		if(null == merchantCode)
			return null;
		Example example = new Example(GhMerchantBrand.class);
		Criteria criteria = example.createCriteria().andEqualTo("merchantCode", merchantCode)
													.andEqualTo("deletedFlag", "N");
		return merchantBrandMapper.selectByExample(example);
	}
	
	public Integer countMerchantBrand(String merchantCode){
		Integer result = 0;
		if(null == merchantCode)
			return result;
		GhMerchantBrand condition = new GhMerchantBrand();
		condition.setMerchantCode(merchantCode);
		condition.setDeletedFlag("N");
		return merchantBrandMapper.selectCount(condition);
	}
	

}
