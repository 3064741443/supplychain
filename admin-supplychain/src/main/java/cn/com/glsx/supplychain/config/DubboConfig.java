package cn.com.glsx.supplychain.config;

import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.merchant.facade.remote.MerchantGroupFacadeRemote;
import cn.com.glsx.supplychain.jst.remote.MotorcycleRemote;
import cn.com.glsx.supplychain.jxc.remote.*;
import cn.com.glsx.supplychain.remote.*;
import cn.com.glsx.supplychain.remote.am.ProductSplitAdminRemote;
import cn.com.glsx.supplychain.remote.am.StatementCollectionAdminRemote;
import cn.com.glsx.supplychain.remote.am.StatementFinanceAdminRemote;
import cn.com.glsx.supplychain.remote.am.StatementSellAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ConfigurationService;
import com.glsx.biz.common.base.service.ProvinceService;
import com.glsx.biz.common.user.service.DeviceCategoryService;
import com.glsx.biz.common.user.service.PackagesService;
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
	private static final Integer MINE_SERVICE_TIMEOUT = 600000;
	// --------供应链服务 end ----------

	// --------用户体系服务 start---------
	private static final String COMMON_USER_SERVICE_VERSION = "2.2.0";
	private static final Boolean COMMON_USER_SERVICE_CHECK = false;
	// --------用户体系服务 end---------

	// --------物料服务 start---------
	private static final String MATERIAL_SERVICE_VERSION = "1.0.0";
	private static final Integer MATERIAL_SERVICE_TIMEOUT = 100000;
	// --------物料服务 end---------


	//private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.59:20882";
	private static final String MYSELEF_SERVICE_URL = "dubbo://192.168.1.211:20885";
	// --------供应链导入 start---------
	private static final Integer STATEMENT_TIMEOUT = 500000;


	@Bean
	public ConfigurationService configurationService()
	{
		return referenceBuilderFactory.builder(ConfigurationService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.timeout(MINE_SERVICE_TIMEOUT)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}


	@Bean
	public MerchantFacadeRemote merchantFacadeRemote()
	{
		return referenceBuilderFactory.builder(MerchantFacadeRemote.class)
				.version(MINE_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}

	@Bean
	public MerchantGroupFacadeRemote merchantGroupFacadeRemote()
	{
		return referenceBuilderFactory.builder(MerchantGroupFacadeRemote.class)
				.version(MINE_SERVICE_VERSION)
				.check(COMMON_USER_SERVICE_CHECK)
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
	public TaskExportRemote taskExportRemote()
	{
		return referenceBuilderFactory.builder(TaskExportRemote.class)
				.version(MINE_SERVICE_VERSION)
				.timeout(MINE_SERVICE_TIMEOUT)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	@Bean
	public SupplyChainAdminRemote supplyChainAdminRemote(){
		return referenceBuilderFactory.builder(SupplyChainAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url(MYSELEF_SERVICE_URL)
				.build();
	}

	@Bean
	public DeviceManagerAdminRemote deviceManagerAdminRemote(){
		return referenceBuilderFactory.builder(DeviceManagerAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url("dubbo://192.168.2.43:20880")
				.build();
	}

	//省份Service
	@Bean
	public ProvinceService provinceService(){
		return referenceBuilderFactory.builder(ProvinceService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.timeout(MINE_SERVICE_TIMEOUT)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	//市CityService
	@Bean
	public CityService cityService(){
		return referenceBuilderFactory.builder(CityService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.timeout(MINE_SERVICE_TIMEOUT)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}
	//区AreaService
	@Bean
	public AreaService reaService(){
		return referenceBuilderFactory.builder(AreaService.class)
				.version(COMMON_USER_SERVICE_VERSION)
				.timeout(MINE_SERVICE_TIMEOUT)
				.check(COMMON_USER_SERVICE_CHECK)
				.build();
	}

	//用户管理
	@Bean
	public DealerUserInfoAdminRemote dealerUserInfoAdminRemote(){
		return  referenceBuilderFactory.builder(DealerUserInfoAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
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

	//产品维修库
	@Bean
	public MaintainProductRemote maintainProductRemote(){
		return  referenceBuilderFactory.builder(MaintainProductRemote.class)
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
	//			.url(MYSELEF_SERVICE_URL)
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
	//			.url(MYSELEF_SERVICE_URL)
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

	@Bean
	public DeviceManagerService deviceManagerService(){
		return referenceBuilderFactory.builder(DeviceManagerService.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 查询物料
	 * @return
	 */
	@Bean
	public IMaterialDubboService iMaterialDubboService(){
		return referenceBuilderFactory.builder(IMaterialDubboService.class)
				.timeout(MATERIAL_SERVICE_TIMEOUT)
				.version(MATERIAL_SERVICE_VERSION)
				.build();
	}

	/**
	 * 销售管理
	 * @return
	 */
	@Bean
	public SalesManagerRemote SalesManagerRemote(){
		return referenceBuilderFactory.builder(SalesManagerRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
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
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 销售汇总管理
	 * @return
	 */
	@Bean
	public SalesRelationAdminRemote SalesRelationAdminRemote(){
		return referenceBuilderFactory.builder(SalesRelationAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 处理历史数据：拆分订单
	 * @return
	 */
	@Bean
	public HandleHistoricalDataRemote handleHistoricalDataRemote(){
		return referenceBuilderFactory.builder(HandleHistoricalDataRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 对账单-金融风控
	 * @return
	 */
	@Bean
	public StatementFinanceAdminRemote statementFinanceAdminRemote(){
		return referenceBuilderFactory.builder(StatementFinanceAdminRemote.class)
				.timeout(STATEMENT_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url("dubbo://192.168.1.235:20886")
				.build();
	}
    /**
     * 对账单-广汇集采
     * @return
     */
    @Bean
    public StatementCollectionAdminRemote statementCollectionAdminRemote(){
        return referenceBuilderFactory.builder(StatementCollectionAdminRemote.class)
                .timeout(STATEMENT_TIMEOUT)
                .version(MINE_SERVICE_VERSION)
    //            .url(MYSELEF_SERVICE_URL)
                .build();
    }
    
    /**
     * 对账单-经销
     * @return
     */
    @Bean
    public StatementSellAdminRemote statementSellAdminRemote(){
        return referenceBuilderFactory.builder(StatementSellAdminRemote.class)
                .timeout(STATEMENT_TIMEOUT)
                .version(MINE_SERVICE_VERSION)
    //            .url(MYSELEF_SERVICE_URL)
                .build();
    }
       

	/**
	 * 库存管理
	 * @return
	 */
	@Bean
	public ProductSplitAdminRemote productSplitAdminRemote(){
		return referenceBuilderFactory.builder(ProductSplitAdminRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
	//			.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 产品拆分
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
	
	//广汇18家下单
	@Bean
	public MotorcycleRemote motorcycleRemote(){
		return referenceBuilderFactory.builder(MotorcycleRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url("dubbo://192.168.1.235:20883")
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
	//			.url("dubbo://192.168.1.59:20885")
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
				.url(MYSELEF_SERVICE_URL)
				.build();
	}

	/**
	 * 改版设备等相关接口
	 */
	@Bean
	public JxcDeviceRemote jxcDeviceRemote(){
		return referenceBuilderFactory.builder(JxcDeviceRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.url(MYSELEF_SERVICE_URL)
				.build();
	}


	/**
	 * 商务调拨订单
	 * @return
	 */
	@Bean
	public JxcTransferOrderRemote jxcTransferOrderRemote(){
		return referenceBuilderFactory.builder(JxcTransferOrderRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				//.url(MYSELEF_SERVICE_URL)
				.build();
	}
	
	/**
	 * 服务商库存
	 * @return
	 */
	@Bean
	public StkMerchantStockRemote stkMerchantStockRemote(){
		return referenceBuilderFactory.builder(StkMerchantStockRemote.class)
				.timeout(MINE_SERVICE_TIMEOUT)
				.version(MINE_SERVICE_VERSION)
				.url(MYSELEF_SERVICE_URL)
				.build();
	}
	

}
