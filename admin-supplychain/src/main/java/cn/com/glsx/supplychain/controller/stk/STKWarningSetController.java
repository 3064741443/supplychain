package cn.com.glsx.supplychain.controller.stk;

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
import cn.com.glsx.supplychain.jxc.dto.STKWarningSetDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.StkMerchantStockRemote;
import cn.com.glsx.supplychain.model.stk.WarningSetDelVO;
import cn.com.glsx.supplychain.model.stk.WarningSetQueryVO;
import cn.com.glsx.supplychain.model.stk.WarningSetSubVO;
import cn.com.glsx.supplychain.model.stk.WarningSetUpdVO;

/**
 * @Title: STKWarningSetController.java
 * @Description: 预警设置
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/STKWarningSet",tags="库存管理 预警设置")
@RestController
@RequestMapping("STKWarningSet")
public class STKWarningSetController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private StkMerchantStockRemote stkMerchantStockRemote;
	
	@ApiOperation(value="监控预警设置列表",notes="监控预警设置列表")
	@PostMapping("pageMerchantWarningSet")
	public ResultEntity<RpcPagination<STKWarningSetDTO>> pageMerchantWarningSet(@RequestBody WarningSetQueryVO queryVo){
		logger.info("STKWarningSetController::pageMerchantWarningSet start queryVo:{}",queryVo);
		RpcPagination<STKWarningSetDTO> pagination = new RpcPagination<>();
		STKWarningSetDTO dtoCondition = new STKWarningSetDTO();
		dtoCondition.setWarningType(queryVo.getWarningType());
		if(queryVo.getChannelId() != null){
			dtoCondition.setMerchantChannelId(String.valueOf(queryVo.getChannelId()));
		}
		dtoCondition.setMerchantCode(queryVo.getMerchantName());
		dtoCondition.setMerchantName(queryVo.getMerchantName());
		dtoCondition.setDeviceType(queryVo.getDeviceType());
		pagination.setPageNum(queryVo.getPageNum());
		pagination.setPageSize(queryVo.getPageSize());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<STKWarningSetDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantWarningSet(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::pageMerchantWarningSet return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::pageMerchantWarningSet end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="监控预警设置提交",notes="监控预警设置提交")
	@PostMapping("subMerchantWarningSet")
	public ResultEntity<Integer> subMerchantWarningSet(@RequestBody WarningSetSubVO subVo){
		logger.info("STKWarningSetController::subMerchantWarningSet start subVo:{}",subVo);
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		STKWarningSetDTO dtoCondition = new STKWarningSetDTO();
		dtoCondition.setWarningType(subVo.getWarningType());
		dtoCondition.setChannelType(subVo.getChannelType());
		dtoCondition.setDeletedFlag("N");
		dtoCondition.setMerchantChannelId(subVo.getMerchantChannelId());
		dtoCondition.setMerchantChannelName(subVo.getMerchantChannelName());
		dtoCondition.setMerchantCode(subVo.getMerchantCode());
		dtoCondition.setMerchantName(subVo.getMerchantName());
		dtoCondition.setDeviceType(subVo.getDeviceType());
		dtoCondition.setThresholdHigh(subVo.getThresholdHigh());
		dtoCondition.setThresholdLow(subVo.getThresholdLow());
		dtoCondition.setConsumer(userName);
		RpcResponse<Integer> rpcResponse = stkMerchantStockRemote.subMerchantWarningSet(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::subMerchantWarningSet return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::subMerchantWarningSet end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="监控预警设置修改",notes="监控预警设置修改")
	@PostMapping("updateMerchantWarningSet")
	public ResultEntity<Integer> updateMerchantWarningSet(@RequestBody WarningSetUpdVO updVo){
		logger.info("STKWarningSetController::updateMerchantWarningSet start updVo:{}",updVo);
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		STKWarningSetDTO dtoCondition = new STKWarningSetDTO();
		dtoCondition.setWarningCode(updVo.getWarningCode());
		dtoCondition.setThresholdHigh(updVo.getThresholdHigh());
		dtoCondition.setThresholdLow(updVo.getThresholdLow());
		dtoCondition.setConsumer(userName);
		RpcResponse<Integer> rpcResponse = stkMerchantStockRemote.updateMerchantWarningSet(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::updateMerchantWarningSet return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::updateMerchantWarningSet end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="监控预警设置删除",notes="监控预警设置删除")
	@PostMapping("deleteMerchantWarningSet")
	public ResultEntity<Integer> deleteMerchantWarningSet(@RequestBody WarningSetDelVO delVo){
		logger.info("STKWarningSetController::deleteMerchantWarningSet start delVo:{}",delVo);
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		STKWarningSetDTO dtoCondition = new STKWarningSetDTO();
		dtoCondition.setWarningCode(delVo.getWarningCode());
		dtoCondition.setDeletedFlag("Y");
		dtoCondition.setConsumer(userName);
		RpcResponse<Integer> rpcResponse = stkMerchantStockRemote.updateMerchantWarningSet(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::deleteMerchantWarningSet return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::deleteMerchantWarningSet end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	
}
