package cn.com.glsx.supplychain.jst.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.kafka.UpdateDispatchOrder;
import cn.com.glsx.supplychain.jst.model.EcMerchantOrder;
import cn.com.glsx.supplychain.jst.model.OrderInfoDetail;
import cn.com.glsx.supplychain.jst.service.EcMerchantOrderService;
import cn.com.glsx.supplychain.jst.service.OrderService;
import cn.com.glsx.supplychain.jst.util.JstUtils;

public class UpdateDispatchOrderThread extends Thread{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private OrderService orderService;
	
	private EcMerchantOrderService ecMerchantOrderService;
	
	private UpdateDispatchOrder updateDispatchOrder;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public EcMerchantOrderService getEcMerchantOrderService() {
		return ecMerchantOrderService;
	}

	public void setEcMerchantOrderService(
			EcMerchantOrderService ecMerchantOrderService) {
		this.ecMerchantOrderService = ecMerchantOrderService;
	}

	public UpdateDispatchOrder getUpdateDispatchOrder() {
		return updateDispatchOrder;
	}

	public void setUpdateDispatchOrder(UpdateDispatchOrder updateDispatchOrder) {
		this.updateDispatchOrder = updateDispatchOrder;
	}
	
	@Override
	public void run()
	{
		try
		{
			logger.info("UpdateDispatchOrderThread::run handle updateDispatchOrder:{}",updateDispatchOrder);
			if(StringUtils.isEmpty(updateDispatchOrder))
			{
				return;
			}
			if(StringUtils.isEmpty(updateDispatchOrder.getDispatchOrderCodes()))
			{
				return;
			}
			if(updateDispatchOrder.getDispatchOrderCodes().size() == 0)
			{
				return;
			}
			List<OrderInfoDetail> listOrderInfoDetail = orderService.ListCountOrderInfoDetailGroupByLogistics(updateDispatchOrder.getDispatchOrderCodes());
			if(StringUtils.isEmpty(listOrderInfoDetail))
			{
				return;
			}
			
			Map<String,List<OrderInfoDetail>> mapResult = new HashMap<>();
			for(OrderInfoDetail detail:listOrderInfoDetail)
			{
				if(StringUtils.isEmpty(detail.getMerchantOrderNumber()))
				{
					continue;
				}
				
				List<OrderInfoDetail> listDetail = mapResult.get(detail.getMerchantOrderNumber());
				if(StringUtils.isEmpty(listDetail))
				{
					listDetail = new ArrayList<>();
					mapResult.put(detail.getMerchantOrderNumber(), listDetail);
				}
				listDetail.add(detail);
			}
			
			for(Map.Entry<String,List<OrderInfoDetail>> entry:mapResult.entrySet())
			{
				String merchantOrderCode = entry.getKey();
				List<OrderInfoDetail> listDetail = entry.getValue();
				Integer alreadyShipMentQuantity = 0;
				Integer oweQuantity = 0;
				String shipmentTime = "";
				String shipmentQuantity = "";
				String logisticsDesc = "";
				
				int i = 0;
				
				for(OrderInfoDetail detail:listDetail)
				{
					alreadyShipMentQuantity += detail.getLogisticsCount();
					if(i != 0)
					{
						shipmentTime += ",";
					}
					shipmentTime += JstUtils.getNowDateStringYMD(detail.getCreatedDate());
					if(i != 0)
					{
						shipmentQuantity += ",";
					}
					shipmentQuantity += detail.getLogisticsCount();
					logisticsDesc += detail.getLogisticsCode();
					logisticsDesc += ":";
					logisticsDesc += detail.getLogisticsCount();
					logisticsDesc += "\n";
					logisticsDesc += detail.getLogisticsCompany();
					logisticsDesc += "\n";
					i++;
				}
				
				EcMerchantOrder dbMerchantOrder = ecMerchantOrderService.getEcMerchantOrderByMerchantCode(merchantOrderCode);
				if(StringUtils.isEmpty(dbMerchantOrder))
				{
					continue;
				}
				EcMerchantOrder upMerchantOrder = new EcMerchantOrder();
				upMerchantOrder.setId(dbMerchantOrder.getId());
				upMerchantOrder.setShipmentQuantity(shipmentQuantity);
				upMerchantOrder.setShipmentTime(shipmentTime);
				upMerchantOrder.setAlreadyShipmentQuantity(alreadyShipMentQuantity);
				upMerchantOrder.setOweQuantity(dbMerchantOrder.getOrderQuantity()-alreadyShipMentQuantity);
				upMerchantOrder.setLogisticsDesc(logisticsDesc);
				ecMerchantOrderService.updateEcMerchantOrderByIdNoCatch(upMerchantOrder);
				mapResult = null;
				logger.info("UpdateDispatchOrderThread::run handle ok!");
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(),e);
		}
	}
}
