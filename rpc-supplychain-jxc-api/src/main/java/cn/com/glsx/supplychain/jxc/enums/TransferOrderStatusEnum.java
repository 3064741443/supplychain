package cn.com.glsx.supplychain.jxc.enums;

/**
 * 调拨订单状态
 */
public enum TransferOrderStatusEnum {

    ORDER_WAIT_CHECK("WC","待审核"),
    ORDER_WAIT_SHIPMENTS("WS","待完成"),
    ORDER_PARTIALLY_COMPLETED("PS","部分完成"),
    ORDER_COMPLETED("FA","已完成"),
    ORDER_REVIEW_REJECTED("RB","审核驳回");
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    TransferOrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
