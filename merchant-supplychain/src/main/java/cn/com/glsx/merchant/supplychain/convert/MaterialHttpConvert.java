package cn.com.glsx.merchant.supplychain.convert;

import cn.com.glsx.merchant.supplychain.vo.MaterialVO;
import cn.com.glsx.supplychain.jst.dto.MaterialDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MaterialHttpConvert {

	private static final Logger logger = LoggerFactory.getLogger(MaterialHttpConvert.class);
	
	public static MaterialDTO convertVo(MaterialVO record)
	{
		MaterialDTO dto = new MaterialDTO();
		dto.setMaterialCode(record.getMaterialCode());
		dto.setMaterialName(record.getMaterialName());
		dto.setShipQuantities(record.getShipQuantities());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		return dto;
	}
	
	public static MaterialVO convertDto(MaterialDTO record)
	{
		MaterialVO vo = new MaterialVO();
		vo.setMaterialCode(record.getMaterialCode());
		vo.setMaterialName(record.getMaterialName());
		return vo;
	}
	
	public static List<MaterialDTO> convertListVo(List<MaterialVO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<MaterialDTO> listDto = new ArrayList<MaterialDTO>();
		for(MaterialVO vo:record)
		{
			listDto.add(convertVo(vo));
		}
		return listDto;
	}
	
	public static List<MaterialVO> convertListDto(List<MaterialDTO> record)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<MaterialVO> listVo = new ArrayList<MaterialVO>();
		for(MaterialDTO dto:record)
		{
			listVo.add(convertDto(dto));
		}
		return listVo;
	}
	
	
}
