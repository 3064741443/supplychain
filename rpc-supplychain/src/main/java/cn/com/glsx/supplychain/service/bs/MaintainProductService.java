package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.*;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import cn.com.glsx.supplychain.mapper.DeviceInfoMapper;
import cn.com.glsx.supplychain.mapper.OrderInfoDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.*;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.service.*;
import cn.com.glsx.supplychain.util.StringUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.glsx.oms.fcservice.api.core.FlowResponse;
import com.glsx.oms.fcservice.api.entity.Flowcard;
import com.glsx.oms.fcservice.api.request.FlowCardRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MaintainProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintainProductService.class);

    @Autowired
    private MaintainProductMapper maintainProductMapper;

    @Autowired
    private DeviceFileService deviceFileService;

    @Autowired
    private DeviceFileSnapshotService deviceFileSnapshotService;

    @Autowired
    private OrderInfoDetailMapper orderInfoDetailMapper;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private DeviceCardManagerService deviceCardManagerService;

    @Autowired
    private AfterSaleOrderMapper afterSaleOrderMapper;

    @Autowired
    private AfterSaleOrderDetailMapper afterSaleOrderDetailMapper;

    @Autowired
    private MaintainProductDetailMapper maintainProductDetailMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private DeviceFileVirtualService  deviceFileVirtualService;
    @Autowired
    private DeviceCardManagerService deviceCardService;
    @Autowired
    private SupplyChainExternalService supplyChainExternalService;

    /**
     * 分页查询维修产品库列表
     *
     * @param pageNum
     * @param pageSize
     * @param maintainProduct
     * @return
     */
    public Page<MaintainProduct> listMaintainProduct(int pageNum, int pageSize, MaintainProduct maintainProduct) {
        LOGGER.info("pageNum为：{}, pageSize为：{}, 条件查询参数为 ：{}。", pageNum, pageSize, maintainProduct);
        Page<MaintainProduct> result;
        PageHelper.startPage(pageNum, pageSize);
        result = maintainProductMapper.listMaintainProduct(maintainProduct);
        return result;
    }

    /**
     * 根据id查询维修产品信息
     * @param maintainProduct
     * @return
     */
    public MaintainProduct getMaintainProductInfo(MaintainProduct maintainProduct){
        LOGGER.info("查询维修产品库入参:{}", maintainProduct);
        MaintainProduct result;
        result = maintainProductMapper.selectById(maintainProduct.getId());
        //填充维修管理详情信息
        MaintainProductDetail maintainProductDetail = new MaintainProductDetail();
        maintainProductDetail.setAfterSaleOrderNumber(result.getAfterSaleOrderNumber());
        List<MaintainProductDetail>maintainProductDetails = maintainProductDetailMapper.getMainTainProductDetailList(maintainProductDetail);
        result.setMaintainProductDetailList(maintainProductDetails);
        return result;
    }

    /**
     * 新增维修单
     * @param maintainProduct
     * @return
     */
    public Integer add(MaintainProduct maintainProduct){
        LOGGER.info("新增的参数为：{}。", maintainProduct);
        Integer result;
        //查询售后订单的类型
        AfterSaleOrder aso = new AfterSaleOrder();
        aso.setOrderNumber(maintainProduct.getAfterSaleOrderNumber());
        AfterSaleOrder asoInfo = afterSaleOrderMapper.selectOne(aso);
        if(asoInfo.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_REPAIR.getCode()){
            maintainProduct.setStatus(MaintainProductStatusEnum.MAINTAIN_PRODUCT_STATUS_WAIT_REPAIR.getCode());
        }else{
            maintainProduct.setStatus(MaintainProductStatusEnum.MAINTAIN_PRODUCT_STATUS_WAIT_RETURN.getCode());
        }
        LOGGER.info("新增维修管理详情start。");
        AfterSaleOrderDetail afterSaleOrderDetail = new AfterSaleOrderDetail();
        afterSaleOrderDetail.setAfterSaleOrderNumber(maintainProduct.getAfterSaleOrderNumber());
        afterSaleOrderDetail.setStatus(AfterSaleOrderDetailStatusEnum.AFTER_SALE_ORDER_SIGN_SN.getCode());
        List<AfterSaleOrderDetail> afterSaleOrderDetailInfo = afterSaleOrderDetailMapper.select(afterSaleOrderDetail);
        if(afterSaleOrderDetailInfo != null && afterSaleOrderDetailInfo.size()>0){
            List<MaintainProductDetail>newMianProductDetailList = new ArrayList<>();
            for(AfterSaleOrderDetail list : afterSaleOrderDetailInfo){
                MaintainProductDetail maintainProductDetail = new MaintainProductDetail();
                maintainProductDetail.setAfterSaleOrderNumber(maintainProduct.getAfterSaleOrderNumber());

                DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(list.getSn());
                if(!StringUtil.isEmpty(deviceFile) && deviceFile.getDeletedFlag().equals("N"))
                {
                    Product product = new Product();
                    product.setCode(asoInfo.getProductCode());
                    Product productInfo = productMapper.getProduct(product);
                    LOGGER.info("根据查询售后订单产品类型是否为GPS：type:" + productInfo.getType());

                    String strIccid = null;
                    String strSn    = null;
                    String strImei  = null;

                    LOGGER.info("根据查询售后订单产品类型是否为GPS：type:" + productInfo.getType());
                    //GPS设备都为联网设备,显示ICCID与SN
                    DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(list.getSn());
                    if(!StringUtil.isEmpty(deviceFileSnapshot) && deviceFileSnapshot.getDeletedFlag().equals("N"))
                    {
                        DeviceCardManager deviceCardManager =  deviceCardManagerService.getDeviceCardById(deviceFileSnapshot.getCardId());
                        if(!StringUtil.isEmpty(deviceCardManager))
                        {
                            strIccid = deviceCardManager.getIccid();
                        }
                        maintainProductDetail.setPackageUserTime(deviceFileSnapshot.getPackageUserTime());
                    }

                    strSn       = deviceFile.getSn();
                    strImei     = deviceFile.getSn();

                    DeviceInfo oldDeviceInfo = deviceInfoMapper.getDeviceInfoBySn(list.getSn());
                    if(!StringUtil.isEmpty(oldDeviceInfo))
                    {
                        if(!StringUtils.isEmpty(oldDeviceInfo.getImei()))
                        {
                            strImei = oldDeviceInfo.getImei();
                        }
                    }


                    DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(list.getSn());
                    if(!StringUtil.isEmpty(deviceInfo) && deviceInfo.getDeletedFlag().equals("N"))
                    {
                        maintainProductDetail.setWarehouseId(deviceInfo.getWareHouseId());
                    }
                    String type = "5";
                    if(productInfo.getType().equals(type)){
                        maintainProductDetail.setIccid(strIccid);
                      //  maintainProductDetail.setImei(strImei);
                        maintainProductDetail.setSn(strSn);
                    }else{
                        if(!StringUtil.isEmpty(deviceInfo) && deviceInfo.getIccid().equals("")){//不带卡有SN
                            maintainProductDetail.setSn(strSn);
                        }else{//带卡有ICCID和IMEI
                            maintainProductDetail.setIccid(strIccid);
                            maintainProductDetail.setImei(strImei);
                        }
                    }

                }
                

                //根据sn查询设备库存信息
               /* DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(list.getSn());
                if(!StringUtil.isEmpty(deviceInfo)){
                    Product product = new Product();
                    product.setCode(asoInfo.getProductCode());
                    Product productInfo = productMapper.getProduct(product);
                    LOGGER.info("根据查询售后订单产品类型是否为GPS：type:", productInfo.getType());
                    //GPS设备都为联网设备,显示ICCID与SN
                    String type = "5";
                    if(productInfo.getType().equals(type)){
                        maintainProductDetail.setIccid(deviceInfo.getIccid());
                        maintainProductDetail.setSn(deviceInfo.getSn());
                    }else{
                        if(deviceInfo.getIccid().equals("")){//不带卡有SN
                            maintainProductDetail.setSn(deviceInfo.getSn());
                        }else{//带卡有ICCID和IMEI
                            maintainProductDetail.setIccid(deviceInfo.getIccid());
                            maintainProductDetail.setImei(deviceInfo.getImei());
                        }
                    }
                    maintainProductDetail.setWarehouseId(deviceInfo.getWareHouseId());
            }*/
                //根据sn查询关系明细表
               /* DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(list.getSn());
                if (deviceFileSnapshot != null){
                    maintainProductDetail.setPackageUserTime(deviceFileSnapshot.getPackageUserTime());
                }*/
                //根据售后订单状态给维修详情信息填充状态
                AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
                afterSaleOrder.setOrderNumber(maintainProduct.getAfterSaleOrderNumber());
                AfterSaleOrder afterSaleOrderInfo = afterSaleOrderMapper.selectOne(afterSaleOrder);
                if(afterSaleOrderInfo.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_RETURN.getCode()){
                    maintainProductDetail.setStatus(MaintainProductDetailStatusEnum.MAINTAIN_PRODUCT_STATUS_WAIT_RETURN.getCode());
                }else{
                    maintainProductDetail.setStatus(MaintainProductDetailStatusEnum.MAINTAIN_PRODUCT_STATUS_WAIT_REPAIR.getCode());
                }
                maintainProductDetail.setDispatchFlag("N");
                maintainProductDetail.setCreatedBy(asoInfo.getCreatedBy());
                maintainProductDetail.setUpdatedBy(asoInfo.getUpdatedBy());
                maintainProductDetail.setUpdatedDate(new Date());
                maintainProductDetail.setCreatedDate(new Date());
                maintainProductDetail.setDeletedFlag("N");
                newMianProductDetailList.add(maintainProductDetail);
            }
            LOGGER.info("新增维修管理详情：{}。", newMianProductDetailList);
            maintainProductDetailMapper.insertList(newMianProductDetailList);
        }
        maintainProduct.setCreatedBy(asoInfo.getCreatedBy());
        maintainProduct.setCreatedDate(new Date());
        maintainProduct.setUpdatedBy(asoInfo.getUpdatedBy());
        maintainProduct.setUpdatedDate(new Date());
        maintainProduct.setDeletedFlag("N");
        result = maintainProductMapper.insertSelective(maintainProduct);
        return result;
    }

    /**
     * 修改维修单
     * @param maintainProduct
     * @return
     */
    public Integer updateById(MaintainProduct maintainProduct) {
        LOGGER.info("修改的参数为：{}。", maintainProduct);
        Integer result;
        result = maintainProductMapper.updateByPrimaryKeySelective(maintainProduct);
        LOGGER.info("查询是否还有待维修或待退货的设备：{}。");
        MaintainProductDetail maintainProductDetail = new MaintainProductDetail();
        maintainProductDetail.setAfterSaleOrderNumber(maintainProduct.getAfterSaleOrderNumber());
        List<MaintainProductDetail> maintainProductDetailList = maintainProductDetailMapper.getMainTainProductDetailList(maintainProductDetail);
        if (maintainProductDetailList != null && maintainProductDetailList.size() > 0) {
            Integer repairFlag = 0;
            Integer returnFlag = 0;
            for (MaintainProductDetail list : maintainProductDetailList) {
                //为维修单,并且当维修详情里面还有设备为待维修状态的
                if (maintainProduct.getType() == 2) {
                    if (list.getStatus() == MaintainProductDetailStatusEnum.MAINTAIN_PRODUCT_STATUS_WAIT_REPAIR.getCode()) {
                        repairFlag++;
                    }
                }
                //为退货单,并且当维修详情里面还有设备为待退货状态的
                if (maintainProduct.getType() == 1) {
                    if (list.getStatus() == MaintainProductDetailStatusEnum.MAINTAIN_PRODUCT_STATUS_WAIT_RETURN.getCode()) {
                        returnFlag++;
                    }
                }
            }

            //当两种类型单都没有待维修和待退货(状态为已完成)
            LOGGER.info("两种类型单都没有待维修和待退货======状态变更为已完成");
            //维修类型
            if(maintainProduct.getType() == 2){
                //维修单还有待维修的(状态为维修中)
                if (repairFlag == 0) {
                    maintainProduct.setStatus(MaintainProductStatusEnum.MAINTAIN_PRODUCT_STATUS_ALREADY_FINISH.getCode());
                    maintainProductMapper.updateByPrimaryKeySelective(maintainProduct);
                }else{
                    maintainProduct.setStatus(MaintainProductStatusEnum.MAINTAIN_PRODUCT_STATUS_REPAIR_IN.getCode());
                    maintainProductMapper.updateByPrimaryKeySelective(maintainProduct);
                }
            }
            //退货类型
            if(maintainProduct.getType() == 1){
                if (returnFlag == 0) {
                    maintainProduct.setStatus(MaintainProductStatusEnum.MAINTAIN_PRODUCT_STATUS_ALREADY_FINISH.getCode());
                    maintainProductMapper.updateByPrimaryKeySelective(maintainProduct);
                }
            }
        }
        return result;
    }

    /**
     * 校验ICCID是否是GLSX的卡
     * @param deviceInfo
     * @return
     */
    public DeviceInfo checkIccid(DeviceInfo deviceInfo){
        //判断iccid是否重复
        if(!StringUtils.isEmpty(deviceInfo.getIccid()))
        {
            /*DeviceInfo tempDeviceInfo = deviceInfoService.getDeviceInfoByIccid(deviceInfo.getIccid());
            if(!StringUtils.isEmpty(tempDeviceInfo))
            {
                String message = "iccid重复:\n 仓库库存记录:sn:" + tempDeviceInfo.getSn() +
                        " iccid:" + tempDeviceInfo.getIccid() + " imei:" + tempDeviceInfo.getImei() + "\n 当前入库记录:sn:" +
                        deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
                LOGGER.error("SupplyChainRemoteService::scannerDeviceInfo iccid重复:" + message);
                ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
            }*/

            /*DeviceFileVirtual deviceFileVirtual = deviceFileVirtualService.getDeviceFileVirtualByIccid(deviceInfo.getIccid());
            if(!StringUtils.isEmpty(deviceFileVirtual))
            {
                String message = "iccid重复:\n 虚拟放卡库存记录:iccid:" + deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
                LOGGER.error("SupplyChainRemoteService::scannerDeviceInfo iccid重复:" + message);
                ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
                throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
            }*/

            //检查iccid是否是广联卡 71234567:调试数据不需要验证
            if(!deviceInfo.getIccid().startsWith("71234567"))
            {
                String imsi = this.getImsiByIccidFromFlowCardPlat(deviceInfo.getIccid().toUpperCase());
                if(StringUtils.isEmpty(imsi))
                {
                    LOGGER.error("SupplyChainRemoteService::scannerDeviceInfo iccid:is not glsx card");
                    throw new RpcServiceException(ErrorCodeEnum.ERRCODE_ICCID_IS_NOT_GLSX);
                }

                /*DeviceCardManager record = new DeviceCardManager();
                record.setCompanyId(1);
                record.setImsi(imsi);
                DeviceCardManager deviceCardManager = deviceCardService.getDeviceCardByUniqueKey(record);
                if(!StringUtils.isEmpty(deviceCardManager))
                {
                    DeviceFileSnapshot deviceSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(deviceCardManager.getId());
                    if(!StringUtils.isEmpty(deviceSnapshot) && deviceSnapshot.getDeletedFlag().equals("N"))
                    {
                        String message = "iccid重复:\n 设备关系库存记录:iccid:" + deviceCardManager.getIccid() + " sn:" + deviceSnapshot.getSn() + "\n 当前入库记录:sn:" +
                                deviceInfo.getSn() + " iccid:" + deviceInfo.getIccid() + " imei:" + deviceInfo.getImei();
                        LOGGER.error("SupplyChainRemoteService::scannerDeviceInfo iccid重复:" + message);
                        ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE.setDescrible(message);
                        throw new RpcServiceException(ErrorCodeEnum.ERRCODE_REPEATED_REOCRD_SELF_MESSAGE);
                    }
                }*/
            }
        }
        return deviceInfo;
    }

    private String getImsiByIccidFromFlowCardPlat(String iccid) throws RpcServiceException
    {
        String imsi = null;
        try
        {
            FlowCardRequest flowCardRequest = new FlowCardRequest();
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            flowCardRequest.setTime(time);  //时间
            flowCardRequest.setVersion("2.2.0"); //版本号
            flowCardRequest.setConsumer("task-supplychain"); //项目名称        
            flowCardRequest.setKeyWord(iccid.toUpperCase());
            flowCardRequest.setInvoker("glsx");
            FlowResponse<Flowcard> flowCardFlowResponse = supplyChainExternalService.getFlowCardByIccidOrImsi(flowCardRequest);
            if(!StringUtils.isEmpty(flowCardFlowResponse.getEntiy()))
            {
                imsi = flowCardFlowResponse.getEntiy().getImsi();
            }
        }
        catch(RpcServiceException e)
        {
            throw e;
        }
        return imsi;
    }

}
