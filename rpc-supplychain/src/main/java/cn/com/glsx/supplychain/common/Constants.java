package cn.com.glsx.supplychain.common;

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
	
	//商户订单导出配置id
	public static final Integer TASK_CFG_ID_MERCHANT_ORDER = 103;
	public static final Integer TASK_CFG_ID_JXC_ORDER = 104;
	
	
	
	public final static String NUMBER_HEAD_FINANCE_SPLIT = "01";
	public final static String NUMBER_HEAD_FINANCE_COMBILE = "02";
	public final static String NUMBER_HEAD_COLLECTION_SPLIT = "03";
	public final static String NUMBER_HEAD_COLLECTION_COMBILE = "04";
	public final static String  NUMBER_HEAD_SELL_SPLIT = "05";
	
	public final static String bill_type_code = "XSDD01_SYS";
	public final static String bill_type_name = "标准销售订单";
	public final static String statement_organize_code_fk = "G04";
	public final static String statement_organize_name_fk = "经销(GPS风控)";
	public final static String statement_organize_code_tmh = "G03";
	public final static String statement_organize_name_tmh = "经销(同盟会)";
	public final static String statement_organize_code = "101";
	public final static String statement_organize_name = "深圳广联赛讯有限公司";
	public final static String statement_organize_addr = "深圳南山区崇文园区1栋14楼";
	public final static String statement_organize_contact = "程盼";
	public final static String statement_organize_phone = "18675505424";
	public final static String statement_currency_code = "PRE001";
	public final static String statement_currency_name = "人民币";
	public final static String sales_unit_code = "Pcs";
	public final static String sales_unit_name = "Pcs";
	public final static String reserve_type = "弱预留";
	
	
	public final static Integer MAX_WORK_LENGTH = 32;
	public final static Integer MAX_WAREHOUSE_CODE_LENGTH = 32;
	public final static Integer MERCHANT_CHANNEL_CONVERT_BASE = 200;
	public final static Integer MERCHANT_CHANNEL_CONFIG_TYPE = 14;
}
