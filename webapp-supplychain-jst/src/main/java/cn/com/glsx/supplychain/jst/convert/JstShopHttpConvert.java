package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.vo.JstShopVO;

public class JstShopHttpConvert {

	
	static public List<JstShopVO> convertListDto(List<JstShopDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<JstShopVO> listVo = new ArrayList<JstShopVO>();
		for(JstShopDTO dto:record)
		{
			listVo.add(convertDTO(dto));
		}
		return listVo;
	}
	
	static public JstShopVO convertDTO(JstShopDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstShopVO vo = new JstShopVO();
		vo.setAddr(record.getAddr());
		vo.setCheckFailResult(record.getCheckFailResult());
		vo.setContact(record.getContact());
		vo.setPhone(record.getPhone());
		vo.setRemark(record.getRemark());
		vo.setShopCode(record.getShopCode());
		vo.setShopName(record.getShopName());
		vo.setShopMerchantCode(record.getShopMerchantCode());
		vo.setShopMerchantName(record.getShopMerchantName());
		vo.setStatus(record.getStatus());
		return vo;
	}
}
