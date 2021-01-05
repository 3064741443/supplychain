package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.MaterialInfo;
import cn.com.glsx.supplychain.model.am.ProductMerchantHide;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductMerchantHideMapper extends OreMapper<ProductMerchantHide> {

    int batchInsertOnDuplicateKeyUpdateProductMerchantHide(List<ProductMerchantHide> productMerchantHidelist);


}