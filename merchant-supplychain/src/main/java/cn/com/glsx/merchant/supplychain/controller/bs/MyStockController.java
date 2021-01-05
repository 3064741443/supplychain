package cn.com.glsx.merchant.supplychain.controller.bs;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.merchant.supplychain.config.Constants;
import cn.com.glsx.merchant.supplychain.util.ExcelXlsxStreamingViewTwo;
import cn.com.glsx.merchant.supplychain.util.ThreadLocalCache;
import cn.com.glsx.merchant.supplychain.web.session.Session;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.SupplyRequest;
import cn.com.glsx.supplychain.model.bs.*;
import cn.com.glsx.supplychain.remote.bs.*;
import cn.com.glsx.supplychain.vo.MerchantStockExcelVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName 我的库存Controller
 * @Author xiaowy
 * @Param
 * @Date 2019/11/18 10:51
 * @Version
 **/
@RestController
@RequestMapping("myStock")
public class MyStockController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

   // @Autowired
  //  protected HttpSession session;

    @Autowired
    private BsMerchantStockAdminRemote bsMerchantStockAdminRemote;

    @Autowired
    private DealerUserInfoAdminRemote dealerUserInfoAdminRemote;

    @Autowired
    private ExcelXlsxStreamingViewTwo excelViewTwo;
    
    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询商户库存列表（分页）
     *
     * @param pagination
     * @return
     */
    @RequestMapping("listMerchantStock")
    public ResultEntity<RpcPagination<BsMerchantStock>> listMerchantStock(BsMerchantStock merchantStock, RpcPagination<BsMerchantStock> pagination) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//    DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        if (pagination.getCondition() != null) {
            pagination.getCondition().setMerchantCode(dul.getResult().getMerchantCode());
        } else {
        	merchantStock.setMerchantCode(dul.getResult().getMerchantCode());
        	pagination.setCondition(merchantStock);
        }
        Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
        RpcResponse<RpcPagination<BsMerchantStock>> response = bsMerchantStockAdminRemote.pageMerchantStocks(supplyRequest, pagination);
        ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
        if (errCodeEnum == null) {
            response.setMessage("查询成功");
        } else {
            response.setMessage(errCodeEnum.getDescrible());
        }
        return ResultEntity.result(response);
    }
    
    /**
	 * 统计数量
	 * @param id
	 * @return
	 */
	@RequestMapping("statMerchantStocks")
	public ResultEntity<BsMerchantStock> statMerchantStocks(BsMerchantStock bsMerchantStock) {
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		//	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        bsMerchantStock.setMerchantCode(dul.getResult().getMerchantCode());
		Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
		RpcResponse<BsMerchantStock> response = bsMerchantStockAdminRemote.statMerchantStocks(supplyRequest, bsMerchantStock);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}
	
	/**
	 * 分页查询出货数量详情
	 * @param pagination
	 * @return
	 */
	@RequestMapping("pageMerchantStockSell")
	public ResultEntity<RpcPagination<BsMerchantStockSell>> pageMerchantStockSell(BsMerchantStockSell bsMerchantStockSell,RpcPagination<BsMerchantStockSell> pagination) {
		Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
		//	DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        RpcResponse<DealerUserInfo> dul = dealerUserInfoAdminRemote.getDealerUserInfoByDealerUserName(dealerUserInfo.getName());
        bsMerchantStockSell.setMerchantCode(dul.getResult().getMerchantCode());
		Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
		pagination.setCondition(bsMerchantStockSell);
		RpcResponse<RpcPagination<BsMerchantStockSell>> response = bsMerchantStockAdminRemote.pageMerchantStockSell(supplyRequest, pagination);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
			response.setMessage(errCodeEnum.getDescrible());
		}
		return ResultEntity.result(response);
	}

    /**
     * 导出商户库存列表
     * @param response
     * @param response
     * @return
     */
    @RequestMapping("exportMerchantStockList")
    public ModelAndView exportMerchantStockList(HttpServletResponse response, BsMerchantStock bsMerchantStock) {
    	Session session = ThreadLocalCache.getSession();
        DealerUserInfo dealerUserInfo = (DealerUserInfo)session.get(Constants.SessionKey.WEAPP_USERINFO_KEY.getValue());
    	//   DealerUserInfo dealerUserInfo = (DealerUserInfo) session.getAttribute(Constants.SESSION_LOGIN_USER);
        bsMerchantStock.setMerchantCode(dealerUserInfo.getMerchantCode());
        Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("merchant-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
        RpcResponse<List<BsMerchantStock>> responseMerchantStock = bsMerchantStockAdminRemote.listMerchantStocks(supplyRequest, bsMerchantStock);
        List<BsMerchantStock> merchantStockList = responseMerchantStock.getResult();
        List<MerchantStockExcelVo> orderInfoDetailExcelVos = merchantStockList.stream().map(stock -> convertMerchantSTOCKTo(stock)).collect(Collectors.toList());
        List<Object> merchantStock = new ArrayList<Object>();
        merchantStock.addAll(orderInfoDetailExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", merchantStock);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_TEMPLATE_FILE_PATH_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_FOLDER_MERCHANT_STOCK);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_FILE_NAME_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_FORMAT_MERCHANT_STOCK);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_SHEET_NAME_KEY, cn.com.glsx.merchant.supplychain.config.Constants.EXPORT_NAME_MERCHANT_STOCK);
        try {
            excelViewTwo.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelViewTwo);
    }


    /**
     * @param bsMerchantStock
     * @return MerchantStockExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private MerchantStockExcelVo convertMerchantSTOCKTo(BsMerchantStock bsMerchantStock) {
        MerchantStockExcelVo vo = new MerchantStockExcelVo();
        vo.setMerchantName(StringUtils.isEmpty(bsMerchantStock.getMerchantName()) ? "" : bsMerchantStock.getMerchantName());
        vo.setMaterialCode(StringUtils.isEmpty(bsMerchantStock.getMaterialCode()) ? "" : bsMerchantStock.getMaterialCode());
        vo.setMaterialName(StringUtils.isEmpty(bsMerchantStock.getMaterialName()) ? "" : bsMerchantStock.getMaterialName());
        vo.setMaterialDeviceTypeName(StringUtils.isEmpty(bsMerchantStock.getMaterialDeviceTypeName()) ? "" : bsMerchantStock.getMaterialDeviceTypeName());
        vo.setStatSellNum(bsMerchantStock.getStatSellNum());
        vo.setStatRetnNum(bsMerchantStock.getStatRetnNum());
        vo.setStatSettNum(bsMerchantStock.getStatSettNum());
        vo.setStatCainNum(bsMerchantStock.getStatCainNum());
        vo.setStatCaouNum(bsMerchantStock.getStatCaouNum());
        vo.setStatStckNum(bsMerchantStock.getStatStckNum());
        return vo;
    }
}

