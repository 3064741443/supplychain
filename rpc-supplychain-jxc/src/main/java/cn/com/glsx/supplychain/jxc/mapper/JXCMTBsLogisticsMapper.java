package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDetailDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTBsLogistics;

@Mapper
public interface JXCMTBsLogisticsMapper extends OreMapper<JXCMTBsLogistics>{
    
	public int insertLogistics(JXCMTBsLogistics record);
	
	public Page<JXCMTOrderInfoDetailDTO> pageOrderInfoDetail(JXCMTBsLogistics record);
}