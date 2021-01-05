package cn.com.glsx.merchant.supplychain.config;

import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.supplychain.jst.remote.JxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcProductRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcTransferOrderRemote;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.remote.am.ProductSplitAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import com.glsx.biz.common.base.service.*;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import org.oreframework.boot.autoconfigure.dubbo.reference.ReferenceBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	private static final Boolean COMMON_USER_SERVICE_CHECK = false;
	// --------用户体系服务 end---------
	
	private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.59:20880";

	// --------物料服务 start---------
	private static final String MATERIAL_SERVICE_VERSION = "1.0.0";
	private static final Integer MATERIAL_SERVICE_TIMEOUT = 100000;
	// --------物料服务 end---------
	
	// --------物料服务 start---------
		private static final String CARBRAND_SERVICE_VERSION = "2.2.0";
		private static final Integer CARBRAND_SERVICE_TIMEOUT = 100000;
		// --------物料服务 end---------

	// --------商户服务 start---------
	private static final String MERCHANT_SERVICE_VERSION = "1.0.0";
	private static final Integer MERCHANT_SERVICE_TIMEOUT = 100000;
	private static final Boolean MERCHANT_FLOW_SERVICE_CHECK = false;
	// --------商户服务 end-------

	@Bean
	public SupplyChainAdminRemote supplyChainAdminRemote(){
		return referenceBuilderFactory.builder(SupplyChainAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	@Bean
	public DeviceManagerAdminRemote deviceManagerAdminRemote(){
		return referenceBuilderFactory.builder(DeviceManagerAdminRemote.class).check(false)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	//省份Service
	@Bean
	public ProvinceService provinceService(){
		return referenceBuilderFactory.builder(ProvinceService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	//市CityService
	@Bean
	public CityService cityService(){
		return referenceBuilderFactory.builder(CityService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	//区AreaService
	@Bean
	public AreaService reaService(){
		return referenceBuilderFactory.builder(AreaService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(COMMON_USER_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}

	//用户管理
	@Bean
	public DealerUserInfoAdminRemote dealerUserInfoAdminRemote(){
		return  referenceBuilderFactory.builder(DealerUserInfoAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	//产品管理
	@Bean
	public ProductAdminRemote productAdminRemote(){
		return  referenceBuilderFactory.builder(ProductAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	//商户订单
	@Bean
	public MerchantOrderAdminRemote merchantOrderAdminRemote(){
		return  referenceBuilderFactory.builder(MerchantOrderAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	//地址簿
	@Bean
	public AddressAdminRemote addressAdminRemote(){
		return  referenceBuilderFactory.builder(AddressAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	//售后订单
	@Bean
	public AfterSaleOrderAdminRemote afterSaleOrderAdminRemote(){
		return  referenceBuilderFactory.builder(AfterSaleOrderAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	//销售汇总
	@Bean
	public SalesSummarizingRemote salesSummarizingRemote(){
		return  referenceBuilderFactory.builder(SalesSummarizingRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 销售管理
	 */
	@Bean
	public SalesManagerRemote salesManagerRemote(){
		return referenceBuilderFactory.builder(SalesManagerRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 销售管理与销售对账汇总关系Remote
	 * @return
	 */
	@Bean
	public SalesRelationAdminRemote salesRelationAdminRemote(){
		return referenceBuilderFactory.builder(SalesRelationAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 产品拆分
	 * @return
	 */
	@Bean
	public ProductSplitAdminRemote productSplitAdminRemote(){
		return referenceBuilderFactory.builder(ProductSplitAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 查询物料
	 * @return
	 */
	@Bean
	public IMaterialDubboService iMaterialDubboService(){
		return referenceBuilderFactory.builder(IMaterialDubboService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.build();
	}
	
	/**
	 * 查询库存
	 * @return
	 */
	@Bean
	public BsMerchantStockAdminRemote bsMerchantStockAdminRemote(){
		return referenceBuilderFactory.builder(BsMerchantStockAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}
	
	/**
	 * 微信端涉及的接口
	 * @return
	 */
	@Bean
	public WxBsMerchantRemote wxBsMerchantRemote(){
		return referenceBuilderFactory.builder(WxBsMerchantRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
			//	.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 经销存后台涉及的接口
	 * @return
	 */
	@Bean
	public JxBsMerchantRemote jxBsMerchantRemote(){
		return referenceBuilderFactory.builder(JxBsMerchantRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
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
	
	/**
	 * 经销存改版 进销存重构地址 类型等公共服务接口
	 * @return
	 */
	@Bean
	public JxcCommonRemote jxcCommonRemote(){
		return referenceBuilderFactory.builder(JxcCommonRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.59:20885")
				.build();
	}
	/**
	 * 经销存改版 产品模块接口
	 * @return
	 */
	@Bean
	public JxcProductRemote jxcProductRemote(){
		return referenceBuilderFactory.builder(JxcProductRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.59:20885")
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
	
	@Bean
	public CarbrandService carbrandService(){
		return referenceBuilderFactory.builder(CarbrandService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(CARBRAND_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
				.build();
	}
	
	@Bean
	public CarseriesService carseriesService(){
		return referenceBuilderFactory.builder(CarseriesService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(CARBRAND_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
				.build();
	}
	
	@Bean
	public CartypeService cartypeService(){
		return referenceBuilderFactory.builder(CartypeService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(CARBRAND_SERVICE_VERSION)
		//		.url("dubbo://192.168.1.250:20882")
				.build();
	}

	/**
	 * pc调拨订单
	 * @return
	 */
	@Bean
	public JxcTransferOrderRemote jxcTransferOrderRemote(){
		return referenceBuilderFactory.builder(JxcTransferOrderRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.url("dubbo://192.168.2.43:20885")
				.build();
	}

}
