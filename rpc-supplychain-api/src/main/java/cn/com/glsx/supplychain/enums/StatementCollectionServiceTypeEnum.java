package cn.com.glsx.supplychain.enums;

/**
 * @ClassName StatementCollectionServiceTypeEnum
 * @Author admin
 * @Param
 * @Date 2019/9/18 11:21
 * @Version 1.0
 **/
public enum StatementCollectionServiceTypeEnum {

    STATEMENT_COLLECTION_SERVICE_TYPE_ONE((byte)1,"驾宝无忧"),
    STATEMENT_COLLECTION_SERVICE_TYPE_TWO((byte)2,"金融风控"),
    STATEMENT_COLLECTION_SERVICE_TYPE_THREE((byte)3,"车机"),
    STATEMENT_COLLECTION_SERVICE_TYPE_FOUR((byte)4,"后视镜"),
    STATEMENT_COLLECTION_SERVICE_TYPE_FIVE((byte)5,"车机和后视镜");

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

    StatementCollectionServiceTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
