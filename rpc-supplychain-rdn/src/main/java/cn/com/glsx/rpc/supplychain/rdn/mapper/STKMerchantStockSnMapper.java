package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantStockSn;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnDTO;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantStockSnMapper extends OreMapper<STKMerchantStockSn>{

    List<STKMerchantStockSnDTO> exportMerchantStockSn(STKMerchantStockSn record);
}