package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.ProductHistoryPriceMapper;
import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductHistoryPriceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductHistoryPriceService.class);

    @Autowired
    private ProductHistoryPriceMapper productHistoryPriceMapper;

    /**
     * 新增产品历史价格
     *
     * @param productHistoryPrice 产品历史价格对象
     * @return
     */
    public Integer add(ProductHistoryPrice productHistoryPrice) {
        LOGGER.info("增加产品历史价格表", productHistoryPrice);
        Integer result;
        productHistoryPrice.setTime(new Date());
        productHistoryPrice.setCreatedDate(new Date());
        productHistoryPrice.setUpdatedDate(new Date());
        result = productHistoryPriceMapper.insert(productHistoryPrice);
        return result;
    }

    /**
     * 获取产品历史价格
     *
     * @param code 产品code
     * @return
     */
    public List<ProductHistoryPrice> getProductHistoryPriceByProductCode(String code) {
        LOGGER.info("产品历史价格查询的入参code:{}", code);
        List<ProductHistoryPrice> result;
        ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
        productHistoryPrice.setProductCode(code);
        productHistoryPrice.setDeletedFlag("N");
        result = productHistoryPriceMapper.select(productHistoryPrice);
        return result;
    }

    public List<ProductHistoryPrice> getProductHistoryPrice(String productCode, Date date){
        LOGGER.info("根据时间产品历史价格查询的入参code:{},date:{}", productCode,date);
        List<ProductHistoryPrice> result;
        if(date != null){
            result = productHistoryPriceMapper.getProductHistoryPrice(productCode,date);
        }else {
            result = productHistoryPriceMapper.getProductHistoryPriceByCode(productCode);
        }
        return result;
    }

}
