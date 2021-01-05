package cn.com.glsx.supplychain.service;


import cn.com.glsx.supplychain.Main;
import cn.com.glsx.supplychain.model.DeviceFileSnapshot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class DeviceFileSnapshotServiceTest {

    @Autowired
    private DeviceFileSnapshotService deviceFileSnapshotService;

    @Test
    public void test(){
        DeviceFileSnapshot deviceFileSnapshot =  deviceFileSnapshotService.getDeviceFileSnapshotByCardId(17869);
        System.out.println("1111111111");
    }
}
