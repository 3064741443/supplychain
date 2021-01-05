package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.MaintainProduct;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface MaintainProductMapper{

    Page<MaintainProduct> listMaintainProduct(MaintainProduct maintainProduct);

    int deleteByPrimaryKey(Long id);

    int insertList(List<MaintainProduct> record);

    int insertSelective(MaintainProduct record);

    MaintainProduct selectById(Long id);

    int updateByPrimaryKeySelective(MaintainProduct record);

    int updateByPrimaryKey(MaintainProduct record);

    MaintainProduct getMainTainProductInfo(MaintainProduct maintainProduct);
}