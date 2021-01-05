package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.Main;
import cn.com.glsx.supplychain.model.DeviceFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class DeviceFileServiceTest {

    @Autowired
    private DeviceFileService deviceFileService;

    @Test
    public void testAddDeviceFileByDuplicateKey(){
        DeviceFile deviceFile = new DeviceFile();
        deviceFile.setSn("1232132132133");
        deviceFile.setDeviceCode(100213);
        deviceFile.setPackageId(12);
        deviceFile.setSendMerchantNo("123321");
        deviceFile.setOutStorageType("SC");
        deviceFile.setExternalFlag("EX");
        deviceFile.setCreatedBy("admin");
        deviceFile.setCreatedDate(new Date());
        deviceFile.setUpdatedBy("admin");
        deviceFile.setUpdatedDate(new Date());
        deviceFileService.addDeviceFileByDuplicateKey(deviceFile);
    }
}
