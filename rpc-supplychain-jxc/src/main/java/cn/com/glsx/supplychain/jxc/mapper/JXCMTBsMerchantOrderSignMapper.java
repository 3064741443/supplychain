package cn.com.glsx.supplychain.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTBsMerchantOrderSign;

@Mapper
public interface JXCMTBsMerchantOrderSignMapper extends OreMapper<JXCMTBsMerchantOrderSign>{
    
	public Integer insertBsMerchantOrderSignOnDupliteKey(List<JXCMTBsMerchantOrderSign> listSigns);
}