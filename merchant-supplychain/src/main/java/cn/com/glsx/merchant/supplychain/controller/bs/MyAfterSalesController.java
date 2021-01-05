package cn.com.glsx.merchant.supplychain.controller.bs;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.SupplyadminProperty;
import cn.com.glsx.merchant.supplychain.util.ExcelReads;
import cn.com.glsx.merchant.supplychain.util.ExcelUtils;
import cn.com.glsx.merchant.supplychain.util.ImportedResult;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.enums.AfterSaleOrderTypeEnum;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.DeviceFile;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.OrderInfoDetail;
import cn.com.glsx.supplychain.model.WareHouseInfo;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.remote.bs.*;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Condition;

/**
 * @ClassName 我的售后Controller
 * @Author admin
 * @Param
 * @Date 2019/2/28 10:51
 * @Version
 **/
@RestController
@RequestMapping("myAfterSales")
public class MyAfterSalesController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

 //   @Autowired
 //   protected HttpSession session;


    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private AfterSaleOrderAdminRemote afterSaleOrderAdminRemote;

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private SupplyChainAdminRemote supplyChainAdminRemote;

    @Autowired
    private MerchantOrderAdminRemote merchantOrderAdminRemote;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private AddressAdminRemote addressAdminRemote;


    /**
     * 查询售后订单列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("getAfterOrderlist")
    public ResultEntity<RpcPagination<AfterSaleOrder>> listAfterSaleOrder(@RequestBody RpcPagination<AfterSaleOrder> pagination) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo>dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if(pagination.getCondition() != null){
            pagination.getCondition().setMerchantCode(dul.getResult().getMerchantCode());
        }else {
            AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
            afterSaleOrder.setMerchantCode(dul.getResult().getMerchantCode());
            pagination.setCondition(afterSaleOrder);
        }
        RpcResponse<RpcPagination<AfterSaleOrder>> response = afterSaleOrderAdminRemote.listAfterSaleOrder(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 售后订单修改状态
     *
     * @param
     * @return
     */
    @RequestMapping("UpdateApply")
    public ResultEntity<Integer> updateByOrderNumber(AfterSaleOrder afterSaleOrder) {
       // DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	afterSaleOrder.setCreatedBy(dealerUserInfo.getName());
        afterSaleOrder.setUpdatedBy(dealerUserInfo.getName());
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.updateByOrderNumber(afterSaleOrder);
        if (response.getError() == null) {
            response.setMessage("更改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 新增维修物流信息
     *
     * @param
     * @return
     */
    @RequestMapping("addAfterSaleOrderLogistics")
    public ResultEntity<Integer> addAfterSaleOrderLogistics(Logistics logistics) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//     DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        logistics.setCreatedBy(dealerUserInfo.getName());
        logistics.setUpdatedBy(dealerUserInfo.getName());
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.addAfterSaleOrderLogistics(logistics);
        if (response.getError() == null) {
            response.setMessage("新增成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改维修物流信息
     *
     * @param
     * @return
     */
    @RequestMapping("updateAfterSaleOrderLogistics")
    public ResultEntity<Integer> updateAfterSaleOrderLogistics(Logistics logistics) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        logistics.setUpdatedBy(dealerUserInfo.getName());
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.updateAfterSaleOrderLogistics(logistics);
        if (response.getError() == null) {
            response.setMessage("修改成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物流信息（根据物流对象）
     *
     * @return
     */
    @RequestMapping("getLogistics")
    public ResultEntity<Logistics>getLogistics(@RequestBody Logistics logistics){
        RpcResponse<Logistics>response = afterSaleOrderAdminRemote.getLogistics(logistics);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 查询产品名称
     *
     * @return
     */
    @RequestMapping("getProductNameList")
    public ResultEntity<List<Product>> getProductNameList(@RequestBody Product product) {
        RpcResponse<List<Product>> response = productAdminRemote.getProductList(product);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据产品编号获取产品信息
     *
     * @param
     * @return
     */
    @RequestMapping("getProductByProductCode")
    public ResultEntity<Product> getProductByProductCode(String code) {
        RpcResponse<Product> response = productAdminRemote.getProductByProductCode(code);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
           logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 售后订单(获取商品sn与工厂信息)
     *
     * @param imei
     * @return
     */
    @RequestMapping("getOrderInfoDetailByImei")
    public ResultEntity<OrderInfoDetail> getOrderInfoDetailByImei(String imei) {
        RpcResponse<OrderInfoDetail> response = supplyChainAdminRemote.getOrderInfoDetailByImei(imei);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
           logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 新增售后订单
     *
     * @param afterSaleOrder
     * @return
     */
    @RequestMapping("addAfterSaleOrder")
    public ResultEntity<Integer> addAfterSaleOrder(@RequestBody AfterSaleOrder afterSaleOrder) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        afterSaleOrder.setMerchantCode(dealerUserInfo.getMerchantCode());
        afterSaleOrder.setCreatedBy(dealerUserInfo.getName());
        afterSaleOrder.setUpdatedBy(dealerUserInfo.getName());
        afterSaleOrder.getLogistics().setCreatedBy(dealerUserInfo.getName());
        afterSaleOrder.getLogistics().setUpdatedBy(dealerUserInfo.getName());
        if(afterSaleOrder.getType() == AfterSaleOrderTypeEnum.AFTER_SALE_ORDER_REPAIR.getCode()){
            afterSaleOrder.getLogistics().getReceiveAddress().setCreatedBy(dealerUserInfo.getName());
            afterSaleOrder.getLogistics().getReceiveAddress().setUpdatedBy(dealerUserInfo.getName());
        }
        RpcResponse<Integer> response = afterSaleOrderAdminRemote.add(afterSaleOrder);
        if (response.getError() == null) {
            response.setMessage("新增成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 售后订单(根据仓库ID查询仓库名称)
     *
     * @param id
     * @return
     */
    @RequestMapping("getWarehouseinfo")
    public ResultEntity<WareHouseInfo> getWareHouseInfoById(Integer id) {
        RpcResponse<WareHouseInfo> response = supplyChainAdminRemote.getWareHouseInfoById(id);
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
     * 售后订单（根据订单号获取订售后单对象）
     *
     * @param
     * @return
     */
    @RequestMapping("getAfterSaleOrder")
    public ResultEntity<AfterSaleOrder> getAfterSaleOrder(@RequestBody  AfterSaleOrder afterSaleOrder) {
        RpcResponse<AfterSaleOrder> response = afterSaleOrderAdminRemote.getAfterSaleOrder(afterSaleOrder);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取地址簿信息（回寄的地址信息）
     *
     * @param address
     * @return
     */
    @RequestMapping("getAddressList")
    public ResultEntity<List<Address>> getAddressList(@RequestBody Address address) {
        RpcResponse<List<Address>> response = addressAdminRemote.getAddressList(address);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取售后SN发货记录信息
     *
     * @param afterSaleOrderDetail
     * @return
     */
    @RequestMapping("getAfterSalesSnList")
    public ResultEntity<List<AfterSaleOrderDetail>> getAfterSalesSnList(@RequestBody AfterSaleOrderDetail afterSaleOrderDetail) {
        RpcResponse<List<AfterSaleOrderDetail>> response = afterSaleOrderAdminRemote.getAfterSalesSnList(afterSaleOrderDetail);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据SN或IMEI查询订单详情信息
     *
     * @param imei
     * @return
     */
    @RequestMapping("getDeviceInfoByImeiOrSn")
    public ResultEntity<DeviceInfo> getDeviceInfoByImeiOrSn(String imei) {
        RpcResponse<DeviceInfo> response = afterSaleOrderAdminRemote.getDeviceInfoByImeiOrSn(imei);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据SN查询DeviceFile信息
     *
     * @param sn
     * @return
     */
    @RequestMapping("getDeviceFileBySn")
    public ResultEntity<DeviceFile> getDeviceFileBySn(String sn) {
        RpcResponse<DeviceFile> response = afterSaleOrderAdminRemote.getDeviceFileBySn(sn);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * <br>
     * <b>功能：</b>Sn导入模板下载<br>
     * <b>日期：2019-2-25
     *
     * @param response
     */
    @RequestMapping(value = "/SndownloadTemplate", method = RequestMethod.GET)
    public void SndownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "Sn导入模板.xlsx";
            name = "templates/templateSn.xlsx";

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
    public String snconstrJson(List<SnImport> list) {

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
            json.append("\"sn\":\"" + list.get(i).getSn() + "\",");
            json.append("\"deviceAfterReason\":\"" + list.get(i).getDeviceAfterReason() + "\",");
            json.append("}");
        }
        json.append("]");
        return json.toString();

    }

    /**
     * SN导入返回数据
     *
     * @param files
     * @param
     */
    @ResponseBody
    @RequestMapping(value = "/snImportData")
    public org.oreframework.web.ui.ResponseEntity<ImportedResult> snImportData(@RequestParam(value = "file") MultipartFile files) {
        org.oreframework.web.ui.ResponseEntity<ImportedResult> responseEntity = new org.oreframework.web.ui.ResponseEntity<ImportedResult>();
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

                    List<SnImport> SnList = new ArrayList<SnImport>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, SnImport.class, 0, 0);
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
                        SnImport bean = (SnImport) device;
                        SnList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = afterSaleOrderAdminRemote.checkImportSnList(SnList);
                    List<SnImport> SucessList = new ArrayList<SnImport>();
                    List<SnExport> failList = new ArrayList<SnExport>();
                    SucessList = response.getResult().getSnImportList();
                    failList = response.getResult().getSninvalidList();

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
                        String json = snconstrJson(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, SnExport.class);
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

   /* @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/afterSnImport")
    @ResponseBody
    public ResultEntity<Integer> afterSnImport(String strParam) {
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        String userName = dealerUserInfo.getName();
        String[] strParams = strParam.split("==");
        RpcResponse response = null;
        ErrorCodeEnum errorCodeEnum = null;
        List<SnImport> snImportList = new ArrayList<>();
        try {
            JSONArray json = JSONArray.parseArray(strParams[0]);

            for (int i = 0; i < json.size(); i++) {
                JSONObject jsonObject = json.getJSONObject(i);
                String sn = String.valueOf(jsonObject.get("sn"));

                SnImport snImport = new SnImport();
                snImport.setSn(sn);
                snImportList.add(snImport);

                if (i == 100) {
                    response = afterSaleOrderAdminRemote.importAfterSnList(userName, snImportList);
                    errorCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errorCodeEnum != null) {
                        break;
                    }
                    i = 0;
                    snImportList.clear();
                    snImportList = null;
                }
            }
            //写入数据库
            if (errorCodeEnum == null) {
                response = afterSaleOrderAdminRemote.importAfterSnList(userName, snImportList);
            } else {
                response.setMessage(errorCodeEnum.getDescrible());
            }
        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errorCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }*/
}

