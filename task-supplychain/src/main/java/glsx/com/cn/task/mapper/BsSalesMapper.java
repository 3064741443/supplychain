package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsSales;

@Mapper
public interface BsSalesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BsSales record);

    int insertSelective(BsSales record);

    BsSales selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BsSales record);

    int updateByPrimaryKey(BsSales record);
    
    List<BsSales> listBsSalesByCondition(Long id);
     
}