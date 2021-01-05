package cn.com.glsx.supplychain.jst.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.com.glsx.supplychain.jst.convert.DisProductHttpConvert;
import cn.com.glsx.supplychain.jst.convert.JstShopAgentRelationHttpConvert;
import cn.com.glsx.supplychain.jst.convert.ProductHttpConvert;
import cn.com.glsx.supplychain.jst.dto.BsDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.BsMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.DisDealerUserInfoDTO;
import cn.com.glsx.supplychain.jst.dto.DisProductDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopAgentRelationDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopDTO;
import cn.com.glsx.supplychain.jst.dto.JstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.ProductDTO;
import cn.com.glsx.supplychain.jst.dto.SubBsMerchantShoppingCartDTO;
import cn.com.glsx.supplychain.jst.dto.SubJstShopShoppingCartDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.utils.ThreadLocalCache;
import cn.com.glsx.supplychain.jst.vo.DisProductVO;
import cn.com.glsx.supplychain.jst.vo.MaterialVO;
import cn.com.glsx.supplychain.jst.vo.MerchantVO;
import cn.com.glsx.supplychain.jst.vo.ProductVO;
import cn.com.glsx.supplychain.jst.vo.UserInfoVo;
import cn.com.glsx.supplychain.jst.web.session.Session;

/**
 * @Title: WxProductController.java
 * @Description: 微信小程序产品展示
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@RestController
@RequestMapping("product")
public class WxProductController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	protected HttpSession session;

	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;
	
	/**
	 * 获取代理商
	 */
	@RequestMapping("wxListShopAgentMerchant")
	public ResultEntity<List<MerchantVO>> listShopAgentMerchant()
	{
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::listShopAgentMerchant start userInfoVo:{}", userInfoVo);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::listShopAgentMerchant not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(!userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			logger.info("WxProductController::listShopAgentMerchant listShopAgentMerchant is not shop role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		JstShopDTO dtoCondition = new JstShopDTO();
		dtoCondition.setShopMerchantCode(userInfoVo.getMerchantCode());
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<List<JstShopAgentRelationDTO>> rsp = wxBsMerchantRemote.listShopAgentMerchant(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::listShopAgentMerchant return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		List<JstShopAgentRelationDTO> result = rsp.getResult();
		List<MerchantVO> voList = JstShopAgentRelationHttpConvert.convertDTOList(result);
		logger.info("WxProductController::listShopAgentMerchant end voList:{}", voList);
		return ResultEntity.success(voList);
	}
	
	/**
	 * 获取产品列表
	*/
	@RequestMapping("wxListProduct")
	public ResultEntity<DisProductVO> listProduct(@RequestBody DisProductVO disProduct)
	{
		DisDealerUserInfoDTO disDealerUserInfoDTO = new DisDealerUserInfoDTO();
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::listProduct start userInfoVo:{},disProduct:{}", userInfoVo,disProduct);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::listProduct not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::listProduct listShopAgentMerchant is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		if(StringUtils.isEmpty(disProduct.getListMerchantCode()))
		{
			//如果是门店登录 默认查询所有商户下的商品
			if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
			{
				disProduct.setListMerchantCode(listShopAgentMerchantCodes(userInfoVo));
			}
		}
		if(StringUtils.isEmpty(disProduct.getListMerchantCode()))
		{
			logger.info("WxProductController::listProduct null list merchant codes");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getCode(), JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getDescrible(),null);
		}
		
		disDealerUserInfoDTO.setListMerchantCode(disProduct.getListMerchantCode());
		JstUtils.setBaseDTO(disDealerUserInfoDTO);
		RpcResponse<DisDealerUserInfoDTO> rsp = wxBsMerchantRemote.listDealerUserInfo(disDealerUserInfoDTO);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::listProduct return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		disDealerUserInfoDTO = rsp.getResult();
		if(StringUtils.isEmpty(disDealerUserInfoDTO))
		{
			logger.info("WxProductController::listProduct 商户用户表未建立");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER.getCode(), JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER.getDescrible(),null);
		}
		
		DisProductDTO dtoConditon = DisProductHttpConvert.convertVo(disProduct, disDealerUserInfoDTO.getListDealerUserInfoDTO());
		JstUtils.setBaseDTO(dtoConditon);
		RpcResponse<DisProductDTO> rspProduct = wxBsMerchantRemote.listProduct(dtoConditon);
		errCodeEnum = (JstErrorCodeEnum)rspProduct.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			logger.info("WxProductController::listProduct return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		DisProductVO voReturn = DisProductHttpConvert.convertDto(rspProduct.getResult());
		logger.info("WxProductController::listProduct end voReturn:{}", voReturn);
		return ResultEntity.success(voReturn);
	}
	
	/**
	 * 获取产品(下拉条件用)
	*/
	@RequestMapping("wxListProductBase")
	private ResultEntity<DisProductVO> wxListProductBase(@RequestBody DisProductVO disProduct)
	{
		DisDealerUserInfoDTO disDealerUserInfoDTO = new DisDealerUserInfoDTO();
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		logger.info("WxProductController::wxListProductBase start userInfoVo:{},disProduct:{}", userInfoVo,disProduct);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxListProductBase not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxListProductBase listShopAgentMerchant is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		if(StringUtils.isEmpty(disProduct.getListMerchantCode()))
		{
			//如果是门店登录 默认查询所有商户下的商品
			if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
			{
				disProduct.setListMerchantCode(listShopAgentMerchantCodes(userInfoVo));
			}
			else
			{
				List<String> listMerchantCode = new ArrayList<String>();
				listMerchantCode.add(userInfoVo.getMerchantCode());
				disProduct.setListMerchantCode(listMerchantCode);
			}
		}
		if(StringUtils.isEmpty(disProduct.getListMerchantCode()))
		{
			logger.info("WxProductController::wxListProductBase null list merchant codes");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getCode(), JstErrorCodeEnum.ERRCODE_NULL_MERCHANTCODE.getDescrible(),null);
		}
		disDealerUserInfoDTO.setListMerchantCode(disProduct.getListMerchantCode());
		JstUtils.setBaseDTO(disDealerUserInfoDTO);
		RpcResponse<DisDealerUserInfoDTO> rsp = wxBsMerchantRemote.listDealerUserInfo(disDealerUserInfoDTO);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxListProductBase return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		disDealerUserInfoDTO = rsp.getResult();
		if(StringUtils.isEmpty(disDealerUserInfoDTO))
		{
			logger.info("WxProductController::wxListProductBase 商户用户表未建立");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER.getCode(), JstErrorCodeEnum.ERRCODE_NULL_DEALERUSER.getDescrible(),null);
		}
		
		DisProductDTO dtoConditon = DisProductHttpConvert.convertVo(disProduct, disDealerUserInfoDTO.getListDealerUserInfoDTO());
		JstUtils.setBaseDTO(dtoConditon);
		RpcResponse<DisProductDTO> rspProduct = wxBsMerchantRemote.listProductBase(dtoConditon);
		errCodeEnum = (JstErrorCodeEnum)rspProduct.getError();
		if(!StringUtils.isEmpty(errCodeEnum))
		{
			logger.info("WxProductController::wxListProductBase return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		
		if(!StringUtils.isEmpty(rspProduct.getResult()) && !StringUtils.isEmpty(rspProduct.getResult().getListProductDTO()))
		{
			List<ProductDTO> listProductFiltName = new ArrayList<ProductDTO>();
			Map<String, Integer> mapProductFileName = new HashMap<String, Integer>();
			for(ProductDTO dto:rspProduct.getResult().getListProductDTO())
			{
				Integer bFind = mapProductFileName.get(dto.getProductName());
				if(!StringUtils.isEmpty(bFind))
				{
					continue;
				}
				mapProductFileName.put(dto.getProductName(), 1);
				listProductFiltName.add(dto);
			}
			rspProduct.getResult().setListProductDTO(listProductFiltName);
		}
		
		DisProductVO voReturn = DisProductHttpConvert.convertDto(rspProduct.getResult());
		logger.info("WxProductController::wxListProductBase end voReturn:{}", voReturn);
		return ResultEntity.success(voReturn);
		
		
	}
	
	private List<String> listShopAgentMerchantCodes(UserInfoVo userInfoVo)
	{
		List<String> result = null;
		JstShopDTO dtoCondition = new JstShopDTO();
		dtoCondition.setShopCode(userInfoVo.getShopCode());
		dtoCondition.setShopMerchantCode(userInfoVo.getMerchantCode());
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<List<JstShopAgentRelationDTO>> rsp = wxBsMerchantRemote.listShopAgentMerchant(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::listProduct return"
					+ errCodeEnum.getDescrible());
			return result;
		}
		List<JstShopAgentRelationDTO>  rpcResult = rsp.getResult();
		if(StringUtils.isEmpty(rpcResult))
		{
			return result;
		}
		for(JstShopAgentRelationDTO dto:rpcResult)
		{
			if(StringUtils.isEmpty(result))
			{
				result = new ArrayList<String>();
			}
			result.add(dto.getAgentMerchantCode());
		}
		return result;
	}
	
	/**
	 * 获取产品下硬件物料列表
	 */
	@RequestMapping("wxListProductMaterial")
	public ResultEntity<ProductVO> wxListProductMaterial(@RequestBody ProductVO product)
	{	
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxListProductMaterial start userInfoVo:{},product:{}", userInfoVo,product);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxListProductMaterial not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxListProductMaterial listShopAgentMerchant is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		if(StringUtils.isEmpty(product.getProductCode()) || 
				StringUtils.isEmpty(product.getProductName()) ||
				StringUtils.isEmpty(product.getMerchantCode()) ||
				StringUtils.isEmpty(product.getMerchantName()))
		{
			logger.info("WxProductController::wxListProductMaterial param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		ProductDTO dtoCondition = ProductHttpConvert.convertVo(product);
		JstUtils.setBaseDTO(dtoCondition);
		RpcResponse<ProductDTO> rsp = wxBsMerchantRemote.listProductMaterial(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("WxProductController::wxListProductMaterial return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		ProductVO retVo = ProductHttpConvert.convertDto(rsp.getResult());
		logger.info("WxProductController::wxListProductMaterial end retVo:{}", retVo);
		return ResultEntity.success(retVo);
	}
	
	/**
	 * 添加产品到购物车 返回购物车中物件数量
	 */
	@RequestMapping("wxAddProductToCart")
	public ResultEntity<Integer> wxAddProductToCart(@RequestBody ProductVO product)
	{
		Integer countCart = 0;
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
//		UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxAddProductToCart start userInfoVo:{},product:{}", userInfoVo,product);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxAddProductToCart not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxAddProductToCart listShopAgentMerchant is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			if(StringUtils.isEmpty(product.getProductCode()) ||
					StringUtils.isEmpty(product.getMerchantCode()) ||
					StringUtils.isEmpty(product.getListMaterialVo()) ||
					product.getListMaterialVo().size() == 0)
			{
				logger.info("WxProductController::wxAddProductToCart param error");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
			}
			
			JstShopDTO jstShopDto = getJstShop(userInfoVo.getMerchantCode());
			if(StringUtils.isEmpty(jstShopDto))
			{
				logger.info("WxProductController::wxAddProductToCart jstShop is not exists");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_SHOP.getCode(), JstErrorCodeEnum.ERRCODE_NULL_SHOP.getDescrible(),null);
			}
			JstShopAgentRelationDTO jspShopAgentDTO = getJspShopAgentRelation(jstShopDto.getShopCode(),product.getMerchantCode());
			if(StringUtils.isEmpty(jspShopAgentDTO))
			{
				logger.info("WxProductController::wxAddProductToCart jspShopAgentDTO is not exists");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_SHOPAGENTMERCHANT.getCode(), JstErrorCodeEnum.ERRCODE_NULL_SHOPAGENTMERCHANT.getDescrible(),null);
			}
			ProductDTO jstProduct = getJstProduct(product.getProductCode());
			if(StringUtils.isEmpty(jstProduct))
			{
				logger.info("WxProductController::wxAddProductToCart jspShopAgentDTO is not exists");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_PRODUCT.getCode(), JstErrorCodeEnum.ERRCODE_NULL_PRODUCT.getDescrible(),null);
			}
			SubJstShopShoppingCartDTO subDto = new SubJstShopShoppingCartDTO();
			List<JstShopShoppingCartDTO> listCartDto = new ArrayList<JstShopShoppingCartDTO>();
			//展开购物车
			for(MaterialVO item:product.getListMaterialVo())
			{
				JstShopShoppingCartDTO cartDto = new JstShopShoppingCartDTO();
				cartDto.setAgentMerchantCode(jspShopAgentDTO.getAgentMerchantCode());
				cartDto.setAgentMerchantName(jspShopAgentDTO.getAgentMerchantName());
				cartDto.setCreatedBy(userInfoVo.getMerchantCode());
				cartDto.setUpdatedBy(userInfoVo.getMerchantCode());
				cartDto.setCreatedDate(JstUtils.getNowDate());
				cartDto.setUpdatedDate(JstUtils.getNowDate());
				cartDto.setDeletedFlag("N");
				cartDto.setMaterialCode(item.getMaterialCode());
				cartDto.setMaterialName(item.getMaterialName());
				cartDto.setPackageOne(jstProduct.getPackageOne());
				cartDto.setPrice(jstProduct.getPrice());
				cartDto.setProductCode(jstProduct.getProductCode());
				cartDto.setProductName(jstProduct.getProductName());
				cartDto.setServiceTime(jstProduct.getServiceTime());
				cartDto.setShopCode(jstShopDto.getShopCode());
				cartDto.setShopName(jstShopDto.getShopName());
				cartDto.setTotal(1);
				listCartDto.add(cartDto);
			}
			subDto.setListCartDto(listCartDto);
			subDto.setShopCode(jstShopDto.getShopCode());
			JstUtils.setBaseDTO(subDto);
			RpcResponse<SubJstShopShoppingCartDTO> rsp = wxBsMerchantRemote.submitJspShopShoppingCart(subDto);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxAddProductToCart return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			SubJstShopShoppingCartDTO dtoResult = rsp.getResult();
			if(!StringUtils.isEmpty(dtoResult))
			{
				countCart = dtoResult.getTotalCount();
			}
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			if(StringUtils.isEmpty(product.getProductCode()) ||
					StringUtils.isEmpty(product.getListMaterialVo()) ||
					product.getListMaterialVo().size() == 0)
			{
				logger.info("WxProductController::wxAddProductToCart param error");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
			}
			ProductDTO jstProduct = getJstProduct(product.getProductCode());
			if(StringUtils.isEmpty(jstProduct))
			{
				logger.info("WxProductController::wxAddProductToCart jspShopAgentDTO is not exists");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_NULL_PRODUCT.getCode(), JstErrorCodeEnum.ERRCODE_NULL_PRODUCT.getDescrible(),null);
			}
			
			SubBsMerchantShoppingCartDTO subDto = new SubBsMerchantShoppingCartDTO();
			List<BsMerchantShoppingCartDTO> listCartDto = new ArrayList<BsMerchantShoppingCartDTO>();
			//展开购物车
			for(MaterialVO item:product.getListMaterialVo())
			{
				BsMerchantShoppingCartDTO cartDto = new BsMerchantShoppingCartDTO();
				cartDto.setCreatedBy(userInfoVo.getMerchantCode());
				cartDto.setUpdatedBy(userInfoVo.getMerchantCode());
				cartDto.setCreatedDate(JstUtils.getNowDate());
				cartDto.setUpdatedDate(JstUtils.getNowDate());
				cartDto.setDeletedFlag("N");
				cartDto.setMaterialCode(item.getMaterialCode());
				cartDto.setMaterialName(item.getMaterialName());
				cartDto.setMerchantCode(userInfoVo.getMerchantCode());
				cartDto.setMerchantName(userInfoVo.getMerchantName());
				cartDto.setPackageOne(jstProduct.getPackageOne());
				cartDto.setPrice(jstProduct.getPrice());
				cartDto.setProductCode(jstProduct.getProductCode());
				cartDto.setProductName(jstProduct.getProductName());
				cartDto.setServiceTime(jstProduct.getServiceTime());
				cartDto.setTotal(1);
				listCartDto.add(cartDto);
			}
			subDto.setListCartDto(listCartDto);
			subDto.setMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(subDto);
			RpcResponse<SubBsMerchantShoppingCartDTO> rsp = wxBsMerchantRemote.submitBsMerchantShoppingCart(subDto);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxAddProductToCart return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			SubBsMerchantShoppingCartDTO dtoResult = rsp.getResult();
			if(!StringUtils.isEmpty(dtoResult))
			{
				countCart = dtoResult.getTotalCount();
			}
		}
		
		logger.info("WxProductController::wxAddProductToCart end countCart:{}", countCart);
		return ResultEntity.success(countCart);
	}
	
	
	/**
	 * 添加产品到购物车 返回购物车中物件数量
	 */
	@RequestMapping("wxAddProductToCartNoVerify")
	public ResultEntity<Integer> wxAddProductToCartNoVerify(@RequestBody ProductVO product)
	{
		Integer countCart = 0;
		Session session = ThreadLocalCache.getSession();
		UserInfoVo userInfoVo = (UserInfoVo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
	//	UserInfoVo userInfoVo = (UserInfoVo)session.getAttribute(cn.com.glsx.supplychain.jst.common.Constants.SESSION_LOGIN_USER);
		logger.info("WxProductController::wxAddProductToCartNoVerify start userInfoVo:{},product:{}", userInfoVo,product);
		if(StringUtils.isEmpty(userInfoVo) || StringUtils.isEmpty(userInfoVo.getMerchantCode()))
		{
			logger.info("WxProductController::wxAddProductToCartNoVerify not login or session time out");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()) && userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			logger.info("WxProductController::wxAddProductToCartNoVerify is all role");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
		}
		
		if(StringUtils.isEmpty(product.getProductCode()) ||
				StringUtils.isEmpty(product.getProductName()) ||
				StringUtils.isEmpty(product.getPackageOne()) ||
				StringUtils.isEmpty(product.getPrice()) ||
				StringUtils.isEmpty(product.getServiceTime()) ||
				StringUtils.isEmpty(product.getPackageOne()) ||
				StringUtils.isEmpty(product.getListMaterialVo()) ||
				product.getListMaterialVo().size() == 0)
		{
			logger.info("WxProductController::wxAddProductToCartNoVerify param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		
		if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_SHOP.getValue()))
		{
			if(StringUtils.isEmpty(product.getMerchantCode()) ||
					StringUtils.isEmpty(product.getMerchantName()))
			{
				logger.info("WxProductController::wxAddProductToCartNoVerify param error");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
			}
			
			SubJstShopShoppingCartDTO subDto = new SubJstShopShoppingCartDTO();
			List<JstShopShoppingCartDTO> listCartDto = new ArrayList<JstShopShoppingCartDTO>();
			//展开购物车
			for(MaterialVO item:product.getListMaterialVo())
			{
				JstShopShoppingCartDTO cartDto = new JstShopShoppingCartDTO();
				cartDto.setAgentMerchantCode(product.getMerchantCode());
				cartDto.setAgentMerchantName(product.getMerchantName());
				cartDto.setCreatedBy(userInfoVo.getMerchantCode());
				cartDto.setUpdatedBy(userInfoVo.getMerchantCode());
				cartDto.setCreatedDate(JstUtils.getNowDate());
				cartDto.setUpdatedDate(JstUtils.getNowDate());
				cartDto.setDeletedFlag("N");
				cartDto.setMaterialCode(item.getMaterialCode());
				cartDto.setMaterialName(item.getMaterialName());
				cartDto.setPackageOne(product.getPackageOne());
				cartDto.setPrice(product.getPrice());
				cartDto.setProductCode(product.getProductCode());
				cartDto.setProductName(product.getProductName());
				cartDto.setServiceTime(product.getServiceTime());
				cartDto.setShopCode(userInfoVo.getShopCode());
				cartDto.setShopName(userInfoVo.getShopName());
				cartDto.setTotal(1);
				listCartDto.add(cartDto);
			}
			subDto.setListCartDto(listCartDto);
			subDto.setShopCode(userInfoVo.getShopCode());
			JstUtils.setBaseDTO(subDto);
			RpcResponse<SubJstShopShoppingCartDTO> rsp = wxBsMerchantRemote.submitJspShopShoppingCart(subDto);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxAddProductToCartNoVerify return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			SubJstShopShoppingCartDTO dtoResult = rsp.getResult();
			if(!StringUtils.isEmpty(dtoResult))
			{
				countCart = dtoResult.getTotalCount();
			}
		}
		else if(userInfoVo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
		{
			SubBsMerchantShoppingCartDTO subDto = new SubBsMerchantShoppingCartDTO();
			List<BsMerchantShoppingCartDTO> listCartDto = new ArrayList<BsMerchantShoppingCartDTO>();
			//展开购物车
			for(MaterialVO item:product.getListMaterialVo())
			{
				BsMerchantShoppingCartDTO cartDto = new BsMerchantShoppingCartDTO();
				cartDto.setCreatedBy(userInfoVo.getMerchantCode());
				cartDto.setUpdatedBy(userInfoVo.getMerchantCode());
				cartDto.setCreatedDate(JstUtils.getNowDate());
				cartDto.setUpdatedDate(JstUtils.getNowDate());
				cartDto.setDeletedFlag("N");
				cartDto.setMaterialCode(item.getMaterialCode());
				cartDto.setMaterialName(item.getMaterialName());
				cartDto.setMerchantCode(userInfoVo.getMerchantCode());
				cartDto.setMerchantName(userInfoVo.getMerchantName());
				cartDto.setPackageOne(product.getPackageOne());
				cartDto.setPrice(product.getPrice());
				cartDto.setProductCode(product.getProductCode());
				cartDto.setProductName(product.getProductName());
				cartDto.setServiceTime(product.getServiceTime());
				cartDto.setTotal(1);
				listCartDto.add(cartDto);
			}
			subDto.setListCartDto(listCartDto);
			subDto.setMerchantCode(userInfoVo.getMerchantCode());
			JstUtils.setBaseDTO(subDto);
			RpcResponse<SubBsMerchantShoppingCartDTO> rsp = wxBsMerchantRemote.submitBsMerchantShoppingCart(subDto);
			JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
			if (!StringUtils.isEmpty(errCodeEnum)) {
				logger.info("WxProductController::wxAddProductToCartNoVerify return"
						+ errCodeEnum.getDescrible());
				return ResultEntity.error(errCodeEnum.getCode(),
						errCodeEnum.getDescrible());
			}
			SubBsMerchantShoppingCartDTO dtoResult = rsp.getResult();
			if(!StringUtils.isEmpty(dtoResult))
			{
				countCart = dtoResult.getTotalCount();
			}
		}
		logger.info("WxProductController::wxAddProductToCartNoVerify end countCart:{}", countCart);
		return ResultEntity.success(countCart);
	}
	
	
	private ProductDTO getJstProduct(String productCode)
	{
		ProductDTO result = null;
		ProductDTO condition = new ProductDTO();
		condition.setProductCode(productCode);
		JstUtils.setBaseDTO(condition);
		RpcResponse<ProductDTO> rsp = wxBsMerchantRemote.getProductBaseInfoByProductCode(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if(StringUtils.isEmpty(errCodeEnum))
		{
			result = rsp.getResult();
		}
		return result;
	}
	
	
	private JstShopDTO getJstShop(String merchantCode)
	{
		JstShopDTO result = null;
		JstShopDTO condition = new JstShopDTO();
		condition.setShopMerchantCode(merchantCode);
		JstUtils.setBaseDTO(condition);
		RpcResponse<JstShopDTO> rsp = wxBsMerchantRemote.getJspShopByMerchantCode(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if(StringUtils.isEmpty(errCodeEnum))
		{
			result = rsp.getResult();
		}
		return result;
	}
	
	private JstShopAgentRelationDTO getJspShopAgentRelation(String shopCode, String merchantCode)
	{
		JstShopAgentRelationDTO result = null;
		JstShopAgentRelationDTO condition = new JstShopAgentRelationDTO();
		condition.setShopCode(shopCode);
		condition.setAgentMerchantCode(merchantCode);
		JstUtils.setBaseDTO(condition);
		RpcResponse<JstShopAgentRelationDTO> rsp = wxBsMerchantRemote.getJspShopAgentRelation(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if(StringUtils.isEmpty(errCodeEnum))
		{
			result = rsp.getResult();
		}
		return result;
	}
	
	
	
}
