package cn.com.glsx.supplychain.enums;

/**
 * 产品状态
 */
public enum ProductChannelStatusEnum {
    PRODUCT_CHANNEL_STATUS_COMMON((byte)0,"通用"),
    PRODUCT_CHANNEL_STATUS_GHUI((byte) 1, "广汇"),
    PRODUCT_CHANNEL_STATUS_NOT_GHUI((byte)2, "非广汇");

    private byte code;
    private String name;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ProductChannelStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
