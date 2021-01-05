package cn.com.glsx.supplychain.service.am;

import cn.com.glsx.supplychain.enums.ProductHistoryPriceEnum;
import cn.com.glsx.supplychain.mapper.am.ProductSplitDetailMapper;
import cn.com.glsx.supplychain.mapper.am.ProductSplitHistoryPriceMapper;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import cn.com.glsx.supplychain.model.bs.Product;
import cn.com.glsx.supplychain.util.StringUtil;

import com.alibaba.dubbo.rpc.RpcException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ProductSplitHistoryPriceService
 * @Author admin
 * @Param
 * @Date 2019/9/17 9:58
 * @Version 1.0
 **/
@Service
@Transactional
public class ProductSplitHistoryPriceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductSplitHistoryPriceMapper productSplitHistoryPriceMapper;

    @Autowired
    private ProductSplitDetailMapper productSplitDetailMapper;

    /**
     * 获取产品拆分历史价格Info
     *
     * @param productSplitHistoryPrice 产品拆分历史价格对象
     * @return
     */
    public ProductSplitHistoryPrice getProductSplitHistoryPriceInfo(ProductSplitHistoryPrice productSplitHistoryPrice) {
        logger.info("查询产品拆分历史价格列表入参:{}", productSplitHistoryPrice);
        ProductSplitHistoryPrice result;
        result = productSplitHistoryPriceMapper.selectOne(productSplitHistoryPrice);
        return result;
    }

    /**
     * 获取产品拆分历史价格List
     *
     * @param productSplitHistoryPrice 产品拆分历史价格对象
     * @return
     */
    public List<ProductSplitHistoryPrice> getProductSplitHistoryPriceList(ProductSplitHistoryPrice productSplitHistoryPrice) {
        logger.info("查询产品拆分历史价格列表List入参:{}", productSplitHistoryPrice);
        List<ProductSplitHistoryPrice> result;
        result = productSplitHistoryPriceMapper.select(productSplitHistoryPrice);
        return result;
    }

    /**
     * 新增产品拆分历史价格信息
     *
     * @param productSplitHistoryPrice 产品拆分历史价格对象
     * @return 返回成功条数
     */
    public Integer add(ProductSplitHistoryPrice productSplitHistoryPrice){
        logger.info("新增产品拆分历史价格入参:{}", productSplitHistoryPrice);
        Integer result;
        result = productSplitHistoryPriceMapper.add(productSplitHistoryPrice);
        return result;
    }

    /**
     * 新增产品拆分历史价格信息List
     *
     * @param productSplitHistoryPriceList 产品拆分历史价格对象List
     * @return 返回成功条数
     */
    public Integer addProductSplitHistoryPriceList(List<ProductSplitHistoryPrice> productSplitHistoryPriceList) throws Exception {
        logger.info("新增产品拆分历史价格入参:=" + productSplitHistoryPriceList);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        List<ProductSplitHistoryPrice> productSplitHistoryPriceTpyeByOtherList = new ArrayList<>();
        Integer result;
        Double priceZero = 0.0;
        Double priceSeven = 0.0;
        Double taxRateZero = 0.0;
        Double taxRateSeven = 0.0;
        if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
            ProductSplitHistoryPrice price = new ProductSplitHistoryPrice();
            price.setProductCode(productSplitHistoryPriceList.get(0).getProductCode());
            price.setTime(sp.parse(sp.format(productSplitHistoryPriceList.get(0).getTime())));
            price.setDeletedFlag("N");
            List<ProductSplitHistoryPrice> priceList = productSplitHistoryPriceMapper.select(price);
            if(priceList != null && priceList.size() > 0){
                throw new RpcException("时间已经存在");
            }else{
                for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                    Date date = list.getTime();
                    String strDate = sp.format(date);
                    date = sp.parse(strDate);
                    list.setTime(date);
                    list.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_TOMORROW.getCode());
                    if(list.getProductType().equals("0")){
                        priceZero = list.getPrice();
                        taxRateZero = list.getTaxRate();
                    }else if(list.getProductType().equals("7")){
                        priceSeven = list.getPrice();
                        taxRateSeven = list.getTaxRate();
                    }else {
                        productSplitHistoryPriceTpyeByOtherList.add(list);
                    }
                }
                ProductSplitDetail productSplitDetail = new ProductSplitDetail();
                productSplitDetail.setProductCode(productSplitHistoryPriceList.get(0).getProductCode());
                productSplitDetail.setDeletedFlag("N");
                List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
                for(ProductSplitDetail data : productSplitDetailList){
                    if(data.getProductType().equals("0") || data.getProductType().equals("7")){
                        ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
                        productSplitHistoryPrice.setTime(productSplitHistoryPriceList.get(0).getTime());
                        productSplitHistoryPrice.setProductCode(data.getProductCode());
                        productSplitHistoryPrice.setServiceType(data.getServiceType());
                        productSplitHistoryPrice.setMaterialCode(data.getMaterialCode());
                        productSplitHistoryPrice.setMaterialName(data.getMaterialName());
                        productSplitHistoryPrice.setType(productSplitHistoryPriceList.get(0).getType());
                        if(data.getProductType().equals("0")){
                            productSplitHistoryPrice.setPrice(priceZero);
                            productSplitHistoryPrice.setTaxRate(taxRateZero);
                        }else if(data.getProductType().equals("7")){
                            productSplitHistoryPrice.setPrice(priceSeven);
                            productSplitHistoryPrice.setTaxRate(taxRateSeven);
                        }
                        productSplitHistoryPrice.setProductType(data.getProductType());
                        productSplitHistoryPrice.setCreatedBy(productSplitHistoryPriceList.get(0).getCreatedBy());
                        productSplitHistoryPrice.setCreatedDate(new Date());
                        productSplitHistoryPrice.setUpdatedBy(productSplitHistoryPriceList.get(0).getUpdatedBy());
                        productSplitHistoryPrice.setUpdatedDate(new Date());
                        productSplitHistoryPrice.setDeletedFlag("N");
                        productSplitHistoryPriceTpyeByOtherList.add(productSplitHistoryPrice);
                    }
                }
            }
        }
        result = productSplitHistoryPriceMapper.addProductSplitHistoryPriceList(productSplitHistoryPriceTpyeByOtherList);
        return result;
    }

    public Integer updateProductSplitHistoryPriceByTime(List<ProductSplitHistoryPrice> productSplitHistoryPriceList){
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        ProductSplitHistoryPrice productSplitHistoryPrice;
        if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
            for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                Date date = list.getTime();
                String strDate = sp.format(date);
                try {
                    date = sp.parse(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.setTime(date);
                productSplitHistoryPrice = new ProductSplitHistoryPrice();
                productSplitHistoryPrice.setTime(list.getTime());
                productSplitHistoryPrice.setProductCode(list.getProductCode());
                productSplitHistoryPrice.setPrice(list.getPrice());
                productSplitHistoryPrice.setUpdatedBy(productSplitHistoryPriceList.get(0).getUpdatedBy());
                productSplitHistoryPrice.setUpdatedDate(new Date());
                if(list.getProductType().equals("0")){
                    productSplitHistoryPrice.setProductType("0");
                }else if(list.getProductType().equals("7")){
                    productSplitHistoryPrice.setProductType("7");
                }else{
                    productSplitHistoryPrice.setMaterialCode(list.getMaterialCode());
                    productSplitHistoryPrice.setProductType(list.getProductType());
                }
                productSplitHistoryPriceMapper.updateProductSplitHistoryPriceByTime(productSplitHistoryPrice);
            }
            return 1;
        }else {
            throw new RpcException("服务器异常");
        }
    }

/*    public Integer batchInsertOnDuplicateKeyUpdateProductSplitHistor(List<ProductSplitHistoryPrice> productSplitHistoryPriceList){
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        List<ProductSplitHistoryPrice> productSplitHistoryPriceTpyeByOtherList = new ArrayList<>();
        Double priceZero = 0.0;
        Double priceSeven = 0.0;
        String deletedFlag = "";
        if(productSplitHistoryPriceList != null && productSplitHistoryPriceList.size() > 0){
            for(ProductSplitHistoryPrice list : productSplitHistoryPriceList){
                Date date = list.getTime();
                String strDate = sp.format(date);
                try {
                    date = sp.parse(strDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.setTime(date);
                if(list.getProductType().equals("0")){
                    priceZero = list.getPrice();
                    deletedFlag = list.getDeletedFlag();
                }else if(list.getProductType().equals("7")){
                    priceSeven = list.getPrice();
                    deletedFlag = list.getDeletedFlag();
                }else{
                    productSplitHistoryPriceTpyeByOtherList.add(list);
                }
            }
            ProductSplitDetail productSplitDetail = new ProductSplitDetail();
            productSplitDetail.setProductCode(productSplitHistoryPriceTpyeByOtherList.get(0).getProductCode());
            productSplitDetail.setDeletedFlag("N");
            List<ProductSplitDetail> productSplitDetailList = productSplitDetailMapper.select(productSplitDetail);
            for(ProductSplitDetail data : productSplitDetailList){
                if(data.getProductType().equals("0") || data.getProductType().equals("7")) {
                    ProductSplitHistoryPrice productSplitHistoryPrice = new ProductSplitHistoryPrice();
                    productSplitHistoryPrice.setTime(productSplitHistoryPriceTpyeByOtherList.get(0).getTime());
                    productSplitHistoryPrice.setProductCode(data.getProductCode());
                    productSplitHistoryPrice.setServiceType(data.getServiceType());
                    productSplitHistoryPrice.setMaterialCode(data.getMaterialCode());
                    productSplitHistoryPrice.setMaterialName(data.getMaterialName());
                    productSplitHistoryPrice.setType(productSplitHistoryPriceTpyeByOtherList.get(0).getType());
                    if (data.getProductType().equals("0")) {
                        productSplitHistoryPrice.setPrice(priceZero);
                    } else if (data.getProductType().equals("7")) {
                        productSplitHistoryPrice.setPrice(priceSeven);
                    }
                    productSplitHistoryPrice.setProductType(data.getProductType());
                    productSplitHistoryPrice.setCreatedBy(productSplitHistoryPriceTpyeByOtherList.get(0).getCreatedBy());
                    productSplitHistoryPrice.setCreatedDate(productSplitHistoryPriceTpyeByOtherList.get(0).getCreatedDate());
                    productSplitHistoryPrice.setUpdatedBy(productSplitHistoryPriceTpyeByOtherList.get(0).getUpdatedBy());
                    productSplitHistoryPrice.setUpdatedDate(productSplitHistoryPriceTpyeByOtherList.get(0).getUpdatedDate());
                    productSplitHistoryPrice.setDeletedFlag(deletedFlag);
                    productSplitHistoryPriceTpyeByOtherList.add(productSplitHistoryPrice);
                }
            }
            return productSplitHistoryPriceMapper.batchInsertOnDuplicateKeyUpdateProductSplitHistor(productSplitHistoryPriceTpyeByOtherList);
        }else {
            throw new RpcException("服务器异常");
        }
    }*/

    /**
     * 修改产品拆分历史价格信息
     *
     * @param productSplitHistoryPrice 产品拆分历史价格对象
     * @return 返回成功条数
     */
    public Integer updateById(ProductSplitHistoryPrice productSplitHistoryPrice){
        logger.info("修改产品拆分历史价格入参:{}", productSplitHistoryPrice);
        Integer result;
        result = productSplitHistoryPriceMapper.updateById(productSplitHistoryPrice);
        return result;
    }

    /**
     * 删除对应生效时间的历史价格信息
     *
     * @param
     * @return
     */
    public Integer deleteProductSplitHistoryPrice(ProductSplitHistoryPrice productSplitHistoryPrice){
        Integer result;
        logger.info("修改产品拆分历史价格删除标识入参:=" + productSplitHistoryPrice);
        productSplitHistoryPrice.setDeletedFlag("Y");
        productSplitHistoryPrice.setUpdatedDate(new Date());
        result = productSplitHistoryPriceMapper.updateProductSplitHistoryPriceByTimeProductCode(productSplitHistoryPrice);
        return  result;
    }
    
    //根据时间获取产品价格
    public List<ProductSplitHistoryPrice> listProductPrice(String productCode,Date orderDate){
    	//如果日期为空 默认返回当前价格 否则根据时间获取价格
    	logger.info("listProductPrice:: productCode:" + productCode + " orderDate" + orderDate);
    	if(null == orderDate){
    		Example example = new Example(ProductSplitHistoryPrice.class);
    		example.createCriteria().andEqualTo("productCode", productCode)
    								.andEqualTo("deletedFlag","N")
    								.andEqualTo("type","0");
    		return productSplitHistoryPriceMapper.selectByExample(example);
    	}else{
    		
    		List<ProductSplitHistoryPrice> listPrice = new ArrayList<>();
    		Example example = new Example(ProductSplitHistoryPrice.class);
			example.createCriteria().andEqualTo("productCode", productCode);									
			List<ProductSplitHistoryPrice>  listPriceReturn = productSplitHistoryPriceMapper.selectByExample(example);
			Collections.sort(listPriceReturn, new Comparator<ProductSplitHistoryPrice>(){
				@Override
				public int compare(ProductSplitHistoryPrice o1,
						ProductSplitHistoryPrice o2) {
					return o1.getTime().compareTo(o2.getTime());
				}
			});
			if(null != listPriceReturn && !listPriceReturn.isEmpty()){
				Date timeFlag = null;
				for(ProductSplitHistoryPrice price:listPriceReturn){
					if(price.getTime().after(orderDate)){
						continue;
					}
					if(null == timeFlag){
						timeFlag = price.getTime();
					}
					if(price.getTime().compareTo(timeFlag) != 0){
						break;
					}
					listPrice.add(price);
				}
				logger.info("listProductPrice:: listPrice {}",listPrice);
				return listPrice;
			}
    	}
		return null;
    }
    
    
}
