package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.mapper.SplitOrderMapper;
import cn.com.glsx.supplychain.model.bs.Logistics;
import cn.com.glsx.supplychain.model.bs.SplitOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SplitOrderService {
	
	@Autowired
	SplitOrderMapper splitOrderMapper;
	
	public List<SplitOrder> getOrderList() {
		return splitOrderMapper.selectOrderList();
	}
	
	public List<Logistics> getLogisticsList() {
		return splitOrderMapper.selectLogisticsList();
	}
	
	
	public int saveOrderAndLogistics(List<SplitOrder> orderList, List<Logistics> logisticsList,Map orderMap, Map orderDetailmap) {
		splitOrderMapper.insertLogisticsBatch(logisticsList);
		splitOrderMapper.updateOrderBatch(orderMap);
		splitOrderMapper.insertOrderBatch(orderList);
		return splitOrderMapper.updateOrderDetailBatch(orderDetailmap);
	}
}
