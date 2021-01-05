package cn.com.glsx.supplychain.controller.am;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.StatementSellGlwyImportVo;
import cn.com.glsx.supplychain.model.StatementSellJbwyImportVo;
import cn.com.glsx.supplychain.model.StatementSellRenewImportVo;
import cn.com.glsx.supplychain.model.StatementSellRzfbImportVo;
import cn.com.glsx.supplychain.model.am.*;
import cn.com.glsx.supplychain.remote.am.StatementSellAdminRemote;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import cn.com.glsx.supplychain.util.*;
import cn.com.glsx.supplychain.vo.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 对账单-经销对账拆分
 *
 * @author
 */
@RestController
@RequestMapping("statementSellInfo")
public class StatementSellController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoHolder userInfoHolder;
    @Autowired
    private SupplyadminProperty supplyadminProperty;
    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;
    @Autowired
    private StatementSellAdminRemote statementSellAdminRemote;
    @Autowired
    private ExcelXlsxStreamingViewStatementSellSplit excelXlsxStreamingViewStatementSellSplit;
    @Autowired
    private ExcelXlsxStreamingViewStatementAccountJX excelXlsxStreamingViewStatementAccountJX;
    @Autowired
    private ExcelXlsxStreamingViewStatementAccountJR excelXlsxStreamingViewStatementAccountJR;

    /**
     * 生成对账单
     *
     * @param
     * @return
     */
    @RequestMapping("generaterStatementSellRecon")
    public ResultEntity<StatementSellRecon> generaterStatementSellRecon(@RequestBody StatementSellRecon statementSellRecon) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            statementSellRecon.setCreatedBy(user.getRealname());
            statementSellRecon.setUpdatedBy(user.getRealname());
        } else {
            statementSellRecon.setCreatedBy("admin");
            statementSellRecon.setUpdatedBy("admin");
        }
        RpcResponse<StatementSellRecon> response = statementSellAdminRemote.generaterStatementSellRecon(statementSellRecon);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账单列表
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellRecon")
    public ResultEntity<RpcPagination<StatementSellRecon>> pageStatementSellRecon(@RequestBody RpcPagination<StatementSellRecon> pagination) {
    	pagination.getCondition().setMerchantCode(pagination.getCondition().getMerchantName());
    	RpcResponse<RpcPagination<StatementSellRecon>> response = statementSellAdminRemote.pageStatementSellRecon(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账单详情
     *
     * @param
     * @return
     */
    @RequestMapping("getStatementSellRecon")
    public ResultEntity<StatementSellRecon> getStatementSellRecon(@RequestBody StatementSellRecon statementSellRecon) {
        RpcResponse<StatementSellRecon> response = statementSellAdminRemote.getStatementSellRecon(statementSellRecon);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 修改保存经销对账单详情
     *
     * @param
     * @return
     */
    @RequestMapping("saveStatementSellRecon")
    public ResultEntity<StatementSellRecon> saveStatementSellRecon(@RequestBody StatementSellRecon statementSellRecon) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            statementSellRecon.setCreatedBy(user.getRealname());
            statementSellRecon.setUpdatedBy(user.getRealname());
        } else {
            statementSellRecon.setCreatedBy("admin");
            statementSellRecon.setUpdatedBy("admin");
        }
        RpcResponse<StatementSellRecon> response = statementSellAdminRemote.saveStatementSellRecon(statementSellRecon);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账单导出
     *
     * @param
     * @return
     */
	/*@RequestMapping("exportStatementSellRecon")
	public ModelAndView exportStatementSellRecon(HttpServletRequest request, HttpServletResponse response){
		String requestReconCode = request.getParameter("reconCode");
		StatementSellRecon condition = new StatementSellRecon();
		condition.setReconCode(requestReconCode);
		RpcResponse<StatementSellRecon> dbRsp = statementSellAdminRemote.getStatementSellRecon(condition);
		if (dbRsp.getError() == null) {
			dbRsp.setMessage("查询成功");
        } else {
            logger.error(dbRsp.getMessage());
        }
		Map<String, Object> map = new HashMap<>();
		map.put("objs", dbRsp.getResult());
		map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_SELL_SPLIT);
	}*/

    /**
     * 删除对账单详情
     *
     * @param
     * @return
     */
    @RequestMapping("delStatementSellRecon")
    public ResultEntity<Integer> delStatementSellRecon(@RequestBody StatementSellRecon statementSellRecon) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            statementSellRecon.setCreatedBy(user.getRealname());
            statementSellRecon.setUpdatedBy(user.getRealname());
        } else {
            statementSellRecon.setCreatedBy("admin");
            statementSellRecon.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = statementSellAdminRemote.delStatementSellRecon(statementSellRecon);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 拆分对账单
     *
     * @param
     * @return
     */
    @RequestMapping("splitStatementSellRecon")
    public ResultEntity<Integer> splitStatementSellRecon(@RequestBody StatementSellRecon statementSellRecon) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            statementSellRecon.setCreatedBy(user.getRealname());
            statementSellRecon.setUpdatedBy(user.getRealname());
        } else {
            statementSellRecon.setCreatedBy("admin");
            statementSellRecon.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = statementSellAdminRemote.splitStatementSellRecon(statementSellRecon);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 对账单拆分列表
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellSplit")
    public ResultEntity<RpcPagination<StatementSellSplit>> pageStatementSellSplit(@RequestBody RpcPagination<StatementSellSplit> pagination) {
        RpcResponse<RpcPagination<StatementSellSplit>> response = statementSellAdminRemote.pageStatementSellSplit(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 导出对账单拆分列表
     *
     * @param
     * @return
     */
    @RequestMapping("exportStatementSellSplit")
    public ModelAndView exportStatementSellSplit(HttpServletRequest request, HttpServletResponse response) {
        String requestStartDate = request.getParameter("startDate");
        String requestEndDate = request.getParameter("endDate");
        String requestMaterialCode = request.getParameter("materialCode");
        String requestMaterialName = request.getParameter("materialName");
        String requestSaleGroupCode = request.getParameter("saleGroupCode");
        String requestSaleGroupName = request.getParameter("saleGroupName");
        String requestCustomerCode = request.getParameter("customerCode");
        String requeatcustomerName = request.getParameter("customerName");
        String requestWorkType = request.getParameter("WorkType");
        StatementSellSplit condition = new StatementSellSplit();
        if (!StringUtils.isEmpty(requestStartDate)) {
            condition.setStartDate(SupplychainUtils.getDateFromStrYMD(requestStartDate));
        }
        if (!StringUtils.isEmpty(requestEndDate)) {
            condition.setEndDate(SupplychainUtils.getDateFromStrYMD(requestEndDate));
        }
        condition.setMaterialCode(requestMaterialCode);
        condition.setMaterialName(requestMaterialName);
        condition.setWorkType(requestWorkType);
        condition.setSaleGroupCode(requestSaleGroupCode);
        condition.setSaleGroupName(requestSaleGroupName);
        condition.setCustomerCode(requestCustomerCode);
        condition.setCustomerName(requeatcustomerName);

        RpcResponse<List<StatementSellSplitExcelVo>> rpcResponse = statementSellAdminRemote.listStatementSellSplit(condition);
        List<StatementSellSplitExcelVo> statementSellSplitExcelVoList = rpcResponse.getResult();
        List<Object> statementSellSplitList = new ArrayList<Object>();
        statementSellSplitList.addAll(statementSellSplitExcelVoList);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", statementSellSplitList);
        map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_SELL_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_SELL_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_SELL_SPLIT);
        return new ModelAndView(excelXlsxStreamingViewStatementSellSplit, map);
    }

    /**
     * 对账单拆分列表
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellCombile")
    public ResultEntity<RpcPagination<StatementSellCombile>> pageStatementSellCombile(@RequestBody RpcPagination<StatementSellCombile> pagination) {
        RpcResponse<RpcPagination<StatementSellCombile>> response = statementSellAdminRemote.pageStatementSellCombile(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 导出对账单拆分列表
     *
     * @param
     * @return
     */
    @RequestMapping("exportStatementSellCombile")
    public ModelAndView exportStatementSellCombile(HttpServletRequest request, HttpServletResponse response) {
        String requestStartDate = request.getParameter("startDate");
        String requestEndDate = request.getParameter("endDate");
        String requestMaterialCode = request.getParameter("materialCode");
        String requestMaterialName = request.getParameter("materialName");
        String requestSaleGroupCode = request.getParameter("saleGroupCode");
        String requestSaleGroupName = request.getParameter("saleGroupName");
        String requestCustomerCode = request.getParameter("customerCode");
        String requeatcustomerName = request.getParameter("customerName");
        String requestWorkType = request.getParameter("workType");
        StatementSellCombile condition = new StatementSellCombile();
        if (!StringUtils.isEmpty(requestStartDate)) {
            condition.setStartDate(SupplychainUtils.getDateFromStrYMD(requestStartDate));
        }
        if (!StringUtils.isEmpty(requestEndDate)) {
            condition.setEndDate(SupplychainUtils.getDateFromStrYMD(requestEndDate));
        }
        condition.setMaterialCode(requestMaterialCode);
        condition.setMaterialName(requestMaterialName);
        condition.setSaleGroupCode(requestSaleGroupCode);
        condition.setSaleGroupName(requestSaleGroupName);
        condition.setCustomerCode(requestCustomerCode);
        condition.setCustomerName(requeatcustomerName);
        condition.setWorkType(requestWorkType);

        RpcResponse<List<StatementSellSplitExcelVo>> rpcResponse = statementSellAdminRemote.listStatementSellCombile(condition);
        List<StatementSellSplitExcelVo> statementSellSplitExcelVoList = rpcResponse.getResult();
        List<Object> statementSellSplitList = new ArrayList<Object>();
        statementSellSplitList.addAll(statementSellSplitExcelVoList);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", statementSellSplitList);
        map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_SELL_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_SELL_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementSellSplit.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_SELL_SPLIT);
        return new ModelAndView(excelXlsxStreamingViewStatementSellSplit, map);
    }

    /**
     * 获取商户下产品列表 经销对账
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementSellProductSplit")
    public ResultEntity<List<ProductSplitVo>> listStatementSellProductSplit(@RequestBody StatementSellRecon statementSellRecon) {
        RpcResponse<List<ProductSplitVo>> response = statementSellAdminRemote.listStatementSellProductSplit(statementSellRecon);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取商户下产品详情列表 经销对账
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementSellProductSplitDetail")
    public ResultEntity<List<ProductSplitDetailVo>> listStatementSellProductSplitDetail(@RequestBody ProductSplitVo productSplitVo) {
        RpcResponse<List<ProductSplitDetailVo>> response = statementSellAdminRemote.listStatementSellProductSplitDetail(productSplitVo);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 经销对账-续费微信导入数据校验模版
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/statementSellRenewDownloadTemplate", method = RequestMethod.GET)
    public void statementSellRenewDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-经销对账续费微信对账单模版.xlsx";
            name = "templates/templateStatementSellRenew.xlsx";

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
     * 经销对账-续费支付宝导入数据校验模版
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/statementSellRzfbDownloadTemplate", method = RequestMethod.GET)
    public void statementSellRzfbDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-经销对账续费支付宝对账单模版.xlsx";
            name = "templates/templateStatementSellRzfb.xlsx";

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
     * 经销对账-广联无忧导入数据校验模版
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/statementSellGlwyDownloadTemplate", method = RequestMethod.GET)
    public void statementSellGlwyDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-经销对账广联无忧对账单模版.xlsx";
            name = "templates/templateStatementSellGlwy.xlsx";

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
     * 经销对账-驾宝无忧导入数据校验模版
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/statementSellJbwyDownloadTemplate", method = RequestMethod.GET)
    public void statementSellJbwyDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-经销对账驾宝无忧对账单模版.xlsx";
            name = "templates/templateStatementSellJbwy.xlsx";

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
     * 经销对账-续费微信导入数据校验
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementSellRenewImportDataCheck")
    public ResponseEntity<ImportedResult> statementSellRenewImportDataCheck(@RequestParam(value = "file") MultipartFile files) {
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
                    List<StatementSellRenewImport> renewImportList = new ArrayList<StatementSellRenewImport>();
                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, StatementSellRenewImport.class, 0, 0);
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

                    for (Object item : list) {
                        StatementSellRenewImport bean = (StatementSellRenewImport) item;
                        renewImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = statementSellAdminRemote.CheckStatementSellRenewImportData(renewImportList);
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    List<StatementSellRenewImport> SucessList = response.getResult().getStatementSellRenewSuccessList();
                    List<StatementSellRenewImport> failList = response.getResult().getStatementSellRenewFailedList();
                    failCount = failList.size();
                    successCount = SucessList.size();
                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = constrJsonRenew(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, StatementSellRenewImport.class);
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

    /**
     * 经销对账-续费支付宝导入数据校验
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementSellRzfbImportDataCheck")
    public ResponseEntity<ImportedResult> statementSellRzfbImportDataCheck(@RequestParam(value = "file") MultipartFile files) {
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
                    List<StatementSellRzfbImport> rzfbImportList = new ArrayList<StatementSellRzfbImport>();
                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, StatementSellRzfbImport.class, 0, 0);
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

                    for (Object item : list) {
                        StatementSellRzfbImport bean = (StatementSellRzfbImport) item;
                        rzfbImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = statementSellAdminRemote.CheckStatementSellRzfbImportData(rzfbImportList);
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    List<StatementSellRzfbImport> SucessList = response.getResult().getStatementSellRzfbSuccessList();
                    List<StatementSellRzfbImport> failList = response.getResult().getStatementSellRzfbFailedList();
                    failCount = failList.size();
                    successCount = SucessList.size();
                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = constrJsonRzfb(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, StatementSellRzfbImport.class);
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

    /**
     * 经销对账-续费支付宝导入数据校验
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementSellGlwyImportDataCheck")
    public ResponseEntity<ImportedResult> statementSellGlwyImportDataCheck(@RequestParam(value = "file") MultipartFile files) {
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
                    List<StatementSellGlwyImport> glwyImportList = new ArrayList<StatementSellGlwyImport>();
                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, StatementSellGlwyImport.class, 0, 0);
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

                    for (Object item : list) {
                        StatementSellGlwyImport bean = (StatementSellGlwyImport) item;
                        glwyImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = statementSellAdminRemote.CheckStatementSellGlwyImportData(glwyImportList);
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    List<StatementSellGlwyImport> SucessList = response.getResult().getStatementSellGlwySuccessList();
                    List<StatementSellGlwyImport> failList = response.getResult().getStatementSellGlwyFailedList();
                    failCount = failList.size();
                    successCount = SucessList.size();
                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = constrJsonGlwy(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, StatementSellGlwyImport.class);
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

    /**
     * 经销对账-续费支付宝导入数据校验
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementSellJbwyImportDataCheck")
    public ResponseEntity<ImportedResult> statementSellJbwyImportDataCheck(@RequestParam(value = "file") MultipartFile files) {
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
                    List<StatementSellJbwyImport> jbwyImportList = new ArrayList<StatementSellJbwyImport>();
                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, StatementSellJbwyImport.class, 0, 0);
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

                    for (Object item : list) {
                        StatementSellJbwyImport bean = (StatementSellJbwyImport) item;
                        jbwyImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = statementSellAdminRemote.CheckStatementSellJbwyImportData(jbwyImportList);
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    List<StatementSellJbwyImport> SucessList = response.getResult().getStatementSellJbwySuccessList();
                    List<StatementSellJbwyImport> failList = response.getResult().getStatementSellJbwyFailedList();
                    failCount = failList.size();
                    successCount = SucessList.size();
                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = constrJsonJbwy(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, StatementSellJbwyImport.class);
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

    /**
     * 经销对账-续费微信数据导入
     *
     * @param
     * @return
     */
    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/statementSellRenewImport")
    @ResponseBody
    public ResultEntity<Integer> statementSellRenewImport(@RequestBody StatementSellRenewImportVo importVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        for (int i = 0; i < importVo.getListStatementSellRenewImport().size(); i++) {
            importVo.getListStatementSellRenewImport().get(i).setSaleGroupCode(importVo.getSaleCode());
            importVo.getListStatementSellRenewImport().get(i).setSaleGroupName(importVo.getSaleName());
            importVo.getListStatementSellRenewImport().get(i).setSettleCustomerCode(importVo.getSettleCustomerCode());
            importVo.getListStatementSellRenewImport().get(i).setSettleCustomerName(importVo.getSettleCustomerName());
        }
        RpcResponse<Integer> response = statementSellAdminRemote.ImportStatementSellRenewImportData(userName, importVo.getListStatementSellRenewImport());
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费支付宝数据导入
     *
     * @param
     * @return
     */
    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/statementSellRzfbImport")
    @ResponseBody
    public ResultEntity<Integer> statementSellRzfbImport(@RequestBody StatementSellRzfbImportVo importVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        for (int i = 0; i < importVo.getListStatementSellRzfbImport().size(); i++) {
            importVo.getListStatementSellRzfbImport().get(i).setSaleGroupCode(importVo.getSaleCode());
            importVo.getListStatementSellRzfbImport().get(i).setSaleGroupName(importVo.getSaleName());
            importVo.getListStatementSellRzfbImport().get(i).setSettleCustomerCode(importVo.getSettleCustomerCode());
            importVo.getListStatementSellRzfbImport().get(i).setSettleCustomerName(importVo.getSettleCustomerName());
        }
        RpcResponse<Integer> response = statementSellAdminRemote.ImportStatementSellRzfbImportData(userName, importVo.getListStatementSellRzfbImport());
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费广联无忧数据导入
     *
     * @param
     * @return
     */
    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/statementSellGlwyImport")
    @ResponseBody
    public ResultEntity<Integer> statementSellGlwyImport(@RequestBody StatementSellGlwyImportVo importVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        for (int i = 0; i < importVo.getListStatementSellGlwyImport().size(); i++) {
            importVo.getListStatementSellGlwyImport().get(i).setSaleGroupCode(importVo.getSaleCode());
            importVo.getListStatementSellGlwyImport().get(i).setSaleGroupName(importVo.getSaleName());
            importVo.getListStatementSellGlwyImport().get(i).setSettleCustomerCode(importVo.getSettleCustomerCode());
            importVo.getListStatementSellGlwyImport().get(i).setSettleCustomerName(importVo.getSettleCustomerName());
        }
        RpcResponse<Integer> response = statementSellAdminRemote.ImportStatementSellGlwyImportData(userName, importVo.getListStatementSellGlwyImport());
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费驾宝无忧数据导入
     *
     * @param
     * @return
     */
    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/statementSellJbwyImport")
    @ResponseBody
    public ResultEntity<Integer> statementSellJbwyImport(@RequestBody StatementSellJbwyImportVo importVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        for (int i = 0; i < importVo.getListStatementSellJbwyImport().size(); i++) {
            importVo.getListStatementSellJbwyImport().get(i).setSaleGroupCode(importVo.getSaleCode());
            importVo.getListStatementSellJbwyImport().get(i).setSaleGroupName(importVo.getSaleName());
            importVo.getListStatementSellJbwyImport().get(i).setSettleCustomerCode(importVo.getSettleCustomerCode());
            importVo.getListStatementSellJbwyImport().get(i).setSettleCustomerName(importVo.getSettleCustomerName());
        }
        RpcResponse<Integer> response = statementSellAdminRemote.ImportStatementSellJbwyImportData(userName, importVo.getListStatementSellJbwyImport());
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费微信对账分页
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellRenew")
    public ResultEntity<RpcPagination<StatementSellRenew>> pageStatementSellRenew(@RequestBody RpcPagination<StatementSellRenew> pagination) {
        RpcResponse<RpcPagination<StatementSellRenew>> response = statementSellAdminRemote.pageStatementSellRenew(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费支付宝对账分页
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellRzfb")
    public ResultEntity<RpcPagination<StatementSellRzfb>> pageStatementSellRzfb(@RequestBody RpcPagination<StatementSellRzfb> pagination) {
        RpcResponse<RpcPagination<StatementSellRzfb>> response = statementSellAdminRemote.pageStatementSellRzfb(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费广联无忧对账分页
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellGlwy")
    public ResultEntity<RpcPagination<StatementSellGlwy>> pageStatementSellGlwy(@RequestBody RpcPagination<StatementSellGlwy> pagination) {
        RpcResponse<RpcPagination<StatementSellGlwy>> response = statementSellAdminRemote.pageStatementSellGlwy(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费驾宝无忧对账分页
     *
     * @param
     * @return
     */
    @RequestMapping("pageStatementSellJbwy")
    public ResultEntity<RpcPagination<StatementSellJbwy>> pageStatementSellJbwy(@RequestBody RpcPagination<StatementSellJbwy> pagination) {
        RpcResponse<RpcPagination<StatementSellJbwy>> response = statementSellAdminRemote.pageStatementSellJbwy(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费微信对账删除
     *
     * @param
     * @return
     */
    @RequestMapping("delStatementSellRenew")
    public ResultEntity<Integer> delStatementSellRenew(@RequestBody StatementSellRenew record) {
        RpcResponse<Integer> response = statementSellAdminRemote.delStatementSellRenew(record);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费支付宝对账删除
     *
     * @param
     * @return
     */
    @RequestMapping("delStatementSellRzfb")
    public ResultEntity<Integer> delStatementSellRzfb(@RequestBody StatementSellRzfb record) {
        RpcResponse<Integer> response = statementSellAdminRemote.delStatementSellRzfb(record);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-续费广联无忧对账删除
     *
     * @param
     * @return
     */
    @RequestMapping("delStatementSellGlwy")
    public ResultEntity<Integer> delStatementSellGlwy(@RequestBody StatementSellGlwy record) {

        RpcResponse<Integer> response = statementSellAdminRemote.delStatementSellGlwy(record);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 经销对账-驾保广联无忧对账删除
     *
     * @param
     * @return
     */
    @RequestMapping("delStatementSellJbwy")
    public ResultEntity<Integer> delStatementSellJbwy(@RequestBody StatementSellJbwy record) {
        RpcResponse<Integer> response = statementSellAdminRemote.delStatementSellJbwy(record);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    private String constrJsonRenew(List<StatementSellRenewImport> list) {
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
            json.append("\"saleGroupCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSaleGroupCode()) + "\",");
            json.append("\"saleGroupName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSaleGroupName()) + "\",");
            json.append("\"tradeTime\":\"" + SupplychainUtils.formartImportString(list.get(i).getTradeTime()) + "\",");
            json.append("\"pubaccountId\":\"" + SupplychainUtils.formartImportString(list.get(i).getPubaccountId()) + "\",");
            json.append("\"merchantCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getMerchantCode()) + "\",");
            json.append("\"specialMerchantCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSpecialMerchantCode()) + "\",");
            json.append("\"deviceSn\":\"" + SupplychainUtils.formartImportString(list.get(i).getDeviceSn()) + "\",");
            json.append("\"weixinOrderNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getWeixinOrderNo()) + "\",");
            json.append("\"merchantOrderNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getMerchantOrderNo()) + "\",");
            json.append("\"userFlag\":\"" + SupplychainUtils.formartImportString(list.get(i).getUserFlag()) + "\",");
            json.append("\"tradeType\":\"" + SupplychainUtils.formartImportString(list.get(i).getTradeType()) + "\",");
            json.append("\"tradeStatu\":\"" + SupplychainUtils.formartImportString(list.get(i).getTradeStatu()) + "\",");
            json.append("\"payBank\":\"" + SupplychainUtils.formartImportString(list.get(i).getPayBank()) + "\",");
            json.append("\"currencyType\":\"" + SupplychainUtils.formartImportString(list.get(i).getCurrencyType()) + "\",");
            json.append("\"shsettleOrderMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getShsettleOrderMoney()) + "\",");
            json.append("\"vouchersMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getVouchersMoney()) + "\",");
            json.append("\"weixinReturnNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getWeixinReturnNo()) + "\",");
            json.append("\"merchantReturnNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getMerchantReturnNo()) + "\",");
            json.append("\"returnMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getReturnMoney()) + "\",");
            json.append("\"erchangeReturnMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getErchangeReturnMoney()) + "\",");
            json.append("\"returnType\":\"" + SupplychainUtils.formartImportString(list.get(i).getReturnType()) + "\",");
            json.append("\"returnStatu\":\"" + SupplychainUtils.formartImportString(list.get(i).getReturnStatu()) + "\",");
            json.append("\"merchantName\":\"" + SupplychainUtils.formartImportString(list.get(i).getMerchantName()) + "\",");
            json.append("\"chargesMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getChargesMoney()) + "\",");
            json.append("\"feeRate\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getFeeRate()) + "\",");
            json.append("\"orderMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getOrderMoney()) + "\",");
            json.append("\"applyReturnMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getApplyReturnMoney()) + "\",");
            json.append("\"feeRateRemark\":\"" + SupplychainUtils.formartImportString(list.get(i).getFeeRateRemark()) + "\",");
            json.append("\"workOrder\":\"" + SupplychainUtils.formartImportString(list.get(i).getWorkOrder()) + "\",");
            json.append("\"result\":\"" + SupplychainUtils.formartImportString(list.get(i).getResult()) + "\",");
            json.append("\"settleCustomerCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerCode()) + "\",");
            json.append("\"settleCustomerName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerName()) + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }

    private String constrJsonGlwy(List<StatementSellGlwyImport> list) {
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
            json.append("\"saleGroupCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSaleGroupCode()) + "\",");
            json.append("\"saleGroupName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSaleGroupName()) + "\",");
            json.append("\"belongCompany\":\"" + SupplychainUtils.formartImportString(list.get(i).getBelongCompany()) + "\",");
            json.append("\"area\":\"" + SupplychainUtils.formartImportString(list.get(i).getArea()) + "\",");
            json.append("\"shopCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getShopCode()) + "\",");
            json.append("\"shopName\":\"" + SupplychainUtils.formartImportString(list.get(i).getShopName()) + "\",");
            json.append("\"applyNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getApplyNo()) + "\",");
            json.append("\"contractPaymentNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getContractPaymentNo()) + "\",");
            json.append("\"customerCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getCustomerCode()) + "\",");
            json.append("\"customerName\":\"" + SupplychainUtils.formartImportString(list.get(i).getCustomerName()) + "\",");
            json.append("\"rentAttrible\":\"" + SupplychainUtils.formartImportString(list.get(i).getRentAttrible()) + "\",");
            json.append("\"financialDes\":\"" + SupplychainUtils.formartImportString(list.get(i).getFinancialDes()) + "\",");
            json.append("\"vinNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getVinNo()) + "\",");
            json.append("\"engineNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getEngineNo()) + "\",");
            json.append("\"contractDate\":\"" + SupplychainUtils.formartImportString(list.get(i).getContractDate()) + "\",");
            json.append("\"insureYear\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureYear()) + "\",");
            json.append("\"glwyInsureMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getGlwyInsureMoney()) + "\",");
            json.append("\"glwySettleMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getGlwySettleMoney()) + "\",");
            json.append("\"rentProfitMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getRentProfitMoney()) + "\",");
            json.append("\"insureAssureMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getInsureAssureMoney()) + "\",");
            json.append("\"contractStatu\":\"" + SupplychainUtils.formartImportString(list.get(i).getContractStatu()) + "\",");
            json.append("\"financingMaturity\":\"" + SupplychainUtils.formartImportString(list.get(i).getFinancingMaturity()) + "\",");
            json.append("\"shopAttrib\":\"" + SupplychainUtils.formartImportString(list.get(i).getShopAttrib()) + "\",");
            json.append("\"settleStatu\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleStatu()) + "\",");
            json.append("\"billNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getBillNo()) + "\",");
            json.append("\"applyDate\":\"" + SupplychainUtils.formartImportString(list.get(i).getApplyDate()) + "\",");
            json.append("\"workOrder\":\"" + SupplychainUtils.formartImportString(list.get(i).getWorkOrder()) + "\",");
            json.append("\"result\":\"" + SupplychainUtils.formartImportString(list.get(i).getResult()) + "\",");
            json.append("\"settleCustomerCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerCode()) + "\",");
            json.append("\"settleCustomerName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerName()) + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }

    private String constrJsonJbwy(List<StatementSellJbwyImport> list) {
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
            json.append("\"saleGroupCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSaleGroupCode()) + "\",");
            json.append("\"saleGroupName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSaleGroupName()) + "\",");
            json.append("\"no\":\"" + SupplychainUtils.formartImportString(list.get(i).getNo()) + "\",");
            json.append("\"insureNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureNo()) + "\",");
            json.append("\"vechoPrice\":\"" + SupplychainUtils.formartImportString(list.get(i).getVechoPrice()) + "\",");
            json.append("\"vinNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getVinNo()) + "\",");
            json.append("\"vechoUserName\":\"" + SupplychainUtils.formartImportString(list.get(i).getVechoUserName()) + "\",");
            json.append("\"deviceSn\":\"" + SupplychainUtils.formartImportString(list.get(i).getDeviceSn()) + "\",");
            json.append("\"engineNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getEngineNo()) + "\",");
            json.append("\"deviceType\":\"" + SupplychainUtils.formartImportString(list.get(i).getDeviceType()) + "\",");
            json.append("\"version\":\"" + SupplychainUtils.formartImportString(list.get(i).getVersion()) + "\",");
            json.append("\"insureDueTime\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureDueTime()) + "\",");
            json.append("\"money\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getMoney()) + "\",");
            json.append("\"insureReportPractice\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureReportPractice()) + "\",");
            json.append("\"insureStartDate\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureStartDate()) + "\",");
            json.append("\"insureEndDate\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureEndDate()) + "\",");
            json.append("\"princeAgent\":\"" + SupplychainUtils.formartImportString(list.get(i).getPrinceAgent()) + "\",");
            json.append("\"cityAgent\":\"" + SupplychainUtils.formartImportString(list.get(i).getCityAgent()) + "\",");
            json.append("\"handInMerchant\":\"" + SupplychainUtils.formartImportString(list.get(i).getHandInMerchant()) + "\",");
            json.append("\"shopName\":\"" + SupplychainUtils.formartImportString(list.get(i).getShopName()) + "\",");
            json.append("\"preMerchant\":\"" + SupplychainUtils.formartImportString(list.get(i).getPreMerchant()) + "\",");
            json.append("\"area\":\"" + SupplychainUtils.formartImportString(list.get(i).getArea()) + "\",");
            json.append("\"certifiNo\":\"" + SupplychainUtils.formartImportString(list.get(i).getCertifiNo()) + "\",");
            json.append("\"mobile\":\"" + SupplychainUtils.formartImportString(list.get(i).getMobile()) + "\",");
            json.append("\"vechoBrand\":\"" + SupplychainUtils.formartImportString(list.get(i).getVechoBrand()) + "\",");
            json.append("\"vechoType\":\"" + SupplychainUtils.formartImportString(list.get(i).getVechoType()) + "\",");
            json.append("\"vechoSet\":\"" + SupplychainUtils.formartImportString(list.get(i).getVechoSet()) + "\",");
            json.append("\"vechoColor\":\"" + SupplychainUtils.formartImportString(list.get(i).getVechoColor()) + "\",");
            json.append("\"firstMan\":\"" + SupplychainUtils.formartImportString(list.get(i).getFirstMan()) + "\",");
            //    json.append("\"insureMaturity\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureMaturity()) + "\",");
            json.append("\"sellServerMane\":\"" + SupplychainUtils.formartImportString(list.get(i).getSellServerMane()) + "\",");
            json.append("\"jbwyServerMoney\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getJbwyServerMoney()) + "\",");
            json.append("\"mileage\":\"" + SupplychainUtils.formartImportString(list.get(i).getMileage()) + "\",");
            json.append("\"insureCompany\":\"" + SupplychainUtils.formartImportString(list.get(i).getInsureCompany()) + "\",");
            json.append("\"operator\":\"" + SupplychainUtils.formartImportString(list.get(i).getOperator()) + "\",");
            json.append("\"workOrder\":\"" + SupplychainUtils.formartImportString(list.get(i).getWorkOrder()) + "\",");
            json.append("\"result\":\"" + SupplychainUtils.formartImportString(list.get(i).getResult()) + "\",");
            json.append("\"settleCustomerCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerCode()) + "\",");
            json.append("\"settleCustomerName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerName()) + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }

    private String constrJsonRzfb(List<StatementSellRzfbImport> list) {
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
            json.append("\"settleCustomerCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerCode()) + "\",");
            json.append("\"settleCustomerName\":\"" + SupplychainUtils.formartImportString(list.get(i).getSettleCustomerName()) + "\",");
            json.append("\"workOrder\":\"" + SupplychainUtils.formartImportString(list.get(i).getWorkOrder()) + "\",");
            json.append("\"alipayTransactionNumber\":\"" + SupplychainUtils.formartImportString(list.get(i).getAlipayTransactionNumber()) + "\",");
            json.append("\"alipaySerialNumber\":\"" + SupplychainUtils.formartImportString(list.get(i).getAlipaySerialNumber()) + "\",");
            json.append("\"merchantOrderCode\":\"" + SupplychainUtils.formartImportString(list.get(i).getMerchantOrderCode()) + "\",");
            json.append("\"accountType\":\"" + SupplychainUtils.formartImportString(list.get(i).getAccountType()) + "\",");
            json.append("\"income\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getIncome()) + "\",");
            json.append("\"expenditure\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getExpenditure()) + "\",");
            json.append("\"accountBalance\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getAccountBalance()) + "\",");
            json.append("\"serviceFee\":\"" + SupplychainUtils.getSpecifiedDigitsDouble(4, list.get(i).getServiceFee()) + "\",");
            json.append("\"paymentChannel\":\"" + SupplychainUtils.formartImportString(list.get(i).getPaymentChannel()) + "\",");
            json.append("\"signedProducts\":\"" + SupplychainUtils.formartImportString(list.get(i).getSignedProducts()) + "\",");
            json.append("\"counterAccount\":\"" + SupplychainUtils.formartImportString(list.get(i).getCounterAccount()) + "\",");
            json.append("\"counterName\":\"" + SupplychainUtils.formartImportString(list.get(i).getCounterName()) + "\",");
            json.append("\"bankOrderNumber\":\"" + SupplychainUtils.formartImportString(list.get(i).getBankOrderNumber()) + "\",");
            json.append("\"productName\":\"" + SupplychainUtils.formartImportString(list.get(i).getProductName()) + "\",");
            json.append("\"recordedData\":\"" + SupplychainUtils.formartImportString(list.get(i).getRecordedData()) + "\",");
            json.append("\"result\":\"" + SupplychainUtils.formartImportString(list.get(i).getResult()) + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }


    private boolean beFongKongChannel(Byte channel) {
        String param = String.valueOf(channel);
        return param.equals("2") || param.equals("4");
    }

    /**
     * @param @param deviceCode
     * @return StatementSellReconDetailExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 经销对账单对象转换
     * @author
     */
    private StatementSellReconExcelVo StatementSellReconConvertTo(StatementSellRecon statementSellRecon) {
        StatementSellReconExcelVo vo = new StatementSellReconExcelVo();
        vo.setCustomerName(StringUtils.isEmpty(statementSellRecon.getCustomerName()) ? "" : statementSellRecon.getCustomerName());
        vo.setCustomerAddr(StringUtils.isEmpty(statementSellRecon.getCustomerAddr()) ? "" : statementSellRecon.getCustomerAddr());
        vo.setCustomerContact(StringUtils.isEmpty(statementSellRecon.getCustomerContact()) ? "" : statementSellRecon.getCustomerContact());
        vo.setCustomerPhone(StringUtils.isEmpty(statementSellRecon.getCustomerPhone()) ? "" : statementSellRecon.getCustomerPhone());
        vo.setSaleGroupName(StringUtils.isEmpty(statementSellRecon.getSaleGroupName()) ? "" : statementSellRecon.getSaleGroupName());
        vo.setSaleGroupAddr(StringUtils.isEmpty(statementSellRecon.getSaleGroupAddr()) ? "" : statementSellRecon.getSaleGroupAddr());
        vo.setSaleGroupContact(StringUtils.isEmpty(statementSellRecon.getCustomerContact()) ? "" : statementSellRecon.getSaleGroupContact());
        vo.setSaleGroupPhone(StringUtils.isEmpty(statementSellRecon.getSaleGroupPhone()) ? "" : statementSellRecon.getSaleGroupPhone());
        return vo;
    }


    /**
     * @param @param deviceCode
     * @return StatementSellReconDetailExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 广汇集采-经销对账单明细对象转换
     * @author
     */
    private StatementSellReconDetailGuangHuiExcelVo StatementSellReconDetailGuangHuiConvertTo(StatementSellReconDetail statementSellReconDetail) {
        StatementSellReconDetailGuangHuiExcelVo vo = new StatementSellReconDetailGuangHuiExcelVo();
        vo.setMerchantOrderCode(StringUtils.isEmpty(statementSellReconDetail.getMerchantOrderCode()) ? "" : statementSellReconDetail.getMerchantOrderCode());
        vo.setProductName(StringUtils.isEmpty(statementSellReconDetail.getProductName()) ? "" : statementSellReconDetail.getProductName());
        vo.setMaterialCodes(StringUtils.isEmpty(statementSellReconDetail.getMaterialCodes()) ? "" : statementSellReconDetail.getMaterialCodes());
        vo.setMaterialNames(StringUtils.isEmpty(statementSellReconDetail.getMaterialNames()) ? "" : statementSellReconDetail.getMaterialNames());
        vo.setUnitType(StringUtils.isEmpty(statementSellReconDetail.getUnitType()) ? "" : statementSellReconDetail.getUnitType());
        vo.setSendCount(statementSellReconDetail.getSendCount() == null ? 0 : statementSellReconDetail.getSendCount());
        vo.setUintTotalPrice(statementSellReconDetail.getUintTotalPrice() == null ? 0 : statementSellReconDetail.getUintTotalPrice());
        vo.setTotalPrice(statementSellReconDetail.getTotalPrice() == null ? 0 : statementSellReconDetail.getTotalPrice());
        vo.setSendGoodsTime(StringUtils.isEmpty(statementSellReconDetail.getSendGoodsTime()) ? "" : statementSellReconDetail.getSendGoodsTime());
        vo.setLogisticsInfo(StringUtils.isEmpty(statementSellReconDetail.getLogisticsInfo()) ? "" : statementSellReconDetail.getLogisticsInfo());
        return vo;
    }

    /**
     * @param @param deviceCode
     * @return StatementSellReconDetailExcelVo
     * @throws
     * @Title: convertTo
     * @Description:金融风控- 经销对账单明细对象转换
     * @author
     */
    private StatementSellReconDetailFinancialExcelVo StatementSellReconDetailFinancialConvertTo(StatementSellReconDetail statementSellReconDetail) {
        StatementSellReconDetailFinancialExcelVo vo = new StatementSellReconDetailFinancialExcelVo();
        vo.setProductName(StringUtils.isEmpty(statementSellReconDetail.getProductName()) ? "" : statementSellReconDetail.getProductName());
        vo.setUnitType(StringUtils.isEmpty(statementSellReconDetail.getUnitType()) ? "" : statementSellReconDetail.getUnitType());
        vo.setSendCount(statementSellReconDetail.getSendCount() == null ? 0 : statementSellReconDetail.getSendCount());
        vo.setUintTotalPrice(statementSellReconDetail.getUintTotalPrice() == null ? 0 : statementSellReconDetail.getUintTotalPrice());
        vo.setTotalPrice(statementSellReconDetail.getTotalPrice() == null ? 0 : statementSellReconDetail.getTotalPrice());
        vo.setSendGoodsTime(StringUtils.isEmpty(statementSellReconDetail.getSendGoodsTime()) ? "" : statementSellReconDetail.getSendGoodsTime());
        vo.setLogisticsInfo(StringUtils.isEmpty(statementSellReconDetail.getLogisticsInfo()) ? "" : statementSellReconDetail.getLogisticsInfo());
        vo.setPackageOne(StringUtils.isEmpty(statementSellReconDetail.getPackageOne()) ? "" : statementSellReconDetail.getPackageOne());
        vo.setServiceTime(StringUtils.isEmpty(statementSellReconDetail.getServiceTime()) ? "" : statementSellReconDetail.getServiceTime());
        vo.setHardwareUintPrice(statementSellReconDetail.getHardwareUintPrice() == null ? 0 : statementSellReconDetail.getHardwareUintPrice());
        vo.setServiceUintPrice(statementSellReconDetail.getServiceUintPrice() == null ? 0 : statementSellReconDetail.getServiceUintPrice());
        vo.setHardwareTotalPrice(statementSellReconDetail.getHardwareTotalPrice() == null ? 0 : statementSellReconDetail.getHardwareTotalPrice());
        vo.setServiceTotalPrice(statementSellReconDetail.getServiceTotalPrice() == null ? 0 : statementSellReconDetail.getServiceTotalPrice());
        return vo;
    }


//	/**
//	 * 经销对账单-广汇渠道(其它)和金融风控详情导出
//	 *
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(value ="/exportDistributionGuangHui",method = RequestMethod.GET)
//	public ModelAndView exportDistributionGuangHui(HttpServletRequest request, HttpServletResponse response,String reconCode){
//		//String reconCode = request.getParameter("reconCode");
//		StatementSellRecon condition = new StatementSellRecon();
//		condition.setReconCode(reconCode);
//		RpcResponse<StatementSellRecon> rpcResponse = statementSellAdminRemote.getStatementSellRecon(condition);
//		StatementSellRecon statementSellRecon = rpcResponse.getResult();
//		List<StatementSellReconDetail> statementSellReconDetailList=statementSellRecon.getListReconDetail();
//		List<StatementSellReconDetailExcelVo> statementSellReconDetailExcelVoList=statementSellReconDetailList.stream().map(statementSellReconDetail->StatementSellReconDetailconvertTo(statementSellReconDetail)).collect(Collectors.toList());
//		List<Object> statementSellReconDetailExcelVos = new ArrayList<Object>();
//		statementSellReconDetailExcelVos.addAll(statementSellReconDetailExcelVoList);
//		if(statementSellRecon.getChannel()==1) {
//			Map<String, Object> guangHuiMap = new HashMap<>();
//			guangHuiMap.put("objs", statementSellReconDetailExcelVos);
//			guangHuiMap.put("sales",statementSellRecon);
//			guangHuiMap.put(ExcelXlsxStreamingViewStatementAccountJX.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_ACCOUNT_JX);
//			guangHuiMap.put(ExcelXlsxStreamingViewStatementAccountJX.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_ACCOUNT_JX);
//			guangHuiMap.put(ExcelXlsxStreamingViewStatementAccountJX.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_ACCOUNT_JX);
//			try {
//				excelXlsxStreamingViewStatementAccountJX.buildExcelDocument(guangHuiMap, null, null, response);
//			} catch (Exception e) {
//				logger.error("导出异常：" + e.getMessage(), e);
//			}
//			return new ModelAndView(excelXlsxStreamingViewStatementAccountJX);
//		}else if(statementSellRecon.getChannel()==2){
//			Map<String, Object> financialRiskControlMap = new HashMap<>();
//			financialRiskControlMap.put("objs", statementSellReconDetailExcelVos);
//			financialRiskControlMap.put(ExcelXlsxStreamingViewStatementAccountJR.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_ACCOUNT_JR);
//			financialRiskControlMap.put(ExcelXlsxStreamingViewStatementAccountJR.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_ACCOUNT_JR);
//			financialRiskControlMap.put(ExcelXlsxStreamingViewStatementAccountJR.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_ACCOUNT_JR);
//			try {
//				excelXlsxStreamingViewStatementAccountJR.buildExcelDocument(financialRiskControlMap, null, null, response);
//			} catch (Exception e) {
//				logger.error("导出异常：" + e.getMessage(), e);
//			}
//			return new ModelAndView(excelXlsxStreamingViewStatementAccountJR);
//		}else{
//			return null;
//		}
//		//return new ModelAndView(excelXlsxStreamingViewStatementAccountJX,map);
//	}

    /**
     * 使用经销对账单-广汇渠道(其它)和金融风控详情导出
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/exportDistributionGuangHuiAndFinancial", method = RequestMethod.GET)
    public void exportDistributionGuangHuiAndFinancial(HttpServletResponse response, String reconCode) {
        StatementSellRecon condition = new StatementSellRecon();
        condition.setReconCode(reconCode);
        RpcResponse<StatementSellRecon> rpcResponse = statementSellAdminRemote.getStatementSellRecon(condition);
        StatementSellRecon statementSellRecon = rpcResponse.getResult();

        //经销对账表对象转换
        StatementSellReconExcelVo statementSellReconExcelVo = StatementSellReconConvertTo(statementSellRecon);
        List<StatementSellReconExcelVo> statementSellReconExcelVos = new ArrayList<>();
        statementSellReconExcelVos.add(statementSellReconExcelVo);
        List<StatementSellReconDetail> statementSellReconDetailList = statementSellRecon.getListReconDetail();

        //经销对账表-广汇集采详情对象转换
        List<StatementSellReconDetailGuangHuiExcelVo> statementSellReconDetailExcelVoList = statementSellReconDetailList.stream().map(statementSellReconDetail -> StatementSellReconDetailGuangHuiConvertTo(statementSellReconDetail)).collect(Collectors.toList());

        //经销对账表-金融风控详情对象转换
        List<StatementSellReconDetailFinancialExcelVo> statementSellReconDetailFinancialExcelVoList = statementSellReconDetailList.stream().map(statementSellReconDetail -> StatementSellReconDetailFinancialConvertTo(statementSellReconDetail)).collect(Collectors.toList());

        double installTotalPriceCount=0;
        List<StatementSellReconInstall> listReconInstall=statementSellRecon.getListReconInstall();
        for (StatementSellReconInstall statementSellReconInstall : listReconInstall) {
            installTotalPriceCount+=statementSellReconInstall.getServiceTotalPrice();
        }
        double total = 0;
        for (StatementSellReconDetailGuangHuiExcelVo statementSellReconDetailGuangHuiExcelVo : statementSellReconDetailExcelVoList) {
            total += statementSellReconDetailGuangHuiExcelVo.getTotalPrice();
        }

        double hardwaretotalCount=0;
        double serviceTotalCount=0;
        double totalPriceCount=0;
        for (StatementSellReconDetailFinancialExcelVo statementSellReconDetailFinancialExcelVo : statementSellReconDetailFinancialExcelVoList) {
            hardwaretotalCount += statementSellReconDetailFinancialExcelVo.getHardwareTotalPrice();
            serviceTotalCount+=statementSellReconDetailFinancialExcelVo.getServiceTotalPrice();
            totalPriceCount+=statementSellReconDetailFinancialExcelVo.getTotalPrice();
        }

        Map<String, Object> guangHuiMap = new HashMap<>();
        guangHuiMap.put("data1", statementSellReconExcelVos);
        guangHuiMap.put("data2", statementSellReconDetailExcelVoList);
        guangHuiMap.put("total", total);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String reconTimeStart=formatter.format(statementSellRecon.getReconTimeStart()).replaceAll("-","/");
        String reconTimeEnd=formatter.format(statementSellRecon.getReconTimeEnd()).replaceAll("-","/");
        guangHuiMap.put("reconTimeStart1",reconTimeStart);
        guangHuiMap.put("reconTimeEnd1",reconTimeEnd);

        Map<String, Object> financialMap = new HashMap<>();
        financialMap.put("data3", statementSellReconExcelVos);
        financialMap.put("data4", statementSellReconDetailFinancialExcelVoList);
        financialMap.put("data5",listReconInstall);
        financialMap.put("hardwaretotalCount", hardwaretotalCount);
        financialMap.put("serviceTotalCount", serviceTotalCount);
        financialMap.put("totalPriceCount", totalPriceCount);
        financialMap.put("installTotalPriceCount",installTotalPriceCount);
        double summaryPriceCount=installTotalPriceCount+total;
        financialMap.put("summaryPriceCount",summaryPriceCount);
        financialMap.put("reconTimeStart2",reconTimeStart);
        financialMap.put("reconTimeEnd2",reconTimeEnd);

        String GuangHuiTemplateFileName =ExcelReadAndWriteUtil.getPath()+"templates" + File.separator + "templateStatementAccoutJX.xlsx";
        String guangHuiFileName = ExcelReadAndWriteUtil.getPath() + "StatementAccoutJXGuangHui" + System.currentTimeMillis() + ".xlsx";
        //String fileName = ExcelReadAndWriteUtil.getPath() + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        String guangHuiSheetName = Constants.EXPORT_NAME_STATEMENT_ACCOUNT_JX;

        String financialTemplateFileName =ExcelReadAndWriteUtil.getPath()+"templates" + File.separator + "templateStatementAccoutJR.xlsx";
        String financialFileName = ExcelReadAndWriteUtil.getPath() + "StatementAccoutJRFinancial" + System.currentTimeMillis() + ".xlsx";
        //String fileName = ExcelReadAndWriteUtil.getPath() + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        String financialSheetName = Constants.EXPORT_NAME_STATEMENT_ACCOUNT_JR;
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        //EasyExcel.write(fileName, StatementSellReconDetailGuangHuiExcelVo.class).withTemplate(templateFileName).sheet(Constants.EXPORT_NAME_STATEMENT_ACCOUNT_JX).doWrite(statementSellReconDetailExcelVoList);
        if (statementSellRecon.getChannel() == 1 || statementSellRecon.getChannel() == 3) {
            try {
                ExcelReadAndWriteUtil.compositeFillGuangHui(response, guangHuiMap, GuangHuiTemplateFileName, guangHuiFileName, guangHuiSheetName);
            } catch (Exception e) {
                logger.error("导出异常：" + e.getMessage(), e);
            }
        } else if (statementSellRecon.getChannel() == 2 || statementSellRecon.getChannel() == 4) {
            try {
                ExcelReadAndWriteUtil.compositeFillFinancial(response, financialMap, financialTemplateFileName, financialFileName, financialSheetName);
            } catch (Exception e) {
                logger.error("导出异常：" + e.getMessage(), e);
            }
        }
    }
}
