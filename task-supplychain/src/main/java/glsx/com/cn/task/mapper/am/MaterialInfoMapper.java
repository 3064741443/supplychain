package glsx.com.cn.task.mapper.am;

import cn.com.glsx.supplychain.model.am.MaterialInfo;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface MaterialInfoMapper extends OreMapper<MaterialInfo> {

    int batchInsertOnDuplicateKeyUpdate(List<MaterialInfo> record);

    int add(MaterialInfo record);

    int updateBymaterialCode(MaterialInfo record);

    MaterialInfo getMaxTime();
}
