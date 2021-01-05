package cn.com.glsx.rpc.supplychain.rdn.model.pojo;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/9/27 16:40
 */
public class GoodsReceipt {
    /**
     * 单据号
     */
    private String documentNo;

    /**
     * 收货方
     */
    private String consignee;

    /**
     * 收货地址
     */
    private Address address;

    /**
     * 发货订单列表
     */
   private List<ShipmentOrder> shipmentOrderList;

   /**
    * 总数量
    */
   private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ShipmentOrder> getShipmentOrderList() {
        return shipmentOrderList;
    }

    public void setShipmentOrderList(List<ShipmentOrder> shipmentOrderList) {
        this.shipmentOrderList = shipmentOrderList;
    }

    @Override
    public String toString() {
        return "GoodsReceipt{" +
                "documentNo='" + documentNo + '\'' +
                ", consignee='" + consignee + '\'' +
                ", address=" + address +
                ", shipmentOrderList=" + shipmentOrderList +
                ", totalCount=" + totalCount +
                '}';
    }
}
