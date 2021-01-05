package cn.com.glsx.supplychain.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.oreframework.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.dto.RpcDebugInfo;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;

import com.alibaba.dubbo.config.annotation.Service;

@Component("RpcDebugInfoRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class RpcDebugInfoRemoteImpl implements RpcDebugInfoRemote{

	 private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Autowired
	 private RedisTemplate<String, String> redisClient;

	@Override
	public RpcResponse<RpcDebugInfo> operatorRedis(RpcDebugInfo record) 
	{	
		RpcResponse<RpcDebugInfo> result = null;
		logger.info("RpcDebugInfoRemoteImpl::operatorRedis start record:{}",record);
		try
		{
			if(!record.getOptModule().equals("redis"))
			{
				return RpcResponse.error(ErrorCodeEnum.ERRCODE_INVALID_REQ);
			}
			if(record.getOptFunction().equals("ad"))
			{
				addRedis(record);
			}
			else if(record.getOptFunction().equals("ck"))
			{
				checkRedis(record);
			}
			else if(record.getOptFunction().equals("de"))
			{
				delRedis(record);
			}
			else if(record.getOptFunction().equals("ke"))
			{
				keyRedis(record);
			}
			else
			{
				record.setRedisValue("未知操作");
			}
			return RpcResponse.success(record);
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage(),e);
			result = RpcResponse.error(e);
		}
		logger.info("SupplyChainRemoteImpl::operatorRedis end result:{}",result);
		return result;
		
	}
	
	private void keyRedis(RpcDebugInfo record)
	{
		Set result = redisClient.keys(record.getRedisKey());
		ArrayList<String> list = new ArrayList<>(result);
		record.setRedisValue(list);
	}
	
	private void checkRedis(RpcDebugInfo record)
	{
		record.setRedisValue(redisClient.opsForValue().get(record.getRedisKey()));
	}
	
	private void addRedis(RpcDebugInfo record)
	{
		String result = "暂时不支持!";
		record.setRedisValue(result);	
	}
	
	private void delRedis(RpcDebugInfo record)
	{
		redisClient.delete(record.getRedisKey());
		record.setRedisValue("删除成功!");
	}
}
