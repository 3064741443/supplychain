package cn.com.glsx.supplychain.service;


import java.util.Date;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.OrderStatusEnum;
import cn.com.glsx.supplychain.exception.ServiceException;
import cn.com.glsx.supplychain.mapper.DeviceInfoMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoMapper;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.OrderInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.service.bs.MerchantOrderService;
import cn.com.glsx.supplychain.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Title: DemoService.java
 * @Description:订单信息服务类
 * @author leiyj
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Service
@Transactional
public class OrderInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private OrderInfoDetailMapper orderInfoDetailMapper;
	
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	@Autowired
	private MerchantOrderService merchantOrderService;
	
	/**
	 * 
	 * @Title: getOrderInfoById 
	 * @Description: 通过ID查询订单信息
	 * @param @param id
	 * @param @return
	 * @param @throws ServiceException 
	 * @return OrderInfo
	 * @throws
	 */
	public OrderInfo getOrderInfoById(Integer id) throws RpcServiceException{
		try {
			logger.info("通过订单ID查询订单信息：" + id);
			OrderInfo orderInfo = orderInfoMapper.getOrderInfoById(id);
			logger.info("返回查询结果集：" + JSONSerializer.toJSON(orderInfo).toString());
			return orderInfo;
		} catch (Exception e) {
			logger.info("通过订单ID查询订单数据异常：" + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 查询订单
	 * @Title: load 
	 * @Description: TODO
	 * @param @param requestEntity
	 * @param @return 
	 * @return ResponseEntity<OrderInfo>
	 * @throws
	 */
	public Page<OrderInfo> listOrderInfo(RpcPagination<OrderInfo> pagination,OrderInfo orderInfo) throws RpcServiceException{

		//判断条件是否为空
		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");		
		logger.info("查询订单列表传入参数："  + JSONSerializer.toJSON(pagination).toString());
		
		try {
			//设置分页数据
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			//返回结果集
			return orderInfoMapper.listOrderInfo(orderInfo);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 分页查询
	 * @Title: load 
	 * @Description: TODO
	 * @param @param requestEntity
	 * @param @return 
	 * @return ResponseEntity<OrderInfo>
	 * @throws
	 */
	public Page<OrderInfo> pageOrderInfoFroDelivery(RpcPagination<OrderInfo> pagination) throws RpcServiceException
	{
		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");	
		logger.info("查询订单列表传入参数："  + JSONSerializer.toJSON(pagination.getCondition()).toString());
		try
		{
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			return orderInfoMapper.pageOrderInfoFroDelivery(pagination.getCondition());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 分页查询订单详情
	 * @Title: load 
	 * @Description: TODO
	 * @param @param requestEntity
	 * @param @return 
	 * @return ResponseEntity<OrderInfo>
	 * @throws
	 */
	public Page<OrderInfoDetail> pageOrderInfoDetail(RpcPagination<OrderInfoDetail> pagination) throws RpcServiceException
	{
		RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");	
		logger.info("分页查询订单详情："  + JSONSerializer.toJSON(pagination.getCondition()).toString());
		try
		{
			PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			return orderInfoDetailMapper.pageOrderInfoDetail(pagination.getCondition());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
		
	}
	
	/**
	 * 分页查询订单详情(出库用)
	 * @Title: load 
	 * @Description: TODO
	 * @param @param requestEntity
	 * @param @return 
	 * @return ResponseEntity<OrderInfo>
	 * @throws
	 */
	 public Page<OrderInfoDetail> pageOrderDetailForDelivery(RpcPagination<OrderInfoDetail> pagination) throws RpcServiceException
	 {
		 RpcAssert.assertNotNull(pagination, DefaultErrorEnum.DATA_NULL, "pagination must not be null");	
		 logger.info("分页查询订单详情(出库用)："  + JSONSerializer.toJSON(pagination.getCondition()).toString());
		 try
		 {
			 PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
			 return orderInfoDetailMapper.pageOrderDetailForDelivery(pagination.getCondition());
		 }
		 catch (Exception e)
		 {
			 logger.debug(e.getMessage(),e);
			 throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED,e.getMessage());
		 }
	 }
	
	
	/**
	 * 插入订单信息
	 * @Title: insertSelective
	 * @Description: TODO
	 * @param @param orderInfo
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int insert(OrderInfo orderInfo) throws RpcServiceException{
		logger.info("新增/更新订单信息实体: " + JSONSerializer.toJSON(orderInfo).toString());
		if(StringUtils.isEmpty(orderInfo.getId())){
			String orderCode = StringUtil.getOrderNo(); // 生成订单号
			orderInfo.setOrderCode(orderCode);
			logger.info("生成订单号：" + orderCode);
			logger.info("order insert....");
			orderInfo.setCreatedDate(new Date());
			orderInfo.setUpdatedDate(new Date());
			orderInfo.setStatus(OrderStatusEnum.STATUS_UF.getValue());
			return orderInfoMapper.insert(orderInfo);
		}else{
			logger.info("order update....");
			return update(orderInfo);
		}
	}
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新订单数据
	 * @param @param orderInfo
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int update(OrderInfo orderInfo) throws RpcServiceException{
		logger.info("更新订单数据入口：" + JSONSerializer.toJSON(orderInfo).toString());
		try {
			orderInfo.setUpdatedDate(new Date());
			return orderInfoMapper.update(orderInfo);
		} catch (Exception e) {
			logger.error("更新订单数据异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 取消订单接口
	 * @Title: cancelOrder 
	 * @Description: TODO
	 * @param @param orderInfo
	 * @param @return
	 * @param @throws ServiceException 
	 * @return int
	 * @throws
	 */
	public int cancelOrder(Integer id) throws RpcServiceException{
		
		logger.info("取消订单: " + id);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setUpdatedDate(new Date());
		orderInfo.setStatus(OrderStatusEnum.STATUS_CL.getValue());
		
		// 更新订单
		int result = 0;
		try {
			// 更新订单
			result = orderInfoMapper.update(orderInfo);
			// 根据返回结果判断
			if (result > 0) {
				//通过ID查询订单对象
				orderInfo = orderInfoMapper.getOrderInfoById(id);
				logger.info("订单编号为：" + orderInfo.getOrderCode());
				DeviceInfo record = new DeviceInfo();
				record.setOrderCode(orderInfo.getOrderCode());
				record.setStatus(OrderStatusEnum.STATUS_CL.getValue());
				record.setUpdatedDate(new Date());
				deviceInfoMapper.update(record);
				logger.info("清空设备表订单编码成功");
			} else {
				logger.info("订单【"+ orderInfo.getOrderCode() +"】取消订单失败");
				throw new ServiceException(ServiceException.ErrorCode.SERVICE_ERROR,"订单取消失败");
			}
		} catch (Exception e) {
			logger.error("取消订单异常," + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: getOrderInfoByOrderCode 
	 * @Description: 通过订单编号查询订单信息
	 * @param @param orderCode
	 * @param @return
	 * @param @throws ServiceException 
	 * @return OrderInfo
	 * @throws
	 */
	public OrderInfo getOrderInfoByOrderCode(String orderCode) throws RpcServiceException{
		try {
			logger.info("通过订单编号查询订单信息：" + orderCode);
			OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderCode(orderCode);
			logger.info("返回查询结果集：" + JSONSerializer.toJSON(orderInfo).toString());
			return orderInfo;
		} catch (Exception e) {
			logger.info("通过订单ID查询订单数据异常：" + e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 查询无分页订单
	 * @Title: getOrderList 
	 * @Description: TODO
	 * @param @param orderInfo
	 * @param @return 
	 * @return List<OrderInfo>
	 * @throws
	 */
	public List<OrderInfo> getOrderList(OrderInfo orderInfo) throws RpcServiceException{
		try {
			//返回结果集
			return orderInfoMapper.getOrderList(orderInfo);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	/**
	 * 获取订单设备数量
	 * @Title: getDeviceCountByOrderCode
	 * @Description: TODO
	 * @param @param orderInfo
	 * @param @return 
	 * @return List<OrderInfo>
	 * @throws
	 */
	public Integer getDeviceCountByOrderCode(OrderInfo orderInfo) throws RpcServiceException
	{
		try
		{
			return orderInfoDetailMapper.getDeviceCountByOrderCode(orderInfo.getOrderCode());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public OrderInfoDetail getOrderInfoDetailBySn(String sn) throws RpcServiceException
	{
		try
		{
			return orderInfoDetailMapper.getOrderInfoDetailBySn(sn);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public Integer delOrderInfoDetailById(OrderInfoDetail orderDetail) throws RpcServiceException
	{
		try
		{
			return orderInfoDetailMapper.deleteByPrimaryKey(orderDetail.getId());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public Integer addOrderInfoDetail(OrderInfoDetail record) throws RpcServiceException
	{
		try
		{
			return orderInfoDetailMapper.insertSelective(record);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public Integer batchAddOrderInfoDetail(List<OrderInfoDetail> detailList) throws RpcServiceException
	{
		try
		{
			return orderInfoDetailMapper.batchAddOrderInfoDetail(detailList);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public Integer countOrderInfos(OrderInfo orderInfo) throws RpcServiceException
	{
		try
		{
			return orderInfoMapper.count(orderInfo);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	public List<OrderInfo> getCountOrderDetails(List<String> orderCodes) throws RpcServiceException
	{
		try
		{
			return orderInfoMapper.getCountOrderDetails(orderCodes);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			throw new RpcServiceException(DefaultErrorEnum.DATABASE_ERROR,e.getMessage());
		}
	}
	
	
}
