package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.STKWarningDevicesnDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantWarningDevicesn;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantWarningDevicesnMapper extends OreMapper<STKMerchantWarningDevicesn>{
   
	Page<STKWarningDevicesnDTO> pageWarningDevicesn(STKMerchantWarningDevicesn record);

	List<STKWarningDevicesnDTO> exportWarningDevicesn(STKMerchantWarningDevicesn record);
}