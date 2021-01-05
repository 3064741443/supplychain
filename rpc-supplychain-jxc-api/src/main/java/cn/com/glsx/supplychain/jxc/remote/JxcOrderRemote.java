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
 * @Title: JxcOrderRemote.java
 * @Description: 进销存重构商户总订单，子订单,发货单接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "进销存重构商户总订单，子订单,发货单接口", owner = "supplychain", version = "1.0.0")
public interface JxcOrderRemote {
	
	@ApiMethod("获取商户总订单详情")
	@ApiResponse(value = "获取商户总订单详情(经销存)")
	@ApiParam(name = "JXCMTMerchantOrderSubmitDTO", notes = "商户提交总订单(经销存)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTMerchantOrderDTO> getJxcMerchantOrder(JXCMTMerchantOrderDTO record);
	
	@ApiMethod("取消商户总订单详情")
	@ApiResponse(value = "取消商户总订单详情(经销存)")
	@ApiParam(name = "JXCMTMerchantOrderSubmitDTO", notes = "商户提交总订单(经销存)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> cancelJxcMerchantOrder(JXCMTMerchantOrderDTO record);
	
	@ApiMethod("修改商户总订单详情")
	@ApiResponse(value = "修改商户总订单详情(经销存)")
	@ApiParam(name = "JXCMTMerchantOrderSubmitDTO", notes = "商户提交总订单(经销存)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> updateJxcMerchantOrder(JXCMTMerchantOrderDTO record);
	
	@ApiMethod("商户提交总订单")
	@ApiResponse(value = "商户提交总订单(经销存)")
	@ApiParam(name = "JXCMTMerchantOrderSubmitDTO", notes = "商户提交总订单(经销存)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> submitJxcMerchantOrder(JXCMTMerchantOrderSubmitDTO record);
	
	@ApiMethod("采购订单列表")
	@ApiResponse(value = "采购订单列表")
	@ApiParam(name = "JXCMTBsMerchantOrderDTO", notes = "采购订单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTBsMerchantOrderDTO>> pageBsMerchantOrderJXC(RpcPagination<JXCMTBsMerchantOrderDTO> pagination);

	@ApiMethod("采购订单列表导出")
	@ApiResponse(value = "采购订单列表导出")
	@ApiParam(name = "JXCMTBsMerchantOrderDTO", notes = "采购订单列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JXCMTBsMerchantOrderDTO>> exportPurchaseOrder(JXCMTBsMerchantOrderDTO record);

	@ApiMethod("商务审核订单列表")
	@ApiResponse(value = "商务审核订单列表")
	@ApiParam(name = "JXCMTBsMerchantOrderDTO", notes = "商务审核订单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTBsMerchantOrderDTO>> pageBsMerchantOrderBSS(RpcPagination<JXCMTBsMerchantOrderDTO> pagination);
	
	@ApiMethod("商务审核订单列表导出")
	@ApiResponse(value = "商务审核订单列表导出")
	@ApiParam(name = "JXCMTBsMerchantOrderDTO", notes = "商务审核订单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsMerchantOrderExportDTO>> exportBsMerchantOrderBSS(JXCMTBsMerchantOrderDTO record);
	
	@ApiMethod("供应链分配订单列表")
	@ApiResponse(value = "供应链分配订单列表")
	@ApiParam(name = "JXCMTBsMerchantOrderDTO", notes = "供应链分配订单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTSpMerchantOrderDTO>> pageBsMerchantOrderBSP(RpcPagination<JXCMTSpMerchantOrderDTO> pagination);
	
	@ApiMethod("供应链分配订单列表导出")
	@ApiResponse(value = "供应链分配订单列表导出")
	@ApiParam(name = "JXCMTBsMerchantOrderDTO", notes = "供应链分配订单列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsMerchantOrderExportBspDTO>> exportBsMerchantOrderExportBSP(JXCMTSpMerchantOrderDTO record);
	
	@ApiMethod("获取订单详情")
	@ApiResponse(value = "获取订单详情")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "获取订单详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTBsMerchantOrderDetailDTO> getBsOrder(JXCMTBsMerchantOrderDTO record);
	
	@ApiMethod("订单驳回")
	@ApiResponse(value = "订单驳回")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "订单驳回", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> rebackBsOrder(JXCMTBsMerchantOrderDTO record);
	
	@ApiMethod("供应链订单驳回")
	@ApiResponse(value = "供应链订单驳回")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "供应链订单驳回", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> rebackSpOrder(JXCMTBsMerchantOrderRebackDTO record);
	
	
	@ApiMethod("供应链订单撤回")
	@ApiResponse(value = "供应链订单撤回")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "供应链订单撤回", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> recallSpOrder(JXCMTBsMerchantOrderRecallDTO record);
	
	@ApiMethod("供应链订单提前结束")
	@ApiResponse(value = "供应链订单提前结束")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "供应链订单提前结束", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> finishSpOrder(JXCMTBsMerchantOrderFinishDTO record);
	
	@ApiMethod("订单审核")
	@ApiResponse(value = "订单审核")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "订单审核", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> checkBsOrder(JXCMTBsOrderCheckDTO record);
	
	@ApiMethod("订单修改")
	@ApiResponse(value = "订单修改")
	@ApiParam(name = "JXCMTBsMerchantOrderDetailDTO", notes = "订单审核", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> updateBsOrder(JXCMTBsOrderCheckDTO record);
	
	@ApiMethod("获取发货明细")
	@ApiResponse(value = "获取发货明细")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "获取发货明细", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> listBsMerchantOrderDetail(JXCMTBsMerchantOrderDTO record);

	@ApiMethod("获取车辆信息")
	@ApiResponse(value = "获取车辆信息")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "获取车辆信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> listVehicleInformation();

	@ApiMethod("生成获取签收单")
	@ApiResponse(value = "生成获取签收单")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "生成获取签收单", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsMerchantOrderSignDTO>> generatorMerchantOrderSign(JXCMTBsMerchantOrderGenSignDTO record);

	@ApiMethod("获取子订单车辆审核列表")
	@ApiResponse(value = "获取子订单车辆审核列表")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "获取子订单车辆审核列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> listSpOrderVehicle(JXCMTSpOrderVehicleQueryDTO record);

	@ApiMethod("子订单扫码模式分配发货(单个)")
	@ApiResponse(value = "子订单扫码模式分配发货(单个)")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "子订单扫码模式分配发货(单个)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> dispatchSpOrderScanYSingle(JXCMTSpOrderDispatchScanYDTO record);
	
	@ApiMethod("子订单扫码模式分配发货(批量)")
	@ApiResponse(value = "子订单扫码模式分配发货(批量)")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "子订单扫码模式分配发货(批量)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> dispatchSpOrderScanYBatch(JXCMTSpOrderDispatchScanYDTO record);
	
	@ApiMethod("子订单不扫码模式分配发货(单个)")
	@ApiResponse(value = "子订单不扫码模式分配发货(单个)")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "子订单扫码模式分配发货(单个)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> dispatchSpOrderScanNSingle(JXCMTSpOrderDispatchScanNDTO record);
	
	@ApiMethod("子订单不扫码模式分配发货(批量)")
	@ApiResponse(value = "子订单不扫码模式分配发货(批量)")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "子订单扫码模式分配发货(批量)", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> dispatchSpOrderScanNBatch(JXCMTSpOrderDispatchScanNDTO record);
	
	@ApiMethod("子订单不扫码模式直接发货")
	@ApiResponse(value = "子订单不扫码模式直接发货")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "子订单不扫码模式直接发货", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> dispatchSpOrderDirect(JXCMTSpOrderDispatchDirectDTO record);
	
	@ApiMethod("发货单列表")
	@ApiResponse(value = "发货单列表")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "发货单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTOrderInfoDTO>> pageDispatchOrder(RpcPagination<JXCMTOrderInfoDTO> pagination);

	@ApiMethod("发货单列表导出")
	@ApiResponse(value = "发货单列表导出")
	@ApiParam(name = "JXCMTOrderInfoDTO", notes = "发货单列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTOrderInfoDTO>> exportDispatchOrder(JXCMTOrderInfoDTO record);


	@ApiMethod("获取签收单页面的发货单列表")
	@ApiResponse(value = "获取签收单页面的发货单列表")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "发货单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTOrderInfoSignDTO>> pageSignOrders(RpcPagination<JXCMTOrderInfoSignDTO> pagination);
	
	@ApiMethod("获取签收单列表")
	@ApiResponse(value = "获取签收单列表")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "发货单列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTBsBillDTO>> pageSignBillNumber(RpcPagination<JXCMTBsBillDTO> pagination);
	
	@ApiMethod("生成获取签收单")
	@ApiResponse(value = "生成获取签收单")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "生成获取签收单", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> genBillNumber(JXCMTGenBillNumberDTO record);
	
	@ApiMethod("生成获取签收单记录")
	@ApiResponse(value = "生成获取签收单记录")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "生成获取签收单", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> genBillNumberRecord(JXCMTBsMerchantOrderSignSuplyDTO record);
	
	@ApiMethod("发货单明细列表")
	@ApiResponse(value = "发货单明细列表")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "发货单明细列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTOrderInfoDetailDTO>> pageDispatchOrderDetail(JXCMTOrderInfoDetailDTO record);

	@ApiMethod("获取发货单商户列表")
	@ApiResponse(value = "获取发货单商户列表")
	@ApiParam(name = "JXCMTBsMerchantOrderVehicleDTO", notes = "发货单明细列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTOrderInfoMerchantDTO>> pageDispatchOrderMerchant(RpcPagination<JXCMTOrderInfoMerchantDTO> pagination);

	@ApiMethod("根据发货单获取广汇订单的配置选项")
	@ApiResponse(value = "根据发货单获取广汇订单的配置选项")
	@ApiParam(name = "JXCMTGhMerchantOrderConfigDTO", notes = "根据发货单获取广汇订单的配置选项", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTGhMerchantOrderConfigDTO> getGhMerchantOrderConfig(JXCMTGhMerchantOrderConfigDTO record);
}
