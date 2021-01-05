package glsx.com.cn.task.service.impl;

import cn.com.glsx.supplychain.jst.remote.MotorcycleRemote;
import glsx.com.cn.task.service.MerchantOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName MerchantOrderServiceImpl
 * @Author admin
 * @Param
 * @Date 2019/11/8 16:23
 * @Version
 **/
@Service
public class MerchantOrderServiceImpl implements MerchantOrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MotorcycleRemote motorcycleRemote;

    @Override
    public void generateMerchantOrder() {

        motorcycleRemote.createMerchantOrderByGhOrder();
    }

    @Override
    public void synchronizeMerchantOrderStatus() {
        motorcycleRemote.synchronizeMerchantOrderStatus();
    }
}
