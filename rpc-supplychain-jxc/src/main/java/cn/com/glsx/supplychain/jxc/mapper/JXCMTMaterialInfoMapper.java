package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.model.JXCMTMaterialInfo;

@Mapper
public interface JXCMTMaterialInfoMapper extends OreMapper<JXCMTMaterialInfo>{
   
	Page<JXCMTMaterialInfo> pageMaterialInfo(JXCMTMaterialInfo record);
}