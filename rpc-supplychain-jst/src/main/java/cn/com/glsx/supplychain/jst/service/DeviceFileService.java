package cn.com.glsx.supplychain.jst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.jst.mapper.DeviceFileMapper;
import cn.com.glsx.supplychain.jst.model.DeviceFile;
import cn.com.glsx.supplychain.jst.model.DeviceSnNotInMerchantStock;

@Service
public class DeviceFileService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeviceFileMapper deviceFileMapper;
	
	public DeviceFile getDeviceFileBySn(String sn){
		DeviceFile condition = new DeviceFile();
		condition.setSn(sn);
		condition.setDeletedFlag("N");
		return deviceFileMapper.selectOne(condition);
	}
	
	public List<DeviceSnNotInMerchantStock> listSnNotInMerchantStock(List<String> listSn){
		if(null == listSn || listSn.isEmpty()){
			return null;
		}
		return deviceFileMapper.listSnNotInMerchantStock(listSn);
	}
}
