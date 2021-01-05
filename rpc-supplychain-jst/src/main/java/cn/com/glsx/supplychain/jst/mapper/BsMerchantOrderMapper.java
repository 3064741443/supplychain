package cn.com.glsx.supplychain.jst.mapper;

import cn.com.glsx.supplychain.jst.model.BsMerchantOrder;
import cn.com.glsx.supplychain.jst.model.GhMerchantOrderReflectMcode;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface BsMerchantOrderMapper extends OreMapper<BsMerchantOrder>{
   
    int insert(BsMerchantOrder record);

    int insertSelective(BsMerchantOrder record);
 
    int updateByPrimaryKeySelective(BsMerchantOrder record);

    int updateByPrimaryKey(BsMerchantOrder record);
    
    List<BsMerchantOrder> pageMerchantOrder(BsMerchantOrder record);
    
    List<BsMerchantOrder> countDispatchOrderCode(List<String> orderCodes);

    List<GhMerchantOrderReflectMcode> listGhMerchantOrderReflectMcodeAndBsMerchantOrder();

    Integer batchUpdateGhMerchantOrder(List<GhMerchantOrderReflectMcode> ghMerchantOrderReflectMcodes);
}