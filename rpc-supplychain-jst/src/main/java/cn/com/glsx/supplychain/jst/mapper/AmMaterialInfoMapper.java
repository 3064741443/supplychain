package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.AmMaterialInfo;

@Mapper
public interface AmMaterialInfoMapper extends OreMapper<AmMaterialInfo>{
    
	
    int insert(AmMaterialInfo record);

    int insertSelective(AmMaterialInfo record);

    int updateByPrimaryKeySelective(AmMaterialInfo record);

    int updateByPrimaryKey(AmMaterialInfo record);
    
    List<AmMaterialInfo> listMaterialInfoByMaterialCode(List<String> listMaterialCode);
    
    List<AmMaterialInfo> listMaterialInfoByFirstCode(List<String> listFirstLevelName);
}