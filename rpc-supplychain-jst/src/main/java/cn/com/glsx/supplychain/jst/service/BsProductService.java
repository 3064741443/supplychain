package cn.com.glsx.supplychain.jst.service;

import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.BsProductDetailMapper;
import cn.com.glsx.supplychain.jst.mapper.BsProductHistoryPriceMapper;
import cn.com.glsx.supplychain.jst.mapper.BsProductMapper;
import cn.com.glsx.supplychain.jst.model.BsProduct;
import cn.com.glsx.supplychain.jst.model.BsProductDetail;
import cn.com.glsx.supplychain.jst.model.BsProductHistoryPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BsProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(BsProductService.class);
	@Autowired
	private BsProductMapper bsProductMapper;
	@Autowired
	private BsProductDetailMapper bsProductDetailMapper;
	@Autowired
	private BsProductHistoryPriceMapper bsProductPriceMapper;
	
	public Integer BatchSubmitBsProduct(List<BsProduct> listProduct) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("BsProductService::BatchSubmitBsProduct listProduct:{}",listProduct);
		try
		{
			result = bsProductMapper.insertList(listProduct);
		}
		catch(Exception e)
		{
			logger.error("BsProductService::BatchSubmitBsProduct 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsProductService::BatchSubmitBsProduct end result:{}",result);
		return result;
	}
	
	public Integer BatchSubmitBsProductNoCatch(List<BsProduct> listProduct)
	{
		return bsProductMapper.insertList(listProduct);
	}
	
	public Integer BatchSubmitBsProductDetail(List<BsProductDetail> listProductDetail) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("BsProductService::BatchSubmitBsProductDetail listProductDetail:{}",listProductDetail);
		try
		{
			result = bsProductDetailMapper.insertList(listProductDetail);
		}
		catch(Exception e)
		{
			logger.error("BsProductService::BatchSubmitBsProductDetail 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsProductService::BatchSubmitBsProductDetail end result:{}",result);
		return result;
	}
	
	public Integer BatchSubmitBsProductDetailNoCatch(List<BsProductDetail> listProductDetail)
	{
		return bsProductDetailMapper.insertList(listProductDetail);
	}
	
	public Integer BatchSubmitBsProductPrice(List<BsProductHistoryPrice> listProductPrice) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("BsProductService::BatchSubmitBsProductPrice listProductDetail:{}",listProductPrice);
		try
		{
			result = bsProductPriceMapper.insertList(listProductPrice);
		}
		catch(Exception e)
		{
			logger.error("BsProductService::BatchSubmitBsProductPrice 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsProductService::BatchSubmitBsProductPrice end result:{}",result);
		return result;
	}
	
	public Integer BatchSubmitBsProductPriceNoCatch(List<BsProductHistoryPrice> listProductPrice) 
	{
		return bsProductPriceMapper.insertList(listProductPrice);
	}
}
