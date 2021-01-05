package cn.com.glsx.supplychain.jst.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.model.request.MerchantLoginFacadeRequest;
import cn.com.glsx.merchant.facade.model.response.MerchantFacadeResponse;
import cn.com.glsx.merchant.facade.model.response.MerchantLoginFacadeResponse;
import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.merchant.facade.remote.MerchantLoginFacadeRemote;
import cn.com.glsx.supplychain.jst.vo.MerchantVO;

@Component
public class MerchantManager {

	private static final Logger logger = LoggerFactory.getLogger(MerchantManager.class);
	@Autowired
	private MerchantFacadeRemote merchantFacadeRemote;
	@Autowired
	private MerchantLoginFacadeRemote merchantLoginFacadeRemote;
	
	//通过手机号查询
	public MerchantVO getMerchantByPhone(String phone)
	{
		MerchantVO merchantVo = null;
		try
		{
			RpcResponse<MerchantFacadeResponse> rsp = merchantFacadeRemote.getMerchantFacadeByPhoneRemote(phone);	
			MerchantFacadeResponse merchantFacade = rsp.getResult();
			if(!StringUtils.isEmpty(merchantFacade))
			{
				merchantVo = new MerchantVO();
				merchantVo.setAgentMerchantCode(String.valueOf(merchantFacade.getMerchantId()));
				merchantVo.setAgentMerchantName(merchantFacade.getMerchantName());
			}
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage());
		}
		return merchantVo;
	}
	
	//商户号密码登录认证
	public MerchantVO loginMerchant(String merchantCode, String password)
	{
		MerchantVO merchantVo = null;
		try
		{
			MerchantLoginFacadeRequest request = new MerchantLoginFacadeRequest();
			request.setLoginAccount(merchantCode);
			request.setPassword(password);
			RpcResponse<MerchantLoginFacadeResponse> rsp = merchantLoginFacadeRemote.checkLoginFacadeRemote(request);
			MerchantLoginFacadeResponse merchantLoginFacade = rsp.getResult();
			if(!StringUtils.isEmpty(merchantLoginFacade))
			{
				merchantVo = new MerchantVO();
				merchantVo.setAgentMerchantCode(String.valueOf(merchantLoginFacade.getMerchantId()));
				merchantVo.setAgentMerchantName(merchantLoginFacade.getMerchantName());
			}
			
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage());
		}
		return merchantVo;
	}
	
	public Boolean updateMerchantPassword(String merchantCode, String password)
	{
		Boolean result = false;
		try
		{
			MerchantLoginFacadeRequest request = new MerchantLoginFacadeRequest();
			request.setLoginAccount(merchantCode);
			request.setPassword(password);
			RpcResponse<Boolean> rsp = merchantLoginFacadeRemote.updateMerchantLoginPasswordFacadeRemote(request);
			result = rsp.getResult();
		}
		catch(RpcServiceException e)
		{
			logger.error(e.getMessage());
		}
		return result;
	}
	
}
