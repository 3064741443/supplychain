package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.Address;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface AddressMapper extends OreMapper<Address> {
    int insert(Address address);
    
    Address selectByPhoneAndAddress(Address address);

    Address selectById(Long id);
}