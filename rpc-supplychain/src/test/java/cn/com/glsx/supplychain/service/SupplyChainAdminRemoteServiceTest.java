package cn.com.glsx.supplychain.service;

import cn.com.glsx.supplychain.enums.ReasonInvalidDeviceNum;
import cn.com.glsx.supplychain.mapper.DeviceFileUnstockMapper;
import cn.com.glsx.supplychain.model.DeviceFileUnstock;
import com.glsx.biz.merchant.common.entity.Merchant;
import com.glsx.biz.merchant.service.MerchantService;
import com.glsx.cloudframework.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.glsx.supplychain.Main;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class SupplyChainAdminRemoteServiceTest {

    @Autowired
    private MerchantService merchantService;  //老运营平台接口后面改用商户新平台接口

    @Autowired
    private DeviceFileUnstockMapper deviceFileUnstockMapper;

    @Autowired
    private Environment environment;

    @Test
    public void TEST() throws ServiceException {
        List<Merchant> da =  merchantService.findAll(null);
        System.out.println("");
    }

    @Test
    public void qwe(){
        DeviceFileUnstock deviceFileUnstock = new DeviceFileUnstock();
        String asd = environment.getProperty("additionalRecording.producer.topic");

        deviceFileUnstock.setIccid("8986061900001181190");
        deviceFileUnstock.setImsi("460066540098888");
        deviceFileUnstock.setSn("7777777777777778");
        DeviceFileUnstock dfu = deviceFileUnstockMapper.selectOne(deviceFileUnstock);

        System.out.println(dfu);
    }
}	
