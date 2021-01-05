package cn.com.glsx.supplychain.enums;

/**
 * 汇总状态
 */
public enum SalesSummarizingStatusEnum {

    SALES_SUMMARIZING_STATUS_RECONCILATION((byte) 1, "未对账"),
    SALES_SUMMARIZING_STATUS_SUBMIT((byte)2, "待审核"),
    SALES_SUMMARIZING_STATUS_AMENDMENT((byte)3, "待修正"),
    SALES_SUMMARIZING_STATUS_ACCOMPLISH((byte)4, "已完成"),
    SALES_SUMMARIZING_STATUS_ALREADY_EXPORT((byte)5, "已导出");

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

    SalesSummarizingStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
