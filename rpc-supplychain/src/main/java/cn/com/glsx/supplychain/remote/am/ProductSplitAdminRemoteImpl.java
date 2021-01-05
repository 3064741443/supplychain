package cn.com.glsx.supplychain.remote.am;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.am.ProductSplitDetailService;
import cn.com.glsx.supplychain.service.am.ProductSplitHistoryPriceService;
import cn.com.glsx.supplychain.service.am.ProductSplitService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcException;
import com.glsx.cloudframework.core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author leiming
 * @version V1.0
 * @Title: ProductSplitAdminRemote.java
 * @Description: 产品拆分接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("ProductSplitAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class ProductSplitAdminRemoteImpl implements ProductSplitAdminRemote{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSplitAdminRemoteImpl.class);

    @Autowired
    private ProductSplitService productSplitService;
    @Autowired
    private ProductSplitDetailService productSplitDetailService;
    @Autowired
    private ProductSplitHistoryPriceService productSplitHistoryPriceService;


    @Override
    public RpcResponse<RpcPagination<ProductSplit>> listProductSplit(RpcPagination<ProductSplit> pagination) {
        RpcResponse<RpcPagination<ProductSplit>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(productSplitService.listProductSplit(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(ProductSplit productSplit) {
        try {
            RpcAssert.assertNotNull(productSplit, DefaultErrorEnum.PARAMETER_NULL, "productSplit must not be null");
            return RpcResponse.success(productSplitService.add(productSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateById(ProductSplit productSplit) {
        try {
            RpcAssert.assertNotNull(productSplit, DefaultErrorEnum.PARAMETER_NULL, "productSplit must not be null");
            return RpcResponse.success(productSplitService.updateById(productSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<ProductSplit> getProductSplitInfo(ProductSplit productSplit) {
        try {
            RpcAssert.assertNotNull(productSplit, DefaultErrorEnum.PARAMETER_NULL, "productSplit must not be null");
            return RpcResponse.success(productSplitService.getProductSplitInfo(productSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<ProductSplit>> getProductSplitList(ProductSplit productSplit) {
        try {
            RpcAssert.assertNotNull(productSplit, DefaultErrorEnum.PARAMETER_NULL, "productSplit must not be null");
            return RpcResponse.success(productSplitService.getProductSplitList(productSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    /**
     * 经销商下单的产品详情信息(productType为0的产品)
     *
     * @param productSplitDetailList 产品拆分详情对象List
     * @return
     */
    @Override
    public RpcResponse<List<ProductSplitDetail>> getProductSplitDetailByProductTypeZeroList(List<ProductSplitDetail> productSplitDetailList) {
        try {
            RpcAssert.assertNotNull(productSplitDetailList, DefaultErrorEnum.PARAMETER_NULL, "productSplitDetail must not be null");
            return RpcResponse.success(productSplitDetailService.getProductSplitDetailByProductTypeZeroList(productSplitDetailList));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<ProductSplitDetail>> getProductSplitDetailList(ProductSplitDetail productSplitDetail) {
        try {
            RpcAssert.assertNotNull(productSplitDetail, DefaultErrorEnum.PARAMETER_NULL, "productSplitDetail must not be null");
            return RpcResponse.success(productSplitDetailService.getProductSplitDetailList(productSplitDetail));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateDeletedFlagById(ProductSplit productSplit) {
        try {
            RpcAssert.assertNotNull(productSplit, DefaultErrorEnum.PARAMETER_NULL, "productSplit must not be null");
            return RpcResponse.success(productSplitService.updateDeletedFlagById(productSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateProductSplitStatus(ProductSplit productSplit) {
        try {
            RpcAssert.assertNotNull(productSplit, DefaultErrorEnum.PARAMETER_NULL, "productSplit must not be null");
            return RpcResponse.success(productSplitService.updateProductSplitStatus(productSplit));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> deleteProductSplitHistoryPrice(ProductSplitHistoryPrice productSplitHistoryPrice) {
        try {
            RpcAssert.assertNotNull(productSplitHistoryPrice, DefaultErrorEnum.PARAMETER_NULL, "productSplitHistoryPrice must not be null");
            return RpcResponse.success(productSplitHistoryPriceService.deleteProductSplitHistoryPrice(productSplitHistoryPrice));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<RpcPagination<ProductSplitDetail>> listProductSplitDetail(RpcPagination<ProductSplitDetail> pagination) {
        RpcResponse<RpcPagination<ProductSplitDetail>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(productSplitDetailService.listProductSplitDetail(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return  result;
    }

    @Override
    public RpcResponse<Integer> addProductSplitHistoryPriceList(List<ProductSplitHistoryPrice> productSplitHistoryPriceList) {
        try {
            RpcAssert.assertNotNull(productSplitHistoryPriceList, DefaultErrorEnum.PARAMETER_NULL, "productSplitHistoryPriceList must not be null");
            Integer sum = productSplitHistoryPriceService.addProductSplitHistoryPriceList(productSplitHistoryPriceList);
            return RpcResponse.success(sum);
        }catch (RpcException e){
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_RECORD_REPEATED,e.getMessage());
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateProductSplitHistoryPriceByTime(List<ProductSplitHistoryPrice> productSplitHistoryPriceList) {
        try {
            RpcAssert.assertNotNull(productSplitHistoryPriceList, DefaultErrorEnum.PARAMETER_NULL, "productSplitHistoryPrice must not be null");
            Integer sum = productSplitHistoryPriceService.updateProductSplitHistoryPriceByTime(productSplitHistoryPriceList);
            return RpcResponse.success(sum);
        }catch (RpcException e){
            return RpcResponse.error(ErrorCodeEnum.ERRCODE_RECORD_REPEATED,e.getMessage());
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

/*    @Override
    public RpcResponse<Integer> batchInsertOnDuplicateKeyUpdateProductSplitHistor(List<ProductSplitHistoryPrice> productSplitHistoryPriceList) {
        try {
            RpcAssert.assertNotNull(productSplitHistoryPriceList, DefaultErrorEnum.PARAMETER_NULL, "productSplitHistoryPriceList must not be null");
            return RpcResponse.success(productSplitHistoryPriceService.batchInsertOnDuplicateKeyUpdateProductSplitHistor(productSplitHistoryPriceList));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }*/


}
