package cn.com.glsx.supplychain.enums;

/**
 * @ClassName ProductSplitServiceTypeEnum
 * @Author admin
 * @Param
 * @Date 2019/9/23 19:52
 * @Version 1.0
 **/
public enum ProductSplitServiceTypeEnum {
    PRODUCT_SPLIT_SERVICE_TYPE_ONE((byte)1,"驾宝无忧"),
    PRODUCT_SPLIT_SERVICE_TYPE_TWO((byte)2,"金融风控"),
    PRODUCT_SPLIT_SERVICE_TYPE_THREE((byte)3,"车机"),
    PRODUCT_SPLIT_SERVICE_TYPE_FOUR((byte)4,"后视镜");
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

    ProductSplitServiceTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
