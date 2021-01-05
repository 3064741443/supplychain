package cn.com.glsx.supplychain.jst.dto.gh;

import java.util.List;

@SuppressWarnings("serial")
public class SapGhMerchantOrderDTO extends GhMerchantOrderDTO{
    /**
     * 采购订单状态
     */
    private  String dtoStatus;
    /**
     * 发货数量
     */
    private Integer shipmentsQuantity;

    /**
     * 发货数量和订购总数的百分比
     */
    private  String  percentage;

    /**
     * 催单次数
     */
    private Integer reminderTotal;

    List<GhMerchantOrderDTO> ghMerchantOrderDTOList;

    public List<GhMerchantOrderDTO> getGhMerchantOrderDTOList() {
        return ghMerchantOrderDTOList;
    }

    public void setGhMerchantOrderDTOList(List<GhMerchantOrderDTO> ghMerchantOrderDTOList) {
        this.ghMerchantOrderDTOList = ghMerchantOrderDTOList;
    }

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
        return "SapGhMerchantOrderDTO{" +
                "dtoStatus='" + dtoStatus + '\'' +
                ", shipmentsQuantity=" + shipmentsQuantity +
                ", percentage='" + percentage + '\'' +
                ", reminderTotal=" + reminderTotal +
                ", ghMerchantOrderDTOList=" + ghMerchantOrderDTOList +
                '}';
    }
}
