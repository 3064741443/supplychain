package cn.com.glsx.supplychain.jst.convert;

import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.JstUserOpenIdDTO;
import cn.com.glsx.supplychain.jst.model.JstUserOpenId;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.vo.UserInfoVo;

public class JstUserOpenIdHttpConvert {

	public static UserInfoVo convertDto(JstUserOpenIdDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		UserInfoVo vo = new UserInfoVo();
		vo.setConsumer(record.getConsumer());
		vo.setLoginType(record.getLoginType());
		vo.setMerchantCode(record.getMerchantCode());
		vo.setMerchantName(record.getMerchantName());
		vo.setPassword(record.getPassword());
		vo.setPhone(record.getPhone());
		vo.setRole(record.getRole());
		vo.setShopCode(record.getShopCode());
		vo.setShopName(record.getShopName());
		vo.setVersion(record.getVersion());
		return vo;
	}
	
	public static JstUserOpenIdDTO convertVo(UserInfoVo record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		JstUserOpenIdDTO dto = new JstUserOpenIdDTO();
		dto.setConsumer(record.getConsumer());
		dto.setLoginType(record.getLoginType());
		dto.setMerchantCode(record.getMerchantCode());
		dto.setMerchantName(record.getMerchantName());
		dto.setPassword(record.getPassword());
		dto.setPhone(record.getPhone());
		dto.setRole(record.getRole());
		dto.setShopCode(record.getShopCode());
		dto.setShopName(record.getShopName());
		dto.setVersion(record.getVersion());
		dto.setCreatedBy(record.getMerchantCode());
		dto.setUpdatedBy(record.getMerchantCode());
		dto.setCreatedDate(JstUtils.getNowDate());
		dto.setUpdatedDate(JstUtils.getNowDate());
		return dto;
	}
}
