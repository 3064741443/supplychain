package cn.com.glsx.supplychain.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Settlement
 * @Author admin
 * @Param
 * @Date 2019/7/1 15:28
 * @Version
 **/

public class Settlement implements Serializable {

    private Integer id;

    @Transient
    private Integer consumerId;

    /**
     * 结算客户名称
     */
    private String merchantName;
    /**
     * 物料编号
     */
    private String materialCode;
    /**
     * 对账结算时间
     */
    private Date settlementDate;
    /**
     * 对账结算总数
     */
    private Integer settlementNum;
    /**
     * 含税单价
     */
    private BigDecimal includeTaxPrice;
    /**
     * 不含税单价
     */
    private BigDecimal freeTaxPrice;
    /**
     * 价税合计
     */
    private BigDecimal totalPriceTax;
    /**
     * 不含税金额
     */
    private BigDecimal excludeTaxAmount;
    /**
     * 税额
     */
    private BigDecimal tax;
    /**
     * 客户订单号
     */
    private String customerOrderNum;
    /**
     * 发货订单号
     */
    private String deliveryOrderNum;
    /**
     * 发往商户
     */
    private String sentMerchant;
    /**
     * 发货时间
     */
    private String sentDate;
    /**
     * 物流单号
     */
    private String logisticsOrderNum;
    /**
     * SN／IMEI
     */
    private String sn;
    /**
     *  物料List
     */
    @Transient
    private List<Material> materialList;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Integer getSettlementNum() {
        return settlementNum;
    }

    public void setSettlementNum(Integer settlementNum) {
        this.settlementNum = settlementNum;
    }

    public BigDecimal getIncludeTaxPrice() {
        return includeTaxPrice;
    }

    public void setIncludeTaxPrice(BigDecimal includeTaxPrice) {
        this.includeTaxPrice = includeTaxPrice;
    }

    public BigDecimal getFreeTaxPrice() {
        return freeTaxPrice;
    }

    public void setFreeTaxPrice(BigDecimal freeTaxPrice) {
        this.freeTaxPrice = freeTaxPrice;
    }

    public BigDecimal getTotalPriceTax() {
        return totalPriceTax;
    }

    public void setTotalPriceTax(BigDecimal totalPriceTax) {
        this.totalPriceTax = totalPriceTax;
    }

    public BigDecimal getExcludeTaxAmount() {
        return excludeTaxAmount;
    }

    public void setExcludeTaxAmount(BigDecimal excludeTaxAmount) {
        this.excludeTaxAmount = excludeTaxAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getCustomerOrderNum() {
        return customerOrderNum;
    }

    public void setCustomerOrderNum(String customerOrderNum) {
        this.customerOrderNum = customerOrderNum;
    }

    public String getDeliveryOrderNum() {
        return deliveryOrderNum;
    }

    public void setDeliveryOrderNum(String deliveryOrderNum) {
        this.deliveryOrderNum = deliveryOrderNum;
    }

    public String getSentMerchant() {
        return sentMerchant;
    }

    public void setSentMerchant(String sentMerchant) {
        this.sentMerchant = sentMerchant;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getLogisticsOrderNum() {
        return logisticsOrderNum;
    }

    public void setLogisticsOrderNum(String logisticsOrderNum) {
        this.logisticsOrderNum = logisticsOrderNum;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(String deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public String toString() {
        return "Settlement{" +
                "id=" + id +
                ", merchantName='" + merchantName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                ", settlementDate=" + settlementDate +
                ", settlementNum=" + settlementNum +
                ", includeTaxPrice=" + includeTaxPrice +
                ", freeTaxPrice=" + freeTaxPrice +
                ", totalPriceTax=" + totalPriceTax +
                ", excludeTaxAmount=" + excludeTaxAmount +
                ", tax=" + tax +
                ", customerOrderNum='" + customerOrderNum + '\'' +
                ", deliveryOrderNum='" + deliveryOrderNum + '\'' +
                ", sentMerchant='" + sentMerchant + '\'' +
                ", sentDate='" + sentDate + '\'' +
                ", sn='" + sn + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", deletedFlag='" + deletedFlag + '\'' +
                '}';
    }
}
