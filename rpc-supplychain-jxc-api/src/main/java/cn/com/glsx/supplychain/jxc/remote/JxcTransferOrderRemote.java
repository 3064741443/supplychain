package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jxc.dto.*;

import java.util.List;

/**
 * @author ql
 * @version V1.0
 * @Title: TransferOrderRemote.java
 * @Description: 针对调拨订单rpc接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "针对调拨订单rpc接口", owner = "supplychain", version = "1.0.0")
public interface JxcTransferOrderRemote {

    @ApiMethod("获取服务商列表")
    @ApiResponse(value = "获取服务商列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<JXCDealerUserInfoDTO>> listServiceProvider(JXCDealerUserInfoDTO dealerUserInfoDTO);


    @ApiMethod("发起调拨生成调拨单")
    @ApiResponse(value = "发起调拨生成调拨单")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> generateTransferOrder(JXCMdbGenerateTransferOrderDTO generateTransferOrderDTO);

    @ApiMethod("商务发起调拨生成调拨单")
    @ApiResponse(value = "商务发起调拨生成调拨单")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> generateBsTransferOrder(JXCMdbGenerateBsTransferOrderDTO generateBsTransferOrderDTO);

    /*@ApiMethod("小程序调拨订单列表")
    @ApiResponse(value = "小程序调拨订单列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<JXCMdbTransferOrderDTO>> pageTransferOrder(RpcPagination<JXCMdbTransferOrderQueryDTO> pagination);

    @ApiMethod("商务调拨订单列表")
    @ApiResponse(value = "商务调拨订单列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<JXCBsTransferOrderDTO>> bsPageTransferOrder(RpcPagination<JXCBsTransferOrderQueryDTO> pagination);
*/


    @ApiMethod("调拨订单列表")
    @ApiResponse(value = "调拨订单列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<JXCTransferOrderDTO>> pageTransferOrderJXC(RpcPagination<JXCTransferOrderQueryDTO> pagination);

    @ApiMethod("进销存pc调拨订单列表导出")
    @ApiResponse(value = "进销存pc调拨订单列表导出")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<JXCMdbTransferOrderExportDTO>> exportJxcTransferOrder(JXCTransferOrderQueryDTO transferOrderQueryDTO);

    @ApiMethod("商务调拨订单列表导出")
    @ApiResponse(value = "商务调拨订单列表导出")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<JXCMdbBsTransferOrderExportDTO>> exportBsTransferOrder(JXCBsTransferOrderQueryDTO transferOrderQueryDTO);


    @ApiMethod("小程序调拨订单发货")
    @ApiResponse(value = "小程序调拨订单发货")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> commitTransferOrder(JXCMdbCommitTransferOrderDTO jxcMdbCommitTransferOrderDTO);

    @ApiMethod("小程序调拨订单详情")
    @ApiResponse(value = "小程序调拨订单详情")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<JXCMdbTransferOrderDTO> getTransferOrder(JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO);

    @ApiMethod("调拨发货详情")
    @ApiResponse(value = "调拨发货详情")
    @ApiParam(name = "transferOrderDetailQueryDTO", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<JXCLogisticsDTO>> getTransferShippingDetail(JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO);


    @ApiMethod("导入设备明细")
    @ApiResponse(value = "导入设备明细")
    RpcResponse<Integer> importEquipmentDetails(List<JXCTransferOrderImportDTO> transferOrderImportDTOList);

    @ApiMethod("调拨订单审核")
    @ApiResponse(value = "调拨订单审核")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> checkBsTransferOrder(JXCMdbTransferOrderCheckDTO transferOrderCheckDTO);

    @ApiMethod("调拨订单审核驳回")
    @ApiResponse(value = "调拨订单审核驳回")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> rebackBsTransferOrder(JXCMdbTransferOrderRebackDTO transferOrderRebackDTO);
}
