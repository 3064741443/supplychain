package glsx.com.cn.task.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import glsx.com.cn.task.service.MerchantOrderExportService;
import glsx.com.cn.task.service.MerchantOrderService;
import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ElasticJobConf(
        name = "MerchantOrderGenerateJob",
        overwrite = true,
        description = "广汇订单批量生成商户订单任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class MerchantOrderGenerateJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchantOrderService merchantOrderService;

    @Override
    public void execute(ShardingContext arg0) {
        logger.info("广汇订单批量生成商户订单任务");

        merchantOrderService.generateMerchantOrder();
    }
}
