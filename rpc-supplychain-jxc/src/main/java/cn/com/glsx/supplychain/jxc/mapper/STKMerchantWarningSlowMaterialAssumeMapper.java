package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.STKWarningMaterialAssumeDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningSlowMaterialAssume;

@Mapper
public interface STKMerchantWarningSlowMaterialAssumeMapper extends OreMapper<STKMerchantWarningSlowMaterialAssume>{

	Page<STKWarningMaterialAssumeDTO> pageMerchantWarningMaterialAssume(STKMerchantWarningSlowMaterialAssume record);
}