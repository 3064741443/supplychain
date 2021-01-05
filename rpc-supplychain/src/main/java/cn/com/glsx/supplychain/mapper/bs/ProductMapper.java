package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.Product;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductMapper extends OreMapper<Product> {
    Page<Product> listProduct(Product product);

    List<Product> getProductList(Product product);

    Product getProduct(Product product);

    Integer updateById(Product product);

    Integer updateByCode(Product product);

    Integer insertProduct(Product product);
}