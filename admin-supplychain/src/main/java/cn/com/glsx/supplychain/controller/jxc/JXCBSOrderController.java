package cn.com.glsx.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.config.UploadProperty;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.jxc.remote.JxcProductRemote;
import cn.com.glsx.supplychain.model.jxc.*;
import cn.com.glsx.supplychain.util.ExcelXlsxStreamingViewUtil;
import cn.com.glsx.supplychain.vo.FileUploadVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: JXCBSOrderController.java
 * @Description: 820改版  商务审核订单模块
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/JXCBSOrder",tags="820改版  商务审核订单模块")
@RestController
@RequestMapping("JXCBSOrder")
public class JXCBSOrderController {
	private static final SimpleDateFormat FMT = new SimpleDateFormat("yyMMdd");
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private JxcOrderRemote jxcOrderRemote;
	@Autowired
	private JxcProductRemote jxcProductRemote;
	@Autowired
	private UploadProperty uploadProperty;
	@Autowired
	private ExcelXlsxStreamingViewUtil excelXlsxStreamingViewUtil;
	
	
	@ApiOperation(value="获取子订单列表",notes="获取子订单列表")
	@PostMapping("pageBsOrder")
	public ResultEntity<RpcPagination<JXCMTBsMerchantOrderDTO>> pageBsOrder(@RequestBody JXCMTBsOrderQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::pageBsOrder start user:{},queryVo:{}",user,queryVo);
		RpcPagination<JXCMTBsMerchantOrderDTO> pagination = new RpcPagination<>();
		JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
		dtoCondition.setMerchantOrder(queryVo.getMerchantOrder());
		dtoCondition.setMoOrderCode(queryVo.getMerchantOrder());
		dtoCondition.setStatus(queryVo.getStatus());
		dtoCondition.setSignStatus(queryVo.getSignStatus());
		dtoCondition.setChannelId(queryVo.getChannelId());
		dtoCondition.setOrderTimeStart(queryVo.getOrderTimeStart());
		dtoCondition.setOrderTimeEnd(queryVo.getOrderTimeEnd());
		dtoCondition.setMerchantName(queryVo.getMerchantName());
		dtoCondition.setMerchantCode(queryVo.getMerchantName());
		dtoCondition.setProductTypeId(queryVo.getProductTypeId());
		dtoCondition.setProductCode(queryVo.getProductName());
		dtoCondition.setProductName(queryVo.getProductName());
		dtoCondition.setMaterialCode(queryVo.getMaterialName());
		dtoCondition.setMaterialName(queryVo.getMaterialName());
		dtoCondition.setCheckTimeStart(queryVo.getCheckTimeStart());
		dtoCondition.setCheckTimeEnd(queryVo.getCheckTimeEnd());
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(queryVo.getPageNum());
		pagination.setPageSize(queryVo.getPageSize());
		RpcResponse<RpcPagination<JXCMTBsMerchantOrderDTO>> rpcResponse = jxcOrderRemote.pageBsMerchantOrderBSS(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::pageBsOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCBSOrderController::pageBsOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="获取子订单详情",notes="获取子订单详情")
	@PostMapping("getBsOrder")
	public ResultEntity<JXCMTBsMerchantOrderDetailDTO> getBsOrder(@RequestBody JXCMTBsOrderDetailGetVO getVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::getBsOrder start user:{},getVo:{}",user,getVo);
		JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
		dtoCondition.setMerchantOrder(getVo.getMerchantOrder());
		RpcResponse<JXCMTBsMerchantOrderDetailDTO> rpcResponse = jxcOrderRemote.getBsOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::getBsOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCBSOrderController::getBsOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单驳回",notes="子订单驳回")
	@PostMapping("rebackBsOrder")
	public ResultEntity<Integer> rebackBsOrder(@RequestBody JXCMTBsOrderRebackVO rebackVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::rebackBsOrder start user:{},rebackVo:{}",user,rebackVo);
		JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
		dtoCondition.setMerchantOrder(rebackVo.getMerchantOrder());
		dtoCondition.setRebackReason(rebackVo.getRebackReason());
		dtoCondition.setConsumer(user!=null?user.getRealname():"admin");
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.rebackBsOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::rebackBsOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCBSOrderController::rebackBsOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单审核",notes="子订单审核")
	@PostMapping("checkBsOrder")
	public ResultEntity<Integer> checkBsOrder(@RequestBody JXCMTBsOrderCheckVO checkVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::checkBsOrder start user:{},checkVo:{}",user,checkVo);
		JXCMTBsOrderCheckDTO dtoCondition = new JXCMTBsOrderCheckDTO();
		dtoCondition.setCheckMaterialCode(checkVo.getCheckMaterialCode());
		dtoCondition.setCheckMaterialName(checkVo.getCheckMaterialName());
		dtoCondition.setCheckRemark(checkVo.getCheckRemark());
		dtoCondition.setConsumer(user!=null?user.getRealname():"admin");
		dtoCondition.setInsure(checkVo.getInsure());
		dtoCondition.setMerchantOrder(checkVo.getMerchantOrder());
		dtoCondition.setModelDevice(checkVo.getModelDevice());
		dtoCondition.setSubjectId(checkVo.getSubjectId());
		dtoCondition.setTotalCheck(checkVo.getTotalCheck());
		dtoCondition.setUrlDispatchBills(checkVo.getUrlDispatchBills());
		List<JXCMTBsMerchantOrderVehicleDTO> listOrderVehicleDto = new ArrayList<>();	
		if(checkVo.getListOrderVehicleDto() != null && !checkVo.getListOrderVehicleDto().isEmpty()){
			JXCMTBsMerchantOrderVehicleDTO dto = null;
			if(checkVo.getListOrderVehicleDto().size() == 1 &&
					(checkVo.getListOrderVehicleDto().get(0).getBsCheckQuantity() == null || 
					checkVo.getListOrderVehicleDto().get(0).getBsCheckQuantity() == 0)){
				dto = new JXCMTBsMerchantOrderVehicleDTO();
				dto.setId(checkVo.getListOrderVehicleDto().get(0).getId());
				dto.setBsCheckQuantity(checkVo.getTotalCheck());
				listOrderVehicleDto.add(dto);
			}else{
				for(JXCMTBsMerchantOrderVehicleVO vo:checkVo.getListOrderVehicleDto()){
					dto = new JXCMTBsMerchantOrderVehicleDTO();
					dto.setId(vo.getId());
					dto.setBsCheckQuantity(vo.getBsCheckQuantity());
					listOrderVehicleDto.add(dto);
				}
			}
			dtoCondition.setListOrderVehicleDto(listOrderVehicleDto);
		}
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.checkBsOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::checkBsOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCBSOrderController::checkBsOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="子订单修改",notes="子订单修改")
	@PostMapping("updateBsOrder")
	public ResultEntity<Integer> updateBsOrder(@RequestBody JXCMTBsOrderCheckVO checkVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::updateBsOrder start user:{},checkVo:{}",user,checkVo);
		JXCMTBsOrderCheckDTO dtoCondition = new JXCMTBsOrderCheckDTO();
		dtoCondition.setCheckMaterialCode(checkVo.getCheckMaterialCode());
		dtoCondition.setCheckMaterialName(checkVo.getCheckMaterialName());
		dtoCondition.setCheckRemark(checkVo.getCheckRemark());
		dtoCondition.setConsumer(user!=null?user.getRealname():"admin");
		dtoCondition.setInsure(checkVo.getInsure());
		dtoCondition.setMerchantOrder(checkVo.getMerchantOrder());
		dtoCondition.setModelDevice(checkVo.getModelDevice());
		dtoCondition.setSubjectId(checkVo.getSubjectId());
		dtoCondition.setTotalCheck(checkVo.getTotalCheck());
		dtoCondition.setUrlDispatchBills(checkVo.getUrlDispatchBills());
		List<JXCMTBsMerchantOrderVehicleDTO> listOrderVehicleDto = new ArrayList<>();	
		if(checkVo.getListOrderVehicleDto() != null && !checkVo.getListOrderVehicleDto().isEmpty()){
			JXCMTBsMerchantOrderVehicleDTO dto = null;
			if(checkVo.getListOrderVehicleDto().size() == 1 &&
					(checkVo.getListOrderVehicleDto().get(0).getBsCheckQuantity() == null || 
					checkVo.getListOrderVehicleDto().get(0).getBsCheckQuantity() == 0)){
				dto = new JXCMTBsMerchantOrderVehicleDTO();
				dto.setId(checkVo.getListOrderVehicleDto().get(0).getId());
				dto.setBsCheckQuantity(checkVo.getTotalCheck());
				listOrderVehicleDto.add(dto);
			}else{
				for(JXCMTBsMerchantOrderVehicleVO vo:checkVo.getListOrderVehicleDto()){
					dto = new JXCMTBsMerchantOrderVehicleDTO();
					dto.setId(vo.getId());
					dto.setBsCheckQuantity(vo.getBsCheckQuantity());
					listOrderVehicleDto.add(dto);
				}
			}
			dtoCondition.setListOrderVehicleDto(listOrderVehicleDto);
		}
		RpcResponse<Integer> rpcResponse = jxcOrderRemote.updateBsOrder(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::updateBsOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JXCBSOrderController::updateBsOrder end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	
	@ApiOperation(value="根据订购物料查找审核物料列表",notes="根据订购物料查找审核物料列表")
	@PostMapping("listMaterialCheck")
	public ResultEntity<List<JXCMTBsMerchantMaterialCheckDTO>> listMaterialCheck(@RequestBody JXCMTBsMerchantMaterialCheckVO materialCheckVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::listMaterialCheck start user:{},materialCheckVo:{}",user,materialCheckVo);
		JXCMTBsMerchantMaterialCheckDTO dtoCondition = new JXCMTBsMerchantMaterialCheckDTO();
		dtoCondition.setOrderMaterialCode(materialCheckVo.getOrderMaterialCode());
		RpcResponse<List<JXCMTBsMerchantMaterialCheckDTO>> rpcResponse = jxcProductRemote.listMaterialCheck(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::listMaterialCheck return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("JXCBSOrderController::listMaterialCheck end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	@ApiOperation(value="导出订单列表",notes="导出订单列表")
	@PostMapping("exportBsOrder")
	public ModelAndView exportBsOrder(@RequestBody JXCMTBsOrderQueryVO queryVo){
		User user = userInfoHolder.getUser();
		logger.info("JXCBSOrderController::exportBsOrder start user:{},queryVo:{}",user,queryVo);
		JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
		dtoCondition.setMerchantOrder(queryVo.getMerchantOrder());
		dtoCondition.setMoOrderCode(queryVo.getMerchantOrder());
		dtoCondition.setStatus(queryVo.getStatus());
		dtoCondition.setSignStatus(queryVo.getSignStatus());
		dtoCondition.setChannelId(queryVo.getChannelId());
		dtoCondition.setOrderTimeStart(queryVo.getOrderTimeStart());
		dtoCondition.setOrderTimeEnd(queryVo.getOrderTimeEnd());
		dtoCondition.setMerchantName(queryVo.getMerchantName());
		dtoCondition.setMerchantCode(queryVo.getMerchantName());
		dtoCondition.setProductTypeId(queryVo.getProductTypeId());
		dtoCondition.setProductCode(queryVo.getProductName());
		dtoCondition.setProductName(queryVo.getProductName());
		dtoCondition.setMaterialCode(queryVo.getMaterialName());
		dtoCondition.setMaterialName(queryVo.getMaterialName());
		dtoCondition.setCheckTimeStart(queryVo.getCheckTimeStart());
		dtoCondition.setCheckTimeEnd(queryVo.getCheckTimeEnd());
		dtoCondition.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<JXCMTBsMerchantOrderExportDTO>> rpcResponse = jxcOrderRemote.exportBsMerchantOrderBSS(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (null != errCodeEnum) {
            logger.info("JXCBSOrderController::exportBsOrder return"
                    + errCodeEnum.getDescrible());
            return new ModelAndView(errCodeEnum.getDescrible());
        }
        List<JXCMTBsMerchantOrderExportDTO> listExportDto = rpcResponse.getResult();
        logger.info("listExportDto.size=" + listExportDto.size());
        List<JXCMTBsMerchantOrderBssExcelVo> listExportVo = new ArrayList<>();
        
        if(null != listExportDto){
        	for(JXCMTBsMerchantOrderExportDTO dto:listExportDto){
        		listExportVo.add(convertMerchantOrderBssDto2Vo(dto));
        	} 	
        }   
        logger.info("listExportVo.size=" + listExportVo.size());
        List<Object> merchantOrderList = new ArrayList<Object>();
        merchantOrderList.addAll(listExportVo);
        logger.info("merchantOrderList.size=" + merchantOrderList.size());
        Map<String, Object> map = new HashMap<>();
        map.put("objs", merchantOrderList);
        logger.info("map:{}" + map);
        map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_BS_MERCHANT_ORDER_BSS);
        map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_BS_MERCHANT_ORDER_BSS);
        map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_BS_MERCHANT_ORDER_BSS); 
        return new ModelAndView(excelXlsxStreamingViewUtil,map);   
	}

	private String  convertStatus(Integer status){
		if(org.springframework.util.StringUtils.isEmpty(status))
		{
			return "";
		}
		if (status== 1) {
			return MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
		} else if (status == 0) {
			return MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
		} else if (status == 2) {
			return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
		} else if (status == 3) {
			return MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
		} else if (status==5){
			return MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
		}else if(status==7){
			return MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
		}else if(status==8){
			return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
		}else if(status == 9){
			return MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getName();
		}
		return "";
	}
	
	private JXCMTBsMerchantOrderBssExcelVo convertMerchantOrderBssDto2Vo(JXCMTBsMerchantOrderExportDTO dto){
		JXCMTBsMerchantOrderBssExcelVo vo = new JXCMTBsMerchantOrderBssExcelVo();
		vo.setMoOrderCode(StringUtils.isEmpty(dto.getMoOrderCode())?"":dto.getMoOrderCode());
		vo.setMerchantOrder(StringUtils.isEmpty(dto.getMerchantOrder())?"":dto.getMerchantOrder());
		vo.setMerchantName(StringUtils.isEmpty(dto.getMerchantName())?"":dto.getMerchantName());
		vo.setChannelName(StringUtils.isEmpty(dto.getChannelName())?"":dto.getChannelName());
		vo.setProductName(StringUtils.isEmpty(dto.getProductName())?"":dto.getProductName());
		vo.setOrderMaterialCode(StringUtils.isEmpty(dto.getOrderMaterialCode())?"":dto.getOrderMaterialCode());
		vo.setOrderMaterialName(StringUtils.isEmpty(dto.getOrderMaterialName())?"":dto.getOrderMaterialName());
		vo.setMaterialCode(StringUtils.isEmpty(dto.getMaterialCode())?"":dto.getMaterialCode());
		vo.setMaterialName(StringUtils.isEmpty(dto.getMaterialName())?"":dto.getMaterialName());
		vo.setMdeviceTypeName(StringUtils.isEmpty(dto.getMdeviceTypeName())?"":dto.getMdeviceTypeName());
		vo.setPrice((dto.getPrice()==null)?0.0:dto.getPrice());
		vo.setCheckRemark(StringUtils.isEmpty(dto.getCheckRemark())?"":dto.getCheckRemark());
		vo.setSubjectName(StringUtils.isEmpty(dto.getSubjectName())?"":dto.getSubjectName());
		vo.setInsure(StringUtils.isEmpty(dto.getInsure())?"":(dto.getInsure().equals("Y")?"是":"否"));
		if(org.springframework.util.StringUtils.isEmpty(dto.getBsTotal())){
			vo.setBsTotal(0);
		}else{
			if(!org.springframework.util.StringUtils.isEmpty(dto.getPropQuantity())){
				vo.setBsTotal(dto.getBsTotal()*dto.getPropQuantity());
			}
		}
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
		if(!org.springframework.util.StringUtils.isEmpty(dto.getStatus())) {
			vo.setStatusStr(convertStatus(dto.getStatus()));
		}

        vo.setFastenConfigDesc(StringUtils.isEmpty(dto.getFastenConfigDesc())?"":dto.getFastenConfigDesc());
		vo.setSetOptionConfigDesc(StringUtils.isEmpty(dto.getSetOptionConfigDesc())?"":dto.getSetOptionConfigDesc());
		vo.setCheckBy(StringUtils.isEmpty(dto.getCheckBy())?"":dto.getCheckBy());
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		vo.setCheckTimeStr(org.springframework.util.StringUtils.isEmpty(dto.getCheckTime())?"":sp.format(dto.getCheckTime()));
		vo.setOrderTimeStr(org.springframework.util.StringUtils.isEmpty(dto.getOrderTime())?"":sp.format(dto.getOrderTime()));
		String sendTime = "";
		String logisticsDesc = "";
		if(null != dto.getListLogistics()){
			int flag = 0;
			for(JXCMTBsLogisticsDTO logisticsDto:dto.getListLogistics()){
				if(flag != 0){
					logisticsDesc += "\n";
				}
				logisticsDesc = "物流公司:" + (StringUtils.isEmpty(logisticsDto.getCompany())?"":logisticsDto.getCompany()) + " 物流单号:" + (StringUtils.isEmpty(logisticsDto.getOrderNumber())?"":logisticsDto.getOrderNumber()) + " 数量:" + (logisticsDto.getShipmentsQuantity()==null?0:logisticsDto.getShipmentsQuantity());
				sendTime = logisticsDto.getSendTime();
			}
		}
		vo.setLogisticsDesc(logisticsDesc);
		vo.setSendTime(sendTime);
		return vo;
	}
	
	@ApiOperation(value="上传签收单文件",notes="上传签收单文件")
	@RequestMapping("uploadSignFile")
	public ResultEntity<JXCMTBsUploadFileVO> uploadSignFile(@RequestParam(value = "file") MultipartFile fileData,
			@ModelAttribute FileUploadVO fileUpload)throws Exception{
		JXCMTBsUploadFileVO result = new JXCMTBsUploadFileVO();
		String preFileName = fileUpload.getPreFileName();
		logger.info("UploadController::uploadSignPic preFileName:"
				+ preFileName);
		double size = fileData.getSize();
		int fileSize = (int) Math.ceil(size / FileUtils.ONE_MB);
		if (uploadProperty.getMaxSize() != null
				&& fileSize > uploadProperty.getMaxSize()) {
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_FILE_OUT_OF_RANGE.getCode(), "文件超出大小限制(".concat(uploadProperty.getMaxSize().toString())
					.concat("MB"));
		}
		String savePath = uploadProperty.getDirs().get(preFileName);
		StringBuffer tempFileName = new StringBuffer(preFileName);
		tempFileName.append("-");
		tempFileName.append(FMT.format(new Date()));
		tempFileName.append(UUID.randomUUID().toString().replace("-", "")
				.substring(16));
		fileUpload.setOutputPreFileName(tempFileName.toString());
		String originalFilename = fileData.getOriginalFilename();
		int i = originalFilename.lastIndexOf(".");
		if (i > -1 && i < originalFilename.length())
			tempFileName.append(originalFilename.substring(i).toUpperCase());
		File tempPackage = new File(savePath);
		if (!tempPackage.exists()) {
			tempPackage.mkdirs();
		}
		File tempFile = new File(savePath, tempFileName.toString());
		byte[] tempbytes = new byte[1024];
		int byteread = 0;
		InputStream fis = fileData.getInputStream();
		FileOutputStream fos = new FileOutputStream(tempFile);
		while ((byteread = fis.read(tempbytes)) != -1) {
			fos.write(tempbytes, 0, byteread);
			fos.flush();
		}
		fos.close();
		String serverSavePath = uploadProperty.getUrls().get(preFileName)
				+ tempFileName.toString();
		fileUpload.setSavePath(serverSavePath);
		fileUpload.setOutputFileName(tempFileName.toString());
		fileUpload.setFileUploadName(originalFilename);
		result.setUrl(serverSavePath);
		return ResultEntity.success(result);
	}
	
}
