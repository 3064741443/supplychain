package cn.com.glsx.supplychain.clean;

import cn.com.glsx.supplychain.Main;
import cn.com.glsx.supplychain.manager.DeviceTypeRedisManager;
import cn.com.glsx.supplychain.manager.SupplyChainRedisService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class CleanRedis {

    @Autowired
    private SupplyChainRedisService supplyChainRedisService;

    @Autowired
    private DeviceTypeRedisManager deviceTypeRedis;

    /**
     * 查询设备大类
     */
    @Test
    public void setDeviceType() {
        String deviceType = deviceTypeRedis.getDeviceTypeById(110263);
        System.out.println(deviceType);
    }

    /**
     * 删除设备大类
     */
    @Test
    public void CleanRedisDeviceType() {
        deviceTypeRedis.delDeviceType(110263);
        String deviceType = deviceTypeRedis.getDeviceTypeById(110263);
        System.out.println(deviceType);
    }
}
