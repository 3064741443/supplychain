package cn.com.glsx.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.TransferOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcTransferOrderRemote;
import cn.com.glsx.supplychain.model.jxc.*;
import cn.com.glsx.supplychain.util.ExcelReadAndWriteUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 10:45
 */
@Api(value = "/jxcBsOrder", tags = "商务系统调拨订单")
@RestController
@RequestMapping("jxcBsOrder")
public class JXCBSTransferOrderController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JxcTransferOrderRemote jxcTransferOrderRemote;
    @Autowired
    private UserInfoHolder userInfoHolder;

    @ApiOperation(value = "获取服务商列表", notes = "获取服务商列表")
    @PostMapping("listServiceProvider")
    public ResultEntity<List<JXCDealerUserInfoDTO>> listServiceProvider(@RequestBody JXCDealerUserInfoVo dealerUserInfoVo) {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::listServiceProvider start user:{},queryVo:{}", user, dealerUserInfoVo);
        JXCDealerUserInfoDTO dealerUserInfoDTO = new JXCDealerUserInfoDTO();
        BeanUtils.copyProperties(dealerUserInfoVo, dealerUserInfoDTO);
        RpcResponse<List<JXCDealerUserInfoDTO>> response = jxcTransferOrderRemote.listServiceProvider(dealerUserInfoDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JXCBSTransferOrderController::listServiceProvider return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JXCBSTransferOrderController::listServiceProvider end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }


    @ApiOperation(value = "发起调拨生成调拨单", notes = "发起调拨生成调拨单")
    @PostMapping("generateBsTransferOrder")
    public ResultEntity<Integer> generateBsTransferOrder(@RequestBody JXCMdbGenerateBsTransferOrderVo generateBsTransferOrderVo) {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::generateBsTransferOrder start user:{},queryVo:{}", user, generateBsTransferOrderVo);
        JXCMdbGenerateBsTransferOrderDTO generateTransferOrderDTO = new JXCMdbGenerateBsTransferOrderDTO();
        BeanUtils.copyProperties(generateBsTransferOrderVo, generateTransferOrderDTO);
        generateTransferOrderDTO.setOrderSource(Constants.GXS_ORDER_SOURCE);
        generateTransferOrderDTO.setConsumer(user != null ? user.getRealname() : "admin");
        RpcResponse<Integer> response = jxcTransferOrderRemote.generateBsTransferOrder(generateTransferOrderDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JXCBSTransferOrderController::generateBsTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JXCBSTransferOrderController::generateBsTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "获取调拨订单列表", notes = "获取调拨订单列表")
    @PostMapping("pageBsTransferOrder")
    public ResultEntity<RpcPagination<JXCTransferOrderDTO>> bsPageTransferOrder(@RequestBody JXCBsTransferOrderQueryVo transferOrderVo) {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::bsPageTransferOrder start user:{},queryVo:{}", user, transferOrderVo);
        RpcPagination<JXCTransferOrderQueryDTO> pagination = new RpcPagination<>();
        JXCTransferOrderQueryDTO transferOrderQuery = new JXCTransferOrderQueryDTO();
        transferOrderQuery.setStartTime(transferOrderVo.getStartTime());
        transferOrderQuery.setEndTime(transferOrderVo.getEndTime());
        transferOrderQuery.setTranOrderCode(transferOrderVo.getTranOrderCode());
        transferOrderQuery.setServiceProviderName(transferOrderVo.getServiceProviderName());
        transferOrderQuery.setMaterialName(transferOrderVo.getMaterialName());
        transferOrderQuery.setOrderStatus(transferOrderVo.getOrderStatus());
        transferOrderQuery.setOrderSource(transferOrderVo.getOrderSource());
        transferOrderQuery.setConsumer(user != null ? user.getRealname() : "admin");
        pagination.setCondition(transferOrderQuery);
        pagination.setPageNum(transferOrderVo.getPageNum());
        pagination.setPageSize(transferOrderVo.getPageSize());
        RpcResponse<RpcPagination<JXCTransferOrderDTO>> response = jxcTransferOrderRemote.pageTransferOrderJXC(pagination);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JXCBSTransferOrderController::bsPageTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JXCBSTransferOrderController::bsPageTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }


    @ApiOperation(value = "商务端调拨订单列表导出", notes = "商务端调拨订单列表导出")
    @PostMapping("exportTransferOrder")
    public void exportTransferOrder(@RequestBody JXCBsTransferOrderQueryVo transferOrderVo, HttpServletResponse httpServletResponse) throws Exception {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::exportTransferOrder start user:{},queryVo:{}", user, transferOrderVo);
        JXCBsTransferOrderQueryDTO transferOrderQuery = new JXCBsTransferOrderQueryDTO();
        transferOrderQuery.setStartTime(transferOrderVo.getStartTime());
        transferOrderQuery.setEndTime(transferOrderVo.getEndTime());
        transferOrderQuery.setTranOrderCode(transferOrderVo.getTranOrderCode());
        transferOrderQuery.setServiceProviderName(transferOrderVo.getServiceProviderName());
        transferOrderQuery.setMaterialName(transferOrderVo.getMaterialName());
        transferOrderQuery.setOrderStatus(transferOrderVo.getOrderStatus());
        transferOrderQuery.setOrderSource(transferOrderVo.getOrderSource());
        transferOrderQuery.setConsumer(user != null ? user.getRealname() : "admin");
        RpcResponse<List<JXCMdbBsTransferOrderExportDTO>> response = jxcTransferOrderRemote.exportBsTransferOrder(transferOrderQuery);
        List<JXCMdbBsTransferOrderExportDTO> transferOrderExportDTOS = response.getResult();
        List<JXCMdbBsTransferOrderExportVo> transferOrderExportVos = transferOrderExportDTOS.stream().map(transferOrderExportDTO -> exportBsTransferOrderExportConvertTo(transferOrderExportDTO)).collect(Collectors.toList());
        ExcelReadAndWriteUtil.writeExcel(httpServletResponse, transferOrderExportVos, Constants.EXPORT_NAME_BS_TRANSFER_ORDER_BSS, Constants.EXPORT_NAME_BS_TRANSFER_ORDER_BSS, JXCMdbBsTransferOrderExportVo.class);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            ResultEntity.error(errCodeEnum.getCode(), errCodeEnum.getDescrible());
            LOGGER.error(response.getMessage());
        }
    }


    private JXCMdbBsTransferOrderExportVo exportBsTransferOrderExportConvertTo(JXCMdbBsTransferOrderExportDTO transferOrderExportDTO) {
        JXCMdbBsTransferOrderExportVo transferOrderExportVo = new JXCMdbBsTransferOrderExportVo();
        BeanUtils.copyProperties(transferOrderExportDTO, transferOrderExportVo);
        String orderStr = convertTransferOrderStatus(transferOrderExportVo.getOrderStatus());
        if(transferOrderExportVo.getOrderSource().equals(Constants.GXS_ORDER_SOURCE)){
            transferOrderExportVo.setOrderSource("广联商务");
        }else{
            transferOrderExportVo.setOrderSource("服务商");
        }
        transferOrderExportVo.setOrderStatus(orderStr);
        transferOrderExportVo.setName(transferOrderExportDTO.getBsAddressDTO().getName());
        transferOrderExportVo.setMobile(transferOrderExportDTO.getBsAddressDTO().getMobile());
        transferOrderExportVo.setAddress(transferOrderExportDTO.getBsAddressDTO().getAddress());
        return transferOrderExportVo;
    }

    private String convertTransferOrderStatus(String status) {
        if (status.equals(TransferOrderStatusEnum.ORDER_WAIT_CHECK.getCode())) {
            return TransferOrderStatusEnum.ORDER_WAIT_CHECK.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())) {
            return TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getCode())) {
            return TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_COMPLETED.getCode())) {
            return TransferOrderStatusEnum.ORDER_COMPLETED.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getCode())) {
            return TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getName();
        }
        return "";
    }

    @ApiOperation(value = "调拨订单审核", notes = "调拨订单审核")
    @PostMapping("checkBsTransferOrder")
    public ResultEntity<Integer> checkBsTransferOrder(@RequestBody JXCMdbTransferOrderCheckVO transferOrderCheckVO) {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::checkBsTransferOrder start user:{},transferOrderCheckVO:{}", user, transferOrderCheckVO);
        JXCMdbTransferOrderCheckDTO transferOrderCheckDTO = new JXCMdbTransferOrderCheckDTO();
        BeanUtils.copyProperties(transferOrderCheckVO, transferOrderCheckDTO);
        RpcResponse<Integer> response = jxcTransferOrderRemote.checkBsTransferOrder(transferOrderCheckDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JXCBSTransferOrderController::checkBsTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JXCBSTransferOrderController::checkBsTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "调拨订单审核驳回", notes = "调拨订单审核驳回")
    @PostMapping("rebackBsTransferOrder")
    public ResultEntity<Integer> rebackBsTransferOrder(@RequestBody JXCMdbTransferOrderRebackVO transferOrderRebackVO) {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::rebackBsTransferOrder start user:{},checkVo:{}", user, transferOrderRebackVO);
        JXCMdbTransferOrderRebackDTO transferOrderRebackDTO = new JXCMdbTransferOrderRebackDTO();
        transferOrderRebackDTO.setTranOrderCode(transferOrderRebackVO.getTranOrderCode());
        transferOrderRebackDTO.setRebackReason(transferOrderRebackVO.getBackRemark());
        transferOrderRebackDTO.setConsumer(user != null ? user.getRealname() : "admin");
        RpcResponse<Integer> response = jxcTransferOrderRemote.rebackBsTransferOrder(transferOrderRebackDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JXCBSTransferOrderController::rebackBsTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JXCBSTransferOrderController::rebackBsTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "调拨发货详情", notes = "调拨发货详情")
    @PostMapping("transferShippingDetail")
    public ResultEntity<List<JXCLogisticsDTO>> getTransferShippingDetail(@RequestBody JXCMdbTransferOrderDetailQueryVo transferOrderDetailQueryVo) {
        User user = userInfoHolder.getUser();
        LOGGER.info("JXCBSTransferOrderController::getTransferShippingDetail start user:{},transferOrderDetailQueryVo:{}", user, transferOrderDetailQueryVo);
        JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO = new JXCMdbTransferOrderDetailQueryDTO();
        transferOrderDetailQueryDTO.setTranOrderCode(transferOrderDetailQueryVo.getTranOrderCode());
        RpcResponse<List<JXCLogisticsDTO>> response = jxcTransferOrderRemote.getTransferShippingDetail(transferOrderDetailQueryDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JXCBSTransferOrderController::getTransferShippingDetail return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JXCBSTransferOrderController::getTransferShippingDetail end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }
}
