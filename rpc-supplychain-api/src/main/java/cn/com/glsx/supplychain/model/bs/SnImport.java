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
@Table(name = "SnImport")
public class SnImport implements Serializable{
    /**
     * SN
     */
    private String sn;

    /**
     * 设备售后原因
     */
    private String deviceAfterReason;

    /**
     * 失败描述
     */
    private String failDesc;

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

    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    @Override
    public String toString() {
        return "SnImport{" +
                "sn='" + sn + '\'' +
                ", failDesc='" + failDesc + '\'' +
                '}';
    }
}
