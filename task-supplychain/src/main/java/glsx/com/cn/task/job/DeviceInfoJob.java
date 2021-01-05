package glsx.com.cn.task.job;

import glsx.com.cn.task.service.SupplyChainSyncService;

import org.oreframework.boot.autoconfigure.elasticjob.annotation.ElasticJobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;


@ElasticJobConf(
        name = "DeviceInfoJob",
        overwrite = true,
        description = "设备同步",
        shardingItemParameters = "0=toutiao,1=cheyun",
        misfire = false
)

public class DeviceInfoJob implements SimpleJob{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SupplyChainSyncService supplyChainSyncService;

	@Override
	public void execute(ShardingContext arg0) {
		logger.info("设备同步定时任务调用启动...");
		supplyChainSyncService.SyncDeviceInfoToDeviceFile();
		supplyChainSyncService.SyncDeviceFileUnstockToFlowcatPlat();
		supplyChainSyncService.sysProductPriceRefresh();
	//	supplyChainSyncService.sysSettlementSendData();
	}
}
