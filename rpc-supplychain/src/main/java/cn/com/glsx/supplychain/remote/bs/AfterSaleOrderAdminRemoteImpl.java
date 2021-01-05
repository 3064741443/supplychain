package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.config.DataFormatProperty;
import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.AfterSaleOrderDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.AfterSaleOrderMapper;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.DeviceFileService;
import cn.com.glsx.supplychain.service.DeviceInfoService;
import cn.com.glsx.supplychain.service.bs.AfterSaleOrderServcie;
import cn.com.glsx.supplychain.service.bs.LogisticsService;
import cn.com.glsx.supplychain.util.StringUtil;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.glsx.cloudframework.core.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @author xiexin
 * @version V1.0
 * @Title: AfterSaleOrderAdminRemote.java
 * @Description: 售后订单接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@Component("AfterSaleOrderAdminRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class AfterSaleOrderAdminRemoteImpl implements AfterSaleOrderAdminRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterSaleOrderAdminRemoteImpl.class);

    @Autowired
    private AfterSaleOrderServcie afterSaleOrderServcie;

    @Autowired
    private DeviceFileService deviceFileService;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private DataFormatProperty dataFormatProperty;

    @Autowired
    private AfterSaleOrderMapper afterSaleOrderMapper;

    @Autowired
    private AfterSaleOrderDetailMapper afterSaleOrderDetailMapper;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Override
    public RpcResponse<RpcPagination<AfterSaleOrder>> listAfterSaleOrder(RpcPagination<AfterSaleOrder> pagination) {
        RpcResponse<RpcPagination<AfterSaleOrder>> result;
        try {
            RpcAssert.assertNotNull(pagination, DefaultErrorEnum.PARAMETER_NULL, "pagination must not be null");
            RpcAssert.assertNotNull(pagination.getPageNum(), DefaultErrorEnum.PARAMETER_NULL, "pageNum must not be null");
            RpcAssert.assertNotNull(pagination.getPageSize(), DefaultErrorEnum.PARAMETER_NULL, "pageSize must not be null");
            result = RpcResponse.success(RpcPagination.copyPage(afterSaleOrderServcie.listAfterSaleOrder(pagination.getPageNum(),
                    pagination.getPageSize(),
                    pagination.getCondition())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<AfterSaleOrder> getAfterSaleOrderByOrderNumber(String orderNumber) {
        RpcResponse<AfterSaleOrder> result;
        try {
            RpcAssert.assertNotNull(orderNumber, DefaultErrorEnum.PARAMETER_NULL, "orderNumber must not be null");
            AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
            afterSaleOrder.setOrderNumber(orderNumber);
            Logistics logistics = new Logistics();
            logistics.setType(LogisticsTypeEnum.AFTER_SALE_ORDER.getCode());
            afterSaleOrder.setLogistics(logistics);
            result = RpcResponse.success(afterSaleOrderServcie.getAfterSaleOrder(afterSaleOrder));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<AfterSaleOrder> getAfterSaleOrder(AfterSaleOrder afterSaleOrder) {
        RpcResponse<AfterSaleOrder> result;
        try {
            RpcAssert.assertNotNull(afterSaleOrder, DefaultErrorEnum.PARAMETER_NULL, "orderNumber must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.getAfterSaleOrder(afterSaleOrder));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }


    @Override
    public RpcResponse<Integer> add(AfterSaleOrder afterSaleOrder) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(afterSaleOrder, DefaultErrorEnum.PARAMETER_NULL, "afterSaleOrder must not be null");
            RpcAssert.assertNotNull(afterSaleOrder.getAfterSaleOrderDetailList(), DefaultErrorEnum.PARAMETER_NULL, "AfterSaleOrderDetail must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.add(afterSaleOrder));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateByOrderNumber(AfterSaleOrder afterSaleOrder) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(afterSaleOrder, DefaultErrorEnum.PARAMETER_NULL, "orderNumber  not be null");
            result = RpcResponse.success(afterSaleOrderServcie.updateByOrderNumber(afterSaleOrder));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateAfterSaleOrderLogistics(Logistics logistics) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics not be null");
            result = RpcResponse.success(afterSaleOrderServcie.updateAfterSaleOrderLogistics(logistics));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> updateAfterSaleOrderLogisticsByServiceCodeAndType(Logistics logistics) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics not be null");
            result = RpcResponse.success(afterSaleOrderServcie.updateAfterSaleOrderLogisticsByServiceCodeAndType(logistics));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> addAfterSaleOrderLogistics(Logistics logistics) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics not be null");
            result = RpcResponse.success(afterSaleOrderServcie.addAfterSaleOrderLogistics(logistics));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Logistics> getLogistics(Logistics logistics) {
        RpcResponse<Logistics> result;
        try {
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics not be null");
            result = RpcResponse.success(logisticsService.getLogistics(logistics));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }


    @Override
    public RpcResponse<CheckImportDataVo> checkImportSnList(List<SnImport> snImportList) {
        CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
        AfterSaleOrderDetail afterSaleOrderDetail = new AfterSaleOrderDetail();
        try {
            if (StringUtil.isEmpty(snImportList) || snImportList.size() == 0) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (snImportList.size() > 2000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }
            //导入成功的数据返回
            List<SnImport> successList = new ArrayList<SnImport>();
            //导入失败的 数据
            List<SnExport> failList = new ArrayList<>();

            Map<String, Integer> mapsn = new HashMap<String, Integer>();
            for (SnImport item : snImportList) {
                if (!StringUtil.isEmpty(item.getSn())) {
                    Integer count = mapsn.get(item.getSn());
                    if (count == null || count == 0) {
                        mapsn.put(item.getSn(), 1);
                    } else {
                        count++;
                        mapsn.put(item.getSn(), count);
                    }
                }
            }
            for (SnImport item : snImportList) {
                LOGGER.info("AfterSaleOrderAdminRemoteImpl::checkImportSnList handle data item=" + item.toString());
                SnExport fail = new SnExport();
                boolean add = true;
                //验证Sn是否为空
                if (StringUtil.isEmpty(item.getSn())) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                //验证设备售后原因是否为空
                if(StringUtil.isEmpty(item.getDeviceAfterReason())){
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }
                //验证Sn表格中是否存在
                Integer count = mapsn.get(item.getSn());
                if (count >= 2) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证Sn是否是我们的设备
                OrderInfoDetail orderInfoDetail =  orderInfoDetailMapper.getOrderInfoDetailByImei(item.getSn());
                if (orderInfoDetail == null) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.NVALID_DEVICE_SN.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证sn在售后订单详情表是否重复
                List<AfterSaleOrder> list = afterSaleOrderMapper.selectAfterSaleOrderOngoingBySn(item.getSn());
                if (list != null && list.size() > 0) {
                    for (AfterSaleOrder afterSaleOrder : list) {
                        if (!StringUtil.isEmpty(afterSaleOrder)) {
                            //类型为退货
                            if (afterSaleOrder.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_RETURN.getCode()) {
                                if (afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_REJECT.getCode()
                                        && afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_COUNTERMAND.getCode()) {
                                    BeanUtils.copyProperties(fail, item);
                                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_SN_STATUS.getValue());
                                    failList.add(fail);
                                    add = false;
                                    continue;
                                }
                                //类型为返修
                            } else if (afterSaleOrder.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_REPAIR.getCode()) {
                                if (afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_REJECT.getCode()
                                        && afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_COUNTERMAND.getCode()) {
                                    BeanUtils.copyProperties(fail, item);
                                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_SN_TWO_STATUS.getValue());
                                    failList.add(fail);
                                    add = false;
                                    continue;
                                }
                            }
                        }
                    }
                }

                //验证Sn格式是否正确
                if (item.getSn().length() > dataFormatProperty.getMaxImeiLen()) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_COLUMN_LENGTH.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证sn(imei)是否重复
                DeviceFile deviceFileNew = null;
                try {
                    deviceFileNew = deviceFileService.getDeviceFileBySn(item.getSn());
                } catch (RpcServiceException e) {
                    LOGGER.error("AfterSaleOrderAdminRemoteImpl::checkImportSnList", e);
                    return RpcResponse.error(e.getError());
                }

/*                //设备存在且未被初始化
                if (!StringUtils.isEmpty(deviceFileNew) && "N".equals(deviceFileNew.getDeletedFlag())) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }*/
                LOGGER.info("AfterSaleOrderAdminRemoteImpl::checkImportSnList check repeated Sn in device_file ok!");

                if (add) {
                    successList.add(item);
                }
            }
            LOGGER.info("AfterSaleOrderAdminRemoteImpl::checkImportSnList: check ok !");
            checkImportDataVo.setSnImportList(successList);
            checkImportDataVo.setSninvalidList(failList);
            return RpcResponse.success(checkImportDataVo);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }


    @Override
    public RpcResponse<CheckImportDataVo> checkImportSnChangeList(List<SnChangeImport> snChangeImports) {
        CheckImportDataVo checkImportDataVo = new CheckImportDataVo();
        try {
            if (StringUtil.isEmpty(snChangeImports) || snChangeImports.size() == 0) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM);
            }
            if (snChangeImports.size() > 2000) {
                return RpcResponse.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE);
            }
            //导入成功的数据返回
            List<SnChangeImport> successList = new ArrayList<>();
            //导入失败的 数据
            List<SnChangeExport> failList = new ArrayList<>();

            Map<String, Integer> mapsn = new HashMap<String, Integer>();
            for (SnChangeImport item : snChangeImports) {
                if (!StringUtil.isEmpty(item.getSn())) {
                    Integer count = mapsn.get(item.getSn());
                    if (count == null || count == 0) {
                        mapsn.put(item.getSn(), 1);
                    } else {
                        count++;
                        mapsn.put(item.getSn(), count);
                    }
                }
            }
            for (SnChangeImport item : snChangeImports) {
                LOGGER.info("AfterSaleOrderAdminRemoteImpl::checkImportSnChangeList handle data item=" + item.toString());
                SnChangeExport fail = new SnChangeExport();
                boolean add = true;
                //验证Sn是否为空
                if (StringUtil.isEmpty(item.getSn())) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_DATA_FORMAT.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证Sn表格中是否存在
                Integer count = mapsn.get(item.getSn());
                if (count >= 2) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_REPEAT_IMEI_EXCEL.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证Sn是否是我们的设备
                OrderInfoDetail orderInfoDetail =  orderInfoDetailMapper.getOrderInfoDetailByImei(item.getSn());
                if (orderInfoDetail == null) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.NVALID_DEVICE_SN.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证SN是设备库存中的设备
                DeviceInfo deviceInfo = deviceInfoService.getDeviceInfoBySn(item.getSn());
                if (deviceInfo == null) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.NVALID_DEVICE_SN.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证sn在售后订单详情表是否重复
                List<AfterSaleOrder> list = afterSaleOrderMapper.selectMaintainProductOngoingBySn(item.getSn());
                if (list != null && list.size() > 0) {
                    for (AfterSaleOrder afterSaleOrder : list) {
                        if (!StringUtil.isEmpty(afterSaleOrder)) {
                            //类型为退货
                            if (afterSaleOrder.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_RETURN.getCode()) {
                                if (afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_REJECT.getCode()
                                        && afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_COUNTERMAND.getCode()) {
                                    BeanUtils.copyProperties(fail, item);
                                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_SN_STATUS.getValue());
                                    failList.add(fail);
                                    add = false;
                                    continue;
                                }
                                //类型为返修
                            } else if (afterSaleOrder.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_REPAIR.getCode()) {
                                if (afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_REJECT.getCode()
                                        && afterSaleOrder.getStatus() != AfterSaleOrderStatusEnum.AFTER_SALE_ALREADY_COUNTERMAND.getCode()) {
                                    BeanUtils.copyProperties(fail, item);
                                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_SN_TWO_STATUS.getValue());
                                    failList.add(fail);
                                    add = false;
                                    continue;
                                }
                            }
                        }
                    }
                }

                //验证Sn格式是否正确
                if (item.getSn().length() > dataFormatProperty.getMaxImeiLen()) {
                    BeanUtils.copyProperties(fail, item);
                    fail.setReason(ReasonInvalidDeviceNum.INVALID_DEVICE_COLUMN_LENGTH.getValue());
                    failList.add(fail);
                    add = false;
                    continue;
                }

                //验证sn(imei)是否重复
                DeviceFile deviceFileNew = null;
                try {
                    deviceFileNew = deviceFileService.getDeviceFileBySn(item.getSn());
                } catch (RpcServiceException e) {
                    LOGGER.error("AfterSaleOrderAdminRemoteImpl::checkImportSnChangeList", e);
                    return RpcResponse.error(e.getError());
                }
                LOGGER.info("AfterSaleOrderAdminRemoteImpl::checkImportSnChangeList check repeated Sn in device_file ok!");

                if (add) {
                    successList.add(item);
                }
            }
            LOGGER.info("AfterSaleOrderAdminRemoteImpl::checkImportSnChangeList: check ok !");
            checkImportDataVo.setSnChangeImports(successList);
            checkImportDataVo.setSnChangeExportList(failList);
            return RpcResponse.success(checkImportDataVo);
        } catch (RpcServiceException e) {
            return RpcResponse.error(e.getError(), e.getMessage());
        }
    }




    @Override
    public RpcResponse<Integer> AfterSaleOrderDispatchAddLogistics(Logistics logistics) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(logistics, DefaultErrorEnum.PARAMETER_NULL, "logistics must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.AfterSaleOrderDispatchAddLogistics(logistics));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<Integer> insertAfterSaleOrderDetailList(List<AfterSaleOrderDetail> afterSaleOrderDetailList) {
        RpcResponse<Integer> result;
        try {
            RpcAssert.assertNotNull(afterSaleOrderDetailList, DefaultErrorEnum.PARAMETER_NULL, "afterSaleOrderDetailList must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.insertAfterSaleOrderDetailList(afterSaleOrderDetailList));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<AfterSaleOrderDetail>> getAfterSalesSnList(AfterSaleOrderDetail afterSaleOrderDetail) {
        RpcResponse<List<AfterSaleOrderDetail>> result;
        try {
            RpcAssert.assertNotNull(afterSaleOrderDetail, DefaultErrorEnum.PARAMETER_NULL, "afterSaleOrderDetail must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.getAfterSalesSnList(afterSaleOrderDetail));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<List<MaintainSnChange>> getMainTainSnChangeList(MaintainProductDetail maintainProductDetail) {
        RpcResponse<List<MaintainSnChange>> result;
        try {
            RpcAssert.assertNotNull(maintainProductDetail, DefaultErrorEnum.PARAMETER_NULL, "afterSaleOrderDetail must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.getMainTainSnChangeList(maintainProductDetail));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<DeviceInfo> getDeviceInfoByImeiOrSn(String imei) {
        RpcResponse<DeviceInfo> result;
        try{
            RpcAssert.assertNotNull(imei, DefaultErrorEnum.PARAMETER_NULL, "imei must not be null");
            result = RpcResponse.success(afterSaleOrderServcie.getDeviceInfoByImeiOrSn(imei));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }

    @Override
    public RpcResponse<DeviceFile> getDeviceFileBySn(String sn) {
        RpcResponse<DeviceFile> result;
        try{
            RpcAssert.assertNotNull(sn, DefaultErrorEnum.PARAMETER_NULL, "imei must not be null");
            result = RpcResponse.success(deviceFileService.getDeviceFileBySn(sn));
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            result = RpcResponse.error(e);
        }
        return result;
    }
}
