package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.ProductType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductTypeMapper extends OreMapper<ProductType> {
    List<ProductType> getProductTypeList(ProductType productType);

    ProductType getProductType(ProductType productType);

    Integer updateById(ProductType productType);

}