package cn.com.glsx.supplychain.enums;

public enum AfterSaleOrderStatusEnum {

    AFTER_SALE_ORDER_WAIT_CHECK((byte) 1, "待审核"),
    AFTER_SALE_ORDER_WAIT_SEND((byte) 2, "待寄回"),
    AFTER_SALE_ORDER_WAIT_SHIPMENTS((byte) 3, "待发货"),
    AFTER_SALE_ORDER_WAIT_RECEIVE((byte) 4, "待签收"),
    AFTER_SALE_ALREADY_FINISH((byte) 5, "已完成"),
    AFTER_SALE_ALREADY_REJECT((byte) 6, "已驳回"),
    AFTER_SALE_ALREADY_COUNTERMAND((byte) 7, "已取消"),
    AFTER_SALE_ALREADY_SEND((byte) 8, "已寄回"),
    AFTER_SALE_PORTION_SHIPMENTS((byte) 9, "部分发货"),
    AFTER_SALE_PORTION_RECEIVE((byte) 10, "部分签收");

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

    AfterSaleOrderStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
