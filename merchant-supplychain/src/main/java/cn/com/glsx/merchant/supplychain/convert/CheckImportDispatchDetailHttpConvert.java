package cn.com.glsx.merchant.supplychain.convert;

import org.springframework.util.StringUtils;

import cn.com.glsx.merchant.supplychain.vo.CheckImportDispatchDetailVO;
import cn.com.glsx.supplychain.jst.dto.CheckImportShopOrderDetailDTO;

public class CheckImportDispatchDetailHttpConvert {
	
	public static CheckImportDispatchDetailVO convertDto(CheckImportShopOrderDetailDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CheckImportDispatchDetailVO vo = new CheckImportDispatchDetailVO();
		vo.setShopOrderCode(record.getShopOrderCode());
		vo.setSuccessCount(record.getSuccessCount());
		vo.setFailCount(record.getFailCount());
		vo.setLogisticsVo(BsLogisticsHttpConvert.convertDto(record.getLogisticsDto()));
		vo.setListSucess(CheckDispatchDetailHttpConvert.convertListDto(record.getListSuccess()));
		vo.setListFailed(CheckDispatchDetailHttpConvert.convertExportListDto(record.getListFailed()));
		vo.setLogisticsVo(BsLogisticsHttpConvert.convertDto(record.getLogisticsDto()));
		return vo;
	}

}
