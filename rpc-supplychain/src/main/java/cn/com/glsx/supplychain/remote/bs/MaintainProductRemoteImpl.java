package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.bs.MaintainProduct;
import cn.com.glsx.supplychain.model.bs.MaintainProductDetail;
import cn.com.glsx.supplychain.service.bs.MaintainProductDetailService;
import cn.com.glsx.supplychain.service.bs.MaintainProductService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: MaintainProductRemote.java
 * @Description: 维修产品库接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("MaintainProductRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class MaintainProductRemoteImpl implements MaintainProductRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintainProductRemoteImpl.class);

    @Autowired
    private MaintainProductService maintainProductService;

    @Autowired
    private MaintainProductDetailService maintainProductDetailService;

    @Override
    public RpcResponse<RpcPagination<MaintainProduct>> listMaintainProduct(RpcPagination<MaintainProduct> pagination) {
        RpcResponse<RpcPagination<MaintainProduct>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(maintainProductService.listMaintainProduct(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> add(MaintainProduct maintainProduct) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(maintainProduct, DefaultErrorEnum.PARAMETER_NULL, "maintainProduct must not be null");
            result = RpcResponse.success(maintainProductService.add(maintainProduct));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateById(MaintainProduct maintainProduct) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(maintainProduct, DefaultErrorEnum.PARAMETER_NULL, "maintainProduct must not be null");
            result = RpcResponse.success(maintainProductService.updateById(maintainProduct));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<MaintainProduct> getMaintainProductInfo(MaintainProduct maintainProduct) {
        RpcResponse<MaintainProduct>result;
        try {
            RpcAssert.assertNotNull(maintainProduct, DefaultErrorEnum.PARAMETER_NULL, "maintainProduct must not be null");
            result = RpcResponse.success(maintainProductService.getMaintainProductInfo(maintainProduct));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateByMaintainProductDetail(MaintainProductDetail maintainProductDetail) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(maintainProductDetail, DefaultErrorEnum.PARAMETER_NULL, "maintainProductDetail must not be null");
            result = RpcResponse.success(maintainProductDetailService.updateByMaintainProductDetail(maintainProductDetail));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> batchUpdateMaintainProductDetail(List<MaintainProductDetail> maintainProductDetailList) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(maintainProductDetailList, DefaultErrorEnum.PARAMETER_NULL, "maintainProductDetailList must not be null");
            result = RpcResponse.success(maintainProductDetailService.batchUpdateMaintainProductDetail(maintainProductDetailList));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<DeviceInfo> checkIccid(DeviceInfo deviceInfo) {
        RpcResponse<DeviceInfo> result;
        try {
            RpcAssert.assertNotNull(deviceInfo, DefaultErrorEnum.PARAMETER_NULL, "deviceInfo must not be null");
            result = RpcResponse.success(maintainProductService.checkIccid(deviceInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }
}
