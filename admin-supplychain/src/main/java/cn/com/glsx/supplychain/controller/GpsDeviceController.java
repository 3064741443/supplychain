package cn.com.glsx.supplychain.controller;

import cn.com.glsx.framework.core.beans.ResponseEntity;
import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.DeviceInfoGpsPreimport;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.util.ExcelReads;
import cn.com.glsx.supplychain.util.ExcelUtils;
import cn.com.glsx.supplychain.util.ImportedResult;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gps设备入库管理
 * 
 * @author leiming
 */
@RestController
@RequestMapping("gpsDeviceInfo")
public class GpsDeviceController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoHolder userInfoHolder;

	@Autowired
	private SupplyadminProperty supplyadminProperty;

	@Autowired
	private DeviceManagerAdminRemote deviceManagerAdminRemote;

	/**
	 * <br>
	 * <b>功能：</b>Gps设备入库导入模版<br>
	 * <b>日期：</b> 2018-11-16<br>
	 *
	 * @param response
	 */
	@RequestMapping(value = "/gpsDeviceDownloadTemplate", method = RequestMethod.GET)
	public void gpsDeviceDownloadTemplate(HttpServletResponse response) {
		try {
			OutputStream os = response.getOutputStream();
			String str = "";
			String name = "";
			str = "GPS入库管理列表.xlsx";
			name = "templates/templateGpsDeviceImport.xlsx";

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
	 * gps出入库导入校验
	 *
	 * @param
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "/preGpsDeviceImportCheck")
	public ResponseEntity<ImportedResult> preGpsDeviceImportCheck(
			@RequestParam(value = "file") MultipartFile files) {
		logger.warn("gps出入库导入校验");
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
		if (files != null) {
			BufferedOutputStream bw = null;
			try {
				String fileName = files.getOriginalFilename();
				// 判断是否有文件且是否为图片文件
				if (fileName != null
						&& !"".equalsIgnoreCase(fileName.trim())
						&& (FilenameUtils.getExtension(fileName.trim()).equals(
								"xls") || FilenameUtils.getExtension(
								fileName.trim()).equals("xlsx"))) {
					// 可以选择把文件保存到服务器,创建输出文件对象
					String strUploadFile = supplyadminProperty.getUploadPath()
							+ UUID.randomUUID().toString() + "."
							+ FilenameUtils.getExtension(fileName);
					File outFile = new File(strUploadFile);

					// 文件到输出文件对象
					FileUtils.copyInputStreamToFile(files.getInputStream(),
							outFile);
					List<DeviceInfoGpsPreimport> resultList = new ArrayList<DeviceInfoGpsPreimport>();

					// 获得输入流
					InputStream input = files.getInputStream();
					List<Object> list = null;
					try {
						list = ExcelReads.getInstance().readExcel2Objs(input,
								DeviceInfoGpsPreimport.class, 0, 0);
					} catch (Exception e) {
						importedResult.setIsImported(4);
						importedResult.setCause("请使用有效模板导入数据");
						importList.add(importedResult);
						responseEntity.setData(importList);
						return responseEntity;
					}

					if (list == null || list.size() == 0) {
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
					if (totalCount > opAllowNum) {
						importedResult.setIsImported(3);
						importedResult.setCause("模版中最大允许导入" + opAllowNum
								+ "条,请修改数据重新再导!");
						importList.add(importedResult);
						responseEntity.setData(importList);
						logger.warn("导入异常...");
						return responseEntity;
					}

					for (Object device : list) {
						DeviceInfoGpsPreimport bean = (DeviceInfoGpsPreimport) device;
						resultList.add(bean);
					}

					RpcResponse<CheckImportDataVo> response = deviceManagerAdminRemote
							.checkImportGpsDevceList(resultList);

					ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response
							.getError();
					if (errCodeEnum != null) {
						importedResult.setIsImported(4);
						importedResult.setCause(errCodeEnum.getDescrible());
						importList.add(importedResult);
						responseEntity.setData(importList);
						return responseEntity;
					}

					List<DeviceInfoGpsPreimport> successList = response
							.getResult().getGpsPreImportSuccessList();
					List<DeviceInfoGpsPreimport> failList = response
							.getResult().getGpsPreImportFailList();

					failCount = failList.size();
					successCount = successList.size();

					importedResult.setSuccessCount(successCount);
					importedResult.setFailCount(failCount);
					importedResult.setTotalCount(totalCount);

					// 保存数据
					if (successList != null && successList.size() > 0) {
						String json = JSON.toJSONString(successList);
						importedResult.setIsImported(1);
						importedResult.setMsg(json);
					}

					if (failList != null && failList.size() > 0) {
						String name = UUID.randomUUID().toString() + "."
								+ FilenameUtils.getExtension(fileName);
						String url = supplyadminProperty.getDownloadPath()
								+ name;
						ExcelUtil.getInstance().exportObj2Excel(url, failList,
								DeviceInfoGpsPreimport.class);
						String reUrl = supplyadminProperty.getDomain() + name;
						importedResult.setUrl(reUrl);
						logger.warn("导入结束......,总数：" + totalCount + " 成功："
								+ successCount + " 失败：" + failCount);
					}
				} else {
					importedResult
							.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
					importedResult.setIsImported(2);
					importList.add(importedResult);
					responseEntity.setData(importList);
					logger.warn("导出结束...");
					return responseEntity;
				}
			} catch (Exception e) {
				logger.error("导入操作异常",e.getMessage());
			} finally {
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

	@SuppressWarnings({ "static-access", "deprecation" })
	@RequestMapping(value = "/deviceImport")
	@ResponseBody
	public ResultEntity<Integer> gpsDeviceImport(
			@RequestBody List<DeviceInfoGpsPreimport> gpsPreImports) {
		User user = userInfoHolder.getUser();
		String userName = user != null ? user.getRealname() : "admin";
		RpcResponse<Integer> response = null;
		ErrorCodeEnum errCodeEnum = null;

		response = deviceManagerAdminRemote.importGpsDeviceList(userName,
				gpsPreImports);
		errCodeEnum = (ErrorCodeEnum) response.getError();
		if (errCodeEnum != null) {
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

	@RequestMapping("listDeviceGpsPreimport")
	public ResultEntity<RpcPagination<DeviceInfoGpsPreimport>> pageDeviceInfoGpsPreimport(
			@RequestBody RpcPagination<DeviceInfoGpsPreimport> pagination) {

		RpcResponse<RpcPagination<DeviceInfoGpsPreimport>> response = deviceManagerAdminRemote
				.pageGpsDeviceList(pagination);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if (errCodeEnum == null) {
			response.setMessage("查询成功");
		} else {
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
}
