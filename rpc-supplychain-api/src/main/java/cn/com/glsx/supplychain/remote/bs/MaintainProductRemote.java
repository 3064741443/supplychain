package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.DeviceInfo;
import cn.com.glsx.supplychain.model.bs.MaintainProduct;
import cn.com.glsx.supplychain.model.bs.MaintainProductDetail;

import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: MaintainProductRemote.java
 * @Description: 维修产品库接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "维修产品库接口接口", owner = "supplychain_bs", version = "1.0.0")
public interface MaintainProductRemote {

    @ApiMethod("分页获取维修产品信息表")
    @ApiResponse(value = "返回维修产品分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<MaintainProduct>> listMaintainProduct(RpcPagination<MaintainProduct> pagination);

    @ApiMethod("新增维修产品信息表")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "maintainProductList", notes = "新增LIST", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(MaintainProduct maintainProduct);

    @ApiMethod("修改维修产品信息表")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "maintainProduct", notes = "需要修改的维修产品信息对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updateById(MaintainProduct maintainProduct);

    @ApiMethod("查询维修产品信息表")
    @ApiResponse(value = "查询产品维修信息")
    @ApiParam(name = "maintainProduct", notes = "查询产品维修信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<MaintainProduct>getMaintainProductInfo(MaintainProduct maintainProduct);

    @ApiMethod("修改维修详情")
    @ApiResponse(value = "修改维修详情")
    @ApiParam(name = "maintainProductDetail", notes = "修改维修详情信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer>updateByMaintainProductDetail(MaintainProductDetail maintainProductDetail);

    @ApiMethod("批量修改维修详情")
    @ApiResponse(value = "批量修改维修详情")
    @ApiParam(name = "maintainProductDetailList", notes = "批量修改维修详情信息", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer>batchUpdateMaintainProductDetail(List<MaintainProductDetail> maintainProductDetailList);


    @ApiMethod("检验ICCID是否为glsx的卡")
    @ApiResponse(value = "是否为glsx的卡")
    @ApiParam(name = "maintainProductDetailList", notes = "是否为glsx的卡", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<DeviceInfo>checkIccid(DeviceInfo deviceInfo);
}
