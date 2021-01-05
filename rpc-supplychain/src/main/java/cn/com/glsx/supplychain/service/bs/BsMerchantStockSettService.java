package cn.com.glsx.supplychain.service.bs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockSettMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSett;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class BsMerchantStockSettService {

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockSettService.class);
	@Autowired
	private BsMerchantStockSettMapper merchantStockSettMapper;
	
	public Page<BsMerchantStockSett> pageMerchantStockSett(RpcPagination<BsMerchantStockSett> pagination) throws RpcServiceException
	{
		Page<BsMerchantStockSett> result;
		logger.info("BsMerchantStockSettService::pageMerchantStockSett start pagination:{}",pagination);
		try
		{
			BsMerchantStockSett condition = pagination.getCondition();
			condition.setDeletedFlag("N");
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			result = merchantStockSettMapper.pageMerchantStockSett(condition);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("BsMerchantStockSettService::pageMerchantStockSett 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockSettService::pageMerchantStockSett return result.size=" + (StringUtils.isEmpty(result)?"null":result.size()));
		return result;
	}
}
