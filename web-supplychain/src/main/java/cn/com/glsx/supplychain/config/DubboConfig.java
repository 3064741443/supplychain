package cn.com.glsx.supplychain.config;

import org.oreframework.boot.autoconfigure.dubbo.reference.ReferenceBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcDeviceRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.remote.RpcDebugInfoRemote;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;

@Configuration
public class DubboConfig{
	
	@Autowired
	private ReferenceBuilderFactory referenceBuilderFactory;
	
	// --------供应链服务 start ----------
	private static final String MINE_SERVICE_VERSION = "1.0.0";
	private static final Integer MINE_SERVICE_TIMEOUT = 100000;
	// --------供应链服务 end ----------
	
	@Bean
	public SupplyChainRemote supplyChainRemote(){
		return referenceBuilderFactory.builder(SupplyChainRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url("dubbo://192.168.1.59:20885")
				.build();
	}
	
	@Bean
	public JxcOrderRemote jxcOrderRemote(){
		return referenceBuilderFactory.builder(JxcOrderRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.59:20885")
				.build();
	}
	
	@Bean
	public JxcDeviceRemote jxcDeviceRemote(){
		return referenceBuilderFactory.builder(JxcDeviceRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.59:20885")
				.build();
	}
	
	@Bean
	public JxcCommonRemote jxcCommonRemote(){
		return referenceBuilderFactory.builder(JxcCommonRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.59:20885")
				.build();
	}
	
	@Bean
	public RpcDebugInfoRemote rpcDebugInfoRemote()
	{
		return referenceBuilderFactory.builder(RpcDebugInfoRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
			//	.url("dubbo://192.168.1.147:20881")
				.build();
	}
}
