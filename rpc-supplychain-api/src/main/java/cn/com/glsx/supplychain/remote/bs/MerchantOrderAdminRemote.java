package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import cn.com.glsx.supplychain.vo.MerchantOrderSignVo;

import java.util.Date;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: MerchantOrderAdminRemote.java
 * @Description: 商户订单接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "商户订单接口(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface MerchantOrderAdminRemote {

    @ApiMethod("获取商户订单分页列表")
    @ApiResponse(value = "返回商户订单分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<MerchantOrder>> listMerchantOrder(RpcPagination<MerchantOrder> pagination);

    @ApiMethod("获取商户订单列表")
    @ApiResponse(value = "返回商户订单列表")
    @ApiParam(name = "merchantOrder", notes = "商户订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<MerchantOrder>> getMerchantOrderList(MerchantOrder merchantOrder);

    @ApiMethod("根据商户订单号获取商户订单")
    @ApiResponse(value = "返回商户订单")
    @ApiParam(name = "merchantOrderNumber", notes = "商户订单号", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<MerchantOrder> getMerchantOrderByMerchantOrderNumber(String merchantOrderNumber);

    @ApiMethod("新增商户订单")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "MerchantOrder", notes = "商户订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(MerchantOrder merchantOrder);

    @ApiMethod("新增商户订单(填补老产品)")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "MerchantOrder", notes = "商户订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addSplit(MerchantOrder merchantOrder);

    @ApiMethod("修改商户订单状态")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "status", notes = "商户订单状态", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateOrderStatus(MerchantOrder merchantOrder);

    @ApiMethod("签收订单")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "merchantOrderDetailId", notes = "签收订单", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> acceptMerchantOrder(MerchantOrderDetail merchantOrderDetail);

    @ApiMethod("订单审核")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "merchantOrderDetailId", notes = "订单审核", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> checkMerchantOrder(MerchantOrder merchantOrder);

    @ApiMethod("分配发货")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "merchantOrderDetailId", notes = "分配发货", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addMerchantOrderDispatch(Long merchantOrderDetailId,String dispatchOrderNumber,String updatedBy);

    @ApiMethod("根据物流信息获取发货数量")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "listOrderInfoDetail", notes = "查询物流信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<OrderInfoDetail>> listOrderInfoDetail(List<OrderInfoDetail> orderInfoDetail);

    @ApiMethod("直接发货(不通过扫码工具)")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "merchantOrder", notes = "直接发货", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> sendGoodsUpdateMerchantOrderStatu(MerchantOrder merchantOrder);

    @ApiMethod("商户订单导出")
    @ApiResponse(value = "商户订单列表导出")
    @ApiParam(name = "merchantOrder", notes = "商户订单列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<MerchantOrderExcelVo>> exportMerchantOrderExit(MerchantOrder merchantOrder);

    /*@ApiMethod("商户订单导入check")
    @ApiResponse(value = "商户订单列表导入")
    @ApiParam(name = "merchantOrder", notes = "商户订单列表导入check", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportMerchantOrderList(List<MerchantOrderImport> merchantOrderImportList);*/

    /*@ApiMethod("商户订单导入")
    @ApiResponse(value = "商户订单列表导入")
    @ApiParam(name = "merchantOrder", notes = "商户订单列表导入", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> importMerchantOrderList(List<MerchantOrderImport> merchantOrderImportList);*/

    @ApiMethod("查询物流信息根据业务Code")
    @ApiResponse(value = "查询物流信息根据业务Code")
    @ApiParam(name = "logistics", notes = "查询物流信息根据业务Code", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<Logistics>> getLogisticsInfoListByServiceCode(Logistics logistics);

    @ApiMethod("修改物流信息根据物流ID")
    @ApiResponse(value = "修改物流信息根据物流ID")
    @ApiParam(name = "logistics", notes = "修改物流信息根据物流ID", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(Logistics logistics);

    @ApiMethod("修改物流信息根据物流ID")
    @ApiResponse(value = "修改物流信息根据物流ID")
    @ApiParam(name = "logistics", notes = "修改物流信息根据物流ID", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<OrderInfo> getOrderInfoByOrderCode(String orderCode);

    @ApiMethod("获取项目定义列表")
    @ApiResponse(value = "返回项目定义列表list")
    @ApiParam(name = "subject", notes = "查询项目定义列表参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<Subject>> getSubjectlist(Subject subject);
    
    @ApiMethod("根据订单编号获取发货信息")
    @ApiResponse(value = "返回项目定义列表list")
    @ApiParam(name = "subject", notes = "查询项目定义列表参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<MerchantOrderSignVo>> genSignOrderByMerchantOrderNum(String receiveOrderNo,List<MerchantOrder> listMerchantOrder);
    
    @ApiMethod("添加和修改签收单")
    @ApiResponse(value = "返回项目定义列表list")
    @ApiParam(name = "subject", notes = "查询项目定义列表参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> batchAddMerchantOrderSign(List<BsMerchantOrderSign> listOrderSign);
    
    @ApiMethod("修改签收单")
    @ApiResponse(value = "返回项目定义列表list")
    @ApiParam(name = "subject", notes = "查询项目定义列表参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateMerchantOrderSignBySignCode(BsMerchantOrderSign merchantOrderSign);
    
    @ApiMethod("根据订单好获取签收单")
    @ApiResponse(value = "返回项目定义列表list")
    @ApiParam(name = "subject", notes = "查询项目定义列表参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<BsMerchantOrderSign>> listMerchantOrderSignByMerchantOrder(List<String> listMerchantOrders);
}
