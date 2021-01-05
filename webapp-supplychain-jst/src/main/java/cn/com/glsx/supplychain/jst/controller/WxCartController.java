package cn.com.glsx.supplychain.jst.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.convert.ShoppingCartHttpConvert;
import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.BsMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.DisJstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.DisMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;
import cn.com.glsx.supplychain.jst.vo.CartVO;
import cn.com.glsx.supplychain.jst.vo.DisCartVO;
import cn.com.glsx.supplychain.jst.vo.UserInfoVo;
import cn.com.glsx.supplychain.jst.web.session.Session;

/**
 * @Title: WxCartController.java
 * @Description: 微信小程序购物车
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@RestController
@RequestMapping("cart")
public class WxCartController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected HttpSession session;
	
	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;
	
	/**
	 * 获取购物车物品数量
	 */
	@RequestMapping("wxCountShoppingCart")
	public ResultEntity<Integer> wxCountShoppingCart()
	{
		Integer countCart = 0;
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxCountShoppingCart start userInfoVo:{}", userInfoVo);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxCountShoppingCart not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxCountShoppingCart is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			JstShopDTO dtoCondition = new JstShopDTO();
			dtoCondition.setShopCode(userInfoVo.getShopCode());
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<Integer> rsp = wxBsMerchantRemote.countShopShoppingCart(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxCountShoppingCart return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			countCart = rsp.getResult();
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			BsDealerUserInfoDTO dtoCondition = new BsDealerUserInfoDTO();
			dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<Integer> rsp = wxBsMerchantRemote.countMerchantShoppingCart(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxCountShoppingCart return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			countCart = rsp.getResult();
		}
		logger.info("WxProductController::wxCountShoppingCart end countCart:{}", countCart);
		return ResultEntity.success(countCart);
	}
	
	
	/**
	 * 购物车列表
	 */
	@RequestMapping("wxListShoppingCart")
	public ResultEntity<DisCartVO> wxListShoppingCart(@RequestBody DisCartVO disCart)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxListShoppingCart start userInfoVo:{},disCart:{}", userInfoVo,disCart);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxListShoppingCart not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxListShoppingCart is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			DisJstShopShoppingCartDTO dtoCondition = new DisJstShopShoppingCartDTO();
			dtoCondition.setPageNo(disCart.getPageNo());
			dtoCondition.setPageSize(disCart.getPageSize());
			dtoCondition.setShopCode(userInfoVo.getShopCode());
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<DisJstShopShoppingCartDTO> rsp = wxBsMerchantRemote.pageShopShoppingCart(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxListShoppingCart return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			DisJstShopShoppingCartDTO dtoResult = rsp.getResult();
			disCart.setListCartVo(ShoppingCartHttpConvert.convertListShopCartDto(dtoResult.getListCartDto()));
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			DisMerchantShoppingCartDTO dtoCondition = new DisMerchantShoppingCartDTO();
			dtoCondition.setPageNo(disCart.getPageNo());
			dtoCondition.setPageSize(disCart.getPageSize());
			dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(dtoCondition);
			RpcResponse<DisMerchantShoppingCartDTO> rsp = wxBsMerchantRemote.pageMerchantShoppingCart(dtoCondition);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxListShoppingCart return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			DisMerchantShoppingCartDTO dtoResult = rsp.getResult();
			disCart.setListCartVo(ShoppingCartHttpConvert.convertListMerchantCartDto(dtoResult.getListCartDto()));
		}
		logger.info("WxProductController::wxListShoppingCart end countCart:{}", disCart);
		return ResultEntity.success(disCart);
	}
	
	/**
	 * 删除购物车物品  返回购物车当前数量
	 */
	@RequestMapping("wxRemoveShoppingCart")
	public ResultEntity<Integer> wxRemoveShoppingCart(@RequestBody List<Integer> condition)
	{
		Integer result = 0;
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
	//	UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxRemoveShoppingCart start userInfoVo:{},condition:{}", userInfoVo,condition);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxRemoveShoppingCart not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxRemoveShoppingCart is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			if(StringUtils.isEmpty(condition) ||
					condition.size() == 0)
			{
				result = countShopShoppingCart(userInfoVo.getShopCode());
			}
			else
			{
				DisJstShopShoppingCartDTO dtoCondition = new DisJstShopShoppingCartDTO();
				List<JstShopShoppingCartDTO> listDtoCart = new ArrayList<JstShopShoppingCartDTO>();
				for(Integer item:condition)
				{
					JstShopShoppingCartDTO dto = new JstShopShoppingCartDTO();
					dto.setId(item);
					dto.setUpdatedBy(userInfoVo.getMerchantCode());
					dto.setUpdatedDate(JstUtils.getNowDate());
					dto.setDeletedFlag("Y");
					listDtoCart.add(dto);
				}
				dtoCondition.setListCartDto(listDtoCart);
				dtoCondition.setShopCode(userInfoVo.getShopCode());
				JstUtils.setBaseDTO(dtoCondition);
				RpcResponse<Integer> rsp = wxBsMerchantRemote.updateShopShoppingCart(dtoCondition);
				JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
				if (!StringUtils.isEmpty(errCodeEnum)) {
					logger.info("WxProductController::wxRemoveShoppingCart return"
							+ errCodeEnum.getDescrible());
					return ResultEntity.error(errCodeEnum.getCode(),
							errCodeEnum.getDescrible());
				}
				result = rsp.getResult();
			}
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			if(StringUtils.isEmpty(condition) ||
					condition.size() == 0)
			{
				result = this.countMerchantShoppingCart(userInfoVo.getMerchantCode());
			}
			else
			{
				DisMerchantShoppingCartDTO dtoCondition = new DisMerchantShoppingCartDTO();
				List<BsMerchantShoppingCartDTO> listDtoCart = new ArrayList<BsMerchantShoppingCartDTO>();
				for(Integer item:condition)
				{
					BsMerchantShoppingCartDTO dto = new BsMerchantShoppingCartDTO();
					dto.setId(item);
					dto.setUpdatedBy(userInfoVo.getMerchantCode());
					dto.setUpdatedDate(JstUtils.getNowDate());
					dto.setDeletedFlag("Y");
					listDtoCart.add(dto);
				}
				dtoCondition.setListCartDto(listDtoCart);
				dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
				JstUtils.setBaseDTO(dtoCondition);
				RpcResponse<Integer> rsp = wxBsMerchantRemote.updateMerchantShoppingCart(dtoCondition);
				JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
				if (!StringUtils.isEmpty(errCodeEnum)) {
					logger.info("WxProductController::wxRemoveShoppingCart return"
							+ errCodeEnum.getDescrible());
					return ResultEntity.error(errCodeEnum.getCode(),
							errCodeEnum.getDescrible());
				}
				result = rsp.getResult();
			}
		}
		logger.info("WxProductController::wxListShoppingCart end result:{}", result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 变更购物车商品数量
	 */
	@RequestMapping("wxUpdateShoppingCart")
	public ResultEntity<Integer> wxUpdateShoppingCart(@RequestBody DisCartVO disCart)
	{
		Integer result = 0;
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxUpdateShoppingCart start userInfoVo:{},disCart:{}", userInfoVo,disCart);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxUpdateShoppingCart not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxUpdateShoppingCart is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		if(StringUtils.isEmpty(disCart))
		{
			logger.info("WxProductController::wxUpdateShoppingCart param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			if(StringUtils.isEmpty(disCart.getListCartVo()) ||
					disCart.getListCartVo().size() == 0)
			{
				result = countShopShoppingCart(userInfoVo.getShopCode());
			}
			else
			{
				DisJstShopShoppingCartDTO dtoCondition = new DisJstShopShoppingCartDTO();
				List<JstShopShoppingCartDTO> listDtoCart = new ArrayList<JstShopShoppingCartDTO>();
				for(CartVO item:disCart.getListCartVo())
				{
					JstShopShoppingCartDTO dto = new JstShopShoppingCartDTO();
					dto.setId(item.getId());
					dto.setTotal(item.getOrderCount());
					dto.setUpdatedBy(userInfoVo.getMerchantCode());
					dto.setUpdatedDate(JstUtils.getNowDate());
					listDtoCart.add(dto);
				}
				dtoCondition.setListCartDto(listDtoCart);
				dtoCondition.setShopCode(userInfoVo.getShopCode());
				JstUtils.setBaseDTO(dtoCondition);
				RpcResponse<Integer> rsp = wxBsMerchantRemote.updateShopShoppingCart(dtoCondition);
				JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
				if (!StringUtils.isEmpty(errCodeEnum)) {
					logger.info("WxProductController::wxUpdateShoppingCart return"
							+ errCodeEnum.getDescrible());
					return ResultEntity.error(errCodeEnum.getCode(),
							errCodeEnum.getDescrible());
				}
				result = rsp.getResult();
			}
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			if(StringUtils.isEmpty(disCart.getListCartVo()) ||
					disCart.getListCartVo().size() == 0)
			{
				result = this.countMerchantShoppingCart(userInfoVo.getMerchantCode());
			}
			else
			{
				DisMerchantShoppingCartDTO dtoCondition = new DisMerchantShoppingCartDTO();
				List<BsMerchantShoppingCartDTO> listDtoCart = new ArrayList<BsMerchantShoppingCartDTO>();
				for(CartVO item:disCart.getListCartVo())
				{
					BsMerchantShoppingCartDTO dto = new BsMerchantShoppingCartDTO();
					dto.setId(item.getId());
					dto.setTotal(item.getOrderCount());
					dto.setUpdatedBy(userInfoVo.getMerchantCode());
					dto.setUpdatedDate(JstUtils.getNowDate());
					listDtoCart.add(dto);
				}
				dtoCondition.setListCartDto(listDtoCart);
				dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
				JstUtils.setBaseDTO(dtoCondition);
				RpcResponse<Integer> rsp = wxBsMerchantRemote.updateMerchantShoppingCart(dtoCondition);
				JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
				if (!StringUtils.isEmpty(errCodeEnum)) {
					logger.info("WxProductController::wxUpdateShoppingCart return"
							+ errCodeEnum.getDescrible());
					return ResultEntity.error(errCodeEnum.getCode(),
							errCodeEnum.getDescrible());
				}
				result = rsp.getResult();
			}
		}
		logger.info("WxProductController::wxUpdateShoppingCart end result:{}", result);
		return ResultEntity.success(result);
	}
	
	
	private Integer countShopShoppingCart(String shopCode)
	{
		Integer result = 0;
		JstShopDTO dtoCondition = new JstShopDTO();
		dtoCondition.setShopCode(shopCode);
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.countShopShoppingCart(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if(StringUtils.isEmpty(errCodeEnum))
		{
			result = rsp.getResult();
		}
		return result;
	}
	
	private Integer countMerchantShoppingCart(String merchantCode)
	{
		Integer result = 0;
		BsDealerUserInfoDTO dtoCondition = new BsDealerUserInfoDTO();
		dtoCondition.setMerchantCode(merchantCode);
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.countMerchantShoppingCart(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if(StringUtils.isEmpty(errCodeEnum))
		{
			result = rsp.getResult();
		}
		return result;
	}
	
}
