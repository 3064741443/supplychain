package cn.com.glsx.supplychain.config;

/**
 * 设置常量
 * @author zhoudan
 */
public class Constants {
	public final static String EXPORT_FOLDER = "/templates/template.xlsx";
	public final static String EXPORT_FORMAT = "设备信息.xlsx";
	public final static String EXPORT_NAME = "设备信息";
	public final static int targetPage = -1;
	public final static int targetPageNew = 1;
	public final static int pageSize = -1;
	public final static int pageSizeNew = 10;
	public final static int pageSizeMer = 20;
	
	public final static String EXPORT_FORMAT_TWO = "出货明细.xlsx";
	public final static String EXPORT_NAME_TWO = "出货明细";
	public final static String EXPORT_FOLDER_TWO = "/templates/templatetwo.xlsx";
	
	public final static String EXPORT_FORMAT_DMANA = "设备分类列表.xlsx";
	public final static String EXPORT_NAME_DMANA = "设备分类";
	public final static String EXPORT_FOLDER_DMANA = "/templates/templateDeviceMana.xlsx";
	
	public final static String EXPORT_FORMAT_DCHANGE = "设备更换记录.xlsx";
	public final static String EXPORT_NAME_DCHANGE = "设备更换记录";
	public final static String EXPORT_FOLDER_DCHANGE = "/templates/templateDeviceChange.xlsx";
	
	public final static String EXPORT_FORMAT_DDETAIL = "设备关系明细.xlsx";
	public final static String EXPORT_NAME_DDETAIL = "设备关系明细";
	public final static String EXPORT_FOLDER_DDETAIL = "/templates/templateDeviceDetail.xlsx";
	
	public final static String EXPORT_FORMAT_DINIT = "设备初始化记录.xlsx";
	public final static String EXPORT_NAME_DINIT = "设备初始化记录";
	public final static String EXPORT_FOLDER_DINIT = "/templates/templateDeviceInit.xlsx";

	public final static String EXPORT_FORMAT_SALES = "销售汇总列表.xlsx";
	public final static String EXPORT_NAME_SALES = "销售汇总记录";
	public final static String EXPORT_FOLDER_SALES = "/templates/templateSalesSummarizing.xlsx";

	public final static String EXPORT_FORMAT_MERCHANT = "商户订单列表.xlsx";
	public final static String EXPORT_NAME_MERCHANT = "商户订单记录";
	public final static String EXPORT_FOLDER_MERCHANT = "/templates/templateMerchantOrder.xlsx";

	public final static String EXPORT_FORMAT_STATEMENT_COLLECTION_SPLIT = "对账单-广汇集采(拆分)列表.xlsx";
	public final static String EXPORT_NAME_STATEMENT_COLLECTION_SPLIT = "销售订单#基本信息(FBillHead)";
	public final static String EXPORT_FOLDER_STATEMENT_COLLECTION_SPLIT = "/templates/templateStatementCollectionSplitExport.xlsx";

	public final static String EXPORT_FORMAT_STATEMENT_FINANCE_SPLIT = "对账单-金融风控(拆分)列表.xlsx";
	public final static String EXPORT_NAME_STATEMENT_FINANCE_SPLIT = "销售订单#基本信息(FBillHead)";
	public final static String EXPORT_FOLDER_STATEMENT_FINANCE_SPLIT = "/templates/templateStatementFinanceSplitExport.xlsx";
	
	public final static String EXPORT_FORMAT_STATEMENT_SELL_SPLIT = "对账单-经销(拆分)列表.xlsx";
	public final static String EXPORT_NAME_STATEMENT_SELL_SPLIT = "销售订单#基本信息(FBillHead)";
	public final static String EXPORT_FOLDER_STATEMENT_SELL_SPLIT = "/templates/templateStatementSellSplitExport.xlsx";
	
	public final static String EXPORT_FORMAT_STATEMENT_FINANCE_COMBILE = "对账单-金融风控(汇总)列表.xlsx";
	public final static String EXPORT_NAME_STATEMENT_FINANCE_COMBILE = "销售订单#基本信息(FBillHead)";
	public final static String EXPORT_FOLDER_STATEMENT_FINANCE_COMBILE = "/templates/templateStatementFinanceSplitExport.xlsx";
	
	public final static String EXPORT_FORMAT_STATEMENT_FINANCE = "对账单-金融风控(导入)列表.xlsx";
	public final static String EXPORT_NAME_STATEMENT_FINANCE = "对账单-金融风控(导入)记录";
	public final static String EXPORT_FOLDER_STATEMENT_FINANCE = "/templates/templateStatementFinance.xlsx";
	
	
	public final static String EXPORT_FORMAT_MERCHANT_STOCK = "商户库存列表.xlsx";
	public final static String EXPORT_NAME_MERCHANT_STOCK = "商户库存记录";
	public final static String EXPORT_FOLDER_MERCHANT_STOCK = "/templates/templateMerchantStock.xlsx";

	public final static String EXPORT_FORMAT_STATEMENT_ACCOUNT_JR = "对账单-经销(金融风控).xlsx";
	public final static String EXPORT_NAME_STATEMENT_ACCOUNT_JR = "对账单-经销(金融风控)";
	public final static String EXPORT_FOLDER_STATEMENT_ACCOUNT_JR = "/templates/templateStatementAccoutJR.xlsx";

	public final static String EXPORT_FORMAT_STATEMENT_ACCOUNT_JX = "对账单-经销(其他).xlsx";
	public final static String EXPORT_NAME_STATEMENT_ACCOUNT_JX = "对账单-经销(其他)";
	public final static String EXPORT_FOLDER_STATEMENT_ACCOUNT_JX = "/templates/templateStatementAccoutJX.xlsx";
	
	//商务审核
	public final static String EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS = "商务订单.xlsx";
	public final static String EXPORT_NAME_BS_MERCHANT_ORDER_BSS = "商务订单";
	public final static String EXPORT_FOLDER_BS_MERCHANT_ORDER_BSS = "/templates/templateBusinessReviewOrder.xlsx";
	
	//供应链分配
	public final static String EXPORT_FORMAT_BS_MERCHANT_ORDER_BSP = "供应链分配.xlsx";
	public final static String EXPORT_NAME_BS_MERCHANT_ORDER_BSP = "供应链分配";
	public final static String EXPORT_FOLDER_BS_MERCHANT_ORDER_BSP = "/templates/templateAssignOrder.xlsx";

	//供应链分配
	public final static String EXPORT_FORMAT_INVOICE_MERCHANT_ORDER_BSP = "发货单列表.xlsx";
	public final static String EXPORT_NAME_INVOICE_MERCHANT_ORDER_BSP = "发货单列表";

	//运营平台-设备明细列表
	public final static String EXPORT_FORMAT_DEVICE_FILE = "设备明细列表.xlsx";
	public final static String EXPORT_NAME_DEVICE_FILE = "设备明细列表";

	//商务调拨订单
	public final static String EXPORT_FORMAT_BS_TRANSFER_ORDER_BSS = "商务调拨订单.xlsx";
	public final static String EXPORT_NAME_BS_TRANSFER_ORDER_BSS = "商务调拨订单";
	public final static String EXPORT_FOLDER_BS_TRANSFER_ORDER_BSS = "/templates/templateBusinessReviewOrder.xlsx";

	//库存列表
	public final static String EXPORT_FORMAT_STK_MERCHANT_STOCK = "库存列表.xlsx";
	public final static String EXPORT_NAME_STK_MERCHANT_STOCK = "库存列表";

	//库销比预警列表
	public final static String EXPORT_FORMAT_STK_WARNING_WARESALE = "库销比预警列表.xlsx";
	public final static String EXPORT_NAME_STK_WARNING_WARESALE = "库销比预警列表";

	//呆滞品明细列表
	public final static String EXPORT_FORMAT_STK_WARNING_DEVICESN = "呆滞品明细列表.xlsx";
	public final static String EXPORT_NAME_STK_WARNING_DEVICESN = "呆滞品明细列表";

	//库存设备未激活统计列表
	public final static String EXPORT_FORMAT_STK_STOCK_SN_STAT= "库存设备未激活统计列表.xlsx";
	public final static String EXPORT_NAME_STK_STOCK_SN_STAT = "库存设备未激活统计列表";

	//库存未激活设备明细
	public final static String EXPORT_FORMAT_STK_MERCHANT_STOCK_SN = "库存未激活设备明细列表.xlsx";
	public final static String EXPORT_NAME_STK_MERCHANT_STOCK_SN = "库存未激活设备明细列表";


	public final static Integer MAX_WORK_LENGTH = 32;
	public final static Integer MAX_WAREHOUSE_CODE_LENGTH = 32;
	public final static Integer MERCHANT_CHANNEL_CONVERT_BASE = 200;
	public final static Integer MERCHANT_CHANNEL_CONFIG_TYPE = 14;
	
	public final static Integer GH_ATTRIB_INFO_TYPE_PARENT_BRAND = 51;
	public final static Integer GH_ATTRIB_INFO_TYPE_CATEGORY = 27;

	//广联商务订单来源
	public static final String GXS_ORDER_SOURCE = "GXS";
	//服务商pc经销存订单来源
	public static final String SMJ_ORDER_SOURCE = "SMJ";
	//服务商JXC小程序订单来源
	public static final String SMX_ORDER_SOURCE = "SMX";

}
