package cn.com.glsx.supplychain.service.bs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantStock;

@Service
public class BsMerchantStockService {

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockService.class);
	
	@Autowired
	private BsMerchantStockMapper merchantStockMapper;
	
	public Page<BsMerchantStock> pageMerchantStocks(RpcPagination<BsMerchantStock> pagination) throws RpcServiceException
	{
		Page<BsMerchantStock> result;
		logger.info("BsMerchantStockService::BsMerchantStockService start pagination:{}",pagination);
		try
		{
			BsMerchantStock condition = pagination.getCondition();
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			condition.setDeletedFlag("N");
			result = merchantStockMapper.pageMerchantStocks(condition);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("BsMerchantStockService::BsMerchantStockService 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockService::BsMerchantStockService return result.size=" + (StringUtils.isEmpty(result)?"null":result.size()));
		return result;
	}
	
	public BsMerchantStock getStatMerchantStocks(BsMerchantStock record) throws RpcServiceException
	{
		BsMerchantStock result;
		logger.info("BsMerchantStockService::getStatMerchantStocks start record:{}",record);
		try
		{
			record.setDeletedFlag("N");
			result = merchantStockMapper.getStatMerchantStocks(record);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("BsMerchantStockService::getStatMerchantStocks 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockService::BsMerchantStockService return result:{}",result);
		return result;
	}
	
	public List<BsMerchantStock> listMerchantStocks(BsMerchantStock record) throws RpcServiceException
	{
		List<BsMerchantStock> result;
		logger.info("BsMerchantStockService::listMerchantStocks start record:{}",record);
		try
		{
			record.setDeletedFlag("N");
			result = merchantStockMapper.listMerchantStocks(record);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("BsMerchantStockService::listMerchantStocks 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockService::listMerchantStocks return result:{}",result);
		return result;
	}
	
	
}
