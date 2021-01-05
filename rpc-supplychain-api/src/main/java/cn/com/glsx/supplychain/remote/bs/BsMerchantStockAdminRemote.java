package cn.com.glsx.supplychain.remote.bs;

import java.util.HashMap;
import java.util.List;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.SupplyRequest;
import cn.com.glsx.supplychain.model.bs.BsMerchantStock;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockCainou;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockRetn;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSell;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSett;

/**
 * @author liuquan
 * @version V1.0
 * @Title: BsMerchantStockAdminRemote.java
 * @Description: 商户库存调拨相关接口(针对运营后台以及进销存管理后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "商户库存调拨相关接口(针对运营后台以及进销存管理后台)", owner = "supplychain_bs", version = "1.0.0")
public interface BsMerchantStockAdminRemote {

	@ApiMethod("获取库存列表")
    @ApiResponse(value = "返回库存列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<BsMerchantStock>> pageMerchantStocks(SupplyRequest request,RpcPagination<BsMerchantStock> pagination);
	
	@ApiMethod("统计数量")
    @ApiResponse(value = "返回库存列表")
    @ApiParam(name = "record", notes = "统计数量输出，输入参数  填:商户号 商户下面的统计结果, 不填运营平台统计", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsMerchantStock> statMerchantStocks(SupplyRequest request,BsMerchantStock record);
	
	@ApiMethod("出货数量详情")
    @ApiResponse(value = "出货数量详情")
    @ApiParam(name = "pagination", notes = "商户号,物料编码必填", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<BsMerchantStockSell>> pageMerchantStockSell(SupplyRequest request,RpcPagination<BsMerchantStockSell> pagination);
	
	@ApiMethod("退货数量详情")
    @ApiResponse(value = "退货数量详情")
    @ApiParam(name = "pagination", notes = "商户号,物料编码必填", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<BsMerchantStockRetn>> pageMerchantStockRetn(SupplyRequest request,RpcPagination<BsMerchantStockRetn> pagination);
	
	@ApiMethod("结算数量详情")
    @ApiResponse(value = "结算数量详情")
    @ApiParam(name = "pagination", notes = "商户号,物料编码必填", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<BsMerchantStockSett>> pageMerchantStockSett(SupplyRequest request,RpcPagination<BsMerchantStockSett> pagination);
	
	@ApiMethod("获取库存列表(导出用)")
    @ApiResponse(value = "返回库存列表")
    @ApiParam(name = "record", notes = "条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<BsMerchantStock>> listMerchantStocks(SupplyRequest request,BsMerchantStock record);
	
	@ApiMethod("调入调出列表")
    @ApiResponse(value = "返回调入调出列表/调入调出数量详情")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<BsMerchantStockCainou>> pageMerchantStockCainou(SupplyRequest request,RpcPagination<BsMerchantStockCainou> pagination);
	
	@ApiMethod("添加调入调出列表")
    @ApiResponse(value = "添加调入调出列表")
    @ApiParam(name = "record", notes = "调入调出对象实体", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsMerchantStockCainou> addMerchantStockCainou(SupplyRequest request,BsMerchantStockCainou record);
	
	@ApiMethod("修改调入调出列表")
    @ApiResponse(value = "修改调入调出列表")
    @ApiParam(name = "pagination", notes = "调入调出对象实体 id必填", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsMerchantStockCainou> updateMerchantStockCainou(SupplyRequest request,BsMerchantStockCainou record);
	
	
	@ApiMethod("获取设备类型列表")
    @ApiResponse(value = "获取设备类型列表")
    @ApiParam(name = "pagination", notes = "获取设备类型列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<DeviceType>> listMerchantStockDeviceType(SupplyRequest request,DeviceType record);
	/**
	* 获取商户列表
	* @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
	*/
	@SuppressWarnings("rawtypes")
	@ApiMethod("获取商户列表")
    @ApiResponse(value = "获取商户列表")
    @ApiParam(name = "targetPage", notes = "分页单数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List> listMerchantStockMerchantInfo(HashMap<String, Object> condition,Integer targetPage, Integer pageSize);
}
