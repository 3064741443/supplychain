package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface DealerUserInfoMapper extends OreMapper<DealerUserInfo> {

    Page<DealerUserInfo> listDealerUserInfo(DealerUserInfo dealerUserInfo);

    Integer updateByDealerUserName(DealerUserInfo dealerUserInfo);

    DealerUserInfo getDelerUseInfoById(DealerUserInfo dealerUserInfo);

    Integer updateByDealerUserId(DealerUserInfo dealerUserInfo);

    Integer deleteByDealerUserId(DealerUserInfo dealerUserInfo);

    List<DealerUserInfo>gteDealerUserInfoList(DealerUserInfo dealerUserInfo);
}