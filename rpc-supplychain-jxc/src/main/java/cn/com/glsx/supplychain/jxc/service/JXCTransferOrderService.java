package cn.com.glsx.supplychain.jxc.service;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jxc.common.Constants;
import cn.com.glsx.supplychain.jxc.converter.JXCMTBsLogisticsRpcConvert;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.TransferOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.kafka.ExportBsMdbTransferOrder;
import cn.com.glsx.supplychain.jxc.kafka.ExportJxcMdbTransferOrder;
import cn.com.glsx.supplychain.jxc.kafka.SendKafkaService;
import cn.com.glsx.supplychain.jxc.mapper.JXCMTBsLogisticsMapper;
import cn.com.glsx.supplychain.jxc.mapper.JxcTransferOrderMapper;
import cn.com.glsx.supplychain.jxc.mapper.JxcTransferOrderSnMapper;
import cn.com.glsx.supplychain.jxc.model.*;
import cn.com.glsx.supplychain.jxc.util.JxcUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 11:10
 */
@Service
public class JXCTransferOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JXCTransferOrderService.class);
    @Autowired
    private JXCMTBsDealerUserInfoService jxcmtUserInfoService;
    @Autowired
    private JXCMTBsAddressService jxcmtBsAddressService;
    @Autowired
    private JXCMTBsLogisticsService jxcmtBsLogisticsService;
    @Autowired
    private JxcTransferOrderMapper transferOrderMapper;
    @Autowired
    private JxcTransferOrderSnMapper jxcTransferOrderSnMapper;
    @Autowired
    private JXCMTBsLogisticsMapper jxcmtBsLogisticsMapper;
    @Autowired
    private JXCMTMaterialInfoService materialInfoService;
    @Autowired
    private SendKafkaService sendKafkaService;
    @Autowired
    private SnowflakeWorker snowflakeWorker;

    public Page<JXCMdbTransferOrderDTO> pageTransferOrder(RpcPagination<JXCMdbTransferOrderDTO> pagination) {
        JXCMdbTransferOrder condition = new JXCMdbTransferOrder();
        PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
        return null;
    }

    /**
     * @param record
     * @author: luoqiang
     * @description: 经销存生成的调拨订单
     * @date: 2020/11/5 14:06
     * @return: java.lang.Integer
     */
    public Integer generateTransferOrder(JXCMdbGenerateTransferOrderDTO record) {
        LOGGER.info("生成调拨单请求 {}", JSONObject.toJSON(record));
        JXCMTBsDealerUserInfo dealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(record.getServiceProvidercode());
        //获取登录账号的名称
        JXCMTBsDealerUserInfo outServiceProvider = jxcmtUserInfoService.getBsDealerUserInfo(record.getOutServiceProvidercode());
        record.getAddressDTO().setMerchantCode(record.getServiceProvidercode());
        JXCMTBsAddress address = null;
        List<JXCMdbTransferOrder> transferOrderList = new ArrayList<>();
        List<JXCMTBsLogistics> listBsLogistics = new ArrayList<>();
        if (record.getAddressDTO().getId() != null) {
            jxcmtBsAddressService.updateAddressById(record.getAddressDTO());
            address = jxcmtBsAddressService.getAddressById(record.getAddressDTO().getId());
        } else {
            jxcmtBsAddressService.addAddress(record.getAddressDTO());
            address = jxcmtBsAddressService.getAddressByName(record.getAddressDTO());
        }
        for (JXCMaterialInfoDTO materialInfoDTO : record.getMaterialInfoList()) {
            //生成调拨订单信息
            JXCMdbTransferOrder transferOrder = generateJxcTransferOrder(dealerUserInfo, outServiceProvider.getName(), materialInfoDTO, record.getOrderSource(), record.getOutServiceProvidercode(), record.getOutServiceProviderName());
            transferOrderList.add(transferOrder);
            //生成物流信息
            listBsLogistics.add(generatorBsLogistics(address.getId().intValue(), transferOrder.getTranOrderCode(), record.getConsumer()));
        }

        //生成调拨订单信息
        Integer result = insertListTransferOrder(transferOrderList);
        //生成物流信息
        jxcmtBsLogisticsService.insertListJxcmtBsLogistics(listBsLogistics);
        return result;
    }

    /**
     * @param record
     * @author: luoqiang
     * @description: 商务系统生成的调拨订单
     * @date: 2020/11/5 14:06
     * @return: java.lang.Integer
     */
    public Integer generateBsTransferOrder(JXCMdbGenerateBsTransferOrderDTO record) {
        LOGGER.info("生成调拨单请求 {}", JSONObject.toJSON(record));
        JXCMTBsDealerUserInfo inDealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(record.getInServiceProvidercode());
        JXCMTBsDealerUserInfo outDealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(record.getOutServiceProvidercode());
        JXCMTBsAddress address = null;
        List<JXCMdbTransferOrder> transferOrderList = new ArrayList<>();
        List<JXCMTBsLogistics> listBsLogistics = new ArrayList<>();
        if (record.getAddressDTO().getId() != null) {
            jxcmtBsAddressService.updateAddressById(record.getAddressDTO());
            address = jxcmtBsAddressService.getAddressById(record.getAddressDTO().getId());
        } else {
            jxcmtBsAddressService.addAddress(record.getAddressDTO());
            address = jxcmtBsAddressService.getAddressByName(record.getAddressDTO());
        }

        JXCMaterialInfoDTO materialInfoDTO = new JXCMaterialInfoDTO();
        materialInfoDTO.setMaterialCode(record.getMaterialCode());
        materialInfoDTO.setMaterialName(record.getMaterialName());
        materialInfoDTO.setMaterialTotal(record.getTransferQuantity());

        //生成商务调拨订单信息
        JXCMdbTransferOrder transferOrder = generateBsJxcTransferOrder(inDealerUserInfo, outDealerUserInfo, record.getConsumer(), materialInfoDTO, record.getOrderSource());
        transferOrderList.add(transferOrder);
        //生成物流信息
        listBsLogistics.add(generatorBsLogistics(address.getId().intValue(), transferOrder.getTranOrderCode(), record.getConsumer()));
        //生成调拨订单信息
        Integer result = insertListTransferOrder(transferOrderList);
        //生成物流信息
        jxcmtBsLogisticsService.insertListJxcmtBsLogistics(listBsLogistics);
        return result;
    }

    private JXCMTBsLogistics generatorBsLogistics(Integer addressId, String tranOrderCode, String merchantName) {
        JXCMTBsLogistics logistics = new JXCMTBsLogistics();
        String logiCode = Constants.LOGI_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
        logistics.setReceiveId(addressId.longValue());
        logistics.setCode(logiCode);
        logistics.setType(Byte.valueOf("8"));
        logistics.setServiceCode(tranOrderCode);
        logistics.setCreatedBy(merchantName);
        logistics.setUpdatedBy(merchantName);
        logistics.setCreatedDate(JxcUtils.getNowDate());
        logistics.setUpdatedDate(JxcUtils.getNowDate());
        logistics.setDeletedFlag("N");
        return logistics;
    }

    private Integer insertListTransferOrder(List<JXCMdbTransferOrder> transferOrderList) {
        if (null == transferOrderList || transferOrderList.isEmpty()) {
            return 0;
        }
        return transferOrderMapper.insertList(transferOrderList);
    }

    private JXCMdbTransferOrder generateJxcTransferOrder(JXCMTBsDealerUserInfo dealerUserInfo, String consumer, JXCMaterialInfoDTO materialInfoDTO, String orderSource, String outMerchantCode, String outMerchantName) {
        String tranOrderCode = Constants.TRAN_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
        JXCMdbTransferOrder transferOrder = new JXCMdbTransferOrder();
        transferOrder.setTranOrderCode(tranOrderCode);
        transferOrder.setInMerchantCode(dealerUserInfo.getMerchantCode());
        transferOrder.setInMerchantName(dealerUserInfo.getMerchantName());
        transferOrder.setOutMerchantCode(outMerchantCode);
        transferOrder.setOutMerchantName(outMerchantName);
        transferOrder.setMaterialCode(materialInfoDTO.getMaterialCode());
        transferOrder.setMaterialName(materialInfoDTO.getMaterialName());
        transferOrder.setOrderTotal(materialInfoDTO.getMaterialTotal());
        transferOrder.setOrderSource(orderSource);
        transferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_WAIT_CHECK.getCode());
        transferOrder.setCreatedBy(consumer);
        transferOrder.setUpdatedBy(consumer);
        transferOrder.setCreatedDate(JxcUtils.getNowDate());
        transferOrder.setUpdatedDate(JxcUtils.getNowDate());
        transferOrder.setDeletedFlag("N");
        return transferOrder;
    }

    private JXCMdbTransferOrder generateBsJxcTransferOrder(JXCMTBsDealerUserInfo inDealerUserInfo, JXCMTBsDealerUserInfo outDealerUserInfo, String consumer, JXCMaterialInfoDTO materialInfoDTO, String orderSource) {
        String tranOrderCode = Constants.TRAN_ORDER_PREFIX + JxcUtils.generatorOrderCode(snowflakeWorker);
        JXCMdbTransferOrder transferOrder = new JXCMdbTransferOrder();
        transferOrder.setTranOrderCode(tranOrderCode);
        transferOrder.setInMerchantCode(inDealerUserInfo.getMerchantCode());
        transferOrder.setInMerchantName(inDealerUserInfo.getMerchantName());
        transferOrder.setOutMerchantCode(outDealerUserInfo.getMerchantCode());
        transferOrder.setOutMerchantName(outDealerUserInfo.getMerchantName());
        transferOrder.setMaterialCode(materialInfoDTO.getMaterialCode());
        transferOrder.setMaterialName(materialInfoDTO.getMaterialName());
        transferOrder.setOrderTotal(materialInfoDTO.getMaterialTotal());
        transferOrder.setOrderSource(orderSource);
        transferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode());
        transferOrder.setCreatedBy(consumer);
        transferOrder.setUpdatedBy(consumer);
        transferOrder.setCreatedDate(JxcUtils.getNowDate());
        transferOrder.setUpdatedDate(JxcUtils.getNowDate());
        transferOrder.setDeletedFlag("N");
        return transferOrder;
    }

    public Page<JXCTransferOrderDTO> pageTransferOrderJXC(RpcPagination<JXCTransferOrderQueryDTO> pagination) {
        PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
        JXCTransferOrderQueryDTO transferOrderQueryDTO = pagination.getCondition();
        JXCMdbTransferOrder transferOrder = new JXCMdbTransferOrder();
        transferOrder.setInMerchantName(transferOrderQueryDTO.getServiceProviderName());
        transferOrder.setOutMerchantName(transferOrderQueryDTO.getServiceProviderName());
        if (!StringUtils.isEmpty(transferOrderQueryDTO.getInServiceProviderName())) {
            transferOrder.setInServiceProviderName(transferOrderQueryDTO.getInServiceProviderName());
        }
        //登录调出服务商
        transferOrder.setOutMerchantCode(transferOrderQueryDTO.getMerchantCode());
        //登录调入服务商
        transferOrder.setInMerchantCode(transferOrderQueryDTO.getMerchantCode());
        transferOrder.setOrderSource(transferOrderQueryDTO.getOrderSource());
        transferOrder.setMaterialCode(transferOrderQueryDTO.getMaterialName());
        transferOrder.setMaterialName(transferOrderQueryDTO.getMaterialName());
        transferOrder.setOrderStatus(transferOrderQueryDTO.getOrderStatus());
        transferOrder.setStartTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getStartTime()));
        transferOrder.setEndTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getEndTime()));
        transferOrder.setTranOrderCode(transferOrderQueryDTO.getTranOrderCode());
        transferOrder.setTransferType(transferOrderQueryDTO.getTransferType());
        transferOrder.setMerchantName(transferOrderQueryDTO.getConsumer());
        Page<JXCTransferOrderDTO> transferOrderDTOList = transferOrderMapper.pageTransferOrderJXC(transferOrder);

        List<String> listMaterialCodes = new ArrayList<>();
        List<String> tranOrderCodes = new ArrayList<>();
        List<Long> addressIds = new ArrayList<>();
        for (JXCTransferOrderDTO transferOrderDTO : transferOrderDTOList.getResult()) {
            tranOrderCodes.add(transferOrderDTO.getTranOrderCode());
            listMaterialCodes.add(transferOrderDTO.getMaterialCode());
        }
        Map<String, JXCMTMaterialInfo> materialInfoMap = materialInfoService.mapMaterialByListMaterialCode(listMaterialCodes);
        //获取调拨订单物流信息->获取收货地址ID
        Map<String, JXCMTBsLogistics> map = jxcmtBsLogisticsService.listMapLogisticsById(tranOrderCodes);
        for (JXCTransferOrderDTO transferOrderDTO : transferOrderDTOList.getResult()) {
            transferOrderDTO.setReceiveId(map.get(transferOrderDTO.getTranOrderCode()).getReceiveId());
            addressIds.add(map.get(transferOrderDTO.getTranOrderCode()).getReceiveId());
        }
        JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO = null;
        //获取调拨订单收货地址
        Map<Long, JXCMTBsAddress> addressMap = jxcmtBsAddressService.listMapAddressByIds(addressIds);
        JXCMTBsAddressDTO addressDTO = null;
        //JXCMTBsDealerUserInfo inDealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(transferOrderQueryDTO.getMerchantCode());
        for (JXCTransferOrderDTO transferOrderDTO : transferOrderDTOList.getResult()) {
            //动态判断调拨订单是是调入还是调出
            if (transferOrderQueryDTO.getConsumer().equals(transferOrderDTO.getInServiceProviderName())) {
                transferOrderDTO.setTransferType("调入");
            } else {
                transferOrderDTO.setTransferType("调出");
            }
            //填充地址信息
            addressDTO = new JXCMTBsAddressDTO();
            addressDTO.setName(addressMap.get(transferOrderDTO.getReceiveId()).getName());
            addressDTO.setMobile(addressMap.get(transferOrderDTO.getReceiveId()).getMobile());
            addressDTO.setAddress(addressMap.get(transferOrderDTO.getReceiveId()).getAddress());
            addressDTO.setProvinceName(addressMap.get(transferOrderDTO.getReceiveId()).getProvinceName());
            addressDTO.setCityName(addressMap.get(transferOrderDTO.getReceiveId()).getCityName());
            addressDTO.setAreaName(addressMap.get(transferOrderDTO.getReceiveId()).getAreaName());
            transferOrderDTO.setBsAddressDTO(addressDTO);
            //填充物料是否扫码信息
            transferOrderDTO.setNdScan(materialInfoMap.get(transferOrderDTO.getMaterialCode()).getNdScan());
            if (StringUtils.isEmpty(transferOrderDTO.getSendTotal())) {
                transferOrderDTO.setSendTotal(0);
            }
            if (!StringUtils.isEmpty(transferOrderDTO.getOrderTotal()) && !StringUtils.isEmpty(transferOrderDTO.getSendTotal())) {
                transferOrderDTO.setOweTotal(transferOrderDTO.getOrderTotal() - transferOrderDTO.getSendTotal());
            }
        }
       /* if (!StringUtils.isEmpty(transferOrderQueryDTO.getMerchantCode())) {
            JXCMTBsDealerUserInfo inDealerUserInfo = jxcmtUserInfoService.getBsDealerUserInfo(transferOrderQueryDTO.getMerchantCode());
            //遍历删除调入服务商
            Iterator<JXCTransferOrderDTO> iterator = transferOrderDTOList.getResult().iterator();
            while (iterator.hasNext()) {
                JXCTransferOrderDTO transferOrderDTO = iterator.next();
                if (transferOrderDTO.getInServiceProviderName().equals(inDealerUserInfo.getMerchantName())) {
                    iterator.remove();//使用迭代器的删除方法删除
                }
            }
        }*/
        return transferOrderDTOList;
    }

    public Integer commitTransferOrder(JXCMdbCommitTransferOrderDTO record) {
        Integer result = 0;
        if (!StringUtils.isEmpty(record.getDeliveryType()) && (Constants.LOGISTIC_SEND).equals(record.getDeliveryType())) {
            JXCMTBsLogistics jxcmtBsLogistics = jxcmtBsLogisticsService.generatorSendBsLogistics(record.getReceiveId(), record.getTranOrderCode(), record.getLogisticsDTO(), record.getConsumer());
            JXCMdbTransferOrderSn transferOrderSn = null;
            List<JXCMdbTransferOrderSn> transferOrderSnList = new ArrayList<>();
            if (!StringUtils.isEmpty(record.getSnList())) {
                for (String sn : record.getSnList()) {
                    transferOrderSn = generateJxcTransferOrderSn(record.getTranOrderCode(), sn, record.getConsumer());
                    transferOrderSnList.add(transferOrderSn);
                }
            }
            try {
                JXCMdbTransferOrder jxcMdbTransferOrder = this.getTransferOrderByTranOrderCode(record.getTranOrderCode());
                jxcMdbTransferOrder.setSendTotal(jxcMdbTransferOrder.getSendTotal() == null ? 0 : jxcMdbTransferOrder.getSendTotal());
                Integer shipmentsTotal= jxcMdbTransferOrder.getSendTotal() + record.getLogisticsDTO().getShipmentsQuantity();
                if(shipmentsTotal>jxcMdbTransferOrder.getOrderTotal()) {
                    return 0;
                }
                result = jxcmtBsLogisticsMapper.insertLogistics(jxcmtBsLogistics);
                if (transferOrderSnList.size() > 0) {
                    jxcTransferOrderSnMapper.insertList(transferOrderSnList);
                }
                if (!StringUtils.isEmpty(jxcMdbTransferOrder.getSendTotal()) && !StringUtils.isEmpty(record.getLogisticsDTO().getShipmentsQuantity())) {
                    Integer sendTotal = jxcMdbTransferOrder.getSendTotal() + record.getLogisticsDTO().getShipmentsQuantity();
                    Integer orderTotal = jxcMdbTransferOrder.getOrderTotal();
                    Integer oweTotal = 0;
                    if (!StringUtils.isEmpty(orderTotal)) {
                        oweTotal = orderTotal - sendTotal;
                    }
                    if (oweTotal == 0) {
                        jxcMdbTransferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_COMPLETED.getCode());
                        jxcMdbTransferOrder.setSendTotal(sendTotal);
                        this.updateTransferOrder(jxcMdbTransferOrder);
                    } else if (sendTotal != 0 && oweTotal > 0) {
                        jxcMdbTransferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getCode());
                        jxcMdbTransferOrder.setSendTotal(sendTotal);
                        this.updateTransferOrder(jxcMdbTransferOrder);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
            }
        } else if (!StringUtils.isEmpty(record.getDeliveryType()) && (Constants.OFFLINE_DELIVERY).equals(record.getDeliveryType())) {
            JXCMTBsLogistics jxcmtBsLogistics = jxcmtBsLogisticsService.generatorOfflineDeliveryBsLogistics(record.getReceiveId(), record.getTranOrderCode(), record.getSendCount(), record.getConsumer());
            JXCMdbTransferOrderSn transferOrderSn = null;
            List<JXCMdbTransferOrderSn> transferOrderSnList = new ArrayList<>();
            if (!StringUtils.isEmpty(record.getSnList())) {
                for (String sn : record.getSnList()) {
                    transferOrderSn = generateJxcTransferOrderSn(record.getTranOrderCode(), sn, record.getConsumer());
                    transferOrderSnList.add(transferOrderSn);
                }
            }
            try {
                JXCMdbTransferOrder jxcMdbTransferOrder = this.getTransferOrderByTranOrderCode(record.getTranOrderCode());
                jxcMdbTransferOrder.setSendTotal(jxcMdbTransferOrder.getSendTotal() == null ? 0 : jxcMdbTransferOrder.getSendTotal());
                Integer shipmentsTotal= jxcMdbTransferOrder.getSendTotal() + record.getSendCount();
                if(shipmentsTotal>jxcMdbTransferOrder.getOrderTotal()) {
                    return 0;
                }
                result = jxcmtBsLogisticsMapper.insertLogistics(jxcmtBsLogistics);
                if (transferOrderSnList.size() > 0) {
                    jxcTransferOrderSnMapper.insertList(transferOrderSnList);
                }
                if (!StringUtils.isEmpty(jxcMdbTransferOrder.getSendTotal()) && !StringUtils.isEmpty(record.getSendCount())) {
                    Integer sendTotal = jxcMdbTransferOrder.getSendTotal() + record.getSendCount();
                    Integer orderTotal = jxcMdbTransferOrder.getOrderTotal();
                    Integer oweTotal = 0;
                    if (!StringUtils.isEmpty(orderTotal)) {
                        oweTotal = orderTotal - sendTotal;
                    }
                    if (oweTotal == 0) {
                        jxcMdbTransferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_COMPLETED.getCode());
                        jxcMdbTransferOrder.setSendTotal(sendTotal);
                        this.updateTransferOrder(jxcMdbTransferOrder);
                    } else if (sendTotal != 0 && oweTotal > 0) {
                        jxcMdbTransferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getCode());
                        jxcMdbTransferOrder.setSendTotal(sendTotal);
                        this.updateTransferOrder(jxcMdbTransferOrder);
                    }
                }
            } catch (Exception e) {

                LOGGER.error(e.getMessage(), e);
                throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
            }
        }
        return result;
    }

    private JXCMdbTransferOrderSn generateJxcTransferOrderSn(String tranOrderCode, String sn, String consumer) {
        JXCMdbTransferOrderSn transferOrderSn = new JXCMdbTransferOrderSn();
        transferOrderSn.setTranOrderCode(tranOrderCode);
        transferOrderSn.setSn(sn);
        transferOrderSn.setCreatedBy(consumer);
        transferOrderSn.setUpdatedBy(consumer);
        transferOrderSn.setCreatedDate(JxcUtils.getNowDate());
        transferOrderSn.setUpdatedDate(JxcUtils.getNowDate());
        transferOrderSn.setDeletedFlag("N");
        return transferOrderSn;
    }

    public List<JXCLogisticsDTO> getTransferShippingDetail(JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO) {
        Example example = new Example(JXCMTBsLogistics.class);
        example.createCriteria().andEqualTo("serviceCode", transferOrderDetailQueryDTO.getTranOrderCode())
                .andEqualTo("type", Byte.valueOf("9"));
        List<JXCMTBsLogistics> bsLogisticsList = jxcmtBsLogisticsMapper.selectByExample(example);
        List<JXCLogisticsDTO> bsLogisticsDTOList = JXCMTBsLogisticsRpcConvert.convertBsLogisticsToList(bsLogisticsList);
        List<JXCLogisticsDTO> logisticsDTOList = new ArrayList<>();
        if (StringUtils.isEmpty(bsLogisticsDTOList)) {
            return null;
        }
        for (JXCLogisticsDTO bsLogisticsDTO : bsLogisticsDTOList) {
            if (!StringUtils.isEmpty(bsLogisticsDTO.getCode()) && bsLogisticsDTO.getCode().startsWith("LGI")) {
                bsLogisticsDTO.setDeliveryType(Constants.LOGISTIC_SEND);
            } else {
                bsLogisticsDTO.setDeliveryType(Constants.OFFLINE_DELIVERY);
            }
            logisticsDTOList.add(bsLogisticsDTO);
        }
        return logisticsDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer checkBsTransferOrder(JXCMdbTransferOrderCheckDTO record) {
        Integer result = 0;
        JXCMdbTransferOrder jxcMdbTransferOrder = this.getTransferOrderByTranOrderCode(record.getTranOrderCode());
        if (StringUtils.isEmpty(jxcMdbTransferOrder)) {
            return result;
        }
        jxcMdbTransferOrder.setOrderTotal(record.getTotalCheck());
        jxcMdbTransferOrder.setCheckBy(record.getConsumer());
        jxcMdbTransferOrder.setCheckTotal(record.getTotalCheck());
        jxcMdbTransferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode());
        jxcMdbTransferOrder.setUpdatedBy(record.getConsumer());
        jxcMdbTransferOrder.setUpdatedDate(JxcUtils.getNowDate());
        try {
            result = this.updateTransferOrder(jxcMdbTransferOrder);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer rebackBsTransferOrder(JXCMdbTransferOrderRebackDTO record) {
        Integer result = 0;
        JXCMdbTransferOrder jxcMdbTransferOrder = this.getTransferOrderByTranOrderCode(record.getTranOrderCode());
        if (StringUtils.isEmpty(jxcMdbTransferOrder)) {
            return result;
        }
        jxcMdbTransferOrder.setBackRemark(record.getRebackReason());
        jxcMdbTransferOrder.setOrderStatus(TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getCode());
        jxcMdbTransferOrder.setUpdatedBy(record.getConsumer());
        jxcMdbTransferOrder.setUpdatedDate(JxcUtils.getNowDate());
        try {
            result = this.updateTransferOrder(jxcMdbTransferOrder);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RpcServiceException(JXCErrorCodeEnum.ERRCODE_SYSTEM_ERROR.getDescrible());
        }
        return result;
    }

    private Integer updateTransferOrder(JXCMdbTransferOrder jxcMdbTransferOrder) {
        if (StringUtils.isEmpty(jxcMdbTransferOrder)) {
            return 0;
        }
        return transferOrderMapper.updateByPrimaryKeySelective(jxcMdbTransferOrder);
    }

    private JXCMdbTransferOrder getTransferOrderByTranOrderCode(String tranOrderCode) {
        JXCMdbTransferOrder conditon = new JXCMdbTransferOrder();
        conditon.setTranOrderCode(tranOrderCode);
        return transferOrderMapper.selectOne(conditon);
    }


    public List<JXCMdbBsTransferOrderExportDTO> exportBsTransferOrder(JXCBsTransferOrderQueryDTO transferOrderQueryDTO) {
        LOGGER.info("JXCTransferOrderService::exportBsTransferOrder record::{}", transferOrderQueryDTO);
        //发送kafka异步执行
        ExportBsMdbTransferOrder exportMdbTransferOrder = generatorExportMdbTransferOrder(transferOrderQueryDTO);
        sendKafkaService.notifyBsMdbTransferOrder(transferOrderQueryDTO.getConsumer(), Constants.TASK_CFG_ID_JXC_ORDER_BSTRNSFER, exportMdbTransferOrder);
        JXCMdbTransferOrder transferOrder = new JXCMdbTransferOrder();
        transferOrder.setInMerchantName(transferOrderQueryDTO.getServiceProviderName());
        transferOrder.setOutMerchantName(transferOrderQueryDTO.getServiceProviderName());
        //登录调出服务商
        transferOrder.setOutMerchantCode(transferOrderQueryDTO.getMerchantCode());
        //登录调入服务商
        transferOrder.setInMerchantCode(transferOrderQueryDTO.getMerchantCode());
        transferOrder.setOrderSource(transferOrderQueryDTO.getOrderSource());
        transferOrder.setMaterialCode(transferOrderQueryDTO.getMaterialName());
        transferOrder.setMaterialName(transferOrderQueryDTO.getMaterialName());
        transferOrder.setOrderStatus(transferOrderQueryDTO.getOrderStatus());
        transferOrder.setStartTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getStartTime()));
        transferOrder.setEndTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getEndTime()));
        transferOrder.setTranOrderCode(transferOrderQueryDTO.getTranOrderCode());
        transferOrder.setMerchantName(transferOrderQueryDTO.getConsumer());
        List<JXCMdbBsTransferOrderExportDTO> transferOrderDTOList = transferOrderMapper.exportBsTransferOrderJXC(transferOrder);

        List<String> listMaterialCodes = new ArrayList<>();
        List<String> tranOrderCodes = new ArrayList<>();
        List<Long> addressIds = new ArrayList<>();
        for (JXCMdbBsTransferOrderExportDTO transferOrderDTO : transferOrderDTOList) {
            tranOrderCodes.add(transferOrderDTO.getTranOrderCode());
            listMaterialCodes.add(transferOrderDTO.getMaterialCode());
        }
        //获取调拨订单物流信息->获取收货地址ID
        Map<String, JXCMTBsLogistics> map = jxcmtBsLogisticsService.listMapLogisticsById(tranOrderCodes);
        for (JXCMdbBsTransferOrderExportDTO transferOrderDTO : transferOrderDTOList) {
            transferOrderDTO.setReceiveId(map.get(transferOrderDTO.getTranOrderCode()).getReceiveId());
            addressIds.add(map.get(transferOrderDTO.getTranOrderCode()).getReceiveId());
        }
        JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO = null;
        //获取调拨订单收货地址
        Map<Long, JXCMTBsAddress> addressMap = jxcmtBsAddressService.listMapAddressByIds(addressIds);
        JXCMTBsAddressDTO addressDTO = null;
        for (JXCMdbBsTransferOrderExportDTO transferOrderDTO : transferOrderDTOList) {
            if (!StringUtils.isEmpty(transferOrderDTO.getCode()) && transferOrderDTO.getCode().startsWith("LGI")) {
                transferOrderDTO.setDeliveryType("物流配送");
            } else {
                transferOrderDTO.setDeliveryType("线下配送");
            }
            //填充地址信息
            addressDTO = new JXCMTBsAddressDTO();
            addressDTO.setName(addressMap.get(transferOrderDTO.getReceiveId()).getName());
            addressDTO.setMobile(addressMap.get(transferOrderDTO.getReceiveId()).getMobile());
            addressDTO.setAddress(addressMap.get(transferOrderDTO.getReceiveId()).getAddress());
            addressDTO.setProvinceName(addressMap.get(transferOrderDTO.getReceiveId()).getProvinceName());
            addressDTO.setCityName(addressMap.get(transferOrderDTO.getReceiveId()).getCityName());
            addressDTO.setAreaName(addressMap.get(transferOrderDTO.getReceiveId()).getAreaName());
            transferOrderDTO.setBsAddressDTO(addressDTO);
            if (!StringUtils.isEmpty(transferOrderDTO.getOrderTotal()) && !StringUtils.isEmpty(transferOrderDTO.getSendTotal())) {
                transferOrderDTO.setOweTotal(transferOrderDTO.getOrderTotal() - transferOrderDTO.getSendTotal());
            }
        }

        return transferOrderDTOList;
    }

    public List<JXCMdbTransferOrderExportDTO> exportJxcTransferOrder(JXCTransferOrderQueryDTO transferOrderQueryDTO) {
        LOGGER.info("JXCTransferOrderService::exportJxcTransferOrder record::{}", transferOrderQueryDTO);
        //发送kafka异步执行
        ExportJxcMdbTransferOrder exportMdbTransferOrder = generatorExportJxcMdbTransferOrder(transferOrderQueryDTO);
        sendKafkaService.notifyJXCMdbTransferOrder(transferOrderQueryDTO.getConsumer(), Constants.TASK_CFG_ID_JXC_ORDER_JXCTRNSFER, exportMdbTransferOrder);
        JXCMdbTransferOrder transferOrder = new JXCMdbTransferOrder();
        transferOrder.setInMerchantName(transferOrderQueryDTO.getServiceProviderName());
        transferOrder.setOutMerchantName(transferOrderQueryDTO.getServiceProviderName());
        if (!StringUtils.isEmpty(transferOrderQueryDTO.getInServiceProviderName())) {
            transferOrder.setInServiceProviderName(transferOrderQueryDTO.getInServiceProviderName());
        }
        //登录调出服务商
        transferOrder.setOutMerchantCode(transferOrderQueryDTO.getMerchantCode());
        //登录调入服务商
        transferOrder.setInMerchantCode(transferOrderQueryDTO.getMerchantCode());
        transferOrder.setOrderSource(transferOrderQueryDTO.getOrderSource());
        transferOrder.setMaterialCode(transferOrderQueryDTO.getMaterialName());
        transferOrder.setMaterialName(transferOrderQueryDTO.getMaterialName());
        transferOrder.setOrderStatus(transferOrderQueryDTO.getOrderStatus());
        transferOrder.setStartTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getStartTime()));
        transferOrder.setEndTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getEndTime()));
        transferOrder.setTranOrderCode(transferOrderQueryDTO.getTranOrderCode());
        transferOrder.setTransferType(transferOrderQueryDTO.getTransferType());
        transferOrder.setMerchantName(transferOrderQueryDTO.getConsumer());
        List<JXCMdbTransferOrderExportDTO> transferOrderDTOList = transferOrderMapper.exportJxcTransferOrder(transferOrder);
        List<String> listMaterialCodes = new ArrayList<>();
        List<String> tranOrderCodes = new ArrayList<>();
        List<Long> addressIds = new ArrayList<>();
        for (JXCMdbTransferOrderExportDTO transferOrderDTO : transferOrderDTOList) {
            tranOrderCodes.add(transferOrderDTO.getTranOrderCode());
            listMaterialCodes.add(transferOrderDTO.getMaterialCode());
        }
        //获取调拨订单物流信息->获取收货地址ID
        Map<String, JXCMTBsLogistics> map = jxcmtBsLogisticsService.listMapLogisticsById(tranOrderCodes);
        for (JXCMdbTransferOrderExportDTO transferOrderDTO : transferOrderDTOList) {
            transferOrderDTO.setReceiveId(map.get(transferOrderDTO.getTranOrderCode()).getReceiveId());
            addressIds.add(map.get(transferOrderDTO.getTranOrderCode()).getReceiveId());
        }
        //获取调拨订单收货地址
        Map<Long, JXCMTBsAddress> addressMap = jxcmtBsAddressService.listMapAddressByIds(addressIds);
        JXCMTBsAddressDTO addressDTO = null;
        for (JXCMdbTransferOrderExportDTO transferOrderDTO : transferOrderDTOList) {
            if (!StringUtils.isEmpty(transferOrderDTO.getCode()) && transferOrderDTO.getCode().startsWith("LGI")) {
                transferOrderDTO.setDeliveryType("物流配送");
            } else {
                transferOrderDTO.setDeliveryType("线下配送");
            }
            //动态判断调拨订单是是调入还是调出
            if (transferOrderQueryDTO.getConsumer().equals(transferOrderDTO.getInServiceProviderName())) {
                transferOrderDTO.setTransferType("调入");
            } else {
                transferOrderDTO.setTransferType("调出");
            }
            //填充地址信息
            addressDTO = new JXCMTBsAddressDTO();
            addressDTO.setName(addressMap.get(transferOrderDTO.getReceiveId()).getName());
            addressDTO.setMobile(addressMap.get(transferOrderDTO.getReceiveId()).getMobile());
            addressDTO.setAddress(addressMap.get(transferOrderDTO.getReceiveId()).getAddress());
            addressDTO.setProvinceName(addressMap.get(transferOrderDTO.getReceiveId()).getProvinceName());
            addressDTO.setCityName(addressMap.get(transferOrderDTO.getReceiveId()).getCityName());
            addressDTO.setAreaName(addressMap.get(transferOrderDTO.getReceiveId()).getAreaName());
            transferOrderDTO.setBsAddressDTO(addressDTO);
            if (!StringUtils.isEmpty(transferOrderDTO.getOrderTotal()) && !StringUtils.isEmpty(transferOrderDTO.getSendTotal())) {
                transferOrderDTO.setOweTotal(transferOrderDTO.getOrderTotal() - transferOrderDTO.getSendTotal());
            }
        }

        return transferOrderDTOList;
    }

    private ExportBsMdbTransferOrder generatorExportMdbTransferOrder(JXCBsTransferOrderQueryDTO transferOrderQueryDTO) {
        ExportBsMdbTransferOrder exportMerchantOrder = new ExportBsMdbTransferOrder();
        exportMerchantOrder.setStartTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getStartTime()));
        exportMerchantOrder.setEndTime(JxcUtils.getDateFromString(transferOrderQueryDTO.getEndTime()));
        exportMerchantOrder.setTranOrderCode(transferOrderQueryDTO.getTranOrderCode());
        exportMerchantOrder.setMaterialCode(transferOrderQueryDTO.getMaterialName());
        exportMerchantOrder.setMaterialName(transferOrderQueryDTO.getMaterialName());
        exportMerchantOrder.setOrderStatus(transferOrderQueryDTO.getOrderStatus());
        exportMerchantOrder.setServiceProviderName(transferOrderQueryDTO.getServiceProviderName());
        exportMerchantOrder.setOrderSource(transferOrderQueryDTO.getOrderSource());
        return exportMerchantOrder;
    }

    private ExportJxcMdbTransferOrder generatorExportJxcMdbTransferOrder(JXCTransferOrderQueryDTO transferOrderQueryDTO) {
        ExportJxcMdbTransferOrder exportMerchantOrder = new ExportJxcMdbTransferOrder();
        exportMerchantOrder.setOrderSource(transferOrderQueryDTO.getOrderSource());
        exportMerchantOrder.setTransferType(transferOrderQueryDTO.getTransferType());
        exportMerchantOrder.setMaterialName(transferOrderQueryDTO.getMaterialName());
        exportMerchantOrder.setOrderStatus(transferOrderQueryDTO.getOrderStatus());
        exportMerchantOrder.setInServiceProviderName(transferOrderQueryDTO.getInServiceProviderName());
        return exportMerchantOrder;
    }

}
