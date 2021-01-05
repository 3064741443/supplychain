package glsx.com.cn.task.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import glsx.com.cn.task.service.ProductSplitUpdatePriceService;
import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ElasticJobConf(
        name = "ProductSplitUpdatePriceJob",
        overwrite = true,
        description = "供应链修改价格同步任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class ProductSplitUpdatePriceJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductSplitUpdatePriceService productSplitUpdatePriceService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("供应链修改价格同步任务 开始");
        productSplitUpdatePriceService.updateProductSplit();
        logger.info("供应链修改价格同步任务 结束");
    }
}
