package cn.com.glsx.supplychain.jxc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import com.github.pagehelper.Page;

import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStock;

import java.util.List;

@Mapper
public interface STKMerchantStockMapper extends OreMapper<STKMerchantStock>{

	public Page<STKMerchantStockDTO> pageMerchantStock(STKMerchantStock record);

	public List<STKMerchantStockDTO> exportMerchantStock(STKMerchantStock record);

}