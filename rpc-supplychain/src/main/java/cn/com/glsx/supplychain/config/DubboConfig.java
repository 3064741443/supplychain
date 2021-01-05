package cn.com.glsx.supplychain.config;

import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;

import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.platform.goods.common.service.ServicePackageService;

import org.oreframework.boot.autoconfigure.dubbo.reference.ReferenceBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.glsx.biz.common.base.service.ConfigurationService;
import com.glsx.biz.common.user.service.DeviceCategoryService;
import com.glsx.biz.common.user.service.PackagesService;
import com.glsx.biz.merchant.service.MerchantService;
import com.glsx.biz.user.service.PhysicalDeviceService;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
//import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import com.glsx.oms.flowservice.api.service.FlowCardService;

@Configuration
public class DubboConfig{
	
	@Autowired
	private ReferenceBuilderFactory referenceBuilderFactory;
	
	// --------用户体系服务 start---------
	private static final String COMMON_USER_SERVICE_VERSION = "2.2.0";
	private static final Boolean COMMON_USER_SERVICE_CHECK = false;
	// --------用户体系服务 end---------
	
	// --------用户体系服务 start---------
	private static final String COMMON_FLOW_SERVICE_VERSION = "1.0.0";
	private static final Boolean COMMON_FLOW_SERVICE_CHECK = false;
	// --------用户体系服务 end---------
	
	// --------商户服务 start---------
	private static final String MERCHANT_SERVICE_VERSION = "1.0.0";
	private static final Integer MERCHANT_SERVICE_TIMEOUT = 100000;
	// --------商户服务 end---------
	
	// --------商户服务 start---------
		private static final String CONFIGURE_SERVICE_VERSION = "2.2.0";
		private static final Integer CONFIGURE_SERVICE_TIMEOUT = 100000;
		// --------商户服务 end---------

	// --------服务套餐 start---------
	private static final String SERVICE_PACKAGE_VERSION = "2.0.0";
	private static final Integer SERVICE_PACKAGE_TIMEOUT = 100000;
	// --------服务套餐 end---------

	// --------物料服务 start---------
	private static final String MATERIAL_SERVICE_VERSION = "1.0.0";
	private static final Integer MATERIAL_SERVICE_TIMEOUT = 100000;
	// --------物料服务 end---------
	
	@Bean
	public OpsMgrManager opsMgrManager(){
		return referenceBuilderFactory.builder(OpsMgrManager.class)
				.version(COMMON_FLOW_SERVICE_VERSION)
				.check(COMMON_FLOW_SERVICE_CHECK)
				.build();
	}
	
	@Bean
	public PhysicalDeviceService physicalDeviceService(){
		return referenceBuilderFactory.builder(PhysicalDeviceService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	
	@Bean
	public FlowCardService flowCardService(){
		return referenceBuilderFactory.builder(FlowCardService.class)
				.version(COMMON_FLOW_SERVICE_VERSION)
				.check(COMMON_FLOW_SERVICE_CHECK)
				.build();
	}
	
	@Bean
	public DeviceCategoryService deviceCategoryService(){
		return referenceBuilderFactory.builder(DeviceCategoryService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	
	@Bean
	public PackagesService packagesService(){
		return referenceBuilderFactory.builder(PackagesService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}

	@Bean
	public MerchantService merchantService(){
		return referenceBuilderFactory.builder(MerchantService.class).check(false)
				.timeout(MERCHANT_SERVICE_TIMEOUT)
				.version(MERCHANT_SERVICE_VERSION)
				.build();
	}
	
	@Bean
	public ConfigurationService configurationService(){
		return referenceBuilderFactory.builder(ConfigurationService.class)
				.timeout(CONFIGURE_SERVICE_TIMEOUT)
				.version(CONFIGURE_SERVICE_VERSION)
				.build();
	}

	@Bean
	public ServicePackageService servicePackageService(){
		return referenceBuilderFactory.builder(ServicePackageService.class).check(false)
				.timeout(SERVICE_PACKAGE_TIMEOUT)
				.version(SERVICE_PACKAGE_VERSION)
				.build();
	}

	@Bean
	public IMaterialDubboService iMaterialDubboService(){
		return referenceBuilderFactory.builder(IMaterialDubboService.class)
				.timeout(MATERIAL_SERVICE_TIMEOUT)
				.version(MATERIAL_SERVICE_VERSION)
				.build();
	}
	
	/**
	 * 商户服务
	 * @return
	 */
	@Bean
	public MerchantFacadeRemote merchantFacadeRemote(){
		return referenceBuilderFactory.builder(MerchantFacadeRemote.class)
				.version(MERCHANT_SERVICE_VERSION)
				.timeout(MERCHANT_SERVICE_TIMEOUT)
				.check(false)
				.build();
	}

}
