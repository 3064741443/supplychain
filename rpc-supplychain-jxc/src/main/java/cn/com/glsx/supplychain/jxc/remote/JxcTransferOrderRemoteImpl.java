package cn.com.glsx.supplychain.jxc.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.service.JXCMTBsDealerUserInfoService;
import cn.com.glsx.supplychain.jxc.service.JXCTransferOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 11:07
 */
@Component("JxcTransferOrderRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class JxcTransferOrderRemoteImpl implements JxcTransferOrderRemote {
    private static final Logger LOGGER = LoggerFactory.getLogger(JxcOrderRemoteImpl.class);

    @Autowired
    private JXCTransferOrderService transferOrderService;
    @Autowired
    private JXCMTBsDealerUserInfoService dealerUserInfoService;

    @Override
    public RpcResponse<List<JXCDealerUserInfoDTO>> listServiceProvider(JXCDealerUserInfoDTO dealerUserInfoDTO) {
        LOGGER.info("TransferOrderRemoteImpl::listServiceProvider dealerUserInfoDTO",dealerUserInfoDTO);
        try{
            return  RpcResponse.success(dealerUserInfoService.listServiceProvider(dealerUserInfoDTO));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> generateTransferOrder(JXCMdbGenerateTransferOrderDTO record) {
        LOGGER.info("TransferOrderRemoteImpl::generateTransferOrder record::{}",record);
        try{
            RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
            RpcAssert.assertNotNull(record.getServiceProvidercode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getServiceProvidercode() must not be null");
            RpcAssert.assertNotNull(record.getServiceProviderName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getServiceProviderName() must not be null");
            RpcAssert.assertNotNull(record.getAddressDTO(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDTO() must not be null");
            RpcAssert.assertNotNull(record.getMaterialInfoList(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialInfoList() must not be null");
            return RpcResponse.success(transferOrderService.generateTransferOrder(record));
        }catch(RpcServiceException e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<Integer> generateBsTransferOrder(JXCMdbGenerateBsTransferOrderDTO record) {
        LOGGER.info("TransferOrderRemoteImpl::generateTransferOrder record::{}",record);
        try{
            RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
            RpcAssert.assertNotNull(record.getInServiceProvidercode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getInServiceProvidercode() must not be null");
            RpcAssert.assertNotNull(record.getInServiceProviderName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getInServiceProviderName() must not be null");
            RpcAssert.assertNotNull(record.getOutServiceProvidercode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOutServiceProvidercode() must not be null");
            RpcAssert.assertNotNull(record.getOutServiceProviderName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOutServiceProviderName() must not be null");
            RpcAssert.assertNotNull(record.getMaterialCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialCode() must not be null");
            RpcAssert.assertNotNull(record.getMaterialName(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMaterialName() must not be null");
            RpcAssert.assertNotNull(record.getTransferQuantity(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTransferQuantity() must not be null");
            RpcAssert.assertNotNull(record.getAddressDTO(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"getAddressDTO() must not be null");
            return RpcResponse.success(transferOrderService.generateBsTransferOrder(record));
        }catch(RpcServiceException e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e);
        }
    }

    @Override
    public RpcResponse<RpcPagination<JXCTransferOrderDTO>> pageTransferOrderJXC(RpcPagination<JXCTransferOrderQueryDTO> pagination) {
        LOGGER.info("TransferOrderRemoteImpl::pageBsMerchantOrderJXC pagination::{}",pagination);
        try{
            RpcAssert.assertNotNull(pagination,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination must not be null");
            RpcAssert.assertNotNull(pagination.getCondition(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"pagination.getCondition() must not be null");
            return RpcResponse.success(RpcPagination.copyPage(transferOrderService.pageTransferOrderJXC(pagination)));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMdbTransferOrderExportDTO>> exportJxcTransferOrder(JXCTransferOrderQueryDTO transferOrderQueryDTO) {
        LOGGER.info("TransferOrderRemoteImpl::exportBsTransferOrder transferOrderQueryDTO::{}",transferOrderQueryDTO);
        try{
            RpcAssert.assertNotNull(transferOrderQueryDTO,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"transferOrderQueryDTO must not be null");
            return RpcResponse.success(transferOrderService.exportJxcTransferOrder(transferOrderQueryDTO));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<List<JXCMdbBsTransferOrderExportDTO>> exportBsTransferOrder(JXCBsTransferOrderQueryDTO transferOrderQueryDTO) {
        LOGGER.info("TransferOrderRemoteImpl::exportBsTransferOrder transferOrderQueryDTO::{}",transferOrderQueryDTO);
        try{
            RpcAssert.assertNotNull(transferOrderQueryDTO,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"transferOrderQueryDTO must not be null");
            return RpcResponse.success(transferOrderService.exportBsTransferOrder(transferOrderQueryDTO));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }


    @Override
    public RpcResponse<Integer> commitTransferOrder(JXCMdbCommitTransferOrderDTO record) {
        LOGGER.info("TransferOrderRemoteImpl::commitTransferOrder record::{}",record);
        try{
            RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
            RpcAssert.assertNotNull(record.getTranOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTranOrderCode() must not be null");
            RpcAssert.assertNotNull(record.getDeliveryType(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getDeliveryType() must not be null");
            return RpcResponse.success(transferOrderService.commitTransferOrder(record));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<JXCMdbTransferOrderDTO> getTransferOrder(JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO) {
        return null;
    }

    @Override
    public RpcResponse<List<JXCLogisticsDTO>> getTransferShippingDetail(JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO) {
        LOGGER.info("TransferOrderRemoteImpl::getTransferShippingDetail transferOrderDetailQueryDTO::{}",transferOrderDetailQueryDTO.getTranOrderCode());
        try{
            RpcAssert.assertNotNull(transferOrderDetailQueryDTO,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"transferOrderDetailQueryDTO must not be null");
            RpcAssert.assertNotNull(transferOrderDetailQueryDTO.getTranOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"transferOrderDetailQueryDTO.getTranOrderCode() must not be null");
            return RpcResponse.success(transferOrderService.getTransferShippingDetail(transferOrderDetailQueryDTO));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> importEquipmentDetails(List<JXCTransferOrderImportDTO> transferOrderImportDTOList) {
        return null;
    }

    @Override
    public RpcResponse<Integer> checkBsTransferOrder(JXCMdbTransferOrderCheckDTO record) {
        LOGGER.info("TransferOrderRemoteImpl::checkBsTransferOrder transferOrderDetailQueryDTO::{}",record.getTranOrderCode());
        try{
            RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
            RpcAssert.assertNotNull(record.getTranOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTranOrderCode() must not be null");
            return RpcResponse.success(transferOrderService.checkBsTransferOrder(record));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }

    @Override
    public RpcResponse<Integer> rebackBsTransferOrder(JXCMdbTransferOrderRebackDTO record) {
        LOGGER.info("TransferOrderRemoteImpl::rebackBsTransferOrder record::{}",record.getTranOrderCode());
        try{
            RpcAssert.assertNotNull(record,JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
            RpcAssert.assertNotNull(record.getTranOrderCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getTranOrderCode() must not be null");
            return RpcResponse.success(transferOrderService.rebackBsTransferOrder(record));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR);
        }
    }


}
