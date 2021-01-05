
package glsx.com.cn.task.mapper;
import java.util.Date;
import java.util.List;

import glsx.com.cn.task.model.DeviceInfoGpsPreimport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;


@Mapper
public interface DeviceInfoGpsPreimportMapper extends OreMapper<DeviceInfoGpsPreimport>{
//    int deleteByPrimaryKey(Integer id);

    int insert(DeviceInfoGpsPreimport record);

    int insertSelective(DeviceInfoGpsPreimport record);

//    DeviceInfoGpsPreimport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceInfoGpsPreimport record);

    int updateByPrimaryKey(DeviceInfoGpsPreimport record);
    
    List<DeviceInfoGpsPreimport> listDeviceInfoGpsPreimport(@Param("updatedDate")Date updatedDate,@Param("listSns") List<String> sns);
}