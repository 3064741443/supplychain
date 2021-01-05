package cn.com.glsx.supplychain.jst.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.model.StatementSell;

import java.util.List;


/**
 * @author ql
 * @version V1.0
 * @Title: WxBsMerchantRemote.java
 * @Description: 针对微信端代理商rpc接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "针对微信端代理商rpc接口", owner = "supplychain", version = "1.0.0")
public interface WxBsMerchantRemote {
	
	@ApiMethod("根据商户号判断是门店还是代理商")
    @ApiResponse(value = "返回门店信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<UserInfoDTO> getRoleByMerchantCode(UserInfoDTO record);
	
	@ApiMethod("根据商户号获取经销存系统商户用户信息")
    @ApiResponse(value = "返回商户用户信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsDealerUserInfoDTO> getDealerUserInfoByMerchantCode(BsDealerUserInfoDTO record);

	@ApiMethod("根据商户号获取门店信息")
    @ApiResponse(value = "返回门店信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<JstShopDTO> getJspShopByMerchantCode(JstShopDTO record);
	
	@ApiMethod("根据门店编号获取门店信息")
    @ApiResponse(value = "返回门店信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<JstShopDTO> getJspShopByShopCode(JstShopDTO record);
	
	@ApiMethod("获取门店和供应商的关系")
    @ApiResponse(value = "返回关系")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<JstShopAgentRelationDTO> getJspShopAgentRelation(JstShopAgentRelationDTO record);
	
	@ApiMethod("获取商户地址列表")
    @ApiResponse(value = "返回商户地址列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<BsAddressDTO>> listBsAddressByMerchantCode(BsAddressDTO record);
	
	@ApiMethod("保存添加商户地址")
    @ApiResponse(value = "返回商户地址")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsAddressDTO> saveBsAddress(BsAddressDTO record);
	
	@ApiMethod("保存添加商户地址")
    @ApiResponse(value = "返回商户地址")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsAddressDTO> getBsAddressById(BsAddressDTO record);
	
	@ApiMethod("修改商户地址")
    @ApiResponse(value = "修改商户地址")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<BsAddressDTO> updateBsAddressById(BsAddressDTO record);
	
	@ApiMethod("保存添加商户地址")
    @ApiResponse(value = "返回商户地址")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> removeBsAddress(BsAddressDTO record);
	
	@ApiMethod("获取门店供应商列表")
    @ApiResponse(value = "返回商户地址")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JstShopAgentRelationDTO>> listShopAgentMerchant(JstShopDTO record);
	
	@ApiMethod("获取供应商下搜索门店列表")
    @ApiResponse(value = "返回门店信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisShopDTO> pageJstShopByAgentMerchant(DisShopDTO record); 
	
	@ApiMethod("获取供应商下门店列表")
    @ApiResponse(value = "返回门店信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JstShopDTO>> listJstShopByAgentMerchant(BsDealerUserInfoDTO record);
	
	
	@ApiMethod("获取供应商用户表")
    @ApiResponse(value = "返回商户地址")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisDealerUserInfoDTO> listDealerUserInfo(DisDealerUserInfoDTO record);
	
	@ApiMethod("获取产品列表")
    @ApiResponse(value = "返回产品列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisProductDTO> listProduct(DisProductDTO record);
	
	@ApiMethod("获取产品列表基本信息下拉列表用")
    @ApiResponse(value = "返回产品列表下拉列表用")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisProductDTO> listProductBase(DisProductDTO record);
	
	@ApiMethod("获取产品下硬件物料列表")
    @ApiResponse(value = "返回产品物料信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<ProductDTO> listProductMaterial(ProductDTO record);
	
	@ApiMethod("根据产品编号获取产品的基本信息")
    @ApiResponse(value = "返回产品基本信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<ProductDTO> getProductBaseInfoByProductCode(ProductDTO record);
	
	@ApiMethod("添加门店购物车")
    @ApiResponse(value = "添加门店购物车")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<SubJstShopShoppingCartDTO> submitJspShopShoppingCart(SubJstShopShoppingCartDTO record);
	
	@ApiMethod("添加商户购物车")
    @ApiResponse(value = "添加商户购物车")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<SubBsMerchantShoppingCartDTO> submitBsMerchantShoppingCart(SubBsMerchantShoppingCartDTO record);
	
	@ApiMethod("获取门店购物车商品数量")
    @ApiResponse(value = "返回购物车商品数量")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> countShopShoppingCart(JstShopDTO record);
	
	@ApiMethod("获取商户购物车商品数量")
    @ApiResponse(value = "返回商户购物车商品数量")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> countMerchantShoppingCart(BsDealerUserInfoDTO record);
	
	@ApiMethod("获取门店购物车列表")
    @ApiResponse(value = "返回购物车商品数量")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisJstShopShoppingCartDTO> pageShopShoppingCart(DisJstShopShoppingCartDTO record);
	
	@ApiMethod("获取商户购物车列表")
    @ApiResponse(value = "返回商户购物车商品数量")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisMerchantShoppingCartDTO> pageMerchantShoppingCart(DisMerchantShoppingCartDTO record);
	
	@ApiMethod("变更门店购物车信息")
    @ApiResponse(value = "返回购物车商品数量")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> updateShopShoppingCart(DisJstShopShoppingCartDTO record);
	
	@ApiMethod("变更商户购物车信息")
    @ApiResponse(value = "返回商户购物车商品数量")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> updateMerchantShoppingCart(DisMerchantShoppingCartDTO record);
	
	@ApiMethod("门店提交订单")
    @ApiResponse(value = "返回成功")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> submitShopOrder(SubShopOrderDTO record);
	
	@ApiMethod("商户提交订单")
    @ApiResponse(value = "返回成功")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> submitMerchantOrder(SubMerchantOrderDTO record);
	
	@ApiMethod("商户显示订单列表")
    @ApiResponse(value = "订单列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisOrderDTO> pageMerchantOrder(DisOrderDTO record);
	
	@ApiMethod("商户门店订单列表")
    @ApiResponse(value = "订单列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisOrderDTO> pageShopOrder(DisOrderDTO record);
	
	@ApiMethod("商户门店订单详情")
    @ApiResponse(value = "订单列表")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<OrderDTO> getShopOrderDetail(OrderDTO record);
	
	@ApiMethod("商户门店订单明细列表")
    @ApiResponse(value = "订单明细")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<DisShopOrderDetailDTO> listShopOrderDetail(DisShopOrderDetailDTO record);
	
	@ApiMethod("提交门店订单明细")
    @ApiResponse(value = "订单明细")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> submitShopOrderDetail(SubOrderDetailDTO record);
	
	@ApiMethod("提交门店订单明细-无订单发货")
    @ApiResponse(value = "订单明细-无订单发货")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> submitShopOrderDetailNoOrder(SubOrderDetailDTO record);

    @ApiMethod("不扫码-提交门店订单明细-无订单发货")
    @ApiResponse(value = "不扫码-订单明细-无订单发货")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> noScanCodeWxSubShopOrderDetailNoOrder(SubOrderDetailDTO record);

    @ApiMethod("获取物料信息-无订单发货")
    @ApiResponse(value = "获取物料信息-无订单发货")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<StatementSell>> listMaterialNoOrder(String merchantCode);

	@ApiMethod("提交门店订单明细")
    @ApiResponse(value = "订单明细")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> checkShopOrderDetail(SubOrderDetailDTO record);
	
	@ApiMethod("无订单发货-验证sn")
    @ApiResponse(value = "订单明细")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<Integer> checkShopOrderDetailNoOrder(SubOrderDetailDTO record);
		
	@ApiMethod("无订单发货-查询发货单明细")
    @ApiResponse(value = "订单明细")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<List<JstShopShoppingCartDTO>> getShopOrderDetailNoOrder(SubOrderDetailDTO record);
	
	
	@ApiMethod("根据openid获取用户登陆信息")
    @ApiResponse(value = "用户登陆信息")
    @ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<JstUserOpenIdDTO> getJstUserOpenId(JstUserOpenIdDTO record);
	
	@ApiMethod("保存用户登陆信息")
    @ApiResponse(value = "用户登陆信息")
	@ApiParam(name = "pagination", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<JstUserOpenIdDTO> saveJstUserOpenId(JstUserOpenIdDTO record);
}
