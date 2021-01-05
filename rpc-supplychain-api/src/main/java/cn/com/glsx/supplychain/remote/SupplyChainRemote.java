package cn.com.glsx.supplychain.remote;

import java.util.List;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.AttribMana;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.DeviceType;
import cn.com.glsx.supplychain.model.ExsysDeviceStatu;
import cn.com.glsx.supplychain.model.ExsysDispatchLog;
import cn.com.glsx.supplychain.model.ExsysIdentify;
import cn.com.glsx.supplychain.model.ExsysOrderInfo;
import cn.com.glsx.supplychain.model.FirmwareInfo;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.RequestLog;
import cn.com.glsx.supplychain.model.SupplyRequest;
import cn.com.glsx.supplychain.model.UserInfo;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.Logistics;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

/**
 * @Title: SupplyChainRemote.java
 * @Description: 供应链体系RPC接口(针对终端扫码工具和外部系统派发 web-supplychain)
 * @author ql
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */

@ApiService(value = "供应链体系RPC接口(针对终端扫码工具)",owner = "supplychain",version = "1.0.0")
public interface SupplyChainRemote {
	
	//*********************对外接口**********************/
	
	//供应体系出入库管里员登陆
	@ApiMethod("供应体系出入库管里员登陆")
	@ApiResponse(value = "返回 供应体系出入库管里员登陆结果")
	@ApiParam(name = "userInfo",notes = "请求登陆参数用户数据",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<UserInfo> login(UserInfo userInfo);
	
	//供应链获取设备类型列表
	@ApiMethod("供应体系获取大类型列表")
	@ApiResponse(value = "返回 供应体系获取大类型列表")
	@ApiParam(name = "userInfo",notes = "请求用户数据",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<DeviceType>> listDeviceType(UserInfo userInfo,DeviceType deviceType);
	
	//获取设备类型配置编码列表
	@ApiMethod("获取设备类型配置编码列表")
	@ApiResponse(value = "返回 配置类型编码列表")
	@ApiParam(name = "AttribMana",notes = "请求配置编码参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribMana>> listAttribManaCodes(UserInfo userInfo,AttribMana attribMana);
	
	//获取设备类型配置管理信息
	@ApiMethod("获取设备类型配置编码信息")
	@ApiResponse(value = "返回设备类型配置编码信息")
	@ApiParam(name = "AttribMana",notes = "请求配置编码信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<AttribMana> getAttribManaByManaCode(UserInfo userInfo,AttribMana attribMana);
	
//	//获取配置列表
//	@ApiMethod("获取配置列表")
//	@ApiResponse(value = "返回获取配置列表")
//	@ApiParam(name = "attribInfo",notes = "请求配置参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
//	RpcResponse<List<AttribInfo>> getAttriInfoList(AttribInfo attribInfo);
	
	//获取所属订单列表
	@ApiMethod("获取分派订单列表")
	@ApiResponse(value = "返回获取分派订单列表")
	@ApiParam(name = "orderInfo",notes = "请求订单列表参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<OrderInfo>> listOrderInfo(UserInfo userInfo, OrderInfo record);
	
	//获取订单信息表（出库）
	@ApiMethod("获取订单信息表（出库）")
	@ApiResponse(value = "获取订单信息表（出库）")
	@ApiParam(name = "orderInfo",notes = "请求订单列表参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<OrderInfo>> pageOrderInfo(UserInfo userInfo,RpcPagination<OrderInfo> pagination);
	
	//获取订单详情列表
	@ApiMethod("获取订单详情列表")
	@ApiResponse(value = "获取订单详情列表")
	@ApiParam(name = "orderInfo",notes = "请求订单列表参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<OrderInfoDetail>> pageOrderInfoDetail(UserInfo userInfo,RpcPagination<OrderInfoDetail> pagination);
	
	
	//根据订单号获取订单详情
	@ApiMethod("根据订单号获取订单详情")
	@ApiResponse(value = "返回根据订单号获取订单详情")
	@ApiParam(name = "orderCode",notes = "订单编码",required = true,dataType = ApiParam.DataTypeEnum.STRING)
	RpcResponse<OrderInfo> getOrderInfoByOrderCode(UserInfo userInfo, OrderInfo record);
	
	//获取固件信息列表
	@ApiMethod("获取固件信息列表")
	@ApiResponse(value = "返回获取固件信息列表")
	@ApiParam(name = "firmwareInfo",notes = "请求固件信息参数",required = true,dataType = ApiParam.DataTypeEnum.STRING)
	RpcResponse<List<FirmwareInfo>> getFirmwareInfoList(FirmwareInfo firmwareInfo);
	
	//设备入库
	@ApiMethod("设备入库")
	@ApiResponse(value = "返回设备入库结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DeviceInfo> scannerDeviceInfo(UserInfo userInfo, DeviceInfo record);
	
	@ApiMethod("取消工厂库存入库")
	@ApiResponse(value = "返回设备入库结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> cancelDeviceInfo(UserInfo userInfo, DeviceInfo record); 
	
	@ApiMethod("按照设备编码统计分页")
	@ApiResponse(value = "返回设备入库结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<DeviceInfo>> pageStatAttrib(UserInfo userInfo,RpcPagination<DeviceInfo> pagination);
	
	@ApiMethod("分页查询入库明细")
	@ApiResponse(value = "返回设备入库结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<DeviceInfo>> pageStatAttribDeviceInfos(UserInfo userInfo,RpcPagination<DeviceInfo> pagination);
	
	@ApiMethod("分页查询入库明细导出用")
	@ApiResponse(value = "返回设备入库结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<DeviceInfo>> listExportAttribDeviceInfos(UserInfo userInfo,DeviceInfo record);
	
	@ApiMethod("excel导入入库数据校验")
	@ApiResponse(value = "返回校验结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> excelDeviceInfoInCheck(UserInfo userInfo,List<DeviceInfo> deviceInfoList);
	
	@ApiMethod("excel导入出库数据")
	@ApiResponse(value = "返回校验结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> excelDeviceInfoOutImport(UserInfo userInfo,OrderInfo orderInfo,List<DeviceInfo> deviceInfoList);
	
	@ApiMethod("excel导入出库数据校验")
	@ApiResponse(value = "返回校验结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> excelDeviceInfoOutCheck(UserInfo userInfo,OrderInfo orderInfo,List<DeviceInfo> deviceInfoList);
	
	@ApiMethod("excel导入数据")
	@ApiResponse(value = "返回结果")
	@ApiParam(name = "deviceInfo",notes = "设备入库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<CheckImportDataVo> excelDeviceInfoImport(UserInfo userInfo,List<DeviceInfo> deviceInfoList);
	
	//设备出库
	@ApiMethod("设备出库")
	@ApiResponse(value = "返回设备出库结果")
	@ApiParam(name = "deviceInfo",notes = "设备出库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> deliveryDeviceInfo(UserInfo userInfo, OrderInfo orderInfo, DeviceInfo deviceInfo,Logistics	logistics,Address address);
	
	//设备取消出库
	@ApiMethod("设备取消出库")
	@ApiResponse(value = "返回设备取消出库结果")
	@ApiParam(name = "deviceInfo",notes = "设备出库参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> canceDeliveryDeviceInfo(UserInfo userInfo,DeviceInfo deviceInfo);
	
	//设备调拨
	@ApiMethod("设备调拨")
	@ApiResponse(value = "返回设备调拨结果")
	@ApiParam(name = "deviceInfo",notes = "设备调拨参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> dispatchDeviceInfo(UserInfo userInfo, WareHouseInfo toHouseInfo, DeviceInfo deviceInfo, AttribMana attribMana);
	
	//获取设备硬件编码信息
	@ApiMethod("获取设备硬件编码信息")
	@ApiResponse(value = "获取设备硬件编码信息")
	@ApiParam(name = "deviceInfo",notes = "设备调拨参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<AttribMana> getAttribManaByDeviceSn(UserInfo userInfo,DeviceInfo deviceInfo);
	
	//仓库列表
	@ApiMethod("获取仓库列表")
	@ApiResponse(value = "返回仓库列表")
	@ApiParam(name = "deviceInfo",notes = "获取仓库列表参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<WareHouseInfo>> getWareHouseInfoList(UserInfo userInfo,WareHouseInfo wareHouseInfo);
	
	//根据id获取仓库信息
	@ApiMethod("根据id获取仓库信息")
	@ApiResponse(value = "返回仓库信息")
	@ApiParam(name = "deviceInfo",notes = "返回仓库信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<WareHouseInfo> getWareHouseInfo(UserInfo userInfo, WareHouseInfo wareHouseInfo);
	////////////////////////////////////////////////////
	
	//定时任务同步数据返回日志
	@ApiMethod("定时任务同步数据返回日志")
	@ApiResponse(value = "返回定时任务同步数据返回日志")
	@ApiParam(name = "record",notes = "日志参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> insert(RequestLog record);
	
	//查询日志信息
	@ApiMethod("查询日志信息")
	@ApiResponse(value = "返回查询日志信息结果")
	@ApiParam(name = "record",notes = "请求日志参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RequestLog> getRequestLog(RequestLog record);
	/////////////////////////////////////////////////////
	
	//获取外部系统信息
	@ApiMethod("获取外部系统信息")
	@ApiResponse(value = "返回外部系统")
	@ApiParam(name = "record",notes = "系统标识,数据派发类型",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<ExsysIdentify> getExsysIdentifyBySystemFlag(UserInfo userInfo,ExsysIdentify exsysIdentify);
	
	//保存派发不成功的数据
	@ApiMethod("保存派发不成功的数据")
	@ApiResponse(value = "返回Integer")
	@ApiParam(name = "record",notes = "系统标识,数据派发类型",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> saveExsysDispatchLog(UserInfo userInfo,ExsysDispatchLog exsysDispatchLog);
	
	//获取外部系统信息
	@ApiMethod("根据设备sn发送记录")
	@ApiResponse(value = "返回外部系统")
	@ApiParam(name = "record",notes = "ExsysOrderInfo",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<ExsysDispatchLog> getExsysDispatchLog(UserInfo userInfo,ExsysDispatchLog exsysDispatchLog);
	
	//保存派发不成功的数据
	@ApiMethod("删除派发不成功的数据")
	@ApiResponse(value = "返回Integer")
	@ApiParam(name = "record",notes = "系统标识,sn",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> delExsysDispatchLog(UserInfo userInfo,ExsysDispatchLog exsysDispatchLog);
	
	//获取外部系统信息
	@ApiMethod("处理外部系统修改更变设备状态消息")
	@ApiResponse(value = "返回外部系统")
	@ApiParam(name = "record",notes = "ExsysDeviceStatu",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> handleExsysDeviceStatu(UserInfo userInfo,ExsysDeviceStatu exsysDeviceStatu);
	
	//获取外部系统信息
	@ApiMethod("处理外部系统添加获取修改订单消息")
	@ApiResponse(value = "返回外部系统")
	@ApiParam(name = "record",notes = "ExsysOrderInfo",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> handleExsysOrderInfo(UserInfo userInfo,ExsysOrderInfo exsysOrderInfo);
	
}
