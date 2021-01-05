package cn.com.glsx.supplychain.manager;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.UpdateRecordEnum;
import cn.com.glsx.supplychain.enums.UserFlagTypeEnum;
import cn.com.glsx.supplychain.manager.base.KafkaPostConsumer;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.service.*;
import cn.com.glsx.supplychain.util.RequestVerifyService;
import cn.com.glsx.supplychain.util.StringUtil;
import net.sf.json.JSONObject;

import org.oreframework.jms.kafka.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Component
public class SupplyChainKafkaService extends KafkaPostConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DeviceCardManagerService deviceCardManagerService;

    @Autowired
    private DeviceUserManagerService deviceUserManagerService;

    @Autowired
    private DeviceFileSnapshotService deviceFileSnapshotService;

    @Autowired
    private DeviceFileService deviceFileService;

    @Autowired
    private DeviceCodeService deviceCodeService;

    @Autowired
    private DeviceManagerServiceService deviceManagerService;

    @Autowired
    private RequestVerifyService requestVerifyService;

    @Autowired
    private DeviceUpdateRecordService deviceUpdateRecordService;

    @Autowired
    private DeviceManagerServiceService deviceManagerServiceService;
    
    @Autowired
    private WrongReportImeiService wrongReportImeiService;
    

    public SupplyChainKafkaService() {
        super("active_protocol_change");
    }

    @Override
    public void processMessage(Object obj) {

        logger.info("kafka接受的消息对象：{}", new String(getMessage()));

        String kafkaDto = new String(getMessage());

        JSONObject jasonObject = JSONObject.fromObject(kafkaDto);

        Map map = jasonObject;

        if (map == null) {
            logger.info("kafka接受的消息对象为空。");
            return;
        }

        logger.info("kafka消息转换后的Map:{}", map);

        String companyId = null;
        String flagType = null;
        String strMobile = null;
        String strImsi = null;
        if (!StringUtils.isEmpty(map.get("companyId"))) {
            companyId = map.get("companyId") + "";
        }
        if (!StringUtils.isEmpty(map.get("flagType"))) {
            flagType = map.get("flagType") + "";
        }
        if (!StringUtils.isEmpty(map.get("mobile"))) {
            strMobile = (String) map.get("mobile");
        }
        if(!StringUtils.isEmpty(map.get("imsi")))
        {
        	strImsi = (String) map.get("imsi");
        }
        String strDeviceSn = (String) map.get("imei");
        String strBangtime = (String) map.get("activeTime");
        String strConsumer = (String) map.get("consumer");
        String strVersion = (String) map.get("version");
        Integer deviceType = null;


        logger.info("SupplyChainKagkaService::processMessage param companyId=" + (StringUtils.isEmpty(companyId) ? "" : companyId) +
                " flagType=" + (StringUtils.isEmpty(flagType) ? "" : flagType) + " strDeviceSn=" + (StringUtils.isEmpty(strDeviceSn) ? "" : strDeviceSn) +
                " strImsi=" + (StringUtils.isEmpty(strImsi) ? "" : strImsi) + " strUserFlag=" + (StringUtils.isEmpty(strMobile) ? "" : strMobile) +
                " strConsumer=" + (StringUtils.isEmpty(strConsumer) ? "" : strConsumer) + " strVersion=" + (StringUtils.isEmpty(strVersion) ? "" : strVersion));

        if (StringUtils.isEmpty(strDeviceSn)) {
            logger.error("SupplyChainKagkaService::processMessage error param  empty sn!");
            return;
        }
        
        //老板wince 上报的imei都是同一个  这里做过滤不做状态更新
        WrongReportImei wrongImei = wrongReportImeiService.getWrongReportImeiBySn(strDeviceSn);
        if(!StringUtils.isEmpty(wrongImei))
        {
        	logger.error("SupplyChainKagkaService::processMessage error param  wrong sn !");
            return;
        }
        
        SupplyRequest request = new SupplyRequest();
        request.setConsumer(strConsumer);
        request.setVersion(strVersion);

        if (!requestVerifyService.verifyRequest(request)) {
            logger.error("SupplyChainKagkaService::processMessage not authorized message !");
            return;
        }

        Date dateBangTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBangTime = sdf.parse(strBangtime);
        } catch (ParseException e1) {
            logger.error("SupplyChainKagkaService::processMessage err=" + e1.getMessage());
        }

        try {
            DeviceCardManager conditionCard = new DeviceCardManager();
            conditionCard.setCompanyId(StringUtils.isEmpty(companyId) ? 1 : Integer.valueOf(companyId));
            conditionCard.setImsi(strImsi);
            conditionCard.setCreatedBy(strConsumer);
            conditionCard.setCreatedDate(dateBangTime);
            conditionCard.setUpdatedBy(strConsumer);
            conditionCard.setUpdatedDate(dateBangTime);
            logger.info("deviceCardManagerService::conditionCard:{}", conditionCard);
            if(!StringUtils.isEmpty(strImsi))
            {
            	conditionCard = deviceCardManagerService.addDeviceCard(conditionCard);
            }

            DeviceUserManager conditionUser = new DeviceUserManager();
            conditionUser.setCompanyId(StringUtils.isEmpty(companyId) ? 1 : Integer.valueOf(companyId));
            conditionUser.setUserFlag(strMobile);
            conditionUser.setFlagType(UserFlagTypeEnum.USERTYPE_PH.getValue());
            conditionUser.setCreatedBy(strConsumer);
            conditionUser.setCreatedDate(dateBangTime);
            conditionUser.setUpdatedBy(strConsumer);
            conditionUser.setUpdatedDate(dateBangTime);
            logger.info("deviceUserManagerService::conditionUser:{}", conditionUser);
            if(!StringUtils.isEmpty(strMobile))
            {
            	conditionUser = deviceUserManagerService.addDeviceUser(conditionUser);
            }

            DeviceFileSnapshot deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotBySn(strDeviceSn);
            if (StringUtils.isEmpty(deviceFileSnapshot)) {
                logger.info("SupplyChainKagkaService::processMessage can not find device snapshot by sn:" + strDeviceSn);
                deviceFileSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(conditionCard.getId());
                if(StringUtils.isEmpty(deviceFileSnapshot))
                {
                	logger.error("SupplyChainKagkaService::processMessage can not find device snapshot by cardid:" + conditionCard.getId() + " imsi:" + conditionCard.getImsi());
                	return;
                }
                //在数据同步对激活过的设备 无法判定sn情况下 给定虚拟sn 这里通过sn是查不到的 需要根据卡信息反补一条实际的设备信息 这种情况不考虑记录修改
                if(!deviceFileSnapshot.getSn().equals(strDeviceSn))
                {
                	deviceManagerServiceService.handleDeniesDeviceInfos(deviceFileSnapshot, strDeviceSn);
                }
                return;
            }
            DeviceFile deviceFile = deviceFileService.getDeviceFileBySn(strDeviceSn);
            if(StringUtils.isEmpty(deviceFile))
            {
            	logger.error("SupplyChainKagkaService::processMessage can not find device deviceFile!");
                return;
            }
            if(!StringUtils.isEmpty(deviceFile.getDeviceCode()))
            {
            	DeviceCode deviceCode = deviceCodeService.getDeviceCodeByDeviceCode(deviceFile.getDeviceCode());
            	if(!StringUtils.isEmpty(deviceCode))
            	{
            		deviceType = deviceCode.getTypeId();
            	}
            }
            logger.info("deviceCardManagerService::deviceFileSnapshot:{}", deviceFileSnapshot);

            boolean bNeedUpdate = false;
            DeviceFileSnapshot snapshot = new DeviceFileSnapshot();
            snapshot.setId(deviceFileSnapshot.getId());
            snapshot.setSn(strDeviceSn);
            snapshot.setAndroidPackageId(deviceFileSnapshot.getAndroidPackageId());
            snapshot.setCardId(deviceFileSnapshot.getCardId());
            snapshot.setCardTime(deviceFileSnapshot.getCardTime());
            snapshot.setCreatedBy(deviceFileSnapshot.getCreatedBy());
            snapshot.setCreatedDate(deviceFileSnapshot.getCreatedDate());
            snapshot.setDeletedFlag(deviceFileSnapshot.getDeletedFlag());
            snapshot.setFirmwareId(deviceFileSnapshot.getFirmwareId());
            snapshot.setPackageId(deviceFileSnapshot.getPackageId());
            snapshot.setPackageStatu(deviceFileSnapshot.getPackageStatu());
            snapshot.setPackageUserId(deviceFileSnapshot.getPackageUserId());
            snapshot.setPackageUserTime(deviceFileSnapshot.getPackageUserTime());
            snapshot.setSn(deviceFileSnapshot.getSn());
            snapshot.setSoftVersion(deviceFileSnapshot.getSoftVersion());
            snapshot.setUpdatedBy(request.getConsumer());
            snapshot.setUpdatedDate(dateBangTime);
            snapshot.setUserId(deviceFileSnapshot.getUserId());
            snapshot.setUserTime(deviceFileSnapshot.getUserTime());
            snapshot.setVehicleId(deviceFileSnapshot.getVehicleId());

            //处理卡绑定关系
            if(StringUtils.isEmpty(strImsi) && !StringUtils.isEmpty(deviceFileSnapshot.getCardId()))
            {
            	//如果业务侧验证imsi 侧需要修改卡绑定关系 如果不需要 则不需要处理绑定关系 说实话这个不应该在这里去判断，应该由业务侧决定
            	if(StringUtil.isCheckImsiDeviceType(deviceType))
            	{
            		bNeedUpdate = true;
                	snapshot.setCardId(null);
                    snapshot.setCardTime(null);
            	}
            }
            else if (!conditionCard.getId().equals(deviceFileSnapshot.getCardId()))
            {
                bNeedUpdate = true;
                snapshot.setCardId(conditionCard.getId());
                snapshot.setCardTime(strBangtime);
                deviceUpdateRecordService.setDeviceUpdateRecord(strDeviceSn, UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), conditionCard.getId(), strConsumer);

                //查询这张卡是否被其他设备占用 如果有解除绑定
                DeviceFileSnapshot otherSnapshot = deviceFileSnapshotService.getDeviceFileSnapshotByCardId(conditionCard.getId());
                if (!StringUtils.isEmpty(otherSnapshot) && !otherSnapshot.getSn().equals(strDeviceSn)) {
                    logger.info("deviceCardManagerService::otherSnapshot:{}", otherSnapshot);
                    deviceUpdateRecordService.setDeviceUpdateRecord(otherSnapshot.getSn(), UpdateRecordEnum.UPDATE_RECORD_CARD.getValue(), otherSnapshot.getCardId(), strConsumer);
                    otherSnapshot.setCardId(null);
                    otherSnapshot.setCardTime(null);
                    deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(otherSnapshot);
                }
            }

            //处理用户绑定关系
            if(StringUtils.isEmpty(strMobile) && !StringUtils.isEmpty(deviceFileSnapshot.getUserId()))
            {
            	bNeedUpdate = true;
                snapshot.setUserId(null);
                snapshot.setUserTime(null);
            }
            else if( !StringUtil.isEmpty(conditionUser.getId()) && !conditionUser.getId().equals(deviceFileSnapshot.getUserId()))
            {
            	bNeedUpdate = true;
                snapshot.setUserId(conditionUser.getId());
                snapshot.setUserTime(strBangtime);
                deviceUpdateRecordService.setDeviceUpdateRecord(strDeviceSn, UpdateRecordEnum.UPDATE_RECORD_USER.getValue(), conditionUser.getId(), strConsumer);
            }

            if (bNeedUpdate == true) {
                logger.info("updateDeviceFileSnapshotBySn::入参:{}", snapshot);
               // deviceFileSnapshotService.updateDeviceFileSnapshotBySn(snapshot);
                deviceFileSnapshotService.updateAllColDeviceFileSnapshotBySn(snapshot);
            }
        } catch (RpcServiceException e) {
            logger.error("SupplyChainKagkaService::processMessage e.getError=" + e.getError() + " e.getError.getValue=" + e.getError().getValue());
            return;
        }

        logger.info("SupplyChainKagkaService::processMessage ok");
    }



}
