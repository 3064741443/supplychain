package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementCollectionSplitMapper extends OreMapper<StatementCollectionSplit> {

    List<StatementCollectionSplit> listStatementCollectionSplit(StatementCollectionSplit statementCollectionSplit);

    int add(StatementCollectionSplit record);

    int updateById(StatementCollectionSplit record);
    
    //商户库销专用查询借口
    List<StatementCollectionSplit> listStatementCollectionSplitForMerchantStock(StatementCollectionSplit record);

}