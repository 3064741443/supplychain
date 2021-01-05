package cn.com.glsx.supplychain.jst.enums;

public enum BsMerchantStockDeviceStatusEnum {

    IN("IN"),
    OD("OD"),
    OS("OS"),
    RT("RT");

    private  String code;

    BsMerchantStockDeviceStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BsMerchantStockDeviceStatusEnum{" +
                "code='" + code + '\'' +
                '}';
    }
}
