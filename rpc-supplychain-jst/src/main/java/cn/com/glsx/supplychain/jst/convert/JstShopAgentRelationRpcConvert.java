package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstShopAgentRelationDTO;
import cn.com.glsx.supplychain.jst.model.JstShopAgentRelation;

public class JstShopAgentRelationRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(JstShopAgentRelationRpcConvert.class);
	
	public static List<JstShopAgentRelationDTO> convertBeanList(List<JstShopAgentRelation> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<JstShopAgentRelationDTO> dtoList = new ArrayList<JstShopAgentRelationDTO>();
		for(JstShopAgentRelation beanItem :record)
		{
			dtoList.add(convertBean(beanItem));
		}
		return dtoList;
	}
	
	public static JstShopAgentRelationDTO convertBean(JstShopAgentRelation record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShopAgentRelationDTO dto = new JstShopAgentRelationDTO();
		dto.setId(record.getId());
		dto.setAgentMerchantCode(record.getAgentMerchantCode());
		dto.setAgentMerchantName(record.getAgentMerchantName());
		dto.setShopCode(record.getShopCode());
		return dto;
	}
	
}
