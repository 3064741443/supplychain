package cn.com.glsx.merchant.supplychain.vo;

import cn.com.glsx.supplychain.jst.dto.CheckShopOrderDetaiExportlDTO;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CheckImportDispatchDetailVO implements Serializable{

	/**
	 * 发货数量
	 */
	private Integer shipmentsQuantity;

	private String shopOrderCode;
	
	private Integer successCount;
	
	private Integer failCount;
	
	private LogisticsVO logisticsVo;
	
	private List<CheckDispatchDetailVO> listDetail;
	
	private List<CheckDispatchDetailVO> listSucess;
	
	private List<CheckShopOrderDetaiExportlVO> listFailed;

	public Integer getShipmentsQuantity() {
		return shipmentsQuantity;
	}

	public void setShipmentsQuantity(Integer shipmentsQuantity) {
		this.shipmentsQuantity = shipmentsQuantity;
	}

	public String getShopOrderCode() {
		return shopOrderCode;
	}

	public void setShopOrderCode(String shopOrderCode) {
		this.shopOrderCode = shopOrderCode;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	
	public List<CheckDispatchDetailVO> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<CheckDispatchDetailVO> listDetail) {
		this.listDetail = listDetail;
	}

	public List<CheckDispatchDetailVO> getListSucess() {
		return listSucess;
	}

	public void setListSucess(List<CheckDispatchDetailVO> listSucess) {
		this.listSucess = listSucess;
	}

	public List<CheckShopOrderDetaiExportlVO> getListFailed() {
		return listFailed;
	}

	public void setListFailed(List<CheckShopOrderDetaiExportlVO> listFailed) {
		this.listFailed = listFailed;
	}

	public LogisticsVO getLogisticsVo() {
		return logisticsVo;
	}

	public void setLogisticsVo(LogisticsVO logisticsVo) {
		this.logisticsVo = logisticsVo;
	}

	@Override
	public String toString() {
		return "CheckImportDispatchDetailVO{" +
				"shopOrderCode='" + shopOrderCode + '\'' +
				", successCount=" + successCount +
				", failCount=" + failCount +
				", logisticsVo=" + logisticsVo +
				", listDetail=" + listDetail +
				", listSucess=" + listSucess +
				", listFailed=" + listFailed +
				'}';
	}
}
