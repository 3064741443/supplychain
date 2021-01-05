package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.glsx.supplychain.jst.dto.gh.AttribInfoDTO;
import cn.com.glsx.supplychain.jst.model.AttribInfo;

public class AttribInfoRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(AttribInfoRpcConvert.class);
	
	public static List<AttribInfoDTO> convertBeanList(List<AttribInfo> record){
		if(null == record){
			return null;
		}
		List<AttribInfoDTO> dtoList = new ArrayList<>();
		for(AttribInfo beanItem : record){
			dtoList.add(convertBean(beanItem));
		}
		return dtoList;
	}
	
	public static AttribInfoDTO convertBean(AttribInfo bean){
		AttribInfoDTO dto = new AttribInfoDTO();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		dto.setType(bean.getType());
		dto.setComment(bean.getComment());
		return dto;
	}
	
	public static AttribInfo convertDto(AttribInfoDTO dto){
		AttribInfo bean = new AttribInfo();
		bean.setId(dto.getId());
		bean.setType(dto.getType());
		bean.setName(dto.getName());
		bean.setComment(dto.getComment());
		return bean;
	}
	
}
