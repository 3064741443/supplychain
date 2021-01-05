package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.STKWarningSetDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningSet;

@Mapper
public interface STKMerchantWarningSetMapper extends OreMapper<STKMerchantWarningSet>{

	Page<STKWarningSetDTO> pageWarningSet(STKMerchantWarningSet record);
}