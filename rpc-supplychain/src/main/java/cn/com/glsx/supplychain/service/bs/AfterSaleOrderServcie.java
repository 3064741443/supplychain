package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.mapper.*;
import cn.com.glsx.supplychain.mapper.bs.*;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.DeviceManagerAdminRemoteService;
import cn.com.glsx.supplychain.service.SupplyChainAdminRemoteService;
import cn.com.glsx.supplychain.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AfterSaleOrderServcie {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterSaleOrderServcie.class);

    @Autowired
    private AfterSaleOrderMapper afterSaleOrderMapper;

    @Autowired
    private AfterSaleOrderDetailMapper afterSaleOrderDetailMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private MerchantOrderDetailMapper merchantOrderDetailMapper;

    @Autowired
    private SupplyChainAdminRemoteService adminService;

    @Autowired
    private SalesManagerService salesManagerService;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private MaintainSnChangeMapper maintainSnChangeMapper;

    @Autowired
    private MaintainProductDetailMapper maintainProductDetailMapper;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private DeviceResetRecordMapper deviceResetRecordMapper;

    @Autowired
    private MaintainProductMapper maintainProductMapper;

    @Autowired
    private DeviceFileMapper deviceFileMapper;

    @Autowired
    private DeviceCodeMapper deviceCodeMapper;

    @Autowired
    private DeviceManagerAdminRemoteService deviceManagerAdminRemoteService;

    @Autowired
    private DealerUserInfoMapper dealerUserInfoMapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * 获取售后订单分页列表
     *
     * @param AfterSaleOrder 售后订单对象
     * @return
     */
    public Page<AfterSaleOrder> listAfterSaleOrder(int pageNum, int pageSize, AfterSaleOrder AfterSaleOrder) {
        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, AfterSaleOrder);
        Page<AfterSaleOrder> result;
        PageHelper.startPage(pageNum, pageSize);
        result = afterSaleOrderMapper.listAfterSaleOrder(AfterSaleOrder);
        return result;
    }


    /**
     * 获取售后订单详情
     *
     * @param afterSaleOrder
     * @return
     */
    public AfterSaleOrder getAfterSaleOrder(AfterSaleOrder afterSaleOrder) {
        LOGGER.info("查询售后订单详情的订单号:{}", afterSaleOrder.getOrderNumber());
        AfterSaleOrder result = new AfterSaleOrder();
        result.setOrderNumber(afterSaleOrder.getOrderNumber());
        result = afterSaleOrderMapper.selectOne(result);
        AfterSaleOrderDetail afterSaleOrderDetail = new AfterSaleOrderDetail();
        afterSaleOrderDetail.setAfterSaleOrderNumber(result.getOrderNumber());
        //填充订单详情
        result.setAfterSaleOrderDetailList(afterSaleOrderDetailMapper.select(afterSaleOrderDetail));
        //填充物流信息
        Logistics logistics = new Logistics();
        logistics.setType(afterSaleOrder.getLogistics().getType());
        logistics.setServiceCode(afterSaleOrder.getOrderNumber());
        result.setLogistics(logisticsService.getLogistics(logistics));
        //获取物流List
        List<AfterSaleOrderDetail>afterSaleOrderDetails = result.getAfterSaleOrderDetailList();
        List<Logistics>newLogistics = new ArrayList<>();
        if(afterSaleOrderDetails != null && afterSaleOrderDetails.size() > 0){
            afterSaleOrderDetails = afterSaleOrderDetails.stream().distinct().collect(Collectors.toList());
            for(AfterSaleOrderDetail list : afterSaleOrderDetails){
                if(list.getLogisticsId() != null){
                 Logistics logisticId = new Logistics();
                 logisticId.setId(list.getLogisticsId());
                 Logistics logisticsInfo = logisticsMapper.selectByLogisticsId(logisticId);
                 newLogistics.add(logisticsInfo);
                }
            }
        }
        result.setLogisticsList(newLogistics);
        //获取实际签收数量
        AfterSaleOrder afterSaleOrderInfo = afterSaleOrderMapper.getAfterSaleOrderSignQuantity(result);
        if(!StringUtil.isEmpty(afterSaleOrderInfo)){
            Integer signQuantity = afterSaleOrderInfo.getSignQuantity();
            result.setSignQuantity(signQuantity);
        }
        LOGGER.info("获取maintainSnChangeList信息根据售后订单号:{}", afterSaleOrder.getOrderNumber());
        MaintainProductDetail maintainProductDetail = new MaintainProductDetail();
        maintainProductDetail.setAfterSaleOrderNumber(afterSaleOrder.getOrderNumber());
        List<MaintainProductDetail>maintainProductDetailList = maintainProductDetailMapper.getMainTainProductDetailList(maintainProductDetail);
        List<MaintainSnChange>maintainSnChanges = new ArrayList<>();
        if(maintainProductDetailList != null && maintainProductDetailList.size() > 0){
            for(int i=0;i<maintainProductDetailList.size();i++){
                MaintainSnChange maintainSnChange = maintainSnChangeMapper.selectByMainTainDetailId(maintainProductDetailList.get(i).getId());
                maintainSnChanges.add(maintainSnChange);
            }
        }
        result.setMaintainSnChangeList(maintainSnChanges);
        //维修总费用
        MaintainProduct maintainProduct = new MaintainProduct();
        maintainProduct.setAfterSaleOrderNumber(afterSaleOrder.getOrderNumber());
        MaintainProduct maintainProductInfo = maintainProductMapper.getMainTainProductInfo(maintainProduct);
        if(!StringUtil.isEmpty(maintainProductInfo)){
            result.setRepairCost(maintainProductInfo.getRepairCost());
        }
        return result;
    }


    /**
     * 新增售后订单
     *
     * @param afterSaleOrder
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {RpcServiceException.class})
    public Integer add(AfterSaleOrder afterSaleOrder) {
        LOGGER.info("新增售后订单:{}", afterSaleOrder);
        Integer result;
        String orderCode = StringUtil.getOrderNo(); // 生成订单号
        afterSaleOrder.setOrderNumber(orderCode);
        afterSaleOrder.setStatus(AfterSaleOrderStatusEnum.AFTER_SALE_ORDER_WAIT_CHECK.getCode());
        afterSaleOrder.setOrderTime(new Date());
        afterSaleOrder.setCreatedDate(new Date());
        afterSaleOrder.setUpdatedDate(new Date());
        afterSaleOrder.setDeletedFlag("N");
        result = afterSaleOrderMapper.insert(afterSaleOrder);
        for (int i = 0; i < afterSaleOrder.getAfterSaleOrderDetailList().size(); i++) {
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setAfterSaleOrderNumber(afterSaleOrder.getOrderNumber());
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setStatus(AfterSaleOrderDetailStatusEnum.AFTER_SALE_ORDER_APPLY_SN.getCode());
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setCreatedBy(afterSaleOrder.getCreatedBy());
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setCreatedDate(new Date());
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setUpdatedBy(afterSaleOrder.getUpdatedBy());
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setUpdatedDate(new Date());
            afterSaleOrder.getAfterSaleOrderDetailList().get(i).setDeletedFlag("N");
        }
        afterSaleOrderDetailMapper.insertList(afterSaleOrder.getAfterSaleOrderDetailList());
        List<Address> addressesList;
        //查询地址是否存在
        if(afterSaleOrder.getLogistics().getReceiveAddress() != null){
             addressesList = addressService.getAddressList(afterSaleOrder.getLogistics().getReceiveAddress());
            if (!StringUtil.isEmpty(addressesList) && addressesList.size() < 1) {
                afterSaleOrder.getLogistics().getReceiveAddress().setMerchantCode(afterSaleOrder.getMerchantCode());
                addressService.add(afterSaleOrder.getLogistics().getReceiveAddress());
                afterSaleOrder.getLogistics().setReceiveId(afterSaleOrder.getLogistics().getReceiveAddress().getId());
            } else {
                afterSaleOrder.getLogistics().setReceiveId(addressesList.get(0).getId());
            }
        }
        //填充售后订单物流信息
        String logisticsCode = StringUtil.getOrderNo();
        afterSaleOrder.getLogistics().setCode(logisticsCode);
        afterSaleOrder.getLogistics().setType(LogisticsTypeEnum.AFTER_SALE_ORDER.getCode());
        afterSaleOrder.getLogistics().setServiceCode(afterSaleOrder.getOrderNumber());
        if(afterSaleOrder.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_REPAIR.getCode()){
            logisticsService.add(afterSaleOrder.getLogistics());
        }
        return result;
    }

    public Integer updateByOrderNumber(AfterSaleOrder afterSaleOrder) {
        LOGGER.info("修改售后订单入参:{}", afterSaleOrder);
        Integer result;
        afterSaleOrder.setUpdatedDate(new Date());
        result = afterSaleOrderMapper.updateByOrderNumber(afterSaleOrder);
        //查询售后订单类型
        AfterSaleOrder afterSaleOrder1 = new AfterSaleOrder();
        afterSaleOrder1.setOrderNumber(afterSaleOrder.getOrderNumber());
        AfterSaleOrder afterSaleOrdeType = afterSaleOrderMapper.selectOne(afterSaleOrder1);
        if(afterSaleOrdeType.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_RETURN.getCode()) {
            if(afterSaleOrdeType.getStatus() == AfterSaleOrderStatusEnum.AFTER_SALE_ORDER_WAIT_SEND.getCode()){
                Sales sales = new Sales();
                List<Sales>salesList = new ArrayList<>();
                sales.setMerchantCode(afterSaleOrder.getMerchantCode());
                sales.setProductCode(afterSaleOrder.getProductCode());
                sales.setOrderNumber(afterSaleOrder.getOrderNumber());
                sales.setQuantity(afterSaleOrder.getNumber()*(-1));
                sales.setStatus(SalesReconciliationStatus.SALES_STATUS_NOT_RECONCILATION.getCode());
                sales.setDispatchTime(afterSaleOrdeType.getCreatedDate());
                sales.setUpdatedDate(new Date());
                sales.setCreatedDate(new Date());
                sales.setCreatedBy(afterSaleOrder.getCreatedBy());
                sales.setUpdatedBy(afterSaleOrder.getUpdatedBy());
                sales.setDeletedFlag("N");
                salesList.add(sales);
                salesManagerService.add(salesList);
            }
        }
        //售后订单批次发货
        String dispatchOrderNumber = StringUtil.getOrderNo();
        //生成物流code
        String LogisCode = StringUtil.getDispatchOrderNumber();
        if (afterSaleOrder.getMaintainSnChangeList() !=null && afterSaleOrder.getMaintainSnChangeList().size() > 0) {
            LOGGER.info("AfterSaleOrderServcie::updateByOrderNumber updateByPrimaryKey start");
            if(!StringUtil.isEmpty(afterSaleOrder.getLogistics())){
                afterSaleOrder.getLogistics().setCode(LogisCode);
                afterSaleOrder.getLogistics().setServiceCode(dispatchOrderNumber);
                afterSaleOrder.getLogistics().setAccept("N");
                afterSaleOrder.getLogistics().setShipmentsQuantity(afterSaleOrder.getMaintainSnChangeList().size());
                afterSaleOrder.getLogistics().setCreatedBy(afterSaleOrdeType.getCreatedBy());
                afterSaleOrder.getLogistics().setCreatedDate(new Date());
                afterSaleOrder.getLogistics().setUpdatedBy(afterSaleOrdeType.getUpdatedBy());
                afterSaleOrder.getLogistics().setUpdatedDate(new Date());
                afterSaleOrder.getLogistics().setDeletedFlag("N");
                logisticsMapper.insertSelective(afterSaleOrder.getLogistics());
            }
            for(int i =0;i<afterSaleOrder.getMaintainSnChangeList().size();i++){
                MaintainProductDetail maintainProductDetailInfo = maintainProductDetailMapper.selectById(afterSaleOrder.getMaintainSnChangeList().get(i).getMainTainProductDetailId());
                //售后订单详情信息
                if(!StringUtil.isEmpty(maintainProductDetailInfo)){
                    AfterSaleOrderDetail afterSaleOrderDetail = new AfterSaleOrderDetail();
                    afterSaleOrderDetail.setAfterSaleOrderNumber(maintainProductDetailInfo.getAfterSaleOrderNumber());
                    afterSaleOrderDetail.setSn(maintainProductDetailInfo.getSn()==null?maintainProductDetailInfo.getImei():maintainProductDetailInfo.getSn());
                    afterSaleOrderDetail.setStatus(AfterSaleOrderDetailStatusEnum.AFTER_SALE_ORDER_SIGN_SN.getCode());
                    AfterSaleOrderDetail afterSaleOrderDetailInfo = afterSaleOrderDetailMapper.selectOne(afterSaleOrderDetail);
                    if(!StringUtil.isEmpty(afterSaleOrderDetailInfo)){
                        AfterSaleOrderDetail asod = new AfterSaleOrderDetail();
                        asod.setId(afterSaleOrderDetailInfo.getId());
                        asod.setLogisticsId(afterSaleOrder.getLogistics().getId());
                        afterSaleOrderDetailMapper.updateAfterSaleDetailLogisticsId(asod);//为售后订单详情添加对应物流ID
                    }
                }
            }

            //查看SN是否切换，需要去查看是SN否存在库存中（存在就需要初始化）
            for (MaintainSnChange list : afterSaleOrder.getMaintainSnChangeList()) {
                LOGGER.info("SN切换的的路径start+++++++++++++++++++++++:{}");
                dispatchOrderNumber = StringUtil.getOrderNo();
                MaintainProductDetail maintainProductDetail = maintainProductDetailMapper.selectById(list.getMainTainProductDetailId());
                DeviceFile deviceFile = deviceFileMapper.selectBySn(maintainProductDetail.getSn()==null?maintainProductDetail.getImei():maintainProductDetail.getSn());
                DeviceCode deviceCode = deviceCodeMapper.selectByPrimaryKey(deviceFile.getDeviceCode());
                DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(maintainProductDetail.getSn()==null?maintainProductDetail.getImei():maintainProductDetail.getSn());
                Integer newLogisticsId = Integer.parseInt(String.valueOf(afterSaleOrder.getLogistics().getId()));
                String strAttribCode = "";
                if(StringUtil.isEmpty(deviceInfo) || deviceInfo.getDeletedFlag().equals("Y"))
                {
                    strAttribCode = "unknown" + deviceCode.getTypeId();
                }
                else
                {
                    strAttribCode = deviceInfo.getAttribCode();
                }

                DealerUserInfo dealerUserInfo = new DealerUserInfo();
                dealerUserInfo.setMerchantCode(afterSaleOrdeType.getMerchantCode());
                DealerUserInfo dealerUserName =dealerUserInfoMapper.selectOne(dealerUserInfo);

                Address address = new Address();
                address.setId(afterSaleOrder.getLogistics().getReceiveId());
                Address addressInfo = addressMapper.selectOne(address);

                OrderInfo newOrderInfo = new OrderInfo();
                newOrderInfo.setOrderCode(dispatchOrderNumber);
                newOrderInfo.setTotal(1);
                newOrderInfo.setStatus(OrderStatusEnum.STATUS_OV.getValue());
                newOrderInfo.setDeviceId(deviceFile.getDeviceCode());
                if(!StringUtil.isEmpty(deviceCode))
                {
                    newOrderInfo.setDeviceName(deviceCode.getDeviceName());
                }
                newOrderInfo.setPackageOne(String.valueOf(deviceFile.getPackageId()));
                newOrderInfo.setPackageTwo(String.valueOf(deviceFile.getPackageId()));
                newOrderInfo.setAttribCode(strAttribCode);
                newOrderInfo.setOperatorMerchantNo("1");
                newOrderInfo.setBatch("123");
                newOrderInfo.setWarehouseId(5);
                newOrderInfo.setSendMerchantNo(dealerUserName.getMerchantCode());
                newOrderInfo.setSendMerchantName(dealerUserName.getMerchantName());
                newOrderInfo.setAddress(addressInfo.getCityName()+addressInfo.getAreaName()+addressInfo.getAddress());
                newOrderInfo.setMobile(addressInfo.getMobile());
                newOrderInfo.setContacts(addressInfo.getName());
                newOrderInfo.setCreatedBy(afterSaleOrdeType.getCreatedBy());
                newOrderInfo.setUpdatedBy(afterSaleOrdeType.getCreatedBy());
                newOrderInfo.setCreatedDate(new Date());
                newOrderInfo.setUpdatedDate(new Date());

                OrderInfoDetail newOrderInfoDetail = new OrderInfoDetail();
                newOrderInfoDetail.setOrderCode(dispatchOrderNumber);
                if(!StringUtil.isEmpty(list.getIccid()))
                {
                    newOrderInfoDetail.setIccid(list.getIccid());
                }
                else
                {
                    if(!StringUtil.isEmpty(deviceInfo)&&deviceInfo.getDeletedFlag().equals("N"))
                    {
                        newOrderInfoDetail.setIccid(deviceInfo.getIccid());
                    }
                }
                newOrderInfoDetail.setImei(list.getImei());
                newOrderInfoDetail.setSn(StringUtils.isEmpty(list.getSn())?list.getImei():list.getSn());
                newOrderInfoDetail.setAttribCode(strAttribCode);
                newOrderInfoDetail.setBatch("123");
                newOrderInfoDetail.setWarehouseId(5);
                newOrderInfoDetail.setWarehouseIdUp(5);
                newOrderInfoDetail.setCreatedBy(afterSaleOrdeType.getCreatedBy());
                newOrderInfoDetail.setUpdatedBy(afterSaleOrdeType.getCreatedBy());
                newOrderInfoDetail.setCreatedDate(new Date());
                newOrderInfoDetail.setUpdatedDate(new Date());
                newOrderInfoDetail.setDeletedFlag("N");
                newOrderInfoDetail.setLogisticsId(newLogisticsId);


                DeviceInfo newDeviceInfo = new DeviceInfo();
                if(!StringUtil.isEmpty(list.getIccid()))
                {
                    newDeviceInfo.setIccid(list.getIccid());
                }
                else
                {
                    if(!StringUtil.isEmpty(deviceInfo)&&deviceInfo.getDeletedFlag().equals("N"))
                    {
                        newDeviceInfo.setIccid(deviceInfo.getIccid());
                    }
                }

                if(!StringUtils.isEmpty(list.getImei()))
                {
                    newDeviceInfo.setImei(list.getImei());
                }
                else
                {
                    if(!StringUtil.isEmpty(deviceInfo)&&deviceInfo.getDeletedFlag().equals("N"))
                    {
                        newDeviceInfo.setImei(deviceInfo.getImei());
                    }
                }

                newDeviceInfo.setSn(StringUtils.isEmpty(list.getSn())?list.getImei():list.getSn());
                newDeviceInfo.setAttribCode(strAttribCode);
                newDeviceInfo.setBatch("123");
                newDeviceInfo.setStatus(DeviceEnum.STATUS_OUT.getValue());
                newDeviceInfo.setWareHouseId(5);
                newDeviceInfo.setWareHouseIdUp(5);
                newDeviceInfo.setOrderCode(dispatchOrderNumber);
                newDeviceInfo.setCreatedBy(afterSaleOrdeType.getCreatedBy());
                newDeviceInfo.setUpdatedBy(afterSaleOrdeType.getCreatedBy());
                newDeviceInfo.setCreatedDate(new Date());
                newDeviceInfo.setUpdatedDate(new Date());
                newDeviceInfo.setDeletedFlag("N");

                DeviceResetRecord deviceResetRecord = new DeviceResetRecord();
                deviceResetRecord.setSn(list.getSn()==null?list.getImei():list.getSn());
                deviceResetRecord = deviceManagerAdminRemoteService.initDeviceFileByDeviceResetRecord(deviceResetRecord);
                if (deviceResetRecord != null && deviceResetRecord.getSn() != null) {
                    LOGGER.info("kafka发送初始化消息的sn：" + deviceResetRecord.getSn());
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("sn", deviceResetRecord.getSn());
                    kafkaProducer.sendObject(JSONObject.toJSONString(params).getBytes());
                    LOGGER.info("kafka发送初始化消息结束的sn：" + deviceResetRecord.getSn());
                } else {
                    LOGGER.error("kafka发送初始化消息发送失败对象：{}", deviceResetRecord);
                }
                orderInfoMapper.insertOrderInfo(newOrderInfo);
                orderInfoDetailMapper.insertSelective(newOrderInfoDetail);
                deviceInfoMapper.addDeviceInfoOnDuplicateKey(newDeviceInfo);




               /*DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(list.getSn()==null?list.getImei():list.getSn());
                if(list.getSnChangeFlag() == MaintainSnChangeFlagEnum.MAINTAIN_SN_CHANGE_FLAG_NO.getCode()){
                    //SN存在设备库存中，需进行初始化
                    if (!StringUtil.isEmpty(deviceInfo)) {
                        DeviceResetRecord deviceResetRecord = new DeviceResetRecord();
                        deviceResetRecord.setSn(list.getSn()==null?list.getImei():list.getSn());
                        deviceManagerAdminRemoteService.initDeviceFileByDeviceResetRecord(deviceResetRecord);
                        LOGGER.info("初始化当前sn的设备库存信息end: sn:", list.getSn()==null?list.getImei():list.getSn());
                    }
                }
                //根据id找到对应的维修详情
                MaintainProductDetail maintainProductDetail = maintainProductDetailMapper.selectById(list.getMainTainProductDetailId());
                LOGGER.info("切换之前的SN:", maintainProductDetail.getSn());
                if (!StringUtil.isEmpty(maintainProductDetail)) {
                    //通过老设备的SN找到对应的套餐信息
                    DeviceFile deviceFile = deviceFileMapper.selectBySn(maintainProductDetail.getSn() == null ? maintainProductDetail.getImei() : maintainProductDetail.getSn());
                    //获取地址信息
                    Address address = new Address();
                    address.setId(afterSaleOrder.getLogistics().getReceiveId());
                    Address addressInfo = addressMapper.selectOne(address);
                    Integer total = afterSaleOrder.getMaintainSnChangeList().size();//需求总数
                    OrderInfo orderInfo = new OrderInfo();
                    LOGGER.info("开始添加订单信息:{}", orderInfo);
                    orderInfo.setOrderCode(dispatchOrderNumber);
                    orderInfo.setTotal(total);
                    if(!StringUtil.isEmpty(deviceFile)){
                        orderInfo.setPackageOne(String.valueOf(deviceFile.getPackageId()));
                        orderInfo.setPackageTwo(String.valueOf(deviceFile.getPackageId()));
                    }
                    if(!StringUtil.isEmpty(deviceFile)){
                        orderInfo.setDeviceId(deviceFile.getDeviceCode());
                    }
                    DeviceCode deviceCode = deviceCodeMapper.selectByPrimaryKey(deviceFile.getDeviceCode());
                    if(!StringUtil.isEmpty(deviceCode)){
                        orderInfo.setDeviceName(deviceCode.getDeviceName());
                    }
                    //老的库存信息
                    DeviceInfo oldDeviceInfo = deviceInfoMapper.getDeviceInfoBySn(maintainProductDetail.getSn() == null ? maintainProductDetail.getImei() : maintainProductDetail.getSn());
                    if(!StringUtil.isEmpty(oldDeviceInfo)){
                        orderInfo.setAttribCode(oldDeviceInfo.getAttribCode());
                        orderInfo.setBatch(oldDeviceInfo.getBatch());
                    }
                    orderInfo.setStatus(OrderStatusEnum.STATUS_OV.getValue());
                    orderInfo.setOperatorMerchantNo(afterSaleOrdeType.getMerchantCode());
                    orderInfo.setWarehouseId(5);
                    orderInfo.setSendMerchantNo(afterSaleOrdeType.getMerchantCode());
                    DealerUserInfo dealerUserInfo = new DealerUserInfo();
                    dealerUserInfo.setMerchantCode(afterSaleOrdeType.getMerchantCode());
                    DealerUserInfo dealerUserName =dealerUserInfoMapper.selectOne(dealerUserInfo);
                    if(!StringUtil.isEmpty(dealerUserName)){
                        orderInfo.setSendMerchantName(dealerUserName.getMerchantCode()+"/"+dealerUserName.getMerchantName());
                    }
                    if(!StringUtil.isEmpty(addressInfo)){
                        orderInfo.setAddress(addressInfo.getCityName()+addressInfo.getAreaName()+addressInfo.getAddress());
                        orderInfo.setContacts(addressInfo.getName());
                        orderInfo.setMobile(addressInfo.getMobile());
                    }
                    orderInfo.setCreatedDate(new Date());
                    orderInfo.setUpdatedBy(afterSaleOrder.getUpdatedBy());
                    orderInfo.setUpdatedDate(new Date());
                    orderInfo.setCreatedBy(afterSaleOrder.getUpdatedBy());
                    orderInfo.setDeletedFlag("N");
                    orderInfoMapper.insertOrderInfo(orderInfo);
                    //填充订单详情信息
                    OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
                    LOGGER.info("开始添加订单详情信息:{}", orderInfoDetail);
                    orderInfoDetail.setOrderCode(dispatchOrderNumber);
                    if(list.getIccid() != null){
                        orderInfoDetail.setIccid(list.getIccid());
                    }else{
                        orderInfoDetail.setIccid("");
                    }
                    if(list.getImei() != null){
                        orderInfoDetail.setImei(list.getImei());
                        orderInfoDetail.setSn(list.getImei());
                    }else{
                        orderInfoDetail.setImei("");
                    }
                    if(list.getSn() != null){
                        orderInfoDetail.setImei(list.getSn());
                        orderInfoDetail.setSn(list.getSn());
                    }else{
                        orderInfoDetail.setSn("");
                    }

                    if(!StringUtil.isEmpty(oldDeviceInfo)){
                        orderInfoDetail.setAttribCode(oldDeviceInfo.getAttribCode());
                        orderInfoDetail.setBatch(oldDeviceInfo.getBatch());
                    }
                    orderInfoDetail.setWarehouseId(5);
                    orderInfoDetail.setWarehouseIdUp(5);
                    Integer newLogisticsId = Integer.parseInt(String.valueOf(afterSaleOrder.getLogistics().getId()));
                    orderInfoDetail.setLogisticsId(newLogisticsId);
                    orderInfoDetail.setCreatedBy(afterSaleOrdeType.getCreatedBy());
                    orderInfoDetail.setCreatedDate(new Date());
                    orderInfoDetail.setUpdatedBy(afterSaleOrder.getUpdatedBy());
                    orderInfoDetail.setUpdatedDate(new Date());
                    orderInfoDetail.setDeletedFlag("N");
                    orderInfoDetailMapper.insertSelective(orderInfoDetail);

                    //填充库存信息
                    LOGGER.info("开始填充库存信息:{}");
                    DeviceInfo device = new DeviceInfo();
                    if (list.getIccid()!=null) {
                        device.setIccid(list.getIccid());
                    }else{
                        device.setIccid("");
                    }
                    if(list.getImei() != null){
                        device.setImei(list.getImei());
                    }else{
                        device.setImei("");
                    }
                    if(list.getSn() != null){
                        device.setSn(list.getSn());
                    }else{
                        device.setSn("");
                    }
                    if(!StringUtil.isEmpty(oldDeviceInfo)){
                        device.setAttribCode(oldDeviceInfo.getAttribCode());
                        device.setBatch(oldDeviceInfo.getBatch());
                    }
                    device.setStatus(DeviceEnum.STATUS_IN.getValue());
                    device.setWareHouseId(5);
                    device.setWareHouseIdUp(5);
                    device.setOrderCode(dispatchOrderNumber);
                    device.setCreatedBy(afterSaleOrdeType.getCreatedBy());
                    device.setCreatedDate(new Date());
                    device.setUpdatedBy(afterSaleOrder.getUpdatedBy());
                    device.setDeletedFlag("N");
                    deviceInfoMapper.addDeviceInfoOnDuplicateKey(device);
                }*/
            }

        }
        return result;
    }


    /**
     * 修改售后订单物流信息
     *
     * @param logistics
     * @return
     */
    public Integer updateAfterSaleOrderLogistics(Logistics logistics) {
        LOGGER.info(" 修改的物流信息:{}", logistics);
        Integer result;
        logistics.setUpdatedDate(new Date());
        result = logisticsService.updateById(logistics);
        return result;
    }

    /**
     * 新增售后订单发货物流信息
     *
     * @param logistics
     * @return
     */
    public Integer AfterSaleOrderDispatchAddLogistics(Logistics logistics) {
        LOGGER.info(" 新增售后订单发货物流信息参数:{}", logistics);
        Integer result;
        result = logisticsService.add(logistics);
        return result;
    }

    /**
     * 修改售后订单物流信息
     *
     * @param logistics
     * @return
     */
    public Integer updateAfterSaleOrderLogisticsByServiceCodeAndType(Logistics logistics) {
        LOGGER.info(" 修改的物流信息:{}", logistics);
        Integer result;
        String logisticsCode = StringUtil.getOrderNo();
        logistics.setCode(logisticsCode);
        result = logisticsService.updateByServiceCodeAndType(logistics);
        return result;
    }

    /**
     * 新增售后订单物流信息
     *
     * @param logistics
     * @return
     */
    public Integer addAfterSaleOrderLogistics(Logistics logistics) {
        LOGGER.info("新增售后订单维修物流信息的入参:{}", logistics);
        Integer result;
        List<Address> addressesList = addressService.getAddressList(logistics.getReceiveAddress());
        if(!StringUtil.isEmpty(addressesList) && addressesList.size()<1){
            addressService.add(logistics.getReceiveAddress());
            logistics.setReceiveId(logistics.getReceiveAddress().getId());
        }else {
            logistics.setReceiveId(addressesList.get(0).getId());
        }
        String logisticsCode = StringUtil.getOrderNo();
        logistics.setCode(logisticsCode);
        result = logisticsService.add(logistics);
        return result;
    }

    /**
     * 添加售后订单签收信息
     *
     * @param afterSaleOrderDetailList
     * @return
     */
    public Integer insertAfterSaleOrderDetailList(List<AfterSaleOrderDetail>afterSaleOrderDetailList) {
        LOGGER.info(" 添加售后订单签收信息:{}", afterSaleOrderDetailList);
        Integer result;
        if(afterSaleOrderDetailList != null && afterSaleOrderDetailList.size()>0){
            for(int i=0; i<afterSaleOrderDetailList.size();i++){
                afterSaleOrderDetailList.get(i).setStatus(AfterSaleOrderDetailStatusEnum.AFTER_SALE_ORDER_SIGN_SN.getCode());
                afterSaleOrderDetailList.get(i).setCreatedBy("admin");
                afterSaleOrderDetailList.get(i).setCreatedDate(new Date());
                afterSaleOrderDetailList.get(i).setUpdatedBy("admin");
                afterSaleOrderDetailList.get(i).setUpdatedDate(new Date());
                afterSaleOrderDetailList.get(i).setDeletedFlag("N");
            }
        }
        result = afterSaleOrderDetailMapper.insertList(afterSaleOrderDetailList);
        return result;
    }


    /**
     * 查询售后订单详情为发货的SN
     *
     * @param afterSaleOrderDetail
     * @return
     */
    public List<AfterSaleOrderDetail>getAfterSalesSnList(AfterSaleOrderDetail afterSaleOrderDetail){
        LOGGER.info(" 查询售后订单详情为发货的SN参数:{}", afterSaleOrderDetail);
        List<AfterSaleOrderDetail> result;
        result = afterSaleOrderDetailMapper.select(afterSaleOrderDetail);
        return result;
    }


    /**
     * 查询售后订单实际发货的SN信息
     *
     * @param maintainProductDetail
     * @return
     */
    public List<MaintainSnChange>getMainTainSnChangeList(MaintainProductDetail maintainProductDetail){
        LOGGER.info(" 查询售后订单详情为发货的SN参数:{}", maintainProductDetail);
        List<MaintainSnChange> result = null;
        String afterSaleOrderNumber = maintainProductDetail.getAfterSaleOrderNumber();
        List<MaintainProductDetail> maintainProductDetailList = maintainProductDetailMapper.getMainTainProductDetailByAfterOrderNumber(afterSaleOrderNumber);
        List<Long>ids = new ArrayList<>();
        if(maintainProductDetailList != null && maintainProductDetailList.size()>0){
            Long id;
            for(MaintainProductDetail list : maintainProductDetailList){
                id = new Long(0);
                id = list.getId();
                ids.add(id);
            }
        }
        if(ids.size()>0){
            result = maintainSnChangeMapper.getMainTainSnChangeListById(ids);
        }
        return result;
    }

    /**
     * 查询订单详情信息根据IMEI或者SN
     * @param imei
     * @return
     */
    public DeviceInfo getDeviceInfoByImeiOrSn(String imei){

        DeviceInfo result = deviceInfoMapper.getDeviceInfoBySn(imei);
        if(StringUtil.isEmpty(result) || (!StringUtil.isEmpty(result) && result.getDeletedFlag().equals("Y")))
        {
            DeviceFile deviceFile = deviceFileMapper.selectBySn(imei);
            if(!StringUtil.isEmpty(deviceFile) && deviceFile.getDeletedFlag().equals("N"))
            {
                if(StringUtil.isEmpty(result))
                {
                    result = new DeviceInfo();
                }
                result.setSn(deviceFile.getSn());
                result.setBatch(deviceFile.getBatchNo());
                result.setOrderCode(deviceFile.getOrderCode());
                //防止不走工厂扫码出库 给一个默认仓库 反补信息
                result.setWareHouseId(5);
                result.setWareHouseIdUp(5);
            }
            return result;
        }

        DeviceFile deviceFile = deviceFileMapper.selectBySn(imei);
        if(!StringUtil.isEmpty(deviceFile) && deviceFile.getDeletedFlag().equals("N"))
        {
            return result;
        }
        return null;
    }

}
