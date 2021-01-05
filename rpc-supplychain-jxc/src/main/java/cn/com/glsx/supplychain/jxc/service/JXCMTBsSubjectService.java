package cn.com.glsx.supplychain.jxc.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsSubjectDTO;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsSubjectMapper;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsSubject;

@Service
public class JXCMTBsSubjectService {
	private static final Logger logger = LoggerFactory.getLogger(JXCMTBsSubjectService.class);
	@Autowired
	private JXCMTBsSubjectMapper jxcmtBsSubjectMapper;
	
	public List<JXCMTBsSubjectDTO> listBsSubjectDto(JXCMTBsSubjectDTO record){
		List<JXCMTBsSubjectDTO> listBsSubjectDto = new ArrayList<>();
		List<JXCMTBsSubject> listBsSubject = jxcmtBsSubjectMapper.selectAll();
		JXCMTBsSubjectDTO dto = null;
		if(null == listBsSubject){
			return listBsSubjectDto;
		}
		for(JXCMTBsSubject subject:listBsSubject){
			dto = new JXCMTBsSubjectDTO();
			dto.setId(subject.getId());
			dto.setName(subject.getName());
			listBsSubjectDto.add(dto);
		}
		return listBsSubjectDto;
	}
	
	public String getBsSubjectNameById(Integer id){
		if(null == id){
			return null;
		}
		JXCMTBsSubject condition = new JXCMTBsSubject();
		condition.setId(id);
		JXCMTBsSubject result = jxcmtBsSubjectMapper.selectOne(condition);
		if(null == result){
			return null;
		}
		return result.getName();
	}
	
}
