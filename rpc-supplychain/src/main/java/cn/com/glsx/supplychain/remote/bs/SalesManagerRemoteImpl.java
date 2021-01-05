package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.bs.MaintainProduct;
import cn.com.glsx.supplychain.model.bs.Sales;
import cn.com.glsx.supplychain.service.bs.MaintainProductService;
import cn.com.glsx.supplychain.service.bs.SalesManagerService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.soap.SAAJMetaFactory;
import java.util.List;

/**
 * @author leiming
 * @version V1.0
 * @Title: SalesManagerRemoteImpl.java
 * @Description: 销售接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("SalesManagerRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class SalesManagerRemoteImpl implements SalesManagerRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesManagerRemoteImpl.class);

    @Autowired
    private SalesManagerService salesManagerService;

    @Override
    public RpcResponse<RpcPagination<Sales>> listSalesManager(RpcPagination<Sales> pagination) {
        RpcResponse<RpcPagination<Sales>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(salesManagerService.listSalesManager(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(List<Sales> salesList) {
       RpcResponse<Integer>result;
       try{
           RpcAssert.assertNotNull(salesList,DefaultErrorEnum.PARAMETER_NULL,"salesList must not be null");
           result = RpcResponse.success(salesManagerService.add(salesList));
       }catch (Exception e){
           LOGGER.error(e.getMessage(), e);
           result = RpcResponse.error(e);
       }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateById(Sales sales) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(sales, DefaultErrorEnum.PARAMETER_NULL, "sales must not be null");
            result = RpcResponse.success(salesManagerService.updateById(sales));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Sales> getSalesInfoByid(Long id) {
        RpcResponse<Sales> result;
        try{
            RpcAssert.assertNotNull(id, DefaultErrorEnum.PARAMETER_NULL, "id must not be null");
            result = RpcResponse.success(salesManagerService.getSalesInfoByid(id));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateSalesInfoByid(List<Long> ids,Byte status) {
        RpcResponse<Integer>result;
        try {
            RpcAssert.assertNotNull(ids, DefaultErrorEnum.PARAMETER_NULL, "ids must not be null");
            RpcAssert.assertNotNull(status, DefaultErrorEnum.PARAMETER_NULL, "status must not be null");
            result = RpcResponse.success(salesManagerService.updateSalesInfoByid(ids,status));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }
}
