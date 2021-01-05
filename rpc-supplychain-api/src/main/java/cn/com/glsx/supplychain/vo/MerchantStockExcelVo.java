package cn.com.glsx.supplychain.vo;

import java.io.Serializable;

/**
 * 
 * @Title: MerchantStockExcelVo
 * @Description: 商户库存导出实体类
 * @author xiaowy
 * @date 2019年11月18日 下午4:29:16
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2015
 */
@SuppressWarnings("serial")
public class MerchantStockExcelVo implements Serializable {
	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 商户编号
	 */
	private String merchantCode;

	/**
	 * 商户名称
	 */
	private String merchantName;

	/**
	 * 物料编号
	 */
	private String materialCode;

	/**
	 * 物料名称
	 */
	private String materialName;

	/**
	 * 物料类型id
	 */
	private Integer materialTypeId;

	/**
	 * 物料类型名称
	 */
	private String materialTypeName;

	/**
	 * 物料关联的设备类型id
	 */
	private Integer materialDeviceTypeId;

	/**
	 * 物料关联的设备类型名称
	 */
	private String materialDeviceTypeName;

	/**
	 * 出货数量
	 */
	private Integer statSellNum;

	/**
	 * 退货数量
	 */
	private Integer statRetnNum;

	/**
	 * 结算数量
	 */
	private Integer statSettNum;

	/**
	 * 调入数量
	 */
	private Integer statCainNum;

	/**
	 * 调出数量
	 */
	private Integer statCaouNum;

	/**
	 * 库存数量
	 */
	private Integer statStckNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(Integer materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public Integer getMaterialDeviceTypeId() {
		return materialDeviceTypeId;
	}

	public void setMaterialDeviceTypeId(Integer materialDeviceTypeId) {
		this.materialDeviceTypeId = materialDeviceTypeId;
	}

	public String getMaterialDeviceTypeName() {
		return materialDeviceTypeName;
	}

	public void setMaterialDeviceTypeName(String materialDeviceTypeName) {
		this.materialDeviceTypeName = materialDeviceTypeName;
	}

	public Integer getStatSellNum() {
		return statSellNum;
	}

	public void setStatSellNum(Integer statSellNum) {
		this.statSellNum = statSellNum;
	}

	public Integer getStatRetnNum() {
		return statRetnNum;
	}

	public void setStatRetnNum(Integer statRetnNum) {
		this.statRetnNum = statRetnNum;
	}

	public Integer getStatSettNum() {
		return statSettNum;
	}

	public void setStatSettNum(Integer statSettNum) {
		this.statSettNum = statSettNum;
	}

	public Integer getStatCainNum() {
		return statCainNum;
	}

	public void setStatCainNum(Integer statCainNum) {
		this.statCainNum = statCainNum;
	}

	public Integer getStatCaouNum() {
		return statCaouNum;
	}

	public void setStatCaouNum(Integer statCaouNum) {
		this.statCaouNum = statCaouNum;
	}

	public Integer getStatStckNum() {
		return statStckNum;
	}

	public void setStatStckNum(Integer statStckNum) {
		this.statStckNum = statStckNum;
	}
}