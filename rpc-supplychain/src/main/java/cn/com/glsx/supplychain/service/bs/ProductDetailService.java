package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.ProductDetailMapper;
import cn.com.glsx.supplychain.model.bs.ProductDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductDetailMapper productDetailMapper;

    /**
     * 根据产品编号获取产品详情
     *
     * @param productCode 产品编号
     * @return
     */
    public List<ProductDetail> getProductDetailListByProductCode(String productCode) {
        LOGGER.info("根据产品编号获取产品详情的入参:{}", productCode);
        List<ProductDetail> result;
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductCode(productCode);
        productDetail.setDeletedFlag("N");
        result = productDetailMapper.select(productDetail);
        return result;
    }
}
