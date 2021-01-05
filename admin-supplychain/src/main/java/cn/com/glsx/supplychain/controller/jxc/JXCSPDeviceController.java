package cn.com.glsx.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResponseEntity;
import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.PackageStatuEnum;
import cn.com.glsx.supplychain.jxc.dto.JXCMTDeviceFileDTO;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.model.JXCMTDeviceInfoImport;
import cn.com.glsx.supplychain.jxc.remote.JxcDeviceRemote;
import cn.com.glsx.supplychain.jxc.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.model.jxc.DeviceFileExportVo;
import cn.com.glsx.supplychain.model.jxc.JXCMTDeviceFileSearchVo;
import cn.com.glsx.supplychain.model.jxc.JXCMTDeviceInfoImportCheckVo;
import cn.com.glsx.supplychain.model.jxc.JXCMTDeviceInfoImportVo;
import cn.com.glsx.supplychain.util.ExcelReads;
import cn.com.glsx.supplychain.util.ExcelUtils;
import cn.com.glsx.supplychain.util.ImportedResult;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author liuquan
 * @version V1.0
 * @Title: JXCBSOrderController.java
 * @Description: 820改版  供应链设备管理
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value = "/JXCDevice", tags = "820改版  供应链设备管理")
@RestController
@RequestMapping("JXCDevice")
public class JXCSPDeviceController {
    private final static Logger logger = LoggerFactory.getLogger(JXCSPDeviceController.class);
    @Autowired
    private UserInfoHolder userInfoHolder;
    @Autowired
    private JxcDeviceRemote jxcDeviceRemote;
    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @ApiOperation(value = "设备明细模板下载", notes = "设备明细模板下载")
    @GetMapping("downloadEquipmentDetailsTemplate")
    public void downloadEquipmentDetailsTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "设备明细导入模板.xlsx";
            name = "templates/templateEquipmentDetails.xlsx";
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

  /*  private String constrJsonRenew(List<JXCMTDeviceInfoImport> list) {
        if (null == list || list.size() < 0) {
            return null;
        }
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                json.append(",");
            }
            json.append("{");
            json.append("\"attribCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getAttribCode()) + "\",");
            json.append("\"iccid\":\"" + SupplychainUtils.formartImportString(list.get(i).getIccid()) + "\",");
            json.append("\"imei\":\"" + SupplychainUtils.formartImportString(list.get(i).getImei()) + "\",");
            json.append("\"sn\":\"" + SupplychainUtils.formartImportString(list.get(i).getSn()) + "\",");
            json.append("\"batch\":\"" + SupplychainUtils.formartImportString(list.get(i).getBatch()) + "\",");
            json.append("\"wareHouseName\":\"" + SupplychainUtils.formartImportString(list.get(i).getWareHouseName()) + "\",");
            json.append("\"wareHouseUpName\":\"" + SupplychainUtils.formartImportString(list.get(i).getWareHouseUpName()) + "\"");
            //json.append("\"settleCustomerName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerName()) + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }*/

    @ApiOperation(value = "设备库存管理导入设备明细数据校验", notes = "设备库存管理导入设备明细数据校验")
    @PostMapping("importEquipmentDetailsCheck")
    public ResponseEntity<ImportedResult> importEquipmentDetailsCheck(@RequestParam(value = "file") MultipartFile files) {
        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<>();
        List<ImportedResult> importList = new ArrayList<ImportedResult>();
        ImportedResult importedResult = new ImportedResult();
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
                if (fileName != null && !"".equalsIgnoreCase(fileName.trim())
                        && (FilenameUtils.getExtension(fileName.trim()).equals("xls")
                        || FilenameUtils.getExtension(fileName.trim()).equals("xlsx"))) {
                    // 可以选择把文件保存到服务器,创建输出文件对象
                    String strUploadFile = supplyadminProperty.getUploadPath() + UUID.randomUUID().toString() + "."
                            + FilenameUtils.getExtension(fileName);
                    File outFile = new File(strUploadFile);
                    // 文件到输出文件对象
                    FileUtils.copyInputStreamToFile(files.getInputStream(), outFile);
                    List<JXCMTDeviceInfoImportCheckVo> deviceInfoImportCheckVoList = new ArrayList<>();
                    JXCMTDeviceInfoImport deviceInfoImport = null;
                    List<JXCMTDeviceInfoImport> deviceInfoImportList = new ArrayList<JXCMTDeviceInfoImport>();
                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, JXCMTDeviceInfoImportCheckVo.class, 0, 0);
                    } catch (Exception e) {
                        importedResult.setIsImported(4);
                        importedResult.setCause("请使用有效模板导入数据");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常....");
                        return responseEntity;
                    }

                    logger.info("importEquipmentDetailsCheck :checkImportDeviceList 检验开始" + new Date());
                    if (list == null || list.size() == 0) {
                        importedResult.setIsImported(4);
                        importedResult.setCause("请使用有效模板导入数据");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    if (opAllowNum == 0) {
                        opAllowNum = 20000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        importedResult.setIsImported(3);
                        importedResult.setCause("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                 /*   for (Object item : list) {
                        JXCMTDeviceInfoImport bean = (JXCMTDeviceInfoImport) item;
                        deviceInfoImportList.add(bean);
                    }*/


                    for (Object item : list) {
                        JXCMTDeviceInfoImportCheckVo bean = (JXCMTDeviceInfoImportCheckVo) item;
                        deviceInfoImportCheckVoList.add(bean);
                    }

                    for (JXCMTDeviceInfoImportCheckVo item : deviceInfoImportCheckVoList) {
                        deviceInfoImport = new JXCMTDeviceInfoImport();
                        BeanUtils.copyProperties(item, deviceInfoImport);
                        deviceInfoImportList.add(deviceInfoImport);
                    }

                    RpcResponse<CheckImportDataVo> response = jxcDeviceRemote.checkImportDeviceList(deviceInfoImportList);
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        importedResult.setIsImported(0);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    List<JXCMTDeviceInfoImport> SucessList = response.getResult().getDeviceInfoImportSuccessList();
                    List<JXCMTDeviceInfoImport> failList = response.getResult().getDeviceInfoImportFailList();
                    List<JXCMTDeviceInfoImportCheckVo> deviceInfoImportCheckVos = new ArrayList<>();
                    JXCMTDeviceInfoImportCheckVo deviceInfoImportCheckVo = null;
                    for (JXCMTDeviceInfoImport item : failList) {
                        deviceInfoImportCheckVo = new JXCMTDeviceInfoImportCheckVo();
                        BeanUtils.copyProperties(item, deviceInfoImportCheckVo);
                        deviceInfoImportCheckVos.add(deviceInfoImportCheckVo);
                    }
                    failCount = failList.size();
                    successCount = SucessList.size();
                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = JSON.toJSONString(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, deviceInfoImportCheckVos, JXCMTDeviceInfoImportCheckVo.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                    }
                    logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                } else {
                    importedResult.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
                    importedResult.setIsImported(2);
                    importList.add(importedResult);
                    responseEntity.setData(importList);
                    logger.warn("导出结束....");
                    return responseEntity;
                }
            } catch (IOException e) {
                logger.error("导入操作异常" + e);
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

    @ApiOperation(value = "设备库存管理导入设备明细", notes = "设备库存管理导入设备明细")
    @PostMapping("importEquipmentDetails")
    public ResultEntity<Integer> importEquipmentDetails(@RequestBody JXCMTDeviceInfoImportVo deviceInfoImportVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        RpcResponse<Integer> response = jxcDeviceRemote.importDeviceInfoList(userName, deviceInfoImportVo.getDeviceInfoImportList());
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    @ApiOperation(value = "设备关系明细列表导出", notes = "设备关系明细列表导出")
    @PostMapping("exportDeviceFile")
    public ResultEntity<List<JXCMTDeviceFileDTO>> exportDeviceFile(@RequestBody JXCMTDeviceFileSearchVo deviceFileSearchVo, HttpServletResponse httpServletResponse) throws Exception {
        User user = userInfoHolder.getUser();
        logger.info("JXCSPDeviceController::exportDeviceFile start user:{},deviceFileSearchVo:{}",user,deviceFileSearchVo);
        JXCMTDeviceFileDTO deviceFileDTO = new JXCMTDeviceFileDTO();
        deviceFileDTO.setDevTypeId(deviceFileSearchVo.getDevTypeId());
        deviceFileDTO.setPackageStatu(deviceFileSearchVo.getPackageStatu());
        if (!org.springframework.util.StringUtils.isEmpty(deviceFileSearchVo.getSearchKey()) && !org.springframework.util.StringUtils.isEmpty(deviceFileSearchVo.getSearchValue())) {

            String strKey = deviceFileSearchVo.getSearchKey();
            switch (strKey) {
                case "SN": //IMEI
                    deviceFileDTO.setSn(deviceFileSearchVo.getSearchValue());
                    break;
                case "ID"://当前ICCID
                    deviceFileDTO.setIccid(deviceFileSearchVo.getSearchValue());
                    break;
                case "II"://当前IMSI
                    deviceFileDTO.setImsi(deviceFileSearchVo.getSearchValue());
                    break;
                case "CU"://当前用户
                    deviceFileDTO.setUserFlag(deviceFileSearchVo.getSearchValue());
                    break;
                case "DC"://设备编号
                    deviceFileDTO.setDeviceCode(Integer.valueOf(deviceFileSearchVo.getSearchValue()));
                    break;
                case "PK"://入库商品编号
                    deviceFileDTO.setPackageId(Integer.valueOf(deviceFileSearchVo.getSearchValue()));
                    break;
                case "SM"://发往商户
                    deviceFileDTO.setSendMerchantNo(deviceFileSearchVo.getSearchValue());
                    break;
            }
        }
        deviceFileDTO.setOutStorageStartDate(deviceFileSearchVo.getOutStorageStartDate());
        deviceFileDTO.setOutStorageEndDate(deviceFileSearchVo.getOutStorageEndDate());
        deviceFileDTO.setPackageUserStartDate(deviceFileSearchVo.getPackageUserStartDate());
        deviceFileDTO.setPackageUserEndDate(deviceFileSearchVo.getPackageUserEndDate());
        deviceFileDTO.setConsumer((user==null)?"admin":user.getRealname());
        RpcResponse<List<JXCMTDeviceFileDTO>> response = jxcDeviceRemote.exportDeviceFile(deviceFileDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
           if(errCodeEnum!=null) {
               logger.error(response.getMessage());
               return ResultEntity.error(errCodeEnum.getCode(), errCodeEnum.getDescrible());
           }
       /* List<JXCMTDeviceFileDTO> deviceFileDTOS = response.getResult();
        List<DeviceFileExportVo> deviceFileExportVos = deviceFileDTOS.stream().map(jxcmtDeviceFileDTO -> exportDeviceFileConvertTo(jxcmtDeviceFileDTO)).collect(Collectors.toList());
        Integer numberNo = 1;
        for (DeviceFileExportVo deviceFileExportVo : deviceFileExportVos) {
            deviceFileExportVo.setNumberNo(numberNo++);
        }
        ExcelReadAndWriteUtil.writeExcelWithAuto(httpServletResponse, deviceFileExportVos, Constants.EXPORT_NAME_DEVICE_FILE, Constants.EXPORT_NAME_DEVICE_FILE, DeviceFileExportVo.class);*/
        return ResultEntity.success(response.getResult());
    }

    private DeviceFileExportVo exportDeviceFileConvertTo(JXCMTDeviceFileDTO deviceFileDTO) {
        DeviceFileExportVo deviceFileExportVo = new DeviceFileExportVo();
        BeanUtils.copyProperties(deviceFileDTO, deviceFileExportVo);
        if (PackageStatuEnum.PACKAGE_STATU_UNACTIVE.getValue().equals(deviceFileDTO.getPackageStatu())) {
            deviceFileExportVo.setPackageStatu("未激活");
        } else if (PackageStatuEnum.PACKAGE_STATU_ALACTIVE.getValue().equals(deviceFileDTO.getPackageStatu())) {
            deviceFileExportVo.setPackageStatu("已激活");
        } else if (PackageStatuEnum.PACKAGE_STATU_INITIAL.getValue().equals(deviceFileDTO.getPackageStatu())) {
            deviceFileExportVo.setPackageStatu("初始化");
        }
        if (org.springframework.util.StringUtils.isEmpty(deviceFileDTO.getUserId())) {
            deviceFileExportVo.setUserFlag("否");
        } else {
            deviceFileExportVo.setUserFlag("是");
        }
        deviceFileExportVo.setDeviceName(deviceFileDTO.getDeviceCode() + "/" + deviceFileDTO.getDeviceName());
        return deviceFileExportVo;
    }

}
