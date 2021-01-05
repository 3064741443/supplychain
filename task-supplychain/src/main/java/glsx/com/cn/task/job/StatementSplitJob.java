package glsx.com.cn.task.job;

import cn.com.glsx.framework.core.exception.RpcServiceException;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import glsx.com.cn.task.service.StatementCollectionSplitService;
import glsx.com.cn.task.service.StatementFinanceSplitService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@ElasticJobConf(
        name = "StatementSplitJob",
        overwrite = true,
        description = "供应链代销数据拆分同步任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class StatementSplitJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatementCollectionSplitService statementCollectionSplitService;

    @Autowired
    private StatementFinanceSplitService statementFinanceSplitService;

    @Override
    public void execute(ShardingContext arg0) {
        logger.info("供应链代销数据拆分 同步定时任务调用启动...");
        statementCollectionSplitService.statementCollectionSplit();
        try{
        	statementFinanceSplitService.statementFinanceSplit();

        }catch(RpcServiceException e){
        	logger.error(e.getMessage(),e);
        }
        
    }
}
