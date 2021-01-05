package cn.com.glsx.supplychain.model.jxc;

import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceInfoImport;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/9/9 15:21
 */
public class JXCMTDeviceInfoImportVo {
    List<JXCMTDeviceInfoImport>  deviceInfoImportList;

    public List<JXCMTDeviceInfoImport> getDeviceInfoImportList() {
        return deviceInfoImportList;
    }

    public void setDeviceInfoImportList(List<JXCMTDeviceInfoImport> deviceInfoImportList) {
        this.deviceInfoImportList = deviceInfoImportList;
    }

    @Override
    public String toString() {
        return "JXCMTDeviceInfoImportVo{" +
                "deviceInfoImportList=" + deviceInfoImportList +
                '}';
    }
}
