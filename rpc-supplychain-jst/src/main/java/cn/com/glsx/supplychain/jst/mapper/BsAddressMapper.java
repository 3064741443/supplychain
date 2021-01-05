package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsAddress;

@Mapper
public interface BsAddressMapper extends OreMapper<BsAddress>{
   
    int insert(BsAddress record);

    int insertSelective(BsAddress record);

    int updateByPrimaryKeySelective(BsAddress record);

    int updateByPrimaryKey(BsAddress record);
    
    List<BsAddress> listMerchantOrderAddress(BsAddress record);
}