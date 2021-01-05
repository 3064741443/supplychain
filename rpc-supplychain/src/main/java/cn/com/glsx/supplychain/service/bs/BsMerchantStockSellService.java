package cn.com.glsx.supplychain.service.bs;

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
import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockSellMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSell;

@Service
public class BsMerchantStockSellService {

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockSellService.class);
	
	@Autowired
	private BsMerchantStockSellMapper merchantStockSellMapper;
	
	public Page<BsMerchantStockSell> pageMerchantStockSell(RpcPagination<BsMerchantStockSell> pagination) throws RpcServiceException
	{
		Page<BsMerchantStockSell> result;
		logger.info("BsMerchantStockSellService::pageMerchantStockSell start pagination:{}",pagination);
		try
		{
			BsMerchantStockSell condition = pagination.getCondition();
			condition.setDeletedFlag("N");
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			result = merchantStockSellMapper.pageMerchantStockSell(condition);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("BsMerchantStockSellService::pageMerchantStockSell 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockSellService::pageMerchantStockSell return result.size=" + (StringUtils.isEmpty(result)?"null":result.size()));
		return result;
	}
}
