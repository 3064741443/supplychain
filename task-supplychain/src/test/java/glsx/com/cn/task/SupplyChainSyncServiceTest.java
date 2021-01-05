package glsx.com.cn.task;

import glsx.com.cn.task.service.MaterialInfoService;
import glsx.com.cn.task.service.StatMerchantStockService;
import glsx.com.cn.task.service.StatementSellSplitService;
import glsx.com.cn.task.service.StatementSnJobService;
import glsx.com.cn.task.service.SupplyChainSyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.glsx.framework.core.exception.RpcServiceException;

public class SupplyChainSyncServiceTest extends BaseTest{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SupplyChainSyncService supplyChainSyncService;
	@Autowired
	StatMerchantStockService merchantStockService;
	@Autowired
    private MaterialInfoService materialInfoService;
	@Autowired
	private StatementSnJobService statementSnJobService;
	
	@Autowired
	private StatementSellSplitService sellSplitService;

	@org.junit.Test
	public void SyncDeviceInfoToPhysicalDeviceTest(){
		
		int im = 0;
		//supplyChainSyncService.SyncDeviceInfoToDeviceFile();
		//merchantStockService.doStatMerchantStock();
		//materialInfoService.add();
		try{
		//	statementSnJobService.doSyncStatementSnDispatch();
		//	statementSnJobService.doSyncStatementSnDirect();
		//	sellSplitService.doStatementSellRzfbSplit();
			sellSplitService.doStatementSellJbwySplit();
		}catch(RpcServiceException e){
			logger.error(e.getMessage(),e);
		}
		
		im++;
	}
	

	@org.junit.Test
	public void SyncDeviceFileUnstockToFlowcatPlat(){

		supplyChainSyncService.SyncDeviceFileUnstockToFlowcatPlat();
	}
}
