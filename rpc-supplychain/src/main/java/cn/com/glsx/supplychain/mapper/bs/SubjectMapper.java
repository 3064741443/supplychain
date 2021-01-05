package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Subject record);

    int insertSelective(Subject record);

    Subject selectByPrimaryKey(Integer id);

    List<Subject> select(Subject subject);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKey(Subject record);
}