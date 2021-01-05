package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.ProductDetail;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductDetailMapper extends OreMapper<ProductDetail> {

    Integer updateById(ProductDetail productDetail);

    List<ProductDetail> getProductDetail(ProductDetail productDetail);
}