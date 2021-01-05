package cn.com.glsx.supplychain.remote;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceListImport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceManagerAdminRemoteTest {

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;

    @Test
    public void testGetDeviceFileById() {
        DeviceFile deviceFile = new DeviceFile();
        deviceFile.setId(79);
        deviceManagerAdminRemote.getDeviceFileById(deviceFile);
    }

    @Test
    public void testCheckImportDeviceList(){
        String deviceCode = "101411";
        List<DeviceListImport > importList = new ArrayList<>();
        DeviceListImport deviceListImport = new DeviceListImport();
        deviceListImport.setModulePhone("1234668");
        deviceListImport.setIccid("12346123451234516338");
        deviceListImport.setImsi("460063121000617");
        deviceListImport.setImei("7980709001");
        deviceListImport.setBatchNo("2222");
        deviceListImport.setVcode("123");
        deviceListImport.setPackageNo("813000002");
        deviceListImport.setMentchantNo("100001");
        importList.add(deviceListImport);
        Boolean isOnlyimsi = true;
        deviceManagerAdminRemote.checkImportDeviceList(deviceCode,importList,isOnlyimsi,false);
    }

    @Test
    public void testListDeviceFile(){
        RpcPagination<DeviceFile> pagination = new RpcPagination<>();
        pagination.setPageNum(10);
        DeviceFile deviceFile = new DeviceFile();
        deviceFile.setSn("863810030717431");
        RpcResponse<RpcPagination<DeviceFile>> asd = deviceManagerAdminRemote.listDeviceFile(pagination,deviceFile);
        System.out.println("11111");
    }

    @Test
    public void testImportDeviceListByExternalProgram(){
        DeviceListImport deviceListImport = new DeviceListImport();
        deviceListImport.setImsi("460046175906800");
        deviceListImport.setIccid("89860441191850456800");
        deviceListImport.setImei("1790225035");
        deviceListImport.setModulePhone("1440417596800");
        deviceListImport.setVcode("7VR8U3");
        deviceListImport.setMentchantNo("44184512");
        deviceListImport.setPackageNo("800026");
        List<DeviceListImport> list = new ArrayList<>();
        list.add(deviceListImport);
        deviceManagerAdminRemote.importDeviceListByExternalProgram(1,"admin","110262",list);
    }


}
