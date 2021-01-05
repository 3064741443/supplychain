package cn.com.glsx.supplychain.model.am;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName StatementFinance 对账单-金融风控
 * @Author admin
 * @Param
 * @Date 2019/9/12 13:42
 * @Version
 **/
@Table(name = "am_statement_finance")
public class StatementFinance implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 主物料编号
     */
    private String materialCodeOne;
    /**
     * 从物料编号
     */
    private String materialCodeTwo;
    /**
     * 年月
     */
    private Date time;
    /**
     * 工单号
     */
    private String workOrder;
    /**
     * 主设备号
     */
    private String deviceNumberOne;
    /**
     * 从设备号
     */
    private String deviceNumberTwo;
    /**
     * 有源/无源(主)
     */
    private String sourceOne;
    /**
     * 有源/无源(从)
     */
    private String sourceTwo;
    /**
     * 主设备类型
     */
    private String deviceTypeOne;
    /**
     * 从设备类型
     */
    private String deviceTypeTwo;
    /**
     * 设备数量
     */
    private Integer deviceQuantity;
    /**
     * GPS类型
     */
    private String gpsType;
    /**
     * 服务期限
     */
    private String serviceTime;
    
    /**
     * 是否分期结算
     */
    private String settleByStages;
    
    /**
     * 硬件费+服务费
     */
    private Double sum;
    /**
     *  销售组编码
     */
    private String saleGroupCode;
    /**
     * 销售组名称
     */
    private String saleGroupName;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 发货仓库编码
     */
    private String warehouseCode;
    /**
     * 发货仓库名称
     */
    private String warehouseName;
    /**
     *  状态(1:未拆分,2:拆分成功,3:拆分失败)
     */
    private Byte status;

    /**
     * 失败原因
     */
    private String reasons;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String deletedFlag;
    
    /**
     * 硬件结算客户
     */
    private String hardwareCustomerCode;
    
    /**
     * 硬件结算客户
     */
    private String hardwareCustomerName;
    
    /**
     * 非硬件结算客户
     */
    private String serviceCustomerCode;
    
    /**
     * 非硬件结算客户
     */
    private String serviceCustomerName;
    
    /**
     * 是否投保 
     */
    private String isSure;
    
    /**
     * 投保期限 
     */
    private String sureTime;
    
    /**
     * N:新装结算 C:续费结算 B:补充费结算 D:扣除费结算 I:安装费结算 S:拆装费结算 H:硬件费结算 
     */
    private String workType;
    
    /**
     * 是否结算小安装费 Y:是 N:否 
     */
    private String settleInstall;
    
    /**
     * 合同时间
     */
    private Date contractDate;
    
    /**
     * 物料编码
     */
    private String materialCode;
    
    
    /**
     * 开始时间
     */
    @Transient
    private Date startDate;

    /**
     * 结束时间
     */
    @Transient
    private Date endDate;

    /**
     * 金融风控(拆分)详情
     */
    @Transient
    private List<StatementFinanceSplit>statementFinanceSplitList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getSettleInstall() {
		return settleInstall;
	}

	public void setSettleInstall(String settleInstall) {
		this.settleInstall = settleInstall;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMaterialCodeOne() {
        return materialCodeOne;
    }

    public void setMaterialCodeOne(String materialCodeOne) {
        this.materialCodeOne = materialCodeOne;
    }

    public String getMaterialCodeTwo() {
        return materialCodeTwo;
    }

    public void setMaterialCodeTwo(String materialCodeTwo) {
        this.materialCodeTwo = materialCodeTwo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getDeviceNumberOne() {
        return deviceNumberOne;
    }

    public void setDeviceNumberOne(String deviceNumberOne) {
        this.deviceNumberOne = deviceNumberOne;
    }

    public String getDeviceNumberTwo() {
        return deviceNumberTwo;
    }

    public void setDeviceNumberTwo(String deviceNumberTwo) {
        this.deviceNumberTwo = deviceNumberTwo;
    }

    public String getSourceOne() {
        return sourceOne;
    }

    public void setSourceOne(String sourceOne) {
        this.sourceOne = sourceOne;
    }

    public String getSourceTwo() {
        return sourceTwo;
    }

    public void setSourceTwo(String sourceTwo) {
        this.sourceTwo = sourceTwo;
    }

    public String getDeviceTypeOne() {
        return deviceTypeOne;
    }

    public void setDeviceTypeOne(String deviceTypeOne) {
        this.deviceTypeOne = deviceTypeOne;
    }

    public String getDeviceTypeTwo() {
        return deviceTypeTwo;
    }

    public void setDeviceTypeTwo(String deviceTypeTwo) {
        this.deviceTypeTwo = deviceTypeTwo;
    }

    public Integer getDeviceQuantity() {
        return deviceQuantity;
    }

    public void setDeviceQuantity(Integer deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }

    public String getGpsType() {
        return gpsType;
    }

    public void setGpsType(String gpsType) {
        this.gpsType = gpsType;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getSaleGroupCode() {
        return saleGroupCode;
    }

    public void setSaleGroupCode(String saleGroupCode) {
        this.saleGroupCode = saleGroupCode;
    }

    public String getSaleGroupName() {
        return saleGroupName;
    }

    public void setSaleGroupName(String saleGroupName) {
        this.saleGroupName = saleGroupName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<StatementFinanceSplit> getStatementFinanceSplitList() {
        return statementFinanceSplitList;
    }

    public void setStatementFinanceSplitList(List<StatementFinanceSplit> statementFinanceSplitList) {
        this.statementFinanceSplitList = statementFinanceSplitList;
    }

	public String getHardwareCustomerCode() {
		return hardwareCustomerCode;
	}

	public void setHardwareCustomerCode(String hardwareCustomerCode) {
		this.hardwareCustomerCode = hardwareCustomerCode;
	}

	public String getHardwareCustomerName() {
		return hardwareCustomerName;
	}

	public void setHardwareCustomerName(String hardwareCustomerName) {
		this.hardwareCustomerName = hardwareCustomerName;
	}

	public String getServiceCustomerCode() {
		return serviceCustomerCode;
	}

	public void setServiceCustomerCode(String serviceCustomerCode) {
		this.serviceCustomerCode = serviceCustomerCode;
	}

	public String getServiceCustomerName() {
		return serviceCustomerName;
	}

	public void setServiceCustomerName(String serviceCustomerName) {
		this.serviceCustomerName = serviceCustomerName;
	}

	public String getIsSure() {
		return isSure;
	}

	public void setIsSure(String isSure) {
		this.isSure = isSure;
	}

	public String getSettleByStages() {
		return settleByStages;
	}

	public void setSettleByStages(String settleByStages) {
		this.settleByStages = settleByStages;
	}

	public String getSureTime() {
		return sureTime;
	}

	public void setSureTime(String sureTime) {
		this.sureTime = sureTime;
	}

 
}
