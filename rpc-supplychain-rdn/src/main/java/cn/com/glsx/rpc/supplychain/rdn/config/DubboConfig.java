package cn.com.glsx.rpc.supplychain.rdn.config;

import org.oreframework.boot.autoconfigure.dubbo.reference.ReferenceBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.remote.TaskExportRemote;

/**
 * @Title: DubboConfig.java
 * @Description: Dubbo服务注册配置
 * @author deployer name  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2018
 */
@Configuration
public class DubboConfig {
	
	@Autowired
	private ReferenceBuilderFactory referenceBuilderFactory;
	
	// --------供应链服务 start ----------
	private static final String MINE_SERVICE_VERSION = "1.0.0";
	private static final Integer MINE_SERVICE_TIMEOUT = 600000;
	// --------供应链服务 end ----------
	
	private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.242:20880";
	
	/**
	 * 注册远程服务（示例）
	 * RpcRemote为需要调用的远程服务
	 * @return
	 */
	/*@Bean
	public RpcRemote rpcApiMethodRemote(){
		return factory.builder(RpcRemote.class).check(false).timeout(30000).build();
	}*/
	
	
	@Bean
	public TaskExportRemote taskExportRemote()
	{
		return referenceBuilderFactory.builder(TaskExportRemote.class)
				.version(MINE_SERVICE_VERSION)
				.timeout(MINE_SERVICE_TIMEOUT)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}
	
	/**
	 * 经销存改版 订单模块接口
	 * @return
	 */
	@Bean
	public JxcOrderRemote jxcOrderRemote(){
		return referenceBuilderFactory.builder(JxcOrderRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.59:20885")
				.build();
	}
	
}
