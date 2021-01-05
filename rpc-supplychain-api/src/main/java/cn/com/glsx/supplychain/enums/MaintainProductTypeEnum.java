package cn.com.glsx.supplychain.enums;

/**
 * @ClassName 维修产品库内容枚举
 * @Author admin
 * @Param
 * @Date 2019/4/10 15:45
 * @Version
 **/
public enum  MaintainProductTypeEnum {
    REPLACE_SCREEN((byte)0,"更换屏幕"),
    REPLACE_BOARD((byte)1,"更换主板"),
    REPLACE_SIM_CARD((byte)2,"更换mis卡"),
    OTHER((byte)3,"其他");

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

    MaintainProductTypeEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
