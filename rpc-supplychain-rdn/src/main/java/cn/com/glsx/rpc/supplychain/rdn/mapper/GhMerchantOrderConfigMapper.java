package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.GhMerchantOrderConfig;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface GhMerchantOrderConfigMapper extends OreMapper<GhMerchantOrderConfig>{
	
	public List<GhMerchantOrderConfig> selectGhMerchantOrderConfig(GhMerchantOrderConfig record);
	
	public List<GhMerchantOrderConfig> listGhMerchantOrderConfig(GhMerchantOrderConfig record);
   
}