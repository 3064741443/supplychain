package cn.com.glsx.supplychain.enums;

/**
 * @ClassName StatementFinanceStatusEnum(对账单金融风控状态)
 * @Author admin
 * @Param
 * @Date 2019/9/18 10:05
 * @Version 1.0
 **/
public enum StatementFinanceStatusEnum {
    STATEMENT_FINANCE_NOT_SPLIT((byte)1,"未拆分"),
    STATEMENT_FINANCE_SPLIT_SUCCESS((byte)2,"拆分成功"),
    STATEMENT_FINANCE_SPLIT_FAIL((byte)3,"拆分失败");

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

    StatementFinanceStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
