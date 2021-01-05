package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.service.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("StkMerchantStockRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class StkMerchantStockRemoteImpl
		implements StkMerchantStockRemote{
	private static final Logger logger = LoggerFactory.getLogger(StkMerchantStockRemote.class);
	@Autowired
	private STKMerchantStockService stkMerchantStockService;
	@Autowired
	private STKMerchantStockDeductService stkMerchantStockDeductService;
	@Autowired
	private STKWarningSetService stkWarningSetService;
	@Autowired
	private STKProductConfigService stkProductConfigService;
	@Autowired
	private STKWarningService stlWarningService;
	@Autowired
	private STKMerchantStockSnService stlStockSnService;

	@Override
	public RpcResponse<RpcPagination<STKMerchantStockDTO>> pageMerchantStock(
			RpcPagination<STKMerchantStockDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageMerchantStock pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stkMerchantStockService.pageMerchantStock(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKMerchantStockDTO>> exportMerchantStock(STKMerchantStockDTO record) {
		logger.info("JxcOrderRemoteImpl::exportMerchantStock record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			return RpcResponse.success(stkMerchantStockService.exportMerchantStock(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<STKMerchantStockDeductionDTO> importMerchantStockDeductionCheck(
			STKMerchantStockDeductionDTO record) {
		logger.info("JxcOrderRemoteImpl::importMerchantStockDeductionCheck record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getTradeType(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTradeType must not be null");
			RpcAssert.assertNotNull(record.getListDetailDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto() must not be null");
			RpcAssert.assertIsTrue(!record.getListDetailDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto() must not empty");
			return RpcResponse.success(stkMerchantStockDeductService.importMerchantStockDeductionCheck(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<Integer> importMerchantStockDeduction(
			STKMerchantStockDeductionDTO record) {
		logger.info("JxcOrderRemoteImpl::importMerchantStockDeduction record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getTradeType(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTradeType must not be null");
			RpcAssert.assertNotNull(record.getConsumer(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getConsumer must not be null");
			RpcAssert.assertNotNull(record.getListDetailDto(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto() must not be null");
			RpcAssert.assertIsTrue(!record.getListDetailDto().isEmpty(), JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto() must not empty");
			return RpcResponse.success(stkMerchantStockDeductService.importMerchantStockDeduction(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<STKWarningSetDTO>> pageMerchantWarningSet(
			RpcPagination<STKWarningSetDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageMerchantWarningSet pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stkWarningSetService.pageWarningSet(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<Integer> subMerchantWarningSet(STKWarningSetDTO record) {
		logger.info("JxcOrderRemoteImpl::subMerchantWarningSet record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getConsumer(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getConsumer() must not be null");
			RpcAssert.assertNotNull(record.getWarningType(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getWarningType() must not be null");
			RpcAssert.assertNotNull(record.getChannelType(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getChannelType() must not be null");
			RpcAssert.assertNotNull(record.getDeviceType(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeviceType() must not be null");
			return RpcResponse.success(stkWarningSetService.subMerchantWarningSet(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> updateMerchantWarningSet(STKWarningSetDTO record) {
		logger.info("JxcOrderRemoteImpl::updateMerchantWarningSet record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getConsumer(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getConsumer() must not be null");
			RpcAssert.assertNotNull(record.getWarningCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getWarningCode() must not be null");
			return RpcResponse.success(stkWarningSetService.updateMerchantWarningSet(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<STKWarningWaresaleDTO>> pageMerchantWarningWaresale(
			RpcPagination<STKWarningWaresaleDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageMerchantWarningWaresale pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stlWarningService.pageWarningWaresale(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKWarningWaresaleDTO>> exportMerchantWarningWaresale(STKWarningWaresaleDTO record) {
		logger.info("JxcOrderRemoteImpl::exportMerchantWarningWaresale record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(stlWarningService.exportWarningWaresale(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<STKWarningDevTypeAssumeDTO> getMerchantWarningDeviceTypeAssume(
			JXCMTBaseDTO record) {
		logger.info("JxcOrderRemoteImpl::getMerchantWarningDeviceTypeAssume record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(stlWarningService.getMerchantWarningDeviceTypeAssume());
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<STKWarningMaterialAssumeDTO>> pageMerchantWarningMaterialAssume(
			RpcPagination<STKWarningMaterialAssumeDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::getMerchantWarningDeviceTypeAssume pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stlWarningService.pageMerchantWarningMaterialAssume(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<STKWarningDevicesnDTO>> pageMerchantWarningDeviceSn(
			RpcPagination<STKWarningDevicesnDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageMerchantWarningDeviceSn pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stlWarningService.pageMerchantWarningDeviceSn(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKWarningDevicesnDTO>> exportMerchantWarningDeviceSn(STKWarningDevicesnDTO warningDevicesnDTO) {
		logger.info("JxcOrderRemoteImpl::exportMerchantWarningDeviceSn pagination::{}",warningDevicesnDTO);
		try{
			RpcAssert.assertNotNull(warningDevicesnDTO,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"warningDevicesnDTO must not be null");
			return RpcResponse.success(stlWarningService.exportMerchantWarningDeviceSn(warningDevicesnDTO));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKMerchantStockSnStatDTO>> getMerchantStockSnStatByDeviceType(
			STKMerchantStockSnStatDTO record) {
		logger.info("JxcOrderRemoteImpl::getMerchantStockSnStatByDeviceType record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getUnActiveDays(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getUnActiveDays() must not be null");
			return RpcResponse.success(stlStockSnService.getMerchantStockSnStatByDeviceType(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<STKMerchantStockSnStatDTO>> pageMerchantStockSnStatByToMerchantCode(
			RpcPagination<STKMerchantStockSnStatDTO> pagination) {
		logger.info("JxcOrderRemoteImpl::pageMerchantStockSnStatByToMerchantCode pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getUnActiveDays(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getCondition().getUnActiveDays() must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stlStockSnService.pageMerchantStockSnStatByToMerchantCode(pagination)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKMerchantStockSnStatDTO>> exportMerchantStockSnStatByToMerchantCode(
			STKMerchantStockSnStatDTO record) {
		logger.info("JxcOrderRemoteImpl::exportMerchantStockSnStatByToMerchantCode record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(stlStockSnService.exportMerchantStockSnStatByToMerchantCode(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKMerchantStockSnDTO>> exportMerchantStockSn(STKMerchantStockSnDTO record) {
		logger.info("JxcOrderRemoteImpl::exportMerchantStockSnStatByToMerchantCode record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(stlStockSnService.exportMerchantStockSn(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<RpcPagination<STKProductConfigDTO>> pageMerchantProductConfig(
			STKProductConfigQueryDTO record) {
		logger.info("JxcOrderRemoteImpl::pageMerchantProductConfig record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getPageNum(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageNum() must not be null");
			RpcAssert.assertNotNull(record.getPageSize(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageSize() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(stkProductConfigService.pageMerchantProductConfig(record)));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<List<STKProductConfigDTO>> getMerchantProductConfigByOperatorCode(
			STKProductConfigDTO record) {
		logger.info("JxcOrderRemoteImpl::getMerchantProductConfigByOperatorCode record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOperateCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOperateCode() must not be null");
			return RpcResponse.success(stkProductConfigService.getMerchantProductConfigByOperatorCode(record));
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
		}
	}

	@Override
	public RpcResponse<Integer> addMerchantProductConfig(
			STKProductConfigSubmitDTO record) {
		logger.info("JxcOrderRemoteImpl::addMerchantProductConfig record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListProductConfig(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListProductConfig() must not be null");
			RpcAssert.assertNotNull(record.getConsumer(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getConsumer() must not be null");
			return RpcResponse.success(stkProductConfigService.addMerchantProductConfig(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> updateMerchantProductConfig(
			STKProductConfigSubmitDTO record){
		logger.info("JxcOrderRemoteImpl::updateMerchantProductConfig record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOperateCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getConsumer(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getConsumer() must not be null");
			return RpcResponse.success(stkProductConfigService.updateMerchantProductConfig(record));
		}catch (RpcServiceException e){
			logger.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
}
