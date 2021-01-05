package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantWarningWaresale;
import cn.com.glsx.supplychain.jxc.dto.STKWarningWaresaleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantWarningWaresaleMapper extends OreMapper<STKMerchantWarningWaresale>{

	List<STKWarningWaresaleDTO> exportWarningWaresale(STKMerchantWarningWaresale record);

}