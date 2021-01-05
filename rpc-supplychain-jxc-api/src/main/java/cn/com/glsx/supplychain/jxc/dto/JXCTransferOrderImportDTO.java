package cn.com.glsx.supplychain.jxc.dto;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/27 11:50
 */
public class JXCTransferOrderImportDTO {
    private  String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "JXCTransferOrderImportDTO{" +
                "sn='" + sn + '\'' +
                '}';
    }
}
