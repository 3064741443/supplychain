package glsx.com.cn.task.enums;

/**
 * @ClassName ProductTypeEnum
 * @Author admin
 * @Param
 * @Date 2019/5/7 15:53
 * @Version
 **/
public enum ProductHistoryPriceEnum {

    PRODUCT_HISTORY_PRICE_NOW((byte)0,"当前价格"),
    PRODUCT_HISTORY_PRICE_TOMORROW((byte)1,"未来价格");

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

    ProductHistoryPriceEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
