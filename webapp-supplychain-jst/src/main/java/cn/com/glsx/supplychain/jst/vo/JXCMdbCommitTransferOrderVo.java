package cn.com.glsx.supplychain.jst.vo;

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
public class JXCMdbCommitTransferOrderVo implements Serializable {
    @ApiModelProperty(name = "tranOrderCode", notes = "调拨订单号", dataType = "string", required = true, example = "")
    private String tranOrderCode;
    @ApiModelProperty(name = "deliveryType", notes = "L:物流配送  O:线下配送", dataType = "true", required = true, example = "")
    private String deliveryType;
    @ApiModelProperty(name = "logisticsDTO", notes = "调拨物流信息", dataType = "object", required = true, example = "")
    private JXCLogisticsDTO logisticsDTO;
    @ApiModelProperty(name = "snList", notes = "调拨sn信息", dataType = "object", required = true, example = "")
    private List<String> snList;
    @ApiModelProperty(name = "sendCount", notes = "线下配送发货数量", dataType = "int", required = true, example = "")
    private Integer sendCount;
    @ApiModelProperty(name = "receiveId", notes = "收货地址ID", dataType = "long", required = true, example = "")
    private Long receiveId;

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
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
                ", deliveryType='" + deliveryType + '\'' +
                ", logisticsDTO=" + logisticsDTO +
                ", snList=" + snList +
                ", sendCount=" + sendCount +
                ", receiveId=" + receiveId +
                '}';
    }
}
