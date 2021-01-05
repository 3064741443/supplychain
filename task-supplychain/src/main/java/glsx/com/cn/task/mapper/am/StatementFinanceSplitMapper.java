package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementFinanceSplit;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementFinanceSplitMapper extends OreMapper<StatementFinanceSplit> {

    List<StatementFinanceSplit> listStatementFinanceSplit(StatementFinanceSplit record);

    int add(StatementFinanceSplit record);

    int updateById(StatementFinanceSplit record);
    
    //商户库销专用查询借口
    List<StatementFinanceSplit> listStatementFinanceSplitForMerchantStock(StatementFinanceSplit record);
}