package cn.com.glsx.supplychain.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.glsx.supplychain.model.*;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.enums.AttriInfoEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.util.SupplychainUtils;

/**
 * 订单管理
 * 
 * @author zhoudan
 */
@RestController
@RequestMapping("orderInfo")
public class OrderController {

	@Autowired
	private UserInfoHolder userInfoHolder;

	@Autowired
	private SupplyChainAdminRemote supplyChainAdminRemote;
	
	
	/**
	 * 分页查询订单信息
	 * 
	 * @param orderInfo
	 * @param pagination
	 * @return
	 */
	@RequestMapping("listOrderInfo")
	public ResultEntity<RpcPagination<OrderInfo>> listOrderInfo(OrderInfo orderInfo, RpcPagination<OrderInfo> pagination) {
		RpcResponse<RpcPagination<OrderInfo>> response = supplyChainAdminRemote.listOrderInfo(pagination, orderInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}

		return ResultEntity.result(response);
	}

	/**
	 * 查询硬件设备
	 * 
	 * @param typeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getDeviceCategory")
	public ResultEntity<List> getDeviceCategory(Integer typeId) {
		DeviceCode record = new DeviceCode();
		record.setTypeId(typeId);
		RpcResponse<List> response = supplyChainAdminRemote.listDeviceCode(record);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 查询设备对应套餐
	 * 
	 * @param deviceId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getPackage")
	public ResultEntity<List> getPackage(Integer deviceId) {
		RpcResponse<List> response = supplyChainAdminRemote.listPackageInfoByDeviceCode(deviceId);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 新增订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@RequestMapping("insertOrders")
	public ResultEntity<Integer> insertOrders(OrderInfo orderInfo,HttpServletRequest request) {
		@SuppressWarnings({ "static-access", "deprecation" })
		User user = userInfoHolder.getUser();
		if (null != user) {
			orderInfo.setCreatedBy(user.getRealname());
			orderInfo.setUpdatedBy(user.getRealname());
		}
		if(null == orderInfo.getDeletedFlag()){
			orderInfo.setDeletedFlag("N");
		}
		orderInfo.setDeviceName(orderInfo.getDeviceName().split("/")[1]);
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateOrderInfo(orderInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
		
	}

	/**
	 * 查询订单列表（不分页）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("getOrderInfo")
	public ResultEntity<OrderInfo> getOrderInfo(Integer id) {
		RpcResponse<OrderInfo> response = supplyChainAdminRemote.getOrderInfoById(id);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 修改订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@RequestMapping("updateOrder")
	public ResultEntity<Integer> updateOrder(OrderInfo orderInfo,HttpServletRequest request) {
		@SuppressWarnings({ "static-access", "deprecation" })
		User user = userInfoHolder.getUser();
		if (null != user) {
			orderInfo.setCreatedBy(user.getRealname());
			orderInfo.setUpdatedBy(user.getRealname());
		}
		orderInfo.setUpdatedDate(new Date());
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateOrderInfo(orderInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
		
	}

	/**
	 * 取消订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@RequestMapping("cancelOrder")
	public ResultEntity<Integer> cancelOrder(OrderInfo orderInfo,HttpServletRequest request) {
		RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateOrderInfo(orderInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("取消成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 查询仓库信息
	 * 
	 * @param warehouseId
	 * @return
	 */
	@RequestMapping("getWarehouseInfo")
	public ResultEntity<RpcPagination<WareHouseInfo>> getWarehouse(Integer warehouseId) {
		WareHouseInfo wareHouseInfo = new WareHouseInfo();
		RpcPagination<WareHouseInfo> pagination = new RpcPagination<WareHouseInfo>();
		RpcResponse<RpcPagination<WareHouseInfo>> response = supplyChainAdminRemote.listWarehouseInfo(pagination, wareHouseInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 查询商户号
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping("findSendMerchantNo")
	public ResultEntity<List> findSendMerchantNo(OrderInfo orderInfo) {

		HashMap<String, Object> condition = new HashMap<String, Object>();
		String srchContext = orderInfo.getSendMerchantNo();
		if(SupplychainUtils.isInteger(srchContext)){
			condition.put("merchantId", orderInfo.getSendMerchantNo());
		}else{
			condition.put("merchantName", orderInfo.getSendMerchantName());
		}
		
		RpcResponse<List> response = supplyChainAdminRemote.listMerchantInfo(condition, Constants.targetPageNew, Constants.pageSizeMer);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * 根据商户号查询商户
	 *
	 * @param merchantId
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping("findMerchantInfoByMerchantId")
	public ResultEntity<SupplyChainMerchantInfo> findMerchantInfoByMerchantId(@RequestBody Integer merchantId) {
		RpcResponse<SupplyChainMerchantInfo> response = supplyChainAdminRemote.findMerchantInfoByMerchantId(merchantId);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getSendMerchantNo")
	public ResultEntity<List> getSendMerchantNo(OrderInfo orderInfo) {
		RpcResponse<List> response = supplyChainAdminRemote.listMerchantInfo(Constants.targetPageNew, Constants.pageSizeMer);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
	
	/**
	 * @Title: getModel
	 * @Description: 查询机型数据
	 * @param @return
	 * @return
	 * @throws
	 */
	@RequestMapping("getModel")
	public ResultEntity<List<AttribInfo>> getModel() {
		Integer type = Integer.parseInt(AttriInfoEnum.MODEL.getValue());
		AttribInfo attribInfo = new AttribInfo();
		attribInfo.setType(type);
		RpcResponse<List<AttribInfo>> response = supplyChainAdminRemote.listAttribInfo(attribInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * @Title: getType
	 * @Description: 查询套机/裸机类型
	 * @param @return
	 * @return
	 * @throws
	 */
	@RequestMapping("getType")
	public ResultEntity<List<AttribInfo>> getType() {
		Integer type = Integer.parseInt(AttriInfoEnum.TYPE.getValue());
		AttribInfo attribInfo = new AttribInfo();
		attribInfo.setType(type);
		RpcResponse<List<AttribInfo>> response = supplyChainAdminRemote.listAttribInfo(attribInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * @Title: getConfigure
	 * @Description: 查询配置信息
	 * @param @return
	 * @return
	 * @throws
	 */
	@RequestMapping("getConfigure")
	public ResultEntity<List<AttribInfo>> getConfigure() {
		Integer type = Integer.parseInt(AttriInfoEnum.CONFIGURE.getValue());
		AttribInfo attribInfo = new AttribInfo();
		attribInfo.setType(type);
		RpcResponse<List<AttribInfo>> response = supplyChainAdminRemote.listAttribInfo(attribInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * @Title: getSize
	 * @Description: 查询尺寸
	 * @param @return
	 * @return
	 * @throws
	 */
	@RequestMapping("getSize")
	public ResultEntity<List<AttribInfo>> getSize() {
		Integer type = Integer.parseInt(AttriInfoEnum.SIZE.getValue());
		AttribInfo attribInfo = new AttribInfo();
		attribInfo.setType(type);
		RpcResponse<List<AttribInfo>> response = supplyChainAdminRemote.listAttribInfo(attribInfo);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	/**
	 * @Title: syncDeviceInfo
	 * @Description: 同步
	 * @param @return
	 * @return
	 * @throws
	 */
	@RequestMapping("syncDeviceInfo")
	public ResultEntity<Integer> syncDeviceInfo(String orderCode) {
		RpcResponse<Integer> response = supplyChainAdminRemote.syncDeviceInfo(orderCode);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("数据同步成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

}
