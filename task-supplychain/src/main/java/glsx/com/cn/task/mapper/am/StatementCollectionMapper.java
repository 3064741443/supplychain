package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.StatementCollection;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface StatementCollectionMapper extends OreMapper<StatementCollection> {

    List<StatementCollection> listStatementCollection(StatementCollection statementCollection);

    int add(StatementCollection record);

    int updateById(StatementCollection record);
    
    

}