package cn.com.glsx.supplychain.remote.bs;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.model.bs.Address;
import cn.com.glsx.supplychain.model.bs.DealerUserInfo;

import javax.lang.model.element.NestingKind;
import java.util.List;

/**
 * @author xiexin
 * @version V1.0
 * @Title: AddressAdminRemote.java
 * @Description: 地址薄管理(针对运营业务后台)
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2019
 */
@ApiService(value = "地址薄管理(针对运营业务后台)", owner = "supplychain_bs", version = "1.0.0")
public interface AddressAdminRemote {

    @ApiMethod("根据姓名获取地址")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "name", notes = "姓名", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<List<Address>> getAddressListByName(String name);

    @ApiMethod("根据商户编号获取地址")
    @ApiResponse(value = "返回经销商用户")
    @ApiParam(name = "merchantCode", notes = "商户编号", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<List<Address>> getAddressListByMerchantCode(String merchantCode);

    @ApiMethod("根据对象获取地址")
    @ApiResponse(value = "返回地址")
    @ApiParam(name = "address", notes = "地址对象", required = true, dataType = ApiParam.DataTypeEnum.LONG)
    RpcResponse<List<Address>> getAddressList(Address address);

    @ApiMethod("新增地址薄")
    @ApiResponse(value = "返回成功条数")
    @ApiParam(name = "address", notes = "地址对象", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
    RpcResponse<Integer> add(Address address);

}
