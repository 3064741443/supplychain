package cn.com.glsx.supplychain.enums;

/**
 * @ClassName 维修产品库状态枚举
 * @Author admin
 * @Param
 * @Date 2019/4/10 15:45
 * @Version
 **/
public enum MaintainProductStatusEnum {
    MAINTAIN_PRODUCT_STATUS_WAIT_REPAIR((byte)0,"待维修"),
    MAINTAIN_PRODUCT_STATUS_REPAIR_IN((byte)1,"维修中"),
    MAINTAIN_PRODUCT_STATUS_CANCEL_REPAIR((byte)2,"取消维修"),
    MAINTAIN_PRODUCT_STATUS_ALREADY_FINISH((byte)3,"已完成"),
    MAINTAIN_PRODUCT_STATUS_WAIT_RETURN((byte)4,"待退货");

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

    MaintainProductStatusEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
