package cn.com.glsx.supplychain.enums;

/**
 * @ClassName ProductSplitMaterialTypeEnum
 * @Author LM
 * @Param
 * @Date 2019/12/12 16:36
 * @Version
 **/
public enum ProductSplitMaterialTypeEnum {
    PRODUCT_SPLIT_MATERIAL_TYPE_ZERO((byte)0,"硬件"),
    PRODUCT_SPLIT_MATERIAL_TYPE_ONE((byte)1,"网联软件"),
    PRODUCT_SPLIT_MATERIAL_TYPE_TWO((byte)2,"风险评估软件"),
    PRODUCT_SPLIT_MATERIAL_TYPE_THREE((byte)3,"风控服务"),
    PRODUCT_SPLIT_MATERIAL_TYPE_FOUR((byte)4,"安装服务"),
    PRODUCT_SPLIT_MATERIAL_TYPE_FIVE((byte)5,"智慧门店SAAS服务"),
    PRODUCT_SPLIT_MATERIAL_TYPE_SIX((byte)6,"AI车联网服务"),
    PRODUCT_SPLIT_MATERIAL_TYPE_SEVEN((byte)7,"配件"),
    PRODUCT_SPLIT_MATERIAL_TYPE_UNKNOW((byte)254,"未知");

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

    ProductSplitMaterialTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
