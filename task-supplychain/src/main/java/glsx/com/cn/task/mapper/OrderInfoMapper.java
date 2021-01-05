package glsx.com.cn.task.mapper;

import glsx.com.cn.task.model.OrderInfo;

import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper extends OreMapper<OrderInfo>{
	
	OrderInfo getOrderInfoByOrderCode(String orderCode);

	Integer getShipmentsQuantityByOrderCode(String orderCode);
}
