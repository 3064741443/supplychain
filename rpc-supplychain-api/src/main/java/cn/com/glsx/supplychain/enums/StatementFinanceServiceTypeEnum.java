package cn.com.glsx.supplychain.enums;

/**
 * @ClassName StatementFinanceServiceTypeEnum
 * @Author admin
 * @Param
 * @Date 2019/9/18 11:19
 * @Version 1.0
 **/
public enum StatementFinanceServiceTypeEnum {

    STATEMENT_FINANCE_SERVICE_TYPE_ONE((byte)1,"驾宝无忧"),
    STATEMENT_FINANCE_SERVICE_TYPE_TWO((byte)2,"金融风控和"),
    STATEMENT_FINANCE_SERVICE_TYPE_THREE((byte)3,"车机"),
    STATEMENT_FINANCE_SERVICE_TYPE_FOUR((byte)4,"后视镜");

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

    StatementFinanceServiceTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
