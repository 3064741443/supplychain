package cn.com.glsx.supplychain.remote;


import cn.com.glsx.framework.core.annotation.ApiMethod;
import cn.com.glsx.framework.core.annotation.ApiResponse;
import cn.com.glsx.framework.core.annotation.ApiService;
import cn.com.glsx.framework.core.beans.rpc.RpcResponse;

@ApiService(value = "拆分订单", owner = "supplychain", version = "1.0.0")
public interface HandleHistoricalDataRemote {
	
	
	@ApiMethod("对历史数据一个订单多个产品进行拆分成一个订单一个产品")
	@ApiResponse(value = "返回拆分结果")
	RpcResponse<Integer> splitOrder();


	@ApiMethod("产品编号修改")
	@ApiResponse(value = "返回拆分结果")
	RpcResponse<Integer> updateProductCode();
}
