package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.DeviceCode;
import cn.com.glsx.supplychain.model.bs.*;

import java.util.Date;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: SalesSummarizingRemote.java
 * @Description: 销售汇总接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "销售汇总接口(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface SalesSummarizingRemote {

    @ApiMethod("获取销售汇总分页列表")
    @ApiResponse(value = "返回消费汇总分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<SalesSummarizing>> listSalesSummarizing(RpcPagination<SalesSummarizing> pagination);

    @ApiMethod("获取销售汇总详情")
    @ApiResponse(value = "返回消费汇总分页列表")
    @ApiParam(name = "salesSummarizing", notes = "销售汇总对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<SalesSummarizingDetail>> getSalesSummarizingDetailBySalesSummarizingId(Long salesSummarizingId);

    @ApiMethod("新增销售汇总")
    @ApiResponse(value = "返回条数")
    @ApiParam(name = "salesSummarizing", notes = "销售汇总对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(SalesSummarizing salesSummarizing);

    @ApiMethod("修改销售汇总")
    @ApiResponse(value = "返回条数")
    @ApiParam(name = "salesSummarizing", notes = "销售汇总对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(SalesSummarizing salesSummarizing);

    @ApiMethod("获取订单时间")
    @ApiResponse(value = "时间List")
    @ApiParam(name = "", notes = "", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<Date>> getDate();


    @ApiMethod("销售汇总列表导出")
    @ApiResponse(value = "销售汇总导出列表")
    @ApiParam(name = "salesSummarizing", notes = "销售汇总实体", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<SalesSummarizing>> exportSalesSummarizingExit(SalesSummarizing salesSummarizing);

    @ApiMethod("批量新增销售汇总")
    @ApiResponse(value = "返回条数")
    @ApiParam(name = "salesSummarizing", notes = "销售汇总对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addSalesSummarizingList(List<SalesSummarizing> salesSummarizingList);

    @ApiMethod("获取销售汇总物料详情")
    @ApiResponse(value = "获取销售汇总物料详情列表")
    @ApiParam(name = "salesSummarizingMaterialDetail", notes = "销售汇总物料详情", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<SalesSummarizingMaterialDetail>> getSalesSummarizingMaterialDetailList(SalesSummarizingMaterialDetail salesSummarizingMaterialDetail);
}
