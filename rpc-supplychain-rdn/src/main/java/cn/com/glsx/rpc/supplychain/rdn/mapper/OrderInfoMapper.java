package cn.com.glsx.rpc.supplychain.rdn.mapper;

import cn.com.glsx.rpc.supplychain.rdn.model.pojo.OrderInfo;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOrderInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.oreframework.datasource.mybatis.mapper.OreMapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper extends OreMapper<OrderInfo>{

	public List<JXCMTOrderInfoDTO> exportOrderInfo(OrderInfo record);

}