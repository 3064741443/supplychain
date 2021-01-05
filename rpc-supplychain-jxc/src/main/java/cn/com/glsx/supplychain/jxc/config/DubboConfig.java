package cn.com.glsx.supplychain.jxc.config;

import com.glsx.biz.common.base.service.CarbrandService;
import com.glsx.biz.common.base.service.CarseriesService;
import com.glsx.biz.common.base.service.CartypeService;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import com.glsx.platform.goods.common.service.ServicePackageService;
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
	private ReferenceBuilderFactory referenceBuilderFactory;
	
	// --------服务套餐 start---------
	private static final String SERVICE_PACKAGE_VERSION = "2.0.0";
	private static final Integer SERVICE_PACKAGE_TIMEOUT = 100000;
	// --------服务套餐 end---------
	
	private static final String CARBRAND_SERVICE_VERSION = "2.2.0";
	private static final Integer CARBRAND_SERVICE_TIMEOUT = 100000;

	//流量平台卡校验
	private static final String COMMON_FLOW_SERVICE_VERSION = "1.0.0";
	private static final Boolean COMMON_FLOW_SERVICE_CHECK = false;
	
	@Bean
	public ServicePackageService servicePackageService(){
		return referenceBuilderFactory.builder(ServicePackageService.class).check(false)
				.timeout(SERVICE_PACKAGE_TIMEOUT)
				.version(SERVICE_PACKAGE_VERSION)
				.build();
	}

	@Bean
	public OpsMgrManager opsMgrManager(){
		return referenceBuilderFactory.builder(OpsMgrManager.class)
				.version(COMMON_FLOW_SERVICE_VERSION)
				.check(COMMON_FLOW_SERVICE_CHECK)
				.build();
	}

	@Bean
	public CarbrandService carbrandService(){
		return referenceBuilderFactory.builder(CarbrandService.class)
				.timeout(CARBRAND_SERVICE_TIMEOUT)
				.version(CARBRAND_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
				.build();
	}
	
	@Bean
	public CarseriesService carseriesService(){
		return referenceBuilderFactory.builder(CarseriesService.class)
				.timeout(CARBRAND_SERVICE_TIMEOUT)
				.version(CARBRAND_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
				.build();
	}
	
	@Bean
	public CartypeService cartypeService(){
		return referenceBuilderFactory.builder(CartypeService.class)
				.timeout(CARBRAND_SERVICE_TIMEOUT)
				.version(CARBRAND_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
				.build();
	}
	
}
