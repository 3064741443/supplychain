package glsx.com.cn.task;

import glsx.com.cn.task.service.StatMerchantStockService;

import org.springframework.beans.factory.annotation.Autowired;

public class StatMerchantStockServiceTest extends BaseTest{

	@Autowired
	private StatMerchantStockService stockService;
	
	@org.junit.Test
	public void doStatMerchantStockTest()
	{
		stockService.doStatMerchantStock();
	}
}
