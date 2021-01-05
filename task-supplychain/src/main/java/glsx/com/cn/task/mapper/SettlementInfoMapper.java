package glsx.com.cn.task.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import glsx.com.cn.task.model.SettlementInfo;

import java.util.List;

@Mapper
public interface SettlementInfoMapper extends OreMapper<SettlementInfo> {

    int countSettlementId();

    List<SettlementInfo> settlementInfoList(SettlementInfo settlementInfo);

}