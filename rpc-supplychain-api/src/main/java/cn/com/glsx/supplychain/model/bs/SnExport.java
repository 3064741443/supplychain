package cn.com.glsx.supplychain.model.bs;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName SnImport
 * @Author admin
 * @Param
 * @Date 2019/2/25 11:12
 * @Version
 **/
@SuppressWarnings("serial")
@Table(name = "SnExport")
public class SnExport implements Serializable{
    /**
     * SN
     */
    private String sn;

    /**
     * 设备售后原因
     */
    private String deviceAfterReason;

    /**
     * 失败原因
     */
    private String reason;

    @ExcelResources(title = "SN",order = 0)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @ExcelResources(title = "设备售后原因",order = 1)
    public String getDeviceAfterReason() {
        return deviceAfterReason;
    }

    public void setDeviceAfterReason(String deviceAfterReason) {
        this.deviceAfterReason = deviceAfterReason;
    }

    @ExcelResources(title = "失败原因",order = 2)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "SnExport{" +
                "sn='" + sn + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
