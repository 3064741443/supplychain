package cn.com.glsx.supplychain.enums;

public enum AfterSaleOrderTypeEnum {

    AFTER_SALE_ORDER_RETURN((byte) 1, "退货"),
    AFTER_SALE_ORDER_REPAIR((byte) 2, "维修");

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

    AfterSaleOrderTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
