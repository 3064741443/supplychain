package cn.com.glsx.supplychain.jxc.enums;

/**
 * 商户订单状态
 */
public enum MerchantOrderStatusEnum {

    ORDER_ALREADY_COUNTERMAND((byte)0,"已驳回"),
    ORDER_WAIT_CHECK((byte)1,"待审核"),
    ORDER_WAIT_SHIPMENTS((byte)2,"待发货"),
    ORDER_WAIT_RECEIVE((byte)3,"部分发货"), //待签收
    ORDER_ALREADY_FINISH((byte)5,"完成发货"), //已完成
    ORDER_ALREADY_INVALID((byte)7,"提前结束"), //已作废
    ORDER_WAIT_DISPATCH((byte)8,"待分配"),
    ORDER_ALREADY_CANCEL((byte)9,"已取消");
    

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

    MerchantOrderStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
