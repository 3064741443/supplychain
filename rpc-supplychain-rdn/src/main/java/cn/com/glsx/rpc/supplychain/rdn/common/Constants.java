package cn.com.glsx.rpc.supplychain.rdn.common;

public class Constants {

	//门店订单前缀
	public static final String SHOP_ORDER_PREFIX = "JST";
	
	//物流编号前缀
	public static final String LOGI_ORDER_PREFIX = "LGI";
	
	//商户订单产品快照前缀
	public static final String BS_PRODUCT_CODE_PREFIX = "BSP";
	
	//商户订单好前缀
	public static final String BS_ORDER_PREFIX = "GXS";

	//供货关系门店前缀
	public static final String JST_SHOP = "ST";
	
	//购物车添加的最大数量
	public static final Integer JST_CART_COUNT=99999;

	//广联商务订单来源
	public static final String GXS_ORDER_SOURCE = "GXS";
	//服务商pc经销存订单来源
	public static final String SMJ_ORDER_SOURCE = "SMJ";
	//服务商JXC小程序订单来源
	public static final String SMX_ORDER_SOURCE = "SMX";
	
	//商户订单导出配置id
	public static final Integer TASK_CFG_ID_MERCHANT_ORDER = 103;
	public static final Integer TASK_CFG_ID_JXC_ORDER = 104;
	public static final Integer TASK_CFG_ID_JXC_ORDER_PURCHASE=121;
	public static final Integer TASK_CFG_ID_JXC_ORDER_BUSSCHECK = 122; //商务审核订单导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_BUSPDISP = 123; //供应链分配订单导出
	//商务系统调拨订单列表导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_BSTRNSFER = 124;
	//进销存调拨订单列表导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_JXCTRNSFER = 125;
	//库存列表导出
	public static final Integer TASK_CFG_ID_STK_MERCHANT_STOCK = 126;
	//库销比预警列表导出
	public static final Integer TASK_CFG_ID_STK_WARNING_WARESALE = 127;
	//呆滞品明细列表导出
	public static final Integer TASK_CFG_ID_STK_WARNING_DEVICESN = 128;
	//库存设备未激活统计导出列表导出
	public static final Integer TASK_CFG_ID_STK_STOCK_SN_STAT = 129;
	//库存未激活设备明细导出
	public static final Integer TASK_CFG_ID_STK_MERCHANT_STOCK_SN = 130;
	//供应链发货单导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_INVOICE = 131;
	//运营平台-设备关系明细导出
	public static final Integer TASK_CFG_ID_DEVICE_FILE = 132;
	public static final Integer TASK_CFG_ID_JXC_BILL_GEN = 999;//签收单生成

	
	//product_type / merchant_channel  产品类型和商户渠道对应配置ID
	public static final Integer PRODUCT_CONFIG_TYPE_PRODUCT_TYPE = 49;
	public static final Integer PRODUCT_CONFIG_TYPE_MERCHANT_CHANNEL = 48;
	
	public final static String EXPORT_FORMAT_MERCHANT = "商户订单列表.xlsx";
	public final static String EXPORT_NAME_MERCHANT = "商户订单记录";
	public final static String EXPORT_FOLDER_MERCHANT = "/templates/templateMerchantOrder.xlsx";
	public final static String EXPORT_FOLDER_MERCHANT_JXC = "/templates/templateMerchantOrderMerchant.xlsx";
	
	public final static String EXPORT_FORMAT_MERCHANT_ORDER_JXC = "采购订单列表.xlsx";
    public final static String EXPORT_NAME_MERCHANT_ORDER_JXC = "采购订单列表";
    public final static String EXPORT_FOLDER_MERCHANT_ORDER_JXC = "/templates/templateBsMerchantOrderJXC.xlsx";
    
    //商务审核
  	public final static String EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS = "商务订单.xlsx";
  	public final static String EXPORT_NAME_BS_MERCHANT_ORDER_BSS = "商务订单";
  	public final static String EXPORT_FOLDER_BS_MERCHANT_ORDER_BSS = "/templates/templateBusinessReviewOrder.xlsx";
  	
  	//供应链分配订单
  	public final static String EXPORT_FORMAT_BS_MERCHANT_ORDER_BSP = "供应链分配订单.xlsx";
	public final static String EXPORT_NAME_BS_MERCHANT_ORDER_BSP = "供应链分配订单";
	public final static String EXPORT_FOLDER_BS_MERCHANT_ORDER_BSP = "/templates/templateAssignOrder.xlsx";

	//商务调拨订单
	public final static String EXPORT_FORMAT_BS_TRANSFER_ORDER_BSS = "商务调拨订单.xlsx";
	public final static String EXPORT_NAME_BS_TRANSFER_ORDER_BSS = "商务调拨订单";
	public final static String EXPORT_FOLDER_BS_TRANSFER_ORDER_BSS = "/templates/templateBusinessReviewOrder.xlsx";

	//进销存调拨订单
	public final static String EXPORT_FORMAT_JXC_TRANSFER_ORDER_BSS = "进销存调拨订单.xlsx";
	public final static String EXPORT_NAME_JXC_TRANSFER_ORDER_BSS = "进销存调拨订单";
	public final static String EXPORT_FOLDER_JXC_TRANSFER_ORDER_BSS = "/templates/templateBusinessReviewOrder.xlsx";

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

	//供应链分配
	public final static String EXPORT_FORMAT_INVOICE_MERCHANT_ORDER_BSP = "发货单列表.xlsx";
	public final static String EXPORT_NAME_INVOICE_MERCHANT_ORDER_BSP = "发货单列表";

	//运营平台-设备明细列表
	public final static String EXPORT_FORMAT_DEVICE_FILE = "设备明细列表.xlsx";
	public final static String EXPORT_NAME_DEVICE_FILE = "设备明细列表";

	//货物签收单下载
	public final static String EXPORT_FORMAT_GOODS_RECEIPT = "RV.xlsx";
	public final static String EXPORT_NAME_GOODS_RECEIPT = "RV";
	public final static String EXPORT_FOLDER_GOODS_RECEIPT = "/templates/templateGoodsReceipt.xlsx";
	
  //车机
    public static final Integer PRODUCT_CONFIG_ID_PRODUCT_TYPE_CZ = 412;
    //后视镜
    public static final Integer PRODUCT_CONFIG_ID_PRODUCT_TYPE_HSJ = 413;
    //爱车保镖
    public static final Integer PRODUCT_CONFIG_ID_PRODUCT_TYPE_ACBB = 414;
    //金融风控
    public static final Integer PRODUCT_CONFIG_ID_PRODUCT_TYPE_JRFK = 415;
    //其他
    public static final Integer PRODUCT_CONFIG_ID_PRODUCT_TYPE_ORTHER = 416;
    //id基数
    public static final Integer PRODUCT_CONFIG_ID_PRODUCT_TYPE_BASE = 400;
    
    //广汇代销
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_GHDX = 421;
    //金融风控代销
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_JRFKDX = 422;
    //同盟会渠道
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_TMHQD = 423;
    //金融渠道
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_JRQD = 424;
    //亿咖通
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_YKT = 425;
    //特殊渠道（指定产品）
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_TSQD = 426;
    //安吉租赁
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_AJZL = 427;
    //广汇直营
    public static final Integer PRODUCT_CONFIG_ID_MERCHANT_CHANNEL_GHZY = 428;

	public static final long MillSecondsInOneDay = 86400000;
}
