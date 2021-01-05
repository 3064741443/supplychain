package cn.com.glsx.supplychain.controller.stk;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jxc.dto.JXCAmKcWarehouseRelationDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMaterialInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigDetailDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigQueryDTO;
import cn.com.glsx.supplychain.jxc.dto.STKProductConfigSubmitDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.STKProductConfigMaterialTypeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.StkMerchantStockRemote;
import cn.com.glsx.supplychain.model.jxc.JXCAmKcWarehouseRelationQueryVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTBsMaterialInfoQueryVO;
import cn.com.glsx.supplychain.model.stk.ProductConfigGetVO;
import cn.com.glsx.supplychain.model.stk.ProductConfigMerchantVO;
import cn.com.glsx.supplychain.model.stk.ProductConfigQueryVO;
import cn.com.glsx.supplychain.model.stk.ProductConfigSalesMaterialVO;
import cn.com.glsx.supplychain.model.stk.ProductConfigSendMaterialVO;
import cn.com.glsx.supplychain.model.stk.ProductConfigVO;

/**
 * @Title: STKProductConfigController.java
 * @Description: 产品配置
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/STKProductConfig",tags="库存管理 产品配置")
@RestController
@RequestMapping("STKProductConfig")
public class STKProductConfigController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	@Autowired
	private StkMerchantStockRemote stkMerchantStockRemote;
	
	@ApiOperation(value="获取硬件物料列表",notes="获取硬件物料列表")
	@PostMapping("pageMaterialInfo")
	public ResultEntity<RpcPagination<JXCMTBsMaterialInfoDTO>> pageMaterialInfo(@RequestBody JXCMTBsMaterialInfoQueryVO pageVo){
		User user = userInfoHolder.getUser();
		logger.info("STKProductConfigController::pageMaterialInfo start user:{},pageVo:{}",user,pageVo);
		JXCMTBsMaterialInfoDTO dtoCondition = new JXCMTBsMaterialInfoDTO();
		dtoCondition.setMaterialCode(pageVo.getMaterialName());
		dtoCondition.setMaterialName(pageVo.getMaterialName());
		dtoCondition.setDeviceTypeId(pageVo.getDeviceTypeId());
		RpcResponse<RpcPagination<JXCMTBsMaterialInfoDTO>> rpcResponse =jxcCommonRemote.pageMaterialInfo(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKProductConfigController::pageMaterialInfo return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("STKProductConfigController::pageMaterialInfo end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取服务商K3仓库列表",notes="获取服务商K3仓库列表")
	@PostMapping("pageKcWarehouse")
	public ResultEntity<RpcPagination<JXCAmKcWarehouseRelationDTO>> pageKcWarehouse(@RequestBody JXCAmKcWarehouseRelationQueryVO queryVo){
		logger.info("STKProductConfigController::pageKcWarehouse start queryVo:{}",queryVo);
		RpcPagination<JXCAmKcWarehouseRelationDTO> pagination = new RpcPagination<>();
		JXCAmKcWarehouseRelationDTO dtoCondition = new JXCAmKcWarehouseRelationDTO();
		dtoCondition.setMerchantCode(queryVo.getMerchantName());
		dtoCondition.setMerchantName(queryVo.getMerchantName());
		dtoCondition.setWarehouseCode(queryVo.getWarehouseName());
		dtoCondition.setWarehouseName(queryVo.getWarehouseName());
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(queryVo.getPageNum());
		pagination.setPageSize(queryVo.getPageSize());
		RpcResponse<RpcPagination<JXCAmKcWarehouseRelationDTO>> rpcResponse = jxcCommonRemote.pageAmKcWarehouseRelation(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKProductConfigController::pageKcWarehouse return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("STKProductConfigController::pageKcWarehouse end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="服务商库存产品配置列表",notes="服务商库存产品配置列表")
	@PostMapping("pageMerchantProductConfig")
	public ResultEntity<RpcPagination<STKProductConfigDTO>> pageMerchantProductConfig(@RequestBody ProductConfigQueryVO queryVo){
		logger.info("STKProductConfigController::pageMerchantProductConfig start queryVo:{}",queryVo);
		STKProductConfigQueryDTO dtoCondition = new STKProductConfigQueryDTO();
		dtoCondition.setMaterialCode(queryVo.getMaterialName());
		dtoCondition.setMaterialName(queryVo.getMaterialName());
		dtoCondition.setMerchantCode(queryVo.getMerchantName());
		dtoCondition.setMerchantName(queryVo.getMerchantName());
		dtoCondition.setPeriodStatus(queryVo.getPeriodStatus());
		dtoCondition.setPageNum(queryVo.getPageNum());
		dtoCondition.setPageSize(queryVo.getPageSize());
		RpcResponse<RpcPagination<STKProductConfigDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantProductConfig(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKProductConfigController::pageMerchantProductConfig return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKProductConfigController::pageMerchantProductConfig end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="根据操作码获取服务商库存产品配置",notes="根据操作码获取服务商库存产品配置")
	@PostMapping("getProductConfigByOperatorCode")
	public ResultEntity<ProductConfigVO> getProductConfigByOperatorCode(@RequestBody ProductConfigGetVO getVo){
		logger.info("STKProductConfigController::getProductConfigByOperatorCode start getVo:{}",getVo);
		ProductConfigVO productConfigVo = new ProductConfigVO();
		STKProductConfigDTO dtoCondition = new STKProductConfigDTO();
		dtoCondition.setOperateCode(getVo.getOperateCode());
		RpcResponse<List<STKProductConfigDTO>> rpcResponse = stkMerchantStockRemote.getMerchantProductConfigByOperatorCode(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKProductConfigController::getProductConfigByOperatorCode return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		List<STKProductConfigDTO> listConfigDto = rpcResponse.getResult();
		if(null == listConfigDto || listConfigDto.isEmpty()){
			return ResultEntity.success(null);
		}
		List<ProductConfigMerchantVO> listMerchant = new ArrayList<>();
		List<ProductConfigSalesMaterialVO> listSalesMaterial = new ArrayList<>();
		List<ProductConfigSendMaterialVO> listSendMaterial = new ArrayList<>();
		ProductConfigMerchantVO merchantVo = null;
		ProductConfigSalesMaterialVO salesVo = null;
		ProductConfigSendMaterialVO sendVo = null;
		boolean beDone = false;
		for(STKProductConfigDTO configDto:listConfigDto){
			merchantVo = new ProductConfigMerchantVO();
			merchantVo.setConfigCode(configDto.getConfigCode());
			merchantVo.setMerchantCode(configDto.getMerchantCode());
			merchantVo.setMerchantName(configDto.getMerchantName());
			merchantVo.setWarehouseCode(configDto.getWarehouseCode());
			merchantVo.setWarehouseName(configDto.getWarehouseName());
			listMerchant.add(merchantVo);
			if(beDone)
				continue;
			for(STKProductConfigDetailDTO detailDto:configDto.getListConfigDetail()){
				if(detailDto.getTypeFlag().equals(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SALES.getCode())){
					salesVo = new ProductConfigSalesMaterialVO();
					salesVo.setDeviceType(detailDto.getDeviceType());
					salesVo.setDeviceTypeName(detailDto.getDeviceTypeName());
					salesVo.setMaterialCode(detailDto.getMaterialCode());
					salesVo.setMaterialName(detailDto.getMaterialName());
					salesVo.setTotal(detailDto.getTotal());
					listSalesMaterial.add(salesVo);
				}else{
					sendVo = new ProductConfigSendMaterialVO();
					sendVo.setDeviceType(detailDto.getDeviceType());
					sendVo.setDeviceTypeName(detailDto.getDeviceTypeName());
					sendVo.setMaterialCode(detailDto.getMaterialCode());
					sendVo.setMaterialName(detailDto.getMaterialName());
					sendVo.setTotal(detailDto.getTotal());
					listSendMaterial.add(sendVo);
				}
			}
			productConfigVo.setOperateCode(configDto.getOperateCode());
			productConfigVo.setPeriodEnd(configDto.getPeriodEnd());
			productConfigVo.setPeriodStart(configDto.getPeriodStart());
			productConfigVo.setListMerchant(listMerchant);
			productConfigVo.setListSalesMaterial(listSalesMaterial);
			productConfigVo.setListSendMaterial(listSendMaterial);
			beDone = true;
		}
		logger.info("STKProductConfigController::getProductConfigByOperatorCode start getVo:{}",getVo);
		return ResultEntity.success(productConfigVo);
	}
	
	@ApiOperation(value="新增服务商库存产品配置",notes="新增服务商库存产品配置")
	@PostMapping("addMerchantProductConfig")
	public ResultEntity<Integer> addMerchantProductConfig(@RequestBody ProductConfigVO configVo){
		logger.info("STKProductConfigController::addMerchantProductConfig start getVo:{}",configVo);
		if(configVo.getListMerchant() == null || configVo.getListMerchant().isEmpty()
				|| configVo.getListSalesMaterial() == null || configVo.getListSalesMaterial().isEmpty()
				|| configVo.getListSendMaterial() == null || configVo.getListSendMaterial().isEmpty()){
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
		}
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		
		STKProductConfigSubmitDTO dtoCondition = new STKProductConfigSubmitDTO();
		List<STKProductConfigDTO> listProductConfig = new ArrayList<>();
		List<STKProductConfigDetailDTO> listConfigDetail = null;
		STKProductConfigDTO configDto = null;
		STKProductConfigDetailDTO detailDto = null;
		for(ProductConfigMerchantVO merchantVo:configVo.getListMerchant()){
			configDto = new STKProductConfigDTO();
			configDto.setMerchantCode(merchantVo.getMerchantCode());
			configDto.setMerchantName(merchantVo.getMerchantName());
			configDto.setPeriodStart(configVo.getPeriodStart());
			configDto.setPeriodEnd(configVo.getPeriodEnd());
			configDto.setWarehouseCode(merchantVo.getWarehouseCode());
			configDto.setWarehouseName(merchantVo.getWarehouseName());
			listConfigDetail = new ArrayList<>();
			for(ProductConfigSalesMaterialVO salesVo:configVo.getListSalesMaterial()){
				detailDto = new STKProductConfigDetailDTO();
				detailDto.setDeviceType(salesVo.getDeviceType());
				detailDto.setDeviceTypeName(salesVo.getDeviceTypeName());
				detailDto.setMaterialCode(salesVo.getMaterialCode());
				detailDto.setMaterialName(salesVo.getMaterialName());
				detailDto.setTotal(salesVo.getTotal());
				detailDto.setTypeFlag(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SALES.getCode());
				listConfigDetail.add(detailDto);
			}
			for(ProductConfigSendMaterialVO sendVo:configVo.getListSendMaterial()){
				detailDto = new STKProductConfigDetailDTO();
				detailDto.setDeviceType(sendVo.getDeviceType());
				detailDto.setDeviceTypeName(sendVo.getDeviceTypeName());
				detailDto.setMaterialCode(sendVo.getMaterialCode());
				detailDto.setMaterialName(sendVo.getMaterialName());
				detailDto.setTotal(sendVo.getTotal());
				detailDto.setTypeFlag(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SEND.getCode());
				listConfigDetail.add(detailDto);
			}
			configDto.setListConfigDetail(listConfigDetail);
			listProductConfig.add(configDto);
		}
		dtoCondition.setListProductConfig(listProductConfig);
		dtoCondition.setConsumer(userName);
		RpcResponse<Integer> rpcResponse = stkMerchantStockRemote.addMerchantProductConfig(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKProductConfigController::addMerchantProductConfig return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKProductConfigController::getProductConfigByOperatorCode end result:{}",rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="修改服务商库存产品配置",notes="修改服务商库存产品配置")
	@PostMapping("updateMerchantProductConfig")
	public ResultEntity<Integer> updateMerchantProductConfig(@RequestBody ProductConfigVO configVo){
		logger.info("STKProductConfigController::updateMerchantProductConfig start getVo:{}",configVo);
		if(configVo.getListMerchant() == null || configVo.getListMerchant().isEmpty()
				|| configVo.getListSalesMaterial() == null || configVo.getListSalesMaterial().isEmpty()
				|| configVo.getListSendMaterial() == null || configVo.getListSendMaterial().isEmpty()
				|| StringUtils.isEmpty(configVo.getOperateCode())){
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
		}
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		
		STKProductConfigSubmitDTO dtoCondition = new STKProductConfigSubmitDTO();
		List<STKProductConfigDTO> listProductConfig = new ArrayList<>();
		List<STKProductConfigDetailDTO> listConfigDetail = null;
		STKProductConfigDTO configDto = null;
		STKProductConfigDetailDTO detailDto = null;
		for(ProductConfigMerchantVO merchantVo:configVo.getListMerchant()){
			configDto = new STKProductConfigDTO();
			configDto.setConfigCode(merchantVo.getConfigCode());
			configDto.setMerchantCode(merchantVo.getMerchantCode());
			configDto.setMerchantName(merchantVo.getMerchantName());
			configDto.setPeriodStart(configVo.getPeriodStart());
			configDto.setPeriodEnd(configVo.getPeriodEnd());
			configDto.setWarehouseCode(merchantVo.getWarehouseCode());
			configDto.setWarehouseName(merchantVo.getWarehouseName());
			listConfigDetail = new ArrayList<>();
			for(ProductConfigSalesMaterialVO salesVo:configVo.getListSalesMaterial()){
				detailDto = new STKProductConfigDetailDTO();
				detailDto.setDeviceType(salesVo.getDeviceType());
				detailDto.setDeviceTypeName(salesVo.getDeviceTypeName());
				detailDto.setMaterialCode(salesVo.getMaterialCode());
				detailDto.setMaterialName(salesVo.getMaterialName());
				detailDto.setTotal(salesVo.getTotal());
				detailDto.setTypeFlag(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SALES.getCode());
				listConfigDetail.add(detailDto);
			}
			for(ProductConfigSendMaterialVO sendVo:configVo.getListSendMaterial()){
				detailDto = new STKProductConfigDetailDTO();
				detailDto.setDeviceType(sendVo.getDeviceType());
				detailDto.setDeviceTypeName(sendVo.getDeviceTypeName());
				detailDto.setMaterialCode(sendVo.getMaterialCode());
				detailDto.setMaterialName(sendVo.getMaterialName());
				detailDto.setTotal(sendVo.getTotal());
				detailDto.setTypeFlag(STKProductConfigMaterialTypeEnum.STK_PRODUCT_CONFIG_MATERIAL_SEND.getCode());
				listConfigDetail.add(detailDto);
			}
			configDto.setListConfigDetail(listConfigDetail);
			listProductConfig.add(configDto);
		}
		dtoCondition.setListProductConfig(listProductConfig);
		dtoCondition.setConsumer(userName);
		dtoCondition.setOperateCode(configVo.getOperateCode());
		RpcResponse<Integer> rpcResponse = stkMerchantStockRemote.updateMerchantProductConfig(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKProductConfigController::updateMerchantProductConfig return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKProductConfigController::updateMerchantProductConfig end result:{}",rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
}
