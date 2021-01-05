package cn.com.glsx.supplychain.enums;

public enum ReasonInvalidDeviceNum {

	INVALID_DEVICE_DATA_FORMAT("必填字段为空"),
	INVALID_DEVICE_PACKAGE_NO("商品编码未激活或者与入库编码支持不一致"),
	INVALID_DEVICE_MERCHANT_NO("商户号不存在"),
	INVALID_DEVICE_REPEAT_IMEI("imei在数据库中已经存在,不能被重复导入"),
	INVALID_DEVICE_REPEAT_IMSI("imsi在数据库中已经存在,不能被重复绑定导入"),
	INVALID_DEVICE_UNSTOCK_IMSI("imsi已被激活,请补录"),
	INVALID_DEVICE_REPEAT_IMEI_EXCEL("imei在excel表格中重复"),
	INVALID_DEVICE_REPEAT_IMSI_EXCEL("imsi在excel表格中重复"),
	INVALID_DEVICE_REPEAT_SN_EXCEL("sn在excel表格中重复"),
	INVALID_DEVICE_REPEAT_ICCID_EXCEL("iccid在excel表格中重复"),
	INVALID_DEVICE_COLUMN_LENGTH("字段长度不合法，参考模板对应长度设置对应字段"),
	INVALID_DEVICE_FORMAT_IMSI("imsi格式不正确，应该460开头或者长度不是15位"),
	INVALID_DEVICE_FORMAT_PHONE("模块手机号格式不正确,请输入正确的模块手机号"),
	INVALID_DEVICE_IMEI_EXISTS("虚拟设备导入不应该填入imei"),
	NVALID_DEVICE_SN("该sn不是广联出库设备，不能导入"),
	INVALID_DEVICE_SN_STATUS("该sn订单的类型为退货并且已在退货过程中，不能导入"),
	INVALID_DEVICE_SN_TWO_STATUS("该sn订单的类型为返修并且已在维修过程中，不能导入"),
	INVALID_MERCHANT_ORDER_QUANTITY("发货数量不能大于审核数量"),
	INVALID_MERCHANT_ORDER_SHIPMENT_QUANTITY("发货数量加已发货数不能大于审核数量"),
	INVALID_MERCHANT_ORDER_DISPATCH_LENGTH("物流单号长度不合法，参考模板对应长度设置对应字段"),
	INVALID_MERCHANT_ORDER_NUMBER_NOT_EXIST("对应的订单号不存在"),
    INVALID_MERCHANT_ORDER_IS_EMPTY("物流单或物流公司为空"),
	INVALID_MERCHANT_ORDER_STATUS_ERROR("状态不为待发货不能导出"),
	INVALID_MERCHANT_ORDER_FORMAT_ERROR("物流单或者物流公司包含逗号，请更改为正确数据"),
	INVALID_STATEMENT_FINANCE_TIME_ERROR("对账月份不正确，参考模板对应日期规则"),
	INVALID_STATEMENT_FINANCE_TIME_INVALID("对账月份格式不正确，参考模板对应日期格式"),
	INVALID_STATEMENT_FINANCE_TWO_DEVICE_REEOR("从设备数据有一个不为空，其它均不能为空"),
	INVALID_STATEMENT_FINANCE_MATERIAL_ERROR("导入的物料编码非硬件物料"),
	INVALID_SEND_ORDER_NO_EMPTY("发货单号在系统或者excel中找不到"),
	INVALID_WHOUSE_NAME_EMPTY("工厂仓库在系统或者excel中找不到"),
	INVALID_FORMAT_DEVICE_SN("sn格式不正确"),
	INVALID_FORMAT_DEVICE_ICCID("iccid格式不正确"),
	INVALID_FORMAT_DEVICE_IMSI("imsi格式不正确"),
	INVALID_LOGISTISNO_EMPTY("物流编号不存在"),
	INVALID_LOGISTISCPY_EMPTY("物流公司不存在"),
	INVALID_DEVICE_SN_REPEAT("sn在excel或系统中重复"),
	INVALID_DEVICE_ICCID_REPEAT("iccid在excel或系统中重复"),
	INVALID_DEVICE_IMEI_REPEAT("imei在excel或系统中重复"),
	INVALID_DEVICE_IMSI_REPEAT("imsi在excel或系统中重复"),
	INVALID_SEND_ORDER_QUANTITY("excel发货数量已经超出订单需求数量"),
	INVALID_DEVICE_DEVMNUM("根据设备型号找不到对应的设备类型编号"),
	INVALID_SPLIT_RULE_NOT_FOUND("找不到拆分规则"),
	INVALID_DISPATCH_RULE_NOT_FOUND("找不到分发规则"),
	INVALID_MERCHANT_NAME("系统在仓库表中未找到服务商"),
	INVALID_CUSTOMER_CODE("系统在客户表中未找到总部结算公司"),
	INVALID_GOODS_CODE("系统在物料表中未找到商品编码");
	
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private ReasonInvalidDeviceNum(String value) {
		this.value = value;
	}
	
	
}
