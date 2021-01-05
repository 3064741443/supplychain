package cn.com.glsx.supplychain.jst.vo.gh;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GhMerchantOrderQueryParamVO implements Serializable {
    @ApiModelProperty(name = "categoryId", notes = "分类配置ID", dataType = "string", required = false, example = "")
    private Integer categoryId;
    @ApiModelProperty(name = "spaPurchaseCode", notes = "采购sap编号", dataType = "string", required = false, example = "")
    private String spaPurchaseCode;
    @ApiModelProperty(name = "spaProductCode", notes = "sap产品编码", dataType = "string", required = false, example = "")
    private String spaProductCode;
    @ApiModelProperty(name = "spaProductName", notes = "sap产品名称", dataType = "string", required = false, example = "")
    private String spaProductName;
    @ApiModelProperty(name = "dtoStatus", notes = "采购订单状态", dataType = "string", required = false, example = "")
    private String dtoStatus;
    @ApiModelProperty(name = "merchantCode", notes = "商户号", dataType = "string", required = false, example = "")
    private String merchantCode;
    @ApiModelProperty(name = "pageSize", notes = "每页大小", dataType = "integer", required = false, example = "")
    private Integer pageSize;
    @ApiModelProperty(name = "pageNum", notes = "页码", dataType = "Integer", required = false, example = "")
    private Integer pageNum;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSpaPurchaseCode() {
        return spaPurchaseCode;
    }

    public void setSpaPurchaseCode(String spaPurchaseCode) {
        this.spaPurchaseCode = spaPurchaseCode;
    }

    public String getSpaProductCode() {
        return spaProductCode;
    }

    public void setSpaProductCode(String spaProductCode) {
        this.spaProductCode = spaProductCode;
    }

    public String getSpaProductName() {
        return spaProductName;
    }

    public void setSpaProductName(String spaProductName) {
        this.spaProductName = spaProductName;
    }

    public String getDtoStatus() {
        return dtoStatus;
    }

    public void setDtoStatus(String dtoStatus) {
        this.dtoStatus = dtoStatus;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "GhMerchantOrderQueryParamVO{" +
                "categoryId=" + categoryId +
                ", spaPurchaseCode='" + spaPurchaseCode + '\'' +
                ", spaProductCode='" + spaProductCode + '\'' +
                ", spaProductName='" + spaProductName + '\'' +
                ", dtoStatus='" + dtoStatus + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                '}';
    }
}
