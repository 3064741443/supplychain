package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.ProductSplit;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductSplitMapper extends OreMapper<ProductSplit> {

    List<ProductSplit> listProductSplit(ProductSplit productSplit);

    int add(ProductSplit record);

    int updateById(ProductSplit record);

    List<ProductSplit> getProductSplitByServiceTypeThreeFour(ProductSplit productSplit);

}