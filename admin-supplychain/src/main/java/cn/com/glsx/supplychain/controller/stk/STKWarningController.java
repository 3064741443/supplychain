package cn.com.glsx.supplychain.controller.stk;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.jxc.dto.*;
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
 * @Title: STKWarningController.java
 * @Description: 预警
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/STKWarning",tags="库存管理 预警")
@RestController
@RequestMapping("STKWarning")
public class STKWarningController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private StkMerchantStockRemote stkMerchantStockRemote;
	
	@ApiOperation(value="库销比预警列表",notes="库销比预警列表")
	@PostMapping("pageMerchantWarningWaresale")
	public ResultEntity<RpcPagination<STKWarningWaresaleDTO>> pageMerchantWarningWaresale(@RequestBody WarningWaresaleVO saleVo){
		logger.info("STKWarningController::pageMerchantWarningWaresale start saleVo:{}",saleVo);
		RpcPagination<STKWarningWaresaleDTO> pagination = new RpcPagination<>();
		STKWarningWaresaleDTO dtoCondition = new STKWarningWaresaleDTO();
		dtoCondition.setChannelId(saleVo.getChannelId());
		dtoCondition.setMerchantCode(saleVo.getMerchantName());
		dtoCondition.setMerchantName(saleVo.getMerchantName());
		dtoCondition.setDeviceType(saleVo.getDeviceType());
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(saleVo.getPageNum());
		pagination.setPageSize(saleVo.getPageSize());
		RpcResponse<RpcPagination<STKWarningWaresaleDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantWarningWaresale(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningController::pageMerchantWarningWaresale return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::pageMerchantWarningWaresale end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="库销比预警列表导出",notes="库销比预警列表导出")
	@PostMapping("exportMerchantWarningWaresale")
	public ResultEntity<List<STKWarningWaresaleDTO>> exportMerchantWarningWaresale(@RequestBody WarningWaresaleVO saleVo, HttpServletResponse response) throws Exception {
		User user = userInfoHolder.getUser();
		logger.info("STKWarningController::exportMerchantWarningWaresale start saleVo:{}",saleVo);
		RpcPagination<STKWarningWaresaleDTO> pagination = new RpcPagination<>();
		STKWarningWaresaleDTO dtoCondition = new STKWarningWaresaleDTO();
		dtoCondition.setChannelId(saleVo.getChannelId());
		dtoCondition.setMerchantCode(saleVo.getMerchantName());
		dtoCondition.setMerchantName(saleVo.getMerchantName());
		dtoCondition.setDeviceType(saleVo.getDeviceType());
		dtoCondition.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<STKWarningWaresaleDTO>> rpcResponse = stkMerchantStockRemote.exportMerchantWarningWaresale(dtoCondition);
		List<STKWarningWaresaleDTO> stkWarningWaresaleDTOS= rpcResponse.getResult();
		List<STKWarningWaresaleExportVo> stkWarningWaresaleExportVos = stkWarningWaresaleDTOS.stream().map(stkWarningWaresaleDTO -> exportSTKWarningWaresaleConvertTo(stkWarningWaresaleDTO)).collect(Collectors.toList());
		ExcelReadAndWriteUtil.writeExcel(response, stkWarningWaresaleExportVos, Constants.EXPORT_NAME_STK_WARNING_WARESALE, Constants.EXPORT_NAME_STK_WARNING_WARESALE, STKWarningWaresaleExportVo.class);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			logger.info("STKWarningSetController::exportMerchantWarningWaresale return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("STKWarningSetController::exportMerchantWarningWaresale end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	private STKWarningWaresaleExportVo exportSTKWarningWaresaleConvertTo(STKWarningWaresaleDTO stkWarningWaresaleDTO){
		STKWarningWaresaleExportVo stkWarningWaresaleExportVo=new STKWarningWaresaleExportVo();
		BeanUtils.copyProperties(stkWarningWaresaleDTO,stkWarningWaresaleExportVo);
		return stkWarningWaresaleExportVo;
	}

	@ApiOperation(value="呆滞品预警-按照设备类型分类统计",notes="呆滞品预警-按照设备类型分类统计")
	@PostMapping("getMerchantWarningDeviceTypeAssume")
	public ResultEntity<STKWarningDevTypeAssumeDTO> getMerchantWarningDeviceTypeAssume(){
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		logger.info("STKProductConfigController::getMerchantWarningDeviceTypeAssume start userName:{}",userName);
		JXCMTBaseDTO dtoCondition = new JXCMTBaseDTO();
		dtoCondition.setConsumer(userName);
		RpcResponse<STKWarningDevTypeAssumeDTO> rpcResponse = stkMerchantStockRemote.getMerchantWarningDeviceTypeAssume(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::getMerchantWarningDeviceTypeAssume return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::getMerchantWarningDeviceTypeAssume end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="呆滞品预警-按照商户-物料-预警编码分类统计",notes="呆滞品预警-按照商户-物料-预警编码分类统计")
	@PostMapping("pageMerchantWarningMaterialAssume")
	public ResultEntity<RpcPagination<STKWarningMaterialAssumeDTO>> pageMerchantWarningMaterialAssume(@RequestBody WarningMaterialAssumeVO assumeVo){
		logger.info("STKProductConfigController::pageMerchantWarningMaterialAssume start assumeVo`:{}",assumeVo);
		RpcPagination<STKWarningMaterialAssumeDTO> pagination = new RpcPagination<>();
		STKWarningMaterialAssumeDTO dtoCondition = new STKWarningMaterialAssumeDTO();
		dtoCondition.setDeviceType(assumeVo.getDeviceType());
		dtoCondition.setMaterialCode(assumeVo.getMaterialName());
		dtoCondition.setMaterialName(assumeVo.getMaterialName());
		dtoCondition.setMerchantCode(assumeVo.getMerchantName());
		dtoCondition.setMerchantName(assumeVo.getMerchantName());
		pagination.setPageNum(assumeVo.getPageNum());
		pagination.setPageSize(assumeVo.getPageSize());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<STKWarningMaterialAssumeDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantWarningMaterialAssume(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::pageMerchantWarningMaterialAssume return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::pageMerchantWarningMaterialAssume end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="呆滞品设备明细列表",notes="呆滞品设备明细列表")
	@PostMapping("pageMerchantWarningDeviceSn")
	public ResultEntity<RpcPagination<STKWarningDevicesnDTO>> pageMerchantWarningDeviceSn(@RequestBody WarningDevicesnVO snVo){
		User user = userInfoHolder.getUser();
		logger.info("STKProductConfigController::pageMerchantWarningDeviceSn start snVo`:{}",snVo);
		RpcPagination<STKWarningDevicesnDTO> pagination = new RpcPagination<>();
		STKWarningDevicesnDTO dtoCondition = new STKWarningDevicesnDTO();
		dtoCondition.setToMerchantCode(snVo.getToMerchantName());
		dtoCondition.setToMerchantName(snVo.getToMerchantName());
		dtoCondition.setMaterialCode(snVo.getMaterialName());
		dtoCondition.setMaterialName(snVo.getMaterialName());
		dtoCondition.setWarningCode(snVo.getWarningCode());
		pagination.setPageNum(snVo.getPageNum());
		pagination.setPageSize(snVo.getPageSize());
		pagination.setCondition(dtoCondition);
		RpcResponse<RpcPagination<STKWarningDevicesnDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantWarningDeviceSn(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKWarningSetController::pageMerchantWarningDeviceSn return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKWarningSetController::pageMerchantWarningDeviceSn end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="呆滞品设备明细列表导出",notes="呆滞品设备明细列表导出")
	@PostMapping("exportMerchantWarningDeviceSn")
	public ResultEntity<List<STKWarningDevicesnDTO>> exportMerchantWarningDeviceSn(@RequestBody WarningDevicesnVO snVo,HttpServletResponse response) throws Exception {
		User user = userInfoHolder.getUser();
		logger.info("STKWarningController::exportMerchantWarningDeviceSn start snVo`:{}",snVo);
		STKWarningDevicesnDTO dtoCondition = new STKWarningDevicesnDTO();
		dtoCondition.setToMerchantCode(snVo.getToMerchantName());
		dtoCondition.setToMerchantName(snVo.getToMerchantName());
		dtoCondition.setMaterialCode(snVo.getMaterialName());
		dtoCondition.setMaterialName(snVo.getMaterialName());
		dtoCondition.setWarningCode(snVo.getWarningCode());
		dtoCondition.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<STKWarningDevicesnDTO>> rpcResponse = stkMerchantStockRemote.exportMerchantWarningDeviceSn(dtoCondition);
		List<STKWarningDevicesnDTO> warningDevicesnDTOS=rpcResponse.getResult();
		List<STKWarningDevicesnExportVo>  stkWarningDevicesnExportVos = warningDevicesnDTOS.stream().map(warningDevicesnDTO -> exportSTKWarningDevicesnConvertTo(warningDevicesnDTO)).collect(Collectors.toList());
		ExcelReadAndWriteUtil.writeExcel(response, stkWarningDevicesnExportVos, Constants.EXPORT_NAME_STK_WARNING_DEVICESN, Constants.EXPORT_NAME_STK_WARNING_DEVICESN, STKWarningDevicesnExportVo.class);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			logger.info("STKWarningController::exportMerchantWarningDeviceSn return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("STKWarningController::exportMerchantWarningDeviceSn end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

  private STKWarningDevicesnExportVo exportSTKWarningDevicesnConvertTo(STKWarningDevicesnDTO warningDevicesnDTO){
	  STKWarningDevicesnExportVo stkWarningDevicesnExportVo=new STKWarningDevicesnExportVo();
	  BeanUtils.copyProperties(warningDevicesnDTO,stkWarningDevicesnExportVo);
	  return stkWarningDevicesnExportVo;
  }
}
