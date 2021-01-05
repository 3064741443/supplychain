package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JXCMTDeviceFileSearchVo implements Serializable{
    @ApiModelProperty(name = "devTypeId", notes = "设备大类id", dataType = "int", required = false, example = "")
    private Integer devTypeId;
    @ApiModelProperty(name = "packageStatu", notes = "商品状态", dataType = "string", required = false, example = "")
    private String packageStatu;
    @ApiModelProperty(name = "searchKey", notes = "搜索条件", dataType = "string", required = false, example = "")
    private String searchKey;
    @ApiModelProperty(name = "searchValue", notes = "搜索值", dataType = "string", required = false, example = "")
    private String searchValue;
    @ApiModelProperty(name = "outStorageStartDate", notes = "入库开始时间", dataType = "string", required = false, example = "")
    private String outStorageStartDate;
    @ApiModelProperty(name = "outStorageEndDate", notes = "入库结束时间", dataType = "string", required = false, example = "")
    private String outStorageEndDate;
    @ApiModelProperty(name = "packageUserStartDate", notes = "激活开始时间", dataType = "string", required = false, example = "")
    private String packageUserStartDate;
    @ApiModelProperty(name = "packageUserEndDate", notes = "激活结束时间", dataType = "string", required = false, example = "")
    private String packageUserEndDate;

    public Integer getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(Integer devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getPackageStatu() {
        return packageStatu;
    }

    public void setPackageStatu(String packageStatu) {
        this.packageStatu = packageStatu;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getOutStorageStartDate() {
        return outStorageStartDate;
    }

    public void setOutStorageStartDate(String outStorageStartDate) {
        this.outStorageStartDate = outStorageStartDate;
    }

    public String getOutStorageEndDate() {
        return outStorageEndDate;
    }

    public void setOutStorageEndDate(String outStorageEndDate) {
        this.outStorageEndDate = outStorageEndDate;
    }

    public String getPackageUserStartDate() {
        return packageUserStartDate;
    }

    public void setPackageUserStartDate(String packageUserStartDate) {
        this.packageUserStartDate = packageUserStartDate;
    }

    public String getPackageUserEndDate() {
        return packageUserEndDate;
    }

    public void setPackageUserEndDate(String packageUserEndDate) {
        this.packageUserEndDate = packageUserEndDate;
    }

    @Override
    public String toString() {
        return "JXCMTDeviceFileSearchVo{" +
                "devTypeId=" + devTypeId +
                ", packageStatu='" + packageStatu + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", searchValue='" + searchValue + '\'' +
                ", outStorageStartDate='" + outStorageStartDate + '\'' +
                ", outStorageEndDate='" + outStorageEndDate + '\'' +
                ", packageUserStartDate='" + packageUserStartDate + '\'' +
                ", packageUserEndDate='" + packageUserEndDate + '\'' +
                '}';
    }
}