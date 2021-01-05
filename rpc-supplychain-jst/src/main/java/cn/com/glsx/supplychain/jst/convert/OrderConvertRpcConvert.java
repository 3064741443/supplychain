package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.OrderDTO;
import cn.com.glsx.supplychain.jst.model.BsMerchantOrder;
import cn.com.glsx.supplychain.jst.model.JstShopOrder;

public class OrderConvertRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(OrderConvertRpcConvert.class);
	
	public static List<OrderDTO> convertMerchantOrderListBean(List<BsMerchantOrder> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<OrderDTO> listDto = new ArrayList<OrderDTO>();
		for(BsMerchantOrder model:record)
		{
			listDto.add(convertMerchantOrderBean(model));
		}
		return listDto;
	}
	
	public static OrderDTO convertMerchantOrderBean(BsMerchantOrder record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		OrderDTO dto = new OrderDTO();
		dto.setOrderCode(record.getOrderNumber());
		dto.setProductCode(record.getBsProductCode());
		dto.setProductName(record.getBsProductName());
		dto.setPackageOne(record.getBsPackageOne());
		dto.setServiceTime(record.getBsServiceTime());
		dto.setPrice(record.getBsprice());
		dto.setOrderCount(record.getTotalOrder()==null?0:record.getTotalOrder());
		dto.setCheckCount(record.getTotalCheck()==null?0:record.getTotalCheck());
		dto.setSendCount(record.getSendCount()==null?0:record.getSendCount());
		dto.setWaitCount(dto.getOrderCount()-dto.getSendCount());
		dto.setMaterialCode(record.getBsMaterialCode());
		dto.setMaterialName(record.getBsMaterialName());
		dto.setPrice(record.getBsprice());
		dto.setAddressDto(BsAddressRpcConvert.convertBean(record.getBsAddress()));
		dto.setLogisticsDto(BsLogisticsRpcConvert.convertBean(record.getBsLogistics()));
		return dto;
	}
	
	public static List<OrderDTO> convertShopOrderListBean(List<JstShopOrder> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<OrderDTO> listDto = new ArrayList<OrderDTO>();
		for(JstShopOrder model:record)
		{
			listDto.add(convertShopOrderBean(model));
		}
		return listDto;
	}
	
	public static OrderDTO convertShopOrderBean(JstShopOrder record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		OrderDTO dto = new OrderDTO();
		dto.setOrderCode(record.getShopOrderCode());
		dto.setShopCode(record.getShopCode());
		dto.setShopName(record.getShopName());
		dto.setAgentMerchantCode(record.getAgentMerchantCode());
		dto.setAgentMerchantName(record.getAgentMerchantName());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setPackageOne(record.getPackageOne());
		dto.setServiceTime(record.getServiceTime());
		dto.setPrice(record.getPrice());
		dto.setOrderCount(record.getTotal()==null?0:record.getTotal());
		dto.setSendCount(record.getSendCount()==null?0:record.getSendCount());
		dto.setCheckCount(record.getTotal()==null?0:record.getTotal());
		dto.setWaitCount(dto.getOrderCount()-dto.getSendCount());
		dto.setMaterialCode(record.getMaterialCode());
		dto.setMaterialName(record.getMaterialName());
		dto.setPrice(record.getPrice());
		dto.setAddressDto(BsAddressRpcConvert.convertBean(record.getBsAddress()));
		dto.setLogisticsDto(BsLogisticsRpcConvert.convertBean(record.getBsLogistics()));
		return dto;
	}
}
