package cn.com.glsx.supplychain.jst.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 13:55
 */
public class JXCMdbTransferOrderDetailQueryVo implements Serializable {
    @ApiModelProperty(name = "tranOrderCode", notes = "调拨订单号", dataType = "string", required = true, example = "")
    private String tranOrderCode;

    public String getTranOrderCode() {
        return tranOrderCode;
    }

    public void setTranOrderCode(String tranOrderCode) {
        this.tranOrderCode = tranOrderCode;
    }

    @Override
    public String toString() {
        return "JXCMdbTransferOrderDetailQueryVo{" +
                "tranOrderCode='" + tranOrderCode + '\'' +
                '}';
    }
}
