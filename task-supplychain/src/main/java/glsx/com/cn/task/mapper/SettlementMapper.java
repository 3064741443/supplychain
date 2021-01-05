package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.Settlement;

import java.util.List;

@Mapper
public interface SettlementMapper extends OreMapper<Settlement> {

    List<Settlement> settlementList(Settlement settlement);
}