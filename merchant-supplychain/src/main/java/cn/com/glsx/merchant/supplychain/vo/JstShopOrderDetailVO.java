package cn.com.glsx.merchant.supplychain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class JstShopOrderDetailVO implements Serializable{
    private Integer id;

    /**
     * 门店订单号
     */
    private String shopOrderCode;
    /**
     * 设备SN号
     */
    private String sn;

    /**
     * 物流
     */
    private Integer logisticsId;

    /**
     * 物流单号
     */
    private String logisticsOrderNumber;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    private List<String> snList;

    /**
     * 创建人
     */
    private String createdBy;

    private Date createdDate;
    
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<String> getSnList() {
        return snList;
    }

    public void setSnList(List<String> snList) {
        this.snList = snList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopOrderCode() {
        return shopOrderCode;
    }

    public void setShopOrderCode(String shopOrderCode) {
        this.shopOrderCode = shopOrderCode == null ? null : shopOrderCode.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }


    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsOrderNumber() {
        return logisticsOrderNumber;
    }

    public void setLogisticsOrderNumber(String logisticsOrderNumber) {
        this.logisticsOrderNumber = logisticsOrderNumber;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}