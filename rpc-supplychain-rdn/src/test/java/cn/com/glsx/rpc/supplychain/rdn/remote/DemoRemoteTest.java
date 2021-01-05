package cn.com.glsx.rpc.supplychain.rdn.remote;

import org.junit.Assert;
import org.junit.Test;
import com.alibaba.dubbo.config.annotation.Reference;
import cn.com.glsx.rpc.supplychain.rdn.model.DemoModel;

/**
 * @Title: DemoRemoteTest.java
 * @Description:
 * @author deployer name
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
public class DemoRemoteTest extends BaseRemoteTest{
	
	@Reference
	private DemoRemote demoRemote;
	
	@Test
	public void load(){
		DemoModel model = demoRemote.get("name");
		Assert.assertNotNull(model);
	}
}
