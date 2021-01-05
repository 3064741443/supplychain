package cn.com.glsx.supplychain.model.bs;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName SnChangeImport
 * @Author admin
 * @Param
 * @Date 2019/2/25 11:12
 * @Version
 **/
@SuppressWarnings("serial")
public class SnChangeImport implements Serializable{
    /**
     * SN
     */
    private String sn;

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
