package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jxc.dto.JXCMTCheckImportDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTOderDeviceDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceInfoImport;
import cn.com.glsx.supplychain.jxc.service.JXCMTDeviceFileService;
import cn.com.glsx.supplychain.jxc.service.JXCMTDeviceService;
import cn.com.glsx.supplychain.jxc.vo.CheckImportDataVo;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("JxcDeviceRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class JxcDeviceRemoteImpl implements JxcDeviceRemote{

	private static final Logger logger = LoggerFactory.getLogger(JxcDeviceRemoteImpl.class);
	@Autowired
	private JXCMTDeviceService jxcmtDeviceService;
	@Autowired
	private JXCMTDeviceFileService deviceFileService;
	@Override
	public RpcResponse<Integer> dispatchDeviceDispatch(JXCMTOderDeviceDTO record) {
		logger.info("JxcDeviceRemoteImpl::dispatchDeviceDispatch record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsCpy(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsCpy() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNo() must not be null");
			RpcAssert.assertNotNull(record.getUserName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNo() must not be null");
			RpcAssert.assertNotNull(record.getListDeviceInfos(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDeviceInfos() must not be null");
			return RpcResponse.success(jxcmtDeviceService.dispatchDeviceDispatch(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> cancelDispatchDevice(JXCMTOderDeviceDTO record) {
		logger.info("JxcDeviceRemoteImpl::cancelDispatchDevice record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getUserName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getUserName must not be null");
			RpcAssert.assertNotNull(record.getListDeviceInfos(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDeviceInfos must not be null");
			return RpcResponse.success(jxcmtDeviceService.cancelDispatchDevice(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<JXCMTCheckImportDTO> checkBatchDispatchDevice(
			JXCMTOderDeviceDTO record) {
		logger.info("JxcDeviceRemoteImpl::checkBatchDispatchDevice record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getUserName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getUserName() must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode() must not be null");
		//	RpcAssert.assertNotNull(record.getLogisticsCpy(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsCpy() must not be null");
		//	RpcAssert.assertNotNull(record.getLogisticsNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNo() must not be null");
			RpcAssert.assertNotNull(record.getListDeviceInfos(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDeviceInfos must not be null");
			return RpcResponse.success(jxcmtDeviceService.checkBatchDispatchDevice(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<JXCMTCheckImportDTO> importBatchDispatchDevice(
			JXCMTOderDeviceDTO record) {
		logger.info("JxcDeviceRemoteImpl::checkBatchDispatchDevice record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getUserName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getUserName() must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsCpy(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsCpy() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNo() must not be null");
			RpcAssert.assertNotNull(record.getListDeviceInfos(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDeviceInfos must not be null");
			return RpcResponse.success(jxcmtDeviceService.importBatchDispatchDevice(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	@Override
	public RpcResponse<Integer> deliveryDirect(JXCMTOderDeviceDTO record) {
		logger.info("JxcDeviceRemoteImpl::deliveryDirect record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getUserName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getUserName() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsCpy(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsCpy() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsNo(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsNo() must not be null");
			RpcAssert.assertNotNull(record.getSendQulities(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSendQulities() must not be null");
			RpcAssert.assertNotNull(record.getDispatchOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDispatchOrderCode() must not be null");
			return RpcResponse.success(jxcmtDeviceService.deliveryDirect(record));
		}catch(RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<CheckImportDataVo> checkImportDeviceList(List<JXCMTDeviceInfoImport> importList) {
		logger.info("StatementSellAdminRemoteImpl::CheckStatementSellRenewImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "record must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(),DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(jxcmtDeviceService.checkImportDeviceList(importList));
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> importDeviceInfoList(String userName,List<JXCMTDeviceInfoImport> importList) {
		logger.info("StatementSellAdminRemoteImpl::ImportStatementSellRenewImportData start importList.size:",importList.size());
		try{
			RpcAssert.assertNotNull(importList, DefaultErrorEnum.PARAMETER_NULL, "importList must not be null");
			RpcAssert.assertIsTrue(!importList.isEmpty(), DefaultErrorEnum.DATA_INVALID,"importList must not empty");
			return RpcResponse.success(jxcmtDeviceService.importDeviceInfoList(userName,importList));
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<JXCMTDeviceFileDTO>> exportDeviceFile(JXCMTDeviceFileDTO deviceFileDTO) {
		logger.info("JxcDeviceRemoteImpl::exportDeviceFile start deviceFileDTO:",deviceFileDTO);
		try{
			RpcAssert.assertNotNull(deviceFileDTO, DefaultErrorEnum.PARAMETER_NULL, "deviceFileDTO must not be null");
			return RpcResponse.success(deviceFileService.exportDeviceFile(deviceFileDTO));
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

}
