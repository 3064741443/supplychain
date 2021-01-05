package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceImeiStokeListImport;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

import java.util.Date;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: AfterSaleOrderAdminRemote.java
 * @Description: 售后订单接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "售后订单接口(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface AfterSaleOrderAdminRemote {

    @ApiMethod("获取售后订单分页列表")
    @ApiResponse(value = "返回售后订单分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<AfterSaleOrder>> listAfterSaleOrder(RpcPagination<AfterSaleOrder> pagination);

    @ApiMethod("根据订单号获取订售后单对象")
    @ApiResponse(value = "返回售后订单对象")
    @ApiParam(name = "AfterSaleOrder", notes = "订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<AfterSaleOrder> getAfterSaleOrderByOrderNumber(String orderNumber);

    @ApiMethod("获取订售后单对象")
    @ApiResponse(value = "返回售后订单对象")
    @ApiParam(name = "AfterSaleOrder", notes = "订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<AfterSaleOrder> getAfterSaleOrder(AfterSaleOrder afterSaleOrder);

    @ApiMethod("新增售后订单")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "AfterSaleOrder", notes = "售后订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(AfterSaleOrder afterSaleOrder);

    @ApiMethod("修改售后订单")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "afterSaleOrder", notes = "订单对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateByOrderNumber(AfterSaleOrder afterSaleOrder);

    @ApiMethod("修改售后订单物流信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "status", notes = "售后订单状态", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateAfterSaleOrderLogistics(Logistics logistics);

    @ApiMethod("修改售后订单物流信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "status", notes = "售后订单状态", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateAfterSaleOrderLogisticsByServiceCodeAndType(Logistics logistics);

    @ApiMethod("新增维修物流信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "logistics", notes = "物流对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addAfterSaleOrderLogistics(Logistics logistics);

    @ApiMethod("查询物流信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "logistics", notes = "物流对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse <Logistics> getLogistics(Logistics logistics);

    @ApiMethod("Sn导入(excel)数据")
    @ApiResponse(value = "返回sn导入(excel)数据结果")
    @ApiParam(name = "importList",notes = "导入集合",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportSnList(List<SnImport> snImportList);

    @ApiMethod("发货记录Sn导入(excel)数据")
    @ApiResponse(value = "返回sn导入(excel)数据结果")
    @ApiParam(name = "importList",notes = "导入集合",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<CheckImportDataVo> checkImportSnChangeList(List<SnChangeImport> snChangeImportList);

    @ApiMethod("售后订单添加发货物流信息")
    @ApiResponse(value = "售后订单添加发货物流信息")
    @ApiParam(name = "logistics", notes = "添加发货物流信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> AfterSaleOrderDispatchAddLogistics(Logistics logistics);

    @ApiMethod("发货记录Sn导入(excel)数据")
    @ApiResponse(value = "返回sn导入(excel)数据结果")
    @ApiParam(name = "importList",notes = "导入集合",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> insertAfterSaleOrderDetailList(List<AfterSaleOrderDetail> afterSaleOrderDetailList);

    @ApiMethod("查询售后订单详情为发货的SN")
    @ApiResponse(value = "查询售后订单详情为发货的SN信息")
    @ApiParam(name = "afterSaleOrderDetail", notes = "查询未发货SN信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<AfterSaleOrderDetail>> getAfterSalesSnList(AfterSaleOrderDetail afterSaleOrderDetail);

    @ApiMethod("查询售后订单实际发货的SN信息")
    @ApiResponse(value = "查询售后订单实际发货的SN信息")
    @ApiParam(name = "afterSaleOrderDetail", notes = "查询已完成售后处理的SN信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<MaintainSnChange>> getMainTainSnChangeList(MaintainProductDetail maintainProductDetail);

    @ApiMethod("修改物流信息根据物流ID")
    @ApiResponse(value = "修改物流信息根据物流ID")
    @ApiParam(name = "logistics", notes = "修改物流信息根据物流ID", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceInfo> getDeviceInfoByImeiOrSn(String imei);

    @ApiMethod("查询DeviceFile信息")
    @ApiResponse(value = "查询DeviceFile信息")
    @ApiParam(name = "sn", notes = "查询DeviceFile信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceFile> getDeviceFileBySn(String sn);
}
