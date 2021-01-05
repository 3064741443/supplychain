package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.service.bs.DealerUserInfoService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: DealerUserInfoAdminRemote.java
 * @Description: 经销商用户接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("DealerUserInfoAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class DealerUserInfoAdminRemoteImpl implements DealerUserInfoAdminRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerUserInfoAdminRemoteImpl.class);

    @Autowired
    private DealerUserInfoService dealerUserInfoService;

    @Autowired
    private SupplyChainExternalService supplyChainExternalService;

    @Override
    public RpcResponse<RpcPagination<DealerUserInfo>> listDealerUserInfo(RpcPagination<DealerUserInfo> pagination) {
        RpcResponse<RpcPagination<DealerUserInfo>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(dealerUserInfoService.listDealerUserInfo(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List> getOldMerchantInfo(HashMap<String, Object> condition, Integer targetPage, Integer pageSize) {
        try {
            RpcAssert.assertNotNull(condition, DefaultErrorEnum.PARAMETER_NULL, "condition must not be null");
            RpcAssert.assertNotNull(targetPage, DefaultErrorEnum.PARAMETER_NULL, "targetPage must not be null");
            RpcAssert.assertNotNull(pageSize, DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            List list = supplyChainExternalService.listMerchantInfo(condition, targetPage, pageSize);
            return RpcResponse.success(list);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<DealerUserInfo> getDealerUserInfoByDealerUserName(String dealerUserName) {
        try {
            RpcAssert.assertNotNull(dealerUserName, DefaultErrorEnum.PARAMETER_NULL, "dealerUserName must not be null");
            return RpcResponse.success(dealerUserInfoService.getDealerUserInfoByDealerUserName(dealerUserName));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<DealerUserInfo> getDealerUserInfoByMerchantCode(String merchantCode) {
        try {
            RpcAssert.assertNotNull(merchantCode, DefaultErrorEnum.PARAMETER_NULL, "merchantCode must not be null");
            return RpcResponse.success(dealerUserInfoService.getDealerUserInfoByMerchantCode(merchantCode));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }


    @Override
    public RpcResponse<Integer> add(DealerUserInfo dealerUserInfo) {
        try {
            RpcAssert.assertNotNull(dealerUserInfo, DefaultErrorEnum.PARAMETER_NULL, "DealerUserInfo must not be null");
            return RpcResponse.success(dealerUserInfoService.add(dealerUserInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> update(DealerUserInfo dealerUserInfo) {
        try {
            RpcAssert.assertNotNull(dealerUserInfo, DefaultErrorEnum.PARAMETER_NULL, "DealerUserInfo must not be null");
            return RpcResponse.success(dealerUserInfoService.update(dealerUserInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updatePasswordByName(String name,String password,String updatedBy) {
        try {
            RpcAssert.assertNotNull(name, DefaultErrorEnum.PARAMETER_NULL, "name must not be null");
            RpcAssert.assertNotNull(password, DefaultErrorEnum.PARAMETER_NULL, "password must not be null");
            RpcAssert.assertNotNull(updatedBy, DefaultErrorEnum.PARAMETER_NULL, "updatedBy must not be null");
            return RpcResponse.success(dealerUserInfoService.updatePasswordByName(name,password,updatedBy));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<DealerUserInfo> getDelerUseInfoById(DealerUserInfo dealerUserInfo) {
        try {
            RpcAssert.assertNotNull(dealerUserInfo, DefaultErrorEnum.PARAMETER_NULL, "dealerUserInfo must not be null");
            return RpcResponse.success(dealerUserInfoService.getDelerUseInfoById(dealerUserInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> updateByDealerUserId(DealerUserInfo dealerUserInfo) {
        try {
            RpcAssert.assertNotNull(dealerUserInfo, DefaultErrorEnum.PARAMETER_NULL, "dealerUserInfo must not be null");
            return RpcResponse.success(dealerUserInfoService.updateByDealerUserId(dealerUserInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> deleteByDealerUserId(DealerUserInfo dealerUserInfo) {
        try {
            RpcAssert.assertNotNull(dealerUserInfo, DefaultErrorEnum.PARAMETER_NULL, "dealerUserInfo must not be null");
            return RpcResponse.success(dealerUserInfoService.deleteByDealerUserId(dealerUserInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<List<DealerUserInfo>> gteDealerUserInfoList(DealerUserInfo dealerUserInfo) {
        try {
            RpcAssert.assertNotNull(dealerUserInfo, DefaultErrorEnum.PARAMETER_NULL, "dealerUserInfo must not be null");
            return RpcResponse.success(dealerUserInfoService.gteDealerUserInfoList(dealerUserInfo));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

}
