package cn.com.glsx.supplychain.jst.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.dto.gh.*;

import java.util.List;

@ApiService(value = "车型车辆配置相关接口", owner = "supplychain", version = "1.0.0")
public interface MotorcycleRemote {
	
	@ApiMethod("获取车辆父品牌列表")
	@ApiResponse(value = "返回配置分类列表")
	@ApiParam(name = "listParentBrands", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listParentBrands(AttribInfoDTO record);
	
	@ApiMethod("获取车辆子品牌列表")
	@ApiResponse(value = "返回配置分类列表")
	@ApiParam(name = "listSubBrands", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listSubBrands(GhProductConfigDTO record);
	
	@ApiMethod("获取车辆车系列表")
	@ApiResponse(value = "返回配置分类列表")
	@ApiParam(name = "listAudis", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listAudis(GhProductConfigDTO record);
	
	@ApiMethod("获取车辆车型列表")
	@ApiResponse(value = "返回配置分类列表")
	@ApiParam(name = "listAudis", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listMotorcyle(GhProductConfigDTO record);
	
	@ApiMethod("获取车型配置分类列表")
	@ApiResponse(value = "返回配置分类列表")
	@ApiParam(name = "pageAudiMotorcycle", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listAttribInfo(AttribInfoDTO record);
	
	@ApiMethod("获取商户下车系车型列表")
	@ApiResponse(value = "返回车系车型")
	@ApiParam(name = "pageAudiMotorcycle", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<AudiMotorcycleDTO>> pageAudiMotorcycle(RpcPagination<AudiMotorcycleDTO> pagination);
	
	@ApiMethod("获取商户下车产品配置列表")
	@ApiResponse(value = "返回产品配置表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<GhProductConfigDTO>> pageGhProductConfig(RpcPagination<GhProductConfigDTO> pagination);
	
	@ApiMethod("获取商户下车产品配置年款等列表")
	@ApiResponse(value = "返回产品配置表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listGhProductConfigModeYears(GhProductConfigDTO record);
	
	@ApiMethod("获取商户下车产品配置车系等列表")
	@ApiResponse(value = "返回产品配置表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfoDTO>> listGhProductConfigAudis(GhProductConfigDTO record);
	
	@ApiMethod("获取商户下车产品配置车型列表-带配置参数")
	@ApiResponse(value = "返回产品配置表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<GhProductConfigDTO>> pageGhProductConfigMotorcycle(RpcPagination<GhProductConfigDTO> pagination);

	@ApiMethod("导出商户下车产品配置车型列表-带配置参数")
	@ApiResponse(value = "返回产品配置表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<GhProductConfigDTO>> exportGhProductConfigMotorcycle(GhProductConfigDTO ghProductConfigDTO);


	
	@ApiMethod("获取商户下车产品配置选项配置列表(非车载导航类)")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<GhProductConfigOtherDTO>> listGhProductConfigOther(GhProductConfigDTO record);
	
	@ApiMethod("添加商户产品配置到购物车")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addGhProductConfigCart(GhShoppingCartDTO record);
	
	@ApiMethod("批量添加商户产品配置到购物车")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> batchAddGhProductConfigCart(List<GhShoppingCartDTO> listRecord);
	
	@ApiMethod("删除购物车")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delGhProductConfigCart(List<GhShoppingCartDTO> listRecord);
	
	@ApiMethod("修改除购物车")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> updateGhProductConfigCart(GhShoppingCartDTO record);
	
	@ApiMethod("购物车列表")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<GhShoppingCartDTO>> pageGhShoppingCart(RpcPagination<GhShoppingCartDTO> pagination);
	
	@ApiMethod("提叫订单")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> subGhMerchantOrder(GhSubMerchantOrderDTO record);

	@ApiMethod("订单列表（手机端）")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<GhMerchantOrderDTO>> pageGhMerchantOrder(RpcPagination<GhMerchantOrderDTO> pagination);

	@ApiMethod("订单列表导出")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<GhMerchantOrderDTO>> exportGhMerchantOrder(GhMerchantOrderDTO record);
	
	@ApiMethod("根据订单加购")
	@ApiResponse(value = "返回选项配置列表")
	@ApiParam(name = "pageGhProductConfig", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addShoppingCartFromMerchantOrder(GhMerchantOrderDTO record);
	
	@ApiMethod("获取商户最后下单地址")
    @ApiResponse(value = "返回商户地址列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsAddressDTO> getLastMerchantOrderAddress(BsAddressDTO record);


	@ApiMethod("广汇18家订单生成商户订单")
	@ApiResponse(value = "广汇18家订单生成商户订单")
	@ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer>  createMerchantOrderByGhOrder();

	@ApiMethod("")
	@ApiResponse(value = "同步商户订单状态到广汇订单")
	@ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer>  synchronizeMerchantOrderStatus();

	@ApiMethod("广汇采购订单列表")
	@ApiResponse(value = "广汇采购订单列表")
	@ApiParam(name = "ghMerchantOrderDTO", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<NewGhMerchantOrderDTO>> wxlistMerchantOrders(RpcPagination<NewGhMerchantOrderDTO> ghMerchantOrderDTO);

	@ApiMethod("广汇采购订单-催单")
	@ApiResponse(value = "广汇采购订单-催单")
	@ApiParam(name = "ghMerchantOrderDTO", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> wxReminderOrder(GhMerchantOrderDTO ghMerchantOrderDTO);

	@ApiMethod("广汇采购订单-取消订单")
	@ApiResponse(value = "广汇采购订单-取消订单")
	@ApiParam(name = "ghMerchantOrderDTO", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> wxCancelOrder(GhMerchantOrderDTO ghMerchantOrderDTO);

	@ApiMethod("查询物流信息根据业务Code")
	@ApiResponse(value = "查询物流信息根据业务Code")
	@ApiParam(name = "logistics", notes = "查询物流信息根据业务Code", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<BsLogisticsDTO>> wxGetLogisticsInfoListByServiceCode(BsLogisticsDTO logistics);

}
