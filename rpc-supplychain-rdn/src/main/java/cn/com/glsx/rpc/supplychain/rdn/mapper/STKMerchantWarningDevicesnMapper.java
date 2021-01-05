package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantWarningDevicesn;
import cn.com.glsx.supplychain.jxc.dto.STKWarningDevicesnDTO;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantWarningDevicesnMapper extends OreMapper<STKMerchantWarningDevicesn>{

	List<STKWarningDevicesnDTO> exportWarningDevicesn(STKMerchantWarningDevicesn record);

}