package cn.com.glsx.supplychain.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.model.FirmwareInfo;

import com.github.pagehelper.Page;


@Mapper
public interface FirmwareInfoMapper extends OreMapper<FirmwareInfo>  {
    int insert(FirmwareInfo record);

    FirmwareInfo getFirmwareInfoById(Integer id);

    int update(FirmwareInfo record);
    
    Page<FirmwareInfo> listFirmwareInfo(FirmwareInfo record);
    
    int count(FirmwareInfo record);
    
    List<FirmwareInfo> getVersion(FirmwareInfo firmwareInfo);
    
    FirmwareInfo getFirmwareInfoByVersion(FirmwareInfo record);
    
    int insertSelective(FirmwareInfo record);
}