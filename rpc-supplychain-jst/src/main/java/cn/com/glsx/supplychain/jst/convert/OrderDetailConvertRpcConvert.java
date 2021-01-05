package cn.com.glsx.supplychain.jst.convert;

import cn.com.glsx.supplychain.jst.dto.OrderDetailDTO;
import cn.com.glsx.supplychain.jst.model.JstShopOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailConvertRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(OrderDetailConvertRpcConvert.class);
	
	public static List<OrderDetailDTO> convertListBean(List<JstShopOrderDetail> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<OrderDetailDTO> listDto = new ArrayList<OrderDetailDTO>();
		for(JstShopOrderDetail model:record)
		{
			listDto.add(convertBean(model));
		}
		return listDto;
	}
	
	public static OrderDetailDTO convertBean(JstShopOrderDetail record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setAttribCode(record.getAttribCode());
		dto.setCreatedBy(record.getCreatedBy());
		dto.setUpdatedBy(record.getUpdatedBy());
		dto.setCreatedDate(record.getCreatedDate());
		dto.setUpdatedDate(record.getUpdatedDate());
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setLogisticsId(record.getLogisticsId());
		dto.setShopOrderCode(record.getShopOrderCode());
		dto.setSn(record.getSn());
		dto.setVtSn(record.getVtSn());
		return dto;
	}
}
