package cn.com.glsx.supplychain.jst.dto.gh;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class NewGhMerchantOrderDTO extends GhMerchantOrderDTO{
    /**
     * 采购订单状态
     */
    private  String dtoStatus;
	/**
	 * 发货数量
	 */
    @ApiModelProperty(value = "发货数量")
	private Integer shipmentsQuantity;

    /**
     * 待发数量
     */
    @ApiModelProperty(value = "待发数量")
	private Integer owerSend;

	/**
	 * 发货数量和订购总数的百分比
	 */
    @ApiModelProperty(value = "发货数量和订购总数的百分比")
	private  String  percentage;

	/**
	 * 催单次数
	 */
    @ApiModelProperty(value = "催单次数")
	private Integer reminderTotal;

    public String getDtoStatus() {
        return dtoStatus;
    }

    public void setDtoStatus(String dtoStatus) {
        this.dtoStatus = dtoStatus;
    }

    public Integer getShipmentsQuantity() {
        return shipmentsQuantity;
    }

    public void setShipmentsQuantity(Integer shipmentsQuantity) {
        this.shipmentsQuantity = shipmentsQuantity;
    }

    public Integer getOwerSend() {
        return owerSend;
    }

    public void setOwerSend(Integer owerSend) {
        this.owerSend = owerSend;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Integer getReminderTotal() {
        return reminderTotal;
    }

    public void setReminderTotal(Integer reminderTotal) {
        this.reminderTotal = reminderTotal;
    }

    @Override
    public String toString() {
        return "NewGhMerchantOrderDTO{" +
                "dtoStatus='" + dtoStatus + '\'' +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", owerSend=" + owerSend +
                ", percentage='" + percentage + '\'' +
                ", reminderTotal=" + reminderTotal +
                '}';
    }
}
