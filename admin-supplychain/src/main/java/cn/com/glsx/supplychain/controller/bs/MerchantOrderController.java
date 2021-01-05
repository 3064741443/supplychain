package cn.com.glsx.supplychain.controller.bs;

/**
 * @ClassName MerchantOrderController
 * @Author admin
 * @Param
 * @Date 2019/3/5 16:39
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.DealerUserInfoChannelEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import cn.com.glsx.supplychain.util.ExcelReads;
import cn.com.glsx.supplychain.util.ExcelUtils;
import cn.com.glsx.supplychain.util.ExcelXlsxStreamingViewSalesMerchantOrder;
import cn.com.glsx.supplychain.util.ImportedResult;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.MerchantOrderExcelVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.oreframework.web.ui.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

/**
 * 商户订单
 *
 * @author leiming
 */
@RestController
@RequestMapping("merchantOrder")
public class MerchantOrderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private MerchantOrderAdminRemote merchantOrderAdminRemote;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private ExcelXlsxStreamingViewSalesMerchantOrder excelXlsxStreamingViewSalesMerchantOrder;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;


    /**
     * 查询商户订单列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listMerchantOrder")
    public ResultEntity<RpcPagination<MerchantOrder>> listMerchantOrder(@RequestBody RpcPagination<MerchantOrder> pagination) {
        RpcResponse<RpcPagination<MerchantOrder>> response = merchantOrderAdminRemote.listMerchantOrder(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询商户订单列表（根据商户订单号）
     *
     * @param orderNumber
     * @return
     */
    @RequestMapping("getMerchantOrderInfo")
    public ResultEntity<MerchantOrder> getMerchantOrderByMerchantOrderNumber(String orderNumber) {
        RpcResponse<MerchantOrder> response = merchantOrderAdminRemote.getMerchantOrderByMerchantOrderNumber(orderNumber);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 订单审核（审核成功状态变更为待发货）
     *
     * @param merchantOrder
     * @return
     */
    @RequestMapping("orderCheck")
    public ResultEntity<Integer> orderCheck(@RequestBody MerchantOrder merchantOrder) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            merchantOrder.setCreatedBy(user.getRealname());
        } else {
            merchantOrder.setCreatedBy("admin");
        }
        RpcResponse<Integer> response = merchantOrderAdminRemote.checkMerchantOrder(merchantOrder);
        if (response.getError() == null) {
            response.setMessage("审核成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改订单状态
     *
     * @param merchantOrder
     * @return
     */
    @RequestMapping("updateOrderStatus")
    public ResultEntity<Integer> updateOrderStatus(@RequestBody MerchantOrder merchantOrder) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            merchantOrder.setUpdatedBy(user.getRealname());
        } else {
            merchantOrder.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = merchantOrderAdminRemote.updateOrderStatus(merchantOrder);
        if (response.getError() == null) {
            response.setMessage("开票成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部设备类型(DeviceType)
     *
     * @return
     */
    @RequestMapping("listDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType(Integer id) {
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        RpcResponse<List<DeviceType>> response = deviceManagerAdminRemote.listDeviceType(deviceType);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询产品类型根据产品type
     *
     * @param productType
     * @return
     */
    @RequestMapping("getProductType")
    public ResultEntity<ProductType> getProductType(@RequestBody ProductType productType) {
        RpcResponse<ProductType> response = productAdminRemote.getProductType(productType);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物流信息List根据业务Code
     *
     * @param serviceCode
     * @return
     */
    @RequestMapping("getLogisticsInfoListByServiceCode")
    public ResultEntity<List<Logistics>> getLogisticsInfoListByServiceCode(String serviceCode, String type) {
        Logistics logistics = new Logistics();
        logistics.setServiceCode(serviceCode);
        logistics.setType(Byte.valueOf(type));
        logger.info("查询物流信息List根据业务Code的 入参serviceCode：" + serviceCode + "type :" + type);
        RpcResponse<List<Logistics>> response = merchantOrderAdminRemote.getLogisticsInfoListByServiceCode(logistics);
        logger.info("查询物流信息List根据业务Code的结果：");
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改物流信息
     *
     * @param logistics
     * @return
     */
    @RequestMapping("updateById")
    public ResultEntity<Integer> updateById(@RequestBody Logistics logistics) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            logistics.setUpdatedBy(user.getRealname());
        } else {
            logistics.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = merchantOrderAdminRemote.updateById(logistics);
        if (response.getError() == null) {
            response.setMessage("修改物流成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 直接发货(不通过扫码工具)
     *
     * @param merchantOrder
     * @return
     */
    @RequestMapping("sendGoodsUpdateMerchantOrderStatus")
    public ResultEntity<Integer> sendGoodsUpdateMerchantOrderStatu(@RequestBody MerchantOrder merchantOrder) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            merchantOrder.setUpdatedBy(user.getRealname());
        } else {
            merchantOrder.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = merchantOrderAdminRemote.sendGoodsUpdateMerchantOrderStatu(merchantOrder);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * <br>
     * <b>功能：</b>商户订单导入模版<br>
     * <b>日期：</b> 2018-11-16<br>
     *
     * @param response
     */
    @RequestMapping(value = "/merchantdownloadTemplate", method = RequestMethod.GET)
    public void merchantdownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "商户订单列表.xlsx";
            name = "templates/templateMerchantOrder.xlsx";

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
     * @param list 拼接成json 返回
     * @return String
     */
    public String constrJson(List<MerchantOrderImport> list) {

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
            json.append("\"project\":\"" + list.get(i).getProject() + "\",");
            json.append("\"merchantName\":\"" + list.get(i).getMerchantName() + "\",");
            json.append("\"orderNumber\":\"" + list.get(i).getOrderNumber() + "\",");
            json.append("\"productType\":\"" + list.get(i).getProductType() + "\",");
            json.append("\"specification\":\"" + list.get(i).getSpecification() + "\",");
            json.append("\"programme\":\"" + list.get(i).getProgramme() + "\",");
            json.append("\"productCode\":\"" + list.get(i).getProductCode() + "\",");
            json.append("\"productName\":\"" + list.get(i).getProductName() + "\"");
            json.append("\"orderTime\":\"" + list.get(i).getOrderTime() + "\",");
            json.append("\"orderQuantity\":\"" + list.get(i).getOrderQuantity() + "\",");
            json.append("\"shipmentQuantity\":\"" + list.get(i).getShipmentQuantity() + "\",");
            json.append("\"oweQuantity\":\"" + list.get(i).getOweQuantity() + "\",");
            json.append("\"shipmentTime\":\"" + list.get(i).getShipmentTime() + "\",");
            json.append("\"dispatchOrderNumber\":\"" + list.get(i).getDispatchOrderNumber() + "\",");
            json.append("\"company\":\"" + list.get(i).getCompany() + "\",");
            json.append("\"Addressee\":\"" + list.get(i).getAddressee() + "\"");
            json.append("\"mobile\":\"" + list.get(i).getMobile() + "\"");
            json.append("\"addressDetail\":\"" + list.get(i).getAddressDetail() + "\"");
            json.append("\"productRemarks\":\"" + list.get(i).getProductRemarks() + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();

    }

    /**
     * 导入销售订单
     * @param
     * @param
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/merchantOrderImportData")
    public ResponseEntity<ImportedResult> snImportData(@RequestParam(value = "file") MultipartFile files) {
        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
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

                    List<MerchantOrderImport> merchantOrderImportList = new ArrayList<MerchantOrderImport>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, MerchantOrderImport.class, 0, 0);
                    } catch (Exception e) {
                        importedResult.setIsImported(4);
                        importedResult.setCause("请使用有效模板导入数据");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常....");
                        return responseEntity;
                    }
                    if (list == null || list.size() == 0) {
                        importedResult.setIsImported(4);
                        importedResult.setCause("请使用有效模板导入数据");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    if (opAllowNum == 0) {
                        opAllowNum = 2000;
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

                    for (Object device : list) {
                        MerchantOrderImport bean = (MerchantOrderImport) device;
                        merchantOrderImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = merchantOrderAdminRemote.checkImportMerchantOrderList(merchantOrderImportList);
                    List<MerchantOrderImport> SucessList = new ArrayList<MerchantOrderImport>();
                    List<MerchantOrderExport> failList = new ArrayList<MerchantOrderExport>();
                    SucessList = response.getResult().getMerchantOrderImportList();
                    failList = response.getResult().getMerchantOrderExportlist();

                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {

                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    failCount = failList.size();
                    successCount = SucessList.size();

                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        importedResult.setIsImported(1);
                        importedResult.setMsgList(SucessList);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, MerchantOrderExport.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                    }
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

    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/merchantOrderImport")
    @ResponseBody
    public ResultEntity<Integer> merchantOrderImport(@RequestBody List<MerchantOrderImport> merchantOrderImportList) {
        RpcResponse<Integer> response = null;
        ErrorCodeEnum errCodeEnum = null;
        List<MerchantOrderImport> list = new ArrayList<>();
        try {
            *//*for (int i = 0; i < merchantOrderImportList.size(); i++) {
                list.add(merchantOrderImportList.get(i));
                //写入数据库
                response = merchantOrderAdminRemote.importMerchantOrderList(merchantOrderImportList);
                errCodeEnum = (ErrorCodeEnum) response.getError();
                if (errCodeEnum != null) {
                    break;
                }
            }*//*
            //写入数据库
            if (errCodeEnum == null) {
                response = merchantOrderAdminRemote.importMerchantOrderList(merchantOrderImportList);
            } else {
                response.setMessage(errCodeEnum.getDescrible());
            }
        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);

    }*/


    /**
     * 导出商户订单
     *
     * @param response
     * @param
     * @return
     */
    @RequestMapping("exportMerchantOrderExit")
    public ModelAndView exportMerchantOrderExit(HttpServletRequest request, HttpServletResponse response) {
    	
    	@SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
    	String userName = "";
    	if (null != user) {
    		userName = user.getRealname();
        } else {
        	userName = "admin";
        }
    	if(!StringUtils.isEmpty(user))
    	{
    		userName = user.getRealname();
    	}
        
        //转化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MerchantOrder merchantOrder = new MerchantOrder();
        if (request.getParameter("status") != null) {
            Byte status = Byte.valueOf(request.getParameter("status"));
            merchantOrder.setStatus(status);
        }
        if (request.getParameter("channel") != null) {
            Byte channel = Byte.valueOf(request.getParameter("channel"));
            merchantOrder.setChannel(channel);
        }
        if (request.getParameter("orderNumber") != null) {
            merchantOrder.setOrderNumber(request.getParameter("orderNumber"));
        }
        if (request.getParameter("merchantName") != null) {
            merchantOrder.setMerchantName(request.getParameter("merchantName"));
        }
        if (request.getParameter("type") != null) {
            merchantOrder.setType(request.getParameter("type"));
        }
        if (request.getParameter("materialCode") != null) {
            merchantOrder.setMaterialCode(request.getParameter("materialCode"));
        }
        try {
            if (request.getParameter("startDate") != null) {
                try {
                    merchantOrder.setStartDate(df.parse(request.getParameter("startDate")));
                } catch (Exception e) {
                    logger.error("日期格式转换异常：", e);
                }
            }
            if (request.getParameter("endDate") != null) {
                try {
                    merchantOrder.setEndDate(df2.parse(request.getParameter("endDate")));
                } catch (Exception e) {
                    logger.error("日期格式转换异常：", e);
                }
            }
            if (request.getParameter("checkStartDate") != null) {
                try {
                    merchantOrder.setCheckStartDate(df.parse(request.getParameter("checkStartDate")));
                } catch (Exception e) {
                    logger.error("日期格式转换异常：", e);
                }
            }
            if (request.getParameter("checkEndDate") != null) {

                merchantOrder.setCheckEndDate(df2.parse(request.getParameter("checkEndDate")));

            }
        } catch (Exception e) {
            logger.error("日期格式转换异常：", e);
        }
        merchantOrder.setUserName(userName);
        merchantOrder.setPlatName("PLAT");
        RpcResponse<List<MerchantOrderExcelVo>> responseMerchantOrder = merchantOrderAdminRemote.exportMerchantOrderExit(merchantOrder);
        List<MerchantOrderExcelVo> merchantOrderExportList = responseMerchantOrder.getResult();
        List<MerchantOrderExcelVo> merchantOrderExcelVos = merchantOrderExportList.stream().map(merchantOrderExcelVoOne
                -> convertTo(merchantOrderExcelVoOne)).collect(Collectors.toList());
        List<Object> merchantOrderList = new ArrayList<Object>();
        merchantOrderList.addAll(merchantOrderExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", merchantOrderList);
        map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_MERCHANT);
        map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_MERCHANT);
        map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_MERCHANT);   
        return new ModelAndView(excelXlsxStreamingViewSalesMerchantOrder,map);
    }


    /**
     * 导出商户列表（转换对象）
     */
    private MerchantOrderExcelVo convertTo(MerchantOrderExcelVo merchantOrderExcelVoOne) {
        Date date = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

        MerchantOrderExcelVo merchantOrderVo = new MerchantOrderExcelVo();
        merchantOrderVo.setOrderNumber(StringUtils.isEmpty(merchantOrderExcelVoOne.getOrderNumber()) ? "" : merchantOrderExcelVoOne.getOrderNumber());
        merchantOrderVo.setMerchantName(StringUtils.isEmpty(merchantOrderExcelVoOne.getMerchantName()) ? "" : merchantOrderExcelVoOne.getMerchantName());
        merchantOrderVo.setChannel(StringUtils.isEmpty(merchantOrderExcelVoOne.getChannel()) ? "" : merchantOrderExcelVoOne.getChannel());
        merchantOrderVo.setDeviceType(StringUtils.isEmpty(merchantOrderExcelVoOne.getDeviceType()) ? "" : merchantOrderExcelVoOne.getDeviceType());
        merchantOrderVo.setProductCode(StringUtils.isEmpty(merchantOrderExcelVoOne.getProductCode()) ? "" : merchantOrderExcelVoOne.getProductCode());
        merchantOrderVo.setProductName(StringUtils.isEmpty(merchantOrderExcelVoOne.getProductName()) ? "" : merchantOrderExcelVoOne.getProductName());

        merchantOrderVo.setPrice(merchantOrderExcelVoOne.getPrice() == null ? 0 : merchantOrderExcelVoOne.getPrice());
        merchantOrderVo.setOrderQuantity(merchantOrderExcelVoOne.getOrderQuantity() == null ? 0 : merchantOrderExcelVoOne.getOrderQuantity());
        merchantOrderVo.setCheckQuantity(merchantOrderExcelVoOne.getCheckQuantity() == null ? 0 : merchantOrderExcelVoOne.getCheckQuantity());
        merchantOrderVo.setDispatchOrderNumber(StringUtils.isEmpty(merchantOrderExcelVoOne.getDispatchOrderNumber()) ? "" : merchantOrderExcelVoOne.getDispatchOrderNumber());
        merchantOrderVo.setAlreadyShipmentQuantity(merchantOrderExcelVoOne.getAlreadyShipmentQuantity() == null ? 0 : merchantOrderExcelVoOne.getAlreadyShipmentQuantity());
        merchantOrderVo.setShipmentTime(StringUtils.isEmpty(merchantOrderExcelVoOne.getShipmentTime()) ? "" : merchantOrderExcelVoOne.getShipmentTime());
        merchantOrderVo.setShipmentQuantity(merchantOrderExcelVoOne.getShipmentQuantity() == null ? "0" : merchantOrderExcelVoOne.getShipmentQuantity());

        merchantOrderVo.setSignQuantity(merchantOrderExcelVoOne.getSignQuantity() == null ? 0 : merchantOrderExcelVoOne.getSignQuantity());
        merchantOrderVo.setOweQuantity(merchantOrderExcelVoOne.getOweQuantity() == null ? 0 : merchantOrderExcelVoOne.getOweQuantity());
        merchantOrderVo.setTotalAmount(merchantOrderExcelVoOne.getTotalAmount() == null ? 0.0 : merchantOrderExcelVoOne.getTotalAmount());
        merchantOrderVo.setProductRemarks(StringUtils.isEmpty(merchantOrderExcelVoOne.getProductRemarks()) ? "" : merchantOrderExcelVoOne.getProductRemarks());
        merchantOrderVo.setOrderTime(StringUtils.isEmpty(merchantOrderExcelVoOne.getOrderTime()) ? "" : merchantOrderExcelVoOne.getOrderTime());
        merchantOrderVo.setCheckBy(StringUtils.isEmpty(merchantOrderExcelVoOne.getCheckBy()) ? "" : merchantOrderExcelVoOne.getCheckBy());
        merchantOrderVo.setCheckTime(StringUtils.isEmpty(merchantOrderExcelVoOne.getCheckTime()) ? "" : merchantOrderExcelVoOne.getCheckTime());
        merchantOrderVo.setStatus(StringUtils.isEmpty(merchantOrderExcelVoOne.getStatus()) ? "" : merchantOrderExcelVoOne.getStatus());
        merchantOrderVo.setAddressee(StringUtils.isEmpty(merchantOrderExcelVoOne.getAddressee()) ? "" : merchantOrderExcelVoOne.getAddressee());
        merchantOrderVo.setMobile(StringUtils.isEmpty(merchantOrderExcelVoOne.getMobile()) ? "" : merchantOrderExcelVoOne.getMobile());
        merchantOrderVo.setAddressDetail(StringUtils.isEmpty(merchantOrderExcelVoOne.getAddressDetail()) ? "" : merchantOrderExcelVoOne.getAddressDetail());
        merchantOrderVo.setMaterialCode(StringUtils.isEmpty(merchantOrderExcelVoOne.getMaterialCode())?"":merchantOrderExcelVoOne.getMaterialCode());
        merchantOrderVo.setMaterialName(StringUtils.isEmpty(merchantOrderExcelVoOne.getMaterialName())?"":merchantOrderExcelVoOne.getMaterialName());
        merchantOrderVo.setLogisticsDesc(StringUtils.isEmpty(merchantOrderExcelVoOne.getLogisticsDesc())?"":merchantOrderExcelVoOne.getLogisticsDesc());
        merchantOrderVo.setOrderRemark(StringUtils.isEmpty(merchantOrderExcelVoOne.getOrderRemark())?"":merchantOrderExcelVoOne.getOrderRemark());
        merchantOrderVo.setCheckRemark(StringUtils.isEmpty(merchantOrderExcelVoOne.getCheckRemark())?"":merchantOrderExcelVoOne.getCheckRemark());
        merchantOrderVo.setDealerUserName(StringUtils.isEmpty(merchantOrderExcelVoOne.getDealerUserName())?"":merchantOrderExcelVoOne.getDealerUserName());
        return merchantOrderVo;
    }


    /**
     * 查询物流单号
     *
     * @param orderInfoDetail
     * @return
     */
    @RequestMapping("listOrderInfoDetail")
    public ResultEntity<List<OrderInfoDetail>> listOrderInfoDetail(@RequestBody List<OrderInfoDetail> orderInfoDetail) {
        RpcResponse<List<OrderInfoDetail>> response = merchantOrderAdminRemote.listOrderInfoDetail(orderInfoDetail);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据发货单查询订单信息
     *
     * @param
     * @return
     */
    @RequestMapping("getOrderInfoByOrderCode")
    public ResultEntity<OrderInfo> getOrderInfoByOrderCode(String orderCode) {
        RpcResponse<OrderInfo> response = merchantOrderAdminRemote.getOrderInfoByOrderCode(orderCode);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询项目列表
     *
     * @param
     * @return
     */
    @RequestMapping("getSubjectlist")
    public ResultEntity<List<Subject>> getSubjectlist(Subject subject) {
        RpcResponse<List<Subject>> response = merchantOrderAdminRemote.getSubjectlist(subject);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }
}
