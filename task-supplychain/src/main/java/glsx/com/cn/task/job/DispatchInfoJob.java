package glsx.com.cn.task.job;

import glsx.com.cn.task.service.SyncDeviceToExsysServer;
import glsx.com.cn.task.service.SyncLogDeviceToExsysServer;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

@ElasticJobConf(
        name = "DispatchInfoJob",
        overwrite = true,
        description = "第三方业务系统分发数据 同步任务",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)

public class DispatchInfoJob implements SimpleJob{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SyncDeviceToExsysServer toExsysServer;
	@Autowired
	private SyncLogDeviceToExsysServer logtoExsysServer;
	
	@Override
	public void execute(ShardingContext arg0) {
		logger.info("第三方业务系统分发数据 同步定时任务调用启动...");
		
		logtoExsysServer.dispatchLogDeviceToExsysServer();
		toExsysServer.dispatchDeviceToExsysServer();
		
	}

}
