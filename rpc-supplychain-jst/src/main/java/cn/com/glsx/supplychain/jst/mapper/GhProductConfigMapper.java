package cn.com.glsx.supplychain.jst.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jst.model.GhProductConfig;

@Mapper
public interface GhProductConfigMapper extends OreMapper<GhProductConfig>{
  
	public Page<GhProductConfig> pageProductConfigBySubBrands(List<Integer> records);
	
	public Page<GhProductConfig> pageProductConfigByParentBrands(List<Integer> records);
	
	public Page<GhProductConfig> pageProductConfig();
	
	public Page<GhProductConfig> pageGhProductConfigWithDistinctProductCode(GhProductConfig record);
	
	public Page<GhProductConfig> pageGhProductConfigWithDistinctProductCodeWithMerchantCode(GhProductConfig record);
	
	public List<GhProductConfig> listGhProductConfigModeYears(GhProductConfig record);
	
	public List<GhProductConfig> listGhProductConfigModeYearsWithMerchantCode(GhProductConfig record);
	
	public List<GhProductConfig> listGhProductConfigAudis(GhProductConfig record);
	
	public List<GhProductConfig> listGhProductConfigAudisWithMerchantCode(GhProductConfig record);
	
	public Page<GhProductConfig> pageGhProductConfigMotorcycle(GhProductConfig record);
	
	public Page<GhProductConfig> pageGhProductConfigMotorcycleWithMerchantCode(GhProductConfig record);
	
	public List<GhProductConfig> listDistinctParentBrands();
	
	public List<GhProductConfig> listDistinctSubBrands(GhProductConfig record);
	
	public List<GhProductConfig> listDistinctAudis(GhProductConfig record);
	
	public List<GhProductConfig> listDistinctMotorcyles(GhProductConfig record);
}