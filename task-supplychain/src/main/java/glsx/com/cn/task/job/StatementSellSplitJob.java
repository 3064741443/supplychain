package glsx.com.cn.task.job;

import glsx.com.cn.task.service.StatementSellSplitService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.glsx.framework.core.exception.RpcServiceException;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

@ElasticJobConf(
        name = "StatementSellSplitJob",
        overwrite = true,
        description = "供应链经销数据拆分同步任务（微信支付宝广联无忧驾保无忧）",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class StatementSellSplitJob implements SimpleJob{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StatementSellSplitService sellSplitService;
	@Override
	public void execute(ShardingContext shardingContext) {
		
		logger.info("供应链经销数据拆分同步任务（微信支付宝广联无忧驾保无忧） 开始");
		handleStatementSellRenew();
		handleStatementSellRzfb();
		handleStatementSellGlwy();
		handleStatementSellJbwy();
		logger.info("供应链经销数据拆分同步任务（微信支付宝广联无忧驾保无忧） 结束");
	}
	
	private void handleStatementSellRenew(){
		try{
			sellSplitService.doStatementSellRenewSplit();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	private void handleStatementSellRzfb(){
		try{
			sellSplitService.doStatementSellRzfbSplit();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	private void handleStatementSellGlwy(){
		try{
			sellSplitService.doStatementSellGlwySplit();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	private void handleStatementSellJbwy(){
		try{
			sellSplitService.doStatementSellJbwySplit();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	
}
