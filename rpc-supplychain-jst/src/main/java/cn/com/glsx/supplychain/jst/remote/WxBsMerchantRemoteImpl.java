package cn.com.glsx.supplychain.jst.remote;



import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.framework.core.util.RpcAssert;
import cn.com.glsx.supplychain.jst.convert.JstShopRpcConvert;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.MerchantRoleEnum;
import cn.com.glsx.supplychain.jst.model.BsDealerUserInfo;
import cn.com.glsx.supplychain.jst.model.JstShop;
import cn.com.glsx.supplychain.jst.model.StatementSell;
import cn.com.glsx.supplychain.jst.service.*;
import cn.com.glsx.supplychain.jst.util.JstUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Title: WxBsMerchantRemoteImpl.java
 * @Description:针对微信rpc接口实现
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Component("WxBsMerchantRemote")
@Service(version = "1.0.0", protocol = "dubbo", timeout = 10000)
public class WxBsMerchantRemoteImpl implements WxBsMerchantRemote{

	private static final Logger LOGGER = LoggerFactory.getLogger(WxBsMerchantRemoteImpl.class);
	@Autowired
	private JstShopService jstShopService;
	@Autowired
	private BsDealerUserInfoService bsDealerUserInfoService;
	@Autowired
	private RequestVerifyService verifyService;
	@Autowired
	private BsAddressService bsAddressService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private JstUserOpenIdService userOpenIdService;

	@Override
	public RpcResponse<UserInfoDTO> getRoleByMerchantCode(UserInfoDTO record) {

		MerchantRoleEnum merchantRoleEnum;
		RpcResponse<UserInfoDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::getRoleByMerchantCode record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			JstShop jstShop = jstShopService.getJspShopByShopMerchantCode(record.getMerchantCode());
			BsDealerUserInfo bsDealerUserInfo = bsDealerUserInfoService.getBsDealerUserInfoByMerchantCode(record.getMerchantCode());
			if(StringUtils.isEmpty(jstShop))
			{
				if(StringUtils.isEmpty(bsDealerUserInfo))
				{
					return RpcResponse.error(JstErrorCodeEnum.ERRCODE_ACCOUNT_NOT_EXIST);
				}
				record.setMerchantName(bsDealerUserInfo.getMerchantName());
				merchantRoleEnum = MerchantRoleEnum.ROLE_AGENT;
			}
			else
			{
				if(!StringUtils.isEmpty(bsDealerUserInfo))
				{
					merchantRoleEnum = MerchantRoleEnum.ROLE_ALL;
					record.setMerchantName(bsDealerUserInfo.getMerchantName());
				}
				else
				{
					record.setShopCode(jstShop.getShopCode());
					record.setShopName(jstShop.getShopName());
					merchantRoleEnum = MerchantRoleEnum.ROLE_SHOP;
				}
			}
			record.setRole(merchantRoleEnum.getValue());
			result = RpcResponse.success(record);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getRoleByMerchantCode end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsDealerUserInfoDTO> getDealerUserInfoByMerchantCode(
			BsDealerUserInfoDTO record) {
		BsDealerUserInfoDTO returnObject = null;
		RpcResponse<BsDealerUserInfoDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::getDealerUserInfoByMerchantCode record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			BsDealerUserInfo bsDealerUserInfo = bsDealerUserInfoService.getBsDealerUserInfoByMerchantCode(record.getMerchantCode());
			if(!StringUtils.isEmpty(bsDealerUserInfo))
			{
				returnObject = new BsDealerUserInfoDTO();
				JstUtils.copyObject(returnObject, bsDealerUserInfo);
			}
			result = RpcResponse.success(returnObject);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getDealerUserInfoByMerchantCode end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<List<BsAddressDTO>> listBsAddressByMerchantCode(BsAddressDTO record) {

		RpcResponse<List<BsAddressDTO>> result;
		LOGGER.info("WxBsMerchantRemoteImpl::listBsAddressByMerchantCode record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			List<BsAddressDTO> listDTO = bsAddressService.listBsAddressByMerchantCode(record.getMerchantCode());
			result = RpcResponse.success(listDTO);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listBsAddressByMerchantCode end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsAddressDTO> saveBsAddress(BsAddressDTO record) {
		RpcResponse<BsAddressDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::saveBsAddress record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode must not be null");
			RpcAssert.assertNotNull(record.getAddress(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddress must not be null");
			RpcAssert.assertNotNull(record.getName(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getName must not be null");
			RpcAssert.assertNotNull(record.getMobile(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMobile must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			BsAddressDTO dto = bsAddressService.addBsAddress(record);
			result = RpcResponse.success(dto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::saveBsAddress end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> removeBsAddress(BsAddressDTO record) {
		RpcResponse<Integer> result;
		LOGGER.info("WxBsMerchantRemoteImpl::removeBsAddress record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getId() must not be null");
		//	RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(bsAddressService.removeBsAddress(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::removeBsAddress end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsAddressDTO> getBsAddressById(BsAddressDTO record) {
		RpcResponse<BsAddressDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::getBsAddressById record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getId() must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(bsAddressService.getBsAddressById(record,"N"));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getBsAddressById end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<BsAddressDTO> updateBsAddressById(BsAddressDTO record) {
		RpcResponse<BsAddressDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::updateBsAddressById record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getId() must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(bsAddressService.updateBsAddressById(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::updateBsAddressById end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<List<JstShopAgentRelationDTO>> listShopAgentMerchant(
			JstShopDTO record) {

		RpcResponse<List<JstShopAgentRelationDTO>> result;
		LOGGER.info("WxBsMerchantRemoteImpl::listShopAgentMerchant record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getShopMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			List<JstShopAgentRelationDTO> dtoList = jstShopService.listShopAgentMerchant(record);
			result = RpcResponse.success(dtoList);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listShopAgentMerchant end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<List<JstShopDTO>> listJstShopByAgentMerchant(
			BsDealerUserInfoDTO record) {

		RpcResponse<List<JstShopDTO>> result;
		LOGGER.info("WxBsMerchantRemoteImpl::listJstShopByAgentMerchant record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			List<JstShopDTO> listDto = jstShopService.listJstShopByAgentMerchant(record.getMerchantCode());
			result = RpcResponse.success(listDto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listJstShopByAgentMerchant end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisShopDTO> pageJstShopByAgentMerchant(DisShopDTO record) {
		LOGGER.info("WxBsMerchantRemoteImpl::pageJstShopByAgentMerchant record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAgentMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			return RpcResponse.success(jstShopService.pageJstShopByAgentMerchant(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<DisDealerUserInfoDTO> listDealerUserInfo(
			DisDealerUserInfoDTO record) {

		RpcResponse<DisDealerUserInfoDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::listDealerUserInfo record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantCode must not be null");
			RpcAssert.assertIsTrue(record.getListMerchantCode().size()>0, JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListMerchantCode.getsize>0 must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			DisDealerUserInfoDTO dto = bsDealerUserInfoService.listBsDealerUserInfo(record);
			result = RpcResponse.success(dto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listDealerUserInfo end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisProductDTO> listProduct(DisProductDTO record) {

		RpcResponse<DisProductDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::listProduct record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListDealerUserInfoDTO(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDealerUserInfoDTO must not be null");
			RpcAssert.assertIsTrue(record.getListDealerUserInfoDTO().size()>0, JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDealerUserInfoDTO.getsize>0 must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(productService.listDisplayProduct(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listProduct end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisProductDTO> listProductBase(DisProductDTO record) {
		RpcResponse<DisProductDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::listProductBase record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListDealerUserInfoDTO(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDealerUserInfoDTO must not be null");
			RpcAssert.assertIsTrue(record.getListDealerUserInfoDTO().size()>0, JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDealerUserInfoDTO.getsize>0 must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(productService.listDisplayProductBase(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listProductBase end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<ProductDTO> listProductMaterial(ProductDTO record) {

		RpcResponse<ProductDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::listProductMaterial record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getProductCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(productService.listProductMaterial(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listProductMaterial end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<JstShopDTO> getJspShopByMerchantCode(JstShopDTO record) {

		RpcResponse<JstShopDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::getJspShopByMerchantCode record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getShopMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			JstShopDTO jstShopDto = JstShopRpcConvert.convertBean(jstShopService.getJspShopByShopMerchantCode(record.getShopMerchantCode()));
			result = RpcResponse.success(jstShopDto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getJspShopByMerchantCode end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<JstShopDTO> getJspShopByShopCode(JstShopDTO record) {
		LOGGER.info("WxBsMerchantRemoteImpl::getJspShopByShopCode record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode() must not be null");
			return RpcResponse.success(JstShopRpcConvert.convertBean(jstShopService.getJspShopByShopcode(record.getShopCode())));
		}catch (RpcServiceException e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
	}

	@Override
	public RpcResponse<JstShopAgentRelationDTO> getJspShopAgentRelation(
			JstShopAgentRelationDTO record) {
		RpcResponse<JstShopAgentRelationDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::getJspShopAgentRelation record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAgentMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAgentMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			JstShopAgentRelationDTO relationDto = jstShopService.getShopAgentMerchant(record);
			result = RpcResponse.success(relationDto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getJspShopAgentRelation end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<ProductDTO> getProductBaseInfoByProductCode(
			ProductDTO record) {
		RpcResponse<ProductDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::getProductBaseInfoByProductCode record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getProductCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAgentMerchantCode() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			ProductDTO productDto = productService.getProductBaseInfo(record.getProductCode());
			result = RpcResponse.success(productDto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getProductBaseInfoByProductCode end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<SubJstShopShoppingCartDTO> submitJspShopShoppingCart(
			SubJstShopShoppingCartDTO record) {
		RpcResponse<SubJstShopShoppingCartDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::submitJspShopShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListCartDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListCartDto must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			cartService.submitShopShoppingCart(record.getListCartDto());
			Integer cartCount = cartService.countShopShoppingCart(record.getShopCode());
			record.setTotalCount(cartCount);
			result = RpcResponse.success(record);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::submitJspShopShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<SubBsMerchantShoppingCartDTO> submitBsMerchantShoppingCart(
			SubBsMerchantShoppingCartDTO record) {
		RpcResponse<SubBsMerchantShoppingCartDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::submitBsMerchantShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListCartDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListCartDto must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			cartService.submitMerchantShoppingCart(record.getListCartDto());
			Integer cartCount = cartService.countMerchantShoppingCart(record.getMerchantCode());
			record.setTotalCount(cartCount);
			result = RpcResponse.success(record);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::submitBsMerchantShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> countShopShoppingCart(JstShopDTO record) {

		RpcResponse<Integer> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::countShopShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			Integer count = cartService.countShopShoppingCart(record.getShopCode());
			result = RpcResponse.success(count);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::countShopShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> countMerchantShoppingCart(
			BsDealerUserInfoDTO record) {

		RpcResponse<Integer> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::countMerchantShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			Integer count = cartService.countMerchantShoppingCart(record.getMerchantCode());
			result = RpcResponse.success(count);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::countMerchantShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisJstShopShoppingCartDTO> pageShopShoppingCart(
			DisJstShopShoppingCartDTO record) {
		RpcResponse<DisJstShopShoppingCartDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::pageShopShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertNotNull(record.getPageNo(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageNo must not be null");
			RpcAssert.assertNotNull(record.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageSize must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			DisJstShopShoppingCartDTO dto = cartService.pageShopShoppingCart(record);
			result = RpcResponse.success(dto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::pageShopShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisMerchantShoppingCartDTO> pageMerchantShoppingCart(
			DisMerchantShoppingCartDTO record) {
		RpcResponse<DisMerchantShoppingCartDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::pageMerchantShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getPageNo(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageNo must not be null");
			RpcAssert.assertNotNull(record.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageSize must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			DisMerchantShoppingCartDTO dto = cartService.pageMerchantShoppingCart(record);
			result = RpcResponse.success(dto);
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::pageMerchantShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> updateShopShoppingCart(
			DisJstShopShoppingCartDTO record) {
		RpcResponse<Integer> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::updateShopShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListCartDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListCartDto must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success( cartService.updateShopShoppingCart(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::updateShopShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> updateMerchantShoppingCart(
			DisMerchantShoppingCartDTO record) {

		RpcResponse<Integer> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::updateMerchantShoppingCart record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getListCartDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListCartDto must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success( cartService.updateMerchantShoppingCart(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::updateMerchantShoppingCart end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> submitShopOrder(SubShopOrderDTO record) throws RpcServiceException {
		RpcResponse<Integer> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrder record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getBsAddressDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getBsAddressDto must not be null");
			RpcAssert.assertNotNull(record.getListCartDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListCartDto must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.submitShopOrder(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrder end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> submitMerchantOrder(SubMerchantOrderDTO record) {

		RpcResponse<Integer> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::submitMerchantOrder record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getBsAddressDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getBsAddressDto must not be null");
			RpcAssert.assertNotNull(record.getListCartDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListCartDto must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.submitMerchantOrder(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::submitMerchantOrder end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisOrderDTO> pageMerchantOrder(DisOrderDTO record) {
		RpcResponse<DisOrderDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::pageMerchantOrder record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getMerchantCode must not be null");
			RpcAssert.assertNotNull(record.getPageNo(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageNo must not be null");
			RpcAssert.assertNotNull(record.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageSize must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.pageMerchantOrder(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::pageMerchantOrder end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisOrderDTO> pageShopOrder(DisOrderDTO record) {

		RpcResponse<DisOrderDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::pageShopOrder record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getPageNo(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageNo must not be null");
			RpcAssert.assertNotNull(record.getPageSize(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getPageSize must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.pageShopOrder(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::pageShopOrder end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<OrderDTO> getShopOrderDetail(OrderDTO record) {

		RpcResponse<OrderDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::getShopOrderDetail record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOrderCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.getShopOrderDetail(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getShopOrderDetail end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<DisShopOrderDetailDTO> listShopOrderDetail(
			DisShopOrderDetailDTO record) {

		RpcResponse<DisShopOrderDetailDTO> result = null;
		LOGGER.info("WxBsMerchantRemoteImpl::listShopOrderDetail record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOrderCode must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.listShopOrderDetail(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::listShopOrderDetail end result:{}",result);
		return result;
	}



	@Override
	public RpcResponse<Integer> submitShopOrderDetail(SubOrderDetailDTO record) {

		RpcResponse<Integer> result;
		LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrderDetail record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOrderCode must not be null");
			RpcAssert.assertNotNull(record.getAddressDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto must not be null");
			RpcAssert.assertNotNull(record.getAddressDto().getId(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto().getId() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto must not be null");
			RpcAssert.assertNotNull(record.getLogisticsDto().getCompany(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto().getCompany() must not be null");
			RpcAssert.assertNotNull(record.getLogisticsDto().getOrderNumber(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto().getOrderNumber() must not be null");
			RpcAssert.assertNotNull(record.getListDetailDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto() must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.submitShopOrderDetail(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrderDetail end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> submitShopOrderDetailNoOrder(
			SubOrderDetailDTO record) {
		RpcResponse<Integer> result;
		LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrderDetailNoOrder record::{}",record);
		try{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
			RpcAssert.assertNotNull(record.getAgentMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAgentMerchantCode must not be null");
			RpcAssert.assertNotNull(record.getAddressDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto must not be null");
			RpcAssert.assertNotNull(record.getAddressDto().getName(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto.getName must not be null");
			RpcAssert.assertNotNull(record.getAddressDto().getMobile(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto.getMobile must not be null");
			RpcAssert.assertNotNull(record.getAddressDto().getAddress(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto.getAddress must not be null");
			//RpcAssert.assertNotNull(record.getLogisticsDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto must not be null");
			//RpcAssert.assertNotNull(record.getLogisticsDto().getCompany(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto().getCompany() must not be null");
			//RpcAssert.assertNotNull(record.getLogisticsDto().getOrderNumber(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto().getOrderNumber() must not be null");
			//RpcAssert.assertNotNull(record.getListDetailDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto() must not be null");
			//RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			RpcAssert.assertNotNull(record.getShipType(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShipType() must not be null");
			RpcAssert.assertNotNull(record.getScanType(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getScanType() must not be null");
			result = RpcResponse.success(orderService.submitShopOrderDetailNoOrder(record));
		}
		catch(RpcServiceException e){
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrderDetailNoOrder end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> noScanCodeWxSubShopOrderDetailNoOrder(SubOrderDetailDTO record) {
        RpcResponse<Integer> result;
        LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrderDetailNoOrder record::{}",record);
        try{
            RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
            RpcAssert.assertNotNull(record.getShopCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShopCode must not be null");
            RpcAssert.assertNotNull(record.getAgentMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAgentMerchantCode must not be null");
            RpcAssert.assertNotNull(record.getAddressDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto must not be null");
            RpcAssert.assertNotNull(record.getAddressDto().getName(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto.getName must not be null");
            RpcAssert.assertNotNull(record.getAddressDto().getMobile(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto.getMobile must not be null");
            RpcAssert.assertNotNull(record.getAddressDto().getAddress(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAddressDto.getAddress must not be null");
           // RpcAssert.assertNotNull(record.getLogisticsDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto must not be null");
           //   RpcAssert.assertNotNull(record.getLogisticsDto().getCompany(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto().getCompany() must not be null");
           //   RpcAssert.assertNotNull(record.getLogisticsDto().getOrderNumber(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getLogisticsDto().getOrderNumber() must not be null");
			RpcAssert.assertNotNull(record.getShipType(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getShipType() must not be null");
			RpcAssert.assertNotNull(record.getScanType(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getScanType() must not be null");
            RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
            result = RpcResponse.success(orderService.noScanCodeWxSubShopOrderDetailNoOrder(record));
        }
        catch(RpcServiceException e){
            LOGGER.error(e.getMessage(), e);
            return RpcResponse.error(e.getError(), e.getMessage());
        }
        LOGGER.info("WxBsMerchantRemoteImpl::submitShopOrderDetailNoOrder end result:{}",result);
        return result;
	}

	/**
	 * @author: luoqiang
	 * @description: 获取无订单发货的不扫码的物料信息
	 * @date: 2020/8/4 11:35
	 * @param merchantCode
	 * @return: cn.com.glsx.framework.core.beans.rpc.RpcResponse<java.util.List<cn.com.glsx.supplychain.jst.model.StatementSell>>
	 */
	@Override
	public RpcResponse<List<StatementSell>> listMaterialNoOrder(String merchantCode) {
		RpcAssert.assertNotNull(merchantCode,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"merchantCode must not be null");
		RpcResponse<List<StatementSell>> result = RpcResponse.success(orderService.listMaterialNoOrder(merchantCode));
		return result;
	}


	@Override
	public RpcResponse<Integer> checkShopOrderDetail(SubOrderDetailDTO record) {

		RpcResponse<Integer> result;
		LOGGER.info("WxBsMerchantRemoteImpl::checkShopOrderDetail record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOrderCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOrderCode must not be null");
			RpcAssert.assertNotNull(record.getListDetailDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.checkShopOrderDetail(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::checkShopOrderDetail end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<Integer> checkShopOrderDetailNoOrder(
			SubOrderDetailDTO record) {
		RpcResponse<Integer> result;
		LOGGER.info("WxBsMerchantRemoteImpl::checkShopOrderDetailNoOrder record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAgentMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAgentMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getListDetailDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.checkShopOrderDetailNoOrder(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.info(e.getMessage());
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::checkShopOrderDetail end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<List<JstShopShoppingCartDTO>> getShopOrderDetailNoOrder(
			SubOrderDetailDTO record) {
		RpcResponse<List<JstShopShoppingCartDTO>> result;
		LOGGER.info("WxBsMerchantRemoteImpl::getShopOrderDetailNoOrder record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getAgentMerchantCode(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getAgentMerchantCode() must not be null");
			RpcAssert.assertNotNull(record.getListDetailDto(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getListDetailDto must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(orderService.getShopOrderDetailNoOrder(record));
		}
		catch(Exception e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e);
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getShopOrderDetailNoOrder end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<JstUserOpenIdDTO> getJstUserOpenId(
			JstUserOpenIdDTO record) {

		RpcResponse<JstUserOpenIdDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::getJstUserOpenId record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOpenid(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOpenid must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(userOpenIdService.getJstUserOpenId(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::getJstUserOpenId end result:{}",result);
		return result;
	}

	@Override
	public RpcResponse<JstUserOpenIdDTO> saveJstUserOpenId(
			JstUserOpenIdDTO record) {

		RpcResponse<JstUserOpenIdDTO> result;
		LOGGER.info("WxBsMerchantRemoteImpl::saveJstUserOpenId record::{}",record);
		try
		{
			RpcAssert.assertNotNull(record,JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record must not be null");
			RpcAssert.assertNotNull(record.getOpenid(),JstErrorCodeEnum.ERRCODE_INVALID_PARAM,"record.getOpenid must not be null");
			RpcAssert.assertIsTrue(verifyService.verifyRequest(record), JstErrorCodeEnum.ERRCODE_RPC_FORBIDDEN,"rpc frobidden");
			result = RpcResponse.success(userOpenIdService.saveJstUserOpenId(record));
		}
		catch (RpcServiceException e)
		{
			LOGGER.error(e.getMessage(), e);
			return RpcResponse.error(e.getError(), e.getMessage());
		}
		LOGGER.info("WxBsMerchantRemoteImpl::saveJstUserOpenId end result:{}",result);
		return result;
	}
}
