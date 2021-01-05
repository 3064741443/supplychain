package cn.com.glsx.supplychain.jst.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.dto.*;

import java.util.List;

/**
 * @author ql
 * @version V1.0
 * @Title: JxBsMerchantRemote.java
 * @Description: 进销存系统后台rpc接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "针对进销存系统后台rpc", owner = "supplychain", version = "1.0.0")
public interface JxBsMerchantRemote {

	@ApiMethod("获取经销存系统设备发货分页列表")
	@ApiResponse(value = "返回经销存系统设备发货分页列表")
	@ApiParam(name = "jstShopOrderDTO", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<JstShopOrderDTO>> pageJstShopOrder(RpcPagination<JstShopOrderDTO> jstShopOrderDTO);

	@ApiMethod("经销存系统订单发货,发货明细SN上传效验接口")
	@ApiResponse(value = "返回效验结果")
	@ApiParam(name = "snList", notes = "snList", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
//	RpcResponse<CheckImportDataDTO> checkImportDispatchDetail(List<JstShopOrderDetailImportDTO> jstShopOrderDetailImportDTOList);
	RpcResponse<CheckImportShopOrderDetailDTO> checkImportDispatchDetail(CheckImportShopOrderDetailDTO record);
	
	@ApiMethod("经销存系统订单发货")
	@ApiResponse(value = "返回成功结果")
	@ApiParam(name = "jstShopOrderDetailDTO", notes = "发货明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	//RpcResponse<Integer> dispatchJstShopOrderDetail(JstShopOrderDetailDTO jstShopOrderDetailDTO);
	RpcResponse<CheckImportShopOrderDetailDTO> dispatchJstShopOrderDetail(CheckImportShopOrderDetailDTO record);
	
	
	@ApiMethod("经销存系统订单查看发货数量")
	@ApiResponse(value = "返回订单明细")
	@ApiParam(name = "jstShopOrderDetailDTO", notes = "发货明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JstShopOrderDetailDTO>> getJstShopOrderDetailList(JstShopOrderDetailDTO jstShopOrderDetailDTO);

	@ApiMethod("获取经销存系统设备明细分页列表")
    @ApiResponse(value = "返回经销存系统设备明细分页列表")
    @ApiParam(name = "bsMerchantStockDeviceDTO", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<BsMerchantStockDeviceDTO>> pageBsMerchantStockDevice(RpcPagination<BsMerchantStockDeviceDTO> bsMerchantStockDeviceDTO);

	@ApiMethod("经销存系统设备明细总数查询")
	@ApiResponse(value = "返回明细总数")
	@ApiParam(name = "bsMerchantStockDeviceDTO", notes = "设备明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsMerchantStockDeviceDTO> getSumBsMerchantStockDeviceList(BsMerchantStockDeviceDTO bsMerchantStockDeviceDTO);

	@ApiMethod("获取经销存系统我的门店分页列表")
	@ApiResponse(value = "返回经销存系统我的门店分页列表")
	@ApiParam(name = "JstShopDTO", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<JstShopDTO>> pageMyJstShop(RpcPagination<JstShopDTO> JstShopDTO);

	@ApiMethod("经销存系统我的门店导入效验")
	@ApiResponse(value = "返回效验结果")
	@ApiParam(name = "jstShopImportDTO", notes = "门店导入对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataDTO> checkImportMyJstShop(List<MyJstShopImportDTO> myJstShopImportDTOList);

	@ApiMethod("经销存系统我的门店导入")
	@ApiResponse(value = "返回导入结果")
	@ApiParam(name = "jstShopImportDTO", notes = "门店导入对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> importMyJstShop(List<MyJstShopImportDTO> jstShopImportDTOList);

	@ApiMethod("获取供货关系维护分页列表")
	@ApiResponse(value = "返回供货关系维护分页列表")
	@ApiParam(name = "JstShopDTO", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<JstShopDTO>> pageJstShop(RpcPagination<JstShopDTO> JstShopDTO);

	@ApiMethod("供货关系维护导入效验")
	@ApiResponse(value = "返回效验结果")
	@ApiParam(name = "jstShopImportDTO", notes = "门店导入对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataDTO> checkImportJstShop(List<JstShopImportDTO> jstShopImportDTOList);

	@ApiMethod("供货关系维护导入")
	@ApiResponse(value = "返回导入结果")
	@ApiParam(name = "jstShopImportDTO", notes = "门店导入对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> importJstShop(List<JstShopImportDTO> jstShopImportDTOList);

	@ApiMethod("供货关系维护审核")
	@ApiResponse(value = "返回审核结果")
	@ApiParam(name = "JstShopDTO", notes = "门店对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> checkJstShop(JstShopDTO jstShopDTO);

	@ApiMethod("经销存系统无订单发货设备明细导入效验")
	@ApiResponse(value = "返回效验结果")
	@ApiParam(name = "noOrderDetailImportDTO", notes = "无订单发货设备明细导入对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataDTO> checkImportNoOrderDetail(List<NoOrderDetailImportDTO> noOrderDetailImportDTOList);

	@ApiMethod("无订单发货-批量验证sn")
	@ApiResponse(value = "订单明细")
	@ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportNoOrderDetailDTO> batchCheckShopOrderDetailNoOrder(CheckImportNoOrderDetailDTO record);


}
