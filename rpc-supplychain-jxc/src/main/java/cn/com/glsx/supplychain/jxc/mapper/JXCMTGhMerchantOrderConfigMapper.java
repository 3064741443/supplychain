package cn.com.glsx.supplychain.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTGhMerchantOrderConfig;

@Mapper
public interface JXCMTGhMerchantOrderConfigMapper extends OreMapper<JXCMTGhMerchantOrderConfig>{
	
	public List<JXCMTGhMerchantOrderConfig> selectGhMerchantOrderConfig(JXCMTGhMerchantOrderConfig record);
	
	public List<JXCMTGhMerchantOrderConfig> listGhMerchantOrderConfig(JXCMTGhMerchantOrderConfig record);
   
}