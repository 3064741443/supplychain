package glsx.com.cn.task.job;

import cn.com.glsx.framework.core.exception.RpcServiceException;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import glsx.com.cn.task.service.AcceptMerchantOrderService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.Action;

@ElasticJobConf(
        name = "AcceptMerchantOrderJob",
        overwrite = true,
        description = "自动签收同步任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class AcceptMerchantOrderJob implements SimpleJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AcceptMerchantOrderService acceptMerchantOrderService;

    @Override
    public void execute(ShardingContext arg0) {
        logger.info("自动签收 同步定时任务调用启动...");

        try{
        	acceptMerchantOrderService.acceptMerchantOrder();
        }catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}  
    }
}
