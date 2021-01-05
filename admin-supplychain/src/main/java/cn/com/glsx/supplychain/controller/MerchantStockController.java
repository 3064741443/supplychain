package cn.com.glsx.supplychain.controller;


import cn.com.glsx.supplychain.model.*;
import cn.com.glsx.supplychain.model.bs.BsMerchantStock;
import cn.com.glsx.supplychain.model.bs.BsMerchantStockSell;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.remote.bs.BsMerchantStockAdminRemote;
import cn.com.glsx.supplychain.util.ExcelXlsxStreamingViewTwo;
import cn.com.glsx.supplychain.vo.MerchantStockExcelVo;

/**
 * 库存管理
 * 
 * @author xiaowy
 */
@RestController
@RequestMapping("merchantStock")
public class MerchantStockController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BsMerchantStockAdminRemote bsMerchantStockAdminRemote;
	
	@Autowired
    private ExcelXlsxStreamingViewTwo excelViewTwo;
	
	DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 分页查询库存信息
	 * @param pagination
	 * @return
	 */
	@RequestMapping("listMerchantStock")
	public ResultEntity<RpcPagination<BsMerchantStock>> pageMerchantStocks(BsMerchantStock bsMerchantStock, RpcPagination<BsMerchantStock> pagination) {
		Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("admin-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
		if(null == pagination.getCondition()){
			pagination.setCondition(bsMerchantStock);
		}
		RpcResponse<RpcPagination<BsMerchantStock>> response = bsMerchantStockAdminRemote.pageMerchantStocks(supplyRequest, pagination);
		ErrorCodeEnum errCodeEnum = (ErrorCodeEnum) response.getError();
		if(errCodeEnum == null){
			response.setMessage("查询成功");
		}else{
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
		Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("admin-supplychain");
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
		Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("admin-supplychain");
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
     * 根据查询条件 导出库存列表数据
     * @param orderCode
     * @param response
     * @return
     */
    @RequestMapping("exportMerchantStockList")
    public ModelAndView exportMerchantStockList(HttpServletResponse response, BsMerchantStock bsMerchantStock) {
    	Date date = new Date();
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setConsumer("admin-supplychain");
		supplyRequest.setTime(formater.format(date));
		supplyRequest.setVersion("v1.0");
    	
        //获取库存list
        RpcResponse<List<BsMerchantStock>> responseStock = bsMerchantStockAdminRemote.listMerchantStocks(supplyRequest, bsMerchantStock);
        List<BsMerchantStock> merchantStockList = responseStock.getResult();
        List<MerchantStockExcelVo> orderInfoDetailExcelVos = merchantStockList.stream().map(stock -> convertMerchantSTOCKTo(stock)).collect(Collectors.toList());
        List<Object> merchantStock = new ArrayList<Object>();
        merchantStock.addAll(orderInfoDetailExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", merchantStock);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_MERCHANT_STOCK);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_MERCHANT_STOCK);
        map.put(ExcelXlsxStreamingViewTwo.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_MERCHANT_STOCK);
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
