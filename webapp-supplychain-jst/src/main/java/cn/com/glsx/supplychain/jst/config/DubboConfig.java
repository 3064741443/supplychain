package cn.com.glsx.supplychain.jst.config;

import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.merchant.facade.remote.MerchantLoginFacadeRemote;
import cn.com.glsx.supplychain.jst.remote.MotorcycleRemote;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcTransferOrderRemote;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ProvinceService;
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
	

	private static final String MINE_SERVICE_VERSION = "1.0.0";
	private static final Integer MINE_SERVICE_TIMEOUT = 100000;
	
	private static final String RPC_MERCHANT_FACADE_API_VERSION = "1.0.0";
	private static final Integer RPC_MERCHANT_FACADE_API_TIMEOUT = 100000;
	
	// --------用户体系服务 start---------
	private static final String COMMON_USER_SERVICE_VERSION = "2.2.0";
	private static final Boolean COMMON_USER_SERVICE_CHECK = false;
	// --------用户体系服务 end---------

	private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.2.43:20885";
//	private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.59:20882";
	//private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.83:20882";
//	private static final String MYSELEF_MERCHANT_URL = "dubbo://192.168.3.191:20670";
	
	
	
	/**
	 * 微信端涉及的接口
	 * @return
	 */
	@Bean
	public WxBsMerchantRemote wxBsMerchantRemote(){
		return factory.builder(WxBsMerchantRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}
	
	/**
	 * 商户平台上户接口
	 * @return
	 */
	@Bean
	public MerchantFacadeRemote machantFacadeRemote(){
		return factory.builder(MerchantFacadeRemote.class)
				.timeout(RPC_MERCHANT_FACADE_API_TIMEOUT)
				.version(RPC_MERCHANT_FACADE_API_VERSION)
	//			.url(MYSELEF_MERCHANT_URL)
				.build();
	}
	
	/**
	 * 商户平台上户接口
	 * @return
	 */
	@Bean
	public MerchantLoginFacadeRemote merchantLoginFacadeRemote(){
		return factory.builder(MerchantLoginFacadeRemote.class)
				.timeout(RPC_MERCHANT_FACADE_API_TIMEOUT)
				.version(RPC_MERCHANT_FACADE_API_VERSION)
	//			.url(MYSELEF_MERCHANT_URL)
				.build();
	}
	
	/**
	 * 广汇18家 根据车型车系下单
	 * @return
	 */
	@Bean
	public MotorcycleRemote motorcycleRemote(){
		return factory.builder(MotorcycleRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url(MYSELEF_SERVICE_URL)
				.build();
	}
	
	//省份Service
	@Bean
	public ProvinceService provinceService(){
		return factory.builder(ProvinceService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	//市CityService
	@Bean
	public CityService cityService(){
		return factory.builder(CityService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	//区AreaService
	@Bean
	public AreaService reaService(){
		return factory.builder(AreaService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}

	//公用接口调用
	@Bean
	public JxcCommonRemote jxcCommonRemote(){
		return factory.builder(JxcCommonRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url(MYSELEF_MERCHANT_URL)
				.build();
	}

	/**
	 * 小程序调拨订单
	 * @return
	 */
	@Bean
	public JxcTransferOrderRemote jxcTransferOrderRemote(){
		return factory.builder(JxcTransferOrderRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.url(MYSELEF_SERVICE_URL)
				.build();
	}



}
