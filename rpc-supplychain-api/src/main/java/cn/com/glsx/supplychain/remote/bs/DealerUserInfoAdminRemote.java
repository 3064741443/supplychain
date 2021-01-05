package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcPagination;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;

import java.util.HashMap;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: DealerUserInfoAdminRemote.java
 * @Description: 经销商用户接口(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "经销商用户接口(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface DealerUserInfoAdminRemote {

    @ApiMethod("获取经销商用户分页列表")
    @ApiResponse(value = "返回经销商用户分页列表")
    @ApiParam(name = "pagination", notes = "分页对象，包含条件查询参数", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<RpcPagination<DealerUserInfo>> listDealerUserInfo(RpcPagination<DealerUserInfo> pagination);

    @ApiMethod("获取老商户列表")
    @ApiResponse(value = "返回老商户列表")
    @ApiParam(name = "condition", notes = "查询条件", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<List> getOldMerchantInfo(HashMap<String, Object> condition, Integer targetPage, Integer pageSize);

    @ApiMethod("根据用户名获取经销商用户")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "dealerUserName", notes = "经销商用户名", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<DealerUserInfo> getDealerUserInfoByDealerUserName(String dealerUserName);

    @ApiMethod("根据商户编号获取经销商用户")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "merchantCode", notes = "经销商商户编号", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<DealerUserInfo> getDealerUserInfoByMerchantCode(String merchantCode);

    @ApiMethod("新增经销商用户")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "dealerUserInfo", notes = "经销商用户对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(DealerUserInfo dealerUserInfo);

    @ApiMethod("修改经销商用户")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "dealerUserInfo", notes = "经销商用户对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> update(DealerUserInfo dealerUserInfo);

    @ApiMethod("根据用户名修改经销商用户密码")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "dealerUserInfo", notes = "经销商用户对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> updatePasswordByName(String name,String password,String updatedBy);

    @ApiMethod("根据商户ID获取经销商用户")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "dealerUserInfo", notes = "经销商商户ID", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<DealerUserInfo> getDelerUseInfoById(DealerUserInfo dealerUserInfo);

    @ApiMethod("根据商户ID修改经销商用户")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "dealerUserInfo", notes = "经销商商户ID", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> updateByDealerUserId(DealerUserInfo dealerUserInfo);

    @ApiMethod("根据商户ID修改经销商用户删除标识")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "dealerUserInfo", notes = "经销商商户ID", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<Integer> deleteByDealerUserId(DealerUserInfo dealerUserInfo);

    @ApiMethod("根据商户对象获取经销商用户")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "dealerUserInfo", notes = "经销商商户对象", required = true, dataType = ApiParam.DataTypeEnum.STRING)
    RpcResponse<List<DealerUserInfo>>gteDealerUserInfoList(DealerUserInfo dealerUserInfo);
}
