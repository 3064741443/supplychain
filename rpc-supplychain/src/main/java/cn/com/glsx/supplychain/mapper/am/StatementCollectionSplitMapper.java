package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementCollectionSplitMapper extends OreMapper<StatementCollectionSplit> {
    Page<StatementCollectionSplit> listStatementCollectionSplit(StatementCollectionSplit statementCollectionSplit);

    List<StatementCollectionSplit> exportStatementCollectionSplitExit(StatementCollectionSplit statementCollectionSplit);

    int add(StatementCollectionSplit record);

    int updateById(StatementCollectionSplit record);

    int deleteStatementCollectionSplitByDate(StatementCollectionSplit record);

}