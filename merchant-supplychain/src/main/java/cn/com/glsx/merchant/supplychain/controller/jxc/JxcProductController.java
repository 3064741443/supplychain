
package cn.com.glsx.merchant.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.JxcUtils;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.vo.jxc.JXCMTAudiVO;
import cn.com.glsx.merchant.supplychain.vo.jxc.JXCMTParentBrandVO;
import cn.com.glsx.merchant.supplychain.vo.jxc.JXCMTProductSplitListQueryVO;
import cn.com.glsx.merchant.supplychain.vo.jxc.JXCMTSubBrandVO;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDetailDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitListQueryDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductTypeDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcProductRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;

import com.glsx.biz.common.base.entity.Carbrand;
import com.glsx.biz.common.base.entity.Carseries;
import com.glsx.biz.common.base.entity.Cartype;
import com.glsx.biz.common.base.service.CarbrandService;
import com.glsx.biz.common.base.service.CarseriesService;
import com.glsx.biz.common.base.service.CartypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: JxcProductController.java
 * @Description: 820改版 经销存系统小程序 PC端-广联产品
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/jxcProduct",tags="820改版 经销存系统小程序 PC端-广联产品")
@RestController
@RequestMapping("jxcProduct")
public class JxcProductController {

	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	 @Autowired
	 private JxcCommonRemote jxcCommonRemote;
	 @Autowired
	 private JxcProductRemote jxcProductRemote;
	 @Autowired
	 private CarbrandService carbrandService;
	 @Autowired
	 private CarseriesService carseriesService;
	 @Autowired
	 private CartypeService cartypeService;
	 
	 @ApiOperation(value="获取产品分类列表",notes="获取产品分类列表")
	 @PostMapping("listProductType")
	 public ResultEntity<List<JXCMTProductTypeDTO>> listProductType(){
		 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcProductController::listProductType start dealerUserInfo:{}",dealerUserInfo);
    	 if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	 {
    		 logger.info("JxcProductController::listProductType not login or session time out");
    		 return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	 }
    	 JXCMTProductTypeDTO dtoCondition = new JXCMTProductTypeDTO();
    	 RpcResponse<List<JXCMTProductTypeDTO>> rpcResponse = jxcCommonRemote.listProductType(dtoCondition);
    	 JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
         if (!StringUtils.isEmpty(errCodeEnum)) {
             logger.info("JxcProductController::listProductType return"
                     + errCodeEnum.getDescrible());
             return ResultEntity.error(errCodeEnum.getCode(),
                     errCodeEnum.getDescrible());
         }
         logger.info("JxcProductController::listProductType end result:{}", rpcResponse.getResult());
         return ResultEntity.success(rpcResponse.getResult());
	 }
	 
	 @ApiOperation(value="获取产品列表",notes="获取产品列表")
	 @PostMapping("listProduct")
	 public ResultEntity<List<JXCMTProductSplitDTO>> listProduct(@RequestBody JXCMTProductSplitListQueryVO queryVo){
		 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcProductController::listProduct start dealerUserInfo:{}",dealerUserInfo);
    	 if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	 {
    		 logger.info("JxcProductController::listProduct not login or session time out");
    		 return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	 }
    	 JXCMTProductSplitListQueryDTO dtoCondition = new JXCMTProductSplitListQueryDTO();
    	 dtoCondition.setMaterialCode(queryVo.getMaterialName());
    	 dtoCondition.setMaterialName(queryVo.getMaterialName());
    	 dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
    	 dtoCondition.setMerchantName(dealerUserInfo.getMerchantName());
    	 dtoCondition.setProductName(queryVo.getProductName());
    	 dtoCondition.setProductCode(queryVo.getProductName());
    	 dtoCondition.setProductTypeId(queryVo.getProductTypeId());
    	 JxcUtils.setJXCBaseDTO(dtoCondition);
    	 RpcResponse<List<JXCMTProductSplitDTO>> rpcResponse = jxcProductRemote.listJxcProduct(dtoCondition);
    	 JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
         if (!StringUtils.isEmpty(errCodeEnum)) {
             logger.info("JxcProductController::listProduct return"
                     + errCodeEnum.getDescrible());
             return ResultEntity.error(errCodeEnum.getCode(),
                     errCodeEnum.getDescrible());
         }
         logger.info("JxcProductController::listProduct end result:{}", rpcResponse.getResult());
         List<JXCMTProductSplitDTO> listProductSplit = rpcResponse.getResult();
         List<JXCMTProductSplitDTO> listResult = new ArrayList<>();
         Map<String,JXCMTProductSplitDTO> mapProductSplit = new HashMap<>();
         String strKey = "";
         JXCMTProductSplitDTO productSplitDto = null;
         //过滤 单套数量 物料 产品服务期限  产品套餐
         if(null == listProductSplit){
        	 return ResultEntity.success(listResult);
         }
         for(JXCMTProductSplitDTO productDto:listProductSplit){
        	 strKey = "ps" + productDto.getServiceTime() + "pp" + productDto.getPackageOne();
        	 if(productDto.getListProductSplitDetailDto() != null){
        		 for(JXCMTProductSplitDetailDTO detailDto:productDto.getListProductSplitDetailDto()){
        			 strKey += "pd";
        			 strKey += detailDto.getMaterialCode();
        			 strKey += "pt";
        			 strKey += detailDto.getPropQuantity();
        		 }
        	 }
        	 productSplitDto =  mapProductSplit.get(strKey);
        	 if(null == productSplitDto){
        		 mapProductSplit.put(strKey, productDto);
        		 listResult.add(productDto);
        	 }else if(productDto.getChannelId() == null){
        		 listResult.add(productDto);
        	 }
         }
         return ResultEntity.success(listResult);
	 }
	 
	 @ApiOperation(value="获取所有父品牌列表",notes="获取所有父品牌列表")
	 @PostMapping("listParentCarbrand")
	 public ResultEntity<List<JXCMTParentBrandVO>> listParentCarbrand(){
		 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcProductController::listParentCarbrand start dealerUserInfo:{}",dealerUserInfo);
    	 if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	 {
    		 logger.info("JxcProductController::listParentCarbrand not login or session time out");
    		 return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	 }
    	 List<JXCMTParentBrandVO> listResult = new ArrayList<>();
    	 List<Carbrand> listCardbrand = carbrandService.getParentCarbrandList();
    	 if(null != listCardbrand && listCardbrand.isEmpty()){
    		 return ResultEntity.success(listResult);
    	 }
    	 JXCMTParentBrandVO vo = null;
    	 for(Carbrand brand:listCardbrand){
    		 vo = new JXCMTParentBrandVO();
    		 vo.setId(brand.getBid());
    		 vo.setParentBrandName(brand.getBrand());
    		 listResult.add(vo);
    	 }
    	 return ResultEntity.success(listResult);
	 }
	 
	 @ApiOperation(value="通过品牌ID获取子品牌列表",notes="通过品牌ID获取子品牌列表")
	 @PostMapping("listSubCarbrand")
	 public ResultEntity<List<JXCMTSubBrandVO>> listSubCarbrand(JXCMTParentBrandVO parentBrandVo){
		 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcProductController::listParentCarbrand start dealerUserInfo:{}",dealerUserInfo);
    	 if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	 {
    		 logger.info("JxcProductController::listParentCarbrand not login or session time out");
    		 return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	 }
    	 List<JXCMTSubBrandVO> listResult = new ArrayList<>();
    	 List<Carbrand> listCardbrand = carbrandService.listSubCarbrandByParentId(parentBrandVo.getId());
    	 if(null != listCardbrand && listCardbrand.isEmpty()){
    		 return ResultEntity.success(listResult);
    	 }
    	 JXCMTSubBrandVO vo = null;
    	 for(Carbrand brand:listCardbrand){
    		 vo = new JXCMTSubBrandVO();
    		 vo.setId(brand.getBid());
    		 vo.setSubBrandName(brand.getBrand());
    		 listResult.add(vo);
    	 }
    	 return ResultEntity.success(listResult);
	 }
	 
	 @ApiOperation(value="通过子品牌ID获取车系列表",notes="通过子品牌ID获取车系列表")
	 @PostMapping("listAudio")
	 public ResultEntity<List<JXCMTAudiVO>> listAudio(JXCMTSubBrandVO parentBrandVo){
		 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcProductController::listAudio start dealerUserInfo:{}",dealerUserInfo);
    	 if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	 {
    		 logger.info("JxcProductController::listAudio not login or session time out");
    		 return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	 }
    	 List<JXCMTAudiVO> listResult = new ArrayList<>();
    	 List<Carseries> listCarseries = carseriesService.getByCarbrandId(parentBrandVo.getId());
    	 if(null != listCarseries && listCarseries.isEmpty()){
    		 return ResultEntity.success(listResult);
    	 }
    	 JXCMTAudiVO vo = null;
    	 for(Carseries carseries:listCarseries){
    		 vo = new JXCMTAudiVO();
    		 vo.setId(carseries.getCsid());
    		 vo.setAudiName(carseries.getCarseries());
    		 listResult.add(vo);
    	 }
    	 return ResultEntity.success(listResult);
	 }
	 
	 @ApiOperation(value="通过车系ID获取车型列表",notes="通过车系ID获取车型列表")
	 @PostMapping("listMotorcle")
	 public ResultEntity<List<String>> listMotorcle(JXCMTAudiVO parentBrandVo){
		 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcProductController::listMotorcle start dealerUserInfo:{}",dealerUserInfo);
    	 if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
    	 {
    		 logger.info("JxcProductController::listMotorcle not login or session time out");
    		 return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
    	 }
    	 List<String> listResult = new ArrayList<>();
    	 List<Cartype> listCartype = cartypeService.getBySeiesId(parentBrandVo.getId());
    	 if(null != listCartype && listCartype.isEmpty()){
    		 return ResultEntity.success(listResult);
    	 }
    	 JXCMTAudiVO vo = null;
    	 for(Cartype cartype:listCartype){
    		
    		 listResult.add(cartype.getName());
    	 }
    	 return ResultEntity.success(listResult);
	 }
	 
	  
}

