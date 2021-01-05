package cn.com.glsx.supplychain.model.jxc;

import cn.com.glsx.supplychain.jxc.dto.JXCLogisticsDTO;
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
public class JXCMdbCommitBsTransferOrderVo implements Serializable {
    @ApiModelProperty(name = "tranOrderCode", notes = "调拨订单号", dataType = "string", required = true, example = "")
    private String tranOrderCode;
    @ApiModelProperty(name = "serviceProviderCode", notes = "服务商编码", dataType = "string", required = true, example = "")
    private String serviceProviderCode;
    @ApiModelProperty(name = "serviceProviderName", notes = "服务商名称", dataType = "string", required = true, example = "")
    private String serviceProviderName;
    @ApiModelProperty(name = "materialCode", notes = "调拨物料编码", dataType = "string", required = true, example = "")
    private String materialCode;
    @ApiModelProperty(name = "materialName", notes = "调拨物料名称", dataType = "string", required = true, example = "")
    private String materialName;
    @ApiModelProperty(name = "logisticsDTO", notes = "调拨物流信息", dataType = "object", required = true, example = "")
    private JXCLogisticsDTO logisticsDTO;
    @ApiModelProperty(name = "snList", notes = "调拨sn信息", dataType = "object", required = true, example = "")
    private List<String> snList;
    @ApiModelProperty(name = "sendCount", notes = "线下配送发货数量", dataType = "int", required = true, example = "")
    private Integer sendCount;

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

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

    public JXCLogisticsDTO getLogisticsDTO() {
        return logisticsDTO;
    }

    public void setLogisticsDTO(JXCLogisticsDTO logisticsDTO) {
        this.logisticsDTO = logisticsDTO;
    }

    public List<String> getSnList() {
        return snList;
    }

    public void setSnList(List<String> snList) {
        this.snList = snList;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    @Override
    public String toString() {
        return "JXCMdbCommitTransferOrderVo{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                ", serviceProviderCode='" + serviceProviderCode + '\'' +
                ", serviceProviderName='" + serviceProviderName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", logisticsDTO=" + logisticsDTO +
                ", snList=" + snList +
                ", sendCount=" + sendCount +
                '}';
    }
}
