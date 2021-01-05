package cn.com.glsx.supplychain.controller.stk;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnDTO;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockSnStatDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.StkMerchantStockRemote;
import cn.com.glsx.supplychain.model.stk.*;
import cn.com.glsx.supplychain.util.ExcelReadAndWriteUtil;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: StkMerchantStockController.java
 * @Description: 服务商库存管理设备未激活统计
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/STKMerchantStockSnStat",tags="库存管理 设备未激活统计")
@RestController
@RequestMapping("STKMerchantStockSnStat")
public class STKMerchantStockSnStatController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private StkMerchantStockRemote stkMerchantStockRemote;
	
	@ApiOperation(value="按照设备大类统计未激活设备",notes="按照设备大类统计未激活设备")
	@PostMapping("getMerchantStockSnStatByDeviceType")
	public ResultEntity<List<STKMerchantStockSnStatDTO>> getMerchantStockSnStatByDeviceType(@RequestBody StockSnDevTypeStatVO typeVo){
		logger.info("STKMerchantStockController::getMerchantStockSnStatByDeviceType start typeVo:{}",typeVo);
		STKMerchantStockSnStatDTO dtoCondition = new STKMerchantStockSnStatDTO();
		dtoCondition.setActiveOrNot("N");
		if(typeVo.getUnActiveDayFlag().equals("TH")){
			dtoCondition.setUnActiveDays(90);
		}else if(typeVo.getUnActiveDayFlag().equals("SI")){
			dtoCondition.setUnActiveDays(180);
		}else if(typeVo.getUnActiveDayFlag().equals("NI")){
			dtoCondition.setUnActiveDays(270);
		}
		RpcResponse<List<STKMerchantStockSnStatDTO>> rpcResponse = stkMerchantStockRemote.getMerchantStockSnStatByDeviceType(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKMerchantStockController::getMerchantStockSnStatByDeviceType return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        if(rpcResponse.getResult() != null){
        	for(STKMerchantStockSnStatDTO statDto:rpcResponse.getResult()){
        		if(statDto.getUnActiveDays().equals(90)){
        			statDto.setUnActiveDayFlag("TH");
        		}else if(statDto.getUnActiveDays().equals(180)){
        			statDto.setUnActiveDayFlag("SI");
        		}else if(statDto.getUnActiveDays().equals(270)){
        			statDto.setUnActiveDayFlag("NI");
        		}
        	}
        }
        logger.info("STKMerchantStockController::getMerchantStockSnStatByDeviceType end result:{}",rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="按照设备大类商户统计未激活设备",notes="按照设备大类商户统计未激活设备")
	@PostMapping("pageMerchantStockSnStatByToMerchantCode")
	public ResultEntity<RpcPagination<STKMerchantStockSnStatDTO>> pageMerchantStockSnStatByToMerchantCode(@RequestBody StockSnToMerchantStatVO statVo){
		logger.info("STKMerchantStockController::pageMerchantStockSnStatByToMerchantCode start statVo:{}",statVo);
		RpcPagination<STKMerchantStockSnStatDTO> pagination = new RpcPagination<>();
		STKMerchantStockSnStatDTO dtoCondition = new STKMerchantStockSnStatDTO();
		dtoCondition.setActiveOrNot("N");
		if(statVo.getUnActiveDayFlag().equals("TH")){
			dtoCondition.setUnActiveDays(90);
		}else if(statVo.getUnActiveDayFlag().equals("SI")){
			dtoCondition.setUnActiveDays(180);
		}else if(statVo.getUnActiveDayFlag().equals("NI")){
			dtoCondition.setUnActiveDays(270);
		}
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(statVo.getPageNum());
		pagination.setPageSize(statVo.getPageSize());
		RpcResponse<RpcPagination<STKMerchantStockSnStatDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantStockSnStatByToMerchantCode(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("STKMerchantStockController::pageMerchantStockSnStatByToMerchantCode return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        if(rpcResponse.getResult() != null){
        	for(STKMerchantStockSnStatDTO statDto:rpcResponse.getResult().getList()){
        		if(statDto.getUnActiveDays().equals(90)){
        			statDto.setUnActiveDayFlag("TH");
        		}else if(statDto.getUnActiveDays().equals(180)){
        			statDto.setUnActiveDayFlag("SI");
        		}else if(statDto.getUnActiveDays().equals(270)){
        			statDto.setUnActiveDayFlag("NI");
        		}
        	}
        }
        logger.info("STKMerchantStockController::pageMerchantStockSnStatByToMerchantCode end result:{}",rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="设备激活统计导出 按照设备大类,商户",notes="设备激活统计导出 按照设备大类,商户")
	@PostMapping("exportMerchantStockSnStatByToMerchantCode")
	public ResultEntity<List<STKMerchantStockSnStatDTO>> exportMerchantStockSnStatByToMerchantCode(@RequestBody StockSnToMerchantStatVO statVo, HttpServletResponse response) throws Exception {
		User user = userInfoHolder.getUser();
		logger.info("STKMerchantStockController::exportMerchantStockSnStatByToMerchantCode start statVo:{}",statVo);
		STKMerchantStockSnStatDTO dtoCondition = new STKMerchantStockSnStatDTO();
		dtoCondition.setActiveOrNot("N");
		if(statVo.getUnActiveDayFlag().equals("TH")){
			dtoCondition.setUnActiveDays(90);
		}else if(statVo.getUnActiveDayFlag().equals("SI")){
			dtoCondition.setUnActiveDays(180);
		}else if(statVo.getUnActiveDayFlag().equals("NI")){
			dtoCondition.setUnActiveDays(270);
		}
		dtoCondition.setUnActiveDayFlag(statVo.getUnActiveDayFlag());
		dtoCondition.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<STKMerchantStockSnStatDTO>> rpcResponse = stkMerchantStockRemote.exportMerchantStockSnStatByToMerchantCode(dtoCondition);
		List<STKMerchantStockSnStatDTO> merchantStockSnStatDTOS=rpcResponse.getResult();
		List<STKMerchantStockSnStatExportVo> merchantStockSnStatExportVos = merchantStockSnStatDTOS.stream().map(merchantStockSnStatDTO -> exportMerchantStockSnStatConvertTo(merchantStockSnStatDTO)).collect(Collectors.toList());
		ExcelReadAndWriteUtil.writeExcel(response,merchantStockSnStatExportVos, Constants.EXPORT_NAME_STK_STOCK_SN_STAT, Constants.EXPORT_NAME_STK_STOCK_SN_STAT,STKMerchantStockSnStatExportVo.class);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			logger.info("STKMerchantStockController::exportMerchantStockSnStatByToMerchantCode return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		if(rpcResponse.getResult() != null){
			for(STKMerchantStockSnStatDTO statDto:rpcResponse.getResult()){
				if(statDto.getUnActiveDays().equals(90)){
					statDto.setUnActiveDayFlag("TH");
				}else if(statDto.getUnActiveDays().equals(180)){
					statDto.setUnActiveDayFlag("SI");
				}else if(statDto.getUnActiveDays().equals(270)){
					statDto.setUnActiveDayFlag("NI");
				}
			}
		}
		logger.info("STKMerchantStockController::exportMerchantStockSnStatByToMerchantCode end result:{}",rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	private STKMerchantStockSnStatExportVo exportMerchantStockSnStatConvertTo(STKMerchantStockSnStatDTO merchantStockSnStatDTO){
		STKMerchantStockSnStatExportVo merchantStockSnStatExportVo=new STKMerchantStockSnStatExportVo();
		BeanUtils.copyProperties(merchantStockSnStatDTO,merchantStockSnStatExportVo);
		return merchantStockSnStatExportVo;
	}

	@ApiOperation(value="导出库存设备明细",notes="导出库存设备明细")
	@PostMapping("exportMerchantStockSn")
	public ResultEntity<List<STKMerchantStockSnDTO>> exportMerchantStockSn(@RequestBody MerchantStockSnVO statVo,HttpServletResponse response) throws Exception {
		User user = userInfoHolder.getUser();
		logger.info("STKMerchantStockController::exportMerchantStockSn start statVo:{}",statVo);
		STKMerchantStockSnDTO dtoCondition = new STKMerchantStockSnDTO();
		dtoCondition.setActiveOrNot("N");
		if(statVo.getUnActiveDayFlag().equals("TH")){
			dtoCondition.setUnActiveDays(90);
		}else if(statVo.getUnActiveDayFlag().equals("SI")){
			dtoCondition.setUnActiveDays(180);
		}else if(statVo.getUnActiveDayFlag().equals("NI")){
			dtoCondition.setUnActiveDays(270);
		}
		dtoCondition.setDeviceType(statVo.getDeviceType());
		dtoCondition.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<STKMerchantStockSnDTO>> rpcResponse = stkMerchantStockRemote.exportMerchantStockSn(dtoCondition);
		List<STKMerchantStockSnDTO> merchantStockSnDTOS=rpcResponse.getResult();
		List<STKMerchantStockSnExportVo> merchantStockSnExportVos=merchantStockSnDTOS.stream().map(merchantStockSnDTO -> exportMerchantStockSnConvertTo(merchantStockSnDTO)).collect(Collectors.toList());
		ExcelReadAndWriteUtil.writeExcel(response,merchantStockSnExportVos,Constants.EXPORT_NAME_STK_MERCHANT_STOCK_SN,Constants.EXPORT_NAME_STK_MERCHANT_STOCK_SN, STKMerchantStockSnExportVo.class);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			logger.info("STKMerchantStockController::exportMerchantStockSn return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("STKMerchantStockController::exportMerchantStockSn end result:{}",rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	private STKMerchantStockSnExportVo exportMerchantStockSnConvertTo(STKMerchantStockSnDTO merchantStockSnDTO){
		STKMerchantStockSnExportVo merchantStockSnExportVo=new STKMerchantStockSnExportVo();
		BeanUtils.copyProperties(merchantStockSnDTO,merchantStockSnExportVo);
		return merchantStockSnExportVo;
	}



}
