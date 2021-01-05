package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.glsx.supplychain.jst.dto.gh.AttribInfoDTO;
import cn.com.glsx.supplychain.jst.vo.gh.AttribInfoVO;

public class AttribInfoHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(AttribInfoHttpConvert.class);
	
	public static List<AttribInfoVO> convertList(List<AttribInfoDTO> record){
		if(null == record){
			return null;
		}
		List<AttribInfoVO> listVo = new ArrayList<>();
		for(AttribInfoDTO dto:record){
			listVo.add(convertDto(dto));
		}
		return listVo;
	}
	
	public static AttribInfoVO convertDto(AttribInfoDTO record){
		AttribInfoVO vo = new AttribInfoVO();
		vo.setId(record.getId());
		vo.setName(record.getName());
		return vo;
	}
	
}
