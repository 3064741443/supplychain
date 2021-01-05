package cn.com.glsx.supplychain.service.bs;

import cn.com.glsx.supplychain.enums.AfterSaleOrderTypeEnum;
import cn.com.glsx.supplychain.enums.DeviceEnum;
import cn.com.glsx.supplychain.enums.MaintainSnChangeFlagEnum;
import cn.com.glsx.supplychain.enums.OrderStatusEnum;
import cn.com.glsx.supplychain.mapper.*;
import cn.com.glsx.supplychain.mapper.bs.*;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.AfterSaleOrder;
import cn.com.glsx.supplychain.model.bs.MaintainProductDetail;
import cn.com.glsx.supplychain.service.DeviceCodeService;
import cn.com.glsx.supplychain.service.DeviceFileSnapshotService;
import cn.com.glsx.supplychain.service.DeviceManagerAdminRemoteService;
import cn.com.glsx.supplychain.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MaintainProductDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintainProductDetailService.class);

    @Autowired
    private MaintainProductMapper maintainProductMapper;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private DeviceFileSnapshotService deviceFileSnapshotService;

    @Autowired
    private AfterSaleOrderMapper afterSaleOrderMapper;

    @Autowired
    private DeviceResetRecordMapper deviceResetRecordMapper;

    @Autowired
    private AfterSaleOrderDetailMapper afterSaleOrderDetailMapper;

    @Autowired
    private MaintainProductDetailMapper maintainProductDetailMapper;

    @Autowired
    private MaintainSnChangeMapper maintainSnChangeMapper;

    @Autowired
    private DeviceManagerAdminRemoteService deviceManagerAdminRemoteService;

    @Autowired
    private DeviceFileMapper deviceFileMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private DeviceCodeMapper deviceCodeMapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * 根据id查询维修产品详情信息
     * @param maintainProductDetail
     * @return
     */
    public MaintainProductDetail getMaintainProductDetailInfo(MaintainProductDetail maintainProductDetail){
        LOGGER.info("查询维修产品详情入参:{}", maintainProductDetail);
        MaintainProductDetail result;
        result = maintainProductDetailMapper.getMaintainProductDetailInfo(maintainProductDetail);
        return result;
    }

    /**
     * 新增维修详情信息
     * @param maintainProductDetail
     * @return
     */
    public Integer add(MaintainProductDetail maintainProductDetail){
        LOGGER.info("新增维修详情信息的参数为：{}。", maintainProductDetail);
        Integer result;
        maintainProductDetail.setDispatchFlag("N");
        result = maintainProductDetailMapper.insertSelective(maintainProductDetail);
        return result;
    }

    /**
     * 修改售后管理(根据传过来的SN信息进行入库操作)
     * @param maintainProductDetail
     * @return
     */
    public Integer updateByMaintainProductDetail(MaintainProductDetail maintainProductDetail){
        LOGGER.info("修改售后管理详情的参数为：{}。", maintainProductDetail);
        Integer result;
        if(!StringUtil.isEmpty(maintainProductDetail.getMaintainSnChange())){
            //数据填充到售后SN切换记录表
            maintainProductDetail.getMaintainSnChange().setCreatedBy("admin");
            maintainProductDetail.getMaintainSnChange().setCreatedDate(new Date());
            maintainProductDetail.getMaintainSnChange().setUpdatedBy("admin");
            maintainProductDetail.getMaintainSnChange().setUpdatedDate(new Date());
            maintainProductDetail.getMaintainSnChange().setDeletedFlag("N");
            maintainSnChangeMapper.insertSelective(maintainProductDetail.getMaintainSnChange());
        }
        maintainProductDetail.setDispatchFlag("N");
        maintainProductDetail.setUpdatedDate(new Date());
        result = maintainProductDetailMapper.updateByPrimaryKeySelective(maintainProductDetail);

        //退货直接进行入库
        if(maintainProductDetail.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_RETURN.getCode()) {
            AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
            afterSaleOrder.setOrderNumber(maintainProductDetail.getAfterSaleOrderNumber());
            AfterSaleOrder afterSaleOrderInfo = afterSaleOrderMapper.selectOne(afterSaleOrder);

            // DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(maintainProductDetail.getMaintainSnChange().getSn() == null ? maintainProductDetail.getMaintainSnChange().getImei() : maintainProductDetail.getMaintainSnChange().getSn());
            //DeviceInfo oldDeviceInfo = deviceInfoMapper.getDeviceInfoBySnAndFlagIsY(maintainProductDetail.getMaintainSnChange().getSn(), maintainProductDetail.getMaintainSnChange().getImei());//初始化之后的设备库存信息
           // if (!StringUtil.isEmpty(deviceInfo)) {
                //根据id找到对应的售后详情

                MaintainProductDetail maintainProductDetailInfo = maintainProductDetailMapper.selectById(maintainProductDetail.getId());
                String strSn = StringUtils.isEmpty(maintainProductDetailInfo.getSn()) ? maintainProductDetailInfo.getImei():maintainProductDetailInfo.getSn();
                DeviceFile deviceFile = deviceFileMapper.selectBySn(strSn);
                DeviceCode deviceCode = deviceCodeMapper.selectByPrimaryKey(deviceFile.getDeviceCode());
                DeviceInfo deviceInfo = deviceInfoMapper.getDeviceInfoBySn(strSn);
                String strAttribCode = "";
                if(StringUtil.isEmpty(deviceInfo) || deviceInfo.getDeletedFlag().equals("Y"))
                {
                    strAttribCode = "unknown" + deviceCode.getTypeId();
                }
                else
                {
                    strAttribCode = deviceInfo.getAttribCode();
                }
                if(StringUtil.isEmpty(deviceInfo))
                {
                    deviceInfo = new DeviceInfo();
                }

                if (!StringUtil.isEmpty(maintainProductDetailInfo))
                {

                    if(!StringUtil.isEmpty(maintainProductDetail.getMaintainSnChange().getIccid()))
                    {
                        deviceInfo.setIccid(maintainProductDetail.getMaintainSnChange().getIccid());
                    }
                    else
                    {
                        if(!StringUtil.isEmpty(deviceInfo)&&deviceInfo.getDeletedFlag().equals("N"))
                        {
                            deviceInfo.setIccid(deviceInfo.getIccid());
                        }
                    }

                    if(!StringUtils.isEmpty(maintainProductDetail.getMaintainSnChange().getImei()))
                    {
                        deviceInfo.setImei(maintainProductDetail.getMaintainSnChange().getImei());
                    }
                    else
                    {
                        if(!StringUtil.isEmpty(deviceInfo)&&deviceInfo.getDeletedFlag().equals("N"))
                        {
                            deviceInfo.setImei(deviceInfo.getImei());
                        }
                    }

                  //  deviceInfo.setIccid(maintainProductDetail.getMaintainSnChange().getIccid());
                    deviceInfo.setSn(StringUtils.isEmpty(maintainProductDetail.getMaintainSnChange().getSn())? maintainProductDetail.getMaintainSnChange().getImei():maintainProductDetail.getMaintainSnChange().getSn());
                   // deviceInfo.setImei(maintainProductDetail.getMaintainSnChange().getImei());
                    deviceInfo.setAttribCode(strAttribCode);
                    deviceInfo.setBatch("123");
                    deviceInfo.setStatus(DeviceEnum.STATUS_IN.getValue());
                    deviceInfo.setWareHouseId(6);
                    deviceInfo.setWareHouseIdUp(6);
                    deviceInfo.setOrderCode("");
                    deviceInfo.setCreatedBy(afterSaleOrderInfo.getCreatedBy());
                    deviceInfo.setCreatedDate(new Date());
                    deviceInfo.setUpdatedBy(afterSaleOrderInfo.getUpdatedBy());
                    deviceInfo.setUpdatedDate(new Date());
                    deviceInfo.setDeletedFlag("N");

                    DeviceResetRecord deviceResetRecord = new DeviceResetRecord();
                    deviceResetRecord.setSn(strSn);

                    deviceResetRecord = deviceManagerAdminRemoteService.initDeviceFileByDeviceResetRecord(deviceResetRecord);
                    LOGGER.info("初始化当前sn的设备库存信息end: sn:", maintainProductDetail.getMaintainSnChange().getSn() == null ? maintainProductDetail.getMaintainSnChange().getImei() : maintainProductDetail.getMaintainSnChange().getSn());
                    if (deviceResetRecord != null && deviceResetRecord.getSn() != null) {
                        LOGGER.info("kafka发送初始化消息的sn：" + deviceResetRecord.getSn());
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("sn", deviceResetRecord.getSn());
                        kafkaProducer.sendObject(JSONObject.toJSONString(params).getBytes());
                        LOGGER.info("kafka发送初始化消息结束的sn：" + deviceResetRecord.getSn());
                    } else {
                        LOGGER.error("kafka发送初始化消息发送失败对象：{}", deviceResetRecord);
                    }
                    deviceInfoMapper.addDeviceInfoOnDuplicateKey(deviceInfo);
                }



               // LOGGER.info("切换之前的SN:", maintainProductDetailInfo.getSn());
             /*   if (!StringUtil.isEmpty(maintainProductDetailInfo))
                {
                    //添加库存信息
                    DeviceInfo device = new DeviceInfo();
                    device.setIccid(maintainProductDetail.getMaintainSnChange().getIccid());
                    if (maintainProductDetail.getMaintainSnChange().getImei() != null) {
                        device.setImei(maintainProductDetail.getMaintainSnChange().getImei());
                        device.setSn(maintainProductDetail.getMaintainSnChange().getImei());
                    }
                    if (maintainProductDetail.getMaintainSnChange().getSn() != null) {
                        device.setImei(maintainProductDetail.getMaintainSnChange().getSn());
                        device.setSn(maintainProductDetail.getMaintainSnChange().getSn());
                    }
                                       device.setAttribCode(strAttribCode);
                    device.setBatch("123");
                    device.setStatus(DeviceEnum.STATUS_IN.getValue());
                    device.setWareHouseId(6);
                    device.setWareHouseIdUp(6);
                    //device.setOrderCode(dispatchOrderNumber);
                    device.setCreatedBy(afterSaleOrderInfo.getCreatedBy());
                    device.setCreatedDate(new Date());
                    device.setUpdatedBy(afterSaleOrderInfo.getUpdatedBy());
                    device.setUpdatedDate(new Date());
                    device.setDeletedFlag("N");

                    DeviceResetRecord deviceResetRecord = new DeviceResetRecord();
                    deviceResetRecord.setSn(strSn);
                    deviceManagerAdminRemoteService.initDeviceFileByDeviceResetRecord(deviceResetRecord);
                    LOGGER.info("初始化当前sn的设备库存信息end: sn:", maintainProductDetail.getMaintainSnChange().getSn() == null ? maintainProductDetail.getMaintainSnChange().getImei() : maintainProductDetail.getMaintainSnChange().getSn());

                    deviceInfoMapper.addDeviceInfoOnDuplicateKey(device);
                }*/
            }
     //   }
        return result;
    }

    /**
     * 批量修改维修单详情
     * @param maintainProductDetailList
     * @return
     */
    public Integer batchUpdateMaintainProductDetail(List<MaintainProductDetail> maintainProductDetailList){
        LOGGER.info("批量修改维修管理详情的参数为：{}。", maintainProductDetailList);
        Integer result = 0;
        if(maintainProductDetailList != null && maintainProductDetailList.size() >0){
            for(MaintainProductDetail list : maintainProductDetailList){
                list.setUpdatedDate(new Date());
                list.setDispatchFlag("Y");
                result = maintainProductDetailMapper.updateByPrimaryKeySelective(list);
            }
        }
        return result;
    }


}
