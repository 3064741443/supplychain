package glsx.com.cn.task.mapper;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.Product;

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