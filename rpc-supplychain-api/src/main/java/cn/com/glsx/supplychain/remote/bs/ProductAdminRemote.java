package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.bs.Product;
import cn.com.glsx.supplychain.model.bs.ProductDetail;
import cn.com.glsx.supplychain.model.bs.ProductHistoryPrice;
import cn.com.glsx.supplychain.model.bs.ProductType;

import java.util.Date;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: ProductAdminRemote.java
 * @Description: 产品管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "产品管理(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface ProductAdminRemote {

    @ApiMethod("获取产品分页列表")
    @ApiResponse(value = "返回产品分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<Product>> listProduct(RpcPagination<Product> pagination);

    @ApiMethod("获取产品列表")
    @ApiResponse(value = "返回产品列表")
    @ApiParam(name = "product", notes = "产品对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<Product>> getProductList(Product product);

    @ApiMethod("根据产品编号获取产品对象")
    @ApiResponse(value = "返回产品对象")
    @ApiParam(name = "productCode", notes = "产品编号", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Product> getProductByProductCode(String productCode);

    @ApiMethod("根据产品编号获取产品历史价格")
    @ApiResponse(value = "返回产品历史价格")
    @ApiParam(name = "productCode", notes = "产品编号", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductHistoryPrice>> getProductHistoryPriceByProductCode(String productCode);

    @ApiMethod("根据产品编号和时间获取产品历史价格")
    @ApiResponse(value = "返回产品历史价格")
    @ApiParam(name = "productCode", notes = "产品编号", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductHistoryPrice>> getProductHistoryPrice(String productCode,Date date);

    @ApiMethod("新增产品")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "product", notes = "产品对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(Product product);

    @ApiMethod("修改产品")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "product", notes = "产品对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(Product product);

    @ApiMethod("修改产品价格")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "product", notes = "产品对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateProductPriceByCode(String code,Double amount,String updatedBy,Date time);

    @ApiMethod("根据产品编号获取产品详情对象")
    @ApiResponse(value = "返回产品详情对象")
    @ApiParam(name = "productCode", notes = "产品编号", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductDetail>> getProductDetailByProductCode(String productCode);

    @ApiMethod("根据产品类型获取产品类型List")
    @ApiResponse(value = "返回产品类型List对象")
    @ApiParam(name = "productType", notes = "产品类型", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List<ProductType>> getProductTypeList(ProductType productType);

    @ApiMethod("根据产品类型获取产品类型")
    @ApiResponse(value = "返回产品类型对象")
    @ApiParam(name = "productType", notes = "产品类型", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<ProductType> getProductType(ProductType productType);
}
