package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementCollection;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface StatementCollectionMapper extends OreMapper<StatementCollection> {
    Page<StatementCollection> listStatementCollection(StatementCollection statementCollection);

    StatementCollection getMaxDate();

    StatementCollection getStatementCollectionByid(Long id);

    int add(StatementCollection record);

    int batchAddStatementCollection(List<StatementCollection> statementCollectionList);

    int updateById(StatementCollection record);

    int deleteStatementCollectionByDate(StatementCollection record);

}