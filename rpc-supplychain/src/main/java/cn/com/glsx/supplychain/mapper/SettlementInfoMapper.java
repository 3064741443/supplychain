package cn.com.glsx.supplychain.mapper;

import cn.com.glsx.supplychain.model.Settlement;
import cn.com.glsx.supplychain.model.SettlementInfo;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface SettlementInfoMapper extends OreMapper<SettlementInfo> {
    int MaxSettlementId();

    List<SettlementInfo> SettlementInfoList(SettlementInfo settlementInfo);

    int insertSettlementInfoList(List<SettlementInfo> settlementInfoList);
}