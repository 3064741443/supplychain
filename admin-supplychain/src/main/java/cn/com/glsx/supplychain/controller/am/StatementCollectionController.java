package cn.com.glsx.supplychain.controller.am;

/**
 * @ClassName StatementCollectionController
 * @Author leiming
 * @Param
 * @Date 2019/9/11 15:47
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.StatementCollectionImportVo;
import cn.com.glsx.supplychain.model.am.StatementCollection;
import cn.com.glsx.supplychain.model.am.StatementCollectionImport;
import cn.com.glsx.supplychain.model.am.StatementCollectionSplit;
import cn.com.glsx.supplychain.remote.am.StatementCollectionAdminRemote;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import cn.com.glsx.supplychain.util.*;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.StatementCollectSplitExcelVo;
import com.glsx.cloudframework.core.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.User;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
import org.oreframework.web.ui.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * 对账单-广汇集采
 *
 * @author leiming
 */
@RestController
@RequestMapping("statementCollectionInfo")
public class StatementCollectionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private StatementCollectionAdminRemote statementCollectionAdminRemote;

    @Autowired
    private ExcelXlsxStreamingViewStatementCollectSplit excelXlsxStreamingViewStatementCollectSplit;


    /**
     * 查询对账-广汇集采列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementCollection")
    public ResultEntity<RpcPagination<StatementCollection>> listStatementCollection(@RequestBody RpcPagination<StatementCollection> pagination) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        if (pagination.getCondition() != null) {
            try {
                if (pagination.getCondition().getStartDate() != null) {
                    String strStartDate = df.format(pagination.getCondition().getStartDate());
                    pagination.getCondition().setStartDate(df.parse(strStartDate));
                }
                if (pagination.getCondition().getEndDate() != null) {
                    String strEndDate = df.format(pagination.getCondition().getEndDate());
                    pagination.getCondition().setEndDate(df.parse(strEndDate));
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        RpcResponse<RpcPagination<StatementCollection>> response = statementCollectionAdminRemote.listStatementCollection(pagination);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 删除对账-广汇集采列表以及(拆分结果)
     *
     * @param
     * @return
     */
    @RequestMapping("deleteStatementCollectionByDate")
    public ResultEntity<Integer> deleteStatementCollectionByDate(@RequestBody StatementCollection statementCollection) {
        User user = UserInfoHolder.getUser();
        if (null != user) {
            statementCollection.setUpdatedBy(user.getRealname());
        } else {
            statementCollection.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = statementCollectionAdminRemote.deleteStatementCollectionByDate(statementCollection);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("删除成功");
        } else {
            response.setMessage(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询对账-广汇集采列表根据ID
     *
     * @param
     * @return
     */
    @RequestMapping("getStatementCollectionByid")
    public ResultEntity<StatementCollection> getStatementCollectionByid(Long id) {
        RpcResponse<StatementCollection> response = statementCollectionAdminRemote.getStatementCollectionByid(id);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * <br>
     * <b>功能：</b>对账单-广汇集采导入模版<br>
     * <b>日期：</b> 2018-11-16<br>
     *
     * @param response
     */
    @RequestMapping(value = "/statementCollectionDownloadTemplate", method = RequestMethod.GET)
    public void statementCollectionDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-广汇集采列表.xlsx";
            name = "templates/templateStatementCollection.xlsx";

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
    public String constrJson(List<StatementCollectionImport> list) {

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
            json.append("\"time\":\"" + list.get(i).getTime() + "\",");
            json.append("\"area\":\"" + list.get(i).getArea() + "\",");
            json.append("\"shopName\":\"" + list.get(i).getShopName() + "\",");
            json.append("\"merchant\":\"" + list.get(i).getMerchant() + "\",");
            json.append("\"settleCompany\":\"" + list.get(i).getSettleCompany() + "\",");
            json.append("\"goodsType\":\"" + list.get(i).getGoodsType() + "\",");
            json.append("\"goodsCode\":\"" + list.get(i).getGoodsCode() + "\",");
            json.append("\"price\":\"" + list.get(i).getPrice() + "\",");
            json.append("\"goodsName\":\"" + list.get(i).getGoodsName() + "\",");
            json.append("\"salesQuantity\":\"" + list.get(i).getSalesQuantity() + "\",");
            json.append("\"price\":\"" + list.get(i).getPrice() + "\",");
            json.append("\"rebate\":\"" + list.get(i).getRebate() + "\",");
            json.append("\"serviceType\":\"" + list.get(i).getServiceType() + "\"");        
            json.append("}");
        }
        json.append("]");
        return json.toString();

    }

    /**
     * 导入对账单-广汇集采
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementCollectionImportDataCheck")
    public ResponseEntity<ImportedResult> statementCollectionImportDataCheck(@RequestParam(value = "file") MultipartFile files) {
        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
        List<ImportedResult> importList = new ArrayList<ImportedResult>();
        ImportedResult importedResult = new ImportedResult();

        long totalCount = 0;
        long failedCount = 0;
        long sucessCount = 0;
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

                    List<StatementCollectionImport> statementCollectionImportList = new ArrayList<StatementCollectionImport>();

                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, StatementCollectionImport.class, 0, 0);
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

                    for (Object statement : list) {
                        StatementCollectionImport bean = (StatementCollectionImport) statement;
                        statementCollectionImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = statementCollectionAdminRemote.checkImportStatementCollection(statementCollectionImportList);
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null){
                    	 importedResult.setIsImported(4);
                         importedResult.setCause(errCodeEnum.getDescrible());
                         importList.add(importedResult);
                         responseEntity.setData(importList);
                         return responseEntity;
                    }
                    
                    List<StatementCollectionImport> sucessList = response.getResult().getStatementCollectionSuccessList();
                    List<StatementCollectionImport> failedList = response.getResult().getStatementCollectionFailedList();
                    failedCount = failedList.size();
                    sucessCount = sucessList.size();
                    importedResult.setSuccessCount(sucessList.size());
                    importedResult.setFailCount(failedList.size());
                    importedResult.setTotalCount(totalCount);
                    if(null != sucessList && !sucessList.isEmpty()){
                    	String json = constrJson(sucessList);
                    	 importedResult.setIsImported(1);
                         importedResult.setMsg(json);
                    }
                    if(null != failedList && !failedList.isEmpty()){
                    	String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                    	String url = supplyadminProperty.getDownloadPath() + name;
                    	ExcelUtil.getInstance().exportObj2Excel(url, failedList, StatementCollectionImport.class);
                    	String reUrl = supplyadminProperty.getDomain() + name;
                    	importedResult.setUrl(reUrl);
                    	logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + sucessList.size() + " 失败：" + failedList.size());
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
    @RequestMapping(value = "/statementCollectionImport")
    @ResponseBody
    public ResultEntity<Integer> statementCollectionImport(@RequestBody StatementCollectionImportVo statementCollectionImportVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        RpcResponse<Integer> response = null;
        ErrorCodeEnum errCodeEnum = null;
        try {
            for (int i = 0; i < statementCollectionImportVo.getListImportStatementCollection().size(); i++) {
            	statementCollectionImportVo.getListImportStatementCollection().get(i).setSaleCode(statementCollectionImportVo.getSaleCode());
            	statementCollectionImportVo.getListImportStatementCollection().get(i).setSaleName(statementCollectionImportVo.getSaleName());
            	statementCollectionImportVo.getListImportStatementCollection().get(i).setServiceType(statementCollectionImportVo.getServiceType());
            }
            //写入数据库
            response = statementCollectionAdminRemote.importStatementCollectionImport(userName, statementCollectionImportVo.getListImportStatementCollection());
        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }


    /**
     * 查询对账-金融分控(拆分)列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementCollectionSplit")
    public ResultEntity<RpcPagination<StatementCollectionSplit>> listStatementCollectionSplit(@RequestBody RpcPagination<StatementCollectionSplit> pagination) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        if (pagination.getCondition() != null) {
            try {
                if (pagination.getCondition().getStartDate() != null) {
                    String strStartDate = df.format(pagination.getCondition().getStartDate());
                    pagination.getCondition().setStartDate(df.parse(strStartDate));
                }
                if (pagination.getCondition().getEndDate() != null) {
                    String strEndDate = df.format(pagination.getCondition().getEndDate());
                    pagination.getCondition().setEndDate(df.parse(strEndDate));
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        RpcResponse<RpcPagination<StatementCollectionSplit>> response = statementCollectionAdminRemote.listStatementCollectionSplit(pagination);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
            //response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }


    /**
     * 导出对账单-广汇集采(拆分)
     *
     * @param response
     * @param
     * @return
     */
    @RequestMapping("exportStatementCollectionSplit")
    public ModelAndView exportMerchantOrderExit(HttpServletRequest request, HttpServletResponse response) {
        //转化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StatementCollectionSplit statementCollectionSplit = new StatementCollectionSplit();
        if (request.getParameter("customerCode") != null) {
            statementCollectionSplit.setCustomerCode(request.getParameter("customerCode"));
        }
        if (request.getParameter("customerName") != null) {
            statementCollectionSplit.setCustomerName(request.getParameter("customerName"));
        }
        if (request.getParameter("materialCode") != null) {
            statementCollectionSplit.setMaterialCode(request.getParameter("materialCode"));
        }
        if (request.getParameter("materialName") != null) {
            statementCollectionSplit.setMaterialName(request.getParameter("materialName"));
        }
        if (request.getParameter("saleGroupCode") != null) {
            statementCollectionSplit.setSaleGroupCode(request.getParameter("saleGroupCode"));
        }
        if (request.getParameter("saleGroupName") != null) {
            statementCollectionSplit.setSaleGroupName(request.getParameter("saleGroupName"));
        }
        if (request.getParameter("startDate") != null) {
            try {
                statementCollectionSplit.setStartDate(df.parse(request.getParameter("startDate")));
            } catch (Exception e) {
                logger.error("日期格式转换异常：", e);
            }
        }
        if (request.getParameter("endDate") != null) {
            try {

                String asdasd = request.getParameter("endDate");
                statementCollectionSplit.setEndDate(df2.parse(request.getParameter("endDate")));
            } catch (Exception e) {
                logger.error("日期格式转换异常：", e);
            }
        }

        RpcResponse<List<StatementCollectSplitExcelVo>> responseCollectSplit = statementCollectionAdminRemote.exportStatementCollectionSplitExit(statementCollectionSplit);
        List<StatementCollectSplitExcelVo> statementCollectSplitExportList = responseCollectSplit.getResult();
        List<StatementCollectSplitExcelVo> statementCollectSplitExcelVos = statementCollectSplitExportList.stream().map(statementCollectSplitExcelVoOne -> convertTo(statementCollectSplitExcelVoOne)).collect(Collectors.toList());
        List<Object> statementCollectionSplitList = new ArrayList<Object>();
        statementCollectionSplitList.addAll(statementCollectSplitExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", statementCollectionSplitList);
        map.put(ExcelXlsxStreamingViewStatementCollectSplit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_COLLECTION_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementCollectSplit.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_COLLECTION_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementCollectSplit.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_COLLECTION_SPLIT);
       /* try {
            excelXlsxStreamingViewStatementCollectSplit.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }*/
        return new ModelAndView(excelXlsxStreamingViewStatementCollectSplit,map);
    }

    /**
     * 导出对账单-广汇集采(拆分)对象转换
     */
    private StatementCollectSplitExcelVo convertTo(StatementCollectSplitExcelVo statementCollectSplitExcelVoOne) {
        StatementCollectSplitExcelVo statementCollectSplitExcelVo = new StatementCollectSplitExcelVo();

        statementCollectSplitExcelVo.setNumber(statementCollectSplitExcelVoOne.getNumber());
        statementCollectSplitExcelVo.setBillTypeCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getBillTypeCode()) ? "" : statementCollectSplitExcelVoOne.getBillTypeCode());
        statementCollectSplitExcelVo.setBillTypeName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getBillTypeName()) ? "" : statementCollectSplitExcelVoOne.getBillTypeName());
        statementCollectSplitExcelVo.setBillNumber(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getBillNumber()) ? "" : statementCollectSplitExcelVoOne.getBillNumber());
        statementCollectSplitExcelVo.setTime(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getTime()) ? "" : statementCollectSplitExcelVoOne.getTime());
        statementCollectSplitExcelVo.setSalesCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getSalesCode()) ? "" : statementCollectSplitExcelVoOne.getSalesCode());
        statementCollectSplitExcelVo.setSalesName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getSalesName()) ? "" : statementCollectSplitExcelVoOne.getSalesName());
        statementCollectSplitExcelVo.setCustomerCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getCustomerCode()) ? "" : statementCollectSplitExcelVoOne.getCustomerCode());
        statementCollectSplitExcelVo.setCustomerName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getCustomerName()) ? "" : statementCollectSplitExcelVoOne.getCustomerName());
        statementCollectSplitExcelVo.setSaleGroupCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getSaleGroupCode()) ? "" : statementCollectSplitExcelVoOne.getSaleGroupCode());
        statementCollectSplitExcelVo.setSaleGroupName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getSaleGroupName()) ? "" : statementCollectSplitExcelVoOne.getSaleGroupName());
        statementCollectSplitExcelVo.setCellOne("");
        statementCollectSplitExcelVo.setStatementCurrencyCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getStatementCurrencyCode()) ? "" : statementCollectSplitExcelVoOne.getStatementCurrencyCode());
        statementCollectSplitExcelVo.setStatementCurrencyName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getStatementCurrencyName()) ? "" : statementCollectSplitExcelVoOne.getStatementCurrencyName());
        statementCollectSplitExcelVo.setCellTwo("");
        statementCollectSplitExcelVo.setMaterialCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getMaterialCode()) ? "" : statementCollectSplitExcelVoOne.getMaterialCode());
        statementCollectSplitExcelVo.setMaterialName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getMaterialName()) ? "" : statementCollectSplitExcelVoOne.getMaterialName());
        statementCollectSplitExcelVo.setSalesUnitCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getSalesUnitCode()) ? "" : statementCollectSplitExcelVoOne.getSalesUnitCode());
        statementCollectSplitExcelVo.setSalesUnitName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getSalesUnitName()) ? "" : statementCollectSplitExcelVoOne.getSalesUnitName());

        statementCollectSplitExcelVo.setSalesQuantity(statementCollectSplitExcelVoOne.getSalesQuantity() == null ? 0 : statementCollectSplitExcelVoOne.getSalesQuantity());
        statementCollectSplitExcelVo.setPrice(statementCollectSplitExcelVoOne.getPrice() == null ? 0.0 : SupplychainUtils.getSpecifiedDigitsDouble(4, statementCollectSplitExcelVoOne.getPrice()));
        statementCollectSplitExcelVo.setGift(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getGift()) ? "" : statementCollectSplitExcelVoOne.getGift());
        statementCollectSplitExcelVo.setTaxRate(statementCollectSplitExcelVoOne.getTaxRate() == null ? 0.0 : statementCollectSplitExcelVoOne.getTaxRate());
        statementCollectSplitExcelVo.setTakeGoodsDate(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getTakeGoodsDate()) ? "" : statementCollectSplitExcelVoOne.getTakeGoodsDate());
        statementCollectSplitExcelVo.setStatementOrganizeCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getStatementOrganizeCode()) ? "" : statementCollectSplitExcelVoOne.getStatementOrganizeCode());
        statementCollectSplitExcelVo.setStatementOrganizeName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getStatementOrganizeName()) ? "" : statementCollectSplitExcelVoOne.getStatementOrganizeName());
        statementCollectSplitExcelVo.setReserveType(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getReserveType()) ? "" : statementCollectSplitExcelVoOne.getReserveType());
        statementCollectSplitExcelVo.setWarehouseCode(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getWarehouseCode()) ? "" : statementCollectSplitExcelVoOne.getWarehouseCode());
        statementCollectSplitExcelVo.setWarehouseName(StringUtils.isEmpty(statementCollectSplitExcelVoOne.getWarehouseName()) ? "" : statementCollectSplitExcelVoOne.getWarehouseName());
        statementCollectSplitExcelVo.setCellThree("");
        statementCollectSplitExcelVo.setQuantity(statementCollectSplitExcelVoOne.getQuantity() == null ? 0 : statementCollectSplitExcelVoOne.getQuantity());
        return statementCollectSplitExcelVo;
    }
}
