package cn.com.glsx.supplychain.jst.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.model.BsLogistics;

public class BsLogisticsRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(BsLogisticsRpcConvert.class);
	
	public static BsLogisticsDTO convertBean(BsLogistics record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsLogisticsDTO dto = new BsLogisticsDTO();
		dto.setAccept(record.getAccept());
		dto.setCode(record.getCode());
		dto.setCompany(record.getCompany());
		dto.setCreatedBy(record.getCreatedBy());
		dto.setCreatedDate(record.getCreatedDate());
		dto.setDeletedFlag(record.getDeletedFlag());
		dto.setId(record.getId());
		dto.setOrderNumber(record.getOrderNumber());
		dto.setReceiveId(record.getReceiveId());
		dto.setSendId(record.getSendId());
		dto.setServiceCode(record.getServiceCode());
		dto.setShipmentsQuantity(record.getShipmentsQuantity());
		dto.setType(record.getType());
		dto.setUpdatedBy(record.getUpdatedBy());
		dto.setUpdatedDate(record.getUpdatedDate());
		dto.setShipType(record.getShipType());
		return dto;
	}
	
	public static BsLogistics convertDto(BsLogisticsDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		BsLogistics model = new BsLogistics();
		model.setAccept(record.getAccept());
		model.setCode(record.getCode());
		model.setCompany(record.getCompany());
		model.setCreatedBy(record.getCreatedBy());
		model.setCreatedDate(record.getCreatedDate());
		model.setDeletedFlag(record.getDeletedFlag());
		model.setId(record.getId());
		model.setOrderNumber(record.getOrderNumber());
		model.setReceiveId(record.getReceiveId());
		model.setSendId(record.getSendId());
		model.setServiceCode(record.getServiceCode());
		model.setShipmentsQuantity(record.getShipmentsQuantity());
		model.setType(record.getType());
		model.setUpdatedBy(record.getUpdatedBy());
		model.setUpdatedDate(record.getUpdatedDate());
		return model;
	}
}
