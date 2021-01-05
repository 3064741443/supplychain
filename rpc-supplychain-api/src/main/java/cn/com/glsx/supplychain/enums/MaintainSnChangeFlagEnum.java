package cn.com.glsx.supplychain.enums;

/**
 * @ClassName 维修SN切换标识枚举
 * @Author admin
 * @Param
 * @Date 2019/4/10 15:45
 * @Version
 **/
public enum MaintainSnChangeFlagEnum {
    MAINTAIN_SN_CHANGE_FLAG_YES((byte)1,"是"),
    MAINTAIN_SN_CHANGE_FLAG_NO((byte)2,"否");

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

    MaintainSnChangeFlagEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
