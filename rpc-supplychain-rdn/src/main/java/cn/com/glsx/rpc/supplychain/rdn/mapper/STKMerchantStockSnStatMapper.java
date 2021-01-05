package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantStockSnStat;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnStatDTO;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantStockSnStatMapper extends OreMapper<STKMerchantStockSnStat>{

	List<STKMerchantStockSnStatDTO> exportMerchantStockSnStatByToMerchantCode(STKMerchantStockSnStat record);

}