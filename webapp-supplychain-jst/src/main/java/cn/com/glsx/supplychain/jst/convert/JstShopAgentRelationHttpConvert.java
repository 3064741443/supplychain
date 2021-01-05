package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstShopAgentRelationDTO;
import cn.com.glsx.supplychain.jst.vo.MerchantVO;

public class JstShopAgentRelationHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(JstShopAgentRelationHttpConvert.class);
	
	public static List<MerchantVO> convertDTOList(List<JstShopAgentRelationDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<MerchantVO> voList = new ArrayList<MerchantVO>();
		for(JstShopAgentRelationDTO dtoItem:record)
		{
			voList.add(convertDTO(dtoItem));
		}
		return voList;
	}
	
	public static MerchantVO convertDTO(JstShopAgentRelationDTO record)
	{
		MerchantVO vo = new MerchantVO();
		vo.setAgentMerchantCode(record.getAgentMerchantCode());
		vo.setAgentMerchantName(record.getAgentMerchantName());
		return vo;
	}
}
