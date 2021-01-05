package cn.com.glsx.supplychain.mapper;

import cn.com.glsx.supplychain.model.bs.Logistics;
import cn.com.glsx.supplychain.model.bs.SplitOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SplitOrderMapper {
	
	List<SplitOrder> selectOrderList();
	
	List<Logistics> selectLogisticsList();
	
	int insertOrderBatch(List<SplitOrder> list);
	int insertLogisticsBatch(List<Logistics> list);
	int updateOrderDetailBatch(Map map);
	int updateOrderBatch(Map map);
}