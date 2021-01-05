package cn.com.glsx.supplychain.jst.controller;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jst.convert.AttribInfoHttpConvert;
import cn.com.glsx.supplychain.jst.convert.BsAddressHttpConvert;
import cn.com.glsx.supplychain.jst.dto.BsAddressDTO;
import cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO;
import cn.com.glsx.supplychain.jst.dto.gh.*;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.remote.MotorcycleRemote;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.utils.JstUtils;
import cn.com.glsx.supplychain.jst.vo.AddressVO;
import cn.com.glsx.supplychain.jst.vo.gh.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: WxCartController.java
 * @Description: 广汇18家 基于车型配置下单
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/gh",tags="广汇18家 基于车型配置下单")
@RestController
@RequestMapping("gh")
public class GhMerchantOrderController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MotorcycleRemote motorcycleRemote;
	@Autowired
	private WxBsMerchantRemote wxBsMerchantRemote;
	
	/**
	 * 获取配置分类列表
	 * 主要返回 车载导航 爱车保镖 电子辅助 配件 的大类分类
	 */
	@ApiOperation(value="获取配置分类列表",notes="获取配置分类列表")
	@ApiImplicitParam(name="attribInfo", value="获取配置分类列表json格式",dataType="AttribInfoDTO",required=true)
	@PostMapping("listAttribInfo")
	public ResultEntity<List<AttribInfoDTO>> listAttribInfoDTO(@RequestBody AttribInfoDTO attribInfo){
		logger.info("GhMerchantOrderController::listAttribInfoDTO start attribInfo:{}",attribInfo);
		RpcResponse<List<AttribInfoDTO>> rsp = motorcycleRemote.listAttribInfo(attribInfo);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::listAttribInfoDTO return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::listAttribInfoDTO end rsp.getResult():{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	/**
	 * 获取商户下车系车型列表
	 */
	@PostMapping("pageAudiMotorcycle")
	public ResultEntity<DisAudiMotorcycleVO> pageAudiMotorcycle(@RequestBody DisAudiMotorcycleVO disAudiMotorcycle){
		logger.info("GhMerchantOrderController::pageAudiMotorcycle start disAudiMotorcycle:{}",disAudiMotorcycle);
		DisAudiMotorcycleVO result = new DisAudiMotorcycleVO();
		RpcPagination<AudiMotorcycleDTO> pagination = new RpcPagination<AudiMotorcycleDTO>();
		pagination.setPageNum(disAudiMotorcycle.getPageNo());
		pagination.setPageSize(disAudiMotorcycle.getPageSize());
		AudiMotorcycleDTO dtoCondition = new AudiMotorcycleDTO();
		dtoCondition.setMerchantCode(disAudiMotorcycle.getMerchantCode());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<AudiMotorcycleDTO>> rsp = motorcycleRemote.pageAudiMotorcycle(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::pageAudiMotorcycle return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<AudiMotorcycleDTO> rpcResult = rsp.getResult();
		if(null != rpcResult){
			result.setMerchantCode(disAudiMotorcycle.getMerchantCode());
			result.setListAudiMotorcycle(rpcResult.getList());
		}
		logger.info("GhMerchantOrderController::pageAudiMotorcycle end result:{}",result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 获取产品列表
	 */
	@PostMapping("pageProduct")
	public ResultEntity<DisProductConfigVO> pageProduct(@RequestBody DisProductConfigVO disProductConfig){
		logger.info("GhMerchantOrderController::pageProduct start disProductConfig:{}",disProductConfig);
		DisProductConfigVO result = new DisProductConfigVO();
		RpcPagination<GhProductConfigDTO> pagination = new RpcPagination<GhProductConfigDTO>();
		pagination.setPageNum(disProductConfig.getPageNo());
		pagination.setPageSize(disProductConfig.getPageSize());
		GhProductConfigDTO dtoCondition = new GhProductConfigDTO();
		dtoCondition.setMerchantCode(disProductConfig.getQueryCondition().getMerchantCode());
		dtoCondition.setParentBrandId(disProductConfig.getQueryCondition().getParentBrandId());
		dtoCondition.setSubBrandId(disProductConfig.getQueryCondition().getSubBrandId());
		dtoCondition.setAudiId(disProductConfig.getQueryCondition().getAudiId());
		dtoCondition.setMotorcycle(disProductConfig.getQueryCondition().getMotorcycle());
		dtoCondition.setCategoryId(disProductConfig.getQueryCondition().getCategoryId());
		dtoCondition.setSpaProductCode(disProductConfig.getQueryCondition().getSpaProductCode());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<GhProductConfigDTO>> rsp = motorcycleRemote.pageGhProductConfig(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::pageProduct return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<GhProductConfigDTO> rpcResult = rsp.getResult();
		if(null != rpcResult){
			result.setPageNo(rpcResult.getPageNum());
			result.setPageSize(rpcResult.getPageSize());
			result.setListGhProductConfig(rpcResult.getList());
			result.setQueryCondition(disProductConfig.getQueryCondition());
		}
		logger.info("GhMerchantOrderController::pageProduct end result:{}",result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 根据spacode获取产品列表
	 */
	@PostMapping("pageProductBySpaCode")
	public ResultEntity<DisProductConfigVO> pageProductBySpaCode(@RequestBody DisProductConfigVO disProductConfig){
		logger.info("GhMerchantOrderController::pageProductBySpaCode start disProductConfig:{}",disProductConfig);
		DisProductConfigVO result = new DisProductConfigVO();
		RpcPagination<GhProductConfigDTO> pagination = new RpcPagination<GhProductConfigDTO>();
		pagination.setPageNum(disProductConfig.getPageNo());
		pagination.setPageSize(disProductConfig.getPageSize());
		GhProductConfigDTO dtoCondition = new GhProductConfigDTO();
		dtoCondition.setMerchantCode(disProductConfig.getQueryCondition().getMerchantCode());
		dtoCondition.setParentBrandId(disProductConfig.getQueryCondition().getParentBrandId());
		dtoCondition.setSubBrandId(disProductConfig.getQueryCondition().getSubBrandId());
		dtoCondition.setMotorcycle(disProductConfig.getQueryCondition().getMotorcycle());
		dtoCondition.setCategoryId(disProductConfig.getQueryCondition().getCategoryId());
		dtoCondition.setSpaProductCode(disProductConfig.getQueryCondition().getSpaProductCode());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<GhProductConfigDTO>> rsp = motorcycleRemote.pageGhProductConfig(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::pageProductBySpaCode return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<GhProductConfigDTO> rpcResult = rsp.getResult();
		if(null != rpcResult){
			result.setPageNo(1);
			result.setPageSize(1);
			if(null != rpcResult.getList() && !rpcResult.getList().isEmpty()){
				List<GhProductConfigDTO> listResult = new ArrayList<GhProductConfigDTO>();
				listResult.add(rpcResult.getList().get(0));
				result.setListGhProductConfig(listResult);
			}else{
				result.setListGhProductConfig(null);
			}
			result.setQueryCondition(disProductConfig.getQueryCondition());
		}
		logger.info("GhMerchantOrderController::pageProductBySpaCode end result:{}",result);
		return ResultEntity.success(result);	
	}
	
	/**
	 * 获取车型配置中年款列表
	 */
	@PostMapping("listYearMode")
	public ResultEntity<List<AttribInfoVO>> listYearMode(@RequestBody ModelYearConditionVO queryCondition){
		logger.info("GhMerchantOrderController::listYearMode start queryCondition:{}",queryCondition);
		List<AttribInfoVO> result = null;
		GhProductConfigDTO dtoCondition = new GhProductConfigDTO();
		dtoCondition.setMerchantCode(queryCondition.getQueryCondition().getMerchantCode());
		dtoCondition.setParentBrandId(queryCondition.getQueryCondition().getParentBrandId());
		dtoCondition.setSubBrandId(queryCondition.getQueryCondition().getSubBrandId());
		dtoCondition.setMotorcycle(queryCondition.getQueryCondition().getMotorcycle());
		dtoCondition.setCategoryId(queryCondition.getQueryCondition().getCategoryId());
		dtoCondition.setAudiId(queryCondition.getQueryCondition().getAudiId());
		dtoCondition.setSpaProductCode(queryCondition.getQueryCondition().getSpaProductCode());
		dtoCondition.setGlsxProductCode(queryCondition.getGlsxProductCode());
		RpcResponse<List<AttribInfoDTO>> rsp = motorcycleRemote.listGhProductConfigModeYears(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::listYearMode return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		result = AttribInfoHttpConvert.convertList(rsp.getResult());
		logger.info("GhMerchantOrderController::listYearMode end result:{}",result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 获取车型配置中车系列表
	 */
	@PostMapping("listAudiInfo")
	public ResultEntity<List<AttribInfoVO>> listAudiInfo(@RequestBody ModelYearConditionVO queryCondition){
		logger.info("GhMerchantOrderController::listAudiInfo start queryCondition:{}",queryCondition);
		List<AttribInfoVO> result = null;
		GhProductConfigDTO dtoCondition = new GhProductConfigDTO();
		dtoCondition.setMerchantCode(queryCondition.getQueryCondition().getMerchantCode());
		dtoCondition.setParentBrandId(queryCondition.getQueryCondition().getParentBrandId());
		dtoCondition.setSubBrandId(queryCondition.getQueryCondition().getSubBrandId());
		dtoCondition.setMotorcycle(queryCondition.getQueryCondition().getMotorcycle());
		dtoCondition.setCategoryId(queryCondition.getQueryCondition().getCategoryId());
		dtoCondition.setSpaProductCode(queryCondition.getQueryCondition().getSpaProductCode());
		dtoCondition.setGlsxProductCode(queryCondition.getGlsxProductCode());
		dtoCondition.setAudiId(queryCondition.getQueryCondition().getAudiId());
		RpcResponse<List<AttribInfoDTO>> rsp = motorcycleRemote.listGhProductConfigAudis(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::listAudiInfo return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		result = AttribInfoHttpConvert.convertList(rsp.getResult());
		logger.info("GhMerchantOrderController::listAudiInfo end result:{}",result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 获取车型配置中车型列表
	 */
	@PostMapping("pageMotorcycle")
	public ResultEntity<DisProductConfigVO> pageMotorcycle(@RequestBody MotocycleConditionVO motocycleCondition){
		logger.info("GhMerchantOrderController::pageMotorcycle start motocycleCondition:{}",motocycleCondition);
		DisProductConfigVO result = new DisProductConfigVO();
		RpcPagination<GhProductConfigDTO> pagination = new RpcPagination<GhProductConfigDTO>();
		pagination.setPageNum(motocycleCondition.getPageNo());
		pagination.setPageSize(motocycleCondition.getPageSize());
		GhProductConfigDTO dtoCondition = new GhProductConfigDTO();
		dtoCondition.setMerchantCode(motocycleCondition.getQueryCondition().getMerchantCode());
		dtoCondition.setParentBrandId(motocycleCondition.getQueryCondition().getParentBrandId());
		dtoCondition.setSubBrandId(motocycleCondition.getQueryCondition().getSubBrandId());
		dtoCondition.setMotorcycle(motocycleCondition.getQueryCondition().getMotorcycle());
		dtoCondition.setCategoryId(motocycleCondition.getQueryCondition().getCategoryId());
		dtoCondition.setSpaProductCode(motocycleCondition.getQueryCondition().getSpaProductCode());
		dtoCondition.setGlsxProductCode(motocycleCondition.getGlsxProductCode());
		dtoCondition.setAudiId(motocycleCondition.getAudiId());
		dtoCondition.setModelYearId(motocycleCondition.getModelYearId());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<GhProductConfigDTO>> rsp = motorcycleRemote.pageGhProductConfigMotorcycle(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::pageMotorcycle return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<GhProductConfigDTO> rpcResult = rsp.getResult();
		if(null != rpcResult){
			result.setPageNo(rpcResult.getPageNum());
			result.setPageSize(rpcResult.getPageSize());
			result.setListGhProductConfig(rpcResult.getList());
		}
		logger.info("GhMerchantOrderController::pageMotorcycle end result:{}",result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 获取非车载导航类别的选配置列表
	 */
	@PostMapping("getGhProductConfigOther")
	public ResultEntity<GhProductConfigDTO> getGhProductConfigOther(@RequestBody GhProductConfigDTO productConfig){
		logger.info("GhMerchantOrderController::getGhProductConfigOther start motocycleCondition:{}",productConfig);
		RpcResponse<List<GhProductConfigOtherDTO>> rsp = motorcycleRemote.listGhProductConfigOther(productConfig);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::pageMotorcycle return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		productConfig.setListConfigOther(rsp.getResult());
		logger.info("GhMerchantOrderController::pageMotorcycle end productConfig:{}",productConfig);
		return ResultEntity.success(productConfig);
	}
	
	/**
	 * 批量添加产品到购物车
	 */
	@PostMapping("batchAddGhProductConfigCart")
	public ResultEntity<Integer> batchAddGhProductConfigCart(@RequestBody BatchSubProductConfigCartVO subProductConfig){
		
		logger.info("GhMerchantOrderController::batchAddGhProductConfigCart start subProductConfig:{}",subProductConfig);
		List<GhShoppingCartDTO> listDtoCondition = new ArrayList<>();
		if(StringUtils.isEmpty(subProductConfig.getMerchantCode()) || null == subProductConfig.getListShopCarts() || subProductConfig.getListShopCarts().isEmpty()){
			logger.info("WxAddressController::batchAddGhProductConfigCart param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(),null);
		}
		GhShoppingCartDTO dto = null;
		for(ProductShoppingCartVO vo:subProductConfig.getListShopCarts()){
			dto = new GhShoppingCartDTO();
			dto.setProductConfigCode(vo.getProductConfigCode());
			dto.setTotal(vo.getTotal());
			dto.setRemark(vo.getRemark());
			dto.setListCartConfig(vo.getListCartConfig());
			dto.setMerchantCode(subProductConfig.getMerchantCode());
			listDtoCondition.add(dto);
		}
		RpcResponse<Integer> rsp = motorcycleRemote.batchAddGhProductConfigCart(listDtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::batchAddGhProductConfigCart return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::batchAddGhProductConfigCart end rsp.getResult:{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	/**
	 * 添加产品到购物车
	 */
	@PostMapping("addGhProductConfigCart")
	public ResultEntity<Integer> addGhProductConfigCart(@RequestBody SubProductConfigCartVO subProductConfig){
		logger.info("GhMerchantOrderController::addGhProductConfigCart start subProductConfig:{}",subProductConfig);
		GhShoppingCartDTO dtoCondition = new GhShoppingCartDTO();
		dtoCondition.setMerchantCode(subProductConfig.getMerchantCode());
		dtoCondition.setProductConfigCode(subProductConfig.getProductConfigCode());
		dtoCondition.setTotal(subProductConfig.getTotal());
		dtoCondition.setRemark(subProductConfig.getRemark());
		dtoCondition.setListCartConfig(subProductConfig.getListCartConfig());
		RpcResponse<Integer> rsp = motorcycleRemote.addGhProductConfigCart(dtoCondition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::addGhProductConfigCart return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::addGhProductConfigCart end rsp.getResult:{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	/**
	 * 删除购物车
	 */
	@PostMapping("delGhProductConfigCart")
	public ResultEntity<Integer> delGhProductConfigCart(@RequestBody CartConditionVO cartCondition){
		logger.info("GhMerchantOrderController::delGhProductConfigCart start cartCondition:{}",cartCondition);
		List<GhShoppingCartDTO> listDto = new ArrayList<>();
		if(null != cartCondition.getCartCodes()){
			for(String cartCode:cartCondition.getCartCodes()){
				GhShoppingCartDTO dto = new GhShoppingCartDTO();
				dto.setCartCode(cartCode);
				listDto.add(dto);
			}
		}
		RpcResponse<Integer> rsp = motorcycleRemote.delGhProductConfigCart(listDto);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::delGhProductConfigCart return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::delGhProductConfigCart end rsp.getResult:{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	/**
	 * 修改购物车
	 */
	@PostMapping("updateGhProductConfigCart")
	public ResultEntity<Integer> updateGhProductConfigCart(@RequestBody GhShoppingCartDTO shoppingCart){
		logger.info("GhMerchantOrderController::updateGhProductConfigCart start shoppingCart:{}",shoppingCart);
		RpcResponse<Integer> rsp = motorcycleRemote.updateGhProductConfigCart(shoppingCart);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::updateGhProductConfigCart return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::delGhProductConfigCart end rsp.getResult:{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	/**
	 * 购物车列表
	 */
	@PostMapping("listGhShoppingCart")
	public ResultEntity<DisGhShoppingCartVO> listGhShoppingCart(@RequestBody DisGhShoppingCartVO disGhShoppingCart){
		logger.info("GhMerchantOrderController::listGhShoppingCart start disGhShoppingCart:{}",disGhShoppingCart);
		RpcPagination<GhShoppingCartDTO> pagination = new RpcPagination<GhShoppingCartDTO>();
		pagination.setPageNum(disGhShoppingCart.getPageNo());
		pagination.setPageSize(disGhShoppingCart.getPageSize());
		GhShoppingCartDTO dtoCondition = new GhShoppingCartDTO();
		dtoCondition.setMerchantCode(disGhShoppingCart.getMerchantCode());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<GhShoppingCartDTO>> rsp = motorcycleRemote.pageGhShoppingCart(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::listGhShoppingCart return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		if(null != rsp.getResult()){
			disGhShoppingCart.setPageNo(rsp.getResult().getPageNum());
			disGhShoppingCart.setPageSize(rsp.getResult().getPageSize());
			disGhShoppingCart.setListGhShoppingCart(rsp.getResult().getList());
		}
		logger.info("GhMerchantOrderController::listGhShoppingCart end disGhShoppingCart:{}",disGhShoppingCart);
		return ResultEntity.success(disGhShoppingCart);
	}
	
	/**
	 * 提交订单
	 */
	@PostMapping("subGhMerchantOrder")
	public ResultEntity<Integer> subGhMerchantOrder(@RequestBody GhSubMerchantOrderDTO subGhMerchantOrder){
		logger.info("GhMerchantOrderController::subGhMerchantOrder start subGhMerchantOrder:{}",subGhMerchantOrder);
		RpcResponse<Integer> rsp = motorcycleRemote.subGhMerchantOrder(subGhMerchantOrder);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::subGhMerchantOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::subGhMerchantOrder end rsp.getResult:{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	/**
	 * 订单列表手机端
	 */
	@PostMapping("pageGhMerchantOrder")
	public ResultEntity<DisGhMerchantOrderVO> pageGhMerchantOrder(@RequestBody DisGhMerchantOrderVO disGhMerchantOrder){
		logger.info("GhMerchantOrderController::pageGhMerchantOrder start disGhMerchantOrder:{}",disGhMerchantOrder);
		DisGhMerchantOrderVO result = new DisGhMerchantOrderVO();
		RpcPagination<GhMerchantOrderDTO> pagination = new RpcPagination<GhMerchantOrderDTO>();
		pagination.setPageNum(disGhMerchantOrder.getPageNo());
		pagination.setPageSize(disGhMerchantOrder.getPageSize());
		GhMerchantOrderDTO dtoCondition = new GhMerchantOrderDTO();
		dtoCondition.setMerchantCode(disGhMerchantOrder.getMerchantCode());
		dtoCondition.setCategoryId(disGhMerchantOrder.getCategoryId());
		dtoCondition.setStatus(disGhMerchantOrder.getStatus());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<GhMerchantOrderDTO>> rsp = motorcycleRemote.pageGhMerchantOrder(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::pageGhMerchantOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<GhMerchantOrderDTO> rpcResult = rsp.getResult();
		if(null != result){
			result.setPageNo(rpcResult.getPageNum());
			result.setPageSize(rpcResult.getPageSize());
			result.setListGhMerchantOrder(rpcResult.getList());
		}
		logger.info("GhMerchantOrderController::pageGhMerchantOrder end result:{}",result);
		return ResultEntity.success(result);
	}
	
	/**
	 * 加购
	 */
	@PostMapping("addShoppingCartFromMerchantOrder")
	public ResultEntity<Integer> addShoppingCartFromMerchantOrder(@RequestBody GhMerchantOrderDTO merchantOrderDto){
		logger.info("GhMerchantOrderController::addShoppingCartFromMerchantOrder start merchantOrderDto:{}",merchantOrderDto);
		RpcResponse<Integer> rsp = motorcycleRemote.addShoppingCartFromMerchantOrder(merchantOrderDto);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::addShoppingCartFromMerchantOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("GhMerchantOrderController::addShoppingCartFromMerchantOrder end result:{}",rsp.getResult());
		return ResultEntity.success(rsp.getResult());
	}
	
	
	/**
	 * 获取登陆方商户地址列表
	 */
	@RequestMapping("wxListAddress")
	public ResultEntity<List<AddressVO>> wxListAddress(@RequestBody AddressVO addressVo)
	{	
		List<BsAddressDTO> dtoResult = new ArrayList<>();
		logger.info("GhMerchantOrderController::wxListAddress start addressVo:{}", addressVo);	
		BsAddressDTO condition = new BsAddressDTO();
		condition.setMerchantCode(addressVo.getMerchantCode());
		JstUtils.setBaseDTO(condition);
		RpcResponse<List<BsAddressDTO>> rsp = wxBsMerchantRemote.listBsAddressByMerchantCode(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxListAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		List<BsAddressDTO> result = rsp.getResult();
		
		RpcResponse<BsAddressDTO> lastRsp = motorcycleRemote.getLastMerchantOrderAddress(condition);
		errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxListAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		BsAddressDTO addressDto = lastRsp.getResult();
		
		if(null != addressDto){
			dtoResult.add(addressDto);
		}
		if(null != result){
			for(BsAddressDTO item:result){
				if(null != addressDto){
					if(item.getId().equals(addressDto.getId())){
						continue;
					}
				}	
				dtoResult.add(item);
			}
		}
		List<AddressVO> listBsAddressVO = BsAddressHttpConvert.convertList(dtoResult);
		logger.info("GhMerchantOrderController::wxListAddress end listBsAddressVO:{}", listBsAddressVO);
		return ResultEntity.success(listBsAddressVO);
	}
	
	/**
	 * 删除登陆方商户地址
	 */
	@RequestMapping("wxRemoveAddress")
	public ResultEntity<Integer> wxRemoveAddress(@RequestBody AddressVO address)
	{	
		logger.info("GhMerchantOrderController::wxRemoveAddress start address:{}",address);
		BsAddressDTO condition = new BsAddressDTO();
		condition.setMerchantCode(address.getMerchantCode());
		condition.setId(address.getId());
		JstUtils.setBaseDTO(condition);
		RpcResponse<Integer> rsp = wxBsMerchantRemote.removeBsAddress(condition);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxRemoveAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		Integer result = rsp.getResult();
		logger.info("GhMerchantOrderController::wxRemoveAddress result:{}", result);
		return ResultEntity.success(result);	
	}
	
	
	/**
	 * 添加登陆方商户地址
	 */
	@RequestMapping("wxAddAddress")
	public ResultEntity<AddressVO> wxAddAddress(@RequestBody AddressVO address)
	{
		logger.info("GhMerchantOrderController::wxAddAddress start address:{}", address);	
		BsAddressDTO dtoConditon = BsAddressHttpConvert.convertVO(address);
		JstUtils.setBaseDTO(dtoConditon);
		RpcResponse<BsAddressDTO> rsp = wxBsMerchantRemote.saveBsAddress(dtoConditon);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxListAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		BsAddressDTO result = rsp.getResult();
		AddressVO vo = BsAddressHttpConvert.convertDTO(result);
		logger.info("GhMerchantOrderController::wxAddAddress end vo:{}", vo);
		return ResultEntity.success(vo);
	}
	
	/**
	 * 根据id获取地址
	 */
	@RequestMapping("wxGetAddress")
	public ResultEntity<AddressVO> wxGetAddress(@RequestBody AddressVO address){
		logger.info("GhMerchantOrderController::wxGetAddress start address:{}", address);	
		BsAddressDTO dtoConditon = BsAddressHttpConvert.convertVO(address);
		JstUtils.setBaseDTO(dtoConditon);
		RpcResponse<BsAddressDTO> rsp = wxBsMerchantRemote.getBsAddressById(dtoConditon);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxGetAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		BsAddressDTO result = rsp.getResult();
		AddressVO vo = BsAddressHttpConvert.convertDTO(result);
		logger.info("GhMerchantOrderController::wxAddAddress end vo:{}", vo);
		return ResultEntity.success(vo);
	}
	
	/**
	 * 根据id修改地址
	 */
	@RequestMapping("wxUpdateAddress")
	public ResultEntity<AddressVO> wxUpdateAddress(@RequestBody AddressVO address){
		logger.info("GhMerchantOrderController::wxUpdateAddress start address:{}", address);
		BsAddressDTO dtoConditon = BsAddressHttpConvert.convertVO(address);
		JstUtils.setBaseDTO(dtoConditon);
		RpcResponse<BsAddressDTO> rsp = wxBsMerchantRemote.updateBsAddressById(dtoConditon);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxUpdateAddress return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		BsAddressDTO result = rsp.getResult();
		AddressVO vo = BsAddressHttpConvert.convertDTO(result);
		logger.info("GhMerchantOrderController::wxUpdateAddress end vo:{}", vo);
		return ResultEntity.success(vo);
	}

	/**
	 * @author: luoqiang
	 * @description: 广汇采购订单列表
	 * @date: 2020/9/2 11:38
	 * @param ghMerchantOrderQueryParamVO
	 * @return: cn.com.glsx.framework.core.beans.ResultEntity<cn.com.glsx.framework.core.beans.rpc.RpcPagination<cn.com.glsx.supplychain.jst.dto.gh.NewGhMerchantOrderDTO>>
	 */
	@ApiOperation(value = "广汇采购订单列表",notes = "广汇采购订单列表")
	@PostMapping("wxlistMerchantOrders")
	public ResultEntity<RpcPagination<NewGhMerchantOrderDTO>> wxlistMerchantOrders(@RequestBody GhMerchantOrderQueryParamVO  ghMerchantOrderQueryParamVO) {
		logger.info("GhMerchantOrderController::wxlistMerchantOrders start ghMerchantOrderQueryParamVO:{}",ghMerchantOrderQueryParamVO);
		RpcPagination<NewGhMerchantOrderDTO> pagination=new RpcPagination<>();
		NewGhMerchantOrderDTO ghMerchantOrderDTO=new NewGhMerchantOrderDTO();
		ghMerchantOrderDTO.setDtoStatus(ghMerchantOrderQueryParamVO.getDtoStatus());
		ghMerchantOrderDTO.setCategoryId(ghMerchantOrderQueryParamVO.getCategoryId());
		ghMerchantOrderDTO.setSpaPurchaseCode(ghMerchantOrderQueryParamVO.getSpaPurchaseCode());
		ghMerchantOrderDTO.setSpaProductCode(ghMerchantOrderQueryParamVO.getSpaProductCode());
		ghMerchantOrderDTO.setSpaProductName(ghMerchantOrderQueryParamVO.getSpaProductName());
		ghMerchantOrderDTO.setMerchantCode(ghMerchantOrderQueryParamVO.getMerchantCode());
		pagination.setPageNum(ghMerchantOrderQueryParamVO.getPageNum());
		pagination.setPageSize(ghMerchantOrderQueryParamVO.getPageSize());
		pagination.setCondition(ghMerchantOrderDTO);
		RpcResponse<RpcPagination<NewGhMerchantOrderDTO>> rsp = motorcycleRemote.wxlistMerchantOrders(pagination);
		JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
		if (!StringUtils.isEmpty(errCodeEnum)) {
			logger.info("GhMerchantOrderController::wxlistMerchantOrders return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		RpcPagination<NewGhMerchantOrderDTO> rpcResult = rsp.getResult();
		logger.info("GhMerchantOrderController::wxlistMerchantOrders end rpcResult:{}",rpcResult);
		return ResultEntity.success(rpcResult);
	}

	/**
	 * @author: luoqiang
	 * @description: 广汇采购订单-催单
	 * @date: 2020/8/27 15:12
	 * @param ghMerchantOrderDTO
	 * @return: cn.com.glsx.framework.core.beans.ResultEntity<java.lang.Integer>
	 */
	@ApiOperation(value = "广汇采购订单-催单",notes = "广汇采购订单-催单")
	@PostMapping("wxReminderOrder")
	public ResultEntity<Integer> wxReminderOrder(@RequestBody GhMerchantOrderDTO ghMerchantOrderDTO) {
		RpcResponse<Integer> rsp = motorcycleRemote.wxReminderOrder(ghMerchantOrderDTO);
		if (rsp.getError() == null) {
			rsp.setMessage("查询成功");
		} else {
			logger.error(rsp.getMessage());
		}
		return ResultEntity.success(rsp.getResult());
	}


	/**
	 * @author: luoqiang
	 * @description: 广汇采购订单-取消订单
	 * @date: 2020/8/25 11:56
	 * @param ghMerchantOrderDTO
	 * @return: cn.com.glsx.framework.core.beans.ResultEntity<java.util.List<cn.com.glsx.supplychain.jst.dto.gh.GhMerchantOrderDTO>>
	 */
	@ApiOperation(value = "广汇采购订单-取消订单",notes = "广汇采购订单-取消订单")
	@PostMapping("wxCancelOrder")
	public ResultEntity<Integer> wxCancelOrder(@RequestBody GhMerchantOrderDTO ghMerchantOrderDTO) {
		RpcResponse<Integer> rsp = motorcycleRemote.wxCancelOrder(ghMerchantOrderDTO);
		if (rsp.getError() == null) {
			rsp.setMessage("查询成功");
		} else {
			logger.error(rsp.getMessage());
		}
		return ResultEntity.success(rsp.getResult());
	}

	/**
	 * @author: luoqiang
	 * @description: 查采购单物流信息
	 * @date: 2020/8/26 16:40
	 * @param ghMerchantOrderCode
	 * @return: cn.com.glsx.framework.core.beans.ResultEntity<java.util.List<cn.com.glsx.supplychain.jst.dto.BsLogisticsDTO>>
	 */
	@ApiOperation(value = "查采购单物流信息",notes = "查采购单物流信息")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "ghMerchantOrderCode", value = "广汇订单ID", required = true) })
	@GetMapping("getLogisticsBySpaPurchaseCode")
	public ResultEntity<List<BsLogisticsDTO>> wxGetLogisticsInfoListByServiceCode(@RequestParam(value = "ghMerchantOrderCode")String ghMerchantOrderCode) {
		BsLogisticsDTO logisticsDTO = new BsLogisticsDTO();
		logisticsDTO.setGhMerchantOrderCode(ghMerchantOrderCode);
		logger.info("查询物流信息List根据ghMerchantOrderCode:");
		RpcResponse<List<BsLogisticsDTO>> response = motorcycleRemote.wxGetLogisticsInfoListByServiceCode(logisticsDTO);
		logger.info("查询物流信息List根据spaPurchaseCode：");
		if (response.getError() == null) {
			response.setMessage("查询成功");
		} else {
			logger.error(response.getMessage());
		}
		return ResultEntity.success(response.getResult());
	}
}
