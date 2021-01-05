package cn.com.glsx.supplychain.jxc.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TransferSnImportDTO implements Serializable {

    /**
     * 设备SN
     */
    private String sn;
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "TransferSnImportDTO{" +
                "sn='" + sn + '\'' +
                '}';
    }
}