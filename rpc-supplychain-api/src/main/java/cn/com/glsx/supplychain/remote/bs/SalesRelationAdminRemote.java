package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.bs.SalesRelation;

import java.util.List;


/**
 * @author leiming
 * @version V1.0
 * @Title: SalesRelationAdminRemote.java
 * @Description: 销售管理与销售汇总关系(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "销售管理与销售汇总关系(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface SalesRelationAdminRemote {

    @ApiMethod("查询销售管理关系列表")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "salesRelation", notes = "销售对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<List<SalesRelation>> getSalesRelation(SalesRelation salesRelation);

    @ApiMethod("新增销售管理与销售汇总关系")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "salesRelation", notes = "销售对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(SalesRelation salesRelation);

}
