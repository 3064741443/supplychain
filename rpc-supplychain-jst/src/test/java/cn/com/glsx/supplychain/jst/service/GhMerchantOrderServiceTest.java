package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.supplychain.jst.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/8/20 15:44
 */

//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@RunWith(SpringJUnit4ClassRunner.class)
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
@SpringBootTest(classes = Main.class)
public class GhMerchantOrderServiceTest {
    @Autowired
    private GhMerchantOrderService ghMerchantOrderService;

    @Test
    public void createMerchantOrderByGhOrderTest() {
      Integer result=ghMerchantOrderService.createMerchantOrderByGhOrder();
    }

    @Test
    public void synchronizeMerchantOrderStatusTest() {
        Integer result=ghMerchantOrderService.synchronizeMerchantOrderStatus();
        System.out.println("打印结果"+result);
    }

}
