package cn.com.glsx.supplychain.enums;

/**
 * 物流类型
 */
public enum LogisticsTypeEnum {

    MERCHANT_ORDER((byte)1,"商户订单物流信息"),
    AFTER_SALE_ORDER((byte)2,"售后订单回寄物流信息"),
    AFTER_SALE_ORDER_MAINTAIN((byte)3,"售后订单发货流信息"),
    SEND_ORDER_NO((byte)4,"售后订单发货流信息"),
    SEND_ORDER_DELIVER_SHIMENT((byte)5,"直接发货单");

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

    LogisticsTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
