package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface ProductSplitDetailMapper extends OreMapper<ProductSplitDetail> {

    int add(ProductSplitDetail record);

    int updateById(ProductSplitDetail record);

}