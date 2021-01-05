package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.Main;
import cn.com.glsx.supplychain.model.SupplyDeviceFileRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class DeviceFileUnstockServiceTest {

    @Autowired
    private DeviceFileUnstockService deviceFileUnstockService;

    @Test
    public void testcaonima() throws Exception {
        SupplyDeviceFileRequest supplyDeviceFileRequest = new SupplyDeviceFileRequest();
        supplyDeviceFileRequest.setImsi("460060088888888");
        supplyDeviceFileRequest.setSn("863810030717431");
        deviceFileUnstockService.saveDeviceFileUnstock(supplyDeviceFileRequest);

    }
}
