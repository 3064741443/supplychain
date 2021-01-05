package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.Main;
import cn.com.glsx.supplychain.manager.SupplyChainExternalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class SupplyChainExternalServiceTest {

    @Autowired
    private SupplyChainExternalService supplyChainExternalService;

    @Test
    public void testlistPackageInfoByDeviceCode(){
        supplyChainExternalService.listPackageInfoByDeviceCode(110269);
    }
}
