package glsx.com.cn.task.util;

public enum DeviceImeiStockStatusEnum {

    DEVICE_IMEI_STOCK_STATUS_ENUM_IN("IN"),
    DEVICE_IMEI_STOCK_STATUS_ENUM_EX("EX");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private DeviceImeiStockStatusEnum(String code) {
        this.code = code;
    }
}
