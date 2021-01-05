package cn.com.glsx.supplychain.mapper.bs;

import cn.com.glsx.supplychain.model.bs.MerchantOrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface MerchantOrderDetailMapper extends OreMapper<MerchantOrderDetail> {

    Integer updateById(MerchantOrderDetail merchantOrderDetail);

    List<MerchantOrderDetail> getMerchantOrderDetailListByDispatchOrderNumberList(List<MerchantOrderDetail> merchantOrderDetailList);
}