package cn.com.glsx.supplychain.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementFinance;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementFinanceMapper extends OreMapper<StatementFinance> {
    Page<StatementFinance> listStatementFinance(StatementFinance record);

    StatementFinance getMaxDate();

    StatementFinance getStatementFinanceByid(Long id);

    int add(StatementFinance record);

    int updateById(StatementFinance record);

    int batchAddStatementFinance(List<StatementFinance> statementFinanceList);

    int deleteStatementFinanceByDate(StatementFinance record);
    
    List<StatementFinance> selectStatementFinanceByDate(StatementFinance record);

}