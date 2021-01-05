package cn.com.glsx.supplychain.enums;

/**
 * 产品状态
 */
public enum ProductStatusEnum {

    PRODUCT_STATUS_PUTAWAY((byte) 1, "上架"),
    PRODUCT_STATUS_SOLD_OUT((byte)2, "下架");

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

    ProductStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
