package cn.com.glsx.supplychain.jst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;
import org.junit.Test;
import cn.com.glsx.supplychain.jst.model.DemoModel;

/**
 * @Title: DemoServiceTest.java
 * @Description:
 * @author deployer name
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
public class DemoServiceTest extends BaseServiceTest{
	
	@Autowired
	private DemoService demoService;
	
	@Test
	public void load(){
		DemoModel model = demoService.load();
		Assert.assertNotNull(model);
	}
}
