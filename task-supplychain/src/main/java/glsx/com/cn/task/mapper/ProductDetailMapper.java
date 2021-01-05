package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.ProductDetail;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface ProductDetailMapper extends OreMapper<ProductDetail> {
}