package cn.com.glsx.supplychain.jst.vo;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMaterialInfoDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JXCMdbGenerateTransferOrderVo implements Serializable {
    @ApiModelProperty(name = "serviceProvidercode", notes = "服务商编码", dataType = "string", required = true, example = "")
    private String serviceProvidercode;
    @ApiModelProperty(name = "serviceProviderName", notes = "服务商名称", dataType = "string", required = true, example = "")
    private String serviceProviderName;
    @ApiModelProperty(name = "addressDTO", notes = "地址信息", dataType = "object", required = true, example = "")
    private JXCMTBsAddressDTO addressDTO;
    @ApiModelProperty(name = "materialInfoList", notes = "物料信息", dataType = "list", required = true, example = "")
    private List<JXCMaterialInfoDTO> materialInfoList;

    public String getServiceProvidercode() {
        return serviceProvidercode;
    }

    public void setServiceProvidercode(String serviceProvidercode) {
        this.serviceProvidercode = serviceProvidercode;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public JXCMTBsAddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(JXCMTBsAddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public List<JXCMaterialInfoDTO> getMaterialInfoList() {
        return materialInfoList;
    }

    public void setMaterialInfoList(List<JXCMaterialInfoDTO> materialInfoList) {
        this.materialInfoList = materialInfoList;
    }

    @Override
    public String toString() {
        return "JXCMdbGenerateTransferOrderVo{" +
                "serviceProvidercode='" + serviceProvidercode + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", addressDTO=" + addressDTO +
                ", materialInfoList=" + materialInfoList +
                '}';
    }
}
