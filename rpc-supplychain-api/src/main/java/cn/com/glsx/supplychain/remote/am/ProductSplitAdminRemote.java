package cn.com.glsx.supplychain.remote.am;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.am.ProductSplit;
import cn.com.glsx.supplychain.model.am.ProductSplitDetail;
import cn.com.glsx.supplychain.model.am.ProductSplitHistoryPrice;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.Product;

import java.util.List;

/**
 * @author leiming
 * @version V1.0
 * @Title: ProductSplitAdminRemote.java
 * @Description: 产品拆分管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "产品拆分管理(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface ProductSplitAdminRemote {

    @ApiMethod("分页获取产品拆分列表")
    @ApiResponse(value = "返回产品拆分列表")
    @ApiParam(name = "name", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<ProductSplit>> listProductSplit(RpcPagination<ProductSplit> pagination);

    @ApiMethod("新增产品拆分")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(ProductSplit productSplit);

    @ApiMethod("修改产品拆分")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(ProductSplit productSplit);

    @ApiMethod("查询产品拆分信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<ProductSplit> getProductSplitInfo(ProductSplit productSplit);

    @ApiMethod("查询产品拆分信息List")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductSplit>> getProductSplitList(ProductSplit productSplit);

    @ApiMethod("查询产品拆分详情信息List,仅供进销存下单页面使用")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductSplitDetail>> getProductSplitDetailByProductTypeZeroList(List<ProductSplitDetail> productSplitDetailList);

    @ApiMethod("查询产品拆分详情信息List")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplitDetail", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductSplitDetail>> getProductSplitDetailList(ProductSplitDetail productSplitDetail);

    @ApiMethod("修改产品拆分删除标识")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateDeletedFlagById(ProductSplit productSplit);

    @ApiMethod("修改产品拆分状态")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplit", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateProductSplitStatus(ProductSplit productSplit);

    @ApiMethod("删除对应生效时间的历史价格信息")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplitHistoryPrice", notes = "产品拆分对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> deleteProductSplitHistoryPrice(ProductSplitHistoryPrice productSplitHistoryPrice);

    @ApiMethod("分页获取产品详情拆分列表")
    @ApiResponse(value = "返回产品拆分详情列表")
    @ApiParam(name = "name", notes = "产品拆分详情对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<RpcPagination<ProductSplitDetail>> listProductSplitDetail(RpcPagination<ProductSplitDetail> pagination);

    @ApiMethod("新增产品拆分历史价格List")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplitHistoryPriceList", notes = "产品拆分对象List", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> addProductSplitHistoryPriceList(List<ProductSplitHistoryPrice> productSplitHistoryPriceList);

    @ApiMethod("修改产品拆分对象")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "productSplitHistoryPrice", notes = "产品拆分对象List", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateProductSplitHistoryPriceByTime(List<ProductSplitHistoryPrice> productSplitHistoryPriceList);

/*    @ApiMethod("修改新增产品拆分价格")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "batchInsertOnDuplicateKeyUpdateProductSplitHistor", notes = "产品价格对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> batchInsertOnDuplicateKeyUpdateProductSplitHistor(List<ProductSplitHistoryPrice> productSplitHistoryPriceList);*/


}
