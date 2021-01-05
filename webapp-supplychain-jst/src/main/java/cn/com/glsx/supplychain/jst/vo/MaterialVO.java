package cn.com.glsx.supplychain.jst.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MaterialVO implements Serializable {

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     *产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private  String productName;

    /**
     *配送数量
     */
    private Integer shipQuantities;

    public Integer getShipQuantities() {
        return shipQuantities;
    }

    public void setShipQuantities(Integer shipQuantities) {
        this.shipQuantities = shipQuantities;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "MaterialVO{" +
                "materialCode='" + materialCode + '\'' +
                ", materialName='" + materialName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", shipQuantities=" + shipQuantities +
                '}';
    }
}
