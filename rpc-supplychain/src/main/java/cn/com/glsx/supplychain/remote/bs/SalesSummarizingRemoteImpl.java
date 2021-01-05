package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.bs.SalesSummarizing;
import cn.com.glsx.supplychain.model.bs.SalesSummarizingDetail;
import cn.com.glsx.supplychain.model.bs.SalesSummarizingMaterialDetail;
import cn.com.glsx.supplychain.service.bs.SalesSummarizingMaterialDetailService;
import cn.com.glsx.supplychain.service.bs.SalesSummarizingService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * @author xiexin
 * @version V1.0
 * @Title: SalesSummarizingRemote.java
 * @Description: 销售汇总接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("SalesSummarizingRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class SalesSummarizingRemoteImpl implements SalesSummarizingRemote{

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesSummarizingRemoteImpl.class);

    @Autowired
    private SalesSummarizingService salesSummarizingService;

    @Autowired
    private SalesSummarizingMaterialDetailService salesSummarizingMaterialDetailService;

    @Override
    public RpcResponse<RpcPagination<SalesSummarizing>> listSalesSummarizing(RpcPagination<SalesSummarizing> pagination) {
        RpcResponse<RpcPagination<SalesSummarizing>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(salesSummarizingService.listSalesSummarizing(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<SalesSummarizingDetail>> getSalesSummarizingDetailBySalesSummarizingId(Long salesSummarizingId) {
        RpcResponse<List<SalesSummarizingDetail>> result;
        try {
            RpcAssert.assertNotNull(salesSummarizingId, DefaultErrorEnum.PARAMETER_NULL, "salesSummarizingId must not be null");
            result = RpcResponse.success(salesSummarizingService.getSalesSummarizingDetailBySalesSummarizingId(salesSummarizingId));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(SalesSummarizing salesSummarizing) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(salesSummarizing, DefaultErrorEnum.PARAMETER_NULL, "salesSummarizing must not be null");
            result = RpcResponse.success(salesSummarizingService.add(salesSummarizing));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateById(SalesSummarizing salesSummarizing) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(salesSummarizing, DefaultErrorEnum.PARAMETER_NULL, "salesSummarizing must not be null");
            result = RpcResponse.success(salesSummarizingService.updateById(salesSummarizing));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }


    @Override
    public RpcResponse<List<Date>> getDate() {
        RpcResponse<List<Date>> result;
        try {
            result = RpcResponse.success(salesSummarizingService.getDate());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }


    @Override
    public RpcResponse<List<SalesSummarizing>> exportSalesSummarizingExit(SalesSummarizing salesSummarizing) {
        try {
            List<SalesSummarizing> list = salesSummarizingService.exportSalesSummarizingExit(salesSummarizing);
            return RpcResponse.success(list);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> addSalesSummarizingList(List<SalesSummarizing> salesSummarizingList) {
        RpcResponse<Integer>result;
        try{
            RpcAssert.assertNotNull(salesSummarizingList, DefaultErrorEnum.PARAMETER_NULL, "salesSummarizingList must not be null");
            result = RpcResponse.success(salesSummarizingService.addSalesSummarizingList(salesSummarizingList));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<SalesSummarizingMaterialDetail>> getSalesSummarizingMaterialDetailList(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail) {
        RpcResponse<List<SalesSummarizingMaterialDetail>> result;
        try{
            RpcAssert.assertNotNull(salesSummarizingMaterialDetail, DefaultErrorEnum.PARAMETER_NULL, "salesSummarizingMaterialDetail must not be null");
            result = RpcResponse.success(salesSummarizingMaterialDetailService.getSalesSummarizingMaterialDetailList(salesSummarizingMaterialDetail));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

}
