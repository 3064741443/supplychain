package cn.com.glsx.supplychain.jst.common;

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

    //供货关系门店前缀
    public static final String JST_SHOP = "ST";

    //广汇购物车编码前缀
    public static final String GH_SHOP_CART_PREFIX = "GHSC";

    //广汇订单前缀
    public static final String GH_ORDER_PREFIX = "GHT";

    //虚拟SN前缀
    public static final String VT_SN_PREFIX = "VJSN";

    //购物车添加的最大数量
    public static final Integer JST_CART_COUNT = 99999;
    public static final Integer GH_CART_COUNT = 9999;

    public static final String SYSPRODUCTCODE = "GLSXPDTCODE01";
    public static final String SYSPRODUCTNAME = "未知";
    public static final String SYSMATERIALCODE = "GLSXMTCODE01";
    public static final String SYSMATERIALNAME = "未知";
    public static final Byte SYSSERVICETYPE = 5;
    public static final String SYSSERVICETIME = "12";
    public static final String SYSPACKAGEONE = "未知";

    public static final Integer PRODUCT_CONFIG_CATEGORY_CZDH = 512;
    public static final Integer PRODUCT_CONFIG_CATEGORY_AZBB = 513;
    public static final Integer PRODUCT_CONFIG_CATEGORY_DZFZ = 514;
    public static final Integer PRODUCT_CONFIG_CATEGORY_PJ = 515;

    public static final Integer PRODUCT_CONFIG_PARENT_BRAND_TONGYONG = 1003;
    public static final Integer PRODUCT_CONFIG_SUB_BRAND_TONGYONG = 1009;

    /**
     * 线上配送
     */
    public static final String ONLINE_DELIVERY = "O";

    /**
     * 线下配送
     */
    public static final String OFFLINE_DELIVERY = "F";

    /**
     * 扫码发货
     */
    public static final String SCAN_CODE = "N";

    /**
     * 不扫码发货
     */
    public static final String NO_SCAN_CODE = "Y";

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

    public static final String ORDER_REMINDER_MAIL_HEAD = "直营店发起催单!";
    public static final String ORDER_REMINDER_MAIL_CONTENT = "［{0}］［{1}］已发起［{2}］次催单提醒，请尽快安排发货!";
    // 大于等于5次的提醒内容
    public static final String ORDER_REMINDER_MAIL_CONTENT_REPEAT = "［{0}］［{1}］已发起［{2}］次催单提醒，请与客户联系，并说明延迟发货原因!";

}
