package cn.com.glsx.supplychain.jxc.mapper;

import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnStatDTO;
import cn.com.glsx.supplychain.jxc.model.STKMerchantStockSnStat;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface STKMerchantStockSnStatMapper extends OreMapper<STKMerchantStockSnStat>{
    
	List<STKMerchantStockSnStatDTO> getMerchantStockSnStatByDeviceType(STKMerchantStockSnStat record);
	
	Page<STKMerchantStockSnStatDTO> pageMerchantStockSnStatByToMerchantCode(STKMerchantStockSnStat record);

	List<STKMerchantStockSnStatDTO> exportMerchantStockSnStatByToMerchantCode(STKMerchantStockSnStat record);
}