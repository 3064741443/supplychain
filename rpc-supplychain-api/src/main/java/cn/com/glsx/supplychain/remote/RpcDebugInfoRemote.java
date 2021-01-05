package cn.com.glsx.supplychain.remote;

import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiParam;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;
import cn.com.glsx.supplychain.dto.RpcDebugInfo;

/**
 * @author ql
 * @version V1.0
 * @Title: RpcDebugInfoRemote.java
 * @Description: rpc后台调试接口
 * @Company: Didihu.com.cn
 * @Copyright Copyright (c) 2017
 */
@ApiService(value = "rpc后台调试接口", owner = "supplychain", version = "1.0.0")
public interface RpcDebugInfoRemote {
	
	/**
     * 针对redis操作
     *
     * @param 
     * @return
     * @throws
     */
	@ApiMethod("针对redis操作")
    @ApiResponse(value = "返回操作结果字符串")
    @ApiParam(name = "RpcDebugInfo", notes = "设备大类", required = true, dataType = ApiParam.DataTypeEnum.OBJECT)
	RpcResponse<RpcDebugInfo> operatorRedis(RpcDebugInfo record);
	
}
