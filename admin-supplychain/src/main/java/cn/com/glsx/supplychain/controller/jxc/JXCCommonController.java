package cn.com.glsx.supplychain.controller.jxc;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.enums.AttriInfoEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.dto.JXCMTAttribInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTAttribManaDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMaterialInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsSubjectDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceCodeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeAttribInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTMerchantChannelDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTPackageDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductTypeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTWarehouseInfoDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.model.jxc.JXCMTAttribManaGetVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTAttribManaPageVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTAttribManaQueryVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTAttribManaSubmitVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTBsMaterialInfoQueryVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTDeviceCodeQueryVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTDevicePackageQueryVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTDeviceTypeAttribInfoVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTDeviceTypeVO;
import cn.com.glsx.supplychain.model.jxc.JXCMTWarehouseInfoQueryVO;

/**
 * @Title: JXCBSOrderController.java
 * @Description: 820改版  获取类型等基础公共模块
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/JXCCommon",tags="820改版  获取类型等基础公共模块")
@RestController
@RequestMapping("JXCCommon")
public class JXCCommonController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	
	@ApiOperation(value="获取产品分类列表",notes="获取产品分类列表")
	@PostMapping("listProductType")
	public ResultEntity<List<JXCMTProductTypeDTO>> listProductType(){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listProductType start user:{}",user);
		JXCMTProductTypeDTO dtoCondition = new JXCMTProductTypeDTO();
		RpcResponse<List<JXCMTProductTypeDTO>> rpcResponse = jxcCommonRemote.listProductType(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listProductType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listProductType end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取商户渠道列表",notes="获取商户渠道列表")
	@PostMapping("listMerchantChannel")
	public ResultEntity<List<JXCMTMerchantChannelDTO>> listMerchantChannel(){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listMerchantChannel start user:{}",user);
		JXCMTMerchantChannelDTO dtoCondition = new JXCMTMerchantChannelDTO();
		RpcResponse<List<JXCMTMerchantChannelDTO>> rpcResponse = jxcCommonRemote.listMerchantChannel(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listMerchantChannel return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listMerchantChannel end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取项目列表",notes="获取项目列表")
	@PostMapping("listSubject")
	public ResultEntity<List<JXCMTBsSubjectDTO>> listSubject(){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listSubject start user:{}",user);
		JXCMTBsSubjectDTO dtoCondition = new JXCMTBsSubjectDTO();
		RpcResponse<List<JXCMTBsSubjectDTO>> rpcResponse = jxcCommonRemote.listSubject(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listSubject return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listSubject end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	
	
	@ApiOperation(value="获取设备大类列表",notes="获取设备大类列表")
	@PostMapping("listDeviceType")
	public ResultEntity<List<JXCMTDeviceTypeDTO>> listDeviceType(){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listDeviceType start user:{}",user);
		JXCMTDeviceTypeDTO dtoCondition = new JXCMTDeviceTypeDTO();
		RpcResponse<List<JXCMTDeviceTypeDTO>> rpcResponse = jxcCommonRemote.listDeviceType(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listDeviceType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listDeviceType end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取设备小类列表",notes="获取设备小类列表")
	@PostMapping("listDeviceCode")
	public ResultEntity<List<JXCMTDeviceCodeDTO>> listDeviceCode(@RequestBody JXCMTDeviceCodeQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listDeviceCode start user:{},queryVo:{}",user,queryVo);
		JXCMTDeviceCodeDTO dtoCondition = new JXCMTDeviceCodeDTO();
		dtoCondition.setTypeId(queryVo.getTypeId());
		dtoCondition.setDeviceCode(queryVo.getDeviceCode());
		dtoCondition.setDeviceName(queryVo.getDeviceName());
		RpcResponse<List<JXCMTDeviceCodeDTO>> rpcResponse = jxcCommonRemote.listDeviceCode(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listDeviceCode return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listDeviceCode end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="根据小类获取套餐列表",notes="根据小类获取套餐列表")
	@PostMapping("listPackage")
	public ResultEntity<List<JXCMTPackageDTO>> listPackage(@RequestBody JXCMTDevicePackageQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listPackage start user:{},queryVo:{}",user,queryVo);
		JXCMTPackageDTO dtoCondition = new JXCMTPackageDTO();
		dtoCondition.setDeviceCode(queryVo.getDeviceCode());
		RpcResponse<List<JXCMTPackageDTO>> rpcResponse = jxcCommonRemote.listDevicePackage(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listPackage return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listPackage end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取硬件配置列表",notes="获取硬件配置列表")
	@PostMapping("listAttribMana")
	public ResultEntity<List<JXCMTAttribManaDTO>> listAttribMana(@RequestBody JXCMTAttribManaQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listAttribMana start user:{},queryVo:{}",user,queryVo);
		JXCMTAttribManaDTO dtoCondition = new JXCMTAttribManaDTO();
		dtoCondition.setDevTypeId(queryVo.getDeviceTypeId());
		dtoCondition.setAttribCode(queryVo.getAttribCode());
		RpcResponse<List<JXCMTAttribManaDTO>> rpcResponse = jxcCommonRemote.listAttribMana(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listAttribMana return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listAttribMana end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取硬件配置分页列表",notes="获取硬件配置分页列表")
	@PostMapping("pageAttribMana")
	public ResultEntity<RpcPagination<JXCMTAttribManaDTO>> pageAttribMana(@RequestBody JXCMTAttribManaPageVO pageVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::pageAttribMana start user:{},pageVo:{}",user,pageVo);
		JXCMTAttribManaDTO dtoCondition = new JXCMTAttribManaDTO();
		dtoCondition.setDevTypeId(pageVo.getDeviceTypeId());
		dtoCondition.setAttribCode(pageVo.getAttribName());
		dtoCondition.setAttribName(pageVo.getAttribName());
		dtoCondition.setPageNum(pageVo.getPageNum());
		dtoCondition.setPageSize(pageVo.getPageSize());
		RpcResponse<RpcPagination<JXCMTAttribManaDTO>> rpcResponse = jxcCommonRemote.pageAttribMana(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::pageAttribMana return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::pageAttribMana end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取硬件物料列表",notes="获取硬件物料列表")
	@PostMapping("pageMaterialInfo")
	public ResultEntity<RpcPagination<JXCMTBsMaterialInfoDTO>> pageMaterialInfo(@RequestBody JXCMTBsMaterialInfoQueryVO pageVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::pageMaterialInfo start user:{},pageVo:{}",user,pageVo);
		JXCMTBsMaterialInfoDTO dtoCondition = new JXCMTBsMaterialInfoDTO();
		dtoCondition.setMaterialCode(pageVo.getMaterialName());
		dtoCondition.setMaterialName(pageVo.getMaterialName());
		dtoCondition.setDeviceTypeId(pageVo.getDeviceTypeId());
		RpcResponse<RpcPagination<JXCMTBsMaterialInfoDTO>> rpcResponse =jxcCommonRemote.pageMaterialInfo(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::pageMaterialInfo return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::pageMaterialInfo end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	
	}
	
	@ApiOperation(value="获取仓库列表",notes="获取仓库列表")
	@PostMapping("listWarehouseInfos")
	public ResultEntity<List<JXCMTWarehouseInfoDTO>> listWarehouseInfos(@RequestBody JXCMTWarehouseInfoQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listWarehouseInfos start user:{},queryVo:{}",user,queryVo);
		JXCMTWarehouseInfoDTO dtoCondition = new JXCMTWarehouseInfoDTO();
		dtoCondition.setName(queryVo.getName());
		RpcResponse<List<JXCMTWarehouseInfoDTO>> rpcResponse = jxcCommonRemote.listWarehouseInfo(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listWarehouseInfos return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listWarehouseInfos end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="根据硬件配置编码获取硬件配置",notes="根据硬件配置编码获取硬件配置")
	@PostMapping("getAttribMana")
	public ResultEntity<JXCMTAttribManaDTO> getAttribMana(@RequestBody JXCMTAttribManaGetVO getVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::getAttribMana start user:{},getVo:{}",user,getVo);
		JXCMTAttribManaDTO dtoCondition = new JXCMTAttribManaDTO();
		dtoCondition.setAttribCode(getVo.getAttribCode());
		RpcResponse<JXCMTAttribManaDTO> rpcResponse = jxcCommonRemote.getAttribManaByAttribCode(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::getAttribMana return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::getAttribMana end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="查询机型数据",notes="查询机型数据")
	@PostMapping("getModel")
	public ResultEntity<List<JXCMTAttribInfoDTO>> getModel() {
		Integer type = Integer.parseInt(AttriInfoEnum.MODEL.getValue());
		JXCMTAttribInfoDTO attribInfo = new JXCMTAttribInfoDTO();
		attribInfo.setType(type);
		RpcResponse<List<JXCMTAttribInfoDTO>> rpcResponse = jxcCommonRemote.listAttrinInfoByAttribType(attribInfo);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::getModel return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::getModel end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="查询套机/裸机类型",notes="查询套机/裸机类型")
	@PostMapping("getType")
	public ResultEntity<List<JXCMTAttribInfoDTO>> getType() {
		Integer type = Integer.parseInt(AttriInfoEnum.TYPE.getValue());
		JXCMTAttribInfoDTO attribInfo = new JXCMTAttribInfoDTO();
		attribInfo.setType(type);
		RpcResponse<List<JXCMTAttribInfoDTO>> rpcResponse = jxcCommonRemote.listAttrinInfoByAttribType(attribInfo);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::getType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::getType end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	/**
	 * @Title: getConfigure
	 * @Description: 查询配置信息
	 * @param @return
	 * @return
	 * @throws
	 */
	@ApiOperation(value="查询配置信息",notes="查询配置信息")
	@PostMapping("getConfigure")
	public ResultEntity<List<JXCMTAttribInfoDTO>> getConfigure() {
		Integer type = Integer.parseInt(AttriInfoEnum.CONFIGURE.getValue());
		JXCMTAttribInfoDTO attribInfo = new JXCMTAttribInfoDTO();
		attribInfo.setType(type);
		RpcResponse<List<JXCMTAttribInfoDTO>> rpcResponse = jxcCommonRemote.listAttrinInfoByAttribType(attribInfo);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::getConfigure return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::getConfigure end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	/**
	 * @Title: getSize
	 * @Description: 查询尺寸
	 * @param @return
	 * @return
	 * @throws
	 */
	@ApiOperation(value="查询尺寸",notes="查询尺寸")
	@PostMapping("getSize")
	public ResultEntity<List<JXCMTAttribInfoDTO>> getSize() {
		Integer type = Integer.parseInt(AttriInfoEnum.SIZE.getValue());
		JXCMTAttribInfoDTO attribInfo = new JXCMTAttribInfoDTO();
		attribInfo.setType(type);
		RpcResponse<List<JXCMTAttribInfoDTO>> rpcResponse = jxcCommonRemote.listAttrinInfoByAttribType(attribInfo);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::getSize return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::getSize end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="根据设备大类获取硬件型号配置列表",notes="根据设备大类获取硬件型号配置列表")
	@PostMapping("listAttribInfoByDeviceType")
	public ResultEntity<List<JXCMTAttribInfoDTO>> listAttribInfoByDeviceType(@RequestBody JXCMTDeviceTypeVO typeVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::listAttribInfoByDeviceType start user:{},typeVo:{}",user,typeVo);
		JXCMTDeviceTypeDTO deviceTypeDto = new JXCMTDeviceTypeDTO();
		deviceTypeDto.setId(typeVo.getId());
		RpcResponse<List<JXCMTAttribInfoDTO>> rpcResponse = jxcCommonRemote.listAttribInfoByDeviceType(deviceTypeDto);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::listAttribInfoByDeviceType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::listAttribInfoByDeviceType end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="根据设备大类添加硬件型号配置",notes="根据设备大类添加硬件型号配置")
	@PostMapping("saveAttribInfoByDeviceType")
	public ResultEntity<Integer> saveAttribInfoByDeviceType(@RequestBody JXCMTDeviceTypeAttribInfoVO attrinInfoVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::saveAttribInfoByDeviceType start user:{},attrinInfoVo:{}",user,attrinInfoVo);
		JXCMTDeviceTypeAttribInfoDTO dtoCondition = new JXCMTDeviceTypeAttribInfoDTO();
		dtoCondition.setDeviceTypeId(attrinInfoVo.getDeviceTypeId());
		dtoCondition.setAttribName(attrinInfoVo.getAttribName());
		dtoCondition.setConsumer((user != null)?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcCommonRemote.saveAttribInfoByDeviceType(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::saveAttribInfoByDeviceType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::saveAttribInfoByDeviceType end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="保存硬件配置",notes="保存硬件配置")
	@PostMapping("saveAttribMana")
	public ResultEntity<Integer> saveAttribMana(@RequestBody JXCMTAttribManaSubmitVO submitVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::saveAttribMana start user:{},submitVo:{}",user,submitVo);
		JXCMTAttribManaDTO dtoCondition = new JXCMTAttribManaDTO();
		BeanUtils.copyProperties(submitVo, dtoCondition);
		dtoCondition.setConsumer((user != null)?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcCommonRemote.saveAttribMana(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::saveAttribMana return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::saveAttribMana end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="修改硬件配置",notes="保存硬件配置")
	@PostMapping("updateAttribMana")
	public ResultEntity<Integer> updateAttribMana(@RequestBody JXCMTAttribManaSubmitVO submitVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCCommonController::updateAttribMana start user:{},submitVo:{}",user,submitVo);
		JXCMTAttribManaDTO dtoCondition = new JXCMTAttribManaDTO();
		BeanUtils.copyProperties(submitVo, dtoCondition);
		dtoCondition.setConsumer((user != null)?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcCommonRemote.updateAttribMana(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCCommonController::updateAttribMana return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCCommonController::updateAttribMana end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	
}
