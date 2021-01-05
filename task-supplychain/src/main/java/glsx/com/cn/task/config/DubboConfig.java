package glsx.com.cn.task.config;

import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.supplychain.jst.remote.MotorcycleRemote;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import org.oreframework.boot.autoconfigure.dubbo.reference.ReferenceBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
//import cn.com.glsx.supplychain.remote.DeviceManagerService;
//import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;





import cn.com.glsx.supplychain.remote.DeviceManagerService;
import cn.com.glsx.supplychain.remote.SupplyChainRemote;

import com.glsx.biz.common.user.service.DeviceCategoryService;
import com.glsx.biz.user.service.PhysicalDeviceService;
import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
//import com.glsx.oms.fcservice.api.manager.OpsMgrManager;
import com.glsx.oms.flowservice.api.service.DeviceService;
import com.glsx.oms.flowservice.api.service.FlowCardService;
@Configuration
public class DubboConfig{
	
	@Autowired
	private ReferenceBuilderFactory referenceBuilderFactory;
	
	// --------供应链服务 start ----------
	private static final String MINE_SERVICE_VERSION = "1.0.0";
	private static final Integer MINE_SERVICE_TIMEOUT = 100000;
	// --------供应链服务 end ----------
	
	// --------用户体系服务 start---------
	private static final String COMMON_USER_SERVICE_VERSION = "2.2.0";
	private static final String USER_SERVICE_VERSION = "1.0.0";
	private static final Boolean COMMON_USER_SERVICE_CHECK = false;
	// --------用户体系服务 end---------
	
	// --------用户体系服务 start---------
	private static final String COMMON_FLOW_SERVICE_VERSION = "1.0.0";
	private static final Boolean COMMON_FLOW_SERVICE_CHECK = false;
	// --------用户体系服务 end---------
	
	// --------用户体系服务 start---------
	private static final String COMMON_DEVICE_SERVICE_VERSION = "1.0.0";
	private static final Boolean COMMON_DEVICE_SERVICE_CHECK = false;
	// --------用户体系服务 end---------

	private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.69:20883";

	// --------物料服务 start---------
	private static final String MATERIAL_SERVICE_VERSION = "1.0.0";
	private static final Integer MATERIAL_SERVICE_TIMEOUT = 100000;
	// --------物料服务 end---------

	// --------商户服务 start---------
	private static final String MERCHANT_SERVICE_VERSION = "1.0.0";
	private static final Integer MERCHANT_SERVICE_TIMEOUT = 100000;
	private static final Boolean MERCHANT_FLOW_SERVICE_CHECK = false;
	// --------商户服务 end-------

	@Bean
	public PhysicalDeviceService physicalDeviceService(){
		return referenceBuilderFactory.builder(PhysicalDeviceService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.build();
	}
	
	
	@Bean
	public SupplyChainRemote supplyChainAdminRemote(){
		return referenceBuilderFactory.builder(SupplyChainRemote.class)
				.version(MINE_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
			//	.url("dubbo://192.168.1.147:20881")
				.build();
	}
	
	@Bean
	public FlowCardService flowCardService(){
		return referenceBuilderFactory.builder(FlowCardService.class)
				.version(COMMON_FLOW_SERVICE_VERSION)
				.check(COMMON_DEVICE_SERVICE_CHECK)
				.build();
	}
	
	@Bean
	public OpsMgrManager opsMgrManager(){
		return referenceBuilderFactory.builder(OpsMgrManager.class)
				.version(COMMON_FLOW_SERVICE_VERSION)
				.check(COMMON_DEVICE_SERVICE_CHECK)
				.build();
	}
	
	@Bean
	public DeviceService deviceService(){
		return referenceBuilderFactory.builder(DeviceService.class)
				.version(COMMON_DEVICE_SERVICE_VERSION)
				.check(COMMON_FLOW_SERVICE_CHECK)
				.build();
	} 
	
	/*
	@Bean
	public DeviceInfoRemote deviceInfoRemote(){
		return referenceBuilderFactory.builder(DeviceInfoRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.build();
	}
	
	@Bean
	public OrderInfoRemote orderInfoRemote(){
		return referenceBuilderFactory.builder(OrderInfoRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.build();
	}
	
	
	@Bean
	public DeviceManagerAdminRemote deviceManagerAdminRemote(){
		return referenceBuilderFactory.builder(DeviceManagerAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.build();
	}
	*/
	/**
	 * 查询物料
	 * @return
	 */
	@Bean
	public IMaterialDubboService iMaterialDubboService(){
		return referenceBuilderFactory.builder(IMaterialDubboService.class)
				//.url("dubbo://192.168.2.57:20880")
				.timeout(MATERIAL_SERVICE_TIMEOUT)
				.version(MATERIAL_SERVICE_VERSION)
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
	public DeviceManagerService deviceManagerService(){
		return referenceBuilderFactory.builder(DeviceManagerService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
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
				.check(MERCHANT_FLOW_SERVICE_CHECK)
				.build();
	}

	@Bean
	public MotorcycleRemote motorcycleRemote(){
		return referenceBuilderFactory.builder(MotorcycleRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.url(MYSELEF_SERVICE_URL)
				.version(MINE_SERVICE_VERSION)
				.build();
	}
	
}
