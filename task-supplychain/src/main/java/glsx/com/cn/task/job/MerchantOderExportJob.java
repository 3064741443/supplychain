package glsx.com.cn.task.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import glsx.com.cn.task.service.MerchantOrderExportService;
import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ElasticJobConf(
        name = "MerchantOderExportJob",
        overwrite = true,
        description = "供应链商户订单数据同步任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class MerchantOderExportJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchantOrderExportService merchantOrderExportService;

    @Override
    public void execute(ShardingContext arg0) {
        logger.info("供应链商户订单数据同步任务 同步定时任务已经关闭");

       // merchantOrderExportService.merchantOrderExport();
    }
}
