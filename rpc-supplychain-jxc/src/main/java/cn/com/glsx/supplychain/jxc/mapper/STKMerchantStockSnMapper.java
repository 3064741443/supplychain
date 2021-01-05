package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStockSn;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantStockSnMapper extends OreMapper<STKMerchantStockSn>{

    List<STKMerchantStockSnDTO> exportMerchantStockSn(STKMerchantStockSn record);
}