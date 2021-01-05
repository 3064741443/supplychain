package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.convert.BsAddressRpcConvert;
import cn.com.glsx.supplychain.jst.convert.BsLogisticsRpcConvert;
import cn.com.glsx.supplychain.jst.convert.OrderConvertRpcConvert;
import cn.com.glsx.supplychain.jst.convert.OrderDetailConvertRpcConvert;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.*;
import cn.com.glsx.supplychain.jst.mapper.*;
import cn.com.glsx.supplychain.jst.model.*;
import cn.com.glsx.supplychain.jst.util.JstUtils;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private BsMerchantStockDeviceMapper deviceMapper;  //暂时放这里
    @Autowired
    private JstShopOrderMapper shopOrderMapper;
    @Autowired
    private JstShopOrderDetailMapper shopOrderDetailMapper;
    @Autowired
    private BsMerchantOrderMapper merchantOrderMapper;
    @Autowired
    private BsMerchantOrderDetailMapper merchantOrderDetailMapper;
    @Autowired
    private OrderInfoMapper dispatchOrderInfoMapper;
    @Autowired
    private OrderInfoDetailMapper dispatchOrderInfoDetailMapper;
    @Autowired
    private BsAddressService addressService;
    @Autowired
    private BsLogisticsService logisticsService;
    @Autowired
    private JstShopService shopService;
    @Autowired
    private JstShopMapper jstShopMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private BsProductService bsProductService;
    @Autowired
    private CartService cartService;
    @Autowired
    private BsDealerUserInfoService dealerUserInfoService;
    @Autowired
    private EcMerchantOrderService ecMerchantOrderService;
    @Autowired
    private SnowflakeWorker snowflakeWorker;
    @Autowired
    private BsMerchantStockDeviceService stockDeviceService;
    @Autowired
    private DeviceFileService deviceFileService;
    @Autowired
    private DeviceMerchantReflectService merchantReflectService;
    @Autowired
    private deviceMerchantReflectMapper deviceMerchantReflectMapper;
    @Autowired
    private StatementSellMapper statementSellMapper;


    //根据发货单号获取发货单信息
    public OrderInfo getDispatchOrderInfoByDispatchOrderCode(String dispatchOrderCode) {
        OrderInfo condition = new OrderInfo();
        condition.setOrderCode(dispatchOrderCode);
        return dispatchOrderInfoMapper.selectOne(condition);
    }

    //获取订单详情列表
    public List<OrderInfoDetail> listOrderInfoDetail(OrderInfoDetail record) {
        Example example = new Example(OrderInfoDetail.class);
        example.createCriteria().andEqualTo("orderCode", record.getOrderCode())
                .andEqualTo("logisticsId", record.getLogisticsId());
        return dispatchOrderInfoDetailMapper.selectByExample(example);
    }

    //按照物流分组统计
    public List<OrderInfoDetail> ListCountOrderInfoDetailGroupByLogistics(List<String> listOrderCodes) {
        return dispatchOrderInfoDetailMapper.ListCountOrderInfoDetailGroupByLogistics(listOrderCodes);
    }

    //批量验证sn
    public CheckImportShopOrderDetailDTO batchSubmitShopOrderDetail(CheckImportShopOrderDetailDTO record) throws RpcServiceException {
        logger.info("OrderService::batchSubmitShopOrderDetail start record:{}", record);
        JstShopOrder orderCondition = new JstShopOrder();
        orderCondition.setShopOrderCode(record.getShopOrderCode());
        orderCondition.setDeletedFlag("N");
        JstShopOrder jstShopOrder = shopOrderMapper.selectOne(orderCondition);
        if (StringUtils.isEmpty(jstShopOrder)) {
            logger.info("OrderService::batchSubmitShopOrderDetail order not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOP_ORDER);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_OV.getValue())) {
            logger.info("OrderService::batchSubmitShopOrderDetail order status is ov!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_FINISHED);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_CL.getValue())) {
            logger.info("OrderService::batchSubmitShopOrderDetail order status is cl!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_CANCEL);
        }

        //获取订单地址
        BsLogistics bsLogistics = logisticsService.getLogisticsByShopOrderCode(record.getShopOrderCode(), LogisticsEnum.LOGISTICS_TYPE_6.getCode());
        if (StringUtils.isEmpty(bsLogistics) || StringUtils.isEmpty(bsLogistics.getReceiveId())) {
            logger.info("OrderService::batchSubmitShopOrderDetail order unknow address!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_ADDRESS);
        }

        //获取订单详情数量
        JstShopOrderDetail detailCondition = new JstShopOrderDetail();
        detailCondition.setShopOrderCode(record.getShopOrderCode());
        detailCondition.setDeletedFlag("N");
        Integer detailCount = shopOrderDetailMapper.selectCount(detailCondition);
        Integer count;
        if (record.getListShopOrderDetailDto() != null && record.getListShopOrderDetailDto().size() > 0) {
            count = record.getListShopOrderDetailDto().size();
        } else {
            count = 0;
        }
        if (MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode() == jstShopOrder.getMaterialType()) {
            if ((detailCount + count) > jstShopOrder.getTotal()) {
                logger.info("OrderService::batchSubmitShopOrderDetail over of order total!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_OVER_SHOP_ORDER_TOTAL);
            }
        } else {
            List<BsLogistics> logisticsList = logisticsService.getLogisticsListByShopOrderCode(jstShopOrder.getShopOrderCode());
            int sum = 0;
            for (BsLogistics model : logisticsList) {
                sum = sum + model.getShipmentsQuantity();
            }
            if ((detailCount + record.getShipmentsQuantity() + sum) > jstShopOrder.getTotal()) {
                logger.info("OrderService::batchSubmitShopOrderDetail over of order total!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_OVER_SHOP_ORDER_TOTAL);
            }
        }
        Map<String, BsMerchantStockDevice> mapStockDevice = new HashMap<String, BsMerchantStockDevice>();
        List<String> listSn = new ArrayList<String>();
        List<CheckShopOrderDetailDTO> listSuccess = new ArrayList<CheckShopOrderDetailDTO>();
        List<CheckShopOrderDetaiExportlDTO> listFailed = new ArrayList<CheckShopOrderDetaiExportlDTO>();
        if (record.getListShopOrderDetailDto() != null && record.getListShopOrderDetailDto().size() > 0) {
            for (CheckShopOrderDetailDTO detailDto : record.getListShopOrderDetailDto()) {
                listSn.add(detailDto.getSn());
            }
            Example example = new Example(BsMerchantStockDevice.class);
            example.createCriteria().andEqualTo("merchantCode", record.getMerchantCode())
                    .andEqualTo("deletedFlag", "N")
                    .andIn("sn", listSn);
            List<BsMerchantStockDevice> listMerchantStockDevice = deviceMapper.selectByExample(example);
            if (!StringUtils.isEmpty(listMerchantStockDevice)) {
                for (BsMerchantStockDevice stockDevice : listMerchantStockDevice) {
                    mapStockDevice.put(stockDevice.getSn(), stockDevice);
                }
            }
            for (CheckShopOrderDetailDTO detailDto : record.getListShopOrderDetailDto()) {
                CheckShopOrderDetaiExportlDTO checkShopOrderDetaiExportlDTO = new CheckShopOrderDetaiExportlDTO();
                BsMerchantStockDevice stockDevice = mapStockDevice.get(detailDto.getSn());
                if (StringUtils.isEmpty(stockDevice)) {
                    logger.info("OrderService::batchSubmitShopOrderDetail sn is not in stock!");
                    checkShopOrderDetaiExportlDTO.setSn(detailDto.getSn());
                    checkShopOrderDetaiExportlDTO.setFailDesc("sn:" + detailDto.getSn() + " 未入库");
                    listFailed.add(checkShopOrderDetaiExportlDTO);
                    continue;
                }
                if (!stockDevice.getStatu().equals("IN")) {
                    logger.info("OrderService::batchSubmitShopOrderDetail sn:" + stockDevice.getSn() + " is not in stock");
                    checkShopOrderDetaiExportlDTO.setSn(detailDto.getSn());
                    checkShopOrderDetaiExportlDTO.setFailDesc("sn:" + detailDto.getSn() + " 不在库存");
                    listFailed.add(checkShopOrderDetaiExportlDTO);
                    continue;
                }
                if (!stockDevice.getMerchantCode().equals(jstShopOrder.getAgentMerchantCode())) {
                    logger.info("OrderService::batchSubmitShopOrderDetail sn is not in stock!");
                    checkShopOrderDetaiExportlDTO.setSn(detailDto.getSn());
                    checkShopOrderDetaiExportlDTO.setFailDesc("sn:" + detailDto.getSn() + " 未入库");
                    listFailed.add(checkShopOrderDetaiExportlDTO);
                    continue;
                }
                listSuccess.add(detailDto);
            }
        }
        record.setListSuccess(listSuccess);
        record.setListFailed(listFailed);
        record.setSuccessCount(listSuccess.size());
        record.setFailCount(listSuccess.size());

        bsLogistics.setId(null);
        bsLogistics.setCompany(record.getConsumer());
        bsLogistics.setOrderNumber(record.getLogisticsDto().getOrderNumber());
        bsLogistics.setCompany(record.getLogisticsDto().getCompany());
        bsLogistics.setServiceCode(record.getShopOrderCode());
        bsLogistics.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
        bsLogistics.setShipmentsQuantity(record.getShipmentsQuantity());
        bsLogistics = logisticsService.getLogisticsByOrderNumber(bsLogistics);

        BsMerchantStockDevice deviceSn = null;
        BsMerchantStockDevice stockDeviceCondition = new BsMerchantStockDevice();
        List<JstShopOrderDetail> listShopOrderDetail = new ArrayList<JstShopOrderDetail>();
        for (CheckShopOrderDetailDTO detailDto : listSuccess) {
            deviceSn = mapStockDevice.get(detailDto.getSn());
            if (StringUtils.isEmpty(deviceSn)) {
                logger.info("OrderService::batchSubmitShopOrderDetail sn is not in stock!");
                String message = "sn:" + detailDto.getSn() + " 未入库";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
            if (!deviceSn.getStatu().equals("IN")) {
                logger.info("OrderService::batchSubmitShopOrderDetail sn:" + deviceSn.getSn() + " is not in stock");
                String message = "sn:" + detailDto.getSn() + " 不在库存";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }

            JstShopOrderDetail detail = new JstShopOrderDetail();
            detail.setAttribCode(deviceSn.getAttribCode());
            detail.setCreatedBy("system");
            detail.setCreatedDate(JstUtils.getNowDate());
            detail.setDeletedFlag("N");
            detail.setLogisticsId(bsLogistics.getId().intValue());
            detail.setShopOrderCode(record.getShopOrderCode());
            detail.setSn(deviceSn.getSn());
            detail.setUpdatedBy("system");
            detail.setUpdatedDate(JstUtils.getNowDate());
            listShopOrderDetail.add(detail);

            JstShop jstShop = new JstShop();
            jstShop.setShopCode(jstShopOrder.getShopCode());
            jstShop = jstShopMapper.selectOne(jstShop);
            stockDeviceCondition.setToMerchantCode(jstShop.getShopMerchantCode());
            stockDeviceCondition.setToMerchantName(jstShop.getShopMerchantName());
            stockDeviceCondition.setId(deviceSn.getId());
            stockDeviceCondition.setOutTime(JstUtils.getNowDate());
            stockDeviceCondition.setOutLogisticsId(bsLogistics.getId().intValue());
            stockDeviceCondition.setUpdatedDate(JstUtils.getNowDate());
            stockDeviceCondition.setStatu(MerchantStockDeviceEnum.STATU_OS.getValue());
            deviceMapper.updateByPrimaryKeySelective(stockDeviceCondition);
        }

        if (listShopOrderDetail != null && listShopOrderDetail.size() > 0) {
            shopOrderDetailMapper.insertList(listShopOrderDetail);
        }
        detailCount = shopOrderDetailMapper.selectCount(detailCondition);
        if (MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode() == jstShopOrder.getMaterialType()) {
            if (detailCount == jstShopOrder.getTotal()) {
                jstShopOrder.setStatus(ShopOrderStatuEnum.ORDER_OV.getValue());
                shopOrderMapper.updateByPrimaryKeySelective(jstShopOrder);
            }
        } else {
            List<BsLogistics> logisticsList = logisticsService.getLogisticsListByShopOrderCode(jstShopOrder.getShopOrderCode());
            int sum = 0;
            for (BsLogistics model : logisticsList) {
                sum = sum + model.getShipmentsQuantity();
            }
            if ((detailCount + sum) == jstShopOrder.getTotal()) {
                jstShopOrder.setStatus(ShopOrderStatuEnum.ORDER_OV.getValue());
                shopOrderMapper.updateByPrimaryKeySelective(jstShopOrder);
            }
        }


        logger.info("OrderService::batchSubmitShopOrderDetail end record:{}", record);
        return record;
    }


    //批量验证sn
    public CheckImportShopOrderDetailDTO batchCheckShopOrderDetail(CheckImportShopOrderDetailDTO record) throws RpcServiceException {
        logger.info("OrderService::batchCheckShopOrderDetail start record:{}", record);
        JstShopOrder orderCondition = new JstShopOrder();
        orderCondition.setShopOrderCode(record.getShopOrderCode());
        orderCondition.setDeletedFlag("N");
        JstShopOrder jstShopOrder = shopOrderMapper.selectOne(orderCondition);
        if (StringUtils.isEmpty(jstShopOrder)) {
            logger.info("OrderService::batchCheckShopOrderDetail order not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOP_ORDER);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_OV.getValue())) {
            logger.info("OrderService::batchCheckShopOrderDetail order status is ov!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_FINISHED);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_CL.getValue())) {
            logger.info("OrderService::batchCheckShopOrderDetail order status is cl!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_CANCEL);
        }
        //获取订单详情数量
        JstShopOrderDetail detailCondition = new JstShopOrderDetail();
        detailCondition.setShopOrderCode(record.getShopOrderCode());
        detailCondition.setDeletedFlag("N");
        Integer detailCount = shopOrderDetailMapper.selectCount(detailCondition);
        if ((detailCount + record.getListShopOrderDetailDto().size()) > jstShopOrder.getTotal()) {
            logger.info("OrderService::batchCheckShopOrderDetail over of order total!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_OVER_SHOP_ORDER_TOTAL);
        }

        //      Map<String, BsMerchantStockDevice> mapStockDevice = new HashMap<String, BsMerchantStockDevice>();
        List<String> listSn = new ArrayList<String>();
        List<CheckShopOrderDetailDTO> listSuccess = new ArrayList<CheckShopOrderDetailDTO>();
        List<CheckShopOrderDetaiExportlDTO> listFailed = new ArrayList<CheckShopOrderDetaiExportlDTO>();
        List<String> listSnNotInStock = new ArrayList<>();
        for (CheckShopOrderDetailDTO detailDto : record.getListShopOrderDetailDto()) {
            listSn.add(detailDto.getSn());
        }
        List<BsMerchantStockDevice> listMerchantStockDevice = stockDeviceService.listMerchantStockDeviceBySn(listSn);
        Map<String, BsMerchantStockDevice> mapMerchantStockDevice = null;
        if (null != listMerchantStockDevice && !listMerchantStockDevice.isEmpty()) {
            mapMerchantStockDevice = listMerchantStockDevice.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, Function.identity(), (key1, key2) -> key2));
        }
        BsMerchantStockDevice bsMerchantStockDevice = null;
        for (CheckShopOrderDetailDTO itemDto : record.getListShopOrderDetailDto()) {
            if (null != mapMerchantStockDevice) {
                bsMerchantStockDevice = mapMerchantStockDevice.get(itemDto.getSn());
                if (null == bsMerchantStockDevice) {
                    listSnNotInStock.add(itemDto.getSn());
                }
            } else {
                listSnNotInStock.add(itemDto.getSn());
            }
        }
        if (null == mapMerchantStockDevice) {
            mapMerchantStockDevice = new HashMap<>();
        }
        Map<String, deviceMerchantReflect> mapMerchantReflect = new HashMap<>();
        List<DeviceSnNotInMerchantStock> listDeviceSnNotInMerchantStock = deviceFileService.listSnNotInMerchantStock(listSnNotInStock);
        List<BsMerchantStockDevice> listNewStockDevice = new ArrayList<>();
        deviceMerchantReflect merchantReflect = null;
        if (null != listDeviceSnNotInMerchantStock && !listDeviceSnNotInMerchantStock.isEmpty()) {
            for (DeviceSnNotInMerchantStock stock : listDeviceSnNotInMerchantStock) {
                merchantReflect = getDeviceMerchantReflect(stock.getSendMerchantNo(), mapMerchantReflect);
                if (null == merchantReflect) {
                    continue;
                }
                BsMerchantStockDevice stockDevice = new BsMerchantStockDevice();
                stockDevice.setSn(stock.getSn());
                stockDevice.setAttribCode(stock.getAttribCode());
                stockDevice.setIccid(stock.getIccid());
                stockDevice.setMerchantCode(merchantReflect.getServerMerchantCode());
                stockDevice.setMerchantName(merchantReflect.getServerMerchantName());
                stockDevice.setInTime(stock.getUpdatedDate());
                stockDevice.setInLogisticsId(stock.getLogisticsId());
                stockDevice.setStatu("IN");
                stockDevice.setCreatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setCreatedDate(JstUtils.getNowDate());
                stockDevice.setUpdatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setUpdatedDate(JstUtils.getNowDate());
                stockDevice.setDeletedFlag("N");
                listNewStockDevice.add(stockDevice);
                mapMerchantStockDevice.put(stockDevice.getSn(), stockDevice);
            }
        }
        stockDeviceService.batchAddMerchantStockDevice(listNewStockDevice);

        for (CheckShopOrderDetailDTO detailDto : record.getListShopOrderDetailDto()) {
            CheckShopOrderDetaiExportlDTO checkShopOrderDetaiExportlDTO = new CheckShopOrderDetaiExportlDTO();
            BsMerchantStockDevice stockDevice = mapMerchantStockDevice.get(detailDto.getSn());
            if (StringUtils.isEmpty(stockDevice)) {
                logger.info("OrderService::batchCheckShopOrderDetail sn is not in stock!");
                checkShopOrderDetaiExportlDTO.setSn(detailDto.getSn());
                checkShopOrderDetaiExportlDTO.setFailDesc("sn:" + detailDto.getSn() + " 未入库");
                listFailed.add(checkShopOrderDetaiExportlDTO);
                continue;
            }
            if (!stockDevice.getStatu().equals("IN")) {
                logger.info("OrderService::batchCheckShopOrderDetail sn:" + stockDevice.getSn() + " is not in stock");
                checkShopOrderDetaiExportlDTO.setSn(detailDto.getSn());
                checkShopOrderDetaiExportlDTO.setFailDesc("sn:" + detailDto.getSn() + " 不在库存");
                listFailed.add(checkShopOrderDetaiExportlDTO);
                continue;
            }
            if (!stockDevice.getMerchantCode().equals(jstShopOrder.getAgentMerchantCode())) {
                logger.info("OrderService::batchCheckShopOrderDetail sn is not in stock!");
                checkShopOrderDetaiExportlDTO.setSn(detailDto.getSn());
                checkShopOrderDetaiExportlDTO.setFailDesc("sn:" + detailDto.getSn() + " 未入库");
                listFailed.add(checkShopOrderDetaiExportlDTO);
                continue;
            }
            listSuccess.add(detailDto);
        }
        record.setListSuccess(listSuccess);
        record.setListFailed(listFailed);
        record.setSuccessCount(listSuccess.size());
        record.setFailCount(listFailed.size());
        logger.info("OrderService::batchCheckShopOrderDetail end record:{}", record);
        return record;
    }

    //批量验证sn-无订单发货
    public CheckImportNoOrderDetailDTO batchCheckShopOrderDetailNoOrder(CheckImportNoOrderDetailDTO record) throws RpcServiceException {
        List<String> listSn = new ArrayList<String>();
        List<String> listSnNotInStock = new ArrayList<>();
        List<NoOrderDetailImportDTO> listSuccess = new ArrayList<NoOrderDetailImportDTO>();
        List<NoOrderDetailExportDTO> listFailed = new ArrayList<NoOrderDetailExportDTO>();
        List<NoOrderDetailImportDTO> noOrderDetailImportDTOList = new ArrayList<>();
        Map<String, NoOrderDetailImportDTO> map = new HashMap<>();
        for (NoOrderDetailImportDTO detailDto : record.getNoOrderDetailImportDTOList()) {
            map.put(detailDto.getSn(), detailDto);
        }
        for (String key : map.keySet()) {
            listSn.add(map.get(key).getSn());
            noOrderDetailImportDTOList.add(map.get(key));
        }
        List<BsMerchantStockDevice> listMerchantStockDevice = stockDeviceService.listMerchantStockDeviceBySn(listSn);
        Map<String, BsMerchantStockDevice> mapMerchantStockDevice = null;
        if (null != listMerchantStockDevice && !listMerchantStockDevice.isEmpty()) {
            mapMerchantStockDevice = listMerchantStockDevice.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, Function.identity(), (key1, key2) -> key2));
        }
        BsMerchantStockDevice bsMerchantStockDevice = null;
        for (String key : map.keySet()) {
            if (null != mapMerchantStockDevice) {
                bsMerchantStockDevice = mapMerchantStockDevice.get(map.get(key).getSn());
                if (null == bsMerchantStockDevice) {
                    listSnNotInStock.add(map.get(key).getSn());
                }
            } else {
                listSnNotInStock.add(map.get(key).getSn());
            }
        }
        if (null == mapMerchantStockDevice) {
            mapMerchantStockDevice = new HashMap<>();
        }
        Map<String, deviceMerchantReflect> mapMerchantReflect = new HashMap<>();
        List<DeviceSnNotInMerchantStock> listDeviceSnNotInMerchantStock = deviceFileService.listSnNotInMerchantStock(listSnNotInStock);
        List<BsMerchantStockDevice> listNewStockDevice = new ArrayList<>();
        deviceMerchantReflect merchantReflect = null;
        if (null != listDeviceSnNotInMerchantStock && !listDeviceSnNotInMerchantStock.isEmpty()) {
            for (DeviceSnNotInMerchantStock stock : listDeviceSnNotInMerchantStock) {
                merchantReflect = getDeviceMerchantReflect(stock.getSendMerchantNo(), mapMerchantReflect);
                if (null == merchantReflect) {
                    continue;
                }
                BsMerchantStockDevice stockDevice = new BsMerchantStockDevice();
                stockDevice.setSn(stock.getSn());
                stockDevice.setAttribCode(stock.getAttribCode());
                stockDevice.setIccid(stock.getIccid());
                stockDevice.setMerchantCode(merchantReflect.getServerMerchantCode());
                stockDevice.setMerchantName(merchantReflect.getServerMerchantName());
                stockDevice.setInTime(stock.getUpdatedDate());
                stockDevice.setInLogisticsId(stock.getLogisticsId());
                stockDevice.setStatu("IN");
                stockDevice.setCreatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setCreatedDate(JstUtils.getNowDate());
                stockDevice.setUpdatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setUpdatedDate(JstUtils.getNowDate());
                stockDevice.setDeletedFlag("N");
                listNewStockDevice.add(stockDevice);
                mapMerchantStockDevice.put(stockDevice.getSn(), stockDevice);
            }
        }
        stockDeviceService.batchAddMerchantStockDevice(listNewStockDevice);

        for (String key : map.keySet()) {
            NoOrderDetailExportDTO orderDetailExportDTO = new NoOrderDetailExportDTO();
            BsMerchantStockDevice stockDevice = mapMerchantStockDevice.get(map.get(key).getSn());
            if (StringUtils.isEmpty(stockDevice)) {
                logger.info("OrderService::batchCheckShopOrderDetailNoOrder sn is not in stock!");
                orderDetailExportDTO.setSn(map.get(key).getSn());
                orderDetailExportDTO.setFailDesc("sn" + " 未入库");
                listFailed.add(orderDetailExportDTO);
                continue;
            }
            if (!stockDevice.getStatu().equals("IN")) {
                logger.info("OrderService::batchCheckShopOrderDetail sn:" + stockDevice.getSn() + " is not in stock");
                orderDetailExportDTO.setSn(map.get(key).getSn());
                orderDetailExportDTO.setFailDesc("sn" + " 不在库存");
                listFailed.add(orderDetailExportDTO);
                continue;
            }
            if (!stockDevice.getMerchantCode().equals(record.getMerchantCode())) {
                logger.info("OrderService::batchCheckShopOrderDetail sn is not in stock!");
                orderDetailExportDTO.setSn(map.get(key).getSn());
                orderDetailExportDTO.setFailDesc("sn" + " 未入库");
                listFailed.add(orderDetailExportDTO);
                continue;
            }
            listSuccess.add(map.get(key));

        }
        record.setListSuccess(listSuccess);
        record.setListFailed(listFailed);
        record.setSuccessCount(listSuccess.size());
        record.setFailCount(listFailed.size());
        record.setNoOrderDetailImportDTOList(noOrderDetailImportDTOList);
        record.setTotalCount(noOrderDetailImportDTOList.size());
        return record;
    }

    //验证sn-无订单发货
    public Integer checkShopOrderDetailNoOrder(SubOrderDetailDTO record) throws RpcServiceException {
        Integer result = 0;

        List<String> listSn = new ArrayList<>();
        List<String> listSnNotInStock = new ArrayList<>();
        for (OrderDetailDTO itemDto : record.getListDetailDto()) {
            listSn.add(itemDto.getSn());
        }
        List<BsMerchantStockDevice> listMerchantStockDevice = stockDeviceService.listMerchantStockDeviceBySn(listSn);
        Map<String, BsMerchantStockDevice> mapMerchantStockDevice = null;
        if (null != listMerchantStockDevice && !listMerchantStockDevice.isEmpty()) {
            mapMerchantStockDevice = listMerchantStockDevice.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, Function.identity(), (key1, key2) -> key2));
        }
        BsMerchantStockDevice bsMerchantStockDevice = null;
        for (OrderDetailDTO itemDto : record.getListDetailDto()) {
            if (null != mapMerchantStockDevice) {
                bsMerchantStockDevice = mapMerchantStockDevice.get(itemDto.getSn());
                if (null == bsMerchantStockDevice) {
                    listSnNotInStock.add(itemDto.getSn());
                }
            } else {
                listSnNotInStock.add(itemDto.getSn());
            }
        }
        if (null == mapMerchantStockDevice) {
            mapMerchantStockDevice = new HashMap<>();
        }
        Map<String, deviceMerchantReflect> mapMerchantReflect = new HashMap<>();
        List<DeviceSnNotInMerchantStock> listDeviceSnNotInMerchantStock = deviceFileService.listSnNotInMerchantStock(listSnNotInStock);
        List<BsMerchantStockDevice> listNewStockDevice = new ArrayList<>();
        deviceMerchantReflect merchantReflect = null;
        if (null != listDeviceSnNotInMerchantStock && !listDeviceSnNotInMerchantStock.isEmpty()) {
            for (DeviceSnNotInMerchantStock stock : listDeviceSnNotInMerchantStock) {
                merchantReflect = getDeviceMerchantReflect(stock.getSendMerchantNo(), mapMerchantReflect);
                if (null == merchantReflect) {
                    continue;
                }
                BsMerchantStockDevice stockDevice = new BsMerchantStockDevice();
                stockDevice.setSn(stock.getSn());
                stockDevice.setAttribCode(stock.getAttribCode());
                stockDevice.setIccid(stock.getIccid());
                stockDevice.setMerchantCode(merchantReflect.getServerMerchantCode());
                stockDevice.setMerchantName(merchantReflect.getServerMerchantName());
                stockDevice.setInTime(stock.getUpdatedDate());
                stockDevice.setInLogisticsId(stock.getLogisticsId());
                stockDevice.setStatu("IN");
                stockDevice.setCreatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setCreatedDate(JstUtils.getNowDate());
                stockDevice.setUpdatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setUpdatedDate(JstUtils.getNowDate());
                stockDevice.setDeletedFlag("N");
                listNewStockDevice.add(stockDevice);
                mapMerchantStockDevice.put(stockDevice.getSn(), stockDevice);
            }
        }
        stockDeviceService.batchAddMerchantStockDevice(listNewStockDevice);

        BsMerchantStockDevice deviceSn = null;
        for (OrderDetailDTO detailDto : record.getListDetailDto()) {
            //验证商户库存           
            deviceSn = mapMerchantStockDevice.get(detailDto.getSn());
            if (StringUtils.isEmpty(deviceSn)) {
                logger.info("OrderService::checkShopOrderDetailNoOrder sn is not in stock!");
                String message = "sn:" + detailDto.getSn() + " 未入库";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
            if (!deviceSn.getStatu().equals("IN")) {
                logger.info("OrderService::checkShopOrderDetailNoOrder sn:" + deviceSn.getSn() + " is not in stock");
                String message = "sn:" + detailDto.getSn() + " 不在库存";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
            if (!deviceSn.getMerchantCode().equals(record.getAgentMerchantCode())) {
                logger.info("OrderService::submitShopOrderDetail sn is not in stock!");
                String message = "sn:" + detailDto.getSn() + " 未入库";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
        }
        return result;
    }

    //获取发货明细-无订单发货
    public List<JstShopShoppingCartDTO> getShopOrderDetailNoOrder(SubOrderDetailDTO record) {
        List<JstShopShoppingCartDTO> listResult = new ArrayList<>();
        List<String> listSn = new ArrayList<>();
        for (OrderDetailDTO dto : record.getListDetailDto()) {
            listSn.add(dto.getSn());
        }
        if (listSn.isEmpty()) {
            return listResult;
        }
        List<OrderInfoDetail> listDetail = dispatchOrderInfoDetailMapper.getShopOrderDetailNoOrder(listSn);
        Map<String, OrderInfoDetail> mapOrderInfoDetail = new HashMap<>();
        OrderInfoDetail orderInfoDetail = null;
        if (null != listDetail) {
            for (OrderInfoDetail detail : listDetail) {
                orderInfoDetail = mapOrderInfoDetail.get(detail.getSn());
                if (null != orderInfoDetail) {
                    continue;
                }
                mapOrderInfoDetail.put(detail.getSn(), detail);
            }
        }

        for (OrderDetailDTO subDto : record.getListDetailDto()) {
            JstShopShoppingCartDTO dto = new JstShopShoppingCartDTO();
            orderInfoDetail = mapOrderInfoDetail.get(subDto.getSn());
            if (null != orderInfoDetail) {
                dto.setProductCode(orderInfoDetail.getProductCode());
                dto.setProductName(orderInfoDetail.getProductName());
                dto.setMaterialCode(orderInfoDetail.getMaterialCode());
                dto.setMaterialName(orderInfoDetail.getMaterialName());
                dto.setSn(subDto.getSn());
            } else {
                dto.setProductCode(Constants.SYSPRODUCTCODE);
                dto.setProductName(Constants.SYSPRODUCTNAME);
                dto.setMaterialCode(Constants.SYSMATERIALCODE);
                dto.setMaterialName(Constants.SYSMATERIALNAME);
                dto.setSn(subDto.getSn());
            }
            listResult.add(dto);
        }
        return listResult;
    }


    //验证sn
    public Integer checkShopOrderDetail(SubOrderDetailDTO record) throws RpcServiceException {
        Integer result = 0;
        logger.info("OrderService::checkShopOrderDetail start record:{}", record);
        JstShopOrder orderCondition = new JstShopOrder();
        orderCondition.setShopOrderCode(record.getOrderCode());
        orderCondition.setDeletedFlag("N");
        JstShopOrder jstShopOrder = shopOrderMapper.selectOne(orderCondition);
        if (StringUtils.isEmpty(jstShopOrder)) {
            logger.info("OrderService::checkShopOrderDetail order not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOP_ORDER);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_OV.getValue())) {
            logger.info("OrderService::checkShopOrderDetail order status is ov!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_FINISHED);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_CL.getValue())) {
            logger.info("OrderService::checkShopOrderDetail order status is cl!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_CANCEL);
        }
        //获取订单详情数量
        JstShopOrderDetail detailCondition = new JstShopOrderDetail();
        detailCondition.setShopOrderCode(record.getOrderCode());
        detailCondition.setDeletedFlag("N");
        Integer detailCount = shopOrderDetailMapper.selectCount(detailCondition);
        if ((detailCount + record.getListDetailDto().size()) > jstShopOrder.getTotal()) {
            logger.info("OrderService::checkShopOrderDetail over of order total!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_OVER_SHOP_ORDER_TOTAL);
        }

        List<String> listSn = new ArrayList<>();
        List<String> listSnNotInStock = new ArrayList<>();
        for (OrderDetailDTO itemDto : record.getListDetailDto()) {
            listSn.add(itemDto.getSn());
        }
        List<BsMerchantStockDevice> listMerchantStockDevice = stockDeviceService.listMerchantStockDeviceBySn(listSn);
        Map<String, BsMerchantStockDevice> mapMerchantStockDevice = null;
        if (null != listMerchantStockDevice && !listMerchantStockDevice.isEmpty()) {
            mapMerchantStockDevice = listMerchantStockDevice.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, Function.identity(), (key1, key2) -> key2));
        }
        BsMerchantStockDevice bsMerchantStockDevice = null;
        for (OrderDetailDTO itemDto : record.getListDetailDto()) {
            if (null != mapMerchantStockDevice) {
                bsMerchantStockDevice = mapMerchantStockDevice.get(itemDto.getSn());
                if (null == bsMerchantStockDevice) {
                    listSnNotInStock.add(itemDto.getSn());
                }
            } else {
                listSnNotInStock.add(itemDto.getSn());
            }
        }
        if (null == mapMerchantStockDevice) {
            mapMerchantStockDevice = new HashMap<>();
        }
        Map<String, deviceMerchantReflect> mapMerchantReflect = new HashMap<>();
        List<DeviceSnNotInMerchantStock> listDeviceSnNotInMerchantStock = deviceFileService.listSnNotInMerchantStock(listSnNotInStock);
        List<BsMerchantStockDevice> listNewStockDevice = new ArrayList<>();
        deviceMerchantReflect merchantReflect = null;
        if (null != listDeviceSnNotInMerchantStock && !listDeviceSnNotInMerchantStock.isEmpty()) {
            for (DeviceSnNotInMerchantStock stock : listDeviceSnNotInMerchantStock) {
                merchantReflect = getDeviceMerchantReflect(stock.getSendMerchantNo(), mapMerchantReflect);
                if (null == merchantReflect) {
                    continue;
                }
                BsMerchantStockDevice stockDevice = new BsMerchantStockDevice();
                stockDevice.setSn(stock.getSn());
                stockDevice.setAttribCode(stock.getAttribCode());
                stockDevice.setIccid(stock.getIccid());
                stockDevice.setMerchantCode(merchantReflect.getServerMerchantCode());
                stockDevice.setMerchantName(merchantReflect.getServerMerchantName());
                stockDevice.setInTime(stock.getUpdatedDate());
                stockDevice.setInLogisticsId(stock.getLogisticsId());
                stockDevice.setStatu("IN");
                stockDevice.setCreatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setCreatedDate(JstUtils.getNowDate());
                stockDevice.setUpdatedBy(merchantReflect.getServerMerchantCode());
                stockDevice.setUpdatedDate(JstUtils.getNowDate());
                stockDevice.setDeletedFlag("N");
                listNewStockDevice.add(stockDevice);
                mapMerchantStockDevice.put(stockDevice.getSn(), stockDevice);
            }
        }
        stockDeviceService.batchAddMerchantStockDevice(listNewStockDevice);

        BsMerchantStockDevice deviceCondition = new BsMerchantStockDevice();
        BsMerchantStockDevice deviceSn = null;
        for (OrderDetailDTO detailDto : record.getListDetailDto()) {
            deviceSn = mapMerchantStockDevice.get(detailDto.getSn());
            if (StringUtils.isEmpty(deviceSn)) {
                logger.info("OrderService::submitShopOrderDetail sn is not in stock!");
                String message = "sn:" + detailDto.getSn() + " 未入库";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
            if (!deviceSn.getStatu().equals("IN")) {
                logger.info("OrderService::submitShopOrderDetail sn:" + deviceSn.getSn() + " is not in stock");
                String message = "sn:" + detailDto.getSn() + " 不在库存";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
            if (!deviceSn.getMerchantCode().equals(jstShopOrder.getAgentMerchantCode())) {
                logger.info("OrderService::submitShopOrderDetail sn is not in stock!");
                String message = "sn:" + detailDto.getSn() + " 未入库";
                JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
            }
        }
        result = jstShopOrder.getTotal() - detailCount;
        return result;
    }

    private deviceMerchantReflect getDeviceMerchantReflect(String sendMerchantCode, Map<String, deviceMerchantReflect> mapMerchantReflect) {
        deviceMerchantReflect result = mapMerchantReflect.get(sendMerchantCode);
        if (null != result) {
            return result;
        }
        result = merchantReflectService.getMerchantReflectBySendToMerchant(sendMerchantCode);
        if (null != result) {
            mapMerchantReflect.put(result.getSendToMerchantCode(), result);
        }
        return result;
    }


    //扫码-提交订单明细 - 无订单发货
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer submitShopOrderDetailNoOrder(SubOrderDetailDTO record) throws RpcServiceException {
        Integer result = 0;
        logger.info("OrderService::submitShopOrderDetailNoOrder start record:{}", record);
        //获取门店信息
        JstShop jstShop = getJstShopByShopCode(record.getShopCode());
        if (null == jstShop) {
            logger.info("OrderService::submitShopOrderDetailNoOrder jst shop not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOP);
        }
        //获取商户用户信息接口
        BsDealerUserInfo dealerUserInfo = dealerUserInfoService.getBsDealerUserInfoByMerchantCode(record.getAgentMerchantCode());
        if (null == dealerUserInfo) {
            logger.info("OrderService::submitShopOrderDetailNoOrder dealerUserInfo not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER);
        }
        //获取地址信息
        record.getAddressDto().setMerchantCode(jstShop.getShopMerchantCode());
        record.getAddressDto().setConsumer(record.getConsumer());
        BsAddress bsAddress = getBsAddress(record.getAddressDto());
        if (null == bsAddress) {
            logger.info("OrderService::submitShopOrderDetailNoOrder jst invalid address!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_ADDRESS);
        }
        //获取库存信息sn->merchantstockdevice
        List<String> listSn = new ArrayList<>();
        for (OrderDetailDTO dto : record.getListDetailDto()) {
            listSn.add(dto.getSn());
        }
        Map<String, BsMerchantStockDevice> mapStockDevice = getMerchantStockDevice(listSn, record.getAgentMerchantCode());
        if (null == mapStockDevice) {
            logger.info("OrderService::submitShopOrderDetailNoOrder sn not in merchant stock!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
        }

        //生成购物车信息
        List<JstShopShoppingCart> listShoppingCart = genJstShopShoppingCard(listSn);
        if (null == listShoppingCart) {
            logger.info("OrderService::submitShopOrderDetailNoOrder failed gen shopping cart!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_FAILED_GEN_SHOPPING_CARD);
        }
        //生成订单信息 订单详情 等信息
        try {
            List<BsLogistics> listLogistics = new ArrayList<>();
            List<JstShopOrder> listShopOrder = new ArrayList<JstShopOrder>();
            List<JstShopOrderDetail> listShopOrderDetail = new ArrayList<JstShopOrderDetail>();
            Map<String, ProductDTO> mapProduct = new HashMap<String, ProductDTO>();
            for (JstShopShoppingCart cart : listShoppingCart) {
                if (null == cart.getListSn() || cart.getListSn().isEmpty()) {
                    continue;
                }
                ProductDTO product = getProductFromCache(cart.getProductCode(), mapProduct);
                String orderCode = Constants.SHOP_ORDER_PREFIX_AUTO + JstUtils.getDispatchOrderNumber(snowflakeWorker);
                String logiCode = Constants.LOGI_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
                String offineCode = Constants.OFFLINE_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);

                BsLogistics logistics = null;
                BsLogistics logisticsCondition = null;
                BsLogistics bsLogistics = null;
                //线上配送物流信息
                if (record.getShipType() != null && record.getShipType().equalsIgnoreCase(Constants.ONLINE_DELIVERY)) {
                    logistics = new BsLogistics();
                    logistics.setCode(Constants.LOGI_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker));
                    logistics.setType(LogisticsEnum.LOGISTICS_TYPE_6.getCode());
                    logistics.setServiceCode(orderCode);
                    logistics.setReceiveId(bsAddress.getId());
                    logistics.setCreatedBy(jstShop.getShopMerchantCode());
                    logistics.setUpdatedBy(jstShop.getShopMerchantCode());
                    logistics.setCreatedDate(JstUtils.getNowDate());
                    logistics.setUpdatedDate(JstUtils.getNowDate());
                    logistics.setDeletedFlag("N");
                    listLogistics.add(logistics);

                    logisticsCondition = new BsLogistics();
                    logisticsCondition.setReceiveId(bsAddress.getId());
                    logisticsCondition.setCode(logiCode);
                    logisticsCondition.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
                    logisticsCondition.setServiceCode(orderCode);
                    logisticsCondition.setCompany(record.getLogisticsDto().getCompany());
                    logisticsCondition.setOrderNumber(record.getLogisticsDto().getOrderNumber());
                    logisticsCondition.setCreatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setUpdatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setCreatedDate(JstUtils.getNowDate());
                    logisticsCondition.setUpdatedDate(JstUtils.getNowDate());
                    logisticsCondition.setDeletedFlag("N");
                    bsLogistics = logisticsService.getLogisticsByOrderNumber(logisticsCondition);
                }

                //线下配送物流信息
                if (record.getShipType() != null && record.getShipType().equalsIgnoreCase(Constants.OFFLINE_DELIVERY)) {
                    logistics = new BsLogistics();
                    logistics.setCode(logiCode);
                    logistics.setType(LogisticsEnum.LOGISTICS_TYPE_6.getCode());
                    logistics.setServiceCode(orderCode);
                    logistics.setReceiveId(bsAddress.getId());
                    logistics.setCreatedBy(jstShop.getShopMerchantCode());
                    logistics.setUpdatedBy(jstShop.getShopMerchantCode());
                    logistics.setCreatedDate(JstUtils.getNowDate());
                    logistics.setUpdatedDate(JstUtils.getNowDate());
                    logistics.setDeletedFlag("N");
                    listLogistics.add(logistics);

                    logisticsCondition = new BsLogistics();
                    logisticsCondition.setReceiveId(bsAddress.getId());
                    logisticsCondition.setCode(logiCode);
                    logisticsCondition.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
                    logisticsCondition.setServiceCode(orderCode);
                    logisticsCondition.setCompany("线下配送");
                    logisticsCondition.setOrderNumber(offineCode);
                    logisticsCondition.setCreatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setUpdatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setCreatedDate(JstUtils.getNowDate());
                    logisticsCondition.setUpdatedDate(JstUtils.getNowDate());
                    logisticsCondition.setSendTime(JstUtils.getStringFromDate(JstUtils.getNowDate()));
                    logisticsCondition.setDeletedFlag("N");
                    bsLogistics = logisticsService.getLogisticsByOrderNumber(logisticsCondition);
                }

                JstShopOrder jstShopOrder = new JstShopOrder();
                jstShopOrder.setShopOrderCode(orderCode);
                jstShopOrder.setShopCode(jstShop.getShopCode());
                jstShopOrder.setShopName(jstShop.getShopName());
                jstShopOrder.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
                jstShopOrder.setAgentMerchantName(dealerUserInfo.getMerchantName());
                jstShopOrder.setProductCode((product == null) ? Constants.SYSPRODUCTCODE : product.getProductCode());
                jstShopOrder.setProductName((product == null) ? Constants.SYSPRODUCTNAME : product.getProductName());
                jstShopOrder.setServiceType((product == null) ? Constants.SYSSERVICETYPE : product.getServiceType());
                jstShopOrder.setServiceTime((product == null) ? Constants.SYSSERVICETIME : product.getServiceTime());
                jstShopOrder.setMaterialCode(cart.getMaterialCode());
                jstShopOrder.setMaterialName(cart.getMaterialName());
                jstShopOrder.setMaterialType(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode());
                jstShopOrder.setTotal(cart.getListSn().size());
                jstShopOrder.setPrice((product == null) ? 0.0 : product.getPrice());
                jstShopOrder.setProductSplitPriceTime((product == null) ? JstUtils.getNowDate() : product.getPriceTime());
                jstShopOrder.setPackageOne((product == null) ? Constants.SYSPACKAGEONE : product.getPackageOne());
                jstShopOrder.setStatus(ShopOrderStatuEnum.ORDER_OV.getValue());
                jstShopOrder.setCreatedBy(jstShop.getShopMerchantCode());
                jstShopOrder.setUpdatedBy(jstShop.getShopMerchantCode());
                jstShopOrder.setCreatedDate(JstUtils.getNowDate());
                jstShopOrder.setUpdatedDate(JstUtils.getNowDate());
                jstShopOrder.setScanType(Constants.SCAN_CODE);
                jstShopOrder.setDeletedFlag("N");
                listShopOrder.add(jstShopOrder);

                //生成订单明细
                JstShopOrderDetail detail = null;
                //扫码订单明细生成
                for (String sn : cart.getListSn()) {
                    BsMerchantStockDevice deviceSn = mapStockDevice.get(sn);
                    detail = new JstShopOrderDetail();
                    detail.setAttribCode(deviceSn.getAttribCode());
                    detail.setCreatedBy("system");
                    detail.setCreatedDate(JstUtils.getNowDate());
                    detail.setDeletedFlag("N");
                    detail.setLogisticsId(bsLogistics.getId().intValue());
                    detail.setShopOrderCode(orderCode);
                    detail.setSn(sn);
                    detail.setUpdatedBy("system");
                    detail.setUpdatedDate(JstUtils.getNowDate());
                    detail.setVtSn(Constants.SCAN_CODE);
                    listShopOrderDetail.add(detail);

                    deviceSn.setOutTime(JstUtils.getNowDate());
                    deviceSn.setOutLogisticsId(bsLogistics.getId().intValue());
                    deviceSn.setToMerchantCode(jstShop.getShopMerchantCode());
                    deviceSn.setToMerchantName(jstShop.getShopMerchantName());
                    deviceSn.setUpdatedDate(JstUtils.getNowDate());
                    deviceSn.setStatu(MerchantStockDeviceEnum.STATU_OS.getValue());
                    deviceSn.setVtSn(Constants.SCAN_CODE);
                    result++;
                }
            }
            List<BsMerchantStockDevice> listStockDevice = mapStockDevice.values().stream().collect(Collectors.toList());
            logisticsService.insertLogisticsList(listLogistics);
            shopOrderMapper.insertList(listShopOrder);
            shopOrderDetailMapper.insertList(listShopOrderDetail);
            deviceMapper.batchBatchInsertReplace(listStockDevice);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        return result;
    }

    //不扫码-提交订单明细 - 无订单发货
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer noScanCodeWxSubShopOrderDetailNoOrder(SubOrderDetailDTO record) throws RpcServiceException {
        Integer result = 0;
        logger.info("OrderService::submitShopOrderDetailNoOrder start record:{}", record);
        //填充虚拟设备信息
       /* List<OrderDetailDTO> listDetail = new ArrayList<>();
        OrderDetailDTO newOrderDetaiDto = null;
        if (record.getScanType() != null && record.getScanType().equalsIgnoreCase(Constants.NO_SCAN_CODE)) {
            if (record.getListDetailDto() == null || record.getListDetailDto().size() == 0) {
                for (int i = 0; i < record.getMaterialDTOList().size(); i++) {
                    newOrderDetaiDto = new OrderDetailDTO();
                    newOrderDetaiDto.setSn(Constants.VT_SN_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker));
                    newOrderDetaiDto.setAttribCode(record.getMaterialDTOList().get(i).getMaterialCode());
                    listDetail.add(newOrderDetaiDto);
                }
            }
            record.setListDetailDto(listDetail);
        }*/
        //获取门店信息
        JstShop jstShop = getJstShopByShopCode(record.getShopCode());
        if (null == jstShop) {
            logger.info("OrderService::submitShopOrderDetailNoOrder jst shop not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOP);
        }
        //获取商户用户信息接口
        BsDealerUserInfo dealerUserInfo = dealerUserInfoService.getBsDealerUserInfoByMerchantCode(record.getAgentMerchantCode());
        if (null == dealerUserInfo) {
            logger.info("OrderService::submitShopOrderDetailNoOrder dealerUserInfo not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER);
        }
        //获取地址信息
        record.getAddressDto().setMerchantCode(jstShop.getShopMerchantCode());
        record.getAddressDto().setConsumer(record.getConsumer());
        BsAddress bsAddress = getBsAddress(record.getAddressDto());
        if (null == bsAddress) {
            logger.info("OrderService::submitShopOrderDetailNoOrder jst invalid address!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_ADDRESS);
        }
        //获取库存信息sn->merchantstockdevice
        List<String> listSn = new ArrayList<>();
        for (OrderDetailDTO dto : record.getListDetailDto()) {
            listSn.add(dto.getSn());
        }
        //不扫码生成购物车信息
        List<JstShopShoppingCart> listShoppingCart = noScanCodeGenJstShopShoppingCard(record.getMaterialDTOList());
        if (null == listShoppingCart) {
            logger.info("OrderService::submitShopOrderDetailNoOrder failed gen shopping cart!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_FAILED_GEN_SHOPPING_CARD);
        }
        //生成订单信息 订单详情 等信息
        try {
            List<BsLogistics> listLogistics = new ArrayList<>();
            List<JstShopOrder> listShopOrder = new ArrayList<JstShopOrder>();
            List<JstShopOrderDetail> listShopOrderDetail = new ArrayList<JstShopOrderDetail>();
            Map<String, ProductDTO> mapProduct = new HashMap<String, ProductDTO>();
            List<BsMerchantStockDevice> bsMerchantStockDeviceList = new ArrayList<>();
            for (JstShopShoppingCart cart : listShoppingCart) {
                ProductDTO product = getProductFromCache(cart.getProductCode(), mapProduct);
                String orderCode = Constants.SHOP_ORDER_PREFIX_AUTO + JstUtils.getDispatchOrderNumber(snowflakeWorker);
                String logiCode = Constants.LOGI_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
                String offineCode = Constants.OFFLINE_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);

                BsLogistics logistics = null;
                BsLogistics logisticsCondition = null;
                BsLogistics bsLogistics = null;
                //线上配送物流信息
                if (record.getShipType() != null && record.getShipType().equalsIgnoreCase(Constants.ONLINE_DELIVERY)) {
                    logistics = new BsLogistics();
                    logistics.setCode(Constants.LOGI_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker));
                    logistics.setType(LogisticsEnum.LOGISTICS_TYPE_6.getCode());
                    logistics.setServiceCode(orderCode);
                    logistics.setReceiveId(bsAddress.getId());
                    logistics.setCreatedBy(jstShop.getShopMerchantCode());
                    logistics.setUpdatedBy(jstShop.getShopMerchantCode());
                    logistics.setCreatedDate(JstUtils.getNowDate());
                    logistics.setUpdatedDate(JstUtils.getNowDate());
                    logistics.setDeletedFlag("N");
                    listLogistics.add(logistics);

                    logisticsCondition = new BsLogistics();
                    logisticsCondition.setReceiveId(bsAddress.getId());
                    logisticsCondition.setCode(logiCode);
                    logisticsCondition.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
                    logisticsCondition.setServiceCode(orderCode);
                    logisticsCondition.setCompany(record.getLogisticsDto().getCompany());
                    logisticsCondition.setOrderNumber(record.getLogisticsDto().getOrderNumber());
                    logisticsCondition.setShipmentsQuantity(cart.getTotal());
                    logisticsCondition.setCreatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setUpdatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setCreatedDate(JstUtils.getNowDate());
                    logisticsCondition.setUpdatedDate(JstUtils.getNowDate());
                    logisticsCondition.setDeletedFlag("N");
                    logisticsCondition.setSendTime(JstUtils.getStringFromDate(JstUtils.getNowDate()));
                    listLogistics.add(logisticsCondition);
                    // bsLogistics = logisticsService.getLogisticsByOrderNumber(logisticsCondition);
                }

                //线下配送物流信息
                if (record.getShipType() != null && record.getShipType().equalsIgnoreCase(Constants.OFFLINE_DELIVERY)) {
                    logistics = new BsLogistics();
                    logistics.setCode(logiCode);
                    logistics.setType(LogisticsEnum.LOGISTICS_TYPE_6.getCode());
                    logistics.setServiceCode(orderCode);
                    logistics.setReceiveId(bsAddress.getId());
                    logistics.setCreatedBy(jstShop.getShopMerchantCode());
                    logistics.setUpdatedBy(jstShop.getShopMerchantCode());
                    logistics.setCreatedDate(JstUtils.getNowDate());
                    logistics.setUpdatedDate(JstUtils.getNowDate());
                    logistics.setDeletedFlag("N");
                    listLogistics.add(logistics);

                    logisticsCondition = new BsLogistics();
                    logisticsCondition.setReceiveId(bsAddress.getId());
                    logisticsCondition.setCode(logiCode);
                    logisticsCondition.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
                    logisticsCondition.setServiceCode(orderCode);
                    logisticsCondition.setCompany("线下配送");
                    logisticsCondition.setOrderNumber(offineCode);
                    logisticsCondition.setShipmentsQuantity(cart.getTotal());
                    logisticsCondition.setCreatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setUpdatedBy(jstShop.getShopMerchantCode());
                    logisticsCondition.setCreatedDate(JstUtils.getNowDate());
                    logisticsCondition.setUpdatedDate(JstUtils.getNowDate());
                    logisticsCondition.setDeletedFlag("N");
                    logisticsCondition.setSendTime(JstUtils.getStringFromDate(JstUtils.getNowDate()));
                    listLogistics.add(logisticsCondition);
                 //   bsLogistics = logisticsService.getLogisticsByOrderNumber(logisticsCondition);
                }

                JstShopOrder jstShopOrder = new JstShopOrder();
                jstShopOrder.setShopOrderCode(orderCode);
                jstShopOrder.setShopCode(jstShop.getShopCode());
                jstShopOrder.setShopName(jstShop.getShopName());
                jstShopOrder.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
                jstShopOrder.setAgentMerchantName(dealerUserInfo.getMerchantName());
                jstShopOrder.setProductCode((product == null) ? Constants.SYSPRODUCTCODE : product.getProductCode());
                jstShopOrder.setProductName((product == null) ? Constants.SYSPRODUCTNAME : product.getProductName());
                jstShopOrder.setServiceType((product == null) ? Constants.SYSSERVICETYPE : product.getServiceType());
                jstShopOrder.setServiceTime((product == null) ? Constants.SYSSERVICETIME : product.getServiceTime());
                jstShopOrder.setMaterialCode(cart.getMaterialCode());
                jstShopOrder.setMaterialName(cart.getMaterialName());
                jstShopOrder.setMaterialType(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode());
                jstShopOrder.setTotal(cart.getTotal());
                jstShopOrder.setPrice((product == null) ? 0.0 : product.getPrice());
                jstShopOrder.setProductSplitPriceTime((product == null) ? JstUtils.getNowDate() : product.getPriceTime());
                jstShopOrder.setPackageOne((product == null) ? Constants.SYSPACKAGEONE : product.getPackageOne());
                jstShopOrder.setStatus(ShopOrderStatuEnum.ORDER_OV.getValue());
                jstShopOrder.setCreatedBy(jstShop.getShopMerchantCode());
                jstShopOrder.setUpdatedBy(jstShop.getShopMerchantCode());
                jstShopOrder.setCreatedDate(JstUtils.getNowDate());
                jstShopOrder.setUpdatedDate(JstUtils.getNowDate());
                jstShopOrder.setDeletedFlag("N");
                jstShopOrder.setScanType(Constants.NO_SCAN_CODE);
                listShopOrder.add(jstShopOrder);

                //生成订单明细
//                JstShopOrderDetail detail = null;
//                BsMerchantStockDevice bsMerchantStockDevice = null;
//                if (record.getScanType() != null && record.getScanType().equalsIgnoreCase(Constants.NO_SCAN_CODE)) {
//                    //不扫码订单明细生成
//                    for (OrderDetailDTO orderDetailDTO : record.getListDetailDto()) {
//                        detail = new JstShopOrderDetail();
//                        detail.setAttribCode(orderDetailDTO.getAttribCode());
//                        detail.setCreatedBy("system");
//                        detail.setCreatedDate(JstUtils.getNowDate());
//                        detail.setDeletedFlag("N");
//                        detail.setLogisticsId(bsLogistics.getId().intValue());
//                        detail.setShopOrderCode(orderCode);
//                        detail.setSn(orderDetailDTO.getSn());
//                        detail.setUpdatedBy("system");
//                        detail.setUpdatedDate(JstUtils.getNowDate());
//                        detail.setVtSn(Constants.NO_SCAN_CODE);
//                        listShopOrderDetail.add(detail);
//
//                        bsMerchantStockDevice = new BsMerchantStockDevice();
//                        bsMerchantStockDevice.setSn(orderDetailDTO.getSn());
//                        bsMerchantStockDevice.setAttribCode(orderDetailDTO.getAttribCode());
//                        bsMerchantStockDevice.setMerchantCode(dealerUserInfo.getMerchantCode());
//                        bsMerchantStockDevice.setMerchantName(dealerUserInfo.getMerchantName());
//                        bsMerchantStockDevice.setInTime(JstUtils.getNowDate());
//                        bsMerchantStockDevice.setInLogisticsId(bsLogistics.getId().intValue());
//                        bsMerchantStockDevice.setOutTime(JstUtils.getNowDate());
//                        bsMerchantStockDevice.setOutLogisticsId(bsLogistics.getId().intValue());
//                        bsMerchantStockDevice.setToMerchantCode(jstShop.getShopMerchantCode());
//                        bsMerchantStockDevice.setToMerchantName(jstShop.getShopMerchantName());
//                        bsMerchantStockDevice.setCreatedDate(JstUtils.getNowDate());
//                        bsMerchantStockDevice.setCreatedBy(dealerUserInfo.getName());
//                        bsMerchantStockDevice.setUpdatedDate(JstUtils.getNowDate());
//                        bsMerchantStockDevice.setUpdatedBy(dealerUserInfo.getName());
//                        bsMerchantStockDevice.setStatu(MerchantStockDeviceEnum.STATU_OS.getValue());
//                        bsMerchantStockDevice.setDeletedFlag("N");
//                        bsMerchantStockDevice.setVtSn(Constants.NO_SCAN_CODE);
//                        bsMerchantStockDeviceList.add(bsMerchantStockDevice);
//                        result++;
//                    }
//                }
            }
            logisticsService.insertLogisticsList(listLogistics);
            shopOrderMapper.insertList(listShopOrder);
       //     shopOrderDetailMapper.insertList(listShopOrderDetail);
       //     deviceMapper.batchBatchInsertReplace(bsMerchantStockDeviceList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        return result;
    }

    private ProductDTO getProductFromCache(String productCode, Map<String, ProductDTO> mapProduct) {
        //如果是系统生成的  直接返回空值
        if (productCode.equals(Constants.SYSPRODUCTCODE)) {
            return null;
        }
        ProductDTO product = mapProduct.get(productCode);
        if (null != product) {
            return product;
        }
        product = getProductBaseInfo(productCode);
        if (null != product) {
            mapProduct.put(productCode, product);
        }
        return product;
    }

    private Map<String, BsMerchantStockDevice> getMerchantStockDevice(List<String> listSn, String merchantCode) {
        List<BsMerchantStockDevice> listStockDevice = stockDeviceService.listMerchantStockDevice(listSn, merchantCode);
        if (null == listStockDevice || listStockDevice.isEmpty()) {
            return null;
        }
        return listStockDevice.stream().collect(Collectors.toMap(BsMerchantStockDevice::getSn, a -> a, (k1, k2) -> k1));
    }

    private JstShop getJstShopByShopCode(String shopCode) {
        try {
            return shopService.getJspShopByShopcode(shopCode);
        } catch (RpcServiceException e) {
            logger.info(e.getMessage(), e);
            return null;
        }
    }

    private BsAddress getBsAddress(BsAddressDTO addressDto) {
        try {
            return addressService.getAddressIfExist(addressDto);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return null;
        }
    }

    private List<JstShopShoppingCart> genJstShopShoppingCard(List<String> listSn) {
        List<OrderInfoDetail> listDetail = dispatchOrderInfoDetailMapper.getShopOrderDetailNoOrder(listSn);
        if (null == listDetail) {
            listDetail = new ArrayList<>();
        }
        Map<String, OrderInfoDetail> mapOrderInfoDetail = listDetail.stream().collect(Collectors.toMap(OrderInfoDetail::getSn, Function.identity(), (key1, key2) -> key2));
        Map<String, JstShopShoppingCart> mapShoppingCart = new HashMap<>();
        JstShopShoppingCart cart = null;
        List<String> listCartSn = null;
        OrderInfoDetail detail = null;
        for (String sn : listSn) {
            detail = mapOrderInfoDetail.get(sn);
            if (null != detail) {
                cart = mapShoppingCart.get(detail.getProductCode() + detail.getMaterialCode());
                if (null == cart) {
                    cart = new JstShopShoppingCart();
                    cart.setProductCode(detail.getProductCode());
                    cart.setProductName(detail.getProductName());
                    cart.setMaterialCode(detail.getMaterialCode());
                    cart.setMaterialName(detail.getMaterialName());
                    listCartSn = new ArrayList<String>();
                    listCartSn.add(detail.getSn());
                    cart.setListSn(listCartSn);
                    mapShoppingCart.put(detail.getProductCode() + detail.getMaterialCode(), cart);
                } else {
                    cart.getListSn().add(detail.getSn());
                }
            } else {
                //老数据未走发货订单
                cart = mapShoppingCart.get(Constants.SYSPRODUCTCODE + Constants.SYSMATERIALCODE);
                if (null == cart) {
                    cart = new JstShopShoppingCart();
                    cart.setProductCode(Constants.SYSPRODUCTCODE);
                    cart.setProductName(Constants.SYSPRODUCTNAME);
                    cart.setMaterialCode(Constants.SYSMATERIALCODE);
                    cart.setMaterialName(Constants.SYSMATERIALNAME);
                    listCartSn = new ArrayList<String>();
                    listCartSn.add(sn);
                    cart.setListSn(listCartSn);
                    mapShoppingCart.put(Constants.SYSPRODUCTCODE + Constants.SYSMATERIALCODE, cart);
                } else {
                    cart.getListSn().add(sn);
                }
            }
        }
        return mapShoppingCart.values().stream().collect(Collectors.toList());
    }

    private List<JstShopShoppingCart> noScanCodeGenJstShopShoppingCard(List<MaterialDTO> materialDTOList) {
//        List<OrderInfoDetail> listDetail = dispatchOrderInfoDetailMapper.getShopOrderDetailNoOrder(listSn);
//        if (null == listDetail) {
//            listDetail = new ArrayList<>();
//        }
//        Map<String, OrderInfoDetail> mapOrderInfoDetail = listDetail.stream().collect(Collectors.toMap(OrderInfoDetail::getSn, Function.identity(), (key1, key2) -> key2));
        Map<String, JstShopShoppingCart> mapShoppingCart = new HashMap<>();
        JstShopShoppingCart cart = null;
        List<String> listCartSn = null;
        for (MaterialDTO detail : materialDTOList) {
            if (null != detail) {
                cart = mapShoppingCart.get(detail.getProductCode() + detail.getMaterialCode());
                if (null == cart) {
                    cart = new JstShopShoppingCart();
                    cart.setProductCode(detail.getProductCode());
                    cart.setProductName(detail.getProductName());
                    cart.setMaterialCode(detail.getMaterialCode());
                    cart.setMaterialName(detail.getMaterialName());
                    cart.setTotal(detail.getShipQuantities());
                    mapShoppingCart.put(detail.getProductCode() + detail.getMaterialCode(), cart);
                }
            } else {
                //老数据未走发货订单
                cart = mapShoppingCart.get(Constants.SYSPRODUCTCODE + Constants.SYSMATERIALCODE);
                if (null == cart) {
                    cart = new JstShopShoppingCart();
                    cart.setProductCode(Constants.SYSPRODUCTCODE);
                    cart.setProductName(Constants.SYSPRODUCTNAME);
                    cart.setMaterialCode(Constants.SYSMATERIALCODE);
                    cart.setMaterialName(Constants.SYSMATERIALNAME);
                    mapShoppingCart.put(Constants.SYSPRODUCTCODE + Constants.SYSMATERIALCODE, cart);
                }
            }
        }
        return mapShoppingCart.values().stream().collect(Collectors.toList());
    }

    //提交订单明细
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer submitShopOrderDetail(SubOrderDetailDTO record) throws RpcServiceException {
        Integer result = 0;
        logger.info("OrderService::submitShopOrderDetail start record:{}", record);
        //获取订单信息
        JstShopOrder orderCondition = new JstShopOrder();
        orderCondition.setShopOrderCode(record.getOrderCode());
        orderCondition.setDeletedFlag("N");
        JstShopOrder jstShopOrder = shopOrderMapper.selectOne(orderCondition);
        if (StringUtils.isEmpty(jstShopOrder)) {
            logger.info("OrderService::submitShopOrderDetail order not exist!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOP_ORDER);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_OV.getValue())) {
            logger.info("OrderService::submitShopOrderDetail order status is ov!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_FINISHED);
        }
        if (jstShopOrder.getStatus().equals(ShopOrderStatuEnum.ORDER_CL.getValue())) {
            logger.info("OrderService::submitShopOrderDetail order status is cl!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_ORDER_CANCEL);
        }
        //根据门店编号获取门店信息
        JstShop jstShop = shopService.getJspShopByShopcode(jstShopOrder.getShopCode());
        //获取订单详情数量
        JstShopOrderDetail detailCondition = new JstShopOrderDetail();
        detailCondition.setShopOrderCode(record.getOrderCode());
        detailCondition.setDeletedFlag("N");
        Integer detailCount = shopOrderDetailMapper.selectCount(detailCondition);
        if ((detailCount + record.getListDetailDto().size()) > jstShopOrder.getTotal()) {
            logger.info("OrderService::submitShopOrderDetail over of order total!");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_OVER_SHOP_ORDER_TOTAL);
        }
        try {
            //获取物流信息
            BsLogistics logisticsCondition = new BsLogistics();
            logisticsCondition.setReceiveId(record.getAddressDto().getId());
            logisticsCondition.setCompany(record.getLogisticsDto().getCompany());
            logisticsCondition.setOrderNumber(record.getLogisticsDto().getOrderNumber());
            logisticsCondition.setServiceCode(record.getOrderCode());
            logisticsCondition.setType(LogisticsEnum.LOGISTICS_TYPE_7.getCode());
            logisticsCondition.setDeletedFlag("N");
            BsLogistics bsLogistics = logisticsService.getLogisticsByOrderNumber(logisticsCondition);

            List<JstShopOrderDetail> listShopOrderDetail = new ArrayList<JstShopOrderDetail>();
            //	List<String> listDeviceSn = new ArrayList<String>();

            BsMerchantStockDevice deviceCondition = new BsMerchantStockDevice();
            BsMerchantStockDevice deviceSn = null;
            BsMerchantStockDevice stockDevice = null;
            BsMerchantStockDevice stockDeviceCondition = null;
            for (OrderDetailDTO detailDto : record.getListDetailDto()) {
                deviceCondition.setSn(detailDto.getSn());
                deviceCondition.setDeletedFlag("N");
                deviceSn = deviceMapper.selectOne(deviceCondition);
                if (StringUtils.isEmpty(deviceSn)) {
                    logger.info("OrderService::submitShopOrderDetail sn is not in stock!");
                    String message = "sn:" + detailDto.getSn() + " 未入库";
                    JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                    throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
                }
                if (!deviceSn.getStatu().equals("IN")) {
                    logger.info("OrderService::submitShopOrderDetail sn:" + deviceSn.getSn() + " is not in stock");
                    String message = "sn:" + detailDto.getSn() + " 不在库存";
                    JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE.setDescrible(message);
                    throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SEFE_MESSAGE);
                }

                //listDeviceSn.add(detailDto.getSn());
                JstShopOrderDetail detail = new JstShopOrderDetail();
                detail.setAttribCode(deviceSn.getAttribCode());
                detail.setCreatedBy("system");
                detail.setCreatedDate(JstUtils.getNowDate());
                detail.setDeletedFlag("N");
                detail.setLogisticsId(bsLogistics.getId().intValue());
                detail.setShopOrderCode(record.getOrderCode());
                detail.setSn(deviceSn.getSn());
                detail.setUpdatedBy("system");
                detail.setUpdatedDate(JstUtils.getNowDate());
                listShopOrderDetail.add(detail);

                stockDevice = new BsMerchantStockDevice();
                stockDevice.setId(deviceSn.getId());
                stockDevice.setOutTime(JstUtils.getNowDate());
                stockDevice.setOutLogisticsId(bsLogistics.getId().intValue());
                stockDevice.setToMerchantCode(jstShop.getShopMerchantCode());
                stockDevice.setToMerchantName(jstShop.getShopMerchantName());
                stockDevice.setUpdatedDate(JstUtils.getNowDate());
                stockDevice.setStatu(MerchantStockDeviceEnum.STATU_OS.getValue());
                deviceMapper.updateByPrimaryKeySelective(stockDevice);
            }
            shopOrderDetailMapper.insertList(listShopOrderDetail);
            //	deviceMapper.batchUpdateFinshStatu(listDeviceSn);
            detailCount = shopOrderDetailMapper.selectCount(detailCondition);
            if (detailCount == jstShopOrder.getTotal()) {
                jstShopOrder.setStatus(ShopOrderStatuEnum.ORDER_OV.getValue());
                shopOrderMapper.updateByPrimaryKeySelective(jstShopOrder);
            }
            result = jstShopOrder.getTotal() - detailCount;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        return result;
    }

    //获取订单明细
    public DisShopOrderDetailDTO listShopOrderDetail(DisShopOrderDetailDTO record) throws RpcServiceException {
        logger.info("OrderService::listShopOrderDetail start record:{}", record);
        try {
            JstShopOrderDetail condition = new JstShopOrderDetail();
            condition.setShopOrderCode(record.getOrderCode());
            condition.setPageStart((record.getPageNo() - 1) * record.getPageSize());
            condition.setPageSize(record.getPageSize());
            List<JstShopOrderDetail> listOrderDetail = shopOrderDetailMapper.pageOrderDetail(condition);
            record.setListDetailDto(OrderDetailConvertRpcConvert.convertListBean(listOrderDetail));
        } catch (Exception e) {
            logger.error("JstShopService::listShopOrderDetail 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        logger.info("JstShopService::listShopOrderDetail end result:{}", record);
        return record;
    }

    //获取订单详情表
    public OrderDTO getShopOrderDetail(OrderDTO record) throws RpcServiceException {
        OrderDTO result = null;
        logger.info("OrderService::getShopOrderDetail start record:{}", record);
        try {
            JstShopOrder condition = new JstShopOrder();
            condition.setShopOrderCode(record.getOrderCode());
            JstShopOrder shopOrder = shopOrderMapper.selectOne(condition);
            result = OrderConvertRpcConvert.convertShopOrderBean(shopOrder);

            Example example = new Example(JstShopOrderDetail.class);
            example.createCriteria().andEqualTo("shopOrderCode", record.getOrderCode())
                    .andEqualTo("deletedFlag", "N");
            List<JstShopOrderDetail> listOrderDetail = shopOrderDetailMapper.selectByExample(example);
            JstShopOrderDetail jstShopOrderDetail = new JstShopOrderDetail();
            jstShopOrderDetail.setShopOrderCode(record.getOrderCode());
            Integer sum = 0;
            if(shopOrder.getScanType().equals(Constants.NO_SCAN_CODE)){
            	List<BsLogistics> listLogistics = logisticsService.getLogisticsListByShopOrderCode(shopOrder.getShopOrderCode());
            	if(null != listLogistics){
            		for(BsLogistics logistics:listLogistics){
            			if(null != logistics.getShipmentsQuantity()){
            				sum += logistics.getShipmentsQuantity();
            			}
            		}
            		result.setSendCount(sum);
            	}
            }else{
            	sum = shopOrderDetailMapper.selectCount(jstShopOrderDetail);
                result.setListOrderDetailDto(OrderDetailConvertRpcConvert.convertListBean(listOrderDetail));
                result.setSendCount(sum);
            } 
            BsLogistics logistics = logisticsService.getLogisticsByShopOrderCode(record.getOrderCode(), LogisticsEnum.LOGISTICS_TYPE_7.getCode());
            if (!StringUtils.isEmpty(logistics)) {
                String orderNumber=logistics.getOrderNumber();
                if(orderNumber!=null&&!orderNumber.isEmpty()) {
                    if (orderNumber.startsWith(Constants.OFFLINE_ORDER_PREFIX)) {
                        logistics.setShipType(Constants.OFFLINE_DELIVERY);
                    }else{
                        logistics.setShipType(Constants.ONLINE_DELIVERY);
                    }
                }
                result.setLogisticsDto(BsLogisticsRpcConvert.convertBean(logistics));
                BsAddress address = addressService.getAddressById(logistics.getReceiveId(),null);
                result.setAddressDto(BsAddressRpcConvert.convertBean(address));
            } else {
                //去订单中的地址 显示出来
                logistics = logisticsService.getLogisticsByShopOrderCode(record.getOrderCode(), LogisticsEnum.LOGISTICS_TYPE_6.getCode());
                result.setLogisticsDto(BsLogisticsRpcConvert.convertBean(logistics));
                BsAddress address = addressService.getAddressById(logistics.getReceiveId(),null);
                result.setAddressDto(BsAddressRpcConvert.convertBean(address));
            }
        } catch (Exception e) {
            logger.error("JstShopService::getShopOrderDetail 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        logger.info("JstShopService::getShopOrderDetail end result:{}", record);
        return result;
    }

    //获取门店订单列表
    public DisOrderDTO pageShopOrder(DisOrderDTO record) throws RpcServiceException {
        logger.info("OrderService::pageShopOrder start record:{}", record);
        try {
            JstShopOrder condition = new JstShopOrder();
            condition.setStatus(record.getStatus());
            condition.setOrderTime(record.getOrderTime());
            condition.setServiceType(record.getServiceType());
            condition.setProductName(record.getContext());
            condition.setPageStart((record.getPageNo() - 1) * record.getPageSize());
            condition.setPageSize(record.getPageSize());
            condition.setShopCode(record.getShopCode());
            condition.setAgentMerchantCode(record.getMerchantCode());
            List<JstShopOrder> listShopOrder = shopOrderMapper.pageWxJspShopOrder(condition);
            List<String> listOrderCode = new ArrayList<String>();
            Map<String, JstShopOrder> mapShopOrder = new HashMap<String, JstShopOrder>();
            List<JstShopOrderDetail> listOrderDetail = null;
            if (!StringUtils.isEmpty(listShopOrder)) {
                for (JstShopOrder shopOrder : listShopOrder) {
                    listOrderCode.add(shopOrder.getShopOrderCode());
                    mapShopOrder.put(shopOrder.getShopOrderCode(), shopOrder);
                }
            }
            if (listOrderCode.size() > 0) {
                listOrderDetail = shopOrderDetailMapper.countShopOrderDetail(listOrderCode);
            }
            if (!StringUtils.isEmpty(listOrderDetail)) {
                for (JstShopOrderDetail detail : listOrderDetail) {
                    JstShopOrder shopOrder = mapShopOrder.get(detail.getShopOrderCode());
                    if (!StringUtils.isEmpty(shopOrder)) {
                        shopOrder.setSendCount(detail.getSendCount());
                    }
                }

            }
            record.setListOrderDto(OrderConvertRpcConvert.convertShopOrderListBean(listShopOrder));
        } catch (Exception e) {
            logger.error("JstShopService::pageShopOrder 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        logger.info("JstShopService::pageShopOrder end result:{}", record);
        return record;
    }

    //获取商户订单列表
    public DisOrderDTO pageMerchantOrder(DisOrderDTO record) throws RpcServiceException {

        logger.info("OrderService::pageMerchantOrder start record:{}", record);
        try {
            BsMerchantOrder condition = new BsMerchantOrder();
            condition.setStatus(getMerchantOrderStatuFromDto(record.getStatus()));
            condition.setQuestOrderTime(record.getOrderTime());
            condition.setBsServiceType(record.getServiceType());
            condition.setBsProductName(record.getContext());
            condition.setPageStart((record.getPageNo() - 1) * record.getPageSize());
            condition.setPageSize(record.getPageSize());
            condition.setMerchantCode(record.getMerchantCode());
            List<BsMerchantOrder> listMerchantOrder = merchantOrderMapper.pageMerchantOrder(condition);
            if (!StringUtils.isEmpty(condition.getStatus())) {
                if (condition.getStatus().equals(MerchantOrderStatuEnum.ORDER_WS.getCode()) || condition.getStatus().equals(MerchantOrderStatuEnum.ORDER_FI.getCode())) {
                    List<String> listDispatchOrderNum = new ArrayList<String>();
                    Map<String, BsMerchantOrder> mapDispatch = new HashMap<String, BsMerchantOrder>();
                    List<BsMerchantOrder> listOrderDetail = null;
                    for (BsMerchantOrder order : listMerchantOrder) {
                        if (!StringUtils.isEmpty(order.getDispatchOrderCode())) {
                            listDispatchOrderNum.add(order.getDispatchOrderCode());
                            mapDispatch.put(order.getDispatchOrderCode(), order);
                        }
                    }
                    if (listDispatchOrderNum.size() > 0) {
                        listOrderDetail = merchantOrderMapper.countDispatchOrderCode(listDispatchOrderNum);
                    }
                    if (!StringUtils.isEmpty(listOrderDetail)) {
                        for (BsMerchantOrder detail : listOrderDetail) {
                            BsMerchantOrder merchantOrder = mapDispatch.get(detail.getDispatchOrderCode());
                            if (!StringUtils.isEmpty(merchantOrder)) {
                                merchantOrder.setSendCount(detail.getSendCount());
                            }
                        }
                    }
                }
            }
            record.setListOrderDto(OrderConvertRpcConvert.convertMerchantOrderListBean(listMerchantOrder));
        } catch (Exception e) {
            logger.error("JstShopService::pageMerchantOrder 数据库获取数据异常" + e.getMessage());
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
        }
        logger.info("JstShopService::pageMerchantOrder end result:{}", record);
        return record;
    }


    //提交门店订单
    public Integer submitShopOrder(SubShopOrderDTO subShopOrderDto) throws RpcServiceException {
        Integer result = 0;
        logger.info("OrderService::submitShopOrder start subShopOrderDto:{}", subShopOrderDto);
        //验证地址的有效性
        BsAddress bsAddress = getAddressById(subShopOrderDto.getBsAddressDto().getId(),"N");
        if (StringUtils.isEmpty(bsAddress)) {
            logger.info("OrderService::submitShopOrder invalide address");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_ADDRESS);
        }
        JstShop jstShop = shopService.getJspShopByShopcode(subShopOrderDto.getShopCode());
        if (StringUtils.isEmpty(jstShop)) {
            logger.info("OrderService::submitShopOrder shop has been deleted");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOPAGENTMERCHANT);
        }
        Map<String, ProductDTO> mapProduct = new HashMap<String, ProductDTO>();
        Map<String, Byte> mapMaterialType = new HashMap<String, Byte>();
        //验证购物车物品的有效性
        for (JstShopShoppingCartDTO cartDto : subShopOrderDto.getListCartDto()) {
            //验证购物车
            JstShopShoppingCart shopCart = getShopShoppingCartById(cartDto.getId());
            if (StringUtils.isEmpty(shopCart)) {
                logger.info("OrderService::submitShopOrder product of cart has been deleted!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_CART);
            }
            //验证门店和供应商关系
            JstShopAgentRelation relation = getShopAgentMerchantRelation(subShopOrderDto.getShopCode(), shopCart.getAgentMerchantCode());
            if (StringUtils.isEmpty(relation)) {
                logger.info("OrderService::submitShopOrder the relation of shop and agent merchant were dissolved!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_SHOPAGENTMERCHANT);
            }
            //验证产品是否下架以及价格是否有变更
            ProductDTO product = getProductBaseInfo(shopCart.getProductCode());
            if (StringUtils.isEmpty(product)) {
                logger.info("OrderService::submitShopOrder the statu of product is down!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_PRICE);
            }

            String pdtPrice = String.valueOf(JstUtils.getDecimalDouble(product.getPrice()));
            String carPrice = String.valueOf(JstUtils.getDecimalDouble(shopCart.getPrice()));

            if (!pdtPrice.equals(carPrice)) {
                logger.info("OrderService::submitShopOrder the price of product has been changed!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_PRICE);
            }
            //验证物料和产品的关系
            if (!isMaterialBelongProduct(shopCart.getProductCode(), shopCart.getMaterialCode(), mapMaterialType)) {
                logger.info("OrderService::submitShopOrder matrial of product has been deleted!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_MATERIAL);
            }
            mapProduct.put(product.getProductCode(), product);
        }

        //下单
        List<BsLogistics> listLogistics = new ArrayList<BsLogistics>();
        List<JstShopOrder> listShopOrder = new ArrayList<JstShopOrder>();
        List<JstShopShoppingCartDTO> listCartDto = new ArrayList<JstShopShoppingCartDTO>();
        DisJstShopShoppingCartDTO shoppingCartDto = new DisJstShopShoppingCartDTO();
        for (JstShopShoppingCartDTO cartDto : subShopOrderDto.getListCartDto()) {
            JstShopShoppingCart shopCart = getShopShoppingCartById(cartDto.getId());
            ProductDTO product = mapProduct.get(shopCart.getProductCode());
            String orderCode = Constants.SHOP_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
            String logiCode = Constants.LOGI_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
            BsLogistics bsLogistics = new BsLogistics();
            bsLogistics.setReceiveId(bsAddress.getId());
            bsLogistics.setCode(logiCode);
            bsLogistics.setType(Byte.valueOf("6"));
            bsLogistics.setServiceCode(orderCode);
            bsLogistics.setCreatedBy(jstShop.getShopMerchantCode());
            bsLogistics.setUpdatedBy(jstShop.getShopMerchantCode());
            bsLogistics.setCreatedDate(JstUtils.getNowDate());
            bsLogistics.setUpdatedDate(JstUtils.getNowDate());
            bsLogistics.setDeletedFlag("N");
            listLogistics.add(bsLogistics);

            cartDto.setDeletedFlag("Y");
            listCartDto.add(cartDto);

            JstShopOrder jstShopOrder = new JstShopOrder();
            jstShopOrder.setShopOrderCode(orderCode);
            jstShopOrder.setShopCode(jstShop.getShopCode());
            jstShopOrder.setShopName(jstShop.getShopName());
            jstShopOrder.setAgentMerchantCode(shopCart.getAgentMerchantCode());
            jstShopOrder.setAgentMerchantName(shopCart.getAgentMerchantName());
            jstShopOrder.setProductCode(product.getProductCode());
            jstShopOrder.setProductName(product.getProductName());
            jstShopOrder.setServiceType(product.getServiceType());
            jstShopOrder.setServiceTime(product.getServiceTime());
            jstShopOrder.setMaterialCode(shopCart.getMaterialCode());
            jstShopOrder.setMaterialName(shopCart.getMaterialName());
            jstShopOrder.setMaterialType(mapMaterialType.get(shopCart.getMaterialCode()));
            jstShopOrder.setTotal(shopCart.getTotal());
            jstShopOrder.setPrice(product.getPrice());
            jstShopOrder.setProductSplitPriceTime(product.getPriceTime());
            jstShopOrder.setPackageOne(product.getPackageOne());
            jstShopOrder.setStatus(ShopOrderStatuEnum.ORDER_UN.getValue());
            jstShopOrder.setCreatedBy(jstShop.getShopMerchantCode());
            jstShopOrder.setUpdatedBy(jstShop.getShopMerchantCode());
            jstShopOrder.setCreatedDate(JstUtils.getNowDate());
            jstShopOrder.setUpdatedDate(JstUtils.getNowDate());
            jstShopOrder.setDeletedFlag("N");
            listShopOrder.add(jstShopOrder);
        }
        shoppingCartDto.setListCartDto(listCartDto);
        shoppingCartDto.setShopCode(jstShop.getShopCode());
        logisticsService.batchAddLogistics(listLogistics);
        cartService.updateShopShoppingCart(shoppingCartDto);
        shopOrderMapper.insertList(listShopOrder);
        logger.info("OrderService::submitShopOrder end result:{}", result);
        return result;
    }

    //商户订单提交
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer submitMerchantOrder(SubMerchantOrderDTO subMerchantOrderDto) throws RpcServiceException {
        Integer result = 0;
        logger.info("OrderService::submitMerchantOrder start subMerchantOrderDto:{}", subMerchantOrderDto);
        //验证地址的有效性
        BsAddress bsAddress = getAddressById(subMerchantOrderDto.getBsAddressDto().getId(),"N");
        if (StringUtils.isEmpty(bsAddress)) {
            logger.info("OrderService::submitMerchantOrder invalide address");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_ADDRESS);
        }
        BsDealerUserInfo dealerUserInfo = dealerUserInfoService.getBsDealerUserInfoByMerchantCode(subMerchantOrderDto.getMerchantCode());
        if (StringUtils.isEmpty(dealerUserInfo)) {
            logger.info("OrderService::submitMerchantOrder shop has been deleted");
            throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER_MERCHANT);
        }
        List<String> listProductCode = new ArrayList<String>();
        List<String> listMaterialCode = new ArrayList<String>();
        Map<String, ProductDTO> mapProduct = new HashMap<String, ProductDTO>();
        Map<String, List<AmProductSplitDetail>> mapProductDetail = new HashMap<String, List<AmProductSplitDetail>>();
        Map<String, List<AmProductSplitHistoryPrice>> mapProductPrice = new HashMap<String, List<AmProductSplitHistoryPrice>>();
        Map<String, AmMaterialInfo> mapMaterialInfo = new HashMap<String, AmMaterialInfo>();
        //验证购物车物品的有效性
        for (BsMerchantShoppingCartDTO cartDto : subMerchantOrderDto.getListCartDto()) {
            //验证购物车
            BsMerchantShoppingCart merchantCart = this.getMerchantShoppingCartById(cartDto.getId());
            if (StringUtils.isEmpty(merchantCart)) {
                logger.info("OrderService::submitShopOrder product of cart has been deleted!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_CART);
            }
            //验证产品是否下架以及价格是否有变更
            ProductDTO product = getProductBaseInfo(merchantCart.getProductCode());
            if (StringUtils.isEmpty(product)) {
                logger.info("OrderService::submitMerchantOrder the statu of product is down!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_PRICE);
            }

            String pdtPrice = String.valueOf(JstUtils.getDecimalDouble(product.getPrice()));
            String carPrice = String.valueOf(JstUtils.getDecimalDouble(merchantCart.getPrice()));

            if (!pdtPrice.equals(carPrice)) {
                logger.info("OrderService::submitShopOrder the price of product has been changed!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_PRODUCT_PRICE);
            }
            //验证物料和产品的关系
            if (!isMaterialBelongProduct(merchantCart.getProductCode(), merchantCart.getMaterialCode(), null)) {
                logger.info("OrderService::submitShopOrder matrial of product has been deleted!");
                throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_INVALID_MATERIAL);
            }
            mapProduct.put(product.getProductCode(), product);
            listProductCode.add(product.getProductCode());
            listMaterialCode.add(merchantCart.getMaterialCode());
        }

        mapProductDetail = this.getAmProductSplitDetail(listProductCode, mapProductDetail);
        mapProductPrice = this.getAmProductSplitPrice(listProductCode, mapProductPrice);
        mapMaterialInfo = this.getAmMaterialInfo(listMaterialCode, mapMaterialInfo);
        //下单
        try {
            List<BsLogistics> listLogistics = new ArrayList<BsLogistics>();
            List<BsMerchantOrder> listMerchantOrder = new ArrayList<BsMerchantOrder>();
            List<EcMerchantOrder> listEcMerchantOrder = new ArrayList<EcMerchantOrder>();
            List<BsMerchantOrderDetail> listMerchantOrderDetail = new ArrayList<BsMerchantOrderDetail>();
            List<BsProduct> listBsProduct = new ArrayList<BsProduct>();
            List<BsProductDetail> listBsProductDetail = new ArrayList<BsProductDetail>();
            Map<Byte, BsProductDetail> mapBsProductDetail = new HashMap<Byte, BsProductDetail>();
            List<BsProductHistoryPrice> listBsProductPrice = new ArrayList<BsProductHistoryPrice>();
            Map<String, BsProductHistoryPrice> mapBsProductPrice = new HashMap<String, BsProductHistoryPrice>();
            List<BsMerchantShoppingCartDTO> listMerchantCartDto = new ArrayList<BsMerchantShoppingCartDTO>();
            DisMerchantShoppingCartDTO merchantCartDto = new DisMerchantShoppingCartDTO();
            for (BsMerchantShoppingCartDTO cartDto : subMerchantOrderDto.getListCartDto()) {
                BsMerchantShoppingCart merchantCart = this.getMerchantShoppingCartById(cartDto.getId());
                String bsProductCode = Constants.BS_PRODUCT_CODE_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
                String bsOrderCode = Constants.BS_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);
                String logiCode = Constants.LOGI_ORDER_PREFIX + JstUtils.getDispatchOrderNumber(snowflakeWorker);

                ProductDTO productDto = mapProduct.get(merchantCart.getProductCode());
                List<AmProductSplitDetail> productDetail = mapProductDetail.get(merchantCart.getProductCode());
                List<AmProductSplitHistoryPrice> productPrice = mapProductPrice.get(merchantCart.getProductCode());
                AmMaterialInfo materialInfo = mapMaterialInfo.get(merchantCart.getMaterialCode());

                BsLogistics bsLogistics = new BsLogistics();
                bsLogistics.setReceiveId(bsAddress.getId());
                bsLogistics.setCode(logiCode);
                bsLogistics.setType(Byte.valueOf("1"));
                bsLogistics.setServiceCode(bsOrderCode);
                bsLogistics.setCreatedBy(dealerUserInfo.getName());
                bsLogistics.setUpdatedBy(dealerUserInfo.getName());
                bsLogistics.setCreatedDate(JstUtils.getNowDate());
                bsLogistics.setUpdatedDate(JstUtils.getNowDate());
                bsLogistics.setDeletedFlag("N");
                listLogistics.add(bsLogistics);

                BsMerchantOrderDetail orderDetail = new BsMerchantOrderDetail();
                orderDetail.setMerchantOrderNumber(bsOrderCode);
                orderDetail.setProductCode(bsProductCode);
                orderDetail.setOrderQuantity(merchantCart.getTotal());
                orderDetail.setCheckQuantity(null);
                orderDetail.setCheckQuantity(null);
                orderDetail.setDispatchOrderNumber(null);
                orderDetail.setCreatedBy(dealerUserInfo.getName());
                orderDetail.setUpdatedBy(dealerUserInfo.getName());
                orderDetail.setCreatedDate(JstUtils.getNowDate());
                orderDetail.setUpdatedDate(JstUtils.getNowDate());
                orderDetail.setDeletedFlag("N");
                orderDetail.setProductRemarks(null);
                orderDetail.setCheckBy("");
                orderDetail.setCheckTime(null);
                orderDetail.setSubjectId(null);
                orderDetail.setInsure(null);
                listMerchantOrderDetail.add(orderDetail);

                BsMerchantOrder order = new BsMerchantOrder();
                order.setOrderNumber(bsOrderCode);
                order.setOrderTime(JstUtils.getNowDate());
                order.setMerchantCode(dealerUserInfo.getMerchantCode());
                order.setHopeTime(JstUtils.getFetureDate(7));
                order.setTotalOrder(merchantCart.getTotal());
                order.setTotalCheck(null);
                order.setTotalAmount(merchantCart.getPrice() * merchantCart.getTotal());
                order.setStatus(MerchantOrderStatuEnum.ORDER_WC.getCode());
                order.setRemarks(null);
                order.setCreatedBy(dealerUserInfo.getName());
                order.setUpdatedBy(dealerUserInfo.getName());
                order.setCreatedDate(JstUtils.getNowDate());
                order.setUpdatedDate(JstUtils.getNowDate());
                order.setDeletedFlag("N");
                listMerchantOrder.add(order);

                EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
                ecMerchantOrder.setChannel(getChannelName(dealerUserInfo.getChannel()));
                ecMerchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
                ecMerchantOrder.setMerchantName(dealerUserInfo.getMerchantName());
                ecMerchantOrder.setOrderNumber(bsOrderCode);
                ecMerchantOrder.setProductCode(productDto.getProductCode());
                ecMerchantOrder.setProductName(productDto.getProductName());
                ecMerchantOrder.setMaterialCode(cartDto.getMaterialCode());
                ecMerchantOrder.setMaterialName(cartDto.getMaterialName());
                ecMerchantOrder.setDeviceType(materialInfo.getDeviceType());
                ecMerchantOrder.setPrice(productDto.getPrice());
                ecMerchantOrder.setOrderQuantity(cartDto.getTotal());
                ecMerchantOrder.setCheckQuantity(0);
                ecMerchantOrder.setDispatchOrderNumber("");
                ecMerchantOrder.setAlreadyShipmentQuantity(0);
                ecMerchantOrder.setShipmentTime("");
                ecMerchantOrder.setShipmentQuantity("");
                ecMerchantOrder.setSignQuantity(0);
                ecMerchantOrder.setOweQuantity(0);
                ecMerchantOrder.setTotalAmount(cartDto.getTotal() * productDto.getPrice());
                ecMerchantOrder.setOrderTime(JstUtils.getNowDateStringYMD(JstUtils.getNowDate()));
                ecMerchantOrder.setProductRemarks("");
                ecMerchantOrder.setCheckBy("");
                ecMerchantOrder.setCheckTime("");
                ecMerchantOrder.setStatus(MerchantOrderStatuEnum.ORDER_WC.getName());
                ecMerchantOrder.setAddressee(bsAddress.getName());
                ecMerchantOrder.setMobile(bsAddress.getMobile());
                ecMerchantOrder.setAddressDetail((StringUtils.isEmpty(bsAddress.getProvinceName()) ? "" : bsAddress.getProvinceName())
                        + (StringUtils.isEmpty(bsAddress.getCityName()) ? "" : bsAddress.getCityName()) + (StringUtils.isEmpty(bsAddress.getAreaName()) ? "" : bsAddress.getAreaName())
                        + bsAddress.getAddress());
                ecMerchantOrder.setCreatedBy(dealerUserInfo.getName());
                ecMerchantOrder.setUpdatedBy(dealerUserInfo.getName());
                ecMerchantOrder.setCreatedDate(JstUtils.getNowDate());
                ecMerchantOrder.setUpdatedDate(JstUtils.getNowDate());
                ecMerchantOrder.setDeletedFlag("N");
                ecMerchantOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
                ecMerchantOrder.setLogisticsDesc("");
                listEcMerchantOrder.add(ecMerchantOrder);

                cartDto.setDeletedFlag("Y");
                listMerchantCartDto.add(cartDto);

                BsProduct bsProduct = new BsProduct();
                bsProduct.setCode(bsProductCode);
                //	bsProduct.setName(productDto.getProductName());
                bsProduct.setName(materialInfo.getMaterialName());
                bsProduct.setSpecification(null);
                bsProduct.setPackageOne(productDto.getPackageOne());
                bsProduct.setType(String.valueOf(materialInfo.getDeviceTypeId()));
                bsProduct.setChannel(productDto.getChannel());
                bsProduct.setStatus(Byte.valueOf("1"));
                bsProduct.setCreatedBy(dealerUserInfo.getName());
                bsProduct.setUpdatedBy(dealerUserInfo.getName());
                bsProduct.setCreatedDate(JstUtils.getNowDate());
                bsProduct.setUpdatedDate(JstUtils.getNowDate());
                bsProduct.setDeletedFlag("N");
                bsProduct.setProductSplitId(productDto.getId().longValue());
                bsProduct.setServiceType(productDto.getServiceType());
                bsProduct.setServiceTime(productDto.getServiceTime());
                bsProduct.setMerchantCode(productDto.getMerchantCode());
                bsProduct.setMerchantName(productDto.getMerchantName());
                bsProduct.setAlias(null);
                bsProduct.setDeviceQuantity(productDto.getDeviceQuantity());
                bsProduct.setHardwareContainSource(productDto.getHardwareContainSource());
                bsProduct.setSourceProportion(productDto.getSourceProportion());
                bsProduct.setNotSourceProportion(productDto.getNotSourceProportion());
                bsProduct.setCarType(productDto.getCarType());
                listBsProduct.add(bsProduct);
                mapBsProductDetail.clear();
                boolean bIsRight = false;
                for (AmProductSplitDetail splitDetail : productDetail) {
                    BsProductDetail bsProductDetail = new BsProductDetail();
                    bsProductDetail.setProductCode(bsProductCode);
                    bsProductDetail.setCreatedBy(dealerUserInfo.getName());
                    bsProductDetail.setUpdatedBy(dealerUserInfo.getName());
                    bsProductDetail.setCreatedDate(JstUtils.getNowDate());
                    bsProductDetail.setUpdatedDate(JstUtils.getNowDate());
                    bsProductDetail.setDeletedFlag("N");
                    bsProductDetail.setServiceType(splitDetail.getServiceType());
                    bsProductDetail.setType(Byte.valueOf(splitDetail.getProductType()));
                    bsProductDetail.setMaterialCode(splitDetail.getMaterialCode());
                    bsProductDetail.setMaterialName(splitDetail.getMaterialName());

                    boolean isAddHaredOrPart = false;
                    if (splitDetail.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()))) {
                        if (materialInfo.getMaterialTypeId().equals(47) || materialInfo.getMaterialTypeId().equals(46)) {
                            bsProductDetail.setMaterialCode(merchantCart.getMaterialCode());
                            bsProductDetail.setMaterialName(merchantCart.getMaterialName());
                            //配件和硬件只能选择一个添加
                            if (isAddHaredOrPart) {
                                continue;
                            }
                            isAddHaredOrPart = true;
                            //sb产品  物料定义配件  产品拆分中定义为硬件 这里按照物料类别转化下
                            Byte redifineType = (materialInfo.getMaterialTypeId().equals(47)) ? Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()) : Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode());
                            bsProductDetail.setType(redifineType);
                            mapBsProductDetail.put(bsProductDetail.getType(), bsProductDetail);
                        }
                    } else if (splitDetail.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode()))) {
                        if (materialInfo.getMaterialTypeId().equals(46) || materialInfo.getMaterialTypeId().equals(47)) {
                            bsProductDetail.setMaterialCode(merchantCart.getMaterialCode());
                            bsProductDetail.setMaterialName(merchantCart.getMaterialName());
                            //配件和硬件只能选择一个添加
                            if (isAddHaredOrPart) {
                                continue;
                            }
                            isAddHaredOrPart = true;
                            //sb产品  物料定义配件  产品拆分中定义为硬件 这里按照物料类别转化下
                            Byte redifineType = (materialInfo.getMaterialTypeId().equals(47)) ? Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()) : Byte.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode());
                            bsProductDetail.setType(redifineType);
                            mapBsProductDetail.put(bsProductDetail.getType(), bsProductDetail);
                        }
                    } else {
                        mapBsProductDetail.put(bsProductDetail.getType(), bsProductDetail);
                    }

                }
                for (Map.Entry<Byte, BsProductDetail> entry : mapBsProductDetail.entrySet()) {
                    listBsProductDetail.add(entry.getValue());
                }

                mapBsProductPrice.clear();
                BsProductHistoryPrice optSplitPrice = new BsProductHistoryPrice();
                for (AmProductSplitHistoryPrice splitPrice : productPrice) {
                    BsProductHistoryPrice bsProductPrice = new BsProductHistoryPrice();
                    bsProductPrice.setProductCode(bsProductCode);
                    bsProductPrice.setTime(splitPrice.getTime());
                    bsProductPrice.setType(splitPrice.getType());
                    bsProductPrice.setPrice(splitPrice.getPrice());
                    bsProductPrice.setCreatedBy(dealerUserInfo.getName());
                    bsProductPrice.setUpdatedBy(dealerUserInfo.getName());
                    bsProductPrice.setCreatedDate(JstUtils.getNowDate());
                    bsProductPrice.setUpdatedDate(JstUtils.getNowDate());
                    bsProductPrice.setDeletedFlag("N");
                    bsProductPrice.setProductType(splitPrice.getProductType());
                    bsProductPrice.setServiceType(splitPrice.getServiceType());
                    bsProductPrice.setTaxRate(splitPrice.getTaxRate());
                    bsProductPrice.setMaterialCode(splitPrice.getMaterialCode());
                    bsProductPrice.setMaterialName(splitPrice.getMaterialName());
                    if (splitPrice.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_HARD.getCode()))) {
                        if (materialInfo.getMaterialTypeId().equals(47)) {
                            bsProductPrice.setMaterialCode(merchantCart.getMaterialCode());
                            bsProductPrice.setMaterialName(merchantCart.getMaterialName());
                        }
                    }
                    if (splitPrice.getProductType().equals(String.valueOf(MaterialTypeEnum.MATERIAL_TYPE_PART.getCode()))) {
                        if (materialInfo.getMaterialTypeId().equals(46)) {
                            bsProductPrice.setMaterialCode(merchantCart.getMaterialCode());
                            bsProductPrice.setMaterialName(merchantCart.getMaterialName());
                        }
                    }
                    if (materialInfo.getMaterialCode().equals(splitPrice.getMaterialCode())) {
                        JstUtils.copyObject(optSplitPrice, bsProductPrice);
                    }
                    mapBsProductPrice.put(bsProductPrice.getProductType(), bsProductPrice);
                }

                for (Map.Entry<String, BsProductHistoryPrice> entry : mapBsProductPrice.entrySet()) {
                    if (entry.getValue().getProductType().equals(optSplitPrice.getProductType())) {
                        listBsProductPrice.add(optSplitPrice);
                    } else {
                        listBsProductPrice.add(entry.getValue());
                    }
                }
            }

            merchantCartDto.setMerchantCode(subMerchantOrderDto.getMerchantCode());
            merchantCartDto.setListCartDto(listMerchantCartDto);
            cartService.updateMerchantShoppingCartNoCatch(merchantCartDto);
            logisticsService.batchAddLogisticsNoCatch(listLogistics);
            bsProductService.BatchSubmitBsProductNoCatch(listBsProduct);
            bsProductService.BatchSubmitBsProductDetailNoCatch(listBsProductDetail);
            bsProductService.BatchSubmitBsProductPriceNoCatch(listBsProductPrice);
            ecMerchantOrderService.batchAddEcMerchantOrderNoCatch(listEcMerchantOrder);
            merchantOrderMapper.insertList(listMerchantOrder);
            merchantOrderDetailMapper.insertList(listMerchantOrderDetail);

            listLogistics = null;
            listMerchantOrder = null;
            listEcMerchantOrder = null;
            listMerchantOrderDetail = null;
            listBsProduct = null;
            listBsProductDetail = null;
            mapBsProductDetail = null;
            listBsProductPrice = null;
            mapBsProductPrice = null;
            listMerchantCartDto = null;
            merchantCartDto = null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
        listProductCode = null;
        listMaterialCode = null;
        mapProduct = null;
        mapProductDetail = null;
        mapProductPrice = null;
        mapMaterialInfo = null;
        logger.info("OrderService::submitShopOrder end result:{}", result);
        return result;
    }

    private String getChannelName(Byte channel) {
        if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getName();
        } else if (channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getName();
        }else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SEVER.getCode())){
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SEVER.getName();
        }else if(channel.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_EIGHT.getCode())){
        	return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_EIGHT.getName();
        }
        return "";
    }


    private JstShopShoppingCart getShopShoppingCartById(Integer id) {
        return cartService.getShopShoppingCartById(id);
    }

    private BsMerchantShoppingCart getMerchantShoppingCartById(Integer id) {
        return cartService.getMerchantShoppingCartById(id);
    }

    private Map<String, AmMaterialInfo> getAmMaterialInfo(List<String> listMaterialCode, Map<String, AmMaterialInfo> mapMaterialInfo) {
        return productService.batchGetAmMaterialInfoByMaterialCode(listMaterialCode, mapMaterialInfo);
    }

    private Map<String, List<AmProductSplitDetail>> getAmProductSplitDetail(List<String> listProductCode, Map<String, List<AmProductSplitDetail>> mapProductDetail) {
        return productService.listAmProductSplitDetail(listProductCode, mapProductDetail);
    }

    private Map<String, List<AmProductSplitHistoryPrice>> getAmProductSplitPrice(List<String> listProductCode, Map<String, List<AmProductSplitHistoryPrice>> mapProductPrice) {
        return productService.listAmProductSplitPrice(listProductCode, mapProductPrice);
    }

    private ProductDTO getProductBaseInfo(String productCode) {
        return productService.getProductBaseInfo(productCode);
    }

    private boolean isMaterialBelongProduct(String productCode, String materialCode, Map<String, Byte> mapMaterialType) {
        return productService.isMaterialBelongProduct(productCode, materialCode, mapMaterialType);
    }

    private JstShopAgentRelation getShopAgentMerchantRelation(String shopCode, String shopAgentMerchantCode) {
        return shopService.getShopAgentMerchantBean(shopCode, shopAgentMerchantCode);
    }

    private BsAddress getAddressById(Long id,String deleteFlag) {
        return addressService.getAddressById(id,deleteFlag);
    }

    private Byte getMerchantOrderStatuFromDto(String record) {
        if (StringUtils.isEmpty(record)) {
            return null;
        }
        Byte result = MerchantOrderStatuEnum.ORDER_WC.getCode();
        if (record.equals("WC")) {
            result = MerchantOrderStatuEnum.ORDER_WC.getCode();
            ;
        } else if (record.equals("WS")) {
            result = MerchantOrderStatuEnum.ORDER_WS.getCode();
        } else if (record.equals("FI")) {
            result = MerchantOrderStatuEnum.ORDER_FI.getCode();
        }
        return result;
    }

    public List<StatementSell> listMaterialNoOrder(String merchantCode) {
        List<deviceMerchantReflect> deviceMerchantReflects = new ArrayList<>();
        List<StatementSell> statementSells = new ArrayList<>();
        List<String> merchantCodeList = new ArrayList<>();
        deviceMerchantReflect deviceMerchantReflect = new deviceMerchantReflect();
        deviceMerchantReflect.setServerMerchantCode(merchantCode);
        deviceMerchantReflects = deviceMerchantReflectMapper.select(deviceMerchantReflect);
        if (deviceMerchantReflects != null && deviceMerchantReflects.size() > 0) {
            for (deviceMerchantReflect deviceMerchantReflect2 : deviceMerchantReflects) {
                merchantCodeList.add(deviceMerchantReflect2.getSendToMerchantCode());
            }
        }
        merchantCodeList.add(merchantCode);
        if(null == merchantCodeList || merchantCodeList.isEmpty()){
        	return statementSells;
        }
        statementSells = statementSellMapper.listStatement(merchantCodeList);
        return statementSells;
    }


}
