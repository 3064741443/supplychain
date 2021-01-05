package cn.com.glsx.supplychain.jst.convert;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.ProductDTO;
import cn.com.glsx.supplychain.jst.model.AmProductSplit;

public class ProductRpcConvert {

	private static final Logger logger = LoggerFactory.getLogger(ProductRpcConvert.class);
	
	public static ProductDTO convertBean(AmProductSplit record)
	{
		ProductDTO dto = new ProductDTO();
		dto.setId(record.getId());
		dto.setChannel(record.getChannel());
		dto.setMerchantCode(record.getMerchantCode());
		dto.setMerchantName(record.getMerchantName());
		dto.setPackageOne(record.getPackageOne());
		dto.setPrice(record.getPrice());
		dto.setProductCode(record.getProductCode());
		dto.setProductName(record.getProductName());
		dto.setServiceTime(record.getServiceTime());
		dto.setServiceType(record.getServiceType());
		dto.setPriceTime(record.getTime());
		dto.setDeviceQuantity(record.getDeviceQuantity());
		dto.setSaleMode(record.getSaleMode());
		dto.setHardwareContainSource(record.getHardwareContainSource());
		dto.setSourceProportion(record.getSourceProportion());
		dto.setNotSourceProportion(record.getNotSourceProportion());
		dto.setCarType(record.getCarType());
		
		return dto;
	}
	
	public static List<ProductDTO> convertListBeanSpread(List<AmProductSplit> record,List<BsDealerUserInfoDTO> listDealerUserInfo)
	{
		if(StringUtils.isEmpty(record))
		{
			return null;
		}
		List<ProductDTO> listDto = new ArrayList<ProductDTO>();
		for(AmProductSplit model:record)
		{
			if(!StringUtils.isEmpty(model.getMerchantCode()))
			{
				listDto.add(convertBean(model));
				continue;
			}
			for(BsDealerUserInfoDTO userModel:listDealerUserInfo)
			{
				if(userModel.getChannel().equals(model.getChannel()))
				{
					model.setMerchantCode(userModel.getMerchantCode());
					model.setMerchantName(userModel.getMerchantName());
					listDto.add(convertBean(model));
					model.setMerchantCode(null);
					model.setMerchantName(null);
				}
			}
		}
		return listDto;
	}
}
