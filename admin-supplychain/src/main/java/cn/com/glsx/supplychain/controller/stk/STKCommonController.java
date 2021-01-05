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

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jxc.dto.JXCDealerUserInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMaterialInfoDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceTypeDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTMerchantChannelDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.model.jxc.JXCDealerUserInfoSearchVO;
import cn.com.glsx.supplychain.model.jxc.JXCDealerUserInfoVo;
import cn.com.glsx.supplychain.model.jxc.JXCMTBsMaterialInfoQueryVO;

/**
 * @Title: STKCommonController.java
 * @Description: 库存预警公共模块
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/STKCommon",tags="库存管理 预警公共模块")
@RestController
@RequestMapping("STKCommon")
public class STKCommonController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	
	@ApiOperation(value="获取服务商渠道列表",notes="获取服务商渠道列表")
	@PostMapping("listServerMerchantChannel")
	public ResultEntity<List<JXCMTMerchantChannelDTO>> listServerMerchantChannel(){
		User user = userInfoHolder.getUser();
		logger.info("STKCommonController::listMerchantChannel start user:{}",user);
		JXCMTMerchantChannelDTO dtoCondition = new JXCMTMerchantChannelDTO();
		RpcResponse<List<JXCMTMerchantChannelDTO>> rpcResponse = jxcCommonRemote.listMerchantChannel(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKCommonController::listMerchantChannel return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        
        List<JXCMTMerchantChannelDTO> listChannelDto = new ArrayList<>();
    	JXCMTMerchantChannelDTO dto = new JXCMTMerchantChannelDTO();
    	dto.setId(428);
    	dto.setName("直营店库存");
    	listChannelDto.add(dto);
    	dto = new JXCMTMerchantChannelDTO();
    	dto.setId(421);
    	dto.setName("服务商库存");
    	listChannelDto.add(dto);
    	dto = new JXCMTMerchantChannelDTO();
    	dto.setId(422);
    	dto.setName("金融代销库存");
    	listChannelDto.add(dto);
        logger.info("STKCommonController::listMerchantChannel end result:{}", rpcResponse.getResult());
		return ResultEntity.success(listChannelDto);
	}
	
	@ApiOperation(value="获取服务商列表",notes="获取服务商列表")
	@PostMapping("listServerMerchant")
	public ResultEntity<RpcPagination<JXCDealerUserInfoDTO>> pageServerMerchant(@RequestBody JXCDealerUserInfoSearchVO searchVo){
		logger.info("STKCommonController::pageServerMerchant start searchVo:{}",searchVo);
		RpcPagination<JXCDealerUserInfoDTO> pagination = new RpcPagination<>();
		JXCDealerUserInfoDTO dtoCondition = new JXCDealerUserInfoDTO();
		dtoCondition.setServiceProviderCode(searchVo.getSearchKey());
		dtoCondition.setServiceProviderName(searchVo.getSearchKey());
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(searchVo.getPageNum());
		pagination.setPageSize(searchVo.getPageSize());
		RpcResponse<RpcPagination<JXCDealerUserInfoDTO>> rpcResponse = jxcCommonRemote.pageServerMerchant(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKCommonController::pageServerMerchant return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKCommonController::pageServerMerchant end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取设备大类列表",notes="获取设备大类列表")
	@PostMapping("listDeviceType")
	public ResultEntity<List<JXCMTDeviceTypeDTO>> listDeviceType(){
		User user = userInfoHolder.getUser();
		logger.info("STKCommonController::listDeviceType start user:{}",user);
		JXCMTDeviceTypeDTO dtoCondition = new JXCMTDeviceTypeDTO();
		RpcResponse<List<JXCMTDeviceTypeDTO>> rpcResponse = jxcCommonRemote.listDeviceType(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKCommonController::listDeviceType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("STKCommonController::listDeviceType end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	
	@ApiOperation(value="获取硬件物料列表",notes="获取硬件物料列表")
	@PostMapping("pageMaterialInfo")
	public ResultEntity<RpcPagination<JXCMTBsMaterialInfoDTO>> pageMaterialInfo(@RequestBody JXCMTBsMaterialInfoQueryVO pageVo){
		User user = userInfoHolder.getUser();
		logger.info("STKCommonController::pageMaterialInfo start user:{},pageVo:{}",user,pageVo);
		JXCMTBsMaterialInfoDTO dtoCondition = new JXCMTBsMaterialInfoDTO();
		dtoCondition.setMaterialCode(pageVo.getMaterialName());
		dtoCondition.setMaterialName(pageVo.getMaterialName());
		dtoCondition.setDeviceTypeId(pageVo.getDeviceTypeId());
		RpcResponse<RpcPagination<JXCMTBsMaterialInfoDTO>> rpcResponse =jxcCommonRemote.pageMaterialInfo(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKCommonController::pageMaterialInfo return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("STKCommonController::pageMaterialInfo end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
}
