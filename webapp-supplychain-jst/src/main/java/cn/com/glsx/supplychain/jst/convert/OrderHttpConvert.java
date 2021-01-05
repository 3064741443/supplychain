package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.OrderDTO;
import cn.com.glsx.supplychain.jst.vo.OrderVO;

public class OrderHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(OrderHttpConvert.class);
	
	public static List<OrderVO> convertListDto(List<OrderDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<OrderVO> listVo = new ArrayList<OrderVO>();
		for(OrderDTO dto:record)
		{
			listVo.add(convertDto(dto));
		}
		return listVo;
	}
	
	public static OrderVO convertDto(OrderDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		OrderVO vo = new OrderVO();
		vo.setAddressVo(BsAddressHttpConvert.convertDTO(record.getAddressDto()));
		vo.setCheckCount(record.getCheckCount());
		vo.setListDetail(OrderDetailHttpConvert.convertListDto(record.getListOrderDetailDto()));
		vo.setLogisticsVo(BsLogisticsHttpConvert.convertDto(record.getLogisticsDto()));
		vo.setMaterialCode(record.getMaterialCode());
		vo.setMaterialName(record.getMaterialName());
		vo.setOrderCode(record.getOrderCode());
		vo.setOrderCount(record.getOrderCount());
		vo.setPackageOne(record.getPackageOne());
		vo.setPrice(record.getPrice());
		vo.setProductCode(record.getProductCode());
		vo.setProductName(record.getProductName());
		vo.setSendCount(record.getSendCount());
		vo.setServiceTime(record.getServiceTime());
		vo.setShopCode(record.getShopCode());
		vo.setShopName(record.getShopName());
		vo.setAgentMerchantCode(record.getAgentMerchantCode());
		vo.setAgentMerchantName(record.getAgentMerchantName());
		vo.setWaitCount(record.getWaitCount());
		return vo;
	}
}
