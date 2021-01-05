package cn.com.glsx.supplychain.enums;

public enum AfterSaleOrderDetailStatusEnum {

    AFTER_SALE_ORDER_APPLY_SN((byte) 1, "申请售后SN"),
    AFTER_SALE_ORDER_SIGN_SN((byte) 2, "实际签收SN");

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

    AfterSaleOrderDetailStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
