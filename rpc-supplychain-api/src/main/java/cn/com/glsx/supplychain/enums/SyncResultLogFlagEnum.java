package cn.com.glsx.supplychain.enums;

public enum SyncResultLogFlagEnum {

    SYNC_RESULT_LOG_FLAG_ENUM_PH("PH"),
    SYNC_RESULT_LOG_FLAG_ENUM_CA("CA"),
    SYNC_RESULT_LOG_FLAG_ENUM_FI("FI"),
    SYNC_RESULT_LOG_FLAG_ENUM_EX("EX");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    SyncResultLogFlagEnum(String code) {
        this.code = code;
    }
}
