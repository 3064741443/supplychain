package cn.com.glsx.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResponseEntity;
import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.model.jxc.*;
import cn.com.glsx.supplychain.util.ExcelReadAndWriteUtil;
import cn.com.glsx.supplychain.util.ExcelXlsxStreamingViewUtil;
import cn.com.glsx.supplychain.util.ImportedResult;
import cn.com.glsx.supplychain.util.SupplychainUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Title: JXCSPOrderController.java
 * @Description: 820改版  供应链分配订单模块
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/JXCSPOrder",tags="820改版  供应链分配订单模块")
@RestController
@RequestMapping("JXCSPOrder")
public class JXCSPOrderController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private JxcOrderRemote jxcOrderRemote;
	@Autowired
	private ExcelXlsxStreamingViewUtil excelXlsxStreamingViewUtil;
	
	@ApiOperation(value="获取子订单列表",notes="获取子订单列表")
	@PostMapping("pageSpOrder")
	public ResultEntity<RpcPagination<JXCMTSpMerchantOrderDTO>> pageSpOrder(@RequestBody JXCMTSpOrderQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::pageSpOrder start user:{},queryVo:{}",user,queryVo);
		RpcPagination<JXCMTSpMerchantOrderDTO> pagination = new RpcPagination<>();
		JXCMTSpMerchantOrderDTO conditon = new JXCMTSpMerchantOrderDTO();
		conditon.setMerchantOrder(queryVo.getMerchantOrder());
		conditon.setDispatchOrderCode(queryVo.getDispatchOrderCode());
		conditon.setMerchantCode(queryVo.getMerchantName());
		conditon.setMerchantName(queryVo.getMerchantName());
		conditon.setMaterialCode(queryVo.getMaterialName());
		conditon.setMaterialName(queryVo.getMaterialName());
		conditon.setStatus(queryVo.getStatus());
		conditon.setChannelId(queryVo.getChannelId());
		conditon.setOrderTimeStart(queryVo.getCheckTimeStart());
		conditon.setOrderTimeEnd(queryVo.getCheckTimeEnd());
		pagination.setCondition(conditon);
		pagination.setPageNum(queryVo.getPageNum());
		pagination.setPageSize(queryVo.getPageSize());
		RpcResponse<RpcPagination<JXCMTSpMerchantOrderDTO>> rpcResponse = jxcOrderRemote.pageBsMerchantOrderBSP(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::pageSpOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::pageSpOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取子订单车辆审核列表",notes="获取子订单车辆审核列表")
	@PostMapping("listSpOrderVehicle")
	public ResultEntity<List<JXCMTBsMerchantOrderVehicleDTO>> listSpOrderVehicle(@RequestBody JXCMTSpOrderVehicleQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::listSpOrderVehicle start user:{},queryVo:{}",user,queryVo);
		JXCMTSpOrderVehicleQueryDTO dtoCondition = new JXCMTSpOrderVehicleQueryDTO();
		dtoCondition.setListMerchantOrder(queryVo.getListMerchantOrder());
		RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> rpcResponse = jxcOrderRemote.listSpOrderVehicle(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::listSpOrderVehicle return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::listSpOrderVehicle end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单扫码模式分配发货",notes="子订单模式分配发货")
	@PostMapping("dispatchSpOrderScanY")
	public ResultEntity<Integer> dispatchSpOrderScanY(@RequestBody JXCMTSpOrderDispatchScanYVO scanVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::dispatchSpOrderScanY start user:{},scanVo:{}",user,scanVo);
		JXCMTSpOrderDispatchScanYDTO dtoCondition = new JXCMTSpOrderDispatchScanYDTO();
		dtoCondition.setAddress(scanVo.getAddress());
		dtoCondition.setConsumer(user != null ? user.getRealname():"admin");
		dtoCondition.setContacts(scanVo.getContacts());
		dtoCondition.setDeviceId(scanVo.getDeviceId());
		dtoCondition.setDeviceName(scanVo.getDeviceName());
		dtoCondition.setMaterialCode(scanVo.getMaterialCode());
		dtoCondition.setMaterialName(scanVo.getMaterialName());
		dtoCondition.setMobile(scanVo.getMobile());
		dtoCondition.setPackageOne(scanVo.getPackageOne());
		dtoCondition.setRemark(scanVo.getRemark());
		dtoCondition.setSendMerchantName(scanVo.getSendMerchantName());
		dtoCondition.setSendMerchantNo(scanVo.getSendMerchantNo());
		List<JXCMTMerchantOrderWarehouseDTO> listMerchantOrderWarehouseDto = new ArrayList<>();
		if(scanVo.getListOrderWarehouseVo() != null){
			JXCMTMerchantOrderWarehouseDTO dto = null;
			for(JXCMTSpOrderWarehouseVO vo:scanVo.getListOrderWarehouseVo()){
				dto = new JXCMTMerchantOrderWarehouseDTO();
				dto.setWarehouseId(vo.getWarehouseId());
				dto.setMerchantOrder(vo.getMerchantOrder());
				listMerchantOrderWarehouseDto.add(dto);
			}
		}
		dtoCondition.setListMerchantOrderWarehouseDto(listMerchantOrderWarehouseDto);
		RpcResponse<Integer> rpcResponse = null;
		if(scanVo.getBatchOption().equals("Y")){
			rpcResponse = jxcOrderRemote.dispatchSpOrderScanYBatch(dtoCondition);
		}else if(scanVo.getBatchOption().equals("N")){
			rpcResponse = jxcOrderRemote.dispatchSpOrderScanYSingle(dtoCondition);
		}else{
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
		}
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::dispatchSpOrderScanY return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::dispatchSpOrderScanY end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单不扫码模式分配发货",notes="子订单模式分配发货")
	@PostMapping("dispatchSpOrderScanN")
	public ResultEntity<Integer> dispatchSpOrderScanN(@RequestBody JXCMTSpOrderDispatchScanNVO scanVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::dispatchSpOrderScanN start user:{},scanVo:{}",user,scanVo);
		JXCMTSpOrderDispatchScanNDTO dtoCondition = new JXCMTSpOrderDispatchScanNDTO();
		dtoCondition.setAddress(scanVo.getAddress());
		dtoCondition.setConsumer(user != null ? user.getRealname():"admin");
		dtoCondition.setContacts(scanVo.getContacts());
		dtoCondition.setMaterialCode(scanVo.getMaterialCode());
		dtoCondition.setMaterialName(scanVo.getMaterialName());
		dtoCondition.setMobile(scanVo.getMobile());
		dtoCondition.setRemark(scanVo.getRemark());
		dtoCondition.setSendMerchantName(scanVo.getSendMerchantName());
		dtoCondition.setSendMerchantNo(scanVo.getSendMerchantNo());
		List<JXCMTMerchantOrderWarehouseDTO> listMerchantOrderWarehouseDto = new ArrayList<>();
		if(scanVo.getListOrderWarehouseVo() != null){
			JXCMTMerchantOrderWarehouseDTO dto = null;
			for(JXCMTSpOrderWarehouseVO vo:scanVo.getListOrderWarehouseVo()){
				dto = new JXCMTMerchantOrderWarehouseDTO();
				dto.setWarehouseId(vo.getWarehouseId());
				dto.setMerchantOrder(vo.getMerchantOrder());
				listMerchantOrderWarehouseDto.add(dto);
			}
		}
		dtoCondition.setListMerchantOrderWarehouseDto(listMerchantOrderWarehouseDto);
		RpcResponse<Integer> rpcResponse = null;
		if(scanVo.getBatchOption().equals("Y")){
			rpcResponse = jxcOrderRemote.dispatchSpOrderScanNBatch(dtoCondition);
		}else if(scanVo.getBatchOption().equals("N")){
			rpcResponse = jxcOrderRemote.dispatchSpOrderScanNSingle(dtoCondition);
		}else{
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(),JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
		}
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::dispatchSpOrderScanN return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::dispatchSpOrderScanN end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单不扫码模式直接发货",notes="子订单不扫码模式直接发货")
	@PostMapping("dispatchSpOrderDirect")
	public ResultEntity<Integer> dispatchSpOrderDirect(@RequestBody JXCMTSpOrderDispatchDirectVO directVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::dispatchSpOrderDirect start user:{},directVo:{}",user,directVo);
		JXCMTSpOrderDispatchDirectDTO dtoCondition = new JXCMTSpOrderDispatchDirectDTO();
		dtoCondition.setConsumer(user != null ? user.getRealname():"admin");
		dtoCondition.setLogisticsCompany(directVo.getLogisticsCompany());
		dtoCondition.setLogisticsNum(directVo.getLogisticsNum());
		dtoCondition.setMerchantOrder(directVo.getMerchantOrder());
		dtoCondition.setSendNums(directVo.getSendNums());
		dtoCondition.setSendTime(directVo.getSendTime());
		dtoCondition.setWarehouseId(directVo.getWarehouseId());
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.dispatchSpOrderDirect(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::dispatchSpOrderDirect return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::dispatchSpOrderDirect end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单不扫码模式直接发货批量导入校验",notes="子订单不扫码模式直接发货批量导入校验")
	@PostMapping("importSpOrderDirectCheck")
	public ResponseEntity<ImportedResult> importSpOrderDirectCheck(@RequestParam(value = "file") MultipartFile files){
		
		
		return null;
	}
	
	@ApiOperation(value="子订单不扫码模式直接发货批量导入",notes="子订单不扫码模式直接发货批量导入")
	@PostMapping("importSpOrderDirect")
	public ResponseEntity<Integer> importSpOrderDirect(@RequestBody JXCMTSpOrderDispatchDirectVO directVo){
		return null;
	}
	
	@ApiOperation(value="导出订单列表",notes="导出订单列表")
	@PostMapping("exportSpOrder")
	public ModelAndView exportSpOrder(@RequestBody JXCMTSpOrderQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::exportSpOrder start user:{},queryVo:{}",user,queryVo);
		JXCMTSpMerchantOrderDTO conditon = new JXCMTSpMerchantOrderDTO();
		conditon.setMerchantOrder(queryVo.getMerchantOrder());
		conditon.setDispatchOrderCode(queryVo.getDispatchOrderCode());
		conditon.setMerchantCode(queryVo.getMerchantName());
		conditon.setMerchantName(queryVo.getMerchantName());
		conditon.setMaterialCode(queryVo.getMaterialName());
		conditon.setMaterialName(queryVo.getMaterialName());
		conditon.setStatus(queryVo.getStatus());
		conditon.setChannelId(queryVo.getChannelId());
		conditon.setOrderTimeStart(queryVo.getCheckTimeStart());
		conditon.setOrderTimeEnd(queryVo.getCheckTimeEnd());
		conditon.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<JXCMTBsMerchantOrderExportBspDTO>> rpcResponse = jxcOrderRemote.exportBsMerchantOrderExportBSP(conditon);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::exportSpOrder return"
                    + errCodeEnum.getDescrible());
            return new ModelAndView(errCodeEnum.getDescrible());
        }
		List<JXCMTBsMerchantOrderExportBspDTO> listExportDto = rpcResponse.getResult();
		List<JXCMTBsMerchantOrderBspExcelVo> listExportVo = new ArrayList<>();
		if(null != listExportDto){
			for(JXCMTBsMerchantOrderExportBspDTO dto:listExportDto){
				listExportVo.add(convertMerchantOrderBssDto2Vo(dto));
			}
		}
		List<Object> merchantOrderList = new ArrayList<Object>();
		merchantOrderList.addAll(listExportVo);
		Map<String, Object> map = new HashMap<>();
		map.put("objs", merchantOrderList);
		map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_BS_MERCHANT_ORDER_BSP);
        map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSP);
        map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_BS_MERCHANT_ORDER_BSP); 
        return new ModelAndView(excelXlsxStreamingViewUtil,map);
	}
	
	private JXCMTBsMerchantOrderBspExcelVo convertMerchantOrderBssDto2Vo(JXCMTBsMerchantOrderExportBspDTO dto){
		JXCMTBsMerchantOrderBspExcelVo vo = new JXCMTBsMerchantOrderBspExcelVo();
		vo.setMerchantName(StringUtils.isEmpty(dto.getMerchantName())?"":dto.getMerchantName());
		vo.setChannelName(StringUtils.isEmpty(dto.getChannelName())?"":dto.getChannelName());
		vo.setMaterialCode(StringUtils.isEmpty(dto.getMaterialCode())?"":dto.getMaterialCode());
		vo.setMaterialName(StringUtils.isEmpty(dto.getMaterialName())?"":dto.getMaterialName());
		vo.setMdeviceTypeName(StringUtils.isEmpty(dto.getMdeviceTypeName())?"":dto.getMdeviceTypeName());
		vo.setDispatchOrderCode(StringUtils.isEmpty(dto.getDispatchOrderCode())?"":dto.getDispatchOrderCode());
		vo.setDispatchOrderStatus(StringUtils.isEmpty(dto.getDispatchOrderStatus())?"":dto.getDispatchOrderStatus());
		vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark())?"":dto.getCheckRemark());
		vo.setSubjectName(StringUtils.isEmpty(dto.getSubjectName())?"":dto.getSubjectName());
		vo.setBsCheckQuantity((dto.getBsCheckQuantity()==null)?0:dto.getBsCheckQuantity());
		vo.setBsSendQuantity((dto.getBsSendQuantity()==null)?0:dto.getBsSendQuantity());
		vo.setBsOweQuantity(vo.getBsCheckQuantity() - vo.getBsSendQuantity());
		String motorcycleDesc = "";
		if(!StringUtils.isEmpty(dto.getBsParentBrandName()) 
				|| !StringUtils.isEmpty(dto.getBsSubBrandName())
				|| !StringUtils.isEmpty(dto.getBsAudiName())
				|| !StringUtils.isEmpty(dto.getBsMotorcycle())){
			motorcycleDesc = "父品牌:" + (StringUtils.isEmpty(dto.getBsParentBrandName())?"":dto.getBsParentBrandName()) + "\n";
			motorcycleDesc = motorcycleDesc + "子品牌:" + (StringUtils.isEmpty(dto.getBsSubBrandName())?"":dto.getBsSubBrandName()) + "\n";
			motorcycleDesc = motorcycleDesc + "车系:" + (StringUtils.isEmpty(dto.getBsAudiName())?"":dto.getBsAudiName()) + "\n";
			motorcycleDesc = motorcycleDesc + "车型:" + (StringUtils.isEmpty(dto.getBsMotorcycle())?"":dto.getBsMotorcycle());
			vo.setMotorcycleDesc(motorcycleDesc);
		}else{
			vo.setMotorcycleDesc("");
		}
		vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark())?"":dto.getCheckRemark());
		vo.setWarehouseName(StringUtils.isEmpty(dto.getWarehouseName())?"":dto.getWarehouseName());
		vo.setAddress(StringUtils.isEmpty(dto.getAddress())?"":dto.getAddress());
		vo.setContacts(StringUtils.isEmpty(dto.getContacts())?"":dto.getContacts());
		vo.setMobile(StringUtils.isEmpty(dto.getMobile())?"":dto.getMobile());
		vo.setOrderNumber(StringUtils.isEmpty(dto.getOrderNumber())?"":dto.getOrderNumber());
		vo.setCompany(StringUtils.isEmpty(dto.getCompany())?"":dto.getCompany());
		vo.setShipmentsQuantity(dto.getShipmentsQuantity()==null?0:dto.getShipmentsQuantity());
		vo.setCheckBy(dto.getCheckBy()==null?"":dto.getCheckBy());
		vo.setCheckTime(SupplychainUtils.getStrYMDHMSFromDate(dto.getCheckTime()));
		vo.setMerchantOrderNum(StringUtils.isEmpty(dto.getMerchantOrderNum())?"":dto.getMerchantOrderNum());
		vo.setFastenConfigDesc(StringUtils.isEmpty(dto.getFastenConfigDesc())?"":dto.getFastenConfigDesc());
		vo.setOptionConfigDesc(StringUtils.isEmpty(dto.getOptionConfigDesc())?"":dto.getOptionConfigDesc());
		return vo;
	}
	
	@ApiOperation(value="子订单驳回",notes="子订单驳回")
	@PostMapping("rebackSpOrder")
	public ResultEntity<Integer> rebackSpOrder(@RequestBody JXCMTBsOrderRebackVO rebackVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::rebackSpOrder start user:{},rebackVo:{}",user,rebackVo);
		JXCMTBsMerchantOrderRebackDTO dtoCondition = new JXCMTBsMerchantOrderRebackDTO();
		dtoCondition.setMerchantOrder(rebackVo.getMerchantOrder());
		dtoCondition.setRebackReason(rebackVo.getRebackReason());
		dtoCondition.setConsumer(user!=null?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.rebackSpOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::rebackSpOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::rebackSpOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单撤回",notes="子订单撤回")
	@PostMapping("recallSpOrder")
	public ResultEntity<Integer> recallSpOrder(@RequestBody JXCMTBsOrderRecallVO recallVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::recallSpOrder start user:{},recallVo:{}",user,recallVo);
		JXCMTBsMerchantOrderRecallDTO dtoCondition = new JXCMTBsMerchantOrderRecallDTO();
		dtoCondition.setMerchantOrder(recallVo.getMerchantOrder());
		dtoCondition.setRecallReason(recallVo.getRecallReason());
		dtoCondition.setConsumer(user!=null?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.recallSpOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::recallSpOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::recallSpOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单提前结束 ",notes="子订单提前结束")
	@PostMapping("finishSpOrder")
	public ResultEntity<Integer> finishSpOrder(@RequestBody JXCMTBsOrderFinishVO finishVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::finishSpOrder start user:{},finishVo:{}",user,finishVo);
		JXCMTBsMerchantOrderFinishDTO dtoCondition = new JXCMTBsMerchantOrderFinishDTO();
		dtoCondition.setMerchantOrder(finishVo.getMerchantOrder());
		dtoCondition.setFinishReason(finishVo.getFinishReason());
		dtoCondition.setConsumer(user!=null?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.finishSpOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::finishSpOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCSPOrderController::finishSpOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="发货单列表",notes="发货单列表")
	@PostMapping("pageDispatchOrder")
	public ResultEntity<RpcPagination<JXCMTOrderInfoDTO>> pageDispatchOrder(@RequestBody JXCMTOrderInfoQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::pageDispatchOrder start user:{},queryVo:{}",user,queryVo);
		RpcPagination<JXCMTOrderInfoDTO> pagination = new RpcPagination<>();
		JXCMTOrderInfoDTO dtoConditon = new JXCMTOrderInfoDTO();
		dtoConditon.setDeviceTypeId(queryVo.getDeviceTypeId());
		dtoConditon.setDispatchOrderCode(queryVo.getDispatchOrderCode());
		dtoConditon.setStatus(queryVo.getStatus());
		dtoConditon.setMerchantOrder(queryVo.getMerchantOrder());
		dtoConditon.setSendMerchantNo(queryVo.getSendMerchantNo());
		dtoConditon.setWarehouseId(queryVo.getWarehouseId());
		dtoConditon.setBsQueryType("W");
		dtoConditon.setStartSendTime(queryVo.getStartSendTime());
		dtoConditon.setEndSendTime(queryVo.getEndSendTime());
		pagination.setCondition(dtoConditon);
		pagination.setPageNum(queryVo.getPageNum());
		pagination.setPageSize(queryVo.getPageSize());
		RpcResponse<RpcPagination<JXCMTOrderInfoDTO>> rpcResponse = jxcOrderRemote.pageDispatchOrder(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::pageDispatchOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("JXCSPOrderController::pageDispatchOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="发货单列表导出",notes="发货单列表导出")
	@PostMapping("exportDispatchOrder")
	public ResultEntity<List<JXCMTOrderInfoDTO>> exportDispatchOrder(@RequestBody JXCMTOrderInfoQueryVO queryVo, HttpServletResponse response) throws Exception {
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::exportDispatchOrder start user:{},queryVo:{}",user,queryVo);
		JXCMTOrderInfoDTO dtoConditon = new JXCMTOrderInfoDTO();
		dtoConditon.setDeviceTypeId(queryVo.getDeviceTypeId());
		dtoConditon.setDispatchOrderCode(queryVo.getDispatchOrderCode());
		dtoConditon.setStatus(queryVo.getStatus());
		dtoConditon.setMerchantOrder(queryVo.getMerchantOrder());
		dtoConditon.setSendMerchantNo(queryVo.getSendMerchantNo());
		dtoConditon.setWarehouseId(queryVo.getWarehouseId());
		dtoConditon.setBsQueryType(null);
		dtoConditon.setStartSendTime(queryVo.getStartSendTime());
		dtoConditon.setEndSendTime(queryVo.getEndSendTime());
		dtoConditon.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<JXCMTOrderInfoDTO>> rpcResponse = jxcOrderRemote.exportDispatchOrder(dtoConditon);
		List<JXCMTOrderInfoDTO> orderInfoDTOList=rpcResponse.getResult();
		List<JXCMTOrderInfoExportVo> orderInfoExportVos=orderInfoDTOList.stream().map(orderInfoDTO -> exportOrderInfoConvertTo(orderInfoDTO)).collect(Collectors.toList());
		Integer numberNo=1;
		for(JXCMTOrderInfoExportVo orderInfoExportVo:orderInfoExportVos){
			orderInfoExportVo.setNumber(numberNo++);
		}
		ExcelReadAndWriteUtil.writeExcelWithAuto(response,orderInfoExportVos,Constants.EXPORT_NAME_INVOICE_MERCHANT_ORDER_BSP,Constants.EXPORT_NAME_INVOICE_MERCHANT_ORDER_BSP,JXCMTOrderInfoExportVo.class);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			logger.info("JXCSPOrderController::exportDispatchOrder return"
					+ errCodeEnum.getDescrible());
			return ResultEntity.error(errCodeEnum.getCode(),
					errCodeEnum.getDescrible());
		}
		logger.info("JXCSPOrderController::exportDispatchOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	private  JXCMTOrderInfoExportVo exportOrderInfoConvertTo(JXCMTOrderInfoDTO orderInfoDTO){
		JXCMTOrderInfoExportVo orderInfoExportVo=new JXCMTOrderInfoExportVo();
		orderInfoDTO.setBsParentBrandName(StringUtils.isEmpty(orderInfoDTO.getBsParentBrandName())?"":orderInfoDTO.getBsParentBrandName());
		orderInfoDTO.setBsSubBrandName(StringUtils.isEmpty(orderInfoDTO.getBsSubBrandName())?"":orderInfoDTO.getBsSubBrandName());
		orderInfoDTO.setBsAudiName(StringUtils.isEmpty(orderInfoDTO.getBsAudiName())?"":orderInfoDTO.getBsAudiName());
		orderInfoDTO.setBsMotorcycle(StringUtils.isEmpty(orderInfoDTO.getBsMotorcycle())?"":orderInfoDTO.getBsMotorcycle());
		orderInfoDTO.setBsRemark(StringUtils.isEmpty(orderInfoDTO.getBsRemark())?"":orderInfoDTO.getBsRemark());
		BeanUtils.copyProperties(orderInfoDTO,orderInfoExportVo);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("品牌:" + orderInfoDTO.getBsParentBrandName()+ "\n");
		stringBuffer.append("子品牌:" + orderInfoDTO.getBsSubBrandName()+ "\n");
		stringBuffer.append("车系:" + orderInfoDTO.getBsAudiName() + "\n");
		stringBuffer.append("车型:" + orderInfoDTO.getBsMotorcycle() + "\n");
		stringBuffer.append("车价/颜色:"+orderInfoDTO.getBsRemark());
		orderInfoExportVo.setMotorcycleDesc(stringBuffer.toString());
		if("UF".equals(orderInfoExportVo.getStatus())){
			orderInfoExportVo.setStatus("未完成");
		}else if("OV".equals(orderInfoExportVo.getStatus())){
			orderInfoExportVo.setStatus("完成");
		}else if("CL".equals(orderInfoExportVo.getStatus())){
			orderInfoExportVo.setStatus("已取消");
		}
		String  createdDateStr=orderInfoExportVo.getCreatedTime();
		createdDateStr=createdDateStr.substring(0,createdDateStr.length()-2);
		orderInfoExportVo.setCreatedTime(createdDateStr);

		StringBuffer logisticStringBuffer = new StringBuffer();
		if(!org.springframework.util.StringUtils.isEmpty(orderInfoDTO.getLogisticsDto()) &&orderInfoDTO.getLogisticsDto().size()>0) {
			for (JXCMTBsLogisticsDTO logisticDto : orderInfoDTO.getLogisticsDto()) {
				logisticStringBuffer.append("物流单号:"+(StringUtils.isEmpty(logisticDto.getOrderNumber())?"":logisticDto.getOrderNumber()));
				logisticStringBuffer.append(" ");
				logisticStringBuffer.append("物流公司:"+(StringUtils.isEmpty(logisticDto.getCompany())?"":logisticDto.getCompany()));
				logisticStringBuffer.append(" ");
				logisticStringBuffer.append("发货数量:"+(org.springframework.util.StringUtils.isEmpty(logisticDto.getShipmentsQuantity())?0:logisticDto.getShipmentsQuantity()));
				logisticStringBuffer.append(" ");
				logisticStringBuffer.append("发货时间:"+(StringUtils.isEmpty(logisticDto.getSendTime())?"":logisticDto.getSendTime())+"\n");
			}
			orderInfoExportVo.setLogisticDesc(logisticStringBuffer.toString());
		}
		return orderInfoExportVo;
	}
	
	@ApiOperation(value="发货单明细列表",notes="发货单明细列表")
	@PostMapping("pageDispatchOrderDetail")
	public ResultEntity<RpcPagination<JXCMTOrderInfoDetailDTO>> pageDispatchOrderDetail(@RequestBody JXCMTOrderInfoDetailGetVO getVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCSPOrderController::pageDispatchOrderDetail start user:{},getVo:{}",user,getVo);
		JXCMTOrderInfoDetailDTO dtoCondition = new JXCMTOrderInfoDetailDTO();
		dtoCondition.setDispatchOrderCode(getVo.getDispatchOrderCode());
		dtoCondition.setPageNo(getVo.getPageNum());
		dtoCondition.setPageSize(getVo.getPageSize());
		RpcResponse<RpcPagination<JXCMTOrderInfoDetailDTO>> rpcResponse = jxcOrderRemote.pageDispatchOrderDetail(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("JXCSPOrderController::pageDispatchOrderDetail return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("JXCSPOrderController::pageDispatchOrderDetail end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="下载发货单",notes="下载发货单")
	@PostMapping("downloadDispatchOrder")
	public ModelAndView downloadDispatchOrder(@RequestBody JXCMTOrderInfoDownloadVO downloadVo){
		return null;
	}
}
