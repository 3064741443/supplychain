package cn.com.glsx.supplychain.jst.controller;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;
import cn.com.glsx.supplychain.jst.vo.*;
import cn.com.glsx.supplychain.jst.web.session.Session;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcTransferOrderRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 10:45
 */
@Api(value = "/jxcOrder", tags = "经销存系统小程序调拨订单")
@RestController
@RequestMapping("jxcOrder")
public class TransferOrderController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JxcTransferOrderRemote jxcTransferOrderRemote;

    @ApiOperation(value = "获取服务商列表", notes = "获取服务商列表")
    @PostMapping("listServiceProvider")
    public ResultEntity<List<JXCDealerUserInfoDTO>> listServiceProvider(@RequestBody JXCDealerUserInfoVo dealerUserInfoVo) {
        JXCDealerUserInfoDTO dealerUserInfoDTO = new JXCDealerUserInfoDTO();
        BeanUtils.copyProperties(dealerUserInfoVo, dealerUserInfoDTO);
        RpcResponse<List<JXCDealerUserInfoDTO>> response = jxcTransferOrderRemote.listServiceProvider(dealerUserInfoDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("TransferOrderController::listServiceProvider return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("TransferOrderController::listServiceProvider end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "发起调拨生成调拨单", notes = "发起调拨生成调拨单")
    @PostMapping("generateTransferOrder")
    public ResultEntity<Integer> generateTransferOrder(@RequestBody JXCMdbGenerateTransferOrderVo generateTransferOrderVo) {
        Session session = ThreadLocalCache.getSession();
        UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("TransferOrderController::generateTransferOrder start userInfoVo:{},generateTransferOrderVo:{}", userInfoVo, generateTransferOrderVo);
        if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
            LOGGER.info("TransferOrderController::generateTransferOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
            LOGGER.info("TransferOrderController::generateTransferOrder wrong role");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
        }
        JXCMdbGenerateTransferOrderDTO jxcMdbGenerateTransferOrderDTO = new JXCMdbGenerateTransferOrderDTO();
        BeanUtils.copyProperties(generateTransferOrderVo, jxcMdbGenerateTransferOrderDTO);
        jxcMdbGenerateTransferOrderDTO.setOrderSource(Constants.SMX_ORDER_SOURCE);
        jxcMdbGenerateTransferOrderDTO.setConsumer(userInfoVo.getMerchantName());
        jxcMdbGenerateTransferOrderDTO.setOutServiceProvidercode(userInfoVo.getMerchantCode());
        jxcMdbGenerateTransferOrderDTO.setOutServiceProviderName(userInfoVo.getMerchantName());
        jxcMdbGenerateTransferOrderDTO.setConsumer(userInfoVo.getMerchantName());
        RpcResponse<Integer> response = jxcTransferOrderRemote.generateTransferOrder(jxcMdbGenerateTransferOrderDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("TransferOrderController::generateTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("TransferOrderController::generateTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }


    @ApiOperation(value = "获取调拨订单列表", notes = "获取调拨订单列表")
    @PostMapping("pageTransferOrder")
    public ResultEntity<RpcPagination<JXCTransferOrderDTO>> pageTransferOrder(@RequestBody JXCMdbTransferOrderQueryVo transferOrderQueryVo) {
        Session session = ThreadLocalCache.getSession();
        UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("TransferOrderController::pageTransferOrder start userInfoVo:{},transferOrderQueryVo:{}", userInfoVo, transferOrderQueryVo);
        if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
            LOGGER.info("TransferOrderController::pageTransferOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
            LOGGER.info("TransferOrderController::pageTransferOrder wrong role");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
        }
        JXCTransferOrderQueryDTO transferOrderQuery = new JXCTransferOrderQueryDTO();
        transferOrderQuery.setConsumer(userInfoVo.getMerchantName());
        transferOrderQuery.setOrderStatus(transferOrderQueryVo.getOrderStatus());
        transferOrderQuery.setMerchantCode(userInfoVo.getMerchantCode());
        RpcPagination<JXCTransferOrderQueryDTO> pagination = new RpcPagination<>();
        pagination.setCondition(transferOrderQuery);
        pagination.setPageNum(transferOrderQueryVo.getPageNum());
        pagination.setPageSize(transferOrderQueryVo.getPageSize());
        RpcResponse<RpcPagination<JXCTransferOrderDTO>> response = jxcTransferOrderRemote.pageTransferOrderJXC(pagination);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("TransferOrderController::pageTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("TransferOrderController::pageTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

/*    @ApiOperation(value = "调拨订单详情", notes = "调拨订单详情")
    @GetMapping("getTransferOrder")
    public ResultEntity<JXCMdbTransferOrderDTO> getTransferOrder(@RequestBody JXCMdbTransferOrderDetailQueryVo jxcMdbTransferOrderDetailQueryVo) {
        Session session = ThreadLocalCache.getSession();
        UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO=new JXCMdbTransferOrderDetailQueryDTO();
        RpcResponse<JXCMdbTransferOrderDTO> response=jxcTransferOrderRemote.getTransferOrder(transferOrderDetailQueryDTO);
        return ResultEntity.success(response.getResult());
    }*/

    @ApiOperation(value = "小程序调拨订单发货", notes = "小程序调拨订单发货")
    @PostMapping("commitTransferOrder")
    public ResultEntity<Integer> commitTransferOrder(@RequestBody JXCMdbCommitTransferOrderVo transferOrderVo) {
        Session session = ThreadLocalCache.getSession();
        UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("TransferOrderController::generateTransferOrder start userInfoVo:{},transferOrderVo:{}", userInfoVo, transferOrderVo);
        if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
            LOGGER.info("TransferOrderController::commitTransferOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
            LOGGER.info("TransferOrderController::commitTransferOrder wrong role");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
        }
        JXCMdbCommitTransferOrderDTO jxcMdbCommitTransferOrderDTO = new JXCMdbCommitTransferOrderDTO();
        BeanUtils.copyProperties(transferOrderVo, jxcMdbCommitTransferOrderDTO);
        jxcMdbCommitTransferOrderDTO.setConsumer(userInfoVo.getMerchantName());
        RpcResponse<Integer> response = jxcTransferOrderRemote.commitTransferOrder(jxcMdbCommitTransferOrderDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("TransferOrderController::listServiceProvider return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("TransferOrderController::listServiceProvider end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }



    @ApiOperation(value = "小程序调拨sn校验", notes = "小程序调拨sn校验")
    @PostMapping("checkTransferOrderSn")
    public ResultEntity<CheckTransferOrderDTO> checkTransferOrderSn(@RequestBody CheckTransferOrderVO checkTransferOrderVO) {
        Session session = ThreadLocalCache.getSession();
        UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("TransferOrderController::checkTransferOrderSn start userInfoVo:{},checkTransferOrderVO:{}", userInfoVo, checkTransferOrderVO);
        if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
            LOGGER.info("TransferOrderController::checkTransferOrderSn not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
            LOGGER.info("TransferOrderController::checkTransferOrderSn wrong role");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
        }
        CheckTransferOrderDTO checkTransferOrderDTO=new CheckTransferOrderDTO();
        BeanUtils.copyProperties(checkTransferOrderVO,checkTransferOrderDTO);
        return ResultEntity.success(checkTransferOrderDTO);
    }

}
