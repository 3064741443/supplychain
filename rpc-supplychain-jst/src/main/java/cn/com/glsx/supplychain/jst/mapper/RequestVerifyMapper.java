package cn.com.glsx.supplychain.jst.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.RequestVerify;

@Mapper
public interface RequestVerifyMapper extends OreMapper<RequestVerify>{
    
    int insert(RequestVerify record);

    int insertSelective(RequestVerify record);

    int updateByPrimaryKeySelective(RequestVerify record);

    int updateByPrimaryKey(RequestVerify record);
    
    int countVerifyConsumers(RequestVerify record);
}