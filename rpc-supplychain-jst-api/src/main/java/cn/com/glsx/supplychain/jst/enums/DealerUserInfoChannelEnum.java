package cn.com.glsx.supplychain.jst.enums;


/**
 * @ClassName DealerUserInfoChannelEnum
 * @Author admin
 * @Param
 * @Date 2019/4/12 16:56
 * @Version
 **/
public enum DealerUserInfoChannelEnum {

    DEALER_USER_INFO_CHANNEL_STATUS_GHUI((byte)1,"广汇代销"),
    DEALER_USER_INFO_CHANNEL_STATUS_OTHER((byte)2,"金融风控代销"),
    DEALER_USER_INFO_CHANNEL_STATUS_TMHUI((byte)3,"同盟会渠道"),
    DEALER_USER_INFO_CHANNEL_STATUS_CHANNEL((byte)4,"金融渠道"),
    DEALER_USER_INFO_CHANNEL_STATUS_FIVE((byte)5,"亿咖通"),
    DEALER_USER_INFO_CHANNEL_STATUS_SIX((byte)6,"特殊渠道(指定产品)"),
    DEALER_USER_INFO_CHANNEL_STATUS_SEVER((byte)7,"安吉租赁"),
    DEALER_USER_INFO_CHANNEL_STATUS_EIGHT((byte)8,"广汇直营");


    private Byte code;
    private String name;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    DealerUserInfoChannelEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
