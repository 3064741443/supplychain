package glsx.com.cn.task.job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import glsx.com.cn.task.service.MaterialInfoService;
import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ElasticJobConf(
        name = "MaterialInfoJob",
        overwrite = true,
        description = "物料同步",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)

public class MaterialInfoJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialInfoService materialInfoService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("物料同步开始...");
        materialInfoService.add();
        logger.info("物料同步结束...");
    }
}
