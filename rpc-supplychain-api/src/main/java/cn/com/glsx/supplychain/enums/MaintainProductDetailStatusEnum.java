package cn.com.glsx.supplychain.enums;

/**
 * @ClassName 维修产品库状态枚举
 * @Author admin
 * @Param
 * @Date 2019/4/10 15:45
 * @Version
 **/
public enum MaintainProductDetailStatusEnum {
    MAINTAIN_PRODUCT_STATUS_WAIT_REPAIR((byte)1,"待维修"),
    MAINTAIN_PRODUCT_STATUS_ALREADY_REPAIR((byte)2,"已维修"),
    MAINTAIN_PRODUCT_STATUS_WAIT_RETURN((byte)3,"待退货"),
    MAINTAIN_PRODUCT_STATUS_ALREADY_RETURN((byte)4,"已退货");

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

    MaintainProductDetailStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
