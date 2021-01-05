package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.STKMerchantStock;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDTO;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantStockMapper extends OreMapper<STKMerchantStock> {

    public List<STKMerchantStockDTO> exportMerchantStock(STKMerchantStock record);

}