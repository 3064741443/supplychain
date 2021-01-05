package cn.com.glsx.supplychain.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @program: supplychain
 * @description：StatementSellReconDetailExcelVo
 * @create: 2020-07-15 15:16
 * @author: luoqiang
 * @version: 1.0
 */
@SuppressWarnings("serial")
public class StatementSellReconExcelVo implements Serializable {

    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称", index = 0)
    private String customerName;

    /**
     * 客户地址
     */
    @ExcelProperty(value = "客户地址", index = 0)
    private String customerAddr;

    /**
     * 客户联系人
     */
    @ExcelProperty(value = "客户联系人", index = 0)
    private String customerContact;

    /**
     * 客户联系电话
     */
    @ExcelProperty(value = "客户联系电话", index = 0)
    private String customerPhone;


    /**
     * 销售组名称
     */
    @ExcelProperty(value = "销售组名称", index = 6)
    private String saleGroupName;

    /**
     * 销售组地址
     */
    @ExcelProperty(value = "销售组地址", index = 6)
    private String saleGroupAddr;

    /**
     * 销售组联系人
     */
    @ExcelProperty(value = "销售组联系人", index = 6)
    private String saleGroupContact;

    /**
     * 销售组联系电话
     */
    @ExcelProperty(value = "销售组联系电话", index = 6)
    private String saleGroupPhone;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddr() {
        return customerAddr;
    }

    public void setCustomerAddr(String customerAddr) {
        this.customerAddr = customerAddr;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName;
    }

    public String getSaleGroupAddr() {
        return saleGroupAddr;
    }

    public void setSaleGroupAddr(String saleGroupAddr) {
        this.saleGroupAddr = saleGroupAddr;
    }

    public String getSaleGroupContact() {
        return saleGroupContact;
    }

    public void setSaleGroupContact(String saleGroupContact) {
        this.saleGroupContact = saleGroupContact;
    }

    public String getSaleGroupPhone() {
        return saleGroupPhone;
    }

    public void setSaleGroupPhone(String saleGroupPhone) {
        this.saleGroupPhone = saleGroupPhone;
    }

    @Override
    public String toString() {
        return "StatementSellReconExcelVo{" +
                "customerName='" + customerName + '\'' +
                ", customerAddr='" + customerAddr + '\'' +
                ", customerContact='" + customerContact + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", saleGroupName='" + saleGroupName + '\'' +
                ", saleGroupAddr='" + saleGroupAddr + '\'' +
                ", saleGroupContact='" + saleGroupContact + '\'' +
                ", saleGroupPhone='" + saleGroupPhone + '\'' +
                '}';
    }
}
