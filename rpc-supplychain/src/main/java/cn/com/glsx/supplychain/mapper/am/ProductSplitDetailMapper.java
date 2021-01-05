package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface ProductSplitDetailMapper extends OreMapper<ProductSplitDetail> {
    Page<ProductSplitDetail> listProductSplitDetail(ProductSplitDetail record);

    Page<ProductSplitDetail> getlistProductSplitDetail(ProductSplitDetail record);

    List<ProductSplitDetail> getProductSplitDetailByProductTypeZeroList(@Param("productSplitDetailList") List<ProductSplitDetail> productSplitDetailList);

    List<ProductSplitDetail> getProductSplitDetailByProductTypeList(@Param("productSplitDetail") List<ProductSplitDetail> productSplitDetail);

    int add(ProductSplitDetail record);

    int updateById(ProductSplitDetail record);

    int updateByProductCode(ProductSplitDetail record);

    int batchInsertOnDuplicateKeyUpdateProductSplitDetail(List<ProductSplitDetail> productSplitDetailList);
    
    List<String> listProductCodesByMaterialCode(@Param("materialCode") String materialCode);
    
}