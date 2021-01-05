package cn.com.glsx.supplychain.jst.dto;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoOrderDetailImportDTO implements Serializable {

    /**
     * 设备SN
     */
    private String sn;

    @ExcelResources(title = "设备SN",order = 0)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "NoOrderDetailImportDTO{" +
                "sn='" + sn + '\'' +
                '}';
    }
}