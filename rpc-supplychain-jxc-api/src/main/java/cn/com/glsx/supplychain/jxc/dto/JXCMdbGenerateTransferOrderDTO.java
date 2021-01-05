package cn.com.glsx.supplychain.jxc.dto;

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
public class JXCMdbGenerateTransferOrderDTO extends JXCMTBaseDTO implements Serializable {
    @ApiModelProperty(name = "serviceProvidercode", notes = "调入服务商编码", dataType = "string", required = true, example = "")
    private String serviceProvidercode;
    @ApiModelProperty(name = "serviceProviderName", notes = "调入服务商名称", dataType = "string", required = true, example = "")
    private String serviceProviderName;
    @ApiModelProperty(name = "outServiceProvidercode", notes = "调出服务商编码", dataType = "string", required = true, example = "")
    private String outServiceProvidercode;
    @ApiModelProperty(name = "outServiceProviderName", notes = "调出服务商名称", dataType = "string", required = true, example = "")
    private String outServiceProviderName;
    @ApiModelProperty(name = "addressDTO", notes = "地址信息", dataType = "object", required = true, example = "")
    private JXCMTBsAddressDTO addressDTO;
    @ApiModelProperty(name = "materialInfoList", notes = "物料信息", dataType = "list", required = true, example = "")
    private List<JXCMaterialInfoDTO> materialInfoList;
    @ApiModelProperty(name = "transferQuantity", notes = "调拨数量", dataType = "string", required = true, example = "")
    private Integer transferQuantity;
    @ApiModelProperty(name = "orderSource", notes = "订单来源", dataType = "string", required = true, example = "")
    private  String orderSource;

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

    public Integer getTransferQuantity() {
        return transferQuantity;
    }

    public void setTransferQuantity(Integer transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

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
        return "JXCMdbGenerateTransferOrderDTO{" +
                "serviceProvidercode='" + serviceProvidercode + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", addressDTO=" + addressDTO +
                ", materialInfoList=" + materialInfoList +
                ", transferQuantity=" + transferQuantity +
                ", orderSource='" + orderSource + '\'' +
                '}';
    }
}
