package cn.com.glsx.supplychain.enums;

public enum SyncResultLogStatusEnum {

    SYNC_RESULT_LOG_STATUS_ENUM_SU("SU"),
    SYNC_RESULT_LOG_STATUS_ENUM_FA("FA");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    SyncResultLogStatusEnum(String code) {
        this.code = code;
    }
}
