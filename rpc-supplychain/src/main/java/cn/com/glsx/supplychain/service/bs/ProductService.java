package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.ProductHistoryPriceEnum;
import cn.com.glsx.supplychain.mapper.bs.ProductDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.ProductHistoryPriceMapper;
import cn.com.glsx.supplychain.mapper.bs.ProductMapper;
import cn.com.glsx.supplychain.model.bs.Product;
import cn.com.glsx.supplychain.model.bs.ProductDetail;
import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductHistoryPriceMapper productHistoryPriceMapper;

    /**
     * 获取产品分页列表
     *
     * @param product 产品对象
     * @return
     */
    public List<Product> listProduct(int pageNum, int pageSize, Product product) {
        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, product);
        List<Product> result;
        PageHelper.startPage(pageNum, pageSize);
        result = productMapper.listProduct(product);
        ProductDetail productDetail = new ProductDetail();
/*        for (int i = 0; i < result.size(); i++) {
            productDetail.setProductCode( result.get(i).getCode());
            List<ProductDetail> list = productDetailMapper.select(productDetail);
            result.get(i).setProductDetailList(list);
        }*/
        return result;
    }

    /**
     * 获取产品列表
     *
     * @param product 产品对象
     * @return
     */
    public List<Product> getProductList(Product product) {
        LOGGER.info("查询产品列表入参:{}", product);
        List<Product> result;
        result = productMapper.getProductList(product);
        return result;
    }

    /**
     * 根据产品名称查询产品
     *
     * @param name 产品名称
     * @return
     */
    public Product getProductByName(String name) {
        LOGGER.info("查询产品列表入参:{}", name);
        Product result = new Product();
        result.setName(name);
        result.setDeletedFlag("N");
        result = productMapper.selectOne(result);
        return result;
    }


    /**
     * 根据产品编号获取产品
     *
     * @param productCode 产品编号
     * @return
     */
    public Product getProductByProductCode(String productCode) {
        LOGGER.info("根据产品编号获取产品查询参数为:{}。", productCode);
        Product result;
        Product product = new Product();
        product.setCode(productCode);
        product.setDeletedFlag("N");
        result = productMapper.getProduct(product);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductCode(productCode);
        List<ProductDetail> productDetails;
        productDetails = productDetailMapper.select(productDetail);
        result.setProductDetailList(productDetails);
        //根据产品编号获取产品价格表
        List<ProductHistoryPrice>productHistoryPriceList;
        ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
        productHistoryPrice.setProductCode(productCode);
        productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
        productHistoryPrice.setDeletedFlag("N");
        productHistoryPriceList = productHistoryPriceMapper.select(productHistoryPrice);
        if(productHistoryPriceList != null && productHistoryPriceList.size() > 0){
            result.setProductHistoryPriceList(productHistoryPriceList);
        }
        return result;
    }


    /**
     * 新增产品
     *
     * @param product 产品对象
     * @return 成功条数
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer add(Product product) {
        LOGGER.info("新增产品参数为:{}。", product);
        Integer result;
        Product product1 = this.getProductByName(product.getName());
        if (product1 != null) {
            LOGGER.error("产品名字重复");
            throw new RpcServiceException(ErrorCodeEnum.ERRCODE_PRODUCT_NAME_REPEATED);
        }
        product.setCreatedDate(new Date());
        product.setUpdatedDate(new Date());
        product.setDeletedFlag("N");
        result = productMapper.insertProduct(product);
        /*Product data = new Product();
        data.setCode(product.getId() + "");
        data.setId(product.getId());
        productMapper.updateById(data);
        product.setCode(product.getId() + "");*/
        //产品明细插入
        List<ProductDetail> list = product.getProductDetailList();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setProductCode(product.getCode());
            list.get(i).setCreatedBy(product.getCreatedBy());
            list.get(i).setCreatedDate(new Date());
            list.get(i).setUpdatedBy(product.getUpdatedBy());
            list.get(i).setUpdatedDate(new Date());
            list.get(i).setDeletedFlag("N");
        }
        productDetailMapper.insertList(list);
        //产品价格插入
        Date date = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sp.format(date);
        try {
            date = sp.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
        productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
        productHistoryPrice.setProductCode(product.getCode());
        productHistoryPrice.setPrice(product.getAmount());
        productHistoryPrice.setTime(date);
        productHistoryPrice.setCreatedBy(product.getUpdatedBy());
        productHistoryPrice.setCreatedDate(new Date());
        productHistoryPrice.setUpdatedBy(product.getUpdatedBy());
        productHistoryPrice.setUpdatedDate(new Date());
        productHistoryPrice.setDeletedFlag("N");
        productHistoryPriceMapper.insert(productHistoryPrice);
        return result;
    }

    /**
     * 修改
     *
     * @param product 产品对象
     * @return 成功条数
     */
    public Integer updateById(Product product) {
        LOGGER.info("修改产品参数为 ：{}。", product);
        Integer result;
        result = productMapper.updateById(product);
        return result;
    }

    /**
     * 修改产品价格
     *
     * @param code
     * @param amount    金额
     * @param updatedBy
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer updateProductPriceByCode(String code, Double amount, String updatedBy, Date time) {
        LOGGER.info("修改产品价格的code:{},修改后的价格:{}", code, amount);
        Integer result;
        Product product = new Product();
        product.setCode(code);
        product.setUpdatedBy(updatedBy);
        product.setUpdatedDate(new Date());
        result = productMapper.updateByCode(product);

        ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
        productHistoryPrice.setProductCode(product.getCode());
        productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_TOMORROW.getCode());
        ProductHistoryPrice historyPrice = productHistoryPriceMapper.getProductHistoryPriceByCodeOrType(productHistoryPrice);
        if (historyPrice != null) {
            historyPrice.setDeletedFlag("Y");
            historyPrice.setUpdatedBy(product.getUpdatedBy());
            historyPrice.setUpdatedDate(new Date());
            productHistoryPriceMapper.updateById(historyPrice);
        }

        productHistoryPrice = new ProductHistoryPrice();
        productHistoryPrice.setProductCode(product.getCode());
        productHistoryPrice.setPrice(amount);
        productHistoryPrice.setTime(time);
        productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_TOMORROW.getCode());
        productHistoryPrice.setCreatedBy(product.getUpdatedBy());
        productHistoryPrice.setCreatedDate(new Date());
        productHistoryPrice.setUpdatedBy(product.getUpdatedBy());
        productHistoryPrice.setUpdatedDate(new Date());
        productHistoryPrice.setDeletedFlag("N");
        productHistoryPriceMapper.insert(productHistoryPrice);
        return result;
    }

    /**
     * 根据产品编号获取产品
     *
     * @param productCode 产品编号
     * @return
     */
    public List<ProductDetail> getProductDetailByProductCode(String productCode) {
        LOGGER.info("查询产品详情参数为 ：{}。", productCode);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductCode(productCode);
        List<ProductDetail> result;
        result = productDetailMapper.select(productDetail);
        return result;
    }
    
    public Integer batchSubmitBsProduct(List<Product> listProduct)
    {
    	return productMapper.insertList(listProduct);
    }
    
    public Integer batchSubmitBsProductDetail(List<ProductDetail> listProductDetail) 
    {
    	return productDetailMapper.insertList(listProductDetail);
    }
    
    public Integer batchSubmitBsProductPrice(List<ProductHistoryPrice> listProductPrice)
    {
    	return productHistoryPriceMapper.insertList(listProductPrice);
    }
}
