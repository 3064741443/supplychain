package cn.com.glsx.supplychain.model.jxc;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;

import java.io.Serializable;

@ContentRowHeight(30)
@ColumnWidth(15)
public class DeviceFileExportVo implements Serializable{
    @ExcelProperty(value = "序号",index = 0)
    private Integer numberNo;

    @ExcelProperty(value = "IMEI",index = 1)
    private String sn;

    @ExcelProperty(value = "当前ICCID",index = 2)
    private String iccid;

    @ExcelProperty(value = "当前IMSI",index = 3)
    private String imsi;

    @ExcelProperty(value = "是否绑定用户",index = 4)
    private String userFlag;

    @ExcelProperty(value = "设备类型",index = 5)
    private String deviceTypeName;

    @ExcelProperty(value = "设备编码/名称",index = 6)
    private String deviceName;

    @ExcelProperty(value = "入库商品名称",index = 7)
    private Integer packageId;

    @ExcelProperty(value = "入库商品状态",index = 8)
    private String packageStatu;

    @ExcelProperty(value = "发往商户",index = 9)
    private String sendMerchantNo;

    @ExcelProperty(value = "入库时间",index = 10)
    private String inStorageTime;

    @ExcelProperty(value = "出库时间",index = 11)
    private String outStorageTime;

    public Integer getNumberNo() {
        return numberNo;
    }

    public void setNumberNo(Integer numberNo) {
        this.numberNo = numberNo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getSendMerchantNo() {
        return sendMerchantNo;
    }

    public void setSendMerchantNo(String sendMerchantNo) {
        this.sendMerchantNo = sendMerchantNo;
    }

    public String getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(String inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public String getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(String outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getPackageStatu() {
        return packageStatu;
    }

    public void setPackageStatu(String packageStatu) {
        this.packageStatu = packageStatu;
    }

    @Override
    public String toString() {
        return "DeviceFileExportVo{" +
                "numberNo=" + numberNo +
                ", sn='" + sn + '\'' +
                ", iccid='" + iccid + '\'' +
                ", imsi='" + imsi + '\'' +
                ", userFlag='" + userFlag + '\'' +
                ", deviceTypeName='" + deviceTypeName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", packageId=" + packageId +
                ", packageStatu='" + packageStatu + '\'' +
                ", sendMerchantNo='" + sendMerchantNo + '\'' +
                ", inStorageTime='" + inStorageTime + '\'' +
                ", outStorageTime='" + outStorageTime + '\'' +
                '}';
    }
}