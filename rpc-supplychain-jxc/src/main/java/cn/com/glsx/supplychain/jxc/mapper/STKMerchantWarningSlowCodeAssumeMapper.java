package cn.com.glsx.supplychain.jxc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import cn.com.glsx.supplychain.jxc.dto.STKWarningDevTypeDetailDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningSlowCodeAssume;

@Mapper
public interface STKMerchantWarningSlowCodeAssumeMapper extends OreMapper<STKMerchantWarningSlowCodeAssume>{

	Integer sumWarningDeviceTotal();
	
	List<STKWarningDevTypeDetailDTO> groupWarningDeviceTotalByDeviceType();
}