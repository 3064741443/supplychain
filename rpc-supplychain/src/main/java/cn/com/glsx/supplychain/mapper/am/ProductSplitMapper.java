package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.bs.Product;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductSplitMapper extends OreMapper<ProductSplit> {

    Page<ProductSplit> listProductSplit(ProductSplit productSplit);

    Page<ProductSplit> pageListProductSplit(ProductSplit productSplit);

    List<ProductSplit> getProductSplitList(ProductSplit productSplit);

    ProductSplit getProductSplitByid(Long id);

    int add(ProductSplit record);

    int updateById(ProductSplit record);

    int updateDeletedFlagById(ProductSplit record);

    Integer updateProductSplitStatus(ProductSplit record);

}