package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.model.JXCMTBsAddress;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface JXCMTBsAddressMapper extends OreMapper<JXCMTBsAddress>{
    
	public int insertAddress(JXCMTBsAddress address);
	
	public List<JXCMTBsAddress> listMerchantOrderAddress(JXCMTBsAddress address);

	public List<JXCMTBsAddress> listServiceProviderAddress(JXCMTBsAddress address);

}