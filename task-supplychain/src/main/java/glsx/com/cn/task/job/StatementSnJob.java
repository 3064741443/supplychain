package glsx.com.cn.task.job;

import glsx.com.cn.task.service.StatementSnJobService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.glsx.framework.core.exception.RpcServiceException;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

@ElasticJobConf(
        name = "StatementSnJob",
        overwrite = true,
        description = "商务订单发货数据汇总（经销对账的设备数据）",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class StatementSnJob implements SimpleJob{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StatementSnJobService jobService; 
	
	@Override
	public void execute(ShardingContext shardingContext) {
		
		handleSyncStatementSnDispatch();
		handleSyncStatementSnDirect();
	}
	
	private void handleSyncStatementSnDispatch(){
		try{
			jobService.doSyncStatementSnDispatch();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	private void handleSyncStatementSnDirect(){
		try{
			jobService.doSyncStatementSnDirect();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
	}

	
}
