package cn.com.glsx.supplychain.jxc.common;

public class Constants {

	//门店订单前缀
	public static final String SHOP_ORDER_PREFIX = "JST";
	
	//无订单发货订单前缀
	public static final String SHOP_ORDER_PREFIX_AUTO = "AST";
	
	//物流编号前缀
	public static final String LOGI_ORDER_PREFIX = "LGI";

	//线下配送物流编号前缀
	public static final String OFFLINE_ORDER_PREFIX = "VJSL";

	//商户订单产品快照前缀
	public static final String BS_PRODUCT_CODE_PREFIX = "BSP";

	//商户订单好前缀
	public static final String BS_ORDER_PREFIX = "GXS";
	
	//商户总订单号前缀
	public static final String JXC_ORDER_PREFIX = "JXC";

    //调拨订单号前缀
    public static final String TRAN_ORDER_PREFIX = "DBDD";

	//供货关系门店前缀
	public static final String JST_SHOP = "ST";

	//广汇购物车编码前缀
	public static final String GH_SHOP_CART_PREFIX = "GHSC";

	//广汇订单前缀
	public static final String GH_ORDER_PREFIX = "GHT";
	
	//预警编号前缀
	public static final String STKM_WARNING_PREFIX = "STKWC";
	
	//设备库存产品配置 操作码前缀
	public static final String STKM_PRODUCTCONFIG_OPERATOR_PREFIX = "STKOP";
	//设备库存产品配置  配置码前缀
	public static final String STKM_PRODUCTCONFIG_CONFIG_PREFIX = "STKPC";

    //调拨-物流配送
    public static final String LOGISTIC_SEND= "L";

    //调拨-线下配送
    public static final String OFFLINE_DELIVERY = "O";

	//购物车添加的最大数量
	public static final Integer JST_CART_COUNT=99999;
	public static final Integer GH_CART_COUNT = 9999;

	public static final String SYSPRODUCTCODE = "GLSXPDTCODE01";
    public static final String SYSPRODUCTNAME = "未知";
    public static final String SYSMATERIALCODE = "GLSXMTCODE01";
    public static final String SYSMATERIALNAME = "未知";
    public static final Byte SYSSERVICETYPE = 5;
    public static final String SYSSERVICETIME = "12";
    public static final String SYSPACKAGEONE = "未知";
    
    public static final Integer PRODUCT_CONFIG_TYPE_PRODUCT_TYPE = 49;
    public static final Integer PRODUCT_CONFIG_TYPE_MERCHANT_CHANNEL = 48;
    
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
    
    //物流表数据类型
    //商户订单地址信息
    public static final Byte LOGISTICS_TYPE_ONE = 1;
    //售后订单物流信息
    public static final Byte LOGISTICS_TYPE_TWO = 2;
    //售后订单维修物流信息
    public static final Byte LOGISTICS_TYPE_THREE = 3;
    //扫码工具物流信息
    public static final Byte LOGISTICS_TYPE_FOUR = 4;
    //商户订单直接发货信息
    public static final Byte LOGISTICS_TYPE_FIVE = 5;
    //商户下门店订单地址
    public static final Byte LOGISTICS_TYPE_SIX = 6;
    //门店订单发货明细
    public static final Byte LOGISTICS_TYPE_SEVEN = 7;

    //设备大类对应的Type
    /**
     * 车载导航
     */
    public static final int ATTRIB_INFO_CAR_NAVIGATION=7;

    /**
     * 后视镜
     */
    public static final int ATTRIB_INFO_REARVIEW_MIRROR=8;

    /**
     * 行车记录仪
     */
    public static final int ATTRIB_INFO_DRIVING_RECORDER=9;

    /**
     * GPS/车载精灵
     */
    public static final int ATTRIB_INFO_GPS=10;

    /**
     * OBD
     */
    public static final int ATTRIB_INFO_OBD=15;
    
    public static final Integer TASK_CFG_ID_MERCHANT_ORDER = 103;
	public static final Integer TASK_CFG_ID_JXC_ORDER = 104;
	//采购订单导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_PURCHASE=121;
	//商务审核订单导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_BUSSCHECK = 122;
	//供应链分配订单导出
	public static final Integer TASK_CFG_ID_JXC_ORDER_BUSPDISP = 123;
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
	//签收单生成
	public static final Integer TASK_CFG_ID_JXC_BILL_GEN = 999;
	
	public static final long MillSecondsInOneDay = 86400000;

}
