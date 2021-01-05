package cn.com.glsx.supplychain.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTProductSplit;

@Mapper
public interface JXCMTProductSplitMapper extends OreMapper<JXCMTProductSplit>{
   
	public List<JXCMTProductSplit> listJxcProductSplit(JXCMTProductSplit record);
}