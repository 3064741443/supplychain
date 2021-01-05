package cn.com.glsx.supplychain.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;
import com.github.pagehelper.Page;
import cn.com.glsx.supplychain.model.WareHouseInfo;

@Mapper
public interface WareHouseInfoMapper  extends OreMapper<WareHouseInfo> {
	
	Page<WareHouseInfo> listWareHouseInfo(WareHouseInfo userInfo);
	
	int deleteWareHouseInfo(Integer id);

    int insert(WareHouseInfo record);

    WareHouseInfo getWareHouseById(Integer id);
    
    WareHouseInfo getWareHouseByName(String name);
    
    WareHouseInfo getWareHouseByUpdate(WareHouseInfo record);

    int update(WareHouseInfo record);
    
    int count(WareHouseInfo record);
    
    List<WareHouseInfo> getWareHouseInfo();
    
    List<WareHouseInfo> getWareHouseInfoList(WareHouseInfo record);
}