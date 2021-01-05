package cn.com.glsx.merchant.supplychain.controller.jst;

import cn.com.glsx.framework.core.beans.ResponseEntity;
import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.config.SupplyadminProperty;
import cn.com.glsx.merchant.supplychain.convert.*;
import cn.com.glsx.merchant.supplychain.util.*;
import cn.com.glsx.merchant.supplychain.vo.*;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.jst.dto.*;
import cn.com.glsx.supplychain.jst.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.remote.JxBsMerchantRemote;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;

import com.alibaba.fastjson.JSON;
import com.glsx.biz.common.base.entity.Area;
import com.glsx.biz.common.base.entity.City;
import com.glsx.biz.common.base.entity.Province;
import com.glsx.biz.common.base.service.AreaService;
import com.glsx.biz.common.base.service.CityService;
import com.glsx.biz.common.base.service.ProvinceService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.oreframework.commons.office.poi.zslin.utils.ExcelUtil;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName MerchantStockDeviceController
 * @Description: 进销存系统发货管理接口
 * @Author xiexin
 * @Date 2020/2/13 19:32
 * @Version 1.0
 */
@RestController
@RequestMapping("merchantStockDevice")
public class MerchantStockDeviceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JxBsMerchantRemote jxBsMerchantRemote;

    @Autowired
    private SupplyadminProperty supplyadminProperty;

    @Autowired
    private WxBsMerchantRemote wxBsMerchantRemote;
    
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    //  @Autowired
    //  protected HttpSession session;

    /**
     * 获取经销存系统发货分页列表
     *
     * @param disJstShopOrder
     * @return
     */
    @RequestMapping("pageJstShopOrder")
    public ResultEntity<DisJstShopOrderVO> pageJstShopOrder(@RequestBody DisJstShopOrderVO disJstShopOrder) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        //	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        logger.info("MerchantStockDeviceController::pageJstShopOrder start dealerUserInfo:{},disJstShopOrder:{}", dealerUserInfo, disJstShopOrder);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("MerchantStockDeviceController::pageJstShopOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (StringUtils.isEmpty(disJstShopOrder) ||
                StringUtils.isEmpty(disJstShopOrder.getPageSize()) ||
                StringUtils.isEmpty(disJstShopOrder.getPageNum())) {
            logger.info("MerchantStockDeviceController::pageJstShopOrder invalid param");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
        }
        RpcPagination<JstShopOrderDTO> rpcDtoRequest = new RpcPagination<>();
        rpcDtoRequest.setPageNum(disJstShopOrder.getPageNum());
        rpcDtoRequest.setPageSize(disJstShopOrder.getPageSize());
        JstShopOrderDTO dtoBody = new JstShopOrderDTO();
        dtoBody.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
        if (!StringUtils.isEmpty(disJstShopOrder.getCreatedDateStart())) {
            dtoBody.setCreatedDateStart(JxcUtils.getDateFromString(disJstShopOrder.getCreatedDateStart()));
        }
        if (!StringUtils.isEmpty(disJstShopOrder.getCreatedDateEnd())) {
            dtoBody.setCreatedDateEnd(JxcUtils.getDateFromString(disJstShopOrder.getCreatedDateEnd()));
        }
        if (!StringUtils.isEmpty(disJstShopOrder.getShopName())) {
            dtoBody.setShopName(disJstShopOrder.getShopName());            
        }
        if (!StringUtils.isEmpty(disJstShopOrder.getProductName())) {
            dtoBody.setProductName(disJstShopOrder.getProductName());
        }
        if (!StringUtils.isEmpty(disJstShopOrder.getStatus())) {
            dtoBody.setStatus(disJstShopOrder.getStatus());
        }
        if(!StringUtils.isEmpty(disJstShopOrder.getScanType())){
        	dtoBody.setScanType(disJstShopOrder.getScanType());
        }
        if(!StringUtils.isEmpty(disJstShopOrder.getNoOrder())){
        	dtoBody.setNoOrder(disJstShopOrder.getNoOrder());
        }
        if(!StringUtils.isEmpty(disJstShopOrder.getMaterialCode())){
        	dtoBody.setMaterialCode(disJstShopOrder.getMaterialCode());
        }
      
        rpcDtoRequest.setCondition(dtoBody);
        RpcResponse<RpcPagination<JstShopOrderDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageJstShopOrder(rpcDtoRequest);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rpcPaginationRpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("MerchantStockDeviceController::pageJstShopOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        RpcPagination<JstShopOrderDTO> dtoRpcResult = rpcPaginationRpcResponse.getResult();
        if (!StringUtils.isEmpty(dtoRpcResult)) {
            disJstShopOrder.setPageNum(dtoRpcResult.getPageNum());
            disJstShopOrder.setPageSize(dtoRpcResult.getPageSize());
            disJstShopOrder.setTotal((int) dtoRpcResult.getTotal());
            disJstShopOrder.setPages(dtoRpcResult.getPages());
        }
        List<JstShopOrderVO> listVo = new ArrayList<JstShopOrderVO>();
        for (JstShopOrderDTO dto : dtoRpcResult.getList()) {
            JstShopOrderVO vo = new JstShopOrderVO();
            BeanUtils.copyProperties(dto, vo);
            if(StringUtils.isEmpty(vo.getProvinceName())){
            	if(vo.getProvinceId() != null){
            		vo.setProvinceName(this.getProviceName(vo.getProvinceId()));
            	}
            	if(vo.getCityId() != null){
            		vo.setCityName(this.getCityName(vo.getCityId()));
            	}
            	if(vo.getAreaId() != null){
            		vo.setAreaName(this.getAreaName(vo.getAreaId()));
            	}
            }
            listVo.add(vo);
        }
        disJstShopOrder.setListShopOrder(listVo);
        logger.info("MerchantStockDeviceController::pageJstShopOrder end disJstShopOrder:{}", disJstShopOrder);
        return ResultEntity.success(disJstShopOrder);
    }
  /*  public ResultEntity<RpcPagination<JstShopOrderVO>> pageJstShopOrder(@RequestBody RpcPagination<JstShopOrderVO> jstShopOrderVO) {
        logger.info("MerchantStockDeviceController::pageJstShopOrder jstShopOrderVO:{}", jstShopOrderVO);
        if(StringUtils.isEmpty(jstShopOrderVO.getCondition()) || 
        		StringUtils.isEmpty(jstShopOrderVO.getCondition().getAgentMerchantCode()))
        {
        	return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible());
        }
        if(StringUtils.isEmpty(jstShopOrderVO.getCondition().getCreatedDateStart()))
        {
        	jstShopOrderVO.getCondition().setCreatedDateStart(null);
        }
        if(StringUtils.isEmpty(jstShopOrderVO.getCondition().getCreatedDateEnd()))
        {
        	jstShopOrderVO.getCondition().setCreatedDateEnd(null);
        }
        if(StringUtils.isEmpty(jstShopOrderVO.getCondition().getShopName()))
        {
        	jstShopOrderVO.getCondition().setShopName(null);
        }
        if(StringUtils.isEmpty(jstShopOrderVO.getCondition().getProductName()))
        {
        	jstShopOrderVO.getCondition().setProductName(null);
        }
        if(StringUtils.isEmpty(jstShopOrderVO.getCondition().getStatus()))
        {
        	jstShopOrderVO.getCondition().setStatus(null);
        }
        RpcResponse<RpcPagination<JstShopOrderVO>> result = RpcResponse.success(new RpcPagination<>());
        RpcPagination<JstShopOrderDTO> JstShopOrderDTO = new RpcPagination<>();
        BeanUtils.copyProperties(jstShopOrderVO, JstShopOrderDTO);
        JstShopOrderVO vo = jstShopOrderVO.getCondition();
        if (vo != null) {
            JstShopOrderDTO dto = new JstShopOrderDTO();
            BeanUtils.copyProperties(vo, dto);
            JstShopOrderDTO.setCondition(dto);
        }
        logger.info("MerchantStockDeviceController::pageJstShopOrder JstShopOrderDTO:{}", JstShopOrderDTO.getCondition());
        RpcResponse<RpcPagination<JstShopOrderDTO>> rpcPaginationRpcResponse = jxBsMerchantRemote.pageJstShopOrder(JstShopOrderDTO);
        BeanUtils.copyProperties(rpcPaginationRpcResponse, result);
        List<JstShopOrderDTO> dtoList = rpcPaginationRpcResponse.getResult().getList();
        List<JstShopOrderVO> voList = new ArrayList<>();
        for (JstShopOrderDTO jstShopOrderDTO : dtoList) {
            vo = new JstShopOrderVO();
            BeanUtils.copyProperties(jstShopOrderDTO, vo);
            voList.add(vo);
        }
        result.getResult().setList(voList);
        return ResultEntity.result(result);
    }*/


    /**
     * 发货明细模板下载接口
     *
     * @param response
     */
    @RequestMapping(value = "/jstShopOrderDetailDownloadTemplate", method = RequestMethod.GET)
    public void jstShopOrderDetailDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "发货明细模板.xlsx";
            name = "templates/templateJstShopOrderDetail.xlsx";

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
     * 供货关系维护导入门店模板下载接口
     *
     * @param response
     */
    @RequestMapping(value = "/JstShopDownloadTemplate", method = RequestMethod.GET)
    public void JstShopDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "供货关系维护导入门店模板.xlsx";
            name = "templates/templateJstShop.xlsx";

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
     * 经销存系统发货
     *
     * @param dispatchDetail
     * @return
     */
    @RequestMapping("dispatchJstShopOrderDetail")
    public ResultEntity<CheckImportDispatchDetailVO> dispatchJstShopOrderDetail(@RequestBody CheckImportDispatchDetailVO dispatchDetail) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        //	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        logger.info("MerchantStockDeviceController::dispatchJstShopOrderDetail start dealerUserInfo:{},dispatchDetail:{}", dealerUserInfo, dispatchDetail);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("MerchantStockDeviceController::dispatchJstShopOrderDetail not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (StringUtils.isEmpty(dispatchDetail.getShopOrderCode()) ||
                StringUtils.isEmpty(dispatchDetail.getLogisticsVo()) ||
                StringUtils.isEmpty(dispatchDetail.getLogisticsVo().getCompany()) ||
                StringUtils.isEmpty(dispatchDetail.getLogisticsVo().getOrderNumber())) {
            logger.info("MerchantStockDeviceController::dispatchJstShopOrderDetail invalid param");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
        }
        CheckImportShopOrderDetailDTO dtoCondition = new CheckImportShopOrderDetailDTO();
        dtoCondition.setLogisticsDto(BsLogisticsHttpConvert.convertVo(dispatchDetail.getLogisticsVo()));
        dtoCondition.setShopOrderCode(dispatchDetail.getShopOrderCode());
        dtoCondition.setListShopOrderDetailDto(CheckDispatchDetailHttpConvert.convertListVo(dispatchDetail.getListDetail()));
        dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
        dtoCondition.setShipmentsQuantity(dispatchDetail.getShipmentsQuantity());
        JxcUtils.setBaseDTO(dtoCondition);
        RpcResponse<CheckImportShopOrderDetailDTO> rsp = jxBsMerchantRemote.dispatchJstShopOrderDetail(dtoCondition);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("MerchantStockDeviceController::dispatchJstShopOrderDetail return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        CheckImportShopOrderDetailDTO result = rsp.getResult();

        logger.info("MerchantStockDeviceController::dispatchJstShopOrderDetail end result:{}", result);
        return ResultEntity.success(CheckImportDispatchDetailHttpConvert.convertDto(result));
    }
   /* @RequestMapping("dispatchJstShopOrderDetail")
    public ResultEntity<Integer> dispatchJstShopOrderDetail(@RequestBody JstShopOrderDetailVO jstShopOrderDetailVO) {
        logger.info("MerchantStockDeviceController::dispatchJstShopOrderDetail jstShopOrderVO:{}", jstShopOrderDetailVO);
        JstShopOrderDetailDTO jstShopOrderDetailDTO = new JstShopOrderDetailDTO();
        if (jstShopOrderDetailVO != null) {
            BeanUtils.copyProperties(jstShopOrderDetailVO, jstShopOrderDetailDTO);
        }
        RpcResponse<Integer> rpcResponse = jxBsMerchantRemote.dispatchJstShopOrderDetail(jstShopOrderDetailDTO);
        return ResultEntity.result(rpcResponse);
    }*/

    /**
     * 经销存系统查看发货数量
     *
     * @param jstShopOrderDetailVO
     * @return
     */
    @RequestMapping("getJstShopOrderDetailList")
    public ResultEntity<List<JstShopOrderDetailDTO>> getJstShopOrderDetailList(@RequestBody JstShopOrderDetailVO jstShopOrderDetailVO) {

        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        //	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        logger.info("MerchantStockDeviceController::getJstShopOrderDetailList start dealerUserInfo:{},JstShopOrderDetailVO:{}", dealerUserInfo, jstShopOrderDetailVO);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("MerchantStockDeviceController::getJstShopOrderDetailList not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (StringUtils.isEmpty(jstShopOrderDetailVO) ||
                StringUtils.isEmpty(jstShopOrderDetailVO.getShopOrderCode())) {
            logger.info("MerchantStockDeviceController::getJstShopOrderDetailList invalid param");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
        }

        JstShopOrderDetailDTO jstShopOrderDetailDTO = new JstShopOrderDetailDTO();
        jstShopOrderDetailDTO.setShopOrderCode(jstShopOrderDetailVO.getShopOrderCode());
        JxcUtils.setBaseDTO(jstShopOrderDetailDTO);
        RpcResponse<List<JstShopOrderDetailDTO>> rpcResponse = jxBsMerchantRemote.getJstShopOrderDetailList(jstShopOrderDetailDTO);
        return ResultEntity.result(rpcResponse);
    }

    /**
     * 发货明细SN上传效验接口
     */
    @RequestMapping("checkImportDispatchDetail")
    public ResultEntity<CheckImportDispatchDetailVO> checkImportDispatchDetail(@RequestBody CheckImportDispatchDetailVO dispatchDetail) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        //	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        logger.info("MerchantStockDeviceController::checkImportDispatchDetail start dealerUserInfo:{},dispatchDetail:{}", dealerUserInfo, dispatchDetail);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("MerchantStockDeviceController::checkImportDispatchDetail not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if (StringUtils.isEmpty(dispatchDetail.getShopOrderCode())) {
            logger.info("MerchantStockDeviceController::checkImportDispatchDetail invalid param");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
        }

        CheckImportShopOrderDetailDTO dtoCondition = new CheckImportShopOrderDetailDTO();
        dtoCondition.setShopOrderCode(dispatchDetail.getShopOrderCode());
        dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
        dtoCondition.setListShopOrderDetailDto(CheckDispatchDetailHttpConvert.convertListVo(dispatchDetail.getListDetail()));
        JxcUtils.setBaseDTO(dtoCondition);
        RpcResponse<CheckImportShopOrderDetailDTO> rsp = jxBsMerchantRemote.checkImportDispatchDetail(dtoCondition);
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("WxProductController::checkImportDispatchDetail return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        CheckImportShopOrderDetailDTO result = rsp.getResult();

        logger.info("MerchantStockDeviceController::checkImportDispatchDetail end result:{}", result);
        return ResultEntity.success(CheckImportDispatchDetailHttpConvert.convertDto(result));
    }

    /**
     * 文件上传校验
     */
    @ResponseBody
    @RequestMapping(value = "/checkImportFileDispatchDetail")
    public ResponseEntity<ImportedResult> checkImportFileDispatchDetail(@RequestParam(value = "file") MultipartFile files, HttpServletRequest request) {
        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
        List<ImportedResult> importList = new ArrayList<ImportedResult>();
        ImportedResult importedResult = new ImportedResult();
        //    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
/*        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());

        logger.info("checkImportFileDispatchDetail 文件上传查询到的 用户 ：{}",dealerUserInfo);
        String merchantCode = ((StringUtils.isEmpty(dealerUserInfo))?null:dealerUserInfo.getMerchantCode());*/
        String orderCode = request.getParameter("orderCode");
        String merchantCode = request.getParameter("merchantCode");

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

                    CheckImportShopOrderDetailDTO dtoConditon = new CheckImportShopOrderDetailDTO();
                    List<CheckShopOrderDetailDTO> listShopOrderDetailDto = new ArrayList<CheckShopOrderDetailDTO>();

                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, CheckShopOrderDetailDTO.class, 0, 0);
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
                        opAllowNum = 5000;
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
                        CheckShopOrderDetailDTO bean = (CheckShopOrderDetailDTO) statement;
                        listShopOrderDetailDto.add(bean);
                    }
                    Date date = new Date();
                    dtoConditon.setListShopOrderDetailDto(listShopOrderDetailDto);
                    dtoConditon.setShopOrderCode(orderCode);
                    dtoConditon.setMerchantCode(merchantCode);
                    dtoConditon.setConsumer("merchant-supplychain");
                    dtoConditon.setTime(formater.format(date));
                    dtoConditon.setVersion("v1.0");
                    RpcResponse<CheckImportShopOrderDetailDTO> response = jxBsMerchantRemote.checkImportDispatchDetail(dtoConditon);
                    if (response.getError() != null) {

                        importedResult.setIsImported(4);
                        importedResult.setCause(response.getError().getDisplayName());
                        importList.add(importedResult);
                        responseEntity.setData(importList);
                        return responseEntity;
                    }
                    failCount = response.getResult().getFailCount();
                    successCount = response.getResult().getSuccessCount();

                    importedResult.setSuccessCount(successCount);
                    importedResult.setFailCount(failCount);
                    importedResult.setTotalCount(totalCount);
                    // 保存数据
                    if (response.getResult().getListSuccess() != null && response.getResult().getListSuccess().size() > 0) {
                        String json = jstShopOrderDetailImportToJson(response.getResult().getListSuccess());
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                    }

                    if (response.getResult().getListFailed() != null && response.getResult().getListFailed().size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, response.getResult().getListFailed(), CheckShopOrderDetaiExportlDTO.class);
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

    /**
     * 发货明细转json
     *
     * @param list
     * @return
     */
    public String jstShopOrderDetailImportToJson(List<CheckShopOrderDetailDTO> list) {
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
            json.append("\"sn\":\"" + list.get(i).getSn() + "\"");
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }


    /**
     * 无订单发货设备明细上传效验接口
     */
    @ResponseBody
    @RequestMapping(value = "checkImportNoOrderDetail")
    public ResultEntity<CheckImportNoOrderDetailDTO> checkImportNoOrderDetail(@RequestParam(value = "file") MultipartFile files, HttpServletRequest request) {
        ResultEntity<CheckImportNoOrderDetailDTO> responseEntity = new ResultEntity<CheckImportNoOrderDetailDTO>();
        CheckImportNoOrderDetailDTO importedResult = new CheckImportNoOrderDetailDTO();
        String merchantCode =  request.getParameter("merchantCode");
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

                    List<NoOrderDetailImportDTO> noOrderDetailImportDTOList = new ArrayList<NoOrderDetailImportDTO>();


                    // 获得输入流
                    InputStream input = files.getInputStream();
                    List<Object> list = null;
                    try {
                        list = ExcelReads.getInstance().readExcel2Objs(input, NoOrderDetailImportDTO.class, 0, 0);
                    } catch (Exception e) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("请使用有效模板导入数据");
                        logger.warn("导入异常....");
                        return responseEntity;
                    }
                    if (list == null || list.size() == 0) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("请使用有效模板导入数据");
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    if (opAllowNum == 0) {
                        opAllowNum = 5000;
                    }
                    totalCount = list.size();
                    if (totalCount > opAllowNum) {
                        responseEntity.setReturnCode("1");
                        responseEntity.setMessage("模版中最大允许导入" + opAllowNum + "条,请修改数据重新再导!");
                        logger.warn("导入异常..");
                        return responseEntity;
                    }

                    for (Object statement : list) {
                        NoOrderDetailImportDTO bean = (NoOrderDetailImportDTO) statement;
                        noOrderDetailImportDTOList.add(bean);
                    }
                    Map<String, NoOrderDetailImportDTO>  noOrderDetailImportDTOMap = new HashMap<String, NoOrderDetailImportDTO>();
                    for (NoOrderDetailImportDTO noOrderDetailImportDTO : noOrderDetailImportDTOList) {
                        noOrderDetailImportDTOMap.put(noOrderDetailImportDTO.getSn(), noOrderDetailImportDTO);
                    }
                    RpcResponse<CheckImportDataDTO> response = jxBsMerchantRemote.checkImportNoOrderDetail(noOrderDetailImportDTOList);
                        List<NoOrderDetailImportDTO> sucessList = new ArrayList<NoOrderDetailImportDTO>();
                    List<NoOrderDetailExportDTO> failList = new ArrayList<NoOrderDetailExportDTO>();
                    sucessList = response.getResult().getNoOrderDetailImportDTOList();
                    failList = response.getResult().getNoOrderDetailExportDTOList();
                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
                    if (errCodeEnum != null) {
                        responseEntity.setReturnCode("2");
                        responseEntity.setMessage(errCodeEnum.getDescrible());
                        return responseEntity;
                    }
                    Session session = ThreadLocalCache.getSession();
                    DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
                    logger.info("MerchantStockDeviceController::checkImportNoOrderDetail start userInfoVo:{},checkSubSn:{}", dealerUserInfo,sucessList);
                    if(StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode()))
                    {
                        logger.info("WxProductController::wxCheckNoOrderDetailSn not login or session time out");
                        return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(),null);
                    }
                    CheckImportNoOrderDetailDTO dtoCondition = new CheckImportNoOrderDetailDTO();
                    dtoCondition.setNoOrderDetailImportDTOList(sucessList);
                    dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
                    JxcUtils.setBaseDTO(dtoCondition);
                /*    if(sucessList.size()==0){
                         responseEntity.setReturnCode("-1");
                         responseEntity.setMessage("导入的sn校验之后的信息不能为空");
                         return responseEntity;
                    }*/
                    RpcResponse<CheckImportNoOrderDetailDTO> rsp = jxBsMerchantRemote.batchCheckShopOrderDetailNoOrder(dtoCondition);
                    JstErrorCodeEnum errCodeEnum2 = (JstErrorCodeEnum) rsp.getError();
                    if (!StringUtils.isEmpty(errCodeEnum2)) {
                        logger.info("WxProductController::checkImportNoOrderDetail return"
                                + errCodeEnum2.getDescrible());
                        return ResultEntity.error(errCodeEnum2.getCode(),
                                errCodeEnum2.getDescrible());
                    }

                    if (sucessList != null && sucessList.size() > 0) {
                        CheckImportNoOrderDetailDTO result = rsp.getResult();
                        logger.info("MerchantStockDeviceController::checkImportNoOrderDetail end result:{}",result);
                        importedResult.setNoOrderDetailImportDTOList(result.getNoOrderDetailImportDTOList());
                        importedResult.setSuccessCount(result.getSuccessCount());
                        importedResult.setFailCount(result.getFailCount());
                        importedResult.setListSuccess(result.getListSuccess());
                        importedResult.setListFailed(result.getListFailed());
                        importedResult.setTotalCount(result.getTotalCount());
                        String json = JSON.toJSONString(sucessList); //noOrderDetailImportToJson(sucessList);
                        importedResult.setIsImported(1);
                        importedResult.setMsg(json);
                        importedResult.setCause("导入成功");
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        List<NoOrderDetailExportDTO> failList2=result.getListFailed();
                        ExcelUtil.getInstance().exportObj2Excel(url, failList2, NoOrderDetailExportDTO.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                    }

                 /*   if (failList != null && failList.size() > 0) {
                        String name = UUID.randomUUID().toString() + "."
                                + FilenameUtils.getExtension(fileName);
                        String url = supplyadminProperty.getDownloadPath() + name;
                        ExcelUtil.getInstance().exportObj2Excel(url, failList, NoOrderDetailExportDTO.class);
                        String reUrl = supplyadminProperty.getDomain() + name;
                        importedResult.setUrl(reUrl);
                        logger.warn("导入结束.....,总数：" + totalCount + " 成功：" + successCount + " 失败：" + failCount);
                    }*/
                } else {
                    responseEntity.setReturnCode("1");
                    responseEntity.setMessage("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传!");
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
        responseEntity.setData(importedResult);
        return responseEntity;
    }

//    /**
//     * 无订单发货设备明细上传效验接口
//     */
//    @ResponseBody
//    @RequestMapping(value = "checkImportNoOrderDetail")
//    public ResponseEntity<ImportedResult> checkImportNoOrderDetail(@RequestParam(value = "file") MultipartFile files, HttpServletRequest request) {
//        logger.warn("无订单发货设备明细导入校验");
//        ResponseEntity<ImportedResult> responseEntity = new ResponseEntity<ImportedResult>();
//        List<ImportedResult> importList = new ArrayList<ImportedResult>();
//        ImportedResult importedResult = new ImportedResult();
//        importedResult.setIsImported(0);
//        importedResult.setCause("导入成功");
//
//        long totalCount = 0;
//        long failCount = 0;
//        long successCount = 0;
//
//        String allowNum = supplyadminProperty.getOpAllowNum();
//        Integer opAllowNum = 0;
//        if (!StringUtils.isEmpty(allowNum)) {
//            opAllowNum = Integer.parseInt(allowNum);
//        }
//        // 这里可以支持多文件上传
//        if (files != null) {
//            BufferedOutputStream bw = null;
//            try {
//                String fileName = files.getOriginalFilename();
//                // 判断是否有文件且是否为图片文件
//                if (fileName != null
//                        && !"".equalsIgnoreCase(fileName.trim())
//                        && (FilenameUtils.getExtension(fileName.trim()).equals(
//                        "xls") || FilenameUtils.getExtension(
//                        fileName.trim()).equals("xlsx"))) {
//                    // 可以选择把文件保存到服务器,创建输出文件对象
//                    String strUploadFile = supplyadminProperty.getUploadPath()
//                            + UUID.randomUUID().toString() + "."
//                            + FilenameUtils.getExtension(fileName);
//                    File outFile = new File(strUploadFile);
//
//                    // 文件到输出文件对象
//                    FileUtils.copyInputStreamToFile(files.getInputStream(),
//                            outFile);
//                    List<NoOrderDetailImportDTO> resultList = new ArrayList<NoOrderDetailImportDTO>();
//
//                    // 获得输入流
//                    InputStream input = files.getInputStream();
//                    List<Object> list = null;
//                    try {
//                        list = ExcelReads.getInstance().readExcel2Objs(input,
//                                NoOrderDetailImportDTO.class, 0, 0);
//                    } catch (Exception e) {
//                        importedResult.setIsImported(4);
//                        importedResult.setCause("请使用有效模板导入数据");
//                        importList.add(importedResult);
//                        responseEntity.setData(importList);
//                        return responseEntity;
//                    }
//
//                    if (list == null || list.size() == 0) {
//                        importedResult.setIsImported(4);
//                        importedResult.setCause("请使用有效模板导入数据");
//                        importList.add(importedResult);
//                        responseEntity.setData(importList);
//                        return responseEntity;
//                    }
//
//                    if (opAllowNum == 0) {
//                        opAllowNum = 5000;
//                    }
//
//                    totalCount = list.size();
//                    if (totalCount > opAllowNum) {
//                        importedResult.setIsImported(3);
//                        importedResult.setCause("模版中最大允许导入" + opAllowNum
//                                + "条,请修改数据重新再导!");
//                        importList.add(importedResult);
//                        responseEntity.setData(importList);
//                        logger.warn("导入异常...");
//                        return responseEntity;
//                    }
//
//                    for (Object device : list) {
//                        NoOrderDetailImportDTO bean = (NoOrderDetailImportDTO) device;
//                        resultList.add(bean);
//                    }
//
//                    RpcResponse<CheckImportDataDTO> response = jxBsMerchantRemote.checkImportNoOrderDetail(resultList);
//
//                    ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response
//                            .getError();
//                    if (errCodeEnum != null) {
//                        importedResult.setIsImported(4);
//                        importedResult.setCause(errCodeEnum.getDescrible());
//                        importList.add(importedResult);
//                        responseEntity.setData(importList);
//                        return responseEntity;
//                    }
//
//                    List<NoOrderDetailImportDTO> successList = response
//                            .getResult().getNoOrderDetailImportDTOList();
//                    List<NoOrderDetailExportDTO> failList = response
//                            .getResult().getNoOrderDetailExportDTOList();
//
//                    failCount = failList.size();
//                    successCount = successList.size();
//
//                    importedResult.setSuccessCount(successCount);
//                    importedResult.setFailCount(failCount);
//                    importedResult.setTotalCount(totalCount);
//
//                    // 保存数据
//                    if (successList != null && successList.size() > 0) {
//                        String json = JSON.toJSONString(successList);
//                        importedResult.setIsImported(1);
//                        importedResult.setMsg(json);
//                    }
//
//                    if (failList != null && failList.size() > 0) {
//                        String name = UUID.randomUUID().toString() + "."
//                                + FilenameUtils.getExtension(fileName);
//                        String url = supplyadminProperty.getDownloadPath()
//                                + name;
//                        ExcelUtil.getInstance().exportObj2Excel(url, failList,
//                                NoOrderDetailExportDTO.class);
//                        String reUrl = supplyadminProperty.getDomain() + name;
//                        importedResult.setUrl(reUrl);
//                        logger.warn("导入结束......,总数：" + totalCount + " 成功："
//                                + successCount + " 失败：" + failCount);
//                    }
//                } else {
//                    importedResult
//                            .setCause("上传文件只支持.xls与.xlsx格式，请另存为兼容格式Excel再上传");
//                    importedResult.setIsImported(2);
//                    importList.add(importedResult);
//                    responseEntity.setData(importList);
//                    logger.warn("导出结束...");
//                    return responseEntity;
//                }
//            } catch (Exception e) {
//                logger.error("导入操作异常", e.getMessage());
//            } finally {
//                try {
//                    if (bw != null) {
//                        bw.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        importList.add(importedResult);
//        responseEntity.setData(importList);
//        return responseEntity;
//    }

    /**
     * 转json
     *
     * @param list
     * @return
     */
    public String noOrderDetailImportToJson(List<NoOrderDetailImportDTO> list) {
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
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }


    /**
     * 无订单发货设备明细导入
     *
     * @param jstShopImportDTOList
     * @return
     */
    @RequestMapping("importMyJstShop")
    public ResultEntity<Integer> importMyJstShop(@RequestBody List<MyJstShopImportDTO> jstShopImportDTOList) {
        logger.info("myJstShop::importMyJstShop jstShopImportDTOList:{}", jstShopImportDTOList);
/*        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if (jstShopImportDTOList != null && jstShopImportDTOList.size() > 0) {
            jstShopImportDTOList.get(0).setAgentMerchantCode(dul.getResult().getMerchantCode());
            jstShopImportDTOList.get(0).setAgentMerchantName(dul.getResult().getMerchantName());
        }*/
        RpcResponse<Integer> rpcnRpcResponse = jxBsMerchantRemote.importMyJstShop(jstShopImportDTOList);
        return ResultEntity.result(rpcnRpcResponse);
    }

    /**
     * 无订单发货-设备明细模板下载
     *
     * @param response
     */
    @RequestMapping(value = "/noOrderDetailDownloadTemplate", method = RequestMethod.GET)
    public void noOrderDetailDownloadTemplate(HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();
            String str = "";
            String name = "";
            str = "发货明细模板.xlsx";
            name = "templates/templateNoOrderDetail.xlsx";

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
     * 无订单发货获取发货清单
     */
    @RequestMapping("wxGetNoOrderDetail")
    public ResultEntity<DisNoOrderVO> wxGetNoOrderDetail(@RequestBody SubOrderDetailVO subOrderDetail) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("WxOrderController::wxGetNoOrderDetail start userInfoVo:{},subOrderDetail:{}", dealerUserInfo, subOrderDetail);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("WxProductController::wxGetNoOrderDetail not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
      /*  if(!dealerUserInfo.getRole().equals(MerchantRoleEnum.ROLE_AGENT.getValue()))
        {
            logger.info("WxProductController::wxGetNoOrderDetail must agent role");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getCode(), JstErrorCodeEnum.ERRCODE_FAILED_ROLE_SEL.getDescrible(),null);
        }*/
        SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
        dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
        dtoCondition.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
        JxcUtils.setBaseDTO(dtoCondition);
        RpcResponse<List<JstShopShoppingCartDTO>> rsp = wxBsMerchantRemote.getShopOrderDetailNoOrder(dtoCondition);
        if (null != rsp.getError()) {
            logger.info("WxProductController::wxGetNoOrderDetail return" + rsp.getError().toString());
            return ResultEntity.error(rsp.getError().toString());
        }
        List<JstShopShoppingCartDTO> listCart = rsp.getResult();
        Map<String, DisNoOrderDetailVO> mapOrderDetail = new HashMap<String, DisNoOrderDetailVO>();
        DisNoOrderDetailVO vo = null;
        OrderDetailVO dtVo = null;
        for (JstShopShoppingCartDTO dto : listCart) {
            dtVo = new OrderDetailVO();
            dtVo.setSn(dto.getSn());
            vo = mapOrderDetail.get(dto.getProductCode() + dto.getMaterialCode());
            if (null == vo) {
                vo = new DisNoOrderDetailVO();
                vo.setProductCode(dto.getProductCode());
                vo.setProductName(dto.getProductName());
                vo.setMaterialCode(dto.getMaterialCode());
                vo.setMaterialName(dto.getMaterialName());
                List<OrderDetailVO> listDetail = new ArrayList<>();
                listDetail.add(dtVo);
                vo.setListDetail(listDetail);
                mapOrderDetail.put(dto.getProductCode() + dto.getMaterialCode(), vo);
            } else {
                vo.getListDetail().add(dtVo);
            }
            vo.setCount(vo.getListDetail().size());
        }
        DisNoOrderVO result = new DisNoOrderVO();
        result.setTotalCount(listCart.size());
        result.setListNoOrderDetail(mapOrderDetail.values().stream().collect(Collectors.toList()));
        logger.info("WxOrderController::wxGetNoOrderDetail end result:{}", result);
        return ResultEntity.success(result);
    }

    /**
     * 无订单发货提交sn
     */
    @RequestMapping("wxSubShopOrderDetailNoOrder")
    public ResultEntity<Integer> wxSubShopOrderDetailNoOrder(@RequestBody SubOrderDetailVO subOrderDetail) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("WxOrderController::wxSubShopOrderDetailNoOrder start userInfoVo:{},subOrderDetail:{}", dealerUserInfo, subOrderDetail);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("WxProductController::wxSubShopOrderDetailNoOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        if(subOrderDetail.getShipType().equals("O")){
			if(subOrderDetail.getLogisticsVo() == null 
					||StringUtils.isEmpty(subOrderDetail.getLogisticsVo().getCompany())
					||StringUtils.isEmpty(subOrderDetail.getLogisticsVo().getOrderNumber())){
				logger.info("WxProductController::wxSubShopOrderDetailNoOrder param error");
				return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
			}
		}
		if(subOrderDetail.getAddressVo() == null){
			logger.info("WxProductController::wxSubShopOrderDetailNoOrder param error");
			return ResultEntity.error(JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getCode(), JstErrorCodeEnum.ERRCODE_INVALID_PARAM.getDescrible(), null);
		}
		
        SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
        dtoCondition.setShopCode(subOrderDetail.getShopCode());
        dtoCondition.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
        dtoCondition.setAddressDto(BsAddressHttpConvert.convertVO(subOrderDetail.getAddressVo()));
        dtoCondition.setLogisticsDto(BsLogisticsHttpConvert.convertVo(subOrderDetail.getLogisticsVo()));
        dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
        dtoCondition.setScanType(subOrderDetail.getScanType());
        dtoCondition.setShipType(subOrderDetail.getShipType());
        if (subOrderDetail.getMaterialVOList() != null && subOrderDetail.getMaterialVOList().size() > 0) {
			dtoCondition.setMaterialDTOList(MaterialHttpConvert.convertListVo(subOrderDetail.getMaterialVOList()));
		}
        if(StringUtils.isEmpty(subOrderDetail.getShipType())){
        	 dtoCondition.setShipType("O");
        }
        if(StringUtils.isEmpty(subOrderDetail.getScanType())){
        	dtoCondition.setScanType("N");
        }
       
        JxcUtils.setBaseDTO(dtoCondition);
        RpcResponse<Integer> rsp = null;
        if(dtoCondition.getScanType().equals("Y")){
        	rsp = wxBsMerchantRemote.noScanCodeWxSubShopOrderDetailNoOrder(dtoCondition);
        }else{
        	rsp = wxBsMerchantRemote.submitShopOrderDetailNoOrder(dtoCondition);
        }
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("WxProductController::wxSubShopOrderDetailNoOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        Integer count = rsp.getResult();
        logger.info("WxOrderController::wxSubShopOrderDetailNoOrder end count:{}", count);
        return ResultEntity.success(count);

    }
    
    private String getCityName(Integer cityId){
    	String result = "";
    	City city = cityService.findById(cityId);
    	if(null != city){
    		result = city.getCity();
    	}
    	return result;
    }

    private String getAreaName(Integer areaId){
    	String result = "";
    	Area area = areaService.get(areaId);
    	if(null != area){
    		result = area.getArea();
    	}
    	return result;
    }
    
    
     private String getProviceName(Integer proviceId){
    	String result = "";
    	Province province = provinceService.getByProvinceId(proviceId);
    	if(null != province){
    		result = province.getProvince();
    	}
    	return result;
    }
}
