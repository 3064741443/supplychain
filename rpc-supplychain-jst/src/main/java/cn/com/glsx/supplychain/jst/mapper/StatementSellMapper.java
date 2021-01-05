package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.model.StatementSell;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/8/5 17:33
 */
@Mapper
public interface StatementSellMapper extends OreMapper<StatementSell> {
    List<StatementSell> listStatement(List<String> merchantCodeList);
}
