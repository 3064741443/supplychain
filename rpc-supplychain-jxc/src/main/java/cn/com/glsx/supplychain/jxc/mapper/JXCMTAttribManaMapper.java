package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.JXCMTAttribManaDTO;
import cn.com.glsx.supplychain.jxc.model.JXCMTAttribMana;

@Mapper
public interface JXCMTAttribManaMapper extends OreMapper<JXCMTAttribMana>{
   
	public Page<JXCMTAttribMana> pageAttribMana(JXCMTAttribMana record);
}