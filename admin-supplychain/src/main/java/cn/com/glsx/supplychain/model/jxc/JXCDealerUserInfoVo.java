package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/28 10:05
 */
public class JXCDealerUserInfoVo {
    @ApiModelProperty(name = "serviceProviderCode", notes = "服务商编码", dataType = "string", required = false, example = "")
    private String serviceProviderCode;
    @ApiModelProperty(name = "serviceProviderName", notes = "服务商名称", dataType = "string", required = false, example = "")
    private String serviceProviderName;

    public String getServiceProviderCode() {
        return serviceProviderCode;
    }

    public void setServiceProviderCode(String serviceProviderCode) {
        this.serviceProviderCode = serviceProviderCode;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    @Override
    public String toString() {
        return "JXCBDealerUserInfoDTO{" +
                "serviceProviderCode='" + serviceProviderCode + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                '}';
    }
}
