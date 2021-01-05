package cn.com.glsx.supplychain.jst.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jst.convert.AttribInfoRpcConvert;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.dto.gh.*;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.service.AttribInfoService;
import cn.com.glsx.supplychain.jst.service.GhMerchantOrderService;
import cn.com.glsx.supplychain.jst.service.GhProductConfigService;
import cn.com.glsx.supplychain.jst.service.GhShoppingCartService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName MotorcycleRemoteImpl
 * @Description 广汇18家根据车型下单
 * @Author xiex
 * @Date 2020/2/13 21:55
 * @Version 1.0
 */
@Component("MotorcycleRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class MotorcycleRemoteImpl implements MotorcycleRemote{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WxBsMerchantRemoteImpl.class);
	@Autowired
	private AttribInfoService attribInfoService;
	@Autowired
	private GhProductConfigService productConfigService;
	@Autowired
	private GhShoppingCartService shoppingCartService;
	@Autowired
	private GhMerchantOrderService merchantOrderService;
	
	@Override
	public RpcResponse<List<AttribInfoDTO>> listParentBrands(
			AttribInfoDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listParentBrands record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getType(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getType() must not be null");
			return RpcResponse.success(AttribInfoRpcConvert.convertBeanList(productConfigService.listParentBrands()));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<List<AttribInfoDTO>> listSubBrands(
			GhProductConfigDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listSubBrands record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getParentBrandId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getParentBrandId() must not be null");
			return RpcResponse.success(AttribInfoRpcConvert.convertBeanList(productConfigService.listSubBrands(record)));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<List<AttribInfoDTO>> listAudis(GhProductConfigDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listAudis record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getParentBrandId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getParentBrandId() must not be null");
			RpcAssert.assertNotNull(record.getSubBrandId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSubBrandId() must not be null");
			return RpcResponse.success(AttribInfoRpcConvert.convertBeanList(productConfigService.listAudis(record)));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<List<AttribInfoDTO>> listMotorcyle(
			GhProductConfigDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listMotorcyle record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getParentBrandId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getParentBrandId() must not be null");
			RpcAssert.assertNotNull(record.getSubBrandId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getSubBrandId() must not be null");
			RpcAssert.assertNotNull(record.getAudiId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAudiId() must not be null");
			return RpcResponse.success(AttribInfoRpcConvert.convertBeanList(productConfigService.listMotorcles(record)));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<List<AttribInfoDTO>> listAttribInfo(AttribInfoDTO record) {		
		LOGGER.info("MotorcycleRemoteImpl::listAttribInfo record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getType(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getType() must not be null");
			return RpcResponse.success(AttribInfoRpcConvert.convertBeanList(attribInfoService.listAttribInfo(AttribInfoRpcConvert.convertDto(record))));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<RpcPagination<AudiMotorcycleDTO>> pageAudiMotorcycle(
			RpcPagination<AudiMotorcycleDTO> pagination) {
		LOGGER.info("MotorcycleRemoteImpl::pageAudiMotorcycle pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMerchantCode() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(productConfigService.pageAudiMotorcycle(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<RpcPagination<GhProductConfigDTO>> pageGhProductConfig(
			RpcPagination<GhProductConfigDTO> pagination) {
		LOGGER.info("MotorcycleRemoteImpl::pageGhProductConfig pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMerchantCode() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(productConfigService.pageGhProductConfig(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<List<AttribInfoDTO>> listGhProductConfigModeYears(
			GhProductConfigDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listGhProductConfigModeYears record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
		//	RpcAssert.assertNotNull(record.getGlsxProductCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getGlsxProductCode() must not be null");
			return RpcResponse.success(productConfigService.listGhProductConfigModeYears(record));
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}
	
	@Override
	public RpcResponse<List<AttribInfoDTO>> listGhProductConfigAudis(
			GhProductConfigDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listGhProductConfigAudis record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
		//	RpcAssert.assertNotNull(record.getGlsxProductCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getGlsxProductCode() must not be null");
			return RpcResponse.success(productConfigService.listGhProductConfigAudis(record));
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<RpcPagination<GhProductConfigDTO>> pageGhProductConfigMotorcycle(
			RpcPagination<GhProductConfigDTO> pagination) {
		LOGGER.info("MotorcycleRemoteImpl::pageGhProductConfigMotorcycle pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
		//	RpcAssert.assertNotNull(pagination.getCondition().getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMerchantCode() must not be null");
		//	RpcAssert.assertNotNull(pagination.getCondition().getGlsxProductCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getGlsxProductCode() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(productConfigService.pageGhProductConfigMotorcycle(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<List<GhProductConfigDTO>> exportGhProductConfigMotorcycle(GhProductConfigDTO ghProductConfigDTO) {
		LOGGER.info("MotorcycleRemoteImpl::exportGhProductConfigMotorcycle pagination::{}",ghProductConfigDTO);
        RpcAssert.assertNotNull(ghProductConfigDTO,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"ghProductConfigDTO must not be null");
		try {
			return RpcResponse.success(productConfigService.exportGhProductConfigMotorcycle(ghProductConfigDTO));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<GhProductConfigOtherDTO>> listGhProductConfigOther(
			GhProductConfigDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::listGhProductConfigOther record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getProductConfigCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getProductConfigCode() must not be null");
			return RpcResponse.success(productConfigService.listGhProductConfigOther(record));
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}	
	}

	@Override
	public RpcResponse<Integer> addGhProductConfigCart(
			GhShoppingCartDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::addGhProductConfigCart record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getTotal(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTotal() must not be null");
			RpcAssert.assertNotNull(record.getProductConfigCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getProductConfigCode() must not be null");
			return RpcResponse.success(shoppingCartService.addGhProductConfigCart(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}
	
	@Override
	public RpcResponse<Integer> batchAddGhProductConfigCart(
			List<GhShoppingCartDTO> listRecord) {
		LOGGER.info("MotorcycleRemoteImpl::batchAddGhProductConfigCart listRecord::{}",listRecord);
		try{
			RpcAssert.assertNotNull(listRecord,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(!listRecord.isEmpty(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"listRecord must have value");
			return RpcResponse.success(shoppingCartService.batchAddGhProductConfigCart(listRecord));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> delGhProductConfigCart(
			List<GhShoppingCartDTO> listRecord) {
		LOGGER.info("MotorcycleRemoteImpl::delGhProductConfigCart listRecord::{}",listRecord);
		try{
			RpcAssert.assertNotNull(listRecord,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(!listRecord.isEmpty(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"listRecord must have value");
			return RpcResponse.success(shoppingCartService.delGhProductConfigCart(listRecord));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<Integer> updateGhProductConfigCart(
			GhShoppingCartDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::updateGhProductConfigCart record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getCartCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(shoppingCartService.updateGhProductConfigCart(record));
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<RpcPagination<GhShoppingCartDTO>> pageGhShoppingCart(
			RpcPagination<GhShoppingCartDTO> pagination) {
		LOGGER.info("MotorcycleRemoteImpl::pageGhShoppingCart pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition().getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition().getMerchantCode() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(shoppingCartService.pageGhShoppingCart(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> subGhMerchantOrder(GhSubMerchantOrderDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::pageGhShoppingCart record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getAddressId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressId() must not be null");
			RpcAssert.assertNotNull(record.getListShoppingCartCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListShoppingCartCode() must not be null");
			RpcAssert.assertIsTrue(!record.getListShoppingCartCode().isEmpty(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListShoppingCartCode() must not be empty");
			return RpcResponse.success(merchantOrderService.subGhMerchantOrder(record));
		}catch (RpcServiceException e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}		
	}

	@Override
	public RpcResponse<RpcPagination<GhMerchantOrderDTO>> pageGhMerchantOrder(
			RpcPagination<GhMerchantOrderDTO> pagination) {
		LOGGER.info("MotorcycleRemoteImpl::pageGhMerchantOrder pagination::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(merchantOrderService.pageGhMerchantOrder(pagination.getPageNum(), pagination.getPageSize(), pagination.getCondition())));
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}
	
	@Override
	public RpcResponse<List<GhMerchantOrderDTO>> exportGhMerchantOrder(
			GhMerchantOrderDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::exportGhMerchantOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			return RpcResponse.success(merchantOrderService.exportGhMerchantOrder(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> addShoppingCartFromMerchantOrder(
			GhMerchantOrderDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::pageGhMerchantOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getGhMerchantOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getGhMerchantOrderCode() must not be null");
			RpcAssert.assertNotNull(record.getTotal(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTotal() must not be null");
			RpcAssert.assertNotNull(record.getRemark(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getRemark() must not be null");
			return RpcResponse.success(merchantOrderService.addShoppingCartFromMerchantOrder(record));
		}catch (RpcServiceException e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}	
	}

	@Override
	public RpcResponse<BsAddressDTO> getLastMerchantOrderAddress(
			BsAddressDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::getLastMerchantOrderAddress record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			return RpcResponse.success(merchantOrderService.getLastMerchantOrderAddress(record));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> createMerchantOrderByGhOrder() {
		return RpcResponse.success(merchantOrderService.createMerchantOrderByGhOrder());
	}

	@Override
	public RpcResponse<Integer> synchronizeMerchantOrderStatus() {
		return RpcResponse.success(merchantOrderService.synchronizeMerchantOrderStatus());
	}

	@Override
	public RpcResponse<RpcPagination<NewGhMerchantOrderDTO>> wxlistMerchantOrders(RpcPagination<NewGhMerchantOrderDTO> pagination) {
		LOGGER.info("MotorcycleRemoteImpl::wxlistMerchantOrders record::{}",pagination);
		try{
			RpcAssert.assertNotNull(pagination,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
			RpcAssert.assertNotNull(pagination.getPageNum(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageNum() must not be null");
			RpcAssert.assertNotNull(pagination.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getPageSize() must not be null");
			RpcAssert.assertNotNull(pagination.getCondition(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
			return RpcResponse.success(RpcPagination.copyPage(merchantOrderService.wxlistMerchantOrders(pagination)));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> wxReminderOrder(GhMerchantOrderDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::wxReminderOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getGhMerchantOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getGhMerchantOrderCode() must not be null");
			return RpcResponse.success(merchantOrderService.wxReminderOrder(record));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<Integer> wxCancelOrder(GhMerchantOrderDTO record) {
		LOGGER.info("MotorcycleRemoteImpl::wxlistMerchantOrders record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getGhMerchantOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getGhMerchantOrderCode() must not be null");
			return RpcResponse.success(merchantOrderService.wxCancelOrder(record));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}

	@Override
	public RpcResponse<List<BsLogisticsDTO>> wxGetLogisticsInfoListByServiceCode(BsLogisticsDTO logistics) {
		LOGGER.info("MotorcycleRemoteImpl::wxGetLogisticsInfoListByServiceCode logistics::{}",logistics);
		try{
			RpcAssert.assertNotNull(logistics,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"logistics must not be null");
			RpcAssert.assertNotNull(logistics.getGhMerchantOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"logistics.getGhMerchantOrderCode() must not be null");
			return RpcResponse.success(merchantOrderService.wxGetLogisticsInfoListByServiceCode(logistics));
		}
		catch (Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
	}


}
