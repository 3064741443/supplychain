package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface MerchantOrderMapper extends OreMapper<MerchantOrder> {

    Page<MerchantOrder> listMerchantOrder(MerchantOrder merchantOrder);

    Integer updateByOrderNumber(MerchantOrder merchantOrder);

    List<MerchantOrder> getMerchantOrderListByDate(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    List<MerchantOrder> exportMerchantOrderExit(MerchantOrder merchantOrder);
    
    List<MerchantOrder> getMerchantOrderListForSignOrder(List<MerchantOrder> list);
}