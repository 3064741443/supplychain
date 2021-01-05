package cn.com.glsx.rpc.supplychain.rdn.kafka;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.rpc.supplychain.rdn.common.Constants;
import cn.com.glsx.rpc.supplychain.rdn.config.SystemProperty;
import cn.com.glsx.rpc.supplychain.rdn.converter.BsLogisticsRpcConvert;
import cn.com.glsx.rpc.supplychain.rdn.manager.TaskExportRecordManager;
import cn.com.glsx.rpc.supplychain.rdn.mapper.STKMerchantStockSnStatMapper;
import cn.com.glsx.rpc.supplychain.rdn.model.kafka.*;
import cn.com.glsx.rpc.supplychain.rdn.model.pojo.*;
import cn.com.glsx.rpc.supplychain.rdn.model.tmp.*;
import cn.com.glsx.rpc.supplychain.rdn.service.*;
import cn.com.glsx.rpc.supplychain.rdn.util.ExcelReadAndWriteUtil;
import cn.com.glsx.rpc.supplychain.rdn.util.ExcelXlsxStreamingViewSalesMerchantOrder;
import cn.com.glsx.rpc.supplychain.rdn.util.ExcelXlsxStreamingViewUtil;
import cn.com.glsx.rpc.supplychain.rdn.util.RdnUtils;
import cn.com.glsx.supplychain.enums.DealerUserInfoChannelEnum;
import cn.com.glsx.supplychain.enums.OrderStatusEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.enums.TaskReportExportStatuEnum;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.enums.TransferOrderStatusEnum;
import cn.com.glsx.supplychain.model.TaskRecordExport;
import com.alibaba.fastjson.JSONObject;
import org.oreframework.boot.autoconfigure.snowflake.algorithm.SnowflakeWorker;
import org.oreframework.jms.kafka.consumer.KafkaConsumer;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OperatorExportKafkaConsumer extends KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AttribInfoService attribInfoService;
    @Autowired
    private BsAddressService bsAddressSevice;
    @Autowired
    private BsLogisticsService bsLogisticsService;
    @Autowired
    private DeviceTypeService deviceTypeServie;
    @Autowired
    private EcMerchantOrderService ecMerchantOrderService;
    @Autowired
    private BsMerchantOrderService bsMerchantOrderService;
    @Autowired
    private TransferOrderService transferOrderService;
    @Autowired
    private GhMerchantOrderService jxcmtGhMerchantOrderService;
    @Autowired
    private STKMerchantStockService merchantStockService;
    @Autowired
    private STKWarningService warningService;
    @Autowired
    private STKMerchantStockSnService merchantStockSnService;
    @Autowired
    private OrderDispatchService orderDispatchService;
    @Autowired
    private DeviceFileService deviceFileService;
    @Autowired
    private STKMerchantStockSnStatMapper stockSnStatMapper;
    @Autowired
    private TaskExportRecordManager exportRecordMgr;
    @Autowired
    private SnowflakeWorker snowflakeWorker;
    @Autowired
    private SystemProperty systemProperty;
    @Autowired
    private ExcelXlsxStreamingViewSalesMerchantOrder excelXlsxStream;
    @Autowired
    private ExcelXlsxStreamingViewUtil excelXlsxUtilStream;


    public OperatorExportKafkaConsumer() {
        super("supplychain_send_export_cmd_message", false);
    }

    @Override
    public void processMessage(Object message) {
        logger.info("kafka接受的消息对象：{}", new String(getMessage()));
        try {
            String kafkaDfrom = new String(getMessage());
            OperatorExportKafkaMessage kafkaMessage = (OperatorExportKafkaMessage) JacksonUtils.jsonToBean(kafkaDfrom, OperatorExportKafkaMessage.class);
            if (StringUtils.isEmpty(kafkaMessage.getTaskCfgId())) {
                logger.info("messageType 为空");
                return;
            }
            if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_MERCHANT_ORDER)) {
                handleExportMerchantOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER)) {
                handleExportMerchantOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER_PURCHASE)) {
                handleExportPurchaseOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER_BUSSCHECK)) {
                handleExportBsMerchantOrderBss(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER_BUSPDISP)) {
                handleExportBsMerchantOrderBsp(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_BILL_GEN)) {
                handleGenSignDispatchOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER_BSTRNSFER)) {
                handleExportBsTransferOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER_JXCTRNSFER)) {
                handleExportJXCTransferOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_STK_MERCHANT_STOCK)) {
                handleExportMerchantStock(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_STK_WARNING_WARESALE)) {
                handleExportMerchantWarningWaresale(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_STK_WARNING_DEVICESN)) {
                handleExportMerchantWarningDeviceSn(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_STK_STOCK_SN_STAT)) {
                handleExportMerchantStockSnStat(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_STK_MERCHANT_STOCK_SN)) {
                handleExportMerchantStockSn(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_JXC_ORDER_INVOICE)) {
                handleExportDispatchOrder(kafkaMessage);
            } else if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_DEVICE_FILE)) {
                handleExportDeviceFile(kafkaMessage);
            }
            logger.info("kafka处理消息完毕");
        } catch (RpcServiceException e) {
            logger.error(e.getMessage(), e);
            logger.info("kafka处理消息失败");
        }
    }

    private void handleGenSignDispatchOrder(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getGenBills()) {
                return;
            }
            GenBills genBill = kafkaMessage.getGenBills();
            GoodsReceipt goodsReceipt = null;
            List<BillDetail> listBillDetail = null;
            Map<String, Object> goodsReceiptMap = null;
            List<ShipmentOrder> shipmentOrderList = null;
            Address address = null;
            for (Bill bill : genBill.getListBills()) {

                String operatorFlag = RdnUtils.generatorTimeRandCode(snowflakeWorker);
                String fileName = systemProperty.getUploadPath() + operatorFlag + Constants.EXPORT_FORMAT_GOODS_RECEIPT;
                String fileUrl = systemProperty.getUploadUrl() + operatorFlag + Constants.EXPORT_FORMAT_GOODS_RECEIPT;
                File file = new File(fileName);
                FileOutputStream fileOutputStream = null;
                fileOutputStream = new FileOutputStream(file);
                goodsReceiptMap = new HashMap<>();
                listBillDetail = bill.getListBillDetail();
                shipmentOrderList = new ArrayList<>();
                Integer dataNo = 1;
                for (BillDetail billDetail : listBillDetail) {
                    ShipmentOrder shipmentOrder = convertToShipmentOrder(billDetail, genBill.getBillType());
                    shipmentOrder.setDataNo(dataNo++);
                    shipmentOrderList.add(shipmentOrder);
                }
                goodsReceipt = new GoodsReceipt();
                goodsReceipt.setDocumentNo(bill.getBillSignNumber());
                goodsReceipt.setConsignee(bill.getSendMerchantName());
                goodsReceipt.setShipmentOrderList(shipmentOrderList);
                Integer totalCount = 0;
                for (ShipmentOrder shipmentOrderDto : shipmentOrderList) {
                    totalCount += shipmentOrderDto.getSendCount();
                }
                List<Address> addressList = new ArrayList<>();
                address = new Address();
                address.setAddress(bill.getAddress());
                address.setName(bill.getContacts());
                address.setMobile(bill.getMobile());
                goodsReceipt.setAddress(address);
                addressList.add(address);
                goodsReceipt.setTotalCount(totalCount);
                goodsReceiptMap.put("documentNo", goodsReceipt.getDocumentNo());
                goodsReceiptMap.put("consignee", goodsReceipt.getConsignee());
                goodsReceiptMap.put("totalCount", totalCount);
                goodsReceiptMap.put("addressList", addressList);
                goodsReceiptMap.put("shipmentOrderList", goodsReceipt.getShipmentOrderList());
                String goodsReceiptTemplateFileName = ExcelReadAndWriteUtil.getPath() + "templates" + File.separator + "templateGoodsReceipt.xlsx";
                ExcelReadAndWriteUtil.compositeFillGoodsReceipt(fileOutputStream, goodsReceiptMap, goodsReceiptTemplateFileName, null, null);

                for (BillDetail billDetail : bill.getListBillDetail()) {
                    JXCMTBsMerchantOrderSignSuplyDTO dtoRecord = new JXCMTBsMerchantOrderSignSuplyDTO();
                    dtoRecord.setBillSignNumber(bill.getBillSignNumber());
                    dtoRecord.setDispatchOrderCode(billDetail.getDispatchOrderCode());
                    dtoRecord.setLogisticsNo(billDetail.getLogisticsNo());
                    dtoRecord.setDeletedFlag("N");
                    dtoRecord.setSignUrl(fileUrl);
                    dtoRecord.setBillType(genBill.getBillType());
                    dtoRecord.setCreatedBy(genBill.getUserName());
                    dtoRecord.setUpdatedBy(genBill.getUserName());
                    dtoRecord.setCreatedDate(RdnUtils.getNowDate());
                    dtoRecord.setUpdatedDate(RdnUtils.getNowDate());
                    exportRecordMgr.writeOrderSignRecord(dtoRecord);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private ShipmentOrder convertToShipmentOrder(BillDetail billDetail, String billType) {
        ShipmentOrder shipmentOrder = new ShipmentOrder();
        shipmentOrder.setOrderNumber(org.apache.commons.lang.StringUtils.isEmpty(billDetail.getMerchantOrder()) ? "" : billDetail.getMerchantOrder());
        shipmentOrder.setProductCode(org.apache.commons.lang.StringUtils.isEmpty(billDetail.getMaterialCode()) ? "" : billDetail.getMaterialCode());
        shipmentOrder.setProductName(org.apache.commons.lang.StringUtils.isEmpty(billDetail.getMaterialName()) ? "" : billDetail.getMaterialName());
        if (billType.equals("READ")) {
            shipmentOrder.setSendCount(billDetail.getLogisticsShipmentsQuantity() == null ? 0 : billDetail.getLogisticsShipmentsQuantity());
            shipmentOrder.setSendDate("");
            shipmentOrder.setLogisticsCompany("");
        } else {
            shipmentOrder.setSendCount(billDetail.getLogisticsShipmentsQuantity() == null ? 0 : billDetail.getLogisticsShipmentsQuantity());
            if (billDetail.getLogisticsSendTime().length() >= 12) {
                shipmentOrder.setSendDate(org.apache.commons.lang.StringUtils.isEmpty(billDetail.getLogisticsSendTime()) ? "" : RdnUtils.getNowDateStringYMD(RdnUtils.getDateFromStrYMDHMS(billDetail.getLogisticsSendTime())));
            } else {
                shipmentOrder.setSendDate(billDetail.getLogisticsSendTime());
            }
            if (!StringUtils.isEmpty(billDetail.getLogisticsNo()) && !StringUtils.isEmpty(billDetail.getLogisticsCpy())) {
                shipmentOrder.setLogisticsCompany(billDetail.getLogisticsNo() + "\n" + billDetail.getLogisticsCpy());
            }
        }
        return shipmentOrder;
    }

    private void handleExportBsMerchantOrderBsp(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getMerchantOrderExport()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getMerchantOrderExport()));
            setTaskRecordExport(taskRecordExport);
            MerchantOrder merchantOrder = generatorBsMerchantOrder(kafkaMessage.getMerchantOrderExport());
            MerchantOrder conditon = new MerchantOrder();
            conditon.setOrderNumber(merchantOrder.getOrderNumber());
            List<String> listOrderNums = bsMerchantOrderService.listBsMerchantOrderByDispatchOrderLike(merchantOrder.getDispatchOrderNumber());
            if (null != listOrderNums && !listOrderNums.isEmpty()) {
                conditon.setListOrderNums(listOrderNums);
            }
            conditon.setMerchantCode(merchantOrder.getMerchantCode());
            conditon.setMerchantName(merchantOrder.getMerchantName());
            conditon.setMaterialCode(merchantOrder.getMaterialCode());
            conditon.setMaterialName(merchantOrder.getMaterialName());
            if (!StringUtils.isEmpty(merchantOrder.getStatus())) {
                conditon.setStatus(Byte.valueOf(merchantOrder.getStatus()));
            }
            conditon.setChannel(merchantOrder.getChannel());
            conditon.setOrderTimeStart(merchantOrder.getOrderTimeStart());
            conditon.setOrderTimeEnd(merchantOrder.getOrderTimeEnd());
            List<BsMerchantOrderBspVo> listMerchantOrderBspVo = bsMerchantOrderService.exportBsMerchantOrderBSP(conditon);
            logger.info("分配订单列表的总数为：{}", listMerchantOrderBspVo == null ? 0 : listMerchantOrderBspVo.size());
            List<BsMerchantOrderBspExcelVo> listExcelVos = new ArrayList<>();
            if (null != listMerchantOrderBspVo && !listMerchantOrderBspVo.isEmpty()) {
                List<String> listBserMerchantOrderCode = listMerchantOrderBspVo.stream().map(BsMerchantOrderBspVo::getMerchantOrderCode).collect(Collectors.toList());
                Map<String, BsAddressVo> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
                BsAddressVo bsAddress = null;
                String strAddress = "";
                List<Integer> listChannelIds = listMerchantOrderBspVo.stream().map(BsMerchantOrderBspVo::getChannelId).collect(Collectors.toList());
                Map<Integer, String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);
                List<String> orderNumberList = listMerchantOrderBspVo.stream().map(BsMerchantOrderBspVo::getMerchantOrderCode).collect(Collectors.toList());
                Map<String, List<GhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(orderNumberList);
                List<GhMerchantOrderConfig> listGhMerchantOrderConfig = null;
                AttribInfo attribInfo = null;
                for (BsMerchantOrderBspVo dto : listMerchantOrderBspVo) {
                    bsAddress = mapBsAddress.get(dto.getMerchantOrderCode());
                    if (null != bsAddress) {
                        strAddress = "";
                        if (!StringUtils.isEmpty(bsAddress.getProvinceName())) {
                            strAddress += bsAddress.getProvinceName();
                        }
                        if (!StringUtils.isEmpty(bsAddress.getCityName())) {
                            strAddress += bsAddress.getCityName();
                        }
                        if (!StringUtils.isEmpty(bsAddress.getAreaName())) {
                            strAddress += bsAddress.getAreaName();
                        }
                        strAddress += bsAddress.getAddress();
                        dto.setAddress(strAddress);
                        dto.setContacts(bsAddress.getName());
                        dto.setMobile(bsAddress.getMobile());
                    }
                    if (!StringUtils.isEmpty(dto.getChannelId())) {
                        dto.setChannelName(mapMerchantChannels.get(attribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
                    }
                    if (!StringUtils.isEmpty(dto.getDispatchOrderStatus())) {
                        dto.setDispatchOrderStatus(convertMerchantOrderStatus(Byte.valueOf(dto.getDispatchOrderStatus())));
                    } else {
                        dto.setDispatchOrderStatus("");
                    }
                    //生成固定配置和选项配置
                    listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrderCode());
                    if (!StringUtils.isEmpty(listGhMerchantOrderConfig)) {
                        String optionConfigDesc = "";
                        String fastenConfigDesc = "";
                        for (GhMerchantOrderConfig oConfig : listGhMerchantOrderConfig) {
                            attribInfo = attribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
                            if (null == attribInfo) {
                                continue;
                            }
                            if (oConfig.getStrOption().equals("O")) {
                                optionConfigDesc += attribInfo.getComment();
                                optionConfigDesc += ":";
                                optionConfigDesc += attribInfo.getName();
                                optionConfigDesc += "/";
                            } else if (oConfig.getStrOption().equals("F")) {
                                fastenConfigDesc += attribInfo.getComment();
                                fastenConfigDesc += ":";
                                fastenConfigDesc += attribInfo.getName();
                                fastenConfigDesc += "/";
                            }
                        }
                        dto.setFastenConfigDesc(fastenConfigDesc);
                        dto.setOptionConfigDesc(optionConfigDesc);
                    }
                    listExcelVos.add(convertMerchantOrderBssDto2Vo(dto));
                }
            }
            logger.info("转换之前的分配订单总数：{}", listExcelVos == null ? 0 : listExcelVos.size());
            List<Object> merchantOrderList = new ArrayList<Object>();
            merchantOrderList.addAll(listExcelVos);
            logger.info("转换之后的分配订单总数：{}", merchantOrderList == null ? 0 : merchantOrderList.size());
            Map<String, Object> map = new HashMap<>();
            map.put("objs", merchantOrderList);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_BS_MERCHANT_ORDER_BSP);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSP);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_BS_MERCHANT_ORDER_BSP);
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSP);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSP);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            excelXlsxUtilStream.buildExcelDocument(map, null, fileOutputStream);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private String convertMerchantOrderStatus(Byte merchantOrderStatus) {
        String result = "";
        if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName();
        } else if (merchantOrderStatus.equals(MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getCode())) {
            result = MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getName();
        }
        return result;
    }

    private BsMerchantOrderBspExcelVo convertMerchantOrderBssDto2Vo(BsMerchantOrderBspVo dto) {
        BsMerchantOrderBspExcelVo vo = new BsMerchantOrderBspExcelVo();
        vo.setMerchantName(StringUtils.isEmpty(dto.getMerchantName()) ? "" : dto.getMerchantName());
        vo.setChannelName(StringUtils.isEmpty(dto.getChannelName()) ? "" : dto.getChannelName());
        vo.setMaterialCode(StringUtils.isEmpty(dto.getMaterialCode()) ? "" : dto.getMaterialCode());
        vo.setMaterialName(StringUtils.isEmpty(dto.getMaterialName()) ? "" : dto.getMaterialName());
        vo.setMdeviceTypeName(StringUtils.isEmpty(dto.getMdeviceTypeName()) ? "" : dto.getMdeviceTypeName());
        vo.setDispatchOrderCode(StringUtils.isEmpty(dto.getDispatchOrderCode()) ? "" : dto.getDispatchOrderCode());
        vo.setDispatchOrderStatus(StringUtils.isEmpty(dto.getDispatchOrderStatus()) ? "" : dto.getDispatchOrderStatus());
        vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark()) ? "" : dto.getCheckRemark());
        vo.setSubjectName(StringUtils.isEmpty(dto.getSubjectName()) ? "" : dto.getSubjectName());
        vo.setBsCheckQuantity((dto.getBsCheckQuantity() == null) ? 0 : dto.getBsCheckQuantity());
        vo.setBsSendQuantity((dto.getBsSendQuantity() == null) ? 0 : dto.getBsSendQuantity());
        vo.setBsOweQuantity(vo.getBsCheckQuantity() - vo.getBsSendQuantity());
        String motorcycleDesc = "";
        if (!StringUtils.isEmpty(dto.getBsParentBrandName())
                || !StringUtils.isEmpty(dto.getBsSubBrandName())
                || !StringUtils.isEmpty(dto.getBsAudiName())
                || !StringUtils.isEmpty(dto.getBsMotorcycle())) {
            motorcycleDesc = "父品牌:" + (StringUtils.isEmpty(dto.getBsParentBrandName()) ? "" : dto.getBsParentBrandName()) + "\n";
            motorcycleDesc = motorcycleDesc + "子品牌:" + (StringUtils.isEmpty(dto.getBsSubBrandName()) ? "" : dto.getBsSubBrandName()) + "\n";
            motorcycleDesc = motorcycleDesc + "车系:" + (StringUtils.isEmpty(dto.getBsAudiName()) ? "" : dto.getBsAudiName()) + "\n";
            motorcycleDesc = motorcycleDesc + "车型:" + (StringUtils.isEmpty(dto.getBsMotorcycle()) ? "" : dto.getBsMotorcycle());
            vo.setMotorcycleDesc(motorcycleDesc);
        } else {
            vo.setMotorcycleDesc("");
        }
        vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark()) ? "" : dto.getCheckRemark());
        vo.setWarehouseName(StringUtils.isEmpty(dto.getWarehouseName()) ? "" : dto.getWarehouseName());
        vo.setAddress(StringUtils.isEmpty(dto.getAddress()) ? "" : dto.getAddress());
        vo.setContacts(StringUtils.isEmpty(dto.getContacts()) ? "" : dto.getContacts());
        vo.setMobile(StringUtils.isEmpty(dto.getMobile()) ? "" : dto.getMobile());
        vo.setOrderNumber(StringUtils.isEmpty(dto.getOrderNumber()) ? "" : dto.getOrderNumber());
        vo.setCompany(StringUtils.isEmpty(dto.getCompany()) ? "" : dto.getCompany());
        vo.setShipmentsQuantity(dto.getShipmentsQuantity() == null ? 0 : dto.getShipmentsQuantity());
        vo.setCheckBy(dto.getCheckBy() == null ? "" : dto.getCheckBy());
        vo.setCheckTime(RdnUtils.getStringFromDate(dto.getCheckTime()));
        vo.setMerchantOrderCode(StringUtils.isEmpty(dto.getMerchantOrderCode()) ? "" : dto.getMerchantOrderCode());
        vo.setFastenConfigDesc(StringUtils.isEmpty(dto.getFastenConfigDesc()) ? "" : dto.getFastenConfigDesc());
        vo.setOptionConfigDesc(StringUtils.isEmpty(dto.getOptionConfigDesc()) ? "" : dto.getOptionConfigDesc());
        return vo;
    }

    public void handleExportBsMerchantOrderBss(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getMerchantOrderExport()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getMerchantOrderExport()));
            setTaskRecordExport(taskRecordExport);
            MerchantOrder merchantOrder = generatorBsMerchantOrder(kafkaMessage.getMerchantOrderExport());
            List<BsMerchantOrderBssVo> listMerchantOrderBssVo = bsMerchantOrderService.exportBsMerchantOrderBSS(merchantOrder);
            List<BsMerchantOrderBssExcelVo> listExcelVos = new ArrayList<>();
            if (null != listMerchantOrderBssVo && !listMerchantOrderBssVo.isEmpty()) {
                List<String> listDispatchOrders = new ArrayList<>();
                for (BsMerchantOrderBssVo dto : listMerchantOrderBssVo) {
                    if (!StringUtils.isEmpty(dto.getDispatchOrderCode())) {
                        listDispatchOrders.add(dto.getDispatchOrderCode());
                    }
                }

                List<Integer> listChannelIds = listMerchantOrderBssVo.stream().map(BsMerchantOrderBssVo::getChannelId).collect(Collectors.toList());
                Map<String, List<Logistics>> mapLogistics = this.listBsLogisticsByDispatchOrderCodes(listDispatchOrders);
                Map<Integer, String> mapMerchantChannels = listBsMerchantChannelName(listChannelIds);

                List<String> listMerchantOrder = listMerchantOrderBssVo.stream().map(BsMerchantOrderBssVo::getMerchantOrder).collect(Collectors.toList());
                Map<String, List<GhMerchantOrderConfig>> mapGhMerchantOrderConfigs = jxcmtGhMerchantOrderService.mapListGhMerchantOrderConfigByMerchantOrders(listMerchantOrder);
                List<GhMerchantOrderConfig> listGhMerchantOrderConfig = null;
                AttribInfo attribInfo = null;
                Map<String, BsAddressVo> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listMerchantOrder);
                BsAddressVo bsAddress = null;
                String strAddress = "";
                int bsTotal = 0;
                int bsProp = 1;
                for (BsMerchantOrderBssVo dto : listMerchantOrderBssVo) {
                    if (dto.getChannelId() != null) {
                        dto.setChannelName(mapMerchantChannels.get(attribInfoService.getMerchantChannelFromDbMerchantChannel(dto.getChannelId().byteValue())));
                    }
                    if (!StringUtils.isEmpty(dto.getDispatchOrderCode())) {
                        dto.setListLogistics(BsLogisticsRpcConvert.convertListBean(mapLogistics.get(dto.getDispatchOrderCode())));
                    }
                    //更新地址
                    bsAddress = mapBsAddress.get(dto.getMerchantOrder());
                    if (null != bsAddress) {
                        strAddress = "";
                        if (!StringUtils.isEmpty(bsAddress.getProvinceName())) {
                            strAddress += bsAddress.getProvinceName();
                        }
                        if (!StringUtils.isEmpty(bsAddress.getCityName())) {
                            strAddress += bsAddress.getCityName();
                        }
                        if (!StringUtils.isEmpty(bsAddress.getAreaName())) {
                            strAddress += bsAddress.getAreaName();
                        }
                        strAddress += bsAddress.getAddress();
                        dto.setAddress(strAddress);
                        dto.setContacts(bsAddress.getName());
                        dto.setMobile(bsAddress.getMobile());
                    }
                    //固定配置和选项配置
                    listGhMerchantOrderConfig = mapGhMerchantOrderConfigs.get(dto.getMerchantOrder());
                    if (null == listGhMerchantOrderConfig || listGhMerchantOrderConfig.isEmpty()) {
                        continue;
                    }
                    String optionConfigDesc = "";
                    String fastenConfigDesc = "";
                    for (GhMerchantOrderConfig oConfig : listGhMerchantOrderConfig) {
                        attribInfo = attribInfoService.getAttribInfoById(oConfig.getAttribInfoId());
                        if (null == attribInfo) {
                            continue;
                        }
                        if (oConfig.getStrOption().equals("O")) {
                            optionConfigDesc += attribInfo.getComment();
                            optionConfigDesc += ":";
                            optionConfigDesc += attribInfo.getName();
                            optionConfigDesc += "/";
                        } else if (oConfig.getStrOption().equals("F")) {
                            fastenConfigDesc += attribInfo.getComment();
                            fastenConfigDesc += ":";
                            fastenConfigDesc += attribInfo.getName();
                            fastenConfigDesc += "/";
                        }
                    }
                    dto.setFastenConfigDesc(fastenConfigDesc);
                    dto.setSetOptionConfigDesc(optionConfigDesc);
                }

                for (BsMerchantOrderBssVo vo : listMerchantOrderBssVo) {
                    listExcelVos.add(convertMerchantOrderBssDto2Vo(vo));
                }
            }

            List<Object> merchantOrderList = new ArrayList<Object>();
            merchantOrderList.addAll(listExcelVos);
            Map<String, Object> map = new HashMap<>();
            map.put("objs", merchantOrderList);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_BS_MERCHANT_ORDER_BSS);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_BS_MERCHANT_ORDER_BSS);
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            excelXlsxUtilStream.buildExcelDocument(map, null, fileOutputStream);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private String convertStatus(Integer status) {
        if (org.springframework.util.StringUtils.isEmpty(status)) {
            return "";
        }
        if (status == 1) {
            return MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
        } else if (status == 0) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
        } else if (status == 2) {
            return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        } else if (status == 3) {
            return MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
        } else if (status == 5) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
        } else if (status == 7) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
        } else if (status == 8) {
            return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        } else if (status == 9) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getName();
        }
        return "";
    }

    private BsMerchantOrderBssExcelVo convertMerchantOrderBssDto2Vo(BsMerchantOrderBssVo dto) {
        BsMerchantOrderBssExcelVo vo = new BsMerchantOrderBssExcelVo();
        vo.setMoOrderCode(StringUtils.isEmpty(dto.getMoOrderCode()) ? "" : dto.getMoOrderCode());
        vo.setMerchantOrder(StringUtils.isEmpty(dto.getMerchantOrder()) ? "" : dto.getMerchantOrder());
        vo.setMerchantName(StringUtils.isEmpty(dto.getMerchantName()) ? "" : dto.getMerchantName());
        vo.setChannelName(StringUtils.isEmpty(dto.getChannelName()) ? "" : dto.getChannelName());
        vo.setProductName(StringUtils.isEmpty(dto.getProductName()) ? "" : dto.getProductName());
        vo.setOrderMaterialCode(StringUtils.isEmpty(dto.getOrderMaterialCode()) ? "" : dto.getOrderMaterialCode());
        vo.setOrderMaterialName(StringUtils.isEmpty(dto.getOrderMaterialName()) ? "" : dto.getOrderMaterialName());
        vo.setMaterialCode(StringUtils.isEmpty(dto.getMaterialCode()) ? "" : dto.getMaterialCode());
        vo.setMaterialName(StringUtils.isEmpty(dto.getMaterialName()) ? "" : dto.getMaterialName());
        vo.setMdeviceTypeName(StringUtils.isEmpty(dto.getMdeviceTypeName()) ? "" : dto.getMdeviceTypeName());
        vo.setPrice((dto.getPrice() == null) ? 0.0 : dto.getPrice());
        vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark()) ? "" : dto.getCheckRemark());
        vo.setSubjectName(StringUtils.isEmpty(dto.getSubjectName()) ? "" : dto.getSubjectName());
        vo.setInsure(StringUtils.isEmpty(dto.getInsure()) ? "" : (dto.getInsure().equals("Y") ? "是" : "否"));
        if (org.springframework.util.StringUtils.isEmpty(dto.getBsTotal())) {
            vo.setBsTotal(0);
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(dto.getPropQuantity())) {
                vo.setBsTotal(dto.getBsTotal() * dto.getPropQuantity());
            }
        }
        vo.setBsCheckQuantity((dto.getBsCheckQuantity() == null) ? 0 : dto.getBsCheckQuantity());
        vo.setBsSendQuantity((dto.getBsSendQuantity() == null) ? 0 : dto.getBsSendQuantity());
        vo.setBsOweQuantity(vo.getBsCheckQuantity() - vo.getBsSendQuantity());
        String motorcycleDesc = "";
        if (!StringUtils.isEmpty(dto.getBsParentBrandName())
                || !StringUtils.isEmpty(dto.getBsSubBrandName())
                || !StringUtils.isEmpty(dto.getBsAudiName())
                || !StringUtils.isEmpty(dto.getBsMotorcycle())) {
            motorcycleDesc = "父品牌:" + (StringUtils.isEmpty(dto.getBsParentBrandName()) ? "" : dto.getBsParentBrandName()) + "\n";
            motorcycleDesc = motorcycleDesc + "子品牌:" + (StringUtils.isEmpty(dto.getBsSubBrandName()) ? "" : dto.getBsSubBrandName()) + "\n";
            motorcycleDesc = motorcycleDesc + "车系:" + (StringUtils.isEmpty(dto.getBsAudiName()) ? "" : dto.getBsAudiName()) + "\n";
            motorcycleDesc = motorcycleDesc + "车型:" + (StringUtils.isEmpty(dto.getBsMotorcycle()) ? "" : dto.getBsMotorcycle());
            vo.setMotorcycleDesc(motorcycleDesc);
        } else {
            vo.setMotorcycleDesc("");
        }
        vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark()) ? "" : dto.getCheckRemark());
        vo.setWarehouseName(StringUtils.isEmpty(dto.getWarehouseName()) ? "" : dto.getWarehouseName());
        vo.setAddress(StringUtils.isEmpty(dto.getAddress()) ? "" : dto.getAddress());
        vo.setContacts(StringUtils.isEmpty(dto.getContacts()) ? "" : dto.getContacts());
        vo.setMobile(StringUtils.isEmpty(dto.getMobile()) ? "" : dto.getMobile());
        if (!org.springframework.util.StringUtils.isEmpty(dto.getStatus())) {
            vo.setStatusStr(convertStatus(dto.getStatus()));
        }

        vo.setFastenConfigDesc(StringUtils.isEmpty(dto.getFastenConfigDesc()) ? "" : dto.getFastenConfigDesc());
        vo.setSetOptionConfigDesc(StringUtils.isEmpty(dto.getSetOptionConfigDesc()) ? "" : dto.getSetOptionConfigDesc());
        vo.setCheckBy(StringUtils.isEmpty(dto.getCheckBy()) ? "" : dto.getCheckBy());
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        vo.setCheckTimeStr(org.springframework.util.StringUtils.isEmpty(dto.getCheckTime()) ? "" : sp.format(dto.getCheckTime()));
        vo.setOrderTimeStr(org.springframework.util.StringUtils.isEmpty(dto.getOrderTime()) ? "" : sp.format(dto.getOrderTime()));
        String sendTime = "";
        String logisticsDesc = "";
        if (null != dto.getListLogistics()) {
            int flag = 0;
            for (BsLogisticsVo logisticsDto : dto.getListLogistics()) {
                if (flag != 0) {
                    logisticsDesc += "\n";
                }
                logisticsDesc = "物流公司:" + (StringUtils.isEmpty(logisticsDto.getCompany()) ? "" : logisticsDto.getCompany()) + " 物流单号:" + (StringUtils.isEmpty(logisticsDto.getOrderNumber()) ? "" : logisticsDto.getOrderNumber()) + " 数量:" + (logisticsDto.getShipmentsQuantity() == null ? 0 : logisticsDto.getShipmentsQuantity());
                sendTime = logisticsDto.getSendTime();
            }
        }
        vo.setLogisticsDesc(logisticsDesc);
        vo.setSendTime(sendTime);
        return vo;
    }

    private Map<Integer, String> listBsMerchantChannelName(List<Integer> listChannelIds) {
        List<Integer> listIds = new ArrayList<>();
        for (Integer item : listChannelIds) {
            if (null == item) {
                continue;
            }
            listIds.add(attribInfoService.getMerchantChannelFromDbMerchantChannel(item.byteValue()));
        }
        return attribInfoService.listAttribNameByIds(listIds);
    }

    private Map<String, List<Logistics>> listBsLogisticsByDispatchOrderCodes(List<String> listDispatchOrderCodes) {
        Map<String, List<Logistics>> mapBsLogistics = new HashMap<>();
        List<Logistics> listLogistics = null;
        Logistics bsLogistics = null;
        List<Logistics> listLogisticsDirect = bsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCodes, (byte) 5);
        List<Logistics> listLogisticsDispatch = bsLogisticsService.listBsLogisticsByDispatchOrderCodes(listDispatchOrderCodes, (byte) 4);
        if (null != listLogisticsDirect) {
            for (Logistics logistics : listLogisticsDirect) {
                listLogistics = mapBsLogistics.get(logistics.getServiceCode());
                if (null == listLogistics) {
                    listLogistics = new ArrayList<>();
                    mapBsLogistics.put(logistics.getServiceCode(), listLogistics);
                }
                listLogistics.add(logistics);
            }
        }
        if (null != listLogisticsDispatch) {
            for (Logistics logistics : listLogisticsDispatch) {
                listLogistics = mapBsLogistics.get(logistics.getServiceCode());
                if (null == listLogistics) {
                    listLogistics = new ArrayList<>();
                    mapBsLogistics.put(logistics.getServiceCode(), listLogistics);
                }
                listLogistics.add(logistics);
            }
        }
        return mapBsLogistics;
    }

    private void handleExportPurchaseOrder(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getMerchantOrderExport()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getMerchantOrderExport()));
            setTaskRecordExport(taskRecordExport);
            MerchantOrder merchantOrder = generatorBsMerchantOrder(kafkaMessage.getMerchantOrderExport());
            List<BsMerchantOrderVo> listMerchantOrderVo = bsMerchantOrderService.exportBsMerchantOrderJXC(merchantOrder);
            List<BsMerchantOrderJXCExcelVo> listExcelVos = new ArrayList<>();
            if (null != listMerchantOrderVo && !listMerchantOrderVo.isEmpty()) {
                List<String> listBserMerchantOrderCode = listMerchantOrderVo.stream().map(BsMerchantOrderVo::getMerchantOrder).collect(Collectors.toList());
                Map<String, BsAddressVo> mapBsAddress = listBsAddressByBsMerchantOrderCodes(listBserMerchantOrderCode);
                for (BsMerchantOrderVo vo : listMerchantOrderVo) {
                    listExcelVos.add(convertToJxcExcelVo(vo, mapBsAddress.get(vo.getMerchantOrder())));
                }
            }
            List<Object> merchantOrderList = new ArrayList<Object>();
            merchantOrderList.addAll(listExcelVos);
            Map<String, Object> map = new HashMap<>();
            map.put("objs", merchantOrderList);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_MERCHANT_ORDER_JXC);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_MERCHANT_ORDER_JXC);
            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_MERCHANT_ORDER_JXC);
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_MERCHANT_ORDER_JXC);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_MERCHANT_ORDER_JXC);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            excelXlsxUtilStream.buildExcelDocument(map, null, fileOutputStream);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private void handleExportMerchantOrder(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (StringUtils.isEmpty(kafkaMessage.getTaskCfgId())) {
                return;
            }
            if (StringUtils.isEmpty(kafkaMessage.getMerchantOrderExport())) {
                return;
            }

            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);

            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getMerchantOrderExport()));
            setTaskRecordExport(taskRecordExport);

            EcMerchantOrder ecMerhantOrder = generatorEcMerchantOrder(kafkaMessage.getMerchantOrderExport());
            List<EcMerchantOrder> listEcMerchantOrder = ecMerchantOrderService.exportEcMerchantOrderExit(ecMerhantOrder);
            List<EcMerchantOrderVo> listVo = convertEcMerchantOrderList2Vo(listEcMerchantOrder);
            List<Object> merchantOrderList = new ArrayList<Object>();
            merchantOrderList.addAll(listVo);
            Map<String, Object> map = new HashMap<>();
            map.put("objs", merchantOrderList);
            if (kafkaMessage.getTaskCfgId().equals(Constants.TASK_CFG_ID_MERCHANT_ORDER)) {
                map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_MERCHANT);
            } else {
                map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_MERCHANT_JXC);
            }
            map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_MERCHANT);
            map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_MERCHANT);
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_MERCHANT);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_MERCHANT);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            excelXlsxStream.buildExcelDocument(map, null, fileOutputStream);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private String getUploadPath(String operatorFlag, String fileName) {
        return systemProperty.getUploadPath() + operatorFlag + "_" + fileName;
    }

    private String getUploadUrl(String operatorFlag, String fileName) {
        return systemProperty.getUploadUrl() + operatorFlag + "_" + fileName;
    }

    private void setTaskRecordExport(TaskRecordExport record) {
        exportRecordMgr.setTaskExportRecord(record);
    }

    private TaskRecordExport generatorTaskRecordExport(Integer taskConfigId, String operatorFlag, String exportStatu,
                                                       String crtUser, String updUser, Date crtDate, Date updDate) {
        TaskRecordExport export = new TaskRecordExport();
        export.setOperatorFlag(operatorFlag);
        export.setCreatedBy(crtUser);
        export.setUpdatedBy(updUser);
        export.setCreatedDate(crtDate);
        export.setUpdatedDate(updDate);
        export.setTaskCfgId(taskConfigId);
        export.setStatu(exportStatu);
        export.setDeletedFlag("N");
        return export;
    }

    private List<EcMerchantOrderVo> convertEcMerchantOrderList2Vo(List<EcMerchantOrder> listBean) {
        if (StringUtils.isEmpty(listBean)) {
            return null;
        }
        List<EcMerchantOrderVo> listVo = new ArrayList<EcMerchantOrderVo>();
        for (EcMerchantOrder ecOrder : listBean) {
            listVo.add(convertEcMerchantOrder2Vo(ecOrder));
        }
        return listVo;
    }

    private EcMerchantOrderVo convertEcMerchantOrder2Vo(EcMerchantOrder bean) {
        EcMerchantOrderVo vo = new EcMerchantOrderVo();
        vo.setChannel(StringUtils.isEmpty(bean.getChannel()) ? "" : bean.getChannel());
        vo.setMerchantName(StringUtils.isEmpty(bean.getMerchantName()) ? "" : bean.getMerchantName());
        vo.setOrderNumber(StringUtils.isEmpty(bean.getOrderNumber()) ? "" : bean.getOrderNumber());
        vo.setProductName(StringUtils.isEmpty(bean.getProductName()) ? "" : bean.getProductName());
        vo.setProductCode(StringUtils.isEmpty(bean.getProductCode()) ? "" : bean.getProductCode());
        vo.setDeviceType(StringUtils.isEmpty(bean.getDeviceType()) ? "" : bean.getDeviceType());
        vo.setPrice(StringUtils.isEmpty(bean.getPrice()) ? 0 : bean.getPrice());
        vo.setOrderQuantity(StringUtils.isEmpty(bean.getOrderQuantity()) ? 0 : bean.getOrderQuantity());
        vo.setCheckQuantity(StringUtils.isEmpty(bean.getCheckQuantity()) ? 0 : bean.getCheckQuantity());
        vo.setDispatchOrderNumber(StringUtils.isEmpty(bean.getDispatchOrderNumber()) ? "" : bean.getDispatchOrderNumber());
        vo.setAlreadyShipmentQuantity(StringUtils.isEmpty(bean.getAlreadyShipmentQuantity()) ? 0 : bean.getAlreadyShipmentQuantity());
        vo.setShipmentTime(StringUtils.isEmpty(bean.getShipmentTime()) ? "" : bean.getShipmentTime());
        vo.setShipmentQuantity(StringUtils.isEmpty(bean.getShipmentQuantity()) ? "" : bean.getShipmentQuantity());
        vo.setSignQuantity(StringUtils.isEmpty(bean.getSignQuantity()) ? 0 : bean.getSignQuantity());
        vo.setOweQuantity(StringUtils.isEmpty(bean.getOweQuantity()) ? 0 : bean.getOweQuantity());
        vo.setTotalAmount(vo.getPrice() * vo.getCheckQuantity());
        vo.setOrderTime(StringUtils.isEmpty(bean.getOrderTime()) ? "" : bean.getOrderTime());
        vo.setProductRemarks(StringUtils.isEmpty(bean.getProductRemarks()) ? "" : bean.getProductRemarks());
        vo.setCheckBy(StringUtils.isEmpty(bean.getCheckBy()) ? "" : bean.getCheckBy());
        vo.setCheckTime(StringUtils.isEmpty(bean.getCheckTime()) ? "" : bean.getCheckTime());
        vo.setStatus(StringUtils.isEmpty(bean.getStatus()) ? "" : bean.getStatus());
        vo.setAddressee(StringUtils.isEmpty(bean.getAddressee()) ? "" : bean.getAddressee());
        vo.setMobile(StringUtils.isEmpty(bean.getMobile()) ? "" : bean.getMobile());
        vo.setAddressDetail(StringUtils.isEmpty(bean.getAddressDetail()) ? "" : bean.getAddressDetail());
        vo.setMaterialCode(StringUtils.isEmpty(bean.getMaterialCode()) ? "" : bean.getMaterialCode());
        vo.setMaterialName(StringUtils.isEmpty(bean.getMaterialName()) ? "" : bean.getMaterialName());
        vo.setLogisticsDesc(StringUtils.isEmpty(bean.getLogisticsDesc()) ? "" : bean.getLogisticsDesc());
        vo.setOrderRemark(StringUtils.isEmpty(bean.getOrderRemark()) ? "" : bean.getOrderRemark());
        vo.setCheckRemark(StringUtils.isEmpty(bean.getCheckRemark()) ? "" : bean.getCheckRemark());
        vo.setDealerUserName(StringUtils.isEmpty(bean.getDealerUserName()) ? "" : bean.getDealerUserName());
        return vo;
    }

    private MerchantOrder generatorBsMerchantOrder(ExportMerchantOrder exportMerchantOrder) {
        MerchantOrder merchantOrder = new MerchantOrder();
        merchantOrder.setStatus(exportMerchantOrder.getStatus());
        merchantOrder.setSignStatus(exportMerchantOrder.getSignStatus());
        merchantOrder.setOrderTimeStart(exportMerchantOrder.getStartDate());
        merchantOrder.setOrderTimeEnd(exportMerchantOrder.getEndDate());
        merchantOrder.setMoOrderCode(exportMerchantOrder.getMoMerchantOrder());
        merchantOrder.setOrderNumber(exportMerchantOrder.getOrderNumber());
        merchantOrder.setProductCode(exportMerchantOrder.getProductCode());
        merchantOrder.setProductName(exportMerchantOrder.getProductName());
        merchantOrder.setMaterialCode(exportMerchantOrder.getMaterialCode());
        merchantOrder.setMaterialName(exportMerchantOrder.getMaterialName());
        merchantOrder.setProductTypeId(exportMerchantOrder.getProductTypeId());
        merchantOrder.setMerchantCode(exportMerchantOrder.getMerchantCode());
        merchantOrder.setMerchantName(exportMerchantOrder.getMerchantName());
        merchantOrder.setCheckStartDate(exportMerchantOrder.getCheckStartDate());
        merchantOrder.setCheckEndDate(exportMerchantOrder.getCheckEndDate());
        merchantOrder.setChannel(exportMerchantOrder.getChannel());
        return merchantOrder;
    }

    private EcMerchantOrder generatorEcMerchantOrder(ExportMerchantOrder exportMerchantOrder) {
        EcMerchantOrder ecMerchantOrder = new EcMerchantOrder();
        ecMerchantOrder.setOrderNumber(exportMerchantOrder.getOrderNumber());
        ecMerchantOrder.setMaterialCode(exportMerchantOrder.getMaterialCode());
        if (!StringUtils.isEmpty(exportMerchantOrder.getType())) {
            ecMerchantOrder.setDeviceType(deviceTypeServie.getDeviceTypeNameByTypeId(Integer.valueOf(exportMerchantOrder.getType())));
        }
        ecMerchantOrder.setChannel(getChannelNameById(exportMerchantOrder.getChannel()));
        ecMerchantOrder.setMerchantCode(exportMerchantOrder.getMerchantCode());
        ecMerchantOrder.setMerchantName(exportMerchantOrder.getMerchantName());
        ecMerchantOrder.setStatus(getMerchantOrderStatuName(exportMerchantOrder.getStatus()));
        ecMerchantOrder.setStartDate(exportMerchantOrder.getStartDate());
        ecMerchantOrder.setEndDate(exportMerchantOrder.getEndDate());
        ecMerchantOrder.setCheckStartDate(exportMerchantOrder.getCheckStartDate());
        ecMerchantOrder.setCheckEndDate(exportMerchantOrder.getCheckEndDate());
        return ecMerchantOrder;
    }

    private BsMerchantOrderJXCExcelVo convertToJxcExcelVo(BsMerchantOrderVo bsMerchantOrderVo, BsAddressVo bsAddressVo) {
        BsMerchantOrderJXCExcelVo merchantOrderVo = new BsMerchantOrderJXCExcelVo();
        merchantOrderVo.setMoOrderCode(StringUtils.isEmpty(bsMerchantOrderVo.getMoOrderCode()) ? "" : bsMerchantOrderVo.getMoOrderCode());
        merchantOrderVo.setProductName(StringUtils.isEmpty(bsMerchantOrderVo.getProductName()) ? "" : bsMerchantOrderVo.getProductName());
        merchantOrderVo.setMaterialName(StringUtils.isEmpty(bsMerchantOrderVo.getMaterialName()) ? "" : bsMerchantOrderVo.getMaterialName());
        merchantOrderVo.setTotalOrder(bsMerchantOrderVo.getTotalOrder() == null ? 0 : bsMerchantOrderVo.getTotalOrder());
        merchantOrderVo.setTotalCheck(bsMerchantOrderVo.getTotalCheck() == null ? 0 : bsMerchantOrderVo.getTotalCheck());
        merchantOrderVo.setTotalSends(bsMerchantOrderVo.getTotalSends() == null ? 0 : bsMerchantOrderVo.getTotalSends());
        merchantOrderVo.setTotalOwes(bsMerchantOrderVo.getTotalOwes() == null ? 0 : bsMerchantOrderVo.getTotalOwes());
        merchantOrderVo.setAcceptQuantity(bsMerchantOrderVo.getAcceptQuantity() == null ? 0 : bsMerchantOrderVo.getAcceptQuantity());
        merchantOrderVo.setStatus(getMerchantOrderStatuName(Byte.valueOf(bsMerchantOrderVo.getStatus())));
        merchantOrderVo.setRemarks(StringUtils.isEmpty(bsMerchantOrderVo.getRemarks()) ? "" : bsMerchantOrderVo.getRemarks());
        merchantOrderVo.setName((null == bsAddressVo) ? "" : (StringUtils.isEmpty(bsAddressVo.getName()) ? "" : bsAddressVo.getName()));
        merchantOrderVo.setMobile((null == bsAddressVo) ? "" : (StringUtils.isEmpty(bsAddressVo.getMobile()) ? "" : bsAddressVo.getMobile()));
        merchantOrderVo.setAddress((null == bsAddressVo) ? "" : (StringUtils.isEmpty(bsAddressVo.getAddress()) ? "" : bsAddressVo.getAddress()));
        merchantOrderVo.setOrderTime((bsMerchantOrderVo.getOrderTime() == null) ? "" : RdnUtils.getNowDateStringYMD(bsMerchantOrderVo.getOrderTime()));
        return merchantOrderVo;
    }

    private Map<String, BsAddressVo> listBsAddressByBsMerchantOrderCodes(List<String> listBsMerchantOrderCodes) {
        Map<String, BsAddressVo> mapResult = new HashMap<>();
        List<Logistics> listLogistics = bsLogisticsService.listBsLogisticsByBsMerchantOrderCodes(listBsMerchantOrderCodes);
        if (null == listLogistics || listLogistics.isEmpty()) {
            return mapResult;
        }
        List<Long> listReceiveIds = listLogistics.stream().map(Logistics::getReceiveId).collect(Collectors.toList());
        List<BsAddressVo> listBsAddress = bsAddressSevice.listAddressByIds(listReceiveIds);
        Map<Integer, BsAddressVo> mapBsAddress = listBsAddress.stream().collect(Collectors.toMap(BsAddressVo::getId, a -> a));
        for (Logistics logistics : listLogistics) {
            mapResult.put(logistics.getServiceCode(), mapBsAddress.get(logistics.getReceiveId().intValue()));
        }
        return mapResult;
    }

    private String getChannelNameById(Byte channelId) {
        if (StringUtils.isEmpty(channelId)) {
            return null;
        }
        if (channelId.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_GHUI.getName();
        }
        if (channelId.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_OTHER.getName();
        }
        if (channelId.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_TMHUI.getName();
        }
        if (channelId.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL.getName();
        }
        if (channelId.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_FIVE.getName();
        }
        if (channelId.equals(DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getCode())) {
            return DealerUserInfoChannelEnum.DEALER_USER_INFO_CHANNEL_STATUS_SIX.getName();
        }
        return null;
    }

    private String getMerchantOrderStatuName(Byte status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode())) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode())) {
            return MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())) {
            return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode())) {
            return MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode())) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode())) {
            return MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode())) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
        }
        if (status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getCode())) {
            return MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getName();
        }
        return null;
    }

    private String getDispatchOrderStatuName(String status) {
        if (StringUtils.isEmpty(status)) {
            return null;
        }
        if (status.equals(OrderStatusEnum.STATUS_UF)) {
            return "未完成";
        }
        if (status.equals(OrderStatusEnum.STATUS_OV)) {
            return "已完成";
        }
        if (status.equals(OrderStatusEnum.STATUS_CL)) {
            return "已取消";
        }
        return "";
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 商务调拨订单异步导出
     * @date: 2020/11/25 15:46
     * @return: void
     */
    public void handleExportBsTransferOrder(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportMdbTransferOrder()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportMdbTransferOrder()));
            setTaskRecordExport(taskRecordExport);
            MdbTransferOrder mdbTransferOrder = generatorBsTransferOrder(kafkaMessage.getExportMdbTransferOrder());
            List<BsTransferOrderBssVo> listTransferOrderBssVo = transferOrderService.exportBsTransferOrder(mdbTransferOrder);
            List<BsTransferOrderBssExportVo> listExcelVos = new ArrayList<>();
            if (null != listTransferOrderBssVo && !listTransferOrderBssVo.isEmpty()) {

            }
            List<String> tranOrderCodes = new ArrayList<>();
            List<Long> addressIds = new ArrayList<>();
            for (BsTransferOrderBssVo transferOrderBssVo : listTransferOrderBssVo) {
                tranOrderCodes.add(transferOrderBssVo.getTranOrderCode());
            }
            //获取调拨订单物流信息->获取收货地址ID
            Map<String, Logistics> map = bsLogisticsService.listMapLogisticsById(tranOrderCodes);
            for (BsTransferOrderBssVo transferOrderBssVo : listTransferOrderBssVo) {
                transferOrderBssVo.setReceiveId(map.get(transferOrderBssVo.getTranOrderCode()).getReceiveId());
                addressIds.add(map.get(transferOrderBssVo.getTranOrderCode()).getReceiveId());
            }
            JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO = null;
            //获取调拨订单收货地址
            Map<Long, Address> addressMap = bsAddressSevice.listMapAddressByIds(addressIds);
            JXCMTBsAddressDTO addressDTO = null;
            for (BsTransferOrderBssVo transferOrderBssVo : listTransferOrderBssVo) {
                //填充地址信息
                addressDTO = new JXCMTBsAddressDTO();
                addressDTO.setName(addressMap.get(transferOrderBssVo.getReceiveId()).getName());
                addressDTO.setMobile(addressMap.get(transferOrderBssVo.getReceiveId()).getMobile());
                addressDTO.setAddress(addressMap.get(transferOrderBssVo.getReceiveId()).getAddress());
                addressDTO.setProvinceName(addressMap.get(transferOrderBssVo.getReceiveId()).getProvinceName());
                addressDTO.setCityName(addressMap.get(transferOrderBssVo.getReceiveId()).getCityName());
                addressDTO.setAreaName(addressMap.get(transferOrderBssVo.getReceiveId()).getAreaName());
                transferOrderBssVo.setBsAddressDTO(addressDTO);
                if (!StringUtils.isEmpty(transferOrderBssVo.getOrderTotal()) && !StringUtils.isEmpty(transferOrderBssVo.getSendTotal())) {
                    transferOrderBssVo.setOweTotal(transferOrderBssVo.getOrderTotal() - transferOrderBssVo.getSendTotal());
                }
            }
            List<BsTransferOrderBssExportVo> transferOrderExportVos = listTransferOrderBssVo.stream().map(transferOrderBssVo -> exportBsTransferOrderExportConvertTo(transferOrderBssVo)).collect(Collectors.toList());
//            List<Object> merchantOrderList = new ArrayList<Object>();
//            merchantOrderList.addAll(listExcelVos);
//            Map<String, Object> map = new HashMap<>();
//            map.put("objs", merchantOrderList);
//            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_BS_MERCHANT_ORDER_BSS);
//            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS);
//            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_BS_MERCHANT_ORDER_BSS);
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_BS_TRANSFER_ORDER_BSS);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_BS_TRANSFER_ORDER_BSS);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
//            excelXlsxUtilStream.buildExcelDocument(map, null, fileOutputStream);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, transferOrderExportVos, Constants.EXPORT_NAME_BS_TRANSFER_ORDER_BSS, Constants.EXPORT_NAME_BS_TRANSFER_ORDER_BSS, BsTransferOrderBssExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
            logger.info("OperatorExportKafkaConsumer::handleExportBsTransferOrder taskRecordExport::{}", taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private MdbTransferOrder generatorBsTransferOrder(ExportBsMdbTransferOrder exportMdbTransferOrder) {
        MdbTransferOrder transferOrder = new MdbTransferOrder();
        transferOrder.setStartTime(exportMdbTransferOrder.getStartTime());
        transferOrder.setEndTime(exportMdbTransferOrder.getEndTime());
        transferOrder.setTranOrderCode(exportMdbTransferOrder.getTranOrderCode());
        transferOrder.setInMerchantName(exportMdbTransferOrder.getServiceProviderName());
        transferOrder.setOutMerchantName(exportMdbTransferOrder.getServiceProviderName());
        transferOrder.setMaterialCode(exportMdbTransferOrder.getMaterialCode());
        transferOrder.setMaterialName(exportMdbTransferOrder.getMaterialName());
        transferOrder.setOrderStatus(exportMdbTransferOrder.getOrderStatus());
        transferOrder.setOrderSource(exportMdbTransferOrder.getOrderSource());
        return transferOrder;
    }


    private BsTransferOrderBssExportVo exportBsTransferOrderExportConvertTo(BsTransferOrderBssVo transferOrderBssVo) {
        BsTransferOrderBssExportVo transferOrderExportVo = new BsTransferOrderBssExportVo();
        BeanUtils.copyProperties(transferOrderBssVo, transferOrderExportVo);
        String orderStr = convertTransferOrderStatus(transferOrderExportVo.getOrderStatus());
        if (transferOrderExportVo.getOrderSource().equals(Constants.GXS_ORDER_SOURCE)) {
            transferOrderExportVo.setOrderSource("广联商务");
        } else {
            transferOrderExportVo.setOrderSource("服务商");
        }
        transferOrderExportVo.setOrderStatus(orderStr);
        transferOrderExportVo.setName(transferOrderBssVo.getBsAddressDTO().getName());
        transferOrderExportVo.setMobile(transferOrderBssVo.getBsAddressDTO().getMobile());
        transferOrderExportVo.setAddress(transferOrderBssVo.getBsAddressDTO().getAddress());
        return transferOrderExportVo;
    }

    private String convertTransferOrderStatus(String status) {
        if (status.equals(TransferOrderStatusEnum.ORDER_WAIT_CHECK.getCode())) {
            return TransferOrderStatusEnum.ORDER_WAIT_CHECK.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())) {
            return TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getCode())) {
            return TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_COMPLETED.getCode())) {
            return TransferOrderStatusEnum.ORDER_COMPLETED.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getCode())) {
            return TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getName();
        }
        return "";
    }

    private MdbTransferOrder generatorJxcTransferOrder(ExportJxcMdbTransferOrder exportJxcMdbTransferOrder) {
        MdbTransferOrder transferOrder = new MdbTransferOrder();
        transferOrder.setInServiceProviderName(exportJxcMdbTransferOrder.getInServiceProviderName());
        transferOrder.setMaterialCode(exportJxcMdbTransferOrder.getMaterialName());
        transferOrder.setMaterialName(exportJxcMdbTransferOrder.getMaterialName());
        transferOrder.setOrderStatus(exportJxcMdbTransferOrder.getOrderStatus());
        transferOrder.setTransferType(exportJxcMdbTransferOrder.getTransferType());
        transferOrder.setOrderSource(exportJxcMdbTransferOrder.getOrderSource());
        return transferOrder;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 进销存调拨订单异步导出
     * @date: 2020/11/30 11:18
     * @return: void
     */
    public void handleExportJXCTransferOrder(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportJxcMdbTransferOrder()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportJxcMdbTransferOrder()));
            setTaskRecordExport(taskRecordExport);
            MdbTransferOrder mdbTransferOrder = generatorJxcTransferOrder(kafkaMessage.getExportJxcMdbTransferOrder());
            List<JxcTransferOrderVo> listJxcTransferOrder = transferOrderService.exportJXCTransferOrder(mdbTransferOrder);
            List<JxcTransferOrderExportVo> listExcelVos = new ArrayList<>();
            if (null != listJxcTransferOrder && !listJxcTransferOrder.isEmpty()) {

            }
            List<String> tranOrderCodes = new ArrayList<>();
            List<Long> addressIds = new ArrayList<>();
            for (JxcTransferOrderVo transferOrderBssVo : listJxcTransferOrder) {
                tranOrderCodes.add(transferOrderBssVo.getTranOrderCode());
            }
            //获取调拨订单物流信息->获取收货地址ID
            Map<String, Logistics> map = bsLogisticsService.listMapLogisticsById(tranOrderCodes);
            for (JxcTransferOrderVo transferOrderBssVo : listJxcTransferOrder) {
                transferOrderBssVo.setReceiveId(map.get(transferOrderBssVo.getTranOrderCode()).getReceiveId());
                addressIds.add(map.get(transferOrderBssVo.getTranOrderCode()).getReceiveId());
            }
            JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO = null;
            //获取调拨订单收货地址
            Map<Long, Address> addressMap = bsAddressSevice.listMapAddressByIds(addressIds);
            JXCMTBsAddressDTO addressDTO = null;
            for (JxcTransferOrderVo transferOrderBssVo : listJxcTransferOrder) {
                //填充地址信息
                addressDTO = new JXCMTBsAddressDTO();
                addressDTO.setName(addressMap.get(transferOrderBssVo.getReceiveId()).getName());
                addressDTO.setMobile(addressMap.get(transferOrderBssVo.getReceiveId()).getMobile());
                addressDTO.setAddress(addressMap.get(transferOrderBssVo.getReceiveId()).getAddress());
                addressDTO.setProvinceName(addressMap.get(transferOrderBssVo.getReceiveId()).getProvinceName());
                addressDTO.setCityName(addressMap.get(transferOrderBssVo.getReceiveId()).getCityName());
                addressDTO.setAreaName(addressMap.get(transferOrderBssVo.getReceiveId()).getAreaName());
                transferOrderBssVo.setBsAddressDTO(addressDTO);
                if (!StringUtils.isEmpty(transferOrderBssVo.getOrderTotal()) && !StringUtils.isEmpty(transferOrderBssVo.getSendTotal())) {
                    transferOrderBssVo.setOweTotal(transferOrderBssVo.getOrderTotal() - transferOrderBssVo.getSendTotal());
                }
            }
            List<JxcTransferOrderExportVo> transferOrderExportVos = listJxcTransferOrder.stream().map(transferOrderVo -> exportJXCTransferOrderExportConvertTo(transferOrderVo)).collect(Collectors.toList());
//            List<Object> merchantOrderList = new ArrayList<Object>();
//            merchantOrderList.addAll(listExcelVos);
//            Map<String, Object> map = new HashMap<>();
//            map.put("objs", merchantOrderList);
//            map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_BS_MERCHANT_ORDER_BSS);
//            map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS);
//            map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_BS_MERCHANT_ORDER_BSS);
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_JXC_TRANSFER_ORDER_BSS);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_JXC_TRANSFER_ORDER_BSS);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
//            excelXlsxUtilStream.buildExcelDocument(map, null, fileOutputStream);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, transferOrderExportVos, Constants.EXPORT_NAME_JXC_TRANSFER_ORDER_BSS, Constants.EXPORT_NAME_JXC_TRANSFER_ORDER_BSS, JxcTransferOrderExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private JxcTransferOrderExportVo exportJXCTransferOrderExportConvertTo(JxcTransferOrderVo jxcTransferOrderVo) {
        JxcTransferOrderExportVo transferOrderExportVo = new JxcTransferOrderExportVo();
        BeanUtils.copyProperties(jxcTransferOrderVo, transferOrderExportVo);
        String orderStr = convertTransferOrderStatus(transferOrderExportVo.getOrderStatus());
        if (transferOrderExportVo.getOrderSource().equals(Constants.GXS_ORDER_SOURCE)) {
            transferOrderExportVo.setOrderSource("广联商务");
        } else {
            transferOrderExportVo.setOrderSource("服务商");
        }
        transferOrderExportVo.setOrderStatus(orderStr);
        transferOrderExportVo.setName(jxcTransferOrderVo.getBsAddressDTO().getName());
        transferOrderExportVo.setMobile(jxcTransferOrderVo.getBsAddressDTO().getMobile());
        transferOrderExportVo.setAddress(jxcTransferOrderVo.getBsAddressDTO().getAddress());
        return transferOrderExportVo;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 库存列表异步导出
     * @date: 2020/11/25 15:46
     * @return: void
     */
    public void handleExportMerchantStock(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportSTKMerchantStock()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportSTKMerchantStock()));
            setTaskRecordExport(taskRecordExport);
            STKMerchantStock merchantStock = generatorMerchantStock(kafkaMessage.getExportSTKMerchantStock());
            logger.info("OperatorExportKafkaConsumer::handleExportMerchantStock merchantStock::{}", merchantStock);
            List<STKMerchantStockDTO> stkMerchantStockDTOS = merchantStockService.exportMerchantStock(merchantStock);
            logger.info("OperatorExportKafkaConsumer::handleExportMerchantStock stkMerchantStockDTOS::{}", JSONObject.toJSON(stkMerchantStockDTOS));
            List<STKMerchantStockExportVo> stkMerchantStockExportVos = stkMerchantStockDTOS.stream().map(stkMerchantStockDTO -> exportSTKMerchantStockConvertTo(stkMerchantStockDTO)).collect(Collectors.toList());
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_STK_MERCHANT_STOCK);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_STK_MERCHANT_STOCK);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, stkMerchantStockExportVos, Constants.EXPORT_NAME_STK_MERCHANT_STOCK, Constants.EXPORT_NAME_STK_MERCHANT_STOCK, STKMerchantStockExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private STKMerchantStockExportVo exportSTKMerchantStockConvertTo(STKMerchantStockDTO stkMerchantStockDTO) {
        STKMerchantStockExportVo stkMerchantStockExportVo = new STKMerchantStockExportVo();
        BeanUtils.copyProperties(stkMerchantStockDTO, stkMerchantStockExportVo);
        return stkMerchantStockExportVo;
    }

    private STKMerchantStock generatorMerchantStock(ExportSTKMerchantStock exportSTKMerchantStock) {
        STKMerchantStock stkMerchantStock = new STKMerchantStock();
        stkMerchantStock.setMerchantChannelId(attribInfoService.getDbMerchantChannelFromAttribInfo(exportSTKMerchantStock.getChannelId()));
        stkMerchantStock.setMerchantCode(exportSTKMerchantStock.getMerchantSearchKey());
        stkMerchantStock.setMerchantName(exportSTKMerchantStock.getMerchantSearchKey());
        stkMerchantStock.setDeviceType(exportSTKMerchantStock.getDeviceType());
        stkMerchantStock.setStockMonth(exportSTKMerchantStock.getStockMonth());
        return stkMerchantStock;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 库销比预警列表导出
     * @date: 2020/11/25 15:46
     * @return: void
     */
    public void handleExportMerchantWarningWaresale(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportMerchantWarningWaresale()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportMerchantWarningWaresale()));
            setTaskRecordExport(taskRecordExport);
            STKMerchantWarningWaresale merchantWarningWaresale = generatorMerchantWarningWaresale(kafkaMessage.getExportMerchantWarningWaresale());
            List<STKWarningWaresaleDTO> stkWarningWaresaleDTOS = warningService.exportWarningWaresale(merchantWarningWaresale);
            List<STKWarningWaresaleExportVo> stkWarningWaresaleExportVos = stkWarningWaresaleDTOS.stream().map(stkWarningWaresaleDTO -> exportSTKWarningWaresaleConvertTo(stkWarningWaresaleDTO)).collect(Collectors.toList());
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_STK_WARNING_WARESALE);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_STK_WARNING_WARESALE);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, stkWarningWaresaleExportVos, Constants.EXPORT_NAME_STK_WARNING_WARESALE, Constants.EXPORT_NAME_STK_WARNING_WARESALE, STKWarningWaresaleExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private STKWarningWaresaleExportVo exportSTKWarningWaresaleConvertTo(STKWarningWaresaleDTO stkWarningWaresaleDTO) {
        STKWarningWaresaleExportVo stkWarningWaresaleExportVo = new STKWarningWaresaleExportVo();
        BeanUtils.copyProperties(stkWarningWaresaleDTO, stkWarningWaresaleExportVo);
        return stkWarningWaresaleExportVo;
    }

    private STKMerchantWarningWaresale generatorMerchantWarningWaresale(ExportMerchantWarningWaresale exportMerchantWarningWaresale) {
        STKMerchantWarningWaresale stkMerchantStock = new STKMerchantWarningWaresale();
        stkMerchantStock.setMerchantChannelId(attribInfoService.getDbMerchantChannelFromAttribInfo(exportMerchantWarningWaresale.getChannelId()));
        stkMerchantStock.setMerchantCode(exportMerchantWarningWaresale.getMerchantName());
        stkMerchantStock.setMerchantName(exportMerchantWarningWaresale.getMerchantName());
        stkMerchantStock.setDeviceType(exportMerchantWarningWaresale.getDeviceType());
        return stkMerchantStock;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 呆滞品设备明细列表异步导出
     * @date: 2020/11/25 15:48
     * @return: void
     */
    public void handleExportMerchantWarningDeviceSn(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportWarningDeviceSn()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportWarningDeviceSn()));
            setTaskRecordExport(taskRecordExport);
            STKMerchantWarningDevicesn stkMerchantWarningDevicesn = generatorMerchantWarningDeviceSn(kafkaMessage.getExportWarningDeviceSn());
            List<STKWarningDevicesnDTO> stkWarningDevicesnDTOS = warningService.exportMerchantWarningDeviceSn(stkMerchantWarningDevicesn);
            List<STKWarningDevicesnExportVo> stkWarningDevicesnExportVos = stkWarningDevicesnDTOS.stream().map(stkWarningDevicesnDTO -> exportSTKWarningDevicesnConvertTo(stkWarningDevicesnDTO)).collect(Collectors.toList());
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_STK_WARNING_DEVICESN);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_STK_WARNING_DEVICESN);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, stkWarningDevicesnExportVos, Constants.EXPORT_NAME_STK_WARNING_DEVICESN, Constants.EXPORT_NAME_STK_WARNING_DEVICESN, STKWarningDevicesnExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private STKWarningDevicesnExportVo exportSTKWarningDevicesnConvertTo(STKWarningDevicesnDTO warningDevicesnDTO) {
        STKWarningDevicesnExportVo stkWarningDevicesnExportVo = new STKWarningDevicesnExportVo();
        BeanUtils.copyProperties(warningDevicesnDTO, stkWarningDevicesnExportVo);
        return stkWarningDevicesnExportVo;
    }

    private STKMerchantWarningDevicesn generatorMerchantWarningDeviceSn(ExportWarningDeviceSn exportWarningDeviceSn) {
        STKMerchantWarningDevicesn stkMerchantWarningDevicesn = new STKMerchantWarningDevicesn();
        stkMerchantWarningDevicesn.setToMerchantCode(exportWarningDeviceSn.getToMerchantName());
        stkMerchantWarningDevicesn.setToMerchantName(exportWarningDeviceSn.getToMerchantName());
        stkMerchantWarningDevicesn.setMaterialCode(exportWarningDeviceSn.getMaterialName());
        stkMerchantWarningDevicesn.setMaterialName(exportWarningDeviceSn.getMaterialName());
        stkMerchantWarningDevicesn.setWarningCode(exportWarningDeviceSn.getWarningCode());
        stkMerchantWarningDevicesn.setDeviceType(exportWarningDeviceSn.getDeviceType());
        return stkMerchantWarningDevicesn;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 库存设备未激活统计列表异步导出
     * @date: 2020/11/25 18:30
     * @return: void
     */
    public void handleExportMerchantStockSnStat(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportMerchantStockSnStat()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportMerchantStockSnStat()));
            setTaskRecordExport(taskRecordExport);
            STKMerchantStockSnStat stkMerchantStockSnStat = generatorMerchantStockSnStat(kafkaMessage.getExportMerchantStockSnStat());
            List<STKMerchantStockSnStatDTO> stkMerchantStockSnStatDTOS = merchantStockSnService.exportMerchantStockSnStatByToMerchantCode(stkMerchantStockSnStat);
            List<STKMerchantStockSnStatExportVo> merchantStockSnStatExportVos = stkMerchantStockSnStatDTOS.stream().map(stkMerchantStockSnStatDTO -> exportMerchantStockSnStatConvertTo(stkMerchantStockSnStatDTO)).collect(Collectors.toList());
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_STK_STOCK_SN_STAT);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_STK_STOCK_SN_STAT);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, merchantStockSnStatExportVos, Constants.EXPORT_NAME_STK_STOCK_SN_STAT, Constants.EXPORT_NAME_STK_STOCK_SN_STAT, STKMerchantStockSnStatExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private STKMerchantStockSnStatExportVo exportMerchantStockSnStatConvertTo(STKMerchantStockSnStatDTO merchantStockSnStatDTO) {
        STKMerchantStockSnStatExportVo merchantStockSnStatExportVo = new STKMerchantStockSnStatExportVo();
        BeanUtils.copyProperties(merchantStockSnStatDTO, merchantStockSnStatExportVo);
        return merchantStockSnStatExportVo;
    }

    private STKMerchantStockSnStat generatorMerchantStockSnStat(ExportMerchantStockSnStat exportMerchantStockSnStat) {
        STKMerchantStockSnStat stkMerchantStockSnStat = new STKMerchantStockSnStat();
        if (exportMerchantStockSnStat.getUnActiveDayFlag().equals("TH")) {
            stkMerchantStockSnStat.setUnActiveDays(90);
        } else if (exportMerchantStockSnStat.getUnActiveDayFlag().equals("SI")) {
            stkMerchantStockSnStat.setUnActiveDays(180);
        } else if (exportMerchantStockSnStat.getUnActiveDayFlag().equals("NI")) {
            stkMerchantStockSnStat.setUnActiveDays(270);
        }
        return stkMerchantStockSnStat;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 库存未激活设备明细异步导出
     * @date: 2020/11/25 18:30
     * @return: void
     */
    public void handleExportMerchantStockSn(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportMerchantStockSn()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportMerchantStockSn()));
            setTaskRecordExport(taskRecordExport);
            STKMerchantStockSn merchantStockSn = generatorMerchantStockSn(kafkaMessage.getExportMerchantStockSn());
            List<STKMerchantStockSnDTO> merchantStockSnDTOS = merchantStockSnService.exportMerchantStockSn(merchantStockSn);
            List<STKMerchantStockSnExportVo> merchantStockSnExportVos = merchantStockSnDTOS.stream().map(merchantStockSnDTO -> exportMerchantStockSnConvertTo(merchantStockSnDTO)).collect(Collectors.toList());
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_STK_MERCHANT_STOCK_SN);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_STK_MERCHANT_STOCK_SN);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, merchantStockSnExportVos, Constants.EXPORT_NAME_STK_MERCHANT_STOCK_SN, Constants.EXPORT_NAME_STK_MERCHANT_STOCK_SN, STKMerchantStockSnExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private STKMerchantStockSnExportVo exportMerchantStockSnConvertTo(STKMerchantStockSnDTO merchantStockSnDTO) {
        STKMerchantStockSnExportVo merchantStockSnExportVo = new STKMerchantStockSnExportVo();
        BeanUtils.copyProperties(merchantStockSnDTO, merchantStockSnExportVo);
        return merchantStockSnExportVo;
    }

    private STKMerchantStockSn generatorMerchantStockSn(ExportMerchantStockSn exportMerchantStockSn) {
        STKMerchantStockSnDTO dtoCondition = new STKMerchantStockSnDTO();
        if (exportMerchantStockSn.getUnActiveDayFlag().equals("TH")) {
            dtoCondition.setUnActiveDays(90);
        } else if (exportMerchantStockSn.getUnActiveDayFlag().equals("SI")) {
            dtoCondition.setUnActiveDays(180);
        } else if (exportMerchantStockSn.getUnActiveDayFlag().equals("NI")) {
            dtoCondition.setUnActiveDays(270);
        }
        Example example = new Example(STKMerchantStockSnStat.class);
        example.createCriteria().andEqualTo("deviceType", exportMerchantStockSn.getDeviceType())
                .andEqualTo("unActiveDays", dtoCondition.getUnActiveDays())
                .andEqualTo("activeOrNot", "N");
        List<STKMerchantStockSnStat> stkMerchantStockSnStatList = stockSnStatMapper.selectByExample(example);
        STKMerchantStockSnStat stkMerchantStockSnStat = null;
        if (!StringUtils.isEmpty(stkMerchantStockSnStatList)) {
            stkMerchantStockSnStat = stkMerchantStockSnStatList.get(0);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stkMerchantStockSnStat.getCreatedDate());
        //设置为前三个月
        calendar.add(Calendar.MONTH, -3);
        //得到前3月的时间
        Date formNow3Month = calendar.getTime();
        STKMerchantStockSn stkMerchantStockSn = new STKMerchantStockSn();
        stkMerchantStockSn.setDeviceType(exportMerchantStockSn.getDeviceType());
        stkMerchantStockSn.setUpdatedDate(formNow3Month);
        return stkMerchantStockSn;
    }


    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 供应链发货单异步导出
     * @date: 2020/11/25 18:30
     * @return: void
     */
    public void handleExportDispatchOrder(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportJXCMTOrderInfo()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportJXCMTOrderInfo()));
            setTaskRecordExport(taskRecordExport);
            OrderInfo orderInfo = generatorJXCMTOrderInfo(kafkaMessage.getExportJXCMTOrderInfo());
            List<JXCMTOrderInfoDTO> orderInfoDTOS = orderDispatchService.exportDispatchOrder(orderInfo);
            List<OrderInfoExportVo> orderInfoExportVos = orderInfoDTOS.stream().map(orderInfoDTO -> exportOrderInfoConvertTo(orderInfoDTO)).collect(Collectors.toList());
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_INVOICE_MERCHANT_ORDER_BSP);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_INVOICE_MERCHANT_ORDER_BSP);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcel(fileOutputStream, orderInfoExportVos, Constants.EXPORT_NAME_INVOICE_MERCHANT_ORDER_BSP, Constants.EXPORT_NAME_INVOICE_MERCHANT_ORDER_BSP, OrderInfoExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private OrderInfoExportVo exportOrderInfoConvertTo(JXCMTOrderInfoDTO orderInfoDTO) {
        OrderInfoExportVo orderInfoExportVo = new OrderInfoExportVo();
        orderInfoDTO.setBsParentBrandName(StringUtils.isEmpty(orderInfoDTO.getBsParentBrandName()) ? "" : orderInfoDTO.getBsParentBrandName());
        orderInfoDTO.setBsSubBrandName(StringUtils.isEmpty(orderInfoDTO.getBsSubBrandName()) ? "" : orderInfoDTO.getBsSubBrandName());
        orderInfoDTO.setBsAudiName(StringUtils.isEmpty(orderInfoDTO.getBsAudiName()) ? "" : orderInfoDTO.getBsAudiName());
        orderInfoDTO.setBsMotorcycle(StringUtils.isEmpty(orderInfoDTO.getBsMotorcycle()) ? "" : orderInfoDTO.getBsMotorcycle());
        BeanUtils.copyProperties(orderInfoDTO, orderInfoExportVo);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("品牌：" + orderInfoDTO.getBsParentBrandName() + "\n");
        stringBuffer.append("子品牌：" + orderInfoDTO.getBsSubBrandName() + "\n");
        stringBuffer.append("车系：" + orderInfoDTO.getBsAudiName() + "\n");
        stringBuffer.append("车型：" + orderInfoDTO.getBsMotorcycle() + "\n");
        orderInfoExportVo.setMotorcycleDesc(stringBuffer.toString());
        if ("UF".equals(orderInfoExportVo.getStatus())) {
            orderInfoExportVo.setStatus("未完成");
        } else if ("OV".equals(orderInfoExportVo.getStatus())) {
            orderInfoExportVo.setStatus("完成");
        } else if ("CL".equals(orderInfoExportVo.getStatus())) {
            orderInfoExportVo.setStatus("已取消");
        }
        return orderInfoExportVo;
    }

    private OrderInfo generatorJXCMTOrderInfo(ExportJXCMTOrderInfo exportJXCMTOrderInfo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setDeviceTypeId(exportJXCMTOrderInfo.getDeviceTypeId());
        orderInfo.setDispatchOrderCode(exportJXCMTOrderInfo.getDispatchOrderCode());
        orderInfo.setStatus(exportJXCMTOrderInfo.getStatus());
        orderInfo.setMerchantOrder(exportJXCMTOrderInfo.getMerchantOrder());
        orderInfo.setSendMerchantNo(exportJXCMTOrderInfo.getSendMerchantNo());
        orderInfo.setWarehouseId(exportJXCMTOrderInfo.getWarehouseId());
        return orderInfo;
    }

    /**
     * @param kafkaMessage
     * @author: luoqiang
     * @description: 运营平台-设备明细列表导出
     * @date: 2020/12/9 15:07
     * @return: void
     */
    public void handleExportDeviceFile(OperatorExportKafkaMessage kafkaMessage) throws RpcServiceException {
        try {
            if (null == kafkaMessage.getTaskCfgId()) {
                return;
            }
            if (null == kafkaMessage.getExportDeviceFile()) {
                return;
            }
            String operatorFlag = RdnUtils.getSnowflakeWorkerId(snowflakeWorker);
            TaskRecordExport taskRecordExport = generatorTaskRecordExport(kafkaMessage.getTaskCfgId(), operatorFlag,
                    TaskReportExportStatuEnum.TASK_EXPORT_STATU_ST.getValue(), kafkaMessage.getUserName(), kafkaMessage.getUserName(), RdnUtils.getNowDate(), RdnUtils.getNowDate());
            taskRecordExport.setCondition(JacksonUtils.beanToJson(kafkaMessage.getExportDeviceFile()));
            setTaskRecordExport(taskRecordExport);
            JXCMTDeviceFileDTO deviceFileDTO = generatorDeviceFileDTO(kafkaMessage.getExportDeviceFile());
            List<JXCMTDeviceFileDTO> deviceFileDTOS = deviceFileService.exportDeviceFile(deviceFileDTO);
            List<DeviceFileExportVo> deviceFileExportVos = deviceFileDTOS.stream().map(jxcmtDeviceFileDTO -> exportDeviceFileConvertTo(jxcmtDeviceFileDTO)).collect(Collectors.toList());
            Integer numberNo = 1;
            for (DeviceFileExportVo deviceFileExportVo : deviceFileExportVos) {
                deviceFileExportVo.setNumberNo(numberNo++);
            }
            String fileName = getUploadPath(operatorFlag, Constants.EXPORT_FORMAT_DEVICE_FILE);
            String fileUrl = getUploadUrl(operatorFlag, Constants.EXPORT_FORMAT_DEVICE_FILE);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = null;
            fileOutputStream = new FileOutputStream(file);
            ExcelReadAndWriteUtil.writeExcelWithAuto(fileOutputStream, deviceFileExportVos, Constants.EXPORT_NAME_DEVICE_FILE, Constants.EXPORT_NAME_DEVICE_FILE, DeviceFileExportVo.class);
            taskRecordExport.setStatu(TaskReportExportStatuEnum.TASK_EXPORT_STATU_FI.getValue());
            taskRecordExport.setUrl(fileUrl);
            taskRecordExport.setUpdatedBy(kafkaMessage.getUserName());
            taskRecordExport.setUpdatedDate(RdnUtils.getNowDate());
            setTaskRecordExport(taskRecordExport);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RpcServiceException(e.getMessage());
        }
    }

    private JXCMTDeviceFileDTO generatorDeviceFileDTO(ExportDeviceFile exportDeviceFile) {
        JXCMTDeviceFileDTO deviceFileDTO = new JXCMTDeviceFileDTO();
        deviceFileDTO.setDevTypeId(exportDeviceFile.getDevTypeId());
        deviceFileDTO.setPackageStatu(exportDeviceFile.getPackageStatu());
        deviceFileDTO.setSn(exportDeviceFile.getSn());
        deviceFileDTO.setIccid(exportDeviceFile.getIccid());
        deviceFileDTO.setImsi(exportDeviceFile.getImsi());
        deviceFileDTO.setUserFlag(exportDeviceFile.getUserFlag());
        deviceFileDTO.setDeviceCode(exportDeviceFile.getDeviceCode());
        deviceFileDTO.setPackageId(exportDeviceFile.getPackageId());
        deviceFileDTO.setSendMerchantNo(exportDeviceFile.getSendMerchantNo());
        deviceFileDTO.setOutStorageStartDate(exportDeviceFile.getOutStorageStartDate());
        deviceFileDTO.setOutStorageEndDate(exportDeviceFile.getOutStorageEndDate());
        deviceFileDTO.setPackageUserStartDate(exportDeviceFile.getPackageUserStartDate());
        deviceFileDTO.setPackageUserEndDate(exportDeviceFile.getPackageUserEndDate());
        return deviceFileDTO;
    }

    private DeviceFileExportVo exportDeviceFileConvertTo(JXCMTDeviceFileDTO deviceFileDTO) {
        DeviceFileExportVo deviceFileExportVo = new DeviceFileExportVo();
        BeanUtils.copyProperties(deviceFileDTO, deviceFileExportVo);
        if (PackageStatuEnum.PACKAGE_STATU_UNACTIVE.getValue().equals(deviceFileDTO.getPackageStatu())) {
            deviceFileExportVo.setPackageStatu("未激活");
        } else if (PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue().equals(deviceFileDTO.getPackageStatu())) {
            deviceFileExportVo.setPackageStatu("已激活");
        } else if (PackageStatuEnum.PACKAGE_STATU_INITIAL.getValue().equals(deviceFileDTO.getPackageStatu())) {
            deviceFileExportVo.setPackageStatu("初始化");
        }
        if (org.springframework.util.StringUtils.isEmpty(deviceFileDTO.getUserId())) {
            deviceFileExportVo.setUserFlag("否");
        } else {
            deviceFileExportVo.setUserFlag("是");
        }
        deviceFileExportVo.setDeviceName(deviceFileDTO.getDeviceCode() + "/" + deviceFileDTO.getDeviceName());
        return deviceFileExportVo;
    }
}
