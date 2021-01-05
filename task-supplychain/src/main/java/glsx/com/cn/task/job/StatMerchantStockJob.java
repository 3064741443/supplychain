package glsx.com.cn.task.job;

import glsx.com.cn.task.service.StatMerchantStockService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.glsx.framework.core.exception.RpcServiceException;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;


@ElasticJobConf(
        name = "StatMerchantStockJob",
        overwrite = true,
        description = "供应链代销数据拆分同步任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class StatMerchantStockJob implements SimpleJob{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StatMerchantStockService merchantStockService;

	@Override
	public void execute(ShardingContext arg0) {
		
		logger.info("供应链商户库存代码同步定时任务调用启动...");
		try{
			merchantStockService.doStatMerchantStock();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
		logger.info("供应链商户库存代码同步定时任务调用结束...");
	}
}
