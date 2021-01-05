package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementFinance;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementFinanceMapper extends OreMapper<StatementFinance> {

    List<StatementFinance> listStatementFinance(StatementFinance record);

    int add(StatementFinance record);

    int updateById(StatementFinance record);
    
    int batchInsertOnDuplicate(List<StatementFinance> listFinance);

}