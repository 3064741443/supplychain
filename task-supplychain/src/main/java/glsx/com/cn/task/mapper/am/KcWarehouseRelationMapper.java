package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.KcWarehouseRelation;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface KcWarehouseRelationMapper extends OreMapper<KcWarehouseRelation> {

    List<KcWarehouseRelation> getKcWarehouseRelationList(KcWarehouseRelation record);

    KcWarehouseRelation getKcWarehouseRelationById(Long id);

    int add(KcWarehouseRelation record);

    int updateById(KcWarehouseRelation record);

}