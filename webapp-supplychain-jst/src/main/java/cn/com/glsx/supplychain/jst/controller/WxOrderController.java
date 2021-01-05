package cn.com.glsx.supplychain.jst.controller;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.common.Constants;
import cn.com.glsx.supplychain.jst.convert.*;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.enums.ShopOrderStatuEnum;
import cn.com.glsx.supplychain.jst.model.StatementSell;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;
import cn.com.glsx.supplychain.jst.vo.*;
import cn.com.glsx.supplychain.jst.web.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Title: WxOrderController.java
 * @Description: 微信小程序订单
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@RestController
@RequestMapping("order")
public class WxOrderController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	protected HttpSession session;
	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;
	/**
	 * 商户或者门店下单
	 */
	@RequestMapping("wxSubmitOrder")
	public ResultEntity<Integer> wxSubmitOrder(@RequestBody SubOrderVO subOrder)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxSubmitOrder start userInfoVo:{},subOrder:{}", userInfoVo,subOrder);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxCountShoppingCart not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && !userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxCountShoppingCart is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		if(StringUtils.isEmpty(subOrder.getAddressVo()) ||
				StringUtils.isEmpty(subOrder.getAddressVo().getId()) ||
				StringUtils.isEmpty(subOrder.getListCartVo()) ||
				subOrder.getListCartVo().size() == 0)
		{
			logger.info("WxProductController::wxSubmitOrder param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		
		RpcResponse<Integer> rsp = null;
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			SubShopOrderDTO dtoCondition = new SubShopOrderDTO();
			dtoCondition.setBsAddressDto(BsAddressHttpConvert.convertVO(subOrder.getAddressVo()));
			dtoCondition.setListCartDto(ShoppingCartHttpConvert.convertListShopCartVo(subOrder.getListCartVo()));
			dtoCondition.setShopCode(userInfoVo.getShopCode());
			JstUtils.setBaseDTO(dtoCondition);
			rsp = wxBsMerchantRemote.submitShopOrder(dtoCondition);
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			SubMerchantOrderDTO dtoCondition = new SubMerchantOrderDTO();
			dtoCondition.setBsAddressDto(BsAddressHttpConvert.convertVO(subOrder.getAddressVo()));
			dtoCondition.setListCartDto(ShoppingCartHttpConvert.convertListMerchantCartVo(subOrder.getListCartVo()));
			dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(dtoCondition);
			rsp = wxBsMerchantRemote.submitMerchantOrder(dtoCondition);
		}
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxSubmitOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer result = rsp.getResult();
		logger.info("WxProductController::wxSubmitOrder end result:{}", result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 采购订单列表（商户 订单 ）
	 */
	@RequestMapping("wxDisMerchantOrder")
	public ResultEntity<DisOrderVO> wxDisMerchantOrder(@RequestBody DisOrderVO disOrder)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxDisMerchantOrder start userInfoVo:{},disOrder:{}", userInfoVo,disOrder);
		if(StringUtils.isEmpty(userInfoVo) ||
				StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxDisMerchantOrder not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxDisMerchantOrder wrong role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		if(StringUtils.isEmpty(disOrder) ||
				StringUtils.isEmpty(disOrder.getPageNo()) ||
				StringUtils.isEmpty(disOrder.getPageSize()))
		{
			logger.info("WxProductController::wxSubmitOrder param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		
		DisOrderDTO dtoCondition = new DisOrderDTO();
		if(!StringUtils.isEmpty(disOrder.getContext()))
		{
			dtoCondition.setContext(disOrder.getContext());
		}
		if(!StringUtils.isEmpty(disOrder.getOrderTime()))
		{
			dtoCondition.setOrderTime(JstUtils.formatOrderTime(disOrder.getOrderTime()));
		}
		if(!StringUtils.isEmpty(disOrder.getServiceType()))
		{
			dtoCondition.setServiceType(DisProductHttpConvert.convertServiceTypeVO(disOrder.getServiceType()));
		}
		if(!StringUtils.isEmpty(disOrder.getStatus()))
		{
			dtoCondition.setStatus(disOrder.getStatus());
		}
		dtoCondition.setPageNo(disOrder.getPageNo());
		dtoCondition.setPageSize(disOrder.getPageSize());
		dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<DisOrderDTO> rsp = wxBsMerchantRemote.pageMerchantOrder(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxDisMerchantOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		DisOrderDTO dtoResult = rsp.getResult();
		disOrder.setListOrderVo(OrderHttpConvert.convertListDto(dtoResult.getListOrderDto()));
		logger.info("WxOrderController::wxDisMerchantOrder end condition:{}", disOrder);
		return ResultEntity.success(disOrder);
	}
	
	/**
	 * 获取供应商下门店
	 */
	@RequestMapping("wxListAgentJstShops")
	public ResultEntity<List<JstShopVO>> wxListAgentJstShops()
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxListAgentJstShops not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxListAgentJstShops wrong role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		BsDealerUserInfoDTO dtoCondition = new BsDealerUserInfoDTO();
		dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<List<JstShopDTO>> rsp = wxBsMerchantRemote.listJstShopByAgentMerchant(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxListAgentJstShops return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		List<JstShopDTO> listDtoReturn = rsp.getResult();
		List<JstShopVO> listReturn = JstShopHttpConvert.convertListDto(listDtoReturn);
		logger.info("WxProductController::wxListAgentJstShops end listReturn:{}",listReturn);
		return ResultEntity.success(listReturn);
	}
	
	/**
	 * 获取供应商下搜索门店
	 */
	@RequestMapping("wxListSearchAgentJstShops")
	public ResultEntity<DisShopVO> wxListSearchAgentJstShops(@RequestBody DisShopVO shopVo){
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxListSearchAgentJstShops not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxListSearchAgentJstShops wrong role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		if(null == shopVo){
			shopVo = new DisShopVO();
		}
		DisShopDTO condition = new DisShopDTO();
		condition.setAddr(shopVo.getAddr());
		condition.setAgentMerchantCode(userInfoVo.getMerchantCode());
		condition.setContact(shopVo.getContact());
		condition.setPhone(shopVo.getPhone());
		condition.setShopCode(shopVo.getShopCode());
		condition.setShopName(shopVo.getShopName());
		condition.setPageNo(shopVo.getPageNo());
		condition.setPageSize(shopVo.getPageSize());
		JstUtils.setBaseDTO(condition);
		RpcResponse<DisShopDTO> rsp = wxBsMerchantRemote.pageJstShopByAgentMerchant(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxListSearchAgentJstShops return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		DisShopDTO dtoReturn = rsp.getResult();		
		shopVo.setListShopVo(JstShopHttpConvert.convertListDto(dtoReturn.getListShopDto()));
		logger.info("WxProductController::wxListAgentJstShops end shopVo:{}",shopVo);
		return ResultEntity.success(shopVo);
	}
	
	/**
	 * 发货订单列表（门店 订单）
	 */
	@RequestMapping("wxDisShopOrder")
	public ResultEntity<DisOrderVO> wxDisShopOrder(@RequestBody DisOrderVO disOrder)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxDisShopOrder start userInfoVo:{},disOrder:{}", userInfoVo,disOrder);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxDisShopOrder not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && !userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxDisShopOrder is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		DisOrderDTO dtoCondition = new DisOrderDTO();
		if(!StringUtils.isEmpty(disOrder.getContext()))
		{
			dtoCondition.setContext(disOrder.getContext());
		}
		if(!StringUtils.isEmpty(disOrder.getOrderTime()))
		{
			dtoCondition.setOrderTime(JstUtils.formatOrderTime(disOrder.getOrderTime()));
		}
		if(!StringUtils.isEmpty(disOrder.getServiceType()))
		{
			dtoCondition.setServiceType(DisProductHttpConvert.convertServiceTypeVO(disOrder.getServiceType()));
		}
		if(!StringUtils.isEmpty(disOrder.getStatus()))
		{
			if(disOrder.getStatus().equals("UF") || disOrder.getStatus().equals("WS"))
			{
				dtoCondition.setStatus(ShopOrderStatuEnum.ORDER_UN.getValue());
			}
			else if(disOrder.getStatus().equals("FI"))
			{
				dtoCondition.setStatus(ShopOrderStatuEnum.ORDER_OV.getValue());
			}
			else
			{
				dtoCondition.setStatus(null);
			}
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			dtoCondition.setMerchantCode(disOrder.getMerchantCode());
			dtoCondition.setShopCode(userInfoVo.getShopCode());
		}
		else
		{
			dtoCondition.setShopCode(disOrder.getShopCode());
			dtoCondition.setMerchantCode(userInfoVo.getMerchantCode());
		}
		dtoCondition.setPageNo(disOrder.getPageNo());
		dtoCondition.setPageSize(disOrder.getPageSize());
		
		JstUtils.setBaseDTO(dtoCondition);
		logger.info("WxOrderController::wxDisShopOrder dtoCondition:{}", dtoCondition);
		RpcResponse<DisOrderDTO> rsp = wxBsMerchantRemote.pageShopOrder(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxDisShopOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		DisOrderDTO dtoResult = rsp.getResult();
		disOrder.setListOrderVo(OrderHttpConvert.convertListDto(dtoResult.getListOrderDto()));
		logger.info("WxOrderController::wxDisShopOrder end condition:{}", disOrder);
		return ResultEntity.success(disOrder);
	}
	
	/**
	 * 发货订单详情（门店 订单）
	 */
	@RequestMapping("wxDisShopOrderDetail")
	public ResultEntity<OrderVO> wxDisShopOrderDetail(@RequestBody OrderVO order)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxDisShopOrderDetail start userInfoVo:{},order:{}", userInfoVo,order);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxDisShopOrderDetail not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && !userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxDisShopOrderDetail is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		OrderDTO dtoCondition = new OrderDTO();
		dtoCondition.setOrderCode(order.getOrderCode());
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<OrderDTO> rsp = wxBsMerchantRemote.getShopOrderDetail(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxDisShopOrderDetail return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		OrderDTO dtoResult = rsp.getResult();
		OrderVO voResult = OrderHttpConvert.convertDto(dtoResult);
		logger.info("WxOrderController::wxDisShopOrder end voResult:{}", voResult);
		return ResultEntity.success(voResult);
	}
	
	/**
	 * 发货订单详情设备列表
	 */
	@RequestMapping("wxListShopOrderDetail")
	public ResultEntity<DisOrderDetailVO> wxListShopOrderDetail(@RequestBody DisOrderDetailVO disOrderDetail)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxListShopOrderDetail start userInfoVo:{},disOrderDetail:{}", userInfoVo,disOrderDetail);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxListShopOrderDetail not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && !userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxListShopOrderDetail is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		DisShopOrderDetailDTO dtoCondition = new DisShopOrderDetailDTO();
		dtoCondition.setOrderCode(disOrderDetail.getOrderCode());
		dtoCondition.setPageNo(disOrderDetail.getPageNo());
		dtoCondition.setPageSize(disOrderDetail.getPageSize());
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<DisShopOrderDetailDTO> rsp = wxBsMerchantRemote.listShopOrderDetail(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxListShopOrderDetail return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		DisShopOrderDetailDTO dtoResult = rsp.getResult();
		disOrderDetail.setListDetail(OrderDetailHttpConvert.convertListDto(dtoResult.getListDetailDto()));
		logger.info("WxOrderController::wxDisShopOrder end condition:{}", disOrderDetail);
		return ResultEntity.success(disOrderDetail);
	}
	
	/**
	 * 发货单添加明细 返回明细个数
	 */
	@RequestMapping("wxSubShopOrderDetail")
	public ResultEntity<Integer> wxSubShopOrderDetail(@RequestBody SubOrderDetailVO subOrderDetail)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
	//	UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxSubShopOrderDetail start userInfoVo:{},subOrderDetail:{}", userInfoVo,subOrderDetail);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxSubShopOrderDetail not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxSubShopOrderDetail must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
		dtoCondition.setOrderCode(subOrderDetail.getOrderCode());
		dtoCondition.setAddressDto(BsAddressHttpConvert.convertVO(subOrderDetail.getAddressVo()));
		dtoCondition.setLogisticsDto(BsLogisticsHttpConvert.convertVo(subOrderDetail.getLogisticsVo()));
		dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.submitShopOrderDetail(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxSubShopOrderDetail return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer count = rsp.getResult();
		logger.info("WxOrderController::wxSubShopOrderDetail end condition:{}", subOrderDetail);
		return ResultEntity.success(count);
	}
	
	
	/**
	 * 校验sn的正确性
	 */
	@RequestMapping("wxCheckOrderDetailSn")
	public ResultEntity<Integer> wxCheckOrderDetailSn(@RequestBody CheckSubSnVO checkSubSn)
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
	//	UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxOrderController::wxCheckOrderDetailSn start userInfoVo:{},checkSubSn:{}", userInfoVo,checkSubSn);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxCheckOrderDetailSn not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxCheckOrderDetailSn must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
		dtoCondition.setOrderCode(checkSubSn.getOrderCode());
		List<OrderDetailDTO> listOrderDetailDto = new ArrayList<OrderDetailDTO>();
		OrderDetailDTO orderDetailDto = new OrderDetailDTO();
		orderDetailDto.setSn(checkSubSn.getSn());
		listOrderDetailDto.add(orderDetailDto);
		dtoCondition.setListDetailDto(listOrderDetailDto);
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.checkShopOrderDetail(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxSubShopOrderDetail return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer count = rsp.getResult();
		logger.info("WxOrderController::wxSubShopOrderDetail end count:{}", count);
		return ResultEntity.success(count);
	}
	
	/**
	* 无订单发货验证sn
	*/
	@RequestMapping("wxCheckNoOrderDetailSn")
	public ResultEntity<Integer> wxCheckNoOrderDetailSn(@RequestBody CheckSubSnVO checkSubSn){
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxOrderController::wxCheckNoOrderDetailSn start userInfoVo:{},checkSubSn:{}", userInfoVo,checkSubSn);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxCheckNoOrderDetailSn not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxCheckNoOrderDetailSn must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
		OrderDetailDTO orderDetailDto = new OrderDetailDTO();
		List<OrderDetailDTO> listOrderDetailDto = new ArrayList<OrderDetailDTO>();
		orderDetailDto.setSn(checkSubSn.getSn());
		listOrderDetailDto.add(orderDetailDto);
		dtoCondition.setAgentMerchantCode(userInfoVo.getMerchantCode());
		dtoCondition.setListDetailDto(listOrderDetailDto);
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.checkShopOrderDetailNoOrder(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxCheckNoOrderDetailSn return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer data = rsp.getResult();
		logger.info("WxOrderController::wxCheckNoOrderDetailSn end data:{}", data);
		return ResultEntity.success(data);
	}
	
	/**
	* 无订单发货获取发货清单
	*/
	@RequestMapping("wxGetNoOrderDetail")
	public ResultEntity<DisNoOrderVO> wxGetNoOrderDetail(@RequestBody SubOrderDetailVO subOrderDetail){
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxOrderController::wxGetNoOrderDetail start userInfoVo:{},subOrderDetail:{}", userInfoVo,subOrderDetail);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxGetNoOrderDetail not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxGetNoOrderDetail must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
		dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
		dtoCondition.setAgentMerchantCode(userInfoVo.getMerchantCode());	
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<List<JstShopShoppingCartDTO>> rsp = wxBsMerchantRemote.getShopOrderDetailNoOrder(dtoCondition);
		if(null != rsp.getError()){
			logger.info("WxProductController::wxGetNoOrderDetail return" + rsp.getError().toString());
			return ResultEntity.error(rsp.getError().toString());
		}
		List<JstShopShoppingCartDTO> listCart = rsp.getResult();
		Map<String,DisNoOrderDetailVO> mapOrderDetail = new HashMap<String,DisNoOrderDetailVO>();
		DisNoOrderDetailVO vo = null;
		OrderDetailVO dtVo = null;
		for(JstShopShoppingCartDTO dto:listCart){
			dtVo = new OrderDetailVO();
			dtVo.setSn(dto.getSn());
			vo = mapOrderDetail.get(dto.getProductCode()+dto.getMaterialCode());
			if(null == vo){
				vo = new DisNoOrderDetailVO();
				vo.setProductCode(dto.getProductCode());
				vo.setProductName(dto.getProductName());
				vo.setMaterialCode(dto.getMaterialCode());
				vo.setMaterialName(dto.getMaterialName());
				List<OrderDetailVO> listDetail = new ArrayList<>();
				listDetail.add(dtVo);
				vo.setListDetail(listDetail);
				mapOrderDetail.put(dto.getProductCode()+dto.getMaterialCode(), vo);
			}else{
				vo.getListDetail().add(dtVo);
			}
			vo.setCount(vo.getListDetail().size());
		}
		DisNoOrderVO result = new DisNoOrderVO();
		result.setTotalCount(listCart.size());
		result.setListNoOrderDetail(mapOrderDetail.values().stream().collect(Collectors.toList()));
		logger.info("WxOrderController::wxGetNoOrderDetail end result:{}", result);
		return ResultEntity.success(result);
	}

	/**
	 * 扫码-无订单发货提交sn
	 */
	@RequestMapping("wxSubShopOrderDetailNoOrder")
	public ResultEntity<Integer> wxSubShopOrderDetailNoOrder(@RequestBody SubOrderDetailVO subOrderDetail) {
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxOrderController::wxSubShopOrderDetailNoOrder start userInfoVo:{},subOrderDetail:{}", userInfoVo, subOrderDetail);
		if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
		}
		if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
		}
		SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
		dtoCondition.setShopCode(subOrderDetail.getShopCode());
		dtoCondition.setAgentMerchantCode(userInfoVo.getMerchantCode());
		dtoCondition.setAddressDto(BsAddressHttpConvert.convertVO(subOrderDetail.getAddressVo()));
		dtoCondition.setLogisticsDto(BsLogisticsHttpConvert.convertVo(subOrderDetail.getLogisticsVo()));
		dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
		//新的前端发布版本时候去掉
		dtoCondition.setShipType(subOrderDetail.getShipType());
		dtoCondition.setScanType("N");

		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.submitShopOrderDetailNoOrder(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer count = rsp.getResult();
		logger.info("WxOrderController::wxSubShopOrderDetailNoOrder end count:{}", count);
		return ResultEntity.success(count);
	}

	/**
	 * 不扫码-无订单发货提交无明细
	 */
	@RequestMapping("noScanCodeWxSubShopOrderDetailNoOrder")
	public ResultEntity<Integer> noScanCodeWxSubShopOrderDetailNoOrder(@RequestBody SubOrderDetailVO subOrderDetail) {
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxOrderController::wxSubShopOrderDetailNoOrder start userInfoVo:{},subOrderDetail:{}", userInfoVo, subOrderDetail);
		if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
		}
		if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
		}
		if(subOrderDetail.getShipType().equals("O")){
			if(subOrderDetail.getLogisticsVo() == null 
					||StringUtils.isEmpty(subOrderDetail.getLogisticsVo().getCompany())
					||StringUtils.isEmpty(subOrderDetail.getLogisticsVo().getOrderNumber())){
				logger.info("WxProductController::wxSubShopOrderDetailNoOrder param error");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
			}
		}
		if(subOrderDetail.getAddressVo() == null){
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
		}
		SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
		dtoCondition.setShopCode(subOrderDetail.getShopCode());
		dtoCondition.setAgentMerchantCode(userInfoVo.getMerchantCode());
		dtoCondition.setAddressDto(BsAddressHttpConvert.convertVO(subOrderDetail.getAddressVo()));
		dtoCondition.setLogisticsDto(BsLogisticsHttpConvert.convertVo(subOrderDetail.getLogisticsVo()));
		dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
		if (subOrderDetail.getMaterialVOList() != null && subOrderDetail.getMaterialVOList().size() > 0) {
			dtoCondition.setMaterialDTOList(MaterialHttpConvert.convertListVo(subOrderDetail.getMaterialVOList()));
		}
		dtoCondition.setShipType(subOrderDetail.getShipType());
		dtoCondition.setScanType("Y");
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.noScanCodeWxSubShopOrderDetailNoOrder(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer count = rsp.getResult();
		logger.info("WxOrderController::wxSubShopOrderDetailNoOrder end count:{}", count);
		return ResultEntity.success(count);
	}

	/**
	 * 无订单发货-查询物料信息
	 */
	@RequestMapping(value = "wxMaterialListNoOrder", method = RequestMethod.GET)
	public ResultEntity<List<StatementSell>> listMaterialNoOrder() {
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxOrderController::wxSubShopOrderDetailNoOrder start userInfoVo:{},subOrderDetail:{}", userInfoVo);
		if (StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode())) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
		}
		if (!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue())) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder must agent role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(), null);
		}
		RpcResponse<List<StatementSell>> rsp = wxBsMerchantRemote.listMaterialNoOrder(userInfoVo.getMerchantCode());
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		List<StatementSell> statementSellList = rsp.getResult();
		logger.info("WxOrderController::wxSubShopOrderDetailNoOrder end statementSellList:{}", statementSellList);
		return ResultEntity.success(statementSellList);
	}


	
}
