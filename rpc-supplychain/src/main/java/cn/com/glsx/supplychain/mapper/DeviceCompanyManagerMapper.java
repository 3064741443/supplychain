package cn.com.glsx.supplychain.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.glsx.supplychain.model.DeviceCompanyManager;

@Mapper
public interface DeviceCompanyManagerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceCompanyManager record);

    int insertSelective(DeviceCompanyManager record);

    DeviceCompanyManager selectByPrimaryKey(Integer id);
    
    DeviceCompanyManager selectByUniqueKey(DeviceCompanyManager record);

    int updateByPrimaryKeySelective(DeviceCompanyManager record);

    int updateByPrimaryKey(DeviceCompanyManager record);
}