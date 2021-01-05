package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.BsMerchantOrderMapper;
import cn.com.glsx.rpc.supplychain.rdn.mapper.BsMerchantOrderVehicleMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.BsMerchantOrderVehicle;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.MerchantOrder;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsMerchantOrderBspVo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsMerchantOrderBssVo;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.BsMerchantOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BsMerchantOrderService {

	@Autowired
	private BsMerchantOrderMapper bsMerchantOrderMapper;
	@Autowired
	private BsMerchantOrderVehicleMapper bsMerchantOrderVehicleMapper;
	
	public List<BsMerchantOrderVo> exportBsMerchantOrderJXC(MerchantOrder merchantOrder){
		return bsMerchantOrderMapper.exportBsMerchantOrderJXC(merchantOrder);
	}
	
	public List<BsMerchantOrderBssVo> exportBsMerchantOrderBSS(MerchantOrder merchantOrder){
		return bsMerchantOrderMapper.exportBsMerchantOrderBSS(merchantOrder);
	}
	
	public List<BsMerchantOrderBspVo> exportBsMerchantOrderBSP(MerchantOrder merchantOrder){
		return bsMerchantOrderMapper.exportBsMerchantOrderBSP(merchantOrder);
	}
	
	public List<String> listBsMerchantOrderByDispatchOrderLike(String dispatchOrderLike){
		if(StringUtils.isEmpty(dispatchOrderLike)){
			return null;
		}
		Example example = new Example(BsMerchantOrderVehicle.class);
		example.createCriteria().andLike("dispatchOrderCode", "%"+dispatchOrderLike+"%")			
								.andEqualTo("deletedFlag", "N");
		List<BsMerchantOrderVehicle> listVehicle = bsMerchantOrderVehicleMapper.selectByExample(example);
		if(null == listVehicle || listVehicle.isEmpty()){
			return null;
		}
		return listVehicle.stream().map(BsMerchantOrderVehicle::getMerchantOrder).collect(Collectors.toList());	
	}
	
	
}
