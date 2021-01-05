package cn.com.glsx.supplychain.jst.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.glsx.supplychain.jst.mapper.deviceMerchantReflectMapper;
import cn.com.glsx.supplychain.jst.model.deviceMerchantReflect;

@Service
public class DeviceMerchantReflectService {

	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	 @Autowired
	 private deviceMerchantReflectMapper merchantReflectMapper;
	 
	 public deviceMerchantReflect getMerchantReflectBySendToMerchant(String sendToMerchantCode){
		 deviceMerchantReflect condition = new deviceMerchantReflect();
		 condition.setSendToMerchantCode(sendToMerchantCode);
		 condition.setDeletedFlag("N");
		 return merchantReflectMapper.selectOne(condition);
	 }
}
