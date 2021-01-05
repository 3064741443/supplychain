package cn.com.glsx.supplychain.controller.bs;

/**
 * @ClassName SalesSummarizingController
 * @Author admin
 * @Param
 * @Date 2019/3/5 14:54
 * @Version
 **/

import cn.com.glsx.framework.core.beans.ResultEntity;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.config.Constants;
import cn.com.glsx.supplychain.enums.ErrorCodeEnum;
import cn.com.glsx.supplychain.model.bs.Product;
import cn.com.glsx.supplychain.model.bs.SalesRelation;
import cn.com.glsx.supplychain.model.bs.SalesSummarizing;
import cn.com.glsx.supplychain.model.bs.SalesSummarizingDetail;
import cn.com.glsx.supplychain.remote.bs.ProductAdminRemote;
import cn.com.glsx.supplychain.remote.bs.SalesRelationAdminRemote;
import cn.com.glsx.supplychain.remote.bs.SalesSummarizingRemote;
import cn.com.glsx.supplychain.util.ExcelXlsxStreamingViewSalesSummzrizing;
import cn.com.glsx.supplychain.vo.SalesSummarizingExcelVo;
import com.glsx.oms.bigdata.service.fb.api.IMaterialDubboService;
import com.glsx.oms.bigdata.service.fb.model.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 经销商管理
 *
 * @author leiming
 */
@RestController
@RequestMapping("salesSummarizing")
public class SalesSummarizingController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductAdminRemote productAdminRemote;

    @Autowired
    private SalesSummarizingRemote salesSummarizingRemote;

    @Autowired
    private ExcelXlsxStreamingViewSalesSummzrizing excelXlsxStreamingViewSalesSummzrizing;

    @Autowired
    private IMaterialDubboService iMaterialDubboService;

    @Autowired
    private SalesRelationAdminRemote salesRelationAdminRemote;

    /**
     * 销售管理列表（分页）
     */

    @RequestMapping("getAllSalesMana")
    public ResultEntity<RpcPagination<SalesSummarizing>> listSalesSummarizing(@RequestBody RpcPagination<SalesSummarizing> pagination) {
        RpcResponse<RpcPagination<SalesSummarizing>> response = salesSummarizingRemote.listSalesSummarizing(pagination);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * (销售汇总)获取产品信息
     *
     * @return
     */
    @RequestMapping("getSalesProductList")
    public ResultEntity<List<Product>> getSalesProductList(@RequestBody Product product) {
        RpcResponse<List<Product>> response = productAdminRemote.getProductList(product);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 根据id获取销售管理详情
     */
    @RequestMapping("getSalesManaInfo")
    public ResultEntity<List<SalesSummarizingDetail>> getSalesSummarizingDetailBySalesSummarizingId(@RequestBody Long id) {
        RpcResponse<List<SalesSummarizingDetail>> response = salesSummarizingRemote.getSalesSummarizingDetailBySalesSummarizingId(id);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 修改销售汇总(提交对账)
     */
    @RequestMapping("updateSalesByid")
    public ResultEntity<Integer> updateById(@RequestBody SalesSummarizing salesSummarizing) {
        RpcResponse<Integer> response = salesSummarizingRemote.updateById(salesSummarizing);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }


    /**
     * 销售汇总获取日期
     */
    @RequestMapping("getDate")
    public ResultEntity<List<Date>> getDate() {
        RpcResponse<List<Date>> response = salesSummarizingRemote.getDate();
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 查询销售管理与销售汇总关系信息
     */
    @RequestMapping("getSalesRelation")
    public ResultEntity<List<SalesRelation>>getSalesRelation(@RequestBody SalesRelation salesRelation){
        RpcResponse<List<SalesRelation>> response = salesRelationAdminRemote.getSalesRelation(salesRelation);
        if (response.getError() == null) {
            response.setMessage("查询成功");
        } else {
            logger.error(response.getMessage());
        }
        return ResultEntity.result(response);
    }

    /**
     * 导出销售汇总数据
     *
     * @param
     * @param response
     * @return
     */
    @GetMapping("exportSales")
    public ModelAndView exportSalesSummarizingExit(HttpServletResponse response,HttpServletRequest request) {
        SalesSummarizing salesSummarizing = new SalesSummarizing();
        salesSummarizing.setPageSize(Constants.pageSizeMer);
        Byte lt = new Byte(request.getParameter("status"));
        Byte status = new Byte(lt);
        salesSummarizing.setStatus(status);
        RpcResponse<List<SalesSummarizing>> responseSales = salesSummarizingRemote.exportSalesSummarizingExit(salesSummarizing);
        List<SalesSummarizing> salesSummarizingList = responseSales.getResult();
        List<SalesSummarizingExcelVo> salesSummarizingExcelVos = salesSummarizingList.stream().map(salesSummarizingOne -> convertTo(salesSummarizingOne)).collect(Collectors.toList());
        List<Object> sales = new ArrayList<Object>();
        sales.addAll(salesSummarizingExcelVos);
        Map<String, Object> map = new HashMap<>();
        map.put("objs", sales);
        map.put(ExcelXlsxStreamingViewSalesSummzrizing.EXCEL_TEMPLATE_FILE_PATH_KEY, Constants.EXPORT_FOLDER_SALES);
        map.put(ExcelXlsxStreamingViewSalesSummzrizing.EXCEL_FILE_NAME_KEY, Constants.EXPORT_FORMAT_SALES);
        map.put(ExcelXlsxStreamingViewSalesSummzrizing.EXCEL_SHEET_NAME_KEY, Constants.EXPORT_NAME_SALES);
        try {
            excelXlsxStreamingViewSalesSummzrizing.buildExcelDocument(map, null, null, response);
        } catch (Exception e) {
            logger.error("导出异常：" + e.getMessage(), e);
        }
        return new ModelAndView(excelXlsxStreamingViewSalesSummzrizing);

    }


    /**
     * @param @param salesSummarizing
     * @return SalesSummarizingExcelVo
     * @throws
     * @Title: convertTo
     * @Description: 对象转换
     * @author
     */
    private SalesSummarizingExcelVo convertTo(SalesSummarizing salesSummarizingOne) {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        SalesSummarizingExcelVo vo = new SalesSummarizingExcelVo();
        vo.setBillTypeID(StringUtils.isEmpty(salesSummarizingOne.getBillTypeID()) ? "" : salesSummarizingOne.getBillTypeID());
        vo.setBillTypeName(StringUtils.isEmpty(salesSummarizingOne.getBillTypeName()) ? "" : salesSummarizingOne.getBillTypeName());
        vo.setBillNo(StringUtils.isEmpty(salesSummarizingOne.getBillNo()) ? "" : salesSummarizingOne.getBillNo());
        vo.setSaleOrgId(salesSummarizingOne.getSaleOrgId() == null ? 0 : salesSummarizingOne.getSaleOrgId());
        vo.setSaleOrgName(StringUtils.isEmpty(salesSummarizingOne.getSaleOrgName()) ? "" : salesSummarizingOne.getSaleOrgName());
        vo.setCustId(StringUtils.isEmpty(salesSummarizingOne.getCustId())?"":salesSummarizingOne.getCustId());
        vo.setDate(StringUtils.isEmpty(sp.format(salesSummarizingOne.getDate()))?"" : sp.format(salesSummarizingOne.getDate()));
        vo.setCustName(StringUtils.isEmpty(salesSummarizingOne.getCustName()) ? "" : salesSummarizingOne.getCustName());
        vo.setSettleCurrId(StringUtils.isEmpty(salesSummarizingOne.getSettleCurrId()) ? "" : salesSummarizingOne.getSettleCurrId());
        vo.setSettleCurrName(StringUtils.isEmpty(salesSummarizingOne.getSettleCurrName()) ? "" : salesSummarizingOne.getSettleCurrName());
        vo.setMaterialId(StringUtils.isEmpty(salesSummarizingOne.getMaterialId()) ? "" : salesSummarizingOne.getMaterialId());
        /*//查询物料名称
        Material material = new Material();
        material.setMaterialCode(salesSummarizingOne.getMaterialId());
        List<Material> materials = iMaterialDubboService.list(material);
        for (int i = 0; i < materials.size(); i++) {
            materialName = materials.get(i).getMaterialName();
        }*/
        vo.setMaterialName(StringUtils.isEmpty(salesSummarizingOne.getMaterialName()) ? "" : salesSummarizingOne.getMaterialName());
        vo.setUnitName(StringUtils.isEmpty(salesSummarizingOne.getUnitName()) ? "" : salesSummarizingOne.getUnitName());
        vo.setSalesQuantity(salesSummarizingOne.getSalesQuantity() == null ? 0 : salesSummarizingOne.getSalesQuantity());
        vo.setTaxPrice(salesSummarizingOne.getTaxPrice() == null ? 0.0 : salesSummarizingOne.getTaxPrice());
        vo.setOnlyFree(salesSummarizingOne.getOnlyFree() == null ? false : salesSummarizingOne.getOnlyFree());
        vo.setEntryTaxRate(salesSummarizingOne.getEntryTaxRate() == null ? 0 : salesSummarizingOne.getEntryTaxRate());
        vo.setSettleOrgIds(salesSummarizingOne.getSettleOrgIds() == null ? 0 : salesSummarizingOne.getSettleOrgIds());
        vo.setSettleOrgIdsName(StringUtils.isEmpty(salesSummarizingOne.getSettleOrgIdsName()) ? "" : salesSummarizingOne.getSettleOrgIdsName());
        vo.setReserveType(StringUtils.isEmpty(salesSummarizingOne.getReserveType()) ? "" : salesSummarizingOne.getReserveType());
        vo.setWarehouseCode(StringUtils.isEmpty(salesSummarizingOne.getWarehouseCode())?"":salesSummarizingOne.getWarehouseCode());
        vo.setWarehouseName(StringUtils.isEmpty(salesSummarizingOne.getWarehouseName())?"" : salesSummarizingOne.getWarehouseName());
        vo.setDeliveryDate(salesSummarizingOne.getDeliveryDate());
        vo.setPlanQuantity(salesSummarizingOne.getPlanQuantity() == null ? 0 : salesSummarizingOne.getPlanQuantity());
        return vo;
    }
}
