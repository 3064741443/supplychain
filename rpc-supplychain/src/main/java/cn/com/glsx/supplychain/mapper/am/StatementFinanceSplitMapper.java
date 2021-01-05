package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementFinanceSplitMapper extends OreMapper<StatementFinanceSplit> {
    Page<StatementFinanceSplit> listStatementFinanceSplit(StatementFinanceSplit record);

    List<StatementFinanceSplit>exportStatementFinanceSplitExit(StatementFinanceSplit record);

    int add(StatementFinanceSplit record);

    int updateById(StatementFinanceSplit record);

    int deleteStatementFinanceSplitByDate(StatementFinanceSplit record);

}