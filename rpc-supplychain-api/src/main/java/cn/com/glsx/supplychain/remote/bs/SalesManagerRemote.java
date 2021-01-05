package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.bs.MaintainProduct;
import cn.com.glsx.supplychain.model.bs.Sales;

import java.util.List;

/**
 * @author leiming
 * @version V1.0
 * @Title: SalesManagerRemote.java
 * @Description: 销售管理接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "维修产品库接口接口", owner = "supplychain_bs", version = "1.0.0")
public interface SalesManagerRemote {

    @ApiMethod("分页获取销售管理信息表")
    @ApiResponse(value = "返回销售管理分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<Sales>> listSalesManager(RpcPagination<Sales> pagination);

    @ApiMethod("添加销售管理")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "salesManagerList" ,notes = "新增销售管理List",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer>add(List<Sales>salesList);

    @ApiMethod("修改销售管理信息表")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "sales", notes = "需要修改的销售管理信息对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(Sales sales);

    @ApiMethod("查询销售管理根据销售管理id")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "id" ,notes = "查询销售管理",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Sales>getSalesInfoByid(Long id);

    @ApiMethod("批量修改销售管理根据销售管理id")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "ids" ,notes = "批量修改销售管理",required = true,dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer>updateSalesInfoByid(List<Long> ids,Byte status);
}
