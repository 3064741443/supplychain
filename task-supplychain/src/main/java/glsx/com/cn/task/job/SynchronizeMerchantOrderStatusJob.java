package glsx.com.cn.task.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import glsx.com.cn.task.service.MerchantOrderService;
import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/9/5 17:35
 */
@ElasticJobConf(
        name = "SynchronizeMerchantOrderStatusJob",
        overwrite = true,
        description = "将商户订单状态同步到广汇订单",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class SynchronizeMerchantOrderStatusJob implements SimpleJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchantOrderService merchantOrderService;

    @Override
    public void execute(ShardingContext arg0) {
        logger.info("将商户订单状态同步到广汇订单");
        merchantOrderService.synchronizeMerchantOrderStatus();
    }
}
