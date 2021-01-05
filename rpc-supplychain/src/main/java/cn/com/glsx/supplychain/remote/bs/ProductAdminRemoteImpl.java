package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.bs.Product;
import cn.com.glsx.supplychain.model.bs.ProductDetail;
import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import cn.com.glsx.supplychain.model.bs.ProductType;
import cn.com.glsx.supplychain.service.bs.ProductDetailService;
import cn.com.glsx.supplychain.service.bs.ProductHistoryPriceService;
import cn.com.glsx.supplychain.service.bs.ProductService;
import cn.com.glsx.supplychain.service.bs.ProductTypeService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: ProductAdminRemote.java
 * @Description: 产品管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("ProductAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class ProductAdminRemoteImpl implements ProductAdminRemote {


    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAdminRemoteImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductHistoryPriceService productHistoryPriceService;

    @Autowired
    private ProductTypeService productTypeService;

    @Override
    public RpcResponse<RpcPagination<Product>> listProduct(RpcPagination<Product> pagination) {
        RpcResponse<RpcPagination<Product>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(productService.listProduct(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<Product>> getProductList(Product product) {
        RpcResponse<List<Product>> result;
        try {
            RpcAssert.assertNotNull(product, DefaultErrorEnum.PARAMETER_NULL, "product must not be null");
            result = RpcResponse.success(productService.getProductList(product));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Product> getProductByProductCode(String productCode) {
        try {
            RpcAssert.assertNotNull(productCode, DefaultErrorEnum.PARAMETER_NULL, "productCode must not be null");
            Product product = productService.getProductByProductCode(productCode);
            if (!StringUtils.isEmpty(product)) {
                product.setProductDetailList(productDetailService.getProductDetailListByProductCode(productCode));
            }
            return RpcResponse.success(product);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<ProductHistoryPrice>> getProductHistoryPriceByProductCode(String productCode) {
        try {
            RpcAssert.assertNotNull(productCode, DefaultErrorEnum.PARAMETER_NULL, "productCode must not be null");
            return RpcResponse.success(productHistoryPriceService.getProductHistoryPriceByProductCode(productCode));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<ProductHistoryPrice>> getProductHistoryPrice(String productCode, Date date) {
        try {
            RpcAssert.assertNotNull(productCode, DefaultErrorEnum.PARAMETER_NULL, "productCode must not be null");
            RpcAssert.assertNotNull(date, DefaultErrorEnum.PARAMETER_NULL, "date must not be null");
            return RpcResponse.success(productHistoryPriceService.getProductHistoryPrice(productCode, date));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> add(Product product) {
        try {
            RpcAssert.assertNotNull(product, DefaultErrorEnum.PARAMETER_NULL, "Product must not be null");
            return RpcResponse.success(productService.add(product));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateById(Product product) {
        try {
            RpcAssert.assertNotNull(product, DefaultErrorEnum.PARAMETER_NULL, "Product must not be null");
            return RpcResponse.success(productService.updateById(product));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateProductPriceByCode(String code, Double amount, String updatedBy,Date time) {
        try {
            RpcAssert.assertNotNull(code, DefaultErrorEnum.PARAMETER_NULL, "code must not be null");
            RpcAssert.assertNotNull(amount, DefaultErrorEnum.PARAMETER_NULL, "amount must not be null");
            RpcAssert.assertNotNull(updatedBy, DefaultErrorEnum.PARAMETER_NULL, "updatedBy must not be null");
            RpcAssert.assertNotNull(updatedBy, DefaultErrorEnum.PARAMETER_NULL, "time must not be null");
            return RpcResponse.success(productService.updateProductPriceByCode(code, amount, updatedBy, time));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<ProductDetail>> getProductDetailByProductCode(String productCode) {
        try{
            RpcAssert.assertNotNull(productCode, DefaultErrorEnum.PARAMETER_NULL, "productCode must not be null");
            return RpcResponse.success(productService.getProductDetailByProductCode(productCode));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<ProductType>> getProductTypeList(ProductType productType) {
        try {
            RpcAssert.assertNotNull(productType, DefaultErrorEnum.PARAMETER_NULL, "productType must not be null");
            return  RpcResponse.success(productTypeService.getProductTypeList(productType));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<ProductType> getProductType(ProductType productType) {
        try {
            RpcAssert.assertNotNull(productType, DefaultErrorEnum.PARAMETER_NULL, "productType must not be null");
            return  RpcResponse.success(productTypeService.getProductType(productType));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

}
