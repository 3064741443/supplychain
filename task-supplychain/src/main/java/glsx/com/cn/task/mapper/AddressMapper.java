package glsx.com.cn.task.mapper;


import glsx.com.cn.task.model.Address;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

@Mapper
public interface AddressMapper extends OreMapper<Address> {
    int insert(Address address);
    
    Address selectByPhoneAndAddress(Address address);

    Address selectById(Long id);
}