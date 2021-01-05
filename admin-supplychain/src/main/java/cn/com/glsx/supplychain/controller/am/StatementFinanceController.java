package cn.com.glsx.supplychain.controller.am;

/**
 * @ClassName StatementFinanceController
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
import cn.com.glsx.supplychain.enums.FinanceSettleTypeEnum;
import cn.com.glsx.supplychain.model.StatementFinanceImportVo;
import cn.com.glsx.supplychain.model.am.*;
import cn.com.glsx.supplychain.remote.am.StatementFinanceAdminRemote;
import cn.com.glsx.supplychain.remote.bs.DealerUserInfoAdminRemote;
import cn.com.glsx.supplychain.util.*;
import cn.com.glsx.supplychain.vo.CheckImportDataVo;
import cn.com.glsx.supplychain.vo.StatementFinanceSplitExcelVo;

import com.glsx.cloudframework.core.util.StringUtils;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;

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
 * 对账单-金融风控
 *
 * @author leiming
 */
@RestController
@RequestMapping("statementFinanceInfo")
public class StatementFinanceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private StatementFinanceAdminRemote statementFinanceAdminRemote;

    @Autowired
    private ExcelXlsxStreamingViewStatementFinanceSplit excelXlsxStreamingViewStatementFinanceSplit;
    
    @Autowired
	private ExcelXlsxStreamingViewFinanceTools excelXlsxStreamingViewFinanceTools;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;
    
    /**
     * 查询客户列表
     *
     * @param
     * @return
     */
    @RequestMapping("listKcCustomerRelation")
    public ResultEntity<RpcPagination<KcCustomerRelation>> listKcCustomerRelation(@RequestBody RpcPagination<KcCustomerRelation> pagination){
    	
    	if(null == pagination.getCondition()){
    		KcCustomerRelation condition = new KcCustomerRelation();
    		pagination.setCondition(condition);
    		pagination.setPageNum(1);
    		pagination.setPageSize(100);
    	}
    	
    	if(StringUtils.isEmpty(pagination.getCondition().getCustomerCode())){
    		pagination.getCondition().setCustomerCode(null);
    		pagination.getCondition().setCustomerName(null);
    	}
    	
    	 RpcResponse<RpcPagination<KcCustomerRelation>> response = statementFinanceAdminRemote.listKcCustomerRelation(pagination);
    	 if (response.getError() == null) {
             response.setMessage("查询成功");
         } else {
             logger.error(response.getMessage());
         }
         return ResultEntity.result(response);
    }

    /**
     * 查询对账-金融分控列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementFinance")
    public ResultEntity<RpcPagination<StatementFinance>> listStatementFinance(@RequestBody RpcPagination<StatementFinance> pagination) {
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
        RpcResponse<RpcPagination<StatementFinance>> response = statementFinanceAdminRemote.listStatementFinance(pagination);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询对账-金融分控列表根据ID
     *
     * @param
     * @return
     */
    @RequestMapping("getStatementFinanceByid")
    public ResultEntity<StatementFinance> getStatementCollectionByid(Long id) {
        RpcResponse<StatementFinance> response = statementFinanceAdminRemote.getStatementFinanceByid(id);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 删除对账-金融风控列表以及(拆分结果)
     *
     * @param
     * @return
     */
    @RequestMapping("deleteStatementFinanceByDate")
    public ResultEntity<Integer> deleteStatementFinanceByDate(@RequestBody StatementFinance statementFinance) {
        User user = UserInfoHolder.getUser();
        if (null != user) {
            statementFinance.setUpdatedBy(user.getRealname());
        } else {
            statementFinance.setUpdatedBy("admin");
        }
        RpcResponse<Integer> response = statementFinanceAdminRemote.deleteStatementFinanceByDate(statementFinance);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("删除成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询物料信息
     *
     * @param materialCode
     * @return
     */
    @RequestMapping("getMaterialInfoByCode")
    public ResultEntity<List<Material>> getMaterialInfo(String materialCode) {
        Material material = new Material();
        material.setMaterialCode(materialCode);
        List<Material> list = iMaterialDubboService.list(material);
        RpcResponse<List<Material>> response = RpcResponse.success(list);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * <br>
     * <b>功能：</b>对账单-金融风控工具导入模版<br>
     * <b>日期：</b> 2020-04-07<br>
     *
     * @param response
     */
    @RequestMapping(value = "/statementFinanceToolDownloadTemplate", method = RequestMethod.GET)
    public void statementFinanceToolDownloadTemplate(HttpServletResponse response){
    	try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-金融风控解析工具列表.xlsx";
            name = "templates/templateStatementFinanceTool.xlsx";

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
     * <br>
     * <b>功能：</b>对账单-金融风控导入模版<br>
     * <b>日期：</b> 2020-04-07<br>
     *
     * @param response
     */
    @RequestMapping(value = "/statementFinanceDownloadTemplate", method = RequestMethod.GET)
    public void statementFinanceDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "对账单-金融风控列表.xlsx";
            name = "templates/templateStatementFinance.xlsx";

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
    public String constrJson(List<StatementFinanceImport> list) {

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
            json.append("\"workOrder\":\"" + list.get(i).getWorkOrder() + "\",");
            json.append("\"deviceNumberOne\":\"" + list.get(i).getDeviceNumberOne() + "\",");
            json.append("\"deviceNumberTwo\":\"" + list.get(i).getDeviceNumberTwo() + "\",");
            json.append("\"sourceOne\":\"" + list.get(i).getSourceOne() + "\",");
            json.append("\"sourceTwo\":\"" + list.get(i).getSourceTwo() + "\",");
            json.append("\"materialCodeOne\":\"" + list.get(i).getMaterialCodeOne() + "\",");
            json.append("\"materialCodeTwo\":\"" + list.get(i).getMaterialCodeTwo() + "\",");
            json.append("\"deviceTypeOne\":\"" + list.get(i).getDeviceTypeOne() + "\",");
            json.append("\"deviceTypeTwo\":\"" + list.get(i).getDeviceTypeTwo() + "\",");
            json.append("\"deviceQuantity\":\"" + list.get(i).getDeviceQuantity() + "\",");
            json.append("\"gpsType\":\"" + list.get(i).getGpsType() + "\",");
            json.append("\"serviceTime\":\"" + list.get(i).getServiceTime() + "\",");
            json.append("\"sum\":\"" + list.get(i).getSum() + "\",");
            json.append("\"settleByStages\":\"" + list.get(i).getSettleByStages() + "\",");
            json.append("\"isSure\":\"" + list.get(i).getIsSure() + "\",");
            json.append("\"sureTime\":\"" + list.get(i).getSureTime() + "\",");                       
            json.append("\"warehouseCode\":\"" + list.get(i).getWarehouseCode() + "\",");
            json.append("\"warehouseName\":\"" + list.get(i).getWarehouseName() + "\",");           
            json.append("\"customerCode\":\"" + list.get(i).getCustomerCode() + "\",");
            json.append("\"customerName\":\"" + list.get(i).getCustomerName() + "\",");
            json.append("\"hardwareCustomerCode\":\"" + list.get(i).getHardwareCustomerCode() + "\",");
            json.append("\"hardwareCustomerName\":\"" + list.get(i).getHardwareCustomerName() + "\",");
            json.append("\"serviceCustomerCode\":\"" + list.get(i).getServiceCustomerCode() + "\",");
            json.append("\"serviceCustomerName\":\"" + list.get(i).getServiceCustomerName() + "\",");         
            json.append("\"workType\":\"" + list.get(i).getWorkType() + "\",");
            json.append("\"settleInstall\":\"" + list.get(i).getSettleInstall() + "\",");
            json.append("\"contractDate\":\"" + list.get(i).getContractDate() + "\",");
            json.append("\"materialCode\":\"" + list.get(i).getMaterialCode() + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }
    
    /**
     * 导入对账单工具转换-金融风控
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementFinanceToolDataConvert")
    public ResponseEntity<ImportedResult> statementFinanceToolDataConvert(@RequestParam(value = "file") MultipartFile files){
    	
    	ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
    	List<ImportedResult> importList = new ArrayList<ImportedResult>();
    	ImportedResult importedResult = new ImportedResult();
    	
    	long totalCount = 0;
        long failCount = 0;
        long successCount = 0;    
        Integer opAllowNum = 20000;
           
        //这里可以支持多文件上传
        if (files != null){
        	BufferedOutputStream bw = null;
        	 try {
        		 String fileName = files.getOriginalFilename();
        		 //判断是否有文件且是否为图片文件
        		 if (fileName != null && !"".equalsIgnoreCase(fileName.trim())
                         && (FilenameUtils.getExtension(fileName.trim()).equals("xls")
                         || FilenameUtils.getExtension(fileName.trim()).equals("xlsx"))){
        			
        			 // 可以选择把文件保存到服务器,创建输出文件对象
        			 String dwFileName = UUID.randomUUID().toString() + "."
                             + FilenameUtils.getExtension(fileName);
        			 String strUploadFile = supplyadminProperty.getUploadPath() + dwFileName; 
        			 String strUrlFile = supplyadminProperty.getDomain() + dwFileName;
        			 File outFile = new File(strUploadFile);
        			 
        			
        			
        			 List<StatementFinanceTool> sourceDataList = new ArrayList<StatementFinanceTool>();
        			 List<StatementFinanceImport> destDataList = new ArrayList<StatementFinanceImport>();
        			 //获得输入流
        			 InputStream input = files.getInputStream();
        			 List<Object> list = null;
        			 try{
        				 list = ExcelReads.getInstance().readExcel2Objs(input, StatementFinanceTool.class, 0, 0);
        			 }catch (Exception e){
        				 importedResult.setIsImported(4);
                         importedResult.setCause("请使用有效模板导入数据");
                         importList.add(importedResult);
                         responseEntity.setData(importList);
                         logger.warn("导入异常....");
                         sourceDataList = null;
                         return responseEntity;
        			 }
        			 if (list == null || list.size() == 0){
        				 importedResult.setIsImported(4);
                         importedResult.setCause("请使用有效模板导入数据");
                         importList.add(importedResult);
                         responseEntity.setData(importList);
                         logger.warn("导入异常..");
                         sourceDataList = null;
                         return responseEntity;
        			 }
        			 if (opAllowNum == 0) {
                         opAllowNum = 20000;
                     }
        			 totalCount = list.size();
        			 if(totalCount > opAllowNum){
        				 importedResult.setIsImported(3);
                         importedResult.setCause("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                         importList.add(importedResult);
                         responseEntity.setData(importList);
                         logger.warn("导入异常..");
                         sourceDataList = null;
                         return responseEntity;
        			 }
        			 
        			 for (Object data : list) {
        				 StatementFinanceTool bean = (StatementFinanceTool) data;
                         sourceDataList.add(bean);
                     }
        			 
        			 importedResult = convertDataByFinanceTool(importedResult,sourceDataList,destDataList);
        			 if(importedResult.getIsImported() != 0){
        				 importList.add(importedResult);
                         responseEntity.setData(importList);
                         logger.warn("导入异常..");
                         sourceDataList = null;
                         return responseEntity;
        			 }
        			 
        			 Map<String, Object> map = new HashMap<>();
        			 map.put("objs", destDataList);
        			 map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_FINANCE);
        			 map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_FINANCE);
        			 map.put(ExcelXlsxStreamingViewSalesMerchantOrder.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_FINANCE);
        			 FileOutputStream fileOutputStream = null;
        		     fileOutputStream = new FileOutputStream(outFile);
        		     excelXlsxStreamingViewFinanceTools.buildExcelDocument(map, null, fileOutputStream);
        		     importedResult.setUrl(strUrlFile);  
        		     map = null;
        		     destDataList = null;
        		     sourceDataList = null;
        		     importList.add(importedResult);
        		 }
        	 }catch (Exception e) {
        		 logger.error(e.getMessage(),e);
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
        
        responseEntity.setData(importList);
        return responseEntity;
    }
    
    private boolean isRightFormatWorkType(String workType){
    	if(StringUtils.isEmpty(workType))
    		return false;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_N.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_C.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getName()))
    		return true;
        return workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getName());
    }
    
    private boolean isWorkTypeBDISH(String workType){
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_B.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_D.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_I.getName()))
    		return true;
    	if(workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_S.getName()))
    		return true;
        return workType.equals(FinanceSettleTypeEnum.FINANCE_SETTLE_TYPE_H.getName());
    }
    
    private ImportedResult convertDataByFinanceTool(ImportedResult importedResult, 
    		List<StatementFinanceTool> sourceDataList,
    		List<StatementFinanceImport> destDataList){
    	
    	Map<String,KcWarehouseRelation> mapWarehouseRelation = new HashMap<>();
    	Map<String,KcCustomerRelation> mapCustomerRelation = new HashMap<>();
    	Map<String,StatementFinanceExrule> mapFinanceExrule = getStatementFinaceExruleMap();   	 	
    	KcWarehouseRelation relation = null;
    	KcCustomerRelation customerRelation = null;
    	KcCustomerRelation hardCustRelation = null;
    	KcCustomerRelation servCustRelation = null;
    	StatementFinanceImport finaceData = null;
    	StatementFinanceExrule finaceExrule = null;
    	Integer deviceNums = 0;
    	String source = "";
    	for(StatementFinanceTool toolData:sourceDataList){
    		finaceData = new StatementFinanceImport();
    		
    		if(!isRightFormatWorkType(toolData.getWorkType())){
    			importedResult.setIsImported(4);
    			importedResult.setCause("对账类型 格式错误");
    			return importedResult;
    		}
    		if(StringUtils.isEmpty(toolData.getTime()) || 
    				!SupplychainUtils.isValidDate(toolData.getTime())){
    			importedResult.setIsImported(5);
    			importedResult.setCause("月份 列表日期有错误格式");
    			return importedResult;
    		}
    		if(StringUtils.isEmpty(toolData.getWorkOrder()) || 
    				toolData.getWorkOrder().length() > Constants.MAX_WORK_LENGTH){
    			importedResult.setIsImported(6);
    			importedResult.setCause("工单号有为空或者超过最大长度超限的数据");
    			return importedResult;
    		}
    		
    		relation = getWarehouseRelation(toolData.getWarehouseCode(),mapWarehouseRelation);
    		if(null == relation){
    			importedResult.setIsImported(9);
    			importedResult.setCause("发货仓库编码:" + toolData.getWarehouseCode() + " 找不到仓库信息");
    			return importedResult;
    		}
    		
    		customerRelation = getCustomerRelation(toolData.getCustomerCode(), mapCustomerRelation);
    		if(null == customerRelation){
    			importedResult.setIsImported(9);
    			importedResult.setCause("结算客户编码:" + toolData.getCustomerCode() + " 找不到客户信息");
    			return importedResult;
    		}
    		
    		hardCustRelation = getCustomerRelation(toolData.getHardwareCustomerCode(),mapCustomerRelation);
    		if(null == hardCustRelation){
    			importedResult.setIsImported(9);
    			importedResult.setCause("硬件结算客户编码:" + toolData.getHardwareCustomerCode() + " 找不到客户信息");
    			return importedResult;
    		}
    		
    		servCustRelation = getCustomerRelation(toolData.getServiceCustomerCode(),mapCustomerRelation);
    		if(null == servCustRelation){
    			importedResult.setIsImported(9);
    			importedResult.setCause("非硬件结算客户编码:" + toolData.getServiceCustomerCode() + " 找不到客户信息");
    			return importedResult;
    		}
    		
    		if(!isWorkTypeBDISH(toolData.getWorkType())){
    			
        		if(StringUtils.isEmpty(toolData.getDeviceNumberOne()) && 
        				StringUtils.isEmpty(toolData.getDeviceNumberTwo())){
        			importedResult.setIsImported(7);
        			importedResult.setCause("存在主设备和从设备同时为空的数据");
        			return importedResult;
        		}
        		if(!StringUtils.isEmpty(toolData.getServiceTime())){
        			if(!StringUtils.isDigit(toolData.getServiceTime())){
        				importedResult.setIsImported(7);
            			importedResult.setCause("存在业务服务期限不为整数的数据");
            			return importedResult;
        			}
        		}
        		if(!StringUtils.isEmpty(toolData.getSureTime())){
        			if(!StringUtils.isDigit(toolData.getSureTime())){
        				importedResult.setIsImported(8);
        				importedResult.setCause("存在投保期限不为整数的数据");
        				return importedResult;
        			}
        		}
        		if(StringUtils.isEmpty(toolData.getWarehouseCode()) ||
        				toolData.getWarehouseCode().length() > Constants.MAX_WAREHOUSE_CODE_LENGTH){
        			importedResult.setIsImported(8);
    				importedResult.setCause("存在发货仓库编码为空或者仓库编码长度超限的数据");
    				return importedResult;
        		}
        		if(!StringUtils.isEmpty(toolData.getIsSure())){
        			if(!toolData.getIsSure().equals("是") && !toolData.getIsSure().equals("否")){
        				importedResult.setIsImported(8);
        				importedResult.setCause("存在是否投保的选项非是非否");
        				return importedResult;
        			}
        		}
        		if(!StringUtils.isEmpty(toolData.getSettleByStages())){
        			if(!toolData.getSettleByStages().equals("是") && !toolData.getSettleByStages().equals("否")){
        				importedResult.setIsImported(8);
        				importedResult.setCause("存在金融服务是否分期的选项非是非否");
        				return importedResult;
        			}
        		}
        		if(!StringUtils.isEmpty(toolData.getSettleInstall())){
        			if(!toolData.getSettleInstall().equals("是") && !toolData.getSettleInstall().equals("否")){
        				importedResult.setIsImported(8);
        				importedResult.setCause("存在金融服务是否结算安装费的选项非是非否");
        				return importedResult;
        			}
        		}
        		if(!StringUtils.isEmpty(toolData.getContractDate())){
        			if(!SupplychainUtils.isValidTimeFormat(toolData.getContractDate())){
        				importedResult.setIsImported(8);
        				importedResult.setCause("合同期限定义了值 但是非有效时间格式");
        				return importedResult;
        			}
        		}
        		      		
    		}else{
    			if(StringUtils.isEmpty(toolData.getMaterialCode())){
    				importedResult.setIsImported(8);
    				importedResult.setCause("非新装非续费结算所需的特定物料未填写");
    				return importedResult;
    			}
    		}
    		
    		deviceNums = 0;
    		source = "";
    		
    		finaceData.setSourceOne("");
    		finaceData.setMaterialCodeOne("");
    		finaceData.setDeviceTypeOne("");
    		finaceData.setSourceTwo("");
    		finaceData.setMaterialCodeTwo("");
    		finaceData.setDeviceTypeTwo("");
    		finaceData.setServiceTime("");    	
    		finaceData.setSureTime("");
    		finaceData.setServiceTime("");
    		finaceData.setContractDate("");
    		finaceData.setMaterialCode("");
		
    		finaceData.setTime(toolData.getTime());
    		finaceData.setWorkOrder(toolData.getWorkOrder());
    		finaceData.setDeviceNumberOne(StringUtils.isEmpty(toolData.getDeviceNumberOne())?"":toolData.getDeviceNumberOne());
    		finaceData.setDeviceNumberTwo(StringUtils.isEmpty(toolData.getDeviceNumberTwo())?"":toolData.getDeviceNumberTwo());
    		
    		if(!StringUtils.isEmpty(toolData.getDeviceNumberOne())){
    			finaceExrule = mapFinanceExrule.get(toolData.getDeviceNumberOne().substring(0, 2));
    			if(null != finaceExrule){    				
    				finaceData.setDeviceTypeOne(finaceExrule.getMnumName());
    				finaceData.setMaterialCodeOne(finaceExrule.getMaterialCode());
    				if(finaceExrule.getSource().equals("Y")){
    					source = "有源";
    					finaceData.setSourceOne("有源");
    				}else{
    					source = "无源";
    					finaceData.setSourceOne("无源");
    				}
    			}
    			deviceNums++;
    		}
    		if(!StringUtils.isEmpty(toolData.getDeviceNumberTwo())){
    			finaceExrule = mapFinanceExrule.get(toolData.getDeviceNumberTwo().substring(0, 2));
    			if(null != finaceExrule){   
    				finaceData.setDeviceTypeTwo(finaceExrule.getMnumName());
    				finaceData.setMaterialCodeTwo(finaceExrule.getMaterialCode());
    				if(finaceExrule.getSource().equals("Y")){
    					source += "+有源";
    					finaceData.setSourceTwo("有源");
    				}else{
    					source += "+无源";
    					finaceData.setSourceTwo("无源");
    				}
    			}
    			deviceNums++;
    		}
    		finaceData.setDeviceQuantity(deviceNums);
    		finaceData.setGpsType(source);
    		finaceData.setServiceTime(StringUtils.isEmpty(toolData.getServiceTime())?"":toolData.getServiceTime());
    		finaceData.setIsSure(StringUtils.isEmpty(toolData.getIsSure())?"否":toolData.getIsSure());
    		finaceData.setSureTime(StringUtils.isEmpty(toolData.getSureTime())?"":toolData.getSureTime());
    		finaceData.setSum(toolData.getSum());
    		finaceData.setWarehouseCode(StringUtils.isEmpty(toolData.getWarehouseCode())?"":toolData.getWarehouseCode());
    		finaceData.setWarehouseName((relation==null)?"":relation.getWarehouseName());
    		finaceData.setCustomerCode(StringUtils.isEmpty(toolData.getCustomerCode())?"":toolData.getCustomerCode());
    		finaceData.setCustomerName((customerRelation==null)?"":customerRelation.getCustomerName());
    		finaceData.setHardwareCustomerCode(StringUtils.isEmpty(toolData.getHardwareCustomerCode())?"":toolData.getHardwareCustomerCode());
    		finaceData.setHardwareCustomerName((hardCustRelation==null)?"":hardCustRelation.getCustomerName());
    		finaceData.setServiceCustomerCode(StringUtils.isEmpty(toolData.getServiceCustomerCode())?"":toolData.getServiceCustomerCode());
    		finaceData.setServiceCustomerName((servCustRelation==null)?"":servCustRelation.getCustomerName());
    		finaceData.setSettleByStages(StringUtils.isEmpty(toolData.getSettleByStages())?"否":toolData.getSettleByStages());
    		finaceData.setWorkType(toolData.getWorkType());
    		if(!StringUtils.isEmpty(toolData.getContractDate()))
    		{
    			finaceData.setContractDate(toolData.getContractDate());
    		}else{
    			finaceData.setContractDate("");
    		}
    		if(!StringUtils.isEmpty(toolData.getMaterialCode()))
    		{
    			finaceData.setMaterialCode(toolData.getMaterialCode());	
    		}else{
    			finaceData.setMaterialCode("");
    		}
    		finaceData.setSettleInstall(StringUtils.isEmpty(toolData.getSettleInstall())?"是":toolData.getSettleInstall());	
    		finaceData.setFailDesc("");
    		destDataList.add(finaceData);
    	}
    	importedResult.setIsImported(0);
    	importedResult.setCause("");
    	
    	mapFinanceExrule = null;
    	mapWarehouseRelation = null;
    	return importedResult;
    }
    
    private Map<String,StatementFinanceExrule> getStatementFinaceExruleMap(){
    	StatementFinanceExrule condition = new StatementFinanceExrule();
    	condition.setDeletedFlag("N");
    	RpcResponse<Map<String,StatementFinanceExrule>> rsp = statementFinanceAdminRemote.getStatementFinanceExruleMap(condition);
    	return rsp.getResult();
    }
    
    private KcWarehouseRelation getWarehouseRelation(String warehouseCode,Map<String,KcWarehouseRelation> mapWarehouseRelation){
    	
    	if(StringUtils.isEmpty(warehouseCode)){
    		return null;
    	}
    	KcWarehouseRelation relation = mapWarehouseRelation.get(warehouseCode);
    	if(null != relation){
    		return relation;
    	}
    	KcWarehouseRelation condition = new KcWarehouseRelation();
    	condition.setWarehouseCode(warehouseCode);
    	condition.setDeletedFlag("N");
    	RpcResponse<KcWarehouseRelation> rsp = statementFinanceAdminRemote.getWarehouseRelationByCode(condition);
    	KcWarehouseRelation resultBody = rsp.getResult();
    	if(null != resultBody){
    		mapWarehouseRelation.put(warehouseCode, resultBody);
    	}
    	return rsp.getResult();
    }
    
    private KcCustomerRelation getCustomerRelation(String customerCode,Map<String,KcCustomerRelation> mapCustomerRelation){
    	
    	if(StringUtils.isEmpty(customerCode)){
    		return null;
    	}
    	KcCustomerRelation relation = mapCustomerRelation.get(customerCode);
    	if(null != relation){
    		return relation;
    	}
    	KcCustomerRelation condition = new KcCustomerRelation();
    	condition.setCustomerCode(customerCode);
    	condition.setDeletedFlag("N");
    	RpcResponse<KcCustomerRelation> rsp = statementFinanceAdminRemote.getCustomerRelationByCode(condition);
    	KcCustomerRelation resultBody = rsp.getResult();
    	if(null != resultBody){
    		mapCustomerRelation.put(customerCode, resultBody);
    	}
    	return rsp.getResult();
    }
    
    
    /**
     * 导入对账单-金融风控
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/statementFinanceImportDataCheck")
    public ResponseEntity<ImportedResult> statementFinanceImportDataCheck(@RequestParam(value = "file") MultipartFile files) {
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

                    List<StatementFinanceImport> statementFinanceImportList = new ArrayList<StatementFinanceImport>();

                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, StatementFinanceImport.class, 0, 0);
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

                    for (Object device : list) {
                        StatementFinanceImport bean = (StatementFinanceImport) device;
                        statementFinanceImportList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = statementFinanceAdminRemote.checkImportStatementFinance(statementFinanceImportList);
                    List<StatementFinanceImport> SucessList = new ArrayList<StatementFinanceImport>();
                    List<StatementFinanceImport> failList = new ArrayList<StatementFinanceImport>();
                    SucessList = response.getResult().getStatementFinanceSuccessList();
                    failList = response.getResult().getStatementFinanceFailedList();

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
                        String json = constrJson(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, StatementFinanceImport.class);
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
    @RequestMapping(value = "/statementFinanceImport")
    @ResponseBody
    public ResultEntity<Integer> statementFinanceImport(@RequestBody StatementFinanceImportVo statementFinanceImportVo) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        RpcResponse<Integer> response = null;
        ErrorCodeEnum errCodeEnum = null;
        List<StatementFinanceImport> list = new ArrayList<>();
        try {
            for (int i = 0; i < statementFinanceImportVo.getListStatementFinanceImport().size(); i++) {
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setSaleCode(statementFinanceImportVo.getSaleCode());
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setSaleName(statementFinanceImportVo.getSaleName());
            	/*statementFinanceImportVo.getListStatementFinanceImport().get(i).setCustomerCode(statementFinanceImportVo.getCustomerCode());
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setCustomerName(statementFinanceImportVo.getCustomerName());
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setHardwareCustomerCode(statementFinanceImportVo.getHardwareCustomerCode());
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setHardwareCustomerName(statementFinanceImportVo.getHardwareCustomerName());
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setServiceCustomerCode(statementFinanceImportVo.getServiceCustomerCode());
            	statementFinanceImportVo.getListStatementFinanceImport().get(i).setServiceCustomerName(statementFinanceImportVo.getServiceCustomerName());*/
                list.add(statementFinanceImportVo.getListStatementFinanceImport().get(i));
            }
            //写入数据库
            response = statementFinanceAdminRemote.importStatementFinanceImport(userName, list);
        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 查询对账-金融分控(汇总)列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementFinanceCombile")
    public ResultEntity<RpcPagination<StatementFinanceCombile>> listStatementFinanceCombile(@RequestBody RpcPagination<StatementFinanceCombile> pagination){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
    	if (pagination.getCondition() != null){
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
                e.printStackTrace();
            }
    	}
    	
    	RpcResponse<RpcPagination<StatementFinanceCombile>> response = statementFinanceAdminRemote.listStatementFinanceCombile(pagination);
    	if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询对账-金融分控(拆分)列表（分页）
     *
     * @param
     * @return
     */
    @RequestMapping("listStatementFinanceSplit")
    public ResultEntity<RpcPagination<StatementFinanceSplit>> listStatementFinanceSplit(@RequestBody RpcPagination<StatementFinanceSplit> pagination) {
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
                e.printStackTrace();
            }
        }
        RpcResponse<RpcPagination<StatementFinanceSplit>> response = statementFinanceAdminRemote.listStatementFinanceSplit(pagination);
        //ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }
    
    /**
     * 导出对账单-金融风控(汇总)
     *
     * @param response
     * @param
     * @return
     */
    @RequestMapping("exportStatementFinanceCombile")
    public ModelAndView exportStatementFinanceCombile(HttpServletRequest request, HttpServletResponse response) {
        //转化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StatementFinanceCombile statementFinanceCombile = new StatementFinanceCombile();
        if (request.getParameter("workType") != null) {
            statementFinanceCombile.setWorkType(request.getParameter("workType"));
        }
        if (request.getParameter("customerCode") != null) {
        	statementFinanceCombile.setCustomerCode(request.getParameter("customerCode"));
        }
        if (request.getParameter("customerName") != null) {
        	statementFinanceCombile.setCustomerName(request.getParameter("customerName"));
        }
        if (request.getParameter("materialCode") != null) {
        	statementFinanceCombile.setMaterialCode(request.getParameter("materialCode"));
        }
        if (request.getParameter("materialName") != null) {
        	statementFinanceCombile.setMaterialName(request.getParameter("materialName"));
        }
        if (request.getParameter("startDate") != null) {
            try {
            	statementFinanceCombile.setStartDate(df.parse(request.getParameter("startDate")));
            } catch (Exception e) {
                logger.error("日期格式转换异常：", e);
            }
        }
        if (request.getParameter("endDate") != null) {
            try {

                String asdasd = request.getParameter("endDate");
                statementFinanceCombile.setEndDate(df.parse(asdasd));
            } catch (Exception e) {
                logger.error("日期格式转换异常：", e);
            }
        }

        RpcResponse<List<StatementFinanceSplitExcelVo>> responseFinanceCombile = statementFinanceAdminRemote.exportStatementFinanceCombileExit(statementFinanceCombile);
        List<StatementFinanceSplitExcelVo> statementFinanceSplitExcelVoList = responseFinanceCombile.getResult();
        List<StatementFinanceSplitExcelVo> statementFinanceSplitExcelVos = statementFinanceSplitExcelVoList.stream().map(statementFinanceSplitExcelVoOne -> convertTo(statementFinanceSplitExcelVoOne)).collect(Collectors.toList());
        List<Object> statementFinanceSplitList = new ArrayList<Object>();
        statementFinanceSplitList.addAll(statementFinanceSplitExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", statementFinanceSplitList);
        map.put(ExcelXlsxStreamingViewStatementFinanceSplit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_FINANCE_COMBILE);
        map.put(ExcelXlsxStreamingViewStatementFinanceSplit.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_FINANCE_COMBILE);
        map.put(ExcelXlsxStreamingViewStatementFinanceSplit.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_FINANCE_COMBILE);
        
        return new ModelAndView(excelXlsxStreamingViewStatementFinanceSplit,map);
    }


    /**
     * 导出对账单-金融风控(拆分)
     *
     * @param response
     * @param
     * @return
     */
    @RequestMapping("exportStatementFinanceSplit")
    public ModelAndView exportStatementFinanceSplit(HttpServletRequest request, HttpServletResponse response) {
        //转化时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StatementFinanceSplit statementFinanceSplit = new StatementFinanceSplit();
        if (request.getParameter("customerCode") != null) {
            statementFinanceSplit.setCustomerCode(request.getParameter("customerCode"));
        }
        if (request.getParameter("customerName") != null) {
            statementFinanceSplit.setCustomerName(request.getParameter("customerName"));
        }
        if (request.getParameter("materialCode") != null) {
            statementFinanceSplit.setMaterialCode(request.getParameter("materialCode"));
        }
        if (request.getParameter("materialName") != null) {
            statementFinanceSplit.setMaterialName(request.getParameter("materialName"));
        }
        if (request.getParameter("startDate") != null) {
            try {
                statementFinanceSplit.setStartDate(df.parse(request.getParameter("startDate")));
            } catch (Exception e) {
                logger.error("日期格式转换异常：", e);
            }
        }
        if (request.getParameter("endDate") != null) {
            try {

                String asdasd = request.getParameter("endDate");
                statementFinanceSplit.setEndDate(df2.parse(request.getParameter("endDate")));
            } catch (Exception e) {
                logger.error("日期格式转换异常：", e);
            }
        }

        RpcResponse<List<StatementFinanceSplitExcelVo>> responseFinanceSplit = statementFinanceAdminRemote.exportStatementFinanceSplitExit(statementFinanceSplit);
        List<StatementFinanceSplitExcelVo> statementFinanceSplitExcelVoList = responseFinanceSplit.getResult();
        List<StatementFinanceSplitExcelVo> statementFinanceSplitExcelVos = statementFinanceSplitExcelVoList.stream().map(statementFinanceSplitExcelVoOne -> convertTo(statementFinanceSplitExcelVoOne)).collect(Collectors.toList());
        List<Object> statementFinanceSplitList = new ArrayList<Object>();
        statementFinanceSplitList.addAll(statementFinanceSplitExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", statementFinanceSplitList);
        map.put(ExcelXlsxStreamingViewStatementFinanceSplit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_STATEMENT_FINANCE_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementFinanceSplit.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_STATEMENT_FINANCE_SPLIT);
        map.put(ExcelXlsxStreamingViewStatementFinanceSplit.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_STATEMENT_FINANCE_SPLIT);
        
        return new ModelAndView(excelXlsxStreamingViewStatementFinanceSplit,map);
    }

    /**
     * 导出对账单-金融风控(拆分)对象转换
     */
    private StatementFinanceSplitExcelVo convertTo(StatementFinanceSplitExcelVo statementFinanceSplitExcelVoOne) {
        StatementFinanceSplitExcelVo statementFinanceSplitExcelVo = new StatementFinanceSplitExcelVo();
        statementFinanceSplitExcelVo.setNumber(statementFinanceSplitExcelVoOne.getNumber());
        statementFinanceSplitExcelVo.setBillTypeCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getBillTypeCode()) ? "" : statementFinanceSplitExcelVoOne.getBillTypeCode());
        statementFinanceSplitExcelVo.setBillTypeName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getBillTypeName()) ? "" : statementFinanceSplitExcelVoOne.getBillTypeName());
        statementFinanceSplitExcelVo.setBillNumber(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getBillNumber()) ? "" : statementFinanceSplitExcelVoOne.getBillNumber());
        statementFinanceSplitExcelVo.setTime(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getTime()) ? "" : statementFinanceSplitExcelVoOne.getTime());
        statementFinanceSplitExcelVo.setSalesCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getSalesCode()) ? "" : statementFinanceSplitExcelVoOne.getSalesCode());
        statementFinanceSplitExcelVo.setSalesName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getSalesName()) ? "" : statementFinanceSplitExcelVoOne.getSalesName());
        statementFinanceSplitExcelVo.setCustomerCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getCustomerCode()) ? "" : statementFinanceSplitExcelVoOne.getCustomerCode());
        statementFinanceSplitExcelVo.setCustomerName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getCustomerName()) ? "" : statementFinanceSplitExcelVoOne.getCustomerName());
        statementFinanceSplitExcelVo.setSaleGroupCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getSaleGroupCode()) ? "" : statementFinanceSplitExcelVoOne.getSaleGroupCode());
        statementFinanceSplitExcelVo.setSaleGroupName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getSaleGroupName()) ? "" : statementFinanceSplitExcelVoOne.getSaleGroupName());
        statementFinanceSplitExcelVo.setCellOne("");
        statementFinanceSplitExcelVo.setStatementCurrencyCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getStatementCurrencyCode()) ? "" : statementFinanceSplitExcelVoOne.getStatementCurrencyCode());
        statementFinanceSplitExcelVo.setStatementCurrencyName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getStatementCurrencyName()) ? "" : statementFinanceSplitExcelVoOne.getStatementCurrencyName());
        statementFinanceSplitExcelVo.setCellTwo("");
        statementFinanceSplitExcelVo.setMaterialCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getMaterialCode()) ? "" : statementFinanceSplitExcelVoOne.getMaterialCode());
        statementFinanceSplitExcelVo.setMaterialName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getMaterialName()) ? "" : statementFinanceSplitExcelVoOne.getMaterialName());
        statementFinanceSplitExcelVo.setSalesUnitCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getSalesUnitCode()) ? "" : statementFinanceSplitExcelVoOne.getSalesUnitCode());
        statementFinanceSplitExcelVo.setSalesUnitName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getSalesUnitName()) ? "" : statementFinanceSplitExcelVoOne.getSalesUnitName());

        statementFinanceSplitExcelVo.setSalesQuantity(statementFinanceSplitExcelVoOne.getSalesQuantity() == null ? 0 : statementFinanceSplitExcelVoOne.getSalesQuantity());
        statementFinanceSplitExcelVo.setPrice(statementFinanceSplitExcelVoOne.getPrice() == null ? 0.0 : SupplychainUtils.getSpecifiedDigitsDouble(4, statementFinanceSplitExcelVoOne.getPrice()));
        statementFinanceSplitExcelVo.setGift(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getGift()) ? "" : statementFinanceSplitExcelVoOne.getGift());
        statementFinanceSplitExcelVo.setTaxRate(statementFinanceSplitExcelVoOne.getTaxRate() == null ? 0.0 : statementFinanceSplitExcelVoOne.getTaxRate());
        statementFinanceSplitExcelVo.setTakeGoodsDate(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getTakeGoodsDate()) ? "" : statementFinanceSplitExcelVoOne.getTakeGoodsDate());
        statementFinanceSplitExcelVo.setStatementOrganizeCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getStatementOrganizeCode()) ? "" : statementFinanceSplitExcelVoOne.getStatementOrganizeCode());
        statementFinanceSplitExcelVo.setStatementOrganizeName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getStatementOrganizeName()) ? "" : statementFinanceSplitExcelVoOne.getStatementOrganizeName());
        statementFinanceSplitExcelVo.setReserveType(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getReserveType()) ? "" : statementFinanceSplitExcelVoOne.getReserveType());
        statementFinanceSplitExcelVo.setWarehouseCode(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getWarehouseCode()) ? "" : statementFinanceSplitExcelVoOne.getWarehouseCode());
        statementFinanceSplitExcelVo.setWarehouseName(StringUtils.isEmpty(statementFinanceSplitExcelVoOne.getWarehouseName()) ? "" : statementFinanceSplitExcelVoOne.getWarehouseName());
        statementFinanceSplitExcelVo.setCellThree("");
        statementFinanceSplitExcelVo.setQuantity(statementFinanceSplitExcelVoOne.getQuantity() == null ? 0 : statementFinanceSplitExcelVoOne.getQuantity());
        return statementFinanceSplitExcelVo;
    }
}
