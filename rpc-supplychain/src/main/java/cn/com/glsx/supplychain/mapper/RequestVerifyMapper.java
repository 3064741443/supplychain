package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.RequestVerify;

@Mapper
public interface RequestVerifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RequestVerify record);

    int insertSelective(RequestVerify record);

    RequestVerify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RequestVerify record);

    int updateByPrimaryKey(RequestVerify record);
    
    int countVerifyConsumers(RequestVerify record);
}