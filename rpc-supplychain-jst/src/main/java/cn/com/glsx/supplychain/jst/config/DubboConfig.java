package cn.com.glsx.supplychain.jst.config;

import org.oreframework.boot.autoconfigure.dubbo.reference.ReferenceBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	private ReferenceBuilderFactory factory;

	/**
	 * 注册远程服务（示例）
	 * RpcRemote为需要调用的远程服务
	 * @return
	 */
	/*@Bean
	public RpcRemote rpcApiMethodRemote(){
		return factory.builder(RpcRemote.class).check(false).timeout(30000).build();
	}*/
}
