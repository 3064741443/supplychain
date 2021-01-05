package cn.com.glsx.supplychain.jxc.remote;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantMaterialCheckDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitListQueryDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.service.JXCMTMaterialInfoService;
import cn.com.glsx.supplychain.jxc.service.JXCMTProductSplitService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author ql
 * @version V1.0
 * @Title: JxcCommonRemote.java
 * @Description: 进销存重构地址 类型等公共服务接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Component("JxcProductRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class JxcProductRemoteImpl implements JxcProductRemote{

	private static final Logger logger = LoggerFactory.getLogger(JxcProductRemoteImpl.class);
	@Autowired
	private JXCMTProductSplitService jxcmtProductSplitService;
	@Autowired
	private JXCMTMaterialInfoService jxcmtMaterialInfoService;
	
	@Override
	public RpcResponse<List<JXCMTProductSplitDTO>> listJxcProduct(
			JXCMTProductSplitListQueryDTO record) {
		logger.info("JxcCommonRemoteImpl::listJxcProduct record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			return RpcResponse.success(jxcmtProductSplitService.listJxcProduct(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}
	@Override
	public RpcResponse<List<JXCMTBsMerchantMaterialCheckDTO>> listMaterialCheck(
			JXCMTBsMerchantMaterialCheckDTO record) {
		logger.info("JxcCommonRemoteImpl::JXCMTBsMerchantMaterialCheckDTO record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOrderMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(jxcmtMaterialInfoService.listMaterialCheck(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

}
