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
 * @Title: JxcCommonRemote.java
 * @Description: 进销存重构地址 类型等公共服务接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "进销存重构地址 类型等公共服务接口", owner = "supplychain", version = "1.0.0")
public interface JxcCommonRemote {

	@ApiMethod("获取商户地址列表")
	@ApiResponse(value = "获取商户地址列表")
	@ApiParam(name = "JXCMTBsAddressDTO", notes = "获取商户地址列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsAddressDTO>> listAddress(JXCMTBsAddressDTO record);

	@ApiMethod("获取服务商地址列表")
	@ApiResponse(value = "获取服务商地址列表")
	@ApiParam(name = "JXCMTBsAddressDTO", notes = "获取服务商地址列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsAddressDTO>> listServiceProviderAddress(JXCMTBsAddressDTO record);

	
	@ApiMethod("根据id获取地址详情")
	@ApiResponse(value = "根据id获取地址详情")
	@ApiParam(name = "JXCMTBsAddressDTO", notes = "根据id获取地址详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTBsAddressDTO> getAddress(JXCMTBsAddressDTO record);
	
	@ApiMethod("根据id修改地址信息")
	@ApiResponse(value = "根据id修改地址信息")
	@ApiParam(name = "Integer", notes = "根据id修改地址信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> updateAddress(JXCMTBsAddressDTO record);
	
	@ApiMethod("根据id删除地址信息")
	@ApiResponse(value = "根据id删除地址信息")
	@ApiParam(name = "Integer", notes = "根据id删除地址信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> delAddress(JXCMTBsAddressDTO record);
	
	@ApiMethod("批量根据id删除地址信息")
	@ApiResponse(value = "批量根据id删除地址信息")
	@ApiParam(name = "Integer", notes = "批量根据id删除地址信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> batchDelAddress(JXCMTBsAddressDelDTO record);
	
	@ApiMethod("添加商户地址")
	@ApiResponse(value = "添加商户地址")
	@ApiParam(name = "Integer", notes = "添加商户地址", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> addAddress(JXCMTBsAddressDTO record);
	
	@ApiMethod("获取产品分类列表")
	@ApiResponse(value = "获取产品分类列表")
	@ApiParam(name = "Integer", notes = "获取产品分类列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTProductTypeDTO>> listProductType(JXCMTProductTypeDTO record);
	
	@ApiMethod("获取商户渠道")
	@ApiResponse(value = "获取商户渠道")
	@ApiParam(name = "Integer", notes = "获取商户渠道", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTMerchantChannelDTO>> listMerchantChannel(JXCMTMerchantChannelDTO record);
	
	@ApiMethod("获取商户列表")
	@ApiResponse(value = "获取商户列表")
	@ApiParam(name = "Integer", notes = "获取商户列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCDealerUserInfoDTO>> pageServerMerchant(RpcPagination<JXCDealerUserInfoDTO> pagination);
	
	@ApiMethod("获取项目分类列表")
	@ApiResponse(value = "获取分类分类列表")
	@ApiParam(name = "Integer", notes = "获取产品分类列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsSubjectDTO>> listSubject(JXCMTBsSubjectDTO record);
	
	@ApiMethod("获取设备大类列表")
	@ApiResponse(value = "获取设备大类列表")
	@ApiParam(name = "Integer", notes = "获取设备大类列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTDeviceTypeDTO>> listDeviceType(JXCMTDeviceTypeDTO record);
	
	@ApiMethod("获取支持分发业务系统的设备大类列表")
	@ApiResponse(value = "获取支持分发业务系统的设备大类列表")
	@ApiParam(name = "Integer", notes = "获取设备大类列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTDeviceTypeDTO>> listSupportDispatchDeviceType(JXCMTDeviceTypeDTO record);
	
	@ApiMethod("获取设备小类列表")
	@ApiResponse(value = "获取设备小类列表")
	@ApiParam(name = "Integer", notes = "获取设备小类列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTDeviceCodeDTO>> listDeviceCode(JXCMTDeviceCodeDTO record);
	
	@ApiMethod("获取设备小类配置的激活套餐")
	@ApiResponse(value = "获取设备小类配置的激活套餐")
	@ApiParam(name = "Integer", notes = "获取设备小类配置的激活套餐", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTPackageDTO>> listDevicePackage(JXCMTPackageDTO record);
	
	@ApiMethod("根据设备大类获取硬件配置列表")
	@ApiResponse(value = "根据设备大类获取硬件配置列表")
	@ApiParam(name = "Integer", notes = "根据设备大类获取硬件配置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTAttribManaDTO>> listAttribMana(JXCMTAttribManaDTO record);
	
	@ApiMethod("根据硬件配置列表")
	@ApiResponse(value = "根据硬件配置列表")
	@ApiParam(name = "Integer", notes = "根据硬件配置列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcPagination<JXCMTAttribManaDTO>> pageAttribMana(JXCMTAttribManaDTO record); 
	
	@ApiMethod("硬件配置保存")
	@ApiResponse(value = "硬件配置保存")
	@ApiParam(name = "Integer", notes = "硬件配置保存", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> saveAttribMana(JXCMTAttribManaDTO record);
	
	@ApiMethod("硬件配置修改")
	@ApiResponse(value = "硬件配置修改")
	@ApiParam(name = "Integer", notes = "硬件配置修改", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> updateAttribMana(JXCMTAttribManaDTO record);
	
	@ApiMethod("根据仓库名称获取仓库列表")
	@ApiResponse(value = "根据仓库名称获取仓库列表")
	@ApiParam(name = "Integer", notes = "根据仓库名称获取仓库列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTWarehouseInfoDTO>> listWarehouseInfo(JXCMTWarehouseInfoDTO record);
	
	@ApiMethod("根据硬件配置编码获取配置")
	@ApiResponse(value = "根据硬件配置编码获取配置")
	@ApiParam(name = "Integer", notes = "根据硬件配置编码获取配置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<JXCMTAttribManaDTO> getAttribManaByAttribCode(JXCMTAttribManaDTO record);
	
	@ApiMethod("根据硬件大类获取硬件型号配置")
	@ApiResponse(value = "根据硬件大类获取硬件型号配置")
	@ApiParam(name = "Integer", notes = "根据硬件大类获取硬件型号配置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTAttribInfoDTO>> listAttribInfoByDeviceType(JXCMTDeviceTypeDTO record);
	
	@ApiMethod("根据type获取配置信息")
	@ApiResponse(value = "根据type获取配置信息")
	@ApiParam(name = "Integer", notes = "根据type获取配置信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTAttribInfoDTO>> listAttrinInfoByAttribType(JXCMTAttribInfoDTO record);
	
	@ApiMethod("根据硬件大类添加硬件型号配置")
	@ApiResponse(value = "根据硬件大类添加硬件型号配置")
	@ApiParam(name = "Integer", notes = "根据硬件大类添加硬件型号配置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<Integer> saveAttribInfoByDeviceType(JXCMTDeviceTypeAttribInfoDTO record);
	
	@ApiMethod("搜索物料列表")
	@ApiResponse(value = "搜索物料列表")
	@ApiParam(name = "Integer", notes = "根据硬件大类添加硬件型号配置", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCMTBsMaterialInfoDTO>> pageMaterialInfo(JXCMTBsMaterialInfoDTO record);
	
	@ApiMethod("搜索K3仓库商户对应关系表")
	@ApiResponse(value = "搜索K3仓库商户对应关系表")
	@ApiParam(name = "Integer", notes = "搜索K3仓库商户对应关系表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<RpcPagination<JXCAmKcWarehouseRelationDTO>> pageAmKcWarehouseRelation(RpcPagination<JXCAmKcWarehouseRelationDTO> pagination);
}
