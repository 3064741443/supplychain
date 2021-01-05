package glsx.com.cn.task.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import glsx.com.cn.task.service.JstShopService;
import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName JstShopJob
 * @Description
 * @Author xiex
 * @Date 2020/2/27 21:28
 * @Version 1.0
 */
@ElasticJobConf(
        name = "JstShopJob",
        overwrite = true,
        description = " 供货关系维护 导入门店创建商户",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class JstShopJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JstShopService jstShopService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("供货关系维护 导入门店创建商户 开始");
        jstShopService.updateShopStatus();
        logger.info("供货关系维护 导入门店创建商户 结束");
    }
}
