package glsx.com.cn.task.job;

import glsx.com.cn.task.service.DeviceStatisService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

@ElasticJobConf(
        name = "DeviceStatisJob",
        overwrite = true,
        description = "设备数量统计",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)
public class DeviceStatisJob implements SimpleJob{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DeviceStatisService statisService;
	@Override
	public void execute(ShardingContext shardingContext) {
		logger.info("设备数量统计定时任务启动...");
		statisService.handleDeviceStatis();
	}
}
