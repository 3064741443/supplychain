package cn.com.glsx.supplychain.model.bs;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

/**
 * @ClassName SnChangeExport
 * @Author admin
 * @Param
 * @Date 2019/2/25 11:12
 * @Version
 **/
@SuppressWarnings("serial")
public class SnChangeExport implements Serializable{
    /**
     * SN
     */
    private String sn;

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

    @ExcelResources(title = "失败原因",order = 1)
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
