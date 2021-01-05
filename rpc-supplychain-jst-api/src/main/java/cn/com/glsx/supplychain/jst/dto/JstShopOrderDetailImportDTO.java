package cn.com.glsx.supplychain.jst.dto;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

/**
 * @ClassName JstShopOrderDetailImportDTO
 * @Description
 * @Author xiex
 * @Date 2020/2/17 23:25
 * @Version 1.0
 */
public class JstShopOrderDetailImportDTO implements Serializable {

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
        return "JstShopOrderDetailImportDTO{" +
                "sn='" + sn + '\'' +
                '}';
    }
}
