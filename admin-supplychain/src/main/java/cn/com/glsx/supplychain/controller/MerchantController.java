package cn.com.glsx.supplychain.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glsx.biz.common.base.entity.Configuration;
import com.glsx.biz.common.base.service.ConfigurationService;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.enums.DefaultErrorEnum;
import cn.com.glsx.framework.core.enums.ErrorEnum;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.merchant.facade.model.entity.MerchantDetailFacade;
import cn.com.glsx.merchant.facade.model.entity.MerchantFacade;
import cn.com.glsx.merchant.facade.model.request.AddGroupMerchantFacadeRequest;
import cn.com.glsx.merchant.facade.model.request.GroupMerchantAddFacade;
import cn.com.glsx.merchant.facade.model.response.ResultResponse;
import cn.com.glsx.merchant.facade.remote.MerchantFacadeRemote;
import cn.com.glsx.merchant.facade.remote.MerchantGroupFacadeRemote;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.vo.MerchantChannelVo;
import cn.com.glsx.supplychain.vo.MerchantInfoVo;

@RestController
@RequestMapping("merchant")
public class MerchantController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MerchantFacadeRemote merchantFacadeRemote;
	
	@Autowired
	private MerchantGroupFacadeRemote merchantGroupFacadeRemote;
	
	@Autowired
	private ConfigurationService configurationService;
	
	@RequestMapping("addMerchantInfo")
	public  ResultEntity<MerchantInfoVo> addMerchantInfo(@RequestBody MerchantInfoVo merchantInfoVo)
	{
		MerchantInfoVo merchantInfoVo1 = new MerchantInfoVo();
		RpcResponse<MerchantInfoVo> result = RpcResponse.success(merchantInfoVo1) ;
		logger.info("MerchantController::addMerchantInfo start merchantInfoVo:{}",merchantInfoVo);
		
		MerchantFacade facade = new MerchantFacade();
		facade.setMerchantName(merchantInfoVo.getMerchantName());
		facade.setMerchantAlias(merchantInfoVo.getMerchantAlias());
		facade.setMerchantChannel(merchantInfoVo.getMerchantChannel());
		facade.setContactor(merchantInfoVo.getContactor());
		facade.setContactPhone(merchantInfoVo.getContactPhone());
		facade.setMerchantSource(merchantInfoVo.getMerchantSource());
		facade.setLng(merchantInfoVo.getLng());
		facade.setLat(merchantInfoVo.getLat());
		facade.setGpsAddress(merchantInfoVo.getGpsAddress());
		facade.setCreateTime(new Date());
		facade.setUpdateTime(new Date());
		facade.setStorePhoto(merchantInfoVo.getStorePhoto());
		
		MerchantDetailFacade detail = new MerchantDetailFacade();
		detail.setMerchantPortrait(merchantInfoVo.getMerchantPortrait());
		detail.setProvinceId(merchantInfoVo.getProvinceId());
		detail.setProvinceName(merchantInfoVo.getProvinceName());
		detail.setCityId(merchantInfoVo.getCityId());
		detail.setCityName(merchantInfoVo.getCityName());
		detail.setAreaId(merchantInfoVo.getAreaId());
		detail.setAreaName(merchantInfoVo.getAreaName());
		detail.setAddress(merchantInfoVo.getAddress());
		detail.setPhone(merchantInfoVo.getPhone());
		detail.setFax(merchantInfoVo.getFax());
		detail.setEmail(merchantInfoVo.getEmail());
		detail.setCreateTime(new Date());
		detail.setUpdateTime(new Date());
		
		AddGroupMerchantFacadeRequest facadeRequest = new AddGroupMerchantFacadeRequest();
		GroupMerchantAddFacade merchantAddFacade = new GroupMerchantAddFacade();
		
		try
		{
			RpcResponse<ResultResponse> response = merchantFacadeRemote.saveMerchantFacadeRemote(facade, detail);
			ResultResponse rsp = response.getResult();
			
			merchantInfoVo.setMerchantId(rsp.getMerchantId());
			merchantInfoVo.setGroupId(rsp.getGroupId());
			
			merchantAddFacade.setMerchantId(rsp.getMerchantId());
			merchantAddFacade.setMerchantType(400);
			facadeRequest.setGroupId(5);
			facadeRequest.setGroupMerchantAdd(merchantAddFacade);
			
			response = merchantGroupFacadeRemote.saveMerchantByGroupFacadeRemote(facadeRequest);
			rsp = response.getResult();
			RpcResponse.success(merchantInfoVo);
		}
		catch(RpcServiceException e)
		{
			result.setError(e.getError());
			result.setMessage(e.getMessage());
		}
		
		return ResultEntity.result(result);
	}
	
	@RequestMapping("listMerchantChannels")
	public ResultEntity<List<MerchantChannelVo>>listMerchantChannels(MerchantChannelVo channel)
	{
		RpcResponse<List<MerchantChannelVo>> response = null;
		List<MerchantChannelVo> listVo = new ArrayList<MerchantChannelVo>();
		List<Configuration> listConfig = configurationService.getByType("MERCHANT_LABEL");
		for(Configuration configuation:listConfig)
		{
			MerchantChannelVo channelVo = new MerchantChannelVo();
			channelVo.setChannelId(configuation.getConfId());
			channelVo.setChannelValue(configuation.getConfValue());
			listVo.add(channelVo);
		}
		return ResultEntity.success(listVo);
	}
}
