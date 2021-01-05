package cn.com.glsx.supplychain.remote;

import java.util.HashMap;
import java.util.List;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.*;


/**
 * @Title: SupplyChainRemote.java
 * @Description: 供应链体系RPC接口(针对运营业务后台)
 * @author ql
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "供应链体系RPC接口(针对运营业务后台)",owner = "supplychain",version = "2.0.0")
public interface SupplyChainAdminRemote {

	/**
	* 获取仓库列表
	* @param wareHouseInfo wareHouseInfo条件查询参数
	*/
	@ApiMethod(" 获取仓库列表")
	@ApiResponse(value = "返回 获取仓库列表")
	@ApiParam(name = "wareHouseInfo",notes = "仓库请求对象",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<WareHouseInfo>> listWarehouseInfo(RpcPagination<WareHouseInfo> pagination,WareHouseInfo wareHouseInfo);
	
	/**
	* 添加或修改仓库信息
	* @param wareHouseInfo id=NULL 添加仓库  反之修改仓库信息
	* @return Integer：0 成功 1 失败
	*/
	@ApiMethod("添加或修改仓库信息")
	@ApiResponse(value = "返回添加或修改仓库信息结果")
	@ApiParam(name = "wareHouseInfo",notes = "仓库请求对象",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addAndUpdateWareHouseInfo(WareHouseInfo wareHouseInfo);
	
	/**
	* 根据id获取相应仓库信息
	* @param id:仓库id
	* @return WareHouseInfo:仓库信息
	*/
	@ApiMethod("根据id获取相应仓库信息")
	@ApiResponse(value = "返回根据id获取相应仓库信息")
	@ApiParam(name = "id",notes = "仓库id",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<WareHouseInfo> getWareHouseInfoById(Integer id);
	
	/**
	* 获取仓库操作员信息列表
	* @param userInfo userInfo条件查询参数
	* @return
	*/
	@ApiMethod("获取仓库操作员信息列表")
	@ApiResponse(value = "返回获取仓库操作员信息列表")
	@ApiParam(name = "userInfo",notes = "用户信息请求参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<UserInfo>> listUserInfo(RpcPagination<UserInfo> pagination,UserInfo userInfo);
	
	/**
	* 添加或修改操作员信息
	* @param userInfo id=NULL 添加信息  反之修改信息
	* @return Integer：0 成功 1 失败
	*/
	@ApiMethod("添加或修改操作员信息")
	@ApiResponse(value = "返回添加或修改操作员信息结果")
	@ApiParam(name = "userInfo",notes = "用户信息请求参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addAndUpdateUserInfo(UserInfo userInfo);
	
	/**
	* 根据id获取相应仓库操作员信息
	* @param id:仓库操作员id
	* @return UserInfo:仓库操作员信息
	*/
	@ApiMethod("根据id获取相应仓库操作员信息")
	@ApiResponse(value = "返回用户信息")
	@ApiParam(name = "id",notes = "用户id",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<UserInfo> getUserInfoById(Integer id);
	
	/**
	* 获取固件版本信息列表
	* @param firmwareInfo FirmwareInfo条件查询参数
	* @return
	*/
	@ApiMethod("获取固件版本信息列表")
	@ApiResponse(value = "返回获取固件版本信息列表")
	@ApiParam(name = "firmwareInfo",notes = "固件列表请求参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<FirmwareInfo>> listFirmwareInfo(RpcPagination<FirmwareInfo> pagination,FirmwareInfo firmwareInfo);
	
	/**
	* 添加或修改固件版本信息
	* @param firmwareInfo id=NULL 添加信息  反之修改信息
	* @return Integer：0 成功 1 失败
	*/
	@ApiMethod("添加或修改固件版本信息")
	@ApiResponse(value = "返回添加或修改固件版本信息")
	@ApiParam(name = "firmwareInfo",notes = "固件列表请求参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addAndUpdateFirmwareInfo(FirmwareInfo firmwareInfo);
	
	/**
	* 根据id获取相应固件版本信息
	* @param id:固件版本信息ID
	* @return FirmwareInfo:固件版本信息
	*/
	@ApiMethod("根据id获取相应固件版本信息")
	@ApiResponse(value = "返回根据id获取相应固件版本信息")
	@ApiParam(name = "id",notes = "固件id",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<FirmwareInfo> getFirmwareInfoById(Integer id);
	
	/**
	* 获取硬件设备列表
	* @param deviceInfo DeviceInfo条件查询参数
	*/
	@ApiMethod("获取硬件设备列表")
	@ApiResponse(value = "返回获取硬件设备列表")
	@ApiParam(name = "deviceInfo",notes = "请求设备信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<DeviceInfo>> listDeviceInfo(RpcPagination<DeviceInfo> pagination,DeviceInfo deviceInfo);
	
	/**
	* 根据订单号获取硬件设备列表（快照）
	* @param orderCode DeviceInfo条件查询参数
	*/
	@ApiMethod("根据订单号获取硬件设备列表")
	@ApiResponse(value = "返回根据订单号获取硬件设备列表")
	@ApiParam(name = "orderCode",notes = "订单编码",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<DeviceInfo>> ListDeviceInfoByOrderCode(RpcPagination<DeviceInfo> pagination,String orderCode);
	
	/**
	* 获取订单详情列表
	* @param orderCode DeviceInfo条件查询参数
	*/
	@ApiMethod("获取订单详情列表")
	@ApiResponse(value = "返回订单详情列表")
	@ApiParam(name = "orderCode",notes = "订单编码",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<OrderInfoDetail>> pageOrderInfoDetail(RpcPagination<OrderInfoDetail> pagination,OrderInfoDetail record);
	
	/**
	* 导出硬件设备列表
	* @param deviceInfo 查询条件
	*/
	@ApiMethod("导出硬件设备列表")
	@ApiResponse(value = "返回导出硬件设备列表")
	@ApiParam(name = "deviceInfo",notes = "请求设备信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<DeviceInfo>> ExportDeviceInfo(DeviceInfo deviceInfo);

	/**
	 * 导出订单详情列表
	 * @param orderInfoDetail 查询条件
	 */
	@ApiMethod("导出订单详情列表")
	@ApiResponse(value = "返回导出订单详情列表")
	@ApiParam(name = "deviceInfo",notes = "请求设备信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<OrderInfoDetail>> ExportOrderInfoDetail(OrderInfoDetail orderInfoDetail);
	
	/**
	* 添加或修改硬件设备信息
	* @param deviceInfo id=NULL 添加信息  反之修改信息
	* @return Integer：0 成功 1 失败
	*/
	@ApiMethod("添加或修改硬件设备信息")
	@ApiResponse(value = "返回添加或修改硬件设备信息")
	@ApiParam(name = "deviceInfo",notes = "请求设备信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addAndUpdateDeviceInfo(DeviceInfo deviceInfo);
	
	/**
	* 根据id获取相应硬件设备信息
	* @param id:硬件设备信息ID
	* @return DeviceInfo:硬件设备信息
	*/
	@ApiMethod("根据id获取相应硬件设备信息")
	@ApiResponse(value = "返回相应硬件设备信息")
	@ApiParam(name = "id",notes = "设备信息id",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DeviceInfo> getDeviceInfoById(Integer id);

	/**
	 * 根据iccid获取相应硬件设备信息
	 * @param iccid:硬件设备信息ICCID
	 * @return DeviceInfo:硬件设备信息
	 */
	@ApiMethod("根据id获取相应硬件设备信息")
	@ApiResponse(value = "返回相应硬件设备信息")
	@ApiParam(name = "iccid",notes = "设备信息iccid",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DeviceInfo> getDeviceInfoByIccid(String iccid);

	/**
	 * 根据sn获取相应硬件设备信息
	 * @param sn:硬件设备信息
	 * @return DeviceInfo:硬件设备信息
	 */
	@ApiMethod("根据id获取相应硬件设备信息")
	@ApiResponse(value = "返回相应硬件设备信息")
	@ApiParam(name = "sn",notes = "设备信息",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DeviceInfo> getDeviceInfoBySn(String sn);

	/**
	 * 根据imei获取相应硬件设备信息
	 * @param imei:硬件设备信息
	 * @return DeviceInfo:硬件设备信息
	 */
	@ApiMethod("根据id获取相应硬件设备信息")
	@ApiResponse(value = "返回相应硬件设备信息")
	@ApiParam(name = "sn",notes = "设备信息",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DeviceInfo> getDeviceInfoByImei(String imei);
	
	/**
	* 获取订单信息列表
	* @param orderInfo OrderInfo条件查询参数
	*/
	@ApiMethod("获取订单信息列表")
	@ApiResponse(value = "返回获取订单信息列表")
	@ApiParam(name = "orderInfo",notes = "请求订单信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<OrderInfo>> listOrderInfo(RpcPagination<OrderInfo> pagination,OrderInfo orderInfo);
	
	/**
	* 添加或修改订单信息
	* @param orderInfo id=NULL 添加信息  反之修改信息
	* @return Integer：0 成功 1 失败
	*/
	@ApiMethod("添加或修改订单信息")
	@ApiResponse(value = "返回添加或修改订单信息")
	@ApiParam(name = "orderInfo",notes = "请求订单信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addAndUpdateOrderInfo(OrderInfo orderInfo);
	
	/**
	* 根据id获取相应订单信息
	* @param id:硬件设备信息ID
	* @return DeviceInfo:硬件设备信息
	*/
	@ApiMethod("根据id获取相应订单信息")
	@ApiResponse(value = "返回根据id获取相应订单信息")
	@ApiParam(name = "id",notes = "订单id",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<OrderInfo> getOrderInfoById(Integer id);
	
	/**
	* 获取设备类型列表(小类型)
	* @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
	*/
	@SuppressWarnings("rawtypes")
	@ApiMethod(" 获取设备类型列表")
	@ApiResponse(value = "返回 获取设备类型列表")
	@ApiParam(name = "record",notes = "查询条件",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<List> listDeviceCode(DeviceCode record);
	
	/**
	* 根据设备类型id获取套餐列表
	* @param id 设备类型id
	*/
	@SuppressWarnings("rawtypes")
	@ApiMethod("根据设备类型id获取套餐列表")
	@ApiResponse(value = "返回套餐列表")
	@ApiParam(name = "id",notes = "设备类型id",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<List> listPackageInfoByDeviceCode(Integer code);

	/**
	 * 根据商户号获取商户
	 * @param merchantId:商户号
	 */
	@SuppressWarnings("rawtypes")
	@ApiMethod("获取商户")
	@ApiResponse(value = "返回获取商户列表")
	@ApiParam(name = "merchantId",notes = "商户号",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<SupplyChainMerchantInfo> findMerchantInfoByMerchantId(Integer merchantId);
	
	/**
	* 获取商户列表
	* @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
	*/
	@SuppressWarnings("rawtypes")
	@ApiMethod("获取商户列表")
	@ApiResponse(value = "返回获取商户列表")
	@ApiParam(name = "targetPage",notes = "分页单数",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<List> listMerchantInfo(Integer targetPage, Integer pageSize);
	
	/**
	* 获取商户列表
	* @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
	*/
	@SuppressWarnings("rawtypes")
	@ApiMethod("获取商户列表")
	@ApiResponse(value = "返回获取商户列表")
	@ApiParam(name = "targetPage",notes = "分页单数",required = true,dataType = ApiParam.DataTypeEnum.INTEGER)
	RpcResponse<List> listMerchantInfo(HashMap<String, Object> condition,Integer targetPage, Integer pageSize);
	
	/**
	* 获取设备属性配置信息列表
	* @param attribInfo 条件参数
	*/
	@ApiMethod("获取设备属性配置信息列表")
	@ApiResponse(value = "返回获取商户列表")
	@ApiParam(name = "attribInfo",notes = "请求设备属性配置参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribInfo>> listAttribInfo(AttribInfo attribInfo);
	
	/**
	* 添加或修改设备配置信息
	* @param attribMana id=NULL 添加信息  反之修改信息
	* @return Integer：0 成功 1 失败
	*/
	@ApiMethod("添加或修改设备配置信息")
	@ApiResponse(value = "返回添加或修改设备配置信息")
	@ApiParam(name = "attribMana",notes = "请求设备配置信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> addAndUpdateAttribMana(AttribMana attribMana);
	
	/**
	* 获取设备配置信息列表
	* @param attribMana AttribMana条件查询参数
	*/
	@ApiMethod("获取设备配置信息列表")
	@ApiResponse(value = "返回 获取设备配置信息列表")
	@ApiParam(name = "attribMana",notes = "请求设备配置信息参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<AttribMana>> listAttribMana(RpcPagination<AttribMana> pagination,AttribMana attribMana);
	
	
	/**
	 * 
	 * @Title: getAttribMana 
	 * @Description:通过设备配置编码查询设备配置信息
	 * @param @param attribMana
	 * @return RpcResponse<AttribMana>
	 */
	@ApiMethod("通过设备配置编码查询设备配置信息")
	@ApiResponse(value = "返回备配置信息")
	@ApiParam(name = "manaCode",notes = "备配置信息编码",required = true,dataType = ApiParam.DataTypeEnum.STRING)
	RpcResponse<AttribMana> getAttribManaInfo(String manaCode);
	
	
	/**
	 * 
	 * @Title: syncDeviceInfo 
	 * @Description: 手动同步设备
	 * @param @param orderCode
	 * @return RpcResponse<Integer>
	 */
	@ApiMethod("手动同步设备接口")
	@ApiResponse(value = "返回同步设备结果")
	@ApiParam(name = "orderCode",notes = "订单编码",required = true,dataType = ApiParam.DataTypeEnum.STRING)
	RpcResponse<Integer> syncDeviceInfo(String orderCode);

	/**
	 * 
	 * @Title: saveDeviceCategory 
	 * @Description: 保存小类信息到老设备平台
	 * @param @param deviceCode
	 * @return RpcResponse<Integer>
	 */
	@ApiMethod("保存小类信息到老设备平台")
	@ApiResponse(value = "返回保存结果")
	@ApiParam(name = "deviceCode",notes = "保存设备类型(小类)参数",required = true,dataType = ApiParam.DataTypeEnum.STRING)
	RpcResponse<Integer> saveDeviceCategory(DeviceCode deviceCode);


	@ApiMethod("根据SN查询订单详情")
	@ApiResponse(value = "返回订单详情")
	@ApiParam(name = "imei",notes = "设备SN",required = true,dataType = ApiParam.DataTypeEnum.STRING)
	RpcResponse<OrderInfoDetail> getOrderInfoDetailByImei(String imei);

	//获取设备类型配置编码列表
	@ApiMethod("获取设备类型配置编码列表")
	@ApiResponse(value = "返回 配置类型编码列表")
	@ApiParam(name = "AttribMana",notes = "请求配置编码参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<AttribMana>> listAttribManaCodes(AttribMana attribMana);


	//获取设备配置信息列表
	@ApiMethod("返回设备配置信息列表")
	@ApiResponse(value = "返回设备配置信息列表")
	@ApiParam(name = "AttribMana",notes = "请求配置编码参数",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<AttribMana> getAttribManaByManaCode(String manaCode);
}
