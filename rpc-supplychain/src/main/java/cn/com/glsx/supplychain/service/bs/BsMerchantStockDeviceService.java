package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockDeviceMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockDevice;
import cn.com.glsx.supplychain.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
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
public class BsMerchantStockDeviceService {
	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockDeviceService.class);
	
	@Autowired
	private BsMerchantStockDeviceMapper merchantStockMapper;

	
	public List<BsMerchantStockDevice> listMerchantStockDeviceBySn(List<String> listSn)
	{
		logger.info("BsMerchantStockDeviceService::listMerchantStockDeviceBySn listSn :{}", JSONObject.toJSON(listSn));
		Example example = new Example(BsMerchantStockDevice.class);
		example.createCriteria().andIn("sn", listSn)
								.andEqualTo("deletedFlag","N");
		return merchantStockMapper.selectByExample(example);
	}

	public Map<String,BsMerchantStockDevice> MapMerchantStockDeviceBySn(List<String> listSn){
		if(StringUtil.isEmpty(listSn)||listSn.size()==0){
			return new HashMap<>();
		}
		Map<String,BsMerchantStockDevice> mapResult=null;
		List<BsMerchantStockDevice> bsMerchantStockDevices=listMerchantStockDeviceBySn(listSn);
		if(bsMerchantStockDevices==null || bsMerchantStockDevices.size()==0){
			mapResult= new HashMap<>();
			return  mapResult;
		}
		mapResult = bsMerchantStockDevices.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, a->a));
		if(null == mapResult){
			mapResult = new HashMap<>();
		}
		return mapResult;
	}
}
