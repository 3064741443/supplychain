package cn.com.glsx.supplychain.mapper.bs;


import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.AfterSaleOrder;
import cn.com.glsx.supplychain.model.bs.AfterSaleOrderDetail;
import cn.com.glsx.supplychain.model.bs.MaintainSnChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface AfterSaleOrderDetailMapper  extends OreMapper<AfterSaleOrderDetail> {

    int batchUpdateAfterSaleChangeSn(@Param("maintainSnChangeList")List<MaintainSnChange>maintainSnChangeList, @Param("logisticsId")Long logisticsId);

    int updateAfterSaleDetailLogisticsId(AfterSaleOrderDetail afterSaleOrderDetail);
}