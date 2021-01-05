package cn.com.glsx.supplychain.jst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.supplychain.jst.mapper.BsMerchantStockDeviceMapper;
import cn.com.glsx.supplychain.jst.model.BsMerchantStockDevice;

@Service
public class BsMerchantStockDeviceService {
	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockDeviceService.class);
	
	@Autowired
	private BsMerchantStockDeviceMapper merchantStockMapper;
	
	public Integer batchAddMerchantStockDevice(List<BsMerchantStockDevice> listStockDevice)
	{
		if(null == listStockDevice || listStockDevice.isEmpty()){
			return 0;
		}
		return merchantStockMapper.insertList(listStockDevice);
	}
	
	public List<BsMerchantStockDevice> listMerchantStockDevice(List<String> listSn, String merchantCode)
	{
		Example example = new Example(BsMerchantStockDevice.class);
		example.createCriteria().andIn("sn", listSn)
								.andEqualTo("merchantCode", merchantCode)
								.andEqualTo("deletedFlag","N");
		return merchantStockMapper.selectByExample(example);
	}
	
	public List<BsMerchantStockDevice> listMerchantStockDeviceBySn(List<String> listSn)
	{
		Example example = new Example(BsMerchantStockDevice.class);
		example.createCriteria().andIn("sn", listSn)
								.andEqualTo("deletedFlag","N");
		return merchantStockMapper.selectByExample(example);
	}
}
