package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.STKProductConfigDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantProductConfig;

@Mapper
public interface STKMerchantProductConfigMapper extends OreMapper<STKMerchantProductConfig>{

	Page<STKProductConfigDTO> pageMerchantProductConfig(STKMerchantProductConfig record);
}