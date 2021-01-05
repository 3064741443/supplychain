package cn.com.glsx.supplychain.jst.dto;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JstShopExportDTO implements Serializable {
    /**
     * 供货商名称
     */
    private String agentMerchantName;
    /**
     * 供货商编号
     */
    private String agentMerchantCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 门店地址
     */
    private String addr;
    /**
     *联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String phone;

    //失败描述
    private String failDesc;

    @ExcelResources(title = "供货商名称",order = 0)
    public String getAgentMerchantName() {
        return agentMerchantName;
    }

    public void setAgentMerchantName(String agentMerchantName) {
        this.agentMerchantName = agentMerchantName;
    }

    @ExcelResources(title = "供货商编号",order = 1)
    public String getAgentMerchantCode() {
        return agentMerchantCode;
    }

    public void setAgentMerchantCode(String agentMerchantCode) {
        this.agentMerchantCode = agentMerchantCode;
    }

    @ExcelResources(title = "门店名称",order = 2)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    @ExcelResources(title = "门店地址",order = 3)
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    @ExcelResources(title = "联系人",order = 4)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @ExcelResources(title = "联系电话",order = 5)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @ExcelResources(title = "失败原因",order = 6)
    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }


    @Override
    public String toString() {
        return "JstShopExportDTO{" +
                "agentMerchantName='" + agentMerchantName + '\'' +
                ", agentMerchantCode='" + agentMerchantCode + '\'' +
                ", shopName='" + shopName + '\'' +
                ", addr='" + addr + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", failDesc='" + failDesc + '\'' +
                '}';
    }
}