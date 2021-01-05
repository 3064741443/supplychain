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
import cn.com.glsx.supplychain.mapper.bs.BsMerchantStockRetnMapper;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockRetn;

@Service
public class BsMerchantStockRetnService {

	private static final Logger logger = LoggerFactory.getLogger(BsMerchantStockRetnService.class);
	@Autowired
	private BsMerchantStockRetnMapper merchantStockRetnMapper;
	
	public Page<BsMerchantStockRetn> pageMerchantStockRetn(RpcPagination<BsMerchantStockRetn> pagination) throws RpcServiceException
	{
		Page<BsMerchantStockRetn> result;
		logger.info("BsMerchantStockRetnService::pageMerchantStockRetn start pagination:{}",pagination);
		try
		{
			BsMerchantStockRetn condition = pagination.getCondition();
			condition.setDeletedFlag("N");
			PageHelper.startPage(pagination.getPageNum(),pagination.getPageSize());
			result = merchantStockRetnMapper.pageMerchantStockRetn(condition);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("BsMerchantStockRetnService::pageMerchantStockRetn 数据库获取数据异常" + e.getMessage());
			throw new RpcServiceException(ErrorCodeEnum.ERRCODE_SYSTEM_DATABASE_FAILED);
		}
		logger.info("BsMerchantStockRetnService::pageMerchantStockRetn return result.size=" + (StringUtils.isEmpty(result)?"null":result.size()));
		return result;
	}
}
