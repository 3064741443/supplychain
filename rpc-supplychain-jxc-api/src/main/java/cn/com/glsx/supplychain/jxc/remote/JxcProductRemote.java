package cn.com.glsx.supplychain.jxc.remote;

import java.util.List;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.jxc.dto.JXCMTBsMerchantMaterialCheckDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitDTO;
import cn.com.glsx.supplychain.jxc.dto.JXCMTProductSplitListQueryDTO;

/**
 * @author ql
 * @version V1.0
 * @Title: JxcCommonRemote.java
 * @Description: 进销存重产品部分服务接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "进销存重产品部分服务接口", owner = "supplychain", version = "1.0.0")
public interface JxcProductRemote {

	@ApiMethod("获取产品列表")
	@ApiResponse(value = "获取产品列表(进销存客户端)")
	@ApiParam(name = "JXCMTProductSplitDTO", notes = "获取产品列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTProductSplitDTO>> listJxcProduct(JXCMTProductSplitListQueryDTO record);

	@ApiMethod("获取审核物料列表")
	@ApiResponse(value = "获取审核物料列表")
	@ApiParam(name = "JXCMTProductSplitDTO", notes = "获取产品列表", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	public RpcResponse<List<JXCMTBsMerchantMaterialCheckDTO>>listMaterialCheck(JXCMTBsMerchantMaterialCheckDTO record);
}
