package cn.com.glsx.supplychain.jst.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.enums.MerchantStockDeviceEnum;
import cn.com.glsx.supplychain.jst.kafka.SignMerchantOrder;
import cn.com.glsx.supplychain.jst.model.BsMerchantStockDevice;
import cn.com.glsx.supplychain.jst.model.OrderInfo;
import cn.com.glsx.supplychain.jst.model.OrderInfoDetail;
import cn.com.glsx.supplychain.jst.service.BsMerchantStockDeviceService;
import cn.com.glsx.supplychain.jst.service.OrderService;
import cn.com.glsx.supplychain.jst.util.JstUtils;

public class SignMerchantOrderThread extends Thread{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private OrderService orderService;
	
	private BsMerchantStockDeviceService stockDeviceService;
	
	private SignMerchantOrder signMerchantOrder;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public SignMerchantOrder getSignMerchantOrder() {
		return signMerchantOrder;
	}

	public void setSignMerchantOrder(SignMerchantOrder signMerchantOrder) {
		this.signMerchantOrder = signMerchantOrder;
	}
	
	@Override
	public void run()
	{
		try
		{
			logger.info("SignMerchantOrderThread::run handle signMerchantOrder:{}",signMerchantOrder);
			if(StringUtils.isEmpty(signMerchantOrder))
			{
				logger.info("signMerchantOrder 为空");
				return;
			}
			if(StringUtils.isEmpty(signMerchantOrder.getLogiticsId()))
			{
				logger.info("logisticsid 为空");
				return;
			}
			if(StringUtils.isEmpty(signMerchantOrder.getOrderCode()))
			{
				logger.info("ordercode 为空");
				return;
			}
			
			OrderInfo orderInfo = orderService.getDispatchOrderInfoByDispatchOrderCode(signMerchantOrder.getOrderCode());
			if(StringUtils.isEmpty(orderInfo))
			{
				logger.info("orderinfo 为空");
				return;
			}
			OrderInfoDetail condition = new OrderInfoDetail();
			condition.setOrderCode(signMerchantOrder.getOrderCode());
			condition.setLogisticsId(signMerchantOrder.getLogiticsId().intValue());
			List<OrderInfoDetail> listOrderDetail = orderService.listOrderInfoDetail(condition);
			if(null == listOrderDetail || listOrderDetail.isEmpty())
			{
				logger.info("listOrderDetail 为空");
				return;
			}
			List<BsMerchantStockDevice>	listStockDevice = new ArrayList<BsMerchantStockDevice>();
			List<String> listSn = listOrderDetail.stream().map(t->t.getSn()).distinct().collect(Collectors.toList());
			List<BsMerchantStockDevice> listExistDevice = stockDeviceService.listMerchantStockDevice(listSn, orderInfo.getSendMerchantNo());
			Map<String,BsMerchantStockDevice> mapExistDevice = listExistDevice.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, a -> a,(k1,k2)->k1));
			if(null == mapExistDevice){
				mapExistDevice = new HashMap<>();
			}
			BsMerchantStockDevice merchantStockDevice = null;
			
			for(OrderInfoDetail orderDetail:listOrderDetail)
			{
				merchantStockDevice = mapExistDevice.get(orderDetail.getSn());
				if(null != merchantStockDevice){
					continue;
				}
				BsMerchantStockDevice stockDevice = new BsMerchantStockDevice();
				stockDevice.setSn(orderDetail.getSn());
				stockDevice.setAttribCode(orderDetail.getAttribCode());
				stockDevice.setIccid(orderDetail.getIccid());
				stockDevice.setMerchantCode(orderInfo.getSendMerchantNo());
				stockDevice.setMerchantName(orderInfo.getSendMerchantName());
				stockDevice.setInTime(JstUtils.getNowDate());
				stockDevice.setInLogisticsId(signMerchantOrder.getLogiticsId().intValue());
				stockDevice.setStatu(MerchantStockDeviceEnum.STATU_IN.getValue());
				stockDevice.setCreatedBy(orderInfo.getSendMerchantNo());
				stockDevice.setUpdatedBy(orderInfo.getSendMerchantNo());
				stockDevice.setCreatedDate(JstUtils.getNowDate());
				stockDevice.setUpdatedDate(JstUtils.getNowDate());
				stockDevice.setDeletedFlag("N");
				listStockDevice.add(stockDevice);
			}
			stockDeviceService.batchAddMerchantStockDevice(listStockDevice);
			logger.info("SignMerchantOrderThread::run handle ok");
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(),e);
		}	
	}

	public BsMerchantStockDeviceService getStockDeviceService() {
		return stockDeviceService;
	}

	public void setStockDeviceService(BsMerchantStockDeviceService stockDeviceService) {
		this.stockDeviceService = stockDeviceService;
	}

	
}
