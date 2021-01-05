package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.MaintainProductDetail;
import cn.com.glsx.supplychain.model.bs.MaintainSnChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface MaintainSnChangeMapper{

    int deleteByPrimaryKey(Long id);

    int insertList(List<MaintainSnChange> record);

    int insertSelective(MaintainSnChange record);

    int updateByPrimaryKeySelective(MaintainSnChange record);

    MaintainSnChange selectById(Long id);

    MaintainSnChange selectByMainTainDetailId(Long id);

    int updateByPrimaryKey(MaintainSnChange record);

    List<MaintainSnChange>getMainTainSnChangeList(MaintainSnChange record);

    List<MaintainSnChange>getMainTainSnChangeListById(@Param("ids")List<Long> ids);

}