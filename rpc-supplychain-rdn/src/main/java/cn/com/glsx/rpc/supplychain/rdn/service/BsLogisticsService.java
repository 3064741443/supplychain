package cn.com.glsx.rpc.supplychain.rdn.service;

import cn.com.glsx.rpc.supplychain.rdn.mapper.BsLogisticsMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.Logistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class BsLogisticsService {
	private static final Logger logger = LoggerFactory.getLogger(BsLogisticsService.class);
	@Autowired
	private BsLogisticsMapper bsLogisticsMapper;
	
	public Integer insertListJxcmtBsLogistics(List<Logistics> listJxcmtBsLogistics){
		if(null == listJxcmtBsLogistics || listJxcmtBsLogistics.isEmpty()){
			return 0;
		}
		return bsLogisticsMapper.insertList(listJxcmtBsLogistics);
	}
	
	public List<Logistics> listBsLogisticsByBsMerchantOrderCodes(List<String> listMerchantOrderCodes){
		if(null == listMerchantOrderCodes || listMerchantOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(Logistics.class);
		example.createCriteria().andIn("serviceCode", listMerchantOrderCodes);
		return bsLogisticsMapper.selectByExample(example);
	}
	
	public Logistics getBsLogisticsByBsMerchantOrderCode(String merchantOrderCode){
		Logistics condition = new Logistics();
		condition.setServiceCode(merchantOrderCode);
		condition.setType((byte)1);
		return bsLogisticsMapper.selectOne(condition);
	}
	
	public List<Logistics> listBsLogisticsByDispatchOrderCodes(List<String> listDispatchOrderCodes,byte logisticsType){
		if(null == listDispatchOrderCodes || listDispatchOrderCodes.isEmpty()){
			return null;
		}
		Example example = new Example(Logistics.class);
		example.createCriteria().andIn("serviceCode", listDispatchOrderCodes)
								.andEqualTo("type", logisticsType);
		return bsLogisticsMapper.selectByExample(example);
	}
	
	public List<Logistics> getBsLogisticsByDispatchOrderCode(String dispatchOrderCode,byte logisticsType){
		Example example = new Example(Logistics.class);
		example.createCriteria().andEqualTo("serviceCode", dispatchOrderCode)
								.andEqualTo("type",5)
								.andEqualTo("deletedFlag", "N");
		return bsLogisticsMapper.selectByExample(example);
	}
	
	public Integer updateBsLogisticsSeletive(Logistics logistics){
		return bsLogisticsMapper.updateByPrimaryKeySelective(logistics);
	}

	public List<Logistics> listLogisticsById(List<String> tranOrderCodes){
		if(null == tranOrderCodes || tranOrderCodes.isEmpty()){
			return null;
		}
		Example example=new Example(Logistics.class);
		example.createCriteria().andIn("serviceCode",tranOrderCodes)
				.andEqualTo("type",8)
				.andEqualTo("deletedFlag","N");
		return bsLogisticsMapper.selectByExample(example);
	}


	public Map<String,Logistics> listMapLogisticsById(List<String> tranOrderCodes){
		Map<String,Logistics> mapResult = null;
		List<Logistics> listLogistics = listLogisticsById(tranOrderCodes);
		if(listLogistics==null || listLogistics.size()==0){
			mapResult= new HashMap<>();
			return  mapResult;
		}
		mapResult = listLogistics.stream().collect(Collectors.toMap(Logistics::getServiceCode, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}
	
}
