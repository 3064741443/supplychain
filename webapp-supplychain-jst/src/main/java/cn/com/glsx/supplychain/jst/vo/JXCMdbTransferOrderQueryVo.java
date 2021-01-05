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
public class JXCMdbTransferOrderQueryVo implements Serializable {
    @ApiModelProperty(name = "orderStatus", notes = "调拨订单状态", dataType = "string", required = true, example = "")
    private String orderStatus;
    @ApiModelProperty(name = "pageNum", notes = "当前页", dataType = "int", required = true, example = "")
    private Integer pageNum;
    @ApiModelProperty(name = "pageSize", notes = "页面大小", dataType = "int", required = true, example = "")
    private Integer pageSize;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "JXCMdbTransferOrderQueryVo{" +
                "orderStatus='" + orderStatus + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
