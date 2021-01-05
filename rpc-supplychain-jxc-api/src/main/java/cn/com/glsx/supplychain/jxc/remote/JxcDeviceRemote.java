package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jxc.dto.JXCMTCheckImportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOderDeviceDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceInfoImport;
import cn.com.glsx.supplychain.jxc.vo.CheckImportDataVo;

import java.util.List;

/**
 * @author ql
 * @version V1.0
 * @Title: JxcCommonRemote.java
 * @Description: 设备等相关接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "设备等相关接口", owner = "supplychain", version = "1.0.0")
public interface JxcDeviceRemote {
	
	@ApiMethod("扫码出库")
	@ApiResponse(value = "扫码出库")
	@ApiParam(name = "JXCMTOderDeviceDTO", notes = "扫码出库", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> dispatchDeviceDispatch(JXCMTOderDeviceDTO record);
	
	@ApiMethod("取消出库")
	@ApiResponse(value = "取消出库")
	@ApiParam(name = "JXCMTOderDeviceDTO", notes = "取消出库", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> cancelDispatchDevice(JXCMTOderDeviceDTO record);
	
	@ApiMethod("excel导入出库验证")
	@ApiResponse(value = "excel导入出库验证")
	@ApiParam(name = "JXCMTOderDeviceDTO", notes = "扫码出库", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTCheckImportDTO> checkBatchDispatchDevice(JXCMTOderDeviceDTO record);
	
	@ApiMethod("excel导入出库验证")
	@ApiResponse(value = "excel导入出库")
	@ApiParam(name = "JXCMTOderDeviceDTO", notes = "扫码出库", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTCheckImportDTO> importBatchDispatchDevice(JXCMTOderDeviceDTO record);
	
	@ApiMethod("excel导入出库验证")
	@ApiResponse(value = "excel导入出库")
	@ApiParam(name = "JXCMTOderDeviceDTO", notes = "扫码出库", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> deliveryDirect(JXCMTOderDeviceDTO record);

	@ApiMethod("设备库存管理导入设备明细数据校验")
	@ApiResponse(value = "设备库存管理导入设备明细数据校验")
	@ApiParam(name = "CheckImportDataVo", notes = "校验数据", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> checkImportDeviceList(List<JXCMTDeviceInfoImport> importList);

	@ApiMethod("设备库存管理导入设备明细")
	@ApiResponse(value = "设备库存管理导入设备明细")
	@ApiParam(name = "Integer", notes = "导入设备数量", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> importDeviceInfoList(String userName,List<JXCMTDeviceInfoImport> importList);


	@ApiMethod("设备关系明细列表导出")
	@ApiResponse(value = "返回设备关系明细列表")
	@ApiParam(name = "deviceFileDTO", notes = "设备明细对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JXCMTDeviceFileDTO>> exportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO);

}
