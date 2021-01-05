package cn.com.glsx.supplychain.jst.common;

/**
 * 常量类
 *
 * @author liuyf@didihu.com.cn
 * @version 1.0
 * @history 2017年9月20日 create by liuyf@didihu.com.cn
 */
public final class Constants {

    /**
     * 生成随机种子
     */
    public static final String NAME_SEED = "ABCDEFGHJKMNPQRSTUVWXYZ23456789";

    /**
     * 登录用户在SESSION中的KEY值
     */
    public final static String SESSION_LOGIN_USER = "_session_user";

    /**
     * 用户可用状态
     */
    public final static int USER_AVAILABLE_STATUS = 0;

    /**
     * 用户不可用状态
     */
    public final static int USER_UNAVAILABLE_STATUS = 1;

    /**
     * 本季度
     */
    public static final int TIMETYPE_CURRENT_QUARTER = 15;

    /**
     * 展业至今
     */
    public static final int TIMETYPE_SOFAR = 16;

    /**
     * 今天
     */
    public static final int TIMETYPE_TODAY = 1;
    /**
     * 昨天
     */
    public static final int TIMETYPE_YESTERDAY = 2;
    /**
     * 本周
     */
    public static final int TIMETYPE_THISWEEK = 3;
    /**
     * 上周
     */
    public static final int TIMETYPE_LASTWEEK = 4;
    /**
     * 本月
     */
    public static final int TIMETYPE_THISMONTH = 5;
    /**
     * 上月
     */
    public static final int TIMETYPE_LASTMONTH = 6;
    /**
     * 时间段
     */
    public static final int TIMETYPE_SCOPE = 7;
    /**
     * 时间范围 近12小时
     */
    public static final int TIMETYPE_IN_12_HOURS = 8;
    /**
     * 时间范围 近24小时
     */
    public static final int TIMETYPE_IN_24_HOURS = 9;
    /**
     * 近七天
     */
    public static final int TIMETYPE_IN_7_DAYS = 10;
    /**
     * 近30天
     */
    public static final int TIMETYPE_IN_30_DAYS = 11;
    /**
     * 时间段 带时间
     */
    public static final int TIMETYPE_SCOPE_WITH_TIME = 12;

    /**
     * 今年
     */
    public static final int TIMETYPE_YEAR = 14;


    /**
     * 主从设备标识：0主设备，1从设备
     */
    public static final int TRACK_MAIN = 0;
    public static final int TRACK_ISSUB = 1;

    /**
     * 设备激活状态：0未激活，1激活
     */
    public static final int TRACK_NOT_ACTIVE = 0;
    public static final String TRACK_NOT_ACTIVE_VALUE = "否";
    public static final int TRACK_ACTIVE = 1;
    public static final String TRACK_ACTIVE_VALUE = "是";

    /**
     * 车辆状态:0正常,1删除
     */
    public static final int VEHICLE_NORMAL = 0;
    public static final int VEHICLE_DELETE = 1;

    /**
     * 车辆使用中状态
     */
    public static final int VEHICLE_ISUSE_NORMAL = 2;

    /**
     * 车辆工单状态：0：正常 1：已激活 2：已删除
     */
    public static final int VEHICLE_WORKORDER_NORMAL = 0;
    public static final int VEHICLE_WORKORDER_ACTIVE = 1;
    public static final int VEHICLE_WORKORDER_DELETE = 2;

    /**
     * 车辆分类默认id
     */
    public static final int CLASS_INITIAL_ID = 1;
    /**
     * 车辆分组默认id
     */
    public static final int DEFAULT_GROUP_ID = 1;

    /**
     * 角色车辆关系类型：车辆分组
     */
    public static final int ROLE_VEHICLE_TYPE_GROUP = 0;
    /**
     * 角色车辆关系类型：车辆分类
     */
    public static final int ROLE_VEHICLE_TYPE_CLASS = 1;
    /**
     * 角色车辆关系类型：车辆
     */
    public static final int ROLE_VEHICLE_TYPE_VEHICLE = 2;


    /**
     * 查询类型：0:无条件,1设备号,2：姓名,3：车架号,4：工单号
     **/
    public static final int QUERY_TYPE_NO = 0;
    public static final int QUERY_TYPE_SN = 1;
    public static final int QUERY_TYPE_SATANDNO = 2;
    public static final int QUERY_TYPE_NAME = 3;
    public static final int QUERY_TYPE_WORKORDER_NO = 4;

    /**
     * 是否有源设备:Y有源设备,N无源设备
     */
    public static final String HASSOURCE_Y = "Y";
    public static final String HASSOURCE_N = "N";

    /**
     * 设备型号类型
     */
    public static final String GT700 = "GT700";    //81  91  21
    public static final String GT700B = "GT700B"; //15
    public static final String GT700C = "GT700C"; //22
    public static final String GT700F = "GT700F"; //11
    public static final String GT701 = "GT701";    //13
    public static final String GT701C = "GT701C"; //18  24
    public static final String GT702 = "GT702";    //61 71
    public static final String GT703 = "GT703";    //31 51
    public static final String GT703B = "GT703B";  //16 14  69
    public static final String GT703C = "GT703C";  //23
    public static final String GT703W = "GT703W";  //53
    public static final String GT705W = "GT705W";  //19
    public static final String GT706 = "GT706";    //12
    public static final String GT701a = "GT701A"; //41
    public static final String GT801 = "GT801";   //25
    public static final String GT801C = "GT801C";   //17
    public static final String GT801D = "GT801D";  //80
    public static final String GT800B = "GT800B";   //25
    public static final String GT803 = "GT803";    //26
    public static final String GT803B = "GT803B";   //29

    //服务商JXC小程序订单来源
    public static final String SMX_ORDER_SOURCE = "SMX";

    /**
	 * <P>Description： session键定义</P>
	 * @author Alvin
	 * @version 1.0
	 */
	public enum SessionKey {
		
		// 账户ID SESSION键
		ACCOUNT_ID("SESSION_ACCOUNT_ID"),
		// 协议号(原嘀嘀号)SESSION键
		PROTOCOL_ID("SESSION_PROTOCOL_ID"),
		IMEI("IMEI"),
		// 商户号 SESSION键
		MERCHANT_ID("SESSION_MERCHANT_ID"),
		// 小程序信息  WEAPP_INFO键
		WEAPP_INFO_ID("SESSION_WEAPP_INFO"),
		// 用户SESSION键
		SESSION_USERINFO_KEY("SESSION_USERINFO_KEY"),
		// 请求参数 SESSION键
		REQUEST_PARAMS_KEY("SESSION_REQUEST_PARAMS_KEY"),
		// UNIONID SESSION键
		UNIONID("SESSION_UNIONID"),
		// OPENID SESSION键
		OPENID("SESSION_OPENID"),
		// WECHATID SESSION键
		WECHATID("SESSION_WECHATID"),
		// WEAPP_USERINFO_KEY键
		WEAPP_USERINFO_KEY("SESSION_WEAPP_USERINFO_KEY"),
		// WEAPP_SESSION_KEY键
		WEAPP_SESSION_KEY("SESSION_WEAPP_SESSION_KEY"),
		// PLATFORM_SESSIONID_KEY
		PLATFORM_SESSIONID_KEY("PLATFORM_SESSIONID_KEY");
		
		private final String value;
		
		SessionKey(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}

}