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
 * @Title: StkMerchantStockRemote.java
 * @Description: 针对调拨订单rpc接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "服务商库存RPC接口", owner = "supplychain", version = "1.0.0")
public interface StkMerchantStockRemote {

	@ApiMethod("库存管理列表查询")
	@ApiResponse(value = "库存管理列表")
	@ApiParam(name = "STKMerchantStockDTO", notes = "库存管理列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKMerchantStockDTO>> pageMerchantStock(RpcPagination<STKMerchantStockDTO> pagination);

	@ApiMethod("库存管理列表导出")
	@ApiResponse(value = "库存管理列表导出")
	@ApiParam(name = "STKMerchantStockDTO", notes = "库存管理列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKMerchantStockDTO>> exportMerchantStock(STKMerchantStockDTO record);
	
	@ApiMethod("库存扣减导入数据验证")
	@ApiResponse(value = "库存扣减导入数据验证")
	@ApiParam(name = "STKMerchantStockDeductionDTO", notes = "库存扣减导入数据验证", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<STKMerchantStockDeductionDTO> importMerchantStockDeductionCheck(STKMerchantStockDeductionDTO record);
	
	@ApiMethod("库存扣减导入数据")
	@ApiResponse(value = "库存扣减导入数据")
	@ApiParam(name = "STKMerchantStockDeductionDTO", notes = "库存扣减导入数据", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> importMerchantStockDeduction(STKMerchantStockDeductionDTO record);
	
	@ApiMethod("预警设置列表")
	@ApiResponse(value = "预警设置列表")
	@ApiParam(name = "STKMerchantWarningSetDTO", notes = "预警设置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKWarningSetDTO>> pageMerchantWarningSet(RpcPagination<STKWarningSetDTO> pagination);
	
	@ApiMethod("新增预警设置")
	@ApiResponse(value = "新增预警设置")
	@ApiParam(name = "STKMerchantWarningSetDTO", notes = "新增预警设置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> subMerchantWarningSet(STKWarningSetDTO record);
	
	@ApiMethod("修改预警设置")
	@ApiResponse(value = "修改预警设置")
	@ApiParam(name = "STKMerchantWarningSetDTO", notes = "新增预警设置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> updateMerchantWarningSet(STKWarningSetDTO record);
	
	@ApiMethod("库销比预警列表")
	@ApiResponse(value = "库销比预警列表")
	@ApiParam(name = "STKMerchantWarningWaresaleDTO", notes = "新增预警设置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKWarningWaresaleDTO>> pageMerchantWarningWaresale(RpcPagination<STKWarningWaresaleDTO> pagination);
	
	@ApiMethod("库销比预警列表导出")
	@ApiResponse(value = "库销比预警列表导出")
	@ApiParam(name = "STKMerchantWarningWaresaleDTO", notes = "库销比预警列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKWarningWaresaleDTO>> exportMerchantWarningWaresale(STKWarningWaresaleDTO record);
	
	@ApiMethod("呆滞品预警-按照设备类型分类统计")
	@ApiResponse(value = "呆滞品预警-按照设备类型分类统计")
	@ApiParam(name = "STKWarningDevTypeAssumeDTO", notes = "呆滞品预警-按照设备类型分类统计", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<STKWarningDevTypeAssumeDTO> getMerchantWarningDeviceTypeAssume(JXCMTBaseDTO record);
	
	@ApiMethod("呆滞品预警-按照商户-物料-预警编码分类统计")
	@ApiResponse(value = "呆滞品预警-按照商户-物料-预警编码分类统计")
	@ApiParam(name = "STKWarningDevTypeAssumeDTO", notes = "呆滞品预警-按照商户-物料-预警编码分类统计", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKWarningMaterialAssumeDTO>> pageMerchantWarningMaterialAssume(RpcPagination<STKWarningMaterialAssumeDTO> pagination);

	@ApiMethod("呆滞品设备明细列表")
	@ApiResponse(value = "呆滞品设备明细列表")
	@ApiParam(name = "STKWarningDevicesnDTO", notes = "呆滞品设备明细列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKWarningDevicesnDTO>> pageMerchantWarningDeviceSn(RpcPagination<STKWarningDevicesnDTO> pagination);

	@ApiMethod("呆滞品设备明细列表导出")
	@ApiResponse(value = "呆滞品设备明细列表导出")
	@ApiParam(name = "STKWarningDevicesnDTO", notes = "呆滞品设备明细列表导出", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKWarningDevicesnDTO>> exportMerchantWarningDeviceSn(STKWarningDevicesnDTO warningDevicesnDTO);

	@ApiMethod("设备激活统计 按照设备大类")
	@ApiResponse(value = "设备激活统计 按照设备大类")
	@ApiParam(name = "STKDeviceSnUnActiveStatDTO", notes = "设备激活统计 按照设备大类", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKMerchantStockSnStatDTO>> getMerchantStockSnStatByDeviceType(STKMerchantStockSnStatDTO record);
	
	@ApiMethod("设备激活统计 按照设备大类,商户")
	@ApiResponse(value = "设备激活统计 按照设备大类，商户")
	@ApiParam(name = "STKDeviceSnUnActiveStatDTO", notes = "设备激活统计 按照设备大类，商户", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKMerchantStockSnStatDTO>> pageMerchantStockSnStatByToMerchantCode(RpcPagination<STKMerchantStockSnStatDTO> pagination);
	
	@ApiMethod("设备激活统计导出 按照设备大类,商户")
	@ApiResponse(value = "设备激活统计导出 按照设备大类，商户")
	@ApiParam(name = "STKDeviceSnUnActiveStatDTO", notes = "设备激活统计导出 按照设备大类，商户", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKMerchantStockSnStatDTO>> exportMerchantStockSnStatByToMerchantCode(STKMerchantStockSnStatDTO record);
	
	@ApiMethod("导出库存设备明细")
	@ApiResponse(value = "导出库存设备明细")
	@ApiParam(name = "STKMerchantStockSnDTO", notes = "未激活统计导出 按照设备大类，商户", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKMerchantStockSnDTO>> exportMerchantStockSn(STKMerchantStockSnDTO record);
	
	@ApiMethod("服务商库存产品配置列表")
	@ApiResponse(value = "服务商库存产品配置列表")
	@ApiParam(name = "STKProductConfigDTO", notes = "服务商库存产品配置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<STKProductConfigDTO>> pageMerchantProductConfig(STKProductConfigQueryDTO record);
	
	@ApiMethod("根据操作码获取服务商库存产品配置")
	@ApiResponse(value = "根据操作码获取服务商库存产品配置")
	@ApiParam(name = "STKProductConfigDTO", notes = "服务商库存产品配置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<STKProductConfigDTO>> getMerchantProductConfigByOperatorCode(STKProductConfigDTO record);
	
	@ApiMethod("新增服务商库存产品配置")
	@ApiResponse(value = "新增服务商库存产品配置")
	@ApiParam(name = "STKProductConfigDTO", notes = "服务商库存产品配置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> addMerchantProductConfig(STKProductConfigSubmitDTO record);
	
	@ApiMethod("修改服务商库存产品配置")
	@ApiResponse(value = "修改服务商库存产品配置")
	@ApiParam(name = "STKProductConfigDTO", notes = "服务商库存产品配置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> updateMerchantProductConfig(STKProductConfigSubmitDTO record);
}
