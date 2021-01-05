package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jst.model.BsDealerUserInfo;

@Mapper
public interface BsDealerUserInfoMapper extends OreMapper<BsDealerUserInfo>{
 
    int insert(BsDealerUserInfo record);

    int insertSelective(BsDealerUserInfo record);

    int updateByPrimaryKeySelective(BsDealerUserInfo record);

    int updateByPrimaryKey(BsDealerUserInfo record);
    
    List<BsDealerUserInfo> listByMerchantCode(List<String> listMerchantCode);
}