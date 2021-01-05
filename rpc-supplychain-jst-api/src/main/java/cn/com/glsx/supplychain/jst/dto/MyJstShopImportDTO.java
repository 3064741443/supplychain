package cn.com.glsx.supplychain.jst.dto;

import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;
import java.util.Date;
@SuppressWarnings("serial")
public class MyJstShopImportDTO implements Serializable {
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

    private String agentMerchantCode;

    private String agentMerchantName;

    public String getAgentMerchantCode() {
        return agentMerchantCode;
    }

    public void setAgentMerchantCode(String agentMerchantCode) {
        this.agentMerchantCode = agentMerchantCode;
    }

    public String getAgentMerchantName() {
        return agentMerchantName;
    }

    public void setAgentMerchantName(String agentMerchantName) {
        this.agentMerchantName = agentMerchantName;
    }

    @ExcelResources(title = "门店名称",order = 0)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    @ExcelResources(title = "门店地址",order = 1)
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    @ExcelResources(title = "联系人",order = 2)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @ExcelResources(title = "联系电话",order = 3)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "JstShopImportDTO{" +
                "shopName='" + shopName + '\'' +
                ", addr='" + addr + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}