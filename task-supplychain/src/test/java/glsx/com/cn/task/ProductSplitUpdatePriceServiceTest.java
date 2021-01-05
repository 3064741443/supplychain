package glsx.com.cn.task;

import glsx.com.cn.task.service.AcceptMerchantOrderService;
import glsx.com.cn.task.service.ProductSplitUpdatePriceService;

import org.springframework.beans.factory.annotation.Autowired;



public class ProductSplitUpdatePriceServiceTest extends BaseTest{

	@Autowired
	private ProductSplitUpdatePriceService priceService;
	@Autowired
	private AcceptMerchantOrderService acceptMerchantOrderService;
	
	@org.junit.Test
	public void doUpdateProductSplitTest()
	{
		//priceService.updateProductSplit();
		acceptMerchantOrderService.acceptMerchantOrder();
	}
}
