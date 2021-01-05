package cn.com.glsx.supplychain.jxc.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JXCMdbTransferOrderDTO implements Serializable {
    @ApiModelProperty(name = "tranOrderCode", notes = "调拨订单号", dataType = "string", required = true, example = "")
    private String tranOrderCode;
    @ApiModelProperty(name = "orderTotal", notes = "调拨数量", dataType = "string", required = true, example = "")
    private Integer orderTotal;
    @ApiModelProperty(name = "materialCode", notes = "调拨物料编号", dataType = "string", required = true, example = "")
    private String materialCode;
    @ApiModelProperty(name = "materialName", notes = "调拨物料名称", dataType = "string", required = true, example = "")
    private String materialName;
    @ApiModelProperty(name = "inMerchantName", notes = "调入服务商名称", dataType = "string", required = true, example = "")
    private String  inMerchantName;
    @ApiModelProperty(name = "bsMerchantOrderVehicleDTO", notes = "车辆信息", dataType = "object", required = true, example = "")
    private JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO;
    @ApiModelProperty(name = "adress", notes = "发货地址信息", dataType = "string", required = true, example = "")
    private String adress;
    @ApiModelProperty(name = "remark", notes = "备注信息", dataType = "String", required = true, example = "")
    private String remark;
    @ApiModelProperty(name = "sendTotal", notes = "已发数量", dataType = "string", required = true, example = "")
    private Integer sendTotal;
    @ApiModelProperty(name = "oweTotal", notes = "未完成数量", dataType = "string", required = true, example = "")
    private Integer oweTotal;
    @ApiModelProperty(name = "ndScan", notes = "是否扫码 Y:扫码，N:不扫码", dataType = "string", required = true, example = "")
    private String ndScan;

    public String getNdScan() {
        return ndScan;
    }

    public void setNdScan(String ndScan) {
        this.ndScan = ndScan;
    }

    public Integer getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(Integer sendTotal) {
        this.sendTotal = sendTotal;
    }

    public Integer getOweTotal() {
        return oweTotal;
    }

    public void setOweTotal(Integer oweTotal) {
        this.oweTotal = oweTotal;
    }

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
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

    public String getInMerchantName() {
        return inMerchantName;
    }

    public void setInMerchantName(String inMerchantName) {
        this.inMerchantName = inMerchantName;
    }

    public JXCMTBsMerchantOrderVehicleDTO getBsMerchantOrderVehicleDTO() {
        return bsMerchantOrderVehicleDTO;
    }

    public void setBsMerchantOrderVehicleDTO(JXCMTBsMerchantOrderVehicleDTO bsMerchantOrderVehicleDTO) {
        this.bsMerchantOrderVehicleDTO = bsMerchantOrderVehicleDTO;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "JXCMdbTransferOrderDTO{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                ", orderTotal=" + orderTotal +
                ", materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", inMerchantName='" + inMerchantName + '\'' +
                ", bsMerchantOrderVehicleDTO=" + bsMerchantOrderVehicleDTO +
                ", adress='" + adress + '\'' +
                ", remark='" + remark + '\'' +
                ", sendTotal=" + sendTotal +
                ", oweTotal=" + oweTotal +
                ", ndScan='" + ndScan + '\'' +
                '}';
    }
}
