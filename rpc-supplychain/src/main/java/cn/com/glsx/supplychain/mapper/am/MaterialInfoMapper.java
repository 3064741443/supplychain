package cn.com.glsx.supplychain.mapper.am;


import cn.com.glsx.supplychain.model.am.MaterialInfo;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface MaterialInfoMapper extends OreMapper<MaterialInfo> {

    int add(MaterialInfo record);

    int updateById(MaterialInfo record);
}