package cn.com.glsx.supplychain.manager;

import cn.com.glsx.supplychain.manager.base.KafkaPostConsumer;
import cn.com.glsx.supplychain.mapper.*;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderDetailMapper;
import cn.com.glsx.supplychain.mapper.bs.MerchantOrderMapper;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import cn.com.glsx.supplychain.model.bs.MerchantOrderDetail;
import cn.com.glsx.supplychain.model.bs.SalesSummarizingMaterialDetail;
import cn.com.glsx.supplychain.util.RequestVerifyService;
import cn.com.glsx.supplychain.util.StringUtil;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName SettlementKafkaService
 * @Author admin
 * @Param
 * @Date 2019/7/4 10:11
 * @Version
 **/
@Component
public class SettlementKafkaService extends KafkaPostConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestVerifyService requestVerifyService;

    @Autowired
    private SyncLastIdRecordMapper syncLastIdRecordMapper;



    protected SettlementKafkaService() {
        super("settlement_last_protocol_change");
    }

    @Override
    public void processMessage(Object obj) {
        LOGGER.info("kafka接受的消息对象：{}", new String(getMessage()));

        String kafkaDto = new String(getMessage());

        JSONObject jasonObject = JSONObject.fromObject(kafkaDto);

        Map map = jasonObject;

        if (map == null) {
            LOGGER.info("kafka接受的消息对象为空。");
            return;
        }
        LOGGER.info("kafka消息转换后的Map:{}", map);
        String consumerId = null;
        if (!StringUtils.isEmpty(map.get("consumerId"))) {
            consumerId = map.get("consumerId") + "";
        }

        Integer id =  Integer.valueOf(consumerId);
        id++;
        //修改同步记录表里的财务同步数据结果ID
        SyncLastidRecord syncLastidRecord = new SyncLastidRecord();
        syncLastidRecord.setId(1);
        syncLastidRecord.setSettlementId(id);
        syncLastIdRecordMapper.updateSyncLastidRecord(syncLastidRecord);

        String strConsumer = (String) map.get("consumer");
        String strVersion = (String) map.get("version");

        LOGGER.info("SettlementKafkaService::processMessage param consumerId=" + (StringUtils.isEmpty(consumerId) ? "" : consumerId));

        SupplyRequest request = new SupplyRequest();
        request.setConsumer(strConsumer);
        request.setVersion(strVersion);

        if (!requestVerifyService.verifyRequest(request)) {
            LOGGER.error("SettlementKafkaService::processMessage not authorized message !");
            return;
        }


    }
}
