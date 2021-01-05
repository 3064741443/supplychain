package cn.com.glsx.supplychain.mapper;

import cn.com.glsx.supplychain.model.Settlement;
import cn.com.glsx.supplychain.model.bs.ProductDetail;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface SettlementMapper extends OreMapper<Settlement> {
    int MaxSettlementId();

    List<Settlement> SettlementList(Settlement settlement);

    int insertSettlementList(List<Settlement> settlementList);
}