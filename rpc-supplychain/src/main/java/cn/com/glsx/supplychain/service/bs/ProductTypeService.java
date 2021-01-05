package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.ProductTypeMapper;
import cn.com.glsx.supplychain.model.bs.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductTypeService
 * @Author admin
 * @Param
 * @Date 2019/5/29 13:51
 * @Version
 **/
@Service
public class ProductTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductTypeMapper productTypeMapper;

    /**
     *  查询产品类型信息List
     */
    public List<ProductType>getProductTypeList(ProductType productType){
        LOGGER.info("查询产品类型List入参:{}", productType);
        List<ProductType>result;
        result = productTypeMapper.getProductTypeList(productType);
        return result;
    }

    /**
     *  查询产品类型信息
     */
    public ProductType getProductType(ProductType productType){
        LOGGER.info("查询产品类型入参:{}", productType);
        ProductType result;
        result = productTypeMapper.getProductType(productType);
        return result;
    }


}
