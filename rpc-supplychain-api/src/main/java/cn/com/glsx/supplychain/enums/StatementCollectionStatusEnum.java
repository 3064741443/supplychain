package cn.com.glsx.supplychain.enums;

/**
 * @ClassName StatementCollectionStatusEnum(对账单广联采集状态)
 * @Author admin
 * @Param
 * @Date 2019/9/18 10:05
 * @Version 1.0
 **/
public enum StatementCollectionStatusEnum {
    STATEMENT_COLLECTION_NOT_SPLIT((byte)1,"未拆分"),
    STATEMENT_COLLECTION_SPLIT_SUCCESS((byte)2,"拆分成功"),
    STATEMENT_COLLECTION_SPLIT_FAIL((byte)3,"拆分失败");

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

    StatementCollectionStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
