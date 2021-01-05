package cn.com.glsx.merchant.supplychain.controller.jst;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.framework.common.Constants;
import cn.com.glsx.merchant.supplychain.vo.UserInfoVo;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.UserInfoDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.LoginTypeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;


/**
 * @Title: WxLoginController.java
 * @Description: 微信小程序登陆
 * @author liuquan  
 * @version V1.0  
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@RestController
@RequestMapping("wxlogin")
public class WxLoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected HttpSession session;
	
	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;

	/**
	 * 获取角色
	 */
	@RequestMapping("wxGetRole")
	public ResultEntity<UserInfoVo> wxGetRole(UserInfoVo userInfoVo){
		
		logger.info("WxLoginController::wxGetRole userInfoVo:{}",userInfoVo);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getLoginType()))
		{
			logger.info("WxLoginController::wxGetRole return" + JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),userInfoVo);
		}
		if(!this.isRightLoginType(userInfoVo))
		{
			logger.info("WxLoginController::wxGetRole return" + JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),userInfoVo);
		}
		
		if(!StringUtils.isEmpty(userInfoVo.getPhone()))
		{
			//根据手机号去掉用欣伟的接口 获取商户号
			userInfoVo.setMerchantCode(getMerchantCodeByPhone(userInfoVo.getMerchantCode())); //补到这里面去
		}
		
		if(StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxLoginController::wxGetRole return" + JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getDescrible());
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getCode(), JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getDescrible(),userInfoVo);
		}
		
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		//根据商户号判断角色
		BeanUtils.copyProperties(userInfoVo, userInfoDTO);
		RpcResponse<UserInfoDTO> rsp = wxBsMerchantRemote.getRoleByMerchantCode(userInfoDTO);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			logger.info("WxLoginController::wxGetRole return" + errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),errCodeEnum.getDescrible());
		}
		if(!StringUtils.isEmpty(rsp.getResult()))
		{
			userInfoVo.setRole(rsp.getResult().getRole());
		}
		return ResultEntity.success(userInfoVo);	
	}
	
	/**
	 * 登陆
	 */
	@RequestMapping("wxLogin")
	public ResultEntity<UserInfoVo> wxLogin(UserInfoVo userInfoVo)
	{
		logger.info("WxLoginController::wxLogin userInfoVo:{}",userInfoVo);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getLoginType()))
		{
			logger.info("WxLoginController::wxLogin return" + JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),userInfoVo);
		}
		if(!this.isRightLoginType(userInfoVo))
		{
			logger.info("WxLoginController::wxLogin return" + JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),userInfoVo);
		}
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_ALL.getValue()))
		{
			logger.info("WxLoginController::wxLogin return" + JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible());
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),userInfoVo);
		}
		if(!isRightPassword(userInfoVo))
		{
			logger.info("WxLoginController::wxLogin error password");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_PASSWORD.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_PASSWORD.getDescrible());
		}
		logger.info("WxLoginController::wxLogin return userInfoVo:{}",userInfoVo);
		session.setAttribute(Constants.SESSION_LOGIN_USER, userInfoVo);
		return ResultEntity.success(userInfoVo);
	}
	
	/**
	 * 登出
	 */
	@RequestMapping("wxLogOut")
	public ResultEntity<UserInfoVo> wxLogOut()
	{
		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(Constants.SESSION_LOGIN_USER);
		logger.info("WxLoginController::wxLogOut start userInfoVo:{}",userInfoVo);
		session.removeAttribute(Constants.SESSION_LOGIN_USER);
		logger.info("WxLoginController::wxLogOut end userInfoVo:{}",userInfoVo);
		return ResultEntity.success(userInfoVo);
	}
	
	//根据手机号去掉用欣伟的接口 获取商户号
	private String getMerchantCodeByPhone(String phone)
	{
		return null;
	}
	
	private boolean isRightPassword(UserInfoVo userInfoVo)
	{
		//如果是手机号授权 不需要校验密码
		if(userInfoVo.getLoginType().equals(LoginTypeEnum.LOGIN_TYPE_PS.getValue()))
		{	
		//如果是门店的去欣伟那边验证帐号密码
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			
		}
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			BsDealerUserInfoDTO record = new BsDealerUserInfoDTO();
			record.setMerchantCode(userInfoVo.getMerchantCode());
			RpcResponse<BsDealerUserInfoDTO> rsp = wxBsMerchantRemote.getDealerUserInfoByMerchantCode(record);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if(!StringUtils.isEmpty(errCodeEnum))
			{
				logger.info("WxLoginController::isRightPassword return" + errCodeEnum.getDescrible());
				return false;
			}
			BsDealerUserInfoDTO bsDealerUserInfoDTO = rsp.getResult();
			if(StringUtils.isEmpty(bsDealerUserInfoDTO))
			{
				logger.info("WxLoginController::isRightPassword null bsDealerUserInfoDTO");
				return false;
			}
			if(!bsDealerUserInfoDTO.getPassword().equals(userInfoVo.getPassword()))
			{
				logger.info("WxLoginController::isRightPassword bsDealerUserInfoDTO.getPassword()=" + bsDealerUserInfoDTO.getPassword() + " userInfoVo.getPassword()" + userInfoVo.getPassword());
				return false;
			}
		}
		}
		return true;
	}
	
	private boolean isRightLoginType(UserInfoVo userInfoVo)
	{
		if(userInfoVo.getLoginType().equals(LoginTypeEnum.LOGIN_TYPE_PH.getValue()))
		{
			if(!StringUtils.isEmpty(userInfoVo.getPhone()))
			{
				return true;
			}
		}
		if(userInfoVo.getLoginType().equals(LoginTypeEnum.LOGIN_TYPE_PS.getValue()))
		{
			if(!StringUtils.isEmpty(userInfoVo.getMerchantCode()) && !StringUtils.isEmpty(userInfoVo.getPassword()))
			{
				return true;
			}
		}
		return false;
	}
	
}
