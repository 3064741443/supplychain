package cn.com.glsx.supplychain.model.jxc;

import cn.com.glsx.supplychain.jxc.dto.JXCMTBsAddressDTO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JXCMdbGenerateBsTransferOrderVo implements Serializable {
    @ApiModelProperty(name = "inServiceProvidercode", notes = "调入服务商编码", dataType = "string", required = true, example = "")
    private String inServiceProvidercode;
    @ApiModelProperty(name = "serviceProviderName", notes = "调入服务商名称", dataType = "string", required = true, example = "")
    private String inServiceProviderName;
    @ApiModelProperty(name = "serviceProvidercode", notes = "调出服务商编码", dataType = "string", required = true, example = "")
    private String outServiceProvidercode;
    @ApiModelProperty(name = "serviceProviderName", notes = "调出服务商名称", dataType = "string", required = true, example = "")
    private String outServiceProviderName;
    @ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = true, example = "")
    private String materialCode;
    @ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = true, example = "")
    private String materialName;
    @ApiModelProperty(name = "transferQuantity", notes = "调拨数量", dataType = "string", required = true, example = "")
    private Integer transferQuantity;
    @ApiModelProperty(name = "addressDTO", notes = "地址信息", dataType = "object", required = true, example = "")
    private JXCMTBsAddressDTO addressDTO;

    public String getInServiceProvidercode() {
        return inServiceProvidercode;
    }

    public void setInServiceProvidercode(String inServiceProvidercode) {
        this.inServiceProvidercode = inServiceProvidercode;
    }

    public String getInServiceProviderName() {
        return inServiceProviderName;
    }

    public void setInServiceProviderName(String inServiceProviderName) {
        this.inServiceProviderName = inServiceProviderName;
    }

    public String getOutServiceProvidercode() {
        return outServiceProvidercode;
    }

    public void setOutServiceProvidercode(String outServiceProvidercode) {
        this.outServiceProvidercode = outServiceProvidercode;
    }

    public String getOutServiceProviderName() {
        return outServiceProviderName;
    }

    public void setOutServiceProviderName(String outServiceProviderName) {
        this.outServiceProviderName = outServiceProviderName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getTransferQuantity() {
        return transferQuantity;
    }

    public void setTransferQuantity(Integer transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

    public JXCMTBsAddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(JXCMTBsAddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    @Override
    public String
    toString() {
        return "JXCMdbGenerateBsTransferOrderVo{" +
                "inServiceProvidercode='" + inServiceProvidercode + '\'' +
                ", inServiceProviderName='" + inServiceProviderName + '\'' +
                ", outServiceProvidercode='" + outServiceProvidercode + '\'' +
                ", outServiceProviderName='" + outServiceProviderName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", transferQuantity=" + transferQuantity +
                ", addressDTO=" + addressDTO +
                '}';
    }
}
