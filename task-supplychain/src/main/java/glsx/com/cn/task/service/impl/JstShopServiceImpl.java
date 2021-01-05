package glsx.com.cn.task.service.impl;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.enums.SourceEnum;
import cn.com.glsx.merchant.facade.model.entity.MerchantDetailFacade;
import cn.com.glsx.merchant.facade.model.entity.MerchantFacade;
import cn.com.glsx.merchant.facade.model.response.MerchantFacadeResponse;
import cn.com.glsx.merchant.facade.model.response.ResultResponse;
import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import glsx.com.cn.task.mapper.JstShopAgentRelationMapper;
import glsx.com.cn.task.mapper.JstShopMapper;
import glsx.com.cn.task.model.JstShop;
import glsx.com.cn.task.model.JstShopAgentRelation;
import glsx.com.cn.task.service.JstShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @ClassName JstShopServiceImpl
 * @Description
 * @Author xiex
 * @Date 2020/2/27 22:12
 * @Version 1.0
 */
@Service
public class JstShopServiceImpl implements JstShopService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JstShopMapper jstShopMapper;

    @Autowired
    private JstShopAgentRelationMapper jstShopAgentRelationMapper;

    @Autowired
    private MerchantFacadeRemote merchantFacadeRemote;

    @Override
    public void updateShopStatus() {

        Date date = new Date();
        JstShop jstShop;
        JstShopAgentRelation jstShopAgentRelation = new JstShopAgentRelation();
        jstShopAgentRelation.setStatus("IN");
        List<JstShopAgentRelation> jstShopAgentRelationList = jstShopAgentRelationMapper.select(jstShopAgentRelation);
        for (JstShopAgentRelation jstShopAgentRelationModel : jstShopAgentRelationList) {
            jstShop = new JstShop();
            jstShop.setShopCode(jstShopAgentRelationModel.getShopCode());
            jstShop = jstShopMapper.selectOne(jstShop);
            String merchantName = jstShop.getShopName();
            String phone = jstShop.getPhone();
            RpcResponse<List<MerchantFacadeResponse>> rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, null);
           /* if (!(rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0)) {
                rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, phone);
                if (!(rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0)) {
                    rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(null, phone);
                    if (!(rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0)) {
                        rpcnRpcResponse = merchantFacadeRemote.getMerchantByVagueNameAndPhoneFacade(merchantName, null);
                    }
                }
            }*/

            if (rpcnRpcResponse != null && rpcnRpcResponse.getResult() != null && rpcnRpcResponse.getResult().size() > 0 && rpcnRpcResponse.getResult().size() < 2) {
                jstShop.setShopMerchantName(rpcnRpcResponse.getResult().get(0).getMerchantName());
                jstShop.setShopMerchantCode(rpcnRpcResponse.getResult().get(0).getMerchantId()+"");
                jstShop.setStatus("PS");
                jstShop.setUpdatedDate(date);
                logger.info("JstShopServiceImpl::updateShopStatus jstShop:{}",jstShop);
                jstShopMapper.updateByPrimaryKeySelective(jstShop);
                jstShopAgentRelationModel.setStatus("PS");
                jstShopAgentRelationModel.setUpdatedDate(date);
                logger.info("JstShopServiceImpl::updateShopStatus jstShopAgentRelationModel:{}",jstShopAgentRelationModel);
                jstShopAgentRelationMapper.updateByPrimaryKeySelective(jstShopAgentRelationModel);
            } else {
                jstShop.setStatus("WC");
                jstShop.setUpdatedDate(date);
                jstShopMapper.updateByPrimaryKeySelective(jstShop);
                jstShopAgentRelationModel.setStatus("WC");
                jstShopAgentRelationModel.setUpdatedDate(date);
                jstShopAgentRelationMapper.updateByPrimaryKeySelective(jstShopAgentRelationModel);
            }
        }
    }
}
