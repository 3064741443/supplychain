package cn.com.glsx.merchant.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.SupplyadminProperty;
import cn.com.glsx.merchant.supplychain.util.*;
import cn.com.glsx.merchant.supplychain.vo.jxc.*;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.jst.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.TransferOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcTransferOrderRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/26 10:45
 */
@Api(value = "/jxcMdbOrder", tags = "经销存系统PC端调拨订单")
@RestController
@RequestMapping("jxcMdbOrder")
public class JxcMdbTransferOrderController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SupplyadminProperty supplyadminProperty;
    @Autowired
    private JxcTransferOrderRemote jxcTransferOrderRemote;


    @ApiOperation(value = "获取调入服务商列表", notes = "获取调入服务商列表")
    @PostMapping("listServiceProvider")
    public ResultEntity<List<JXCDealerUserInfoDTO>> listServiceProvider(@RequestBody JXCDealerUserInfoVo dealerUserInfoVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("JxcMdbTransferOrderController::listServiceProvider start dealerUserInfo:{},dealerUserInfoVo:{}", dealerUserInfo, dealerUserInfoVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            LOGGER.info("JxcMdbTransferOrderController::listServiceProvider not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCDealerUserInfoDTO dealerUserInfoDTO = new JXCDealerUserInfoDTO();
        BeanUtils.copyProperties(dealerUserInfoVo,dealerUserInfoDTO);
        RpcResponse<List<JXCDealerUserInfoDTO>> response = jxcTransferOrderRemote.listServiceProvider(dealerUserInfoDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JxcMdbTransferOrderController::listServiceProvider return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JxcMdbTransferOrderController::listServiceProvider end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "发起调拨生成调拨单", notes = "发起调拨生成调拨单")
    @PostMapping("generateMdbTransferOrder")
    public ResultEntity<Integer> generateTransferOrder(@RequestBody JXCMdbGenerateTransferOrderVo generateTransferOrderVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("JxcMdbTransferOrderController::generateTransferOrder start dealerUserInfo:{},generateTransferOrderVo:{}", dealerUserInfo, generateTransferOrderVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            LOGGER.info("JxcMdbTransferOrderController::generateTransferOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCMdbGenerateTransferOrderDTO jxcMdbGenerateTransferOrderDTO = new JXCMdbGenerateTransferOrderDTO();
        BeanUtils.copyProperties(generateTransferOrderVo, jxcMdbGenerateTransferOrderDTO);
        jxcMdbGenerateTransferOrderDTO.setOrderSource(Constants.SMJ_ORDER_SOURCE);
        jxcMdbGenerateTransferOrderDTO.setOutServiceProvidercode(dealerUserInfo.getMerchantCode());
        jxcMdbGenerateTransferOrderDTO.setOutServiceProviderName(dealerUserInfo.getMerchantName());
        jxcMdbGenerateTransferOrderDTO.setConsumer(dealerUserInfo.getName());
        //JxcUtils.setJXCBaseDTO(jxcMdbGenerateTransferOrderDTO);
        RpcResponse<Integer> response = jxcTransferOrderRemote.generateTransferOrder(jxcMdbGenerateTransferOrderDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JxcMdbTransferOrderController::generateTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JxcMdbTransferOrderController::generateTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }


    @ApiOperation(value = "获取调拨订单列表", notes = "获取调拨订单列表")
    @PostMapping("pageTransferOrderJXC")
    public ResultEntity<RpcPagination<JXCTransferOrderDTO>> pageTransferOrderJXC(@RequestBody JXCTransferOrderQueryVo transferOrderQueryVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("JxcMdbTransferOrderController::pageTransferOrderJXC start dealerUserInfo:{},generateTransferOrderVo:{}", dealerUserInfo, transferOrderQueryVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            LOGGER.info("JxcMdbTransferOrderController::pageTransferOrderJXC not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCTransferOrderQueryDTO transferOrderQueryDTO = new JXCTransferOrderQueryDTO();
        transferOrderQueryDTO.setMerchantCode(dealerUserInfo.getMerchantCode());
        transferOrderQueryDTO.setInServiceProviderName(transferOrderQueryVo.getInServiceProviderName());
        transferOrderQueryDTO.setTransferType(transferOrderQueryVo.getTransferType());
        transferOrderQueryDTO.setOrderSource(transferOrderQueryVo.getOrderSource());
        transferOrderQueryDTO.setMaterialName(transferOrderQueryVo.getMaterialName());
        transferOrderQueryDTO.setOrderStatus(transferOrderQueryVo.getOrderStatus());
        RpcPagination<JXCTransferOrderQueryDTO> pagination = new RpcPagination<>();
        transferOrderQueryDTO.setConsumer(dealerUserInfo.getMerchantName());
        pagination.setCondition(transferOrderQueryDTO);
        pagination.setPageNum(transferOrderQueryVo.getPageNum());
        pagination.setPageSize(transferOrderQueryVo.getPageSize());
        RpcResponse<RpcPagination<JXCTransferOrderDTO>> response = jxcTransferOrderRemote.pageTransferOrderJXC(pagination);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JxcMdbTransferOrderController::pageTransferOrderJXC return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JxcMdbTransferOrderController::pageTransferOrderJXC end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }


    @ApiOperation(value = "pc端调拨订单列表导出", notes = "pc端调拨订单列表导出")
    @PostMapping("exportTransferOrder")
    public void exportTransferOrder(@RequestBody JXCTransferOrderQueryVo transferOrderQueryVo,HttpServletResponse httpServletResponse) throws Exception {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("JxcMdbTransferOrderController::exportTransferOrder start dealerUserInfo:{},generateTransferOrderVo:{}", dealerUserInfo, transferOrderQueryVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            LOGGER.info("JxcMdbTransferOrderController::exportTransferOrder not login or session time out");
        }
        JXCTransferOrderQueryDTO transferOrderQueryDTO = new JXCTransferOrderQueryDTO();
        transferOrderQueryDTO.setMerchantCode(dealerUserInfo.getMerchantCode());
        transferOrderQueryDTO.setInServiceProviderName(transferOrderQueryVo.getInServiceProviderName());
        transferOrderQueryDTO.setTransferType(transferOrderQueryVo.getTransferType());
        transferOrderQueryDTO.setOrderSource(transferOrderQueryVo.getOrderSource());
        transferOrderQueryDTO.setMaterialName(transferOrderQueryVo.getMaterialName());
        transferOrderQueryDTO.setOrderStatus(transferOrderQueryVo.getOrderStatus());
        transferOrderQueryDTO.setConsumer(dealerUserInfo.getMerchantName());
        RpcResponse<List<JXCMdbTransferOrderExportDTO>> response = jxcTransferOrderRemote.exportJxcTransferOrder(transferOrderQueryDTO);
        List<JXCMdbTransferOrderExportDTO> transferOrderExportDTOS=response.getResult();
        List<JXCMdbTransferOrderExportVo> transferOrderExportVos= transferOrderExportDTOS.stream().map(transferOrderExportDTO -> exportJXCTransferOrderExportConvertTo(transferOrderExportDTO)).collect(Collectors.toList());
        ExcelReadAndWriteUtil.writeExcel(httpServletResponse, transferOrderExportVos, Constants.EXPORT_NAME_JXC_TRANSFER_ORDER_BSS, Constants.EXPORT_NAME_JXC_TRANSFER_ORDER_BSS, JXCMdbTransferOrderExportVo.class);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            ResultEntity.error(errCodeEnum.getCode(), errCodeEnum.getDescrible());
            LOGGER.error(response.getMessage());
        }

    }

    private JXCMdbTransferOrderExportVo exportJXCTransferOrderExportConvertTo(JXCMdbTransferOrderExportDTO transferOrderExportDTO) {
        JXCMdbTransferOrderExportVo transferOrderExportVo = new JXCMdbTransferOrderExportVo();
        BeanUtils.copyProperties(transferOrderExportDTO, transferOrderExportVo);
        String orderStr = convertTransferOrderStatus(transferOrderExportVo.getOrderStatus());
        if(transferOrderExportVo.getOrderSource().equals(Constants.GXS_ORDER_SOURCE)){
            transferOrderExportVo.setOrderSource("广联商务");
        }else{
            transferOrderExportVo.setOrderSource("服务商");
        }
        transferOrderExportVo.setServiceProviderName(transferOrderExportDTO.getInServiceProviderName());
        transferOrderExportVo.setOrderStatus(orderStr);
        transferOrderExportVo.setName(transferOrderExportDTO.getBsAddressDTO().getName());
        transferOrderExportVo.setMobile(transferOrderExportDTO.getBsAddressDTO().getMobile());
        transferOrderExportVo.setAddress(transferOrderExportDTO.getBsAddressDTO().getAddress());
        return transferOrderExportVo;
    }

    private String convertTransferOrderStatus(String status) {
        if (status.equals(TransferOrderStatusEnum.ORDER_WAIT_CHECK.getCode())) {
            return TransferOrderStatusEnum.ORDER_WAIT_CHECK.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode())) {
            return TransferOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getCode())) {
            return TransferOrderStatusEnum.ORDER_PARTIALLY_COMPLETED.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_COMPLETED.getCode())) {
            return TransferOrderStatusEnum.ORDER_COMPLETED.getName();
        } else if (status.equals(TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getCode())) {
            return TransferOrderStatusEnum.ORDER_REVIEW_REJECTED.getName();
        }
        return "";
    }

    //    @ApiOperation(value = "调拨订单详情", notes = "调拨订单详情")
//    @GetMapping("getMdbTransferOrder")
//    public ResultEntity<JXCMdbTransferOrderDTO> getTransferOrder(@RequestParam(value ="tranOrderCode")String tranOrderCode) {
//        RpcResponse<JXCMdbTransferOrderDTO> response=jxcTransferOrderRemote.getTransferOrder(tranOrderCode);
//        return ResultEntity.success(response.getResult());
//    }
    @ApiOperation(value = "调拨发货详情", notes = "调拨发货详情")
    @PostMapping("transferShippingDetail")
    public ResultEntity<List<JXCLogisticsDTO>> getTransferShippingDetail(@RequestBody JXCMdbTransferOrderDetailQueryVo transferOrderDetailQueryVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("JxcMdbTransferOrderController::getTransferShippingDetail start dealerUserInfo:{},generateTransferOrderVo:{}", dealerUserInfo, transferOrderDetailQueryVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            LOGGER.info("JxcMdbTransferOrderController::getTransferShippingDetail not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCMdbTransferOrderDetailQueryDTO transferOrderDetailQueryDTO = new JXCMdbTransferOrderDetailQueryDTO();
        transferOrderDetailQueryDTO.setTranOrderCode(transferOrderDetailQueryVo.getTranOrderCode());
        RpcResponse<List<JXCLogisticsDTO>> response = jxcTransferOrderRemote.getTransferShippingDetail(transferOrderDetailQueryDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JxcMdbTransferOrderController::getTransferShippingDetail return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JxcMdbTransferOrderController::getTransferShippingDetail end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "pc端调拨订单发货", notes = "pc端调拨订单发货")
    @PostMapping("commitMdbTransferOrder")
    public ResultEntity<Integer> commitTransferOrder(@RequestBody JXCMdbCommitBsTransferOrderVo transferOrderVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        LOGGER.info("JxcMdbTransferOrderController::commitTransferOrder start dealerUserInfo:{},generateTransferOrderVo:{}", dealerUserInfo, transferOrderVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            LOGGER.info("JxcMdbTransferOrderController::commitTransferOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCMdbCommitTransferOrderDTO jxcMdbCommitTransferOrderDTO = new JXCMdbCommitTransferOrderDTO();
         BeanUtils.copyProperties(transferOrderVo,jxcMdbCommitTransferOrderDTO);
        jxcMdbCommitTransferOrderDTO.setConsumer(dealerUserInfo.getMerchantName());
        RpcResponse<Integer> response = jxcTransferOrderRemote.commitTransferOrder(jxcMdbCommitTransferOrderDTO);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) response.getError();
        if (null != errCodeEnum) {
            LOGGER.info("JxcMdbTransferOrderController::commitTransferOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        LOGGER.info("JxcMdbTransferOrderController::commitTransferOrder end result:{}", response.getResult());
        return ResultEntity.success(response.getResult());
    }

    @ApiOperation(value = "pc端设备明细模板下载", notes = "pc端设备明细模板下载")
    @GetMapping("downloadSnTemplate")
    public void downloadSnTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "调拨sn明细导入模板.xlsx";
            name = "templates/templateTransferSn.xlsx";
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + new String(str.getBytes("GBK"), "iso8859-1") + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            ExcelUtils.downloadExcelTemplate(os, name);

        } catch (Exception e) {
            LOGGER.error("下载Excle模版出错：", e);
        }
        return;
    }

    @ApiOperation(value = "pc端设备明细导入校验", notes = "pc端设备明细导入校验")
    @PostMapping("importEquipmentDetailsCheck")
    public ResultEntity<CheckImportTransferSnDTO> importEquipmentDetailsCheck(@RequestParam(value = "file") MultipartFile files, HttpServletRequest request) {
        ResultEntity<CheckImportTransferSnDTO> responseEntity = new ResultEntity<CheckImportTransferSnDTO>();
        CheckImportTransferSnDTO importedResult = new CheckImportTransferSnDTO();
        Integer totalCount = 0;
        Integer failCount = 0;
        Integer successCount = 0;
        String allowNum = supplyadminProperty.getOpAllowNum();
        Integer opAllowNum = 0;
        if (!com.glsx.cloudframework.core.util.StringUtils.isEmpty(allowNum)) {
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
                    List<TransferSnImportDTO> transferSnImportDTOList = new ArrayList<TransferSnImportDTO>();
                    List<TransferSnImportVo> transferSnImportVoList=new ArrayList<>();
                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, TransferSnImportVo.class, 0, 0);
                    } catch (Exception e) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("请使用有效模板导入数据");
                        LOGGER.warn("导入异常....");
                        return responseEntity;
                    }
                    if (list == null || list.size() == 0) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("请使用有效模板导入数据");
                        LOGGER.warn("导入异常..");
                        return responseEntity;
                    }

                    if (opAllowNum == 0) {
                        opAllowNum = 5000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        LOGGER.warn("导入异常..");
                        return responseEntity;
                    }

                    for (Object statement : list) {
                        TransferSnImportVo bean = (TransferSnImportVo) statement;
                        transferSnImportVoList.add(bean);
                    }
                    TransferSnImportDTO transferSnImport=null;
                    for(TransferSnImportVo transferSnImportVo:transferSnImportVoList){
                        transferSnImport=new TransferSnImportDTO();
                        transferSnImport.setSn(transferSnImportVo.getSn());
                        transferSnImportDTOList.add(transferSnImport);
                    }
                    Map<String, TransferSnImportDTO> transferSnImportDTOMap = new HashMap<String, TransferSnImportDTO>();
                    for (TransferSnImportDTO transferSnImportDTO : transferSnImportDTOList) {
                        transferSnImportDTOMap.put(transferSnImportDTO.getSn(), transferSnImportDTO);
                    }
                    if (StringUtils.isEmpty(transferSnImportDTOList) || transferSnImportDTOList.size() == 0) {
                        return ResultEntity.error(ErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), ErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
                    }
                    if (transferSnImportDTOList.size() > 5000) {
                        ResultEntity.error(ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE.getCode(), ErrorCodeEnum.ERRCODE_MAX_IMPORT_SIZE.getDescrible());
                    }
                    LOGGER.info("JxcMdbTransferOrderController::importEquipmentDetailsCheck 检验开始" + new Date());
                    for (TransferSnImportDTO item : transferSnImportDTOList) {
                        LOGGER.info("JxcMdbTransferOrderController::importEquipmentDetailsCheck handle data item=" + item.toString());
                        boolean add = true;
                        //验证所有字段是否为空
                        if (!StringUtils.isEmpty(item) && this.objCheckMyIsNull(item)) {
                            responseEntity.setReturnCode("1");
                            responseEntity.setMessage(ErrorCodeEnum.INVALID_DEVICE_DATA_FORMAT.getDescrible());
                            LOGGER.warn("导入异常..");
                            return responseEntity;
                        }
                        if (item.getSn().length() > 20) {
                            responseEntity.setReturnCode("1");
                            responseEntity.setMessage(ErrorCodeEnum.ERRCODE_SN_TOO_LONG.getDescrible());
                            LOGGER.warn("导入异常..");
                            return responseEntity;
                        }
                    }
                    importedResult.setTransferSnImportDTOList(transferSnImportDTOList);
                } else {
                    responseEntity.setReturnCode("1");
                    responseEntity.setMessage("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传!");
                    LOGGER.warn("导出结束....");
                    return responseEntity;
                }
            } catch (IOException e) {
                LOGGER.error("导入操作异常" + e);
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
        responseEntity.setData(importedResult);
        return responseEntity;
    }

    //校验字段是否为空
    private Boolean objCheckMyIsNull(TransferSnImportDTO transferSnImportDTO) {
        int i = 0;
        Boolean result = false;
        if (StringUtils.isEmpty(transferSnImportDTO.getSn())) {
            i++;
        }
        if (i > 0) {
            result = true;
        }
        return result;
    }

}
