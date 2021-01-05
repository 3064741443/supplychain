package cn.com.glsx.supplychain.jst.controller;

import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;







import org.bouncycastle.util.encoders.Base64;
import org.oreframework.util.http.HttpUtil;
import org.oreframework.util.json.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.config.WechatProperty;
import cn.com.glsx.supplychain.jst.convert.JstUserOpenIdHttpConvert;
import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.dto.JstUserOpenIdDTO;
import cn.com.glsx.supplychain.jst.dto.UserInfoDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.LoginTypeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.manager.MerchantManager;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.utils.AES;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.utils.MD5Util;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;
import cn.com.glsx.supplychain.jst.utils.WxPKCS7Encoder;
import cn.com.glsx.supplychain.jst.vo.JsCodeSessionVO;
import cn.com.glsx.supplychain.jst.vo.MerchantVO;
import cn.com.glsx.supplychain.jst.vo.UserInfoVo;
import cn.com.glsx.supplychain.jst.vo.WechatAuthSettingVO;
import cn.com.glsx.supplychain.jst.vo.WechatPhoneVO;
import cn.com.glsx.supplychain.jst.web.session.Session;


/**
 * @Title: WxLoginController.java
 * @Description: 微信小程序登陆
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@RestController
@RequestMapping("login")
public class WxLoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	final String JSCODE_2_SESSION_PATH = "https://api.weixin.qq.com/sns/jscode2session";

//	@Autowired
//	protected HttpSession session;
	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;
	@Autowired
	private MerchantManager merchantManager;
	@Autowired
	private WechatProperty wechatProperty;
	
	private WechatPhoneVO decryptWechatPhone(WechatAuthSettingVO authSetting, String sessionKey) {
        WechatPhoneVO wechatPhoneVO = null;
        AES aes = new AES();
        try {
            byte[] resultByte = aes.decrypt(Base64.decode(authSetting.getEncryptedData()), Base64.decode(sessionKey), Base64.decode(authSetting.getIv()));
            if (null != resultByte && resultByte.length > 0) {	
                String phoneInfo = new String(WxPKCS7Encoder.decode(resultByte));
                logger.info("decrypt wechat phone return:{}", phoneInfo);
                wechatPhoneVO = JacksonUtils.jsonCastBean(phoneInfo, WechatPhoneVO.class);
            }
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return wechatPhoneVO;
    }
	
	/**
     * 利用jscode登录
     *
     * @param code
     * @return
     * @author Alvin.zengqi
     * @date 2018年3月5日 下午2:54:31
     */
    @RequestMapping("jscode/login")
    public ResultEntity<UserInfoVo> loginByJsCode(@RequestParam(name = "code", required = true) String code) {
        
    	UserInfoVo result = null;
    	JsCodeSessionVO jsCodeSession = this.jsCode2Session(code);
        if (StringUtils.isEmpty(jsCodeSession)) {
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_JS_CODE.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_JS_CODE.getDescrible(),null);
        }
        Session session = ThreadLocalCache.getSession();
        logger.debug("get sessoin by code:{} return:{}", code, jsCodeSession);
        session.put(Constants.SessionKey.WEAPP_SESSION_KEY.getValue(), jsCodeSession.getSessionKey());
        session.put(Constants.SessionKey.OPENID.getValue(), jsCodeSession.getOpenId());
        
        if(!StringUtils.isEmpty(jsCodeSession.getOpenId()))
        {
        	JstUserOpenIdDTO dtoCondition = new JstUserOpenIdDTO();
            dtoCondition.setOpenid(jsCodeSession.getOpenId());
            dtoCondition.setDeletedFlag("N");
            JstUtils.setBaseDTO(dtoCondition);
            RpcResponse<JstUserOpenIdDTO> rsp = wxBsMerchantRemote.getJstUserOpenId(dtoCondition);
            JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
    		if (!StringUtils.isEmpty(errCodeEnum)) {
    			logger.info("WxLoginController::loginByJsCode return55555"
    					+ errCodeEnum.getDescrible());
    			return ResultEntity.error(errCodeEnum.getCode(),
    					errCodeEnum.getDescrible());
    		}
    		JstUserOpenIdDTO dtoResult = rsp.getResult();
    		result = JstUserOpenIdHttpConvert.convertDto(dtoResult);
    		if(!StringUtils.isEmpty(result))
    		{
    			session.put(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue(), result);
    		}
        }
        logger.info("WxLoginController::loginByJsCode :{}",result);
        return ResultEntity.success(result);
    }
    
   
	/**
	 * 手机号授权
	 */
	@RequestMapping("wxAuthPhone")
	public ResultEntity<UserInfoVo> wxAuthPhone(@RequestBody WechatAuthSettingVO authSetting)
	{
		Session session = ThreadLocalCache.getSession();
		logger.info("wechat auth phone settion:{}", authSetting);
		if (authSetting == null) {
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
        }
		String sessionKey = session.get(Constants.SessionKey.WEAPP_SESSION_KEY.getValue(), String.class);
		logger.info("wechat auth sessionkey:{}",sessionKey);
		 WechatPhoneVO wechatPhoneVO = null;
		 if (StringUtils.isEmpty(authSetting.getEncryptedData())
	                || StringUtils.isEmpty(authSetting.getIv())) {
	            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
	      }
		 logger.info("decryptWechatPhone with sessionKey:{}", sessionKey);
		 wechatPhoneVO = decryptWechatPhone(authSetting, sessionKey);
		 
		 UserInfoVo condition = new UserInfoVo();
		 condition.setPhone(wechatPhoneVO.getPhoneNumber());
		 condition.setLoginType(LoginTypeEnum.LOGIN_TYPE_PH.getValue());
		 condition.setConsumer(authSetting.getConsumer());
		 condition.setVersion(authSetting.getVersion()); 
		 return wxGetRole(condition);
	}
	
	
	/**
	 * 获取角色
	 */
	@RequestMapping("wxGetRole")
	public ResultEntity<UserInfoVo> wxGetRole(UserInfoVo userInfoVo) {
		
		logger.info("WxLoginController::wxGetRole userInfoVo:{}", userInfoVo);
		if (StringUtils.isEmpty(userInfoVo)
				|| StringUtils.isEmpty(userInfoVo.getLoginType())) {
			logger.info("WxLoginController::wxGetRole return111"
					+ JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),
					userInfoVo);
		}
		if (!this.isRightLoginType(userInfoVo)) {
			logger.info("WxLoginController::wxGetRole return11111112"
					+ JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),
					userInfoVo);
		}
		
		//验证密码
		if(userInfoVo.getLoginType().equals(LoginTypeEnum.LOGIN_TYPE_PS.getValue()))
		{
			userInfoVo.setRole(MerchantRoleEnum.ROLE_AGENT.getValue());
			if (!isRightPassword(userInfoVo)) {
				userInfoVo.setRole(MerchantRoleEnum.ROLE_SHOP.getValue());
				if (!isRightPassword(userInfoVo))
				{
					logger.info("WxLoginController::wxGetRole error password");
					return ResultEntity.error(
							JstErrorCodeEnum.ERRCODE_FAILED_PASSWORD.getCode(),
							JstErrorCodeEnum.ERRCODE_FAILED_PASSWORD.getDescrible());
				}	
			}
			userInfoVo.setRole(null);
		}
		
		if (!StringUtils.isEmpty(userInfoVo.getPhone())) {
			// 根据手机号去掉用欣伟的接口 获取商户号
			MerchantVO merchantVo = merchantManager.getMerchantByPhone(userInfoVo.getPhone());
			if(StringUtils.isEmpty(merchantVo))
			{
				logger.info("WxLoginController::wxGetRole return11111111113"
						+ JstErrorCodeEnum.ERRCODE_INVALID_PHONE.getDescrible());
				return ResultEntity.error(
						JstErrorCodeEnum.ERRCODE_INVALID_PHONE.getCode(),
						JstErrorCodeEnum.ERRCODE_INVALID_PHONE.getDescrible(),
						userInfoVo);
			}
			
			userInfoVo.setMerchantCode(merchantVo.getAgentMerchantCode()); // 补到这里面去
		}

		if (StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
			logger.info("WxLoginController::wxGetRole return11111111114"
					+ JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getDescrible());
			return ResultEntity.error(
					JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getCode(),
					JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getDescrible(),
					userInfoVo);
		}

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		// 根据商户号判断角色
		BeanUtils.copyProperties(userInfoVo, userInfoDTO);
		JstUtils.setBaseDTO(userInfoDTO);
		RpcResponse<UserInfoDTO> rsp = wxBsMerchantRemote
				.getRoleByMerchantCode(userInfoDTO);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxLoginController::wxGetRole return55555"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		if (!StringUtils.isEmpty(rsp.getResult())) {
			userInfoVo.setRole(rsp.getResult().getRole());
			userInfoVo.setMerchantCode(rsp.getResult().getMerchantCode());
			userInfoVo.setMerchantName(rsp.getResult().getMerchantName());
		}
		
		logger.info("WxLoginController::wxGetRole end userInfoVo:{}", userInfoVo);
		
		return ResultEntity.success(userInfoVo);
	}

	/**
	 * 登陆
	 */
	@RequestMapping("wxLogin")
	public ResultEntity<UserInfoVo> wxLogin(UserInfoVo userInfoVo) {
		
		logger.info("WxLoginController::wxLogin userInfoVo:{}", userInfoVo);
		if (StringUtils.isEmpty(userInfoVo)
				|| StringUtils.isEmpty(userInfoVo.getLoginType())
				|| StringUtils.isEmpty(userInfoVo.getRole())) {
			logger.info("WxLoginController::wxLogin return"
					+ JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),
					userInfoVo);
		}
		if (!this.isRightLoginType(userInfoVo)) {
			logger.info("WxLoginController::wxLogin return"
					+ JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
			return ResultEntity.error(
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),
					JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),
					userInfoVo);
		}
		if (userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_ALL.getValue())) {
			logger.info("WxLoginController::wxLogin return"
					+ JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible());
			return ResultEntity.error(
					JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(),
					JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),
					userInfoVo);
		}
		//如果手机号授权登录 不需要验证密码
		if(userInfoVo.getLoginType().equals(LoginTypeEnum.LOGIN_TYPE_PS.getValue()))
		{
			if (!isRightPassword(userInfoVo)) {
				logger.info("WxLoginController::wxLogin error password");
				return ResultEntity.error(
						JstErrorCodeEnum.ERRCODE_FAILED_PASSWORD.getCode(),
						JstErrorCodeEnum.ERRCODE_FAILED_PASSWORD.getDescrible());
			}
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			BsDealerUserInfoDTO dtoCondition = new BsDealerUserInfoDTO();
			dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<BsDealerUserInfoDTO> rsp = wxBsMerchantRemote.getDealerUserInfoByMerchantCode(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxLoginController::wxLogin return"	
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			BsDealerUserInfoDTO dtoReturn = rsp.getResult();
			userInfoVo.setMerchantName(dtoReturn.getMerchantName());
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			JstShopDTO dtoCondition = new JstShopDTO();
			dtoCondition.setShopMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<JstShopDTO> rsp = wxBsMerchantRemote.getJspShopByMerchantCode(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxLoginController::wxLogin return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			JstShopDTO dtoReturn = rsp.getResult();
			userInfoVo.setMerchantName(dtoReturn.getShopMerchantName());
			userInfoVo.setShopCode(dtoReturn.getShopCode());
			userInfoVo.setShopName(dtoReturn.getShopName());
		}
		
		logger.info("WxLoginController::wxLogin return userInfoVo:{}",
				userInfoVo);
		Session session = ThreadLocalCache.getSession();
		session.put(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue(), userInfoVo);
		String openId = session.get(Constants.SessionKey.OPENID.getValue(), String.class);
		if(!StringUtils.isEmpty(openId))
		{
			JstUserOpenIdDTO dtoCondition = JstUserOpenIdHttpConvert.convertVo(userInfoVo);
			dtoCondition.setOpenid(openId);
			dtoCondition.setDeletedFlag("N");
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<JstUserOpenIdDTO> rsp = wxBsMerchantRemote.saveJstUserOpenId(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxLoginController::wxLogin return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
		}
		//session.setAttribute(Constants.SESSION_LOGIN_USER, userInfoVo);
		return ResultEntity.success(userInfoVo);
	}

	/**
	 * 登出
	 */
	@RequestMapping("wxLogOut")
	public ResultEntity<UserInfoVo> wxLogOut() {
		//UserInfoVo userInfoVo = (UserInfoVo) session
		//		.getAttribute(Constants.SESSION_LOGIN_USER);
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxLoginController::wxLogOut start userInfoVo:{}",
				userInfoVo);
		
		String openId = session.get(Constants.SessionKey.OPENID.getValue(), String.class);
		if(!StringUtils.isEmpty(openId))
		{
			JstUserOpenIdDTO dtoCondition = new JstUserOpenIdDTO();
			dtoCondition.setOpenid(openId);
			dtoCondition.setDeletedFlag("Y");
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<JstUserOpenIdDTO> rsp = wxBsMerchantRemote.saveJstUserOpenId(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxLoginController::wxLogin return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
		}
		
		session.remove(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		session.remove(Constants.SessionKey.WEAPP_SESSION_KEY.getValue());
		session.remove(Constants.SessionKey.OPENID.getValue());
	//	session.removeAttribute(Constants.SESSION_LOGIN_USER);
		logger.info("WxLoginController::wxLogOut end userInfoVo:{}", userInfoVo);
		return ResultEntity.success(userInfoVo);
	}
	
	/**
	 * 修改商户密码
	 */
	@RequestMapping("wxUpdateMerchantPassword")
	public ResultEntity<UserInfoVo> wxUpdateMerchantPassword(UserInfoVo userInfoVo)
	{
		logger.info("WxLoginController::wxLogOut start userInfoVo:{}",
				userInfoVo);
		Boolean bRet = merchantManager.updateMerchantPassword(userInfoVo.getMerchantCode(), userInfoVo.getPassword());
		
		logger.info("WxLoginController::wxLogOut end bRet:{}",
				bRet);
		return ResultEntity.success(userInfoVo);
	}
	

	private boolean isRightPassword(UserInfoVo userInfoVo) {
		// 如果是手机号授权 不需要校验密码
		if (userInfoVo.getLoginType().equals(
				LoginTypeEnum.LOGIN_TYPE_PS.getValue())) {
			// 如果是门店的去欣伟那边验证帐号密码
			if (userInfoVo.getRole().equals(
					MerchantRoleEnum.ROLE_SHOP.getValue())) {
				//	String passwordMd5 = MD5Util.MD5Encode(userInfoVo.getPassword(), "UTF-8");
					MerchantVO merchantVo = merchantManager.loginMerchant(userInfoVo.getMerchantCode(), userInfoVo.getPassword());
					if(StringUtils.isEmpty(merchantVo))
					{
						return false;  //发布时改回
					}
					return true;
			}
			if (userInfoVo.getRole().equals(
					MerchantRoleEnum.ROLE_AGENT.getValue())) {
				BsDealerUserInfoDTO record = new BsDealerUserInfoDTO();
				record.setMerchantCode(userInfoVo.getMerchantCode());
				RpcResponse<BsDealerUserInfoDTO> rsp = wxBsMerchantRemote
						.getDealerUserInfoByMerchantCode(record);
				JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp
						.getError();
				if (!StringUtils.isEmpty(errCodeEnum)) {
					logger.info("WxLoginController::isRightPassword return"
							+ errCodeEnum.getDescrible());
					return false;
				}
				BsDealerUserInfoDTO bsDealerUserInfoDTO = rsp.getResult();
				if (StringUtils.isEmpty(bsDealerUserInfoDTO)) {
					logger.info("WxLoginController::isRightPassword null bsDealerUserInfoDTO");
					return false;
				}
				
	//			String passwordMd5 = MD5Util.MD5Encode(bsDealerUserInfoDTO.getPassword(), "UTF-8");
				
				if (bsDealerUserInfoDTO.getPassword().equals(
						userInfoVo.getPassword())) {
					logger.info("WxLoginController::isRightPassword bsDealerUserInfoDTO.getPassword()="
							+ bsDealerUserInfoDTO.getPassword()
							+ " userInfoVo.getPassword()"
							+ userInfoVo.getPassword());
					return true;
				}
			}
		}
		return false;
	}

	private boolean isRightLoginType(UserInfoVo userInfoVo) {
		if (userInfoVo.getLoginType().equals(
				LoginTypeEnum.LOGIN_TYPE_PH.getValue())) {
			if (!StringUtils.isEmpty(userInfoVo.getPhone())) {
				return true;
			}
		}
		if (userInfoVo.getLoginType().equals(
				LoginTypeEnum.LOGIN_TYPE_PS.getValue())) {
			if (!StringUtils.isEmpty(userInfoVo.getMerchantCode())
					&& !StringUtils.isEmpty(userInfoVo.getPassword())) {
				return true;
			}
		}
		return false;
	//	return true;
	}

	
	private JsCodeSessionVO jsCode2Session(String code)
	{
		try
		{
			Map<String, String> params = new HashMap<String, String>(5);
			params.put("appid", wechatProperty.getWeAppId());
			params.put("secret", wechatProperty.getWeAppSecret());
			params.put("grant_type", "authorization_code");
			params.put("js_code", code);
			String url = JSCODE_2_SESSION_PATH;
			logger.debug("js code to session request url:{}", url);
			logger.debug("js code to session request params:{}", params);
			String response = HttpUtil.get(url, params, 30*1000);
			logger.debug("js code to session response:{}", response);
			return (JsCodeSessionVO) JacksonUtils.jsonCastBean(response, JsCodeSessionVO.class);
		}
		catch (Exception e) {
			logger.error("js code to session error:" + e.getMessage(), e);
		}
		return null;
	}
}
