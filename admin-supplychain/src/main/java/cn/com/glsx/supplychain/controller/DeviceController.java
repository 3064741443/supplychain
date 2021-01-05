package cn.com.glsx.supplychain.controller;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.framework.core.exception.RpcServiceException;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.config.OmsProperty;
import cn.com.glsx.supplychain.config.SupplyadminProperty;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.enums.UpdateRecordEnum;
import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.MerchantOrder;
import cn.com.glsx.supplychain.remote.DeviceManagerAdminRemote;
import cn.com.glsx.supplychain.remote.SupplyChainAdminRemote;
import cn.com.glsx.supplychain.util.*;
import cn.com.glsx.supplychain.vo.*;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 硬件设备管理
 *
 * @author zhoudan
 */
@RestController
@RequestMapping("deviceInfo")
public class DeviceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OmsProperty supplychainProperty;

    @Autowired
    private ExcelXlsxStreamingView excelView;

    @Autowired
    private ExcelXlsxStreamingViewTwo excelViewTwo;

    @Autowired
    private UserInfoHolder userInfoHolder;

    @Autowired
    private SupplyChainAdminRemote supplyChainAdminRemote;

    @Autowired
    private DeviceManagerAdminRemote deviceManagerAdminRemote;

    @Autowired
    private ExcelXlsxStreamingViewDeMana excelViewDeMana;

    @Autowired
    private ExcelXlsxStreamingViewDeChange excelViewDeChange;

    @Autowired
    private ExcelXlsxStreamingViewDeDetail excelViewDeDetail;

    @Autowired
    private ExcelXlsxStreamingViewDeInit excelViewDeInit;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    /**
     * 分页查询设备未入库列表
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listDeviceFileUnstock")
    public ResultEntity<RpcPagination<DeviceFileUnstock>> listDeviceFileUnstock(@RequestBody RpcPagination<DeviceFileUnstock> pagination) {
        RpcResponse<RpcPagination<DeviceFileUnstock>> response = deviceManagerAdminRemote.listDeviceFileUnstock(pagination);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 分页查询硬件设备信息
     *
     * @param deviceInfo
     * @param pagination
     * @return
     */
    @RequestMapping("listDeviceInfo")
    public ResultEntity<RpcPagination<DeviceInfo>> listDeviceInfo(DeviceInfo deviceInfo, RpcPagination<DeviceInfo> pagination) {
        RpcResponse<RpcPagination<DeviceInfo>> response = supplyChainAdminRemote.listDeviceInfo(pagination, deviceInfo);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据id查询硬件设备信息
     *
     * @param id
     * @return
     */
    @RequestMapping("getDeviceInfoById")
    public ResultEntity<DeviceInfo> getDeviceInfoById(@RequestBody Integer id) {
        RpcResponse<DeviceInfo> response = supplyChainAdminRemote.getDeviceInfoById(id);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 修改或添加硬件设备信息
     *
     * @param deviceInfo
     * @return
     */
    @RequestMapping("addAndUpdateDeviceInfo")
    public ResultEntity<Integer> addAndUpdateDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        RpcResponse<Integer> response = supplyChainAdminRemote.addAndUpdateDeviceInfo(deviceInfo);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("修改成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询设备编号信息
     *
     * @param code
     * @return
     */
    @RequestMapping("getDeviceCode")
    public ResultEntity<DeviceCode> getDeviceCode(Integer code) {
        DeviceCode deviceCode = new DeviceCode();
        deviceCode.setDeviceCode(code);
        RpcResponse<DeviceCode> response = deviceManagerAdminRemote.getDeviceCode(deviceCode);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }


    /**
     * 查询设备外部卡虚拟信息
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping("listDeviceFileVirtual")
    public ResultEntity<RpcPagination<DeviceFileVirtual>> listDeviceFileVirtual(RpcPagination<DeviceFileVirtual> pagination, DeviceFileVirtual record) {
        RpcResponse<RpcPagination<DeviceFileVirtual>> response = deviceManagerAdminRemote.listDeviceFileVirtual(pagination, record);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询IMEI设备库存信息
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping("listDeviceImeiStock")
    public ResultEntity<RpcPagination<DeviceImeiStock>> listDeviceImeiStock(RpcPagination<DeviceImeiStock> pagination, DeviceImeiStock record) {
        RpcResponse<RpcPagination<DeviceImeiStock>> response = deviceManagerAdminRemote.listDeviceImeiStock(pagination, record);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据id删除IMEI设备库存信息
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping("removeDeviceImeiStock")
    public ResultEntity<Integer> removeDeviceImeiStock(Integer id) {
        RpcResponse<Integer> response = deviceManagerAdminRemote.reomveDeviceImeiStock(id);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("删除成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }


    /**
     * 导出硬件设备数据
     *
     * @param deviceInfo
     * @param response
     * @return
     */
    @RequestMapping("exportExit")
    public ModelAndView exportDeviceInfoExit(HttpServletResponse response, DeviceInfo deviceInfo) {
        if (deviceInfo != null) {
            deviceInfo.setIccid(deviceInfo.getOrderCode());
            deviceInfo.setImei(deviceInfo.getOrderCode());
        }
        //获取设备list0
        RpcResponse<List<DeviceInfo>> responseDevice = supplyChainAdminRemote.ExportDeviceInfo(deviceInfo);
        List<DeviceInfo> deviceInfoList = responseDevice.getResult();
        List<DeviceExcelVo> deviceExcelVos = deviceInfoList.stream().map(device -> convertTo(device)).collect(Collectors.toList());
        List<Object> devices = new ArrayList<Object>();
        devices.addAll(deviceExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", devices);
        map.put(ExcelXlsxStreamingView.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER);
        map.put(ExcelXlsxStreamingView.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT);
        map.put(ExcelXlsxStreamingView.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME);
        try {
            excelView.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelView);
    }

    /**
     * 根据订单号 分页查询硬件设备信息
     *
     * @param orderCode
     * @param pagination
     * @return
     */
    @RequestMapping("getDeviceDetails")
    public ResultEntity<RpcPagination<OrderInfoDetail>> ListDeviceInfoByOrderCode(String orderCode, RpcPagination<OrderInfoDetail> pagination) {

        OrderInfoDetail record = new OrderInfoDetail();
        record.setOrderCode(orderCode);
        RpcResponse<RpcPagination<OrderInfoDetail>> response = supplyChainAdminRemote.pageOrderInfoDetail(pagination, record);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 根据订单号 导出硬件设备数据
     *
     * @param orderCode
     * @param response
     * @return
     */
    @RequestMapping("exportDeviceByOrderCode")
    public ModelAndView exportDeviceByOrderCode(HttpServletResponse response, String orderCode) {
        OrderInfoDetail orderInfoDetail = new OrderInfoDetail();
        if (!StringUtils.isEmpty(orderCode)) {
            orderInfoDetail.setOrderCode(orderCode);
        }
        //获取设备list
        RpcResponse<List<OrderInfoDetail>> responseDevice = supplyChainAdminRemote.ExportOrderInfoDetail(orderInfoDetail);
        List<OrderInfoDetail> orderInfoDetailList = responseDevice.getResult();
        List<OrderInfoDetailExcelVo> orderInfoDetailExcelVos = orderInfoDetailList.stream().map(device -> convertOrderInfoDetailTo(device)).collect(Collectors.toList());
        List<Object> orderInfos = new ArrayList<Object>();
        orderInfos.addAll(orderInfoDetailExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", orderInfos);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_TWO);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_TWO);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_TWO);
        try {
            excelViewTwo.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelViewTwo);
    }

    /**
     * 设备分类列表
     *
     * @param pagination 分页参数 DeviceCode条件查询参数
     * @return
     * @throws
     */
    @RequestMapping("listDeviceCode")
    public ResultEntity<RpcPagination<DeviceCode>> listDeviceCode(RpcPagination<DeviceCode> pagination, DeviceCode deviceCode) {
        RpcResponse<RpcPagination<DeviceCode>> response = deviceManagerAdminRemote.pageDeviceCode(pagination, deviceCode);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 设备关系明细列表
     *
     * @param pagination 分页参数
     * @return
     * @throws
     */
    @RequestMapping("listDeviceFile")
    public ResultEntity<RpcPagination<DeviceFile>> listDeviceFile(RpcPagination<DeviceFile> pagination, @RequestBody DeviceFile deviceFile) {

        if (!StringUtils.isEmpty(deviceFile.getSearchType())) {

            DeviceCode deviceCode = new DeviceCode();
            deviceCode.setTypeId(Integer.valueOf(deviceFile.getSearchType()));
            deviceFile.setDeviceCodeTable(deviceCode);
        }

        if (!StringUtils.isEmpty(deviceFile.getSearchKey()) && !StringUtils.isEmpty(deviceFile.getSearchValue())) {

            String strKey = deviceFile.getSearchKey();
            switch (strKey) {
                case "SN": //IMEI
                    deviceFile.setSn(deviceFile.getSearchValue());
                    break;
                case "ID"://当前ICCID
                {
                    DeviceCardManager deviceCard = new DeviceCardManager();
                    deviceCard.setIccid(deviceFile.getSearchValue());
                    deviceFile.setDeviceCardManager(deviceCard);
                }
                break;
                case "II"://当前IMSI
                {
                    DeviceCardManager deviceCard = new DeviceCardManager();
                    deviceCard.setImsi(deviceFile.getSearchValue());
                    deviceFile.setDeviceCardManager(deviceCard);
                }
                break;
                case "CU"://当前用户
                {
                    DeviceUserManager deviceUser = new DeviceUserManager();
                    deviceUser.setUserFlag(deviceFile.getSearchValue());
                    deviceFile.setDeviceUserManager(deviceUser);
                }
                break;
                case "DC"://设备编号
                    deviceFile.setDeviceCode(Integer.valueOf(deviceFile.getSearchValue()));
                    break;
                case "PK"://入库商品编号
                    deviceFile.setPackageId(Integer.valueOf(deviceFile.getSearchValue()));
                    break;
                case "SM"://发往商户
                    deviceFile.setSendMerchantNo(deviceFile.getSearchValue());
                    break;
            }
        }


        if (!StringUtils.isEmpty(deviceFile.getPackageStatu())) {
            String strStatus = deviceFile.getPackageStatu();
            switch (strStatus) {
                case "AL":
                case "IN":
                case "UA": {
                    DeviceFileSnapshot snapshot = new DeviceFileSnapshot();
                    snapshot.setPackageStatu(strStatus);
                    deviceFile.setSnapshot(snapshot);
                }
                break;
            }
        }

        RpcResponse<RpcPagination<DeviceFile>> response = deviceManagerAdminRemote.listDeviceFile(pagination, deviceFile);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 设备更换记录列表
     *
     * @param pagination 分页参数
     * @return
     * @throws
     */
    @RequestMapping("listDeviceUpdateRecord")
    public ResultEntity<RpcPagination<DeviceUpdateRecord>> listDeviceUpdateRecord(RpcPagination<DeviceUpdateRecord> pagination, DeviceUpdateRecord deviceUpdateRecord) {
        RpcResponse<RpcPagination<DeviceUpdateRecord>> response = deviceManagerAdminRemote.listDeviceUpdateRecord(pagination, deviceUpdateRecord);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 设备初始化记录列表
     *
     * @param pagination 分页参数
     * @return
     * @throws
     */
    @RequestMapping("listDeviceResetRecord")
    public ResultEntity<RpcPagination<DeviceResetRecord>> listDeviceResetRecord(RpcPagination<DeviceResetRecord> pagination, DeviceResetRecord deviceResetRecord) {
        RpcResponse<RpcPagination<DeviceResetRecord>> response = deviceManagerAdminRemote.listDeviceResetRecord(pagination, deviceResetRecord);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取全部设备类型 （大类型）
     *
     * @return
     */
    @RequestMapping("getDeviceType")
    public ResultEntity<List<DeviceType>> listDeviceType() {
        DeviceType record = new DeviceType();
        RpcResponse<List<DeviceType>> response = deviceManagerAdminRemote.listDeviceType(record);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 获取商户列表
     *
     * @param targetPage:指定页 （NULL 默认1） pageSize:指定页大小(null 默认40)
     * @return
     */
/*	@SuppressWarnings("rawtypes")
	@RequestMapping("getMerchantInfo")
	public ResultEntity<List> listMerchantInfo(Integer targetPage, Integer pageSize) {
		RpcResponse<List> response = deviceManagerAdminRemote.listMerchantInfo(Constants.targetPage, Constants.pageSize);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}*/

    /**
     * 查询品牌定制商信息
     *
     * @param deviceCode
     * @return
     */
    @ResponseBody
    @SuppressWarnings("rawtypes")
    @RequestMapping("getMerchantInfo")
    public ResultEntity<List> findlistMerchantInfo(DeviceCode deviceCode) {
        HashMap<String, Object> condition = new HashMap<String, Object>();
        if (deviceCode.getMerchantId() != null) {
            condition.put("merchantId", deviceCode.getMerchantId());
        }
        if (deviceCode.getMerchantName() != null) {
            condition.put("merchantName", deviceCode.getMerchantName());
        }
        RpcResponse<List> response = deviceManagerAdminRemote.findlistMerchantInfo(condition, Constants.targetPageNew, Constants.pageSizeMer);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 设备分类添加和修改
     *
     * @param deviceCode
     * @return
     */
    @RequestMapping("insertAndUpdateDevice")
    public ResultEntity<Integer> addAndUpdateDeviceCode(DeviceCode deviceCode) {
        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            deviceCode.setCreatedBy(user.getRealname());
            deviceCode.setUpdatedBy(user.getRealname());
        }
        //判断是否为新增
        boolean isAdd = true;
        if (deviceCode.getId() == null) {
            isAdd = false;
        }

        RpcResponse<Integer> response = deviceManagerAdminRemote.addAndUpdateDeviceCode(deviceCode);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();

        if (errCodeEnum == null) {
            if (!isAdd) {
                logger.error("新增设备类型返回的id:" + response.getResult());
                //调用老平台保存设备类型接口
                deviceCode.setDeviceCode(response.getResult());
                logger.error("调用老数据平台同步数据：" + deviceCode.toString());
                RpcResponse<Integer> rpcResult = supplyChainAdminRemote.saveDeviceCategory(deviceCode);
                errCodeEnum = (ErrorCodeEnum) rpcResult.getError();
                logger.error("调用老数据平台同步数据返回结果：" + errCodeEnum);
            }
            if (errCodeEnum == null) {
                response.setMessage("操作成功");
            } else {
                logger.error("调用老数据平台失败：" + errCodeEnum.getDescrible());
                response.setMessage(errCodeEnum.getDescrible());
            }
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 设备关系明细详情
     *
     * @param id 设备sn / id
     * @return
     * @throws RpcServiceException
     */
    @RequestMapping("getDeviceRelationDetail")
    public ResultEntity<DeviceFile> getDeviceFileById(Integer id) {

        DeviceFile deviceFile = new DeviceFile();
        deviceFile.setId(id);
        //deviceFile.setSn(sn);
        RpcResponse<DeviceFile> response = deviceManagerAdminRemote.getDeviceFileById(deviceFile);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * 初始化设备
     *
     * @param deviceResetRecord
     * @return
     */
    @RequestMapping("initDeviceRelation")
    public ResultEntity<Integer> initDeviceRelation(DeviceResetRecord deviceResetRecord) {

        @SuppressWarnings({"static-access", "deprecation"})
        User user = userInfoHolder.getUser();
        if (null != user) {
            deviceResetRecord.setCreatedBy(user.getRealname());
            deviceResetRecord.setUpdatedBy(user.getRealname());
        }

        RpcResponse<Integer> response = deviceManagerAdminRemote.addAndUpdateDeviceResetRecord(deviceResetRecord);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("操作成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }

    /**
     * <br>
     * <b>功能：</b>模版下载<br>
     * <b>日期：</b> 2017-10-25<br>
     *
     * @param response
     */
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
    public void downloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "设备导入模板.xlsx";
            name = "templates/templateImport.xlsx";

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
     * <b>功能：</b>Imei设备库存模版下载<br>
     * <b>日期：</b> 2018-11-16<br>
     *
     * @param response
     */
    @RequestMapping(value = "/imeidownloadTemplate", method = RequestMethod.GET)
    public void imeidownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "IMEI设备库存导入模板.xlsx";
            name = "templates/templateImeiStoke.xlsx";

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
    public String constrJson(List<DeviceListImport> list) {

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
            json.append("\"iccid\":\"" + list.get(i).getIccid() + "\",");
            json.append("\"imsi\":\"" + list.get(i).getImsi() + "\",");
            json.append("\"imei\":\"" + list.get(i).getImei() + "\",");
            json.append("\"vcode\":\"" + list.get(i).getVcode() + "\",");
            json.append("\"batchNo\":\"" + list.get(i).getBatchNo() + "\",");
            json.append("\"packageNo\":\"" + list.get(i).getPackageNo() + "\",");
            json.append("\"mentchantNo\":\"" + list.get(i).getMentchantNo() + "\",");
            json.append("\"modulePhone\":\"" + list.get(i).getModulePhone() + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();

    }

    /**
     * @param list 拼接成json 返回
     * @return String
     */
    public String imeiconstrJson(List<DeviceImeiStokeListImport> list) {

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
            json.append("\"imei\":\"" + list.get(i).getImei() + "\",");
            json.append("\"externalFlag\":\"" + list.get(i).getExternalFlag() + "\",");
            json.append("\"devType\":\"" + list.get(i).getDevType() + "\",");
            json.append("\"merchantNo\":\"" + list.get(i).getMerchantNo() + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();

    }


    /**
     * 设备导入数据校验
     *
     * @param deviceCode
     * @param isOnlyimsi
     */
    @ResponseBody
    @RequestMapping(value = "/preDeviceImport")
    public ResponseEntity<ImportedResult> deviceImportCheck(String deviceCode, Boolean isOnlyimsi, @RequestParam(value = "file") MultipartFile files) {
        logger.warn("开始校验");
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

        if (StringUtils.isEmpty(isOnlyimsi)) {
            isOnlyimsi = false;
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
                    List<DeviceListImport> resultList = new ArrayList<DeviceListImport>();

                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, DeviceListImport.class, 0, 0);
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
                        opAllowNum = 2000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        importedResult.setIsImported(3);
                        importedResult.setCause("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常...");
                        return responseEntity;
                    }

                    for (Object device : list) {

                        DeviceListImport bean = (DeviceListImport) device;
                        resultList.add(bean);
                    }

                    RpcResponse<CheckImportDataVo> response = deviceManagerAdminRemote.checkImportDeviceList(deviceCode, resultList, isOnlyimsi, false);

                    List<DeviceListImport> SucessList = new ArrayList<DeviceListImport>();
                    List<DeviceListExport> FailList = new ArrayList<DeviceListExport>();
                    SucessList = response.getResult().getImportList();
                    FailList = response.getResult().getInvalidList();

                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {

                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }

                    failCount = FailList.size();
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


                    if (FailList != null && FailList.size() > 0) {
                        // 错误数据 不需要模版导出
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, FailList, DeviceListExport.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束......,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                        //importedResult.setCause("导入成功 "+successCount + " 条，失败：" + failCount + " 条，请下载");
                    }
                } else {
                    importedResult.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
                    importedResult.setIsImported(2);
                    importList.add(importedResult);
                    responseEntity.setData(importList);
                    logger.warn("导出结束...");
                    return responseEntity;
                }

            } catch (Exception e) {
                logger.error("导入操作异常");
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
     * 补录设备导入数据校验
     *
     * @param deviceCode
     * @param isOnlyimsi
     */
    @ResponseBody
    @RequestMapping(value = "/preDeviceFileUnstockImport")
    public ResponseEntity<ImportedResult> preDeviceFileUnstockImport(String deviceCode, Boolean isOnlyimsi, @RequestParam(value = "file") MultipartFile files) {
        logger.warn("开始校验");
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

        if (StringUtils.isEmpty(isOnlyimsi)) {
            isOnlyimsi = false;
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
                    List<DeviceListImport> resultList = new ArrayList<DeviceListImport>();

                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, DeviceListImport.class, 0, 0);
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
                        opAllowNum = 2000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        importedResult.setIsImported(3);
                        importedResult.setCause("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        logger.warn("导入异常...");
                        return responseEntity;
                    }

                    for (Object device : list) {

                        DeviceListImport bean = (DeviceListImport) device;
                        resultList.add(bean);
                    }

                    RpcResponse<CheckImportDataVo> response = deviceManagerAdminRemote.checkImportDeviceList(deviceCode, resultList, isOnlyimsi, true);

                    List<DeviceListImport> SucessList = new ArrayList<DeviceListImport>();
                    List<DeviceListExport> FailList = new ArrayList<DeviceListExport>();
                    SucessList = response.getResult().getImportList();
                    FailList = response.getResult().getInvalidList();

                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {

                        importedResult.setIsImported(4);
                        importedResult.setCause(errCodeEnum.getDescrible());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }

                    failCount = FailList.size();
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


                    if (FailList != null && FailList.size() > 0) {
                        // 错误数据 不需要模版导出
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, FailList, DeviceListExport.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束......,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                        //importedResult.setCause("导入成功 "+successCount + " 条，失败：" + failCount + " 条，请下载");
                    }
                } else {
                    importedResult.setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
                    importedResult.setIsImported(2);
                    importList.add(importedResult);
                    responseEntity.setData(importList);
                    logger.warn("导出结束...");
                    return responseEntity;
                }

            } catch (Exception e) {
                logger.error("导入操作异常");
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
     * IMEI设备库存导入返回数据
     *
     * @param files
     * @param
     */
    @ResponseBody
    @RequestMapping(value = "/predeviceimeiStokeImport")
    public ResponseEntity<ImportedResult> predeviceimeiStokeImport(@RequestParam(value = "file") MultipartFile files) {
        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
        List<ImportedResult> importList = new ArrayList<ImportedResult>();
        ImportedResult importedResult = new ImportedResult();

        long totalCount = 0;
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

                    List<DeviceImeiStokeListImport> imeiStokeList = new ArrayList<DeviceImeiStokeListImport>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, DeviceImeiStokeListImport.class, 0, 0);
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
                        DeviceImeiStokeListImport bean = (DeviceImeiStokeListImport) device;
                        imeiStokeList.add(bean);
                    }
                    RpcResponse<CheckImportDataVo> response = deviceManagerAdminRemote.preImportDeviceImeiStokeList(imeiStokeList);
                    List<DeviceImeiStokeListImport> SucessList = new ArrayList<DeviceImeiStokeListImport>();
                    SucessList = response.getResult().getImeiStokeList();

                    // 保存数据
                    if (SucessList != null && SucessList.size() > 0) {
                        String json = imeiconstrJson(SucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

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
    @RequestMapping(value = "/deviceImport")
    @ResponseBody
    public ResultEntity<Integer> deviceImport(@RequestBody List<DeviceListImport> deviceListImports) {

        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        //设备编号
        String deviceCode = deviceListImports.get(0).getDeviceCode();
        Boolean isOnlyimsi = Boolean.parseBoolean(deviceListImports.get(0).getIsOnlyimsi());
        RpcResponse<Integer> response = null;
        ErrorCodeEnum errCodeEnum = null;
        List<DeviceListImport> list = new ArrayList<>();
        try {
            for (int i = 0; i < deviceListImports.size(); i++) {
                list.add(deviceListImports.get(i));           
            }
            //写入数据库
            response = deviceManagerAdminRemote.importDeviceListByDeviceCode(userName, deviceCode, list, isOnlyimsi);
            errCodeEnum = (ErrorCodeEnum) response.getError();
            if (errCodeEnum != null) {
            	response.setMessage(errCodeEnum.getDescrible());
            }
        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);

    }


    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/deviceFileUnstockImport")
    @ResponseBody
    public ResultEntity<Integer> deviceFileUnstockImport(@RequestBody List<DeviceListImport> deviceListImport) {
        User user = userInfoHolder.getUser();
        String userName = user != null ? user.getRealname() : "admin";
        //设备编号
        String deviceCode = deviceListImport.get(0).getDeviceCode();
        Boolean isOnlyimsi = Boolean.parseBoolean(deviceListImport.get(0).getIsOnlyimsi());
        RpcResponse<Integer> response = null;
        ErrorCodeEnum errCodeEnum = null;
        List<DeviceListImport> list = new ArrayList<>();
        try {

            for (int i = 0; i < deviceListImport.size(); i++) {
                list.add(deviceListImport.get(i));
                //写入数据库
                response = deviceManagerAdminRemote.importDeviceFileUnstockListByDeviceCode(userName, deviceCode, list, isOnlyimsi);
                errCodeEnum = (ErrorCodeEnum) response.getError();
                if (errCodeEnum != null) {
                    break;
                }
            }

            //写入数据库
            if (errCodeEnum == null) {
                response = deviceManagerAdminRemote.importDeviceFileUnstockListByDeviceCode(userName, deviceCode, list, isOnlyimsi);
            } else {
                response.setMessage(errCodeEnum.getDescrible());
            }

        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);

    }

    @SuppressWarnings({"static-access", "deprecation"})
    @RequestMapping(value = "/deviceimeiStokeImport")
    @ResponseBody
    public ResultEntity<Integer> deviceimeiStokeImport(@RequestBody List<DeviceImeiStokeListImport> deviceImeiStokeListImport) {
        User user = userInfoHolder.getUser();

        String userName = user != null ? user.getRealname() : "admin";
        RpcResponse<Integer> response = null;
        ErrorCodeEnum errCodeEnum = null;
        List<DeviceImeiStokeListImport> list = new ArrayList<>();
        try {
            for (int i = 0; i < deviceImeiStokeListImport.size(); i++) {
                list.add(deviceImeiStokeListImport.get(i));
                //写入数据库
                response = deviceManagerAdminRemote.importDeviceimeiStokeList(userName, list);
                errCodeEnum = (ErrorCodeEnum) response.getError();
                if (errCodeEnum != null) {
                    break;
                }
            }
            //写入数据库
            if (errCodeEnum == null) {
                response = deviceManagerAdminRemote.importDeviceimeiStokeList(userName, list);
            } else {
                response.setMessage(errCodeEnum.getDescrible());
            }
        } catch (Exception e) {
            logger.error("导入异常：", e);
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);

    }

    /**
     * 设备分类列表导出
     *
     * @param response
     * @param deviceCode
     * @return
     */
    @RequestMapping("exportDeviceCode")
    public ModelAndView exportDeviceCode(HttpServletResponse response, DeviceCode deviceCode) {

        deviceCode.setPageSize(Constants.pageSizeNew);
        RpcResponse<List<DeviceCode>> responseDevice = deviceManagerAdminRemote.exportDeviceCode(deviceCode);
        List<DeviceCode> deviceCodeList = responseDevice.getResult();
        List<DeviceCodeExcelVo> deviceCodeExcelVos = deviceCodeList.stream().map(deviceCodeOne -> convertTo(deviceCodeOne)).collect(Collectors.toList());
        List<Object> deviceCodes = new ArrayList<Object>();
        deviceCodes.addAll(deviceCodeExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", deviceCodes);
        map.put(ExcelXlsxStreamingViewDeMana.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_DMANA);
        map.put(ExcelXlsxStreamingViewDeMana.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_DMANA);
        map.put(ExcelXlsxStreamingViewDeMana.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_DMANA);
        try {
            excelViewDeMana.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelViewDeMana);
    }

    /**
     * 设备更换记录列表导出
     *
     * @param deviceUpdateRecord
     * @return
     */
    @RequestMapping("exportDeviceUpdateRecord")
    public ModelAndView exportDeviceUpdateRecord(HttpServletResponse response, DeviceUpdateRecord deviceUpdateRecord) {

        RpcResponse<List<DeviceUpdateRecord>> responseDevice = deviceManagerAdminRemote.exportDeviceUpdateRecord(deviceUpdateRecord);
        List<DeviceUpdateRecord> deviceUpdateRecordList = responseDevice.getResult();
        List<DeviceUpdateRecordExcelVo> deviceUpdateRecordExcelVos = deviceUpdateRecordList.stream().map(deviceUpdateRecordOne -> convertTo(deviceUpdateRecordOne)).collect(Collectors.toList());
        List<Object> deviceUpdateRecords = new ArrayList<Object>();
        deviceUpdateRecords.addAll(deviceUpdateRecordExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", deviceUpdateRecords);
        map.put(ExcelXlsxStreamingViewDeChange.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_DCHANGE);
        map.put(ExcelXlsxStreamingViewDeChange.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_DCHANGE);
        map.put(ExcelXlsxStreamingViewDeChange.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_DCHANGE);
        try {
            excelViewDeChange.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelViewDeChange);
    }

    /**
     * 设备初始化记录列表导出
     *
     * @param deviceResetRecord
     * @return
     */
    @RequestMapping("exportDeviceResetRecord")
    public ModelAndView exportDeviceResetRecord(HttpServletResponse response, DeviceResetRecord deviceResetRecord) {

        RpcResponse<List<DeviceResetRecord>> responseDevice = deviceManagerAdminRemote.exportDeviceResetRecord(deviceResetRecord);
        List<DeviceResetRecord> deviceResetRecordList = responseDevice.getResult();
        List<DeviceResetRecordExcelVo> deviceResetRecordExcelVos = deviceResetRecordList.stream().map(deviceResetRecordOne -> convertTo(deviceResetRecordOne)).collect(Collectors.toList());
        List<Object> deviceResetRecords = new ArrayList<Object>();
        deviceResetRecords.addAll(deviceResetRecordExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", deviceResetRecords);
        map.put(ExcelXlsxStreamingViewDeInit.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_DINIT);
        map.put(ExcelXlsxStreamingViewDeInit.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_DINIT);
        map.put(ExcelXlsxStreamingViewDeInit.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_DINIT);
        try {
            excelViewDeInit.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelViewDeInit);
    }

    /**
     * 设备关系明细导出
     *
     * @param deviceFile
     * @return
     */
    @RequestMapping("exportDeviceFile")
    public ModelAndView exportDeviceFile(HttpServletResponse response, DeviceFile deviceFile) {

        //deviceFile.setPageSize(Constants.pageSizeMer);
        if (!StringUtils.isEmpty(deviceFile.getSearchType())) {

            DeviceCode deviceCode = new DeviceCode();
            deviceCode.setTypeId(Integer.valueOf(deviceFile.getSearchType()));
            deviceFile.setDeviceCodeTable(deviceCode);
        }

        if (!StringUtils.isEmpty(deviceFile.getSearchKey()) && !StringUtils.isEmpty(deviceFile.getSearchValue())) {

            String strKey = deviceFile.getSearchKey();
            switch (strKey) {
                case "SN": //IMEI
                    deviceFile.setSn(deviceFile.getSearchValue());
                    break;
                case "ID"://当前ICCID
                {
                    DeviceCardManager deviceCard = new DeviceCardManager();
                    deviceCard.setIccid(deviceFile.getSearchValue());
                    deviceFile.setDeviceCardManager(deviceCard);
                }
                break;
                case "II"://当前IMSI
                {
                    DeviceCardManager deviceCard = new DeviceCardManager();
                    deviceCard.setImsi(deviceFile.getSearchValue());
                    deviceFile.setDeviceCardManager(deviceCard);
                }
                break;
                case "CU"://当前用户
                {
                    DeviceUserManager deviceUser = new DeviceUserManager();
                    deviceUser.setUserFlag(deviceFile.getSearchValue());
                    deviceFile.setDeviceUserManager(deviceUser);
                }
                break;
                case "DC"://设备编号
                    deviceFile.setDeviceCode(Integer.valueOf(deviceFile.getSearchValue()));
                    break;
                case "PK"://入库商品编号
                    deviceFile.setPackageId(Integer.valueOf(deviceFile.getSearchValue()));
                    break;
                case "SM"://发往商户
                    deviceFile.setSendMerchantNo(deviceFile.getSearchValue());
                    break;
            }
        }


        if (!StringUtils.isEmpty(deviceFile.getPackageStatu())) {
            String strStatus = deviceFile.getPackageStatu();
            switch (strStatus) {
                case "AL":
                case "IN":
                case "UA": {
                    DeviceFileSnapshot snapshot = new DeviceFileSnapshot();
                    snapshot.setPackageStatu(strStatus);
                    deviceFile.setSnapshot(snapshot);
                }
                break;
            }
        }
        RpcResponse<List<DeviceFile>> responseDevice = deviceManagerAdminRemote.exportDeviceFile(deviceFile);
        List<DeviceFile> deviceFileList = responseDevice.getResult();
        List<DeviceFileExcelVo> deviceFileExcelVos = deviceFileList.stream().map(deviceFileOne -> convertTo(deviceFileOne)).collect(Collectors.toList());
        List<Object> deviceFiles = new ArrayList<Object>();
        deviceFiles.addAll(deviceFileExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", deviceFiles);
        map.put(ExcelXlsxStreamingViewDeDetail.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_DDETAIL);
        map.put(ExcelXlsxStreamingViewDeDetail.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_DDETAIL);
        map.put(ExcelXlsxStreamingViewDeDetail.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_DDETAIL);
        try {
            excelViewDeDetail.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelViewDeDetail);
    }

    /**
     * 获取最大的统计报表
     * @return
     */
    @RequestMapping("getMaxDeviceStatisReport")
    public ResultEntity<DeviceStatisReport> getMaxDeviceStatisReport() {
        RpcResponse<DeviceStatisReport> response = deviceManagerAdminRemote.getMaxDeviceStatisReport();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * @param @param device
     * @return DeviceExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private DeviceExcelVo convertTo(DeviceInfo device) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DeviceExcelVo vo = new DeviceExcelVo();
        vo.setIccid(StringUtils.isEmpty(device.getIccid()) ? "" : device.getIccid());
        vo.setImei(StringUtils.isEmpty(device.getImei()) ? "" : device.getImei());
        vo.setAttribCode(StringUtils.isEmpty(device.getAttribCode()) ? "" : device.getAttribCode());

        if (!StringUtils.isEmpty(device.getAttribMana())) {

            vo.setBoardVersion(StringUtils.isEmpty(device.getAttribMana().getBoardVersion()) ? "" : device.getAttribMana().getBoardVersion());
            vo.setSoftVersion(StringUtils.isEmpty(device.getAttribMana().getSoftVersion()) ? "" : device.getAttribMana().getSoftVersion());
            vo.setModelName(StringUtils.isEmpty(device.getAttribMana().getModelName()) ? "" : device.getAttribMana().getModelName());
            vo.setTypeName(StringUtils.isEmpty(device.getAttribMana().getTypeName()) ? "" : device.getAttribMana().getTypeName());
            vo.setConfigureName(StringUtils.isEmpty(device.getAttribMana().getConfigureName()) ? "" : device.getAttribMana().getConfigureName());

        } else {
            vo.setBoardVersion("");
            vo.setSoftVersion("");
            vo.setModelName("");
            vo.setTypeName("");
            vo.setConfigureName("");
        }
        vo.setBatch(StringUtils.isEmpty(device.getBatch()) ? "" : device.getBatch());
        vo.setStatus(device.getStatus().equals("IN") ? "入库" : "出库");
        if (!StringUtils.isEmpty(device.getWareHouseInfo())) {

            vo.setWareHouseName(StringUtils.isEmpty(device.getWareHouseInfo().getWareHouseName()) ? "" : device.getWareHouseInfo().getWareHouseName());
            vo.setWareHouseUpName(StringUtils.isEmpty(device.getWareHouseInfo().getWareHouseUpName()) ? "" : device.getWareHouseInfo().getWareHouseUpName());
        } else {
            vo.setWareHouseName("");
            vo.setWareHouseUpName("");
        }
        vo.setCreatedTime(StringUtils.isEmpty(formatter.format(device.getCreatedDate())) ? "" : formatter.format(device.getCreatedDate()));

        if (device.getStatus().equals("OUT")) {
            vo.setUpdatedTime(StringUtils.isEmpty(formatter.format(device.getUpdatedDate())) ? "" : formatter.format(device.getUpdatedDate()));
        } else {
            vo.setUpdatedTime("");
        }

        vo.setOrderCode(StringUtils.isEmpty(device.getOrderCode()) ? "" : device.getOrderCode());
        return vo;
    }

    /**
     * @param @param device
     * @return OrderInfoDetailExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private OrderInfoDetailExcelVo convertOrderInfoDetailTo(OrderInfoDetail orderInfoDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        OrderInfoDetailExcelVo vo = new OrderInfoDetailExcelVo();
        vo.setIccid(StringUtils.isEmpty(orderInfoDetail.getIccid()) ? "" : orderInfoDetail.getIccid());
        vo.setSn(StringUtils.isEmpty(orderInfoDetail.getSn()) ? "" : orderInfoDetail.getSn());
        vo.setImei(StringUtils.isEmpty(orderInfoDetail.getImei()) ? "" : orderInfoDetail.getImei());
        vo.setAttribCode(StringUtils.isEmpty(orderInfoDetail.getAttribCode()) ? "" : orderInfoDetail.getAttribCode());
        vo.setBatch(StringUtils.isEmpty(orderInfoDetail.getBatch()) ? "" : orderInfoDetail.getBatch());
        vo.setUpdatedDate(StringUtils.isEmpty(formatter.format(orderInfoDetail.getUpdatedDate())) ? "" : formatter.format(orderInfoDetail.getUpdatedDate()));
        vo.setOrderCode(StringUtils.isEmpty(orderInfoDetail.getOrderCode()) ? "" : orderInfoDetail.getOrderCode());
        return vo;
    }

    /**
     * @param @param deviceCode
     * @return DeviceCodeExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private DeviceCodeExcelVo convertTo(DeviceCode deviceCodeOne) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DeviceCodeExcelVo vo = new DeviceCodeExcelVo();
        vo.setDeviceCode(deviceCodeOne.getDeviceCode() == null ? 0 : deviceCodeOne.getDeviceCode());
        vo.setDeviceName(StringUtils.isEmpty(deviceCodeOne.getDeviceName()) ? "" : deviceCodeOne.getDeviceName());

        if (!StringUtils.isEmpty(deviceCodeOne.getDeviceType())) {
            vo.setName(StringUtils.isEmpty(deviceCodeOne.getDeviceType().getName()) ? "" : deviceCodeOne.getDeviceType().getName());
        } else {
            vo.setName("");
        }

        vo.setMerchantName(StringUtils.isEmpty(deviceCodeOne.getMerchantName()) ? "" : deviceCodeOne.getMerchantName());
        vo.setCreatedBy(StringUtils.isEmpty(deviceCodeOne.getCreatedBy()) ? "" : deviceCodeOne.getCreatedBy());
        vo.setCreatedTime(formatter.format(deviceCodeOne.getCreatedDate()));
        return vo;
    }

    /**
     * @param @param deviceUpdateRecord
     * @return DeviceUpdateRecordExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private DeviceUpdateRecordExcelVo convertTo(DeviceUpdateRecord deviceUpdateRecordOne) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DeviceUpdateRecordExcelVo vo = new DeviceUpdateRecordExcelVo();
        vo.setSn(StringUtils.isEmpty(deviceUpdateRecordOne.getSn()) ? "" : deviceUpdateRecordOne.getSn());

        if (!StringUtils.isEmpty(deviceUpdateRecordOne.getFlagType())) {
            if (deviceUpdateRecordOne.getFlagType().equals(UpdateRecordEnum.UPDATE_RECORD_ACTI.getValue())) {
                vo.setFlagType("激活用户");
            }
            if (deviceUpdateRecordOne.getFlagType().equals(UpdateRecordEnum.UPDATE_RECORD_CARD.getValue())) {
                vo.setFlagType("流量卡");
            }
            if (deviceUpdateRecordOne.getFlagType().equals(UpdateRecordEnum.UPDATE_RECORD_FIRM.getValue())) {
                vo.setFlagType("版本");
            }
            if (deviceUpdateRecordOne.getFlagType().equals(UpdateRecordEnum.UPDATE_RECORD_USER.getValue())) {
                vo.setFlagType("绑定用户");
            }
            if (deviceUpdateRecordOne.getFlagType().equals(UpdateRecordEnum.UPDATE_RECORD_VEHI.getValue())) {
                vo.setFlagType("车辆");
            }
        }

        vo.setFlagName(StringUtils.isEmpty(deviceUpdateRecordOne.getFlagName()) ? "" : deviceUpdateRecordOne.getFlagName());
        vo.setPreFlagName(StringUtils.isEmpty(deviceUpdateRecordOne.getPreFlagName()) ? "" : deviceUpdateRecordOne.getPreFlagName());
        vo.setCreatedTime(StringUtils.isEmpty(formatter.format(deviceUpdateRecordOne.getCreatedDate())) ? "" : formatter.format(deviceUpdateRecordOne.getCreatedDate()));
        return vo;
    }

    /**
     * @param @param deviceResetRecord
     * @return DeviceResetRecordExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private DeviceResetRecordExcelVo convertTo(DeviceResetRecord deviceResetRecordOne) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DeviceResetRecordExcelVo vo = new DeviceResetRecordExcelVo();
        vo.setSn(StringUtils.isEmpty(deviceResetRecordOne.getSn()) ? "" : deviceResetRecordOne.getSn());
        vo.setCreatedTime(StringUtils.isEmpty(formatter.format(deviceResetRecordOne.getCreatedDate())) ? "" : formatter.format(deviceResetRecordOne.getCreatedDate()));
        vo.setCreatedBy(StringUtils.isEmpty(deviceResetRecordOne.getCreatedBy()) ? "" : deviceResetRecordOne.getCreatedBy());
        vo.setRemark(StringUtils.isEmpty(deviceResetRecordOne.getRemark()) ? "" : deviceResetRecordOne.getRemark());
        return vo;
    }

    /**
     * @param @param deviceFileOne
     * @return DeviceFileExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private DeviceFileExcelVo convertTo(DeviceFile deviceFileOne) {
        DeviceFileExcelVo vo = new DeviceFileExcelVo();
        vo.setSn(StringUtils.isEmpty(deviceFileOne.getSn()) ? "" : deviceFileOne.getSn());

        //入库流量卡
        if (!StringUtils.isEmpty(deviceFileOne.getInitialCardManager())) {
            vo.setIccid(StringUtils.isEmpty(deviceFileOne.getInitialCardManager().getIccid()) ? "" : deviceFileOne.getInitialCardManager().getIccid());
            vo.setImsi(StringUtils.isEmpty(deviceFileOne.getInitialCardManager().getImsi()) ? "" : deviceFileOne.getInitialCardManager().getImsi());
        } else {
            vo.setIccid("");
            vo.setImsi("");
        }

        vo.setVerifCode(StringUtils.isEmpty(deviceFileOne.getVerifCode()) ? "" : deviceFileOne.getVerifCode());
        vo.setBatchNo(StringUtils.isEmpty(deviceFileOne.getBatchNo()) ? "" : deviceFileOne.getBatchNo());

        //设备类型
        vo.setName(StringUtils.isEmpty(deviceFileOne.getDeviceTypeName()) ? "" : deviceFileOne.getDeviceTypeName());
        vo.setDeviceCode(deviceFileOne.getDeviceCode() == null ? 0 : deviceFileOne.getDeviceCode());

        //设备名称
        if (!StringUtils.isEmpty(deviceFileOne.getDeviceCodeTable())) {
            vo.setDeviceName(StringUtils.isEmpty(deviceFileOne.getDeviceCodeTable().getDeviceName()) ? "" : deviceFileOne.getDeviceCodeTable().getDeviceName());
        } else {
            vo.setDeviceName("");
        }

        vo.setPackageId(deviceFileOne.getPackageId() == null ? 0 : deviceFileOne.getPackageId());
        vo.setPackageName(StringUtils.isEmpty(deviceFileOne.getPackageName()) ? "" : deviceFileOne.getPackageName());
        vo.setOperatorMerchantName(StringUtils.isEmpty(deviceFileOne.getOperatorMerchantName()) ? "" : deviceFileOne.getOperatorMerchantName());
        vo.setSendMerchantName(StringUtils.isEmpty(deviceFileOne.getSendMerchantName()) ? "" : deviceFileOne.getSendMerchantName());

        //当前绑定卡
        if (!StringUtils.isEmpty(deviceFileOne.getDeviceCardManager())) {
            vo.setIccid(StringUtils.isEmpty(deviceFileOne.getDeviceCardManager().getIccid()) ? "" : deviceFileOne.getDeviceCardManager().getIccid());
            vo.setImsi(StringUtils.isEmpty(deviceFileOne.getDeviceCardManager().getImsi()) ? "" : deviceFileOne.getDeviceCardManager().getImsi());
        } else {
            vo.setIccid("");
            vo.setImsi("");
        }

        //商品激活用户
        if (!StringUtils.isEmpty(deviceFileOne.getDeviceActiveUserManager())) {

            DeviceUserManager deviceUserManager = deviceFileOne.getDeviceActiveUserManager();
            vo.setPackageUserId(deviceUserManager.getUserFlag() == null ? "0" : deviceUserManager.getUserFlag());
        } else {
            vo.setPackageId(0);
        }

        //当前用户
        if (!StringUtils.isEmpty(deviceFileOne.getDeviceUserManager())) {
            vo.setUserFlag(StringUtils.isEmpty(deviceFileOne.getDeviceUserManager().getUserFlag()) ? "" : deviceFileOne.getDeviceUserManager().getUserFlag());
        } else {
            vo.setUserFlag("");
        }

        vo.setModelName(StringUtils.isEmpty(deviceFileOne.getModelName()) ? "" : deviceFileOne.getModelName());
        vo.setSoftVersion(StringUtils.isEmpty(deviceFileOne.getSoftVersion()) ? "" : deviceFileOne.getSoftVersion());

        if (!StringUtils.isEmpty(deviceFileOne.getSnapshot())) {
            DeviceFileSnapshot snapshot = deviceFileOne.getSnapshot();
            vo.setPackageUserTime(StringUtils.isEmpty(snapshot.getPackageUserTime()) ? "" : snapshot.getPackageUserTime());
            vo.setUserTime(StringUtils.isEmpty(snapshot.getUserTime()) ? "" : snapshot.getUserTime());
        }

        vo.setTerminalDiscode(StringUtils.isEmpty(deviceFileOne.getTerminalDiscode()) ? "" : deviceFileOne.getTerminalDiscode());

        return vo;
    }

}
