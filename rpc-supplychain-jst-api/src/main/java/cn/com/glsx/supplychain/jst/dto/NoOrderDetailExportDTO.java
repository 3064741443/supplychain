package cn.com.glsx.supplychain.jst.dto;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoOrderDetailExportDTO implements Serializable {

    /**
     * 设备sn
     */
    private String sn;

    /**
     *  失败描述
     */
    private String failDesc;



    @ExcelResources(title = "设备SN",order = 0)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @ExcelResources(title = "失败原因",order =1)
    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    @Override
    public String toString() {
        return "NoOrderDetailExportDTO{" +
                "sn='" + sn + '\'' +
                ", failDesc='" + failDesc + '\'' +
                '}';
    }
}