package cn.com.glsx.supplychain.jst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.convert.BsMerchantShoppingCartRpcConvert;
import cn.com.glsx.supplychain.jst.convert.JstShopShoppingCartRpcConvert;
import cn.com.glsx.supplychain.jst.dto.BsMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.DisJstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.DisMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.mapper.BsMerchantShoppingCartMapper;
import cn.com.glsx.supplychain.jst.mapper.JstShopShoppingCartMapper;
import cn.com.glsx.supplychain.jst.model.BsAddress;
import cn.com.glsx.supplychain.jst.model.BsMerchantShoppingCart;
import cn.com.glsx.supplychain.jst.model.JstShopShoppingCart;

@Service
public class CartService {
	private static final Logger logger = LoggerFactory.getLogger(CartService.class);
	@Autowired
	private JstShopShoppingCartMapper shopCartMapper;
	@Autowired
	private BsMerchantShoppingCartMapper merchantCartMapper;
	
	public JstShopShoppingCart getShopShoppingCartById(Integer id) throws RpcServiceException
	{
		JstShopShoppingCart result = null;
		logger.info("CartService::getShopShoppingCartById start id:{}",id);
		try
		{
			JstShopShoppingCart record = new JstShopShoppingCart();
			record.setId(id);
			record.setDeletedFlag("N");
			result = shopCartMapper.selectOne(record);
		}
		catch(Exception e)
		{
			logger.error("CartService::getShopShoppingCartById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::getShopShoppingCartById end result:{}",result);
		return result;
		
	}
	
	public BsMerchantShoppingCart getMerchantShoppingCartById(Integer id) throws RpcServiceException
	{
		BsMerchantShoppingCart result = null;
		logger.info("CartService::getMerchantShoppingCartById start id:{}",id);
		try
		{
			BsMerchantShoppingCart record = new BsMerchantShoppingCart();
			record.setId(id);
			result = merchantCartMapper.selectOne(record);
		}
		catch(Exception e)
		{
			logger.error("CartService::getMerchantShoppingCartById 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::getMerchantShoppingCartById end result:{}",result);
		return result;
	}
	
	public Integer updateShopShoppingCart(DisJstShopShoppingCartDTO dtoCondition) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("CartService::updateShopShoppingCart start condition:{}",dtoCondition);
		try
		{
			List<JstShopShoppingCart> listBean = JstShopShoppingCartRpcConvert.convertListDto(dtoCondition.getListCartDto());
			for(JstShopShoppingCart bean:listBean)
			{
				shopCartMapper.updateByPrimaryKeySelective(bean);
			}
			JstShopShoppingCart condition = new JstShopShoppingCart();
			condition.setShopCode(dtoCondition.getShopCode());
			condition.setDeletedFlag("N");
			result = shopCartMapper.selectCount(condition);
		}
		catch(Exception e)
		{
			logger.error("CartService::updateShopShoppingCart 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::updateShopShoppingCart end result:{}",result);
		return result;
	}
	
	public Integer updateMerchantShoppingCart(DisMerchantShoppingCartDTO dtoCondition) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("CartService::updateMerchantShoppingCart start condition:{}",dtoCondition);
		try
		{
			List<BsMerchantShoppingCart> listBean = BsMerchantShoppingCartRpcConvert.convertListDto(dtoCondition.getListCartDto());
			for(BsMerchantShoppingCart bean:listBean)
			{
				merchantCartMapper.updateByPrimaryKeySelective(bean);
			}
			BsMerchantShoppingCart condition = new BsMerchantShoppingCart();
			condition.setMerchantCode(dtoCondition.getMerchantCode());
			condition.setDeletedFlag("N");
			result = merchantCartMapper.selectCount(condition);
		}
		catch(Exception e)
		{
			logger.error("CartService::updateMerchantShoppingCart 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::updateMerchantShoppingCart end result:{}",result);
		return result;
	}
	
	public Integer updateMerchantShoppingCartNoCatch(DisMerchantShoppingCartDTO dtoCondition)
	{
		List<BsMerchantShoppingCart> listBean = BsMerchantShoppingCartRpcConvert.convertListDto(dtoCondition.getListCartDto());
		for(BsMerchantShoppingCart bean:listBean)
		{
			merchantCartMapper.updateByPrimaryKeySelective(bean);
		}
		BsMerchantShoppingCart condition = new BsMerchantShoppingCart();
		condition.setMerchantCode(dtoCondition.getMerchantCode());
		condition.setDeletedFlag("N");
		return merchantCartMapper.selectCount(condition);
	}
	
	public DisMerchantShoppingCartDTO pageMerchantShoppingCart(DisMerchantShoppingCartDTO dtoCondition) throws RpcServiceException
	{
		logger.info("CartService::pageMerchantShoppingCart start condition:{}",dtoCondition);
		try
		{
			BsMerchantShoppingCart beanCondition = new BsMerchantShoppingCart();
			beanCondition.setMerchantCode(dtoCondition.getMerchantCode());
			beanCondition.setPageSize(dtoCondition.getPageSize());
			beanCondition.setPageStart((dtoCondition.getPageNo()-1)*dtoCondition.getPageSize());
			beanCondition.setDeletedFlag("N");
			List<BsMerchantShoppingCart> listBean = merchantCartMapper.pageMerchantShoppingCart(beanCondition);
			List<BsMerchantShoppingCartDTO> listDto = BsMerchantShoppingCartRpcConvert.convertListBean(listBean);
			dtoCondition.setListCartDto(listDto);
		}
		catch(Exception e)
		{
			logger.error("CartService::pageMerchantShoppingCart 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::pageMerchantShoppingCart end dtoCondition:{}",dtoCondition);
		return dtoCondition;
	}
	
	public DisJstShopShoppingCartDTO pageShopShoppingCart(DisJstShopShoppingCartDTO dtoCondition) throws RpcServiceException
	{
		logger.info("CartService::pageShopShoppingCart start condition:{}",dtoCondition);
		try
		{
			JstShopShoppingCart beanCondition = new JstShopShoppingCart();
			beanCondition.setShopCode(dtoCondition.getShopCode());
			beanCondition.setPageSize(dtoCondition.getPageSize());
			beanCondition.setDeletedFlag("N");
			beanCondition.setPageStart((dtoCondition.getPageNo()-1)*dtoCondition.getPageSize());
			List<JstShopShoppingCart> listBean = shopCartMapper.pageShopShoppingCart(beanCondition);
			List<JstShopShoppingCartDTO> listDto = JstShopShoppingCartRpcConvert.convertListBean(listBean);
			dtoCondition.setListCartDto(listDto);
		}
		catch(Exception e)
		{
			logger.error("CartService::pageShopShoppingCart 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::pageShopShoppingCart end dtoCondition:{}",dtoCondition);
		return dtoCondition;
		
	}
	
	public Integer submitShopShoppingCart(List<JstShopShoppingCartDTO> listCartDto) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("CartService::submitShopShoppingCart start listCartDto:{}",listCartDto);
		
		List<JstShopShoppingCart> listCart = JstShopShoppingCartRpcConvert.convertListDto(listCartDto);
		if(!StringUtils.isEmpty(listCart))
		{
			JstShopShoppingCart cartCondition = null;
			for(JstShopShoppingCart shopShoppingCart:listCart)
			{
				cartCondition = new JstShopShoppingCart();
				cartCondition.setProductCode(shopShoppingCart.getProductCode());
				cartCondition.setAgentMerchantCode(shopShoppingCart.getAgentMerchantCode());
				cartCondition.setMaterialCode(shopShoppingCart.getMaterialCode());
				cartCondition.setShopCode(shopShoppingCart.getShopCode());
				JstShopShoppingCart bean = shopCartMapper.selectOne(cartCondition);
				if(StringUtils.isEmpty(bean))
				{
					if(shopShoppingCart.getTotal() > Constants.JST_CART_COUNT)
					{
						logger.error("CartService::pageShopShoppingCart 超出购物车最大数量!");
						throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_MORE_THAN_CATDEF);
					}
					shopCartMapper.insertSelective(shopShoppingCart);
				}
				else
				{
					bean.setPackageOne(shopShoppingCart.getPackageOne());
					bean.setPrice(shopShoppingCart.getPrice());
					bean.setServiceTime(shopShoppingCart.getServiceTime());
					if(bean.getDeletedFlag().equals("Y"))
					{
						bean.setDeletedFlag("N");
						bean.setTotal(shopShoppingCart.getTotal());
					}
					else
					{
						bean.setTotal(bean.getTotal() + shopShoppingCart.getTotal());
					}
					if(bean.getTotal() > Constants.JST_CART_COUNT)
					{
						logger.error("CartService::pageShopShoppingCart 超出购物车最大数量!");
						throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_MORE_THAN_CATDEF);
					}
					shopCartMapper.updateByPrimaryKeySelective(bean);
				}
			}
		}
		logger.info("CartService::submitShopShoppingCart end result:{}",result);
		return result;	
	}
	
	public Integer countShopShoppingCart(String shopCode) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("CartService::countShopShoppingCart start shopCode:{}",shopCode);
		try
		{
			JstShopShoppingCart condition = new JstShopShoppingCart();
			condition.setShopCode(shopCode);
			condition.setDeletedFlag("N");
		//	result = shopCartMapper.sumShopShoppingCart(condition);
			result = shopCartMapper.selectCount(condition);
			if(StringUtils.isEmpty(result))
			{
				result = 0;
			}
		}
		catch(Exception e)
		{
			logger.error("CartService::countShopShoppingCart 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::countShopShoppingCart end result:{}",result);
		return result;	
	}
	
	public Integer submitMerchantShoppingCart(List<BsMerchantShoppingCartDTO> listCartDto) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("CartService::submitMerchantShoppingCart start listCartDto:{}",listCartDto);
		List<BsMerchantShoppingCart> listCart = BsMerchantShoppingCartRpcConvert.convertListDto(listCartDto);
		if(!StringUtils.isEmpty(listCart))
		{
			BsMerchantShoppingCart cartCondition = null;
			for(BsMerchantShoppingCart merchantShopingCart:listCart)
			{
				cartCondition = new BsMerchantShoppingCart();
				cartCondition.setMerchantCode(merchantShopingCart.getMerchantCode());
				cartCondition.setProductCode(merchantShopingCart.getProductCode());
				cartCondition.setMaterialCode(merchantShopingCart.getMaterialCode());
				BsMerchantShoppingCart bean = merchantCartMapper.selectOne(cartCondition);
				if(StringUtils.isEmpty(bean))
				{
					if(merchantShopingCart.getTotal() > Constants.JST_CART_COUNT)
					{
						logger.error("CartService::pageShopShoppingCart 超出购物车最大数量!");
						throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_MORE_THAN_CATDEF);
					}
					merchantCartMapper.insertSelective(merchantShopingCart);
				}
				else
				{
					bean.setPackageOne(merchantShopingCart.getPackageOne());
					bean.setPrice(merchantShopingCart.getPrice());
					bean.setServiceTime(merchantShopingCart.getServiceTime());
					if(bean.getDeletedFlag().equals("Y"))
					{
						bean.setDeletedFlag("N");
						bean.setTotal(merchantShopingCart.getTotal());
					}
					else
					{
						bean.setTotal(bean.getTotal()+merchantShopingCart.getTotal());
					}
					
					if(bean.getTotal() > Constants.JST_CART_COUNT)
					{
						logger.error("CartService::pageShopShoppingCart 超出购物车最大数量!");
						throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_MORE_THAN_CATDEF);
					}
					merchantCartMapper.updateByPrimaryKeySelective(bean);
				}
			}
		}
		logger.info("CartService::submitMerchantShoppingCart end result:{}",result);
		return result;
	}
	
	public Integer countMerchantShoppingCart(String merchantCode) throws RpcServiceException
	{
		Integer result = 0;
		logger.info("CartService::countMerchantShoppingCart start merchantCode:{}",merchantCode);
		try
		{
			BsMerchantShoppingCart condition = new BsMerchantShoppingCart();
			condition.setMerchantCode(merchantCode);
			condition.setDeletedFlag("N");
		//	result = merchantCartMapper.sumMerchantShoppingCart(condition);
			result = merchantCartMapper.selectCount(condition);
			if(StringUtils.isEmpty(result))
			{
				result = 0;
			}
		}
		catch(Exception e)
		{
			logger.error("CartService::countMerchantShoppingCart 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(JstErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("CartService::countMerchantShoppingCart end result:{}",result);
		return result;
	}
	
	
	
}
