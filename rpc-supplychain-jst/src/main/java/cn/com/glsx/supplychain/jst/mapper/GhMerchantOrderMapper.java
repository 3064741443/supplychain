package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.model.GhMerchantOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface GhMerchantOrderMapper extends OreMapper<GhMerchantOrder>{
   
	public Page<GhMerchantOrder> pageGhMerchantOrder(GhMerchantOrder record);

	//Page<SapGhMerchantOrderDTO> wxlistSapMerchantOrders(GhMerchantOrder record);

	Page<GhMerchantOrder> wxlistMerchantOrders(GhMerchantOrder record);
	
	public List<GhMerchantOrder> listGhMerchantOrder(GhMerchantOrder record);

	List<GhMerchantOrder> listGhMerchantOrderNoMerchantOrder(GhMerchantOrder record);
}