package cn.com.glsx.supplychain.model.am;


import org.oreframework.commons.office.poi.zslin.utils.ExcelResources;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName StatementFinance 对账单-金融风控(导入)
 * @Author admin
 * @Param
 * @Date 2019/9/12 13:42
 * @Version
 **/
@SuppressWarnings("serial")
public class StatementFinanceImport implements Serializable {
    /**
     * 年月
     */
    private String time;
    /**
     * 工单号
     */
    private String workOrder;
    /**
     * 主设备号
     */
    private String deviceNumberOne;
    /**
     * 有源/无源(主)
     */
    private String sourceOne;
    /**
     * 主物料编号
     */
    private String materialCodeOne;
    /**
     * 从设备号
     */
    private String deviceNumberTwo;
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
     * 从物料编号
     */
    private String materialCodeTwo;
    /**
     * 设备数量
     */
    private Integer deviceQuantity;
    /**
     * GPS类型
     */
    private String gpsType;
    /**
     * 业务服务期限
     */
    private String serviceTime;
    /**
     * 计算金额
     */
    private Double sum;
    /**
     *  销售编码
     */
    private String saleCode;
    /**
     * 销售名称
     */
    private String saleName;
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
     * 硬件结算客户编码
     */
    private String hardwareCustomerCode;
    
    /**
     * 硬件结算客户名称
     */
    private String hardwareCustomerName;
    
    /**
     * 非硬件结算客户编码
     */
    private String serviceCustomerCode;
    
    /**
     * 非硬件结算客户名称
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
     * 是否分期结算
     */
    private String settleByStages;
    
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
    private String contractDate;
    
    /**
     * 物料编码
     */
    private String materialCode;
    

    //失败描述
    private String failDesc;

    @ExcelResources(title = "月份",order = 0)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @ExcelResources(title = "工单号",order = 1)
    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }
    @ExcelResources(title = "主设备号",order = 2)
    public String getDeviceNumberOne() {
        return deviceNumberOne;
    }

    public void setDeviceNumberOne(String deviceNumberOne) {
        this.deviceNumberOne = deviceNumberOne;
    }
    @ExcelResources(title = "有源/无源(主)",order = 3)
    public String getSourceOne() {
        return sourceOne;
    }

    public void setSourceOne(String sourceOne) {
        this.sourceOne = sourceOne;
    }
    @ExcelResources(title = "物料编号(主)",order = 4)
    public String getMaterialCodeOne() {
        return materialCodeOne;
    }

    public void setMaterialCodeOne(String materialCodeOne) {
        this.materialCodeOne = materialCodeOne;
    }
    
    @ExcelResources(title = "主设备类型",order = 5)
    public String getDeviceTypeOne() {
        return deviceTypeOne;
    }

    public void setDeviceTypeOne(String deviceTypeOne) {
        this.deviceTypeOne = deviceTypeOne;
    }
    
    @ExcelResources(title = "从设备号",order = 6)
    public String getDeviceNumberTwo() {
        return deviceNumberTwo;
    }

    public void setDeviceNumberTwo(String deviceNumberTwo) {
        this.deviceNumberTwo = deviceNumberTwo;
    }
    @ExcelResources(title = "有源/无源(从)",order = 7)
    public String getSourceTwo() {
        return sourceTwo;
    }

    public void setSourceTwo(String sourceTwo) {
        this.sourceTwo = sourceTwo;
    }
    
    @ExcelResources(title = "物料编号(从)",order = 8)
    public String getMaterialCodeTwo() {
        return materialCodeTwo;
    }

    public void setMaterialCodeTwo(String materialCodeTwo) {
        this.materialCodeTwo = materialCodeTwo;
    }
    
    @ExcelResources(title = "从设备类型",order = 9)
    public String getDeviceTypeTwo() {
        return deviceTypeTwo;
    }

    public void setDeviceTypeTwo(String deviceTypeTwo) {
        this.deviceTypeTwo = deviceTypeTwo;
    }
    

    @ExcelResources(title = "设备数量",order = 10)
    public Integer getDeviceQuantity() {
        return deviceQuantity;
    }

    public void setDeviceQuantity(Integer deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }
    @ExcelResources(title = "GPS类型",order = 11)
    public String getGpsType() {
        return gpsType;
    }

    public void setGpsType(String gpsType) {
        this.gpsType = gpsType;
    }
    @ExcelResources(title = "业务服务期限",order = 12)
    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }
    @ExcelResources(title = "结算金额",order = 13)
    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
    
    @ExcelResources(title = "金融服务费是否分期结算",order = 14)
    public String getSettleByStages() {
		return settleByStages;
	}

	public void setSettleByStages(String settleByStages) {
		this.settleByStages = settleByStages;
	}
	
	@ExcelResources(title = "是否投保",order = 15)
	public String getIsSure() {
		return isSure;
	}

	public void setIsSure(String isSure) {
		this.isSure = isSure;
	}
	
	@ExcelResources(title = "投保期限",order = 16)
	public String getSureTime() {
		return sureTime;
	}

	public void setSureTime(String sureTime) {
		this.sureTime = sureTime;
	}
    
	@ExcelResources(title = "发货仓库编码",order = 17)
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    @ExcelResources(title = "发货仓库名称",order = 18)
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    
    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
    
    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }
    @ExcelResources(title = "结算客户编码",order = 23)
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    @ExcelResources(title = "结算客户名称",order = 24)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    @ExcelResources(title = "硬件结算客户编码",order = 25)
	public String getHardwareCustomerCode() {
		return hardwareCustomerCode;
	}

	public void setHardwareCustomerCode(String hardwareCustomerCode) {
		this.hardwareCustomerCode = hardwareCustomerCode;
	}
	@ExcelResources(title = "硬件结算客户名称",order = 26)
	public String getHardwareCustomerName() {
		return hardwareCustomerName;
	}

	public void setHardwareCustomerName(String hardwareCustomerName) {
		this.hardwareCustomerName = hardwareCustomerName;
	}
	@ExcelResources(title = "非硬件结算客户编码",order = 27)
	public String getServiceCustomerCode() {
		return serviceCustomerCode;
	}

	public void setServiceCustomerCode(String serviceCustomerCode) {
		this.serviceCustomerCode = serviceCustomerCode;
	}
	@ExcelResources(title = "非硬件结算客户名称",order = 28)
	public String getServiceCustomerName() {
		return serviceCustomerName;
	}

	public void setServiceCustomerName(String serviceCustomerName) {
		this.serviceCustomerName = serviceCustomerName;
	}

	@ExcelResources(title = "对账类型",order = 22)
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	@ExcelResources(title = "是否结安装费",order = 19)
	public String getSettleInstall() {
		return settleInstall;
	}

	public void setSettleInstall(String settleInstall) {
		this.settleInstall = settleInstall;
	}

	@ExcelResources(title = "合同日期",order = 20)
	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	@ExcelResources(title = "物料编码",order = 21)
	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	@ExcelResources(title = "失败原因",order = 29)
    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

	@Override
	public String toString() {
		return "StatementFinanceImport [time=" + time + ", workOrder="
				+ workOrder + ", deviceNumberOne=" + deviceNumberOne
				+ ", sourceOne=" + sourceOne + ", materialCodeOne="
				+ materialCodeOne + ", deviceNumberTwo=" + deviceNumberTwo
				+ ", sourceTwo=" + sourceTwo + ", deviceTypeOne="
				+ deviceTypeOne + ", deviceTypeTwo=" + deviceTypeTwo
				+ ", materialCodeTwo=" + materialCodeTwo + ", deviceQuantity="
				+ deviceQuantity + ", gpsType=" + gpsType + ", serviceTime="
				+ serviceTime + ", sum=" + sum + ", saleCode=" + saleCode
				+ ", saleName=" + saleName + ", customerCode=" + customerCode
				+ ", customerName=" + customerName + ", warehouseCode="
				+ warehouseCode + ", warehouseName=" + warehouseName
				+ ", hardwareCustomerCode=" + hardwareCustomerCode
				+ ", hardwareCustomerName=" + hardwareCustomerName
				+ ", serviceCustomerCode=" + serviceCustomerCode
				+ ", serviceCustomerName=" + serviceCustomerName + ", isSure="
				+ isSure + ", sureTime=" + sureTime + ", settleByStages="
				+ settleByStages + ", workType=" + workType
				+ ", settleInstall=" + settleInstall + ", contractDate="
				+ contractDate + ", materialCode=" + materialCode
				+ ", failDesc=" + failDesc + "]";
	}

	

	

	
	
    
    
   
    
}
