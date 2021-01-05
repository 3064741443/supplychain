package cn.com.glsx.merchant.supplychain.controller.jxc;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.constants.MerchantConstants;
import cn.com.glsx.merchant.supplychain.convert.BsAddressHttpConvert;
import cn.com.glsx.merchant.supplychain.convert.BsLogisticsHttpConvert;
import cn.com.glsx.merchant.supplychain.convert.MaterialHttpConvert;
import cn.com.glsx.merchant.supplychain.convert.OrderDetailHttpConvert;
import cn.com.glsx.merchant.supplychain.util.*;
import cn.com.glsx.merchant.supplychain.vo.SubOrderDetailVO;
import cn.com.glsx.merchant.supplychain.vo.jxc.*;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.jst.dto.SubOrderDetailDTO;
import cn.com.glsx.supplychain.jst.enums.JstErrorCodeEnum;
import cn.com.glsx.supplychain.jst.model.StatementSell;
import cn.com.glsx.supplychain.jst.remote.WxBsMerchantRemote;
import cn.com.glsx.supplychain.jxc.dto.*;
import cn.com.glsx.supplychain.jxc.enums.JXCErrorCodeEnum;
import cn.com.glsx.supplychain.jxc.enums.MerchantOrderStatusEnum;
import cn.com.glsx.supplychain.jxc.remote.JxcOrderRemote;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;
import cn.com.glsx.supplychain.remote.bs.MerchantOrderAdminRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuquan
 * @version V1.0
 * @Title: JxcOrderController.java
 * @Description: 820改版 经销存系统小程序 PC端-订单
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@Api(value = "/jxcOrder", tags = "820改版 经销存系统小程序 PC端-订单")
@RestController
@RequestMapping("jxcOrder")
public class JxcOrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JxcOrderRemote jxcOrderRemote;
    @Autowired
    private WxBsMerchantRemote wxBsMerchantRemote;
    @Autowired
    private MerchantOrderAdminRemote merchantOrderAdminRemote;
    @Autowired
    private ExcelXlsxStreamingViewUtil excelXlsxStreamingViewUtil;
    @Autowired
    private Environment env;
    private Map<String, Object> map;
    
    @ApiOperation(value = "获取产品订单", notes = "获取产品订单")
    @PostMapping("getOrder")
    public ResultEntity<JXCMTMerchantOrderDTO> getOrder(@RequestBody JXCMTMerchantOrderGetVO getVo){
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcOrderController::getOrder start dealerUserInfo:{},getVo:{}", dealerUserInfo, getVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("JxcOrderController::getOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCMTMerchantOrderDTO dtoConditon = new JXCMTMerchantOrderDTO();
        dtoConditon.setMoOrderCode(getVo.getMoOrderCode());
        RpcResponse<JXCMTMerchantOrderDTO> rpcResponse = jxcOrderRemote.getJxcMerchantOrder(dtoConditon);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::getOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcOrderController::getOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
    }
    
    @ApiOperation(value = "取消产品订单", notes = "修改产品订单")
    @PostMapping("cancelOrder")
    public ResultEntity<Integer> cancelOrder(@RequestBody JXCMTMerchantOrderGetVO getVo){
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcOrderController::cancelOrder start dealerUserInfo:{},getVo:{}", dealerUserInfo, getVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("JxcOrderController::cancelOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCMTMerchantOrderDTO dtoConditon = new JXCMTMerchantOrderDTO();
        dtoConditon.setMoOrderCode(getVo.getMoOrderCode());
        dtoConditon.setConsumer(dealerUserInfo.getMerchantCode());
        RpcResponse<Integer> rpcResponse = jxcOrderRemote.cancelJxcMerchantOrder(dtoConditon);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::cancelOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcOrderController::cancelOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
    }
    
    @ApiOperation(value = "修改产品订单", notes = "修改产品订单")
    @PostMapping("updateOrder")
    public ResultEntity<Integer> updateOrder(@RequestBody JXCMTMerchantOrderDTO orderDto){
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcOrderController::updateOrder start dealerUserInfo:{},orderDto:{}", dealerUserInfo, orderDto);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("JxcOrderController::updateOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        orderDto.setConsumer(dealerUserInfo.getMerchantCode());
        RpcResponse<Integer> rpcResponse = jxcOrderRemote.updateJxcMerchantOrder(orderDto);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::updateOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcOrderController::updateOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
    }

    @ApiOperation(value = "提交产品订单", notes = "提交产品订单")
    @PostMapping("submitOrder")
    public ResultEntity<Integer> submitOrder(@RequestBody JXCMTMerchantOrderSubmitVO submitVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcOrderController::submitOrder start dealerUserInfo:{},submitVo:{}", dealerUserInfo, submitVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("JxcOrderController::submitOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        List<JXCMTProductSplitDTO> listProductSplitDto = submitVo.getListProductSplitDto();
        for(JXCMTProductSplitDTO splitDto:listProductSplitDto){
        	for(JXCMTProductSplitDetailDTO detailDto:splitDto.getListProductSplitDetailDto()){
        		detailDto.setOrderTotal(detailDto.getPropQuantity()*splitDto.getTotal());
        	}
        }
        JXCMTMerchantOrderSubmitDTO dtoCondition = new JXCMTMerchantOrderSubmitDTO();
        JXCMTBsAddressDTO addressDto = new JXCMTBsAddressDTO();
        BeanUtils.copyProperties(submitVo.getAddressVo(), addressDto);
        dtoCondition.setAddressDto(addressDto);
        dtoCondition.getAddressDto().setMerchantCode(dealerUserInfo.getMerchantCode());
        dtoCondition.getAddressDto().setConsumer(dealerUserInfo.getMerchantCode());
        dtoCondition.setHopeTime(submitVo.getHopeTime());
        dtoCondition.setListProductSplitDto(submitVo.getListProductSplitDto());
        dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
        JxcUtils.setJXCBaseDTO(dtoCondition);
        RpcResponse<Integer> rpcResponse = jxcOrderRemote.submitJxcMerchantOrder(dtoCondition);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::submitOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcOrderController::submitOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
    }

    @ApiOperation(value = "采购订单列表", notes = "采购订单列表")
    @PostMapping("pageOrder")
    public ResultEntity<RpcPagination<JXCMTBsMerchantOrderDTO>> pageOrder(@RequestBody JXCMTBsOrderQueryVO queryVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcOrderController::pageOrder start dealerUserInfo:{},queryVo:{}", dealerUserInfo, queryVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("JxcOrderController::pageOrder not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        RpcPagination<JXCMTBsMerchantOrderDTO> pagination = new RpcPagination<>();
        JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
        dtoCondition.setStatus(queryVo.getStatus());
        dtoCondition.setSignStatus(queryVo.getSignStatus());
        dtoCondition.setOrderTimeStart(queryVo.getOrderTimeStart());
        dtoCondition.setOrderTimeEnd(queryVo.getOrderTimeEnd());
        dtoCondition.setMoOrderCode(queryVo.getMoOrderCode());
        dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
        dtoCondition.setMerchantOrder(queryVo.getMerchantOrder());
        dtoCondition.setProductName(queryVo.getProductName());
        dtoCondition.setOrderMaterialCode(queryVo.getMaterialName());
        dtoCondition.setOrderMaterialName(queryVo.getMaterialName());
        pagination.setCondition(dtoCondition);
        pagination.setPageNum(queryVo.getPageNum());
        pagination.setPageSize(queryVo.getPageSize());
        RpcResponse<RpcPagination<JXCMTBsMerchantOrderDTO>> rpcResponse = jxcOrderRemote.pageBsMerchantOrderJXC(pagination);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::pageOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcOrderController::pageOrder end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
    }

    @ApiOperation(value = "获取下单明细", notes = "获取下单明细")
    @PostMapping("getOrderDetail")
    public ResultEntity<List<JXCMTBsMerchantOrderVehicleDTO>> getOrderDetail(@RequestBody JXCMTBsMerchantOrderVehicleGetVO getVo) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("JxcOrderController::getOrderDetail start dealerUserInfo:{},getVo:{}", dealerUserInfo, getVo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("JxcOrderController::getOrderDetail not login or session time out");
            return ResultEntity.error(JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JXCErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
        dtoCondition.setMerchantOrder(getVo.getMerchantOrder());
        RpcResponse<List<JXCMTBsMerchantOrderVehicleDTO>> rpcResponse = jxcOrderRemote.listBsMerchantOrderDetail(dtoCondition);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::getOrderDetail return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        logger.info("JxcOrderController::getOrderDetail end result:{}", rpcResponse.getResult());
        return ResultEntity.success(rpcResponse.getResult());
    }


    @ApiOperation(value = "下载货物签收单", notes = "下载货物签收单")
    @PostMapping("generateSignOrder")
    public ResultEntity<FileUploadServerData> generateSignOrder(@RequestBody JXCMTBsOrderSignVO signVo) {

        logger.info("JxcOrderController::generateSignOrder start signVo:{}", signVo);
        FileUploadServerData result = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
        String strDate = formatter.format(new Date());
        PdfCreateUtil pdfCreateUtil = PdfCreateUtil.getInstance();
        pdfCreateUtil.setEnv(env);
        Map params = new HashMap<>();
        String receiveOrderNo = MerchantStringUtil.getReceiveOrderNo();
        params.put(MerchantConstants.PdfKey.SIGN_ORDER_NO, receiveOrderNo);
        params.put(MerchantConstants.PdfKey.SIGN_ORDER_DATE, strDate);
        JXCMTBsMerchantOrderGenSignDTO dtoCondition = new JXCMTBsMerchantOrderGenSignDTO();
        dtoCondition.setDocumentNo(receiveOrderNo);
        dtoCondition.setListMerchantOrders(signVo.getListMerchantOrders());
        RpcResponse<List<JXCMTBsMerchantOrderSignDTO>> rpcResponse = jxcOrderRemote.generatorMerchantOrderSign(dtoCondition);
        JXCErrorCodeEnum errCodeEnum = (JXCErrorCodeEnum) rpcResponse.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("JxcOrderController::generateSignOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        List<JXCMTBsMerchantOrderSignDTO> listSignDto = rpcResponse.getResult();
        StringBuffer orderDetails = new StringBuffer();
        Integer count = 0;
        Integer intNo = 1;
        String strRecvCompany = "";
        String strRecvContact = "";
        String strRecvMobile = "";
        String strRecvAddr = "";
        for (JXCMTBsMerchantOrderSignDTO dto : listSignDto) {
            String orderDetail = MessageFormat.format(MerchantConstants.PdfKey.SIGN_ORDER_DETAIL_TEMPLATE,
                    intNo, dto.getMerchantOrder(), dto.getFactoryName(), dto.getMaterialCode() + "/" + dto.getMaterialName(),
                    dto.getShipmentQuantity(), dto.getShipmentTime(), dto.getLogisticsCpy() + ":" + dto.getLogisticsNo(), dto.getRemark() == null ? "" : dto.getRemark());
            orderDetails.append(orderDetail);
            count += Integer.valueOf(dto.getShipmentQuantity());
            intNo++;
            strRecvCompany = dto.getMerchantName();
            strRecvContact = dto.getContacts();
            strRecvMobile = dto.getMobile();
            strRecvAddr = dto.getAddress();
        }
        params.put(MerchantConstants.PdfKey.SIGN_RECV_COMPANY, strRecvCompany);
        params.put(MerchantConstants.PdfKey.SIGN_RECV_CONTACT, strRecvContact);
        params.put(MerchantConstants.PdfKey.SIGN_RECV_PHONE, strRecvMobile);
        params.put(MerchantConstants.PdfKey.SIGN_RECV_ADDR, strRecvAddr);
        params.put(MerchantConstants.PdfKey.SIGN_ORDER_DETAIL, orderDetails);
        params.put(MerchantConstants.PdfKey.SIGN_TOTAL, String.valueOf(count));
        logger.info("JxcOrderController::generateSignOrder end params:{}", params);
        byte[] pdfFileByte = pdfCreateUtil.createPDF(MerchantConstants.PdfKey.HTML_TEMPLATE_APPLY, params);
        FileUploadServerData fileUploadServerData = pdfCreateUtil.uploadToFileSystem(receiveOrderNo,
                pdfFileByte, MerchantConstants.PdfKey.FILE_TYPE);
        return ResultEntity.success(fileUploadServerData);
    }

    @ApiOperation(value = "不扫码-无订单发货提交sn", notes = "不扫码-无订单发货提交sn")
    @PostMapping("noScanCodeWxSubShopOrderDetailNoOrder")
    public ResultEntity<Integer> noScanCodeWxSubShopOrderDetailNoOrder(@RequestBody SubOrderDetailVO subOrderDetail) {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("WxOrderController::wxSubShopOrderDetailNoOrder start userInfoVo:{},subOrderDetail:{}", dealerUserInfo, subOrderDetail);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("WxProductController::wxSubShopOrderDetailNoOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        SubOrderDetailDTO dtoCondition = new SubOrderDetailDTO();
        dtoCondition.setShopCode(subOrderDetail.getShopCode());
        dtoCondition.setAgentMerchantCode(dealerUserInfo.getMerchantCode());
        dtoCondition.setAddressDto(BsAddressHttpConvert.convertVO(subOrderDetail.getAddressVo()));
        dtoCondition.setLogisticsDto(BsLogisticsHttpConvert.convertVo(subOrderDetail.getLogisticsVo()));
        dtoCondition.setListDetailDto(OrderDetailHttpConvert.convertListVo(subOrderDetail.getListDetail()));
        if (subOrderDetail.getMaterialVOList() != null && subOrderDetail.getMaterialVOList().size() > 0) {
            dtoCondition.setMaterialDTOList(MaterialHttpConvert.convertListVo(subOrderDetail.getMaterialVOList()));
        }
        dtoCondition.setShipType(subOrderDetail.getShipType());
        dtoCondition.setScanType(subOrderDetail.getScanType());
        JstUtils.setBaseDTO(dtoCondition);
        RpcResponse<Integer> rsp = wxBsMerchantRemote.noScanCodeWxSubShopOrderDetailNoOrder(dtoCondition);
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

    /**
     * 无订单发货-查询物料信息
     */
    @ApiOperation(value = "无订单发货-查询物料信息", notes = "无订单发货-查询物料信息")
    @PostMapping("pageOrder")
    @RequestMapping(value = "wxMaterialListNoOrder", method = RequestMethod.GET)
    public ResultEntity<List<StatementSell>> listMaterialNoOrder() {
        Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
        logger.info("WxOrderController::wxSubShopOrderDetailNoOrder start userInfoVo:{},subOrderDetail:{}", dealerUserInfo);
        if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
            logger.info("WxProductController::wxSubShopOrderDetailNoOrder not login or session time out");
            return ResultEntity.error(JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getCode(), JstErrorCodeEnum.ERRCODE_SESSION_TIME_OUT.getDescrible(), null);
        }
        RpcResponse<List<StatementSell>> rsp = wxBsMerchantRemote.listMaterialNoOrder(dealerUserInfo.getMerchantCode());
        JstErrorCodeEnum errCodeEnum = (JstErrorCodeEnum) rsp.getError();
        if (!StringUtils.isEmpty(errCodeEnum)) {
            logger.info("WxProductController::wxSubShopOrderDetailNoOrder return"
                    + errCodeEnum.getDescrible());
            return ResultEntity.error(errCodeEnum.getCode(),
                    errCodeEnum.getDescrible());
        }
        List<StatementSell> statementSellList = rsp.getResult();
        logger.info("WxOrderController::wxSubShopOrderDetailNoOrder end statementSellList:{}", statementSellList);
        return ResultEntity.success(statementSellList);
    }
    
    @ApiOperation(value = "采购订单列表导出", notes = "采购订单列表导出")
    @PostMapping("exportPurchaseOrder")
    public ModelAndView exportPurchaseOrder(@RequestBody JXCMTBsOrderQueryVO queryVo){
    	 Session session = ThreadLocalCache.getSession();
         DealerUserInfo dealerUserInfo = (DealerUserInfo) session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
         logger.info("JxcOrderController::exportPurchaseOrder start dealerUserInfo:{},queryVo:{}", dealerUserInfo, queryVo);
         if (StringUtils.isEmpty(dealerUserInfo) || StringUtils.isEmpty(dealerUserInfo.getMerchantCode())) {
             logger.info("JxcOrderController::exportPurchaseOrder not login or session time out");
         }
         JXCMTBsMerchantOrderDTO dtoCondition = new JXCMTBsMerchantOrderDTO();
         dtoCondition.setMerchantCode(dealerUserInfo.getMerchantCode());
         dtoCondition.setStatus(queryVo.getStatus());
         dtoCondition.setSignStatus(queryVo.getSignStatus());
         dtoCondition.setOrderTimeStart(queryVo.getOrderTimeStart());
         dtoCondition.setOrderTimeEnd(queryVo.getOrderTimeEnd());
         dtoCondition.setMoOrderCode(queryVo.getMoOrderCode());
         dtoCondition.setMerchantOrder(queryVo.getMerchantOrder());
         dtoCondition.setProductName(queryVo.getProductName());
         dtoCondition.setProductCode(queryVo.getProductName());
         dtoCondition.setMaterialName(queryVo.getMaterialName());
         dtoCondition.setMaterialCode(queryVo.getMaterialName());
         RpcResponse<List<JXCMTBsMerchantOrderDTO>> rpcResponse = jxcOrderRemote.exportPurchaseOrder(dtoCondition);
         List<JXCMTBsMerchantOrderDTO> listRpcResult = rpcResponse.getResult();
         List<JXCMTBsMerchantOrderExcelVo> listExcelVos = new ArrayList<>();
        JXCMTBsMerchantOrderExcelVo jxcmtBsMerchantOrderExcelVo=null;
         if(null != listRpcResult){
        	 for(JXCMTBsMerchantOrderDTO dtoObject:listRpcResult){
                 jxcmtBsMerchantOrderExcelVo=convertTo(dtoObject);
        		 listExcelVos.add(jxcmtBsMerchantOrderExcelVo);
        	 }
         }
         List<Object> merchantOrderList = new ArrayList<Object>();
         merchantOrderList.addAll(listExcelVos);
         Map<String, Object> map = new HashMap<>();
         map.put("objs", merchantOrderList);
         map.put(ExcelXlsxStreamingViewUtil.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_MERCHANT_ORDER);
         map.put(ExcelXlsxStreamingViewUtil.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_MERCHANT_ORDER);
         map.put(ExcelXlsxStreamingViewUtil.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_MERCHANT_ORDER);
         return new ModelAndView(excelXlsxStreamingViewUtil,map);    
    }

    /**
     * 导出采购订单列表（转换对象）
     */
    private JXCMTBsMerchantOrderExcelVo convertTo(JXCMTBsMerchantOrderDTO jxcmtBsMerchantOrderDTO) {
       
        JXCMTBsMerchantOrderExcelVo merchantOrderVo = new JXCMTBsMerchantOrderExcelVo();
        merchantOrderVo.setMoOrderCode(StringUtils.isEmpty(jxcmtBsMerchantOrderDTO.getMoOrderCode()) ? "" : jxcmtBsMerchantOrderDTO.getMoOrderCode());
        merchantOrderVo.setProductName(StringUtils.isEmpty(jxcmtBsMerchantOrderDTO.getProductName()) ? "" : jxcmtBsMerchantOrderDTO.getProductName());
        merchantOrderVo.setMaterialName(StringUtils.isEmpty(jxcmtBsMerchantOrderDTO.getMaterialName()) ? "" : jxcmtBsMerchantOrderDTO.getMaterialName());
        merchantOrderVo.setTotalOrder(jxcmtBsMerchantOrderDTO.getTotalOrder() == null ? 0 : jxcmtBsMerchantOrderDTO.getTotalOrder());
        merchantOrderVo.setTotalCheck(jxcmtBsMerchantOrderDTO.getTotalCheck() == null ? 0 : jxcmtBsMerchantOrderDTO.getTotalCheck());
        merchantOrderVo.setTotalSends(jxcmtBsMerchantOrderDTO.getTotalSends() == null ? 0 : jxcmtBsMerchantOrderDTO.getTotalSends());
        merchantOrderVo.setTotalOwes(jxcmtBsMerchantOrderDTO.getTotalOwes() == null ? 0 : jxcmtBsMerchantOrderDTO.getTotalOwes());
        merchantOrderVo.setAcceptQuantity(jxcmtBsMerchantOrderDTO.getAcceptQuantity() == null ? 0 : jxcmtBsMerchantOrderDTO.getAcceptQuantity());
        merchantOrderVo.setStatus(getMerchantOrderStatuName(Byte.valueOf(jxcmtBsMerchantOrderDTO.getStatus())));
        merchantOrderVo.setRemarks(StringUtils.isEmpty(jxcmtBsMerchantOrderDTO.getRemarks()) ? "" : jxcmtBsMerchantOrderDTO.getRemarks());
        JXCMTBsAddressDTO bsAddressDto = jxcmtBsMerchantOrderDTO.getBsAddressDto();
        merchantOrderVo.setName((null==bsAddressDto)?"":(StringUtils.isEmpty(bsAddressDto.getName()) ? "" : bsAddressDto.getName()));
        merchantOrderVo.setMobile((null==bsAddressDto)?"":(StringUtils.isEmpty(bsAddressDto.getMobile()) ? "" : bsAddressDto.getMobile()));
        merchantOrderVo.setAddress((null==bsAddressDto)?"":(StringUtils.isEmpty(bsAddressDto.getAddress()) ? "" : bsAddressDto.getAddress()));
        merchantOrderVo.setOrderTime(JxcUtils.getStringFromDate(jxcmtBsMerchantOrderDTO.getOrderTime()));
        return merchantOrderVo;
    }
    
    private String getMerchantOrderStatuName(Byte status)
	{
    	if(StringUtils.isEmpty(status))
		{
			return "";
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_ALREADY_COUNTERMAND.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_WAIT_CHECK.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_WAIT_SHIPMENTS.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_WAIT_RECEIVE.getName();
		}		
		if(status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_ALREADY_FINISH.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_ALREADY_INVALID.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_WAIT_DISPATCH.getName();
		}
		if(status.equals(MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getCode()))
		{
			return MerchantOrderStatusEnum.ORDER_ALREADY_CANCEL.getName();
		}
		return "";
	}
}
