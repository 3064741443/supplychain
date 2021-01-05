package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.model.bs.SalesRelation;
import cn.com.glsx.supplychain.service.bs.SalesRelationService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author leiming
 * @version V1.0
 * @Title: SalesRelationAdminRemote.java
 * @Description: 销售管理与销售汇总关系(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("SalesRelationAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class SalesRelationAdminRemoteImpl implements SalesRelationAdminRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesRelationAdminRemoteImpl.class);

    @Autowired
    private SalesRelationService salesRelationService;

    @Override
    public RpcResponse<List<SalesRelation>> getSalesRelation(SalesRelation salesRelation) {
        try{
            return RpcResponse.success(salesRelationService.getSalesRelation(salesRelation));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> add(SalesRelation salesRelation) {
        try {
            RpcAssert.assertNotNull(salesRelation, DefaultErrorEnum.PARAMETER_NULL, "salesRelation must not be null");
            return RpcResponse.success(salesRelationService.add(salesRelation));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }
}
