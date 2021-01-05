package cn.com.glsx.supplychain.enums;

/**
 * @ClassName SalesReconciliationStatus
 * @Author leiming
 * @Param
 * @Date 2019/4/16 14:37
 * @Version
 **/
public enum SalesReconciliationStatus {
    SALES_STATUS_NOT_RECONCILATION((byte)1,"未对账"),
    SALES_STATUS_ALREADY_RECONCILATION((byte)2,"已对账"),
    SALES_STATUS_ALREADY_FINISH((byte)3,"已完成");

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

    SalesReconciliationStatus(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
