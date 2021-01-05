package cn.com.glsx.supplychain.kafka;

import cn.com.glsx.supplychain.Main;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oreframework.jms.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class kafkaTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void test(){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sn", "994000000000001");
        kafkaProducer.sendObject(JSONObject.toJSONString(params).getBytes());
    }
}
