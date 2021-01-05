package cn.com.glsx.supplychain.enums;

/**
 * @ClassName ProductSplitChannelEnum
 * @Author admin
 * @Param
 * @Date 2019/9/18 11:32
 * @Version
 **/
public enum  ProductSplitChannelEnum {
    PRODUCT_SPLIT_CHANNEL_STATUS_ALL((byte)0,"全部"),
    PRODUCT_SPLIT_CHANNEL_STATUS_GHUI((byte)1,"广汇"),
    PRODUCT_SPLIT_CHANNEL_STATUS_TMHUI((byte)2,"同盟会");


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

    ProductSplitChannelEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
