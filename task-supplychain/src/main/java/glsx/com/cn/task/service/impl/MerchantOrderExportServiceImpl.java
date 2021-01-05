package glsx.com.cn.task.service.impl;

import cn.com.glsx.supplychain.enums.DealerUserInfoChannelEnum;
import cn.com.glsx.supplychain.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.enums.ProductHistoryPriceEnum;
import cn.com.glsx.supplychain.model.bs.ProductType;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import glsx.com.cn.task.mapper.*;
import glsx.com.cn.task.model.*;
import glsx.com.cn.task.service.MerchantOrderExportService;
import glsx.com.cn.task.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MerchantOrderExport
 * @Author admin
 * @Param
 * @Date 2019/11/8 16:23
 * @Version
 **/
@Service
public class MerchantOrderExportServiceImpl implements MerchantOrderExportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchantOrderMapper merchantOrderMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductHistoryPriceMapper productHistoryPriceMapper;

    @Autowired
    private EcMerchantOrderMapper ecMerchantOrderMapper;


    public void merchantOrderExport() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<EcMerchantOrder> ecMerchantOrderList;
        List<MerchantOrderExcelVo> merchantOrderVoList = new ArrayList<>();
        List<MerchantOrder> merchantOrderAddList = new ArrayList<>();
        List<MerchantOrder> merchantOrderUpdateList = new ArrayList<>();
        //根据订单号list查询商户列表
        List<MerchantOrder> list = merchantOrderMapper.exportMerchantOrderExit();
        List<String> dispatchOrderNumberList = new ArrayList<>();
        Logistics logistics;
        for (MerchantOrder merchantOrder : list) {
            if (merchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())
                    || merchantOrder.getStatus().equals(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode())) {
                dispatchOrderNumberList.add(merchantOrder.getMerchantOrderDetailInfo().getDispatchOrderNumber());
                //填充现在的直接发货数量
                logistics = new Logistics();
                logistics.setServiceCode(merchantOrder.getMerchantOrderDetailInfo().getDispatchOrderNumber());
                List<Logistics> logisticsList = logisticsMapper.select(logistics);
                if (logisticsList != null && logisticsList.size() > 0) {
                    for (Logistics logisticsData : logisticsList) {
                        if (StringUtils.isEmpty(merchantOrder.getMerchantOrderDetailInfo().getShipmentsQuantity())) {
                            merchantOrder.getMerchantOrderDetailInfo().setShipmentsQuantity(0);
                        }
                        if (StringUtils.isEmpty(logisticsData.getShipmentsQuantity())) {
                            logisticsData.setShipmentsQuantity(0);
                        }
                        merchantOrder.getMerchantOrderDetailInfo().setShipmentsQuantity(merchantOrder.getMerchantOrderDetailInfo().getShipmentsQuantity()
                                + logisticsData.getShipmentsQuantity());
                    }
                }
            }
        }
        //填充现在的分配发货数量
        List<OrderInfoDetail> orderInfoList = orderInfoDetailMapper.getShipmentsQuantityByOrderCodeList(dispatchOrderNumberList);
        Integer orderInfoSum = 0;
        for (int i = 0; i < list.size(); i++) {
            orderInfoSum = 0;
            if (list.get(i).getMerchantOrderDetailInfo().getDispatchOrderNumber() != null) {
                if (StringUtils.isEmpty(list.get(i).getMerchantOrderDetailInfo().getShipmentsQuantity())
                        || list.get(i).getMerchantOrderDetailInfo().getShipmentsQuantity() < 1) {
                    for (int j = 0; j < orderInfoList.size(); j++) {
                        if (list.get(i).getMerchantOrderDetailInfo().getDispatchOrderNumber().equals(orderInfoList.get(j).getOrderCode())) {
                            orderInfoSum++;
                        }
                        if (orderInfoList.size() - j < 2) {
                            list.get(i).getMerchantOrderDetailInfo().setShipmentsQuantity(orderInfoSum);
                        }
                    }
                }
            }
        }

        //对比
        EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
        ecMerchantOrderList = ecMerchantOrderMapper.select(ecMerchantOrder);
        if (ecMerchantOrderList != null && ecMerchantOrderList.size() > 0) {
            int key;
            for (int i = 0; i < list.size(); i++) {
                key = 0;
                Date listUpdateDate = list.get(i).getUpdatedDate();
                String listUpdateDateStr = sdf.format(listUpdateDate);
                for (int j = 0; j < ecMerchantOrderList.size(); j++) {
                    Date ecMerchantOrderDate = ecMerchantOrderList.get(j).getUpdatedDate();
                    String ecMerchantOrderDateStr = sdf.format(ecMerchantOrderDate);
                    //对比修改时间
                    if (list.get(i).getOrderNumber().equals(ecMerchantOrderList.get(j).getOrderNumber())
                            && !listUpdateDateStr.equals(ecMerchantOrderDateStr)) {
                        merchantOrderUpdateList.add(list.get(i));
                        key++;
                        //对比发货数量
                    } else if (list.get(i).getOrderNumber().equals(ecMerchantOrderList.get(j).getOrderNumber())) {
                        if (StringUtils.isEmpty(list.get(i).getMerchantOrderDetailInfo().getShipmentsQuantity())) {
                            list.get(i).getMerchantOrderDetailInfo().setShipmentsQuantity(0);
                        }
                        if (StringUtils.isEmpty(ecMerchantOrderList.get(j).getAlreadyShipmentQuantity())) {
                            ecMerchantOrderList.get(j).setAlreadyShipmentQuantity(0);
                        }
                        if (list.get(i).getStatus().equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())
                                || list.get(i).getStatus().equals(MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode())) {
                            if (!list.get(i).getMerchantOrderDetailInfo().getShipmentsQuantity().equals(ecMerchantOrderList.get(j).getAlreadyShipmentQuantity())) {
                                merchantOrderUpdateList.add(list.get(i));
                            }
                        }
                        key++;
                    }
                    if (ecMerchantOrderList.size() - j < 2 && key == 0) {
                        merchantOrderAddList.add(list.get(i));
                    }
                }
            }
        } else {
            merchantOrderAddList.addAll(list);
        }
        add(merchantOrderAddList, true);
        add(merchantOrderUpdateList, false);

        logger.info("exportMerchantOrderExit return merchantOrderVoList.size()" + merchantOrderVoList.size());
    }

    void add(List<MerchantOrder> merchantOrderList, Boolean key) {
        List<EcMerchantOrder> ecMerchantOrderListOne = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (merchantOrderList.size() > 0) {
            for (int i = 0; i < merchantOrderList.size(); i++) {
                MerchantOrderDetail merchantOrderDetails = new MerchantOrderDetail();
                merchantOrderDetails.setMerchantOrderNumber(merchantOrderList.get(i).getOrderNumber());
                //查询商户订单详情列表
                MerchantOrderDetail merchantOrderDetail = merchantOrderDetailMapper.selectOne(merchantOrderDetails);

                //填充Excel字段
                MerchantOrderExcelVo merchantOrderExcelVo = new MerchantOrderExcelVo();
                if (merchantOrderList.get(i).getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode()) {
                    merchantOrderExcelVo.setChannel("广汇代销");
                } else if (merchantOrderList.get(i).getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode()) {
                    merchantOrderExcelVo.setChannel("金融风控代销");
                } else if (merchantOrderList.get(i).getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getCode()) {
                    merchantOrderExcelVo.setChannel("同盟会渠道");
                } else if (merchantOrderList.get(i).getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getCode()) {
                    merchantOrderExcelVo.setChannel("金融渠道");
                } else if (merchantOrderList.get(i).getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode()) {
                    merchantOrderExcelVo.setChannel("亿咖通");
                } else if (merchantOrderList.get(i).getChannel() == DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode()){
                    merchantOrderExcelVo.setChannel("特殊渠道(指定产品)");
                }
                merchantOrderExcelVo.setMerchantCode(merchantOrderList.get(i).getMerchantCode());
                merchantOrderExcelVo.setMerchantName(merchantOrderList.get(i).getMerchantName());
                merchantOrderExcelVo.setOrderNumber(merchantOrderDetail.getMerchantOrderNumber());
                //查询单价
                ProductHistoryPrice productHistoryPrice = new ProductHistoryPrice();
                productHistoryPrice.setProductCode(merchantOrderDetail.getProductCode());
                productHistoryPrice.setType(ProductHistoryPriceEnum.PRODUCT_HISTORY_PRICE_NOW.getCode());
                List<ProductHistoryPrice> productHistoryPriceList = productHistoryPriceMapper.getProductHistoryPriceByCodeOrType(productHistoryPrice);
                Double priceSum = 0.0;
                if (productHistoryPriceList != null && productHistoryPriceList.size() > 0) {
                    for (ProductHistoryPrice price : productHistoryPriceList) {
                        priceSum = priceSum + price.getPrice();
                    }
                }
                merchantOrderExcelVo.setPrice(priceSum);
                //获取产品信息
                Product productInfo;
                Product product = new Product();
                product.setCode(merchantOrderDetail.getProductCode());
                product.setDeletedFlag("N");
                productInfo = productMapper.getProduct(product);
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductCode(merchantOrderDetail.getProductCode());
                List<ProductDetail> productDetails;
                productDetails = productDetailMapper.select(productDetail);
                productInfo.setProductDetailList(productDetails);
                if (productInfo.getType() != null && !productInfo.getType().equals("null")) {
                    ProductType productType = new ProductType();
                    productType.setId(Long.valueOf(productInfo.getType()));
                    //ProductType productTypeInfo = productTypeMapper.getProductType(productType);
                    DeviceType deviceType = deviceTypeMapper.selectByPrimaryKey(Integer.valueOf(productInfo.getType()));
                    if (!StringUtils.isEmpty(deviceType)) {
                        merchantOrderExcelVo.setDeviceType(deviceType.getName());
                    }
                }

                if (productInfo.getProductDetailList() != null && productInfo.getProductDetailList().size() > 0) {
                    for (int j = 0; j < productInfo.getProductDetailList().size(); j++) {//物料编号拼接
                        if (!StringUtil.isEmpty(productInfo.getProductDetailList().get(j).getType())) {
                            if (productInfo.getProductDetailList().get(j).getType() == (byte) 0 ||
                                    productInfo.getProductDetailList().get(j).getType() == (byte) 7) {
                                merchantOrderExcelVo.setProductCode(productInfo.getProductDetailList().get(j).getMaterialCode());
                            }
                        }
                    }
                }
                merchantOrderExcelVo.setProductName(productInfo.getName());

                //转换下单日期格式
                Date orderTimeOne = merchantOrderList.get(i).getOrderTime();
                String strOrderTime = formatter.format(orderTimeOne);
                merchantOrderExcelVo.setOrderTime(strOrderTime);
                merchantOrderExcelVo.setOrderQuantity(merchantOrderList.get(i).getTotalOrder());
                merchantOrderExcelVo.setCheckQuantity(merchantOrderList.get(i).getTotalCheck());
                if (!StringUtil.isEmpty(merchantOrderDetail.getDispatchOrderNumber())) {
                    merchantOrderExcelVo.setDispatchOrderNumber(merchantOrderDetail.getDispatchOrderNumber());
                }
                //查询物流信息填充发货数量
                Logistics logistics1 = new Logistics();
                logistics1.setServiceCode(merchantOrderDetail.getDispatchOrderNumber());
                String shipmentTime = "";
                String shipmentQuantity = "";
                String newshipmentQuantity = "";
                Integer alreadyShipmentsQuantity = 0;//总发货数量
                if (!StringUtils.isEmpty(logistics1.getServiceCode())) {
                    List<Logistics> logisticsList1 = logisticsMapper.select(logistics1);
                    if (logisticsList1.size() > 0) {
                        List<OrderInfoDetail> orderInfoDetailList = new ArrayList<>();
                        //先去拿直接发货数量
                        for (int k = 0; k < logisticsList1.size(); k++) {
                            if (logisticsList1.get(k).getShipmentsQuantity() != null) {
                                alreadyShipmentsQuantity = alreadyShipmentsQuantity + logisticsList1.get(k).getShipmentsQuantity();
                            }
                            if (logisticsList1.size() >= 2) {
                                shipmentTime += formatter.format(logisticsList1.get(k).getCreatedDate());
                                if (logisticsList1.size() - k != 1) {
                                    shipmentTime += ",";
                                }
                                shipmentQuantity += logisticsList1.get(k).getShipmentsQuantity();
                                if (logisticsList1.size() - k != 1) {
                                    shipmentQuantity += ",";
                                }
                            } else {
                                shipmentTime = formatter.format(logisticsList1.get(k).getCreatedDate());
                                shipmentQuantity = String.valueOf(logisticsList1.get(k).getShipmentsQuantity());
                            }
                            Long logisticeId = logisticsList1.get(k).getId();
                            OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
                            orderInfoDetail.setLogisticsId(logisticeId.intValue());
                            orderInfoDetailList.add(orderInfoDetail);

                        }

                        //通过物流ID去订单详情拿分配发货数量
                        if (shipmentQuantity.contains("null")) {
                            List<OrderInfoDetail> orderInfoDetails = orderInfoDetailMapper.listOrderInfoDetail(orderInfoDetailList);
                            if (orderInfoDetails != null && orderInfoDetailList.size() > 0) {
                                newshipmentQuantity = String.valueOf(orderInfoDetails.size());
                            }
                            merchantOrderExcelVo.setShipmentQuantity(newshipmentQuantity);
                        } else {
                            merchantOrderExcelVo.setShipmentQuantity(shipmentQuantity);
                        }

                        //已发货总数
                        if (alreadyShipmentsQuantity == 0) {
                            Integer Quantity = orderInfoMapper.getShipmentsQuantityByOrderCode(merchantOrderDetail.getDispatchOrderNumber());
                            if (Quantity != 0) {
                                alreadyShipmentsQuantity = Quantity;
                            }
                        }
                        merchantOrderExcelVo.setAlreadyShipmentQuantity(alreadyShipmentsQuantity);
                        merchantOrderExcelVo.setSignQuantity(merchantOrderDetail.getAcceptQuantity());
                        merchantOrderExcelVo.setTotalAmount(priceSum * merchantOrderList.get(i).getTotalCheck());
                        merchantOrderExcelVo.setShipmentTime(shipmentTime);

                    }
                }
                if (alreadyShipmentsQuantity == 0) {
                    merchantOrderExcelVo.setOweQuantity(merchantOrderList.get(i).getTotalCheck());
                } else {
                    merchantOrderExcelVo.setOweQuantity(merchantOrderList.get(i).getTotalCheck() - alreadyShipmentsQuantity);
                }
                if (!StringUtil.isEmpty(merchantOrderDetail.getProductRemarks())) {//产品备注
                    merchantOrderExcelVo.setProductRemarks(merchantOrderDetail.getProductRemarks());
                } else {
                    merchantOrderExcelVo.setProductRemarks(merchantOrderList.get(i).getRemarks());
                }
                merchantOrderExcelVo.setCheckBy(merchantOrderDetail.getCheckBy());
                if (merchantOrderDetail.getCheckTime() != null) {
                    String checkTime = formatter.format(merchantOrderDetail.getCheckTime());
                    merchantOrderExcelVo.setCheckTime(checkTime);
                }
                Logistics logistics = new Logistics();
                logistics.setServiceCode(merchantOrderList.get(i).getOrderNumber());
                Logistics logisticsInfo = logisticsMapper.selectOne(logistics);
                if (logisticsInfo != null) {
                    if (!StringUtil.isEmpty(logisticsInfo)) {
                        Address address = addressMapper.selectById(logisticsInfo.getReceiveId());
                        if (!StringUtil.isEmpty(address)) {
                            //查询地址信息
                            merchantOrderExcelVo.setAddressee(address.getName());
                            merchantOrderExcelVo.setMobile(address.getMobile());
                            if (address.getProvinceName() != null) {
                                merchantOrderExcelVo.setAddressDetail(address.getProvinceName() + address.getCityName() + address.getAreaName() + address.getAddress());
                            } else {
                                merchantOrderExcelVo.setAddressDetail(address.getAddress());
                            }
                        }
                    }
                }


                //转换商户订单状态
                if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode()) {
                    merchantOrderExcelVo.setStatus("已驳回");
                } else if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode()) {
                    merchantOrderExcelVo.setStatus("待审核");
                } else if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()) {
                    merchantOrderExcelVo.setStatus("待发货");
                } else if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode()) {
                    merchantOrderExcelVo.setStatus("待签收");
                } else if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_PORTION_RECEIVE.getCode()) {
                    merchantOrderExcelVo.setStatus("部分签收");
                } else if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode()) {
                    merchantOrderExcelVo.setStatus("已完成");
                } else if (merchantOrderList.get(i).getStatus() == MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()) {
                    merchantOrderExcelVo.setStatus("已作废");
                }
                EcMerchantOrder ecMerchantOrderOne = new EcMerchantOrder();
                ecMerchantOrderOne.setChannel(merchantOrderExcelVo.getChannel());
                ecMerchantOrderOne.setMerchantCode(merchantOrderExcelVo.getMerchantCode());
                ecMerchantOrderOne.setMerchantName(merchantOrderExcelVo.getMerchantName());
                ecMerchantOrderOne.setOrderNumber(merchantOrderExcelVo.getOrderNumber());
                ecMerchantOrderOne.setProductName(merchantOrderExcelVo.getProductName());
                ecMerchantOrderOne.setProductCode(merchantOrderExcelVo.getProductCode());
                ecMerchantOrderOne.setDeviceType(merchantOrderExcelVo.getDeviceType());
                ecMerchantOrderOne.setPrice(merchantOrderExcelVo.getPrice());
                ecMerchantOrderOne.setOrderQuantity(merchantOrderExcelVo.getOrderQuantity());
                ecMerchantOrderOne.setCheckQuantity(merchantOrderExcelVo.getCheckQuantity());
                ecMerchantOrderOne.setDispatchOrderNumber(merchantOrderExcelVo.getDispatchOrderNumber());
                ecMerchantOrderOne.setAlreadyShipmentQuantity(merchantOrderExcelVo.getAlreadyShipmentQuantity());
                ecMerchantOrderOne.setShipmentTime(merchantOrderExcelVo.getShipmentTime());
                ecMerchantOrderOne.setShipmentQuantity(merchantOrderExcelVo.getShipmentQuantity());
                ecMerchantOrderOne.setSignQuantity(merchantOrderExcelVo.getSignQuantity());
                ecMerchantOrderOne.setOweQuantity(merchantOrderExcelVo.getOweQuantity());
                ecMerchantOrderOne.setTotalAmount(merchantOrderExcelVo.getTotalAmount());
                ecMerchantOrderOne.setOrderTime(merchantOrderExcelVo.getOrderTime());
                ecMerchantOrderOne.setProductRemarks(merchantOrderExcelVo.getProductRemarks());
                ecMerchantOrderOne.setCheckBy(merchantOrderExcelVo.getCheckBy());
                ecMerchantOrderOne.setCheckTime(merchantOrderExcelVo.getCheckTime());
                ecMerchantOrderOne.setStatus(merchantOrderExcelVo.getStatus());
                ecMerchantOrderOne.setAddressee(merchantOrderExcelVo.getAddressee());
                ecMerchantOrderOne.setMobile(merchantOrderExcelVo.getMobile());
                ecMerchantOrderOne.setAddressDetail(merchantOrderExcelVo.getAddressDetail());
                ecMerchantOrderOne.setCreatedBy(merchantOrderList.get(i).getCreatedBy());
                ecMerchantOrderOne.setCreatedDate(merchantOrderList.get(i).getCreatedDate());
                ecMerchantOrderOne.setUpdatedBy(merchantOrderList.get(i).getUpdatedBy());
                ecMerchantOrderOne.setUpdatedDate(merchantOrderList.get(i).getUpdatedDate());
                ecMerchantOrderOne.setDeletedFlag(merchantOrderList.get(i).getDeletedFlag());

                if (key) {
                    ecMerchantOrderListOne.add(ecMerchantOrderOne);
                } else {
                    ecMerchantOrderMapper.updateByOrderNumber(ecMerchantOrderOne);
                }
            }
            if (key) {
                ecMerchantOrderMapper.insertEcMerchantOrder(ecMerchantOrderListOne);
            }
        }
    }
}
