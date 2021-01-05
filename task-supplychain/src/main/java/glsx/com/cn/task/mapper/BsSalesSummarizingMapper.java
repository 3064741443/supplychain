package glsx.com.cn.task.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glsx.com.cn.task.model.BsSalesSummarizing;

@Mapper
public interface BsSalesSummarizingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BsSalesSummarizing record);

    int insertSelective(BsSalesSummarizing record);

    BsSalesSummarizing selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BsSalesSummarizing record);

    int updateByPrimaryKey(BsSalesSummarizing record);
    
    List<BsSalesSummarizing> listSalesSummarizingByCondition(BsSalesSummarizing record);
}