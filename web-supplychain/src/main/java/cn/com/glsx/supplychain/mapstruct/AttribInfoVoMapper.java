package cn.com.glsx.supplychain.mapstruct;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.vo.AttribInfoVo;

@Mapper
public interface AttribInfoVoMapper {

	AttribInfoVoMapper MAPPER = Mappers.getMapper(AttribInfoVoMapper.class);
	
    public AttribInfoVo AttribInfoToAttribInfoVo(AttribInfo attribInfo);
    
	public List<AttribInfoVo> AttribInfoToAttribInfoVos(List<AttribInfo> AttribInfoList);
}
