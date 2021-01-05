package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.model.BsDealerUserInfo;

public class BsDealerUserInfoRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(BsDealerUserInfoRpcConvert.class);
	
	public static List<BsDealerUserInfoDTO> convertBeanList(List<BsDealerUserInfo> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<BsDealerUserInfoDTO> dtoList = new ArrayList<BsDealerUserInfoDTO>();
		for(BsDealerUserInfo modelItem:record)
		{
			dtoList.add(convertBean(modelItem));
		}
		return dtoList;
	}
	
	public static BsDealerUserInfoDTO convertBean(BsDealerUserInfo record)
	{
		BsDealerUserInfoDTO dto = new BsDealerUserInfoDTO();
		dto.setChannel(record.getChannel());
		dto.setId(record.getId());
		dto.setMerchantCode(record.getMerchantCode());
		dto.setMerchantName(record.getMerchantName());
		dto.setName(record.getName());
		dto.setPassword(record.getPassword());
		dto.setSaleMode(record.getSaleMode());
		dto.setType(record.getType());
		return dto;
	}
}
