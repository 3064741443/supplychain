package cn.com.glsx.supplychain.enums;


/**
 * @ClassName DealerUserInfoSaleModeEnum
 * @Author admin
 * @Param
 * @Date 2019/4/12 16:56
 * @Version
 **/
public enum DealerUserInfoSaleModeEnum {

    DEALER_USER_INFO_SALE_MODE_STATUS_ONE((byte)1,"经销"),
    DEALER_USER_INFO_SALE_MODE_STATUS_TWO((byte)2,"代销");

    private Byte code;
    private String name;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    DealerUserInfoSaleModeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
