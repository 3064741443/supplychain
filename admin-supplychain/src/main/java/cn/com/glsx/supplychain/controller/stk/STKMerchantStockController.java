package cn.com.glsx.supplychain.controller.stk;

import cn.com.glsx.framework.core.beans.ResponseEntity;
import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDTO;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDeductionDTO;
import cn.com.glsx.supplychain.jxc.dto.STKMerchantStockDeductionDetailDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcCommonRemote;
import cn.com.glsx.supplychain.jxc.remote.StkMerchantStockRemote;
import cn.com.glsx.supplychain.model.jxc.STKMerchantStockSearchVO;
import cn.com.glsx.supplychain.model.stk.MerchantStockDeductDetailVO;
import cn.com.glsx.supplychain.model.stk.MerchantStockDeductVO;
import cn.com.glsx.supplychain.model.stk.STKMerchantStockExportVo;
import cn.com.glsx.supplychain.util.*;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Title: StkMerchantStockController.java
 * @Description: 服务商库存管理
 * @author liuquan
 * @version V1.0
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value="/STKMerchantStock",tags="库存管理 服务商库存管理")
@RestController
@RequestMapping("STKMerchantStock")
public class STKMerchantStockController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserInfoHolder userInfoHolder;
	@Autowired
	private JxcCommonRemote jxcCommonRemote;
	@Autowired
	private StkMerchantStockRemote stkMerchantStockRemote;
	@Autowired
	private SupplyadminProperty supplyadminProperty;
	
	@ApiOperation(value="库存管理列表查询",notes="库存管理列表查询")
	@PostMapping("pageMerchantStock")
	public ResultEntity<RpcPagination<STKMerchantStockDTO>> pageMerchantStock(@RequestBody STKMerchantStockSearchVO merchantStockSearchVo){
		logger.info("STKMerchantStockController::pageMerchantStock start merchantStockSearchVo:{}",merchantStockSearchVo);
		RpcPagination<STKMerchantStockDTO> pagination = new RpcPagination<>();
		STKMerchantStockDTO dtoCondition = new STKMerchantStockDTO();
		dtoCondition.setChannelId(merchantStockSearchVo.getChannelId());
		dtoCondition.setMerchantCode(merchantStockSearchVo.getMerchantSearchKey());
		dtoCondition.setMerchantName(merchantStockSearchVo.getMerchantSearchKey());
		dtoCondition.setDeviceType(merchantStockSearchVo.getDeviceType());
		dtoCondition.setStockMonth(merchantStockSearchVo.getStockMonth());
		pagination.setCondition(dtoCondition);
		pagination.setPageNum(merchantStockSearchVo.getPageNum());
		pagination.setPageSize(merchantStockSearchVo.getPageSize());
		RpcResponse<RpcPagination<STKMerchantStockDTO>> rpcResponse = stkMerchantStockRemote.pageMerchantStock(pagination);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKMerchantStockController::pageMerchantStock return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKMerchantStockController::pageMerchantStock end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}

	@ApiOperation(value="库存管理列表导出",notes="库存管理列表导出")
	@PostMapping("exportMerchantStock")
	public void exportMerchantStock(@RequestBody STKMerchantStockSearchVO merchantStockSearchVo,HttpServletResponse response) throws Exception {
		User user = userInfoHolder.getUser();
		logger.info("STKMerchantStockController::exportMerchantStock start merchantStockSearchVo:{}",merchantStockSearchVo);
		STKMerchantStockDTO dtoCondition = new STKMerchantStockDTO();
		dtoCondition.setChannelId(merchantStockSearchVo.getChannelId());
		dtoCondition.setMerchantCode(merchantStockSearchVo.getMerchantSearchKey());
		dtoCondition.setMerchantName(merchantStockSearchVo.getMerchantSearchKey());
		dtoCondition.setDeviceType(merchantStockSearchVo.getDeviceType());
		dtoCondition.setStockMonth(merchantStockSearchVo.getStockMonth());
		dtoCondition.setConsumer((user==null)?"admin":user.getRealname());
		RpcResponse<List<STKMerchantStockDTO>> rpcResponse = stkMerchantStockRemote.exportMerchantStock(dtoCondition);
		List<STKMerchantStockDTO> stkMerchantStockDTOS=rpcResponse.getResult();
		List<STKMerchantStockExportVo> stkMerchantStockExportVos = stkMerchantStockDTOS.stream().map(stkMerchantStockDTO -> exportSTKMerchantStockConvertTo(stkMerchantStockDTO)).collect(Collectors.toList());
		ExcelReadAndWriteUtil.writeExcel(response, stkMerchantStockExportVos, Constants.EXPORT_NAME_STK_MERCHANT_STOCK, Constants.EXPORT_NAME_STK_MERCHANT_STOCK, STKMerchantStockExportVo.class);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
			logger.info("STKMerchantStockController::exportMerchantStock return"
					+ errCodeEnum.getDescrible());
		}
		logger.info("STKMerchantStockController::exportMerchantStock end result:{}", rpcResponse.getResult());
	}

	private STKMerchantStockExportVo exportSTKMerchantStockConvertTo(STKMerchantStockDTO stkMerchantStockDTO) {
		STKMerchantStockExportVo stkMerchantStockExportVo = new STKMerchantStockExportVo();
		BeanUtils.copyProperties(stkMerchantStockDTO, stkMerchantStockExportVo);
		return stkMerchantStockExportVo;
	}
	
	/**
	 * <br>
	 * <b>功能：</b>库存扣减导入模板<br>
	 * <b>日期：</b> 2020-11-09<br>
	 *
	 * @param response
	 */
	@ApiOperation(value="库存扣减导入模板",notes="库存扣减导入模板")
	@RequestMapping(value = "/mstkDeductDownloadTemplate", method = RequestMethod.GET)
	public void mstkDeductDownloadTemplate(HttpServletResponse response){
		try {
			OutputStream os = response.getOutputStream();
			String str = "";
			String name = "";
			str = "库存管理扣减模板.xlsx";
			name = "templates/templateMerchantStockDeduct.xlsx";

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(str.getBytes("GBK"), "iso8859-1") + "\"");
			response.setContentType("application/octet-stream;charset=UTF-8");
			ExcelUtils.downloadExcelTemplate(os, name);

		} catch (Exception e) {
			logger.error("下载Excle模版出错：", e);
		}
		return;
	}
	
	/**
	 * 库存扣减导入验证
	 *
	 * @param
	 * @param
	 */
	@ApiOperation(value="库存扣减导入验证",notes="库存扣减导入验证")
	@ResponseBody
	@RequestMapping(value = "/merchantStockDeductImportCheck")
	public ResponseEntity<ImportedResult> merchantStockDeductImportCheck(String tradeType,@RequestParam(value = "file") MultipartFile files){
		logger.warn("库存扣减导入验证");
		ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
		List<ImportedResult> importList = new ArrayList<ImportedResult>();
		ImportedResult importedResult = new ImportedResult();
		importedResult.setIsImported(0);
		importedResult.setCause("导入成功");
		
		long totalCount = 0;
		long failCount = 0;
		long successCount = 0;
		
		String allowNum = supplyadminProperty.getOpAllowNum();
		Integer opAllowNum = 0;
		if (!StringUtils.isEmpty(allowNum)) {
			opAllowNum = Integer.parseInt(allowNum);
		}
		// 这里可以支持多文件上传
		if (files != null){
			BufferedOutputStream bw = null;
			try{
				String fileName = files.getOriginalFilename();
				// 判断是否有文件且是否为图片文件
				if (fileName != null
						&& !"".equalsIgnoreCase(fileName.trim())
						&& (FilenameUtils.getExtension(fileName.trim()).equals(
								"xls") || FilenameUtils.getExtension(
								fileName.trim()).equals("xlsx"))){
					// 可以选择把文件保存到服务器,创建输出文件对象
					String strUploadFile = supplyadminProperty.getUploadPath()
							+ UUID.randomUUID().toString() + "."
							+ FilenameUtils.getExtension(fileName);
					File outFile = new File(strUploadFile);
					// 文件到输出文件对象
					FileUtils.copyInputStreamToFile(files.getInputStream(),
							outFile);
					
					STKMerchantStockDeductionDTO dtoCondition = new STKMerchantStockDeductionDTO();
					List<STKMerchantStockDeductionDetailDTO> listDetail = new ArrayList<>();
					dtoCondition.setListDetailDto(listDetail);
				//	List<MerchantStockDeductDetailVO> resultList = new ArrayList<MerchantStockDeductDetailVO>();
					// 获得输入流
					InputStream input = files.getInputStream();
					List<Object> list = null;
					try{
						list = ExcelReads.getInstance().readExcel2Objs(input,
								MerchantStockDeductDetailVO.class, 0, 0);
					}catch (Exception e){
						importedResult.setIsImported(4);
						importedResult.setCause("请使用有效模板导入数据");
						importList.add(importedResult);
						responseEntity.setData(importList);
						return responseEntity;
					}
					
					if (list == null || list.size() == 0){
						importedResult.setIsImported(4);
						importedResult.setCause("请使用有效模板导入数据");
						importList.add(importedResult);
						responseEntity.setData(importList);
						return responseEntity;
					}
					
					if (opAllowNum == 0) {
						opAllowNum = 5000;
					}
					
					totalCount = list.size();
					if (totalCount > opAllowNum){
						importedResult.setIsImported(3);
						importedResult.setCause("模版中最大允许导入" + opAllowNum
								+ "条,请修改数据重新再导!");
						importList.add(importedResult);
						responseEntity.setData(importList);
						logger.warn("导入异常...");
						return responseEntity;
					}
					
					for (Object device : list){
						MerchantStockDeductDetailVO bean = (MerchantStockDeductDetailVO) device;
						STKMerchantStockDeductionDetailDTO dtoObject = new STKMerchantStockDeductionDetailDTO();
						BeanUtils.copyProperties(bean, dtoObject);
						listDetail.add(dtoObject);
					}
					dtoCondition.setTradeType(tradeType);
					
					RpcResponse<STKMerchantStockDeductionDTO> rpcResponse = stkMerchantStockRemote.importMerchantStockDeductionCheck(dtoCondition);
					JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
					if (null != errCodeEnum) {
						importedResult.setIsImported(4);
						importedResult.setCause(errCodeEnum.getDescrible());
						importList.add(importedResult);
						responseEntity.setData(importList);
						return responseEntity;
			        }
					
					List<STKMerchantStockDeductionDetailDTO> successList = rpcResponse.getResult().getListSuccessDto();
					List<STKMerchantStockDeductionDetailDTO> failList = rpcResponse.getResult().getListFailedDto();
					
					failCount = failList.size();
					successCount = successList.size();
					
					importedResult.setSuccessCount(successCount);
					importedResult.setFailCount(failCount);
					importedResult.setTotalCount(totalCount);
					
					// 保存数据
					if (successList != null && successList.size() > 0){
						String json = JSON.toJSONString(successList);
						importedResult.setIsImported(1);
						importedResult.setMsg(json);
					}
					if (failList != null && failList.size() > 0){
						List<MerchantStockDeductDetailVO> listFailedVo = new ArrayList<>();
						for(STKMerchantStockDeductionDetailDTO dto:failList){
							MerchantStockDeductDetailVO vo = new MerchantStockDeductDetailVO();
							BeanUtils.copyProperties(dto, vo);
							listFailedVo.add(vo);
						}
						
						String name = UUID.randomUUID().toString() + "."
								+ FilenameUtils.getExtension(fileName);
						String url = supplyadminProperty.getDownloadPath()
								+ name;
						ExcelUtil.getInstance().exportObj2Excel(url, listFailedVo,
								MerchantStockDeductDetailVO.class);
						String reUrl = supplyadminProperty.getDomain() + name;
						importedResult.setUrl(reUrl);
						logger.warn("导入结束......,总数：" + totalCount + " 成功："
								+ successCount + " 失败：" + failCount);
					}
				}else{
					importedResult
					.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
					importedResult.setIsImported(2);
					importList.add(importedResult);
					responseEntity.setData(importList);
					logger.warn("导出结束...");
					return responseEntity;
				}
			}catch (Exception e){
				logger.error("导入操作异常",e.getMessage());
			}finally{
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		importList.add(importedResult);
		responseEntity.setData(importList);
		return responseEntity;
	}
	
	@ApiOperation(value="库存扣减导入",notes="库存扣减导入")
//	@SuppressWarnings({"static-access", "deprecation"})
//	@RequestMapping(value = "/merchantStockDeductImport")
 //   @ResponseBody
	@PostMapping("merchantStockDeductImport")
    public ResultEntity<Integer> merchantStockDeductImport(@RequestBody MerchantStockDeductVO deductVo){
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		RpcResponse<Integer> response = null;
		
		STKMerchantStockDeductionDTO dtoCondition = new STKMerchantStockDeductionDTO();
		List<STKMerchantStockDeductionDetailDTO> listDetailDto = new ArrayList<>();
		dtoCondition.setTradeType(deductVo.getTradeType());
		dtoCondition.setConsumer(userName);
		dtoCondition.setTime(SupplychainUtils.getStrYMDHMSFromDate(new Date()));
		if(null == deductVo.getListDetailVo() || deductVo.getListDetailVo().isEmpty()){
			return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), "数据列表为空");
		}
		STKMerchantStockDeductionDetailDTO detailDto = null;
		for(MerchantStockDeductDetailVO detailVo:deductVo.getListDetailVo()){
			detailDto = new STKMerchantStockDeductionDetailDTO();
			BeanUtils.copyProperties(detailVo, detailDto);
			listDetailDto.add(detailDto);
		}
		dtoCondition.setListDetailDto(listDetailDto);
		RpcResponse<Integer> rpcResponse = stkMerchantStockRemote.importMerchantStockDeduction(dtoCondition);
		JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
		if (null != errCodeEnum) {
            logger.info("STKMerchantStockController::merchantStockDeductImport return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
		logger.info("STKMerchantStockController::merchantStockDeductImport end result:{}", rpcResponse.getResult());
		return ResultEntity.success(rpcResponse.getResult());
	}
	
	
}
