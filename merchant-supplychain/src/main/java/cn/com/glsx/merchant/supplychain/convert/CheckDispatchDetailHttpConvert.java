package cn.com.glsx.merchant.supplychain.convert;

import java.util.ArrayList;
import java.util.List;

import cn.com.glsx.merchant.supplychain.vo.CheckShopOrderDetaiExportlVO;
import cn.com.glsx.supplychain.jst.dto.CheckShopOrderDetaiExportlDTO;
import org.springframework.util.StringUtils;

import cn.com.glsx.merchant.supplychain.vo.CheckDispatchDetailVO;
import cn.com.glsx.supplychain.jst.dto.CheckShopOrderDetailDTO;

public class CheckDispatchDetailHttpConvert {

	public static List<CheckDispatchDetailVO> convertListDto(List<CheckShopOrderDetailDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<CheckDispatchDetailVO> listVo = new ArrayList<CheckDispatchDetailVO>();
		for(CheckShopOrderDetailDTO dto:record)
		{
			listVo.add(convertDto(dto));
		}
		return listVo;
	}
	
	public static List<CheckShopOrderDetailDTO> convertListVo(List<CheckDispatchDetailVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<CheckShopOrderDetailDTO> listDto = new ArrayList<CheckShopOrderDetailDTO>();
		for(CheckDispatchDetailVO vo:record)
		{
			listDto.add(convertVo(vo));
		}
		return listDto;
	}

	public static List<CheckShopOrderDetaiExportlVO> convertExportListDto(List<CheckShopOrderDetaiExportlDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<CheckShopOrderDetaiExportlVO> listVo = new ArrayList<CheckShopOrderDetaiExportlVO>();
		for(CheckShopOrderDetaiExportlDTO dto:record)
		{
			listVo.add(convertExportDto(dto));
		}
		return listVo;
	}

	public static List<CheckShopOrderDetaiExportlDTO> convertExportListVo(List<CheckShopOrderDetaiExportlVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<CheckShopOrderDetaiExportlDTO> listDto = new ArrayList<CheckShopOrderDetaiExportlDTO>();
		for(CheckShopOrderDetaiExportlVO vo:record)
		{
			listDto.add(convertExportVo(vo));
		}
		return listDto;
	}
	
	public static CheckDispatchDetailVO convertDto(CheckShopOrderDetailDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CheckDispatchDetailVO vo = new CheckDispatchDetailVO();
		vo.setDetail(record.getDetail());
		vo.setSn(record.getSn());
		return vo;
	}
	
	public static CheckShopOrderDetailDTO convertVo(CheckDispatchDetailVO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CheckShopOrderDetailDTO dto = new CheckShopOrderDetailDTO();
		dto.setDetail(record.getDetail());
		dto.setSn(record.getSn());
		return dto;
	}

	public static CheckShopOrderDetaiExportlVO convertExportDto(CheckShopOrderDetaiExportlDTO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CheckShopOrderDetaiExportlVO vo = new CheckShopOrderDetaiExportlVO();
		vo.setDetail(record.getDetail());
		vo.setSn(record.getSn());
		return vo;
	}

	public static CheckShopOrderDetaiExportlDTO convertExportVo(CheckShopOrderDetaiExportlVO record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		CheckShopOrderDetaiExportlDTO dto = new CheckShopOrderDetaiExportlDTO();
		dto.setDetail(record.getDetail());
		dto.setSn(record.getSn());
		return dto;
	}
}
